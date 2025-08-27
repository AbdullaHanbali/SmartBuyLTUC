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
    String phoneValue = "0790411538";


	@BeforeTest
	public void mySetup() {
		
		driver.get(TheURL);
		
		driver.manage().window().maximize();
		
	}

	@Test(priority = 1, enabled = true)
	public void homePageAccessability() throws InterruptedException {

	    String title = driver.getTitle();
	    Assert.assertTrue(title != null && !title.trim().isEmpty(), "Page title is empty");
	    Assert.assertFalse(title.contains("404") || title.toLowerCase().contains("error"),
	            "Page title indicates an error: " + title);

	    Assert.assertTrue(driver.findElements(By.tagName("header")).size() > 0, "Header not found");
	    Assert.assertTrue(driver.findElements(By.tagName("footer")).size() > 0, "Footer not found");

	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    Number heightNum = (Number) js.executeScript("return document.body.scrollHeight");
	    long height = heightNum.longValue();

	    for (int i = 0; i < height; i += 100) {
	        js.executeScript("window.scrollBy(0, 100);");
	        Thread.sleep(100);
	    }

	    long scrollBottom = ((Number) js.executeScript("return window.pageYOffset;")).longValue();
	    
	    //نتاكد انه عمل سكرول للاسفل
	    Assert.assertTrue(scrollBottom > 0, "Page did not scroll down");

	    Thread.sleep(1000);

	    for (int i = 0; i < height; i += 100) {
	        js.executeScript("window.scrollBy(0, -100);");
	        Thread.sleep(100);
	    }

	    long scrollTop = ((Number) js.executeScript("return window.pageYOffset;")).longValue();
	    
	    //نتاكد انه عمل سكرول للاعلى
	    Assert.assertEquals(scrollTop, 0, "Page did not scroll back to top");
	}

	@Test(priority = 2, enabled = true)
	public void ProductCategoryNavigation() throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    Actions actions = new Actions(driver);

	    WebElement shopByCategory = wait.until(ExpectedConditions.elementToBeClickable(
	        By.xpath("//*[@id=\"shopify-section-sections--23554635530550__header\"]/section/nav/div/div/ul/li[2]/a")
	    ));
	    
	    //موجود فعليا Shop by Category  نتاكد ان زر   
	    Assert.assertTrue(shopByCategory.isDisplayed(), "Shop by Category link not found!");

	    actions.moveToElement(shopByCategory).click().build().perform();
	    Thread.sleep(2000);

	    List<WebElement> subCategories = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
	        By.cssSelector("a.nav-dropdown__link.menu-toggle")
	    ));

	    // فلترة العناصر بدون نص
	    subCategories = subCategories.stream()
	        .filter(e -> !e.getText().trim().isEmpty())
	        .toList();

	    // تأكد أن هناك عناصر بعد الفلترة
	    Assert.assertTrue(subCategories.size() > 0, "No visible subcategories found!");

	    // اختر عنصر عشوائي
	    int randomIndex = new Random().nextInt(subCategories.size());
	    WebElement randomSubCategory = subCategories.get(randomIndex);
	    String chosenCategory = randomSubCategory.getText().trim();

	    // كبسة أولى (فتح القائمة)
	    actions.moveToElement(randomSubCategory).click().build().perform();
	    wait.until(ExpectedConditions.elementToBeClickable(randomSubCategory));
	    Thread.sleep(2000);

	    // كبسة ثانية (الدخول للصفحة)
	    actions.moveToElement(randomSubCategory).click().build().perform();
	    wait.until(ExpectedConditions.urlContains("collections"));
	    Thread.sleep(3000);

	    String currentUrl = driver.getCurrentUrl();
	    
	    // shop by category نتاكد انه احنا بالصفحة الخاصة ب 
	    Assert.assertTrue(currentUrl.contains("collections") || currentUrl.contains("category"),
	            "Did not navigate to category page!");

	    String pageSource = driver.getPageSource().toLowerCase();
	    
	    // نتاكد من وجود العنصر المختار في الصفحة
	    Assert.assertTrue(pageSource.contains(chosenCategory.toLowerCase()),
	            "Chosen category name not found on the page!");
	}
	
	@Test(priority = 3, enabled = true)
	public void accessPromotionsPage() throws InterruptedException {
		
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // اضغط على لينك Promotions
	    WebElement promotionsLink = wait.until(
	        ExpectedConditions.elementToBeClickable(By.cssSelector("a.nav-bar__link.link[href='/collections/promotions']"))
	    );
	    promotionsLink.click();
	    
	    Thread.sleep(2000);

	    // استنى تحميل الصفحة
	    wait.until(ExpectedConditions.urlContains("/collections/promotions"));

	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    Number pageHeight = (Number) js.executeScript("return document.body.scrollHeight");
	    long height = pageHeight.longValue();

	    // Scroll down gradually to bottom
	    for (int i = 0; i < height; i += 100) {
	        js.executeScript("window.scrollBy(0, 100);");
	        Thread.sleep(100);
	    }

	    long scrollBottom = ((Number) js.executeScript("return window.pageYOffset;")).longValue();

	    // نتاكد من النزول لاخر الصفحة
	    Assert.assertTrue(scrollBottom > 0, "Page did not scroll down");

	    // Wait 2 seconds at bottom
	    Thread.sleep(1000);

	    // Scroll up gradually to top
	    for (int i = 0; i < height; i += 100) {
	        js.executeScript("window.scrollBy(0, -100);");
	        Thread.sleep(100);
	    }

	    long scrollTop = ((Number) js.executeScript("return window.pageYOffset;")).longValue();

	    // نتاكد من الطلوع لاول الصفحة
	    Assert.assertEquals(scrollTop, 0, "Page did not scroll back to top");

	    List<WebElement> products = driver.findElements(By.cssSelector(".product-item"));
	    
	    // Assertion: وجود منتجات
	    Assert.assertTrue(products.size() > 0, "ما في منتجات بصفحة Promotions");

	    List<WebElement> prices = driver.findElements(By.cssSelector(".price, .product-price"));
	    
	    // Assertion: وجود أسعار
	    Assert.assertTrue(prices.size() > 0, "الأسعار غير ظاهرة بالصفحة");
	}

	@Test(priority = 4, enabled = true)
	public void ProductSearchFunctionality() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // ابحث عن مربع البحث وأدخل النص
	    WebElement searchBox = wait.until(
	        ExpectedConditions.presenceOfElementLocated(By.name("q"))
	    );
	    searchBox.sendKeys("iphone 14");

	    // اضغط على زر البحث
	    WebElement searchButton = wait.until(
	        ExpectedConditions.elementToBeClickable(By.className("search-bar__submit"))
	    );
	    searchButton.click();

	    // استنى تحميل صفحة النتائج
	    wait.until(ExpectedConditions.urlContains("/search"));

	    List<WebElement> results = driver.findElements(By.cssSelector(".product-item"));
	    
	    // Assertion: تحقق من ظهور منتجات في نتائج البحث
	    Assert.assertTrue(results.size() > 0, "ما في نتائج لبحث iphone 14");

	    String firstProductName = results.get(0).getText().toLowerCase();
	    
	    // Assertion إضافي: تحقق أن اسم أول منتج يحتوي على iPhone 14 
	    Assert.assertTrue(firstProductName.contains("iphone 14"), 
	        "أول منتج ما له علاقة بـ iphone 14");
	}

	@Test(priority = 5, enabled = true)
	public void languageSelection() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // اضغط على زر تغيير اللغة
	    WebElement languageSwitcher = wait.until(
	        ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='localization_form_header_locale']/div/button"))
	    );
	    languageSwitcher.click();

	    // اختر اللغة العربية من القائمة
	    WebElement chooseArabic = wait.until(
	        ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='desktop-locale-selector']/div/ul/li[2]/button"))
	    );
	    chooseArabic.click();

	    wait.until(ExpectedConditions.urlContains("/ar/"));
	    
	    // نتاكد ان اللغة تغيرت للعربي    
	    Assert.assertTrue(driver.getCurrentUrl().contains("/ar/"), "Arabic language is not applied (URL check).");
	}

	@Test(priority = 6, enabled = true)
	public void testSignup() {
		
	    driver.get("https://smartbuy-me.com/ar/account/register");

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("window.scrollTo(0,150)");

	    System.out.println("Signup email: " + email);
	    System.out.println("Password: " + password);

	    // إدخال بيانات التسجيل
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("customer[first_name]"))).sendKeys(firstName);
	    driver.findElement(By.id("customer[last_name]")).sendKeys(lastName);
	    driver.findElement(By.id("customer[email]")).sendKeys(email);
	    driver.findElement(By.id("customer[password]")).sendKeys(password);

	    // الضغط على زر "تسجيل"
	    wait.until(ExpectedConditions.elementToBeClickable(
	        By.xpath("//button[@class='form__submit button button--primary button--full']"))).click();

	    // حفظ الكوكيز
	    driver.manage().addCookie(new org.openqa.selenium.Cookie("signup_email", email));
	    driver.manage().addCookie(new org.openqa.selenium.Cookie("signup_password", password));

	    // الانتقال لصفحة الحساب للتأكد من تسجيل الدخول
	    driver.get("https://smartbuy-me.com/ar/account");

	    WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(
	        By.xpath("//a[@href='/ar/account/logout' and contains(text(),'تسجيل الخروج')]")));
	    
	    // Assertion 1: التأكد من تسجيل الدخول (وجود زر تسجيل الخروج في صفحة حسابي)
	    Assert.assertTrue(logoutButton.isDisplayed(), "لم يتم تسجيل الدخول بنجاح");

	    // الضغط على "تسجيل الخروج"
	    logoutButton.click();

	    // الانتظار لإعادة تحميل الصفحة الرئيسية
	    wait.until(ExpectedConditions.urlToBe("https://smartbuy-me.com/ar"));

	    WebElement loginOrCreateLink = wait.until(ExpectedConditions.presenceOfElementLocated(
	        By.xpath("//a[contains(@href,'/account/login')]")));
	    
	    // Assertion 2: التأكد من تسجيل الخروج **آخر شيء
	    Assert.assertNotNull(loginOrCreateLink, "تسجيل الخروج لم يتم بنجاح");
	}
	
	@Test(priority = 7, enabled = true)
	public void loginPage() {
	    driver.get("https://smartbuy-me.com/ar/account/login");

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("window.scrollTo(0,200)");

	    if (email != null && password != null) {
	        // إدخال بيانات الدخول
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("customer[email]"))).sendKeys(email);
	        driver.findElement(By.id("customer[password]")).sendKeys(password);

	        // الضغط على زر تسجيل الدخول
	        WebElement loginButton = driver.findElement(By.cssSelector("button.form__submit.button.button--primary.button--full"));
	        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();

	        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//a[@href='/ar/account/logout' and contains(text(),'تسجيل الخروج')]")));
	        
	        // Assertion: التأكد من تسجيل الدخول (وجود زر تسجيل الخروج في الهيدر أو صفحة الحساب)
	        Assert.assertTrue(logoutButton.isDisplayed(), "تسجيل الدخول فشل: زر تسجيل الخروج غير موجود");

	    } else {
	        System.err.println("❌ Email or password is null. Make sure testSignup() ran first.");
	    }
	}
	
	@Test(priority = 8, enabled = true)
	public void addRandomProductToCart() throws InterruptedException {

	    driver.get("https://smartbuy-me.com/");

	    String[] sections = {"Featured Products", "Promotions", "New Arrivals", "Trending Now"};
	    String selectedSection = sections[rand.nextInt(sections.length)];
	    System.out.println("Selected section: " + selectedSection);

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // section لازم يبين
	    WebElement section = wait.until(ExpectedConditions.visibilityOfElementLocated(
	        By.xpath("//h2[contains(@class,'section__title') and contains(text(),'" + selectedSection + "')]/ancestor::section")
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

	    // استنى يكون clickable
	    wait.until(ExpectedConditions.elementToBeClickable(randomItem)).click();

	    // استنى زر add to cart
	    WebElement addToCartBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
	        By.xpath("//button[contains(@class,'product-form__add-button')]")
	    ));

	    // assertion انه كبسة الاضافه للكارت ظاهرة 
	    Assert.assertTrue(addToCartBtn.isDisplayed(), "Add to Cart button not displayed!");

	    if (addToCartBtn.getText().trim().equalsIgnoreCase("Sold out")) {
	        System.out.println("Product is Sold Out.");
	        return;
	    }

	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartBtn);

	    wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();

	    // استنى زر الإغلاق يبين
	    WebElement closeBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
	        By.xpath("//*[name()='svg' and contains(@class,'fa-times')]")
	    ));

	    // assertion انه البوب أب فتح
	    Assert.assertTrue(closeBtn.isDisplayed(), "Popup did not appear after adding product!");

	    // كلك على الإغلاق
	    closeBtn.click();

	    // assertion انه البوب أب اختفى
	    Assert.assertTrue(
	    	    wait.until(ExpectedConditions.invisibilityOf(closeBtn)),
	    	    "Popup did not disappear after closing!"
	    	);
	    System.out.println("Product added to cart and popup closed successfully.");

	    // رجوع للهوم
	    driver.get("https://smartbuy-me.com/");

	    // تحقق من الهوم بيج باستخدام URL بدلاً من اللوجو
	    String currentUrl = driver.getCurrentUrl();
	    Assert.assertEquals(currentUrl, "https://smartbuy-me.com/", "Not on Home Page!");

	    System.out.println("Successfully verified Home Page by URL.");
	    
	}
	
	@Test(priority = 9, enabled = true)
	public void UpdateToCart() throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	    // انتظر أيقونة الكارت تكون قابلة للنقر قبل الضغط
	    WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(
	        By.cssSelector("a[href='/cart']")
	    ));
	    cartIcon.click();

	    // انتظر ظهور Mini Cart نفسه، بدل الاعتماد على العناصر الداخلية
	    WebElement miniCart = wait.until(ExpectedConditions.visibilityOfElementLocated(
	        By.id("mini-cart")
	    ));
	    // Assertion: نتأكد أن Mini Cart ظاهر
	    Assert.assertTrue(miniCart.isDisplayed(), "Mini Cart did not appear!");

	    // اضغط على زر زيادة الكمية كما هو (لا تغيير بالكبسات أو Xpath)
	    WebElement increaseButton = driver.findElement(By.xpath(
	        "//button[contains(@class,'quantity-selector__button') and @data-action='increase-quantity' and @data-line='1']"
	    ));
	    increaseButton.click();

	    // ننتظر تحديث Mini Cart بعد الضغط على زر الزيادة (يمكنه أن يكون هناك animation)
	    Thread.sleep(2000); // انتظار بسيط لتجنب مشاكل الـ animation

	    // اضغط أيقونة الكارت مرة ثانية للتأكد من أن الكارت ما زال ظاهر بعد التحديث
	    cartIcon.click();

	    WebElement updatedMiniCart = wait.until(ExpectedConditions.visibilityOfElementLocated(
	        By.id("mini-cart")
	    ));
	    
	    // Assertion: نتحقق أن Mini Cart ما زال ظاهر بعد التحديث
	    Assert.assertTrue(updatedMiniCart.isDisplayed(), "Mini Cart is not visible after updating quantity!");
	}
	
	@Test(priority = 10, enabled = true)
	public void removeFromCart() throws InterruptedException {

	    Thread.sleep(2000); // انتظار بسيط قبل فتح الكارت

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	    // انتظر أيقونة الكارت تكون قابلة للنقر قبل الضغط
	    WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(
	        By.cssSelector("a[href='/cart']")
	    ));
	    cartIcon.click();

	    // انتظر ظهور زر تقليل الكمية قبل الضغط
	    WebElement decreaseButton = wait.until(ExpectedConditions.elementToBeClickable(
	        By.xpath("//button[contains(@class,'quantity-selector__button') and @data-action='decrease-quantity' and @data-line='1']")
	    ));
	    decreaseButton.click();

	    // انتظر اختفاء المنتج داخل Mini Cart بعد تقليل الكمية
	    wait.until(ExpectedConditions.invisibilityOfElementLocated(
	        By.cssSelector("#mini-cart .mini-cart__item")
	    ));
	    
	    List<WebElement> cartItems = driver.findElements(By.cssSelector("#mini-cart .mini-cart__item"));
	    
	    // Assertion: نتأكد أن المنتج اختفى فعليًا من الكارت
	    Assert.assertTrue(cartItems.isEmpty(), "Product was not removed from the cart!");

	    Thread.sleep(2000); // انتظار بسيط لتجنب أي أنيميشن

	    // اضغط أيقونة الكارت مرة ثانية للتأكد أن Mini Cart نفسه ما زال ظاهر
	    cartIcon.click();
	    WebElement miniCart = wait.until(ExpectedConditions.visibilityOfElementLocated(
	        By.id("mini-cart")
	    ));
	    
	    // Assertion: نتأكد أن Mini Cart ظاهر بعد إزالة المنتج
	    Assert.assertTrue(miniCart.isDisplayed(), "Mini Cart is not visible after removing product!");
	}
	
	@Test(priority = 11, enabled = true)
	public void ShowCartPage() throws InterruptedException {

	    Thread.sleep(2000);

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	    // اضغط على أيقونة الكارت
	    WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(
	        By.xpath("(//span[@class='icon-state__primary'])[3]")
	    ));
	    cartIcon.click();

	    Thread.sleep(2000);

	    // اضغط على "View cart"
	    WebElement viewCartLink = wait.until(ExpectedConditions.elementToBeClickable(
	        By.xpath("//a[normalize-space()='View cart'][1]")
	    ));
	    viewCartLink.click();

	    // انتظار بسيط للتأكد أن الصفحة انتقلت للـ Cart URL
	    wait.until(ExpectedConditions.urlContains("/cart"));
	}
	
	@Test(priority = 12, enabled = true)
	public void CheckOut() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    String phoneValue = "0785075401"; // رقم الهاتف المستخدم

	    // اضغط على زر Checkout
	    WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(By.name("checkout")));
	    checkoutBtn.click();
	    System.out.println("Clicked on Checkout button.");

	    // اضغط على Marketing opt-in مع Refresh
	    WebElement marketingOptIn = wait.until(ExpectedConditions.refreshed(
	            ExpectedConditions.elementToBeClickable(By.name("marketing_opt_in"))
	    ));
	    marketingOptIn.click();
	    System.out.println("Marketing opt-in clicked.");

	    // تعبئة عنوان الشحن
	    WebElement addressInput = wait.until(ExpectedConditions.refreshed(
	            ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Address']"))
	    ));
	    addressInput.clear();
	    addressInput.sendKeys(addressField);
	    
	    // نتاكد ان تم تعبئة الخانة بالقيمة الصحيحة
	    Assert.assertEquals(addressInput.getAttribute("value"), addressField, "Address field not filled correctly!");

	    // تعبئة Apartment
	    WebElement apartmentInput = wait.until(ExpectedConditions.refreshed(
	            ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Apartment, suite, etc.']"))
	    ));
	    apartmentInput.clear();
	    apartmentInput.sendKeys("5");
	    
	    // نتاكد ان تم تعبئة الخانة بالقيمة الصحيحة
	    Assert.assertEquals(apartmentInput.getAttribute("value"), "5", "Apartment field not filled correctly!");

	    // تعبئة المدينة
	    WebElement cityInput = wait.until(ExpectedConditions.refreshed(
	            ExpectedConditions.visibilityOfElementLocated(By.name("city"))
	    ));
	    cityInput.clear();
	    cityInput.sendKeys(addressField);
	    
	    // نتاكد ان تم تعبئة الخانة بالقيمة الصحيحة
	    Assert.assertEquals(cityInput.getAttribute("value"), addressField, "City field not filled correctly!");

	    // تعبئة رمز البريد
	    WebElement postalCodeInput = wait.until(ExpectedConditions.refreshed(
	            ExpectedConditions.visibilityOfElementLocated(By.name("postalCode"))
	    ));
	    postalCodeInput.clear();
	    postalCodeInput.sendKeys("11118");
	    
	    // نتاكد ان تم تعبئة الخانة بالقيمة الصحيحة
	    Assert.assertEquals(postalCodeInput.getAttribute("value"), "11118", "Postal Code field not filled correctly!");

	    // تعبئة رقم الهاتف بدون الفراغات
	    WebElement phoneInput = wait.until(ExpectedConditions.refreshed(
	            ExpectedConditions.visibilityOfElementLocated(By.name("phone"))
	    ));
	    phoneInput.clear();
	    phoneInput.sendKeys(phoneValue);
	    String actualPhone = phoneInput.getAttribute("value").replaceAll("\\s+", "");
	    
	    // نتاكد ان تم تعبئة الخانة بالقيمة الصحيحة
	    Assert.assertEquals(actualPhone, phoneValue, "Phone field not filled correctly!");

	    // اختيار طريقة الشحن
	    WebElement shippingMethod = wait.until(ExpectedConditions.refreshed(
	            ExpectedConditions.elementToBeClickable(By.name("shipping_methods"))
	    ));
	    shippingMethod.click();
	    
	    //نتاكد من اختيار shipping method
	    Assert.assertTrue(shippingMethod.isDisplayed(), "Shipping method not selected!");

	    // اضغط على زر الدفع النهائي مع Refresh
	    WebElement payButton = wait.until(ExpectedConditions.refreshed(
	            ExpectedConditions.elementToBeClickable(By.id("checkout-pay-button"))
	    ));
	    payButton.click();
	    
	    // تم الضغط على pay button
	    Assert.assertTrue(payButton.isDisplayed(), "Pay button not clicked!");

	    System.out.println("Checkout completed successfully.");

	    // العودة للهوم بعد إتمام العملية
	    driver.get("https://smartbuy-me.com/");
	    Assert.assertTrue(driver.getCurrentUrl().contains("smartbuy-me.com"), "Home page did not load!");
	}
	
	@Test(priority = 13, enabled = true)
	public void productDetailAccuracy() throws InterruptedException {
	    driver.get("https://smartbuy-me.com/");

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // اختار أول منتج
	    WebElement firstProduct = wait.until(
	        ExpectedConditions.elementToBeClickable(By.cssSelector(".product-item__title"))
	    );
	    String productNameOnGrid = firstProduct.getText().trim();
	    firstProduct.click();

	    // تحقق من اسم المنتج في صفحة التفاصيل
	    WebElement productNameOnDetail = wait.until(
	        ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1.product-meta__title"))
	    );
	    
	    // اسيرشن: نتأكد أن اسم المنتج في Detail page مطابق لاسم المنتج في Grid
	    Assert.assertEquals(productNameOnDetail.getText().trim(), productNameOnGrid, "Product name mismatch!");

	    // تحقق من ظهور الصورة
	    WebElement productImage = driver.findElement(By.cssSelector(".product-gallery__image"));
	    
	    // اسيرشن: نتأكد أن صورة المنتج ظاهرة
	    Assert.assertTrue(productImage.isDisplayed(), " Product image is not displayed!");

	    // تحقق من السعر
	    List<WebElement> productPrices = driver.findElements(
	        By.cssSelector("span.price, span.price--highlight, span.price--compare")
	    );
	    
	    // اسيرشن: نتأكد أن السعر ظاهر (أي نوع: عادي أو خصم)
	    Assert.assertTrue(!productPrices.isEmpty(), "Product price is not displayed!");

	    // تحقق من التوفر
	    List<WebElement> quantityBox = driver.findElements(By.cssSelector("input.quantity-selector__value"));
	    List<WebElement> soldOutLabel = driver.findElements(By.cssSelector(".product-form__add-button[disabled]"));

	    // اسيرشن: نتأكد أن هناك إما حقل quantity أو زر Sold out
	    Assert.assertTrue(
	        !quantityBox.isEmpty() || !soldOutLabel.isEmpty(),
	        "Availability info is missing! (No quantity input or sold-out label found)"
	    );
	}
	 
	@Test(priority = 14, enabled = true)
	public void responsiveDesignOnMobileDevices() throws InterruptedException {

	    driver.get("https://smartbuy-me.com");

	    // ضبط حجم الشاشة لمحاكاة جهاز موبايل
	    driver.manage().window().setSize(new Dimension(375, 812));

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // انتظر ظهور أيقونة القائمة على الموبايل
	    WebElement menuIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(
	        By.xpath("//button[contains(@class, 'header__mobile-nav-toggle') and contains(@class, 'icon-state') and contains(@class, 'touch-area')]")
	    ));
	    // اسيرشن: نتأكد أن أيقونة القائمة ظاهرة
	    Assert.assertTrue(menuIcon.isDisplayed(), "Mobile menu icon is not visible!");

	    // اضغط على أيقونة القائمة
	    menuIcon.click();

	    // انتظر ظهور القائمة الجانبية للموبايل
	    WebElement mobileMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mobile-menu")));
	    // اسيرشن: نتأكد أن القائمة الجانبية ظهرت
	    Assert.assertTrue(mobileMenu.isDisplayed(), "Menu drawer is not visible!");
	}
	 
	@AfterTest
	public void tearDown() {

	}
}
