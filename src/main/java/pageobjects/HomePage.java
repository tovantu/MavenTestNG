package pageobjects;

import common.EnvironmentConfig;
import hooks.PageBase;
import manage.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class HomePage extends PageBase {
    private static Logger LOGGER = LogManager.getLogger(HomePage.class);

    @FindBy(xpath = "//figure[@itemprop='image']//img[@role='presentation']")
    private WebElement firstImageUserIcon;

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
        scrollElementIntoView(firstImageUserIcon);
        new Actions(driver).sendKeys(Keys.PAGE_UP).build().perform();
        waitForElementVisible(firstImageUserIcon);
        moveToElement(firstImageUserIcon);
    }

    public void clickOnFollowButton(){
        LOGGER.info("Click on follow button");
        waitForElementVisible(followButton);
        followButton.click();
    }

    public void clickOnFollowingButton(){
        LOGGER.info("Click on following button");
        waitForElementVisible(followingButton);
        followingButton.click();
        waitForElementVisible(followButton);
    }


}
