package tests;

import org.testng.annotations.Test;
import pages.DiaryPage;
import lombok.extern.log4j.Log4j2;
import pages.SettingsPage;


@Log4j2
public class SettingsTest extends BaseTest {

    @Test
    public void changeLanguageSettingsTest() {
        DiaryPage diaryPage = new DiaryPage(driver);
        diaryPage
                .openSettings()
                .chooseLanguage("Français", "Modifications enregistrées")
                .chooseLanguage("Portuguese", "Seu idioma foi alterado com sucesso")
                .chooseLanguage("Deutsch", "Deine Spracheinstellung wurde erfolgreich geändert")
                .chooseLanguage("English", "Your language has been changed successfully")
                .openEntries();

    }


}
