package lib.ui.mobile_web;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "css:button#searchIcon";
        SEARCH_INPUT = "css:form>input[type='search']";
        SEARCH_CANCEL_BUTTON = "css:button.clear";
        SEARCH_RESULTS = "id:org.wikipedia:id/page_list_item_container";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://div[contains(@class, 'wikidata-description')][contains(text(), '{SUBSTRING}')]";
        SEARCH_RESULT_ELEMENT = "css:ui.page-list>li.page-summary";
        SEARCH_EMPTY_RESULT_ELEMENT = "css:p.without-results";
        //SEARCH_EMPTY_RESULT_ELEMENT = "xpath://XCUIElementTypeStaticText[@name='No results found']";
        SEARCH_RESULT_BY_TWO_SUBSTRINGS_TPL = "xpath://div[contains(@class, 'wikidata-description')][contains(text(), '{DESCRIPTION}')]/../div[contains(@class, 'data-title')][contains(text(), '{TITLE}')]";
    }

    public MWSearchPageObject(RemoteWebDriver driver){
        super(driver);
    }
}
