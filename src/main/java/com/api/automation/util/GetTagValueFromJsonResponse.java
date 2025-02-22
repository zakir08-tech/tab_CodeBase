package com.api.automation.util;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;
 
public class GetTagValueFromJsonResponse {
	static String getNewString;
	static int getIndex;
 
    public static String GetJsonTagElement(String jsonTagName, Object jsonResponse) {
	    String readLine = "";
	    char[] readChars = null;
	    String readElmBuffer = "";
	    BufferedReader jsonBuffer = null;
	    getNewString = "";
	    int k = 0;
	    int getTagPosition = 1;
	    String newTagName = "";
	    String tagName = jsonTagName;
	    String getChar = "";
	    boolean charFnd = false;
	    boolean elmFnd = false;
	    Scanner checkForDigit;
	    int cntIndex = 0;
	    boolean tagFound = true;
                       
        try {
            StringReader jsonReader = new StringReader((String) jsonResponse);
           
            jsonBuffer = new BufferedReader(jsonReader);
            while ((readLine = jsonBuffer.readLine()) != null) {
            	getNewString = getNewString + readLine + "\n";
            }
 
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
            }
 
            String getNewTag = "\"" + tagName + "\"";
            readChars = getNewString.toCharArray();
 
            for (int i = 0; i<= readChars.length - 1; i++) {
                String getRunTimeTxt = "";
                for (int j = i; j<= getNewTag.length() - 1 + i; j++) {
                    try {
                    	getRunTimeTxt = getRunTimeTxt + readChars[j];
                    } catch (ArrayIndexOutOfBoundsException exception) {
                    	tagFound = false;
                    }
                }
 
                if (getNewTag.contentEquals(getRunTimeTxt.trim())) {
                	cntIndex++;
                	if (getTagPosition == cntIndex) {
                        k = i + getNewTag.length();
                        break;
                    }
                }
            }
 
            if (tagFound == true) {
            	for (int i = k; i<= readChars.length - 1; i++) {
            		boolean isCharAdigit = Character.isDigit(readChars[i]);
                    if (isCharAdigit == true) {
                    	getIndex = i;
                        for (int j = i; j<= readChars.length - 1; j++) {
                        	if (Character.isDigit(readChars[j]) == false) {
	                            elmFnd = true;
	                            break;
                            }
                            readElmBuffer = readElmBuffer + readChars[j];
                        }
                    } else if (readChars[i] == '"') {
	                    getIndex = i + 1;
	                    for (int j = i + 1; j<= readChars.length - 1; j++) {
	                        if (readChars[j] == '"') {
	                            elmFnd = true;
	                            break;
	                        }
                            readElmBuffer = readElmBuffer + readChars[j];
                        }
                    } else if (readChars[i] == 'f' || readChars[i] == 't') {
                    	getIndex = i;
                        for (int j = i; j<= readChars.length - 1; j++) {
                        	if (readChars[j] == ',' || readChars[j] == '}' || readChars[j] == '\n') {
			                    elmFnd = true;
			                    break;
                            }
                            readElmBuffer = readElmBuffer + readChars[j];
                        }
                    }
 
                    if (elmFnd == true) break;
                }
            }
        } catch (NullPointerException e) {
	        //System.out.println(e.toString());
	        System.out.println("required tag [" +jsonTagName+ "] not found in json response");
        } catch (IOException e) {
        	System.out.println(e.toString());
        }
                       
        if (tagFound == false) {
        	readElmBuffer = "#.";
        }
                       
        return readElmBuffer;
 
    }
 
}