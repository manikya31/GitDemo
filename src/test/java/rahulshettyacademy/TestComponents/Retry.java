package rahulshettyacademy.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

// IRetryAnalyzer is the Interface which is used to re-run the Flaky tests in testNG to make sure it is actual failure
public class Retry implements IRetryAnalyzer {

	// This method is triggered whenever test fails, after doing all mentioned in onTestFailure, 
	// it comes here and checks whether user specified any test cases to re-run and how many no of times, etc
	// If test cases passes, it will not at all come here
	
	int count = 0;
	int maxTry = 1;
	@Override
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
		if(count<maxTry) 
		{
			count++;
			return true;		// As long as the retry method returns false, it will stop re-trying. 
								//So we will return true for how many maxTry times we need test to run	
		}
		return false;
	}

}
