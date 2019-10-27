package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.LogoutPage;
import utils.CapabilitiesGenerator;
import utils.ConfigurationFileManager;

import java.util.concurrent.TimeUnit;

public class BaseTest {
    public WebDriver driver;
    String loginName = ConfigurationFileManager.getInstance().getLogin();
    String loginPassword = ConfigurationFileManager.getInstance().getPassword();
    String expectedUrl = ConfigurationFileManager.getInstance().getUrlAfterLogin();

    @BeforeMethod
    public void openDriver() {
        driver = new ChromeDriver(CapabilitiesGenerator.getChromeOptions());
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
        LoginPage loginPage = new LoginPage(driver);
        loginPage
                .openPage()
                .login(loginName, loginPassword)
                .checkLogin(expectedUrl)
                .checkLanguage();
    }


    @AfterMethod
    public void closeDriver() {
        new LogoutPage(driver).logOut();
    }
}