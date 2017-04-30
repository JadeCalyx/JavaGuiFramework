/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blogspot.jadecalyx.webtools;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


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
        loadIndexFromJson(_site);
    }
    
    public jcAddressSet GetAddressSet(String handle) {
        return _addresses.get(handle);
    }
    
    public String GetUrlMatch(String url) {
	Set keys = _addresses.keySet();
	for (String key: _addresses.keySet()) {
	    if (_addresses.get(key).Matches(url)) {
		return key;
	    }
	}
	return "dummy";
    }
    
    private void loadIndex(String site) throws Exception {
		int type = 0;
		int handle = 1;
		int segment = 2;
		int mask = 3;
	
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
				if (parts.length > 3) { //must have all parts to process, else skip
					if (parts[segment].equals("empty-string")) {parts[segment] = "";}
					_addresses.put(parts[handle], new jcAddressSet(parts[segment], parts[mask]));
				}
			}
		}
                
    }
	
	private void loadIndexFromJson(String site) throws Exception {
	
        String s = System.getProperty("file.separator");
        String runPath = System.getProperty("user.dir");
		String fullPath = String.join(s, runPath, "SiteInfo", site, "AddressInfo", "addresses.json");
		File f = new File(fullPath);
		if (!f.isFile()) {
			throw new Exception(String.format("loadIndex unable to find file for site: %s", site));
		}
		
		//load json file
		JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(fullPath));
        JSONObject jsonObject =  (JSONObject) obj;
		JSONArray addressList = (JSONArray) jsonObject.get("address-list");

		for (int i=0; i < addressList.size(); i++ ) {
			JSONObject address = (JSONObject) addressList.get(i);
			_addresses.put(address.get("handle").toString(), new jcAddressSet(address.get("segment").toString(), address.get("mask").toString()));
		}

			
			
			
            //Iterator<String> iterator = cars.iterator();
            //while (iterator.hasNext()) {
            // System.out.println(iterator.next());
           // }
    }
		
		
}
    
    
    
    


