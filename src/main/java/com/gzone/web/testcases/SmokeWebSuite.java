
package com.gzone.web.testcases;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.gzone.utils.PropertyFileReader;
import com.gzone.utils.ReadFromExcel;
import com.gzone.web.helper.SeleniumCommands;
import com.gzone.web.pages.HomePageWeb;

/**
 * The Class SmokeTestSuite.
 */
public class SmokeWebSuite extends SeleniumCommands {


	/** The property file name. */
	private String PROPERTY_FILE_NAME = "config.properties";

	/** The reader. */
	PropertyFileReader reader = new PropertyFileReader(PROPERTY_FILE_NAME);
	/** The Constant logger. */
	final static Logger logger = Logger.getLogger(SeleniumCommands.class);

	/**
	 * launchPrerequisites.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@BeforeMethod
	public void launchPrerequisites() throws IOException {	
		launchapp();
		driver = getDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	/**
	 * Credentials data provider method.
	 *
	 * @return the object[][]
	 */
	@DataProvider(name = "credentials")
	public Object[][] credDataProviderMehod() {

		ReadFromExcel excelReader = new ReadFromExcel(
				reader.getResourcePath("data") + File.separator + "DataSheet.xlsx", "DataTable1");
		return excelReader.getSheetData();
	}

	/**
	 * Calculatepercent.
	 *
	 * @param username
	 *            the username
	 * @param password
	 *            the password
	 */
	@Test(dataProvider = "credentials")
	public void loginToWebApplication(String username, String password) {
		open("http://newtours.demoaut.com/");
		type(HomePageWeb.tf_userName, username);
		type(HomePageWeb.tf_password, password);
		click(HomePageWeb.btn_signIn);
	}

	/**
	 * Close browser.
	 */
	@AfterMethod
	public void closeBrowser() {
		driver.close();
		driver.quit();
		logger.info("Browser successfully closed");
	}

}
