package tests;

import org.testng.annotations.Test;
import pages.DiaryPage;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class DiaryTest extends BaseTest {

    @Test(retryAnalyzer = MyRetryAnalyzer.class)
    public void addTextEntry() {
        String textMessage = "Test message";
        DiaryPage page = new DiaryPage(driver);
        page
                .addEntry(textMessage)
                .clickBackToEntriesIcon()
                .verifyEntryMessage(textMessage, 1);
    }

    @Test
    public void addEntryWithImage() {
      //https://content.onliner.by/news/1100x5616/cd4ab5fe98649030080244f3c81857c3.jpeg
    }

    @Test
    public void editEntry() {
        String textMessage = "Test message";
        String editedTextMessage = "Edited Test message";
        DiaryPage page = new DiaryPage(driver);

        page
                .addEntry(textMessage)
                .clickBackToEntriesIcon()
                .verifyEntryMessage(textMessage, 1)
                .editEntry(editedTextMessage, 1)
                .clickBackToEntriesIcon()
                .verifyEntryMessage(editedTextMessage, 1);
    }

    @Test
    public void deleteEntry() {
        String textMessage = "Test message";
        DiaryPage page = new DiaryPage(driver);
        page
                .addEntry(textMessage)
                .clickBackToEntriesIcon()
                .verifyEntryMessage(textMessage, 1)
                .deleteEntry(1);

    }

    @Test
    public void deleteAllEntries() {
        String textMessage = "Test message";
        DiaryPage page = new DiaryPage(driver);
        page
                .addEntry(textMessage)
                .clickBackToEntriesIcon()
                .verifyEntryMessage(textMessage, 1)
                .addEntry(textMessage)
                .clickBackToEntriesIcon()
                .verifyEntryMessage(textMessage, 1)
                .deleteAllEntries();

    }
}
