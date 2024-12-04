package org.PageObjectModel;

import AbstractComponents.AbstractComponents;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Login extends AbstractComponents {

    WebDriver driver;

    public Login(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void pageLaunch() {
        driver.get("https://rahulshettyacademy.com/client/");
    }

    //Using PageFactory class to simplify code
    @FindBy(id = "userEmail")
    WebElement emailId;
    @FindBy(id = "userPassword")
    WebElement uPassword;
    @FindBy(id = "login")
    WebElement uLogin;

    @FindBy(xpath = "//div[@aria-label='Incorrect email or password.']")
    WebElement loginError;

    public ProductCatalogue pageLogin(String mailid, String pwd) {
        emailId.sendKeys(mailid);
        uPassword.sendKeys(pwd);
        uLogin.click();
        return new ProductCatalogue(driver);
    }
    public String loginErrormsg(){
        waitVisible(loginError);
        return loginError.getText();
    }
}
