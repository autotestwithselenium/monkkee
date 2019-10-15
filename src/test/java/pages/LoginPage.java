package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import lombok.extern.log4j.Log4j2;
import org.testng.Assert;

@Log4j2
public class LoginPage extends BasePage {
    WebDriverWait wait;
    String URL = "https://my.monkkee.com/#/";

    private By emailField = By.name("login");
    private By passwordField = By.name("password");
    private By loginButton = By.xpath("//*[@class='btn btn-primary']");


    public LoginPage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, 10);
    }

    public LoginPage login(String username, String password) {
        log.info("username: " + username);
        log.info("password: " + password);
        driver.findElement(emailField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
        return this;
    }

    public LoginPage openPage() {
        log.info("login URL: " + URL);
        driver.get(URL);
        return this;
    }

    public void checkLogin(String expectedURL) {
        wait.until(ExpectedConditions.urlToBe(expectedURL));
        Assert.assertEquals(driver.getCurrentUrl(), expectedURL);
    }
}