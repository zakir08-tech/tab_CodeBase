package com.api.automation.util;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
 
public class ReadPayloadFile {
 
	public static String readPayload;
	public static String exceptionMessage;
 
    public static String readPayloadFromFile(String payloadFile) {
	    readPayload ="";
	    if (payloadFile.isEmpty()) {
	        exceptionMessage = "payload file path not defined.";
	        System.out.println("*** PAYLOAD FILE PATH NOT DEFINED! ***");
	    }
 
        File file = new File(payloadFile);
        try {
        	readPayload = new String(Files.readAllBytes(Paths.get(file.toString())));
        } catch(IOException e) {
	        System.out.println(e.toString());
	        VerifyValueAPICommon.verifyErrorMessage(e.toString(), e.getClass().getName());
        }
        return readPayload;
    }
 
    public static String readEncodedPayloadFromFile(String payload){
    	String getLoadTxt ="";
        String[] pload =payload.split("\n");
        String getKey ="";
        String getValue ="";
        String getNewLoad ="";
        
        for(String load: pload){
        	getKey =load.split(":")[0];
        	getValue =load.split(":")[1];
        	getNewLoad =getKey +"="+ getValue;
        	
        	getLoadTxt = getLoadTxt + getNewLoad + "&";
        }
 
        readPayload = getLoadTxt.substring(0, getLoadTxt.length() - 1);
        return readPayload;
    }
 
    public static String readNonJsonPayload(String payloadFile){
	    String readPayload ="";
	    /*if (payloadFile.isEmpty()) {
	                exceptionMessage = "payload file path not defined.";
	                System.out.println("*** PAYLOAD FILE PATH NOT DEFINED! ***");
	    }*/
 
        try {
            FileInputStream fis=new FileInputStream(payloadFile);
            Scanner sc=new Scanner(fis);
            while(sc.hasNextLine())
            {
            	readPayload = readPayload + sc.nextLine().toString() + "\n";
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
            VerifyValueAPICommon.verifyErrorMessage(e.toString(), e.getClass().getName());
        }
 
        readPayload = readPayload.substring(0, readPayload.length() - 1);
        return readPayload;
    }
 
}