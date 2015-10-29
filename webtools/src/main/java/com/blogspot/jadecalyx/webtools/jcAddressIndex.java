/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blogspot.jadecalyx.webtools;

import java.io.File;
import java.nio.file.Files;
import java.util.*;

/**
 *
 * @author johnchambers
 */
public class jcAddressIndex {
    
    String _site;
    Map<String, jcAddressSet> _addresses;
    
    public jcAddressIndex(String site) throws Exception {
        _site = site;
	_addresses = new HashMap<String, jcAddressSet>();
        loadIndex(_site);
    }
    
    public jcAddressSet GetAddressSet(String handle) {
        return _addresses.get(handle);
    }
    
    private void loadIndex(String site) throws Exception {
	int type = 0;
	int comment = 1;
	int handle = 2;
	int segment = 3;
	int mask = 4;
	
        String s = System.getProperty("file.separator");
        String runPath = System.getProperty("user.dir");
	String fullPath = String.join(s, runPath, "SiteInfo", site, "AddressInfo", "addresses.tsv");
	File f = new File(fullPath);
	if (!f.isFile()) {
	    throw new Exception(String.format("loadIndex unable to find file for site: %s", site));
	}
	
        List<String> lines = Files.readAllLines(f.toPath());
	for(int i = 0; i < lines.size(); i++) {
	    String[] parts = lines.get(i).split("\t");
	    if (parts.length < 1) {
		continue;
	    }
	    if (parts[type].equalsIgnoreCase("-")) {
		continue;
	    }
	    if (parts[type].equalsIgnoreCase("+")) {
		if (parts.length > 4) { //must have all parts to process, else skip
		    if (parts[segment].equals("empty-string")) {parts[segment] = "";}
		    _addresses.put(parts[handle], new jcAddressSet(parts[segment], parts[mask]));
		}
	    }
	}
                
    }
    
    
    
    
}
