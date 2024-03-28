package rahulshettyacademy.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

import org.openqa.selenium.WebDriver;

public class LandingPage extends AbstractComponent{
	
	WebDriver driver;
	
	public LandingPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	//WebElement userEmails = driver.findElement(By.id("userEmail"));
	// Using Page Factory we can do this locators very easily
	@FindBy(id = "userEmail")
	WebElement userEmail;
	
	@FindBy(id = "userPassword")
	WebElement passwordEle;
	
	@FindBy(id="login")
	WebElement loginBtn;
	
	@FindBy(css="[class*='flyInOut']")
	WebElement errorMsg;
	
	public void goTo() {	
		driver.get("https://rahulshettyacademy.com/client");
		
	}
	
	public String getErrorMessage() {
		
		waitMethodForVisibility(errorMsg);
		return errorMsg.getText();
	}
	
	public ProductsPage loginApplication(String email,String password) {
		userEmail.sendKeys(email);
		passwordEle.sendKeys(password);
		loginBtn.click();	
		ProductsPage productsPage = new ProductsPage(driver);
		return productsPage;
	}
	
}
