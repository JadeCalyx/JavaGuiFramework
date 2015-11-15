/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blogspot.jadecalyx.webtools;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author johnchambers
 */
public class jcBrowser {
    
    WebDriver _driver;
    String _site;
    jcAddressHelper _addressHelper;
    jcPageFactory _pageFactory;
    jcWebPage _currPage;
    
    public jcBrowser(String browserType, String site) throws Exception {
        _site = site;
	_addressHelper = new jcAddressHelper(_site, "https://en.wikipedia.org");
	initDriver(browserType);
	_pageFactory = new jcPageFactory(_site, _driver);
    }
    
    public String GetHello() {
        return System.getProperty("user.dir");//"hello";
    }
    
    public void Close() {
        _driver.quit();
    }
    
    public void GotoPage(String siteHandle) {
        String address = _addressHelper.GetAddress(siteHandle);
        _driver.navigate().to(address);
    }
    
    public jcWebPage GetPage() throws Exception {
		String u = _driver.getCurrentUrl();
		String handle = _addressHelper.GetHandleForUrl(u);
		_currPage = _pageFactory.GetPage(handle, _currPage);
		return _currPage;
    }
    
    
    
    private void initDriver(String browserType) {
        switch(browserType.toLowerCase()) {
            case "firefox" : _driver = new FirefoxDriver();
                break;
            default: System.out.println("Unable to find browser");
                break;
        }
    }
}
