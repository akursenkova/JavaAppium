package tests;

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

public class MyListTests extends CoreTestCase {

    private static final String name_of_folder = "Learning programming";
    private static final String
            login = "akursenkova",
            password = "";


    @Test
    @Features(value = {@Feature(value="Search"),@Feature(value="Article"),@Feature(value="My Lists")})
    @DisplayName("Save article to my list")
    @Description("Save article to the list and then delete it from list")
    @Step("Starting test testSaveFirstArticleToList")
    @Severity(value = SeverityLevel.MINOR)
    public void testSaveFirstArticleToList() throws InterruptedException {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String article_title = ArticlePageObject.getArticleTitle();

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
                    article_title,
                    ArticlePageObject.getArticleTitle()
            );
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

        MyListsPageObject.swipeByArticleToDelete(article_title);
    }

}
