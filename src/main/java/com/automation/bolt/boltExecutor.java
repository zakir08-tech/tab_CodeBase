package com.automation.bolt;

import static com.automation.bolt.boltRunner.getCurrRunId;
import static com.automation.bolt.boltRunner.trTestCards;
import static com.automation.bolt.boltRunner.trTestCase;
import static com.automation.bolt.boltRunner.trTestSteps;
import static com.automation.bolt.common.breakTheExceptionMsg;
import static com.automation.bolt.common.getCurrentDateAndTime;
import static com.automation.bolt.common.killProcess;
import static com.automation.bolt.gui.ExecuteRegressionSuite.bttnLoadRegSuite;
import static com.automation.bolt.gui.ExecuteRegressionSuite.bttnRefreshTestRun;
import static com.automation.bolt.gui.ExecuteRegressionSuite.bttnStartTestRun;
import static com.automation.bolt.gui.ExecuteRegressionSuite.bttnStopTestRun;
import static com.automation.bolt.gui.ExecuteRegressionSuite.chkBoxAssociateObjOR;
import static com.automation.bolt.gui.ExecuteRegressionSuite.chkBoxFilterFailTest;
import static com.automation.bolt.gui.ExecuteRegressionSuite.chkBoxRunHeadless;
import static com.automation.bolt.gui.ExecuteRegressionSuite.chkBoxSelectDeselectAllRun;
import static com.automation.bolt.gui.ExecuteRegressionSuite.excelFile;
import static com.automation.bolt.gui.ExecuteRegressionSuite.lblChrome;
import static com.automation.bolt.gui.ExecuteRegressionSuite.lblEdge;
import static com.automation.bolt.gui.ExecuteRegressionSuite.rdButtonChrome;
import static com.automation.bolt.gui.ExecuteRegressionSuite.rdButtonEdge;
import static com.automation.bolt.gui.ExecuteRegressionSuite.stopExecution;
import static com.automation.bolt.gui.ExecuteRegressionSuite.tableExecuteRegSuite;
import static com.automation.bolt.htmlReportCommon.concatenateHashMapDataWithNewLine;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebDriverException;

import com.automation.bolt.gui.ExecuteRegressionSuite;

import io.github.bonigarcia.wdm.config.WebDriverManagerException;

public class boltExecutor extends Thread {
    //static Logger log = Logger.getLogger(boltExecutor.class.getName());
    public static org.apache.logging.log4j.Logger log = LogManager.getLogger(boltExecutor.class);
    public static boltRunner bRunner = new boltRunner();
    public static ExecuteRegressionSuite exeRunner = new ExecuteRegressionSuite();
    public static boolean testRunInProgress;
    public static String testRunStartDateAndTime ="";
    public static String testRunEndDateAndTime ="";
    public static String totalTestRun ="";
    public static String totalFailed ="";
    public static String totalPassed ="";
    public static String totalWarning ="";
    public static String totalNoRun ="";
    public static String totalRunTime ="";
    public static String testSuiteName ="";
    public static SimpleDateFormat dateFormatter =new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    public static Date startRunDateTime;
    public static Date endRunDateTime;
    public static boolean getStatus;
    public static String getErrorMessage;
   
    @Override
    public void run() {
    	// Configure Log4j with a basic console appender
        BasicConfigurator.configure();
        // Optionally set the logging level for FreeMarker
        Logger.getLogger("freemarker.cache").setLevel(Level.INFO);
        
    	//final org.apache.logging.log4j.Logger logger = LogManager.getLogger(boltExecutor.class);
    	//PropertyConfigurator.configure(boltExecutor.class.getResourceAsStream("log4j.properties"));
        //PropertyConfigurator.configure(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/config/log4j.properties");
        testRunInProgress =true;
        bRunner = new boltRunner();
        
        try {
            //Thread.sleep(1000);
            try {
                testRunStartDateAndTime =getCurrentDateAndTime();
                startRunDateTime =dateFormatter.parse(testRunStartDateAndTime);
            } catch (ParseException ex) {}
            common.readTestRunner_TestSteps();
            getStatus =common.readTestRunner_ObjectRepository();
            if(getStatus ==true){
                try{
                    bRunner.boltTestRunner();
                }catch (IllegalStateException | WebDriverManagerException | IOException  exp){
                    log.error("seems to be driver issue:\n"+breakTheExceptionMsg(exp.getMessage()+"\n\nstopping the test run NOW!"));
                    ExecuteRegressionSuite.importDataFromExcelModel.setValueAt("Interrupted!", getCurrRunId, 3);
                }
            }
        }
        catch (InterruptedException exp) {
            ExecuteRegressionSuite.importDataFromExcelModel.setValueAt("Interrupted!", getCurrRunId, 3);
            log.error(exp);
        }catch(WebDriverException exp){
            log.error("seems to be driver issue:\n"+breakTheExceptionMsg(exp.getMessage()+"\n\nstopping the test run NOW!"));
            ExecuteRegressionSuite.importDataFromExcelModel.setValueAt("Interrupted!", getCurrRunId, 3);
            stopExecution =true;
            getErrorMessage =exp.getMessage().replace("\n", ". ");
        }
        
        try{
            if (ExecuteRegressionSuite.importDataFromExcelModel.getValueAt(getCurrRunId, 3).toString().contentEquals("Stopping..."))
                ExecuteRegressionSuite.importDataFromExcelModel.setValueAt("Interrupted!", getCurrRunId, 3); 
        }catch(ArrayIndexOutOfBoundsException exp){}
        
        try { 
            testRunEndDateAndTime =getCurrentDateAndTime();
            endRunDateTime =dateFormatter.parse(testRunEndDateAndTime);
        } catch (ParseException ex) {}
                    
        String trGetCards =concatenateHashMapDataWithNewLine(trTestCards);
        String trCardsContainer =htmlReportCommon.trTestContainer.replace("$testCards", trGetCards);
        
        String getRunStartEndTime =common.getTestrunTime(startRunDateTime, endRunDateTime);
        String htmlReport ="";
        
        if(stopExecution ==true){
            htmlReport =htmlReportCommon.trTemplateEditTestRunInfo(htmlReportCommon.updateErrorMessageForHardStopAndWebDriverException(getErrorMessage), 
                testRunStartDateAndTime, totalRunTime, getRunStartEndTime.split(":")[0], getRunStartEndTime.split(":")[1], getRunStartEndTime.split(":")[2]);
        }else if(stopExecution !=true){
            htmlReport =htmlReportCommon.trTemplateEditTestRunInfo(htmlReportCommon.htmlTestReport, 
                testRunStartDateAndTime, totalRunTime, getRunStartEndTime.split(":")[0], getRunStartEndTime.split(":")[1], getRunStartEndTime.split(":")[2]);
        }
            
        String testHtmlReport =htmlReport.replace("$testCaseSteps", trCardsContainer);
        if(getStatus ==true)
            common.createHtmlTestReport(testHtmlReport,excelFile.getName());
        
        trTestSteps =new LinkedHashMap<Integer, String>();
        trTestCase =new LinkedHashMap<Integer, String>();
        trTestCards =new LinkedHashMap<Integer, String>();
        boltRunner.testResult =new LinkedHashMap<Integer, Object>();
            
        bttnStartTestRun.setEnabled(true);
        bttnRefreshTestRun.setEnabled(true);
        chkBoxFilterFailTest.setEnabled(true);
        chkBoxFilterFailTest.setSelected(false);
        chkBoxSelectDeselectAllRun.setEnabled(true);
        tableExecuteRegSuite.setEnabled(true);
        bttnLoadRegSuite.setEnabled(true);
        bttnStopTestRun.setEnabled(false);
        chkBoxRunHeadless.setEnabled(true);
        chkBoxAssociateObjOR.setEnabled(true);
        rdButtonEdge.setEnabled(true);
        rdButtonChrome.setEnabled(true);
        lblChrome.setEnabled(true);
        lblEdge.setEnabled(true);
        testRunInProgress =false;
        
        killProcess("chromedriver.exe");
        killProcess("msedgedriver.exe");
    }
}