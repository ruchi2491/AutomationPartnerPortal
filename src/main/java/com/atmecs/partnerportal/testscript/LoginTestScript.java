package com.atmecs.partnerportal.testscript;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.atmecs.falcon.automation.ui.selenium.Browser;
import com.atmecs.falcon.automation.util.reporter.ReportLogService;
import com.atmecs.falcon.automation.util.reporter.ReportLogServiceImpl;
import com.atmecs.partnerportal.testsuite.TestSuiteBase;
import com.atmecs.partnerportal.dataprovider.ExcelUtils;

public class LoginTestScript extends TestSuiteBase {

	private ReportLogService report = new ReportLogServiceImpl(LoginTestScript.class);
	private static final String PARTNER = "PARTNER";
	private static final String ADMIN = "ADMIN";
	private static final String DISTRIBUTOR = "DISTRIBUTOR";
	Browser browser = null;

	@BeforeMethod
	@Parameters({ "os", "osVersion", "browser", "browserVersion" })
	public void initializeBrowser(String os, String osVersion, String br, String browserVersion) throws IOException {
		browser = new Browser();
		Utils.initialize(browser, report, os, osVersion, br, browserVersion);
	}

	@Test(dataProvider = "logindata", dataProviderClass = ExcelUtils.class)
	public void loginLogoutWithVerifyUserRoleTest(String username, String password) throws Exception {
		Utils.login(browser, report, username, password);
		checkLoginBy();
		Utils.logout(browser, report);
	}

	@AfterMethod
	public void closeBrowser() {
		browser.closeBrowser();
	}

	private void checkLoginBy() {
		report.info("Checking is login by Partner?");
		WebElement element = browser.getFindFromBrowser().findElementByTagName("h3");
		String str = element.getText();

		if (str.contains(PARTNER))
			report.info("Logged in by Partner");
		else if (str.contains(ADMIN))
			report.info("Logged in by Admin");
		else if (str.contains(DISTRIBUTOR))
			report.info("Logged in by distributor");
		else
			report.info("Logged in by " + str);
	}

}
