package com.deque;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * This class demonstrates tests using Axe Watcher in Auto Analyze mode.
 * It includes setup and teardown methods for initializing and closing the WebDriver,
 * as well as test methods for performing actions on a sample website.
 */
public class SampleTestbase {

    private WebDriver driver;

    /**
     * Sets up the WebDriver and Axe Watcher before each test.
     * Configures Axe Watcher with API key and server URL.
     */
    
     @BeforeMethod
     public void setUp() {
         // Set up WebDriver using WebDriverManager
         WebDriverManager.chromedriver().setup();
         ChromeOptions chromeOptions = new ChromeOptions();
 
         // Add optional Chrome options
         chromeOptions.addArguments("--start-maximized"); // Start browser maximized
         chromeOptions.addArguments("--disable-notifications"); // Disable notifications
 
         // Initialize the WebDriver
         driver = new ChromeDriver(chromeOptions);
     }

    /**
     * Cleans up after each test by flushing Axe Watcher results and quitting the WebDriver.
     */
    @AfterMethod
    public void tearDown() {
        // Flush Axe Watcher results
       // ((AxeWatcherDriver) driver).axeWatcher().flush();
        
        // Quit the WebDriver
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Test method to demonstrate the use of the refresh action in Selenium WebDriver.
     * Navigates to a URL, performs some clicks, and refreshes the page.
     */
    @Test
    public void testWithRefreshMethod() {
        // Navigate to the website
        driver.get("https://abcdcomputech.dequecloud.com");
        
        // Perform click actions on navigation links
        driver.findElement(By.cssSelector("#topnav > ul > li:nth-child(5) > a")).click();
        driver.findElement(By.cssSelector("#topnav > ul > li:nth-child(2) > a")).click();
        
        // Refresh the page
        driver.navigate().refresh();
    }

    /**
     * Test method to demonstrate the use of click actions in Selenium WebDriver.
     * Navigates to a URL and performs multiple click actions on navigation links.
     */
    @Test
    public void testWithClickAction() {
        // Navigate to the website
        driver.get("https://abcdcomputech.dequecloud.com");
        
        // Perform click actions on navigation links
        driver.findElement(By.cssSelector("#topnav > ul > li:nth-child(5) > a")).click();
        driver.findElement(By.cssSelector("#topnav > ul > li:nth-child(2) > a")).click();
        driver.findElement(By.cssSelector("#topnav > ul > li:nth-child(3) > a")).click();
    }
}