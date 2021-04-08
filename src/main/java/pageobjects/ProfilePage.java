package pageobjects;

import common.EnvironmentConfig;
import hooks.PageBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProfilePage extends PageBase {

    private static final Logger LOGGER = LogManager.getLogger(ProfilePage.class);

    @FindBy(xpath="//a[@href='/account']")
    private WebElement editProfileButton;

    @FindBy(id = "user_username")
    public WebElement usernameInput;

    @FindBy(xpath = "//input[@value='Update account']")
    private WebElement updateButton;


    public void navigate(String username){
        LOGGER.info("Navigate to Profile page");
        String baseURL = EnvironmentConfig.getEnvironment();
        driver.navigate().to(baseURL + "@" + username);
    }

    public void clickOnEditProfileButton(){
        LOGGER.info("Click on edit profile button");
        waitForElementVisible(editProfileButton);
        editProfileButton.click();
    }

    public void inputUsername(String username){
        LOGGER.info("Input username: " + username);
        sendKeys(usernameInput, username);
    }

    public void clickOnUpdateButton(){
        LOGGER.info("Click on update button");
        updateButton.click();
    }

    public String getValueUsernameInput(){
        return getAttribute(usernameInput, "value");
    }

}
