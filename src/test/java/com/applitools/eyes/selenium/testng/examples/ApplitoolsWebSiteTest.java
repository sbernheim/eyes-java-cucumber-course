package com.applitools.eyes.selenium.testng.examples;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.SessionUrls;
import com.applitools.eyes.StepInfo;
import com.applitools.eyes.TestResultContainer;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.TestResultsStatus;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.StepInfo.AppUrls;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.SeleniumCheckSettings;
import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.eyes.selenium.introspection.Introspect;
import com.applitools.eyes.visualgrid.model.EmulationBaseInfo;
import com.applitools.eyes.visualgrid.model.IosDeviceInfo;
import com.applitools.eyes.visualgrid.model.RenderBrowserInfo;

public class ApplitoolsWebSiteTest {
    private static final Logger log = LoggerFactory.getLogger(Introspect.thisClass());
    
    //private static final String pageURLDiffs = "https://demo.applitools.com/index_v2.html";
    private static final String pageURL = "https://www.fanniemae.com";
    
    private static final String defaultSearchTerm = "mortgage rates";
    
    public static void runSingleTest(WebDriver driver, Eyes eyes, boolean forceDiffs) {
        runTest(driver, eyes, forceDiffs, defaultSearchTerm);
    }

    public static void runTest(WebDriver driver, Eyes eyes, boolean forceDiffs, String searchTerm) {
        // Load the login page.
        driver.get(pageURL);
        
        // Get the text of the footer.
        String footerText = driver.findElement(By.cssSelector("div#footer-new")).getText();
        log.trace("Found Footer: {}", footerText);
        
        // Accept cookies
        String cookieButtonSelector = "button#onetrust-accept-btn-handler";
        WebDriverWait acceptCookieWait = new WebDriverWait(driver, 30);
        try {
            acceptCookieWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(cookieButtonSelector)));
            driver.findElement(By.cssSelector(cookieButtonSelector)).click();
        } catch (TimeoutException e) {
            log.info("Did not find the Accept Cookies button for this test run: CSS Selector '{}'", cookieButtonSelector);
        }
            
        // Verify the Main page loaded correctly.
        if (eyes != null) eyes.check(Target.window().fully().withName("Main page"));

        // Click on the search button
        // Enter search term
        // Perform search
        driver.findElement(By.cssSelector("button.fm-toggle-search")).click();;
        WebElement s = driver.findElement(By.cssSelector("#searchbox > div > div > div.magic-box-input > input"));
        s.sendKeys(searchTerm);
        s.sendKeys(Keys.ENTER);

        // Verify the Search Results page loaded correctly.
        if (eyes != null) eyes.check(Target.window().fully().withName("Search results"));
    }
    
    public static void logTestResults(TestResultsSummary allTestResults) {
        // Future home of TestResultSummary parsing logic.
        log.info("RESULTS: {}", allTestResults);
    }

}
