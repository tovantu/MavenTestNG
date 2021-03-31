package pageobjects;

import hooks.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends PageBase {


    @FindBy(xpath = "/html/body/div[1]/div[3]/form/div[1]/div[1]/div[1]/div/div[2]/input")
    WebElement inputFiled;

    public void inputIntoSearchField(String text){
        waitForElementVisible(inputFiled);
        inputFiled.sendKeys(text);

    }

}
