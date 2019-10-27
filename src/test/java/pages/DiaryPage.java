package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

import utils.AllureUtils;
import utils.ElementNotPresentAtPageException;

import static org.testng.AssertJUnit.assertEquals;

@Log4j2
public class DiaryPage extends BasePage {

    public DiaryPage(WebDriver driver) {
        super(driver);
    }

    private By addEntryIcon = By.xpath("//*[@id='create-entry']");
    private By entryBodyMessage = By.xpath("//*[@class='entry-container clearfix ng-scope']//*[@ng-bind-html='entry.body']");
    private By entryLink = By.xpath("//a[@class='entry']");
    private By entryCheckboxNotMarked = By.xpath("//*[@class='entry-container clearfix ng-scope']//*[@class='ng-pristine ng-valid']");
    private By entryCheckboxMarked = By.xpath("//*[@class='entry-container clearfix ng-scope']//*[@class='ng-valid ng-dirty']");
    private By chooseAllEntriesCheckbox = By.xpath("//input[@ng-model='model.allChecked']");
    private By deleteIcon = By.id("delete-entries");
    private By noEntriesMessage = By.xpath("//*[@ng-show='noneMsgVisible']");
    private By animationPicture = By.xpath("//img[@class='animation']");
    private By deleteIconDisabled = By.xpath("//a[@class='btn btn-primary disabled' and @id='delete-entries']");
    private By printIcon = By.xpath("//a[@ng-click='printEntries()']");
    private By donationButton = By.xpath("//a[@class='donate-button ng-scope' and text()='Feed the monkkee']");
    private By manageTagsLink = By.xpath("//a[@href='#/tags']");
    private By searchField = By.xpath("//input[@id='appendedInputButton']");
    private By searchFilterLabel = By.xpath("//span[@class='ng-binding search-parameter']");
    private By searchButton = By.xpath("//button[@class='btn btn-primary input-group-addon' and @type='submit']");
    private By resetLink = By.xpath("//a[@id='reset-search']");
    private By changeDateAndTimeLink = By.xpath("//input[@id='datepicker']");
    private By daysInCalendar = By.xpath("//div[@class='datepicker-days datepicker-mode']//table[@class=' table-condensed']//td");
    private By settingsLink = By.xpath("//a[@href='#/settings/locale']");

    @Step("Click add entry")
    public EntryPage clickAddEntry() {
        clickElement(addEntryIcon);
        animationWait(animationPicture);
        return new EntryPage(driver);
    }

    @Step("Get number of entries")
    public int getNumberOfEntries() {
        return driver.findElements(entryBodyMessage).size();
    }

    @Step("Check entered value in entry")
    public DiaryPage verifyEntryMessage(String textMessage, int numberOfEntry) {
        String actualEntryMessage = driver.findElements(entryBodyMessage).get(numberOfEntry - 1).getText();
        assertEquals("Entered message in entry should be equal to message in the opened entry", textMessage, actualEntryMessage);
        return this;
    }

    @Step("Open entry")
    public EntryPage clickEntry(int numberOfEntry) {
        driver.findElements(entryLink).get(numberOfEntry - 1).click();
        animationWait(animationPicture);
        return new EntryPage(driver);
    }

    @Step("Delete entry")
    public DiaryPage deleteEntry(int numberOfEntry) {
        int numberOfEntries = getNumberOfEntries();
        driver.findElements(entryCheckboxNotMarked).get(numberOfEntry - 1).click();
        waitElementToBeClickable(deleteIcon);
        clickElement(deleteIcon);
        Alert alert = driver.switchTo().alert();
        alert.accept();
        waitPresenceOfElementLocated(deleteIconDisabled);
        int numberOfEntriesAfterDelete = getNumberOfEntries();
        assertEquals("Number of entries should be decreased by 1 after deletion of 1 entry", numberOfEntries - 1, numberOfEntriesAfterDelete);
        return this;
    }

    @Step("Delete all entries")
    public DiaryPage deleteAllEntries() {
        driver.findElement(chooseAllEntriesCheckbox).click();
        waitElementToBeClickable(deleteIcon);
        clickElement(deleteIcon);
        Alert alert = driver.switchTo().alert();
        alert.accept();
        waitPresenceOfElementLocated(deleteIconDisabled);
        int numberOfEntriesAfterDelete = getNumberOfEntries();
        assertEquals("After deletion of all entries number of entries should be 0", 0, numberOfEntriesAfterDelete);
        assertEquals("After deletion of all entries text 'no entries found' should be displayed", driver.findElement(noEntriesMessage).getText(), "No entries found");
        AllureUtils.takeScreenshot(driver);
        return this;
    }

    @Step("Check, if tag exists in entry")
    public DiaryPage verifyTagInEntry(String tagNameValue) {
        try {
            driver.findElement(By.xpath(String.format("//*[@class='entry-container clearfix ng-scope']//span[contains(text(), %s)]", tagNameValue)));
        } catch (Throwable ex) {
            log.error("Tag is not added in entry! Xpath of tag: " + String.format("//*[@class='entry-container clearfix ng-scope']//span[contains(text(), %s)]", tagNameValue));
            throw new ElementNotPresentAtPageException("Tag is not added for entry", ex);
        }
        return this;
    }

    @Step("Print entry")
    public DiaryPage printEntry(int numberOfEntry) {
        String expectedUrl = "https://my.monkkee.com/print_template";
        String winHandleBefore = driver.getWindowHandle();
        driver.findElements(entryCheckboxNotMarked).get(numberOfEntry - 1).click();
        waitElementToBeClickable(printIcon);
        clickElement(printIcon);
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        log.info("Current URL in print Page: " + driver.getCurrentUrl());
        assertEquals("After press on icon Print page with expected URL should be opened", expectedUrl, driver.getCurrentUrl());
        driver.close();
        driver.switchTo().window(winHandleBefore);
        driver.findElements(entryCheckboxMarked).get(numberOfEntry - 1).click();
        return this;
    }

    @Step("Click donation button")
    public DiaryPage clickDonationButton() {
        String expectedUrl = "https://www.monkkee.com/en/support-us-with-a-donation/";
        driver.findElement(donationButton).click();
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        log.info("Current URL in donation Page: " + driver.getCurrentUrl());
        assertEquals("After press on donation button page with expected URL should be opened", expectedUrl, driver.getCurrentUrl());
        driver.close();
        driver.switchTo().window(tabs.get(0));
        return this;
    }

    @Step("Open list of tags")
    public TagsPage openTagsList() {
        clickElement(manageTagsLink);
        animationWait(animationPicture);
        return new TagsPage(driver);
    }

    @Step("Search entries by value entered in Search field")
    public DiaryPage searchEntryByText(String textForSearch, int expectedNumberOfEntries) {
        clickElement(searchField);
        setValueInField(searchField, textForSearch);
        clickElement(searchButton);
        waitTextToBe(searchFilterLabel, textForSearch);
        assertEquals("Search should return expected number of entries", getNumberOfEntries(), expectedNumberOfEntries);
        AllureUtils.takeScreenshot(driver);
        return this;
    }

    @Step("Search entries by tag")
    public DiaryPage searchEntryByTag(String tagForSearch, int expectedNumberOfEntries) {
        clickElement(By.xpath(String.format("//a[contains(text(), '%s')]", tagForSearch)));
        waitTextToBe(searchFilterLabel, tagForSearch);
        assertEquals("Search should return expected number of entries", getNumberOfEntries(), expectedNumberOfEntries);
        AllureUtils.takeScreenshot(driver);
        return this;
    }

    @Step("Search entries chosen day in calendar")
    public DiaryPage searchEntryByDate(String dayValue, int expectedNumberOfEntries) {
        clickElement(changeDateAndTimeLink);
        List<WebElement> allDates = driver.findElements(daysInCalendar);
        for (WebElement ele : allDates) {
            String date = ele.getText();
            if (date.equalsIgnoreCase(dayValue)) {
                ele.click();
                break;
            }
        }
        animationWait(animationPicture);
        waitPresenceOfElementLocated(resetLink);
        assertEquals("Search should return expected number of entries", getNumberOfEntries(), expectedNumberOfEntries);
        AllureUtils.takeScreenshot(driver);
        return this;
    }

    @Step("Reset search results")
    public DiaryPage resetSearchResults(int expectedNumberOfEntriesAfterReset) {
        clickElement(resetLink);
        waitInvisibilityOfElementLocated(resetLink);
        assertEquals("After reset all entries should be displayed in diary page", getNumberOfEntries(), expectedNumberOfEntriesAfterReset);
        AllureUtils.takeScreenshot(driver);
        return this;
    }

    @Step("Open settings page")
    public SettingsPage openSettings() {
        clickElement(settingsLink);
        animationWait(animationPicture);
        return new SettingsPage(driver);
    }
}