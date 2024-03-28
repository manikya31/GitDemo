package rahulshettyacademy.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderConfirmationPage {
	
	WebDriver driver;
	public OrderConfirmationPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver,this);
		
	}
	@FindBy(css="label[class='ng-star-inserted']")
	WebElement orderId;
	
	@FindBy(css=".hero-primary")
	WebElement orderConfMsg;
	
	public String getOrderId() 
	{
		return orderId.getText();
	}
	public String getOrderConfMsg() 
	{
		return orderConfMsg.getText();
	}
	

}
