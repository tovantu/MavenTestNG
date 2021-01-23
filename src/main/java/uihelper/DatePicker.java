package uihelper;


import driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class DatePicker {
    private int timeOut = 10;
    public void selectDate(String date) throws InterruptedException {

        String[] array = date.split(" ");
        String day = array[0];
        String month = array[1];
        int monthDiff = getCurrentMonth() - Integer.parseInt(month);
        String year = array[2];
        int yearDiff = getCurrentYear() - Integer.parseInt(year);

        String leftButtonOfMonthXpath = "//div[contains(@class,'q-date__arrow')][1]//button";
        String rightButtonOfMonthXpath = "//div[contains(@class,'q-date__arrow')][2]//button";
        String leftButtonOfYearXpath = "//div[contains(@class,'q-date__arrow')][3]//button";
        String rightButtonOfYearXpath = "//div[contains(@class,'q-date__arrow')][4]//button";
        String dayXpath = String.format("//span[text()='%s']/ancestor::button", day);
        String monthXpath = "/html/body/div[6]/div[2]/div/div[2]/div/div/div[1]/div[2]/div/button/span[2]/span/span";
        String yearXpath = "/html/body/div[6]/div[2]/div/div[2]/div/div/div[1]/div[5]/div/button/span[2]/span/span";


        if(yearDiff > 0){
            clickOnArrowButton(leftButtonOfYearXpath, yearDiff, year);
        }else if(yearDiff < 0){
            clickOnArrowButton(rightButtonOfYearXpath, yearDiff, year);
        }


        String monthByText = "";
        switch(Integer.parseInt(month)){
            case 1:  monthByText = "January";       break;
            case 2:  monthByText = "February";      break;
            case 3:  monthByText = "March";         break;
            case 4:  monthByText = "April";         break;
            case 5:  monthByText = "May";           break;
            case 6:  monthByText = "June";          break;
            case 7:  monthByText = "July";          break;
            case 8:  monthByText = "August";        break;
            case 9:  monthByText = "September";     break;
            case 10: monthByText = "October";       break;
            case 11: monthByText = "November";      break;
            case 12: monthByText = "December";      break;
            default: monthByText = "Invalid month"; break;
        }
        if(monthDiff > 0){
            clickOnArrowButton(leftButtonOfMonthXpath, monthDiff, monthByText);
        }else if(monthDiff < 0){
            clickOnArrowButton(rightButtonOfMonthXpath, monthDiff, monthByText);
        }

        try{
            waitForElementClickAbleByXpath(dayXpath).click();
        }catch(Exception e){
            Thread.sleep(500);
            waitForElementVisibleByXpath(dayXpath).click();
        }

    }

    private void clickOnArrowButton(String xpath, int diff, String type) throws InterruptedException {
        for(int i = 0; i < Math.abs(diff); i++){
            try{
                waitForElementClickAbleByXpath(xpath).click();
            }catch (Exception e){
                waitForElementClickAbleByXpath(xpath).click();
            }
        }
    }

    public WebElement waitForElementClickAbleByXpath(String xpath){
        WebElement element = fluentWaitByXpath(DriverManager.getDriver(), xpath);
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), this.timeOut);
        wait.until(ExpectedConditions.elementToBeClickable(element));

        return element;
    }

    public WebElement waitForElementVisibleByXpath(String xpath){
        WebElement element = fluentWaitByXpath(DriverManager.getDriver(), xpath);
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), this.timeOut);
        wait.until(ExpectedConditions.visibilityOf(element));

        return element;
    }
    public WebElement fluentWaitByXpath(WebDriver driver, String xpath){
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(timeOut, TimeUnit.SECONDS)
                .pollingEvery(1, TimeUnit.SECONDS)
                .ignoring(StaleElementReferenceException.class);

        WebElement element = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.xpath(xpath));
            }
        });
        return element;
    }

    public int getCurrentMonth(){
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        int month = cal.get(Calendar.MONTH);
        return month;
    }

    public int getCurrentYear(){
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        int month = cal.get(Calendar.YEAR);
        return month;
    }

}
