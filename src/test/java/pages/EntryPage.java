package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;

import utils.AllureUtils;
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

    @Step("Add new entry")
    public EntryPage addEntry(String message) {
        waitPresenceOfElementLocated(saveIconDisabled);
        waitClickable(entryArea);
        clickElement(entryArea);
        setValueInField(entryArea, message);
        try {
            waitPresenceOfElementLocated(saveIconEnabled);
        } catch (Throwable ex) {
            waitTextToBe(saveIconDisabled, "saved");
            assertEquals("Text of save icon should be 'saved'", driver.findElement(saveIconDisabled).getText(), "saved");
        }
        AllureUtils.takeScreenshot(driver);
        return this;
    }

    @Step("Add image in entry")
    public EntryPage addImage(String imageLink) {
        clickElement(addImageIcon);
        clickElement(imageUrlInputField);
        setValueInField(imageUrlInputField, imageLink);
        clickElement(confirmButtonInImageForm);
        waitTextToBe(saveIconDisabled, "saved");
        assertEquals("Text of save icon should be 'saved'", driver.findElement(saveIconDisabled).getText(), "saved");
        return this;
    }

    @Step("Edit entry")
    public EntryPage editEntry(String message) {
        clickElement(entryArea);
        setValueInField(entryArea, message);
        waitTextToBe(saveIconDisabled, "saved");
        assertEquals("Text of save icon should be 'saved'", driver.findElement(saveIconDisabled).getText(), "saved");
        return this;
    }

    @Step("Check added value in entry")
    public EntryPage verifyAddedValueInEntry(String message) {
        assertEquals("Value in entry area is the same as entered earlier value'", driver.findElement(entryArea).getText(), message);
        return this;
    }

    @Step("Remove value from entry")
    public EntryPage clearEntry() {
        clickElement(entryArea);
        driver.findElement(entryArea).clear();
        waitTextToBe(saveIconDisabled, "saved");
        assertEquals("Text of save icon should be 'saved'", driver.findElement(saveIconDisabled).getText(), "saved");
        return this;
    }

    @Step("Check, if image was added in entry")
    public EntryPage verifyEntryWithAddedImage(String imageLink) {
        log.info("Image validation link: " + String.format("//img[@data-cke-saved-src='%s']", imageLink));
        try {
            driver.findElement(By.xpath(String.format("//img[@data-cke-saved-src='%s']", imageLink)));
        } catch (Throwable ex) {
            log.error("Image is not added in entry. xpath of image: " + String.format("//img[@data-cke-saved-src='%s']", imageLink));
            throw new ElementNotPresentAtPageException("Image is not added", ex);
        }
        return this;
    }

    @Step("Open diary page with entries")
    public DiaryPage clickBackToEntriesIcon() {
        clickElement(backToEntriesIcon);
        animationWait(animationPicture);
        waitPresenceOfElementLocated(addEntryIcon);
        return new DiaryPage(driver);
    }

    @Step("Add new tag")
    public EntryPage addNewTag(String tagName) {
        clickElement(newTagField);
        setValueInField(newTagField, tagName);
        clickElement(createNewTagButton);
        WebElement tagElement = driver.findElement(By.linkText(tagName));
        waitVisibilityOf(tagElement);
        assertTrue(checkIfTagIsAddedInEntry(tagName));
        return this;
    }

    @Step("Open older entry from existing entry")
    public EntryPage openOlderEntry(String messageText) {
        clickElement(openOlderEntryLinkEnabled);
        waitVisibilityOfElementLocated(openNewerEntryLinkEnabled);
        assertEquals("Text value in opened older entry is the same as expected value", messageText, driver.findElement(entryArea).getText());
        return this;
    }

    @Step("Open newer entry from existing entry")
    public EntryPage openNewerEntry(String messageText) {
        clickElement(openNewerEntryLinkEnabled);
        waitVisibilityOfElementLocated(openNewerEntryLinkDisabled);
        assertEquals("Text value in opened newer entry is the same as expected value", messageText, driver.findElement(entryArea).getText());
        return this;
    }

    @Step("Change day and time in entry")
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
        assertEquals("Changed date and time in entry is the same as expected defined value", expectedDateAndTime, driver.findElement(dateAndTimeField).getText());
        return this;
    }

    @Step("Change day in entry")
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

    @Step("Choose existing tag from tags list")
    public EntryPage chooseExistingTagFromList(String tagName) {
        Select tagsList = new Select(driver.findElement(selectTagField));
        tagsList.selectByVisibleText(tagName);
        clickElement(confirmAddingExistingTagButton);
        assertTrue(checkIfTagIsAddedInEntry(tagName));
        return this;
    }

    @Step("Check if tag is added in entry")
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

    @Step("Remove tag from entry")
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