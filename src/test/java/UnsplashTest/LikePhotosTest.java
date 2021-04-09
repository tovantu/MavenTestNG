package UnsplashTest;

import common.ReadProperties;
import helpers.api.PhotoAPI;
import helpers.api.ProfileAPI;
import hooks.TestBase;
import hooks.WebPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class LikePhotosTest extends TestBase {

    @Test(groups = "High", priority = 1)
    public void listOfLikedPhotosTest(){
        WebPage webPage = new WebPage();
        String email = ReadProperties.getInstanceDataTest("account").getProperty("email");
        String password = ReadProperties.getInstanceDataTest("account").getProperty("password");
        String username = ReadProperties.getInstanceDataTest("account").getProperty("username");
        ArrayList<ArrayList<String>> listArray;
        ArrayList<String> listPhotosId = new ArrayList<>();
        ArrayList<String> listTitle;
        PhotoAPI photoAPI = new PhotoAPI();
        ProfileAPI profileAPI = new ProfileAPI();
        profileAPI.updateUsername(username);
        try{
            listArray = photoAPI.likeRandomPhotos(3);
            listPhotosId = listArray.get(0);
            listTitle = listArray.get(1);
            webPage.loginPage.navigate();
            webPage.loginPage.login(email, password);
            webPage.likePage.navigate(username);
            for(int i = 0; i < listTitle.size(); i ++){
                String title = listTitle.get(i);
                Assert.assertTrue(webPage.likePage.checkImageExist(listTitle.get(i)), String.format("The image: %s doesn't displayed on the UI", listTitle.get(i)) );
            }
        }finally {
            //Clean up data
            for(String photoId : listPhotosId){
                photoAPI.unlikePhoto(photoId);
            }
        }
    }
}
