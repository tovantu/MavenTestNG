package pageobjects;

import common.EnvironmentConfig;
import hooks.PageBase;
import manage.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends PageBase {

    private static final Logger LOGGER = LogManager.getLogger(LoginPage.class);

    @FindBy(id = "user_email")
    private WebElement emailInput;

    @FindBy(id = "user_password")
    private WebElement passwordInput;

    @FindBy(xpath = "//input[@value='Login']")
    private WebElement loginButton;


    public void navigate(){
        LOGGER.info("Navigate to login page");
        String baseURL = EnvironmentConfig.getEnvironment();
        driver.navigate().to(baseURL + "login");
    }

    public void login(String email, String password){
        LOGGER.info("Login with email: " + email + " and password: " + password);
        sendKeys(emailInput, email);
        sendKeys(passwordInput, password);
        loginButton.click();
    }
}
