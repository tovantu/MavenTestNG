package util;

import driver.DriverManager;
import uihelper.ReadProperties;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class TestBase {
    public WebPage webPage;
    private ExtentTest extentTest;

    @BeforeMethod
    public void setUp(Method method){
        extentTest = ExtendManager.getInstance().createTest(method.getName());
        DriverManager.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        DriverManager.getDriver().manage().window().maximize();
        String URL = ReadProperties.getInstance("testsetting").getProperty("url");
        DriverManager.getDriver().get(URL);
        webPage = new WebPage();
    }

    @AfterMethod
    public void cleanUp(ITestResult result) throws IOException {
        if(result.getStatus() == ITestResult.FAILURE){
            String base64Screenshot = ((TakesScreenshot)DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64);
            extentTest.log(Status.FAIL, "Image", MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
            extentTest.log(Status.FAIL, result.getThrowable());

        }else if(result.getStatus() == ITestResult.SKIP){
            extentTest.log(Status.SKIP, MarkupHelper.createLabel("Skip", ExtentColor.ORANGE));
        }
        
        DriverManager.resetDriver();
    }

    @AfterSuite
    public void afterSuite(){
        ExtendManager.getInstance().flush();
    }
}
