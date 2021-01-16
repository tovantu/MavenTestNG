import Util.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;


public class Test2 extends TestBase {

    @Test
    public void test222222() throws InterruptedException {
        webPage.homePage.inputIntoSearchField("Text Test");
        Assert.assertTrue(false);
    }

    @Test
    public void test22222222222() throws InterruptedException {
        webPage.homePage.inputIntoSearchField("Text Test");
    }

}
