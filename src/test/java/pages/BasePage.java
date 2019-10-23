package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {

    public WebDriver driver;
    public WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 15);

    }

    public void clickToElement(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        driver.findElement(locator).click();
    }

    public void animationWait(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void waitClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void waitTextToBe(By locator, String textToCompare) {
        wait.until(ExpectedConditions.textToBe(locator, textToCompare));
    }

    public void waitPresenceOfElementLocated(By locator){
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));

    }

    public void waitInvisibilityOfElementLocated(By locator){
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
}