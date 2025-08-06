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
	public void ProductCategoryNavigation() throws InterruptedException {

		Actions actions = new Actions(driver);

		WebElement shopByCategory = driver.findElement(By
		.xpath("//*[@id=\"shopify-section-sections--23554635530550__header\"]/section/nav/div/div/ul/li[2]/a"));
		actions.moveToElement(shopByCategory).click().build().perform();

		Thread.sleep(2000);

		List<WebElement> subCategories = driver.findElements(By.cssSelector("a.nav-dropdown__link.menu-toggle"));

		int randomIndex = new Random().nextInt(subCategories.size());
		WebElement randomSubCategory = subCategories.get(randomIndex);

		actions.moveToElement(randomSubCategory).click().build().perform();
		
		Thread.sleep(2000);
		
		actions.moveToElement(randomSubCategory).click().build().perform();

		Thread.sleep(1000);

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
		
		Thread.sleep(1000);
	}
	
	@Test(priority = 3)
	public void ProductSearchFunctionality() throws InterruptedException {
		
		driver.findElement(By.name("q")).sendKeys("iphone 14 plus");
		
		Thread.sleep(1000);
		
		driver.findElement(By.className("search-bar__submit")).click();
	
	}

	@AfterTest
	public void tearDown() {

	}
}
