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
	
	String[] firstNames = {"Yazan", "Ali", "Hasan", "Mais", "Dareen"};
    String[] lastNames = {"Alaa", "Saif", "Abdullah", "Hamzeh", "Marwan", "Abedalrahman", "Omar", "Yazan"};
    String[] passwords = {"alaa@45", "saif1998", "abduallah09", "hamzeh66", "marwan666", "abedalrahman6544", "omar123", "yazan98"};
    String firstName = firstNames[rand.nextInt(firstNames.length)];
    String lastName = lastNames[rand.nextInt(lastNames.length)];
    String email = firstName.toLowerCase() + lastName.toLowerCase() + rand.nextInt(7000) + "@gmail.com";
    String password = passwords[rand.nextInt(passwords.length)];


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
	
	@Test(priority = 5, enabled = true)
    public void addRandomProductToCart() throws InterruptedException {
        driver.get("https://smartbuy-me.com/");

        String[] sections = {"Featured Products", "Promotions", "New Arrivals", "Trending Now"};
        String selectedSection = sections[rand.nextInt(sections.length)];
        System.out.println("Selected section: " + selectedSection);

        WebElement section = driver.findElement(By.xpath(
            "//h2[contains(@class,'section__title') and contains(text(),'" + selectedSection + "')]/ancestor::section"
        ));

        List<WebElement> items = section.findElements(
            By.xpath(".//a[contains(@class,'product-item__image-wrapper')]")
        );

        if (items.size() == 0) {
            System.out.println("No products found in this section.");
            return;
        }

        WebElement randomItem = items.get(rand.nextInt(items.size()));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", randomItem);
        randomItem.click();
        Thread.sleep(2000);

        WebElement addToCartBtn = driver.findElement(
            By.xpath("//button[contains(@class,'product-form__add-button')]")
        );

        if (addToCartBtn.getText().trim().equalsIgnoreCase("Sold out")) {
            System.out.println("Product is Sold Out.");
            return;
        }

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartBtn);
        addToCartBtn.click();
        Thread.sleep(1500);

        driver.findElement(By.xpath("//*[name()='svg' and contains(@class,'fa-times')]")).click();
        System.out.println("Product added to cart and popup closed successfully.");
        
        Thread.sleep(2000);
        
        driver.get("https://smartbuy-me.com/");
        Thread.sleep(2000);
    }
	
	@Test(priority = 6, enabled = true)
	public void removeFromCart() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(
	        By.cssSelector("a[href='/cart']")  
	    ));
	    cartIcon.click();

	    WebElement removeButton = wait.until(ExpectedConditions.elementToBeClickable(
	       By.xpath("//*[@id='mini-cart']//a[contains(@class,'mini-cart__quantity-remove')]")));
	    removeButton.click();

	    
	    wait.until(ExpectedConditions.invisibilityOfElementLocated(
	    	    By.cssSelector("#mini-cart .mini-cart__item")));
	}
	
	@Test(priority = 7, enabled = false)
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
	
	@Test(priority = 8, enabled = false)
	public void testSignup() throws InterruptedException {
	    
	    driver.get("https://smartbuy-me.com/ar/account/register");
	    Thread.sleep(5000);

	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("window.scrollTo(0,150)");
	    Thread.sleep(2000);

	    System.out.println(" Signup email: " + email);
	    System.out.println(" Password: " + password);

	    driver.findElement(By.id("customer[first_name]")).sendKeys(firstName);
	    driver.findElement(By.id("customer[last_name]")).sendKeys(lastName);
	    driver.findElement(By.id("customer[email]")).sendKeys(email);
	    driver.findElement(By.id("customer[password]")).sendKeys(password);

	    driver.findElement(By.xpath("//button[@class='form__submit button button--primary button--full']")).click();

	    driver.manage().addCookie(new org.openqa.selenium.Cookie("signup_email", email));
	    driver.manage().addCookie(new org.openqa.selenium.Cookie("signup_password", password));
	}
	
	@Test(priority = 9, enabled = false)
	public void LoginPage() throws InterruptedException {
	    Thread.sleep(2000);
	    driver.get("https://smartbuy-me.com/ar/account/login");

	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("window.scrollTo(0,200)");
	    Thread.sleep(2000);

	    if (email != null && password != null) {
	        driver.findElement(By.id("customer[email]")).sendKeys(email);
	        driver.findElement(By.id("customer[password]")).sendKeys(password);
	        Thread.sleep(2000);
	        driver.findElement(By.className("Vtl-Button__Content")).click();
	        Thread.sleep(1000);
	        driver.findElement(By.cssSelector(".form__submit.button.button--primary.button--full")).click();
	    } else {
	        System.err.println("❌ Email or password is null. Make sure testSignup() ran first.");
	    }
	}

	@AfterTest
	public void tearDown() {

	}
}
