package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.testng.AssertJUnit.assertEquals;

@Log4j2
public class EntryPage extends BasePage {
    WebDriverWait wait;

    public EntryPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, 10);
    }

    private By entryArea = By.xpath("//*[@id='editable']");
    private By saveIcon = By.xpath("//*[@class='cke_combo_text cke_combo_inlinelabel cke_savetoggle_text']");
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

    public EntryPage addEntry(String message) {
        wait.until(ExpectedConditions.elementToBeClickable(entryArea));
        driver.findElement(entryArea).click();
        driver.findElement(entryArea).sendKeys(message);
        wait.until(ExpectedConditions.textToBe(saveIcon, "saved"));
        assertEquals(driver.findElement(saveIcon).getText(), "saved");
        return this;
    }

    public EntryPage addImage(String imageLink) {
        driver.findElement(addImageIcon).click();
        driver.findElement(imageUrlInputField).click();
        driver.findElement(imageUrlInputField).sendKeys(imageLink);
        driver.findElement(confirmButtonInImageForm).click();
        wait.until(ExpectedConditions.textToBe(saveIcon, "saved"));
        assertEquals(driver.findElement(saveIcon).getText(), "saved");
        return this;
    }

    public EntryPage editEntry(String message) {
        driver.findElement(entryArea).click();
        driver.findElement(entryArea).clear();
        driver.findElement(entryArea).sendKeys(message);
        wait.until(ExpectedConditions.textToBe(saveIcon, "saved"));
        assertEquals(driver.findElement(saveIcon).getText(), "saved");
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
        wait.until(ExpectedConditions.presenceOfElementLocated(addEntryIcon));
        return this;
    }

    public EntryPage addNewTag(String tagName) {
        driver.findElement(newTagField).click();
        driver.findElement(newTagField).sendKeys(tagName);
        driver.findElement(createNewTagButton).click();
        WebElement tagElement = driver.findElement(By.linkText(tagName));
        wait.until(ExpectedConditions.visibilityOf(tagElement));
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

}