package tests;

import org.testng.annotations.Test;
import pages.DiaryPage;
import lombok.extern.log4j.Log4j2;
import pages.EntryPage;
import pages.TagsPage;

@Log4j2
public class DiaryEntryTagsTest extends BaseTest {

    @Test(retryAnalyzer = MyRetryAnalyzer.class)
    public void addNewTagInEntryTest() {
        String textMessage = "Test message";
        String tagName = "testTag";
        DiaryPage diaryPage = new DiaryPage(driver);
        EntryPage entryPage = new EntryPage(driver);
        TagsPage tagsPage = new TagsPage(driver);
        diaryPage.openTagsList();
        boolean tagExistsInList = tagsPage.checkTagPresenseInList(tagName);
        if (tagExistsInList) {
            log.info("Tag exists in Tags list!");
            tagsPage.deleteTag(tagName);
        }
        tagsPage.clickBackToEntriesIcon();
        diaryPage.clickAddEntry();
        entryPage.addEntry(textMessage)
                .addNewTag(tagName)
                .clickBackToEntriesIcon();
        diaryPage.verifyTagInEntry(tagName);
    }

    @Test(retryAnalyzer = MyRetryAnalyzer.class)
    public void chooseExistingTagInEntryTest() {
        String textMessage = "Test message";
        String tagName = "testTag1";
        DiaryPage diaryPage = new DiaryPage(driver);
        EntryPage entryPage = new EntryPage(driver);
        TagsPage tagsPage = new TagsPage(driver);
        diaryPage.openTagsList();
        boolean tagExistsInList = tagsPage.checkTagPresenseInList(tagName);
        tagsPage.clickBackToEntriesIcon();
        if (tagExistsInList == false) { //adding new entry with new tag
            diaryPage.clickAddEntry();
            entryPage.addEntry(textMessage)
                    .addNewTag(tagName)
                    .clickBackToEntriesIcon();
            diaryPage.verifyTagInEntry(tagName);
        }
        //add new entry, choose existing tag
        diaryPage.clickAddEntry();
        entryPage.addEntry(textMessage)
                .chooseExistingTagFromList(tagName)
                .clickBackToEntriesIcon();
        diaryPage.verifyTagInEntry(tagName);
    }

    @Test(retryAnalyzer = MyRetryAnalyzer.class)
    public void removeTagFromEntryTest() {
        String textMessage = "Test message";
        String tagName = "testTag";
        DiaryPage diaryPage = new DiaryPage(driver);
        EntryPage entryPage = new EntryPage(driver);
        TagsPage tagsPage = new TagsPage(driver);
        diaryPage.openTagsList();
        boolean tagExistsInList = tagsPage.checkTagPresenseInList(tagName);
        if (tagExistsInList) {
            log.info("Tag exists in Tags list!");
            tagsPage.deleteTag(tagName);
        }
        tagsPage.clickBackToEntriesIcon();
        diaryPage.clickAddEntry();
        entryPage.addEntry(textMessage)
                .addNewTag(tagName)
                .clickBackToEntriesIcon();
        diaryPage.verifyTagInEntry(tagName);
        diaryPage.clickEntry(1);
        entryPage.removeTagFromEntry(tagName)
                .clickBackToEntriesIcon();
    }

    @Test
    public void manageTagsTest() {
        DiaryPage diaryPage = new DiaryPage(driver);
        diaryPage.openTagsList();
        TagsPage tagsPage = new TagsPage(driver);
        tagsPage.checkTagPresenseInList("testTag");
    }
}
