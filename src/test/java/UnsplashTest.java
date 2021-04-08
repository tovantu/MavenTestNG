import common.Common;
import helpers.api.ApiHelper;
import hooks.TestBase;
import hooks.WebPage;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UnsplashTest extends TestBase {

    @Test(groups = "Demo1")
    public void followAPhotographerSuccessfullyTest(){
        WebPage webPage = new WebPage();
        webPage.loginPage.navigate();
        webPage.loginPage.login("togiadapda4@gmail.com", "Tuto.123");
        webPage.homePage.hoverOnFirtImageUserIcon();
        webPage.homePage.clickOnFollowButton();
        Assert.assertTrue(webPage.homePage.followingButton.isDisplayed(), "The following button doesn't displayed");
        Assert.assertEquals(webPage.homePage.followingButton.getCssValue("background-color"),"rgba(238, 238, 238, 1)");
        webPage.homePage.clickOnFollowingButton();
        webPage.homePage.followButton.isDisplayed();
    }

    @Test(groups = "Demo2")
    public void updateUsernameUrlInProfileTest(){
        WebPage webPage = new WebPage();
        ApiHelper apiHelper = new ApiHelper();
        Map<String, String> params = new HashMap<>();
        params.put("username", "tutovan");
        Response response = apiHelper.updateUserProfile("/me", params);
        Assert.assertEquals(response.getStatusCode(),200, "Update username failed");

        webPage.loginPage.navigate();
        webPage.loginPage.login("togiadapda4@gmail.com", "Tuto.123");
        webPage.profilePage.navigate("tutovan");
        webPage.profilePage.clickOnEditProfileButton();
        String username = "newtutovan";
        webPage.profilePage.inputUsername(username);
        webPage.profilePage.clickOnUpdateButton();
        webPage.profilePage.navigate(username);
        webPage.profilePage.clickOnEditProfileButton();
        Assert.assertEquals(webPage.profilePage.getValueUsernameInput(),username, "Username doesn't match");
    }

    @Test(groups = "Demo3")
    public void listOfLikedPhotosTest(){
        WebPage webPage = new WebPage();
        ArrayList<ArrayList<String>> listArray;
        ArrayList<String> listPhotosId = new ArrayList<>();
        ArrayList<String> listTitle;
        ApiHelper apiHelper = new ApiHelper();
        try{
            listArray = apiHelper.likeRandomPhotos(3);
            listPhotosId = listArray.get(0);
            listTitle = listArray.get(1);
            Map<String, String> params = new HashMap<>();
            params.put("username", "tutovan");
            Response response = apiHelper.updateUserProfile("/me", params);
            Assert.assertEquals(response.getStatusCode(),200, "Update username failed");
            webPage.loginPage.navigate();
            webPage.loginPage.login("togiadapda4@gmail.com", "Tuto.123");
            webPage.likePage.navigate("tutovan");
            for(int i = 0; i < listTitle.size(); i ++){
               String title = listTitle.get(i);
               Assert.assertTrue(webPage.likePage.checkImageExist(listTitle.get(i)), String.format("The image: %s doesn't displayed on the UI", listTitle.get(i)) );
            }

        }finally {
            for(String photoId : listPhotosId){
                apiHelper.unlikePhoto(photoId);
            }
        }
    }

    @Test(groups = "Demo3")
    public void removePhotosFromCollectionSuccessfullyTest(){
        WebPage webPage = new WebPage();
        ArrayList<String> listImagesTitle;
        ApiHelper apiHelper = new ApiHelper();
        String collectionId = "";
        String collectionTitle = "Collection Title " + String.valueOf(Common.ranDomNumber(1,99999));
        try{
            Response response = apiHelper.createPrivateCollection(collectionTitle);
            Assert.assertEquals(response.getStatusCode(),201, "Create collection failed");
            String jsonString = response.body().asString();
            JsonPath jsonPath = new JsonPath(jsonString);
            collectionId = jsonPath.get("id").toString();

            listImagesTitle = apiHelper.addRandomPhotosIntoCollection(collectionId, 2);
            String titleFirstImage = listImagesTitle.get(0);

            Map<String, String> params = new HashMap<>();
            params.put("username", "tutovan");
            Response responseUpdateProfile = apiHelper.updateUserProfile("/me", params);
            Assert.assertEquals(responseUpdateProfile.getStatusCode(),200, "Update username failed");

            webPage.loginPage.navigate();
            webPage.loginPage.login("togiadapda4@gmail.com", "Tuto.123");
            webPage.collectionPage.navigate("tutovan");
            webPage.collectionPage.clickOnCollection(collectionTitle);
            webPage.collectionPage.removeImageFromCollection(collectionTitle, titleFirstImage);
            webPage.collectionPage.navigate("tutovan");
            Assert.assertTrue(webPage.collectionPage.checkNumberOfPhotosInCollection(collectionTitle,1), "Number of photos in the collection doesn't match");
            webPage.collectionPage.clickOnCollection(collectionTitle);
            Assert.assertTrue(webPage.collectionPage.checkImageNotExistInCollection(titleFirstImage));

        }finally {
            apiHelper.removeCollection(collectionId);
        }
    }
}
