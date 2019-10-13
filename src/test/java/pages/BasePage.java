package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

class BasePage {

    private WebDriver driver;

    BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}