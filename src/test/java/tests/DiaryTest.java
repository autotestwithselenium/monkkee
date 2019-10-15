package tests;

import org.testng.annotations.Test;
import pages.DiaryPage;
import lombok.extern.log4j.Log4j2;
import pages.EntryPage;


@Log4j2
public class DiaryTest extends BaseTest {

    @Test(retryAnalyzer = MyRetryAnalyzer.class)
    public void addTextEntry() {
        String textMessage = "Test message";
        DiaryPage diaryPage = new DiaryPage(driver);
        EntryPage entryPage = new EntryPage(driver);
        diaryPage.clickAddEntry();
        entryPage
                .addEntry(textMessage)
                .clickBackToEntriesIcon();
        diaryPage.verifyEntryMessage(textMessage, 1);
    }

    @Test()
    public void addEntryWithImage() {
        String imageUrl="https://content.onliner.by/news/1100x5616/cd4ab5fe98649030080244f3c81857c3.jpeg";
        DiaryPage diaryPage = new DiaryPage(driver);
        EntryPage entryPage = new EntryPage(driver);
        diaryPage.clickAddEntry();
        entryPage
                .addImage(imageUrl)
                .clickBackToEntriesIcon();
    }

    @Test(retryAnalyzer = MyRetryAnalyzer.class)
    public void editEntry() {
        String textMessage = "Test message";
        String editedTextMessage = "Edited Test message";
        new DiaryPage(driver)
                // .addEntry(textMessage)
                //   .clickBackToEntriesIcon()
                .verifyEntryMessage(textMessage, 1)
                .editEntry(editedTextMessage, 1)
                //    .clickBackToEntriesIcon()
                .verifyEntryMessage(editedTextMessage, 1);
    }

    @Test(retryAnalyzer = MyRetryAnalyzer.class)
    public void deleteEntry() {
        String textMessage = "Test message";
        DiaryPage diaryPage = new DiaryPage(driver);
        EntryPage entryPage = new EntryPage(driver);
        diaryPage.clickAddEntry();
        entryPage
                .addEntry(textMessage)
                .clickBackToEntriesIcon();
        diaryPage
                .verifyEntryMessage(textMessage, 1)
                .deleteEntry(1);

    }

    @Test(retryAnalyzer = MyRetryAnalyzer.class)
    public void deleteAllEntries() {
        String textMessage = "Test message";
        DiaryPage page = new DiaryPage(driver);
        page
                // .addEntry(textMessage)
                //  .clickBackToEntriesIcon()
                .verifyEntryMessage(textMessage, 1)
                //  .addEntry(textMessage)
                // .clickBackToEntriesIcon()
                .verifyEntryMessage(textMessage, 1)
                .deleteAllEntries();

    }
}
