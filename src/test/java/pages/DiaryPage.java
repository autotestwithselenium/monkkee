package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

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
    private By manageTagsLink = By.xpath("//a[text() = 'Manage tags']");
    private By searchField = By.xpath("//input[@placeholder='Search']");
    private By searchFilterLabel = By.xpath("//span[@class='ng-binding search-parameter']");
    private By searchButton = By.xpath("//button[@title='Search']");
    private By resetLink = By.xpath("//a[text()='reset']");
    private By changeDateAndTimeLink = By.xpath("//input[@placeholder='Select date']");
    private By daysInCalendar = By.xpath("//div[@class='datepicker-days datepicker-mode']//table[@class=' table-condensed']//td");
    private By settingsLink = By.xpath("//a[@href='#/settings/locale']");


    public EntryPage clickAddEntry() {
        clickElement(addEntryIcon);
        animationWait(animationPicture);
        return new EntryPage(driver);
    }

    public int getNumberOfEntries() {
        return driver.findElements(entryBodyMessage).size();
    }

    public DiaryPage verifyEntryMessage(String textMessage, int numberOfEntry) {
        String actualEntryMessage = driver.findElements(entryBodyMessage).get(numberOfEntry - 1).getText();
        assertEquals(textMessage, actualEntryMessage);
        return this;
    }

    public EntryPage clickEntry(int numberOfEntry) {
        driver.findElements(entryLink).get(numberOfEntry - 1).click();
        animationWait(animationPicture);
        return new EntryPage(driver);
    }

    public DiaryPage deleteEntry(int numberOfEntry) {
        int numberOfEntries = getNumberOfEntries();
        driver.findElements(entryCheckboxNotMarked).get(numberOfEntry - 1).click();
        waitElementToBeClickable(deleteIcon);
        clickElement(deleteIcon);
        Alert alert = driver.switchTo().alert();
        alert.accept();
        waitPresenceOfElementLocated(deleteIconDisabled);
        int numberOfEntriesAfterDelete = getNumberOfEntries();
        assertEquals(numberOfEntries - 1, numberOfEntriesAfterDelete);
        return this;
    }

    public DiaryPage deleteAllEntries() {
        driver.findElement(chooseAllEntriesCheckbox).click();
        waitElementToBeClickable(deleteIcon);
        clickElement(deleteIcon);
        Alert alert = driver.switchTo().alert();
        alert.accept();
        waitPresenceOfElementLocated(deleteIconDisabled);
        int numberOfEntriesAfterDelete = getNumberOfEntries();
        assertEquals(0, numberOfEntriesAfterDelete);
        assertEquals(driver.findElement(noEntriesMessage).getText(), "No entries found");
        return this;
    }

    public DiaryPage verifyTagInEntry(String tagNameValue) {
        try {
            driver.findElement(By.xpath(String.format("//*[@class='entry-container clearfix ng-scope']//span[contains(text(), %s)]", tagNameValue)));
        } catch (Throwable ex) {
            throw new ElementNotPresentAtPageException("Tag is not added for entry", ex);
        }
        return this;
    }

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
        assertEquals(expectedUrl, driver.getCurrentUrl());
        driver.close();
        driver.switchTo().window(winHandleBefore);
        driver.findElements(entryCheckboxMarked).get(numberOfEntry - 1).click();
        return this;
    }

    public DiaryPage clickDonationButton() {
        String expectedUrl = "https://www.monkkee.com/en/support-us-with-a-donation/";
        driver.findElement(donationButton).click();
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        log.info("Current URL in donation Page: " + driver.getCurrentUrl());
        assertEquals(expectedUrl, driver.getCurrentUrl());
        driver.close();
        driver.switchTo().window(tabs.get(0));
        return this;
    }

    public TagsPage openTagsList() {
        clickElement(manageTagsLink);
        animationWait(animationPicture);
        return new TagsPage(driver);
    }

    public DiaryPage searchEntryByText(String textForSearch, int expectedNumberOfEntries) {
        clickElement(searchField);
        setValueInField(searchField, textForSearch);
        driver.findElement(searchField).sendKeys(textForSearch);
        clickElement(searchButton);
        waitTextToBe(searchFilterLabel, textForSearch);
        assertEquals(getNumberOfEntries(), expectedNumberOfEntries);
        return this;
    }

    public DiaryPage searchEntryByTag(String tagForSearch, int expectedNumberOfEntries) {
        clickElement(By.xpath(String.format("//a[contains(text(), '%s')]", tagForSearch)));
        waitTextToBe(searchFilterLabel, tagForSearch);
        assertEquals(getNumberOfEntries(), expectedNumberOfEntries);
        return this;
    }

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
        assertEquals(getNumberOfEntries(), expectedNumberOfEntries);
        return this;
    }

    public DiaryPage resetSearchResults(int expectedNumberOfEntriesAfterReset) {
        clickElement(resetLink);
        waitInvisibilityOfElementLocated(resetLink);
        assertEquals(getNumberOfEntries(), expectedNumberOfEntriesAfterReset);
        return this;
    }

    public SettingsPage openSettings() {
        clickElement(settingsLink);
        animationWait(animationPicture);
        return new SettingsPage(driver);
    }
}