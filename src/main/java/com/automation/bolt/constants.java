package com.automation.bolt;

import org.apache.log4j.PropertyConfigurator;

public class constants {
	public static String userDir = System.getProperty("user.dir").replaceAll("\\\\", "/");
	public static String propFileName = "RunConfig.properties";
	public static String waitTimeInSec =null;
	public static String testRunList =null;
	public static String testRunBrowser =null;
	public static String runnerFileName =null;
	public static String runMode =null;
	public static boolean externalRun =false;
	public static String driverPathJSONfile =userDir +"/config/driverPath.json";
        
    public static void loadder(String[] args) {
    	//PropertyConfigurator.configure(boltExecutor.class.getResourceAsStream("log4j.properties").toString());
    	common.readConfigProperties();
    	externalRun =true;
    	
    	try {
    		runnerFileName =args[0];
    	}catch(ArrayIndexOutOfBoundsException exp) {
    		runnerFileName =common.prop.getProperty("test.suite.file.path");
    	}
    	
    	try {
    		testRunList =args[1];
    		if(args[1].isEmpty())
    			testRunList =common.prop.getProperty("test.run.id.list");
    	}catch(ArrayIndexOutOfBoundsException exp) {
    		testRunList =common.prop.getProperty("test.run.id.list");
    	}
    	
    	try {
    		testRunBrowser=args[2];
    		if(args[2].isEmpty())
    			testRunBrowser =common.prop.getProperty("test.run.driver.browser");
    	}catch(ArrayIndexOutOfBoundsException exp) {
    		testRunBrowser =common.prop.getProperty("test.run.driver.browser");
    	}
    	
    	try {
    		waitTimeInSec=args[3];
    		if(args[3].isEmpty())
    			waitTimeInSec =common.prop.getProperty("test.run.wait.timeout");
    	}catch(ArrayIndexOutOfBoundsException exp) {
    		waitTimeInSec =common.prop.getProperty("test.run.wait.timeout");
    	}
    	
    	try {
    		runMode=args[4];
    		if(args[4].isEmpty())
    			runMode =common.prop.getProperty("test.run.driver.headless");
    	}catch(ArrayIndexOutOfBoundsException exp) {
    		runMode =common.prop.getProperty("test.run.driver.headless");
    	}
    	
		if(runnerFileName ==null) {
			boltExecutor.log.error("required [test.suite.file.path] test runner file name not found in the config file!\nexiting the run......");
			System.exit(0);
		}
		
		if(waitTimeInSec ==null)
			waitTimeInSec ="60";
		
		if(testRunList ==null || testRunList.isEmpty()) {
			boltExecutor.log.error("required [test.run.id.list] test run list not found!\nexiting the run......");
			System.exit(0);
		}
		
		if(testRunBrowser ==null || (!testRunBrowser.toLowerCase().contentEquals("chrome") && 
				!testRunBrowser.toLowerCase().contentEquals("edge")))
			testRunBrowser ="chrome";
		
		if(runMode ==null || (!runMode.toLowerCase().contentEquals("true") && 
				!runMode.toLowerCase().contentEquals("false")))
			runMode ="false";
    }
}
