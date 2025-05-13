package com.deque;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.deque.axe_core.commons.AxeWatcherOptions;
import com.deque.axe_core.selenium.AxeWatcher;
import com.deque.axe_core.selenium.AxeWatcherDriver;
import com.deque.util.EnvLoader;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * This class demonstrates tests using Axe Watcher in Auto Analyze mode.
 * It includes setup and teardown methods for initializing and closing the WebDriver,
 * as well as test methods for autoscan different types of web pages and performing actions on those web pages.
 */
public class AutoAnalyzeModeTest {

    private WebDriver driver;

    /**
     * Sets up the WebDriver and Axe Watcher before each test.
     * Configures Axe Watcher with API key and server URL.
     */
    @BeforeMethod
    public void setUp() {
        // Load environment variables
        String githubRunId = EnvLoader.get("GITHUB_RUN_ID");
        if (githubRunId == null) {
            githubRunId = "RUN-" + (int) (Math.random() * 100000);
        }
        String apiKey = EnvLoader.get("API_KEY");
        String serverUrl = EnvLoader.get("SERVER_URL");

        // Configure Axe Watcher options
        AxeWatcherOptions options = new AxeWatcherOptions()
                .setApiKey(apiKey)
                .setServerUrl(serverUrl)
                .setBuildId(githubRunId);
        
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
     * Cleans up after each test by flushing Axe Watcher results and quitting the WebDriver.
     */
    @AfterMethod
    public void tearDown() {
        // Flush Axe Watcher results
        ((AxeWatcherDriver) driver).axeWatcher().flush();
        
        // Quit the WebDriver
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Test method to demonstrate the use of the refresh action in Selenium WebDriver.
     *  As we are not handling iframe with java integration now, even though the page is having iframes related issues it cannot be found as failures.
     * Expected Results:
     * - Branches and Commits page: Displays a new branch card, A11y threshold of 6, 1 page state, and the latest Axe Core/Watcher versions.
     * - Issue page: Identifies failure rules such as auto-complete-valid, aria-allowed-attribute, color-contrast,  
     *   label, and link-in-text-block issues. Page state: 1
     */
    @Test
    public void testWithIframesPage() {
        // Navigate to the website
        driver.get("https://qateam.dequecloud.com/attest/api/test.html");
         // Refresh the page
        driver.navigate().refresh();
    }

    /**
     * Test method to demonstrate the use of click actions in a Single webpage using Selenium WebDriver.
     * Navigates to a URL and performs multiple click actions on navigation links.
     * Expected Results:
     * - Branches and Commits page: Displays a new branch card, A11y threshold of 158, 4 page state, and the latest Axe Core/Watcher versions.
     * - Issue page: Identifies failure rules such as  color-contrast, image-alt, label, link-in-text-block, link-name, input-image-alt and marque issues.
     *  Page state: 4 https://abcdcomputech.dequecloud.com, https://abcdcomputech.dequecloud.com/support.php,  https://abcdcomputech.dequecloud.com/laptopsandnotebooks.php and https://abcdcomputech.dequecloud.com/desktops.php
     */
    @Test
    public void testSinglePageWithLinks() {
        // Navigate to the website
        driver.get("https://abcdcomputech.dequecloud.com");
        
        // Perform click actions on navigation links
        driver.findElement(By.cssSelector("#topnav > ul > li:nth-child(5) > a")).click();
        driver.findElement(By.cssSelector("#topnav > ul > li:nth-child(2) > a")).click();
        driver.findElement(By.cssSelector("#topnav > ul > li:nth-child(3) > a")).click();
    }

    /**
     * Test method to demonstrate scan a clean page, which has zero accessibility issues using Selenium WebDriver.
     * Navigates to a URL and performs scan and flush the results to devhub.
     * Expected Results:
     * - Branches and Commits page: Displays a new branch card, A11y threshold of 0, 1 page state, and the latest Axe Core/Watcher versions.
     * - Issue page: Identifies zro failure rules   
     *   Page state: 1 -https://qateam.dequecloud.com/testfiles/cleanpage.html
     */

     @Test
    public void testForCleanPage() {
        // Navigate to the website
        driver.get("https://qateam.dequecloud.com/testfiles/cleanpage.html");
        // Assert the title of the page
        String expectedTitle = "Test File - Clean Page";
        String actualTitle = driver.getTitle();
        assert actualTitle.equals(expectedTitle) : "Expected title: " + expectedTitle + ", but got: " + actualTitle;
       
    }
     /**
     * Test method to demonstrate scan a dynamic page using Selenium WebDriver.
     * Navigates to a URL and performs scan and flush the results to devhub. As this is dynamic page, sometime issues count may vary.
     * Expected Results:
     * - Branches and Commits page: Displays a new branch card, A11y threshold of 24, 1 page state, and the latest Axe Core/Watcher versions.
     * - Issue page: Identifies failure rules such as  button-name, color-contrast, image-alt, frame-title, html-has-lang, link-in-text-block, link-name and select-name issues.   
     *   Page state: 1 -https://dequeuniversity.com/demo/mars
     */

     @Test
    public void testForDynamicPage() {
        // Navigate to the website
        driver.get("https://dequeuniversity.com/demo/mars/");
        // Assert the title of the page
        String expectedTitle = "Mars Commuter: Travel to Mars for Work or Pleasure!";
        String actualTitle = driver.getTitle();
        assert actualTitle.equals(expectedTitle) : "Expected title: " + expectedTitle + ", but got: " + actualTitle;
       
    }

     /**
     * Test method to demonstrate scan a static content type page using Selenium WebDriver.
     * Navigates to a URL and performs scan and flush the results to devhub.
     * Expected Results:
     * - Branches and Commits page: Displays a new branch card, A11y threshold of 12, 1 page state, and the latest Axe Core/Watcher versions.
     * - Issue page: Identifies failure rules such as  color-contrast and image-alt issues.
     *  Page state: 1 https://broken-workshop.dequelabs.com 
 
     */
    
    @Test
    public void testForStaticPage() {
        // Navigate to the website
        driver.get("https://broken-workshop.dequelabs.com/");
        // Assert the title of the page
        String expectedTitle = "[INSERT TITLE HERE]";
        String actualTitle = driver.getTitle();
        assert actualTitle.equals(expectedTitle) : "Expected title: " + expectedTitle + ", but got: " + actualTitle;
       
    }
      /**
     * Test method to demonstrate scan a dynamic page on actions finding the dom changes using Selenium WebDriver.
     * Navigates to a URL and performs scan and flush the results to devhub. As this is dynamic page, sometime issues count may vary.
     * Expected Results:
     * - Branches and Commits page: Displays a new branch card, A11y threshold of 30, 7 page states, and the latest Axe Core/Watcher versions.
     * - Issue page: Identifies failure rules such as  button-name, color-contrast, image-alt, frame-title, html-has-lang, link-in-text-block, link-name, label and select-name issues.   
     *   Page state: 7 -https://dequeuniversity.com/demo/mars
     */
    @Test
    public void marsDOMChangeTest() throws InterruptedException {
        driver.get("https://dequeuniversity.com/demo/mars/");
        Thread.sleep(1000);
         // Assert the title of the page
         String expectedTitle = "Mars Commuter: Travel to Mars for Work or Pleasure!";
         String actualTitle = driver.getTitle();
         assert actualTitle.equals(expectedTitle) : "Expected title: " + expectedTitle + ", but got: " + actualTitle;
       
        driver.findElement(By.id("widget-controls-activities-label")).click();
        Thread.sleep(1000);

        driver.findElement(By.id("widget-controls-passes-label")).click();
        Thread.sleep(1000);

        driver.findElement(By.id("widget-controls-hotels-label")).click();
        Thread.sleep(1000);

        driver.findElement(By.id("widget-controls-reservations-label")).click();
        Thread.sleep(1000);

        driver.findElement(By.cssSelector("#route-type-radio-group > span:nth-child(2) > label")).click();
        Thread.sleep(1000);
    }
}
