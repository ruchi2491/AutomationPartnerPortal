package com.atmecs.partnerportal.testscript;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.atmecs.falcon.automation.ui.selenium.Browser;
import com.atmecs.falcon.automation.util.enums.LocatorType;
import com.atmecs.falcon.automation.util.parser.PropertyParser;
import com.atmecs.falcon.automation.util.reporter.ReportLogService;
import com.atmecs.falcon.automation.util.reporter.ReportLogServiceImpl;
import com.atmecs.partnerportal.Utils.OrderSummaryInfo;

public class ProjectSchedulingTestScript {
	private static final String PROPERTY_FILE_NAME = "partners.properties";
	public static WebDriverWait wait = null;
	static Properties prop = null;

	private ReportLogService report = new ReportLogServiceImpl(LoginTestScript.class);

	enum OFFERING {
		APACHE_FLUM, LAUNCHING_PROJECT, WEB_HOSTING
	}

	Browser browser = null;
	OrderSummaryInfo orderInfo;

	@BeforeMethod
	@Parameters({ "os", "osVersion", "browser", "browserVersion" })
	public void initializeBrowser(String os, String osVersion, String br, String browserVersion) throws IOException {
		browser = new Browser();
		prop = new PropertyParser().loadProperty(Utils.getPathFor(PROPERTY_FILE_NAME));
		Utils.initialize(browser, report, os, osVersion, br, browserVersion);
		// wait = new WebDriverWait(browser.getDriver(), 10);
	}

	@AfterMethod
	public void closeBrowser() {
		Utils.logout(browser, report);
		browser.closeBrowser();
	}

	@Test
	public void schedulingTest() {
		try {
			goToProject();
			browser.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			browser.getDriver().findElement(By.xpath(prop.getProperty("previousmonth"))).click();
			WebElement sourceElement = browser.getDriver().findElement(By.xpath(prop.getProperty("resource")));
			WebElement destinationElement = browser.getDriver().findElement(By.xpath(prop.getProperty("date")));
			dragAndDrop(sourceElement, destinationElement);
			browser.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			WebElement afterschedulingName = browser.getDriver()
					.findElement(By.xpath(prop.getProperty("scheduledresource")));
			System.out.println(afterschedulingName.isDisplayed());
		} catch (StaleElementReferenceException e) {
			e.toString();
		}
		// Assert.assertTrue(afterschedulingName.isDisplayed());

	}

	public void goToProject() {
		Utils.login(browser, report);
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("project"));
		browser.getWait().HardPause(1000);
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("selectproject"));
		browser.getWait().HardPause(1000);
	}

	public void dragAndDrop(WebElement sourceElement, WebElement destinationElement) {
		(new Actions(browser.getDriver())).dragAndDrop(sourceElement, destinationElement).perform();
	}

	public void extendScheduledResource() {

	}

}
