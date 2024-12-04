package SeleniumFramework.TestCases;

import SeleniumFramework.TestComponents.BaseTest;
import SeleniumFramework.TestComponents.RetryTest;
import org.PageObjectModel.Checkout;
import org.PageObjectModel.ProductCatalogue;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ErrorValidations extends BaseTest {
    String requiredproduct = "ADIDAS ORIGINAL";

    @Test(groups = {"ErrorHandling"},retryAnalyzer = RetryTest.class)
    public void loginErrorValidation() throws InterruptedException {
        login.pageLogin("venky.regadapa@outlook.com","Tester@1"); //giving wrong password
        Assert.assertEquals(login.loginErrormsg(),"Incorrect email or password.");
    }

    @Test
    public void productErrorValidation() throws InterruptedException {
        ProductCatalogue productCatalogue = login.pageLogin("venky.regadapa@outlook.com","Tester@123");
        productCatalogue.getProduct(requiredproduct);
        productCatalogue.addToCart();
        Checkout checkOut = productCatalogue.clickOnCartIcon(); //Drive object creation within the page object classes encapsulating it from test
        Assert.assertFalse(checkOut.compareAddedItems("ADIDAS ORG"));
    }
}
