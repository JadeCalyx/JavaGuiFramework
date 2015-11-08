/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blogspot.jadecalyx.webtools;

/**
 *
 * @author John
 */
public class jcAddressHelper {
    
    String _siteName;
    String _prefix;
    jcAddressIndex _addressIndex;
    
    public jcAddressHelper(String siteName, String prefix) throws Exception {
        _prefix = prefix;
        _addressIndex = new jcAddressIndex(siteName);
    }
    
    public String GetAddress(String handle) {
        jcAddressSet currSet = _addressIndex.GetAddressSet(handle);
        return _prefix + "/" + currSet.GetSegment();
    }
    
    public String GetHandleForUrl(String fullUrl) {
	String handle = _addressIndex.GetUrlMatch(fullUrl);
	return handle;
    }
    
    
}
