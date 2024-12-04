package SeleniumFramework.TestComponents;

import Resources.ExtentReportTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class Listeners extends BaseTest implements ITestListener {
    //ITestListener gives inbuilt methods, need to implements ITestListener to the class to use its inbuilt methods
    //Below are the inbuilt methods from ITestListener interface, we can override and use customized code for our test

    ExtentReports reports = ExtentReportTest.config();
    //importing config() method from ExtentReportTest class, and it returns ExtentReports obj, storing that Object by creating obj for ExtentReports

    ExtentTest test;
    //by default after calling extentReports, it will return object and we can store that obj using ExtentTest, it will have inbuilt methods to use

    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>(); //will create Unique thread id for each test
    //Thread safe is a java class, if we do parallel tests execution, results may differ because all are using listeners at same time.
    //Thread safe will allocate memory for each entry as id and give proper results for each test

    @Override
    public void onTestStart(ITestResult result) {
        test = reports.createTest(result.getMethod().getMethodName());
        //Initializing extent report by calling with method createTest and printing method name by using results variable because it will store all the details of test
        extentTest.set(test); //it will set and pick particular thread which is executing and set map with results of that thread
    }
    @Override
    public void onTestSuccess(ITestResult result) {
        // test.log(Status.PASS,"Test Passed");
        extentTest.get().log(Status.PASS,"Test Passed");
        //Adding screenshot to the report, args(path for the screenshot & screenshot name as method name)
        extentTest.get().addScreenCaptureFromPath(captureScreenshot(result),result.getMethod().getMethodName());
    }
    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().fail(result.getThrowable()); //it will print error message after test fail
        extentTest.get().log(Status.FAIL,"Test Failed");
        extentTest.get().addScreenCaptureFromPath(captureScreenshot(result),result.getMethod().getMethodName());
    }
    @Override
    public void onTestSkipped(ITestResult result) {
    }
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }
    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        this.onTestFailure(result);
    }
    @Override
    public void onStart(ITestContext context) {
    }
    @Override
    public void onFinish(ITestContext context) {
        reports.flush();
    }

    //Own method to take screenshot
    public String captureScreenshot(ITestResult result) {
        //it will automatically suggest you with try catch for unwanted exceptions
        String path;
        try {
            path = takeScreenshot(result.getMethod().getMethodName(),driverGet(result));
            //calling takeScreenshot() method from base class to take screenshot, args(screenshot name as method name, current method driver from driverGet() method
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return path;
    }

    //for the above, driver will use to take screenshot in base class, but that driver has no life if we call takeScreenshot from this class
    //for that, We need to get driver details from results variable because it will store all the details of current test
    public WebDriver driverGet(ITestResult result){
        //it will automatically suggest you with try catch for unwanted exceptions
        WebDriver driver;
        try {
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return driver;
    }

}
