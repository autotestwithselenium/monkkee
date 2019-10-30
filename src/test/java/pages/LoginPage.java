package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import lombok.extern.log4j.Log4j2;
import org.testng.Assert;
import utils.AllureUtils;
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
    private By settingsLink = By.xpath("//a[@href='#/settings/locale']");

    public LoginPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 10);
    }

    @Step("Login to monkkee")
    public LoginPage login(String username, String password) {
        log.info("username: " + username);
        log.info("password: " + password);
        setValueInField(emailField, username);
        setValueInField(passwordField, password);
        clickElement(loginButton);
        animationWait(animationPicture);
        return this;
    }

    @Step("Open monkkee URL")
    public LoginPage openPage() {
        log.info("login URL: " + URL);
        driver.get(URL);
        return this;
    }

    @Step("Check, if English language is set in settings")
    public LoginPage checkLanguage() {
        if (driver.findElement(settingsLink).getText().equals("Settings")) {
            log.info("English is set");
        } else {
            log.info("Setting value: " + driver.findElement(settingsLink).getText());
            clickElement(settingsLink);
            new SettingsPage(driver)
                    .chooseLanguage("English", "Your language has been changed successfully")
                    .openDiaryPage();
        }
        return this;
    }

    @Step("Check, if login is successful")
    public LoginPage checkLogin(String expectedURL) {
        try {
            waitUrlToBe(expectedURL);
        } catch (Throwable ex) {
            AllureUtils.takeScreenshot(driver);
            clickElement(cancelDonationButton);
            AllureUtils.takeScreenshot(driver);
            waitInvisibilityOfElementLocated(cancelDonationButton);
            waitUrlToBe(expectedURL);
        }
        Assert.assertEquals(driver.getCurrentUrl(), expectedURL, "After login URL should be as expected - entries URL");
        return this;
    }

}