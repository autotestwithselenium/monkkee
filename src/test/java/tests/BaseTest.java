package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import pages.LoginPage;
import pages.LogoutPage;
import utils.CapabilitiesGenerator;

import java.util.concurrent.TimeUnit;

public class BaseTest {
    WebDriver driver;
    String loginName = "tms1@mailinator.com";
    String loginPassword = "password01";
    String expectedUrl = "https://my.monkkee.com/#/entries";

    @BeforeClass
    public void openDriver() {
        driver = new ChromeDriver(CapabilitiesGenerator.getChromeOptions());
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        LoginPage loginPage = new LoginPage(driver);
        loginPage
                .openPage()
                .login(loginName, loginPassword)
                .checkLogin(expectedUrl);
    }

    @AfterClass
    public void closeDriver() {
        new LogoutPage(driver).logOut();
    }
}