package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class iOSArticlePageObject extends ArticlePageObject {
    static {
        ARTICLE_ID_TPL = "id:{ARTICLE_ID}";
        TITLE = "id:Java (programming language)";
        FOOTER_ELEMENT = "id:View article in browser";
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "id:Save for later";
        CLOSE_ARTICLE_BUTTON = "id:Back";
        CLOSE_SYNC_POPUP_BUTTON = "id:places auth close";
        SEARCH_BUTTON = "id:Search Wikipedia";
    }

    public iOSArticlePageObject(AppiumDriver driver) {
        super(driver);
    }
}
