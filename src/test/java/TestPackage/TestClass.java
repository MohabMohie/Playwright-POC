package TestPackage;

import Fixtures.MainTestFixture;
import Pages.Google.Home;
import Pages.Google.Results;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TestClass extends MainTestFixture {
    @Description("When I navigate to google, and I search for Microsoft Playwright, then result stats will not be empty.")
    @DisplayName("Google Search - Flat Design")
    @Test
    void flatTest() {
        page.navigate("https://www.google.com/ncr");
        page.type("//*[@type][@name='q']", "Microsoft Playwright");
        page.keyboard().press("Enter");
        Locator resultStats = page.locator("#result-stats");
        assertThat(resultStats).not().hasText("");
    }

    @Description("When I navigate to google, and I search for Microsoft Playwright, then result stats will not be empty.")
    @DisplayName("Google Search - Basic POM Design")
    @Test
    void basicPomTest() {
        var home = new Home(page);
        home.navigate();
        home.searchForQuery("Microsoft Playwright");
        var results = new Results(page);
        results.assertResultStatsAreNotEmpty();
    }

    @Description("When I navigate to google, and I search for Microsoft Playwright, then result stats will not be empty.")
    @DisplayName("Google Search - Fluent POM Design")
    @Test
    void fluentPomTest() {
        new Home(page)
                .navigate()
                .searchForQuery("Microsoft Playwright")
                .assertResultStatsAreNotEmpty();
    }
}
