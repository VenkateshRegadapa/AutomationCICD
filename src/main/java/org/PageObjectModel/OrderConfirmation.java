package org.PageObjectModel;

import AbstractComponents.AbstractComponents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrderConfirmation extends AbstractComponents {
    WebDriver driver;
    public OrderConfirmation(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @FindBy(className = "hero-primary")
    WebElement orderConfirmText;

    public String compareConfirmText(){
        String orderConfirm = orderConfirmText.getText();
        successMessage();
        return orderConfirm;
    }
}
