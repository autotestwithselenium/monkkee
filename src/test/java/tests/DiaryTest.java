package tests;

import org.testng.annotations.Test;
import pages.DiaryPage;
import lombok.extern.log4j.Log4j2;
import pages.EntryPage;
import pages.TagsPage;

import static com.sun.javafx.fxml.expression.Expression.not;


@Log4j2
public class DiaryTest extends BaseTest {

    @Test(retryAnalyzer = MyRetryAnalyzer.class)
    public void addTextEntryTest() {
        String textMessage = "Test message";
        DiaryPage diaryPage = new DiaryPage(driver);
        EntryPage entryPage = new EntryPage(driver);
        diaryPage.clickAddEntry();
        entryPage
                .addEntry(textMessage)
                .clickBackToEntriesIcon();
        diaryPage.verifyEntryMessage(textMessage, 1);
    }

    @Test(retryAnalyzer = MyRetryAnalyzer.class)
    public void addEntryWithImageTest() {
        String imageUrl = "https://content.onliner.by/news/1100x5616/cd4ab5fe98649030080244f3c81857c3.jpeg";
        DiaryPage diaryPage = new DiaryPage(driver);
        EntryPage entryPage = new EntryPage(driver);
        diaryPage.clickAddEntry();
        entryPage
                .addImage(imageUrl)
                .clickBackToEntriesIcon();
        diaryPage.clickEntry(1);
        entryPage
                .verifyEntryWithAddedImage(imageUrl)
                .clickBackToEntriesIcon();
    }

    @Test(retryAnalyzer = MyRetryAnalyzer.class, groups = {"dataDrivenTest"})
    public void editEntryTest() {
        String textMessage = "Test message";
        String editedTextMessage = "Edited Test message";
        DiaryPage diaryPage = new DiaryPage(driver);
        EntryPage entryPage = new EntryPage(driver);
        diaryPage.clickAddEntry();
        entryPage
                .addEntry(textMessage)
                .clickBackToEntriesIcon();
        diaryPage
                .verifyEntryMessage(textMessage, 1)
                .clickEntry(1);
        entryPage
                .editEntry(editedTextMessage)
                .clickBackToEntriesIcon();
        diaryPage
                .verifyEntryMessage(editedTextMessage, 1);
    }

    @Test(retryAnalyzer = MyRetryAnalyzer.class)
    public void printEntryTest() {
        String textMessage = "Test message";
        DiaryPage diaryPage = new DiaryPage(driver);
        EntryPage entryPage = new EntryPage(driver);
        diaryPage.clickAddEntry();
        entryPage
                .addEntry(textMessage)
                .clickBackToEntriesIcon();
        diaryPage
                .verifyEntryMessage(textMessage, 1)
                .printEntry(1);
    }


    @Test(retryAnalyzer = MyRetryAnalyzer.class)
    public void deleteEntryTest() {
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
    public void deleteAllEntriesTest() {
        String textMessage = "Test message";
        DiaryPage diaryPage = new DiaryPage(driver);
        EntryPage entryPage = new EntryPage(driver);
        diaryPage.clickAddEntry();
        entryPage
                .addEntry(textMessage)
                .clickBackToEntriesIcon();
        diaryPage
                .verifyEntryMessage(textMessage, 1)
                .clickAddEntry();
        entryPage
                .addEntry(textMessage)
                .clickBackToEntriesIcon();
        diaryPage
                .verifyEntryMessage(textMessage, 1)
                .deleteAllEntries();
    }


    @Test
    public void openOlderEntryTest() {
        String textMessageFirst = "Test message First";
        String textMessageSecond = "Test message Second";
        DiaryPage diaryPage = new DiaryPage(driver);
        EntryPage entryPage = new EntryPage(driver);
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
        diaryPage.clickEntry(1);
        entryPage
                .openOlderEntry(textMessageFirst)
                .clickBackToEntriesIcon();
    }

    @Test
    public void openNewerEntryTest() {
        String textMessageFirst = "Test message First";
        String textMessageSecond = "Test message Second";
        DiaryPage diaryPage = new DiaryPage(driver);
        EntryPage entryPage = new EntryPage(driver);
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
        diaryPage.clickEntry(2);
        entryPage
                .openNewerEntry(textMessageSecond)
                .clickBackToEntriesIcon();
    }

    @Test
    public void changeDateAndTimeInEntryTest() {
        String textMessage = "Test message";
        String dayValue = "1";
        String timeValue = "01:01 AM";
        String expectedDateAndTime = "Tue, 1 Oct. 2019 01:01 AM";
        DiaryPage diaryPage = new DiaryPage(driver);
        EntryPage entryPage = new EntryPage(driver);
        diaryPage.clickAddEntry();
        entryPage
                .addEntry(textMessage)
                .clickBackToEntriesIcon();
        diaryPage
                .verifyEntryMessage(textMessage, 1)
                .clickEntry(1);
        entryPage
                .changeDateAndTimeInEntry(dayValue, timeValue, expectedDateAndTime)
                .clickBackToEntriesIcon();
    }


    @Test(retryAnalyzer = MyRetryAnalyzer.class)
    public void openDonationPageTest() {
        DiaryPage diaryPage = new DiaryPage(driver);
        diaryPage.clickDonationButton();
    }

}
