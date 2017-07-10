package com.atmecs.partnerportal.testscript;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.atmecs.falcon.automation.ui.selenium.Browser;
import com.atmecs.falcon.automation.util.enums.LocatorType;
import com.atmecs.falcon.automation.util.parser.PropertyParser;
import com.atmecs.falcon.automation.util.reporter.ReportLogService;
import com.atmecs.falcon.automation.util.reporter.ReportLogServiceImpl;
import com.atmecs.falcon.automation.verifyresult.VerificationManager;
import com.atmecs.partnerportal.testsuite.TestSuiteBase;

public class AddToCartAndVerifyTestScript extends TestSuiteBase {

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
	/*
	 * @AfterMethod public void closeBrowser() { browser.closeBrowser(); }
	 */

	@Test
	@Parameters({ "os", "osVersion", "browser", "browserVersion" })
	public void addToCartAndVerifyTest(String os, String osVersion, String br, String browserVersion) {
		Utils.login(browser, report);
		goToOfferingPage();
		int availableOfferings = getCountForAvailableOffering();
		for (int i = 0; i < availableOfferings; i++) {
			removeItemsFromCart();
			addToCart(i);
		}
		Utils.logout(browser, report);
		browser.closeBrowser();
	}

	private void goToOfferingPage() {
		browser.getWait().HardPause(1000);
		report.info("Going to offering tab");
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("offering_tab"));
		browser.getWait().HardPause(2000);
	}

	private int getCountForAvailableOffering() {
		goToOfferingPage();
		// System.out.println("Going to count");
		return browser.getDriver().findElements(By.xpath(prop.getProperty("offerings"))).size();
	}

	public void addToCart(int index) {
		goToOfferingPage();
		String itemToAdd = browser.getDriver().findElements(By.xpath(prop.getProperty("offerings"))).get(index)
				.getText();
		report.info("adding to cart: " + itemToAdd);
		browser.getWait().HardPause(2000);
		WebElement element = browser.getDriver().findElements(By.xpath(prop.getProperty("add_to_cart_btns")))
				.get(index);

		element.click();
		browser.refreshBrowser();

		report.info("going to cart in add to cart");
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("cart_tab"));
		report.info("Checking item added in the cart");
		browser.getWait().HardPause(2000);

		String actualFound = browser.getTextField().readText(LocatorType.XPATH, prop.getProperty("cart_items"));
		report.info("going to page where continue shopping and checkout button is their");
		VerificationManager.verifyString(actualFound.toUpperCase(), itemToAdd.toUpperCase(), "");
	}

	public void removeItemsFromCart() {
		report.info("going to cart page");
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("cart_tab"));
		browser.getWait().HardPause(2000);

		List<WebElement> list = browser.getDriver().findElements(By.xpath(prop.getProperty("remove_from_cart_btns")));

		if (list.isEmpty()) {
			report.info("The cart is empty");
			return;
		}
		report.info("Total " + list.size() + "items found in cart");
		System.out.println("the total items in cart=" + list.size());

		List<WebElement> listOfItemsAdded = browser.getDriver().findElements(By.xpath(prop.getProperty("cart_items")));
		report.info("Items found in cart are:");
		for (WebElement item : listOfItemsAdded) {
			report.info(item.getText());
		}
		for (WebElement element : list) {
			element.click();
		}
		report.info("All items has been removed");

	}
}
