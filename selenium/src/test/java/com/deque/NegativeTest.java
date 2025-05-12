package com.deque;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import com.deque.axe_core.commons.AxeWatcherOptions;
import com.deque.axe_core.selenium.AxeWatcher;
import com.deque.axe_core.selenium.AxeWatcherDriver;
import com.deque.util.EnvLoader;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * This class demonstrates various Selenium WebDriver methods and actions
 * integrated with Axe Watcher for accessibility testing using TestNG.
 * It includes setup and teardown methods for initializing and closing the WebDriver,
 * as well as multiple test methods to demonstrate navigation and element interactions.
 */
public class NegativeTest {



        /**
         * Negative test that simulates passing an invalid API key to Axe Watcher.
         * Test passes if Axe Watcher fails with the expected error message.
         */
        @Test
        public void invalidAPIKEYTest() {
            WebDriver driver = null;
    
            try {
                 // Load environment variables
        String githubRunId = EnvLoader.get("GITHUB_RUN_ID");
        if (githubRunId == null) {
            githubRunId = "RUN-" + (int) (Math.random() * 100000);
        }
                // Simulate invalid API key
                String invalidApiKey = EnvLoader.get("INVALID_API_KEY");
                String serverUrl = EnvLoader.get("SERVER_URL");
               
                AxeWatcherOptions options = new AxeWatcherOptions()
                        .setApiKey(invalidApiKey)
                        .setServerUrl(serverUrl)
                        .setBuildId(githubRunId);
    
                AxeWatcher watcher = new AxeWatcher(options).enableDebugLogger();
    
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = watcher.configure(new ChromeOptions());
    
                // This should throw a RuntimeException due to invalid API key
                driver = watcher.wrapDriver(new ChromeDriver(chromeOptions));
                driver.get("https://abcdcomputech.dequecloud.com");
                ((AxeWatcherDriver) driver).axeWatcher().flush();
    
                // If initialization unexpectedly succeeds, fail the test
                assert false : "Axe Watcher session initialized successfully with an invalid API key — this is not expected.";
    
            } catch (RuntimeException e) {
                // Expecting specific error from Axe Watcher
                String expectedError = "Invalid API key";
                assert e.getMessage().contains(expectedError) :
                        "Unexpected error message. Expected to contain: \"" + expectedError + "\", but got: " + e.getMessage();
    
                System.out.println("Caught expected exception: " + e.getMessage());
            } finally {
                // Clean up driver if it was somehow initialized
                if (driver != null) {
                    driver.quit();
                }
            }
           
        }

         /**
         * Negative test that simulates passing no API key to Axe Watcher.
         * Test passes if Axe Watcher fails with the expected error message.
         */
        @Test
        public void noAPIKEYTest() {
            WebDriver driver = null;
    
            try {
                 // Load environment variables
        String githubRunId = EnvLoader.get("GITHUB_RUN_ID");
        if (githubRunId == null) {
            githubRunId = "RUN-" + (int) (Math.random() * 100000);
        }
                // Simulate invalid API key
                String invalidApiKey = EnvLoader.get("NO_API_KEY");
                String serverUrl = EnvLoader.get("SERVER_URL");
               
                AxeWatcherOptions options = new AxeWatcherOptions()
                        .setApiKey(invalidApiKey)
                        .setServerUrl(serverUrl)
                        .setBuildId(githubRunId);
    
                AxeWatcher watcher = new AxeWatcher(options).enableDebugLogger();
    
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = watcher.configure(new ChromeOptions());
    
                // This should throw a RuntimeException due to invalid API key
                driver = watcher.wrapDriver(new ChromeDriver(chromeOptions));
                driver.get("https://abcdcomputech.dequecloud.com");
                ((AxeWatcherDriver) driver).axeWatcher().flush();
    
                // If initialization unexpectedly succeeds, fail the test
                assert false : "Axe Watcher session initialized successfully with an invalid API key — this is not expected.";
    
            } catch (RuntimeException e) {
                // Expecting specific error from Axe Watcher
                String expectedError = "Invalid API key";
                assert e.getMessage().contains(expectedError) :
                        "Unexpected error message. Expected to contain: \"" + expectedError + "\", but got: " + e.getMessage();
    
                System.out.println("Caught expected exception: " + e.getMessage());
            } finally {
                // Clean up driver if it was somehow initialized
                if (driver != null) {
                    driver.quit();
                }
            }
           
        }

        /**
         * Negative test that simulates passing an invalid server url to Axe Watcher.
         * Test passes if Axe Watcher fails with the expected error message.
         */
        @Test
        public void invalidServerURLTest() {
            WebDriver driver = null;
    
            try {
                 // Load environment variables
        String githubRunId = EnvLoader.get("GITHUB_RUN_ID");
        if (githubRunId == null) {
            githubRunId = "RUN-" + (int) (Math.random() * 100000);
        }
                // Simulate invalid API key
                String invalidApiKey = EnvLoader.get("API_KEY");
                String serverUrl = EnvLoader.get("INVALID_SERVER_URL");
               
                AxeWatcherOptions options = new AxeWatcherOptions()
                        .setApiKey(invalidApiKey)
                        .setServerUrl(serverUrl)
                        .setBuildId(githubRunId);
    
                AxeWatcher watcher = new AxeWatcher(options).enableDebugLogger();
    
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = watcher.configure(new ChromeOptions());
    
                // This should throw a RuntimeException due to invalid API key
                driver = watcher.wrapDriver(new ChromeDriver(chromeOptions));
                driver.get("https://abcdcomputech.dequecloud.com");
                ((AxeWatcherDriver) driver).axeWatcher().flush();
    
               // Fail the test if no exception occurs
            assert false : "Axe Watcher initialized with invalid server URL — this is unexpected.";

        } catch (RuntimeException e) {
            // Expected exception due to invalid server URL
            String expectedError = "Could not write to variables.json file";
            assert e.getMessage().contains(expectedError) :
                    "Unexpected error. Expected message containing: \"" + expectedError + "\", but got: " + e.getMessage();

            System.out.println("Caught expected exception: " + e.getMessage());
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
           
        }
        /**
         * Negative test that simulates passing no server url to Axe Watcher.
         * Test passes if Axe Watcher fails with the expected error message.
         */
        @Test
        public void noServerURLTest() {
            WebDriver driver = null;
    
            try {
                 // Load environment variables
        String githubRunId = EnvLoader.get("GITHUB_RUN_ID");
        if (githubRunId == null) {
            githubRunId = "RUN-" + (int) (Math.random() * 100000);
        }
                // Simulate invalid API key
                String invalidApiKey = EnvLoader.get("API_KEY");
                String serverUrl = EnvLoader.get("NO_SERVER_URL");
               
                AxeWatcherOptions options = new AxeWatcherOptions()
                        .setApiKey(invalidApiKey)
                        .setServerUrl(serverUrl)
                        .setBuildId(githubRunId);
    
                AxeWatcher watcher = new AxeWatcher(options).enableDebugLogger();
    
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = watcher.configure(new ChromeOptions());
    
                // This should throw a RuntimeException due to invalid API key
                driver = watcher.wrapDriver(new ChromeDriver(chromeOptions));
                driver.get("https://abcdcomputech.dequecloud.com");
                ((AxeWatcherDriver) driver).axeWatcher().flush();
    
               // Fail the test if no exception occurs
            assert false : "Axe Watcher initialized with no server URL — this is unexpected.";

        } catch (RuntimeException e) {
            // Expected exception due to invalid server URL
            String expectedError = "URI is not absolute";
            assert e.getMessage().contains(expectedError) :
                    "Unexpected error. Expected message containing: \"" + expectedError + "\", but got: " + e.getMessage();

            System.out.println("Caught expected exception: " + e.getMessage());
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
           
        }

      /**
         * Negative test that simulates passing chromeoptions as healdless mode  to Axe Watcher.
         * Test passes if Axe Watcher fails with the expected error message.
         */
        @Test
        public void healdlessModeTest() {
            WebDriver driver = null;
    
            try {
                 // Load environment variables
        String githubRunId = EnvLoader.get("GITHUB_RUN_ID");
        if (githubRunId == null) {
            githubRunId = "RUN-" + (int) (Math.random() * 100000);
        }
                // Simulate healdless mode
                String apiKey = EnvLoader.get("API_KEY");
                String serverUrl = EnvLoader.get("SERVER_URL");
               
                AxeWatcherOptions options = new AxeWatcherOptions()
                        .setApiKey(apiKey)
                        .setServerUrl(serverUrl)
                        .setBuildId(githubRunId);
    
                AxeWatcher watcher = new AxeWatcher(options).enableDebugLogger();
    
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = watcher.configure(new ChromeOptions().addArguments("--headless"));
               
                // This should throw a RuntimeException due to healdless mode
                driver = watcher.wrapDriver(new ChromeDriver(chromeOptions));
                driver.get("https://abcdcomputech.dequecloud.com");
                ((AxeWatcherDriver) driver).axeWatcher().flush();
    
               // If no exception, force fail
            assert false : "Expected error for unsupported headless mode, but no exception occurred.";

        } catch (RuntimeException e) {
            String expectedMessage = "@axe-core/watcher does not support fully headless mode. Please use `--headless=new` or a fully headed browser.";
            assert e.getMessage().contains(expectedMessage)
                : "Unexpected error. Expected message containing: \"" + expectedMessage + "\", but got: " + e.getMessage();

            System.out.println("Caught expected headless mode error: " + e.getMessage());
        } finally {
            if (driver != null) {
                driver.quit();
            }
           
        }  
    }

    /**
         * Negative test that simulates passing chromeoptions as incognito mode  to Axe Watcher.
         * Test passes if Axe Watcher fails with the expected error message.
         */
        @Test
        public void incognitoModeTest() {
            WebDriver driver = null;
    
            try {
                 // Load environment variables
        String githubRunId = EnvLoader.get("GITHUB_RUN_ID");
        if (githubRunId == null) {
            githubRunId = "RUN-" + (int) (Math.random() * 100000);
        }
                // Simulate incognito mode
                String apiKey = EnvLoader.get("API_KEY");
                String serverUrl = EnvLoader.get("SERVER_URL");
               
                AxeWatcherOptions options = new AxeWatcherOptions()
                        .setApiKey(apiKey)
                        .setServerUrl(serverUrl)
                        .setBuildId(githubRunId);
    
                AxeWatcher watcher = new AxeWatcher(options).enableDebugLogger();
    
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = watcher.configure(new ChromeOptions().addArguments("--incognito"));
               
                // This should throw a RuntimeException due to incognito mode argument
                driver = watcher.wrapDriver(new ChromeDriver(chromeOptions));
                driver.get("https://abcdcomputech.dequecloud.com");
                ((AxeWatcherDriver) driver).axeWatcher().flush();
    
               // If no exception, force fail
            assert false : "Expected error for unsupported incognito mode, but no exception occurred.";

        } catch (RuntimeException e) {
            String expectedMessage = "@axe-core/watcher does not support incognito mode. Please remove it and try again.";
            assert e.getMessage().contains(expectedMessage)
                : "Unexpected error. Expected message containing: \"" + expectedMessage + "\", but got: " + e.getMessage();

            System.out.println("Caught expected headless mode error: " + e.getMessage());
        } finally {
            if (driver != null) {
                driver.quit();
            }
           
        }  
    }
    /**
         * Negative test that simulates passing chromeoptions as incognito mode  to Axe Watcher.
         * Test passes if Axe Watcher fails with the expected error message.
         */
        @Test
        public void missingConfigureTest() {
            WebDriver driver = null;
    
            try {
                 // Load environment variables
        String githubRunId = EnvLoader.get("GITHUB_RUN_ID");
        if (githubRunId == null) {
            githubRunId = "RUN-" + (int) (Math.random() * 100000);
        }
                // Simulate incognito mode
                String apiKey = EnvLoader.get("API_KEY");
                String serverUrl = EnvLoader.get("SERVER_URL");
               
                AxeWatcherOptions options = new AxeWatcherOptions()
                        .setApiKey(apiKey)
                        .setServerUrl(serverUrl)
                        .setBuildId(githubRunId);
    
                AxeWatcher watcher = new AxeWatcher(options).enableDebugLogger();
    
                // Set up WebDriver without passing options through watcher
            WebDriverManager.chromedriver().setup();
            ChromeOptions chromeOptions = new ChromeOptions();
            // Skipping: chromeOptions = watcher.configure(chromeOptions); ← this line is intentionally missing

            // Now wrap the driver (expected to fail or misbehave)
            driver = watcher.wrapDriver(new ChromeDriver(chromeOptions));

            // If no exception occurs, force fail the test
            assert false : "Expected error due to missing watcher.configure(), but driver was wrapped without issue.";

        } catch (RuntimeException e) {
            assert e.getMessage().contains("axe") || e.getMessage().contains("Watcher")
                : "Unexpected error. Expected Axe Watcher related error, but got: " + e.getMessage();

            System.out.println("Caught expected watcher configuration error: " + e.getMessage());
        } finally {
            if (driver != null) {
                driver.quit();
            }
           
        }  
    }
}
