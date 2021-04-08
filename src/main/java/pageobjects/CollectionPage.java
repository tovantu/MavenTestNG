package pageobjects;

import common.EnvironmentConfig;
import hooks.PageBase;
import manage.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class CollectionPage extends PageBase {

    private static final Logger LOGGER = LogManager.getLogger(CollectionPage.class);

    String collection = "//div[contains(text(), '%s')]";
    String imageByTitle = "//img[@alt='%s']";
    String addToCollectionByTitle = "//h4[contains(text(), '%s')]";
    String numberOfPhotosInCollection = "//div[contains(text(), '%s')]/ancestor::div[@data-test='collection-feed-card']//div[contains(text(), '%s photo')]";

    @FindBy(xpath = "//button[@title = 'Add to collection'][@class='_2jBzD _3jtP1 _1CBrG _1WPby xLon9 _asSA hhSId _3dBbn']")
    private WebElement addToCollectionButton;

    public void navigate(String username){
        LOGGER.info("Navigate to collection page");
        String baseURL = EnvironmentConfig.getEnvironment();
        driver.navigate().to(baseURL + "@" + username + "/collections");
    }

    public void clickOnCollection(String title){
        LOGGER.info("Click on collection by title");
        String xpathCollection = String.format(collection, title);
        WebElement element = waitForElementVisibleByXpath(By.xpath(xpathCollection));
        element.click();
    }

    public void removeImageFromCollection(String collection,  String imageTitle){
        LOGGER.info("Remove image from collection by title");
        String imageXpathByTitle = String.format(imageByTitle, imageTitle);
        WebElement imageByTitleElement = waitForElementVisibleByXpath(By.xpath(imageXpathByTitle));
        imageByTitleElement.click();
        addToCollectionButton.click();
        String addToCollectionByTitleXpath = String.format(addToCollectionByTitle, collection);
        WebElement addToCollectionByTitleElement = waitForElementVisibleByXpath(By.xpath(addToCollectionByTitleXpath));
        addToCollectionByTitleElement.click();
    }

    public Boolean checkNumberOfPhotosInCollection(String title, int number){
        LOGGER.info("Check number of photos in a collection");
        String numberOfPhotosInCollectionXpath = String.format(numberOfPhotosInCollection, title, String.valueOf(number));
        if(waitForElementVisibleByXpath(By.xpath(numberOfPhotosInCollectionXpath)) != null){
            return true;
        }
        return false;
    }

    public Boolean checkImageNotExistInCollection(String title){
        LOGGER.info("Check image not exist in a collection");
        String imageXpathByTitle = String.format(imageByTitle, title);
        try{
            driver.findElement(By.xpath(imageXpathByTitle));
            return false;
        }catch (Exception exception){
            return true;
        }
    }
}

