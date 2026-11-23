package ecommerceWebSite.TestComponents;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import academy.resourses.ExtendReportstestNG;

public class Listeners extends BaseTest implements ITestListener {

	ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	ExtentReports extent = ExtendReportstestNG.getReportObject();

	@Override
	public void onTestStart(ITestResult result) {
		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
		test.set(extentTest);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ExtentTest extentTest = test.get();
		if (extentTest != null) {
			extentTest.log(Status.PASS, "Test Passed");
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		ExtentTest extentTest = test.get();
		if (extentTest == null) {
			extentTest = extent.createTest(result.getMethod().getMethodName());
			test.set(extentTest);
		}
		extentTest.fail(result.getThrowable());
		WebDriver driver = null;
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (driver != null) {
			try {
				String filePath = getScreenshot(result.getMethod().getMethodName(), driver);
				if (filePath != null) {
					extentTest.addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}
	
	@Override
    public void onTestSkipped(ITestResult result) {

        ExtentTest extentTest = test.get();

        if (extentTest != null) {
            extentTest.log(Status.SKIP, "Test Skipped");
        }
    }
}