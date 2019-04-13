package lib.ui;

import org.openqa.selenium.WebElement;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class ArticlePageObject extends MainPageObject {
    protected static String
        TITLE,
        FOOTER_ELEMENT,
        OPTIONS_BUTTON,
        OPTIONS_ADD_TO_MY_LIST_BUTTON,
        OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
        ADD_TO_MY_LIST_OVERLAY,
        MY_LIST_NAME_INPUT,
        MY_LIST_OK_BUTTON,
        CLOSE_ARTICLE_BUTTON,
        SEARCH_BUTTON,
        SAVED_LIST_LOCATOR_TPL,
        CLOSE_SYNC_POPUP_BUTTON,
        ARTICLE_ID_TPL,
        ARTICLE_CONTENTS;

    public ArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    /* TEMPLATES METHODS */
    private static String getSavedListElement(String substring) {
        return SAVED_LIST_LOCATOR_TPL.replace("{LIST_NAME}", substring);
    }

    private static String getArticleId(String substring) {
        return ARTICLE_ID_TPL.replace("{ARTICLE_ID}", substring);
    }

    private static String getBodyContent(String substring) {
        return ARTICLE_CONTENTS.replace("{CONTENT}", substring);
    }
    /* TEMPLATES METHODS */

    public WebElement waitForTitleElement() {
        return this.waitForElementPresent(TITLE, "Cannot find article title on page", 15);
    }

    public WebElement waitForArticleById(String id) {
        String article_id = getArticleId(id);
        return this.waitForElementPresent(article_id, "Cannot find article title on page", 15);
    }

    public WebElement waiForArticleByBodyContent(String substring) {
        String body_content = getBodyContent(substring);
        return this.waitForElementPresent(body_content, "Cannot find article contents on page", 15);
    }

    public String getArticleTitle() {
        WebElement title_element = waitForTitleElement();
        if (Platform.getInstance().isAndroid()) {
            return title_element.getAttribute("text");
        } else if (Platform.getInstance().isIOS()) {
            return title_element.getAttribute("name");
        } else {
            return title_element.getText();
        }
    }

    public void swipeToFooter() {
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40);
        } else if (Platform.getInstance().isIOS()) {
            // 40 swipes were not enough for iOS
            this.swipeUpTillElementAppear(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    150);
        } else {
            this.scrollWebPageTillElementNotVisible(
                    FOOTER_ELEMENT,
                    "Cannot find the end of article",
                    40);
        }
    }

    public void addArticleToMyList(String name_of_folder) {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                5
        );

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5
        );

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find ''Got it' tip overlay",
                5
        );

        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Cannot find input to set name for articles folder",
                5
        );

        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot put text into articles folder input",
                5
        );

        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot press OK button",
                5
        );
    }

    public void addArticleToSavedList(String name_of_folder) {
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                15
        );

        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5
        );

        String saved_list_xpath = getSavedListElement(name_of_folder);
        this.waitForElementAndClick(
                saved_list_xpath,
                "Cannot find previously created reading list",
                5
        );
    }

    public void addArticleToMySaved() {
        if (Platform.getInstance().isMW()) {
            this.removeArticleFromSavedIfItAdded();
        }
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5);
    }

    public void removeArticleFromSavedIfItAdded() {
        if (this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON)) {
            this.waitForElementAndClick(
                    OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
                    "Cannot click button to remove an article from saved",
                    5
            );
            this.waitForElementPresent(
                    OPTIONS_ADD_TO_MY_LIST_BUTTON,
                    "Cannot find button to add an article to saved list after removing it from this list before",
                    5);
        }
    }

    public void closeArticle() {
        if ((Platform.getInstance().isIOS() || Platform.getInstance().isAndroid())) {
            this.waitForElementAndClick(
                    CLOSE_ARTICLE_BUTTON,
                    "Cannot close article, cannot find X link",
                    5
            );
        } else {
            System.out.println("Method closeArticle() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public void closeSyncPopup() {
        this.waitForElementAndClick(
                CLOSE_SYNC_POPUP_BUTTON,
                "Cannot close sync pop-up, cannot find X link",
                5
        );
    }

    public void clickOnSearchButton() {
        this.waitForElementAndClick(
                SEARCH_BUTTON,
                "Cannot close article, cannot find X link",
                5
        );
    }

    public void checkArticleTitlePresence() {
        assertElementPresent(
                TITLE,
                "Cannot find article title!"
        );
    }
}
