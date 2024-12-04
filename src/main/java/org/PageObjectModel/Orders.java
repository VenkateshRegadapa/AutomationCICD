package org.PageObjectModel;

import AbstractComponents.AbstractComponents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class Orders extends AbstractComponents {
    WebDriver driver;
    public Orders(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @FindBy(css = "td:nth-child(3)")
    List<WebElement> ordersAdded;

    public boolean searchAddedOrders(String addedProduct){
        return ordersAdded.stream().anyMatch(s-> s.getText().equalsIgnoreCase(addedProduct));
    }
}
