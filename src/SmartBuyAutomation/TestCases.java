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
import org.testng.Assert;
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

	@Test(priority = 1, enabled = false)
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

	@Test(priority = 2, enabled = false)
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
	
	@Test(priority = 3, enabled = false)
	public void ProductSearchFunctionality() throws InterruptedException {
		
		driver.findElement(By.name("q")).sendKeys("iphone 14 plus");
		
		Thread.sleep(1000);
		
		driver.findElement(By.className("search-bar__submit")).click();
	
	}
	
	@Test(priority = 4, enabled = false)
	public void languageSelection() throws InterruptedException {
		
        Thread.sleep(2000);
		
        driver.findElement(By.cssSelector("#vitals_popup > div > div > div.Vtl-Modal__ContentWrapper > div > div > div > div > div > button.Vtl-Button.Vtl-Button--large.Vtl-Button--isLightTheme.Vtl-Button--isGhostVariant.vtl-text-body-m.Vtl-Popup__SecondaryButton")).click();
        
        Thread.sleep(2000);
		
		WebElement languageSwithcer = driver.findElement(By.xpath("//*[@id=\"localization_form_header_locale\"]/div/button"));
		languageSwithcer.click();
		Thread.sleep(2000);
		
		WebElement chooseArabic = driver.findElement(By.xpath("//*[@id=\"desktop-locale-selector\"]/div/ul/li[2]/button"));
		chooseArabic.click();
		Thread.sleep(2000);
		
		WebElement arabicHome = driver.findElement(By.xpath("//*[@id=\"shopify-section-sections--23554635530550__header\"]/section/header/div/div/div[2]/div[1]"));
		Assert.assertTrue(arabicHome.isDisplayed(), "Arabic content is not displayed on the page.");
		
	}
	
	@Test(priority = 5, enabled = false)
	public void removeFromCart() throws InterruptedException {
		
        Thread.sleep(2000);
		
        driver.findElement(By.xpath("//*[@id=\"vitals_popup\"]/div/div/div[2]/div/div/div/div/div/button[2]")).click();
        
		Thread.sleep(2000);
		
		WebElement removeButon = driver.findElement(By.xpath("//*[@id=\"mini-cart\"]/div/div[1]/div/div/div[2]/div[2]/a"));
		removeButon.click();
		Thread.sleep(2000);
		
		WebElement emptyCartMsg = driver.findElement(By.xpath("//*[@id=\"mini-cart\"]/div/div"));
		Assert.assertTrue(emptyCartMsg.isDisplayed(), "Cart is not empty after removing items.");
		
		driver.findElement(By.xpath("//*[@id=\"shopify-section-sections--23554635530550__header\"]/section/header/div/div/div[2]/div[4]/a")).click();
		Thread.sleep(2000);
	}
	
	@Test(priority = 6, enabled = false)
	public void productDetailAccuracy() throws InterruptedException {
		
        Thread.sleep(2000);
		
        driver.findElement(By.xpath("//*[@id=\"vitals_popup\"]/div/div/div[2]/div/div/div/div/div/button[2]")).click();
		
		WebElement firstProduct = driver.findElement(By.xpath("//*[@id=\"shopify-section-template--23554639757622__featured_collection_AyKFpr\"]/section/div[2]/div/div/div/div/div/div[5]/div[2]/div/a"));
        String productNameOnGrid = firstProduct.getText().trim();
        firstProduct.click();

        Thread.sleep(2000); 

        WebElement productNameOnDetail = driver.findElement(By.xpath("//h1[@class='product-meta__title heading h1']"));
        String productNameDetail = productNameOnDetail.getText().trim();
        Assert.assertEquals(productNameDetail, productNameOnGrid, "Product name mismatch!");

        WebElement productImage = driver.findElement(By.xpath("//img[contains(@class, 'product-gallery__image')]"));
        Assert.assertTrue(productImage.isDisplayed(), "Product image is not displayed!");

        WebElement productPrice = driver.findElement(By.xpath("//span[contains(@class,'price') and contains(@class,'price--highlight')]"));
        Assert.assertTrue(productPrice.isDisplayed(), "Product price is not displayed!");

        WebElement availability = driver.findElement(By.cssSelector("input.quantity-selector__value"));
        Assert.assertTrue(availability.isDisplayed(), "Availability info is missing!");
		 
	}

	@AfterTest
	public void tearDown() {

	}
}
