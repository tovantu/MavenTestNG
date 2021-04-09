package hooks;


import manage.DriverManager;
import common.ReadProperties;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class PageBase {
    public int timeOut = Integer.parseInt(ReadProperties.getInstanceFromResources("testsetting").getProperty("timeOut"));
    public WebDriver driver;
    public WebDriverWait wait;

    public PageBase(){
        this.driver = DriverManager.getDriver();
        wait = new WebDriverWait(this.driver, timeOut);
        PageFactory.initElements(this.driver,this);
    }

    public void waitForElementVisible(WebElement element){
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementVisible(WebElement element, int timeOut){
        WebDriverWait wait = new WebDriverWait(this.driver, timeOut);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement waitForElementVisibleByXpath(By by){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void waitForTextToBePresentInElement(WebElement element, String text){
        wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    public void sendKeysByJavaScript(WebElement element, String text){
        waitForElementVisible(element);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(String.format("arguments[0].value='%s';", text), element);
    }

    public void sendKeysByAction(String text){
        Actions action = new Actions(driver);
        action.sendKeys(text).build().perform();
    }

    public void moveToElement(WebElement element){
        Actions action = new Actions(driver);
        action.moveToElement(element).build().perform();
    }

    public void scrollElementIntoView(WebElement element){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void sendKeys(WebElement element, String text){
        waitForElementVisible(element);
        element.clear();
        element.sendKeys(text);
    }

    public String getAttribute(WebElement element, String attribute){
        return element.getAttribute(attribute);
    }
}
