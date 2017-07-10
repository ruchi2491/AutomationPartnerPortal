package com.atmecs.partnerportal.testscript;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.atmecs.falcon.automation.ui.selenium.Browser;
import com.atmecs.falcon.automation.ui.seleniuminterfaces.IKeys.KeyType;
import com.atmecs.falcon.automation.util.enums.LocatorType;
import com.atmecs.falcon.automation.util.reporter.ReportLogService;
import com.atmecs.falcon.automation.util.reporter.ReportLogServiceImpl;
import com.atmecs.falcon.automation.verifyresult.VerificationManager;
import com.atmecs.partnerportal.testsuite.TestSuiteBase;




public class SampleTestScript extends TestSuiteBase{
	
	private ReportLogService report = new ReportLogServiceImpl(SampleTestScript.class);
	public static final String APACHE_FLUME = "Apache Flume"; 
	public static final String URL = "http://55.55.53.12:8080/partnersportal/#/login";
	
	enum OFFERING{APACHE_FLUM,LAUNCHING_PROJECT,WEB_HOSTING}
	enum FILTER_BY_STATUS{IN_PROGRESS,OPEN,COMPLETE}
	Browser browser = null;
	
	/*
	@Test
	@Parameters({"os", "osVersion", "browser", "browserVersion"})
	public void sampleTest(String os, String osVersion, String br, String browserVersion) {
		// write your first test script here
		// sample
		report.info("Opening browser: "+ br);
		browser.openURL("http://www.google.com", os, osVersion, br, browserVersion);
		report.info("Maximizing browser window");
		browser.maximizeWindow();
		report.info("entering text: "+"Selenium");
		browser.getTextField().enterTextField(LocatorType.NAME, "q", "Selenium");
		report.info("pressing enter key");
		browser.getKey().pressKey(LocatorType.NAME, "q", KeyType.ENTER);
		report.info("waiting for 2 second");
		browser.getWait().HardPause(2000);
		String text = browser.getCurrentPageTitle();
		report.info("verifying page title");
		Verify.verifyString(text, "Selenium - Google Search", "Verifying String Message ");
	}
	
	*/


	//@Test(priority = 0)
	@Parameters({"os", "osVersion", "browser", "browserVersion"})
	public void partnersLoginTest(String os, String osVersion, String br, String browserVersion) throws ParseException {
		initialize(os, osVersion, br, browserVersion);
		login();
		
		browser.getWait().HardPause(5000);
//		addToCart();
//		checkItemsInCart();
//		listOfProjects();
//
//		addToCart();
//		browser.getWait().HardPause(3000);
//		removeItemsFromCart();
//		browser.getWait().HardPause(3000);
		
//		listOfProjects();
		
//		filterProjectListByStatus(FILTER_BY_STATUS.IN_PROGRESS);
//		filterProjectListByStatus(FILTER_BY_STATUS.OPEN);
//		filterProjectListByStatus(FILTER_BY_STATUS.COMPLETE);
		

		Date startDate = new SimpleDateFormat("dd mm yyyy").parse("24 04 2017");
		Date endDate = new Date();
		filterProjectByStartAndEndDate(startDate, endDate);
//		checkOfferDetails();
//		listOfProjects();
//		addToCart();
//		checkout();
//		removeItemsFromCart();
		browser.getWait().HardPause(3000);
//		checkout();
//		browser.getWait().HardPause(3000);
		
		logout();
		browser.getWait().HardPause(3000);
		
	
		
		
	}
	
	
	
	//@Test(priority = 2)
	@Parameters({"os", "osVersion", "browser", "browserVersion"})
	public void loginLogoutWithVerifyUserRoleTest(String os, String osVersion, String br, String browserVersion){
		initialize(os, osVersion, br, browserVersion);
		login();
		checkLoginBy();
		logout();
		browser.closeBrowser();
	}
	
	@Test(priority = 3)
	@Parameters({"os", "osVersion", "browser", "browserVersion"})
	public void addToCartAndVerifyTest(String os, String osVersion, String br, String browserVersion){
		initialize(os, osVersion, br, browserVersion);
		login();
		addToCart();
		logout();
		browser.closeBrowser();
	}
	
	
	//@Test(priority = 4)
	@Parameters({"os", "osVersion", "browser", "browserVersion"})
	public void removeFromCartTest(String os, String osVersion, String br, String browserVersion){
		initialize(os, osVersion, br, browserVersion);
		login();
		removeItemsFromCart();
		logout();
		browser.closeBrowser();
	}
	
	
	@Test(priority = 5)
	@Parameters({"os", "osVersion", "browser", "browserVersion"})
	public void checkoutTest(String os, String osVersion, String br, String browserVersion){
		initialize(os, osVersion, br, browserVersion);
		login();
		addToCart();
		checkout();
		logout();
		browser.closeBrowser();
	}
	
	@Test(priority = 6)
	@Parameters({"os", "osVersion", "browser", "browserVersion"})
	public void filterProjectsByStatusTest(String os, String osVersion, String br, String browserVersion){
		initialize(os, osVersion, br, browserVersion);
		login();
		listOfProjects();
		filterProjectListByStatus(FILTER_BY_STATUS.IN_PROGRESS);
		filterProjectListByStatus(FILTER_BY_STATUS.OPEN);
		filterProjectListByStatus(FILTER_BY_STATUS.COMPLETE);
		logout();
		browser.closeBrowser();
	}
	
	//@Test(priority = 7)
	@Parameters({"os", "osVersion", "browser", "browserVersion"})
	public void filterProjectsByStartAndEndDateTest(String os, String osVersion, String br, String browserVersion) throws ParseException{
		initialize(os, osVersion, br, browserVersion);
		login();
		listOfProjects();
		Date startDate = new SimpleDateFormat("dd mm yyyy").parse("21 04 2017");
		Date endDate = new Date();
		filterProjectByStartAndEndDate(startDate, endDate);
		logout();
		browser.closeBrowser();
	}
	
	//@Test(priority = 8)
	@Parameters({"os", "osVersion", "browser", "browserVersion"})
	public void filterProjectsByNameTest(String os, String osVersion, String br, String browserVersion) throws ParseException{
		initialize(os, osVersion, br, browserVersion);
		login();
		listOfProjects();
		filterProjectsByProjectName("falcon");
		logout();
		browser.closeBrowser();
	}
	
	
	//@Test(priority = 9)
	@Parameters({"os", "osVersion", "browser", "browserVersion"})
	public void listOfOfferingAvailableTest(String os, String osVersion, String br, String browserVersion) {
		initialize(os, osVersion, br, browserVersion);
		getlistOfOfferings();
		
	}
	
	
	
	
	
	
	
	
	public void initialize(String os, String osVersion, String br, String browserVersion){
		report.info("Opening browser: "+ br);
		browser.openURL(URL, os, osVersion, br, browserVersion);
		report.info("Maximizing browser window");
		browser.maximizeWindow();
		browser.getWait().HardPause(2000);
	}

	
	
	static String getXpathFor(String fieldName) {
		return "//input[@placeholder='"+fieldName+"']";
	}
	
	public void login() {
		report.info("Entering username: "+"tarunkumar.singh@atmecs.com");
		browser.getTextField().enterTextField(LocatorType.ID, "email", "tarunkumar.singh@atmecs.com");
		report.info("Entering password: "+"*******");
		browser.getTextField().enterTextField(LocatorType.ID, "pwd", "tarun123");
		report.info("pressing enter key");
		browser.getKey().pressKey(LocatorType.ID, "pwd", KeyType.ENTER);
		browser.getWait().HardPause(2000);
	}
	
	public void logout() {
		report.info("Trying to logout");
		browser.getClick().performClick(LocatorType.XPATH, "//a[text()='Logout']");
		browser.getWait().HardPause(5000);
		report.info("logout Successful");
	}
	
	public void addToCart() {
		browser.getWait().HardPause(2000);
		report.info("Going to offering tab");
		browser.getClick().performClick(LocatorType.XPATH, "//a[text()='Offerings']");

		browser.getWait().HardPause(3000);
		report.info("adding to cart: "+APACHE_FLUME);
		String offeringXpath = "//a[i[@class='fa fa-shopping-cart']]"; 
		WebElement element = browser.getDriver().findElements(By.xpath(offeringXpath)).get(0);
		String str = element.getText();
		
		System.out.println(str);
		
		element.click();
		browser.refreshBrowser();
		browser.getWait().HardPause(2000);		
		
		report.info("going to checkout page");
		browser.getClick().performClick(LocatorType.XPATH, "//a[@ui-sref='partner.checkout']");
		browser.getWait().HardPause(2000);
		report.info("Checking item added in the cart");
		String itemAddedToCart = browser.getTextField().readText(LocatorType.XPATH, "//h1");
		System.out.println(itemAddedToCart);
//		VerificationManager.verifyString(itemAddedToCart, APACHE_FLUME, "");
	}
	
	public void checkItemsInCart() {
		report.info("going to cart page");
		browser.getClick().performClick(LocatorType.XPATH, "//a[@ui-sref='partner.checkout']");
		browser.getWait().HardPause(2000);
		
		List<WebElement> listOfItemsAdded = browser.getDriver().findElements(By.xpath("//h1"));
		if(listOfItemsAdded.isEmpty()){
			report.info("The cart is Empty");
			return;
		}
		
		System.out.println("The Items present in cart are:");
		for (WebElement item : listOfItemsAdded) {
			System.out.println(item.getText());
		}
	}

	public void removeItemsFromCart() {
		report.info("going to cart page");
		browser.getClick().performClick(LocatorType.XPATH, "//a[@ui-sref='partner.checkout']");
		browser.getWait().HardPause(2000);
		
		//TODO:
		List<WebElement> list = browser.getDriver().findElements(By.xpath("//button[contains(@ng-click,'removeFromCart')]"));
		if(list.isEmpty()){
			report.info("The cart is empty");
			return;
		}
		
		report.info("Total " +list.size()+ "items found in cart");
		System.out.println("the total items in cart="+list.size());
		
		List<WebElement> listOfItemsAdded = browser.getDriver().findElements(By.xpath("//h1"));
		System.out.println("The Items are:");
		for (WebElement item : listOfItemsAdded) {
			System.out.println(item.getText());
		}
		for (WebElement element : list) {
			element.click();
		}
		
		report.info("The cart is empty");
		
		/*
		report.info("checking cart is empty or not?");
		WebElement element = browser.getFindFromBrowser().findElementByXpath("//h3[contains(text(),'Empty')]");
		boolean isCartEmpty = element.isDisplayed();
		if(isCartEmpty)
			report.info("The cart is Empty");
		*/
		
	}
	
	public void checkLoginBy() {
		report.info("Checking is login by Partner?");
		WebElement element = browser.getFindFromBrowser().findElementByTagName("h3");
		String str = element.getText();
		
		if(str.contains("PARTNER"))
			report.info("Logged in by Partner");
		else if(str.contains("ADMIN"))
			report.info("Logged in by Admin");
		else
			report.info("Logged in by "+str);
	}
	
	public void checkout() {
		report.info("going to checkout");
		browser.getClick().clickImageByXpath(LocatorType.XPATH, "//a[text()='Checkout']");		//clicking on checkout page (going to checkout page)
		
		
		browser.getWait().HardPause(2000);
		browser.getTextField().enterTextField(LocatorType.XPATH, getXpathFor("Customer Name"), "Suraj");
		browser.getTextField().enterTextField(LocatorType.XPATH, getXpathFor("Organization ID"), "280");
		browser.getTextField().enterTextField(LocatorType.XPATH, getXpathFor("Project Name"), "falcon");
		browser.getTextField().enterTextField(LocatorType.XPATH, getXpathFor("Customer Address Line 1"), "manikonda");
		browser.getTextField().enterTextField(LocatorType.XPATH, getXpathFor("Customer Address Line 2"), "hyderabad");
		browser.getTextField().enterTextField(LocatorType.XPATH, getXpathFor("Customer City"), "hyderabad");
		browser.getTextField().enterTextField(LocatorType.XPATH, getXpathFor("Customer State"), "Telengana");
		
	//	browser.getWait().HardPause(2000);
		browser.getClick().performClick(LocatorType.XPATH, "//span[text()='Select a country from the list']");
		browser.getWait().HardPause(1000);
		browser.getTextField().enterTextField(LocatorType.XPATH, "//input[@placeholder='Select a country from the list']", "India");
		browser.getKey().pressKey(LocatorType.ID, "pwd", KeyType.ARROW_DOWN);
		browser.getKey().pressKey(LocatorType.ID, "pwd", KeyType.ENTER);
	//	browser.getWait().HardPause(2000);
		
		browser.getTextField().enterTextField(LocatorType.XPATH, getXpathFor("Customer Postal Code"), "500089");
		browser.getTextField().enterTextField(LocatorType.XPATH, getXpathFor("Customer Phone Number"), "9000908592");
		String email = "patil.suraj@atmecs.com";
		browser.getTextField().enterTextField(LocatorType.XPATH, getXpathFor("Customer Email"),email.trim());
		browser.getTextField().enterTextField(LocatorType.XPATH, getXpathFor("PO Number"), "1234");
		
		browser.getWait().HardPause(6000);
		
		report.info("Agree to terms and condition");
		browser.getClick().performClick(LocatorType.XPATH, "//div[@class='col-lg-8']/div/input");
		report.info("Clicking on Buy now");
		browser.getClick().performClick(LocatorType.XPATH, "//button[text()='Buy Now']");
		
		browser.getWait().HardPause(2000);
		WebElement msg = browser.getFindFromBrowser().findElementByXpath("//h4[contains(text(),'The consulting')]");
		report.info("order completed with msg "+msg.getText());
		//h4[contains(text(),'The consulting')]
		
		
	}
	
	
	public void listOfProjects() {
		report.info("Going to projects tab");
		browser.getClick().performClick(LocatorType.XPATH, "//a[contains(text(),'Projects')]");
		browser.getWait().HardPause(3000);
		report.info("Getting the list of projects");
		List<WebElement> totalprojects = browser.getDriver().findElements(By.xpath("//*[@id='export']/tbody/tr/td[5]"));
		if(totalprojects.isEmpty()){
			report.info("No project found");
			return;
		}
		
		report.info("Total "+totalprojects.size()+" projects found");
		for (WebElement projectElement : totalprojects) {
			System.out.println(projectElement.getText());
		}
		
	}
	
	public void getlistOfOfferings() {
		report.info("Going to offering page");
		browser.getClick().performClick(LocatorType.XPATH, "//a[contains(text(),'Offerings')]");
		report.info("Getting the offerings");
		browser.getWait().HardPause(3000);
		List<WebElement> listOfOfferings = browser.getDriver().findElements(By.xpath("//a/h3"));
		if(listOfOfferings.isEmpty()) {
			report.info("No Offering found");
			return;
		}
		report.info("Total "+listOfOfferings+" offerings found");
		report.info("The offerings are:");
		for (WebElement offeringElement : listOfOfferings) {
			System.out.println(offeringElement.getText());
		}
	}
	
	public void checkOfferDetails() {
		report.info("Going to offering page");
		browser.getClick().performClick(LocatorType.XPATH, "//a[contains(text(),'Offerings')]");
		browser.getWait().HardPause(2000);
		List<WebElement> offeringsList = browser.getDriver().findElements(By.xpath("//a/h3"));
		report.info("Going to offer Details");
		offeringsList.get(0).click();
		browser.getWait().HardPause(2000);
		
		WebElement offer = browser.getFindFromBrowser().findElementByTagName("h1");
		List<WebElement> offerDetails = browser.getDriver().findElements(By.xpath("//li/h3"));
		if (!offerDetails.isEmpty()) {
			report.info("Offer details page found with offer "+offer.getText());
			report.info("Details found are:");
			for (WebElement element : offerDetails) {
				report.info(element.getText());
			}
		}else
			report.info("Offer page not found");
		
		
		
	}
	
	
	
	public void filterProjectListByStatus(FILTER_BY_STATUS status) {
		//input[@type='checkbox' AND contains(@ng-true-value,'In Progress')]
		
		report.info("Going to projects page");
		browser.getClick().performClick(LocatorType.XPATH, "//a[contains(text(),'Projects')]");
		browser.getWait().HardPause(2000);
		String expectedStatus = null;
		report.info("filtering by status :"+status);
		System.out.println("clicking on checkbox");
		
		switch (status) {
		case IN_PROGRESS:
			expectedStatus  = "In Progress";
			browser.getClick().performClick(LocatorType.XPATH, "//input[contains(@ng-true-value,'In Progress')]");
			break;
		case OPEN:
			expectedStatus  = "Open";
			browser.getClick().performClick(LocatorType.XPATH, "//input[contains(@ng-true-value,'Open')]");
			break;
		case COMPLETE:
			expectedStatus  = "Complete";
			browser.getClick().performClick(LocatorType.XPATH, "//input[contains(@ng-true-value,'Complete')]");
			break;
		default:
			break;
		}
		
		
		browser.getWait().HardPause(5000);
		
		
		report.info("Getting list of filtered projects");
		List<WebElement> filteredProjectList = browser.getDriver().findElements(By.xpath("//tbody/tr/td[5]"));
		
		if(filteredProjectList.isEmpty()){
			report.info("No project found with selected criteria");
			return;
		}
		
		List<WebElement> filteredStatusList = browser.getDriver().findElements(By.xpath("//tbody/tr/td[9]"));
		int index = 0;
		for (WebElement element : filteredStatusList) {
			String actualStatus = element.getText();
			report.info("verifying for project: "+filteredProjectList.get(index).getText());
			VerificationManager.verifyString(actualStatus, expectedStatus, "");
			index++;
		}
		
		browser.getClick().clickImageByXpath(LocatorType.XPATH, "//button[contains(@ng-click,'clearAllFilters')]");		//clear all filter button pressed
		browser.getWait().HardPause(2000);
		
	}
	
	
	
	
	public void filterProjectsByProjectName(String projectName){
		report.info("Going to projects page");
		browser.getClick().performClick(LocatorType.XPATH, "//a[contains(text(),'Projects')]");
		browser.getWait().HardPause(2000);
		report.info("Filtering by project name: "+projectName);
		browser.getTextField().enterTextField(LocatorType.XPATH, "//input[@placeholder='Project Name']", projectName);
		report.info("Getting list of filtered projects");
		List<WebElement> filteredProjectList = browser.getDriver().findElements(By.xpath("//tbody/tr/td[5]"));
		if(filteredProjectList.isEmpty()){
			report.info("No project found with selected criteria");
			return;
		}
		report.info("Total "+filteredProjectList.size()+" projects found");
		report.info("verifying the project names");
		for (WebElement element : filteredProjectList) {
			VerificationManager.verifyString(element.getText(), projectName, "");
		}
	}
	
	
	public void filterProjectByStartAndEndDate(Date startDate,Date endDate) throws ParseException {
		
		report.info("Going to projects page");
		browser.getClick().performClick(LocatorType.XPATH, "//a[contains(text(),'Projects')]");
		browser.getWait().HardPause(4000);
		report.info("selecting date");
		browser.getClick().performClick(LocatorType.XPATH, "//button[@ng-click='open1()']");
		browser.getWait().HardPause(1000);
		report.info("selecting start date: "+new SimpleDateFormat("dd MMMM yyyy").format(startDate));
		selectDate(startDate);
		browser.getClick().performClick(LocatorType.XPATH, "//button[@ng-click='open2()']");
		browser.getWait().HardPause(1000);
		report.info("selecting start date: "+new SimpleDateFormat("dd MMMM yyyy").format(endDate));
		selectDate(endDate);
		List<WebElement> filteredProjectList = browser.getDriver().findElements(By.xpath("//tbody/tr/td[5]"));
		if(filteredProjectList.isEmpty()){
			report.info("No project found with selected criteria");
			return;
		}
		report.info("Total "+filteredProjectList.size()+" projects found");
		report.info("Checking project dates");
		List<WebElement> startDateList = browser.getDriver().findElements(By.xpath("//tbody/tr/td[6]"));
		List<WebElement> endDateList = browser.getDriver().findElements(By.xpath("//tbody/tr/td[7]"));
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy");
		for (WebElement startDateElement : startDateList) {
			int value = dateFormat.parse(startDateElement.getText()).compareTo(startDate);
			if(value<0)
				report.error("date found before the expected date");
			else
				report.info("start date for project found:"+startDateElement.getText()+" which is equal or after selected start date");
		}
		
		for (WebElement endDateElement : endDateList) {
			int value = dateFormat.parse(endDateElement.getText()).compareTo(endDate);
			if(value>0)
				report.error("date found after the expected date");
			else
				report.info("end date for project found:"+endDateElement.getText()+" which is equal or before selected end date");
		}
		
		browser.getWait().HardPause(10000);
	}
	
	private void selectDate(Date date) {
		int day = Integer.parseInt(new SimpleDateFormat("dd").format(date));
		int month = Integer.parseInt(new SimpleDateFormat("MM").format(date));
		int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(date));
		browser.getClick().performClick(LocatorType.XPATH, "//button[contains(@id,'datepicker')]");
		browser.getWait().HardPause(1000);
		WebElement yrBtnElement = browser.getFindFromBrowser().findElementByXpath("//button[contains(@id,'datepicker')]");
		int selectedYear = Integer.parseInt(yrBtnElement.getText());
		if(selectedYear != year) {
			int diff = (selectedYear > year) ? selectedYear-year : year - selectedYear;
			if (year < selectedYear) {
				for (int i = 0; i < diff; i++) {
					browser.getClick().performClick(LocatorType.XPATH, "//button[@ng-click='move(-1)']");
					browser.getWait().HardPause(500);
				}
				
			}else{
				for (int i = 0; i < diff; i++) {
					browser.getClick().performClick(LocatorType.XPATH, "//button[@ng-click='move(1)']");
					browser.getWait().HardPause(500);
				}
			}	
		}
		browser.getWait().HardPause(2000);
		List<WebElement> monthElements = browser.getDriver().findElements(By.xpath("//button[@ng-click='select(dt.date)']"));
		monthElements.get(month-1).click();
		browser.getWait().HardPause(2000);		
		List<WebElement> dateElements = browser.getDriver().findElements(By.xpath("//td[starts-with(@id,'datepicker')]/button"));		
		int count = -1;
		for (WebElement dateElement : dateElements) {
			if(Integer.parseInt(dateElement.getText()) == 1)
				break;
			count++;
		}
		int reqDateIndex = count + day;
		dateElements.get(reqDateIndex).click();
		browser.getWait().HardPause(2000);
	}
	
	public void editProjectName(String oldName,String newName) {
		report.info("Going to project page");
		browser.getClick().performClick(LocatorType.XPATH, "//a[contains(text(),'Projects')]");
		browser.getWait().HardPause(2000);

		
	}
	
	public static void main(String[] args) throws IOException {
	
	}
	
}
