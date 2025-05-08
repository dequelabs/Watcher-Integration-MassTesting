package com.deque;

import java.util.Arrays;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.deque.axe_core.commons.AxeRuleOptions;
import com.deque.axe_core.commons.AxeRunContext;
import com.deque.axe_core.commons.AxeRunOnly;
import com.deque.axe_core.commons.AxeRunOptions;
import com.deque.axe_core.commons.AxeWatcherOptions;
import com.deque.axe_core.selenium.AxeWatcher;
import com.deque.axe_core.selenium.AxeWatcherDriver;
import com.deque.util.EnvLoader;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * This class demonstrates the use of Axe Watcher configurations for accessibility testing.
 * It includes setup and teardown methods, a data provider for different configurations,
 * and a test method to validate accessibility using various configurations.
 */
public class AxeConfigurationsTest {

    private WebDriver driver;

    /**
     * Cleans up after each test by quitting the WebDriver.
     * Ensures that the browser is closed and the WebDriver instance is set to null.
     */
    @AfterMethod
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
    @DataProvider(name = "axeConfigurations")
    public Object[][] provideConfigurations() {
        // Load environment variables
        String githubRunId = EnvLoader.get("GITHUB_RUN_ID");
        if (githubRunId == null) {
            githubRunId = "RUN-" + (int) (Math.random() * 100000);
        }
        String apiKey = EnvLoader.get("API_KEY");
        String serverUrl = EnvLoader.get("SERVER_URL");

        // Create different configurations for Axe Watcher
        return new Object[][] {
            {
                new AxeWatcherOptions()
                    .setApiKey(apiKey)
                    .setServerUrl(serverUrl)
                    .setBuildId(githubRunId)
                    .setRunOptions(
                        new AxeRunOptions().setRules(new HashMap<String, AxeRuleOptions>() {{
                            put("image-alt", new AxeRuleOptions().setEnabled(false));
                        }})
                    )
            },
            {
                 new AxeWatcherOptions()
                    .setApiKey(apiKey)
                    .setServerUrl(serverUrl)
                    .setBuildId(githubRunId)
                    .setRunOptions(
                        new AxeRunOptions().setRules(new HashMap<String, AxeRuleOptions>() {{
                            put("image-alt", new AxeRuleOptions().setEnabled(false));
                            put("color-contrast", new AxeRuleOptions().setEnabled(false));
                        }})
                    )
            },
            {
                new AxeWatcherOptions()
                    .setApiKey(apiKey)
                    .setServerUrl(serverUrl)
                    .setBuildId(githubRunId)
                    .setRunContext(
                        new AxeRunContext().setInclude(Arrays.asList("#wcag2aa-fail"))
                    )
            },
            {
                new AxeWatcherOptions()
                    .setApiKey(apiKey)
                    .setServerUrl(serverUrl)
                    .setBuildId(githubRunId)
                    .setRunContext(
                        new AxeRunContext().setInclude(Arrays.asList("#wcag2aa-fail","#wcag21aa-fail"))
                    )
            },
            {
                new AxeWatcherOptions()
                    .setApiKey(apiKey)
                    .setServerUrl(serverUrl)
                    .setBuildId(githubRunId)
                    .setRunContext(
                        new AxeRunContext().setExclude(Arrays.asList("#wcag2aa"))
                    )
            },
            {
                new AxeWatcherOptions()
                    .setApiKey(apiKey)
                    .setServerUrl(serverUrl)
                    .setBuildId(githubRunId)
                    .setRunContext(
                        new AxeRunContext().setExclude(Arrays.asList("#wcag2aa-fail","#wcag21aa-fail"))
                    )
            },
            {
                new AxeWatcherOptions()
                    .setApiKey(apiKey)
                    .setServerUrl(serverUrl)
                    .setBuildId(githubRunId)
                   .setRunOptions(
                           new AxeRunOptions()
                                   .setRules(new HashMap<String, AxeRuleOptions>() 
                                   {{
                                    put("color-contrast", new AxeRuleOptions().setEnabled(true));
                                }})
                                   .setRunOnly(new AxeRunOnly()
                                           .setType("rule")
                                           .setValues(Arrays.asList("color-contrast"))))
                            
            },
            {
                new AxeWatcherOptions()
                    .setApiKey(apiKey)
                    .setServerUrl(serverUrl)
                    .setBuildId(githubRunId)
                   .setRunOptions(
                           new AxeRunOptions()
                                   .setRules(new HashMap<String, AxeRuleOptions>() 
                                   {{
                                    put("color-contrast", new AxeRuleOptions().setEnabled(true));
                                    put("label", new AxeRuleOptions().setEnabled(true));
                                }})
                                   .setRunOnly(new AxeRunOnly()
                                           .setType("rule")
                                           .setValues(Arrays.asList("color-contrast","label"))))
                            
            },
            {
                new AxeWatcherOptions()
                    .setApiKey(apiKey)
                    .setServerUrl(serverUrl)
                    .setBuildId(githubRunId)
                   .setRunOptions(
                           new AxeRunOptions()
                                   .setRules(new HashMap<String, AxeRuleOptions>() 
                                   {{
                                    
                                }})
                                   .setRunOnly(new AxeRunOnly()
                                           .setType("tag")
                                           .setValues(Arrays.asList("wcag21aa"))))
                            
            },
            {
                new AxeWatcherOptions()
                    .setApiKey(apiKey)
                    .setServerUrl(serverUrl)
                    .setBuildId(githubRunId)
                   .setRunOptions(
                           new AxeRunOptions()
                                   .setRules(new HashMap<String, AxeRuleOptions>() 
                                   {{
                                    
                                }})
                                   .setRunOnly(new AxeRunOnly()
                                           .setType("tag")
                                           .setValues(Arrays.asList("wcag21aa","wcag2aa"))))
                            
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
    @Test(dataProvider = "axeConfigurations")
    public void testAxeConfigurations(AxeWatcherOptions options) throws InterruptedException {
        // Initialize Axe Watcher with the provided options
        AxeWatcher watcher = new AxeWatcher(options).enableDebugLogger();

        // Set up WebDriver using WebDriverManager and Axe Watcher
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = watcher.configure(new ChromeOptions());
        driver = watcher.wrapDriver(new ChromeDriver(chromeOptions));

        // Navigate to the test page and flush Axe Watcher results
        driver.get("https://qateam.dequecloud.com/attest/api/test.html");
        ((AxeWatcherDriver) driver).axeWatcher().flush();
    }
}