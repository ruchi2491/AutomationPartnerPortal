package com.atmecs.partnerportal.runner;

import java.io.File;
import java.util.List;

import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import com.atmecs.falcon.automation.dataprovider.TestDataProvider;
import com.atmecs.falcon.automation.ui.selenium.CustomListener;
import com.atmecs.falcon.automation.util.main.AbstractTestNGEngine;
import com.atmecs.falcon.automation.util.main.TestNGEngineFactory;
import com.atmecs.falcon.automation.util.main.TestNGEngineTemplateType;
import com.atmecs.falcon.automation.util.parser.PropertyParser;
import com.atmecs.falcon.automation.util.reporter.ReportLogService;
import com.atmecs.falcon.automation.util.reporter.ReportLogServiceImpl;
import com.atmecs.falcon.automation.util.reporter.TestReportUploadClient;
import com.beust.jcommander.internal.Lists;

/**
 * TestNGRunner is the Main Class of MOJO type generates new Xml Suites on
 * runtime for each client for each child suite in the existing parent Xml Suite
 * and executes
 * 
 * @author nv092106
 */
public class TestNGRunner {
	private static AbstractTestNGEngine testNGEngine = new TestNGEngineFactory()
			.getTestNGEngine(TestNGEngineTemplateType.DESIRED_SUITE_FOR_GIVEN_MODULES);
	@SuppressWarnings("rawtypes")
	private static List<Class> listners = Lists.newArrayList();
	private static TestNG testng = new TestNG();
	private static List<XmlSuite> suitesToRun = null;
	private static TestDataProvider dataProvider = TestDataProvider.getInstance();
	private static ReportLogService report = new ReportLogServiceImpl(TestNGRunner.class);

	private static void initialize() throws Exception {
		// Custom Listener to testng
		listners.add(CustomListener.class);
		testng.setListenerClasses(listners);
	}

	/**
	 * Purpose: The main method invoked by the Maven plugin that uses the
	 * services of TestNGEngine to create new Xml Suites on runtime and executes
	 * them
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		initialize();

		suitesToRun = testNGEngine.getSuitesToRunFor(PropertyParser.readEnvOrConfigProperty("SuiteFileName"),
				PropertyParser.readEnvOrConfigProperty("ClientName"),
				PropertyParser.readEnvOrConfigProperty("ModuleName"),
				PropertyParser.readEnvOrConfigProperty("BrowserCaps"));
		testng.setXmlSuites(suitesToRun);
		testng.setSuiteThreadPoolSize(Integer.parseInt(PropertyParser.readEnvOrConfigProperty("instances")));
		testng.run();
		 uploadTestNGResultsXml();

	}

	/**
	 * After test execution this method will post the results to Report Server
	 */
	private static void uploadTestNGResultsXml() {
		final String uploadUrl = PropertyParser.readEnvOrConfigProperty("testreport.uploadurl");
		String testNGResultsXmlFilePath = System.getProperty("user.dir") + File.separator + "test-output"
				+ File.separator + "testng-results.xml";

		TestReportUploadClient testReportUploadClient = new TestReportUploadClient(uploadUrl);
		try {
			String response = testReportUploadClient.upload(dataProvider.getSessionId(),
					PropertyParser.readEnvOrConfigProperty("ClientName"), "WEB", "QA", "Regression", "Local",
					"Windows 10", "Desktop", "Chrome v51", testNGResultsXmlFilePath);
			report.info("Response : " + response);

		} catch (Exception e) {
			report.error("Unknown error : : Cannot Upload the testng-results.xml " + e.getMessage());
		}
	}
}
