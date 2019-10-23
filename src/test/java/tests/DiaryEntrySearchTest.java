package tests;

import org.testng.annotations.Test;
import pages.DiaryPage;
import lombok.extern.log4j.Log4j2;
import pages.EntryPage;
import pages.TagsPage;

public class DiaryEntrySearchTest extends BaseTest {

    @Test(retryAnalyzer = MyRetryAnalyzer.class)
    public void searchEntryByText() {
        int numberOfEntries;
        String textMessageFirst = "First Test message";
        String textMessageSecond = "Second Test message";
        String searchTextOneEntry = "Second";
        String searchTextSeveralEntries = "Test";
        String searchTextNoEntries = "Library";
        DiaryPage diaryPage = new DiaryPage(driver);
        EntryPage entryPage = new EntryPage(driver);
        numberOfEntries = diaryPage.getNumberOfEntries();
        if (numberOfEntries > 0) {
            diaryPage.deleteAllEntries();
        }
        diaryPage.clickAddEntry();
        entryPage
                .addEntry(textMessageFirst)
                .clickBackToEntriesIcon();
        diaryPage.verifyEntryMessage(textMessageFirst, 1);
        diaryPage.clickAddEntry();
        entryPage
                .addEntry(textMessageSecond)
                .clickBackToEntriesIcon();
        diaryPage.verifyEntryMessage(textMessageSecond, 1);
        diaryPage.searchEntryByText(searchTextOneEntry, 1);
        diaryPage.searchEntryByText(searchTextSeveralEntries, 2);
        diaryPage.searchEntryByText(searchTextNoEntries, 0);

    }

}
