package pages;

import lombok.extern.log4j.Log4j2;
import net.bytebuddy.implementation.bytecode.Throw;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;

@Log4j2
public class EntryPage extends BasePage {

    public EntryPage(WebDriver driver) {
        super(driver);
    }

    private By entryArea = By.xpath("//*[@id='editable']");
    private By saveIconDisabled = By.xpath("//*[@class='cke_combo_text cke_combo_inlinelabel cke_savetoggle_text']");
    private By saveIconEnabled = By.xpath("//a[@class='cke_button cke_button__savetoggle cke_button_off']");

    private By backToEntriesIcon = By.id("back-to-overview");
    private By addEntryIcon = By.xpath("//*[@title='Create an entry']");
    private By addImageIcon = By.xpath("//*[@title='Image']");
    private By imageUrlInputField = By.xpath("//input[@class='cke_dialog_ui_input_text']");
    private By confirmButtonInImageForm = By.xpath("//span[text()='OK']");
    private By newTagField = By.xpath("//input[@id='new-tag']");
    private By createNewTagButton = By.xpath("//button[@id='assign-new-tag']");
    private By animationPicture = By.xpath("//img[@class='animation']");
    private By openOlderEntryLinkEnabled = By.xpath("//a[@class='btn btn-primary' and @title='Older']");
    private By openNewerEntryLinkEnabled = By.xpath("//a[@class='btn btn-primary' and @title='Newer']");
    private By openNewerEntryLinkDisabled = By.xpath("//a[@class='btn btn-primary disabled' and @title='Newer']");
    private By changeDateAndTimeLink = By.xpath("//a[@title='Change entry date/time']");
    private By changeDateField = By.id("date");
    private By changeTimeField = By.id("time");
    private By saveChangeDateAndTimeButton = By.xpath("//button[@ng-click='changeDate()']");
    private By daysInCalendar = By.xpath("//div[@class='datepicker-days datepicker-mode']//table[@class=' table-condensed']//td");
    private By dateAndTimeField = By.xpath("//time");
    private By selectTagField = By.xpath("//select[@id='select-tag']");
    private By confirmAddingExistingTagButton = By.xpath("//button[@id='assign-existing-tag']");
    private By assignedTagToEntry = By.xpath("//span[@ng-repeat='assignedTag in assignedTags']/a");

    public EntryPage addEntry(String message) {
        waitPresenceOfElementLocated(saveIconDisabled);
        waitClickable(entryArea);
        driver.findElement(entryArea).click();
        driver.findElement(entryArea).sendKeys(message);
        try {
            waitPresenceOfElementLocated(saveIconEnabled);
        } catch (Throwable ex) {
            waitTextToBe(saveIconDisabled, "saved");
            assertEquals(driver.findElement(saveIconDisabled).getText(), "saved");
        }
        return this;
    }

    public EntryPage addImage(String imageLink) {
        driver.findElement(addImageIcon).click();
        driver.findElement(imageUrlInputField).click();
        driver.findElement(imageUrlInputField).sendKeys(imageLink);
        driver.findElement(confirmButtonInImageForm).click();
        wait.until(ExpectedConditions.textToBe(saveIconDisabled, "saved"));
        assertEquals(driver.findElement(saveIconDisabled).getText(), "saved");
        return this;
    }

    public EntryPage editEntry(String message) {
        driver.findElement(entryArea).click();
        driver.findElement(entryArea).clear();
        driver.findElement(entryArea).sendKeys(message);
        wait.until(ExpectedConditions.textToBe(saveIconDisabled, "saved"));
        assertEquals(driver.findElement(saveIconDisabled).getText(), "saved");
        return this;
    }

    public EntryPage verifyEntryWithAddedImage(String imageLink) {
        log.info("Image validation link: " + String.format("//img[@data-cke-saved-src='%s']", imageLink));
        try {
            driver.findElement(By.xpath(String.format("//img[@data-cke-saved-src='%s']", imageLink)));
        } catch (Throwable ex) {
            throw new ElementNotPresentAtPageException("Image is not added", ex);
        }
        return this;
    }


    public EntryPage clickBackToEntriesIcon() {
        driver.findElement(backToEntriesIcon).click();
        animationWait(animationPicture);
        waitPresenceOfElementLocated(addEntryIcon);
        return this;
    }

    public EntryPage addNewTag(String tagName) {
        driver.findElement(newTagField).click();
        driver.findElement(newTagField).sendKeys(tagName);
        driver.findElement(createNewTagButton).click();
        WebElement tagElement = driver.findElement(By.linkText(tagName));
        wait.until(ExpectedConditions.visibilityOf(tagElement));
        assertTrue(checkIfTagIsAddedInEntry(tagName));
        return this;
    }

    public EntryPage openOlderEntry(String messageText) {
        driver.findElement(openOlderEntryLinkEnabled).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(openNewerEntryLinkEnabled));
        assertEquals(messageText, driver.findElement(entryArea).getText());
        return this;
    }

    public EntryPage openNewerEntry(String messageText) {
        driver.findElement(openNewerEntryLinkEnabled).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(openNewerEntryLinkDisabled));
        assertEquals(messageText, driver.findElement(entryArea).getText());
        return this;
    }

    public EntryPage changeDateAndTimeInEntry(String dayValue, String timeValue, String expectedDateAndTime) {
        driver.findElement(changeDateAndTimeLink).click();
        driver.findElement(changeDateField).click();

        List<WebElement> allDates = driver.findElements(daysInCalendar);
        for (WebElement ele : allDates) {
            String date = ele.getText();
            if (date.equalsIgnoreCase(dayValue)) {
                ele.click();
                break;
            }
        }

        driver.findElement(changeTimeField).click();
        driver.findElement(changeTimeField).clear();
        driver.findElement(changeTimeField).sendKeys(timeValue);
        driver.findElement(saveChangeDateAndTimeButton).click();
        assertEquals(expectedDateAndTime, driver.findElement(dateAndTimeField).getText());
        return this;
    }


    public EntryPage chooseExistingTagFromList(String tagName) {
        Select tagsList = new Select(driver.findElement(selectTagField));
        tagsList.selectByVisibleText(tagName);
        driver.findElement(confirmAddingExistingTagButton).click();
        assertTrue(checkIfTagIsAddedInEntry(tagName));
        return this;
    }


    public boolean checkIfTagIsAddedInEntry(String tagName) {
        boolean assignedTag = false;
        List<WebElement> assignedToEntryTags = driver.findElements(assignedTagToEntry);
        for (WebElement tag : assignedToEntryTags) {
            String tagNameValue = tag.getText();
            if (tagNameValue.equalsIgnoreCase(tagName)) {
                assignedTag = true;
                break;
            }
        }
        return assignedTag;
    }

    public EntryPage removeTagFromEntry(String tagName){
        List<WebElement> assignedToEntryTags = driver.findElements(assignedTagToEntry);
        for (WebElement tag : assignedToEntryTags) {
            String tagNameValue = tag.getText();
            if (tagNameValue.equalsIgnoreCase(tagName)) {
                tag.click();
                break;
            }
        }
        assertFalse(checkIfTagIsAddedInEntry(tagName));
        return this;
    }

}