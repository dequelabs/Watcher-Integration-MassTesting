package com.deque;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.deque.axe_core.commons.AxeWatcherOptions;
import com.deque.axe_core.selenium.AxeWatcher;
import com.deque.axe_core.selenium.AxeWatcherDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;

/**
 * This class demonstrates various Selenium WebDriver methods and actions
 * integrated with Axe Watcher for accessibility testing using TestNG.
 * It includes setup and teardown methods for initializing and closing the WebDriver,
 * as well as multiple test methods to demonstrate navigation and element interactions.
 */
public class WatcherTest {

    private WebDriver driver;

    /**
     * Sets up the WebDriver and Axe Watcher before each test.
     * Configures Axe Watcher with API key and server URL.
     */
    @BeforeClass
    public void setUp() {
       

        // Configure Axe Watcher options
        AxeWatcherOptions options = new AxeWatcherOptions()
                .setApiKey("apiKey")
                .setServerUrl("serverUrl");
               
                
        // Initialize Axe Watcher
        AxeWatcher watcher = new AxeWatcher(options).enableDebugLogger();

        // Set up WebDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions = watcher.configure(chromeOptions);

        // Wrap the WebDriver with Axe Watcher
        driver = watcher.wrapDriver(new ChromeDriver(chromeOptions));
    }

    /**
     * Cleans up after each test by quitting the WebDriver.
     */
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            ((AxeWatcherDriver) driver).axeWatcher().flush();
            driver.quit(); // Close the browser
        }
    }

    /**
    * Test method to validate the accessibility scan of a specific URL using the get() method in Selenium WebDriver.
    * Expected Results:
    * - Branches and Commits page: Displays a new branch card, A11y threshold of 7, 1 page state, and the latest Axe Core/Watcher versions.
    * - Issue page: Identifies failure rules such as auto-complete-valid, aria-allowed-attribute, color-contrast, label-content-name-mismatch, 
    *   label, and link-in-text-block issues. Page state: 1
    */
    @Test
    @Description("Verify the Scan page state using get wrap method")
    public void testGet() {
        driver.get("https://qateam.dequecloud.com/attest/api/test.html");   
    }

    /**
     * Test method to demonstrate the use of navigate().to() method in Selenium WebDriver.
     * Navigates to a specific URL and flushes the Axe Watcher.
     * Expected Results:
     * - Branches and Commits page: Displays a new branch card, A11y threshold of 7, 1 page state, and the latest Axe Core/Watcher versions.
     * - Issue page: Identifies failure rules such as auto-complete-valid, aria-allowed-attribute, color-contrast, label-content-name-mismatch, 
     *   label, and link-in-text-block issues. Page state: 1
     */
    @Test
    @Description("Verify the Scan page state using navigate wrap method")
    public void testNavigateTO() {
        driver.navigate().to("https://qateam.dequecloud.com/attest/api/test.html"); 
    }

    
}