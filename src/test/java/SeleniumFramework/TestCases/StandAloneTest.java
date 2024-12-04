package SeleniumFramework.TestCases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class StandAloneTest {
    public static void main(String[] args) throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));

        driver.get("https://rahulshettyacademy.com/client/");

        String requiredproduct = "ADIDAS ORIGINAL";
        String countrie = "new";

        driver.findElement(By.id("userEmail")).sendKeys("venky.regadapa@outlook.com");
        driver.findElement(By.id("userPassword")).sendKeys("Tester@123");
        driver.findElement(By.id("login")).click();
        List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

        WebElement productname = products.stream().filter(s ->
                s.findElement(By.cssSelector("b")).getText().equalsIgnoreCase(requiredproduct)).findFirst().orElse(null);
        productname.findElement(By.cssSelector(".btn.w-10.rounded")).click();

        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating")))); //waiting until page loading animation got disappear
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-container"))); //waiting till added cart message get displayed
        System.out.println(driver.findElement(By.id("toast-container")).getText());

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[routerlink*='cart']"))); //waiting till cart option get displayed and click

        Actions actions = new Actions(driver);
        //driver.findElement(By.cssSelector("[routerlink*='cart']")).click(); //element click intercepted exception when element is unable to click, by using actions we can achieve
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[routerlink*='cart']")));
        actions.moveToElement(driver.findElement(By.cssSelector("[routerlink*='cart']"))).click().build().perform();

        List<WebElement> addedItems = driver.findElements(By.cssSelector(".cartSection h3"));
        boolean matchedResult = addedItems.stream().anyMatch(s-> s.getText().equalsIgnoreCase(requiredproduct));
        Assert.assertTrue(matchedResult);

        driver.findElement(By.xpath("//li[@class='totalRow']/button")).click();

        driver.findElement(By.cssSelector("[placeholder='Select Country']")).sendKeys("India");
        actions.moveToElement(driver.findElement(By.cssSelector(".list-group-item:nth-child(3)"))).click().build().perform();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".action__submit")));
        actions.moveToElement(driver.findElement(By.cssSelector(".action__submit"))).click().build().perform();
        
        String oredrConfirm = driver.findElement(By.className("hero-primary")).getText();
        Assert.assertTrue(oredrConfirm.equalsIgnoreCase("Thankyou for the order."));
        System.out.println(driver.findElement(By.id("toast-container")).getText());

    }
}
