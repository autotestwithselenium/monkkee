package tests;

import io.qameta.allure.Description;
import org.testng.annotations.Test;
import pages.DiaryPage;
import lombok.extern.log4j.Log4j2;
import utils.MyRetryAnalyzer;

@Log4j2
public class DiaryEntryActivitiesTest extends BaseTest {

    @Test(description = "2 - add text entry", retryAnalyzer = MyRetryAnalyzer.class)
    @Description("Add text entry")
    public void addTextEntryTest() {
        String textMessage = "Test message";
        DiaryPage diaryPage = new DiaryPage(driver);
        diaryPage
                .clickAddEntry()
                .addEntry(textMessage)
                .clickBackToEntriesIcon().verifyEntryMessage(textMessage, 1);
    }

    @Test(description = "3 - add entry with image", retryAnalyzer = MyRetryAnalyzer.class)
    @Description("Add entry with image")
    public void addEntryWithImageTest() {
        String imageUrl = "https://content.onliner.by/news/1100x5616/cd4ab5fe98649030080244f3c81857c3.jpeg";
        DiaryPage diaryPage = new DiaryPage(driver);
        diaryPage
                .clickAddEntry()
                .addImage(imageUrl)
                .clickBackToEntriesIcon()
                .clickEntry(1)
                .verifyEntryWithAddedImage(imageUrl)
                .clickBackToEntriesIcon();
    }

    @Test(description = "4 - edit entry", retryAnalyzer = MyRetryAnalyzer.class, groups = {"dataDrivenTest"})
    @Description("Edit entry")
    public void editEntryTest() {
        String textMessage = "Test message";
        String editedTextMessage = "Edited Test message";
        DiaryPage diaryPage = new DiaryPage(driver);
        diaryPage
                .clickAddEntry()
                .addEntry(textMessage)
                .clickBackToEntriesIcon()
                .verifyEntryMessage(textMessage, 1)
                .clickEntry(1)
                .editEntry(editedTextMessage)
                .clickBackToEntriesIcon()
                .verifyEntryMessage(editedTextMessage, 1);
    }

    @Test(description = "5 - change date/time in entry", retryAnalyzer = MyRetryAnalyzer.class, groups = {"dataDrivenTest"})
    @Description("Change date/time in entry")
    public void changeDateAndTimeInEntryTest() {
        String textMessage = "Test message";
        String dayValue = "1";
        String timeValue = "01:01 AM";
        String expectedDateAndTime = "Tue, 1 Oct. 2019 01:01 AM";
        DiaryPage diaryPage = new DiaryPage(driver);
        diaryPage
                .clickAddEntry()
                .addEntry(textMessage)
                .clickBackToEntriesIcon()
                .verifyEntryMessage(textMessage, 1)
                .clickEntry(1)
                .changeDateAndTimeInEntry(dayValue, timeValue, expectedDateAndTime)
                .clickBackToEntriesIcon();
    }

    @Test(description = "6 - print entry", retryAnalyzer = MyRetryAnalyzer.class)
    @Description("Print entry")
    public void printEntryTest() {
        String textMessage = "Test message";
        DiaryPage diaryPage = new DiaryPage(driver);
        diaryPage
                .clickAddEntry()
                .addEntry(textMessage)
                .clickBackToEntriesIcon()
                .verifyEntryMessage(textMessage, 1)
                .printEntry(1);
    }

    @Test(description = "7 - delete entry", retryAnalyzer = MyRetryAnalyzer.class)
    @Description("Delete entry")
    public void deleteEntryTest() {
        String textMessage = "Test message";
        DiaryPage diaryPage = new DiaryPage(driver);
        diaryPage
                .clickAddEntry()
                .addEntry(textMessage)
                .clickBackToEntriesIcon()
                .verifyEntryMessage(textMessage, 1)
                .deleteEntry(1);
    }

    @Test(description = "8 - delete all entries", retryAnalyzer = MyRetryAnalyzer.class)
    @Description("Delete all entries")
    public void deleteAllEntriesTest() {
        String textMessage = "Test message";
        DiaryPage diaryPage = new DiaryPage(driver);
        diaryPage
                .clickAddEntry()
                .addEntry(textMessage)
                .clickBackToEntriesIcon()
                .verifyEntryMessage(textMessage, 1)
                .clickAddEntry()
                .addEntry(textMessage)
                .clickBackToEntriesIcon()
                .verifyEntryMessage(textMessage, 1)
                .deleteAllEntries();
    }
}
