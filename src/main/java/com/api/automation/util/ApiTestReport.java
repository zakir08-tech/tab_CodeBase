package com.api.automation.util;
 
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.json.JSONObject;

import com.api.automation.bolt.API_TestRunner;
import com.api.automation.bolt.loadAPITestRunner;
import com.automation.bolt.constants;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
 
public class ApiTestReport {
	 
	static String bttnColorSuccess = "btn btn-success btn-xs";
	static String bttnColorDanger = "btn btn-danger btn-xs";
	static String bttnColorWarning = "btn btn-warning btn-xs";
	static String responseCodeTextColorSuccess = "text-success";
	static String responseCodeTextColorDanger = "text-danger";
	static String responseCodeTextColorWarning = "text-warning";
	static String textResonseCodeColor;
	static String APITestResultPath;
	public static String TestAutomationReport;
 
    public static String uniqueID;
    public static String property = "java.io.tmpdir";
    public static String tempDir;
    
    public static ExtentTest extentTest;
    public static ExtentReports extent;
    public static ExtentSparkReporter sparkReporter;
 
    public static void GenerateApiTestReport() {
	    HashMap<Object, List<Object>> storeJsonResponse = new HashMap<>();
	    LinkedHashMap<Object, Object> getAPiVerifyTags = new LinkedHashMap<>();
	    Object jsonTagName = null;
	    String jsonTagExpectedVal = null;
	    String jsonTagActualVal = null;
	 
	    boolean jsonTagStatus;
	    boolean jsonRespTagStatus;
	    boolean dbValidtionStatus;
	    boolean jsonTagFnd;
	    
        if(!new File(constants.userDir+"/htmlTestReport").exists()){
        	new File(constants.userDir+"/htmlTestReport").mkdir();
        }
 
        APITestResultPath =constants.userDir+"/htmlTestReport/";
        
        extent = new ExtentReports();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMMM-yyyy hh:mm:ss a");
        String strDate = formatter.format(date).toLowerCase();
        uniqueID = strDate.replaceAll(":", "-");
        TestAutomationReport = APITestResultPath + "BoltApiTestReport " + uniqueID + ".html";
    	sparkReporter = new ExtentSparkReporter(TestAutomationReport);
    	extent.attachReporter(sparkReporter);
    	
    	sparkReporter.config().setOfflineMode(true);
    	sparkReporter.config().setDocumentTitle("Bolt: API Test Report");
    	sparkReporter.config().setReportName("API Test Report");
    	sparkReporter.config().setTheme(Theme.DARK);
    	sparkReporter.config().setTimeStampFormat("EEE, MMMM dd,yyyy, hh:mm: a '('zzz')'");
    	sparkReporter.config().setEncoding("UTF-8");
 
        for (Entry<Object, HashMap<Object, Object>> entryReport: loadAPITestRunner.ApiTestRunnerMap.entrySet()) {
        	Object getRunID = entryReport.getValue().get("Run ID");
            Object getRequest = entryReport.getValue().get("Request");            
            Object getRequestUrl = entryReport.getValue().get("Request URL");
            Object getRequestExpStatus = entryReport.getValue().get("Expected Status");
            Object getRequestActualStatus = entryReport.getValue().get("Actual Status");
            Object getJSONResponse = entryReport.getValue().get("JSON Response");
            
            storeJsonResponse = API_TestRunner.verifyJsonResponseAttributes.get(getRunID);
            getAPiVerifyTags = loadAPITestRunner.saveVerifyRespTagElmMap.get(getRunID);
            jsonTagStatus = true;
            jsonRespTagStatus = true;
            dbValidtionStatus = true;
            jsonTagFnd = true;
            String jsonTagExp = "";
            Object jsonTagAct = "";
            String jsonTagSummaryTxt = "";
            String jsonTagSummary = "";
            String getHeadersDetails = "";
            Markup jResp=null;
            
            Object getJSONPayload = entryReport.getValue().get("Payload");
            Object getAPISummary = entryReport.getValue().get("Test Summary");
            if(getAPISummary.toString().isBlank()) {
            	getAPISummary = "{no test summary available}";
            }
            extentTest = extent.createTest("Test ["+getRunID+"]: " + getAPISummary.toString());
            
            if(getRequest.toString().contentEquals("NA"))
            	extentTest.warning(MarkupHelper.createLabel("API Request type not defined", ExtentColor.PINK));
            else
            	extentTest.info(MarkupHelper.createLabel(getRequest.toString(), ExtentColor.BLACK));
            
            if(getRequestUrl ==null || getRequestUrl.toString().isEmpty())
            	extentTest.warning(MarkupHelper.createLabel("API URL not defined", ExtentColor.PINK));
            else
            	extentTest.info(MarkupHelper.createLabel(getRequestUrl.toString(), ExtentColor.TRANSPARENT));
            
            HashMap<Object, Object> headerMapNew = loadAPITestRunner.saveHeaderMap.get(getRunID);
            try{
            	for (Entry<Object, Object> jsonTagNotFnd: headerMapNew.entrySet()) {
            		getHeadersDetails = getHeadersDetails + jsonTagNotFnd.getKey() +": "+ jsonTagNotFnd.getValue() + "\r\n";
                }
            }catch (NullPointerException exp){
            	//exp.printStackTrace();
            }
 
            if(getHeadersDetails.isEmpty()){
            	getHeadersDetails = "No headers define for the given API request";
            }else
            	extentTest.info(MarkupHelper.createUnorderedList(headerMapNew));
            
            // add payload to test report
            Object getJsonPayloadType =entryReport.getValue().get("Payload Type");
            
            if(getJsonPayloadType.toString().contains("x-www-form-urlencoded")) {
            	try {
            		String[] getItemList = getJSONPayload.toString().split("&");
            		String[] newItemList = new String[getItemList.length];
                	
                	int i=0;
                	
                	for(String x:getItemList) {
                		newItemList[i] =x.replace("=", ":");
                		i++;
                	}
                	extentTest.info(MarkupHelper.createUnorderedList(newItemList));
            	}catch(NullPointerException exp) {}
            }else {
            	if(getJSONPayload !=null) {
            		jResp = MarkupHelper.createCodeBlock(prettyPrintUsingGson(getJSONPayload.toString()),CodeLanguage.JSON);
            		extentTest.info(jResp);
            	}
            }
           
            // add json response to test report
            try {
            	getJSONResponse = getJSONResponse.toString().replaceAll("<", "&lt;").replaceAll(">", "&gt;");
            	extentTest.info(MarkupHelper.createCodeBlock(prettyPrintUsingGson(getJSONResponse.toString()),CodeLanguage.JSON));
            } catch (NullPointerException exp) {
            	//exp.printStackTrace();
            }catch(JsonSyntaxException exp) {
            	extentTest.fail(MarkupHelper.createLabel(getJSONResponse.toString(), ExtentColor.PINK));
            }
            
            if (storeJsonResponse != null) {
            	for (Entry<Object, List<Object>> jsonTagElm: storeJsonResponse.entrySet()) {
	                jsonTagExp = jsonTagElm.getValue().get(0).toString().toLowerCase();
	                jsonTagAct = jsonTagElm.getValue().get(1).toString().toLowerCase();
 
                    if (jsonTagExp != "#." && jsonTagAct != "#.") {
	                    if (!jsonTagElm.getValue().get(0).toString().toLowerCase().contentEquals(jsonTagElm.getValue().get(1).toString().toLowerCase())) {
	                        jsonTagActualVal = " [Actual:" + "<font color=\"red\"><b>" + jsonTagElm.getValue().get(1) + "</b></font>" + "]";
	                        jsonTagStatus = false;
	                    } else {
	                    	jsonTagActualVal = " [Actual:" + "<font color=\"green\"><b>" + jsonTagElm.getValue().get(1) + "</b></font>" + "]";
	                    }
 
                        jsonTagName = jsonTagElm.getKey();
                        jsonTagExpectedVal = "[Expected:" + "<font color=\"green\"><b>" + jsonTagElm.getValue().get(0) + "</b></font>" + "]";
                        jsonTagSummaryTxt = jsonTagName + ":" + jsonTagExpectedVal + jsonTagActualVal + "\n";
                    } else if (jsonTagExp == "#." && jsonTagAct == "#.") {
                        jsonTagStatus = false;
                        jsonTagName = jsonTagElm.getKey();
                        jsonTagSummaryTxt = jsonTagName + ":</font>" + "<font color=\"red\"><b><i>" + "given tag not found in JSON response!" + "</i></b></font>" + "\n";
                    }
 
                    jsonTagSummary = jsonTagSummary + jsonTagSummaryTxt;
                }
            }

            if (getRequestActualStatus != null) {
            	JSONObject resStatus = jsonStatusObject(getRequestExpStatus.toString(), getRequestActualStatus.toString());
            	if (getRequestExpStatus.toString().contentEquals(getRequestActualStatus.toString()) &&
            		jsonTagStatus == true &&
                    jsonRespTagStatus == true &&
                    jsonTagFnd == true &&
                    dbValidtionStatus == true) {
            		extentTest.pass(MarkupHelper.createCodeBlock(resStatus.toString(),CodeLanguage.JSON));
                    textResonseCodeColor = responseCodeTextColorSuccess;
                } else if (getRequestExpStatus.toString().contentEquals(getRequestActualStatus.toString())
                    && jsonTagStatus == false
                    || jsonRespTagStatus == false
                    || jsonTagFnd == false
                    || dbValidtionStatus == false) {
                		extentTest.fail(MarkupHelper.createCodeBlock(resStatus.toString(),CodeLanguage.JSON));
                        textResonseCodeColor = responseCodeTextColorDanger;
                }else {
                		extentTest.fail(MarkupHelper.createCodeBlock(resStatus.toString(),CodeLanguage.JSON));
		                textResonseCodeColor = responseCodeTextColorDanger;
                }
            } else {
                textResonseCodeColor = responseCodeTextColorDanger;
            }
 
            LinkedHashMap<Object, Object> getTagElm = loadAPITestRunner.saveTagElmMap.get(getRunID);
            if (getTagElm != null) {
            	for (Entry<Object, Object> jsonTagElm: getTagElm.entrySet()) {
	                jsonTagExp = (String) jsonTagElm.getKey();
	                jsonTagAct = jsonTagElm.getValue();
                }
            }
 
            getTagElm = loadAPITestRunner.saveTagElmMap.get(getRunID);
            if (getTagElm != null) {
            	for (Entry<Object, Object> jsonTagNotFnd: getTagElm.entrySet()) {
	                jsonTagExp = (String) jsonTagNotFnd.getKey();
	                jsonTagAct = jsonTagNotFnd.getValue();
                }
            }
        }
	        extent.flush();
	        
	        File htmlFile = new File(TestAutomationReport);
	        try {
	        	Desktop.getDesktop().browse(htmlFile.getAbsoluteFile().toURI());
	        } catch (IOException exp) {
	        	exp.printStackTrace();
	        }
    }
 
    public static void saveTestReport(String argHtmlTestFile) {
	    File file = new File(TestAutomationReport);
	    FileWriter fw = null;
 
        try {
	        fw = new FileWriter(file.getAbsoluteFile());
	        BufferedWriter bw = new BufferedWriter(fw);
	        bw.write(argHtmlTestFile);
	        bw.close();
        } catch (IOException exp) {
	        exp.printStackTrace();
	        tempDir = System.getProperty(property);
	        TestAutomationReport = tempDir + "Bolt_ApiTestReport " + uniqueID + ".html";
            saveTestReport(argHtmlTestFile);
            throw new RuntimeException(exp);
        }
    }
    
    public static String prettyPrintUsingGson(String uglyJson) throws JsonSyntaxException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement jsonElement = JsonParser.parseString(uglyJson);
        String prettyJsonString = gson.toJson(jsonElement);
        return prettyJsonString;
    }
    
    public static JSONObject jsonStatusObject(String expStatus, String actStatus) {
    	JSONObject jo = new JSONObject();
    	jo.put("Expected Status", expStatus);
    	
    	try {
    		if(expStatus.trim().contentEquals(actStatus))
        		jo.put("Actual Status", actStatus);
        	else
        		jo.put("Actual Status", Integer.valueOf(actStatus));
        
    	}catch(NumberFormatException exp) {
    		jo.put("Actual Status", "null");
    	}
    		
    	return jo;
    }
}

