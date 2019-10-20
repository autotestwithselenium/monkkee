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

    @Test(retryAnalyzer = MyRetryAnalyzer.class)
    public void addEntryWithImage() {
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

    @Test(retryAnalyzer = MyRetryAnalyzer.class)
    public void editEntry() {
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
    public void printEntry() {
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

    @Test(retryAnalyzer = MyRetryAnalyzer.class)
    public void addTag() {
        String textMessage = "Test message";
        String tagName = "testTag";
        DiaryPage diaryPage = new DiaryPage(driver);
        EntryPage entryPage = new EntryPage(driver);
        diaryPage.clickAddEntry();
        entryPage.addEntry(textMessage)
                .addNewTag(tagName)
                .clickBackToEntriesIcon();
        diaryPage.verifyTagInEntry(tagName);
    }


    @Test
    public void openOlderEntry() {
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
    public void openNewerEntry() {
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
    public void changeDateAndTimeInEntry() {
        String textMessage = "Test message";
        String dayValue ="1";
        String timeValue ="01:01 AM";
        String expectedDateAndTime ="Tue, 1 Oct. 2019 01:01 AM";
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
    public void openDonationPage() {
        DiaryPage diaryPage = new DiaryPage(driver);
        diaryPage.clickDonationButton();
    }

}
