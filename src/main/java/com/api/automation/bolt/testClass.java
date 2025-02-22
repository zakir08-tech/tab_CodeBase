package com.api.automation.bolt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.json.JsonValue;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class testClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("name", "1");
	      jsonObject.put("keystore", "./cert/keystore1.jks");
	      jsonObject.put("keystore-pwd", "Bhagavatula");
	      jsonObject.put("truststore", "1989-09-26");
	      jsonObject.put("truststore-pwd", "Vishakhapatnam");
	     
	      JSONArray array = new JSONArray();
		
	      
	      array.add(jsonObject);
	      jsonObject = new JSONObject();
	      
	      jsonObject.put("name", "1");
	      jsonObject.put("keystore", "./cert/keystore2.jks");
	      jsonObject.put("keystore-pwd", "Bhagavatula1");
	      jsonObject.put("truststore", "1989-09-261");
	      jsonObject.put("truststore-pwd", "Vishakhapatnam1");
	      
	      array.add(jsonObject);
	      
	      try {
	    	 
	    	  Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    	  JsonElement jsonElement = JsonParser.parseString(array.toJSONString());
	    	  Object prettyJson = gson.toJson(jsonElement);
	    	  
	    	  File directory = new File(String.valueOf("./ssl"));

	    	  if (!directory.exists()) {
	    		  directory.mkdir();    
	    	  }
	    	  
	          FileWriter file = new FileWriter("./ssl/sslCert.json");
	          file.write(prettyJson.toString());
	          file.close();
	          	    	  
	       } catch (IOException e) {
	          // TODO Auto-generated catch block
	          e.printStackTrace();
	       }
		
			
	}
}
