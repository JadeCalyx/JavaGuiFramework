/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blogspot.jadecalyx;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import com.blogspot.jadecalyx.webtools.jcBrowserFactory;
import com.blogspot.jadecalyx.webtools.jcBrowser;
import java.io.File;
import java.util.Properties;

/**
 *
 * @author johnchambers
 */
public class TestSandbox {
    
    public TestSandbox() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void hello() throws Exception {
        System.out.println("start");
	String p = System.getProperty("file.separator");
        String curDir = System.getProperty("user.dir");
        jcBrowserFactory bf = new jcBrowserFactory("Wikipedia");
        jcBrowser br = bf.GetNewBrowser("firefox");
        System.out.println(br.GetHello());
        System.out.println(File.pathSeparator);
        br.Close();
        
        System.out.println("done");
        
    }
}
