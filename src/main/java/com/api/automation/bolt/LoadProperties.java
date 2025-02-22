package com.api.automation.bolt;
 
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
 
public class LoadProperties {
	public static Properties prop;
	 
    public static void PropertyLoader() throws IOException {
    	//System.out.println(Constants.PROPERTY_FILE_NAME);
	    FileReader reader = new FileReader(Constants.PROPERTY_FILE_NAME);
	    prop = new Properties();
	    prop.load(reader);
	}
}
