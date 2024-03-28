package rahulshettyacademy.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.pageobjects.LandingPage;

public class BaseTest {
	
	public WebDriver driver;
	public LandingPage landingPage;
	public WebDriver initializeDriver() throws Exception 
	{
		// If we want to run those test on multiple browsers, its not possible now so we should set some global properties
		// which gives on what browser these tests should run and if we change those props, browser value will also change
        // if we set globalprop = chrome, tests will run in chrome
		// if we set globalprop = firefox, tests will run in firefox
		// To set those globalprop, we need one config file of properties type. 
		// Create one package in main and create a file with .properties as extension
		// Using this properties file we can use Properties call in java and execute our things
		
		// Properties class - to decide on which browser the tests should execute as we cant create each single method for each driver
		Properties properties = new Properties();		
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\rahulshettyacademy\\resources\\GlobalData.properties");		
		properties.load(fis);
		
		String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") : properties.getProperty("browser");
		
		//String browserName = properties.getProperty("browser");		
		
		if(browserName.contains("chrome")) 
		{			
			ChromeOptions options = new ChromeOptions(); // Used to ruin the Tests in headless mode, we need to pass the options.
			
			if(browserName.contains("headless")) 
			{		
				options.addArguments("headless");
			}
			
			WebDriverManager.chromedriver().setup();
			driver =  new ChromeDriver();
			//driver.manage().window().setSize(new Dimension(1440, 900)); --> Used to set the dimenson 
			// whenever browser open and generally used when any element is not visible and entire page not fit in single and scroll is used
		}
		else if(browserName.equalsIgnoreCase("firefox"))
		{
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		else if(browserName.equalsIgnoreCase("edge"))
		{
			WebDriverManager.edgedriver().setup();
		    driver = new EdgeDriver();
		}	
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
		
	}
	
	public List<HashMap<String, String>> getJSONDataToMap(String filePath) throws IOException 
	{
		// Step 1 : Read JSON to string using readFileToString Method which takes File object as parameter
		String jsonContent = FileUtils.readFileToString(new File(filePath),StandardCharsets.UTF_8);
		
		// Step 2: Convert String to HashMap with the help of jackson databind
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String,String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>() {
		});
		
		// Step 3 - return data i,e This data will take JSON and convert to List of type Hashmap with String type look like {map},{map1}
		return data;
	}
	
	public String getScreenShot(String testCaseName,WebDriver driver) throws IOException 
	{
		
		TakesScreenshot ts = (TakesScreenshot) driver;
		File srcFile = ts.getScreenshotAs(OutputType.FILE);
		File destFile =  new File(System.getProperty("user.dir")+"\\reports\\"+testCaseName+".png");
		FileUtils.copyFile(srcFile,destFile);
		return System.getProperty("user.dir")+"\\reports\\"+testCaseName+".png";
	}
	
	@BeforeMethod(alwaysRun = true)
	public  LandingPage launchApplication() throws Exception
	{
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}
//	@AfterMethod(alwaysRun = true)
//	public void close() 
//	{
//		driver.close();
//	}
}
