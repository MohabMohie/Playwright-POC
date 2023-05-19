package Fixtures;

import Engine.BrowserFactory;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;

import java.io.IOException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MainTestFixture {
    public Page page;

    @Step("Setting up browser")
    @BeforeAll
    static void beforeAll() {
        BrowserFactory.init();
    }

    @Step("Closing browser")
    @AfterAll
    static void afterAll() throws IOException {
        BrowserFactory.terminate();
    }

    @Step("Setting up browser context")
    @BeforeEach
    void beforeEach() {
        page = BrowserFactory.initContext();
    }

    @Step("Closing browser context")
    @AfterEach
    void afterEach() throws IOException {
        BrowserFactory.terminateContext();
    }
}
