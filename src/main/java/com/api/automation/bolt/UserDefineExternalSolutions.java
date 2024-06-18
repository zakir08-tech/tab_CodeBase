package com.api.automation.bolt;
 
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.api.automation.util.GenerateRandomNumber;
import com.api.automation.util.databaseOperations;
 
public class UserDefineExternalSolutions {
	public static String readExternalMethodName;
	public static String serverName = LoadProperties.prop.getProperty(Constants.SQLServer_ServerName);
	public static String serverPort = LoadProperties.prop.getProperty(Constants.SQLServer_Port);
	public static String dbName = LoadProperties.prop.getProperty(Constants.SQLServer_DatabaseName);
	public static String dbUserName = LoadProperties.prop.getProperty(Constants.SQLServer_UserName);
	public static String dbUserPassword = LoadProperties.prop.getProperty(Constants.SQLServer_UserPassword);
	public static SimpleDateFormat timeStamp = new SimpleDateFormat("yyyyMMddHHmmss");
           
    public static Object runExternalMethod(String[] getMethodArgs) {
    	Object getReturnVal = null;
 
        switch (readExternalMethodName) {
	        case "generateUniqueBankID":
	            getReturnVal = generateUniqueBankID(getMethodArgs);
	            break;
	        case "generateUniqueTransactionID":
	            getReturnVal = generateUniqueTransactionID(getMethodArgs);
	            break;
	        case "generateUniqueRefNumber":
	            getReturnVal = generateUniqueReferenceNumber(getMethodArgs);
	            break;         
	        case "generateUniqueKridNumber":
	            getReturnVal = generateUniqueKRIDNumber(getMethodArgs);         
	            break;
	        case "generateUniqueNumberTimeStamp":
	            getReturnVal = UniqueNumberUsingTimeStamp(getMethodArgs);    
	            break;
	        default:
	        	System.out.println("no method found with this name: " + readExternalMethodName);
        }
        
        if (getReturnVal == null)
        	getReturnVal = "#.";
 
        return getReturnVal;
    }
           
    public static String UniqueNumberUsingTimeStamp(String[] getMethodArgs) {
	    String timeFormat ="";
	    String preFix ="";
                       
        try{
        	timeFormat =getMethodArgs[0];
        }catch(NullPointerException exp){
        	timeFormat ="yyyyMMddHHmmss";
        }
                       
        try{
        	preFix =getMethodArgs[1];
        }catch(NullPointerException | ArrayIndexOutOfBoundsException exp){}
                       
        try{
        	timeStamp = new SimpleDateFormat(timeFormat);
        }catch(IllegalArgumentException exp){
            timeStamp = new SimpleDateFormat("yyyyMMddHHmmss");
        }
                       
        String uniqueNumberTimeStamp;
        uniqueNumberTimeStamp = timeStamp.format(new Date());
 
        try {
        	Thread.sleep(1000);
        } catch(InterruptedException e) {
        	System.out.println(e.toString());
        }
 
        return preFix+uniqueNumberTimeStamp;
    }
           
    public static int generateUniqueBankID(String[] getMethodArgs) {
	    Connection connectToSqlSrvr = databaseOperations.getSQLServerConnection(serverName, serverPort, dbUserName, dbUserPassword, dbName);
	    ResultSet getQueryRes;
	    Boolean empl_idExist = null;
	    int getRandomNum = 0;
                       
        if(connectToSqlSrvr != null){
        	try{
                do {
                    getRandomNum = GenerateRandomNumber.generateRandomNumber(9999999, 1000000);
                    String sqlQueryToRun = "select empl_id from [hrms].[hrms].ps_user_transaction where empl_id = '" + getRandomNum + "'";
                    getQueryRes = databaseOperations.executeSQLQuery(connectToSqlSrvr, sqlQueryToRun);
                                                           
                    if(getQueryRes != null)
                    	empl_idExist = generateNewHRMSEmplID(getQueryRes);
                    if(empl_idExist == false)
                    	System.out.println("new Bank ID generated: "+getRandomNum);
 
                } while (empl_idExist == true);
            }catch (NullPointerException excpMsg) {
	            System.out.println(excpMsg.toString() +"  \n:found at generateUniqueBankID");
	            getRandomNum = 0;
            }
        }
                       
        return getRandomNum;
    }
 
    public static String generateUniqueTransactionID(String[] getMethodArgs) {
	    Connection connectToSqlSrvr = databaseOperations.getSQLServerConnection(serverName, serverPort, dbUserName, dbUserPassword, dbName);
	    ResultSet getQueryRes;
	    Boolean empl_idExist = null;
	    int getRandomNum = 0;
                       
        if(connectToSqlSrvr != null){
        	try{
        		do {
	                getRandomNum = GenerateRandomNumber.generateRandomNumber(99999, 10000);
	                String sqlQueryToRun = "select transaction_id from [hrms].[hrms].ps_user_transaction where transaction_id = 'transID_" + getRandomNum + "'";
	                getQueryRes = databaseOperations.executeSQLQuery(connectToSqlSrvr, sqlQueryToRun);
                                           
                    if(getQueryRes != null)
                    	empl_idExist = generateNewHRMSTransactionID(getQueryRes);
                    if(empl_idExist == false)
                    	System.out.println("new Transaction ID generated: transID_"+getRandomNum);
 
                } while (empl_idExist == true);
            }catch (NullPointerException excpMsg) {
            	System.out.println(excpMsg.toString() +"  \n:found at generateUniqueTransactionID");
                getRandomNum = 0;
            }
        }
                       
        return "transID_"+getRandomNum;
    }
           
    public static String generateUniqueReferenceNumber(String[] getMethodArgs) {
	    Connection connectToSqlSrvr = databaseOperations.getSQLServerConnection(serverName, serverPort, dbUserName, dbUserPassword, dbName);
	    ResultSet getQueryRes;
	    Boolean empl_idExist = null;
	    int getRandomNum = 0;
                       
        if(connectToSqlSrvr != null){
        	try{
        		do {
	                getRandomNum = GenerateRandomNumber.generateRandomNumber(99999, 10000);
	                String sqlQueryToRun = "select ref_num from [hrms].[hrms].ps_user_transaction where ref_num = 'refNum_" + getRandomNum + "'";
	                getQueryRes = databaseOperations.executeSQLQuery(connectToSqlSrvr, sqlQueryToRun);
                                                           
                    if(getQueryRes != null)
                    	empl_idExist = generateNewHRMSTransactionID(getQueryRes);
                    if(empl_idExist == false)
                    	System.out.println("new reference number generated: refNum_"+getRandomNum);
 
                } while (empl_idExist == true);
            }catch (NullPointerException excpMsg) {
                System.out.println(excpMsg.toString() +"  \n:found at generateUniqueReferenceNumber");
                getRandomNum = 0;
            }
        }
                       
        return "refNum_"+getRandomNum;
    }
           
    public static int generateUniqueKRIDNumber(String[] getMethodArgs) {
	    Connection connectToSqlSrvr = databaseOperations.getSQLServerConnection(serverName, serverPort, dbUserName, dbUserPassword, dbName);
	    ResultSet getQueryRes;
	    Boolean empl_idExist = null;
	    int getRandomNum = 0;
                       
        if(connectToSqlSrvr != null){
        	try{
        		do {
	                getRandomNum = GenerateRandomNumber.generateRandomNumber(999999, 100000);
	                String sqlQueryToRun = "select alternate_id from [hrms].[hrms].ps_user_transaction where alternate_id = '" + getRandomNum + "'";
	                getQueryRes = databaseOperations.executeSQLQuery(connectToSqlSrvr, sqlQueryToRun);
	               
                    if(getQueryRes != null)
	                    empl_idExist = generateNewHRMSTransactionID(getQueryRes);
                    if(empl_idExist == false)
                    	System.out.println("new KRID generated: "+getRandomNum);
 
                } while (empl_idExist == true);
            }catch (NullPointerException excpMsg) {
                System.out.println(excpMsg.toString() +"  \n:found at generateUniqueKRIDNumber");
                getRandomNum = 0;
            }
        }                
        return getRandomNum;
    }
           
    public static boolean generateNewHRMSEmplID(ResultSet sqlQueryResult) {
	    boolean empl_idFnd = false;
	    int getEmpId;
 
        try {
	        while (sqlQueryResult.next()) {
	            getEmpId = sqlQueryResult.getInt(1);
	            System.out.println("Bank ID already exist: " + getEmpId);
	            empl_idFnd = true;
	        }
 
            if (empl_idFnd != true) {
                databaseOperations.sqlResultSet.close();
                databaseOperations.connection.close();
            }
 
        } catch (SQLException | NullPointerException excpMsg) {
        	System.out.println(excpMsg.toString() +"  \n:found at generateNewHRMSEmplID");
        }
        return empl_idFnd;
    }
         
    public static boolean generateNewHRMSTransactionID(ResultSet sqlQueryResult) {
	    boolean trans_idFnd = false;
	    int getTransId;
 
        try {
	        while (sqlQueryResult.next()) {
	            getTransId = sqlQueryResult.getInt(1);
	            System.out.println("Transaction ID already exist: " + getTransId);
                trans_idFnd = true;
            }
 
            if (trans_idFnd != true) {
	            databaseOperations.sqlResultSet.close();
	            databaseOperations.connection.close();
            }
 
        } catch (SQLException | NullPointerException excpMsg) {
        	System.out.println(excpMsg.toString() +"  \n:found at generateNewHRMSTransactionID");
        }
        return trans_idFnd;
    }
           
    public static boolean generateNewHRMSReferenceNumber(ResultSet sqlQueryResult) {
	    boolean ref_numFnd = false;
	    int getRefNum;
 
        try {
	        while (sqlQueryResult.next()) {
	            getRefNum = sqlQueryResult.getInt(1);
	            System.out.println("Reference Number already exist: " + getRefNum);
	            ref_numFnd = true;
	        }
 
            if (ref_numFnd != true) {
                databaseOperations.sqlResultSet.close();
                databaseOperations.connection.close();
            }
 
        } catch (SQLException | NullPointerException excpMsg) {
        	System.out.println(excpMsg.toString() +"  \n:found at generateNewHRMSReferenceNumber");
        }
        return ref_numFnd;
    }
}

