package com.blogspot.jadecalyx;




import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author John Chambers
 */
public class PropertyReader {
   
    private Properties _props;
    
    public PropertyReader(String filePrefix)throws Exception {
        String s = System.getProperty("file.separator");
        String runPath = System.getProperty("user.dir");
        String fullFileName = filePrefix + ".properties";
	String fullPath = String.join(s, runPath, fullFileName);
	File f = new File(fullPath);
	if (!f.isFile()) {
            throw new Exception(String.format("loadIndex unable to find property file: %s", filePrefix));
	}
	FileInputStream fileInput = new FileInputStream(f);
	_props = new Properties();
	_props.load(fileInput);
	fileInput.close();     
    }
            
    public String GetProperty(String property) {
        return _props.getProperty(property);
    }
}
