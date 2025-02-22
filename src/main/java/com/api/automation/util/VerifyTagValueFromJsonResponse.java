package com.api.automation.util;
 
import com.api.automation.bolt.API_TestRunner;
 
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
 
public class VerifyTagValueFromJsonResponse {
	static String getNewString;
	static int getIndex;
 
    public static void VerifyJsonTagElement(Object testRunId, LinkedHashMap<Object, Object> jsonElement, Object jsonResponse) {
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
 
                            if (elementName.toLowerCase().contentEquals(readElmBuffer.toString().toLowerCase())) {
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
 
                        if (kPost == 0) {
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
                        storeElementVal = new ArrayList<>();
            }
 
        } catch (FileNotFoundException exp) {
                    exp.printStackTrace();
        } catch (IOException exp) {
                    exp.printStackTrace();
        }
 
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