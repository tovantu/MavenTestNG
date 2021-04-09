package UnsplashTest;

import common.ReadProperties;
import hooks.TestBase;
import hooks.WebPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FollowTest extends TestBase {

    @Test(groups = "High", priority = 1)
    public void followAPhotographerSuccessfullyTest(){
        String email = ReadProperties.getInstanceDataTest("account").getProperty("email");
        String password = ReadProperties.getInstanceDataTest("account").getProperty("password");
        WebPage webPage = new WebPage();
        webPage.loginPage.navigate();
        webPage.loginPage.login(email, password);
        webPage.homePage.clickOnFirstImage();
        webPage.homePage.hoverOnFirtImageUserIcon();
        webPage.homePage.clickOnFollowButton();
        Assert.assertTrue(webPage.homePage.followingButton.isDisplayed(), "The following button doesn't displayed");
        Assert.assertEquals(webPage.homePage.followingButton.getCssValue("background-color"),"rgba(238, 238, 238, 1)");
    }
}
