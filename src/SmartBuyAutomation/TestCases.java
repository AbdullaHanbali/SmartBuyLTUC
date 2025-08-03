package SmartBuyAutomation;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestCases {
	
	WebDriver driver = new ChromeDriver();	
	String TheURL = "https://smartbuy-me.com/";
	
	Random rand = new Random();
	
	@BeforeTest
	public void mySetup() {
		driver.get(TheURL);
		driver.manage().window().maximize();
	}
	
	@Test(priority = 1)
	public void homePageAccessability() throws InterruptedException {
		
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='vitals_popup']//button[2]")).click();
		
		JavascriptExecutor js = (JavascriptExecutor) driver; 
		// Scroll down gradually to bottom
		long height = (long) js.executeScript("return document.body.scrollHeight");
		for (int i = 0; i < height; i += 100) {
		    js.executeScript("window.scrollBy(0, 100);");
		    Thread.sleep(100); 
		}

		// Wait 2 seconds at bottom
		Thread.sleep(1000);

		// Scroll up gradually to top
		for (int i = 0; i < height; i += 100) {
		    js.executeScript("window.scrollBy(0, -100);");
		    Thread.sleep(100);
		}
	}
	
	@Test(priority = 2)
	public void ProductCategoryNavigation() {
		
	
	}
	
	@AfterTest
	public void tearDown() {

    }
}
