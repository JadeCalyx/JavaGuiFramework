/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blogspot.jadecalyx.webtools;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
    
    public jcBrowser(String browserType, String site, String prefix) throws Exception {
        _site = site;
	_addressHelper = new jcAddressHelper(_site, prefix);
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
    
    public jcBrowser Maximize() {
        //_driver.manage().window().
        return this;
    }

    public boolean WaitForPageChange() throws Exception {
            String currHandle = "";
            if (_currPage != null)
            {
                currHandle = _currPage.GetHandle();
            }
            int timeout = 30;
            this.GetPage();
            while ((_currPage.GetHandle().equals(currHandle)) && (timeout-- > 0))
            {
                Thread.sleep(1000);
                this.GetPage();
            }
            if (timeout > 0)
            {
                return true;
            }
            else
            {
                return false;
            }
    }
    
    private void initDriver(String browserType) {
        switch(browserType.toLowerCase()) {
            case "chrome" : ChromeDriverManager.getInstance().setup();
                _driver = new ChromeDriver();
                break;
            case "firefox" : _driver = new FirefoxDriver();
                break;
            default: System.out.println("Unable to find browser");
                break;
        }
    }
}
