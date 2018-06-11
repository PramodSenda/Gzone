package com.gzone.web.helper;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.gzone.utils.PropertyFileReader;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class AutomationCommandsHelper {

	/** The web driver. */
	public static WebDriver driver = null;

	/** The Constant logger. */
	final static Logger logger = Logger.getLogger(AutomationCommandsHelper.class);

	/** The property file name. */
	private String PROPERTY_FILE_NAME = "config.properties";

	/** The reader. */
	PropertyFileReader reader = new PropertyFileReader(PROPERTY_FILE_NAME);

	public void launchapp() throws IOException {
		String browser = reader.getPropertyValue("BROWSER");
		String platform = reader.getPropertyValue("PLATFORM");

		if ("Web".equalsIgnoreCase(platform)) {

			if (browser.equalsIgnoreCase("firefox")) {
				logger.info("Executing on FireFox");
				File f = new File(reader.getResourcePath("libs") + File.separator + "geckodriver");
				if (System.getProperty("os.name").startsWith("Mac")) {
					Runtime.getRuntime().exec("chmod 777 " + f.getAbsolutePath());
				}
				System.setProperty("webdriver.gecko.driver", f.getAbsolutePath());
				setDriver(new FirefoxDriver());
			} else if (browser.equalsIgnoreCase("chrome")) {

				logger.info("Executing on CHROME");
				if (System.getProperty("os.name").startsWith("windows")) {
					File f = new File(reader.getResourcePath("libs") + File.separator + "chromedriver_win.exe");
					System.setProperty("webdriver.chrome.driver", f.getAbsolutePath());
				} else {
					File f = new File(reader.getResourcePath("libs") + File.separator + "chromedriver");
					Runtime.getRuntime().exec("chmod 777 " + f.getAbsolutePath());
					System.setProperty("webdriver.chrome.driver", f.getAbsolutePath());
				}
				setDriver(new ChromeDriver());
			} else if (browser.equalsIgnoreCase("iexplore")) {

				logger.info("Executing on IE");
				File f = new File(reader.getResourcePath("libs") + File.separator + "IEDriverServer(x86).exe");
				System.setProperty("webdriver.ie.driver", f.getAbsolutePath());
				setDriver(new InternetExplorerDriver());
			} else {
				logger.error("The Browser Type is Undefined");
				throw new IllegalArgumentException("The Browser Type is Undefined");
			}
		} else if ("Mobile".equalsIgnoreCase(platform)) {
			if (browser.equalsIgnoreCase("Native")) {
				String apkPath = reader.getPropertyValue("APK_PATH");
				File apkFile = new File(apkPath + File.separator + "eribank.apk");
				String apkFilePath = apkFile.getAbsolutePath();
				DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
				desiredCapabilities.setCapability(MobileCapabilityType.APP, apkFilePath);
				desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
				desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "4.4");
				desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "300");
				desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
				URL url = new URL("http://127.0.0.1:4723/wd/hub");
				setDriver(new AndroidDriver(url, desiredCapabilities));
			}
		} else {
			logger.error("The Platform Type is Undefined");
			throw new IllegalArgumentException("The Platform Type is Undefined");
		}
	}

	/**
	 * Gets the driver.
	 *
	 * @return the driver
	 */
	public static WebDriver getDriver() {
		return driver;
	}

	/**
	 * Sets the driver.
	 *
	 * @param driver
	 *            the new web driver
	 */
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
}
