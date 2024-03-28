package rahulshettyacademy.tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.data.DataReader;
import rahulshettyacademy.pageobjects.LandingPage;
import rahulshettyacademy.pageobjects.OrderConfirmationPage;
import rahulshettyacademy.pageobjects.OrderHistoryPage;
import rahulshettyacademy.pageobjects.PaymentsPage;
import rahulshettyacademy.pageobjects.ProductCheckoutPage;
import rahulshettyacademy.pageobjects.ProductsPage;

public class SubmitOrderTest extends BaseTest {
	
	String productName =  "ZARA COAT 3";
	// Submitting order
	
	@Test(dataProvider = "getData",groups = {"Purchase"})
	public void submitOrder(HashMap<String,String> input) throws Exception
	{
			
		// Landing page and driver initialization
		// Refactored code to even launch driver and launch app separetely
		//LandingPage landingPage = launchApplication();
		ProductsPage productsPage = landingPage.loginApplication(input.get("email"),input.get("password"));
		List<WebElement> products = productsPage.getProductsList();
		productsPage.addProductToCart(input.get("product"));

		
		//Product checkout page
		ProductCheckoutPage pCheckoutPage = productsPage.goToCartPage();
		Boolean match = pCheckoutPage.validateProducts(input.get("product"));
		Assert.assertTrue(match);
		
	    //Payment Page
		PaymentsPage paymentPage = pCheckoutPage.checkout();
		paymentPage.selectCountryName("Ind");
		
		// Order Confirmation page
		OrderConfirmationPage ocp = paymentPage.placeOrder();
		String confirmMsg = ocp.getOrderConfMsg();
		Assert.assertTrue(confirmMsg.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		System.out.println("Order Id is "+ocp.getOrderId());
	}
	
	// Verify whether Order (ZARA Coat 3) is displayed in Orders Page
	
	@Test(dependsOnMethods = {"submitOrder"})
	public void orderHistoryTest() 
	{
		// ZARA COAT 3
		ProductsPage productsPage = landingPage.loginApplication("automationtest2@test.com", "Password@123");
		OrderHistoryPage ohp = productsPage.goToOrdersPage();
		Assert.assertTrue(ohp.verifyOrderDisplay(productName));
	}
	
	@DataProvider
	public Object[][] getData() throws IOException
	{
		//3rd way-  Driving data  using JSON object and converting it to Hashmap
		   // 1-st option is Creating object for datareader and accessing its methods
		//DataReader dr = new DataReader();
		//dr.getJSONDataToMap();
		  //2nd optinś is by placing the datareader code onto Base test
		List<HashMap<String,String>> data = getJSONDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\rahulshettyacademy\\data\\PurchaseOrder.json");
		return new Object[][] {{data.get(0)},{ data.get(1) }};
		
		// 2nd Way - Driving data from hashmap
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("email", "automationtest2@test.com");
//		map.put("password", "Password@123");
//		map.put("product", "ZARA COAT 3");
//		
//		HashMap<String, String> map1 = new HashMap<String, String>();
//		map1.put("email", "automationtestveena@test.com");
//		map1.put("password", "Veena@1234");
//		map1.put("product", "ADIDAS ORIGINAL");
//		return new Object[][] {{map},{map1}};
		// 1st Way - Using Normal Multi-dimensional array
		//return new Object[][] {{"automationtest2@test.com","Password@123","ZARA COAT 3"},{"automationtestveena@test.com","Veena@1234","ADIDAS ORIGINAL"} };
	}

}
