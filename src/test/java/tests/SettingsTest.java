package tests;

import io.qameta.allure.Description;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.DiaryPage;
import lombok.extern.log4j.Log4j2;
import utils.MyRetryAnalyzer;

@Log4j2
public class SettingsTest extends BaseTest {

    @Test(description = "20 - change language settings", dataProvider = "getDataForLanguageSettings", retryAnalyzer = MyRetryAnalyzer.class)
    @Description("Choose another language in settings")
    public void changeLanguageSettingsTest(String language, String warningMessage) {
        DiaryPage diaryPage = new DiaryPage(driver);
        diaryPage
                .openSettings()
                .chooseLanguage(language, warningMessage)
                .openDiaryPage();
    }

    @DataProvider
    public Object[][] getDataForLanguageSettings() {
        return new Object[][]{{"Français", "Modifications enregistrées"},
                {"Portuguese", "Seu idioma foi alterado com sucesso"},
                {"Deutsch", "Deine Spracheinstellung wurde erfolgreich geändert"},
                {"English", "Your language has been changed successfully"}};
    }

    @Test(description = "22 - change password settings, enter incorrect existing password", retryAnalyzer = MyRetryAnalyzer.class)
    @Description("Change password settings, enter incorrect existing password")
    public void changePasswordIncorrectOldPasswordTest() {
        DiaryPage diaryPage = new DiaryPage(driver);
        String existingPassword = "IncorrectPassword!#";
        String newPassword = "testTest123!#";
        diaryPage
                .openSettings()
                .changePasswordWithIncorrectExistingPassword(existingPassword, newPassword)
                .openDiaryPage();
    }
}
