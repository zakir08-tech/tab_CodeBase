package com.automation.bolt;

import org.apache.log4j.PropertyConfigurator;

public class constants {
	public static String userDir = System.getProperty("user.dir").replaceAll("\\\\", "/");
	public static String propFileName = "RunConfig.properties";
	public static String waitTimeInSec;
	public static String testRunList;
	public static String testRunBrowser;
	public static String runnerFileName;
	public static String driverPathJSONfile =userDir +"/config/driverPath.json";
        
    public static void loadder() {
    	PropertyConfigurator.configure(boltExecutor.class.getResourceAsStream("log4j.properties"));
    	common.readConfigProperties();
    	
    	runnerFileName =common.prop.getProperty("test.suite.file.path");
		if(runnerFileName ==null) {
			boltExecutor.log.error("required [test.suite.file.path] test runner file name not found in the config file!\nexiting the run......");
			System.exit(0);
		}
		
		waitTimeInSec =common.prop.getProperty("test.run.wait.timeout");
		if(waitTimeInSec ==null)
			waitTimeInSec ="60";
		
		testRunList =common.prop.getProperty("test.run.id.list");
		if(testRunList ==null || testRunList.isEmpty()) {
			boltExecutor.log.error("required [test.run.id.list] test run list not found!\nexiting the run......");
			System.exit(0);
		}
		
		testRunBrowser =common.prop.getProperty("test.run.driver.browser");
		if(testRunBrowser ==null || (!testRunBrowser.toLowerCase().contentEquals("chrome") && 
				!testRunBrowser.toLowerCase().contentEquals("edge")))
			testRunBrowser ="chrome";
    }
}
