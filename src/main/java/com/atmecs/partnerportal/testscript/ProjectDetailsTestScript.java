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

public class ProjectDetailsTestScript {

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
		Utils.logout(browser, report);
		browser.closeBrowser();
	}

	@Test(priority = 0)
	public void addResources() {
		goToProject();
		browser.getTextField().enterTextField(LocatorType.XPATH, prop.getProperty("firstnameofresource"), "Hitendra");
		browser.getTextField().enterTextField(LocatorType.XPATH, prop.getProperty("lastnameofresource"), "Chudhari");
		browser.getTextField().enterTextField(LocatorType.XPATH, prop.getProperty("emailofresource"),
				"Hitendra.chudhari@gmail.com");
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("addresource"));

	}

	@Test(priority = 1)
	public void checkProjectStatus() throws InterruptedException {
		goToProject();
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("editprojectstatus"));
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("choseprojectstatus"));
		Thread.sleep(1000);
		browser.getKey().pressKey(LocatorType.XPATH, prop.getProperty("choseprojectstatus"), KeyType.ARROW_DOWN);
		browser.getKey().pressKey(LocatorType.XPATH, prop.getProperty("choseprojectstatus"), KeyType.ENTER);
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("doneediting"));

	}

	@Test(priority = 3)
	public void customerContactTest() {
		goToProject();
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("customerprojecttab"));
		browser.getTextField().enterTextField(LocatorType.XPATH, prop.getProperty("firstnamecustomerprojet"), "hiten");
		browser.getTextField().enterTextField(LocatorType.XPATH, prop.getProperty("lastnamecustomerproject"),
				"chudhari");
		browser.getTextField().enterTextField(LocatorType.XPATH, prop.getProperty("emailcustomerproject"),
				"hiten.chudhari@gmail.com");
		browser.getTextField().enterTextField(LocatorType.XPATH, prop.getProperty("phonenumbercustomerproject"),
				"7894561237");
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("addcontact"));
	}

	@Test(priority = 2)
	public void updateResources() {
		goToProject();
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("editresource"));
		browser.getDriver().findElement(By.xpath(prop.getProperty("firstnameofresource"))).clear();
		browser.getTextField().enterTextField(LocatorType.XPATH, prop.getProperty("firstnameofresource"), "sayali");
		browser.getDriver().findElement(By.xpath(prop.getProperty("lastnameofresource"))).clear();
		browser.getTextField().enterTextField(LocatorType.XPATH, prop.getProperty("lastnameofresource"), "More");
		browser.getDriver().findElement(By.xpath(prop.getProperty("emailofresource"))).clear();
		browser.getTextField().enterTextField(LocatorType.XPATH, prop.getProperty("emailofresource"),
				"sayali.mantri@gmail.com");
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("updatebtn"));
		browser.getWait().HardPause(1000);
	}

	public void goToProject() {
		Utils.login(browser, report);
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("project"));
		browser.getWait().HardPause(1000);
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("selectproject"));
		browser.getWait().HardPause(1000);
	}

	@Test(priority = 4)
	public void updateContactTest() {
		goToProject();
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("customerprojecttab"));
		browser.getWait().HardPause(500);
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("editcustomercontact"));
		browser.getWait().HardPause(500);
		browser.getDriver().findElement(By.xpath(prop.getProperty("firstnamecustomerprojet"))).clear();
		browser.getDriver().findElement(By.xpath(prop.getProperty("lastnamecustomerproject"))).clear();
		browser.getDriver().findElement(By.xpath(prop.getProperty("emailcustomerproject"))).clear();
		browser.getDriver().findElement(By.xpath(prop.getProperty("phonenumbercustomerproject"))).clear();
		browser.getTextField().enterTextField(LocatorType.XPATH, prop.getProperty("firstnamecustomerprojet"), "hiten");
		browser.getTextField().enterTextField(LocatorType.XPATH, prop.getProperty("lastnamecustomerproject"),
				"chudhari");
		browser.getTextField().enterTextField(LocatorType.XPATH, prop.getProperty("emailcustomerproject"),
				"hiten.chudhari@gmail.com");
		browser.getTextField().enterTextField(LocatorType.XPATH, prop.getProperty("phonenumbercustomerproject"),
				"7894561237");
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("updatecustomercontact"));
	}

}
