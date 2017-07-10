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
import com.atmecs.falcon.automation.ui.seleniuminterfaces.IKeys.KeyType;
import com.atmecs.falcon.automation.util.enums.LocatorType;
import com.atmecs.falcon.automation.util.parser.PropertyParser;
import com.atmecs.falcon.automation.util.reporter.ReportLogService;
import com.atmecs.falcon.automation.util.reporter.ReportLogServiceImpl;
import com.atmecs.partnerportal.Utils.OrderSummaryInfo;

public class CheckoutNewTestScript {

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
		browser.closeBrowser();
	}

	@Test
	@Parameters({ "os", "osVersion", "browser", "browserVersion" })
	public void checkoutTest(String os, String osVersion, String br, String browserVersion) {
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

		browser.getWait().HardPause(15000);
		report.info("clicking on checkout button");
		browser.getClick().clickImageByXpath(LocatorType.XPATH, prop.getProperty("checkout_btn"));

		browser.getWait().HardPause(10000);
		browser.getTextField().enterTextField(LocatorType.XPATH, "//input[@name='customerName']", "RuchiAM");
		browser.getTextField().enterTextField(LocatorType.XPATH, "//input[@name='organizationId']", "Ruchi123");
		browser.getTextField().enterTextField(LocatorType.XPATH, "//input[@name='projectName']", "RuchiPro");
		browser.getTextField().enterTextField(LocatorType.XPATH, "//input[@name='addressLine1']", "Lanco");
		browser.getTextField().enterTextField(LocatorType.XPATH, "//input[@name='addressLine2']", "Hills");
		browser.getTextField().enterTextField(LocatorType.XPATH, "//input[@name='companyCity']", "Hyderabad");
		browser.getTextField().enterTextField(LocatorType.XPATH, "//input[@name='companyState']", "Telengana");

		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("select_country_btn"));
		browser.getWait().HardPause(1000);
		browser.getTextField().enterTextField(LocatorType.XPATH, prop.getProperty("select_country_input"), "india");
		browser.getKey().pressKey(LocatorType.XPATH, prop.getProperty("select_country_input"), KeyType.ARROW_DOWN);
		browser.getKey().pressKey(LocatorType.XPATH, prop.getProperty("select_country_input"), KeyType.ENTER);

		browser.getTextField().enterTextField(LocatorType.XPATH, "//input[@name='postCode']", "500089");
		browser.getTextField().enterTextField(LocatorType.XPATH, "//input[@name='mobile']", "7894561230");
		browser.getTextField().enterTextField(LocatorType.XPATH, "//input[@name='email']", "ruchi.more@atmecs.com");
		browser.getTextField().enterTextField(LocatorType.XPATH, "//input[@name='ponumber']", "12345");

		browser.getWait().HardPause(1000);

		report.info("Agree to terms and condition");
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("terms_and_condtion_for_checkout"));
		report.info("Clicking on Buy now");
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("buy_now_for_checkout"));

		browser.getWait().HardPause(10000);
		WebElement msg = browser.getFindFromBrowser().findElementByXpath(prop.getProperty("order_completed_msg"));
		report.info("order completed with msg " + msg.getText());
		browser.getWait().HardPause(1000);

		Utils.logout(browser, report);
	}

}
