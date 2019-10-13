package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LogoutPage {
    WebDriver driver;
    WebDriverWait wait;
    String expectedURLAfterLogOut = "https://my.monkkee.com/#/";
    private By emailField=By.name("login");

    private By logoutLink = By.xpath("//button[@class='user-menu-btn']");

    public LogoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public void logOut() {
        driver.findElement(logoutLink).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(emailField));
        String actualURLAfterLogOut = driver.getCurrentUrl();
        log.info("Expected URL after logout: " + expectedURLAfterLogOut);
        log.info("Actual URL after logout: " + actualURLAfterLogOut);
        Assert.assertEquals(actualURLAfterLogOut, expectedURLAfterLogOut);
        driver.quit();
    }
}