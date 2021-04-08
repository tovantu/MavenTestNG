import hooks.TestBase;
import hooks.WebPage;
import manage.DriverManager;
import org.testng.annotations.Test;

public class ParallelTest extends TestBase {
    @Test(groups = "De")
    public void tinhteLoginTest(){
        WebPage webPage = new WebPage();
        webPage.tinhTeLoginPage.navigate();
        webPage.tinhTeLoginPage.login("togiadapda4@gmail.com", "Dancaydat1!");
    }
    @Test(groups = "De")
    public void tinhteLoginTestParallel(){
        WebPage webPage = new WebPage();
        webPage.tinhTeLoginPage.navigate();
        webPage.tinhTeLoginPage.login("togiadapda4@gmail.com", "Dancaydat1!");
    }
}
