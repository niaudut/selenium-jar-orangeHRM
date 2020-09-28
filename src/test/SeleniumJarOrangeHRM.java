package test;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class SeleniumJarOrangeHRM {
	public static String browser;
	static WebDriver driver;
	
	public static void main(String[] args) {
		SeleniumJarOrangeHRM test = new SeleniumJarOrangeHRM();
		test.setBrowser("Firefox");
		test.setBrowserConfig();
		test.verfyLogin();
		test.verifySearchSystemUsers();
		test.verifyApplyLeave();
	}
	
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	
	public void setBrowserConfig() {
		String projectLocation = System.getProperty("user.dir");
		
		if(browser.contains("Chrome")) {
			System.setProperty("webdriver.chrome.driver", projectLocation+"\\lib\\driver\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		if(browser.contains("Firefox")) {
			System.setProperty("webdriver.gecko.driver", projectLocation+"\\lib\\driver\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
	}
	
	@Test
	public void verfyLogin() {
		driver.get("https://opensource-demo.orangehrmlive.com/");
		driver.findElement(By.id("txtUsername")).sendKeys("Admin");
		driver.findElement(By.id("txtPassword")).sendKeys("admin123");
		driver.findElement(By.id("btnLogin")).click();
		String expectedLogin = "Dashboard";
		String actualLogin = "";
		
		actualLogin = driver.findElement(By.linkText("Dashboard")).getText();
		
		Assert.assertEquals(expectedLogin, actualLogin);
		System.out.println("Login passed");
	}
	
	@Test
	public void verifySearchSystemUsers() {
		driver.findElement(By.id("menu_admin_viewAdminModule")).click();
		WebElement userRole = driver.findElement(By.id("searchSystemUser_userType"));
		Select role = new Select(userRole);
		role.selectByVisibleText("ESS");
		driver.findElement(By.id("searchBtn")).click();
		driver.findElement(By.id("searchSystemUser_userName")).sendKeys("robert.craig");
		driver.findElement(By.id("searchBtn")).click();
		String expectedSearchSystemUsers = "robert.craig";
		String actualSearchSystemUsers = "";
		
		actualSearchSystemUsers = driver.findElement(By.linkText("robert.craig")).getText();
		
		Assert.assertEquals(expectedSearchSystemUsers, actualSearchSystemUsers);
		System.out.println("Search System Users passed");
	}
	
	@Test
	public void verifyApplyLeave() {
		driver.findElement(By.id("menu_leave_viewLeaveModule")).click();
		driver.findElement(By.id("menu_leave_applyLeave")).click();
		WebElement leaveTypeDropDown = driver.findElement(By.id("applyleave_txtLeaveType"));
		Select leaveType = new Select(leaveTypeDropDown);
		leaveType.selectByValue("1");
		driver.findElement(By.id("applyleave_txtFromDate")).sendKeys("2020-10-01");
		driver.findElement(By.id("applyleave_txtToDate")).sendKeys("2020-10-02");
		WebElement partialDaysDropDown = driver.findElement(By.id("applyleave_partialDays"));
		Select partialDays = new Select(partialDaysDropDown);
		partialDays.selectByIndex(1);
		driver.findElement(By.id("applyBtn")).click();
		String expectedVerifyApplyLeave = "Successfully Submitted";
		String actualVerifyApplyLeave = "";
		
		
		actualVerifyApplyLeave = driver.findElement(By.className("message success fadable")).getText();
		Assert.assertEquals(expectedVerifyApplyLeave, actualVerifyApplyLeave);
	}
}
