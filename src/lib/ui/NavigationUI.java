package lib.ui;

import io.appium.java_client.AppiumDriver;

public abstract class NavigationUI extends MainPageObject {
    protected static String MY_LISTS_LINK;

    public NavigationUI(AppiumDriver driver) {
        super(driver);
    }

    public void clickMyLists() {
        this.waitForElementAndClick(
                MY_LISTS_LINK,
                "Cannot find navigation button to My lists",
                5
        );
    }
}
