package com.deque;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.deque.axe_core.commons.AxeWatcherOptions;
import com.deque.axe_core.selenium.AxeWatcher;
import com.deque.axe_core.selenium.AxeWatcherDriver;
import com.deque.util.EnvLoader;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * This class demonstrates the use of Axe Watcher configurations for accessibility testing.
 * It includes setup and teardown methods, a data provider for different configurations for excluding URLs,
 * and a test method to validate accessibility using various configurations.
 */
public class ExcludeUrlTest {

    private WebDriver driver;

    /**
     * Cleans up after each test by quitting the WebDriver.
     * Ensures that the browser is closed and the WebDriver instance is set to null.
     */
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            ((AxeWatcherDriver) driver).axeWatcher().flush();
            driver.quit();
            driver = null; 
        }
    }

    /**
     * Provides different configurations for Axe Watcher using a data provider.
     * Each configuration includes API key, server URL, and different patterns for excluding the scanned urls.
     *
     * @return A 2D array of {@link AxeWatcherOptions} objects with different configurations.
     */
    @DataProvider(name = "excludeUrlConfigurations")
    public Object[][] provideConfigurations() {
        // Load environment variables
        // Generate a unique run ID if not provided
        // This is useful for identifying the test run in the Axe Watcher dashboard.
        // The run ID is generated randomly if not set in the environment variables used to group results for a specific test run.
        String githubRunId = EnvLoader.get("GITHUB_RUN_ID");
        if (githubRunId == null) {
            githubRunId = "RUN-" + (int) (Math.random() * 100000);
        }
        String apiKey = EnvLoader.get("API_KEY");
        String serverUrl = EnvLoader.get("SERVER_URL");
        return new Object[][] {
            /**
             * This configuration excludes URLs that match the pattern "https://abcdcomputech.dequecloud.com".
             * Actual scans done on 5 pages but as we excluded "https://abcdcomputech.dequecloud.com" , we cannt see this page state in the issues page-page states list
             * Expected Results: 155 issues with 4 page states
             * Branches and Commits page: Displays a new branch card, A11y threshold of 155, 4 page states, and the latest Axe Core/Watcher versions.
             * - Issue page: Identifies failure rules such as  color-contrast, image-alt, label, link-in-text-block, link-name, input-image-alt and marque issues.
             Page state: 4 https://abcdcomputech.dequecloud.com/cart.php, https://abcdcomputech.dequecloud.com/support.php,  https://abcdcomputech.dequecloud.com/laptopsandnotebooks.php and https://abcdcomputech.dequecloud.com/desktops.php
             */
            { "ExclueSingleUrl", new AxeWatcherOptions()
                    .setApiKey(apiKey)
                    .setServerUrl(serverUrl)
                    .setBuildId(githubRunId)
                    .setExcludeUrlPatterns(new String[]{"https://abcdcomputech.dequecloud.com"})
              },
             /**
             * This configuration excludes more than one URLs that match the pattern.
             * Actual scans done on 5 pages but as we excluded "https://abcdcomputech.dequecloud.com/desktops.php" , "https://abcdcomputech.dequecloud.com/support.php" , 
             * we cannt see these two page state in the issues page-page states list
             * Expected Results: 112 issues with 3 page states
             * Branches and Commits page: Displays a new branch card, A11y threshold of 112, 3 page states, and the latest Axe Core/Watcher versions.
             * - Issue page: Identifies failure rules such as  color-contrast, image-alt, label, link-in-text-block, link-name issues.
             Page state: 3 https://abcdcomputech.dequecloud.com  https://abcdcomputech.dequecloud.com/cart.php and https://abcdcomputech.dequecloud.com/laptopsandnotebooks.php 
             */
            {"ExcludeMultiUrl", new AxeWatcherOptions()
                    .setApiKey(apiKey)
                    .setServerUrl(serverUrl)
                    .setBuildId(githubRunId)
                    .setExcludeUrlPatterns(new String[]{"https://abcdcomputech.dequecloud.com/desktops.php" , "https://abcdcomputech.dequecloud.com/support.php"})
            },
              /**
             * This configuration excludes  URLs that match the pattern.
             * Actual scans done on 5 pages but as we excluded  all the other urls with pattern match - "https://abcdcomputech.dequecloud.com/*.* *"" , 
             * we cannt see these all the other  page states except  https://abcdcomputech.dequecloud.com in the issues page-page states list
             * Expected Results: 29 issues with 1 page states
             * Branches and Commits page: Displays a new branch card, A11y threshold of 29, 1 page states, and the latest Axe Core/Watcher versions.
             * - Issue page: Identifies failure rules such as  color-contrast, image-alt, label, link-in-text-block, link-name issues.
             Page state: 1 https://abcdcomputech.dequecloud.com  
             */
            {"ExcludeUrlwithPatter", new AxeWatcherOptions()
                    .setApiKey(apiKey)
                    .setServerUrl(serverUrl)
                    .setBuildId(githubRunId)
                    .setExcludeUrlPatterns(new String[]{"https://abcdcomputech.dequecloud.com/*.*"})
            },
                /**
             * This configuration excludes  URLs that match the pattern.
             * Actual scans done on 5 pages but as we excluded  all the urls with pattern match **.* *"" , 
             * we cannt see any  other  page states in the issues page-page states list
             * Expected Results: 0 issues with 0 page states
             * Branches and Commits page: Displays a new branch card, A11y threshold of 0, 0 page states, and the latest Axe Core/Watcher versions.
             * - Issue page: 0 issues
             Page state: 0 
             */
            {"ExcludeAll", new AxeWatcherOptions()
                    .setApiKey(apiKey)
                    .setServerUrl(serverUrl)
                    .setBuildId(githubRunId)
                    .setExcludeUrlPatterns(new String[]{"**/*.*"})
            },
             /**
             * This configuration excludes more than one URLs that match the pattern.
             * Actual scans done on 5 pages but as we specified that to exclude url but didnt pass url specifically that can be excluded from the scanned urls 
             * we can see all the 5 scanned page states in the issues page-page states list
             * Expected Results: 184 issues with 5 page states
             * Branches and Commits page: Displays a new branch card, A11y threshold of 184, 5 page states, and the latest Axe Core/Watcher versions.
             * - Issue page: Identifies failure rules such as  color-contrast, image-alt, label, link-in-text-block, link-name, input-image-alt and marque issues.
             Page state: 5 https://abcdcomputech.dequecloud.com  https://abcdcomputech.dequecloud.com/cart.php and https://abcdcomputech.dequecloud.com/laptopsandnotebooks.php 
             "https://abcdcomputech.dequecloud.com/desktops.php" , "https://abcdcomputech.dequecloud.com/support.php
             */
            {"Excludenull", new AxeWatcherOptions()
                    .setApiKey(apiKey)
                    .setServerUrl(serverUrl)
                    .setBuildId(githubRunId)
                    .setExcludeUrlPatterns(new String[]{" "})
            },
             /**
             * This configuration excludes more than one URLs that match the pattern.
             * Actual scans done on 5 pages but as we specified that to exclude url but didnt pass url specifically that can be excluded from the scanned urls 
             * we can see all the 5 scanned page states in the issues page-page states list
             * Expected Results: 184 issues with 5 page states
             * Branches and Commits page: Displays a new branch card, A11y threshold of 184, 5 page states, and the latest Axe Core/Watcher versions.
             * - Issue page: Identifies failure rules such as  color-contrast, image-alt, label, link-in-text-block, link-name, input-image-alt and marque issues.
             Page state: 5 https://abcdcomputech.dequecloud.com  https://abcdcomputech.dequecloud.com/cart.php and https://abcdcomputech.dequecloud.com/laptopsandnotebooks.php 
             "https://abcdcomputech.dequecloud.com/desktops.php" , "https://abcdcomputech.dequecloud.com/support.php
             */
            {"ExcludenonUrlpattern", new AxeWatcherOptions()
                    .setApiKey(apiKey)
                    .setServerUrl(serverUrl)
                    .setBuildId(githubRunId)
                    .setExcludeUrlPatterns(new String[]{"Google Page"})
            },
        };
    }

    /**
     * Executes accessibility tests using different Axe Watcher configurations.
     * The test navigates to a sample page and flushes the Axe Watcher results.
     * Expected Results:
     * - Branches and Commits page: 
     * - Issue page:
     * @param options The {@link AxeWatcherOptions} object containing the configuration for the test.
     * @throws InterruptedException If the thread is interrupted during execution.
     */
    @Test(dataProvider = "excludeUrlConfigurations")
    public void testExcludeUrlConfigurations(String ConfigName, AxeWatcherOptions options) throws InterruptedException {
        System.out.println("Running test for configuration: " + ConfigName);
        // Initialize Axe Watcher with the provided options
        AxeWatcher watcher = new AxeWatcher(options).enableDebugLogger();

        // Set up WebDriver using WebDriverManager and Axe Watcher
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = watcher.configure(new ChromeOptions());
        driver = watcher.wrapDriver(new ChromeDriver(chromeOptions));

        // Navigate to the test page and flush Axe Watcher results
        driver.navigate().to("https://abcdcomputech.dequecloud.com");
        driver.findElement(By.cssSelector("#topnav > ul > li:nth-child(4) > a")).click();
        driver.findElement(By.cssSelector("#topnav > ul > li:nth-child(5) > a")).click();
        driver.findElement(By.cssSelector("#topnav > ul > li:nth-child(2) > a")).click();
        driver.findElement(By.cssSelector("#topnav > ul > li:nth-child(3) > a")).click();
       
    }
}




