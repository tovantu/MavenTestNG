package pageobjects;


import manage.DriverManager;
import common.ReadProperties;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class PageBase {
    public int timeOut = Integer.parseInt(ReadProperties.getInstance("testsetting").getProperty("timeOut"));
    public WebDriver driver = DriverManager.getDriver();
    public WebDriverWait wait = new WebDriverWait(driver, timeOut);

    public void waitForElementVisible(WebElement element){
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void sendKeysByJavaScript(WebElement element, String text){
        waitForElementVisible(element);
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript(String.format("arguments[0].value='%s';", text), element);
    }
    public void sendKeysByAction(String text){
        Actions action = new Actions(driver);
        action.sendKeys(text).build().perform();
    }
}
