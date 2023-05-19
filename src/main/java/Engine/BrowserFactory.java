package Engine;

import com.microsoft.playwright.*;
import io.qameta.allure.Allure;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BrowserFactory {
    private static final ThreadLocal<Playwright> playwright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browser = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> browserContext = new ThreadLocal<>();
    static ThreadLocal<Page> page = new ThreadLocal<>();

    public static void init() {
        Properties.playwright = ConfigFactory.create(Engine.Props.Playwright.class);
        createSession();
        launchBrowser();
    }

    public static void terminate() throws IOException {
        Reporter.getLogger().log(Level.INFO, "Attaching execution log");
        Allure.getLifecycle().addAttachment("Execution log", "text/x-log", ".log", Files.readAllBytes(Path.of("target/logs/log4j.log")));
        Reporter.getLogger().log(Level.INFO, "Closing session");
        playwright.get().close();
    }

    private static void createSession() {
        Reporter.getLogger().log(Level.INFO, "Starting session");
        playwright.set(Playwright.create());
    }

    private static void launchBrowser() {
        BrowserType browserType = switch (Properties.playwright.browserType().toLowerCase()) {
            case "firefox" -> playwright.get().firefox();
            case "webkit" -> playwright.get().webkit();
            default -> playwright.get().chromium();
        };
        Reporter.getLogger().log(Level.INFO, "Launching \"" + browserType.name() + "\"");
//
//        Map<String , String > environmentVariables = new HashMap<>();
//        environmentVariables.put("DEBUG","pw:api");

        browser.set(browserType.launch(new BrowserType.LaunchOptions()
                        .setHeadless(Properties.playwright.headlessMode())
//                .setEnv(environmentVariables)
        ));
    }

    public static synchronized Page initContext() {
        Reporter.getLogger().log(Level.INFO, "Launching new context");
        browserContext.set(browser.get().newContext(new Browser.NewContextOptions()
                        .setRecordVideoDir(Paths.get("target/videos/"))
                        .setRecordVideoSize(640, 480)
                        .setAcceptDownloads(true)
//                .setBaseURL("")
                        .setViewportSize(1920, 1080)
        ));

        Reporter.getLogger().log(Level.INFO, "Starting trace");
        browserContext.get().tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
        Reporter.getLogger().log(Level.INFO, "Launching new page");
        page.set(browserContext.get().newPage());

        Reporter.setVideoFilePath(page.get().video().path());
        return page.get();
    }

    public static void terminateContext() throws IOException {
        Reporter.attachScreenshot(page);
        Reporter.attachTrace(browserContext);
        // navigate to https://trace.playwright.dev/ to open the trace
        // npm install allure@latest
        // allure serve allure-results
        Reporter.getLogger().log(Level.INFO, "Closing context");
        browserContext.get().close();

        Reporter.attachVideo();
    }
}
