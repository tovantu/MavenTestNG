package manage;

import common.Common;
import common.EnvironmentConfig;
import common.Utilities;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DriverManager {

    private static ThreadLocal<DriverManager> driverManager = ThreadLocal.withInitial(()->null);

    public WebDriver driver ;


    public DriverManager(){
        String browser = EnvironmentConfig.getBrowser();
        if(browser.equalsIgnoreCase("chrome")){
//            System.setProperty("webdriver.chrome.driver", Utilities.getFilePathByOs("\\src\\main\\resources\\drivers\\chromedriver.exe"));
            WebDriverManager.chromedriver().setup();
            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("download.default_directory",System.getProperty("user.dir") + File.separator + "downloads");
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("prefs", prefs);
            driver = new ChromeDriver(options);
        }else if(browser.equalsIgnoreCase("firefox")){
//            System.setProperty("webdriver.gecko.driver", Utilities.getFilePathByOs("\\src\\main\\resources\\drivers\\geckodriver.exe"));
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
    }

    public static DriverManager createInstance(){
        if (driverManager.get() != null){
            return driverManager.get();
        }
        driverManager.set(new DriverManager());
        return driverManager.get();
    }

    public static WebDriver getDriver(){
        return createInstance().driver;
    }

    public static void resetDriver(){
        getDriver().quit();
        driverManager.remove();
    }


}
