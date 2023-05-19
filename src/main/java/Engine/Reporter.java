package Engine;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import io.qameta.allure.Allure;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Reporter {
    @Getter
    private static final org.apache.logging.log4j.Logger logger = Logger.getLogger();
    @Setter
    private static Path videoFilePath;

    public static void attachScreenshot(ThreadLocal<Page> page) throws IOException {
        var finalScreenshotPath = Paths.get("target/screenshot.png");
        page.get().screenshot(new Page.ScreenshotOptions()
                .setPath(finalScreenshotPath)
                .setFullPage(true));
        logger.log(Level.INFO, "Attaching screenshot");
        Allure.getLifecycle().addAttachment("Screenshot", "image/png", ".png", Files.readAllBytes(finalScreenshotPath));
    }

    public static void attachTrace(ThreadLocal<BrowserContext> browserContext) throws IOException {
        var traceFilePath = Paths.get("target/trace.zip");
        browserContext.get().tracing().stop(new Tracing.StopOptions()
                .setPath(traceFilePath));
        logger.log(Level.INFO, "Attaching trace");
        Allure.getLifecycle().addAttachment("Trace: https://trace.playwright.dev/", "application/zip", ".zip", Files.readAllBytes(traceFilePath));

    }

    public static void attachVideo() throws IOException {
        Reporter.getLogger().log(Level.INFO, "Attaching recording");
        Allure.getLifecycle().addAttachment("Recording", "video/webm", ".webm", Files.readAllBytes(videoFilePath));
    }
}
