package com.blogspot.jadecalyx;

import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.blogspot.jadecalyx.webtools.jcBrowserFactory;
import com.blogspot.jadecalyx.webtools.jcBrowser;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import com.blogspot.jadecalyx.webtools.jcWebPage;
import org.testng.Assert;
import org.openqa.selenium.*;
import java.util.*;
import org.joda.time.DateTime;

public class HistoryTest {
    
        private PropertyReader _props;
        private jcBrowser _browser;
        private jcBrowserFactory _browserFactory;
    
    public HistoryTest() throws Exception{
        _props = new PropertyReader("prod");
        _browserFactory = new jcBrowserFactory("Wikipedia", _props.GetProperty("WebPrefix"));
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
        _browser.Close();
    }
    
            /// <summary>
        /// Validates that the main page seach performs as expected.
        /// Stories: Wiki-101, Wiki-153
        /// Bugs: Wiki-937
        /// </summary>
    //@Test
    //[Category("GUI")]
    //[Category("History")]
    //[Description("This test determines if the history link takes you to the correct page.")]
    //[TestCase("firefox")]
    //[TestCase("chrome")]
    @Test
    public void OpenPageHistory() throws Exception
    {
        _browser = _browserFactory.GetNewBrowser("chrome");
        _browser.Maximize();
        _browser.GotoPage("main-page");
        jcWebPage currPage = _browser.GetPage();
        currPage.Click("view-history-anchor");
        _browser.WaitForPageChange();
        jcWebPage newPage = _browser.GetPage();
        Assert.assertTrue(newPage.GetHandle().equals("view-history-page"),
            "Landed on wrong page: " );
    }
/*
        [Test]
        [Category("GUI")]
        [Category("History")]
        [TestCase("chrome", "main-page", "limit-to-20-anchor", 20)]
        [TestCase("chrome", "archery-page", "limit-to-50-anchor", 50)]
        [TestCase("chrome", "main-page", "limit-to-100-anchor", 100)]
        [TestCase("chrome", "archery-page", "limit-to-250-anchor", 250)]
        [TestCase("chrome", "main-page", "limit-to-500-anchor", 500)]
        [TestCase("firefox", "archery-page", "limit-to-20-anchor", 20)]
        [TestCase("firefox", "main-page", "limit-to-50-anchor", 50)]
        [TestCase("firefox", "archery-page", "limit-to-100-anchor", 100)]
        [TestCase("firefox", "main-page", "limit-to-250-anchor", 250)]
        [TestCase("firefox", "archery-page", "limit-to-500-anchor", 500)]
        public void FilterHistoryPageListEntries(string browser, 
            string subjectPage, string anchorToClick, int expectedCount)
        {
    */
    @Test    
    public void FilterHistoryPageListEntries() throws Exception {
        String browser = "chrome";
        String subjectPage = "archery-page";
        String anchorToClick = "limit-to-500-anchor";
        int expectedCount = 500;
//open browser
        _browser = _browserFactory.GetNewBrowser(browser);
        _browser.Maximize();
        //go to expected page
        _browser.GotoPage(subjectPage);
        //click history tab
        _browser.GetPage().Click("view-history-anchor");
        //click a limit amount
        jcWebPage currPage = _browser.GetPage();
        currPage.Click(anchorToClick);
        //count limit amount displayed rows
        List<WebElement> listItems = currPage.GetWebList("page-history-list");
        int count = listItems.size();
        Assert.assertTrue(count == expectedCount, "Wrong number of items. Expected {0}, Found {1}");
    }
/*
        [Test]
        [Category("GUI")]
        [Category("History")]
*/
    @Test
    public void HistoryOrderOldest()throws Exception
        {
        _browser = _browserFactory.GetNewBrowser("chrome");
        _browser.Maximize();
        _browser.GotoPage("main-page");
        _browser.GetPage().Click("view-history-anchor");
        jcWebPage currPage = _browser.GetPage();
        currPage.Click("oldest-anchor");
        List<WebElement> listItems = currPage.GetWebList("page-history-list");
        List<DateTime> dateList;
        dateList = new ArrayList<DateTime>();
        for (WebElement el : listItems)
        {
            WebElement d = el.findElement(By.cssSelector("a[class=\"mw-changeslist-date\"]"));
            String theDate = d.getText();
            
            String[] parts = theDate.split(",");
            String[] hm = parts[0].trim().split(":");
            String[] dmy = parts[1].trim().split(" ");
            int m;
            switch (dmy[1].toLowerCase())
            {
                case "january": m = 1; break;
                case "february": m = 2; break;
                case "march": m = 3; break;
                case "april": m = 4; break;
                case "may": m = 5; break;
                case "june": m = 6; break;
                case "july": m = 7; break;
                case "august": m = 8; break;
                case "september": m = 9; break;
                case "october": m = 10; break;
                case "november": m = 11; break;
                case "december": m = 11; break;
                default: m = 0; break;
            }
            //     "00:06, 4 December 2015"‎
            DateTime d1 =  new DateTime(
                    Integer.parseInt(dmy[2]), m,
                    Integer.parseInt(dmy[0]),
                Integer.parseInt(hm[0]), Integer.parseInt(hm[1]), 0);
            dateList.add(d1);
        }
            
        String error = "";
        for(int i = 1; i < dateList.size(); i++)
        {
            
           boolean notBefore = dateList.get(i - 1).isAfter(dateList.get(i)); 
            if (notBefore)
                error += "Dates out of order. upper date: {0}, lower date {1}";
        }
        Assert.assertTrue(error.length() == 0, error);
    }
/*
        [Test]
        [Category("GUI")]
        [Category("History")]
*/
        @Test
        public void HistoryOrderNewest()throws Exception
        {
            _browser = _browserFactory.GetNewBrowser("chrome");
            _browser.Maximize();
            _browser.GotoPage("main-page");
            _browser.GetPage().Click("view-history-anchor");
            jcWebPage currPage = _browser.GetPage();
            List<WebElement> listItems = currPage.GetWebList("page-history-list");
            List<DateTime> dateList = new ArrayList<DateTime>();
            for (WebElement el : listItems)
            {
                WebElement d = el.findElement(By.cssSelector("a[class=\"mw-changeslist-date\"]"));
                String theDate = d.getText();
                String[] parts = theDate.split(",");
                String[] hm = parts[0].trim().split(":");
                String[] dmy = parts[1].trim().split(" ");
                int m;
                switch (dmy[1].toLowerCase())
                {
                    case "january": m = 1; break;
                    case "february": m = 2; break;
                    case "march": m = 3; break;
                    case "april": m = 4; break;
                    case "may": m = 5; break;
                    case "june": m = 6; break;
                    case "july": m = 7; break;
                    case "august": m = 8; break;
                    case "september": m = 9; break;
                    case "october": m = 10; break;
                    case "november": m = 11; break;
                    case "december": m = 11; break;
                    default: m = 0; break;

                }
                DateTime d1 =  new DateTime(
                    Integer.parseInt(dmy[2]), m,
                    Integer.parseInt(dmy[0]),
                    Integer.parseInt(hm[0]), Integer.parseInt(hm[1]), 0);
                dateList.add(d1);
            }
            
            String error = "";
            for(int i = 1; i < dateList.size(); i++)
            {
               boolean notBefore = dateList.get(i).isAfter(dateList.get(i - 1)); 
                if (notBefore)
                    error += "Dates out of order. upper date: {0}, lower date {1}";
            }        
            Assert.assertTrue(error.length() == 0, error);
        }

    
    
}
