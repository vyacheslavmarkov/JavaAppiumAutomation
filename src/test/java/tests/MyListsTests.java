package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;
import org.openqa.selenium.WebElement;

public class MyListsTests extends CoreTestCase {
    private static final String name_of_folder = "Learning programming";
    private static final String
            login = "Vmarkov1991",
            password = "wiki_testing";

    @Test
    public void testSaveFirstArticleToMyList() {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(name_of_folder);
        } else {
            ArticlePageObject.addArticleToMySaved();

            if (Platform.getInstance().isIOS()) {
                // we need to close 'Sync my saved articles' pop-up here (iOS)
                ArticlePageObject.closeSyncPopup();
            }
        }
        if (Platform.getInstance().isMW()) {
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();

            ArticlePageObject.waitForTitleElement();

            assertEquals("We are not on the same page after login",
                    article_title,
                    ArticlePageObject.getArticleTitle());

            // article adds to the watchlist automatically after login
            ArticlePageObject.addArticleToMySaved();
        }

        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.openNavigation();
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
        SearchPageObject.clickByArticleWithSubstring("utomotive brand manufacturer");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);

        if ((Platform.getInstance().isAndroid()) || Platform.getInstance().isMW()) {
            ArticlePageObject.waitForTitleElement();
        } else {
            ArticlePageObject.waitForArticleById("Mazda");
        }

        String name_of_folder = "Vehicle Brands";

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToMyList(name_of_folder);
        } else {
            ArticlePageObject.addArticleToMySaved();

            if (Platform.getInstance().isIOS()) {
                // we need to close 'Sync my saved articles' pop-up here (iOS)
                ArticlePageObject.closeSyncPopup();
            }
        }

        String article_title;
        if (Platform.getInstance().isMW()) {
            // save the article title first
            article_title = ArticlePageObject.getArticleTitle();
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();

            ArticlePageObject.waitForTitleElement();

            assertEquals("We are not on the same page after login",
                    article_title,
                    ArticlePageObject.getArticleTitle());

            // article adds to the watchlist automatically after login
            ArticlePageObject.addArticleToMySaved();
        }

        ArticlePageObject.clickOnSearchButton();
        if (Platform.getInstance().isIOS()) {
            SearchPageObject.clearSearchInput();
        }

        SearchPageObject.typeSearchLine("Ford");
        // subtitle is really different in mobile apps and in web
        if (Platform.getInstance().isMW()) {
            SearchPageObject.clickByArticleWithSubstring("merican automobile manufacturer");
        } else {
            SearchPageObject.clickByArticleWithSubstring("Automotive brand manufacturer");
        }
        if ((Platform.getInstance().isAndroid() || Platform.getInstance().isMW())) {
            ArticlePageObject.waitForTitleElement();
        } else if (Platform.getInstance().isIOS()) {
            ArticlePageObject.waitForArticleById("Ford Motor Company");
        }

        if (Platform.getInstance().isAndroid()) {
            ArticlePageObject.addArticleToSavedList(name_of_folder);
        } else {
            ArticlePageObject.addArticleToMySaved();
        }

        ArticlePageObject.closeArticle();
        if (Platform.getInstance().isIOS()) {
            // need to close first article too
            ArticlePageObject.closeArticle();
        }

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.openNavigation();
        NavigationUI.clickMyLists();

        MyListsPageObject MyListPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {
            MyListPageObject.openFolderByName(name_of_folder);
        }
        String article_title_ford = "Ford Motor Company";
        MyListPageObject.swipeByArticleToDelete(article_title_ford);

        String article_title_mazda = "Mazda";
        if ((Platform.getInstance().isAndroid() || Platform.getInstance().isMW())) {
            MyListPageObject.waitForArticleToAppearByTitle(article_title_mazda);
        } else {
            MyListPageObject.waitForArticleToAppearByLabel("Mazda");
        }

        MyListPageObject.openSavedArticle(article_title_mazda);

        if (Platform.getInstance().isAndroid()) {
            article_title = ArticlePageObject.getArticleTitle();
            assertEquals(
                    "Wrong title of the article!",
                    "Mazda",
                    article_title
            );
        } else if (Platform.getInstance().isIOS()) {
            // for iOS it is checked that article has appropriate accessibility_id
            ArticlePageObject.waitForArticleById("Mazda");
        } else {
            // for MW it is checked that article content contains appropriate char sequence
            ArticlePageObject.waiForArticleByBodyContent("Mazda Motor Corporation");
        }
    }
}
