package com.api.automation.bolt;
 
public class Constants {
            public static String SysDir = System.getProperty("user.dir");
 
            public static int PRETTY_PRINT_INDENT_FACTOR = 4;
 
            /*to run from eclipse workspace [uncomment when running from eclipse]*/
            //public static String PROPERTY_FILE_NAME = ".\\src\\main\\java\\com\\api\\automation\\config\\config.properties";
 
            /*to run from system directory [to run using executable jar]*/
            public static String PROPERTY_FILE_NAME = SysDir+"/config/config.properties";
 
            public static String testRunnerSheet = "API Test";
            public static String requestHeaderSheet = "Request Headers";
            public static String requestBodySheet = "Request Body(payload)";
            public static String requestModifyPayloadSheet = "Modify Payload";
            public static String requestVerifyResponseSheet = "Verify Response";
            public static String requestDbValidationeSheet = "Database Validation";
            //public static String requestModifyAPIurl = "Modify API URL";
            public static String Run_API_COLUMN_NAME = "Run API";
            public static String Run_ID_COLUMN_NAME = "Run ID";
            public static String Request_COLUMN_NAME = "Request";
            public static String RequestURL_COLUMN_NAME = "Request URL";
            public static String ResponseCode_COLUMN_NAME = "Expected Status";
            public static String Summary_COLUMN_NAME = "Summary";
            public static String SSL_Verify_COLUMN_NAME = "SSL Verification";
            public static String Basic_Auth_COLUMN_NAME = "Basic Authorization";
            public static String Request_Header_COLUMN_NAME = "Header";
            public static String Header_Value_COLUMN_NAME = "Value";
            public static String Header_Reference_COLUMN_NAME = "Reference";
            public static String Tag_COLUMN_NAME = "Tag";
            public static String Element_COLUMN_NAME = "Value";
            public static String Reference_COLUMN_NAME = "Reference";
            public static String SqlQuery_COLUMN_NAME = "SQL Query";
            public static String ExpOutPut_COLUMN_NAME = "Expected Output";
            public static String Payload_COLUMN_NAME = "Payload";
            public static String PayloadType_COLUMN_NAME = "Type";
            public static String Run_API_STATUS = "YES";
            public static String Run_API_Actual_Status = "Actual Status";
            public static String Run_API_JSON_Request = "JSON Request";
            public static String Run_API_JSON_Response = "JSON Response";
            public static String Response_Status_Phrase = "Status Code Phrase";
            public static String Run_API_Request_URL = "Request URL";
 
            public static String PROXY_HOST = "one.proxy.att.com";
            public static int PROXY_PORT = 8080;
            public static String PROXY_USERNAME = "";
            public static String PROXY_PASSWORD = "";
            public static String testSuiteName;
            public static String APITestResultFilePathPropName = "APITestResultFilePath";
            public static String APITestRunnerFilePropName = "APITestRunnerFile";
            public static String APITestRunnerFilePathPropName = "APITestRunnerFilePath";
            public static String JsonResponseFilePathPropName = "JsonResponseFilePath";
 
            public static String AuthUserNamePropName = "authUserName";
            public static String AuthPasswordPropName = "authPassword";
            public static String TrustStorePropName = "trustStoreFilePath";
            public static String TrustStorePwdPropName = "trustStorePwd";
            public static String KeyStorePropName = "keyStoreFilePath";
            public static String KeyStorePwdPropName = "keyStorePwd";
 
            public static String EmailHostPropName = "emailHost";
            public static String ToEmailPropName = "toEmail";
            public static String FromEmailPropName = "DL-IT-ABS-API_Test_Utility@att.com";
            public static String smtpPortPropName = "smtpPort";
 
            public static String UNIQUE_NUMBER_TIMESTAMP = "UNIQUE_NUMBER_TIMESTAMP";
 
            public static String SQLServer_ServerName = "sqlServerName";
            public static String SQLServer_Port = "sqlServerPort";
            public static String SQLServer_DatabaseName = "sqlServerDatabaseName";
            public static String SQLServer_UserName = "sqlServerUserName";
            public static String SQLServer_UserPassword = "sqlServerPassword";
}

