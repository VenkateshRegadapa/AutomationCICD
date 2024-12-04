package org.PageObjectModel;

import AbstractComponents.AbstractComponents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class Checkout extends AbstractComponents {
    WebDriver driver;
    public Checkout(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @FindBy(css = ".cartSection h3")
    List<WebElement> addedItems;
    @FindBy(xpath = "//li[@class='totalRow']/button")
    WebElement checkout;

    public boolean compareAddedItems(String addedProduct){
        return addedItems.stream().anyMatch(s-> s.getText().equalsIgnoreCase(addedProduct));
    }
    public OrderReview clickOnCheckout(){
        //waitVisible(checkout);
        actions.moveToElement(checkout).click().build().perform();
        //checkout.click();
        return new OrderReview(driver);
    }
}
