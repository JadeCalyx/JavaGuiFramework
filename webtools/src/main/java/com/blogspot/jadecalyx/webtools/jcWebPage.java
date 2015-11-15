/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blogspot.jadecalyx.webtools;

import java.util.*;
import org.openqa.selenium.*;

/**
 *
 * @author johnchambers
 */
public class jcWebPage {
    
    String _handle;
    String _site;
	jcPageObjectHelper _pageObjectHelper;
	WebDriver _driver;
    
    public jcWebPage(WebDriver driver, String handle, String site) throws Exception {
		_handle = handle;
		_site = site;
		_pageObjectHelper = new jcPageObjectHelper(_site, _handle);
		_driver = driver;
    }
    
    public String GetHandle() {
		return _handle;
    }
	
	public void Click(String objectHandle) {
		Stack<jcPageObjectSet> es = _pageObjectHelper.GetLookupDetails(objectHandle);
		WebElement we = extractElement(null, es);
		we.click();
	}
	
	public void SetText(String objectHandle, String textToSet) {
		Stack<jcPageObjectSet> es = _pageObjectHelper.GetLookupDetails(objectHandle);
		WebElement we = extractElement(null, es);
		we.sendKeys(textToSet);
	}
	
	private WebElement extractElement(WebElement currElement, Stack<jcPageObjectSet> cssStack) {
		if (cssStack.empty()) {
			return currElement;
		}
		jcPageObjectSet currSet = cssStack.pop();
		List<WebElement> wel;
		if (currElement == null) {
			wel = _driver.findElements(By.cssSelector(currSet.GetDetails()));
		}
		else {
			wel = currElement.findElements(By.cssSelector(currSet.GetDetails()));
		}
			if (!wel.isEmpty()) {
				return extractElement(wel.get(0), cssStack);
			}
			else {
				return null;
			}
	}
	
	
    
}
