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
public class jcPageObjectHelper {
    
    String _site;
    String _handle;
    Map<String, List<jcPageObjectSet>> _objectIndex; 
    
    public jcPageObjectHelper(String site, String pageHandle) throws Exception {
		_site = site;
		_handle = pageHandle;
		_objectIndex = new HashMap<>();
		loadIndexFromJson(_site, _handle);
	
    }
	
	public Stack<jcPageObjectSet> GetLookupDetails(String handle) {
		return expandLookupDetails(null, _objectIndex.get(handle));
	}
	
	private Stack<jcPageObjectSet> expandLookupDetails(List<jcPageObjectSet> returnSet,
			List<jcPageObjectSet> incomingSet) {
		if (returnSet == null) {returnSet = new Stack<>();}
		while (incomingSet.size() > 0) {
			jcPageObjectSet currSet = incomingSet.get(incomingSet.size() - 1);
			incomingSet.remove(incomingSet.size() - 1);
			if (currSet.GetType().equals("css")) {
				returnSet.add(currSet);
			}
			else {
				if (currSet.GetType().equals("handle")) {
					List<jcPageObjectSet> expandedSet = GetLookupDetails(currSet.GetDetails());
					while (expandedSet.size() > 0) {
						returnSet.add(expandedSet.get(expandedSet.size() - 1));
						expandedSet.remove(expandedSet.size() - 1);
					}
				}
			}
		}
		Stack<jcPageObjectSet> orderedReturnSet = new Stack<>();
		while (returnSet.size() > 0) {
			orderedReturnSet.push(returnSet.get(returnSet.size() - 1));
			returnSet.remove(returnSet.size() - 1);
		}
		//return orderedReturnSet;
		return orderedReturnSet;
	}
    
	private void loadIndexFromJson(String site, String pageHandle) throws Exception {

	
        String s = System.getProperty("file.separator");
        String runPath = System.getProperty("user.dir");
		String fileToRead = pageHandle + ".json";
		String fullPath = String.join(s, runPath, "SiteInfo", site, "PageInfo", fileToRead);
		File f = new File(fullPath);
		if (!f.isFile()) {
			throw new Exception(String.format("loadIndex unable to find file for site: %s and file %s", site, fullPath));
		}
		
		//load json file
		JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(fullPath));
        JSONObject jsonObject =  (JSONObject) obj;
		JSONArray objectList = (JSONArray) jsonObject.get("object-list");

		try {
			if (objectList.size() > 0) {
				for (int i = 0; i < objectList.size(); i++) {
					JSONObject theObject = (JSONObject) objectList.get(i);
					JSONArray lookups = (JSONArray) theObject.get("lookup");
					List<jcPageObjectSet> currList = new ArrayList<jcPageObjectSet>();
					for (int j = (lookups.size() - 1); j > -1; j--) {
							JSONObject currLookup = (JSONObject) lookups.get(j);
							currList.add(new jcPageObjectSet(currLookup.get("type").toString(), currLookup.get("detail").toString()));
					}
					_objectIndex.put(theObject.get("handle").toString(), currList);
				}
			}
		}
		catch(Exception e) {
			int x = 1;
		}
	
		//load include list
		JSONArray includeList = (JSONArray) jsonObject.get("include-list");
		if (includeList.size() > 0) {
			for (int i=0; i<includeList.size(); i++) {
				loadIndexFromJson(site, includeList.get(i).toString());
			}
		}
		
		
	}

    
}
