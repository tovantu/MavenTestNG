package pageobjects;

import util.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends PageBase {

    public HomePage(){
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//*[@id=\"__next\"]/div/div[1]/div/div[2]/div[1]/div[2]/div[3]/div/div/div[2]/div[1]/div/div/div[1]/input")
    WebElement inputFiled;

    public void inputIntoSearchField(String text){
        inputFiled.sendKeys(text);
    }

}
