package org.PageObjectModel;

import AbstractComponents.AbstractComponents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrderReview extends AbstractComponents {
    WebDriver driver;
    public OrderReview(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @FindBy(css = "[placeholder='Select Country']")
    private WebElement countrie; //To achieve encapsulation, hiding this element from other classes, those classes cannot access this directly
    @FindBy(css = ".list-group-item:nth-child(3)")
    private WebElement clickOnCntry; //private elements can be accessible within the class only
    @FindBy(css = ".action__submit")
    private WebElement submitButton;  //if other classes want to access, they can access through action methods only, ex: placeOrder() method and that method should be public

    public void selectCountrie(String cntri){
        countrie.sendKeys(cntri);
        actions.moveToElement(clickOnCntry).click().build().perform();
    }
    public OrderConfirmation placeOrder(){
        waitVisible(submitButton);
        actions.moveToElement(submitButton).click().build().perform();
        OrderConfirmation confirmation = new OrderConfirmation(driver);
        return confirmation;
    }


}
