package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;
import pages.LogoutPage;
import utils.CapabilitiesGenerator;

import java.util.concurrent.TimeUnit;

public class BaseTest {
    WebDriver driver;
    String loginName = "tms5@mailinator.com";
    String loginPassword = "password01";
    String expectedUrl = "https://my.monkkee.com/#/entries";

   // @BeforeClass
    @BeforeMethod
    public void openDriver() {
        driver = new ChromeDriver(CapabilitiesGenerator.getChromeOptions());
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
        LoginPage loginPage = new LoginPage(driver);
        loginPage
                .openPage()
                .login(loginName, loginPassword)
                .checkLogin(expectedUrl);
    }

   // @AfterClass
   @AfterMethod
   public void closeDriver() {
       new LogoutPage(driver).logOut();
   }
}