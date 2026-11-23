package ecommerceWebSite.stepDefinition;

import java.io.IOException;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import academy.pageobject.CartPage;
import academy.pageobject.CheckoutPage;
import academy.pageobject.ConfirmationPage;
import academy.pageobject.LandingPage;
import academy.pageobject.ProductCatalogue;
import ecommerceWebSite.TestComponents.BaseTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitionImp extends BaseTest{
	
	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public ConfirmationPage confirmationPage;
	@Given("I landed on Ecommerce Page")
	public void I_landed_on_Ecommerce_() throws IOException {
		landingPage = launchApplication();
	}
	
	@Given("^Logged in with username (.+) and password (.+)$")
	public void logged_in_username_and_password(String username, String password) {
		productCatalogue = landingPage.loginApplication(username, password);
	}
	
	@When("^I add product (.+) to Cart$")
	public void i_add_product_to_cart(String productName) throws InterruptedException{
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
	}
	
	@When("^Checkout (.+) and submit the order$")
	public void checkout_submit_order(String productName) {
		CartPage CartPage = productCatalogue.goToCartpage();
		boolean match = CartPage.verifyProductDisplay(productName);
		Assert.assertTrue(match);
		CheckoutPage CheckoutPage = CartPage.goToCheckout();
		CheckoutPage.selectCountry("India");
		confirmationPage = CheckoutPage.submitOrder();
	}
	
    @Then("{string} message is displayed on ConfirmationPage")
    public void message_displayed_confirmationPage(String string) {
    	String confirmationMessage = confirmationPage.getConfirmationMessage();
		System.out.println(confirmationMessage);
		Assert.assertTrue(confirmationMessage.equalsIgnoreCase(string));
	}

	
}