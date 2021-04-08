package manage;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestListener implements ITestListener {
    private ExtentTest extentTest;
    private List<ExtentTest> skipTestsToRemove = new ArrayList<>();

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        System.out.println("I am in onStart method " + iTestContext.getName());
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        System.out.println("I am in onFinish method " + iTestContext.getName());
        //Do tier down operations for extentreports reporting!

        Iterator<ITestResult> failedTestCases =iTestContext.getFailedTests().getAllResults().iterator();
        while (failedTestCases.hasNext()) {
            System.out.println("failedTestCases");
            ITestResult failedTestCase = failedTestCases.next();
            ITestNGMethod method = failedTestCase.getMethod();
            if (iTestContext.getFailedTests().getResults(method).size() > 1) {
                System.out.println("failed test case remove as dup:" + failedTestCase.getTestClass().toString());
                failedTestCases.remove();
            } else {
                if (iTestContext.getPassedTests().getResults(method).size() > 0) {
                    System.out.println("failed test case remove as pass retry:" + failedTestCase.getTestClass().toString());
                    failedTestCases.remove();
                }
            }
        }

        //Remove skip test when retry
        Iterator<ITestResult> skippedTestCases = iTestContext.getSkippedTests().getAllResults().iterator();
        while (skippedTestCases.hasNext()) {
            ITestResult skippedTestCase = skippedTestCases.next();
            ITestNGMethod method = skippedTestCase.getMethod();
            if (iTestContext.getFailedTests().getResults(method).size() > 0 || iTestContext.getPassedTests().getResults(method).size() > 0) {

                String methodName = method.getMethodName();
                for(ExtentTest exTest : skipTestsToRemove){
                    String name = exTest.getModel().getName();
                    if(name.equalsIgnoreCase(methodName)){
                        ExtendManager.getInstance().removeTest(exTest);
                    }
                }
            }
        }

        ExtendManager.getInstance().flush();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        System.out.println("I am in onTestStart method " + getTestMethodName(iTestResult) + " start");
        extentTest = ExtendManager.getInstance().createTest(getTestMethodName(iTestResult));
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("I am in onTestSuccess method " + getTestMethodName(iTestResult) + " succeed");
        //ExtentReports log operation for passed tests.
        extentTest.log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        System.out.println("I am in onTestFailure method " + getTestMethodName(iTestResult) + " failed");
        ITestContext context = iTestResult.getTestContext();
        WebDriver driver = (WebDriver) context.getAttribute("WebDriver");
        String base64Screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
        try {
            extentTest.log(Status.FAIL, "Image", MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
        } catch (IOException e) {
            e.printStackTrace();
        }
        extentTest.log(Status.FAIL, iTestResult.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("I am in onTestSkipped method " + getTestMethodName(iTestResult) + " skipped");
        //ExtentReports log operation for skipped tests.
        extentTest.log(Status.SKIP, "Test Skipped");
        skipTestsToRemove.add(extentTest);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
    }
}
