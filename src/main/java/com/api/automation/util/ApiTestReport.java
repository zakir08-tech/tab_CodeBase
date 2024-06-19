package com.api.automation.util;
 
import com.automation.bolt.common;
import com.automation.bolt.constants;
import com.api.automation.bolt.loadAPITestRunner;
import com.api.automation.bolt.API_TestRunner;
 
import java.awt.*;
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
 
import static com.api.automation.bolt.boltApiExecutor.endRunDateTime;
import static com.api.automation.bolt.boltApiExecutor.startRunDateTime;
 
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
 
    public static void GenerateApiTestReport() {
 
	    String getHtmlTable = "\n";
	    String getHtmlDiv = "\n";
	    String htmlDiv = "\n";
	    String htmlSummaryDiv = "\n";
	    String htmlTestFile;
	    int intFail = 0;
	    int intPass = 0;
	    String bttnColor = null;
	    //String getExecutionRunTime = API_TestRunner.executionTime;
	    String getExecutionRunTime = common.getTestrunTime(startRunDateTime, endRunDateTime);
	    HashMap<Object, List<Object>> storeJsonResponse = new HashMap<>();
	    LinkedHashMap<Object, Object> getAPiVerifyTags = new LinkedHashMap<>();
	    Object jsonTagName = null;
	    String jsonTagExpectedVal = null;
	    String jsonTagActualVal = null;
	    String getSqlQuerySummary = null;
	    String getSqlQuerySummaryTxt = null;
	    boolean jsonTagStatus;
	    boolean jsonRespTagStatus;
	    boolean dbValidtionStatus;
	    boolean jsonTagFnd;
	    String getReqType = null;
	    int dbSqlPst;
	    String getElmPst = null;
 
        if(!new File(constants.userDir+"/htmlTestReport").exists()){
        	new File(constants.userDir+"/htmlTestReport").mkdir();
        }
 
        APITestResultPath =constants.userDir+"/htmlTestReport/";
        //APITestResultPath = LoadProperties.prop.getProperty(Constants.APITestResultFilePathPropName);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy hh:mm a");
 
        for (Entry<Object, HashMap<Object, Object>> entryReport: loadAPITestRunner.ApiTestRunnerMap.entrySet()) {
            Object getRunID = entryReport.getValue().get("Run ID");
            Object modifiedRunID =getRunID.toString().replaceAll("[.]","_");
 
            Object getRequest = entryReport.getValue().get("Request");
            Object getRequestUrl = entryReport.getValue().get("Request URL");
            Object getRequestExpStatus = entryReport.getValue().get("Expected Status");
            Object getRequestActualStatus = entryReport.getValue().get("Actual Status");
            Object getJSONResponse = entryReport.getValue().get("JSON Response");
            Object getJSONResponseError =entryReport.getValue().get("JSON Response");
            Object getAPISummary = entryReport.getValue().get("Test Summary");
            dbSqlPst = 0;
            String getHeadersDetails = "";
	 
            HashMap<Object, Object> headerMapNew = loadAPITestRunner.ApiTestRunnerMap.get(getRunID);
            try{
            	for (Entry<Object, Object> jsonTagNotFnd: headerMapNew.entrySet()) {
            		getHeadersDetails = getHeadersDetails + jsonTagNotFnd.getKey() +": "+ jsonTagNotFnd.getValue() + "\r\n";
                }
            }catch (NullPointerException exp){
            	//exp.printStackTrace();
            }
 
            if(getHeadersDetails.isEmpty()){
            	getHeadersDetails = "No headers define for the given API request";
            }
 
            try {
            	getJSONResponse = getJSONResponse.toString().replaceAll("<", "&lt;").replaceAll(">", "&gt;");
            } catch (NullPointerException exp) {
            	//exp.printStackTrace();
            }
 
            Object getJSONPayload = entryReport.getValue().get("Payload");
            Object httpStatusCodeDes = entryReport.getValue().get("Status Code Phrase");
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
            String jsonTagElmTxt = "";
            String jsonTagElmSummary = "";
            String jsonTagElmNotFndSummary = "";
            htmlDiv = "";
            htmlSummaryDiv = "";
            getSqlQuerySummary = "";
            getSqlQuerySummaryTxt = "";
 
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
 
            LinkedHashMap<Object, Object> getTagElm = loadAPITestRunner.saveTagElmMap.get(getRunID);
            if (getTagElm != null) {
            	for (Entry<Object, Object> jsonTagElm: getTagElm.entrySet()) {
	                jsonTagExp = (String) jsonTagElm.getKey();
	                jsonTagAct = jsonTagElm.getValue();
                    if (jsonTagAct.toString().contains("_RefFnd_")) {
                    	jsonTagElmTxt = "Required reference Tag [<font color=\"red\"><b><i>" + jsonTagAct.toString().split("_RefFnd_")[0] + "</i></b></font>] " +
                    					"not found in test run [<font color=\"red\"><b><i>" + jsonTagAct.toString().split("_RefFnd_")[1].split("#")[1] + "</i></b></font>] response body!" + "\n";
                        jsonTagElmSummary = jsonTagElmSummary + jsonTagElmTxt;
                        jsonRespTagStatus = false;
                    }
                }
            }
 
            getTagElm = loadAPITestRunner.saveTagElmMap.get(getRunID);
            if (getTagElm != null) {
            	for (Entry<Object, Object> jsonTagNotFnd: getTagElm.entrySet()) {
	                jsonTagExp = (String) jsonTagNotFnd.getKey();
	                jsonTagAct = jsonTagNotFnd.getValue();
	                if (jsonTagAct.toString().contains("#.")) {
	                	jsonTagElmTxt = "Required Tag [<font color=\"red\"><b><i>" + jsonTagExp + "</i></b></font>] " +
                                        "not found in payload body!" + "\n";
                        jsonTagElmNotFndSummary = jsonTagElmNotFndSummary + jsonTagElmTxt;
                        jsonTagFnd = false;
                    }
                }
            }
 
            /*LinkedHashMap<Object, Object> getDbValidations = loadAPITestRunner.saveDbValidationMap.get(Integer.parseInt((String) getRunID));
	            Object getTagElmTxt = null;
	            String labelTxt = null;
 
                if (!getDbValidations.isEmpty()) {
                	for (Entry<Object, Object> dbEntry: getDbValidations.entrySet()) {
	                    String getDbKey = (String) dbEntry.getKey();
	                    Object expDbValue = dbEntry.getValue();
 
                        if (getDbKey.contains(Constants.SqlQuery_COLUMN_NAME)) {
	                        dbSqlPst++;
	                        getElmPst = getDbKey.split("_")[1];
	                        Object getDbActual = entryReport.getValue().get("dbActualData" + "_" + getElmPst);
	                        Object getDbExpected = entryReport.getValue().get("dbExpectedData" + "_" + getElmPst);
 
                            if (getDbActual != null) {
	                            if (entryReport.getValue().get("Json Request Tag_" + getElmPst) == null) {
	                                labelTxt = "";
	                                getTagElmTxt = getDbValidations.get("SQL Query_" + getElmPst).toString();
                                } else {
	                                labelTxt = "JSON Element ";
	                                getTagElmTxt = entryReport.getValue().get("Json Request Tag_" + getElmPst);
                                }
 
                                if (getDbExpected.toString().toLowerCase().contentEquals(getDbActual.toString().toLowerCase())) {
	                                getSqlQuerySummaryTxt = "<tr class=\"warning\"><td><font color=\"\"><b><i>" + dbSqlPst + "</i></b></font></td>" +
	                                                        "\n<td>" + labelTxt + "{<font color=\"blue\">" + getTagElmTxt + "</font>} data [<font color=\"green\"><b><i>" + getDbExpected +
	                                                        "</i></b></font>]</td>\n<td>DB field {<font color=\"blue\">" + entryReport.getValue().get("Db Column Name_" + getElmPst) + "</font>} data [<font color=\"green\"><b><i>" +
	                                                        getDbActual + "</i></b></font>]</td></tr>" + "\n";
                                } else {
	                                if (getDbExpected.toString().contentEquals("#.")) {
                                    	getDbExpected = "<font color=\"red\"><i>info: required JSON payload Element not found!</i></font>";
	                                }
                                getSqlQuerySummaryTxt = "<tr class=\"danger\"><td><font color=\"\"><b><i>" + dbSqlPst + "</i></b></font></td>" +
                                                        "\n<td>" + labelTxt + "{<font color=\"blue\">" + getTagElmTxt + "</font>} data [<font color=\"green\"><b><i>" + getDbExpected +
                                                        "</i></b></font>]</td>\n<td>DB field {<font color=\"blue\">" + entryReport.getValue().get("Db Column Name_" + getElmPst) + "</font>} data [<font color=\"red\"><b><i>" +
                                                        getDbActual + "</i></b></font>]</td></tr>" + "\n";
                                dbValidtionStatus = false;
                            	}
	                        } else if (getDbActual == null) {
	                        	getSqlQuerySummaryTxt = "SQL " + dbSqlPst + ": " + expDbValue + "\n<font color=\"red\"><b><i>given query didn't get executed!</i></b></font>" + "\n";
	                        }
                        	getSqlQuerySummary = getSqlQuerySummary + "\n" + getSqlQuerySummaryTxt;
                    	}
                	}
                }*/
 
                if (getRequestActualStatus != null) {
                	if (getRequestExpStatus.toString().contentEquals(getRequestActualStatus.toString()) &&
	                    jsonTagStatus == true &&
	                    jsonRespTagStatus == true &&
	                    jsonTagFnd == true &&
	                    dbValidtionStatus == true) {
                        bttnColor = bttnColorSuccess;
                        textResonseCodeColor = responseCodeTextColorSuccess;
                        intPass++;
                    } else if (getRequestExpStatus.toString().contentEquals(getRequestActualStatus.toString())
	                    && jsonTagStatus == false
	                    || jsonRespTagStatus == false
	                    || jsonTagFnd == false
	                    || dbValidtionStatus == false) {
	                        bttnColor = bttnColorDanger;
	                        textResonseCodeColor = responseCodeTextColorDanger;
	                        intFail++;
                    }else {
			                bttnColor = bttnColorDanger;
			                textResonseCodeColor = responseCodeTextColorDanger;
			                intFail++;
                    }
                } else {
	                bttnColor = bttnColorDanger;
	                textResonseCodeColor = responseCodeTextColorDanger;
	                intFail++;
                }
 
                if (getAPISummary == null)
                	getAPISummary = "no summary define!";
 
                    String htmlTable = "                                                                   <tr>" +
	                    "                                                                                   <td><b><font size=\"1.5\">" + getRunID + "</font></b></td>" +
	                    "                                                                                   <td><i><font size=\"1\" color=\"brown\">" + getAPISummary + "</font></i></td>" +
	                    "                                                                                   <td data-toggle=\"collapse\" data-target=\"#demo" + modifiedRunID + "\"><button type=\"button\" class=\"" + bttnColor + "\" onclick=\"scrollWin()\"><b>" + getRequest + "</b></button></td>\r\n" +
	                    "                                                                                   <td data-container=\"body\" data-toggle=\"tooltip\" title=\"Expected Status:" + getRequestExpStatus + "\"><font size=\"1.5\">" + getRequestUrl + "</font></td>\r\n" +
	                    "                                                                                   <td data-toggle=\"tooltip\" data-container=\"body\" title=\"" + httpStatusCodeDes + "\"><b class=\"" + textResonseCodeColor + "\"><font size=\"1.5\">" + getRequestActualStatus + "</font></b></td>\r\n" +
	                    "                                                                       </tr>";
 
                    htmlSummaryDiv = "                                          <div id=\"demo" + modifiedRunID + "\" class=\"collapse\">\r\n" +
                    					"                                                           <pre style=\"background-color:#C0C0C0;\">[Test Run:<b class=\"text-danger\">" + getRunID + "</b>] <font class=\"text-success\">" +getAPISummary+ "</font></pre>\r\n";
                                /*"                                                        <p><b>API Summary:</b></p>\r\n"+
                                "                                                           <pre class=\"text-danger\">" + getAPISummary + "</pre>\r\n";*/
 
                    getReqType = getRequest.toString().trim().toUpperCase();
 
                    if(getRequestActualStatus == null){
                    	getRequestActualStatus = "";
                    }
 
                    if (!getRequestActualStatus.toString().toLowerCase().contentEquals("error")) {
                                htmlDiv = "                                                        <p><b>Request headers:</b></p>\r\n" +
                                			"                                                           <pre class=\"text-danger\">" + getHeadersDetails + "</pre>\r\n";
 
                    if(getJSONPayload !=null && !getJSONPayload.toString().isEmpty()){
                    	htmlDiv = htmlDiv+ "                                                      <p><b>JSON payload:</b></p>\r\n" +
                    						"                                                           <pre class=\"text-danger\">" + getJSONPayload + "</pre>\r\n";
                    }
 
                    if(getJSONResponse !=null && !getJSONResponse.toString().isEmpty()){
                                htmlDiv = htmlDiv+ "                                                      <p><b>JSON response:</b></p>\r\n" +
                                                        "                                                           <pre class=\"text-danger\">" + getJSONResponse + "</pre>\r\n";
                    }
 
                    /*if (getReqType.contentEquals("GET")) {
                    	htmlDiv = htmlDiv+ "                                                      <p><b>JSON response:</b></p>\r\n" +
                                                                                    "                                                           <pre class=\"text-danger\">" + getJSONResponse + "</pre>\r\n";
 
                    } else if (getReqType.contentEquals("POST") || getReqType.contentEquals("PUT")) {
                                htmlDiv = htmlDiv+ "                                                      <p><b>JSON payload:</b></p>\r\n" +
                                                        "                                                           <pre class=\"text-danger\">" + getJSONPayload + "</pre>\r\n" +
                                                        "                                                           <p><b>JSON response:</b></p>\r\n" +
                                                        "                                                           <pre class=\"text-danger\">" + getJSONResponse + "</pre>\r\n";
                    }*/
 
                    } else {
	                    htmlDiv = "                                                        <p><b>Response Error:</b></p>\r\n" +
	                                            "                                                           <pre class=\"text-danger\">" + getJSONResponseError + "</pre>\r\n";
                    }
 
                    if (jsonTagFnd == false) {
	                    htmlDiv = htmlDiv + "                                                     <p><b>JSON payload tag(s) to modify not found:</b></p>\r\n" +
	                                            "                                                           <pre class=\"text-danger\">" + jsonTagElmNotFndSummary + "</pre>\r\n";
                    }
 
                    if (jsonRespTagStatus == false) {
	                    htmlDiv = htmlDiv + "                                                     <p><b>JSON reference response tag(s) not found:</b></p>\r\n" +
	                                            "                                                           <pre class=\"text-danger\">" + jsonTagElmSummary + "</pre>\r\n";
                    }
 
                    /*if (!getDbValidations.isEmpty()) {
                                htmlDiv = htmlDiv + "                                                     <p><b>SQL database validation:</b></p>\r\n" +
                                                        "                                                           <pre class=\"text-danger\">\r\n" + "\r\n<table class=\"table table-bordered\"><tbody>\r\n" +
                                                        getSqlQuerySummary + "</tbody></table></pre>\r\n";
                    }*/
 
                    if (storeJsonResponse != null) {
                    	htmlDiv = htmlDiv + "                                                     <p><b>JSON response tag(s) verification:</b></p>\r\n" +
                                                        "                                                           <pre class=\"text-danger\">" + jsonTagSummary + "</pre>\r\n" +
                                                        "                                               </div>";
                    } else if (getReqType.contentEquals("POST") ||
	                    getReqType.contentEquals("GET") ||
	                    getReqType.contentEquals("PUT") ||
	                    getReqType.contentEquals("NA")) {
                        	htmlDiv = htmlDiv + "                                         </div>";
                    }
 
                    getHtmlTable = getHtmlTable + htmlTable;
                    getHtmlDiv = getHtmlDiv + htmlSummaryDiv + htmlDiv;
                }
 
                String htmlType = "<!DOCTYPE html>  \r\n" +
                                        "<html lang=\"en\">";
                String htmlHeader = "    <head>\r\n" +
                                        "                       <title>Bolt: API Test Report</title>  \r\n" +
                                        "                                   <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\"/>\r\n" +
                                        "                                   <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js\"></script>  \r\n" +
                                        "                                   <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js\"></script> \r\n" +
                                        "     <script src='https://kit.fontawesome.com/a076d05399.js'></script> \r\n" +
                                        "                                   <link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.7.0/css/all.css' integrity='sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ' crossorigin='anonymous'>" +
                                        "                                   \r\n" +
                                        "                       <style>\r\n" +
                                        "                                   #panelPanelPrimary {\r\n" +
                                        "                                   height: 100%;\r\n" +
                                        "                                   width: 100%;\r\n" +
                                        "                                   }\r\n" +
                                        "                       </style>\r\n" +
                                        "                       <style>\r\n" +
                                        "                                   .tooltip-inner {\r\n" +
                                        "                                   color:yellow;\r\n" +
                                        "                                   }\r\n" +
                                        "                       </style>\r\n" +
                                        "                       <style>\r\n" +
                                        "                                   #scrollable {\r\n" +
                                        "                                   height: 280px;\r\n" +
                                        "                                   width: 100%;\r\n" +
                                        "                                   overflow: scroll;\r\n" +
                                        "                                   }\r\n" +
                                        "                       </style>\r\n" +
                                        "                       <style>\r\n" +
                                        "                                   body {\r\n" +
                                        "                                   height: 1030px;\r\n" +
                                        "                                   }\r\n" +
                                        "                       </style>\r\n" +
                                        "                       <style>\r\n" +
                                        "                                   #myBtn {\r\n" +
                                        "                                     display: none;\r\n" +
                                        "                                     position: fixed;\r\n" +
                                        "                                     bottom: 45px;\r\n" +
                                        "                                     right: 35px;\r\n" +
                                        "                                     font-weight: bold;" +
                                        "                                     z-index: 99;\r\n" +
                                        "                                     font-size: 20px;\r\n" +
                                        "                                     border: none;\r\n" +
                                        "                                     outline: none;\r\n" +
                                        "                                     background-color: red;\r\n" +
                                        "                                     color: white;\r\n" +
                                        "                                     cursor: pointer;\r\n" +
                                        "                                     padding: 5px;\r\n" +
                                        "                                     border-radius: 5px;\r\n" +
                                        "                                   }\r\n" +
                                        "                                   #myBtn:hover {\r\n" +
                                        "                                     background-color: #428bca;\r\n" +
                                        "                                   }\r\n" +
                                        "                       </style>\r\n" +
                                        "                       <style>\r\n" +
                                        "                                   @keyframes example {\r\n" +
                                        "                                   0% {color: red;}\r\n" +
                                        "                                   100% {color: white;}\r\n" +
                                        "                       }\r\n" +
                                        "                       </style>\r\n" +
                                        "           </head>\r\n";
 
                String htmlbody1 = "<body>  \r\n" +
                                        "           <button onclick=\"topFunction()\" id=\"myBtn\" class=\"btn btn-default\">" +
                                        "                       <span class=\"glyphicon glyphicon-triangle-top\"></span></button> \r\n" +
                                        "           <div class=\"container-fluid\" id=\"containerFluid\"><h4><b>Bo<i class=\"fa fa-bolt\" style=\"font-size:15px;animation-name:example; position:relative; animation-duration:2s; animation-iteration-count:infinite\"></i>t</b><br><font size=\"2\" color=\"#FF0000\">API Test Accelerator</font></h4> \r\n" +
                                        "                       <div class=\"panel panel-primary\" id=\"panelPanelPrimary\">\r\n" +
                                        "                                   <div class=\"panel-heading\">\r\n" +
                                        "                                               <div>\r\n" +
                                        "                                               <div class=\"panel-title pull-left\" style=\"white-space: pre-wrap; font-family: Consolas\"><h3 style=\"color:yellow\" class=\"panel-title\"><Strong>API Test Report&#10;</Strong></h3>Dated: " +"<span class=\"label label-warning\">" +dateFormat.format(new Date()).toLowerCase() +"</span>" +"&#10;</h3>Execution Run: " +"<span class=\"label label-warning\">" +getExecutionRunTime +"</span>" +"<h3></div>\r\n" +
                                        "                                               <div class=\"panel-title pull-right\" style=\"white-space: pre-wrap; font-family: Consolas\"><h3 style=\"color:yellow\" class=\"panel-title\"><Strong>Execution status&#10;</Strong></h3>Failed API:<b>" + "<span class=\"label label-danger\">" +intFail +"</span>" +"</b>&#10;Passed API:<b>" +"<span class=\"label label-success\">" +intPass +"</span>" +"</b></div><br><br></br>\r\n" +
                                        "                                               </div>\r\n" +
                                        "                                   </div>\r\n" +
                                        "                                   \r\n" +
                                        "                                   <div class=\"panel-body\">\r\n" +
                                        "                                               <div class=\"scrollable\" id=\"scrollable\">\r\n" +
                                        //                                                          <table class=\"table table-striped table-bordered table-fixedheader\">\r\n" +
                                        "                                                           <table class=\"table table-striped table-bordered\">\r\n" +
                                        "                                                                       <thead>\r\n" +
                                        "                                                                       <tr>\r\n" +
                                        "                                                                                   <th><font size=\"1.5\">Run<font color=\"white\">_</font>ID</font></th>\r\n" +
                                        "                                                                                   <th><font size=\"1.5\">Test<font color=\"white\">_</font>Description</font></th>\r\n" +
                                        "                                                                                   <th><font size=\"1.5\">Request</font></th>\r\n" +
                                        "                                                                                   <th><font size=\"1.5\">Request<font color=\"white\">_</font>URL</font></th>\r\n" +
                                        "                                                                                   <th><font size=\"1.5\">Response<font color=\"white\">_</font>Code</font></th>\r\n" +
                                        "                                                                       </tr>\r\n" +
                                        "                                                                       </thead>\r\n" +
                                        "\r\n" +
                                        "                                                           <tbody>";
 
                String htmlBody2 = "                                                      </tbody>\r\n" +
                                        "                                                           \r\n" +
                                        "                                                           </table>\r\n" +
                                        "                                               </div>                           \r\n" +
                                        "                                   </div>\r\n" +
                                        "                                   \r\n" +
                                        "                                   <div class=\"p-3 mb-2 bg-primary\">\r\n" +
                                        "                                               <marquee behavior=\"scroll\" direction=\"left\" scrollamount=\"2\">\r\n" +
                                        "                                                           <font color=\"yellow\" face=\"Consolas\">Click request button to view API Request-Response summary!</font>\r\n" +
                                        "                                               </marquee>\r\n" +
                                        "                                   </div>\r\n" +
                                        "                       </div>\r\n" +
                                        "                       \r\n" +
                                        "                       <div class=\"panel panel-primary\">\r\n" +
                                        "                       <div class=\"p-3 mb-2 bg-primary\">\r\n" +
                                        "                                   <marquee behavior=\"scroll\" direction=\"left\" scrollamount=\"2\">\r\n" +
                                        "                                               <font color=\"yellow\" face=\"Consolas\"><b>API Test Summary</b></font>\r\n" +
                                        "                                   </marquee></div>\r\n" +
                                        "                                   <div class=\"panel-footer\"> \r\n";
 
                String htmlBody3 = "                              </div>\r\n" +
                                        "                       </div>\r\n" +
                                        "           </div>\r\n" +
                                        "           \r\n" +
                                        "           <script>\r\n" +
                                        "                       $(document).ready(function(){\r\n" +
                                        "                       $('[data-toggle=\"tooltip\"]').tooltip();   \r\n" +
                                        "                       });\r\n" +
                                        "           </script>\r\n" +
                                        "           <script>\r\n" +
                                        "                       function scrollWin() {\r\n" +
                                        "                       window.scrollTo(0,1000);\r\n" +
                                        "                       }\r\n" +
                                        "           </script>\r\n" +
                                        "           <script>\r\n" +
                                        "                       function topFunction() {\r\n" +
                                        "                       window.location.href=window.location.href\r\n" +
                                        "                       document.body.scrollTop = 0;\r\n" +
                                        "                       document.documentElement.scrollTop = 0;\r\n" +
                                        "                       }\r\n" +
                                        "           </script>\r\n" +
                                        "           <script>\r\n" +
                                        "                       window.onscroll = function() {scrollFunction()};\r\n" +
                                        "                       function scrollFunction() {\r\n" +
                                        "                         if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {\r\n" +
                                        "                                   document.getElementById(\"myBtn\").style.display = \"block\";\r\n" +
                                        "                         } else {\r\n" +
                                        "                                   document.getElementById(\"myBtn\").style.display = \"none\";\r\n" +
                                        "                         }\r\n" +
                                        "                       }\r\n" +
                                        "           </script>" +
                                        "           <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js\"></script>  \r\n" +
                                        "           <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js\"></script>\r\n" +
                                        "           </body>  \r\n" +
                                        "</html>";
 
            htmlTestFile = htmlType + htmlHeader + htmlbody1 + getHtmlTable + htmlBody2 + getHtmlDiv + htmlBody3;
 
	        Date date = new Date();
	        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMMM-yyyy hh:mm:ss a");
            String strDate = formatter.format(date).toLowerCase();
            uniqueID = strDate.replaceAll(":", "-");
 
            TestAutomationReport = APITestResultPath + "BoltApiTestReport " + uniqueID + ".html";
            saveTestReport(htmlTestFile);
 
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
}

