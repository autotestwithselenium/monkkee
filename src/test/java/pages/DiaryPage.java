package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

@Log4j2
public class DiaryPage extends BasePage {

    public DiaryPage(WebDriver driver) {
        super(driver);
    }

    private By addEntryIcon = By.xpath("//*[@title='Create an entry']");
    private By entryBodyMessage = By.xpath("//*[@class='entry-container clearfix ng-scope']//*[@ng-bind-html='entry.body']");
    private By entryLink = By.xpath("//a[@class='entry']");
    private By linkToEntriesPage = By.xpath("//*[@ng-href='#/entries']");
    private By entryCheckboxNotMarked = By.xpath("//*[@class='entry-container clearfix ng-scope']//*[@class='ng-pristine ng-valid']");
    private By entryCheckboxMarked = By.xpath("//*[@class='entry-container clearfix ng-scope']//*[@class='ng-valid ng-dirty']");
    private By chooseAllEntriesCheckbox = By.xpath("//*[@title='Select all']");
    private By deleteIcon = By.id("delete-entries");
    private By noEntriesMessage = By.xpath("//*[@ng-show='noneMsgVisible']");
    private By animationPicture = By.xpath("//img[@class='animation']");
    private By deleteIconDisabled = By.xpath("//a[@class='btn btn-primary disabled' and @id='delete-entries']");
    private By printIcon = By.xpath("//*[@title='Print selected entries']");
    private By donationButton = By.xpath("//a[@class='donate-button ng-scope' and text()='Feed the monkkee']");
    private By manageTagsLink = By.xpath("//a[text() = 'Manage tags']");
    private By searchField = By.xpath("//input[@placeholder='Search']");
    private By searchFilterLabel = By.xpath("//span[@class='ng-binding search-parameter']");
    private By searchButton = By.xpath("//button[@title='Search']");
    private By resetLink = By.xpath("//a[text()='reset']");
    private By changeDateAndTimeLink = By.xpath("//input[@placeholder='Select date']");
    private By daysInCalendar = By.xpath("//div[@class='datepicker-days datepicker-mode']//table[@class=' table-condensed']//td");

    public DiaryPage clickAddEntry() {
        clickToElement(addEntryIcon);
        animationWait(animationPicture);
        return this;
    }

    public int getNumberOfEntries() {
        return driver.findElements(entryBodyMessage).size();
    }

    public DiaryPage verifyEntryMessage(String textMessage, int numberOfEntry) {
        String actualEntryMessage = driver.findElements(entryBodyMessage).get(numberOfEntry - 1).getText();
        assertEquals(textMessage, actualEntryMessage);
        return this;
    }

    public DiaryPage clickEntry(int numberOfEntry) {
        driver.findElements(entryLink).get(numberOfEntry - 1).click();
        animationWait(animationPicture);
        return this;
    }

    public DiaryPage deleteEntry(int numberOfEntry) {
        int numberOfEntries = getNumberOfEntries();
        driver.findElements(entryCheckboxNotMarked).get(numberOfEntry - 1).click();
        wait.until(ExpectedConditions.elementToBeClickable(deleteIcon));
        driver.findElement(deleteIcon).click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        wait.until(ExpectedConditions.presenceOfElementLocated(deleteIconDisabled));
        int numberOfEntriesAfterDelete = getNumberOfEntries();
        assertEquals(numberOfEntries - 1, numberOfEntriesAfterDelete);
        return this;
    }

    public DiaryPage deleteAllEntries() {
        driver.findElement(chooseAllEntriesCheckbox).click();
        wait.until(ExpectedConditions.elementToBeClickable(deleteIcon));
        driver.findElement(deleteIcon).click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        wait.until(ExpectedConditions.presenceOfElementLocated(deleteIconDisabled));
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
        wait.until(ExpectedConditions.elementToBeClickable(printIcon));
        driver.findElement(printIcon).click();
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

    public DiaryPage openTagsList() {
        driver.findElement(manageTagsLink).click();
        animationWait(animationPicture);
        return this;
    }

    public DiaryPage searchEntryByText(String textForSearch, int expectedNumberOfEntries) {
        driver.findElement(searchField).click();
        driver.findElement(searchField).clear();
        driver.findElement(searchField).sendKeys(textForSearch);
        driver.findElement(searchButton).click();
        waitTextToBe(searchFilterLabel, textForSearch);
        assertEquals(getNumberOfEntries(), expectedNumberOfEntries);
        return this;
    }


    public DiaryPage searchEntryByTag(String tagForSearch, int expectedNumberOfEntries) {
        driver.findElement(By.xpath(String.format("//a[contains(text(), '%s')]", tagForSearch))).click();
        waitTextToBe(searchFilterLabel, tagForSearch);
        assertEquals(getNumberOfEntries(), expectedNumberOfEntries);
        return this;
    }

    public DiaryPage searchEntryByDate(String dayValue, int expectedNumberOfEntries) {
        driver.findElement(changeDateAndTimeLink).click();

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
        driver.findElement(resetLink).click();
        waitInvisibilityOfElementLocated(resetLink);
        assertEquals(getNumberOfEntries(), expectedNumberOfEntriesAfterReset);
        return this;

    }

}