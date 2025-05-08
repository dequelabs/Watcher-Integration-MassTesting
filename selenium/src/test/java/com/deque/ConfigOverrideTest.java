package com.deque;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.deque.axe_core.commons.AxeWatcherOptions;
import com.deque.axe_core.commons.ConfigurationOverrides;
import com.deque.axe_core.selenium.AxeWatcher;
import com.deque.axe_core.selenium.AxeWatcherDriver;
import com.deque.util.EnvLoader;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * This class demonstrates the use of Axe Watcher configurations for accessibility testing.
 * It includes setup and teardown methods, a data provider for different configurations,
 * and a test method to validate accessibility using various configurations.
 */
public class ConfigOverrideTest {

    private WebDriver driver;

    /**
     * Cleans up after each test by quitting the WebDriver.
     * Ensures that the browser is closed and the WebDriver instance is set to null.
     */
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null; 
        }
    }

    /**
     * Provides different configurations for Axe Watcher using a data provider.
     * Each configuration includes API key, server URL, and specific run options or contexts.
     *
     * @return A 2D array of {@link AxeWatcherOptions} objects with different configurations.
     */
    @DataProvider(name = "configureOverrides")
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
             * This configuration overrides the axe-core version already holding with the specified override version
             * in this case latest version to 4.8.0.
             * Expected Results:
             * - Branches and Commits page: Displays a new branch card, A11y threshold of 29, 1 page state, and the  Axe Core verison is 4.8.0 Watcher version should be latest.
             * - Issue page: Identifies failure rules such as  color-contrast, image-alt, label, link-in-text-block, link-name.
             *  Page state: 1 https://abcdcomputech.dequecloud.com
             */
            {
                new AxeWatcherOptions()
                    .setApiKey(apiKey)
                    .setServerUrl(serverUrl)
                    .setBuildId(githubRunId)
                    .setConfigurationOverrides(
                           new ConfigurationOverrides().setAxeCoreVersion("4.8.0"))
              },
             /**
             * This configuration overrides the accessibility stantdard already holding with the specified override standard
             * in this case it is WCAG22AAA. so that we can see the results related to wcag 2.2 AAA. if any on the test page, target-size issues
             * Expected Results:
             * - Branches and Commits page: Displays a new branch card, A11y threshold of 65, 1 page state, and the  Axe Core verison and Watcher version should be latest.
             * - Issue page: Identifies failure rules such as  color-contrast, image-alt, label, link-in-text-block, link-name, target-size.
             *  Page state: 1 https://abcdcomputech.dequecloud.com
             */
            {
                new AxeWatcherOptions()
                    .setApiKey(apiKey)
                    .setServerUrl(serverUrl)
                    .setBuildId(githubRunId)
                    .setConfigurationOverrides(
                     new ConfigurationOverrides()
                             .setAccessibilityStandard(ConfigurationOverrides.AccessibilityStandard.WCAG22AAA))
            },
             /**
             * This configuration overrides the experimental rules enabled/disabled already holding with the specified override enabled/diabled status
             * in this case it is we are enabling to show the experimental issues on the page if any. If there is no experimental issues on the page results cannot be shown as swtiched status
             * Expected Results:
             * - Branches and Commits page: Displays a new branch card, A11y threshold of 29, 1 page state, and the  Axe Core verison and  Watcher version should be latest.
             * - Issue page: Identifies failure rules such as  color-contrast, image-alt, label, link-in-text-block, link-name.
             *  Page state: 1 https://abcdcomputech.dequecloud.com
             */
            {
                new AxeWatcherOptions()
                    .setApiKey(apiKey)
                    .setServerUrl(serverUrl)
                    .setBuildId(githubRunId)
                    .setConfigurationOverrides(
                     new ConfigurationOverrides().setEnableExperimental(true))
            },
              /**
             * This configuration overrides the best-practice rules enabled/disabled already holding with the specified override enabled/diabled status
             * in this case it is we are enabling to show the best-practice issues on the page if any. If there is no experimental issues on the page results cannot be shown as swtiched status
             * Expected Results:
             * - Branches and Commits page: Displays a new branch card, A11y threshold of 29 (40 issues with 11 best-practices in the issues summary table can show), 1 page state, and the  Axe Core Watcher version should be latest.
             * - Issue page: Identifies failure rules such as  color-contrast, heading-order, image-alt, label, landmark-one-main,link-in-text-block, link-name and region.
             *  Page state: 1 https://abcdcomputech.dequecloud.com
             */
            {
                new AxeWatcherOptions()
                    .setApiKey(apiKey)
                    .setServerUrl(serverUrl)
                    .setBuildId(githubRunId)
                    .setConfigurationOverrides(
                     new ConfigurationOverrides().setEnableBestPractices(true))
            },
        };
    }

    /**
     * Executes accessibility tests using different Axe Watcher configurations.
     * The test navigates to a sample page and flushes the Axe Watcher results.
     *
     * @param options The {@link AxeWatcherOptions} object containing the configuration for the test.
     * @throws InterruptedException If the thread is interrupted during execution.
     */
    @Test(dataProvider = "configureOverrides")
    public void testWithDifferentConfigurations(AxeWatcherOptions options) throws InterruptedException {
        // Initialize Axe Watcher with the provided options
        AxeWatcher watcher = new AxeWatcher(options).enableDebugLogger();

        // Set up WebDriver using WebDriverManager and Axe Watcher
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = watcher.configure(new ChromeOptions());
        driver = watcher.wrapDriver(new ChromeDriver(chromeOptions));

        // Navigate to the test page and flush Axe Watcher results
        driver.get("https://abcdcomputech.dequecloud.com");
        ((AxeWatcherDriver) driver).axeWatcher().flush();
    }
}