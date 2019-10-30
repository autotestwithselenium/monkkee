package tests;

import io.qameta.allure.Description;
import org.testng.annotations.Test;
import pages.DiaryPage;
import lombok.extern.log4j.Log4j2;
import pages.TagsPage;
import utils.MyRetryAnalyzer;

@Log4j2
public class DiaryEntryTagsTest extends BaseTest {

    @Test(description = "9 - add new tag in entry", retryAnalyzer = MyRetryAnalyzer.class)
    @Description("Add new tag in new entry (new tag can be created, when new entry is created)")
    public void addNewTagInEntryTest() {
        String textMessage = "Test message";
        String tagName = "testTag";
        DiaryPage diaryPage = new DiaryPage(driver);
        diaryPage
                .openTagsList()
                .deleteTag(tagName)
                .clickBackToEntriesIcon()
                .clickAddEntry()
                .addEntry(textMessage)
                .addNewTag(tagName)
                .clickBackToEntriesIcon()
                .verifyTagInEntry(tagName);
    }

    @Test(description = "10 - choose existing tag for entry", retryAnalyzer = MyRetryAnalyzer.class)
    @Description("Choose existing tag, when new entry is created")
    public void chooseExistingTagInEntryTest() {
        String textMessage = "Test message";
        String tagName = "testTag1";
        DiaryPage diaryPage = new DiaryPage(driver);
        TagsPage tagsPage = new TagsPage(driver);
        diaryPage
                .openTagsList()
                .clickBackToEntriesIcon();
        if (tagsPage.checkTagPresenseInList(tagName) == false) { //adding new entry with new tag
            diaryPage
                    .clickAddEntry()
                    .addEntry(textMessage)
                    .addNewTag(tagName)
                    .clickBackToEntriesIcon()
                    .verifyTagInEntry(tagName);
        }
        diaryPage   //add new entry, choose existing tag
                .clickAddEntry()
                .addEntry(textMessage)
                .chooseExistingTagFromList(tagName)
                .clickBackToEntriesIcon()
                .verifyTagInEntry(tagName);
    }

    @Test(description = "11 - remove tag in entry", retryAnalyzer = MyRetryAnalyzer.class)
    @Description("Remove added earlier tag from entry")
    public void removeTagFromEntryTest() {
        String textMessage = "Test message";
        String tagName = "testTag";
        DiaryPage diaryPage = new DiaryPage(driver);
        diaryPage
                .openTagsList()
                .deleteTag(tagName)
                .clickBackToEntriesIcon()
                .clickAddEntry()
                .addEntry(textMessage)
                .addNewTag(tagName)
                .clickBackToEntriesIcon()
                .verifyTagInEntry(tagName)
                .clickEntry(1)
                .removeTagFromEntry(tagName)
                .clickBackToEntriesIcon();
    }
}
