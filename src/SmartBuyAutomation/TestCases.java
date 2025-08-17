package SmartBuyAutomation;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
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
    String addressField = "amman";


	@BeforeTest
	public void mySetup() {
		
		driver.get(TheURL);
		
		driver.manage().window().maximize();
		
	}

	@Test(priority = 1, enabled = true)
	public void homePageAccessability() throws InterruptedException {

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

	@Test(priority = 2, enabled = true)
	public void ProductCategoryNavigation() throws InterruptedException {
		
		Thread.sleep(2000);

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

		Thread.sleep(3000);

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
	
	@Test(priority = 3, enabled = true)
	public void ProductSearchFunctionality() throws InterruptedException {
		
		Thread.sleep(2000);
		
		driver.findElement(By.name("q")).sendKeys("iphone 14 plus");
		
		Thread.sleep(1000);
		
		driver.findElement(By.className("search-bar__submit")).click();
	
	}
	
	@Test(priority = 4, enabled = true)
	public void languageSelection() throws InterruptedException {
	    
	    Thread.sleep(2000);

	    WebElement languageSwithcer = driver.findElement(By.xpath("//*[@id=\"localization_form_header_locale\"]/div/button"));
	    languageSwithcer.click();
	    Thread.sleep(2000);

	    WebElement chooseArabic = driver.findElement(By.xpath("//*[@id=\"desktop-locale-selector\"]/div/ul/li[2]/button"));
	    chooseArabic.click();
	    Thread.sleep(2000);

	    // التحقق من اللغة العربية عن طريق الـ URL
	    Assert.assertTrue(driver.getCurrentUrl().contains("/ar/"), "Arabic language is not applied (URL check).");
	}
	
	@Test(priority = 5, enabled = true)
	public void testSignup() throws InterruptedException {
	    
	    driver.get("https://smartbuy-me.com/ar/account/register");
	    Thread.sleep(2000);

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
	
	@Test(priority = 6, enabled = true)
	public void LoginPage() throws InterruptedException {
	    Thread.sleep(2000);
	    driver.get("https://smartbuy-me.com/ar/account/login");

	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("window.scrollTo(0,200)");
	    Thread.sleep(2000);

	    if (email != null && password != null) {
	        driver.findElement(By.id("customer[email]")).sendKeys(email);
	        driver.findElement(By.id("customer[password]")).sendKeys(password);
	        Thread.sleep(1000);
	        driver.findElement(By.cssSelector("button.form__submit.button.button--primary.button--full")).click();
	    } else {
	        System.err.println("❌ Email or password is null. Make sure testSignup() ran first.");
	    }
	}
	
	
	@Test(priority = 7, enabled = true)
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
	
	 @Test(priority = 8, enabled = true)
	    public void UpdateToCart() throws InterruptedException {
		 
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		 WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(
		    By.cssSelector("a[href='/cart']")  
	     ));
	 
		 cartIcon.click();

		 WebElement increaseButton = driver.findElement(By.xpath("//button[contains(@class,'quantity-selector__button') and @data-action='increase-quantity' and @data-line='1']"));
		 increaseButton.click();

		 wait.until(ExpectedConditions.invisibilityOfElementLocated(
		    By.cssSelector("#mini-cart .mini-cart__item")));
		 
		 Thread.sleep(2000);
		 
		 cartIcon.click();
	 }
	
	@Test(priority = 9, enabled = true)
	public void removeFromCart() throws InterruptedException {
		
		 Thread.sleep(2000);
		
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(
	        By.cssSelector("a[href='/cart']")  
	    ));
	    cartIcon.click();

	    WebElement decreaseButton = wait.until(ExpectedConditions.elementToBeClickable(
	        By.xpath("//button[contains(@class,'quantity-selector__button') and @data-action='decrease-quantity' and @data-line='1']")));
	    decreaseButton.click();

	    
	    wait.until(ExpectedConditions.invisibilityOfElementLocated(
	    	    By.cssSelector("#mini-cart .mini-cart__item")));
	    
	    Thread.sleep(2000);
	    
	    cartIcon.click();
	}
	
	@Test(priority = 10, enabled = true)
	public void ShowCartPage() throws InterruptedException {
	    
	    Thread.sleep(2000);

	    driver.findElement(By.xpath("(//span[@class='icon-state__primary'])[3]")).click();
	    Thread.sleep(3000);

	    driver.findElement(By.xpath("//a[normalize-space()='View cart'][1]")).click();
	}
	
	@Test(priority = 11, enabled = true)
    public void CheckOut() throws InterruptedException {
		
        driver.findElement(By.xpath("//button[@name='checkout']")).click();

       
        Thread.sleep(4000);
        
        driver.findElement(By.xpath("//input[@name='marketing_opt_in']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//input[@placeholder='Address']")).sendKeys(addressField);
        driver.findElement(By.xpath("//input[@placeholder='Apartment, suite, etc.']")).sendKeys("5");
        driver.findElement(By.name("city")).sendKeys("Amman");
        driver.findElement(By.name("postalCode")).sendKeys("11118");
        driver.findElement(By.name("phone")).sendKeys("0785075401");
        Thread.sleep(3000);
        driver.findElement(By.name("shipping_methods")).click();
        driver.findElement(By.id("checkout-pay-button")).click();
        
        Thread.sleep(2000);
        
        driver.get("https://smartbuy-me.com/");
	}
	
	@Test(priority = 12, enabled = true)
	public void productDetailAccuracy() throws InterruptedException {
	    driver.get("https://smartbuy-me.com/");

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // اختار أول منتج
	    WebElement firstProduct = wait.until(
	        ExpectedConditions.elementToBeClickable(By.cssSelector(".product-item__title"))
	    );
	    String productNameOnGrid = firstProduct.getText().trim();
	    firstProduct.click();

	    // تحقق من التفاصيل
	    WebElement productNameOnDetail = wait.until(
	        ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1.product-meta__title"))
	    );
	    Assert.assertEquals(productNameOnDetail.getText().trim(), productNameOnGrid, "❌ Product name mismatch!");

	    WebElement productImage = driver.findElement(By.cssSelector(".product-gallery__image"));
	    Assert.assertTrue(productImage.isDisplayed(), "❌ Product image is not displayed!");

	    // ✅ تحقق من السعر (أي نوع: عادي أو خصم)
	    List<WebElement> productPrices = driver.findElements(
	        By.cssSelector("span.price, span.price--highlight, span.price--compare")
	    );
	    Assert.assertTrue(!productPrices.isEmpty(), "❌ Product price is not displayed!");

	    // ✅ تحقق من التوفر (quantity OR Sold out)
	    List<WebElement> quantityBox = driver.findElements(By.cssSelector("input.quantity-selector__value"));
	    List<WebElement> soldOutLabel = driver.findElements(By.cssSelector(".product-form__add-button[disabled]"));

	    Assert.assertTrue(
	        !quantityBox.isEmpty() || !soldOutLabel.isEmpty(),
	        "❌ Availability info is missing! (No quantity input or sold-out label found)"
	    );
	}


	 @Test(priority = 13, enabled = true)
	 public void accessPromotionsPage() throws InterruptedException {
		 
		    Thread.sleep(2000);
	       
			WebElement promotionsLink = driver.findElement(By.cssSelector("a.nav-bar__link.link[href='/collections/promotions']"));
			promotionsLink.click();

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
	 
	 @Test(priority = 14, enabled = true)
	 public void responsiveDesignOnMobileDevices() throws InterruptedException {
		 
		    Thread.sleep(2000);

			driver.get("https://smartbuy-me.com");

		    driver.manage().window().setSize(new Dimension(375, 812));
		    Thread.sleep(2000);

		    WebElement menuIcon = driver.findElement(By.xpath("//button[contains(@class, 'header__mobile-nav-toggle') and contains(@class, 'icon-state') and contains(@class, 'touch-area')]"));
		    Thread.sleep(3000);
		    Assert.assertTrue(menuIcon.isDisplayed(), "Mobile menu icon is not visible!");

		    menuIcon.click();
		    Thread.sleep(1500);

		    WebElement mobileMenu = driver.findElement(By.id("mobile-menu"));
		    Assert.assertTrue(mobileMenu.isDisplayed(), "Menu drawer is not visible!");
	    }
	 
	 
	@AfterTest
	public void tearDown() {

	}
}
