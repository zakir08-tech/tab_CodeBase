package com.api.automation.util;
 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
 
public class UserDefineExternalSolutions {
	public static String readExternalMethodName;
	public static SimpleDateFormat timeStamp = new SimpleDateFormat("yyyyMMddHHmmss");
    public static String UniqueValueWithPreFix =null;

    public static Object runExternalMethod(String[] getMethodArgs) {
    	Object getReturnVal = null;
    	
    	if(readExternalMethodName ==null)
    		readExternalMethodName ="NULL";
    	
        switch (readExternalMethodName) {
	        case "generateUniqueBankID":
	            //getReturnVal = generateUniqueBankID(getMethodArgs);
	            break;
	        case "generateUniqueTransactionID":
	            //getReturnVal = generateUniqueTransactionID(getMethodArgs);
	            break;
	        case "generateUniqueRefNumber":
	            //getReturnVal = generateUniqueReferenceNumber(getMethodArgs);
	            break;         
	        case "generateUniqueKridNumber":
	            //getReturnVal = generateUniqueKRIDNumber(getMethodArgs);         
	            break;
	        case "UniqueValueWithPreFix":
	            getReturnVal = UniqueNumberUsingTimeStampPreFix(getMethodArgs);
	            break;
            case "LastUniqueValueWithPreFix":
                getReturnVal = LastUniqueNumberUsingTimeStampPreFix();
                break;
	        default:
	        	System.out.println("no method found with this name: " + readExternalMethodName);
        }
        
        if (getReturnVal == null)
        	getReturnVal = "NULL";
 
        return getReturnVal;
    }
           
    public static String UniqueNumberUsingTimeStampPreFix(String[] getMethodArgs) {
	    String timeFormat ="";
	    String preFix ="";
                       
        try{
        	timeFormat =getMethodArgs[0];
        }catch(NullPointerException exp){
        	timeFormat ="YYMMddhhmSS";
        }
                       
        try{
        	preFix =getMethodArgs[1];
        }catch(NullPointerException | ArrayIndexOutOfBoundsException exp){}
                       
        try{
        	timeStamp = new SimpleDateFormat(timeFormat);
        }catch(IllegalArgumentException exp){
            timeStamp = new SimpleDateFormat("YYMMddhhmSS");
        }
                       
        String uniqueNumberTimeStamp;
        uniqueNumberTimeStamp = timeStamp.format(new Date());
 
        try {
        	Thread.sleep(1000);
        } catch(InterruptedException e) {
        	System.out.println(e.toString());
        }
        
        UniqueValueWithPreFix =preFix+uniqueNumberTimeStamp;
        return preFix+uniqueNumberTimeStamp;
    }

    public static String LastUniqueNumberUsingTimeStampPreFix() {
        return UniqueValueWithPreFix;
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