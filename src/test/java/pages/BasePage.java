package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class BasePage {

    public WebDriver driver;

    BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickToElement(By locator) {
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(locator));
        driver.findElement(locator).click();
    }

    public void animationWait(By locator) {
        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
}