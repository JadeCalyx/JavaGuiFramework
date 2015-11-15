/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blogspot.jadecalyx.webtools;

import org.openqa.selenium.*;
/**
 *
 * @author johnchambers
 */
public class jcPageFactory {
    
    String _site;
	WebDriver _driver;
	
    
    public jcPageFactory(String site, WebDriver driver) {
		_site = site;
		_driver = driver;
    }
    
    public jcWebPage GetPage(String handle, jcWebPage currPage) throws Exception {
		if (currPage != null) {
			if (currPage.GetHandle().equals(handle)) {
				return currPage;
			}
		}
	
		jcWebPage np = new jcWebPage(_driver, handle, _site);
	
		return np;
    }
    
    
 }
