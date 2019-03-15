package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListsPageObject extends MainPageObject {
    public static final String
        FOLDER_BY_NAME_TPL = "//*[@text='{FOLDER_NAME}']",
        ARTICLE_BY_TITLE_TPL = "//*[@text='{TITLE}']";

    private static String getFolderXpathByName(String name_of_folder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleXpathByTitle(String article_title) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }

    public MyListsPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void openFolderByName(String name_of_folder) {
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementAndClick(
                By.xpath(folder_name_xpath),
                "Cannot find created folder by name " + name_of_folder,
                5
        );
    }

    public void waitForArticleToAppearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementPresent(
                By.xpath(article_xpath),
                "Cannot find saved article by title " + article_title,
                15
        );
    }

    public void waitForArticleToDisappearByTitle(String article_title) {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementNotPresent(
                By.xpath(article_xpath),
                "Saved article still present with title " + article_title,
                15
        );
    }

    public void swipeByArticleToDelete(String article_title) {
        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.swipeElementToLeft(
                By.xpath(article_xpath),
                "Cannot find saved article"
        );
        this.waitForArticleToDisappearByTitle(article_title);
    }

    public void openSavedArticle(String article_title) {
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementAndClick(
                By.xpath(article_xpath),
                "Cannot open saved article by title " + article_title,
                5
        );
    }
}
