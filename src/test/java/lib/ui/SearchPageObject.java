package lib.ui;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class SearchPageObject extends MainPageObject{

    protected static String
        SEARCH_INIT_ELEMENT,
        SEARCH_INPUT,
        SEARCH_CANCEL_BUTTON,
        SEARCH_RESULTS,
        SEARCH_RESULT_BY_SUBSTRING_TPL,
        SEARCH_RESULT_ELEMENT,
        SEARCH_EMPTY_RESULT_ELEMENT,
        SEARCH_RESULT_BY_TWO_SUBSTRINGS_TPL;

    public SearchPageObject(RemoteWebDriver driver){
        super(driver);
    }

    /* TEMPLATE METHODS */
    private static String getResultSearchElement(String substring){
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getSearchElementByTitleAndDescription(String title, String description){
        return SEARCH_RESULT_BY_TWO_SUBSTRINGS_TPL.replace("{TITLE}", title).replace("{DESCRIPTION}", description);
    }
    /* TEMPLATE METHODS */

    @Step("Initializing the search field")
    public void initSearchInput(){
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click init element", 5);
        this.waitForElementPresent(SEARCH_INPUT, "Cannot find search input after clicking search init element");
    }

    @Step("Check if search input contains text 'Search…'")
    public void assertElementHasText(){
        String expected_text = "Search…";
        String error_message = "Cannot find search input with text 'Search…'";
        WebElement text_element = waitForElementPresent(SEARCH_INPUT, error_message, 5);
        String actual_text = text_element.getAttribute("text");

        Assert.assertEquals(
                error_message,
                expected_text,
                actual_text
        );
    }

    @Step("Waiting for button to cancel search results")
    public void waitForCancelButtonAppear(){
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button", 5);
    }

    @Step("Waiting for search cancel button to disappear")
    public void waitForCancelButtonDisappear(){
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button is still present", 5);
    }

    @Step("Clicking button to cancel search results")
    public void clickCancelSearch(){
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button", 5);
    }

    @Step("Typing '{search_line}' to the search line")
    public void typeSearchLine(String search_line){
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Cannot find and type into search input", 5);
    }

    @Step("Waiting for search results and select an article by substring in article title")
    public void waitForSearchResult(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(search_result_xpath, "Cannot find search result with substring " + substring);
    }

    @Step("Waiting for search results appearing")
    public void waitForSearchResultsAppear(){
        this.waitForElementPresent(SEARCH_RESULTS, "Cannot find search results", 15);
    }

    @Step("Waiting for search results disappearing")
    public void waitForSearchResultsDisappear(){
        this.waitForElementNotPresent(SEARCH_RESULTS, "Still find search results", 15);
    }

    @Step("Click by article with substring in article title")
    public void clickByArticleWithSubstring(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath, "Cannot find and click search result with substring " + substring, 10);
    }

    @Step("Comparing number of all search results with number of results contains some text")
    public void compareSearchResults(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.assertAllSearchResultsContainSomeText(
                SEARCH_RESULTS,
                search_result_xpath,
                "Number of results with text '" + substring + "' doesn't equals number of all search results"
        );
    }

    @Step("Getting amount of found articles")
    public int getAmountOfFoundArticles(){
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request",
                15
        );
        return  this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    @Step("Waiting for empty results label")
    public void waitForEmptyResultLabel(){
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT, "Cannot find empty result element", 15);
    }

    @Step("Making sure there are no search results")
    public void assertThereIsNoResultOfSearch(){
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT, "We supposed not to find any results");
    }

    @Step("Waiting for search results and select an article by substrings in article title and description")
    public void waitForArticleWithTitleAndDescription(String title, String description) {
        String searched_article = getSearchElementByTitleAndDescription(title, description);
        this.waitForElementPresent(searched_article, "Cannot find article with title: " + title + " and description: " + description, 10);
    }
}
