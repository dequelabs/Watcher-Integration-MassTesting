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
            /**
             * Test method to demonstrate the use of the disable certain rule from the scan results 
             * Expected Results:
             * - Branches and Commits page: Displays a new branch card, A11y threshold of 5, 1 page state, and the latest Axe Core/Watcher versions.
             * - Issue page: Identifies failure rules such as auto-complete-valid, aria-allowed-attribute,   
             *   label, and link-in-text-block issues. Page state: 1
             */
            {
                new AxeWatcherOptions()
                    .setApiKey(apiKey)
                    .setServerUrl(serverUrl)
                    .setBuildId(githubRunId)
                    .setRunOptions(
                        new AxeRunOptions().setRules(new HashMap<String, AxeRuleOptions>() {{
                            put("color-contrast", new AxeRuleOptions().setEnabled(false));
                        }})
                    )
            },
             /**
             * Test method to demonstrate the use of the disable multiple rules from the scan results 
             * Expected Results:
             * - Branches and Commits page: Displays a new branch card, A11y threshold of 4, 1 page state, and the latest Axe Core/Watcher versions.
             * - Issue page: Identifies failure rules such as auto-complete-valid, aria-allowed-attribute and link-in-text-block issues. Page state: 1
             */
            {
                 new AxeWatcherOptions()
                    .setApiKey(apiKey)
                    .setServerUrl(serverUrl)
                    .setBuildId(githubRunId)
                    .setRunOptions(
                        new AxeRunOptions().setRules(new HashMap<String, AxeRuleOptions>() {{
                            put("label", new AxeRuleOptions().setEnabled(false));
                            put("color-contrast", new AxeRuleOptions().setEnabled(false));
                        }})
                    )
            },
            /**
             * Test method to demonstrate the use of the scope of particular element that only can be included  from the scan results 
             * Expected Results:
             * - Branches and Commits page: Displays a new branch card, A11y threshold of 1, 1 page state, and the latest Axe Core/Watcher versions.
             * - Issue page: Identifies failure rules such as color-contrast. Page state: 1
             */
            {
                new AxeWatcherOptions()
                    .setApiKey(apiKey)
                    .setServerUrl(serverUrl)
                    .setBuildId(githubRunId)
                    .setRunContext(
                        new AxeRunContext().setInclude(Arrays.<String>asList("#wcag2aa-fail"))
                    )
            },
             /**
             * Test method to demonstrate the use of the scope of particular elements that only can be included  from the scan results 
             * Expected Results:
             * - Branches and Commits page: Displays a new branch card, A11y threshold of 2, 1 page state, and the latest Axe Core/Watcher versions.
             * - Issue page: Identifies failure rules such as color-contrast and autocomplete-valid Page state: 1
             */
            {
                new AxeWatcherOptions()
                    .setApiKey(apiKey)
                    .setServerUrl(serverUrl)
                    .setBuildId(githubRunId)
                    .setRunContext(
                        new AxeRunContext().setInclude(Arrays.<String>asList("#wcag2aa-fail", "#wcag21aa-fail"))
                    )
            },
             /**
             * Test method to demonstrate the use of the scope of particular element that only can be excluded  from the scan results 
             * Expected Results: (except color-contrast issue all the other issues can be foound)
             * - Branches and Commits page: Displays a new branch card,  A11y threshold of 5, 1 page state, and the latest Axe Core/Watcher versions.
             * - Issue page: Identifies failure rules such as auto-complete-valid, aria-allowed-attribute,   
             *   label, and link-in-text-block issues. Page state: 1
             */
            {
                new AxeWatcherOptions()
                    .setApiKey(apiKey)
                    .setServerUrl(serverUrl)
                    .setBuildId(githubRunId)
                    .setRunContext(
                        new AxeRunContext().setExclude(Arrays.<String>asList("#wcag2aa"))
                    )
            },
             /**
             * Test method to demonstrate the use of the scope of particular elements that only can be excluded  from the scan results 
             * Expected Results: (except color-contrast and autocomplete-valid issues all the other issues can be foound)
             * - Branches and Commits page: Displays a new branch card,  A11y threshold of 4, 1 page state, and the latest Axe Core/Watcher versions.
             * - Issue page: Identifies failure rules such as  aria-allowed-attribute, label, and link-in-text-block issues. Page state: 1
             */
            {
                new AxeWatcherOptions()
                    .setApiKey(apiKey)
                    .setServerUrl(serverUrl)
                    .setBuildId(githubRunId)
                    .setRunContext(
                        new AxeRunContext().setExclude(Arrays.<String>asList("#wcag2aa-fail", "#wcag21aa-fail"))
                    )
            },
             /**
             * Test method to demonstrate the use of the runoption runonly rule  from the scan results 
             * Expected Results: (only color-contrast  issues appeared all the other issues cannot be foound)
             * - Branches and Commits page: Displays a new branch card,  A11y threshold of 1, 1 page state, and the latest Axe Core/Watcher versions.
             * - Issue page: Identifies failure rules such as color-contrast issues. Page state: 1
             */
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
            /**
             * Test method to demonstrate the use of the runoption runonly rules  from the scan results 
             * Expected Results: (only color-contrast and label rule  issues only appeared all the other issues cannot be foound)
             * - Branches and Commits page: Displays a new branch card,  A11y threshold of 2, 1 page state, and the latest Axe Core/Watcher versions.
             * - Issue page: Identifies failure rules such as color-contrast and label issues. Page state: 1
             */
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
            /**
             * Test method to demonstrate the use of the runoption runonly standargs(tags)  from the scan results 
             * Expected Results: (only wcag21aa standard  rules  issues  appeared all the other issues cannot be foound)
             * - Branches and Commits page: Displays a new branch card,  A11y threshold of 1, 1 page state, and the latest Axe Core/Watcher versions.
             * - Issue page: Identifies failure rules such as autocomplete-valid issues. Page state: 1
             */
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
             /**
             * Test method to demonstrate the use of the runoption runonly multiple standargs(tags)  from the scan results 
             * Expected Results: (only wcag21aa & wcag2aa standard  rules  issues  appeared all the other issues cannot be foound)
             * - Branches and Commits page: Displays a new branch card,  A11y threshold of 2, 1 page state, and the latest Axe Core/Watcher versions.
             * - Issue page: Identifies failure rules such as autocomplete-valid and color-contrast issues. Page state: 1
             */
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