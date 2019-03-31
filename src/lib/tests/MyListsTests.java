package lib.tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {
    private static final String name_of_folder = "Learning programming";

    @Test
    public void testSaveFirstArticleToMyList() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(name_of_folder);
        } else {
            ArticlePageObject.addArticleToMySaved();
            // we need to close 'Sync my saved articles' pop-up here
            ArticlePageObject.closeSyncPopup();
        }

        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            MyListsPageObject.openFolderByName(name_of_folder);
        }

        MyListsPageObject.swipeByArticleToDelete(article_title);
    }

    @Test
    public void testTwoArticlesInListBehavior() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Mazda");
        SearchPageObject.clickByArticleWithSubstring("Automotive brand manufacturer");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();

        String name_of_folder = "Vehicle Brands";
        ArticlePageObject.addArticleToMyList(name_of_folder);
        ArticlePageObject.clickOnSearchButton();

        SearchPageObject.typeSearchLine("Ford");
        SearchPageObject.clickByArticleWithSubstring("Automotive brand manufacturer");
        ArticlePageObject.addArticleToSavedList(name_of_folder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();

        MyListsPageObject MyListPageObject = MyListsPageObjectFactory.get(driver);
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
