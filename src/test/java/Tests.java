import Utils.DriverSingleton;
import Utils.Utils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

import static org.testng.AssertJUnit.assertEquals;

public class Tests {

    private static WebDriver driver;

    private static ExtentReports extent;
    private static ExtentTest test;

    private String timeNow = "C:\\Users\\Owner\\IdeaProjects\\Class11HW\\src\\Output\\" + String.valueOf(System.currentTimeMillis());


    @BeforeMethod
    public void init(){
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("C:\\Users\\Owner\\IdeaProjects\\Class11HW\\src\\Output\\Report.html");
        // attach reporter
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        // name your test and add description
        test = extent.createTest("MyFirstTest", "Sample description");

        // log results
        test.log(Status.INFO, "@Before class");
        driver = DriverSingleton.getDriverInstance();
    }

    @Test
    public void test1(){
        driver.get("https://dgotlieb.github.io/Navigation/Navigation.html");
        driver.switchTo().frame("my-frame");
        System.out.println(driver.findElement(By.cssSelector("div[id='iframe_container']")).getText());
    }

    @Test
    public void test2(){
        driver.get("https://translate.google.com/");
        test.info("go to google translate");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        test.pass("the before screen shot", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot(timeNow)).build());
        driver.findElement(By.tagName("textarea")).sendKeys("Bionic");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        test.pass("the after screen shot", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot(timeNow)).build());
        test.pass("the test has passed");
    }

    @Test
    public void test3(){
        driver.get(Utils.getData("site"));
    }

    @Test
    public void test4(){
        driver.get("https://dgotlieb.github.io/Navigation/Navigation.html");
        driver.findElement(By.cssSelector("input[id='MyAlert']")).click();
        System.out.println(driver.switchTo().alert().getText());
        driver.switchTo().alert().accept();

        driver.findElement(By.cssSelector("input[id='MyPrompt']")).click();
        driver.switchTo().alert().sendKeys("Efi");
        driver.switchTo().alert().accept();
        driver.switchTo().defaultContent();
        assertEquals(driver.findElement(By.cssSelector("span[id='output']")).getText(),"Efi");

        driver.findElement(By.cssSelector("input[id='MyConfirm']")).click();
        driver.switchTo().alert().accept();
        driver.switchTo().defaultContent();
        assertEquals(driver.findElement(By.cssSelector("span[id='output']")).getText(),"Confirmed");

        driver.findElement(By.cssSelector("input[id='openNewTab']")).click();
        driver.switchTo().defaultContent();
        assertEquals(driver.findElement(By.cssSelector("span[id='output']")).getText(),"Confirmed");

        driver.findElement(By.tagName("a")).click();
        ArrayList<String> windows = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(windows.get(0));
        assertEquals(driver.findElement(By.cssSelector("span[id='output']")).getText(),"Confirmed");
    }

    @Test
    public void test5() throws IOException, ParseException {
        driver.get(siteFromJson());
    }

    @AfterMethod
    public void tearDown(){
        test.log(Status.INFO, "@After test " + "After test method");
        driver.quit();
        // build and flush report
        extent.flush();
    }

    private static String takeScreenShot(String ImagesPath) {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File screenShotFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destinationFile = new File(ImagesPath + ".png");
        try {
            FileUtils.copyFile(screenShotFile, destinationFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return ImagesPath + ".png";
    }

    private static String siteFromJson() throws IOException, ParseException {
        Object obj = new JSONParser().parse(new FileReader("site.json"));

        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;

        // getting firstName and lastName
        String site = (String) jo.get("site");
        return site;
    }
}
