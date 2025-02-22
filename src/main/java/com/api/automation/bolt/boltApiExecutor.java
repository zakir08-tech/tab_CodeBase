package com.api.automation.bolt;

//import static com.automation.bolt.common.getCurrentDateAndTime;
import static com.automation.bolt.gui.ExecuteApiTest.*;
//import static com.automation.bolt.gui.ExecuteApiTest.bttnRefreshTestRun;
//import static com.automation.bolt.gui.ExecuteApiTest.bttnStartTestRun;
//import static com.automation.bolt.gui.ExecuteApiTest.bttnStopTestRun;
//import static com.automation.bolt.gui.ExecuteApiTest.chkBoxFilterFailTest;
//import static com.automation.bolt.gui.ExecuteApiTest.chkBoxSelectDeselectAllRun;
//import static com.automation.bolt.gui.ExecuteApiTest.tableExecuteRegSuite;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.automation.bolt.boltRunner;
import com.automation.bolt.gui.ExecuteApiTest;
import com.fasterxml.jackson.core.JsonProcessingException;

import static com.automation.bolt.gui.ExecuteRegressionSuite.bttnRefreshTestRun;

public class boltApiExecutor extends Thread {
	static Logger log = Logger.getLogger(boltApiExecutor.class.getName());
	public static boltRunner bRunner = new boltRunner();
	public static loadAPITestRunner apiTestLoader = new loadAPITestRunner();
	public static API_TestRunner apiRunner = new API_TestRunner();

	public static ExecuteApiTest exeRunner = new ExecuteApiTest();
	public static boolean testRunInProgress;
	public static String testRunStartDateAndTime = "";
	public static String testRunEndDateAndTime = "";
	public static String totalTestRun = "";
	public static String totalFailed = "";
	public static String totalPassed = "";
	public static String totalWarning = "";
	public static String totalNoRun = "";
	public static String totalRunTime = "";
	public static String testSuiteName = "";
	public static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
	public static Date startRunDateTime;
	public static Date endRunDateTime;
	public static boolean getStatus;
	public static String getErrorMessage;

	@Override
	public void run() {
		// PropertyConfigurator.configure(boltApiExecutor.class.getResourceAsStream("log4j.properties"));
		// PropertyConfigurator.configure(System.getProperty("user.dir").replaceAll("\\\\",
		// "/")+"/config/log4j.properties");
		testRunInProgress = true;
		apiTestLoader.loadApiTest();

		try {
			API_TestRunner.runAPItest();
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | NullPointerException
				| JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		bttnStartTestRun.setEnabled(true);
		ExecuteApiTest.bttnRefreshTestRun.setEnabled(true);
		bttnRefreshTestRun.setEnabled(true);
		chkBoxFilterFailTest.setEnabled(true);
		chkBoxFilterFailTest.setSelected(false);
		chkBoxSelectDeselectAllRun.setEnabled(true);
		tabExecuteRegSuite.setEnabled(true);
		bttnLoadRegSuite.setEnabled(true);
		bttnStopTestRun.setEnabled(false);
		testRunInProgress = false;
	}
}
