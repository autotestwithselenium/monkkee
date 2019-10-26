package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import utils.AllureUtils;
import lombok.extern.log4j.Log4j2;
import static org.testng.AssertJUnit.assertEquals;

@Log4j2
public class SettingsPage extends BasePage {
    public SettingsPage(WebDriver driver) {
        super(driver);
    }
    private By languageSettingsLink = By.xpath("//a[@href='#/settings/locale']/i[@class='icon-cog icon-light']");
    private By messageAboutChangedLanguage = By.xpath("//div[@class='alert alert-success']");
    private By selectLanguage = By.name("selectLocale");
    private By confirmChangeLanguageButton = By.xpath("//button[@type='submit']");
    private By backToEntriesIcon = By.xpath("//a[@href='#/entries' and @class='btn btn-primary']");
    private By animationPicture = By.xpath("//img[@class='animation']");
    private By addEntryIcon = By.xpath("//*[@id='create-entry']");
    private By openPasswordSettingsLink = By.xpath("//a[@href='#/settings/password']");
    private By oldPasswordField = By.xpath("//input[@id='old-password']");
    private By newPasswordField = By.xpath("//input[@id='password']");
    private By newPasswordConfirmationField = By.xpath("//input[@id='password-confirmation']");
    private By confirmChangePasswordButton = By.xpath("//div[@class='btn-text-content' and text() ='OK']");
    private By validationMessageForFieldOldPassword = By.xpath("//input[@id='old-password']/following-sibling::div");


    @Step("Choose the following language: '{language}' in settings")
    public SettingsPage chooseLanguage(String language, String languageText) {
        clickElement(languageSettingsLink);
        waitClickable(selectLanguage);
        Select tagsList = new Select(driver.findElement(selectLanguage));
        tagsList.selectByVisibleText(language);
        log.info("Chosen value of language in list: "+language);
        clickElement(confirmChangeLanguageButton);
        waitPresenceOfElementLocated(messageAboutChangedLanguage);
        assertEquals(driver.findElement(messageAboutChangedLanguage).getText(), languageText);
        AllureUtils.takeScreenshot(driver);
        return this;
    }

    @Step("Change password, set incorrect value in field 'Old password'")
    public SettingsPage changePasswordWithIncorrectExistingPassword(String existingPassword, String newPassword) {
        log.info("Value set infield 'Old password': "+existingPassword);
        clickElement(openPasswordSettingsLink);
        clickElement(oldPasswordField);
        setValueInField(oldPasswordField, existingPassword);

        clickElement(newPasswordField);
        setValueInField(newPasswordField, newPassword);

        clickElement(newPasswordConfirmationField);
        setValueInField(newPasswordConfirmationField, newPassword);

        clickElement(confirmChangePasswordButton);
        waitPresenceOfElementLocated(validationMessageForFieldOldPassword);
        assertEquals(driver.findElement(validationMessageForFieldOldPassword).getText(), "Old password is wrong");
        AllureUtils.takeScreenshot(driver);
        return this;
    }

    @Step("Open Diary Page")
    public DiaryPage openDiaryPage() {
        waitPresenceOfElementLocated(backToEntriesIcon);
        waitElementToBeClickable(backToEntriesIcon);
        driver.findElement(backToEntriesIcon).click();
        animationWait(animationPicture);
        waitPresenceOfElementLocated(addEntryIcon);
        return new DiaryPage(driver);
    }
}
