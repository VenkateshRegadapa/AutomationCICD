package SeleniumFramework.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryTest implements IRetryAnalyzer {
    //this method will help to re-run if any case is failed
    int count =0;
    int run = 1;
    @Override
    public boolean retry(ITestResult iTestResult) {
        if(count<run){ // if method failed, it will come to retry method and condition passes because its failed 1 time and rerun will perform
            count++; //if that method fails again, it will come to check the condition, here its already rerun and we limited rerun as 1, so for condition fails and return false and 2nd rerun wont happen
            return true;
        }
        return false;
        //We need to provide to tell to that test case which will be fail sometimes and at that time rerun
        //For that method, we have to mention at test annotation like @Test(retryAnalyzer = RetryTest.class)
    }
}
