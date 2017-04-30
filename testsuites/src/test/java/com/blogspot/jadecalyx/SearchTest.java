/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blogspot.jadecalyx;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.blogspot.jadecalyx.webtools.jcBrowserFactory;
import com.blogspot.jadecalyx.webtools.jcBrowser;
import java.io.File;
import java.util.Properties;
import com.blogspot.jadecalyx.webtools.jcWebPage;



/**
 *
 * @author John Chambers
 */
public class SearchTest {
    
    private jcBrowser _browser;
    private jcBrowserFactory _browserFactory;
	private PropertyReader _props;

    public SearchTest() throws Exception {
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
    
        // <summary>
    // Validates that the main page seach performs as expected.
    // Stories: Wiki-101, Wiki-153
    // Bugs: Wiki-937
    // </summary>
    @Test
    public void PerformValidMainPageSearch() throws Exception {
        System.out.println("start");
        _browser = _browserFactory.GetNewBrowser("chrome");
        _browser.Maximize();
        _browser.GotoPage("main-page");
        jcWebPage currPage = _browser.GetPage();
        currPage.SetText("search-box", "archery");
        currPage.Click("search-button");
        boolean pageChanged = _browser.WaitForPageChange();
        Assert.assertTrue(pageChanged, "The page did not change");
        jcWebPage newPage = _browser.GetPage();
        Assert.assertTrue(newPage.GetHandle().equals("archery-page"),"Wrong page name");
        System.out.println("done");
    }

}
