/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blogspot.jadecalyx;
import org.junit.Assume;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import com.blogspot.jadecalyx.webtools.jcBrowserFactory;
import com.blogspot.jadecalyx.webtools.jcBrowser;
import java.io.File;
import java.util.Properties;
import com.blogspot.jadecalyx.webtools.jcWebPage;

/**
 *
 * @author johnchambers
 */
public class JUSearchTests {

    private jcBrowser _browser;
    private jcBrowserFactory _browserFactory;
   
    public JUSearchTests() {
        _browserFactory = new jcBrowserFactory("Wikipedia");
    }
    
    @BeforeClass
    public static void setUpClass() {
        //_browserFactory = new jcBrowserFactory("wikipedia");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
        _browser.Close();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // <summary>
    // Validates that the main page seach performs as expected.
    // Stories: Wiki-101, Wiki-153
    // Bugs: Wiki-937
    // </summary>
    @Test
    @Description("this is the description.")
    public void PerformValidMainPageSearch() throws Exception {
        System.out.println("start");
        _browser = _browserFactory.GetNewBrowser("chrome");
        _browser.Maximize();
        _browser.GotoPage("main-page");
        jcWebPage currPage = _browser.GetPage();
        currPage.SetText("search-box", "archery");
        currPage.Click("search-button");
        boolean pageChanged = _browser.WaitForPageChange();
        assertTrue("The page did not change", pageChanged);
        jcWebPage newPage = _browser.GetPage();
        assertTrue("message", newPage.GetHandle().equals("archery-page"));
        System.out.println("done");
    }



    
} //end of search tests class
