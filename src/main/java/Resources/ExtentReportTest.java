package Resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportTest {
    public static ExtentReports config(){
        //2 reports classes will be there, 1. ExtentReport 2.ExtentSparkReports
        //we need to config ExtentSparkReports and attach those changes to main class ExtentReport
        String path = System.getProperty("user.dir")+"\\Reports\\Test1.html"; //it will create new Reports folder in project and save the report in html format
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(path);
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("Test Results");

        ExtentReports extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter); //attaching all changes to main class obj to apply in real report
        extentReports.setSystemInfo("SDET","Venky Regadapa");
        return extentReports;
    }
}
