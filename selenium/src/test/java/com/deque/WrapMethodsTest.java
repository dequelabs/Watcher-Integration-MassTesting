package com.deque;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.deque.axe_core.commons.AxeWatcherOptions;
import com.deque.axe_core.selenium.AxeWatcher;
import com.deque.axe_core.selenium.AxeWatcherDriver;
import com.deque.util.EnvLoader;
import com.deque.util.GitUtils;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;

/**
 * This class demonstrates various Selenium WebDriver methods and actions
 * integrated with Axe Watcher for accessibility testing using TestNG.
 * It includes setup and teardown methods for initializing and closing the WebDriver,
 * as well as multiple test methods to demonstrate navigation and element interactions.
 */
public class WrapMethodsTest {

    private WebDriver driver;

    /**
     * Sets up the WebDriver and Axe Watcher before each test.
     * Configures Axe Watcher with API key and server URL.
     */
    @BeforeClass
    public void setUp() {
        // Generate a random branch name and switch to it
        String branchName = GitUtils.generateBranchName(Thread.currentThread().getStackTrace()[2].getMethodName());
        GitUtils.checkoutNewBranch(branchName);

        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "4");

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
    * - Branches and Commits page: Displays a new branch card, A11y threshold of 6, 1 page state, and the latest Axe Core/Watcher versions.
    * - Issue page: Identifies failure rules such as auto-complete-valid, aria-allowed-attribute, color-contrast,  
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
     * - Branches and Commits page: Displays a new branch card, A11y threshold of 6, 1 page state, and the latest Axe Core/Watcher versions.
     * - Issue page: Identifies failure rules such as auto-complete-valid, aria-allowed-attribute, color-contrast,  
     *   label, and link-in-text-block issues. Page state: 1
     */
    @Test
    @Description("Verify the Scan page state using navigate wrap method")
    public void testNavigateTO() {
        driver.navigate().to("https://qateam.dequecloud.com/attest/api/test.html"); 
    }

    /**
     * Test method to demonstrate the use of navigate().back() method in Selenium WebDriver.
     * Navigates to a specific URL, then navigates to another URL, and finally goes back to the previous page.
     * Expected Results:
     * - Branches and Commits page: Displays a new branch card, A11y threshold of 83, 2 page state, and the latest Axe Core/Watcher versions.
     * - Issue page: Identifies failure rules such as  color-contrast, image-alt, label, link-in-text-block and link-name issues. Page state: 2
     *    https://abcdcomputech.dequecloud.com and https://abcdcomputech.dequecloud.com/desktops.php
     */
    @Test
    @Description("Verify the Scan page state using navigate back wrap method")
    public void testNavigateBack() {
        driver.get("https://abcdcomputech.dequecloud.com/");
        driver.navigate().to("https://abcdcomputech.dequecloud.com/desktops.php");
        driver.navigate().back(); 
       
    }

    /**
     * Test method to demonstrate the use of navigate().forward() method in Selenium WebDriver.
     * Navigates to a specific URL, then navigates to another URL, goes back to the previous page,
     * and then navigates forward to the next page.
     * Expected Results:
     * - Branches and Commits page: Displays a new branch card, A11y threshold of 83, 2 page state, and the latest Axe Core/Watcher versions.
     * - Issue page: Identifies failure rules such as  color-contrast, image-alt, label, link-in-text-block and link-name issues. Page state: 2
     *    https://abcdcomputech.dequecloud.com and https://abcdcomputech.dequecloud.com/desktops.php
     */
    @Test
    @Description("Verify the Scan page state using navigate forward wrap method")
    public void testNavigateForward() {
        driver.get("https://abcdcomputech.dequecloud.com/");
        driver.navigate().to("https://abcdcomputech.dequecloud.com/desktops.php");
        driver.navigate().back();
        driver.navigate().forward();  
    }

    /**
     * Test method to demonstrate the use of navigate().refresh() method in Selenium WebDriver.
     * Navigates to a specific URL, clicks on two elements, and then refreshes the page.
     * Expected Results:
     * - Branches and Commits page: Displays a new branch card, A11y threshold of 104, 4 page state, and the latest Axe Core/Watcher versions.
     * - Issue page: Identifies failure rules such as  color-contrast, image-alt, label, link-in-text-block, link-name, input-image-alt and marque issues.
     *  Page state: 4 https://abcdcomputech.dequecloud.com, https://abcdcomputech.dequecloud.com/support.php, 3&4 https://abcdcomputech.dequecloud.com/laptopsandnotebooks.php
     */
    @Test
    @Description("Verify the Scan page state using navigate refresh wrap method")
    public void testNavigateRefresh() {
        driver.get("https://abcdcomputech.dequecloud.com");
        driver.findElement(By.cssSelector("#topnav > ul > li:nth-child(5) > a")).click();
        driver.findElement(By.cssSelector("#topnav > ul > li:nth-child(2) > a")).click();
        driver.navigate().refresh();  
    }

    /**
     * Test method to demonstrate the use of click() action in Selenium WebDriver.
     * Navigates to a specific URL, clicks on multiple elements, and flushes the Axe Watcher.
     * Expected Results:
     * - Branches and Commits page: Displays a new branch card, A11y threshold of 158, 4 page state, and the latest Axe Core/Watcher versions.
     * - Issue page: Identifies failure rules such as  color-contrast, image-alt, label, link-in-text-block, link-name, input-image-alt and marque issues.
     *  Page state: 4 https://abcdcomputech.dequecloud.com, https://abcdcomputech.dequecloud.com/support.php,  https://abcdcomputech.dequecloud.com/laptopsandnotebooks.php and https://abcdcomputech.dequecloud.com/desktops.php
     */
    @Test
    @Description("Verify the Scan page state using click wrap method")
    public void testFindElementClickAction() {
        driver.get("https://abcdcomputech.dequecloud.com");
        driver.findElement(By.cssSelector("#topnav > ul > li:nth-child(5) > a")).click();
        driver.findElement(By.cssSelector("#topnav > ul > li:nth-child(2) > a")).click();
        driver.findElement(By.cssSelector("#topnav > ul > li:nth-child(3) > a")).click();   
    }

    /**
     * Test method to demonstrate the use of findElement() - sendKeys() action in Selenium WebDriver.
     * Navigates to a login page, enters credentials, and submits the form.
     * Expected Results:
     * - Branches and Commits page: Displays a new branch card, A11y threshold of 2, 2 page state, and the latest Axe Core/Watcher versions.
     * - Issue page: Identifies failure rules such as  color-contrast issues.
     *  Page state: 2 https://the-internet.herokuapp.com/login and https://the-internet.herokuapp.com/secure
     */
    @Test
    @Description("Verify the Scan page state using sendkeys wrap method")
    public void testFindElementSendKeysAction() {
        driver.get("https://the-internet.herokuapp.com/login");
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("tomsmith");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("SuperSecretPassword!");
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.submit();
        WebElement flashMessage = driver.findElement(By.id("flash"));
        if (flashMessage.isDisplayed()) {
            System.out.println("Login successful! Flash message is visible.");
        } else {
            System.out.println("Login failed! Flash message not found.");
        } 
    }

    /**
     * Test method to demonstrate the use of clear() and submit() actions in Selenium WebDriver.
     * Navigates to a login page, clears the password field, re-enters credentials, and submits the form.
     * Expected Results:
     * - Branches and Commits page: Displays a new branch card, A11y threshold of 2, 2 page state, and the latest Axe Core/Watcher versions.
     * - Issue page: Identifies failure rules such as  color-contrast issues.
     *  Page state: 2 https://the-internet.herokuapp.com/login and https://the-internet.herokuapp.com/secure
     */
    @Test
    @Description("Verify the Scan page state using clear and submit wrap method")
    public void testFindElementClearAndSubmit() {
        driver.get("https://the-internet.herokuapp.com/login");
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("tomsmith");
        WebElement password = driver.findElement(By.id("password"));
        password.clear();
        password.sendKeys("SuperSecretPassword!");
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.submit();
        WebElement flashMessage = driver.findElement(By.id("flash"));
        if (flashMessage.isDisplayed()) {
            System.out.println("Login successful! Flash message is visible.");
        } else {
            System.out.println("Login failed! Flash message not found.");
        }
        
    }

     /**
     * Test method to demonstrate the use of findElements(), click() actions in Selenium WebDriver.
     * Navigates to a login page, clears the password field, re-enters credentials, and submits the form.
     * Expected Results:
     * - Branches and Commits page: Displays a new branch card, A11y threshold of 104, 4 page state, and the latest Axe Core/Watcher versions.
     * - Issue page: Identifies failure rules such as  color-contrast, image-alt, label, link-in-text-block, link-name, input-image-alt and marque issues.
     *  Page state: 4 https://abcdcomputech.dequecloud.com, https://abcdcomputech.dequecloud.com/support.php, 3&4 https://abcdcomputech.dequecloud.com/laptopsandnotebooks.php
     */
    @Test
    @Description("Verify the Scan page state using findElements(), click() wrap method ")
    public void testFindElements() {
        driver.get("https://abcdcomputech.dequecloud.com");

        List<WebElement> scanNavLinks = driver.findElements(By.cssSelector("#topnav > ul > li:nth-child(5) > a"));
        if (!scanNavLinks.isEmpty()) {
            scanNavLinks.get(0).click();
        } else {
        System.out.println("Scan navigation link not found.");
        }

        List<WebElement> dashboardNavLinks = driver.findElements(By.cssSelector("#topnav > ul > li:nth-child(2) > a"));
        if (!dashboardNavLinks.isEmpty()) {
            dashboardNavLinks.get(0).click();
        } else {
        System.out.println("Dashboard navigation link not found.");
        }

        driver.navigate().refresh();
    }
}