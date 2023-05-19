package Engine.Props;

import Engine.Properties;
import Engine.Reporter;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.Level;

@SuppressWarnings("unused")
@Sources({"system:properties", "file:src/test/resources/playwright.properties"})
public interface Playwright extends PropsManager {
    private static void setProperty(String key, String value) {
        var updatedProps = new java.util.Properties();
        updatedProps.setProperty(key, value);
        Properties.playwright = ConfigFactory.create(Playwright.class, updatedProps);
        // temporarily set the system property to support hybrid read/write mode
        System.setProperty(key, value);
        Reporter.getLogger().log(Level.INFO, "Setting \"" + key + "\" property with \"" + value + "\".");
    }

    @Key("browserType")
    @DefaultValue("chromium")
    String browserType();

    @Key("headlessMode")
    @DefaultValue("true")
    boolean headlessMode();

    default PropsManager.SetProperty set() {
        return new SetProperty();
    }

    class SetProperty implements PropsManager.SetProperty {
        public void browserType(String value) {
            setProperty("browserType", value);
        }

        public void headlessMode(boolean value) {
            setProperty("headlessMode", String.valueOf(value));
        }
    }

}