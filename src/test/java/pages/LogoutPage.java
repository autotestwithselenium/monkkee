package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import lombok.extern.log4j.Log4j2;
import utils.AllureUtils;

@Log4j2
public class LogoutPage extends BasePage {
    WebDriverWait wait;
    String expectedURLAfterLogOut = "https://my.monkkee.com/#/";
    private By emailField = By.name("login");
    private By logoutLink = By.xpath("//button[@class='user-menu-btn']");
    private By logoutLinkInDonationWindow = By.xpath("//div[@class='donation-notice-buttons']//button[@class='btn btn-primary'][1]");

    public LogoutPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, 10);
    }

    public void logOut() {

        driver.findElement(logoutLink).click();
        try {
            waitPresenceOfElementLocated(emailField);
        } catch (Throwable ex) {
            AllureUtils.takeScreenshot(driver);
            clickElement(logoutLinkInDonationWindow);
            AllureUtils.takeScreenshot(driver);
            waitInvisibilityOfElementLocated(logoutLinkInDonationWindow);
            waitPresenceOfElementLocated(emailField);
        }
        String actualURLAfterLogOut = driver.getCurrentUrl();
        log.info("Expected URL after logout: " + expectedURLAfterLogOut);
        log.info("Actual URL after logout: " + actualURLAfterLogOut);
        Assert.assertEquals(actualURLAfterLogOut, expectedURLAfterLogOut, "URL should be correct after logging out");
        driver.quit();
    }
}