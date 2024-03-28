package rahulshettyacademy.tests;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;


import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.OrderConfirmationPage;
import rahulshettyacademy.pageobjects.PaymentsPage;
import rahulshettyacademy.pageobjects.ProductCheckoutPage;
import rahulshettyacademy.pageobjects.ProductsPage;

public class ErrorValidation extends BaseTest {
	
	
	@Test(groups = {"Error Handling"},retryAnalyzer = rahulshettyacademy.TestComponents.Retry.class)
	public void loginErrorValidation() {
		
		String productName = "ZARA COAT 3";	
		landingPage.loginApplication("automationtest2@test.com", "Password@12345");
		String errorMsg = landingPage.getErrorMessage();
		Assert.assertEquals("Incorrect email or password.",errorMsg);				
	}
	
	@Test
	public void productErrorValidation() throws Exception
	{
			
		String productName = "ZARA COAT 3";
		ProductsPage productsPage = landingPage.loginApplication("automationtestveena@test.com", "Veena@1234");
		List<WebElement> products = productsPage.getProductsList();
		productsPage.addProductToCart(productName);
		
		//Product checkout page
		ProductCheckoutPage pCheckoutPage = productsPage.goToCartPage();
		Boolean match = pCheckoutPage.validateProducts("ZARA COAT 33");
		Assert.assertFalse(match);

	}

}
