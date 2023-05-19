package Pages.Google;

import Pages.Page;
import io.qameta.allure.Step;

public class Home extends Page {
    private final String url = "https://www.google.com/ncr";
    private final String search_input = "//*[@type][@name='q']";

    public Home(com.microsoft.playwright.Page page) {
        super(page);
    }

    @Step("Navigate to " + url)
    public Home navigate() {
        page.navigate(url);
        return this;
    }

    @Step("Search for {query}")
    public Results searchForQuery(String query) {
        page.locator(search_input).type(query);
        page.keyboard().press("Enter");
        return new Results(page);
    }
}
