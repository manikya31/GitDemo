package rahulshettyacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

public class PaymentsPage extends AbstractComponent {
	
	WebDriver driver;
	public PaymentsPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(css=".form-group input")
	WebElement countryNameInpt;
	
	@FindBy(css=".ta-results button span")
	List<WebElement> countryNamesList;

	
	By submitBtnBy = By.cssSelector(".action__submit");
	
	@FindBy(css=".action__submit")
	WebElement submitBtn;
	
	public List<WebElement> getCountryNameList() 
	{
		return countryNamesList;
	}
	
	public void selectCountryName(String countryName) {
		
		countryNameInpt.sendKeys(countryName);
		List<WebElement> countryNames = getCountryNameList();	
		for(WebElement cname:countryNames) {
			if(cname.getText().equalsIgnoreCase("India"))
			{
				cname.click();
				break;
			}
		}
		scrollBy("window.scrollBy(0,600)");
		waitMethodForVisibility(submitBtnBy);
	}
	public OrderConfirmationPage placeOrder()
	{
		
		submitBtn.click();
		return new OrderConfirmationPage(driver);
	}

}
