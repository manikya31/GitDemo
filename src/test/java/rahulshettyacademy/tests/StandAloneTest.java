package rahulshettyacademy.tests;

import static org.testng.Assert.assertTrue;

import java.beans.Visibility;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {

	public static void main(String[] args) throws Exception {
	
		
		
		String productName = "ZARA COAT 3";
		List<String> productList = new ArrayList<String>();
		productList.add("ZARA COAT 3");
		productList.add("ADIDAS");
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		// Login page or landing page
		driver.get("https://rahulshettyacademy.com/client");
		driver.manage().window().maximize();
		driver.findElement(By.id("userEmail")).sendKeys("automationtest2@test.com");
		driver.findElement(By.id("userPassword")).sendKeys("Password@123");		
		driver.findElement(By.id("login")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));	
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		WebElement prod = products.stream().filter(product -> 
		product.findElement(By.xpath("//div[@class='card']/div[@class='card-body']/h5/b")).getText().equals(productName)).findFirst().orElse(null);		
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		
		
		driver.findElement(By.cssSelector("button[routerlink*='cart']")).click();
		
		System.out.println(driver.findElement(By.cssSelector(".cartSection h3 ")).getText());
		
		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3 "));
		Boolean match = cartProducts.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(match);
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		driver.findElement(By.cssSelector(".form-group input")).sendKeys("Ind");
		List<WebElement> countryNames = driver.findElements(By.cssSelector(".ta-results button span"));
		System.out.println(countryNames.size());
		for(WebElement cname:countryNames) {
			if(cname.getText().equalsIgnoreCase("India"))
			{
				System.out.println(cname.getText());
				cname.click();
				break;
			}
		}
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		js.executeScript("window.scrollBy(0,600)");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".action__submit")));
		driver.findElement(By.cssSelector(".action__submit")).click();
		System.out.println("Order Id is "+driver.findElement(By.cssSelector("label[class='ng-star-inserted']")).getText());
		
		
//		for(int i = 0;i<products.size();i++) {
//		   System.out.println(products.get(i).findElement(By.cssSelector(".card .card-body h5 b")).getText());
//		}	
		
//		for (WebElement product : products) {
//			String productName = product.findElement(By.cssSelector(".card .card-body h5 b")).getText();
//			System.out.println(productName);	
//			if(productName.contains("ZARA COAT 3") || productName.contains("ADIDAS")) 
//			{				
//				//Thread.sleep(3000);
//				product.findElement(By.cssSelector(".card .card-body button:last-of-type")).click();			
//				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
//				wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
//		     	//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
//			}
//		}
//		driver.findElement(By.cssSelector("button[routerlink*='cart']")).click();
		

	}

}
