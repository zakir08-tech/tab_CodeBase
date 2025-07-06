package com.api.automation.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.api.automation.bolt.API_TestRunner;
import com.api.automation.bolt.Constants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class common {
	static String getNewString;
	static int getIndex;
	static String jsonFilePath = Constants.ENV_VAR_JSON_FILE;
	
	@SuppressWarnings("unchecked")
	public static void addNewEnvVarToJson(String jsonElmName, String jsonElmVal) {	
		try {
			Reader reader = new FileReader(jsonFilePath);
			JSONParser parser = new JSONParser();
			
	        JSONObject data = (JSONObject) parser.parse(reader);
	        
	        data.put(jsonElmName, jsonElmVal);
	        
	        Gson gson = new GsonBuilder().setPrettyPrinting().create();
	        String json = gson.toJson(data);
	        
	        try (FileWriter file = new FileWriter(jsonFilePath)) {
				file.write(json);
				file.flush();
			}
		} catch (IOException | ParseException e) {
			System.err.println(e.toString());
			//e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Object readEnvVarFromJson(String jsonElmName) {	
		Object getElmVal = null;
		try {
			Reader reader = new FileReader(jsonFilePath);
			JSONParser parser = new JSONParser();
	        JSONObject data = (JSONObject) parser.parse(reader);
	        
	        getElmVal = data.get(jsonElmName);
		} catch (IOException | ParseException e) {
			System.err.println(e.toString());
			//e.printStackTrace();
		}
		return getElmVal;
	}
	
	@SuppressWarnings("unchecked")
	public void updateEnvVarJson(LinkedHashMap<String, String> envVarList) {
		
		try {
			Reader reader = new FileReader(jsonFilePath);
			JSONParser parser = new JSONParser();
			JSONObject data = (JSONObject) parser.parse(reader);
			
			LinkedHashMap<String, String> result = (LinkedHashMap<String, String>) new ObjectMapper().readValue(data.toString(), new TypeReference<Map<String, String>>(){});
			result.forEach((key,value) -> {
				if(!envVarList.containsKey(key)) 
					data.remove(key);
			});
			
			envVarList.forEach((key,value) -> {
				if(!result.containsKey(key)) 
					data.put(key,value);
			});
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
	        String json = gson.toJson(data);
	        
	        try (FileWriter file = new FileWriter(jsonFilePath)) {
				file.write(json);
				file.flush();
			}
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void getJsonElementValue(Object testRunId, LinkedHashMap<Object, Object> jsonElement, Object jsonResponse) {
	    String readLine = "";
	    char[] readChars = null;
	    String readElmBuffer = "";
	    BufferedReader jsonBuffer = null;
	    getNewString = "";
	    int k = 0;
	    HashMap<Object, List<Object>> storeJsonResponse = new HashMap<>();
	    ArrayList<Object> storeElementVal = new ArrayList<Object> ();
 
        StringReader jsonReader = new StringReader((String) jsonResponse);
 
        try {
            jsonBuffer = new BufferedReader(jsonReader);
            while ((readLine = jsonBuffer.readLine()) != null) {
            	getNewString = getNewString + readLine + "\n";
            }
 
            for (Map.Entry<Object, Object> entry: jsonElement.entrySet()) {
                int getTagPosition = 1;
                String newTagName = "";
                String tagName = (String) entry.getKey();
                String elementName = entry.getValue().toString();
                String getChar = "";
                boolean charFnd = false;
                boolean elmFnd = false;
                Scanner checkForDigit;
                boolean tagFound = true;
                boolean linearSrch = false;
                readElmBuffer ="";
 
                char[] array = tagName.toCharArray();
                for (char ch: array) {
                    if (ch == ']') {
                        charFnd = false;
                        newTagName = newTagName + ch;
                    }
 
                    if (charFnd == true) {
                        getChar = getChar + ch;
                        newTagName = newTagName + ch;
                    }
 
                    if (ch == '[') {
                        charFnd = true;
                        newTagName = newTagName + ch;
                    }
                }
 
                if (!getChar.isEmpty()) {
                	checkForDigit = new Scanner(getChar);
                	if (checkForDigit.hasNextInt()) {
	                    getTagPosition = Integer.parseInt(getChar);
	                    tagName = tagName.replace(newTagName, "");
                	} else System.out.println("tag position number is not a digit: " + tagName);
                }else
                	linearSrch = true;
 
                    String getNewTag = "\"" + tagName + "\"";
                    readChars = getNewString.toCharArray();
                    int iInc;
                    int kPost = getNextTagPositionFromResponse(0, readChars, getNewTag);
                    
                    if (kPost != 0) {
                    	for (iInc = kPost; iInc<= readChars.length - 1; iInc++) {
	                        boolean isCharAdigit = Character.isDigit(readChars[iInc]);
	                        if (isCharAdigit == true) {
	                            getIndex = iInc;
	                            for (int jInc = iInc; jInc<= readChars.length - 1; jInc++) {
	                                //if (Character.isDigit(readChars[jInc]) == false) {
	                                if (readChars[jInc] == ',' && readChars[jInc - 1] != '\\') {	
	                                    elmFnd = true;
	                                    break;
	                                }
                                    readElmBuffer = readElmBuffer + readChars[jInc];
                                }
                            } else if (readChars[iInc] == '"' && readChars[iInc - 1] != '\\') {
	                            getIndex = iInc + 1;
	                            int jInc;
	                            for (jInc = iInc + 1; jInc<= readChars.length - 1; jInc++) {
	                            	if (readChars[jInc] == '"' && readChars[jInc - 1] != '\\') {
	                                    elmFnd = true;
	                                    break;
                                    }
                                    readElmBuffer = readElmBuffer + readChars[jInc];
                                }
                            } 
           
                            if (elmFnd == true) {
                            	addNewEnvVarToJson(elementName,readElmBuffer.toString());
                            	break;
                            }                                
                    	}
                    }
               
               if(elmFnd == false) {
            	   
               }
               
	           /*if (kPost == 0) {
	        	   storeElementVal.add("#.");
	        	   storeElementVal.add("#.");
	            } else {
	                storeElementVal.add(elementName);
	                if(readElmBuffer.isEmpty()&& linearSrch == false){
	                	readElmBuffer = "no text matched with the expected text for the given tag";
	                }
	               storeElementVal.add(readElmBuffer);
	            }
	 
	            storeJsonResponse.put(entry.getKey(), storeElementVal);
	            API_TestRunner.verifyJsonResponseAttributes.put(testRunId, storeJsonResponse);
	            readElmBuffer = "";
	            storeElementVal = new ArrayList<>();*/
            }
 
        } catch (FileNotFoundException exp) {
        	exp.printStackTrace();
        } catch (IOException exp) {
        	exp.printStackTrace();
        }
    }
	
	public static boolean VerifyJsonTagElement(Object testRunId, LinkedHashMap<Object, Object> jsonElement, Object jsonResponse) {
        String readLine = "";
        char[] readChars = null;
        String readElmBuffer = "";
        BufferedReader jsonBuffer = null;
        getNewString = "";
        int k = 0;
        boolean jsonElmTextMatched =true;
        
        HashMap<Object, List<Object>> storeJsonResponse = new HashMap<>();
        ArrayList<Object> storeElementVal = new ArrayList<Object> ();
        StringReader jsonReader = new StringReader((String) jsonResponse);
 
        try {
            jsonBuffer = new BufferedReader(jsonReader);
            while ((readLine = jsonBuffer.readLine()) != null) {
            	getNewString = getNewString + readLine + "\n";
            }
 
            for (Map.Entry<Object, Object> entry: jsonElement.entrySet()) {
                int getTagPosition = 1;
                String newTagName = "";
                String tagName = (String) entry.getKey();
                String elementValue = entry.getValue().toString();
                String getChar = "";
                boolean charFnd = false;
                boolean elmFnd = false;
                Scanner checkForDigit;
                //int cntIndex = 0;
                boolean tagFound = true;
                boolean linearSrch = false;
 
                char[] array = tagName.toCharArray();
                for (char ch: array) {
                    if (ch == ']') {
                        charFnd = false;
                        newTagName = newTagName + ch;
                    }
 
                    if (charFnd == true) {
                        getChar = getChar + ch;
                        newTagName = newTagName + ch;
                    }
 
                    if (ch == '[') {
                        charFnd = true;
                        newTagName = newTagName + ch;
                    }
                }
 
                if (!getChar.isEmpty()) {
                	checkForDigit = new Scanner(getChar);
                	if (checkForDigit.hasNextInt()) {
	                    getTagPosition = Integer.parseInt(getChar);
	                    tagName = tagName.replace(newTagName, "");
                	} else System.out.println("tag position number is not a digit: " + tagName);
                }else
                	linearSrch = true;
 
                    String getNewTag = "\"" + tagName + "\"";
                    readChars = getNewString.toCharArray();
                    int iInc;
                    int kPost = getNextTagPositionFromResponse(0, readChars, getNewTag);
                    
                    if (kPost != 0) {
                    	for (iInc = kPost; iInc<= readChars.length - 1; iInc++) {
	                        boolean isCharAdigit = Character.isDigit(readChars[iInc]);
	                        if (isCharAdigit == true) {
	                            getIndex = iInc;
	                            for (int jInc = iInc; jInc<= readChars.length - 1; jInc++) {
	                                if (Character.isDigit(readChars[jInc]) == false) {
	                                    elmFnd = true;
	                                    break;
	                                }
                                    readElmBuffer = readElmBuffer + readChars[jInc];
                                }
                            } else if (readChars[iInc] == '"' && readChars[iInc - 1] != '\\') {
	                            getIndex = iInc + 1;
	                            int jInc;
	                            for (jInc = iInc + 1; jInc<= readChars.length - 1; jInc++) {
	                            	if (readChars[jInc] == '"' && readChars[jInc - 1] != '\\') {
	                                    elmFnd = true;
	                                    break;
                                    }
                                    readElmBuffer = readElmBuffer + readChars[jInc];
                                }
                                //kPost = jInc;
                            } /*else if (readChars[iInc] == 'f' || readChars[iInc] == 't') {
	                            getIndex = iInc;
	                            for (int jInc = iInc; jInc<= readChars.length - 1; jInc++) {
	                                if (readChars[jInc] == ',' || readChars[jInc] == '}' || readChars[jInc] == '\n') {
	                                    elmFnd = true;
	                                    break;
	                                }
                                    readElmBuffer = readElmBuffer + readChars[jInc];
	                            }
                            }*/
 
                            if (elementValue.toLowerCase().contentEquals(readElmBuffer.toString().toLowerCase())) {
                                if (elmFnd == true) {
                                	break;
                                }
                            } else
                                if(linearSrch == true && !readElmBuffer.isEmpty()){
                                	break;
                                }
                                readElmBuffer = "";
                    		}
                        }
                    
                    	if(elmFnd ==false) {
                    		storeElementVal.add("json~elm~not~found");
                    		jsonElmTextMatched =false;
                    	}
                    		
                        if (kPost == 0) {
                            storeElementVal.add("");
                        } else {
                            storeElementVal.add(elementValue);
                            if(readElmBuffer.isEmpty()&& linearSrch == false){
                            	readElmBuffer = "no text matched with the expected text for the given tag";
                            }
                            storeElementVal.add(readElmBuffer);
                            if(!storeElementVal.get(0).toString().contentEquals(storeElementVal.get(1).toString()))
                            	jsonElmTextMatched =false;
                        }
                        
                        storeJsonResponse.put(entry.getKey(), storeElementVal);
                        API_TestRunner.verifyJsonResponseAttributes.put(testRunId, storeJsonResponse);
                        readElmBuffer = "";
                        storeElementVal = new ArrayList<>();
            }
 
        } catch (FileNotFoundException exp) {
                    exp.printStackTrace();
        } catch (IOException exp) {
                    exp.printStackTrace();
        }
        
		return jsonElmTextMatched;
 
    }
 
    public static int getNextTagPositionFromResponse(int charReadPosition, char[] responseChars, String tagNewName) {
        int k = 0;
	    for (int iChar = charReadPosition; iChar<= responseChars.length - 1; iChar++) {
	    	String getRunTimeTxt = "";
            for (int jChar = iChar; jChar<= tagNewName.length() - 1 + iChar; jChar++) {
            	try {
            		getRunTimeTxt = getRunTimeTxt + responseChars[jChar];
                } catch (ArrayIndexOutOfBoundsException exp) {
                	//exp.printStackTrace();
                }
            }
	 
            if (tagNewName.contentEquals(getRunTimeTxt.trim())) {
		        k = iChar + tagNewName.length();
		        break;
            }
        }
	    return k;
    }
}
