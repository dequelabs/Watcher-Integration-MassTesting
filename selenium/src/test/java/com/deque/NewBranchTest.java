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
public class NewBranchTest {

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
    @Description("Verify the Scan result pushes to new branch")
    public void testGitBranch() {
        driver.get("https://qateam.dequecloud.com/attest/api/test.html");   
    }
}
