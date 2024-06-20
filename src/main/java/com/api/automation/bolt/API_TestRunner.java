package com.api.automation.bolt;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.api.automation.util.ApiTestReport;
import com.api.automation.util.ConvertXmlToJson;
import com.api.automation.util.GetTagValueFromJsonResponse;
import com.api.automation.util.ReadPayloadFile;
import com.api.automation.util.SaveResponseAsFile;
import com.api.automation.util.UpdateJsonPayload;
import com.api.automation.util.VerifyTagValueFromJsonResponse;
import com.api.automation.util.VerifyValueAPICommon;
import com.automation.bolt.gui.ExecuteApiTest;

public class API_TestRunner extends loadAPITestRunner {

	static Object getApiTestRunId;
	static CloseableHttpResponse closeableHttpRespone;
	static Object requestPayload;
	static String requestPayloadType;
	static JSONArray jsonResponse;
	static JSONObject jsonXmlResponse;
	static Object retrieveResponse;
	static Object getApiTestRequest;
	static Object getApiTestRequestUrl;
	static Object getExpResponseCode;
	static Object getSSLCertificationFlag;
	static Object getBasicAuthFlag;

    public static Object getAuth1;
    public static Object getAuth2;
    static HttpClient restClient;
    static String sqlQuery;
    static String sqlQueryData;
    static String expSqlOutput;

    static HashMap<Object, Object> testOut_Put = new HashMap<> ();
    static LinkedHashMap<Object, Object> requestHeaders = new LinkedHashMap<> ();
    static LinkedHashMap<Object, Object> payloadTagElement = new LinkedHashMap<> ();
    static LinkedHashMap<Object, Object> responseDbValidation = new LinkedHashMap<> ();
    static LinkedHashMap<Object, Object> verifyResponseTagElement = new LinkedHashMap<> ();

    public static LinkedHashMap<Object, HashMap<Object, List<Object>>> verifyJsonResponseAttributes = new LinkedHashMap<> ();
    public static LinkedHashMap<Object, HashMap<Object, List<Object>>> modifyJsonResponseAttributes = new LinkedHashMap<> ();
    static String getPayloadFilePath;
    static Object getPayloadType;
    public static String executionTime;
    public static int getCurrRunId;
    public static boolean finalRunStatus;
    public static boolean getThreadStatus;

    public static void runAPItest() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
    	System.out.println("running api's test!");
    	
        restClient = new HttpClient();
        boolean blnJSONresponse;
        boolean blnXmlresponse;
        boolean blnNoResponse;

        ResponsePOST getPostResponse = new ResponsePOST();
        ResponsePUT getPutResponse = new ResponsePUT();
        ResponseGET getGetResponse = new ResponseGET();
        SaveResponseAsFile saveResponse = new SaveResponseAsFile();

        String getErrorDesc = null;
        String getErrorMsg = null;
        String getHtmlResponse = null;
        /*String getFileExtension = null;
        Object newSqlQuery = null;
        Object newSqlExpOutPut = null;
        Object getQueryData = null;
        String getKey = null;
        Object expValue = null;
        String getDbColName = null;
        String getJsonRespstatusCodeValue = null;
        String getJsonRespstatusCode = null;*/

        long startTime = System.currentTimeMillis();
        verifyJsonResponseAttributes = new LinkedHashMap<> ();

        for (Entry<Object, HashMap<Object, Object>> testRunnerEntry: ApiTestRunnerMap.entrySet()) { //for:1
            
        	finalRunStatus =true;
			getApiTestRunId = testRunnerEntry.getKey(); //get  test run id
			getCurrRunId =0;
			 for(int i=0; i<ExecuteApiTest.importDataFromExcelModel.getRowCount(); i++){
			     if(ExecuteApiTest.importDataFromExcelModel.getValueAt(i, 1).toString().contentEquals(getApiTestRunId.toString())){
			         getCurrRunId =i;
			         break;
			     }
			 }
			
			ExecuteApiTest.tableExecuteRegSuite.setRowSelectionInterval(getCurrRunId, getCurrRunId);            
	    	ExecuteApiTest.tableExecuteRegSuite.scrollRectToVisible(ExecuteApiTest.tableExecuteRegSuite.getCellRect(ExecuteApiTest.tableExecuteRegSuite.getSelectedRow(), 0, true));
	    	ExecuteApiTest.tableExecuteRegSuite.requestFocus();
	    	ExecuteApiTest.importDataFromExcelModel.setValueAt("Running...", getCurrRunId, 3);
        	
	    	blnJSONresponse = false;
	        blnXmlresponse = false;
	        blnNoResponse = false;
	        closeableHttpRespone = null;
	        requestPayload = null;
	        requestPayloadType = null;
	        getSSLCertificationFlag =null;
	        getBasicAuthFlag =null;
            //getDbColName = "";

            HttpClient.httpCloseableResponse = null;
            ReadPayloadFile.readPayload = null;
            ResultSet getQueryRes = null;

            getApiTestRequest = testRunnerEntry.getValue().get("Request"); //get Request type
            getApiTestRequestUrl = testRunnerEntry.getValue().get("Request URL"); //get service url
            getExpResponseCode = testRunnerEntry.getValue().get("Expected Status"); //get response code
            getAuth1 =testRunnerEntry.getValue().get("AuthVal1"); //get auth value 1
            getAuth2 =testRunnerEntry.getValue().get("AuthVal2"); //get auth value 2


            testOut_Put = ApiTestRunnerMap.get(getApiTestRunId);
            requestHeaders = loadAPITestRunner.saveHeaderMap.get(getApiTestRunId); //get Headers
            payloadTagElement = loadAPITestRunner.saveTagElmMap.get(getApiTestRunId); //get payload tag/element
            getSSLCertificationFlag = testRunnerEntry.getValue().get("SSL Verification"); //get ssl certification flag

            if(getSSLCertificationFlag ==null)
            	getSSLCertificationFlag ="";

            getBasicAuthFlag = testRunnerEntry.getValue().get("Authorization"); //get ssl certification flag
            if(getBasicAuthFlag.toString().contentEquals("Bearer Token")){
	            updateValueFromJsonResponse(getApiTestRunId, getAuth1,"AuthVal1");
	            getAuth1 =testRunnerEntry.getValue().get("AuthVal1");

                if(requestHeaders ==null){
					requestHeaders =new LinkedHashMap<> ();
					requestHeaders.put("Authorization","Bearer "+getAuth1);
					loadAPITestRunner.saveHeaderMap.put(getApiTestRunId, requestHeaders);
                }else
                	requestHeaders.put("Authorization","Bearer "+getAuth1);
            	}

                if(getBasicAuthFlag ==null)
                	getBasicAuthFlag ="";

                verifyResponseTagElement = loadAPITestRunner.saveVerifyRespTagElmMap.get(getApiTestRunId); //get payload tag/element
                //responseDbValidation = loadAPITestRunner.saveDbValidationMap.get(getApiTestRunId); //get database validation items

                System.out.println("\nExecuting Test Run: " + getApiTestRunId);
                requestPayload =testRunnerEntry.getValue().get("Payload");
                getPayloadType = testRunnerEntry.getValue().get("Payload Type"); //get payload type

                if (payloadTagElement !=null) {
                    requestPayload =testRunnerEntry.getValue().get("Payload");
                    requestPayload =UpdateJsonPayload.UpdateJsonTagElement(getApiTestRunId, payloadTagElement, requestPayload.toString());
                }

                if (requestPayload != null){
                	if(getPayloadType.toString().toLowerCase().contains("urlencoded"))
                		requestPayload = ReadPayloadFile.readEncodedPayloadFromFile((String) requestPayload);
                }

                if (getApiTestRequest.toString().trim().toUpperCase().contentEquals("GET")) { //if:1; GET call
                	if (requestHeaders ==null)
                		closeableHttpRespone = restClient.getClientResponse(getApiTestRequestUrl, getSSLCertificationFlag, getBasicAuthFlag, getBasicAuthFlag);
                	else {
                		// update header value from previous json response
                		updateHeaderFromJsonResponse(getApiTestRunId);
                		closeableHttpRespone = restClient.getClientResponse(getApiTestRequestUrl, requestHeaders, getSSLCertificationFlag, getBasicAuthFlag); //GET call with headers
                	}

                    if (closeableHttpRespone != null) {
	                    retrieveResponse = getGetResponse.testGetResponse(closeableHttpRespone, getExpResponseCode);
	                    testOut_Put.put(Constants.Run_API_Actual_Status, Integer.toString(ResponseGET.statusCode));
	                    testOut_Put.put(Constants.Response_Status_Phrase, ResponseGET.responsePhrase);

                    } else {
		                    getErrorMsg = (String) VerifyValueAPICommon.errorMapping.get("Error");
		                    testOut_Put.put(Constants.Run_API_Actual_Status, "Error");
		                    testOut_Put.put(Constants.Response_Status_Phrase, getErrorMsg);
		                    finalRunStatus =false;
                    }

                } else if (getApiTestRequest.toString().trim().toUpperCase().contentEquals("POST")) { //POST call
                	if (requestPayload != null) {
	                    try{
                            if (requestHeaders.size() != 0){
                            	updateHeaderFromJsonResponse(getApiTestRunId);
                            }
	                    }catch(NullPointerException exp){}

	                    closeableHttpRespone = restClient.postClientRequest(getApiTestRequestUrl, requestPayload.toString(), requestHeaders, getSSLCertificationFlag, getBasicAuthFlag);
	                }else
	                	closeableHttpRespone = restClient.postClientRequest(getApiTestRequestUrl, requestHeaders, getSSLCertificationFlag, getBasicAuthFlag);
	                	if (closeableHttpRespone != null) {
	                		try {
	                			retrieveResponse = getPostResponse.testPostResponse(closeableHttpRespone, getExpResponseCode);
	                        } catch (ParseException exp) {
	                        	exp.printStackTrace();
	                        }
	
	                        testOut_Put.put(Constants.Run_API_Actual_Status, ResponsePOST.statusCode);
	                        testOut_Put.put(Constants.Response_Status_Phrase, ResponsePOST.responsePhrase);
	
	                	} else {
							getErrorMsg = (String) VerifyValueAPICommon.errorMapping.get("Error");
							testOut_Put.put(Constants.Run_API_Actual_Status, "Error");
							testOut_Put.put(Constants.Response_Status_Phrase, getErrorMsg);
							finalRunStatus =false;
	                	}
                } else if (getApiTestRequest.toString().trim().toUpperCase().contentEquals("PUT")) { //PUT call
                	if (requestPayload != null){
                		if (requestHeaders.size() != 0){
                			updateHeaderFromJsonResponse(getApiTestRunId);
                		}
                		closeableHttpRespone = restClient.putClientRequest(getApiTestRequestUrl, requestPayload.toString(), requestHeaders, getSSLCertificationFlag, getBasicAuthFlag);
                	}
                	else closeableHttpRespone = restClient.putClientRequest(getApiTestRequestUrl, requestHeaders, getSSLCertificationFlag, getBasicAuthFlag);
                		if (closeableHttpRespone != null) {
                			try {
                				retrieveResponse = getPutResponse.testPutResponse(closeableHttpRespone, getExpResponseCode);
                            } catch (ParseException exp) {
                            	exp.printStackTrace();
                            }

                            testOut_Put.put(Constants.Run_API_Actual_Status, Integer.toString(ResponsePUT.statusCode));
                            testOut_Put.put(Constants.Response_Status_Phrase, ResponsePUT.responsePhrase);

                        } else {
	                        getErrorMsg = (String) VerifyValueAPICommon.errorMapping.get("ErrorDesc");
	                        testOut_Put.put(Constants.Run_API_Actual_Status, "Error");
	                        testOut_Put.put(Constants.Response_Status_Phrase, getErrorMsg);
	                        finalRunStatus =false;
                        }
                } else{
	                //testRunMap.put("Request", "NA");
	                testOut_Put.put("Request", "NA");
	                ApiTestRunnerMap.put(getApiTestRunId, testRunMap);
                }

                //storeRequestPayloadToHasMap
                if (closeableHttpRespone != null) {
	                //retrieveResponse = RemoveBracketsFromJSONString.removeBrackets(retrieveResponse);
	                if (VerifyValueAPICommon.isJSONValid(retrieveResponse.toString()) == true) {
	                    try {
	                    	jsonResponse = new JSONArray(retrieveResponse.toString());
	                    } catch (JSONException exp) {
							//exp.printStackTrace();
							if (retrieveResponse.toString().charAt(0) != '[')
								jsonResponse = new JSONArray("[" + retrieveResponse.toString() + "]");
							else jsonResponse = new JSONArray(retrieveResponse.toString());
	                    }

	                    blnJSONresponse = true;
	                } else if (VerifyValueAPICommon.isXMLValid(retrieveResponse.toString()) == true) {
	                    jsonXmlResponse = ConvertXmlToJson.XMLtoJSON(retrieveResponse.toString());
	                    jsonResponse = new JSONArray("[" + jsonXmlResponse + "]");
	                    blnXmlresponse = true;
	                } else {
	                    getHtmlResponse = retrieveResponse.toString().replaceAll("[\\\r\\\n]+", "");
	                    blnNoResponse = true;
	                }

                    if (requestPayload != null) {
                    	testOut_Put.put("Payload", requestPayload);
                    }
                }

                // save the json response to the hash-map
                if (closeableHttpRespone != null && (blnJSONresponse || blnXmlresponse)) {
                	try {
                		try {
                			saveResponse.savingResponseToFile(jsonResponse.toString(Constants.PRETTY_PRINT_INDENT_FACTOR), getApiTestRequest, String.valueOf(getApiTestRunId));
                			if (verifyResponseTagElement !=null) {
                				VerifyTagValueFromJsonResponse.VerifyJsonTagElement(getApiTestRunId, verifyResponseTagElement, jsonResponse.toString(Constants.PRETTY_PRINT_INDENT_FACTOR));
                			}
                        } catch (IOException exp) {
                        	exp.printStackTrace();
                        }
	                } catch (JSONException exp) {
	                	exp.printStackTrace();
	                }
                	
                	testOut_Put.put(Constants.Run_API_JSON_Response, jsonResponse.toString(Constants.PRETTY_PRINT_INDENT_FACTOR));
                } else if (closeableHttpRespone != null && blnNoResponse) {
                	testOut_Put.put(Constants.Run_API_JSON_Response, getHtmlResponse);
                    try {
                    	saveResponse.savingResponseToFile(getHtmlResponse, getApiTestRequest, String.valueOf(getApiTestRunId));
                    } catch (IOException exp) {
                    	exp.printStackTrace();
                    }
                } else {
	                try{
	                    getErrorMsg = (String) VerifyValueAPICommon.errorMapping.get("Error");
	                    getErrorDesc = (String) VerifyValueAPICommon.errorMapping.get("ErrorDesc");
	                    testOut_Put.put(Constants.Run_API_Actual_Status, "Error");
	                    testOut_Put.put(Constants.Response_Status_Phrase, getErrorMsg);
	                    testOut_Put.put(Constants.Run_API_JSON_Response, getErrorDesc);
	                    finalRunStatus =false;
	                }catch(NullPointerException  exp){
	                	exp.printStackTrace();
	                }
                }
                
                if(!testOut_Put.get(Constants.Run_API_Actual_Status).toString().equals(getExpResponseCode))
                	finalRunStatus =false;
                
                verifyAPIHeadersForTestRunStatus();
                
                if(finalRunStatus ==false)
                	ExecuteApiTest.importDataFromExcelModel.setValueAt("FAIL", getCurrRunId, 3);
                else if(finalRunStatus ==true)
                	ExecuteApiTest.importDataFromExcelModel.setValueAt("PASS", getCurrRunId, 3);
                //else
                	//ExecuteApiTest.importDataFromExcelModel.setValueAt("Interrupted!", getCurrRunId, 3);

                //Database validation
                /*if (!responseDbValidation.isEmpty()) {
                	for (Entry<Object, Object> entry: responseDbValidation.entrySet()) {
	                    getKey = (String) entry.getKey();
	                    expValue = entry.getValue();

                            if (getKey.contains(Constants.SqlQuery_COLUMN_NAME)) {
                                newSqlQuery = updateSqlQuery(expValue, payloadTagElement);
                                responseDbValidation.replace(getKey, (String) newSqlQuery);
                            }

                            if (getKey.contains(Constants.ExpOutPut_COLUMN_NAME)) {
	                            if(expValue.toString().charAt(0) == '.'){
									testOut_Put.put("Json Request Tag_"+getKey.toString().split("_")[1], expValue.toString().substring(1));
	                            }

	                            newSqlExpOutPut = updateSqlQueryExpectedOutput(expValue, payloadTagElement, testRunnerEntry);
	                            responseDbValidation.replace(getKey, (String) newSqlExpOutPut);
                            }
				  }

                Connection connectToSqlSrvr = databaseOperations.getSQLServerConnection(
                                        serverName,
                                        serverPort,
                                        dbUserName,
                                        dbUserPassword,
                                        dbName);

                for (Entry<Object, Object> entry: responseDbValidation.entrySet()) {
                    getKey = (String) entry.getKey();
                    expValue = entry.getValue();

                    if (getKey.contains(Constants.SqlQuery_COLUMN_NAME)) {
                    	String getTagPst = getKey.split("_")[1];
						if (connectToSqlSrvr != null) {
							getQueryRes = databaseOperations.executeSQLQuery(connectToSqlSrvr, responseDbValidation.get(getKey));

	                            if (getQueryRes != null) {
	                            	try {
	                                    ResultSetMetaData rsmd = getQueryRes.getMetaData();
	                                    getDbColName = rsmd.getColumnName(1);
	
	                                    if (!getQueryRes.isBeforeFirst()) {
											getQueryData = "Null";
	                                    }
	
	                                    while (getQueryRes.next()) {
	                                    	getQueryData = getQueryRes.getObject(1);
											if(getQueryData == null)
												getQueryData = "Null";
	                                    }
	                                } catch (SQLException | NullPointerException expMsg) {
										System.out.println(expMsg.toString());
	                                }
	
								}
			        }

	                    if (connectToSqlSrvr != null && getQueryRes != null) {
	                        testOut_Put.put("Db Column Name_" +getTagPst,getDbColName);
	                        testOut_Put.put("dbActualData" + "_" + getTagPst, getQueryData.toString());
	                        testOut_Put.put("dbExpectedData" + "_" + getTagPst, (String) responseDbValidation.get(Constants.ExpOutPut_COLUMN_NAME + "_" + getTagPst));
	                    } else if (connectToSqlSrvr == null || getQueryRes == null) {
	                        testOut_Put.put("dbActualData" + "_" + getTagPst, null);
	                        testOut_Put.put("dbExpectedData" + "_" + getTagPst, (String) responseDbValidation.get(Constants.ExpOutPut_COLUMN_NAME + "_" + getTagPst));
	                    }

				    }
                }

	                try {
	                    getQueryRes.close();
	                    connectToSqlSrvr.close();
	                } catch (SQLException | NullPointerException expMsg) {
						System.out.println(expMsg.toString());
	                }

                }*/ //Database validation
                
	        ApiTestRunnerMap.put(getApiTestRunId, testOut_Put);
	        testOut_Put = new HashMap<>();
	        
	        getThreadStatus =ExecuteApiTest.runThread.isInterrupted();
	        if(getThreadStatus ==true) {
	        	ExecuteApiTest.importDataFromExcelModel.setValueAt("Interrupted!", getCurrRunId, 3);
	        	break;
	        }
        } // for:1 ends here
        
        if(getThreadStatus ==true) {
	    	boolean keyFound =false;
	    	Iterator<?> it = ApiTestRunnerMap.entrySet().iterator();
	    	
	    	while (it.hasNext()) {
	    		Entry item = (Entry) it.next();
	    	    if(item.getKey().equals(getApiTestRunId)) {
	    			keyFound =true;
	    		}
	    	    
	    	    if(keyFound ==true) {
	    	    	it.remove();
	    	    }
	    	}
        }
        
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        long minutes = (totalTime / 1000) / 60;
        long seconds = (totalTime / 1000) % 60;

        executionTime = String.format("%02d:%02d mins", minutes, seconds);
        
        //generate test report
        ApiTestReport.GenerateApiTestReport();

        //email test automation report
        //EmailTestReport.emailTestReport();
    }

    public static void updateHeaderFromJsonResponse(Object runID){
		HashMap<String, String> newMap = new HashMap<String, String>();
		String attachText = null;

        for (Entry<Object, Object> updateHeader : requestHeaders.entrySet()) {
	        if(updateHeader.getValue().toString().contains("_RefFnd_")){
	            String splitTagName = updateHeader.getValue().toString().split("_RefFnd_")[0];
	            String getTestId = updateHeader.getValue().toString().split("_RefFnd_")[1].replace("#", "");
	            HashMap<Object, Object> getPrevJsonResponse = ApiTestRunnerMap.get(getTestId);
	            String getJsonResponse = (String) getPrevJsonResponse.get("JSON Response");
	            String getRespTagVal = GetTagValueFromJsonResponse.GetJsonTagElement(splitTagName, getJsonResponse);

                if(getRespTagVal.contentEquals("#.")){
		            System.out.println("required tag [" +splitTagName+ "] not found in the json response");
		            requestHeaders.put(updateHeader.getKey(), "required tag [" +splitTagName+ "] not found in the json response");
                }
                else{
                    if(attachText == null)
                    	requestHeaders.put(updateHeader.getKey(), getRespTagVal.toString());
                    else
                    	requestHeaders.put(updateHeader.getKey(), attachText+getRespTagVal.toString());
                 }
                	
                loadAPITestRunner.saveHeaderMap.put(runID, requestHeaders);

	        }
        }
    }

    public static void updateValueFromJsonResponse(Object runID, Object modifyValue, Object keyName){
	    if(modifyValue.toString().contains("_RefFnd_")){
	        String splitTagName = modifyValue.toString().split("_RefFnd_")[0];
	        Object getTestId = modifyValue.toString().split("_RefFnd_")[1].replace("#", "");
	        HashMap<Object, Object> getPrevJsonResponse = ApiTestRunnerMap.get(getTestId);
	        String getJsonResponse = (String) getPrevJsonResponse.get("JSON Response");
	        Object getRespTagVal = GetTagValueFromJsonResponse.GetJsonTagElement(splitTagName, getJsonResponse);

            if(getRespTagVal.toString().contentEquals("#.")){
            	System.out.println("required tag [" +splitTagName+ "] not found in the json response");
            }
            
            testRunMap.put(keyName, getRespTagVal);
            ApiTestRunnerMap.put(runID, testRunMap);

	    }

    }

    private static Object updateSqlQuery(Object strSqlQuery, LinkedHashMap<Object, Object> mapReadTagValue) {
	    char[] sqlQueryToChar = strSqlQuery.toString().toCharArray();
	    boolean reqCharFnd = false;
	    String getTagName = "";

        for (char getChar: sqlQueryToChar) {
	        if (reqCharFnd == true && getChar != '>')
	                    getTagName = getTagName + getChar;
	        else if (getChar == '>') {
	                    break;
	        } else
	        	reqCharFnd = false;
                if (getChar == '<')
                	reqCharFnd = true;
        }

        if (reqCharFnd == true) {
	        CharSequence getTagVlaue = (CharSequence) mapReadTagValue.get(getTagName);
	        if (getTagVlaue != null)
	        	strSqlQuery = strSqlQuery.toString().replace("<" + getTagName + ">", getTagVlaue);
	        else
	        	System.out.println("required tag reference [" + getTagName + "] not found! \n for the given query: " + strSqlQuery.toString());
	
	        strSqlQuery = strSqlQuery.toString().replace("<" + getTagName + ">", "<#.>");

        }

        return strSqlQuery;
    }
    
    public static void verifyAPIHeadersForTestRunStatus() {
    	HashMap<Object, List<Object>> storeJsonResponse = new HashMap<>();
        storeJsonResponse =verifyJsonResponseAttributes.get(getApiTestRunId);
        
        if(storeJsonResponse ==null)
        	return;
        
        for (Entry<Object, List<Object>> jsonTagElm: storeJsonResponse.entrySet()) {
        	String jsonTagExp = jsonTagElm.getValue().get(0).toString().toLowerCase();
            String jsonTagAct = jsonTagElm.getValue().get(1).toString().toLowerCase();
            
            if (!jsonTagExp.toLowerCase().contentEquals(jsonTagAct.toLowerCase()) ||
            		(jsonTagExp.contentEquals("#.") && jsonTagAct.contentEquals("#."))) {
            	finalRunStatus =false;
            	break;
            }
        }
    }
    
    private static Object updateSqlQueryExpectedOutput(Object strSqlExpOutPut, LinkedHashMap<Object, Object> mapReadTagValue, Entry<Object, HashMap<Object, Object>> testRunnerEntry) {
	    boolean reqCharFnd = false;
	    boolean reqPayloadTag = false;
	    String getTagName = "";

        if (strSqlExpOutPut.toString().charAt(0) == '#') {
            getTagName = strSqlExpOutPut.toString().substring(1);
            reqCharFnd = true;
        } else if (strSqlExpOutPut.toString().charAt(0) == '.') {
            getTagName = strSqlExpOutPut.toString().substring(1);
            reqPayloadTag = true;
        }

        if (reqCharFnd == true) {
	        CharSequence getTagVlaue = (CharSequence) mapReadTagValue.get(getTagName);
	        if (getTagVlaue != null)
	        	strSqlExpOutPut = getTagVlaue;
            else {
            	System.out.println("required tag reference [" + getTagName + "] not found! \n for the given tag reference: " + strSqlExpOutPut.toString());
            	strSqlExpOutPut = "#.";
             }
        }
        
        if (reqPayloadTag == true) {
	        Object getJsonPayload = testRunnerEntry.getValue().get("JSON Request");
	        strSqlExpOutPut = GetTagValueFromJsonResponse.GetJsonTagElement(getTagName, getJsonPayload);
        }

		return strSqlExpOutPut;
    }
}