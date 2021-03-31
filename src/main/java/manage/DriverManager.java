package manage;

import common.Common;
import common.EnvironmentConfig;
import common.Utilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverManager {

    private static ThreadLocal<DriverManager> driverManager = ThreadLocal.withInitial(()-> null);

    public WebDriver driver ;


    public DriverManager(){
        String browser = EnvironmentConfig.getBrowser();
        if(browser.equalsIgnoreCase("chrome")){
            System.setProperty("webdriver.chrome.driver", Utilities.getFilePathByOs("\\src\\main\\resources\\drivers\\chromedriver.exe"));
            driver = new ChromeDriver();
        }else if(browser.equalsIgnoreCase("firefox")){
            System.setProperty("webdriver.gecko.driver", Utilities.getFilePathByOs("\\src\\main\\resources\\drivers\\geckodriver.exe"));
            driver = new FirefoxDriver();
        }
    }

    public static DriverManager createInstance(){
        if (driverManager.get() != null) return driverManager.get();
        driverManager.set(new DriverManager());
        return driverManager.get();
    }

    public static WebDriver getDriver(){
        return createInstance().driver;
    }


    public static void resetDriver(){
        getDriver().quit();
        driverManager.set(null);
    }
}
