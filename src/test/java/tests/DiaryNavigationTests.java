package tests;

import io.qameta.allure.Description;
import org.testng.annotations.Test;
import pages.DiaryPage;
import utils.MyRetryAnalyzer;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DiaryNavigationTests extends BaseTest {
    @Test(description = "16 - open older entry", retryAnalyzer = MyRetryAnalyzer.class)
    @Description("Open older entry from opened entry")
    public void openOlderEntryTest() {
        String textMessageFirst = "Test message First";
        String textMessageSecond = "Test message Second";
        DiaryPage diaryPage = new DiaryPage(driver);
        diaryPage
                .clickAddEntry()
                .addEntry(textMessageFirst)
                .clickBackToEntriesIcon()
                .verifyEntryMessage(textMessageFirst, 1)
                .clickAddEntry()
                .addEntry(textMessageSecond)
                .clickBackToEntriesIcon()
                .verifyEntryMessage(textMessageSecond, 1)
                .clickEntry(1)
                .openOlderEntry(textMessageFirst)
                .clickBackToEntriesIcon();
    }

    @Test(description = "17 - open newer entry", retryAnalyzer = MyRetryAnalyzer.class)
    @Description("Open newer entry from opened entry")
    public void openNewerEntryTest() {
        String textMessageFirst = "Test message First";
        String textMessageSecond = "Test message Second";
        DiaryPage diaryPage = new DiaryPage(driver);
        diaryPage
                .clickAddEntry()
                .addEntry(textMessageFirst)
                .clickBackToEntriesIcon()
                .verifyEntryMessage(textMessageFirst, 1)
                .clickAddEntry()
                .addEntry(textMessageSecond)
                .clickBackToEntriesIcon()
                .verifyEntryMessage(textMessageSecond, 1)
                .clickEntry(2)
                .openNewerEntry(textMessageSecond)
                .clickBackToEntriesIcon();
    }

    @Test(description = "18 - open donations page", retryAnalyzer = MyRetryAnalyzer.class)
    @Description("open donations page 'Feed the monkkey'")
    public void openDonationPageTest() {
        DiaryPage diaryPage = new DiaryPage(driver);
        diaryPage.clickDonationButton();
    }
}
