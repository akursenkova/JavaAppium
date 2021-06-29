package tests;

import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

public class SearchTests extends CoreTestCase {

    @Test
    @Feature(value="Search")
    @DisplayName("Search an article")
    @Step("Starting test testSearch")
    @Severity(value = SeverityLevel.CRITICAL)
    public void testSearch() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("bject-oriented programming language");

    }

    @Test
    @Feature(value="Search")
    @DisplayName("Cancel search results")
    @Step("Starting test testCancelSearch")
    @Severity(value = SeverityLevel.MINOR)
    public void testCancelSearch() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        if (Platform.getInstance().isMW()){
            SearchPageObject.typeSearchLine("Java");
        }
        SearchPageObject.waitForCancelButtonAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonDisappear();

    }

    @Test
    @Feature(value="Search")
    @DisplayName("Get amount of not empty search results")
    @Description("Get amount of search results and compare if it more than 0")
    @Step("Starting test testAmountOfNotEmptySearch")
    @Severity(value = SeverityLevel.TRIVIAL)
    public void testAmountOfNotEmptySearch() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "Linkin Park Discography";
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();
        Assert.assertTrue(
                "We found too few results",
                amount_of_search_results > 0
        );
    }

    @Test
    @Feature(value="Search")
    @DisplayName("Check if search result is empty")
    @Step("Starting test testAmountOfEmptySearch")
    @Severity(value = SeverityLevel.TRIVIAL)
    public void testAmountOfEmptySearch() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search_line = "hjhjkhfdhz";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    @Feature(value="Search")
    @DisplayName("Find article by title and description")
    @Step("Starting test testFindArticleByTitleAndDescription")
    @Severity(value = SeverityLevel.TRIVIAL)
    public void testFindArticleByTitleAndDescription(){

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForArticleWithTitleAndDescription("Java", "Island of Indonesia");
    }
}
