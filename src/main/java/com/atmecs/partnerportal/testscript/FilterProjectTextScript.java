package com.atmecs.partnerportal.testscript;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
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

public class FilterProjectTextScript extends TestSuiteBase {

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

	/*
	 * @Test(priority = 0) public void filterProjectsByStatusTest(){
	 * Utils.login(browser, report); listOfProjects();
	 * filterProjectListByStatus(FILTER_BY_STATUS.IN_PROGRESS);
	 * filterProjectListByStatus(FILTER_BY_STATUS.OPEN);
	 * filterProjectListByStatus(FILTER_BY_STATUS.COMPLETE);
	 * Utils.logout(browser, report); }
	 */

	@Test(priority = 1)
	public void filterProjectsByStartAndEndDateTest() throws ParseException {
		Utils.login(browser, report);
		listOfProjects();
		Date startDate = new SimpleDateFormat("dd mm yyyy").parse("21 04 2017");
		Date endDate = new Date();
		filterProjectByStartAndEndDate(startDate, endDate);
		Utils.logout(browser, report);
	}

	@Test(priority = 2)
	public void filterProjectsByNameTest() throws ParseException {
		Utils.login(browser, report);
		listOfProjects();
		filterProjectsByProjectName("falcon");
		Utils.logout(browser, report);
	}

	private void listOfProjects() {
		goToProjectTab();
		report.info("Getting the list of projects");
		List<WebElement> totalprojects = getListOfProjects();
		if (totalprojects.isEmpty()) {
			report.info("No project found");
			return;
		}

		report.info("Total " + totalprojects.size() + " projects found");
		report.info("Projects are:");
		for (WebElement projectElement : totalprojects) {
			report.info(projectElement.getText());
		}

	}

	private void goToProjectTab() {
		report.info("Going to projects page");
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("project_tab"));
		browser.getWait().HardPause(15000);
	}

	private List<WebElement> getListOfProjects() {
		return browser.getDriver().findElements(By.xpath(prop.getProperty("project_list")));
	}

	public void filterProjectListByStatus(FILTER_BY_STATUS status) {
		goToProjectTab();
		String expectedStatus = null;
		report.info("filtering by status :" + status);
		System.out.println("clicking on checkbox");

		switch (status) {
		case IN_PROGRESS:
			expectedStatus = "In Progress";
			browser.getClick().performClick(LocatorType.XPATH, "//input[contains(@ng-true-value,'In Progress')]");
			break;
		case OPEN:
			expectedStatus = "Open";
			browser.getClick().performClick(LocatorType.XPATH, "//input[contains(@ng-true-value,'Open')]");
			break;
		case COMPLETE:
			expectedStatus = "Complete";
			browser.getClick().performClick(LocatorType.XPATH, "//input[contains(@ng-true-value,'Complete')]");
			break;
		default:
			break;
		}

		browser.getWait().HardPause(1000);

		report.info("Getting list of filtered projects");
		List<WebElement> filteredProjectList = getListOfProjects();

		if (filteredProjectList.isEmpty()) {
			report.info("No project found with selected criteria");
			return;
		}

		List<WebElement> filteredStatusList = browser.getDriver()
				.findElements(By.xpath(prop.getProperty("filtered_status_list")));
		int index = 0;
		for (WebElement element : filteredStatusList) {
			String actualStatus = element.getText();
			report.info("verifying for project: " + filteredProjectList.get(index).getText());
			VerificationManager.verifyString(actualStatus, expectedStatus, "");
			index++;
		}

		browser.getClick().clickImageByXpath(LocatorType.XPATH, prop.getProperty("reset_filter_btn")); // clear
																										// all
																										// filter
																										// button
																										// pressed
		browser.getWait().HardPause(1000);
	}

	public void filterProjectByStartAndEndDate(Date startDate, Date endDate) throws ParseException {

		goToProjectTab();
		report.info("selecting date");
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("start_date_select_btn"));
		browser.getWait().HardPause(1000);
		report.info("selecting start date: " + new SimpleDateFormat("dd MMMM yyyy").format(startDate));
		selectDate(startDate);
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("end_date_select_btn"));
		browser.getWait().HardPause(1000);
		report.info("selecting start date: " + new SimpleDateFormat("dd MMMM yyyy").format(endDate));
		selectDate(endDate);
		List<WebElement> filteredProjectList = getListOfProjects();
		if (filteredProjectList.isEmpty()) {
			report.info("No project found with selected criteria");
			return;
		}
		report.info("Total " + filteredProjectList.size() + " projects found");
		report.info("Checking project dates");
		List<WebElement> startDateList = browser.getDriver()
				.findElements(By.xpath(prop.getProperty("start_date_list")));
		List<WebElement> endDateList = browser.getDriver().findElements(By.xpath(prop.getProperty("end_date_list")));

		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy");
		for (WebElement startDateElement : startDateList) {
			int value = dateFormat.parse(startDateElement.getText()).compareTo(startDate);
			if (value < 0)
				report.error("date found before the expected date");
			else
				report.info("start date for project found:" + startDateElement.getText()
						+ " which is equal or after selected start date");
		}

		for (WebElement endDateElement : endDateList) {
			int value = dateFormat.parse(endDateElement.getText()).compareTo(endDate);
			if (value > 0)
				report.error("date found after the expected date");
			else
				report.info("end date for project found:" + endDateElement.getText()
						+ " which is equal or before selected end date");
		}

		browser.getWait().HardPause(1000);
	}

	private void selectDate(Date date) {
		int day = Integer.parseInt(new SimpleDateFormat("dd").format(date));
		int month = Integer.parseInt(new SimpleDateFormat("MM").format(date));
		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
		browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("date_picker_btn"));
		browser.getWait().HardPause(1000);
		WebElement yrBtnElement = browser.getFindFromBrowser().findElementByXpath(prop.getProperty("date_picker_btn"));
		int selectedYear = Integer.parseInt(yrBtnElement.getText());
		if (selectedYear != year) {
			int diff = (selectedYear > year) ? selectedYear - year : year - selectedYear;
			if (year < selectedYear) {
				for (int i = 0; i < diff; i++) {
					browser.getClick().performClick(LocatorType.XPATH, prop.getProperty("year_select_left_btn"));
					browser.getWait().HardPause(500);
				}

			} else {
				for (int i = 0; i < diff; i++) {
					browser.getClick().performClick(LocatorType.XPATH,
							prop.getProperty("//button[@ng-click='move(1)']"));
					browser.getWait().HardPause(500);
				}
			}
		}
		browser.getWait().HardPause(5000);
		List<WebElement> monthElements = browser.getDriver().findElements(By.xpath(prop.getProperty("month_btn_list")));
		monthElements.get(month - 1).click();
		browser.getWait().HardPause(5000);
		List<WebElement> dateElements = browser.getDriver().findElements(By.xpath(prop.getProperty("date_btn_list")));
		int count = -1;
		for (WebElement dateElement : dateElements) {
			if (Integer.parseInt(dateElement.getText()) == 1)
				break;
			count++;
		}
		int reqDateIndex = count + day;
		dateElements.get(reqDateIndex).click();
		browser.getWait().HardPause(5000);
	}

	public void filterProjectsByProjectName(String projectName) {
		goToProjectTab();
		report.info("Filtering by project name: " + projectName);
		browser.getTextField().enterTextField(LocatorType.XPATH, prop.getProperty("filter_by_project_name_text"),
				projectName);
		report.info("Getting list of filtered projects");
		List<WebElement> filteredProjectList = getListOfProjects();
		if (filteredProjectList.isEmpty()) {
			report.info("No project found with selected criteria");
			return;
		}
		report.info("Total " + filteredProjectList.size() + " projects found");
		report.info("verifying the project names");
		for (WebElement element : filteredProjectList) {
			VerificationManager.verifyString(element.getText(), projectName, "");
		}
	}
}
