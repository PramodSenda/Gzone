package com.gzone.web.helper;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

// TODO: Auto-generated Javadoc
/**
 * The Class SeleniumCommands.
 */
public class SeleniumCommands extends AutomationCommandsHelper{
	

	/** The Constant logger. */
	final static Logger logger = Logger.getLogger(SeleniumCommands.class);

	/** The time out. */
	private static int timeOut = 10;

	/**
	 * Instantiates a new selenium commands.
	 */
	public SeleniumCommands() {
		DOMConfigurator.configure("log4j.xml");
	}

	/**
	 * Launch the given url.
	 *
	 * @param url
	 *            the url
	 */
	public final static void open(String url) {

		try {

			getDriver().navigate().to(url);
			logger.info("Launch the URL " + url + " successfully.");
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * Type on a web element.
	 *
	 * @param byLocator
	 *            the by locator
	 * 
	 */
	public static void click(By byLocator) {

		try {
			WebElement element = findElement(byLocator);
			element.click();
			logger.info("Clicked on the object " + byLocator);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

	}

	/**
	 * Type on a web element.
	 *
	 * @param byLocator
	 *            the by locator
	 * @param text
	 *            the text
	 */
	public final static void type(By byLocator, String text) {

		try {
			WebElement element = findElement(byLocator);
			element.sendKeys(text);
			logger.info("Typed the value " + text + " in to object " + byLocator);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

	}

	/**
	 * Select.
	 *
	 * @param byLocator
	 *            the by locator
	 * @param value
	 *            the value
	 */
	public final static void select(By byLocator, String value) {
		try {
			Select element = new Select(findElement(byLocator));
			element.selectByVisibleText(value);
			logger.info("Selected the value " + value + " from the dropdown " + byLocator);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

	}

	/**
	 * Switch to a Frame.
	 *
	 * @param byLocator
	 *            the by locator
	 * 
	 */
	public static void switchFrame(By byLocator) {

		try {
			WebElement element = findElement(byLocator);
			getDriver().switchTo().frame(element);
			logger.info("Switch to the frame" + byLocator);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

	}

	/**
	 * Switch to a Frame.
	 *
	 * @param frame
	 *            the frame
	 */
	public static void switchToParentFrame(String frame) {

		try {
			if ("parent".equalsIgnoreCase(frame)) {
				getDriver().switchTo().defaultContent();
				logger.info("Switch to the parent frame");
			} else {
				throw new Exception(frame + " : is an invalid input");

			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}

	}


	/**
	 * Wait for element in the web page.
	 *
	 * @param byLocator
	 *            the by locator
	 */
	public final static void waitForElement(By byLocator) {

		findElement(byLocator);
		logger.info("Waited for the object " + byLocator + " to appear.");
	}

	/**
	 * Find element in the web page.
	 *
	 * @param byLocator
	 *            the by locator
	 * @return the web element
	 */
	public static WebElement findElement(By byLocator) {

		WebElement element = (new WebDriverWait(getDriver(), timeOut))
				.until(ExpectedConditions.presenceOfElementLocated(byLocator));

		return element;

	}

	/**
	 * get specific property value of a web element and stores to string
	 * variable.
	 *
	 * @param property
	 *            the property of the element.
	 * @param byLocator
	 *            the by locator
	 * @return value of the property.
	 */

	public final static String getElementPropertyToString(String property, By byLocator) {
		String propertyValue = null;
		try {
			WebElement element = findElement(byLocator);
			propertyValue = element.getAttribute(property);
			logger.info("Stored the property value of the object " + byLocator + " property :" + property + "value : "
					+ propertyValue);

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return propertyValue;
	}

	/**
	 * Gets the element count.
	 *
	 * @param byLocator
	 *            the by locator
	 * @return the element count
	 */
	public final static int getElementCount(By byLocator) {
		int elementCount = 0;
		try {
			elementCount = driver.findElements(byLocator).size();
			logger.info("Element count for" + byLocator + " is :" + elementCount);

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		return elementCount;
	}

}
