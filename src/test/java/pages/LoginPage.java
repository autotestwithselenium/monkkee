package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import lombok.extern.log4j.Log4j2;
import org.testng.Assert;
import utils.ConfigurationFileManager;

@Log4j2
public class LoginPage extends BasePage {
    WebDriverWait wait;
    String URL = ConfigurationFileManager.getInstance().getUrlBeforeLogin();

    private By emailField = By.name("login");
    private By passwordField = By.name("password");
    private By loginButton = By.xpath("//*[@class='btn btn-primary']");
    private By animationPicture = By.xpath("//img[@class='animation']");
    private By cancelDonationButton = By.xpath("//button[@custom-modal-close='login()']");

    public LoginPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 10);
    }

    public LoginPage login(String username, String password) {
        log.info("username: " + username);
        log.info("password: " + password);
        setValueInField(emailField, username);
        setValueInField(passwordField, password);
        clickElement(loginButton);
        animationWait(animationPicture);
        return this;
    }

    public LoginPage openPage() {
        log.info("login URL: " + URL);
        driver.get(URL);
        return this;
    }

    public void checkLogin(String expectedURL) {
        try {
            waitUrlToBe(expectedURL);
        } catch (Throwable ex) {
            clickElement(cancelDonationButton);
            waitInvisibilityOfElementLocated(cancelDonationButton);
            waitUrlToBe(expectedURL);
        }
        Assert.assertEquals(driver.getCurrentUrl(), expectedURL);
    }
}