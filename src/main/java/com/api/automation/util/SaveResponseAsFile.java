package com.api.automation.util;
 
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
 
public class SaveResponseAsFile {
	public static String jsonFilePath;
	
	public void savingResponseToFile(String getResponse, Object apiRequest, String apiTestID) throws IOException {
 
		jsonFilePath = "./json-ResponseFiles/";
		String uniqueID;
 
	    Date date = new Date();
	    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMMM-yyyy hh:mm:ss a");
		String strDate = formatter.format(date).toLowerCase();
		uniqueID = strDate.replaceAll(":", "-");
 
		jsonFilePath = jsonFilePath + apiTestID + "_" + apiRequest + " " + uniqueID + ".txt";
        File file = new File(jsonFilePath);
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(getResponse);
        bw.flush();
        bw.close();
	}
}