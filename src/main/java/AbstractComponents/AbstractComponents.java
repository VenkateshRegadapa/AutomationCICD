package AbstractComponents;

import org.PageObjectModel.Checkout;
import org.PageObjectModel.Orders;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AbstractComponents {
    WebDriver driver;
    WebDriverWait wait;
    public Actions actions;
    public AbstractComponents(WebDriver driver){
      this.driver = driver;
      wait = new WebDriverWait(this.driver, Duration.ofSeconds(4));
      PageFactory.initElements(driver,this); //need to initialize the PageFactory with driver to tell FindBy annotates use this
        // we are initializing in parent class so can child classes can initiate, reducing code by initializing code in all classes

        actions = new Actions(this.driver);
    }

    @FindBy(css = "[routerlink*='cart']")
    public WebElement cartIcon;
    @FindBy(id = "toast-container")
    public WebElement successMsg;
    @FindBy(css = "[routerlink*='myorders']")
    public WebElement oredrsIcon;

    public void waitVisible(WebElement find){
        //wait.until(ExpectedConditions.visibilityOfElementLocated(find));
        wait.until(ExpectedConditions.visibilityOf(find));
    }
    public void waitInVisible() throws InterruptedException {
        Thread.sleep(2000);
        //wait.until(ExpectedConditions.invisibilityOf(ele)); //Website having some issues with elements which will take time to load
    }
    public void successMessage(){
        System.out.println(successMsg.getText());
    }
    public Checkout clickOnCartIcon(){
        actions.moveToElement(cartIcon).click().build().perform();
        return new Checkout(driver);
    }
    public Orders clickOnOrdersIcon(){
        actions.moveToElement(oredrsIcon).click().build().perform();
        return new Orders(driver);
    }
}
