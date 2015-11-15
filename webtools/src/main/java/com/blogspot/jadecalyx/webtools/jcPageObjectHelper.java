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
    Map<String, Stack<jcPageObjectSet>> _objectIndex; 
    
    public jcPageObjectHelper(String site, String pageHandle) throws Exception {
		_site = site;
		_handle = pageHandle;
		_objectIndex = new HashMap<>();
		loadIndex(_site, _handle);
	
    }
	
	public Stack<jcPageObjectSet> GetLookupDetails(String handle) {
		return _objectIndex.get(handle);
	}
    
    private void loadIndex(String site, String pageHandle) throws Exception {
		int type = 0;
		int comment = 1;
		int handle = 2;
		int lookupType = 3;
		int lookupDetails = 4;
	
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
			if (parts[type].equalsIgnoreCase("+")) {
				//parts.length - 3 mod 2 = 0
				if (((parts.length - 3) % 2) == 0) {
					Stack<jcPageObjectSet> currStack = new Stack<jcPageObjectSet>();
					for (int j = (parts.length - 1); j > handle; j -= 2) {
						currStack.push(new jcPageObjectSet(parts[j-1], parts[j]));
					}
					_objectIndex.put(parts[handle], currStack);
				}
			}
		}
	}
 
    
}
