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
public class jcBrowserFactory {
    
    String _site;
    String _prefix;
    
    public jcBrowserFactory(String site, String prefix) {
        _site = site;
        _prefix = prefix;
    }
    
    public String GetHello() {
        return "hello";
    }
    
    public jcBrowser GetNewBrowser(String browserType) throws Exception {
        return new jcBrowser(browserType, _site, _prefix);
    }
    
    
    
}
