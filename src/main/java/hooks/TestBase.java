package hooks;

import common.EnvironmentConfig;
import common.ReadPropertiesSingleTon;
import manage.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

public class TestBase {

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method){
        WebDriver driver = DriverManager.getDriver();
        driver.manage().window().maximize();
        driver.get(EnvironmentConfig.getEnvironment());
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUp(ITestResult result) {
        DriverManager.resetDriver();
    }}
