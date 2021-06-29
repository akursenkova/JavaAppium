import io.qameta.allure.*;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;


public class HomeWork extends CoreTestCase {

    private static final String
            login = "akursenkova",
            password = "";

    //Ex 2
    @Test
    @Feature(value="Search")
    @DisplayName("Check if search input is present")
    @Description("If search input contains some text it present on the screen")
    @Step("Starting test testSearchInputContainsText")
    @Severity(value = SeverityLevel.NORMAL)
    public void testSearchInputContainsText(){

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.assertElementHasText();
    }


    //Ex 3
    @Test
    @Feature(value="Search")
    @DisplayName("Clear search input")
    @Description("Type text in the search input and make sure text is disappear after cleaning input")
    @Step("Starting test testClearSearchInput")
    @Severity(value = SeverityLevel.NORMAL)
    public void testClearSearchInput(){

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResultsAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForSearchResultsDisappear();
    }


    //Ex 4
    @Test
    @Feature(value="Search")
    @DisplayName("Every search result contains searched text")
    @Step("Starting test testSearchResultsContainText")
    @Severity(value = SeverityLevel.NORMAL)
    public void testSearchResultsContainText(){

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String searched_text = "Java";
        SearchPageObject.typeSearchLine(searched_text);
        SearchPageObject.waitForSearchResult(searched_text);
        SearchPageObject.compareSearchResults(searched_text);
    }


    //Ex 5. Added adaptation for iOS and mobile web
    @Test
    @Features(value = {@Feature(value="Search"),@Feature(value="Article"),@Feature(value="My Lists")})
    @DisplayName("Save two articles to my list")
    @Description("Save two articles to the list and then delete one of them from list")
    @Step("Starting test testSaveArticlesToList")
    @Severity(value = SeverityLevel.MINOR)
    public void testSaveArticlesToList() throws InterruptedException {

        String first_article = "Object-oriented programming language";
        String first_article_title = "Java (programming language)";
        String second_article = "Island of Indonesia";
        String second_article_ios = "Indonesian island";
        String second_article_title = "Java";
        String name_of_folder = "Learning programming";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring(first_article);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();

        if (Platform.getInstance().isAndroid()){
            ArticlePageObject.addArticleToMyList(name_of_folder);
            ArticlePageObject.closeArticle();
        } else if (Platform.getInstance().isIOS()){
            ArticlePageObject.addArticleToMySaved();
            ArticlePageObject.closeArticle();
            SearchPageObject.clickCancelSearch();
        } else {
            ArticlePageObject.addArticleToMySaved();
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();

            ArticlePageObject.waitForTitleElement();

            Assert.assertEquals("We are not on the same page after login",
                    first_article_title,
                    ArticlePageObject.getArticleTitle()
            );
        }

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");

        if (Platform.getInstance().isAndroid()){
            SearchPageObject.clickByArticleWithSubstring(second_article);
        } else {
            SearchPageObject.clickByArticleWithSubstring(second_article_ios);
        }

        if (Platform.getInstance().isAndroid()){
            ArticlePageObject.waitForTitleElement();
            ArticlePageObject.addArticleToExistingList(name_of_folder);
            ArticlePageObject.closeArticle();
        } else if (Platform.getInstance().isIOS()){
            ArticlePageObject.addArticleToMySaved();
            ArticlePageObject.closeArticle();
            SearchPageObject.clickCancelSearch();
        } else {
            ArticlePageObject.addArticleToMySaved();
        }

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.openNavigation();
        NavigationUI.clickMyList();

        MyListsPageObject MyListsPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isIOS()){
            MyListsPageObject.closeAlertSyncArticles();
        }

        if (Platform.getInstance().isAndroid()){
            MyListsPageObject.openFolderByName(name_of_folder);
        }

        MyListsPageObject.swipeByArticleToDelete(first_article_title);

        ArticlePageObject.takeScreenshot("saved_articles");

        if (Platform.getInstance().isAndroid()){
            MyListsPageObject.clickArticleAndCompareTitle(second_article_title);
        } else {
            MyListsPageObject.waitForArticleToAppear(second_article_title);
        }

    }


    //Ex 6
    @Test //this test will be failed, because article title doesn't have time to load
    public void testAssertTitle(){

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.assertTitlePresent();
    }

}