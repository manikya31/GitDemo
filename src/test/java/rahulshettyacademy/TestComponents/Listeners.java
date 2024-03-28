package rahulshettyacademy.TestComponents;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import rahulshettyacademy.resources.ExtentReporterNG;
import org.openqa.selenium.WebDriver;

//ITestListener Interface Has all  related to Listeners which implements testng listeners

public class Listeners extends BaseTest implements ITestListener {

	ExtentTest test;
	ExtentReports extent = ExtentReporterNG.getExtentReportObject();
	// There was one problem of concurrency and test object getting over ridden by another method object
	// and actually method is failing but resulting in failing x method instead of y method because of this overridden problem
	// Problem: It uses same thread for all.
	// Solution: Use the ThreadLocal class which assigns each unique thread to each test
	ThreadLocal<ExtentTest> threadLocal = new ThreadLocal<ExtentTest>();
	@Override
	public void onTestStart(ITestResult result) {
		
		// Before every test start this method gets run so we write here the createTest so that before every test this gets attached
		ITestListener.super.onTestStart(result);
		test = extent.createTest(result.getMethod().getMethodName());
		threadLocal.set(test);// unique thread id(ErrorvalidationTest)-> and added to its test object
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ITestListener.super.onTestSuccess(result);
		//test.log(Status.PASS, "Test Passed");
		// Just replacing every where test to ThreadLocal
		threadLocal.get().log(Status.PASS, "Test Passed");
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		
		ITestListener.super.onTestFailure(result);
		// As Test fails, the execution comes here and checks anything is there to execute when test fails
		// and executes this block of code.
		// Step 1 - Call the  fail method of extent explicitly because execution comes here only if test fails
		// and Get the logs for failure using result object and pass it inside the fail method of extent 
		
		//test.fail(result.getThrowable()); 
		
		// Instead of line 52, introduced line 53 using threadLocal object, 
				//we retrieve the exact thread that is being set, using get method so that overridden will not be done and that particular method test only will be executed
		threadLocal.get().fail(result.getThrowable());	
		
		// for the driver object on getScreenshot as we should give life. And also that should belong to that
		//particular class where screen shot is happening
		// for that class, we have those details in result object.
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		// Write the screen shot code as we need screenshot when test fails and attach it to reporter or extent
		String filePath = null;
		try {
			filePath = getScreenShot(result.getMethod().getMethodName(),driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//test.addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
		// Just replacing every where test to ThreadLocal
		threadLocal.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSkipped(result);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onStart(context);
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onFinish(context);
		extent.flush();
	}
	
	
	

}
