package com.api.automation.bolt;

import java.net.http.HttpClient;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class API_TestRunner extends LoadTestRunner {

   /*public static Integer getApiTestRunId;
   static CloseableHttpResponse closeableHttpRespone;
   static String requestPayload;
   static String requestPayloadType;
   static JSONArray jsonResponse;
   static JSONObject jsonXm1Response;
   static Object retrieveResponse;
   static String getApiTestRequest;
   static String getApiTestRequestUrl;
   static String getExpResponseCode;
   static String getSSLCertificationFlag;
   static String getBasicAuthFlag;
   static HttpClient restClient;
   static String sqlQuery;
   static String sqlQueryData;
   static String expSqlOutput;
   static HashMap < String, String > testOut_Put = new HashMap < String, String > ();
   static HashMap < String, String > requestHeaders = new HashMap < String, String > ();
   static HashMap < String, Object > payloadTagElement = new HashMap < String, Object > ();
   static HashMap < String, Object > responseDbValidation = new HashMap < String, Object > ();
   static HashMap < String, Object > verifyResponseTagElement = new HashMap < String, Object > ();

   public static HashMap < Integer, HashMap < String, List < String >>> verifyJsonResponseAttributes = new HashMap < Integer, HashMap < String, List < String >>> ();
   public static HashMap < Integer, HashMap < String, List < String >>> modifyJsonResponseAttributes = new HashMap < Integer, HashMap < String, List < String >>> ();

   static String getPayloadFilePath;
   static String getPayloadType;

   public static String executionTime;

   public static String serverName = LoadProperties.prop.getProperty(Constants.SQLServer_ServerName);
   public static String serverPort = LoadProperties.prop.getProperty(Constants.SQLServer_Port);

   public static String dblame = LoadProperties.prop.getProperty(Constants.SQLServer_DatabaseName);
   public static String dbUserName = LoadProperties.prop.getProperty(Constants.SQLServer_UserName);

   public static String dbuserPassword = LoadProperties.prop.getProperty(Constants.SQLServer_UserPassword);

   public static void runAPItest() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

      System.out.println("running api's test!");
      restClient = new Httpclient();

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
      String getFileExtension = null;
      Object newSqlQuery = null;
      Object newSqlExpOutPut = null;
      Object getQueryData = null;
      String getKey = null;
      Object expValue = null;
      String getDbColName = null;
      String getJsonRespstatusCodeValue = null;
      String getJsonRespstatusCode = null;
      long startTime = System.currentTimeMillis();

      for (Entry < Integer, HashMap < String, String >> testRunnerEntry: TestRunner.saveTestRunMap.entrySet()) { //for:1 
    	  
    	 blnJSONresponse = false;
         blnXmlresponse = false;
         blnNoResponse = false;
         closeableHttpRespone = null;
         requestPayload = null;
         requestPayloadType = null;
         getSSLCertificationFlag = null;
         getBasicAuthFlag = null;
         getDbColName = "";

         HttpClient.httpCloseableResponse = null;
         ReadPayloadFile.readPayload = null;
         ResultSet getQueryRes = null;

         getApiTestRunId = testRunnerEntry.getKey(); //get test run id
         getApiTestRequest = testRunnerEntry.getValue().get(Constants.Request_COLUMN_NAME); //get Request type 
         getApiTestRequestUrl = testRunnerEntry.getValue().get(Constants.RequestURL_COLUMN_NAME); //get service url 
         getExpResponseCode = testRunnerEntry.getValue().get(Constants.ResponseCode_COLUMN_NAME); //get response code 

         getSSLCertificationFlag = testRunnerEntry.getValue().get(Constants.SSL_Verify_COLUMN_NAME); //get ss1 certification flag
         if (getSSLCertificationFlag == null) {
            getSSLCertificationFlag = "";
         }

         getBasicAuthFlag = testRunnerEntry.getValue().get(Constants.Basic_Auth_COLUMN_NAME); //get ss1 certification flag 
         if (getBasicAuthFlag == null) {
            getBasicAuthFlag = "";
         }

         try {
            getPayloadFilePath = TestRunner.savePayloadMap.get(getApiTestRunId).split("[1]")[0]; //get payload file path 
            getPayloadType = TestRunner.savePayloadMap.get(getApiTestRunId).split("[]")[1]; //get payload type
         } catch (NullPointerException exp) {
            getPayloadFilePath = null;
         }

         getPayloadType = TestRunner.savePayloadMap.get(getApiTestRunId); //get payload file path
         verifyResponseTagElement = TestRunner.saveVerifyRespTagElmMap.get(getApiTestRunId); //get payload tag/element
         responseDbValidation = TestRunner.saveDbValidationMap.get(getApiTestRunId); //get database validation items
         System.out.println("\nExecuting Test Run: " + getApiTestRunId);

         if (!payloadTagElement.isEmpty()) {
            String getFileExtn = GetFileExtension.getFileExtension(getPayloadFilePath);

            if (getFileExtn.contentEquals("json")) {
               UpdateJsonPayload.UpdateJsonTagElement(getApiTestRunId, payloadTagElement, getPayloadFilePath);
            } else {
               UpdateXmlPayload.UpdateXmlTagElement(payloadTagElement, getPayloadFilePath);
            }
         }

         if (getPayloadFilePath != null) {
            if (getPayloadType.toLowerCase().contains("json")) {
               requestPayload = ReadPayloadFile.readPayloadFromFile(getPayloadFilePath);
            } else if (getPayloadType.toLowerCase().contains("urlencoded")) {
               requestPayload = ReadPayloadFile.readEncodedPayloadFromFile(getPayloadFilePath);
            }
         }

         if (getApiTestRequest.trim().toUpperCase().contentEquals("GET")) { //if:1; GET call
            if (requestHeaders.size() == 0) {
               closeableHttpRespone = restClient.getClientResponse(getApiTestRequestUrl, getSSLCertificationFlag, getBasicAuthFlag);
            } else {
               // update header value from previous json response
               updateHeaderForJsonResponse(getApiTestRunId);
               closeableHttpRespone = restClient.getClientResponse(getApiTestRequestUrl, requestHeaders, getSSLCertificationFlag, getBasicAuthFlag); //GET call with headers
            }

            if (closeableHttpRespone != null) {
               retrieveResponse = getGetResponse.testGetResponse(closeableHttpRespone, getExpResponseCode);
               testOut_Put.put(Constants.Run_API_Actual_Status, Integer.toString(ResponseGET.statusCode));
               testOut_Put.put(Constants.Response_Status_Phrase, ResponseGET.responsePhrase);
            } else {
               getErrorMsg = VerifyValueAPICommon.errorMapping.get("Error");
               testOut_Put.put(Constants.Run_API_Actual Status, "Error");
               testOut_Put.put(Constants.Response_Status_Phrase, getErrorMsg);
            }
         } else if (getApiTestRequest.trim().toUpperCase().contentEquals("POST")) { //POST call
            if (requestPayload != null) {
               if (requestHeaders.size() != 0) {
                  updateHeaderForJsonResponse(getApiTestRunId);
               }

               closeableHttpRespone = restClient.postClientRequest(getApiTestRequestUrl, requestPayload.toString(), requestHeaders, getSSLCertificationFlag, getBasicAuthFlag);
            } else {
               closeableHttpRespone = restClient.postClientRequest(getApiTestRequestUrl, requestHeaders, getSSLCertificationFlag, getBasicAuthFlag);
            }

            if (closeableHttpRespone != null) {
               try {
                  retrieveResponse = getPostResponse.testPostResponse(closeableHttpRespone, getExpResponseCode);
               } catch (ParseException e) {
                  System.out.println(e.toString());
               }

               testOut_Put.put(Constants.Run_API_Actual Status, Integer.toString(ResponsePOST.statusCode));
               testOut_Put.put(Constants.Response_Status_Phrase, ResponsePOST.responsePhrase);
            } else {
               getErrorMsg = VerifyValueAPICommon.errorMapping.get("Error");
               testout_Put.put(Constants.Run_API_Actual Status, "Error");
               testout_Put.put(Constants.Response_Status_Phrase, getErrorMsg);
            }
         } else if (getApiTestRequest.trim().toUpperCase().contentEquals("PUT")) { //PUT call
            if (requestPayload != null) {
               if (requestHeaders.size() != 0) {
                  updateHeaderForJsonResponse(getApiTestRunId);
               }
               closeableHttpRespone = restClient.putClientRequest(getApiTestRequestUrl, request Payload.toString(), requestHeaders, getSSLCertificationFlag, getBasicAuthFlag);
            } else {
               closeableHttpRespone = restClient.putClientRequest(getApiTestRequestUrl, requestHeaders, get SSLCertification Flag, getBasicAuthFlag);
            }

            if (closeableHttpRespone != null) {
               try {
                  retrieveResponse = getPutResponse.testPutResponse(closeableHttpRespone, getExpResponseCode);
               } catch (ParseException e) {
                  System.out.println(e.toString());
               }

               testout_Put.put(Constants.Run_API_Actual_Status, Integer.toString(Response PUT.statusCode));
               testout_Put.put(Constants.Response_Status_Phrase, ResponsePUT.responsePhrase);
            } else {
               getErrorMsg = VerifyValueAPICommon.errorMapping.get("ErrorDesc");
               testOut_Put.put(Constants.Run_API_Actual_Status, "Error");
               testout_Put.put(Constants.Response_Status_Phrase, getErrorMsg);
            }
         }

         //storeRequestPayloadToHasMap
         if (closeableHttpRespone != null) {
            //retrieveResponse = RemoveBracketsFromJSONString.removeBrackets (retrieveResponse);
            if (VerifyValueAPICommon.isJSONValid(retrieveResponse.toString()) == true) {
               try {
                  jsonResponse = new JSONArray(retrieveResponse);
               } catch (JSONException e) {
                  //System.out.println("Error: "+e.getMessage());
                  if (retrieveResponse.toString().charAt(0) != '[') jsonResponse = new JSONArray("[" + retrieveResponse.toString() + "]");
                  else jsonResponse = new JSONArray(retrieveResponse.toString());
               }
               blnJSONresponse = true;

            } else if (VerifyValueAPICommon.isXML Valid(retrieveResponse.toString()) == true) {
               jsonXmlResponse ConvertXmlToJson.XMLtoJSON(retrieveResponse.toString());
               jsonResponse = new JSONArray("[" + jsonXmlResponse + "]");
               blnXmlresponse = true;
            } else {
               getHtml Response = retrieveResponse.toString().replaceAll("[\\\r\\\n]+", "");
               blnNoResponse = true;
            }

            if (getPayloadFilePath = null) {
               getFileExtension = GetFileExtension.getFileExtension(getPayloadFilePath);
               if (getFileExtension.contentEquals(".xml")) {
                  testout_Put.put(Constants.Run_API_JSON_Request, XML.toJSONObject(requestPayload).toString(Constants.PRETTY PRINT_INDENT_FACTOR));
               } else if (getFileExtension.contentEquals("json")) {
                  testOut_Put.put(Constants.Run API JSON_Request, requestPayload.toString());
               } else
                  testout_Put.put(Constants.Run_API_JSON_Request, ReadPayloadFile.readNonJsonPayload(get PayloadFilePath));
            }
         }

         // save the json response to the hash-map
         if (closeableHttpRespone != null && (binJSONresponse || binXmlresponse)) {
            try {
               try {
                  saveResponse.savingResponseToFile(jsonResponse.toString(Constants.PRETTY PRINT INDENT_FACTOR), getApiTestRequest, String.valueOf(getApiTestRunId));
                  if (!verifyResponseTagElement.isEmpty())
                     VerifyTagValueFromJsonResponse.VerifyJsonTagElement(getApiTestRunId, verifyResponseTagElement, jsonResponse.toString(Constants.PRETTY PRINT_INDENT_FACTOR));
               } catch (IOException e) {
                  System.out.println(e.toString());
               }

            } catch (JSONException e) {
               System.out.printin(e.toString());
            }

            testout_Put.put(Constants.Run_API_JSON_Response, jsonResponse.toString(Constants.PRETTY_PRINT__INDENT_FACTOR));

         } else if (closeableHttpRespone != null && blnNoResponse) {
            testout_Put.put(Constants.Run_API_JSON_Response, getHtml Response);
            try {
               saveResponse.savingResponseToFile(getHtml.Response, getApiTestRequest, String.valueOf(getApiTestRunId));
            } catch (IOException e) {
               System.out.println(e.toString());
            }
         } else {
            try {
               getErrorMsg = VerifyValueAPICommon.errorMapping.get("Error");
               getErrorDesc = VerifyValueAPICommon.errorMapping.get("ErrorDesc");
               testOut_Put.put(Constants.Run_API_Actual_Status, "Error");
               testOut_Put.put(Constants.Response_Status_Phrase, getErrorMsg);
               testOut_Put.put(Constants.Run_API_JSON_Response, getErrorDesc);
            } catch (NullPointerException excpMsg) {
               System.out.println(excpMsg.toString());
            }
         }

         //Database validation
         if (!responseDbValidation, isEmpty()) {
            for (Entry < String, Object > entry: responseDbValidation.entrySet()) {
               getKey = entry.getKey();
               expValue = entry.getValue();

               if (getKey.contains(Constants.SqlQuery_COLUMN_NAME)) {
                  newSqlQuery = updateSqlQuery(expValue, payloadTagElement);
                  responseDbValidation.replace(getKey, (String) newSqlQuery);
               }

               if (getKey.contains(Constants.ExpOut Put_COLUMN_NAME)) {
                  if (expValue.toString().charAt(0) = '.') {
                     testout_Put.put("Json Request Tag " + getKey.toString().split("_")[1], expValue.toString().substring(1));
                  }
                  newSqlExpoutPut = updateSqlQueryExpectedOutput(expValue, payloadTagElement, testRunnerEntry);
                  responsepbValidation.replace(getKey, (String) newSqlExpOut Put);
               }
            }

            Connection connectToSqlSrvr = databaseOperations.getSQLServerConnection(
               serverName,
               serverPort,
               dbUserName,
               dbUserPassword,
               dbName);

            for (Entry < String, Object > entry: responseDbValidation.entrySet()) {
               getKey entry.getKey();
               expValue = entry.getValue();
               if (getKey.contains(Constants.SqlQuery_COLUMN_NAME)) {
                  String getTagPst = getKey.split(" ")[1];
                  if (connectToSqlSrvr != null) {
                     getQueryRes = databaseOperations.executeSQLQuery(connectToSqlSrvr, responseDbValidation.get(getKey));
                     if (getQueryRes != null) {
                        try {
                           ResultSetMetaData ramd = getQueryRes.getMetaData();
                           getDbColName = rsmd.getColumnName(1);
                           if (!getQueryRes.isBeforeFirst()) {
                              getQueryData = "Null";
                           }

                           while (getQueryRes.next()) {
                              getQueryData = getQueryRes.getObject(1);
                              if (getQueryData == null)
                                 getQueryData = "Null";
                           }
                        } catch (SQLException | NullPointerException expMsg) {
                           System.out.println(expMsg.toString());
                        }
                     }
                  }

                  if (connectToSqlSrvr != null && getQueryRes != null) {
                     testout_Put.put("Db Column Name_" + getTagPst, getDbColName);
                     testout_Put.put("dbActualData" + " " + getTagPst, getQueryData.toString());
                     testout_Put.put("dbExpectedData" + " " + getTagPst, (String) response DbValidation.get(Constants.ExpOutPut_COLUMN_NAME + "_" + getTagPst));
                  } else if (connectToSqlSrvr == null || getQueryRes == null) {
                     testOut_Put.put("dbActualData" + " " + getTagPst, null);
                     testout_Put.put("dbExpectedData" + "_" + getTagPst, (String) responseDbValidation.get(Constants.ExpOutPut_COLUMN_NAME + "_" + getTagPst));
                  }
               }
            }

            try {
               getQueryRes.close():
                  connectToSqlSrvr.close():
            } catch (SQLException | NullPointerException expMsg) {
               System.out.println(expMsg.toString());
            } //Database validation

            TestRunner.saveTestRunMap.put(getApiTestRunId, testOut_Put);
            testOut_Put = new HashMap < String, String > ();
         } //database validation end here

      } // parent for loop ends here

      long endTime = System.currentTimeMillis();
      long totalTime = endTime - startTime:
         long minutes = (totalTime / 1000) / 60;
      long seconds = (totalTime / 1000) % 60;
      executionTime = String.format("%02d: 02d mins", minutes, seconds); //generate test report
      ApiTestReport.GenerateApiTestReport();

   }

   public static void updateHeaderForJsonResponse(int runID) {
      HashMap < String, String > newMap = new HashMap < String, String > ();
      String attachText = null;

      for (Map.Entry < String, String > updateHeader: requestHeaders.entrySet()) {
         if (updateHeader.getValue().toString().contains("_RefFnd_")) {
            String splitTagName = updateHeader.getValue().toString().split("_RefFnd_")[0];

            if (splitTagName.contains(":")) {
               attachText = splitTagName.split(":")[0];
               splitTagName = splitTagName.split(":")[1];
            }

            String getTestId = updateHeader.getValue().toString().split("_RefFnd ")[1].replace("#", "");
            HashMap < String, String > getPrevJsonResponse = TestRunner.saveTestRunMap.get(Integer.parseInt(getTestId));
            String getJsonResponse = getPrevJsonResponse.get("JSON Response");
            String getRespTagVal = GetTagValueFromJsonResponse.GetJsonTagElement(splitTagName, getJsonResponse);

            if (getRespTagVal.contentEquals("#.")) {
               System.out.println("required tag [" + splitTagName + "] not found in the json response");
            } else {
               if (attachText == null) {
                  requestHeaders.put(updateHeader.getKey(), getRespTagVal.toString());
               } else {
                  requestHeaders.put(updateReader.getKey(), attachText + getRespTagVal.toString());
               }
            }

            TestRunner.saveHeaderMap.put(runID, requestHeaders);
         }
      }
   }

   private static Object updateSqlQuery(Object strSqlQuery, HashMap < String, Object > mapReadTagValue) {
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

      return strSqlQuery;
   }

   private static Object updateSqlQueryExpectedOutput(Object strSqlExpOutPut, HashMap < String, Object > mapReadTagValue, Entry < Integer, HashMap < String, String >> testRunnerEntry) {
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
            strSqlExpOutPut = ".";
         }
      }

      if (reqPayloadTag = true) {
         String getJsonPayload = testRunnerEntry.getValue().get("JSON Request");
         strSqlExpOutPut = GetTagValueFromJsonResponse.GetJsonTagElement(getTagName, getJsonPayload);

      }
      return strSqlExpOutPut;
   }*/
}