package lib.tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchTests extends CoreTestCase {
    @Test
    public void testSearch() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    @Test
    public void testCancelSearch() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        String search_line = "Linkin Park Diskography";
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "We found too few search results!",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        String search_line = "xacsdcsdcds";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testSearchInputPlaceholder() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        WebElement element = SearchPageObject.initSearchInput();

        String searchInputPlaceholder = element.getAttribute("text");
        assertEquals(
                "Search input placeholder doesn't contain expected text!",
                "Search…",
                searchInputPlaceholder
        );
    }

    @Test
    public void testCheckResultsAndCancelSearch() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        String search_line = "Ford";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForSearchResult("Automotive brand manufacturer");

        // check that there are several items (> 1) in the results list
        int itemsNumber = SearchPageObject.getAmountOfFoundArticles();
        assertTrue(
                "Too small number of search results!\nGot " + itemsNumber,
                itemsNumber > 1);

        SearchPageObject.clickCancelSearch();
        // wait while search results disappear
        SearchPageObject.waitForSearchResultsDisappear();
    }

    @Test
    public void testCheckSearchResultsText() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        String search_line = "Ford";
        SearchPageObject.typeSearchLine(search_line);

        // get titles of search results
        List<WebElement> listItems = SearchPageObject.waitForSearchResultsTitles();

        // check that all results contain search query
        assertTrue(
                "Some search results titles don't contain expected query \"" + search_line + "\"!",
                listItems.stream().allMatch(p -> p.getAttribute("text").contains(search_line))
        );
    }
}
