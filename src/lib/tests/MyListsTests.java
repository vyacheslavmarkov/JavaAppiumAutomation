package lib.tests;

import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {
    @Test
    public void testSaveFirstArticleToMyList() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();
        String name_of_folder = "Learning programming";
        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListPageObject = new MyListsPageObject(driver);
        MyListPageObject.openFolderByName(name_of_folder);
        MyListPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    public void testTwoArticlesInListBehavior() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Mazda");
        SearchPageObject.clickByArticleWithSubstring("Automotive brand manufacturer");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();

        String name_of_folder = "Vehicle Brands";
        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.clickOnSearchButton();

        SearchPageObject.typeSearchLine("Ford");
        SearchPageObject.clickByArticleWithSubstring("Automotive brand manufacturer");
        ArticlePageObject.addArticleToSavedList(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListPageObject = new MyListsPageObject(driver);
        MyListPageObject.openFolderByName(name_of_folder);
        String article_title_ford = "Ford Motor Company";
        MyListPageObject.swipeByArticleToDelete(article_title_ford);

        String article_title_mazda = "Mazda";
        MyListPageObject.waitForArticleToAppearByTitle(article_title_mazda);
        MyListPageObject.openSavedArticle(article_title_mazda);

        String article_title = ArticlePageObject.getArticleTitle();

        assertEquals(
                "Wrong title of the article!",
                "Mazda",
                article_title
        );
    }
}
