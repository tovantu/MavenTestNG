package UnsplashTest;

import common.Common;
import common.ReadProperties;
import helpers.api.CollectionAPI;
import helpers.api.ProfileAPI;
import hooks.TestBase;
import hooks.WebPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class CollectionTest extends TestBase {

    @Test(groups = "High", priority = 1)
    public void removePhotosFromCollectionSuccessfullyTest(){
        String email = ReadProperties.getInstanceDataTest("account").getProperty("email");
        String password = ReadProperties.getInstanceDataTest("account").getProperty("password");
        String username = ReadProperties.getInstanceDataTest("account").getProperty("username");
        WebPage webPage = new WebPage();
        ArrayList<String> listImagesTitle;
        CollectionAPI collectionAPI = new CollectionAPI();
        ProfileAPI profileAPI = new ProfileAPI();
        profileAPI.updateUsername(username);
        String collectionId = "";
        String collectionTitle = "Collection Title " + String.valueOf(Common.ranDomNumber(1,99999));
        try{
            collectionId = collectionAPI.createPrivateCollection(collectionTitle);
            listImagesTitle = collectionAPI.addRandomPhotosIntoCollection(collectionId, 2);
            String titleFirstImage = listImagesTitle.get(0);
            webPage.loginPage.navigate();
            webPage.loginPage.login(email, password);
            webPage.collectionPage.navigate(username);
            webPage.collectionPage.clickOnCollection(collectionTitle);
            webPage.collectionPage.removeImageFromCollection(collectionTitle, titleFirstImage);
            webPage.collectionPage.navigate(username);
            Assert.assertTrue(webPage.collectionPage.checkNumberOfPhotosInCollection(collectionTitle,1), "Number of photos in the collection doesn't match");
            webPage.collectionPage.clickOnCollection(collectionTitle);
            Assert.assertTrue(webPage.collectionPage.checkImageNotExistInCollection(titleFirstImage), "Image removed still exist in the collection");
        }finally {
            //Clean up data
            collectionAPI.removeCollection(collectionId);
        }
    }
}
