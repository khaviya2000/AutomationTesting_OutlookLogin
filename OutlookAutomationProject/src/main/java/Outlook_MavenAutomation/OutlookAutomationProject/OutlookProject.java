package Outlook_MavenAutomation.OutlookAutomationProject;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class OutlookProject {

	WebDriver driver;

	@BeforeMethod
	public void setEnv() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\deena\\Documents\\Software\\chromedriver-win64\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.setBinary("C:\\Users\\deena\\Documents\\Software\\chrome-win64\\chrome.exe");
	    driver = new ChromeDriver(options);

		driver.manage().deleteAllCookies();
		driver.get("https://www.outlook.com/");
	}

	@DataProvider
	public Object[][] dataSet() throws Exception {
		File src1 = new File(
				"C:\\Users\\deena\\Documents\\Software\\OutlookAutomationProject\\repository\\testdata.properties");
		FileInputStream fis1 = new FileInputStream(src1);
		Properties pro1 = new Properties();
		pro1.load(fis1);
		Object arr[][] = new Object[1][2];

		arr[0][0] = pro1.getProperty("Testdata1");
		arr[0][1] = pro1.getProperty("Testdata2");

		return arr;
	}

	@Test(dataProvider = "dataSet")
	public void Login(String username, String password) {

		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//span[@class='btn__text']")).click();
		Set<String> window = driver.getWindowHandles();
		System.out.println(window);
		Iterator<String> nexttab = window.iterator();
		String win1 = nexttab.next();
		String win2 = nexttab.next();

		driver.switchTo().window(win2);
		System.out.println("Window :" + win2);

		driver.findElement(By.xpath("//input[@type='email']")).sendKeys(username);
		driver.findElement(By.id("idSIButton9")).click();
		driver.findElement(By.id("i0118")).sendKeys(password);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.id("acceptButton")).click();
		WebDriverWait waiting = new WebDriverWait(driver, 20);
		waiting.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='textContainer label-193']")))
				.click();
		driver.findElement(By.xpath("//div[@role='combobox']")).sendKeys("<RECEIVER EMAIL>");
		driver.findElement(By.xpath("//input[@placeholder='Add a subject']")).sendKeys("<SUBJECT>");
		driver.findElement(By.xpath("//div[@role='textbox']")).sendKeys("<EMAIL BODY>");
		driver.findElement(By.xpath("//*[text()='Send']")).click();

	}

}
