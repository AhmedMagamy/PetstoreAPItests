package utilites;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ReportsGenerator implements ITestListener {
    ExtentHtmlReporter htmlReporter = null;
    ExtentReports extent = new ExtentReports();
    ExtentTest test1 = null;


    public void onFinish(ITestContext result) {
        System.out.println("test is completed  : " + result.getName());
        test1.info("Automated tests run is finished ");
        extent.flush();


    }

    public void onStart(ITestContext result) {
        // TODO Auto-generated method stub
        try {

            String filename = result.getStartDate().toString().replaceAll(":", "-");
            String projPath = System.getProperty("user.dir");
            htmlReporter = new ExtentHtmlReporter(projPath + "\\Reports\\" + filename + ".html");
            extent.attachReporter(htmlReporter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // TODO Auto-generated method stub

        test1.fail(result.getName() + "TestFailedButWithinSuccessPercentage");


    }

    public void onTestFailure(ITestResult result) {


        System.out.println(result.getInstanceName());
        System.out.println("test is failed : " + result.getName());
        test1.fail("test is failed : " + result.getName());


    }

    public void onTestSkipped(ITestResult result) {
        System.out.println("test is skipped : " + result.getName());
        test1.skip("test is skipped : " + result.getName());

    }

    public void onTestStart(ITestResult result) {
        System.out.println("test started : " + result.getName());
        test1 = extent.createTest(result.getName());

    }

    public void onTestSuccess(ITestResult result) {
        System.out.println("test is sucessful : " + result.getName());
        test1.pass("test is sucessful : " + result.getName());

    }
}