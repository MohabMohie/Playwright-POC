package Pages.Google;

import Pages.Page;
import io.qameta.allure.Step;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class Results extends Page {
    private final String resultStats_label = "#result-stats";

    public Results(com.microsoft.playwright.Page page) {
        super(page);
    }

    @Step("Assert that result stats are not empty.")
    public void assertResultStatsAreNotEmpty() {
        assertThat(page.locator(resultStats_label)).not().hasText("");
    }
}
