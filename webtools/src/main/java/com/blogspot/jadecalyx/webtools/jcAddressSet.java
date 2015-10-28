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
public class jcAddressSet {
    
    private String _mask;
    private String _segment;
    
    public jcAddressSet(String segment, String mask) {
        _segment = segment;
        _mask = mask;
    }
    
    public String GetSegment() {
        return _segment;
    }
    
    public Boolean Matches(String url) {
        return url.matches(_mask);
    }
    
    
}
