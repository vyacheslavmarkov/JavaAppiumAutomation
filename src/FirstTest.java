import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FirstTest extends CoreTestCase {

/*    @Test
    public void testSearchInputPlaceholder() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5
        );

        WebElement element = MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search input",
                5
        );

        String searchInputPlaceholder = element.getAttribute("text");
        assertEquals(
                "Search input placeholder doesn't contain expected text!",
                "Search…",
                searchInputPlaceholder
        );
    }

    @Test
    public void testCheckResultsAndCancelSearch() {
        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find Search Wikipedia input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                "Ford",
                "Cannot find search input",
                5
        );

        List<WebElement> listItems = MainPageObject.waitForElementsPresent(
                By.id("org.wikipedia:id/page_list_item_container"),
                "Cannot find search results",
                15
        );

        // check that there are several items (> 1) in the results list
        int itemsNumber = listItems.size();
        assertTrue(
                "Too small number of search results!\nGot " + itemsNumber,
                itemsNumber > 1);

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );

        MainPageObject.waitForElementNotPresent(
                By.id("org.wikipedia:id/page_list_item_container"),
                "Search results are still presented on the page",
                5
        );
    }

    @Test
    public void testCheckSearchResultsText() {
        String query = "Ford";

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find Search Wikipedia input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/search_src_text"),
                query,
                "Cannot find search input",
                5
        );

        // get titles of search results
        List<WebElement> listItems = MainPageObject.waitForElementsPresent(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot find search results",
                15
        );

        // check that all results contain search query
        assertTrue(
                "Some search results titles don't contain expected query \"" + query + "\"!",
                listItems.stream().allMatch(p -> p.getAttribute("text").contains(query))
        );
    }

    @Test
    public void testTwoArticlesBehavior() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5
        );

        String search_input_locator = "//*[contains(@text,'Search…')]";
        MainPageObject.waitForElementAndSendKeys(
                By.xpath(search_input_locator),
                "Mazda",
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Automotive brand manufacturer']"),
                "Cannot find appropriate search result",
                25
        );

        String more_options_locator = "//android.widget.ImageView[@content-desc='More options']";
        MainPageObject.waitForElementAndClick(
                By.xpath(more_options_locator),
                "Cannot find button to open article options",
                15
        );

        String add_to_list_locator = "//*[@text='Add to reading list']";
        MainPageObject.waitForElementAndClick(
                By.xpath(add_to_list_locator),
                "Cannot find option to add article to reading list",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                5
        );

        String articles_folder_name_input_locator = "org.wikipedia:id/text_input";
        MainPageObject.waitForElementAndClear(
                By.id(articles_folder_name_input_locator),
                "Cannot find input to set name for articles folder",
                5
        );

        String name_of_folder = "Vehicle Brands";

        MainPageObject.waitForElementAndSendKeys(
                By.id(articles_folder_name_input_locator),
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press OK button",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/menu_page_search"),
                "Cannot find 'Search' icon",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath(search_input_locator),
                "Ford",
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Automotive brand manufacturer']"),
                "Cannot find appropriate search result",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath(more_options_locator),
                "Cannot find button to open article options",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath(add_to_list_locator),
                "Cannot find option to add article to reading list",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find previously created reading list",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to My lists",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='"+ name_of_folder + "']"),
                "Cannot find created folder",
                5
        );

        String article_title_ford_locator = "//*[@text='Ford Motor Company']";
        MainPageObject.swipeElementToLeft(
                By.xpath(article_title_ford_locator),
                "Cannot find saved article"
        );

        MainPageObject.waitForElementNotPresent(
                By.xpath(article_title_ford_locator),
                "Cannot delete saved article",
                5
        );

        String article_title_mazda_locator = "//*[@text='Mazda']";
        MainPageObject.waitForElementPresent(
                By.xpath(article_title_mazda_locator),
                "Cannot find the second article 'Mazda'",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath(article_title_mazda_locator),
                "Cannot find the second article 'Mazda'",
                5
        );

        WebElement element = MainPageObject.waitForElementPresent(
                By.xpath(article_title_mazda_locator),
                "Cannot find the second article 'Mazda'",
                5
        );

        String article_title = element.getAttribute("text");

        assertEquals(
                "Wrong title of the article!",
                "Mazda",
                article_title
        );
    }

    @Test
    public void testCheckArticleTitlePresence() {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find Search Wikipedia input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Mazda",
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Automotive brand manufacturer']"),
                "Cannot find appropriate search result",
                15
        );

        // check the title presence. It really fails without the delay
        MainPageObject.assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title!"
        );
    }*/
}
