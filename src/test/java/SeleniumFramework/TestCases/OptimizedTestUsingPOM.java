package SeleniumFramework.TestCases;

import SeleniumFramework.TestComponents.BaseTest;
import org.PageObjectModel.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class OptimizedTestUsingPOM extends BaseTest {
    String requiredproduct = "ADIDAS ORIGINAL";
    @Test
    public void submitOrder() throws InterruptedException, IOException {
        ProductCatalogue productCatalogue = login.pageLogin("venky.regadapa@outlook.com","Tester@123");
          //if we are sure what will be the next page then we can create object for next class and return it while calling last method of the page
          //and can capture it in an variable and we can use it, applies same for upcoming classes
        productCatalogue.getProduct(requiredproduct);
        productCatalogue.addToCart();
        Checkout checkOut = productCatalogue.clickOnCartIcon(); //Drive object creation within the page object classes encapsulating it from test
        Assert.assertTrue(checkOut.compareAddedItems(requiredproduct));
        OrderReview review = checkOut.clickOnCheckout();
        review.selectCountrie("new");
        OrderConfirmation confirmation = review.placeOrder();
        String confirmText = confirmation.compareConfirmText();
        Assert.assertTrue(confirmText.equalsIgnoreCase("Thankyou for the order."));
    }
}
