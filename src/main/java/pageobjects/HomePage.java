package pageobjects;

import common.Constant;
import common.EnvironmentConfig;
import hooks.PageBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends PageBase {
    private static Logger LOGGER = LogManager.getLogger(HomePage.class);

    @FindBy(xpath = "//div[@data-test='photos-route']//a//img[@role='presentation']")
    private WebElement firstImageUserIcon;

    @FindBy(xpath = "//figure[@itemprop='image']")
    private WebElement firstImage;

    @FindBy(xpath = "//figure//a[@itemprop='contentUrl']")
    private WebElement firstImageContentUrl;

    @FindBy(xpath = "//div[@data-test='photos-route']//a[@title = 'Download photo']")
    private WebElement downloadButton;

    @FindBy(xpath = "//button[@title='Follow']")
    public WebElement followButton;

    @FindBy(xpath = "//button[@title='Following']")
    public WebElement followingButton;

    public void navigate(){
        LOGGER.info("Navigate to home page");
        String baseURL = EnvironmentConfig.getEnvironment();
        driver.navigate().to(baseURL);
    }

    public void hoverOnFirtImageUserIcon(){
        LOGGER.info("Hover on first image user icon");
        waitForElementVisible(firstImageUserIcon);
        moveToElement(firstImageUserIcon);
    }

    public void clickOnFollowButton(){
        LOGGER.info("Click on follow button");
        try {
            waitForElementVisible(followButton, 3);
            followButton.click();
        }catch (Exception exception){
            clickOnFollowingButton();
            followButton.click();
        }
    }

    public void clickOnFollowingButton(){
        LOGGER.info("Click on following button");
        waitForElementVisible(followingButton);
        followingButton.click();
        waitForElementVisible(followButton);
    }

    public void clickOnFirstImage(){
        firstImage.click();
    }

    public void clickOnDownloadButton(){
        waitForElementVisible(downloadButton);
        downloadButton.click();
    }

    public String getFirstImageId(){
        String href = getAttribute(firstImageContentUrl, "href");
        return href.replace(Constant.DEV_URL +"photos/","");
    }

}
