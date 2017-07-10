package com.atmecs.partnerportal.testscript;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;

import com.atmecs.falcon.automation.ui.selenium.Browser;
import com.atmecs.falcon.automation.util.enums.LocatorType;
import com.atmecs.falcon.automation.util.parser.PropertyParser;
import com.atmecs.falcon.automation.util.reporter.ReportLogService;

public class Utils {

	public static final String URL = "http://localhost:8080/partnersportal/#/login";
	// "http://dev-asdp.citrix.com/" ;
	// "http://55.55.53.13:8080/partnersportal/#/login";

	private static Properties prop = null;
	private static final String propertyFileName = "partners.properties";

	public static void initialize(Browser browser, ReportLogService report, String os, String osVersion, String br,
			String browserVersion) throws IOException {
		System.out.println("getting the path");
		System.out.println(getPathFor(propertyFileName));
		prop = new PropertyParser().loadProperty(getPathFor(propertyFileName));
		report.info("Opening browser: " + br);
		browser.openURL(URL, os, osVersion, br, browserVersion);
		report.info("Maximizing browser window");
		browser.maximizeWindow();
		browser.getWait().HardPause(2000);

	}

	public static void login(Browser browser, ReportLogService report) {
		report.info("Entering username: " + "tarunkumar.singh@atmecs.com");
		browser.getTextField().enterTextField(LocatorType.ID, prop.getProperty("usernameid"),
				"shubham.tripathi@atmecs.com");
		report.info("Entering password: " + "*******");
		browser.getTextField().enterTextField(LocatorType.ID, prop.getProperty("passwordid"), "shubham123");
		report.info("Trying to login");
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("login_btn"));
		browser.getWait().HardPause(2000);
		browser.getFindFromBrowser().findElementByXpath(prop.getProperty("logout_btn"));
	}

	public static void loginAdmin(Browser browser, ReportLogService report) {
		report.info("Entering username: " + "tarunkumar.singh@atmecs.com");
		browser.getTextField().enterTextField(LocatorType.ID, prop.getProperty("usernameid"),
				"ruchira.more@atmecs.com");
		report.info("Entering password: " + "*******");
		browser.getTextField().enterTextField(LocatorType.ID, prop.getProperty("passwordid"), "ruchi123");
		report.info("Trying to login");
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("login_btn"));
		browser.getWait().HardPause(2000);
		browser.getFindFromBrowser().findElementByXpath(prop.getProperty("logout_btn"));
	}

	public static void login(Browser browser, ReportLogService report, String username, String password)
			throws Exception {
		report.info("Entering username: " + username);
		browser.getTextField().enterTextField(LocatorType.ID, prop.getProperty("usernameid"), username);
		report.info("Entering password: " + password);
		browser.getTextField().enterTextField(LocatorType.ID, prop.getProperty("passwordid"), password);
		report.info("Trying to login");
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("login_btn"));
		browser.getWait().HardPause(2000);
		browser.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement logoutBtn = browser.getFindFromBrowser().findElementByXpath(prop.getProperty("logout_btn"));

		if (logoutBtn != null)
			report.info("login successful");
		else {
			report.info("login unsuccessful");
			throw new Exception("login unsuccessful");
		}

	}

	public static void logout(Browser browser, ReportLogService report) {
		report.info("Trying to logout");
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("logout_btn"));
		browser.getWait().HardPause(5000);
		report.info("logout Successful");
	}

	public static String getPathFor(String fileName) {
		String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main"
				+ File.separator + "resources" + File.separator + fileName;
		return filePath;
	}

}
