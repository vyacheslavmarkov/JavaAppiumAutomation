package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchPageObject extends MainPageObject {

    private static final String
        SEARCH_INIT_ELEMENT = "//*[contains(@text,'Search Wikipedia')]",
        SEARCH_INPUT = "//*[contains(@text,'Search…')]",
        SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn",
        SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
        SEARCH_RESULT_ELEMENT = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
        SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text='No results found']",
        SEARCH_RESULT_ELEMENT_TITLE = "org.wikipedia:id/page_list_item_title",
        SEARCH_RESULT_ELEMENT_BY_TITLE_SUBTITLE_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_container']/android.widget.LinearLayout/*[@resource-id='org.wikipedia:id/page_list_item_title' and @text='{TITLE}']/..//*[@resource-id='org.wikipedia:id/page_list_item_description' and @text='{DESCRIPTION}']";

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getResultSearchElement(String substring) {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getResultSearchElementByTitleAndDescription(String title, String description) {
        return SEARCH_RESULT_ELEMENT_BY_TITLE_SUBTITLE_TPL.replace("{TITLE}", title).replace("{DESCRIPTION}", description);
    }
    /* TEMPLATES METHODS */

    public WebElement initSearchInput() {
        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT), "Cannot find and click search init element", 5);
        return this.waitForElementPresent(By.xpath(SEARCH_INPUT), "Cannot find search input after clicking search init element");
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON), "Cannot find search cancel button", 5);
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON), "Search cancel button is still present!", 5);
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON), "Cannot find and click search cancel button", 5);
    }

    public void typeSearchLine(String search_line) {
        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT), search_line, "Cannot find and type into search input", 5);
    }

    public void waitForSearchResult(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(By.xpath(search_result_xpath), "Cannot find search result with substring " + substring);
    }

    public List<WebElement> waitForSearchResultsTitles() {
        return this.waitForElementsPresent(By.id(SEARCH_RESULT_ELEMENT_TITLE), "Cannot find search results titles", 15);
    }

    public void clickByArticleWithSubstring(String substring) {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(By.xpath(search_result_xpath), "Cannot find and click search result with substring " + substring, 10);
    }

    public int getAmountOfFoundArticles() {
        this.waitForElementPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "Cannot find anything by the request",
                15
        );

        return this.getAmountOfElements(
                By.xpath(SEARCH_RESULT_ELEMENT)
        );
    }

    public void waitForEmptyResultsLabel() {
        this.waitForElementPresent(
                By.xpath(SEARCH_EMPTY_RESULT_ELEMENT),
                "Cannot find empty result element",
                15
        );
    }

    public void assertThereIsNoResultOfSearch() {
        this.assertElementNotPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "We supposed not to find any results"
        );
    }

    public void waitForSearchResultsDisappear() {
        this.waitForElementNotPresent(
                By.xpath(SEARCH_RESULT_ELEMENT),
                "Search results are not cleared!",
                5
        );
    }

    public WebElement waitForElementByTitleAndDescription(String title, String description) {
        String element_by_title_and_description_xpath = getResultSearchElementByTitleAndDescription(title, description);
        return this.waitForElementPresent(
                By.xpath(element_by_title_and_description_xpath),
                "Cannot find search result by given title \"" + title + "\" and description \"" + description + "\"",
                15);
    }
}