import util.TestBase;
import org.testng.annotations.Test;


public class Test3 extends TestBase {

    @Test(groups = { "group1" })
    public void test333333() throws InterruptedException {
        webPage.homePage.inputIntoSearchField("Text Test");
    }

    @Test
    public void test333333333() throws InterruptedException {
        webPage.homePage.inputIntoSearchField("Text Test");
    }

}
