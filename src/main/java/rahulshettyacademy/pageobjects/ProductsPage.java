package rahulshettyacademy.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import rahulshettyacademy.AbstractComponents.AbstractComponent;

import org.openqa.selenium.WebDriver;

public class ProductsPage extends AbstractComponent{
	
	WebDriver driver;
	
	public ProductsPage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(css=".mb-3")
	List<WebElement> products;
	
	By productsBy = By.cssSelector(".mb-3");
	By addTocartBy = By.cssSelector(".card-body button:last-of-type");
	
	By toastContainerBy = By.cssSelector("#toast-container");
	
	@FindBy(css=".ng-animating")
	WebElement spinner;
	

	
	public List<WebElement> getProductsList()
	{		
		waitMethodForVisibility(productsBy);
		return products;		
	}
	public WebElement getProductByName(String productName) {
		
		waitMethodForVisibility(productsBy);
		List<WebElement> products = getProductsList();
		WebElement prod = products.stream().filter(product -> 
		product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);	
		return prod;		
	}
	public void addProductToCart(String productName) throws Exception
	{
		WebElement prod = getProductByName(productName);
		prod.findElement(addTocartBy).click();
		waitMethodForVisibility(toastContainerBy);	
		waitMethodForInVisibility(spinner);
	}

}
