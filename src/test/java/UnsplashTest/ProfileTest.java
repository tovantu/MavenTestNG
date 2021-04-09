package UnsplashTest;

import common.ReadProperties;
import helpers.api.ProfileAPI;
import hooks.TestBase;
import hooks.WebPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProfileTest extends TestBase {

    @Test(groups = "High", priority = 2)
    public void updateUsernameUrlInProfileTest(){
        String email = ReadProperties.getInstanceDataTest("account").getProperty("email");
        String password = ReadProperties.getInstanceDataTest("account").getProperty("password");
        String username = ReadProperties.getInstanceDataTest("account").getProperty("username");
        WebPage webPage = new WebPage();
        ProfileAPI profileAPI = new ProfileAPI();
        profileAPI.updateUsername(username);
        webPage.loginPage.navigate();
        webPage.loginPage.login(email, password);
        webPage.profilePage.navigate(username);
        webPage.profilePage.clickOnEditProfileButton();
        String newusername = "newtutovan";
        webPage.profilePage.inputUsername(newusername);
        webPage.profilePage.clickOnUpdateButton();
        webPage.profilePage.navigate(newusername);
        webPage.profilePage.clickOnEditProfileButton();
        Assert.assertEquals(webPage.profilePage.getValueUsernameInput(),newusername, "Username doesn't match");
    }
}
