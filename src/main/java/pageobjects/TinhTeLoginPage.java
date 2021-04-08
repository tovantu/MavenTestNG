package pageobjects;

import hooks.PageBase;
import manage.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TinhTeLoginPage extends PageBase {

    @FindBy(xpath = "//form[@id='pageLogin']//input[@id='ctrl_pageLogin_login']")
    private WebElement emailInput;

    @FindBy(xpath = "//form[@id='pageLogin']//input[@id='ctrl_pageLogin_password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//*[@id=\"pageLogin\"]/dl[3]/dd/input")
    private WebElement loginButton;

    public void navigate(){
        driver.navigate().to("https://tinhte.vn/login/");
    }

    public void login(String email, String password){
        System.out.println("----------------Driver" + driver);
        sendKeys(emailInput, email);
        sendKeys(passwordInput, password);
        loginButton.click();
    }

}
