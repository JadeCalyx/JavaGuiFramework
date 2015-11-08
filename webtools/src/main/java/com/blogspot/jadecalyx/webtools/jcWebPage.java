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
public class jcWebPage {
    
    String _handle;
    String _site;
    
    public jcWebPage(String handle, String site) {
	_handle = handle;
	_site = site;
    }
    
    public String GetHandle() {
	return _handle;
    }
    
}
