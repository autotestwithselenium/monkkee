package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebElement;

import java.util.List;

@Log4j2
public class TagsPage extends BasePage {

    public TagsPage(WebDriver driver) {
        super(driver);
    }

    private By tagName = By.xpath("//tr[@ng-repeat='tag in tags']/td[1]");
    private By deleteTagButton = By.xpath("//a[@class='btn btn-primary' and @ng-click='deleteTag(tag)']");
    private By backToEntriesIcon = By.xpath("//a[@title='Back to overview']");
    private By animationPicture = By.xpath("//img[@class='animation']");
    private By addEntryIcon = By.xpath("//*[@title='Create an entry']");


    public boolean checkTagPresenseInList(String nameOfTag) {
        boolean tagExistsInList = false;
        int numberOfTagInList = 0;
        log.info("tagName: " + tagName);

        List<WebElement> tagNames = driver.findElements(tagName);
        for (WebElement name : tagNames) {
            if (name.getText().equals(nameOfTag)) {
                tagExistsInList = true;
                break;
            }
            numberOfTagInList++;
        }
        log.info("numberOfTagInList: " + numberOfTagInList);
        return tagExistsInList;
    }

    public TagsPage deleteTag(String nameOfTag){
        if (checkTagPresenseInList(nameOfTag)){
        int numberOfTagInList = 0;

        log.info("tagName: " + tagName);
        List<WebElement> tagNames = driver.findElements(tagName);
        for (WebElement name : tagNames) {
            if (name.getText().equals(nameOfTag)) {
                break;
            }
            numberOfTagInList++;
        }

        driver.findElements(deleteTagButton).get(numberOfTagInList).click();
        Alert alert = driver.switchTo().alert();
        alert.accept();}
        return this;
    }

    public DiaryPage clickBackToEntriesIcon() {
        driver.findElement(backToEntriesIcon).click();
        animationWait(animationPicture);
        waitPresenceOfElementLocated(addEntryIcon);
        return new DiaryPage(driver);
    }
}