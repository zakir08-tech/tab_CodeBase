package com.api.automation.util;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
public class databaseOperations {
	public static Connection connection;
	public static Statement sqlStatements;
	public static ResultSet sqlResultSet;
           
    public static Connection getSQLServerConnection(String serverName, String port, String userName, String Password, String DatabaseName){
    	String connectionURL = "jdbc:sqlserver://" +serverName+ ":" +port+ ";databaseName=" +DatabaseName+ ";user=" +userName+ ";password=" +Password;
                       
        try {
        	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(connectionURL);
        } catch (ClassNotFoundException | SQLException excpMsg) {
        	System.out.println(excpMsg.toString() +"  \n:found at getSQLServerConnection");
        }
        return connection;
    }
     
    public static ResultSet executeSQLQuery(Connection sqlConnection, Object sqlQuery){ 
	    try {
	        sqlStatements = sqlConnection.createStatement();
	        sqlStatements.setQueryTimeout(20);
	        sqlResultSet = sqlStatements.executeQuery(sqlQuery.toString());
	    } catch (SQLException | NullPointerException excpMsg) {
	        //ApiTestExecutor.LOGGER.log(Level.INFO, excpMsg.toString() + "  \n:found at executeSQLQuery");
	        System.out.println(excpMsg.toString() + "  \n:found at executeSQLQuery");
	    }
                       
        return sqlResultSet;
    }
}

