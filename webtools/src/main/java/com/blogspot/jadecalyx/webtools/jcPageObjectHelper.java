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
public class jcPageObjectHelper {
    
    String _site;
    String _handle;
    Map<String, List<jcPageObjectSet>> _objectIndex; 
    
    public jcPageObjectHelper(String site, String pageHandle) throws Exception {
		_site = site;
		_handle = pageHandle;
		_objectIndex = new HashMap<>();
		loadIndex(_site, _handle);
	
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
    
    private void loadIndex(String site, String pageHandle) throws Exception {
		int type = 0;
		int handle = 1;
		int lookupType = 2;
		int lookupDetails = 3;
	
        String s = System.getProperty("file.separator");
        String runPath = System.getProperty("user.dir");
		String fileToRead = pageHandle + ".tsv";
		String fullPath = String.join(s, runPath, "SiteInfo", site, "PageInfo", fileToRead);
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
			//load the associated file into index
			if (parts[type].equals("++")) {
				loadIndex(site, parts[handle]);
				continue;
			}
			if (parts[type].equalsIgnoreCase("+")) {
				if (((parts.length - 2) % 2) == 0) {
					List<jcPageObjectSet> currList = new ArrayList<jcPageObjectSet>();
					for (int j = (parts.length - 1); j > handle; j -= 2) {
						currList.add(new jcPageObjectSet(parts[j-1], parts[j]));
					}
					_objectIndex.put(parts[handle], currList);
				}
			}
		}
	}
 
    
}
