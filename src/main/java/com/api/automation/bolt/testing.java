package com.api.automation.bolt;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.io.FileWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.api.automation.util.common;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class testing {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException, ParseException {
		common common = new common();
		// TODO Auto-generated method stub
		//common.addNewEnvVarToJson("access_id", "898109hkk");
		
		LinkedHashMap<String, String> envVarList = new LinkedHashMap<String, String>();
		envVarList.put("token_id", "898j8j8jj");
		envVarList.put("access_id", "898j8j8jj");
		envVarList.put("username", "898j8j8jj");
		envVarList.put("password", "898j8j8jj");
		envVarList.put("date", "12-Feb-2010");
		
		common.updateEnvVarJson(envVarList);
		
	}
	
	
}
