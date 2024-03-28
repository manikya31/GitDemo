package rahulshettyacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class ProductCheckoutPage {
	
	WebDriver driver;
	public ProductCheckoutPage(WebDriver driver) {
		
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(css=".cartSection h3 ")
	List<WebElement> cartProductsList;
	
	@FindBy(css=".totalRow button")
	WebElement checkoutBtn;
	
	
//	public List<WebElement> getProductsAddedToCart() 
//	{
//		return cartProductsList;
//		
//	}
	
	public Boolean validateProducts(String productName) 
	{	
		//List<WebElement> cartProducts = getProductsAddedToCart();
		return cartProductsList.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));	
	}
	
	public PaymentsPage checkout() 
	{
		checkoutBtn.click();
		PaymentsPage paymentPage = new PaymentsPage(driver);
		return paymentPage;
	}
	

}
