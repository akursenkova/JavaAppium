package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationUI extends MainPageObject{

    protected static String
            MY_LISTS_LINK,
            OPEN_NAVIGATION;


    public NavigationUI(RemoteWebDriver driver){
        super(driver);
    }

    @Step("Open my lists screen")
    public void clickMyList(){
        if (Platform.getInstance().isMW()){
            this.tryClickElementWithFewAttempts(
                    MY_LISTS_LINK,
                    "Cannot find navigation button to My Lists",
                    5
            );
        } else {

            this.waitForElementPresent(
                    MY_LISTS_LINK,
                    "Cannot find navigation button to My Lists",
                    5
            );

            this.waitForElementAndClick(
                    MY_LISTS_LINK,
                    "Cannot find navigation button to My list",
                    5
            );
        }
    }

    @Step("Open navigation menu")
    public void openNavigation(){
        if (Platform.getInstance().isMW()){
            this.waitForElementAndClick(OPEN_NAVIGATION, "Cannot find and click open navigation button", 5);
        } else {
            System.out.println("Method openNavigation() do nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

}
