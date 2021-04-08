package pageobjects;

import common.EnvironmentConfig;
import hooks.PageBase;
import manage.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LikePage extends PageBase {

    private static Logger LOGGER = LogManager.getLogger(LikePage.class);

    String imageWithTitle = "//img[@alt='%s']";

    public void navigate(String username){
        LOGGER.info("Navigate to like page");
        String baseURL = EnvironmentConfig.getEnvironment();
        driver.navigate().to(baseURL + "@" + username + "/likes");
    }

    public Boolean checkImageExist(String title){
        LOGGER.info("Check image exist in like page");
        WebElement element = waitForElementVisibleByXpath(By.xpath(String.format(imageWithTitle, title)));
        if(element != null){
            return true;
        }
        return false;
    }
}
