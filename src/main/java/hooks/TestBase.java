package hooks;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import common.ReadProperties;
import manage.DriverManager;
import manage.ExtendManager;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pageobjects.HomePage;
import pageobjects.PageBase;
import pageobjects.WebPage;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class TestBase {
    public WebPage webPage;

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method){
        DriverManager.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        DriverManager.getDriver().manage().window().maximize();
        String URL = ReadProperties.getInstance("testsetting").getProperty("url");
        DriverManager.getDriver().get(URL);
        webPage = new WebPage();
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUp(ITestResult result) {
        DriverManager.resetDriver();
    }}
