/***
 *
 */
package com.atmecs.partnerportal.testsuite;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.atmecs.falcon.automation.util.logging.LogLevel;
import com.atmecs.falcon.automation.util.logging.LogManager;
import com.atmecs.falcon.automation.util.parser.PropertyParser;

public class TestSuiteBase {
	
//	protected Browser browser;

    @BeforeSuite
    public void preSetup() {
       
        LogManager.setLogLevel(LogLevel.valueOf(PropertyParser.readEnvOrConfigProperty("LOG_LEVEL")));
    }

    @AfterSuite
    public void teardown() {
       // browser.closeBrowser();
    }

}