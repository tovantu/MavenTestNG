package hooks;

import common.EnvironmentConfig;
import common.ReadProperties;
import common.Utilities;
import manage.DriverManager;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class TestBase {
    public WebPage webPage;

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method){
        DriverManager.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        DriverManager.getDriver().manage().window().maximize();
        DriverManager.getDriver().get(EnvironmentConfig.getEnvironment());
        webPage = new WebPage();
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUp(ITestResult result) {
        DriverManager.resetDriver();
    }}
