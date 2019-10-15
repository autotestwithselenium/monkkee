package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import lombok.extern.log4j.Log4j2;

import static org.testng.AssertJUnit.assertEquals;

@Log4j2
public class DiaryPage extends BasePage{
    WebDriverWait wait;

    public DiaryPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, 10);
    }

    private By addEntryIcon = By.xpath("//*[@title='Create an entry']");
    private By backToEntriesIcon = By.id("back-to-overview");
    private By entryBodyMessage = By.xpath("//*[@class='entry-container clearfix ng-scope']//*[@ng-bind-html='entry.body']");
    private By entryLink = By.xpath("//a[@class='entry']");
    private By linkToEntriesPage = By.xpath("//*[@ng-href='#/entries']");
    private By entryCheckbox = By.xpath("//*[@class='entry-container clearfix ng-scope']//*[@class='ng-pristine ng-valid']");
    private By chooseAllEntriesCheckbox=By.xpath("//*[@title='Select all']");
    private By deleteIcon = By.id("delete-entries");
    private By noEntriesMessage = By.xpath("//*[@ng-show='noneMsgVisible']");

    public DiaryPage clickAddEntry() {
        clickToElement(addEntryIcon);
        return this;
    }

    public DiaryPage openEntriesPage() {
        clickToElement(linkToEntriesPage);
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

    public DiaryPage editEntry(String editMessage, int numberOfEntry) {
//        driver.findElements(entryLink).get(numberOfEntry - 1).click();
//        driver.findElement(entryArea).click();
//        driver.findElement(entryArea).clear();
//        driver.findElement(entryArea).sendKeys(editMessage);
//
//        wait.until(ExpectedConditions.textToBe(saveIcon, "saved"));
//        assertEquals(driver.findElement(saveIcon).getText(), "saved");
        return this;
    }

    public DiaryPage deleteEntry(int numberOfEntry) {
        int numberOfEntries = getNumberOfEntries();
        driver.findElements(entryCheckbox).get(numberOfEntry - 1).click();
        wait.until(ExpectedConditions.elementToBeClickable(deleteIcon));
        driver.findElement(deleteIcon).click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        ExpectedConditions.not(ExpectedConditions.elementToBeClickable(deleteIcon));
//        try {
//            Thread.sleep(200);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
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
        ExpectedConditions.not(ExpectedConditions.elementToBeClickable(deleteIcon));
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int numberOfEntriesAfterDelete = getNumberOfEntries();
        assertEquals(0, numberOfEntriesAfterDelete);
        assertEquals(driver.findElement(noEntriesMessage).getText(), "No entries found");

        return this;
    }

}