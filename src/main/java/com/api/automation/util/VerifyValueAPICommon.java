package com.api.automation.util;
 
//import com.automation.bolt.runner.TestRunner;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.text.WordUtils;
import org.xml.sax.InputSource;
 
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.xml.parsers.DocumentBuilderFactory;
 
public class VerifyValueAPICommon {
	public static LinkedHashMap < Object, Object > errorMapping = new LinkedHashMap<>();
 
    public static void verifyResponseCodeStatus(Object actualStausCode, Object expectedStatusCode) {
	    if (actualStausCode == expectedStatusCode) System.out.println("response code: expected[" + expectedStatusCode + "] actual[" + actualStausCode + "]");
	    else System.out.println("response code: expected[" + expectedStatusCode + "] actual[" + actualStausCode + "]");
    }
 
    public static void verifyErrorMessage(String errorMessage, String ignoredStrClassName) {
	    errorMapping = new LinkedHashMap <> ();
	    String gerErrorDesc = null;
 
        gerErrorDesc = WordUtils.wrap(errorMessage, 150, "\n", false);;
 
        errorMapping.put("Error", "Runtime Error");
        errorMapping.put("ErrorDesc", gerErrorDesc);
 
        /*if (errorMessage.toLowerCase().contains("failed to respond")) {
                    errorMapping.put("Error", JavaExceptionLibrary.NoHttpResponse_Error);
                    errorMapping.put("ErrorDesc", gerErrorDesc);
        } else if (errorMessage.toLowerCase().contains("path building failed")) {
                    errorMapping.put("Error", JavaExceptionLibrary.SSLHandShake_Error);
                    errorMapping.put("ErrorDesc", gerErrorDesc);
        } else if (errorMessage.toLowerCase().contains("read timed out")) {
                    errorMapping.put("Error", JavaExceptionLibrary.SocketTimeout_Error);
                    errorMapping.put("ErrorDesc", gerErrorDesc);
        } else if (errorMessage.toLowerCase().contains("connection refused")) {
                    errorMapping.put("Error", JavaExceptionLibrary.Network_Error);
                    errorMapping.put("ErrorDesc", gerErrorDesc);
        } else if (errorMessage.toLowerCase().contains("unknownhostexception")) {
                    errorMapping.put("Error", JavaExceptionLibrary.UnknownHost_Error);
                    errorMapping.put("ErrorDesc", "UnKnown Host Exception: " + gerErrorDesc);
        } else if (errorMessage.toLowerCase().contains("connection timed out")) {
                    errorMapping.put("Error", JavaExceptionLibrary.Network_Error1);
                    errorMapping.put("ErrorDesc", gerErrorDesc);
        } else if (errorMessage.toLowerCase().contains("nosuchfileexception")) {
                    errorMapping.put("Error", JavaExceptionLibrary.NoSuchFile_Error);
                    errorMapping.put("ErrorDesc", "No Such File Found Exception: " + gerErrorDesc);
        } else if (errorMessage.toLowerCase().contains("accessdeniedexception")) {
                    if (gerErrorDesc.trim().contentEquals("")) gerErrorDesc = ReadPayloadFile.exceptionMessage;
                    errorMapping.put("Error", JavaExceptionLibrary.FileAccessDenied_Error);
                    errorMapping.put("ErrorDesc", "File Access Denied Exception: " + gerErrorDesc);
        }else if (errorMessage.toLowerCase().contains("nopayloadfound")){
                    errorMapping.put("Error", JavaExceptionLibrary.PayLoadNotFound_Error);
                    errorMapping.put("ErrorDesc", gerErrorDesc);
        }else if (errorMessage.toLowerCase().contains("cell index must be >= 0")){
                    errorMapping.put("Error", JavaExceptionLibrary.ColumnNotFoundError_Error);
                    if(TestRunner.getErrorMessageDescription.isEmpty())
                                errorMapping.put("ErrorDesc", gerErrorDesc);
                    else
                                errorMapping.put("ErrorDesc", TestRunner.getErrorMessageDescription);
        }*/
    }
 
    public static boolean isJSONValid(String jsonInString) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        mapper.readTree(jsonInString);
	        return true;
	    } catch(IOException e) {
	    	return false;
	    }
    }
 
    public static boolean isXMLValid(String xmlInString) {
        try {
            DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xmlInString)));
            return true;
        } catch(Exception e) {
        	return false;
        }
    }
}