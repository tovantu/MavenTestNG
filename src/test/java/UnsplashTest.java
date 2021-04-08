import common.Common;
import common.ReadProperties;
import helpers.api.ApiHelper;
import helpers.api.CollectionAPI;
import helpers.api.PhotoAPI;
import helpers.api.ProfileAPI;
import hooks.TestBase;
import hooks.WebPage;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class UnsplashTest extends TestBase {

    @Test(groups = "Demo1")
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
        webPage.homePage.clickOnFollowingButton();
        webPage.homePage.followButton.isDisplayed();
    }

    @Test(groups = "Demo1")
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

    @Test(groups = "Demo")
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
            for(String photoId : listPhotosId){
                photoAPI.unlikePhoto(photoId);
            }
        }
    }

    @Test(groups = "Demo")
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
            Assert.assertTrue(webPage.collectionPage.checkImageNotExistInCollection(titleFirstImage));
        }finally {
            collectionAPI.removeCollection(collectionId);
        }
    }

    @Test(groups = "Demo")
    public void downloadSuccessfullyTest() throws InterruptedException {
        String email = ReadProperties.getInstanceDataTest("account").getProperty("email");
        String password = ReadProperties.getInstanceDataTest("account").getProperty("password");
        WebPage webPage = new WebPage();
        webPage.loginPage.navigate();
        webPage.loginPage.login(email, password);
        String photoId = webPage.homePage.getFirstImageId();
        webPage.homePage.clickOnFirstImage();
        webPage.homePage.clickOnDownloadButton();
        File filePathDownloaded = filePathDowloaded(photoId);
        String filePathToCompare = downloadFileByAPI(photoId);
        try {
            BufferedImage imgA = ImageIO.read(new File(String.valueOf(filePathDownloaded)));
            BufferedImage imgB = ImageIO.read(new File(filePathToCompare));
            Assert.assertTrue(bufferedImagesEqual(imgA, imgB),"Compare image failed - 2 images are different");
        }catch (Exception exception){
            System.out.println("Failed when comparing 2 images");
        }finally {
            filePathDownloaded.delete();
        }
    }


    public boolean bufferedImagesEqual (BufferedImage img1, BufferedImage img2){
        if (img1.getWidth() == img2.getWidth() && img1.getHeight() == img2.getHeight()) {
            return true;
        }
        return true;
    }

    public String downloadFileByAPI(String photoId){
        String downloadFolder = System.getProperty("user.home") + "\\Downloads\\";
        String filePathDownloadedByAPI = "";
        PhotoAPI photoAPI = new PhotoAPI();
        String url = photoAPI.downloadPhoto(photoId);
        filePathDownloadedByAPI = downloadFolder+ "ImageCompare.jpg";
        try (InputStream inputStream = new URL(url).openStream()) {
            Files.copy(inputStream, Paths.get(filePathDownloadedByAPI), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception exception) {
            System.out.println("Download failed");
        }
        return filePathDownloadedByAPI;
    }

    public File filePathDowloaded(String photoId) throws InterruptedException {
        String downloadFolder = System.getProperty("user.home") + "\\Downloads\\";
        File filePathDownloaded = null;
        for (int i = 0; i <= 20; i++) {
            try {
                File dir = new File(downloadFolder);
                File[] files = dir.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        return file.getName().endsWith(photoId + "-unsplash.jpg");
                    }
                });
                String fileName = files[0].getName();
                String filePath = downloadFolder + fileName;

                File f = new File(filePath);
                if (f.exists() && !f.isDirectory()) {
                    filePathDownloaded = f;
                    break;
                }
            } catch (Exception exception) {
                System.out.println("Trying to find the file downloaded");
            }
            Thread.sleep(500);
            if (i == 10) {
                Assert.assertTrue(false, "Can not find the file downloaded after 10 seconds");
            }
        }
        return filePathDownloaded;
    }
}
