package com.atmecs.partnerportal.testscript;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.atmecs.falcon.automation.ui.selenium.Browser;
import com.atmecs.falcon.automation.util.parser.PropertyParser;
import com.atmecs.falcon.automation.util.reporter.ReportLogService;
import com.atmecs.falcon.automation.util.reporter.ReportLogServiceImpl;

public class PartnerSurveyTest {

	static Properties prop = null;
	private ReportLogService report = new ReportLogServiceImpl(LoginTestScript.class);
	public static WebDriverWait wait = null;
	private static final String PROPERTY_FILE_NAME = "partners.properties";

	enum FILTER_BY_STATUS {
		IN_PROGRESS, OPEN, COMPLETE
	}

	Browser browser = null;

	@BeforeClass
	public void preInitialize() throws IOException {
		prop = new PropertyParser().loadProperty(Utils.getPathFor(PROPERTY_FILE_NAME));

	}

	@BeforeMethod
	@Parameters({ "os", "osVersion", "browser", "browserVersion" })
	public void initializeBrowser(String os, String osVersion, String br, String browserVersion) throws IOException {
		browser = new Browser();
		Utils.initialize(browser, report, os, osVersion, br, browserVersion);
		wait = new WebDriverWait(browser.getDriver(), 10);

	}

	@AfterMethod
	public void closeBrowser() {
		browser.closeBrowser();
	}

	@Test
	public void searchByProjectName() throws InterruptedException {
		Utils.loginAdmin(browser, report);
		Thread.sleep(1000);
		browser.getDriver().findElement(By.xpath("//a[contains(text(),'Partner Survey')]")).click();
		Thread.sleep(1000);
		WebElement element = browser.getDriver()
				.findElement(By.xpath("//*[@ ng-model='PSController.survey.project_name']"));
		element.click();
		element.sendKeys(Keys.ARROW_DOWN);
		element.sendKeys(Keys.ENTER);
		browser.getDriver().findElement(By.xpath("//button[contains(text(),'Search')]")).click();
	}

	@Test
	public void searchByCompanyName() throws InterruptedException {
		Utils.loginAdmin(browser, report);
		Thread.sleep(1000);
		browser.getDriver().findElement(By.xpath("//a[contains(text(),'Partner Survey')]")).click();
		Thread.sleep(1000);
		WebElement element = browser.getDriver()
				.findElement(By.xpath("//*[@ng-model='PSController.survey.company_name']"));
		element.click();
		element.sendKeys(Keys.ARROW_DOWN);
		element.sendKeys(Keys.ENTER);
		browser.getDriver().findElement(By.xpath("//button[contains(text(),'Search')]")).click();
	}

	@Test
	public void searchByQuarter() throws InterruptedException {
		Utils.loginAdmin(browser, report);
		Thread.sleep(1000);
		browser.getDriver().findElement(By.xpath("//a[contains(text(),'Partner Survey')]")).click();
		Thread.sleep(1000);
		WebElement element = browser.getDriver()
				.findElement(By.xpath("//*[@ng-change='PSController.selectQuarter()']"));
		element.click();
		element.sendKeys(Keys.ARROW_DOWN);
		element.sendKeys(Keys.ENTER);
		browser.getDriver().findElement(By.xpath("//button[contains(text(),'Search')]")).click();
	}

	@Test
	public void searchByUnanswered() throws InterruptedException {
		Utils.loginAdmin(browser, report);
		Thread.sleep(1000);
		browser.getDriver().findElement(By.xpath("//a[contains(text(),'Partner Survey')]")).click();
		Thread.sleep(1000);
		browser.getDriver().findElement(By.xpath("//input[@type='checkbox']")).click();
		browser.getDriver().findElement(By.xpath("//button[contains(text(),'Search')]")).click();
	}

	/*
	 * @Test public void resetAll() throws InterruptedException {
	 * Thread.sleep(1000);
	 * driver.findElement(By.xpath("//button[contains(text(),'Reset All')]")).
	 * click(); }
	 */

}
