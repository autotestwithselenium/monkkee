package tests;

import io.qameta.allure.Description;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.DiaryPage;
import lombok.extern.log4j.Log4j2;
import utils.MyRetryAnalyzer;

@Log4j2
public class DiaryEntryActivitiesTest extends BaseTest {

    @Test(description = "1 - add entry with value", retryAnalyzer = MyRetryAnalyzer.class, dataProvider = "getDataForEntry")
    @Description("Add entry with value")
    public void addTextEntryTest(String entryValue) {
        DiaryPage diaryPage = new DiaryPage(driver);
        diaryPage
                .clickAddEntry()
                .addEntry(entryValue)
                .clickBackToEntriesIcon()
                .clickEntry(1)
                .verifyAddedValueInEntry(entryValue)
                .clickBackToEntriesIcon();
    }

    @DataProvider
    public Object[][] getDataForEntry() {
        return new Object[][]{{"Test message"},
                {"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin elementum ex nulla, id eleifend elit lobortis sed. Nullam maximus felis eget ullamcorper pretium. Aenean cursus, tortor vel pellentesque dapibus, arcu nunc malesuada purus, eget accumsan purus mauris eu leo. Nullam a risus sagittis, consequat nulla at, tristique purus. Mauris quis imperdiet ex. Donec sed condimentum nibh. Sed molestie viverra rutrum. Nunc dictum ac lectus at dictum. Pellentesque ullamcorper ipsum quis tincidunt pretium. Donec mattis tortor id elit tristique, eget sodales erat tempor. Aliquam erat volutpat. Pellentesque pellentesque dapibus varius. Ut urna sem, volutpat vitae eros sed, hendrerit accumsan sapien. Fusce pharetra accumsan tellus, in cursus metus lobortis ac. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos.Nam eu urna vel enim sodales aliquam. Aliquam ultricies eget sapien sit amet faucibus. Nunc in ornare nisl, nec auctor nibh. Nunc fringilla arcu elei"},
                {"<html><body><p>test</p></body></html>"},
                {"<note><to>Jane</to><from>Tom</from><heading>Reminder</heading><body>Hello!</body></note>"}};
    }

    @Test(description = "2 - add empty entry", retryAnalyzer = MyRetryAnalyzer.class)
    @Description("Add text entry")
    public void addEmptyEntryTest() {
        String textMessage = "Test message";
        DiaryPage diaryPage = new DiaryPage(driver);
        diaryPage
                .clickAddEntry()
                .addEntry(textMessage)
                .clearEntry()
                .clickBackToEntriesIcon()
                .verifyEntryMessage("No content", 1);
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
