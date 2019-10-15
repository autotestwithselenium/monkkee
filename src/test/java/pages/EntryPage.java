package pages;

import com.fasterxml.jackson.databind.ser.Serializers;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.testng.AssertJUnit.assertEquals;

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
    private By addedImage = By.xpath("//img[@data-cke-saved-src='https://content.onliner.by/news/1100x5616/cd4ab5fe98649030080244f3c81857c3.jpeg']");

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

    public EntryPage verifyEntryWithAddedImage(String imageLink) {

        return this;

    }


    public EntryPage clickBackToEntriesIcon() {
        driver.findElement(backToEntriesIcon).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(addEntryIcon));
        return this;
    }
}
