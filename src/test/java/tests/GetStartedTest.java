package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.WelcomePageObject;
import org.junit.Test;

public class GetStartedTest extends CoreTestCase {

    @Test
    @Feature(value="Welcome Screens")
    @DisplayName("Pass through welcome screens")
    @Description("We go through welcome screens (only for ios app)")
    @Step("Starting test testPassThroughWelcome")
    @Severity(value = SeverityLevel.BLOCKER)
    public void testPassThroughWelcome(){

        if ((Platform.getInstance().isAndroid()) || (Platform.getInstance().isMW())) {
            return;
        }
        WelcomePageObject WelcomePage = new WelcomePageObject(driver);

        WelcomePage.waitForLearnMoreLink();
        WelcomePage.clickNextButton();

        WelcomePage.waitForNewWayToExploreText();
        WelcomePage.clickNextButton();

        WelcomePage.waitForAddOrEditPreferredLangLink();
        WelcomePage.clickNextButton();

        WelcomePage.waitForLearnMoreAboutDataLink();
        WelcomePage.clickGetStartedButton();
    }
}
