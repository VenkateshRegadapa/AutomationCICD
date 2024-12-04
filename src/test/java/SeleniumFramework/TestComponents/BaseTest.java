package SeleniumFramework.TestComponents;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.PageObjectModel.Login;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class BaseTest {
    public WebDriver driver;
    public Login login;

    @BeforeMethod(alwaysRun = true)
    public void launchBrowser() throws IOException {
        Properties properties = new Properties(); //to use methods in properties class, need to create .properties file before using it
        FileInputStream input = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//Resources//GlobalData.properties");
        //need to give .properties file path to input stream to read data and setting property as user.dir instead of C:\Users\venky\IdeaProjects\SeleniumFrameworks to avoid more code
        properties.load(input);

        //Using ternary operator to read the browser value from cmd, if value is there it will execute first, if not execute last one which is from Json file
        String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : properties.getProperty("browser");
        //String browserName = properties.getProperty("browser");

        if(browserName.contains("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--incognito");
            if(browserName.contains("headless")) {
                options.addArguments("headless"); //to run execution in headless mode, it wont showup chrome but backend it will do execution
            }
                driver = new ChromeDriver(options);
            driver.manage().window().setSize(new Dimension(1440,900)); //to set window in full screen mode using dimensions if some elements are not visible
        }
        else if (browserName.equalsIgnoreCase("edge")) {
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--incognito");
            driver = new EdgeDriver(options);
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
        login = new Login(driver);
        login.pageLaunch();
    }
    @AfterMethod(alwaysRun = true)
    public void closeBrowser(){
        driver.close();
    }

    public List<HashMap<String,String>> getJsonDataToMap(String filePath) throws IOException {
        //Read json file and store it in string
        String jsonData = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
        //mapping string to Hashmap by using - Jackson databind dependency
        ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String,String>> dataList = mapper.readValue(jsonData, new TypeReference<List<HashMap<String,String>>>(){});
        //above line will store the data in a Hashmap list, ex: {map1,map2}
        return dataList;
    }

    public String takeScreenshot(String testCaseName,WebDriver driver1) throws IOException {
        TakesScreenshot screenshot = (TakesScreenshot) driver1; //casting driver to screenshot
        File source = screenshot.getScreenshotAs(OutputType.FILE); //taking screenshot in the output type as File/ in file format
        File filePath = new File(System.getProperty("user.dir")+"//Reports//"+testCaseName+".png"); //setting path to store for screenshot
        FileUtils.copyFile(source,filePath);
        return System.getProperty("user.dir")+"//Reports//"+testCaseName+".png";
    }
}
