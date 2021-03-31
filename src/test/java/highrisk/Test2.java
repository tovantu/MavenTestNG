package highrisk;

import hooks.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;


public class Test2 extends TestBase {

    @Test(groups = { "group1" })
    public void test222222() throws InterruptedException {
        webPage.homePage.inputIntoSearchField("Text Test");
        Assert.assertTrue(false);
    }

    @Test(groups = {"group2"})
    public void test22222222222() throws InterruptedException {
        webPage.homePage.inputIntoSearchField("Text Test");
    }

}
