package com.automation.bolt;

import static com.automation.bolt.common.createUniqueID;
import static com.automation.bolt.common.executeTestStep;
import static com.automation.bolt.common.executeUserDefineTestStep;
import static com.automation.bolt.common.writeTestStep;
import static com.automation.bolt.glueCode.stepSuccess;
import static com.automation.bolt.gui.EditRegressionSuite.comboBoxTestFlow;
import com.automation.bolt.gui.ExecuteRegressionSuite;
import static com.automation.bolt.gui.ExecuteRegressionSuite.stopExecution;
import static com.automation.bolt.htmlReportCommon.concatenateHashMapDataWithNewLine;
import static com.automation.bolt.htmlReportCommon.trTemplateEditScreenShot;
import static com.automation.bolt.htmlReportCommon.trTemplateEditStepFailed;
import static com.automation.bolt.htmlReportCommon.trTemplateEditStepPassed;
import static com.automation.bolt.htmlReportCommon.trTemplateEditStepSkipped;
import static com.automation.bolt.htmlReportCommon.trTemplateEditStepWarningKeyword;
import static com.automation.bolt.htmlReportCommon.trTemplateEditStepWarningObject;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

public class boltRunner{
    public static int testRunId;
    public static String testRunStep ="";
    public static String testElement ="";
    public static String testData ="";
    public static String testDescription ="";
    public static String elmLocator ="";
    public static String elmLocatorValue ="";
    public static WebElement getElement =null;
    public static String logError;
    public static String testStepResult;
    public static String saveStepResult;
    public static boolean keywordInvalid;
    public static String getTestId;
    public static String getTestDesc;
    public static ExecuteRegressionSuite exeRegSuite = new ExecuteRegressionSuite();
    public static String testRunStatus;
    public static String failTestRunstatus;
    public static String passTestRunstatus;
    public static String warningTestRunstatus;
    public static int getCurrRunId;
    public static String getSkipStep;
    public static Integer stepIndex;
    public static Integer stepTestNumber;
    public static Date stepStartTime;
    public static Date stepEndTime;
    public static Date lastStepEndTime;
    public static boolean checkForStepTime; 
    public static DateTimeFormatter formatter;
    public static String stepExecTimeInterval;
    public static String formattedTime;
    
    public static LinkedHashMap<Integer, String> getTestSteps = new LinkedHashMap<Integer, String>();
    public static LinkedHashMap<Integer, ArrayList<String>> getTestFlowSteps = new LinkedHashMap<Integer, ArrayList<String>>();

    public static LinkedHashMap<Integer, String> getTestStep = new LinkedHashMap<Integer, String>();
    @SuppressWarnings("rawtypes")
    public static LinkedHashMap<Integer, LinkedHashMap> getTestFlow = new LinkedHashMap<Integer, LinkedHashMap>();

    public static LinkedHashMap<Integer, String> failTestStep = new LinkedHashMap<Integer, String>();
    @SuppressWarnings("rawtypes")
    public static LinkedHashMap<Integer, LinkedHashMap> failTestFlow = new LinkedHashMap<Integer, LinkedHashMap>();

    public static LinkedHashMap<Integer, String> resTestStep = new LinkedHashMap<Integer, String>();
    @SuppressWarnings("rawtypes")
    public static LinkedHashMap<Integer, LinkedHashMap> resTestFlow = new LinkedHashMap<Integer, LinkedHashMap>();
    public static HashMap<Integer, List<WebElement>> lhm = new HashMap<Integer, List<WebElement>>();
    
    public static LinkedHashMap<Integer, String> trTestSteps = new LinkedHashMap<Integer, String>();
    public static LinkedHashMap<Integer, String> trTestCase= new LinkedHashMap<Integer, String>();
    public static LinkedHashMap<Integer, String> trTestCards= new LinkedHashMap<Integer, String>();
    public static LinkedHashMap<Integer, String> userDefineSteps = new LinkedHashMap<Integer, String>();
    
    public static LinkedHashMap<Integer, String> testResult = new LinkedHashMap<Integer, String>();
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void boltTestRunner() throws InterruptedException, IOException {
        
        for(Entry<Integer, LinkedHashMap> runTestFlow:common.mapTestFlows.entrySet()){
        	checkForStepTime =false;
        	lastStepEndTime = null;
        	stepStartTime = null;
        	stepEndTime = null;
        			
            ArrayList<String> loadTestSteps = new ArrayList<String>();
            getTestFlowSteps = new LinkedHashMap<Integer, ArrayList<String>>();
            
            if(stopExecution ==true)
                break;
            
            testRunId =runTestFlow.getKey();
            testRunStatus ="";
            failTestRunstatus ="";
            passTestRunstatus ="";
            warningTestRunstatus ="";
            
            loadTestSteps =new ArrayList<String>();
            loadTestSteps =(ArrayList<String>) runTestFlow.getValue().get(1);
                    
            getTestId =loadTestSteps.get(0);
            getTestDesc =loadTestSteps.get(4);
                
            boltExecutor.log.info("");
            boltExecutor.log.info("......running test "+testRunId);
            
            getCurrRunId =0;
            for(int i=0; i<ExecuteRegressionSuite.importDataFromExcelModel.getRowCount(); i++){
                if(ExecuteRegressionSuite.importDataFromExcelModel.getValueAt(i, 1).toString().contentEquals(getTestId)){
                    getCurrRunId =i;
                    break;
                }
            }
            
            try {
            	 ExecuteRegressionSuite.tableExecuteRegSuite.setRowSelectionInterval(getCurrRunId, getCurrRunId);            
                 ExecuteRegressionSuite.tableExecuteRegSuite.scrollRectToVisible(ExecuteRegressionSuite.tableExecuteRegSuite.getCellRect(ExecuteRegressionSuite.tableExecuteRegSuite.getSelectedRow(), 0, true));
                 ExecuteRegressionSuite.tableExecuteRegSuite.requestFocus();
                 
                 ExecuteRegressionSuite.importDataFromExcelModel.setValueAt("Running...", getCurrRunId, 3);
                 glueCode.getWebDriver(ExecuteRegressionSuite.testRunBrowser);
            }catch(IllegalArgumentException exp) {
            	glueCode.runHeadless =Boolean.valueOf(constants.runMode);
            	glueCode.getWebDriver(constants.testRunBrowser);
            }
      
            getTestFlowSteps =common.mapTestFlows.get(testRunId);      
            common.createTestResult();

            getTestStep = new LinkedHashMap<Integer, String>();
            resTestStep = new LinkedHashMap<Integer, String>();
            failTestStep = new LinkedHashMap<Integer, String>();
            trTestSteps = new LinkedHashMap<Integer, String>();
            
            for(Map.Entry<Integer, ArrayList<String>> testStep:getTestFlowSteps.entrySet()) {
                if(stopExecution ==true)
                    break;
                
                loadTestSteps = new ArrayList<String>();
                loadTestSteps =testStep.getValue();
                 
                boolean executeStep =true;
                boolean testFlowFnd =false;
                boolean testElmFnd =false;
                boolean testElmCreated =false;
                boolean testStepSkipped =false;
                String elm ="";
                String getAttributeName ="";
                String getAttributeValue ="";
                String getActionName ="";
                String getActionValue ="";
                
                getElement = null;
                testElement = null;
                testData = "";
                logError = "";
                userDefineSteps = new LinkedHashMap<Integer, String>();
                stepIndex =0;
                stepTestNumber =testStep.getKey();
                
                try{
                    testRunStep =loadTestSteps.get(1);
                }catch(ArrayIndexOutOfBoundsException exp){
                    testRunStep ="";
                }
                
                keywordInvalid = true;

                try {
                    testElement =loadTestSteps.get(2);
                }catch (ArrayIndexOutOfBoundsException | NullPointerException exp) {
                    testElement ="";
                }

                try {
                    testData =loadTestSteps.get(3);
                }catch (ArrayIndexOutOfBoundsException | NullPointerException exp) {
                    testData ="";
                }
                
                try {
                    testDescription =loadTestSteps.get(4);
                }catch (ArrayIndexOutOfBoundsException | NullPointerException exp) {
                    testDescription ="";
                }
                
                try {
                    getSkipStep =loadTestSteps.get(0);
                }catch (ArrayIndexOutOfBoundsException | NullPointerException exp) {
                    getSkipStep ="";
                }
                
                if(getSkipStep.contentEquals("#")){
                	common.writeTestStep(testStep.getKey().toString(), testElement, testData, testDescription);
                    resTestStep.put(testStep.getKey(), "SKIPPED");
                    common.writeTestStepWithResult(common.docx, testStepResult,"SKIPPED",testRunStep);
                    String trSkipped =trTemplateEditStepSkipped(htmlReportCommon.trTemplateSkipped, testStep.getKey().toString(), common.getTestStepFromString(testStepResult));
                    
                    trTestSteps.put(testStep.getKey(), trSkipped);
                    getTestStep.put(testStep.getKey(), saveStepResult);
                    testStepSkipped =true;
                }
                
                if(testStepSkipped ==false){
                    for(int i=0; i<comboBoxTestFlow.getItemCount(); i++){
                        if(comboBoxTestFlow.getItemAt(i).contentEquals(testRunStep.toUpperCase())){
                            testFlowFnd =true;
                            break;
                        }
                    }

                    if(testFlowFnd ==false){
                        writeTestStep(testStep.getKey().toString(), testElement, testData, testDescription);
                        resTestStep.put(testStep.getKey(), "WARNING");
                        common.writeTestStepWithResult(common.docx, testStepResult,"WARNING",testRunStep);
                        String trWarning =trTemplateEditStepWarningKeyword(htmlReportCommon.trTemplateKeywordWarning, testStep.getKey().toString(), common.getTestStepFromString(testStepResult));
                        trTestSteps.put(testStep.getKey(), trWarning);
                        
                        boltExecutor.log.warn(testStepResult+ ": WARNING");
                        warningTestRunstatus ="WARNING";
                    }
                }
                
                formatter = DateTimeFormatter.ofPattern("mm:ss:SS");
                
                if(checkForStepTime ==false)
                	stepStartTime = new Date();
                else if (checkForStepTime ==true)
                	stepStartTime = lastStepEndTime;
                	
                if(testFlowFnd ==true){
                    if(!testRunStep.toUpperCase().contentEquals("URL") &&
                        !testRunStep.toUpperCase().contentEquals("HARD_WAIT") &&    
                        !testRunStep.toUpperCase().contentEquals("OPEN_NEW_WINDOW") &&
                        !testRunStep.toUpperCase().contentEquals("MOVE_TO_WINDOW") &&
                        !testRunStep.toUpperCase().contentEquals("SWITCH_WINDOW") &&
                        !testRunStep.toUpperCase().contentEquals("SWITCH_IFRAME") &&
                        !testRunStep.toUpperCase().contentEquals("SWITCH_DEFAULT") &&
                        !testRunStep.toUpperCase().contentEquals("USER_DEFINE") &&
                        !testRunStep.toUpperCase().contentEquals("ALERT_SUBMIT") &&
                        !testRunStep.toUpperCase().contentEquals("ROBOT_EVENTS")
                        ){
                        
                        if(testRunStep.toUpperCase().contentEquals("TAKE_SCREENSHOT") && testElement.isEmpty()){
                            testElmFnd =true;
                        }else if(testRunStep.toUpperCase().contentEquals("ROBOT_SCREENSHOT")){
                            testElmFnd =true;
                        }else{
                            if(!testRunStep.toUpperCase().contentEquals(":GET") &&
                                    !testRunStep.toUpperCase().contentEquals(":IF") &&
                                    !testRunStep.toUpperCase().contentEquals(":THEN") &&
                                    !testRunStep.toUpperCase().contentEquals(":START") &&
                                    !testRunStep.toUpperCase().contentEquals(":STOP")){
                                elm = common.getElementFromObjectRepository(testElement);
                                if(elm ==null) {
                                    writeTestStep(testStep.getKey().toString(), testElement, testData, testDescription);
                                    resTestStep.put(testStep.getKey(), "WARNING");
                                    common.writeTestStepWithResult(common.docx, testStepResult,"WARNING",testRunStep);
                                    common.writeTestStepWithResult(common.docx, logError,"ERROR",testRunStep);
                                    
                                    String trWarning =trTemplateEditStepWarningObject(htmlReportCommon.trTemplateOjbectWarning, testStep.getKey().toString(), common.getTestStepFromString(testStepResult), logError);
                                    trTestSteps.put(testStep.getKey(), trWarning);
                                    
                                    boltExecutor.log.warn(testStepResult+ ": WARNING");
                                    warningTestRunstatus ="WARNING";
                                }else
                                    testElmFnd =true;
                            }else if(testRunStep.toUpperCase().contentEquals(":GET") ||
                                    testRunStep.toUpperCase().contentEquals(":IF")||
                                    testRunStep.toUpperCase().contentEquals(":THEN") ||
                                    !testRunStep.toUpperCase().contentEquals(":START") ||
                                    !testRunStep.toUpperCase().contentEquals(":STOP")){
                                testElmFnd =true;
                            }
                        }
                    }else
                        testElmFnd =true;
                }
                
                if((testFlowFnd ==true && (testRunStep.toUpperCase().contentEquals("TAKE_SCREENSHOT") || testRunStep.toUpperCase().contentEquals("ROBOT_SCREENSHOT")) && 
                    !testElement.isEmpty() && testElmFnd==true) || testFlowFnd ==true && testElmFnd==true &&
                    !testRunStep.toUpperCase().contentEquals("URL") &&
                    !testRunStep.toUpperCase().contentEquals("HARD_WAIT") &&
                    !testRunStep.toUpperCase().contentEquals("OPEN_NEW_WINDOW") &&
                    !testRunStep.toUpperCase().contentEquals("MOVE_TO_WINDOW") &&
                    !testRunStep.toUpperCase().contentEquals("SWITCH_WINDOW") &&
                    !testRunStep.toUpperCase().contentEquals("SWITCH_IFRAME") &&
                    !testRunStep.toUpperCase().contentEquals("USER_DEFINE") &&
                    !testRunStep.toUpperCase().contentEquals("SWITCH_DEFAULT") &&
                    !testRunStep.toUpperCase().contentEquals("ALERT_SUBMIT") &&    
                    !testRunStep.toUpperCase().contentEquals("ROBOT_EVENTS") &&
                    !testRunStep.toUpperCase().contentEquals("ROBOT_SCREENSHOT") &&    
                    !testRunStep.toUpperCase().contentEquals("TAKE_SCREENSHOT")    
                    ){
                    
                    try{
                        elmLocator = elm.split("[|]")[0];
                        elmLocatorValue = elm.split(elmLocator)[1];
                        elmLocatorValue = elmLocatorValue.replaceFirst("[|]", "");
                    }catch(NullPointerException | ArrayIndexOutOfBoundsException exp){}
                    
                    if(!testRunStep.toUpperCase().contentEquals(":GET")&&
                            !testRunStep.toUpperCase().contentEquals(":IF")&&
                            !testRunStep.toUpperCase().contentEquals(":THEN") &&
                            !testRunStep.toUpperCase().contentEquals(":START") &&
                            !testRunStep.toUpperCase().contentEquals(":STOP")){
                        getElement = getElement(elmLocator, elmLocatorValue);

                        if(getElement ==null) {
                            writeTestStep(testStep.getKey().toString(), testElement, testData, testDescription);
                            resTestStep.put(testStep.getKey(), "FAIL");
                            failTestStep.put(testStep.getKey(), logError);

                            common.writeTestStepWithResult(common.docx, testStepResult,"FAIL",testRunStep);
                            common.writeTestStepWithResult(common.docx, logError,"ERROR",testRunStep);
                            
                            if(glueCode.keyTakeScreenShot())
                                common.addScreenShotToDoc(common.docx,glueCode.screenshotPath);
                            
                            String trFailed =trTemplateEditStepFailed(htmlReportCommon.trTemplateStepFailed, 
                            		testStep.getKey().toString(), 
                            		common.getTestStepFromString(testStepResult), 
                            		logError, glueCode.dataUrl); 
                            		//glueCode.screenshotPath);
                            trTestSteps.put(testStep.getKey(), trFailed);
                            
                            boltExecutor.log.info(testStepResult+ ": FAIL");
                            boltExecutor.log.error(logError);
                            failTestRunstatus ="FAIL";
                            break;
                        }else
                            testElmCreated =true;
                    }else if(!testRunStep.toUpperCase().contentEquals(":START") || 
                            !testRunStep.toUpperCase().contentEquals(":STOP") || 
                            testRunStep.toUpperCase().contentEquals(":IF") ||
                            testRunStep.toUpperCase().contentEquals(":THEN") ||
                            testRunStep.toUpperCase().contentEquals(":GET")){
               
                        try {
                            getAttributeName =testData.split("=")[0].trim();
                        }catch (ArrayIndexOutOfBoundsException exp) {
                            getAttributeName ="";
                        }

                        try {
                            getAttributeValue =testData.split("=")[1].trim();
                        }catch (ArrayIndexOutOfBoundsException exp) {
                            getAttributeValue ="";
                        }
                        
                        try {
                            getActionName =testDescription.split("=")[0].trim();
                        }catch (ArrayIndexOutOfBoundsException exp) {
                            getActionName ="";
                        }
                        
                        try {
                            getActionValue =testDescription.split("=")[1].trim();
                        }catch (ArrayIndexOutOfBoundsException exp) {
                            getActionValue ="";
                        }
                        
                        testElmCreated =true;
                    }
                }else if(testFlowFnd ==true && testElmFnd==true)
                    testElmCreated =true;
                
                
                if(testElmCreated ==true){
                    if(testData.toLowerCase().contentEquals(":runtime")){
                        if(checkTestDataForRunTime() ==false)
                            executeStep =false;
                    }else if(testData.toLowerCase().contentEquals(":u-value")){
                        common.runTimmerFromCurrentTime(1);
                        createRunTimeTestData();
                    }
                
                    if(!testRunStep.contentEquals("GET")){
                        writeTestStep(testStep.getKey().toString(), testElement, testData, testDescription);
                    }
                         
                    if(executeStep ==true){
                        if(!testRunStep.contentEquals(":START") &&
                                !testRunStep.contentEquals(":STOP") &&
                                !testRunStep.contentEquals(":IF") &&
                                !testRunStep.contentEquals(":GET") &&
                                !testRunStep.contentEquals(":THEN")){
                            executeTestStep(testRunStep, testData, getElement, testElement, testStep, testDescription);
                        }else
                            executeUserDefineTestStep(testRunStep, testElement, getAttributeName, getAttributeValue, getActionName, testStep,
                                    getActionName, getActionValue, testDescription);
                    }
                    
                    stepEndTime = new Date();
                    lastStepEndTime = new Date();
                    checkForStepTime =true;
                    
                    stepExecTimeInterval = getTimeInterval(stepStartTime, stepEndTime);
                    
                    if(glueCode.stepSuccess ==false) {
                        resTestStep.put(testStep.getKey(), "FAIL");
                        failTestStep.put(testStep.getKey(), logError);

                        common.writeTestStepWithResult(common.docx, testStepResult,"FAIL",testRunStep);
                        common.writeTestStepWithResult(common.docx, logError,"ERROR",testRunStep);
                        
                        if(glueCode.keyTakeScreenShot())
                            common.addScreenShotToDoc(common.docx,glueCode.screenshotPath);
                        
                        String trFailed =trTemplateEditStepFailed(htmlReportCommon.trTemplateStepFailed, 
                        		testStep.getKey().toString(), 
                        		common.getTestStepFromString(testStepResult), 
                        		logError, glueCode.dataUrl);
                        		//glueCode.screenshotPath);
                        trTestSteps.put(testStep.getKey(), trFailed);
                        
                        boltExecutor.log.info(testStepResult+ ": FAIL");
                        boltExecutor.log.error(logError);
                        failTestRunstatus ="FAIL";
                        
                        break;
                    }else if(glueCode.stepSuccess ==true){
                        resTestStep.put(testStep.getKey(), "PASS");
                        passTestRunstatus ="PASS";
                        String trPassed ="";
                        
                        if((!testRunStep.toUpperCase().contentEquals("TAKE_SCREENSHOT") && !testRunStep.toUpperCase().contentEquals("ROBOT_SCREENSHOT")) &&
                                !testRunStep.toUpperCase().contentEquals(":START") &&
                                !testRunStep.toUpperCase().contentEquals(":STOP")) {
                            boltExecutor.log.info(testStepResult+ ": PASS");
                            common.writeTestStepWithResult(common.docx, testStepResult,"PASS",testRunStep);
                            
                            if((testRunStep.toUpperCase().contentEquals(":IF") ||
                                    testRunStep.toUpperCase().contentEquals(":THEN")) &&
                                    testDescription.toLowerCase().contentEquals("take_screenshot")){
                                
                                trPassed =trTemplateEditScreenShot(htmlReportCommon.trTemplateScreenShot, 
                                		testStep.getKey().toString(), 
                                		common.getTestStepFromString(testStepResult), glueCode.dataUrl);  
                                		//glueCode.screenshotPath);
                            }else{
                                trPassed =trTemplateEditStepPassed(htmlReportCommon.trTemplatePassed, 
                                		testStep.getKey().toString(), 
                                		common.getTestStepFromString(testStepResult));
                            }
                                
                            trTestSteps.put(testStep.getKey(), trPassed);
                        }else {
                            boltExecutor.log.info(testStepResult+ ": "+glueCode.screenshotPath);
                            common.writeTestStepWithResult(common.docx, testStepResult+ ":","screenshot",testRunStep);
                            String trScreenShot ="";
                            if(testRunStep.toUpperCase().contentEquals("TAKE_SCREENSHOT") || testRunStep.toUpperCase().contentEquals("ROBOT_SCREENSHOT"))
                                trScreenShot =trTemplateEditScreenShot(htmlReportCommon.trTemplateScreenShot, 
                                		testStep.getKey().toString(), 
                                		common.getTestStepFromString(testStepResult), glueCode.dataUrl); 
                                		//glueCode.screenshotPath);
                            else
                                trScreenShot =trTemplateEditStepPassed(htmlReportCommon.trTemplatePassed, 
                                		testStep.getKey().toString(), 
                                		common.getTestStepFromString(testStepResult));
                            trTestSteps.put(testStep.getKey(), trScreenShot);
                        }
                    }
                }
                getTestStep.put(testStep.getKey(), saveStepResult);
            }    
            
            if(stopExecution ==true)
                return;
                
            if(failTestRunstatus.contentEquals("FAIL"))
                testRunStatus ="FAIL";
            else if(warningTestRunstatus.contentEquals("WARNING"))
                testRunStatus ="WARNING";
            else if(passTestRunstatus.contentEquals("PASS"))
                testRunStatus ="PASS";
            
            String trGetSteps =concatenateHashMapDataWithNewLine(trTestSteps);
            String cardTestCase =htmlReportCommon.trTemplateEditCard(htmlReportCommon.trTemplateCard, testRunStatus, getTestId,"[Test ID: "+getTestId+"] "+getTestDesc); 
            cardTestCase =cardTestCase.replace("$stepRows", trGetSteps);
            
            testResult.put(testRunId, testRunStatus);
            
            try {
            	ExecuteRegressionSuite.importDataFromExcelModel.setValueAt(testRunStatus, getCurrRunId, 3);
            }catch(ArrayIndexOutOfBoundsException exp) {}
            
            getTestFlow.put(runTestFlow.getKey(), getTestStep);
            resTestFlow.put(runTestFlow.getKey(), resTestStep);
            failTestFlow.put(runTestFlow.getKey(), failTestStep);
            try{
                glueCode.boltDriver.quit();
            }catch(Exception exp){
                boltExecutor.log.error("web driver error: " +exp.getMessage());
            }
            common.saveAndCloseDocFile();
            trTestCards.put(Integer.valueOf(getTestId), cardTestCase);
        }
         
        if(stopExecution ==true){
            try{
                glueCode.boltDriver.quit();
            }catch(NullPointerException exp){
                boltExecutor.log.error("web driver error: " +exp.getMessage());
            }
        }
    }
    
    public static void createRunTimeTestData(){
         String getTestStep ="";
         String getRunTimeTestData =createUniqueID();
         
        try{
            getTestStep =String.valueOf(testDescription.trim().charAt(0));
            if(getTestStep.contentEquals(":")){
                testData =testDescription.substring(1).trim() +"_"+ getRunTimeTestData;
            }else
                testData =getRunTimeTestData;
                
        }catch(ArrayIndexOutOfBoundsException exp){
            testData =getRunTimeTestData;
        }catch(IndexOutOfBoundsException exp){
            testData =getRunTimeTestData;
        }
    }
    
    public static boolean checkTestDataForRunTime(){
        boolean getTxt =true;
        String getTestStep ="";
        int getTestIndex =0;
        
        if(testDescription.isEmpty())
            getTxt =false;

        if(getTxt ==true){
            try{
                getTestStep =testDescription.split("[:]")[1];
            }catch(ArrayIndexOutOfBoundsException exp){
               getTxt =false; 
            }
        }

        if(getTxt ==true){
            try{
                getTestIndex =Integer.parseInt(getTestStep);
            }catch(NumberFormatException exp){
                getTxt =false; 
            }   
        }

        if(getTxt ==true){
            try{
                testData =getTestFlowSteps.get(getTestIndex).get(3);
            }catch(NullPointerException exp){
                getTxt =false;
            }catch(ArrayIndexOutOfBoundsException exp){
                testData ="";
            }
        }

        if(getTxt ==false){
            stepSuccess = false;
            boltRunner.logError = "Expected reference ["+testDescription+"] not found to retrieve runtime text";
        }
        return getTxt;
    }
            
    public static WebElement getElement(String elmLocator, String elmLocatorValue) {
        WebElement elm =null;
        try {
            if (elmLocator.toLowerCase().contentEquals("id"))
                elm = glueCode.boltDriver.findElement(By.id(elmLocatorValue));
            else if (elmLocator.toLowerCase().contentEquals("xpath"))
                elm = glueCode.boltDriver.findElement(By.xpath(elmLocatorValue));
        }catch(NoSuchElementException exp) {
                logError = exp.getMessage();
        }catch(WebDriverException exp) {
                logError = exp.getMessage();
        }
        return elm;
    }
	
    public static String getRunTimeText(String readTxt, LinkedHashMap<Integer, String> getTestStep){
        String xtestStep ="";
        String getNewText="";
        
        try{
            xtestStep =readTxt.split("[:]")[1];
        }catch(ArrayIndexOutOfBoundsException exp){
            
        }
        
        try{
            getNewText =getTestStep.get(Integer.parseInt(xtestStep)).split("[|]")[2].trim();
        }catch (NumberFormatException exp){
            
        }catch(ArrayIndexOutOfBoundsException exp){
            getNewText ="";
        }
        return getNewText;   
    }
    
    public static String updateGetTextWithTestData(String testStep, String newText){
        String getReplaceText ="";
        int secondOccurance = 0;
        int indx =0;
        int indxy =0;
        
        for(int i=0; i<testStep.length(); i++){
            if(testStep.charAt(i) =='|'){
               indx++; 
            }
            
            if(indx ==2){
                secondOccurance =i+1;
                for(int j=i+1; j<testStep.length(); j++){
                    indxy++;
                    if(testStep.charAt(j) =='|'){
                        break;
                    }
                }                      
                break;
            }   
        }
        
        getReplaceText =testStep.substring(0, secondOccurance) +newText+testStep.substring((secondOccurance+indxy)-1);
        return getReplaceText;
    }
    
    public static String getTimeInterval(Date startTime, Date endTime) {
        long diffInMillis = endTime.getTime() - startTime.getTime();
        long hours = TimeUnit.MILLISECONDS.toHours(diffInMillis);
        diffInMillis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis);
        diffInMillis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(diffInMillis);
        diffInMillis -= TimeUnit.SECONDS.toMillis(seconds);
        long milliseconds = diffInMillis;
        return String.format("%02d:%02d:%03d", minutes, seconds, milliseconds);
    }
}