package com.automation.bolt;

public class constants {
	public static String userDir = System.getProperty("user.dir").replaceAll("\\\\", "/");
	//public static String testFlow = "TestFlow";
	//public static String objectRepo = "ObjectRepository";
	public static String propFileName = "config.properties";
	public static int waitTimeInSec;
	public static String runnerFileName;
	public static String driverPathJSONfile =userDir +"/config/driverPath.json";
        public static String trSatusPass ="table-success";
        public static String trSatusFail ="table-danger";
        public static String cardHeaderStatusPass ="#8fbc8f";
        public static String cardHeaderStatusFail ="pink";
        
        public static void loadder() {
		try {
			runnerFileName = common.prop.getProperty("bolt.test.runner.file.name");
			if(runnerFileName ==null) {
				//System.out.println("required [bolt.test.runner.file.name=] test runner file name not found in the config file!\nexiting the run......");
				boltExecutor.log.error("required [bolt.test.runner.file.name=] test runner file name not found in the config file!\nexiting the run......");
				System.exit(0);
			}
			waitTimeInSec = Integer.parseInt(common.prop.getProperty("bolt.drivers.default.wait.timeout"));
		}catch(NumberFormatException exp) {
			//System.out.println(exp.getMessage());
			boltExecutor.log.error(exp.getMessage());
			waitTimeInSec = 10;
		}
	}
}
