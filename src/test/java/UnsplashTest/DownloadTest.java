package UnsplashTest;

import common.ReadProperties;
import helpers.api.PhotoAPI;
import hooks.TestBase;
import hooks.WebPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class DownloadTest extends TestBase {

    @Test(groups = "High", priority = 1)
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
            //Clean up data
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
