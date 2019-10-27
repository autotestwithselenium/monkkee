package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;

import utils.ElementNotPresentAtPageException;

@Log4j2
public class EntryPage extends BasePage {

    public EntryPage(WebDriver driver) {
        super(driver);
    }

    private By entryArea = By.xpath("//*[@id='editable']");
    private By saveIconDisabled = By.xpath("//*[@class='cke_combo_text cke_combo_inlinelabel cke_savetoggle_text']");
    private By saveIconEnabled = By.xpath("//a[@class='cke_button cke_button__savetoggle cke_button_off']");
    private By backToEntriesIcon = By.id("back-to-overview");
    private By addEntryIcon = By.xpath("//*[@id='create-entry']");
    private By addImageIcon = By.xpath("//*[@class='cke_button cke_button__image cke_button_off']");
    private By imageUrlInputField = By.xpath("//input[@class='cke_dialog_ui_input_text']");
    private By confirmButtonInImageForm = By.xpath("//a[@class='cke_dialog_ui_button cke_dialog_ui_button_ok']/span[@class='cke_dialog_ui_button']");
    private By newTagField = By.xpath("//input[@id='new-tag']");
    private By createNewTagButton = By.xpath("//button[@id='assign-new-tag']");
    private By animationPicture = By.xpath("//img[@class='animation']");
    private By openOlderEntryLinkEnabled = By.xpath("//a[@class='btn btn-primary' and @ng-class='model.nextClass']");
    private By openNewerEntryLinkEnabled = By.xpath("//a[@class='btn btn-primary' and @ng-class='model.previousClass']");
    private By openNewerEntryLinkDisabled = By.xpath("//a[@class='btn btn-primary disabled' and @ng-class='model.previousClass']");
    private By changeDateAndTimeLink = By.xpath("//a[@ng-click='switchToDateEditMode()']");
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
        clickElement(entryArea);
        setValueInField(entryArea, message);
        try {
            waitPresenceOfElementLocated(saveIconEnabled);
        } catch (Throwable ex) {
            waitTextToBe(saveIconDisabled, "saved");
            assertEquals(driver.findElement(saveIconDisabled).getText(), "saved");
        }
        return this;
    }

    public EntryPage addImage(String imageLink) {
        clickElement(addImageIcon);
        clickElement(imageUrlInputField);
        setValueInField(imageUrlInputField, imageLink);
        clickElement(confirmButtonInImageForm);
        waitTextToBe(saveIconDisabled, "saved");
        assertEquals(driver.findElement(saveIconDisabled).getText(), "saved");
        return this;
    }

    public EntryPage editEntry(String message) {
        clickElement(entryArea);
        setValueInField(entryArea, message);
        waitTextToBe(saveIconDisabled, "saved");
        assertEquals(driver.findElement(saveIconDisabled).getText(), "saved");
        return this;
    }

    public EntryPage verifyAddedValueInEntry(String message) {
        assertEquals(driver.findElement(entryArea).getText(), message);
        return this;
    }

    public EntryPage clearEntry() {
        clickElement(entryArea);
        driver.findElement(entryArea).clear();
        waitTextToBe(saveIconDisabled, "saved");
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

    public DiaryPage clickBackToEntriesIcon() {
        clickElement(backToEntriesIcon);
        animationWait(animationPicture);
        waitPresenceOfElementLocated(addEntryIcon);
        return new DiaryPage(driver);
    }

    public EntryPage addNewTag(String tagName) {
        clickElement(newTagField);
        setValueInField(newTagField, tagName);
        clickElement(createNewTagButton);
        WebElement tagElement = driver.findElement(By.linkText(tagName));
        waitVisibilityOf(tagElement);
        assertTrue(checkIfTagIsAddedInEntry(tagName));
        return this;
    }

    public EntryPage openOlderEntry(String messageText) {
        clickElement(openOlderEntryLinkEnabled);
        waitVisibilityOfElementLocated(openNewerEntryLinkEnabled);
        assertEquals(messageText, driver.findElement(entryArea).getText());
        return this;
    }

    public EntryPage openNewerEntry(String messageText) {
        clickElement(openNewerEntryLinkEnabled);
        waitVisibilityOfElementLocated(openNewerEntryLinkDisabled);
        assertEquals(messageText, driver.findElement(entryArea).getText());
        return this;
    }

    public EntryPage changeDateAndTimeInEntry(String dayValue, String timeValue, String expectedDateAndTime) {
        clickElement(changeDateAndTimeLink);
        clickElement(changeDateField);

        List<WebElement> allDates = driver.findElements(daysInCalendar);
        for (WebElement ele : allDates) {
            String date = ele.getText();
            if (date.equalsIgnoreCase(dayValue)) {
                ele.click();
                break;
            }
        }

        clickElement(changeTimeField);
        setValueInField(changeTimeField, timeValue);
        clickElement(saveChangeDateAndTimeButton);
        assertEquals(expectedDateAndTime, driver.findElement(dateAndTimeField).getText());
        return this;
    }

    public EntryPage changeDateInEntry(String dayValue) {
        clickElement(changeDateAndTimeLink);
        clickElement(changeDateField);

        List<WebElement> allDates = driver.findElements(daysInCalendar);
        for (WebElement ele : allDates) {
            String date = ele.getText();
            if (date.equalsIgnoreCase(dayValue)) {
                ele.click();
                break;
            }
        }

        clickElement(saveChangeDateAndTimeButton);
        return this;
    }

    public EntryPage chooseExistingTagFromList(String tagName) {
        Select tagsList = new Select(driver.findElement(selectTagField));
        tagsList.selectByVisibleText(tagName);
        clickElement(confirmAddingExistingTagButton);
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

    public EntryPage removeTagFromEntry(String tagName) {
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