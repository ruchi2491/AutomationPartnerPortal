package com.atmecs.partnerportal.testscript;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.atmecs.falcon.automation.ui.selenium.Browser;
import com.atmecs.falcon.automation.util.parser.PropertyParser;
import com.atmecs.falcon.automation.util.reporter.ReportLogService;
import com.atmecs.falcon.automation.util.reporter.ReportLogServiceImpl;
import com.atmecs.partnerportal.dataprovider.ExcelOfferDataConfig;

public class OfferTestcases {
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

	@Test(dataProvider = "offerdata", priority = 0)
	public void createOffer(String catname, String offername, String description, String sku, String skudescription,
			String price, String hours) throws InterruptedException {
		Utils.loginAdmin(browser, report);
		Thread.sleep(1000);
		browser.getDriver().findElement(By.xpath("//a[contains(text(),'" + catname + "') and @class='ng-binding']"))
				.click();
		Thread.sleep(2000);
		browser.getDriver().findElement(By.xpath("//button[@ng-click='AOController.createNewOffer()']")).click();
		Thread.sleep(2000);
		browser.getDriver().findElement(By.xpath("//input[@name='offerName']")).sendKeys(offername);
		browser.getDriver().findElement(By.xpath("//input[@name='sku']")).sendKeys(sku);
		browser.getDriver().findElement(By.xpath("//input[@name='skuDesc']")).sendKeys(skudescription);
		browser.getDriver().findElement(By.xpath("//input[@name='msrp']")).sendKeys(price);
		browser.getDriver().findElement(By.xpath("//input[@name='totalHours']")).sendKeys(hours);
		browser.getDriver().findElement(By.xpath(".//*[@id='offerDesc']")).sendKeys(description);
		browser.getDriver().findElement(By.xpath("//button[text()='Create Offer']")).click();
		Thread.sleep(1000);
		browser.getDriver().findElement(By.xpath("//a[contains(text(),'Categories')]")).click();
		Thread.sleep(2000);

	}

	@DataProvider(name = "offerdata")
	public Object[][] passData() throws IOException {
		ExcelOfferDataConfig config = new ExcelOfferDataConfig(
				"E:\\ruchi\\citrixelearning\\adminside\\TestData\\offer.xlsx");
		int row = config.getRowCount(0);
		Object[][] data = new Object[row][7];
		for (int i = 0; i < row; i++) {
			data[i][0] = config.getdata(0, i, 0);
			data[i][1] = config.getdata(0, i, 1);
			data[i][2] = config.getdata(0, i, 2);
			data[i][3] = config.getdata(0, i, 3);
			data[i][4] = config.getdata(0, i, 4);
			data[i][5] = config.getdata(0, i, 5);
			data[i][6] = config.getdata(0, i, 6);
		}
		// System.out.println(data[1][4]);
		return data;
	}

	@Test(priority = 2)
	public void deleteTest() throws InterruptedException {
		Utils.loginAdmin(browser, report);
		Thread.sleep(1000);
		browser.getDriver().findElement(By.xpath("//a[contains(text(),'edittest') and @class='ng-binding']")).click();
		Thread.sleep(1000);
		List<WebElement> elements = browser.getDriver()
				.findElements(By.xpath("//img[@src='/partnersportal/assets/images/delete.png']"));
		for (int i = 0; i < elements.size(); i++) {
			elements.get(i).click();
			Thread.sleep(500);
		}

	}

	@DataProvider(name = "editofferdata")
	public Object[][] editData() throws IOException {
		ExcelOfferDataConfig config = new ExcelOfferDataConfig(
				"E:\\ruchi\\citrixelearning\\adminside\\TestData\\offer.xlsx");
		int row = config.getRowCount(1);
		Object[][] data = new Object[row][7];
		for (int i = 0; i < row; i++) {
			data[i][0] = config.getdata(1, i, 0);
			data[i][1] = config.getdata(1, i, 1);
			data[i][2] = config.getdata(1, i, 2);
			data[i][3] = config.getdata(1, i, 3);
			data[i][4] = config.getdata(1, i, 4);
			data[i][5] = config.getdata(1, i, 5);
			data[i][6] = config.getdata(1, i, 6);
		}
		return data;
	}

	@Test(dataProvider = "editofferdata", priority = 1)
	public void editTest(String catname, String offername, String description, String sku, String skudescription,
			String price, String hours) throws InterruptedException {
		Utils.loginAdmin(browser, report);
		Thread.sleep(3000);
		browser.getDriver().findElement(By.xpath("//a[contains(text(),'" + catname + "') and @class='ng-binding']"))
				.click();
		Thread.sleep(1000);
		List<WebElement> elements = browser.getDriver()
				.findElements(By.xpath("//img[@src='/partnersportal/assets/images/edit.png']"));
		for (int i = 0; i < elements.size(); i++) {
			try {
				elements.get(i).click();

				Thread.sleep(1000);
				browser.getDriver().findElement(By.xpath("//input[@name='offerName']")).clear();
				browser.getDriver().findElement(By.xpath("//input[@name='sku']")).clear();
				browser.getDriver().findElement(By.xpath("//input[@name='skuDesc']")).clear();
				browser.getDriver().findElement(By.xpath("//input[@name='msrp']")).clear();
				browser.getDriver().findElement(By.xpath("//input[@name='totalHours']")).clear();
				browser.getDriver().findElement(By.xpath(".//*[@id='offerDesc']")).clear();

				Thread.sleep(1000);

				browser.getDriver().findElement(By.xpath("//input[@name='offerName']")).sendKeys(offername);
				browser.getDriver().findElement(By.xpath("//input[@name='sku']")).sendKeys(sku);
				browser.getDriver().findElement(By.xpath("//input[@name='skuDesc']")).sendKeys(skudescription);
				browser.getDriver().findElement(By.xpath("//input[@name='msrp']")).sendKeys(price);
				browser.getDriver().findElement(By.xpath("//input[@name='totalHours']")).sendKeys(hours);
				browser.getDriver().findElement(By.xpath(".//*[@id='offerDesc']")).sendKeys(description);
				browser.getDriver().findElement(By.xpath("//button[text()='Update Offer']")).click();

				browser.getDriver().findElement(By.xpath("//button[text()='Yes, Update it!']")).click();
				Thread.sleep(1000);
				browser.getDriver().findElement(By.xpath("//a[contains(text(),'Categories')]")).click();

			} catch (StaleElementReferenceException elementHasDisappeared) {
				elementHasDisappeared.toString();

			}

		}
	}

}
