package rahulshettyacademy.tests;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.pageobjects.LandingPage;
import rahulshettyacademy.pageobjects.OrderConfirmationPage;
import rahulshettyacademy.pageobjects.PaymentsPage;
import rahulshettyacademy.pageobjects.ProductCheckoutPage;
import rahulshettyacademy.pageobjects.ProductsPage;

public class End2EndTest {
	
public static void main(String[] args) throws Exception {
	
		
		String productName = "ZARA COAT 3";
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		
		// Login page or landing page
		LandingPage landingPage = new LandingPage(driver);
		landingPage.goTo();
		landingPage.loginApplication("automationtest2@test.com", "Password@123");
		
		//Product catalogue page
		ProductsPage productsPage = new ProductsPage(driver); 
		List<WebElement> products = productsPage.getProductsList();
		productsPage.addProductToCart(productName);
		productsPage.goToCartPage();
		
		//Product checkout page
		ProductCheckoutPage pCheckoutPage =  new ProductCheckoutPage(driver);
		Boolean match = pCheckoutPage.validateProducts(productName);
		Assert.assertTrue(match);
		pCheckoutPage.checkout();
		
	    //Payment Page
		PaymentsPage paymentPage = new PaymentsPage(driver);
		pCheckoutPage.checkout();
		paymentPage.selectCountryName("Ind");
		paymentPage.placeOrder();
		
		// Order Confirmation page
		OrderConfirmationPage ocp = new OrderConfirmationPage(driver);
		paymentPage.placeOrder();
		String confirmMsg = ocp.getOrderConfMsg();
		Assert.assertTrue(confirmMsg.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		System.out.println("Order Id is "+ocp.getOrderId());
		

	}

}
