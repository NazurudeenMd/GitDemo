package ecommerceWebSite.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import academy.pageobject.CartPage;
import academy.pageobject.CheckoutPage;
import academy.pageobject.ConfirmationPage;
import academy.pageobject.OrderPage;
import academy.pageobject.ProductCatalogue;
import ecommerceWebSite.TestComponents.BaseTest;

public class SubmitOrder extends BaseTest {
	
	String productName = "ZARA COAT 3";
	
	@Test(dataProvider = "getData", groups = { "PurchaseOrder" })
	public void Submitorder(HashMap<String, String> input) throws IOException {
		
		ProductCatalogue ProductCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		List<WebElement> products = ProductCatalogue.getProductList();
		ProductCatalogue.addProductToCart(input.get("product"));
		CartPage CartPage = ProductCatalogue.goToCartpage();
		boolean match = CartPage.verifyProductDisplay(productName);
		Assert.assertTrue(match);
		CheckoutPage CheckoutPage = CartPage.goToCheckout();
		CheckoutPage.selectCountry("India");
		ConfirmationPage ConfirmationPage = CheckoutPage.submitOrder();
		String confirmationMessage = ConfirmationPage.getConfirmationMessage();
		System.out.println(confirmationMessage);
		Assert.assertTrue(confirmationMessage.equalsIgnoreCase("Thankyou for the order."));
	}
	
	@Test(dependsOnMethods = { "Submitorder" })
	public void OrderHistroyTest() {
		ProductCatalogue ProductCatalogue = landingPage.loginApplication("anishika@gmail.com", "Iamking@000");
		OrderPage ordersPage = ProductCatalogue.goToOrderPage();
		Assert.assertTrue(ordersPage.verifyOrderDisplay(productName));
	}
	
	@DataProvider
	public Object[][] getData() throws IOException {
		
		/*HashMap<String, String> map = new HashMap<String, String>();
		map.put("email", "anishika@gmail.com");
		map.put("password", "Iamking@000");
		map.put("product", "ZARA COAT 3");
		
		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("email", "shetty@gmail.com");
		map1.put("password", "Iamking@000");
		map1.put("product", "ADIDAS ORIGINAL"); */
		
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java//ecommerceWebSite//data//PurchaseOrder.json");
		
		return new Object[][] {{data.get(0)}, {data.get(1)}};
	}
}