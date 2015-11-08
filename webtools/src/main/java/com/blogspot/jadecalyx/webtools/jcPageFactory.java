/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blogspot.jadecalyx.webtools;

/**
 *
 * @author johnchambers
 */
public class jcPageFactory {
    
    String _site;
    
    public jcPageFactory(String site) {
	_site = site;
    }
    
    public jcWebPage GetPage(String handle, jcWebPage currPage) {
	if (currPage != null) {
	    if (currPage.GetHandle().equals(handle)) {
			return currPage;
	    }
	}
	
	jcWebPage np = new jcWebPage(handle, _site);
	
	return np;
    }
    
    
 }
