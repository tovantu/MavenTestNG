import helpers.EmployeeHelper;
import hooks.TestBase;
import models.Employee;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UiTest extends TestBase {
    @Test(groups = {"group1"})
    public void test1() throws InterruptedException {
        webPage.homePage.inputIntoSearchField("Text Test");
    }

    @Test
    public void test111111111() throws InterruptedException {
        webPage.homePage.inputIntoSearchField("Text Test");
        Assert.assertTrue(false);
    }

    @Test(groups = {"Demo"})
    public void testHelper() throws InterruptedException {
        EmployeeHelper employeeHelper = new EmployeeHelper();
        Employee employee = new Employee("Name", "Username", "Password");
        employeeHelper.createEmployee(employee);
        Thread.sleep(2000);
        Assert.assertTrue(true);
    }
}
