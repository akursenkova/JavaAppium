package lib.ui;

import io.qameta.allure.Step;
import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class MyListsPageObject extends MainPageObject{

    protected static String
            FOLDER_BY_NAME_TPL,
            ARTICLE_BY_TITLE_TPL,
            ARTICLE_TITLE,
            CLOSE_ALERT_BUTTON,
            DELETE_ARTICLE_BUTTON,
            REMOVE_FROM_SAVED_BUTTON;

    private static String getFolderXpathByName(String name_of_folder){
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleXpathByTitle(String article_title){
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }

    private static String getRemoveButtonByTitle(String article_title){
        return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}", article_title);
    }

    public MyListsPageObject(RemoteWebDriver driver){
        super(driver);
    }

    @Step("Open folder by name: {name_of_folder}")
    public void openFolderByName(String name_of_folder){
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementPresent(
                folder_name_xpath,
                "Cannot find folder by name " + name_of_folder,
                5
        );

        this.waitForElementAndClick(
                folder_name_xpath,
                "Cannot click folder " + name_of_folder,
                5
        );
    }

    @Step("Close alert in screen with my saved articles")
    public void closeAlertSyncArticles(){
        if (Platform.getInstance().isIOS()){
            this.waitForElementAndClick(CLOSE_ALERT_BUTTON, "Cannot find and click close alert button", 5);
        }
    }

    @Step("Delete article from the list of saved articles")
    public void swipeByArticleToDelete(String article_title){
        this.waitForArticleToAppear(article_title);
        String article_xpath = getSavedArticleXpathByTitle(article_title);

        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()){
            this.swipeElementToLeft(
                    article_xpath,
                    "Cannot find saved article"
            );
        } else {
            String remove_locator = getRemoveButtonByTitle(article_title);
            this.waitForElementAndClick(
                    remove_locator,
                    "Cannot click button to remove article from saved",
                    10
            );
        }

        if (Platform.getInstance().isIOS()){
            this.waitForElementAndClick(DELETE_ARTICLE_BUTTON, "Cannot find and click delete button", 5);
        }

        if (Platform.getInstance().isMW()){
            driver.navigate().refresh();
        }

        this.waitForArticleToDisappear(article_title);
    }

    @Step("Waiting for article to disappear from saved articles list")
    public void waitForArticleToDisappear(String article_title){
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementNotPresent(
                article_xpath,
                "Saved article still present with title " + article_title,
                15
        );
    }

    @Step("Waiting for article to appear in saved articles list")
    public void waitForArticleToAppear(String article_title){
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementPresent(
                article_xpath,
                "Cannot find saved article by title " + article_title,
                15
        );
    }

    @Step("Find article by title and click it")
    public void clickArticleByTitle(String article_title){
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        waitForElementPresent(
                article_xpath,
                "Cannot find article " + article_title,
                5
        );

        waitForElementAndClick(
                article_xpath,
                "Cannot find article " + article_title,
                5
        );

    }

    @Step("Compare if article title equals expected title")
    public void compareArticleTitle(String article_title){

        WebElement title_element = waitForElementPresent(
                ARTICLE_TITLE,
                "Cannot find article title",
                15
        );

        String actual_title_name = title_element.getAttribute("text");

        Assert.assertEquals(
                "Title of opened article doesn't equals " + article_title,
                actual_title_name,
                article_title
        );
    }

    @Step("Open article '{article_title} and compare it's title with expected one'")
    public void clickArticleAndCompareTitle(String article_title){
        clickArticleByTitle(article_title);
        compareArticleTitle(article_title);
    }
}
