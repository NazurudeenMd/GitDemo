package ecommerceWebSite.tests;

import java.io.IOException;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import academy.pageobject.CartPage;
import academy.pageobject.ProductCatalogue;
import ecommerceWebSite.TestComponents.BaseTest;
import ecommerceWebSite.TestComponents.Retry;

public class ErrorValidation extends BaseTest{
	
	ExtentReports extend;
	
	@Test (groups= {"ErrorHandling"}, retryAnalyzer = Retry.class)
	public void LoginErrorValidation() throws IOException {
		
		landingPage.loginApplication("anishika@gmail.com", "Iamng@000");
		Assert.assertEquals("Incorrect email password", landingPage.getErrorMessage());
	}
	
	@Test
	public void ProductErrorValidation() throws IOException {
		
		String productName = "ZARA COAT 3";
		ProductCatalogue ProductCatalogue = landingPage.loginApplication("rahulshetty@gmail.com", "Iamking@000");
		List<WebElement> products = ProductCatalogue.getProductList();
		ProductCatalogue.addProductToCart(productName);
		CartPage CartPage = ProductCatalogue.goToCartpage();
		boolean match = CartPage.verifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(match);
	}
}