package com.atmecs.partnerportal.testscript;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

public class AddToCartTest {

	private static final String PROPERTY_FILE_NAME = "partners.properties";
	public static WebDriverWait wait = null;
	static Properties prop = null;

	private ReportLogService report = new ReportLogServiceImpl(LoginTestScript.class);

	enum OFFERING {
		APACHE_FLUM, LAUNCHING_PROJECT, WEB_HOSTING
	}

	Browser browser = null;

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
		browser.closeBrowser();
	}

	@Test
	@Parameters({ "os", "osVersion", "browser", "browserVersion" })
	public void addToCartTest(String os, String osVersion, String br, String browserVersion) {
		Utils.login(browser, report);
		browser.getWait().HardPause(1000);
		report.info("Going to offering tab");
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("offering_tab"));
		browser.getWait().HardPause(5000);

		System.out.println("before click");

		List<WebElement> offers = browser.getDriver().findElements(By.xpath("//a[@ng-model='test']"));
		System.out.println(offers);
		for (int i = 0; i < offers.size(); i++) {
			System.out.println(offers.get(i).getText());
			offers.get(i).click();
		}
		System.out.println("after click");

		browser.refreshBrowser();
		browser.getWait().HardPause(5000);

		browser.getClick().performClick(LocatorType.XPATH, "//a[@ui-sref='partner.checkout']");

		Utils.logout(browser, report);
	}

}
