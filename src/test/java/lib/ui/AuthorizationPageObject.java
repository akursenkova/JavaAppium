package lib.ui;

import io.qameta.allure.Step;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AuthorizationPageObject extends MainPageObject{

    private static final String
            LOGIN_BUTTON = "xpath://body/div/div/a[text()='Log in']",
            LOGIN_INPUT = "css:input[name='wpName']",
            PASSWORD_INPUT = "css:input[name='wpPassword']",
            SUBMIT_BUTTON = "css:button#wpLoginAttempt";

    public AuthorizationPageObject(RemoteWebDriver driver){
        super(driver);
    }

    @Step("Click 'Log in' button")
    public void clickAuthButton() throws InterruptedException{
        this.waitForElementPresent(LOGIN_BUTTON, "Cannot find auth button", 10);
        Thread.sleep(1000);
        this.waitForElementAndClick(LOGIN_BUTTON, "Cannot find and click auth button", 5);
    }

    @Step("Enter login and password to the inputs")
    public void enterLoginData(String login, String password){
        this.waitForElementPresent(LOGIN_INPUT,"Cannot find login to the input", 5);
        this.waitForElementAndSendKeys(LOGIN_INPUT, login,"Cannot find and put login to the input", 5);
        this.waitForElementAndSendKeys(PASSWORD_INPUT, password, "Cannot find and put password to the input", 5);
    }

    @Step("Submitting authorization form")
    public void submitForm(){
        this.waitForElementAndClick(SUBMIT_BUTTON, "Cannot find and click submit auth button", 5);
    }
}
