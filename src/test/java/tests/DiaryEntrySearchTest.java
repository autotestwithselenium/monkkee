package tests;

import io.qameta.allure.Description;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.DiaryPage;
import lombok.extern.log4j.Log4j2;
import utils.MyRetryAnalyzer;

@Log4j2
public class DiaryEntrySearchTest extends BaseTest {

    @Test(description = "12 - search entries by text", retryAnalyzer = MyRetryAnalyzer.class, dataProvider = "getTextForSearchAndExpectedNumberOfEntries")
    @Description("Search entries, with entered text in search form")
    public void searchEntryByTextTest(String textForSearch, int numberOfEntriesFoundBySearch) {
        int numberOfEntries;
        String textMessageFirst = "First Test message";
        String textMessageSecond = "Second Test message";
        DiaryPage diaryPage = new DiaryPage(driver);
        numberOfEntries = diaryPage.getNumberOfEntries();
        if (numberOfEntries > 0) {
            diaryPage.deleteAllEntries();
        }
        diaryPage
                .clickAddEntry()
                .addEntry(textMessageFirst)
                .clickBackToEntriesIcon()
                .verifyEntryMessage(textMessageFirst, 1)
                .clickAddEntry()
                .addEntry(textMessageSecond)
                .clickBackToEntriesIcon()
                .verifyEntryMessage(textMessageSecond, 1)
                .searchEntryByText(textForSearch, numberOfEntriesFoundBySearch);
    }

    @DataProvider
    public Object[][] getTextForSearchAndExpectedNumberOfEntries() {
        return new Object[][]{{"Second", 1}, {"Test", 2}, {"Library", 0}};
    }


    @Test(description = "13 - search entry by tags", retryAnalyzer = MyRetryAnalyzer.class)
    @Description("Search entries by chosen tag")
    public void searchEntryByTagsTest() {
        int numberOfEntries;
        String textMessage = "First Test message";
        String tagNameFirst = "Poem";
        String tagNameSecond = "Article";
        DiaryPage diaryPage = new DiaryPage(driver);

        numberOfEntries = diaryPage.getNumberOfEntries();
        if (numberOfEntries > 0) {
            diaryPage.deleteAllEntries();
        }
        diaryPage
                .openTagsList()
                .deleteTag(tagNameFirst)
                .clickBackToEntriesIcon()
                .clickAddEntry()
                .addEntry(textMessage)
                .addNewTag(tagNameFirst)
                .clickBackToEntriesIcon()
                .verifyTagInEntry(tagNameFirst)
                .openTagsList()
                .deleteTag(tagNameSecond)
                .clickBackToEntriesIcon()
                .clickAddEntry()
                .addEntry(textMessage)
                .addNewTag(tagNameSecond)
                .clickBackToEntriesIcon()
                .verifyTagInEntry(tagNameSecond)
                .clickAddEntry()
                .addEntry(textMessage)
                .chooseExistingTagFromList(tagNameSecond)
                .clickBackToEntriesIcon()
                .verifyTagInEntry(tagNameSecond)
                .searchEntryByTag(tagNameFirst, 1)
                .searchEntryByTag(tagNameSecond, 2);
    }


    @Test(description = "14 - reset filter of search results", retryAnalyzer = MyRetryAnalyzer.class)
    @Description("Reset filter of search results")
    public void resetSearchEntryByTextTest() {
        int numberOfEntries;
        String textMessageFirst = "First Test message";
        String textMessageSecond = "Second Test message";
        String searchTextOneEntry = "Second";
        DiaryPage diaryPage = new DiaryPage(driver);
        numberOfEntries = diaryPage.getNumberOfEntries();
        if (numberOfEntries > 0) {
            diaryPage.deleteAllEntries();
        }
        diaryPage
                .clickAddEntry()
                .addEntry(textMessageFirst)
                .clickBackToEntriesIcon()
                .verifyEntryMessage(textMessageFirst, 1)
                .clickAddEntry()
                .addEntry(textMessageSecond)
                .clickBackToEntriesIcon()
                .verifyEntryMessage(textMessageSecond, 1)
                .searchEntryByText(searchTextOneEntry, 1)
                .resetSearchResults(2);
    }

    @Test(description = "15 - choose entries by calendar day", retryAnalyzer = MyRetryAnalyzer.class)
    @Description("Choose entries by calendar day")    public void searchEntryByDateTest() {
        int numberOfEntries;
        String textMessage = "Test message";
        String dayValueFirst = "1";
        String dayValueSecond = "23";
        DiaryPage diaryPage = new DiaryPage(driver);
        numberOfEntries = diaryPage.getNumberOfEntries();
        if (numberOfEntries > 0) {
            diaryPage.deleteAllEntries();
        }
        diaryPage
                .clickAddEntry()
                .addEntry(textMessage)
                .changeDateInEntry(dayValueFirst)
                .clickBackToEntriesIcon()
                .verifyEntryMessage(textMessage, 1)
                .clickAddEntry()
                .addEntry(textMessage)
                .changeDateInEntry(dayValueSecond)
                .clickBackToEntriesIcon()
                .verifyEntryMessage(textMessage, 1)
                .clickAddEntry()
                .addEntry(textMessage)
                .changeDateInEntry(dayValueSecond)
                .clickBackToEntriesIcon()
                .verifyEntryMessage(textMessage, 1)
                .searchEntryByDate(dayValueFirst, 1)
                .resetSearchResults(3)
                .searchEntryByDate(dayValueSecond, 2)
                .resetSearchResults(3);
    }
}
