package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.NavigationUI;
import lib.ui.android.AndroidNavigatioUI;
import lib.ui.ios.iOSNavigationUI;

public class NavigationUIFactory {
    public static NavigationUI get(AppiumDriver driver) {
        if (Platform.getInstance().isAndroid()) {
            return new AndroidNavigatioUI(driver);
        } else {
            return new iOSNavigationUI(driver);
        }
    }
}
