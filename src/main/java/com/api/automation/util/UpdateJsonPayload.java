package com.api.automation.util;
 
import com.api.automation.bolt.UserDefineExternalSolutions;
import com.api.automation.bolt.loadAPITestRunner;
 
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
 
public class UpdateJsonPayload {
 
	static String getNewString;
	private static int getIndex;
 
    public static String UpdateJsonTagElement(Object testRunId, HashMap<Object, Object> jsonElement, String jsonPayload) {
 
	    String readLine = "";
	    char[] readChars = null;
	    String readElmBuffer = "";
	    BufferedReader jsonBuffer = null;
	    getNewString = "";
	    int k = 0;
	    String tagName="";
	    Object getMthdReturnValue;

		String splitTagName =null;
		String getTestId =null;
		HashMap<Object, Object> getPrevJsonResponse =null;
		String getJsonResponse =null;
		String getRespTagVal =null;
		String[] getMethAttributes =null;
		String getMethodName =null;
		String[] getMethodArgs =null;

	    for (Map.Entry<Object, Object> getTagElm : jsonElement.entrySet()) {
	    	if(getTagElm.getValue().toString().startsWith("{") &&
	    			getTagElm.getValue().toString().endsWith("}")){

			try{
				splitTagName = getTagElm.getValue().toString().replaceAll("[{]","").replaceAll("[}]", "");
				getRespTagVal = (String) common.readEnvVarFromJson((String) splitTagName);
            	
				jsonElement.replace(getTagElm.getKey(), getRespTagVal);
			} catch(NullPointerException ignored){}

	    	}else if(getTagElm.getValue().toString().contains("|")){                        
	            getMethAttributes =getTagElm.getValue().toString().split("[|]");
	            getMethodName =getMethAttributes[1];

	            try{
	            	getMethodArgs =getMethAttributes[2].split(",");
	            }catch(ArrayIndexOutOfBoundsException exp){}
                                                             
	            UserDefineExternalSolutions.readExternalMethodName = getMethodName;
	            getMthdReturnValue = UserDefineExternalSolutions.runExternalMethod(getMethodArgs);
                                               
	            if(getMthdReturnValue.toString().contentEquals("#.")){
	            	System.out.println("required method [" +getMethodName+ "] not found.");
	            }else
	            	jsonElement.replace(getTagElm.getKey(), getMthdReturnValue.toString());
		    	}
            }
 
	    	try {
	            Reader inputString = new StringReader(jsonPayload);
	            //jsonBuffer = new BufferedReader(new FileReader(jsonFile));
	            jsonBuffer = new BufferedReader(inputString);
	            while ((readLine = jsonBuffer.readLine()) != null) {
	            	getNewString = getNewString + readLine + "\n";
	            }
 
                for (Map.Entry<Object, Object> entry: jsonElement.entrySet()) {
                    int getTagPosition = 1;
                    String newTagName = "";
                    tagName = (String) entry.getKey();
                    
                    String elementName =null;
                    
                    try {
                    	elementName = entry.getValue().toString();
                    }catch(NullPointerException exp) {}
                    
                    String getChar = "";
                    boolean charFnd = false;
                    boolean elmFnd = false;
                    Scanner checkForDigit;
                    int cntIndex = 0;
 
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
 
                    for (int i = 0; i <= readChars.length - 1; i++) {
                    	String getRunTimeTxt = "";
                        for (int j = i; j <= getNewTag.length() - 1 + i; j++) {
                        	getRunTimeTxt = getRunTimeTxt + readChars[j];
                        }
 
                        if (getNewTag.contentEquals(getRunTimeTxt.trim())) {
	                        cntIndex++;
	                        if (getTagPosition == cntIndex) {
	                            k = i + getNewTag.length();
	                            break;
	                        }
                        }
                    }
 
                    for (int i = k; i <= readChars.length - 1; i++) {
                    	boolean isCharAdigit = Character.isDigit(readChars[i]);
                    	if (isCharAdigit == true) {
                    		getIndex = i;
                    		for (int j = i; j <= readChars.length - 1; j++) {
                    			//if (Character.isDigit(readChars[j]) == false) {
                    			if (readChars[j] == ',' && readChars[j - 1] != '\\') {		
	                                elmFnd = true;
	                                break;
                    			}
                    			readElmBuffer = readElmBuffer + readChars[j];
                    		}
                    	} else if (readChars[i] == '"') {
                    		getIndex = i + 1;
                    		for (int j = i + 1; j <= readChars.length - 1; j++) {
	                            if (readChars[j] == '"') {
	                                elmFnd = true;
	                                break;
	                            }
	                            readElmBuffer = readElmBuffer + readChars[j];
                            }
                    	} else if (readChars[i] == 'f' || readChars[i] == 't') {
                    		getIndex = i;
                    		for (int j = i; j <= readChars.length - 1; j++) {
                    			if (readChars[j] == ',' || readChars[j] == '}' || readChars[j] == '\n') {
	                                elmFnd = true;
	                                break;
                    			}
                    			readElmBuffer = readElmBuffer + readChars[j];
                    		}
                    	}
 
                        if (elmFnd == true) break;
                    }
                    
                    if(elementName ==null)
                    	elementName ="<null>";
                    	
                    	getNewString = getNewString.substring(0, getIndex) + elementName + getNewString.substring(getIndex + readElmBuffer.length());
	                
                    readElmBuffer = "";
                }
            } catch(FileNotFoundException exp) {
            	exp.printStackTrace();
            } catch(ArrayIndexOutOfBoundsException exp) {
	            exp.printStackTrace();
	            System.out.println("Required tag [" +tagName+ "] to modify not found in json payload!");
	            jsonElement.replace(tagName, "#.");
            } catch (IOException exp) {
            	exp.printStackTrace();
            } catch (StringIndexOutOfBoundsException exp) {}
 
	    	return getNewString;
    }
}