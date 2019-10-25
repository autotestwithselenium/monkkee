package tests;

import org.testng.annotations.Test;
import pages.DiaryPage;
import lombok.extern.log4j.Log4j2;
import pages.EntryPage;
import pages.TagsPage;

@Log4j2
public class DiaryEntrySearchTest extends BaseTest {

    @Test(retryAnalyzer = MyRetryAnalyzer.class)
    public void searchEntryByTextTest() {
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

    @Test(retryAnalyzer = MyRetryAnalyzer.class)
    public void searchEntryByTagsTest() {
        int numberOfEntries;
        String textMessage = "First Test message";
        String tagNameFirst = "Poem";
        String tagNameSecond = "Article";
        DiaryPage diaryPage = new DiaryPage(driver);
        EntryPage entryPage = new EntryPage(driver);
        TagsPage tagsPage = new TagsPage(driver);

        numberOfEntries = diaryPage.getNumberOfEntries();
        if (numberOfEntries > 0) {
            diaryPage.deleteAllEntries();
        }
        diaryPage.openTagsList();
        boolean tagExistsInList = tagsPage.checkTagPresenseInList(tagNameFirst);
        if (tagExistsInList) {
            log.info("Tag exists in Tags list!");
            tagsPage.deleteTag(tagNameFirst);
        }
        tagsPage.clickBackToEntriesIcon();
        diaryPage.clickAddEntry();
        entryPage.addEntry(textMessage)
                .addNewTag(tagNameFirst)
                .clickBackToEntriesIcon();
        diaryPage.verifyTagInEntry(tagNameFirst);
        diaryPage.openTagsList();
        tagExistsInList = tagsPage.checkTagPresenseInList(tagNameSecond);
        if (tagExistsInList) {
            log.info("Tag exists in Tags list!");
            tagsPage.deleteTag(tagNameSecond);
        }
        tagsPage.clickBackToEntriesIcon();
        diaryPage.clickAddEntry();
        entryPage.addEntry(textMessage)
                .addNewTag(tagNameSecond)
                .clickBackToEntriesIcon();
        diaryPage.verifyTagInEntry(tagNameSecond);
        diaryPage.clickAddEntry();
        entryPage.addEntry(textMessage)
                .chooseExistingTagFromList(tagNameSecond)
                .clickBackToEntriesIcon();
        diaryPage.verifyTagInEntry(tagNameSecond);
        diaryPage.searchEntryByTag(tagNameFirst, 1);
        diaryPage.searchEntryByTag(tagNameSecond, 2);
    }


    @Test(retryAnalyzer = MyRetryAnalyzer.class)
    public void resetSearchEntryByTextTest() {
        int numberOfEntries;
        String textMessageFirst = "First Test message";
        String textMessageSecond = "Second Test message";
        String searchTextOneEntry = "Second";
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
        diaryPage.resetSearchResults(2);

    }

    @Test
    public void searchEntryByDateTest() {
        int numberOfEntries;
        String textMessage = "Test message";
        String dayValueFirst = "1";
        String dayValueSecond = "23";
        DiaryPage diaryPage = new DiaryPage(driver);
        EntryPage entryPage = new EntryPage(driver);
        numberOfEntries = diaryPage.getNumberOfEntries();
        if (numberOfEntries > 0) {
            diaryPage.deleteAllEntries();
        }
        diaryPage.clickAddEntry();
        entryPage
                .addEntry(textMessage)
                .changeDateInEntry(dayValueFirst)
                .clickBackToEntriesIcon();
        diaryPage
                .verifyEntryMessage(textMessage, 1)
                .clickAddEntry();
        entryPage
                .addEntry(textMessage)
                .changeDateInEntry(dayValueSecond)
                .clickBackToEntriesIcon();
        diaryPage
                .verifyEntryMessage(textMessage, 1)
                .clickAddEntry();
        entryPage
                .addEntry(textMessage)
                .changeDateInEntry(dayValueSecond)
                .clickBackToEntriesIcon();
        diaryPage
                .verifyEntryMessage(textMessage, 1)
                .searchEntryByDate(dayValueFirst, 1)
                .resetSearchResults(3)
                .searchEntryByDate(dayValueSecond, 2)
                .resetSearchResults(3);
    }
}
