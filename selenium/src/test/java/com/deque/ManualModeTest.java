package com.deque;

import org.openqa.selenium.By;
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

import io.github.bonigarcia.wdm.WebDriverManager;

public class ManualModeTest {

    private static WebDriver driver;

    /**
     * Sets up the WebDriver and Axe Watcher before all tests.
     * Configures Axe Watcher with API key and server URL.
     */
    @BeforeClass
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
                .setAutoAnalyze(false)
                .setBuildId(githubRunId);
        AxeWatcher watcher = new AxeWatcher(options).enableDebugLogger();
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions = watcher.configure(chromeOptions);
        driver = watcher.wrapDriver(new ChromeDriver(chromeOptions));
    }

    /**
     * Cleans up after all tests by quitting the WebDriver.
     */
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            ((AxeWatcherDriver) driver).axeWatcher().flush();
            driver.quit(); // Close the browser
        }
    }

    /**
     * Test method to demonstrate no analyze call.
     * Expected Results:
     * - Branches and Commits page: Zero findings.
     * - Issue page: Zero issues Zero Page state
     */
    @Test
    public void testWithNoAnalyseCall() {
        driver.get("https://abcdcomputech.dequecloud.com");  
    }

    /**
     * Test method to demonstrate a single analyze call.
     * Expected Results:
     * - Branches and Commits page: Displays a new branch card, A11y threshold of 29, 1 page state, and the latest Axe Core/Watcher versions.
     * - Issue page: Identifies failure rules such as  color-contrast, image-alt, label, link-in-text-block, link-name.
     *  Page state: 1 https://abcdcomputech.dequecloud.com
     */
    @Test
    public void testWithAnalyseCall() {
        driver.get("https://abcdcomputech.dequecloud.com");
        ((AxeWatcherDriver) driver).axeWatcher().analyze();   
    }

    /**
     * Test method to demonstrate multiple analyze calls. x number of times analyse() call, x number of page states can be found
     * Expected Results:
     * - Branches and Commits page: Displays a new branch card, A11y threshold of 29, 4 page state, and the latest Axe Core/Watcher versions.
     * - Issue page: Identifies failure rules such as  color-contrast, image-alt, label, link-in-text-block, link-name.
     *  Page state: 1-4 https://abcdcomputech.dequecloud.com
     */
    @Test
    public void testWithAnalyseCallMultiTimes() {
        driver.get("https://abcdcomputech.dequecloud.com");
        ((AxeWatcherDriver) driver).axeWatcher().analyze();
        ((AxeWatcherDriver) driver).axeWatcher().analyze();
        ((AxeWatcherDriver) driver).axeWatcher().analyze();
        ((AxeWatcherDriver) driver).axeWatcher().analyze();  
    }

    /**
     * Test method to demonstrate chaining analyze calls.
     * Expected Results:
     * - Branches and Commits page: Displays a new branch card, A11y threshold of 29, 4 page state, and the latest Axe Core/Watcher versions.
     * - Issue page: Identifies failure rules such as  color-contrast, image-alt, label, link-in-text-block, link-name.
     *  Page state: 1-4 https://abcdcomputech.dequecloud.com
     */
    @Test
    public void testWithChainingAnalyseCall() {
        driver.get("https://abcdcomputech.dequecloud.com");
        ((AxeWatcherDriver) driver).axeWatcher().analyze().analyze().analyze().analyze();  
    }

    /**
     * Test method to demonstrate start and stop calls.
     * Start before test page loads should start the watcher and stop after the page loads.
     *  So here between start and stop one page loaded one page state can be found
     * Expected Results:
     * - Branches and Commits page: Displays a new branch card, A11y threshold of 29, 1 page state, and the latest Axe Core/Watcher versions.
     * - Issue page: Identifies failure rules such as  color-contrast, image-alt, label, link-in-text-block, link-name.
     *  Page state: 1 https://abcdcomputech.dequecloud.com
     */
    @Test
    public void testWithStartStop() {
        ((AxeWatcherDriver) driver).axeWatcher().start();
        driver.get("https://abcdcomputech.dequecloud.com/");
        ((AxeWatcherDriver) driver).axeWatcher().stop();
        driver.findElement(By.cssSelector("#topnav > ul > li:nth-child(5) > a")).click(); 
    }

    /**
     * Test method to demonstrate multiple start and stop calls.
     * Start before test page loads should start the watcher and stop after the page loads.
     * After fist stop there is no start cal before loading the next page so it cannt be scanned with watcher
     *  So here between start and stop one page loaded one page state can be found
     * Expected Results:
     * - Branches and Commits page: Displays a new branch card, A11y threshold of 29, 1 page state, and the latest Axe Core/Watcher versions.
     * - Issue page: Identifies failure rules such as  color-contrast, image-alt, label, link-in-text-block, link-name.
     *  Page state: 1 https://abcdcomputech.dequecloud.com
     */
    @Test
    public void testWithStartStopMultiTimes() {
        ((AxeWatcherDriver) driver).axeWatcher().start();
        driver.get("https://abcdcomputech.dequecloud.com/");
        ((AxeWatcherDriver) driver).axeWatcher().stop();
        driver.findElement(By.cssSelector("#topnav > ul > li:nth-child(5) > a")).click();
        ((AxeWatcherDriver) driver).axeWatcher().start();
        ((AxeWatcherDriver) driver).axeWatcher().stop();
        ((AxeWatcherDriver) driver).axeWatcher().start();
        ((AxeWatcherDriver) driver).axeWatcher().stop();
    }
    /**
     * Test method to demonstrate Analyse cal in between multiple start and stop calls.
     * Start before test page loads should start the watcher and stop after the page loads.
     * After fist stop there is no start cal before loading the next page so it cannt be scanned with watcher
     *  So here between start and stop one page loaded one page state can be found and with in between analyyse call will find one more page state
     * Expected Results:
     * - Branches and Commits page: Displays a new branch card, A11y threshold of 109, 5 page states, and the latest Axe Core/Watcher versions.
     * - Issue page: Identifies failure rules such as  color-contrast, image-alt, label, link-in-text-block, link-name.
     *  Page state: 5 - 1 https://abcdcomputech.dequecloud.com, 2-3 child(4) page https://abcdcomputech.dequecloud.com/cart.php, 4-5 child(3) page https://abcdcomputech.dequecloud.com/desktops.php
     */
    @Test
    public void testAnalyseBetweenStartNStop() {
        ((AxeWatcherDriver) driver).axeWatcher().start();
        driver.get("https://abcdcomputech.dequecloud.com/"); //1 page state
        ((AxeWatcherDriver) driver).axeWatcher().stop(); //here stops and no start before page is rendered, so this page state not scanned
        driver.findElement(By.cssSelector("#topnav > ul > li:nth-child(5) > a")).click();
        ((AxeWatcherDriver) driver).axeWatcher().start(); //2 page state with child(4) page
        driver.findElement(By.cssSelector("#topnav > ul > li:nth-child(4) > a")).click();
        ((AxeWatcherDriver) driver).axeWatcher().analyze(); //3 page state with child(4) page again with analyse() call
      
        ((AxeWatcherDriver) driver).axeWatcher().stop(); //here stops and no start before page is rendered, so this page state not scanned
        driver.findElement(By.cssSelector("#topnav > ul > li:nth-child(3) > a")).click();
        ((AxeWatcherDriver) driver).axeWatcher().start(); // start but no page rendered here so no page state here 
        ((AxeWatcherDriver) driver).axeWatcher().analyze(); // 4 page state onchild(3) page because it opened and analyse called
        ((AxeWatcherDriver) driver).axeWatcher().stop();//here stops and no start before page is rendered, so this page state not scanned
        ((AxeWatcherDriver) driver).axeWatcher().start(); // start but no page rendered here so no page state here 
        ((AxeWatcherDriver) driver).axeWatcher().analyze();// 5 page state onchild(3) page because it opened and analyse called
        ((AxeWatcherDriver) driver).axeWatcher().stop();//here stops and no start before page is rendered, so this page state not scanned
        ((AxeWatcherDriver) driver).axeWatcher().start();// start but no page rendered here so no page state here 
        ((AxeWatcherDriver) driver).axeWatcher().stop(); //here stops and no start before page is rendered, so this page state not scanned
    }
}