package org.PageObjectModel;

import AbstractComponents.AbstractComponents;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProductCatalogue extends AbstractComponents {
    WebDriver driver;
    public ProductCatalogue(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @FindBy(css = ".mb-3")
    List<WebElement> products;
    @FindBy(css = ".ng-animating")
    WebElement loadingAnimate;
    @FindBy(css = "[routerlink*='cart']")

    WebElement productname;
    By addtocart = By.cssSelector(".btn.w-10.rounded");
    By productsTag = By.cssSelector("b");

    public void getProduct(String name){
        productname = products.stream().filter(s ->
                s.findElement(productsTag).getText().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
    public void addToCart() throws InterruptedException {
        productname.findElement(addtocart).click();
        waitInVisible();
        waitVisible(successMsg);
        successMessage();
        waitVisible(cartIcon);
    }
}
