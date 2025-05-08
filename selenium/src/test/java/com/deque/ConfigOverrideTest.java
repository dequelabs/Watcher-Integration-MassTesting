package com.deque;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;

import com.deque.axe_core.commons.AxeWatcherOptions;
import com.deque.axe_core.selenium.AxeWatcher;
import com.deque.axe_core.selenium.AxeWatcherDriver;
import com.deque.util.EnvLoader;
import com.deque.axe_core.commons.ConfigurationOverrides;
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
            {
                new AxeWatcherOptions()
                    .setApiKey(apiKey)
                    .setServerUrl(serverUrl)
                    .setBuildId(githubRunId)
                    .setConfigurationOverrides(
                           new ConfigurationOverrides().setAxeCoreVersion("4.8.0"))
              },
            {
                new AxeWatcherOptions()
                    .setApiKey(apiKey)
                    .setServerUrl(serverUrl)
                    .setBuildId(githubRunId)
                    .setConfigurationOverrides(
                     new ConfigurationOverrides()
                             .setAccessibilityStandard(ConfigurationOverrides.AccessibilityStandard.WCAG22AAA))
            },
            {
                new AxeWatcherOptions()
                    .setApiKey(apiKey)
                    .setServerUrl(serverUrl)
                    .setBuildId(githubRunId)
                    .setConfigurationOverrides(
                     new ConfigurationOverrides().setEnableExperimental(true))
            },
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