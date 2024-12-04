package SeleniumFramework.TestCases;

import org.PageObjectModel.*;
import SeleniumFramework.TestComponents.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class PurchaseOrders extends BaseTest {
    String requiredproduct = "ADIDAS ORIGINAL";

    @Test(groups = {"Purchaser"},dataProvider = "dataProvide")
    public void submitOrder(HashMap<String,String> data) throws InterruptedException, IOException {
        ProductCatalogue productCatalogue = login.pageLogin(data.get("email"),data.get("password"));
        productCatalogue.getProduct(data.get("product"));
        productCatalogue.addToCart();
        Checkout checkOut = productCatalogue.clickOnCartIcon();
        Assert.assertTrue(checkOut.compareAddedItems(data.get("product")));
        OrderReview review = checkOut.clickOnCheckout();
        review.selectCountrie("new");
        OrderConfirmation confirmation = review.placeOrder();
        String confirmText = confirmation.compareConfirmText();
        Assert.assertTrue(confirmText.equalsIgnoreCase("Thankyou for the order."));
        //takeScreenshot("submitOrder");
    }

    @Test(dependsOnMethods = {"submitOrder"})
    public void ordersCheck(){
        ProductCatalogue productCatalogue = login.pageLogin("venky.regadapa@outlook.com","Tester@123");
        productCatalogue.getProduct(requiredproduct);
        Orders orders = productCatalogue.clickOnOrdersIcon();
        Assert.assertTrue(orders.searchAddedOrders(requiredproduct));
    }

    /*
    @DataProvider
    public Object[][] dataProvide(){
        return new Object[][] {{"starlord45@mailinator.com","Starlord@123","ZARA COAT 3"},{"venky.regadapa@outlook.com","Tester@123","ADIDAS ORIGINAL"}};
        //providing data to the test by using parameterization
        //need to catch the objects in test method by crating args, ex: public void submitOrder(String email,String password,String product)
    }
    @DataProvider
    public Object[][] dataProvide(){
        //providing data to the test by using/integrating Hashmap to the data provider
        HashMap<String,String> map1 = new HashMap<String,String>();
        map1.put("email","starlord45@mailinator.com");
        map1.put("password","Starlord@123");
        map1.put("product","IPHONE 13 PRO");
        HashMap<String,String> map2 = new HashMap<String,String>();
        map2.put("email","venky.regadapa@outlook.com");
        map2.put("password","Tester@123");
        map2.put("product","ADIDAS ORIGINAL");

        return new Object[][] {{map1},{map2}};
    } */

    @DataProvider
    public Object[][] dataProvide() throws IOException {
        //providing data to the test by Json file
        List<HashMap<String,String>> testData = getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//SeleniumFramework//TestData//PurchaseOrdersData.json");
        //Passing json file path as argument
        return new Object[][] {{testData.get(0)}, {testData.get(1)}};
    }
}
