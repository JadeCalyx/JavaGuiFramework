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
public class jcPageObjectSet {
    
    String _lookupType;
    String _lookupDetails;
    
    public jcPageObjectSet(String lookupType, String lookupDetails) {
	_lookupType = lookupType;
	_lookupDetails = lookupDetails;
    }
    
    public String GetType() {
	return _lookupType;
    }
    
    public String GetDetails() {
	return _lookupDetails;
    }
    
    
}
