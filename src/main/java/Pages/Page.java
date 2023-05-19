package Pages;

public abstract class Page {
    public com.microsoft.playwright.Page page;

    public Page(com.microsoft.playwright.Page page) {
        this.page = page;
    }
}
