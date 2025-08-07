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
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;

import com.api.automation.bolt.API_TestRunner;
import com.api.automation.bolt.loadAPITestRunner;
import com.automation.bolt.constants;
import com.automation.bolt.gui.ExecuteApiTest;
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

 
public class ApiTestReport1 {

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
    public static ExecuteApiTest exeApiTest = new ExecuteApiTest();
 
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
        String getSuiteName = exeApiTest.excelFile.getName().toString().split(".xlsx")[0]+"~";
        TestAutomationReport = APITestResultPath + getSuiteName + uniqueID + ".html";
    	sparkReporter = new ExtentSparkReporter(TestAutomationReport);
    	extent.attachReporter(sparkReporter);
    	
    	sparkReporter.config().setOfflineMode(true);
    	sparkReporter.config().setDocumentTitle("Bolt: API Test Report");
    	sparkReporter.config().setReportName("API Test Report");
    	sparkReporter.config().setTheme(Theme.DARK);
    	sparkReporter.config().setTimeStampFormat("EEE, MMMM dd,yyyy, hh:mm: a '('zzz')'");
    	//sparkReporter.config().setEncoding("UTF-8");
    
        for (Entry<Object, HashMap<Object, Object>> entryReport: loadAPITestRunner.ApiTestRunnerMap.entrySet()) {
        	Object getRunID = entryReport.getValue().get("Run ID");
            Object getRequest = entryReport.getValue().get("Request");            
            Object getRequestUrl = entryReport.getValue().get("Request URL");
            Object getRequestExpStatus = entryReport.getValue().get("Expected Status");
            Object getRequestActualStatus = entryReport.getValue().get("Actual Status");
            Object getJSONResponse = entryReport.getValue().get("JSON Response");
            Object getAuthType = entryReport.getValue().get("Authorization");
            Object getAuth1 = entryReport.getValue().get("AuthVal1");
            Object getAuth2 = entryReport.getValue().get("AuthVal2");
            
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
            if(getAPISummary.toString().isEmpty()) {
            	getAPISummary = "{no test summary available}";
            }
            extentTest = extent.createTest("Test ["+getRunID+"]: " + getAPISummary.toString());
            
            if(getRequest.toString().contentEquals("NA"))
            	extentTest.warning(MarkupHelper.createLabel("API Request type not defined", ExtentColor.PINK));
            else
            	extentTest.info(MarkupHelper.createLabel(getRequest.toString(), ExtentColor.BLUE));
            
            if(getRequestUrl ==null || getRequestUrl.toString().isEmpty())
            	extentTest.warning(MarkupHelper.createLabel("API URL not defined", ExtentColor.PINK));
            else
            	extentTest.info(MarkupHelper.createLabel(getRequestUrl.toString(), ExtentColor.BLUE));
            
            // add header list to test report
            HashMap<Object, Object> headerMapNew = loadAPITestRunner.saveHeaderMap.get(getRunID);  
            if(headerMapNew ==null){
            	getHeadersDetails = "No headers define for the given API request";
            }else {
            	String getHeaderList="";
            	for (Map.Entry<Object, Object> entry : headerMapNew.entrySet()) {
            		getHeaderList = getHeaderList +"<span style=\"color:#33b5ff;\">"+ entry.getKey()+"</span>"+": "+entry.getValue()+"<br>";
            	}
            	
            	extentTest.info(MarkupHelper.createLabel("Headers:", ExtentColor.TEAL).getMarkup().concat("<br>"+getHeaderList));
            }
            	
            // add basic auth to test report
            if(getAuthType.toString().contentEquals("Basic Auth")) {
            	String getBasicAuthUsername ="<span style=\"color:#33b5ff;\">Username</span>: " +getAuth1.toString();
            	String getBasicAuthPwd ="<span style=\"color:#33b5ff;\">Password</span>: " +getAuth2.toString();
            	extentTest.info(MarkupHelper.createLabel("Basic Authentication:", ExtentColor.TEAL).getMarkup()
            			.concat("<br>"+getBasicAuthUsername+"<br>"+getBasicAuthPwd));
            }
            
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
            		extentTest.info(MarkupHelper.createLabel("Payload:", ExtentColor.TEAL).getMarkup().concat(jResp.getMarkup().toString().indent(4)));
            	}
            }
           
            // add json response to test report
            try {
            	jResp = MarkupHelper.createCodeBlock(prettyPrintUsingGson(getJSONResponse.toString()),CodeLanguage.JSON);
            	extentTest.pass(MarkupHelper.createLabel("Response:", ExtentColor.TEAL).getMarkup().concat(jResp.getMarkup().toString().indent(4)));
            } catch (NullPointerException exp) {
            	//exp.printStackTrace();
            }catch(JsonSyntaxException exp) {
            	extentTest.fail(MarkupHelper.createLabel(getJSONResponse.toString(), ExtentColor.PINK));
            }
            
            if (storeJsonResponse != null) {
            	String getTagElm ="";
            	boolean verifyStatus= true;
            	
            	for (Entry<Object, List<Object>> jsonTagElm: storeJsonResponse.entrySet()) {
	                jsonTagExp = jsonTagElm.getValue().get(0).toString();
	                jsonTagAct = jsonTagElm.getValue().get(1).toString();
	                
	                if(jsonTagExp.toString().contentEquals("json~elm~not~found")) {
	                	verifyStatus =false;
	                	getTagElm = getTagElm + "<span style=\"color:#33b5ff;\">" 
	                	+jsonTagElm.getKey() +"</span>" +": Expected JSON element &lt;" +"<span style=\"color:#ff4f33;\">"
	                	+jsonTagElm.getKey() +"</span>"+"&gt; not found </span>" +"<br>";
	                }else if(jsonTagExp.trim().toLowerCase().contentEquals(jsonTagAct.toString().toLowerCase())) {
	                	getTagElm = getTagElm + "<span style=\"color:#33b5ff;\">" +jsonTagElm.getKey() 
	                	+"</span>" +": Expected &lt;" +"<span style=\"color:#33ff42;\">"
	                	+jsonTagExp +"</span>"+"&gt; Actual &lt;" +"<span style=\"color:#33ff42;\">"+ jsonTagAct+ "</span>"+"&gt;<br>";
	                }else {
	                	verifyStatus =false;
	                	getTagElm = getTagElm + "<span style=\"color:#33b5ff;\">" 
	                	+jsonTagElm.getKey() +"</span>" +": Expected &lt;" +"<span style=\"color:#33ff42;\">"
	                	+jsonTagExp +"</span>"+"&gt; Actual &lt;" +"<span style=\"color:#ff4f33;\">"+ jsonTagAct+ "</span>"+"&gt;<br>";
	                }
            	}
            	if(verifyStatus ==true)
            		extentTest.pass(MarkupHelper.createLabel("JSON Response Element Verification:", ExtentColor.TEAL).getMarkup().concat("<br>"+ getTagElm));
            	else
            		extentTest.fail(MarkupHelper.createLabel("JSON Response Element Verification:", ExtentColor.TEAL).getMarkup().concat("<br>"+ getTagElm));
            }

            if (getRequestActualStatus != null) {
            	JSONObject resStatus = jsonStatusObject(getRequestExpStatus.toString(), getRequestActualStatus.toString());
            	String getExpStatus =getRequestExpStatus.toString();
            	String getActStatus =getRequestActualStatus.toString();
            	
            	if (getRequestExpStatus.toString().contentEquals(getRequestActualStatus.toString()) &&
            		jsonTagStatus == true &&
                    jsonRespTagStatus == true &&
                    jsonTagFnd == true &&
                    dbValidtionStatus == true) {
            		extentTest.pass(MarkupHelper.createLabel("Status:", ExtentColor.TEAL).getMarkup().
            				concat("<br> <span style=\"color:#33b5ff;\">Expected Status</span>: "+"<span style=\"color:#33ff42;\">"+getExpStatus+"</span>"+"<br> <span style=\"color:#33b5ff;\">Actual Status</span>: "+"<span style=\"color:#33ff42;\">"+getActStatus+"</span>"));
            		//extentTest.pass(MarkupHelper.createCodeBlock(resStatus.toString(),CodeLanguage.JSON));
                    textResonseCodeColor = responseCodeTextColorSuccess;
                } else if (getRequestExpStatus.toString().contentEquals(getRequestActualStatus.toString())
                    && jsonTagStatus == false
                    || jsonRespTagStatus == false
                    || jsonTagFnd == false
                    || dbValidtionStatus == false) {
                	extentTest.fail(MarkupHelper.createLabel("Status:", ExtentColor.TEAL).getMarkup().
            				concat("<br> <span style=\"color:#33b5ff;\">Expected Status</span>: "+"<span style=\"color:#33ff42;\">"+getExpStatus+"</span>"+"<br> <span style=\"color:#33b5ff;\">Actual Status</span>: "+"<span style=\"color:#ff4f33;\">"+getActStatus+"</span>"));
                		//extentTest.fail(MarkupHelper.createCodeBlock(resStatus.toString(),CodeLanguage.JSON));
                        textResonseCodeColor = responseCodeTextColorDanger;
                }else {
                	extentTest.fail(MarkupHelper.createLabel("Status:", ExtentColor.TEAL).getMarkup().
            				concat("<br> <span style=\"color:#33b5ff;\">Expected Status</span>: "+"<span style=\"color:#33ff42;\">"+getExpStatus+"</span>"+"<br> <span style=\"color:#33b5ff;\">Actual Status</span>: "+"<span style=\"color:#ff4f33;\">"+getActStatus+"</span>"));
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

