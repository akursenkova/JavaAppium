package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase {

    @Test
    @DisplayName("Change screen orientation")
    @Description("We open an article, change screen orientation and make sure article not changed after rotation")
    @Step("Starting test testChangeScreenOrientation")
    @Severity(value = SeverityLevel.NORMAL)
    public void testChangeScreenOrientation() {

        if (Platform.getInstance().isMW()){
            return;
        }

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        String title_before_rotation = ArticlePageObject.getArticleTitle();
        this.rotateScreenLandscape();
        String title_after_rotation = ArticlePageObject.getArticleTitle();

        Assert.assertEquals(
                "Article title has been changed after screen rotation",
                title_before_rotation,
                title_after_rotation
        );

        this.rotateScreenPortrait();
        String title_after_second_rotation = ArticlePageObject.getArticleTitle();

        Assert.assertEquals(
                "Article title has been changed after screen rotation",
                title_before_rotation,
                title_after_second_rotation
        );
    }

    @Test
    @DisplayName("Check opened article after sending app to the background")
    @Description("We search an article, send app to the background and make sure article still present after opening app")
    @Step("Starting test testCheckSearchArticleInBackground")
    @Severity(value = SeverityLevel.NORMAL)
    public void testCheckSearchArticleInBackground() {

        if (Platform.getInstance().isMW()){
            return;
        }

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
        this.backgroundApp(2);
        SearchPageObject.waitForSearchResult("Object-oriented programming language");

    }
}
