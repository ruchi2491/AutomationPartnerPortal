package com.atmecs.partnerportal.testscript;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.atmecs.falcon.automation.ui.selenium.Browser;
import com.atmecs.falcon.automation.ui.seleniuminterfaces.IKeys.KeyType;
import com.atmecs.falcon.automation.util.enums.LocatorType;
import com.atmecs.falcon.automation.util.parser.PropertyParser;
import com.atmecs.falcon.automation.util.parser.XlsReader;
import com.atmecs.falcon.automation.util.reporter.ReportLogService;
import com.atmecs.falcon.automation.util.reporter.ReportLogServiceImpl;
import com.atmecs.partnerportal.Utils.OrderSummaryInfo;
import com.atmecs.partnerportal.dataprovider.ExcelUtils;
import com.atmecs.partnerportal.testsuite.TestSuiteBase;
import com.google.common.base.Function;

public class CheckOutTestScript extends TestSuiteBase {
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
		wait = new WebDriverWait(browser.getDriver(), 10);
	}

	@AfterMethod
	public void closeBrowser() {
		browser.closeBrowser();
	}

	@Test(dataProvider = "ordersummary", dataProviderClass = ExcelUtils.class)
	public void checkoutTest(String username, String password, String customerName, String customerOrgId,
			String projectName, String address1, String address2, String city, String state, String country,
			String postalCode, String customerPhoneNo, String customeEmail, String poNumber) throws Exception {
		System.out.println(username);
		Utils.login(browser, report, username, password);
		addToCart(0);
		OrderSummaryInfo orderInfo = new OrderSummaryInfo(customerName, customerOrgId, projectName, address1, address2,
				city, state, country, postalCode, customerPhoneNo, customeEmail, poNumber);
		checkout(orderInfo);
		Utils.logout(browser, report);

	}

	private void goToOfferingPage() {
		browser.getWait().HardPause(1000);
		report.info("Going to offering tab");
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("offering_tab"));
		browser.getWait().HardPause(2000);
	}

	private void addToCart(int index) {
		goToOfferingPage();
		String itemToAdd = browser.getDriver().findElements(By.xpath(prop.getProperty("offerings"))).get(index)
				.getText();
		report.info("adding to cart: " + itemToAdd);
		browser.getWait().HardPause(2000);
		WebElement element = browser.getDriver().findElements(By.xpath(prop.getProperty("add_to_cart_btns")))
				.get(index);

		element.click();
		browser.refreshBrowser();
		/*
		 * report.info("going to cart");
		 * browser.getClick().performClick(LocatorType.XPATH,prop.getProperty(
		 * "cart_tab")); report.info("Checking item added in the cart");
		 * browser.getWait().HardPause(2000);
		 * 
		 * String actualFound =
		 * browser.getTextField().readText(LocatorType.XPATH,prop.getProperty(
		 * "cart_items"));
		 * VerificationManager.verifyString(actualFound.toUpperCase(),
		 * itemToAdd.toUpperCase(), "");
		 */
	}

	private void checkout(OrderSummaryInfo orderInfo) {
		browser.getWait().HardPause(2000);
		report.info("going to cart");
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("cart_tab")); // clicking
																							// on
																							// checkout
																							// page
																							// (going
																							// to
																							// checkout
																							// page)

		browser.getWait().HardPause(2000);
		report.info("clicking on checkout button");
		browser.getClick().clickImageByXpath(LocatorType.XPATH, prop.getProperty("checkout_btn"));

		browser.getWait().HardPause(2000);
		browser.getTextField().enterTextField(LocatorType.XPATH, getXpathFor("Customer Name"),
				orderInfo.getCustomerName());
		browser.getTextField().enterTextField(LocatorType.XPATH, getXpathFor("Organization ID"),
				orderInfo.getCustomerOrgId());
		browser.getTextField().enterTextField(LocatorType.XPATH, getXpathFor("Project Name"),
				orderInfo.getProjectName());
		browser.getTextField().enterTextField(LocatorType.XPATH, getXpathFor("Customer Address Line 1"),
				orderInfo.getAddress1());
		browser.getTextField().enterTextField(LocatorType.XPATH, getXpathFor("Customer Address Line 2"),
				orderInfo.getAddress2());
		browser.getTextField().enterTextField(LocatorType.XPATH, getXpathFor("Customer City"), orderInfo.getCity());
		browser.getTextField().enterTextField(LocatorType.XPATH, getXpathFor("Customer State"), orderInfo.getState());

		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("select_country_btn"));
		browser.getWait().HardPause(1000);
		browser.getTextField().enterTextField(LocatorType.XPATH, prop.getProperty("select_country_input"),
				orderInfo.getCountry());
		browser.getKey().pressKey(LocatorType.XPATH, prop.getProperty("select_country_input"), KeyType.ARROW_DOWN);
		browser.getKey().pressKey(LocatorType.XPATH, prop.getProperty("select_country_input"), KeyType.ENTER);

		browser.getTextField().enterTextField(LocatorType.XPATH, getXpathFor("Customer Postal Code"),
				orderInfo.getPostalCode());
		browser.getTextField().enterTextField(LocatorType.XPATH, getXpathFor("Customer Phone Number"),
				orderInfo.getCustomerPhoneNo());
		browser.getTextField().enterTextField(LocatorType.XPATH, getXpathFor("Customer Email"),
				orderInfo.getCustomeEmail());
		browser.getTextField().enterTextField(LocatorType.XPATH, getXpathFor("PO Number"), orderInfo.getPoNumber());

		browser.getWait().HardPause(1000);

		report.info("Agree to terms and condition");
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("terms_and_condtion_for_checkout"));
		report.info("Clicking on Buy now");
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("buy_now_for_checkout"));

		browser.getWait().HardPause(2000);

		WebElement msg = browser.getFindFromBrowser().findElementByXpath(prop.getProperty("order_completed_msg"));
		report.info("order completed with msg " + msg.getText());

	}

	static String getXpathFor(String fieldName) {
		return "//input[@placeholder='" + fieldName + "']";
	}

	public static void main(String[] args) throws Exception {
		String fileName = "loginCreds.xlsx";
		String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
				+ File.separator + "resources" + File.separator + fileName;
		XlsReader reader = new XlsReader();
		reader.setPath(filePath);
		int columnCount = reader.getColumnCount("order_summary");
		System.out.println("columnCount= " + columnCount);

	}

	public void testWaits() throws Exception {

		// explicit wait
		Wait<WebDriver> explicitWait = new WebDriverWait(browser.getDriver(), 30);
		explicitWait
				.until(ExpectedConditions.visibilityOf(browser.getFindFromBrowser().findElementByXpath("xpath here")));

		// implicit wait
		browser.getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		WebElement element = browser.getFindFromBrowser().findElementByXpath("xpath here");

		// Fluent wait
		Wait<WebDriver> fluentWait = new FluentWait<WebDriver>(browser.getDriver()).withTimeout(60, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

		WebElement reqElement = fluentWait.until(new Function<WebDriver, WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				WebElement element = driver.findElement(By.xpath("xpath here"));
				return element;
			}
		});

		String reqStr = fluentWait.until(new Function<WebDriver, String>() {
			@Override
			public String apply(WebDriver driver) {
				return driver.findElement(By.xpath("xpath here")).getText();
			}
		});

	}
}
