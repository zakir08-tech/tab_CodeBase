package com.automation.bolt;

import static com.automation.bolt.boltRunner.getCurrRunId;
import static com.automation.bolt.boltRunner.trTestCards;
import static com.automation.bolt.common.breakTheExceptionMsg;
import static com.automation.bolt.common.getCurrentDateAndTime;
import static com.automation.bolt.glueCode.implicitWaitTime;
import static com.automation.bolt.glueCode.pageLoadTimeOut;
import static com.automation.bolt.gui.ExecuteRegressionSuite.arrTestId;
import static com.automation.bolt.gui.ExecuteRegressionSuite.excelFile;
import static com.automation.bolt.gui.ExecuteRegressionSuite.testSuiteFilePath;
import static com.automation.bolt.htmlReportCommon.concatenateHashMapDataWithNewLine;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.apache.log4j.Logger;

import com.automation.bolt.gui.ExecuteRegressionSuite;

import io.github.bonigarcia.wdm.config.WebDriverManagerException;

public class externalTabRun extends constants{
	 public static boolean getStatus;
	 public static boltRunner bRunner = new boltRunner();
	 public static Logger log = Logger.getLogger(boltExecutor.class.getName());
	 
	public static void main(String[] args) {
		loadder(args);
		
		implicitWaitTime =waitTimeInSec;
		pageLoadTimeOut =waitTimeInSec;
		
		bRunner = new boltRunner();
		testSuiteFilePath = runnerFileName;
		excelFile = new File(testSuiteFilePath);
		
		String[] runIdList =testRunList.split(",");
		
		for(String runId:runIdList)
			arrTestId.add(runId);
					
		boltExecutor.testRunStartDateAndTime =getCurrentDateAndTime();
		try {
			boltExecutor.startRunDateTime =boltExecutor.dateFormatter.parse(boltExecutor.testRunStartDateAndTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		getStatus =common.readTestRunner_TestSteps();		
		getStatus =common.readTestRunner_ObjectRepository();
        if(getStatus ==true){
            try{
                bRunner.boltTestRunner();
            }catch (IllegalStateException | WebDriverManagerException | IOException | InterruptedException  exp){
                log.error("seems to be driver issue:\n"+breakTheExceptionMsg(exp.getMessage()+"\n\nstopping the test run NOW!"));
                ExecuteRegressionSuite.importDataFromExcelModel.setValueAt("Interrupted!", getCurrRunId, 3);
            }
        }
        
        boltExecutor.testRunEndDateAndTime =getCurrentDateAndTime();
        try {
            boltExecutor.endRunDateTime =boltExecutor.dateFormatter.parse(boltExecutor.testRunEndDateAndTime);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        String trGetCards =concatenateHashMapDataWithNewLine(trTestCards);
        String trCardsContainer =htmlReportCommon.trTestContainer.replace("$testCards", trGetCards);
        
        String getRunStartEndTime =common.getTestrunTime(boltExecutor.startRunDateTime, boltExecutor.endRunDateTime);
        String htmlReport ="";
        
        htmlReport =htmlReportCommon.trTemplateEditTestRunInfo(htmlReportCommon.htmlTestReport, 
            boltExecutor.testRunStartDateAndTime, boltExecutor.totalRunTime, getRunStartEndTime.split(":")[0], getRunStartEndTime.split(":")[1], getRunStartEndTime.split(":")[2]);
        
        String testHtmlReport =htmlReport.replace("$testCaseSteps", trCardsContainer);
        if(getStatus ==true)
            common.createHtmlTestReport(testHtmlReport,excelFile.getName());
	}

}
