package com.api.automation.util;
 
import org.json.JSONObject;
import org.json.XML;
 
public class ConvertXmlToJson {
	static JSONObject newJsonResponse = null;
 
    public static JSONObject XMLtoJSON(String strResponse) {
        newJsonResponse = XML.toJSONObject(strResponse);
        return newJsonResponse;
    }
}