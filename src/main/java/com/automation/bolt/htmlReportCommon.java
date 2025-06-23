/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.bolt;

import static com.automation.bolt.gui.ExecuteRegressionSuite.excelFile;
import static com.automation.bolt.gui.ExecuteRegressionSuite.importDataFromExcelModel;
import static com.automation.bolt.gui.ExecuteRegressionSuite.arrTestId;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author zakir
 */
public class htmlReportCommon {
    
    public static String trTemplatePassed ="							  <tr class=\"$tableStatus\">\n" +
    "								 <td>$testId</td>\n" +
    "								 <td>$testStep</td>\n" +
    "								 <td>$testDuration</td>\n" +
    "							  </tr>";
    
    public static String trTemplateUserDefine ="							  <tr class=\"$tableStatus\">\n" +
    	    "								 <td>$testId</td>\n" +
    	    "								 <td>$testStep</td>\n" +
    	    "								 <td>$testDuration</td>\n" +
    	    "							  </tr>";
    
    public static String trTemplateScreenShot ="							  <tr class=\"$tableStatus\">\n" +
    "                                 <td>$testId</td>\n" +
    "                                 <td>$testStep<img src=\"$screenShotFilePath\" width=\"100%\" height=\"auto\"></td>\n" +
    "								 <td>$testDuration</td>\n" +
    "                              </tr>";
    
    public static String trTemplateScreenShotUserDefine ="							  <tr class=\"$tableStatus\">\n" +
    	    "                                 <td>$testId</td>\n" +
    	    "                                 <td><img src=\"$screenShotFilePath\" width=\"100%\" height=\"auto\"></td>\n" +
    	    "                              </tr>";

    public static String trTemplateStepFailed ="                              <tr class=\"$tableStatus\">\n" +
    "                                 <td>$testId</td>\n" +
    "                                 <td>$testStep<br><font style=\"color:blue\">$stepError</font>\n" +
    "								 <img src=\"$screenShotFilePath\" width=\"100%\" height=\"auto\">\n" +
    "								 </td>\n" +
    "								 <td>$testDuration</td>\n" +
    "                              </tr>";

    public static String trTemplateKeywordWarning ="							  <tr class=\"$tableStatus\">\n" +
    "								 <td>$testId</td>\n" +
    "								 <td>$testStep</td>\n" +
    "								 <td>$testDuration</td>\n" +
    "							  </tr>";

    public static String trTemplateOjbectWarning ="                             <tr class=\"$tableStatus\">\n" +
    "                                 <td>$testId</td>\n" +
    "                                 <td>$testStep<br><font style=\"color:brown\">$stepError</font>\n" +
    "								 </td>\n" +
    "								 <td>$testDuration</td>\n" +
    "                              </tr>";

    public static String trTemplateSkipped ="							  <tr class=\"$tableStatus\">\n" +
    "								 <td>$testId</td>\n" +
    "								 <td>$testStep</td>\n" +
    "								 <td>$testDuration</td>\n" +
    "							  </tr>";
    
    public static String trTemplateCard="            <div class=\"card\">\n" +
    "               <div class=\"card-header\" style=\"background-color: $testResultColor\">\n" +
    "                  <a class=\"collapsed btn\" data-bs-toggle=\"collapse\" href=\"#TestCase-$collapseIndex\" style=\"width:100%\">\n" +
    "                  <h6 align=\"left\">$testDescription</h6>\n" +
    "                  </a>\n" +
    "               </div>\n" +
    "               <div id=\"TestCase-$collapseIndex\" class=\"collapse\" data-bs-parent=\"#accordion\">\n" +
    "                  <div class=\"card-body\">\n" +
    "                     <div class=\"container mt-3\">\n" +
    "                        <table class=\"table\">\n" +
    "                           <thead>\n" +
    "                              <tr class=\"table-dark\">\n" +
    "                                 <th style=\"width:8%\">Step No.</th>\n" +
    "                                 <th>Test Step</th>\n" + 
    "                                 <th style=\"width:15%\">Duration</th>\n" +
    "                              </tr>\n" +
    "                           </thead>\n" +
    "                           <tbody>\n" +
    "							  $stepRows\n" +
    "                           </tbody>\n" +
    "                        </table>\n" +
    "                     </div>\n" +
    "                  </div>\n" +
    "               </div>\n" +
    "            </div>";
    
    public static String trTestContainer ="      <div class=\"container mt-3\">\n" +
    "         <div id=\"accordion\">\n" +
    "            $testCards\n" +
    "         </div>\n" +
    "      </div>";
    
    String x="<style>\n" +
"		@keyframes example {\n" +
"		0% {color: red;}\n" +
"		100% {color: white;}\n" +
"		}\n" +
"	</style>	";
    
    public static String htmlTestReport ="<!DOCTYPE html>\n" +
    "<html lang=\"en\">\n" +
    "   <head>\n" +
    "      <title>Bolt-TestRunReport</title>\n" +
    "      <meta charset=\"UTF-8\">\n" +
    "      <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
    "      <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
    "      <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js\"></script>\n" +
    "      <script src=\"https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js\"></script>\n" +
    "      <link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.7.0/css/all.css' integrity='sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ' crossorigin='anonymous'>\n" +
    "   <style>\n" +
    "		@keyframes example {\n" +
    "		0% {color: red;}\n" +
    "		100% {color: white;}\n" +
    "		}\n" +
    "		table \r\n" + 
    "         {\r\n" + 
    "         table-layout:fixed;\r\n" + 
    "         width:100%;\r\n" + 
    "         }"+
    "	</style>	"+
    "   </head>\n" +
    "   <body>\n" +
    "      <div class=\"container-lg mt-3\" style=\"width: 100%;\">\n" +
    "         <h4><b>Bo<i class=\"fa fa-bolt\" style=\"font-size:18px;animation-name:example; position:relative; animation-duration:2s; animation-iteration-count:infinite\"></i>t</b></h1>\n" +
    "         <h6 style=\"color: red\">Test Automation Accelerator</h6>\n" +
    "      </div>\n" +
    "      <div class=\"container-lg mt-3 border\" style=\"width: 100%; height:330px; background-color: #ebebeb\">\n" +
    "         <div style=\"float: left\">\n" +
    "            <p><br>Test Run Date: <font style=\"color: #483d8b\"><b>$runDate </b></font><font style=\"color: #cb4154 \"><b>$runTime</b></font></p>\n" +
    "            <p>Total Run: <font style=\"color: blue\"><b>$totalRun</b></font></p>\n" +
    "            <p>Passed: <font style=\"color: green\"><b>$totalPassed</b></font></p>\n" +
    "            <p>Failed: <font style=\"color: red\"><b>$totalFailed</b></font></p>\n" +
    "            <p>Warning: <font style=\"color: #ff7f50\"><b>$totalWarings</b></font></p>\n" +
    "            <p>Run Time: <font style=\"color: #8b0000\"><b>$totalHrs</b></font> hrs <font style=\"color: #ff6347\"><b>$totalMins</b></font> mins <font style=\"color: #6495ed\"><b>$totalSec</b></font> sec</p>\n" +
    "            <p>Test Suite: <font style=\"color: #e5aa70\"><b>$testSuiteName</b></font></p>\n" +
    "         </div>\n" +
    "         <canvas id=\"myChart\" style=\"max-width:600px; float: right;\"></canvas>\n" +
    "      </div>\n" +
    "      $testCaseSteps\n" +
    "      <script>\n" +
    "         var xValues = [\"Pass\", \"Fail\", \"Warning\"];\n" +
    "         var yValues = [$totalPassed, $totalFailed, $totalWarings];\n" +
    "         var barColors = [\n" +
    "           \"#8fbc8f\",\n" +
    "           \"#ffc0cb\",\n" +
    "           \"#fffacd\",\n" +
    "         ];\n" +
    "         \n" +
    "         new Chart(\"myChart\", {\n" +
    "           type: \"doughnut\",\n" +
    "           data: {\n" +
    "             labels: xValues,\n" +
    "             datasets: [{\n" +
    "               backgroundColor: barColors,\n" +
    "               data: yValues\n" +
    "             }]\n" +
    "           },\n" +
    "           options: {\n" +
    "             title: {\n" +
    "               display: true,\n" +
    "               text: \"Test Run Graph Summary\"\n" +
    "             }\n" +
    "           }\n" +
    "         });\n" +
    "      </script>\n" +
    "   </body>\n" +
    "</html>";
    
    public static String htmlTestReportHardStop="";     
    public static String htmlTestReportPath =constants.userDir+"/htmlTestReport";
    public static String tesCaseResultPath =constants.userDir+"/testResults";
    public static SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
    public static SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
    public static boolean testReportFolderExist;
    public static boolean testReportFolderEmpty;
    public static boolean testCaseReportFolderExist;
    public static boolean testCaseReportFolderEmpty;
    public static boltRunner bRunner;
    public static Integer stepIndex=0;
    
    public static void writeStepInHtmlReport(String addStep) {
    	boltRunner.userDefineSteps.put(stepIndex++, addStep);
    }
    
    public static void attachScreenShotInHtmlReport() {
    	glueCode.keyTakeScreenShot();
    	boltRunner.userDefineSteps.put(stepIndex++, "~take~screenshot~"+glueCode.screenshotPath);
    }
    
    public static String trTemplateEditTestRunInfo(String trTemplate, String testRunDateTime, String totalRun, String runHrs, String runMins, String runSecs){
        
        String editedTemplate =trTemplate;
                
        editedTemplate =editedTemplate.replace("$runDate", testRunDateTime.split(" ")[0]);
        editedTemplate =editedTemplate.replace("$runTime", testRunDateTime.split(" ")[1]);
        editedTemplate =editedTemplate.replace("$totalRun", String.valueOf(getTotalRunCount()));
        editedTemplate =editedTemplate.replace("$totalPassed", String.valueOf(getTheStatusCount("PASS")));
        editedTemplate =editedTemplate.replace("$totalFailed", String.valueOf(getTheStatusCount("FAIL")));
        editedTemplate =editedTemplate.replace("$totalWarings", String.valueOf(getTheStatusCount("WARNING")));
        editedTemplate =editedTemplate.replace("$totalHrs", String.valueOf(runHrs));
        editedTemplate =editedTemplate.replace("$totalMins", String.valueOf(runMins));
        editedTemplate =editedTemplate.replace("$totalSec", String.valueOf(runSecs));
        editedTemplate =editedTemplate.replace("$testSuiteName", excelFile.getName());
        
        return editedTemplate;
    }   
    
    public static String updateErrorMessageForHardStopAndWebDriverException(String errorMsg){
        htmlTestReportHardStop ="<!DOCTYPE html>\n" +
        "<html lang=\"en\">\n" +
        "   <head>\n" +
        "      <title>Bolt-TestRunReport</title>\n" +
        "      <meta charset=\"utf-8\">\n" +
        "      <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
        "      <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
        "      <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js\"></script>\n" +
        "      <script src=\"https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js\"></script>\n" +
        "      <link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.7.0/css/all.css' integrity='sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ' crossorigin='anonymous'>\n" +
        "		<script type=\"text/javascript\">\n" +
        "			window.alert(\""+ errorMsg +"\")\n" +
        "		</script>\n"+  
        "   <style>\n" +
        "		@keyframes example {\n" +
        "		0% {color: red;}\n" +
        "		100% {color: white;}\n" +
        "		}\n" +
        "		table \r\n" + 
        "         {\r\n" + 
        "         table-layout:fixed;\r\n" + 
        "         width:100%;\r\n" + 
        "         }"+
        "	</style>	"+
        "   </head>\n" +
        "   <body>\n" +
        "      <div class=\"container-lg mt-3\" style=\"width: 100%;\">\n" +
        "         <h4><b>Bo<i class=\"fa fa-bolt\" style=\"font-size:18px;animation-name:example; position:relative; animation-duration:2s; animation-iteration-count:infinite\"></i>t</b></h1>\n" +
        "         <h6 style=\"color: red\">Test Automation Accelerator</h6>\n" +
        "      </div>\n" +
        "      <div class=\"container-lg mt-3 border\" style=\"width: 100%; height:330px; background-color: #ebebeb\">\n" +
        "         <div style=\"float: left\">\n" +
        "            <p><br>Test Run Date: <font style=\"color: #483d8b\"><b>$runDate </b></font><font style=\"color: #cb4154 \"><b>$runTime</b></font></p>\n" +
        "            <p>Total Run: <font style=\"color: blue\"><b>$totalRun</b></font></p>\n" +
        "            <p>Passed: <font style=\"color: green\"><b>$totalPassed</b></font></p>\n" +
        "            <p>Failed: <font style=\"color: red\"><b>$totalFailed</b></font></p>\n" +
        "            <p>Warning: <font style=\"color: #ff7f50\"><b>$totalWarings</b></font></p>\n" +
        "            <p>Run Time: <font style=\"color: #8b0000\"><b>$totalHrs</b></font> hrs <font style=\"color: #ff6347\"><b>$totalMins</b></font> mins <font style=\"color: #6495ed\"><b>$totalSec</b></font> sec</p>\n" +
        "            <p>Test Suite: <font style=\"color: #e5aa70\"><b>$testSuiteName</b></font></p>\n" +
        "         </div>\n" +
        "         <canvas id=\"myChart\" style=\"max-width:600px; float: right;\"></canvas>\n" +
        "      </div>\n" +
        "      $testCaseSteps\n" +
        "      <script>\n" +
        "         var xValues = [\"Pass\", \"Fail\", \"Warning\"];\n" +
        "         var yValues = [$totalPassed, $totalFailed, $totalWarings];\n" +
        "         var barColors = [\n" +
        "           \"#8fbc8f\",\n" +
        "           \"#ffc0cb\",\n" +
        "           \"#fffacd\",\n" +
        "         ];\n" +
        "         \n" +
        "         new Chart(\"myChart\", {\n" +
        "           type: \"doughnut\",\n" +
        "           data: {\n" +
        "             labels: xValues,\n" +
        "             datasets: [{\n" +
        "               backgroundColor: barColors,\n" +
        "               data: yValues\n" +
        "             }]\n" +
        "           },\n" +
        "           options: {\n" +
        "             title: {\n" +
        "               display: true,\n" +
        "               text: \"Test Run Graph Summary\"\n" +
        "             }\n" +
        "           }\n" +
        "         });\n" +
        "      </script>\n" +
        "   </body>\n" +
        "</html>";
        
        return htmlTestReportHardStop;
    }
            
    public static int getTheStatusCount(String testRunstatus){
    	int countPass =0;
    	
    	for (Integer testId: boltRunner.testResult.keySet()) {
    	    String key = String.valueOf(testId);
    	    String value = boltRunner.testResult.get(testId).toString();
    	    if(value.contentEquals(testRunstatus)){
    			countPass++;
    		}
    	}
    	
    /*	int rowCount =importDataFromExcelModel.getRowCount();
        int colCount =importDataFromExcelModel.getColumnCount();
        
        
        for(int i=0; i<rowCount; i++){
            boolean run = (boolean) importDataFromExcelModel.getValueAt(i, 0);
            if(run ==true){
                if(importDataFromExcelModel.getValueAt(i, colCount-1).toString().contentEquals(testRunstatus)){
                    countPass++;
                }
            }
        }	*/
         return countPass;
    }
    
    public static int getTotalRunCount(){
    	int countRun =0;
    	
    	if(constants.externalRun ==true) {
        	countRun =arrTestId.size();
        	return countRun;
        }
        	
    	int rowCount =importDataFromExcelModel.getRowCount();
        for(int i=0; i<rowCount; i++){
            boolean run = (boolean) importDataFromExcelModel.getValueAt(i, 0);
            if(run ==true)
                countRun++;    
        }
         return countRun;
    }
    
    public static String trTemplateEditStepPassed(String trTemplate, String trTestStepId, String trTestStep){
        String editedTemplate =trTemplate;
        
        if(boltRunner.userDefineSteps.size() !=0) {
        	String editedTemplate1 ="";
        	String editedTemplate2 = "";
        	
        	for (Map.Entry<Integer,String> entrySteps : boltRunner.userDefineSteps.entrySet()) {
        		if(entrySteps.getValue().contains("~take~screenshot~")) {
        			editedTemplate1 = trTemplateScreenShotUserDefine;
        			editedTemplate1 = editedTemplate1.replace("$tableStatus", "table-info");
            		editedTemplate1 = editedTemplate1.replace("$testId", trTestStepId +"."+ (entrySteps.getKey()+1));
            		editedTemplate1 =editedTemplate1.replace("$screenShotFilePath", entrySteps.getValue().split("~take~screenshot~")[1]);
        		}else {
        			editedTemplate1 = trTemplateUserDefine;
            		editedTemplate1 = editedTemplate1.replace("$tableStatus", "table-info");
            		editedTemplate1 = editedTemplate1.replace("$testId", trTestStepId +"."+ (entrySteps.getKey()+1));
            		editedTemplate1 =editedTemplate1.replace("$testStep", entrySteps.getValue());
        		}
        		editedTemplate2 = editedTemplate2 +"\n"+ editedTemplate1;
        	}
        	editedTemplate =editedTemplate.replace("$tableStatus", "table-success");
            editedTemplate =editedTemplate.replace("$testId", trTestStepId);
            editedTemplate =editedTemplate.replace("$testStep", trTestStep);
            editedTemplate =editedTemplate.replace("$testDuration", boltRunner.stepExecTimeInterval);
	        editedTemplate =editedTemplate + editedTemplate2;
        	
        }else {
        	editedTemplate =editedTemplate.replace("$tableStatus", "table-success");
            editedTemplate =editedTemplate.replace("$testId", trTestStepId);
            editedTemplate =editedTemplate.replace("$testStep", trTestStep);
            editedTemplate =editedTemplate.replace("$testDuration", boltRunner.stepExecTimeInterval);
        }
        
        return editedTemplate;
    }
    
    public static String trTemplateEditStepSkipped(String trTemplate, String trTestStepId, String trTestStep){
        String editedTemplate =trTemplate;
        
        editedTemplate =editedTemplate.replace("$tableStatus", "table-secondary");
        editedTemplate =editedTemplate.replace("$testId", trTestStepId);
        editedTemplate =editedTemplate.replace("$testStep", trTestStep);
        
        return editedTemplate;
    }
    
    public static String trTemplateEditStepWarningKeyword(String trTemplate, String trTestStepId, String trTestStep){
        String editedTemplate =trTemplate;
        
        editedTemplate =editedTemplate.replace("$tableStatus", "table-warning");
        editedTemplate =editedTemplate.replace("$testId", trTestStepId);
        editedTemplate =editedTemplate.replace("$testStep", trTestStep);
        
        return editedTemplate;
    }
    
    public static String trTemplateEditStepWarningObject(String trTemplate, String trTestStepId, String trTestStep, String stepError){
        String editedTemplate =trTemplate;
        
        editedTemplate =editedTemplate.replace("$tableStatus", "table-warning");
        editedTemplate =editedTemplate.replace("$testId", trTestStepId);
        editedTemplate =editedTemplate.replace("$testStep", trTestStep);
        editedTemplate =editedTemplate.replace("$stepError", stepError);
        
        return editedTemplate;
    }
    
    public static String trTemplateEditScreenShot(String trTemplate, String trTestStepId, String trTestStep, String filePath){
        String editedTemplate =trTemplate;
        
        editedTemplate =editedTemplate.replace("$tableStatus", "table-success");
        editedTemplate =editedTemplate.replace("$testId", trTestStepId);
        editedTemplate =editedTemplate.replace("$testStep", trTestStep);
        editedTemplate =editedTemplate.replace("$screenShotFilePath", filePath);
        editedTemplate =editedTemplate.replace("$testDuration", boltRunner.stepExecTimeInterval);
                
        return editedTemplate;
    }
    
    public static String trTemplateEditStepFailed(String trTemplate, String trTestStepId, String trTestStep, String stepError, String filePath){
        String editedTemplate =trTemplate;
        
        if(boltRunner.userDefineSteps.size() !=0) {
        	String editedTemplate1 ="";
        	String editedTemplate2 = "";
        	
        	for (Map.Entry<Integer,String> entrySteps : boltRunner.userDefineSteps.entrySet()) {
        		if(entrySteps.getValue().contains("~take~screenshot~")) {
        			editedTemplate1 = trTemplateScreenShotUserDefine;
        			editedTemplate1 = editedTemplate1.replace("$tableStatus", "table-info");
            		editedTemplate1 = editedTemplate1.replace("$testId", trTestStepId +"."+ (entrySteps.getKey()+1));
            		editedTemplate1 =editedTemplate1.replace("$screenShotFilePath", entrySteps.getValue().split("~take~screenshot~")[1]);
        		}else {
        			editedTemplate1 = trTemplateUserDefine;
        			editedTemplate1 = editedTemplate1.replace("$tableStatus", "table-info");
            		editedTemplate1 = editedTemplate1.replace("$testId", trTestStepId +"."+ (entrySteps.getKey()+1));
            		editedTemplate1 =editedTemplate1.replace("$testStep", entrySteps.getValue());
        		}
        		editedTemplate2 = editedTemplate2 +"\n"+ editedTemplate1;
        	}
        	editedTemplate =editedTemplate.replace("$tableStatus", "table-danger");
	        editedTemplate =editedTemplate.replace("$testId", trTestStepId);
	        editedTemplate =editedTemplate.replace("$testStep", trTestStep);
	        editedTemplate =editedTemplate.replace("$stepError", stepError);
	        editedTemplate =editedTemplate.replace("$screenShotFilePath", filePath);
	        editedTemplate =editedTemplate.replace("$testDuration", boltRunner.stepExecTimeInterval);
	        
	        editedTemplate =editedTemplate + editedTemplate2;
        	
        }else {
	        editedTemplate =editedTemplate.replace("$tableStatus", "table-danger");
	        editedTemplate =editedTemplate.replace("$testId", trTestStepId);
	        editedTemplate =editedTemplate.replace("$testStep", trTestStep);
	        editedTemplate =editedTemplate.replace("$stepError", stepError);
	        editedTemplate =editedTemplate.replace("$screenShotFilePath", filePath);
	        editedTemplate =editedTemplate.replace("$testDuration", boltRunner.stepExecTimeInterval);
        }
        
        return editedTemplate;
    }
    
    public static String trTemplateEditCard(String trTemplate, String trTestResult, String trTestId, String trTestDesc){
        String backgroundColor ="";
        String editedTemplate =trTemplate;
        
        switch(trTestResult) {
            case "PASS":
                backgroundColor ="#8fbc8f";
                break;
           case "FAIL":
                backgroundColor ="pink";
                break;
           case "WARNING":
                backgroundColor ="#fffacd";
                break;     
        }        
        
        editedTemplate =editedTemplate.replace("$testResultColor", backgroundColor);
        editedTemplate =editedTemplate.replace("$collapseIndex", trTestId);
        
        char[] newTxtx =trTestDesc.toCharArray();
        int indexCount =0;
        for(char x: newTxtx) {
            indexCount++;
            if(x ==']')
                break;
        }
        trTestDesc =trTestDesc.substring(0, indexCount) +"<i>"+ trTestDesc.substring(indexCount, trTestDesc.length()) +"</i>";     
        editedTemplate =editedTemplate.replace("$testDescription", trTestDesc);
        
        return editedTemplate;
    }
    
    public static String concatenateHashMapDataWithNewLine(LinkedHashMap<Integer, String> trTestSteps){
        String trTSteps = "";
        
        for (Map.Entry<Integer,String> entry : trTestSteps.entrySet()){
            trTSteps =trTSteps + entry.getValue() + "\n";
        }
        
        return trTSteps.replaceAll("([\\r\\n]+$)", "");
    }
    
    public static LinkedHashMap<Integer, String> getSuiteList() {	
        LinkedHashMap<Integer, String> testSuties =new LinkedHashMap<Integer, String>();
        int suiteCount =0;
        String getFileName ="";
        String getNewFileName ="";
        testReportFolderExist =true;
        testReportFolderEmpty =false;
        
        File folder = new File(htmlTestReportPath);
        if(!folder.exists()){
            JOptionPane.showMessageDialog(null, "Required folder [htmlTestReport] not found!", "Alert", JOptionPane.WARNING_MESSAGE);
            testReportFolderExist =false;
            return testSuties;
        }
            
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles ==null || listOfFiles.length ==0){
            testReportFolderEmpty =true;
            return testSuties;
        }
           
        for (File file : listOfFiles) {
            if (file.isFile()) {
                getFileName =file.getName();
                getFileName =getFileName.split("[~]")[0];

                if(!getFileName.contentEquals(getNewFileName) && !getFileName.isEmpty()) {
                    getNewFileName =file.getName();
                    getNewFileName =getFileName.split("[~]")[0];
                    suiteCount++;
                    testSuties.put(suiteCount, getNewFileName);
                }
            }
        }
        return testSuties;
    }
    
    public static LinkedHashMap<Integer, String> getTestCaseResultSuiteList(){
        LinkedHashMap<Integer, String> testSuties =new LinkedHashMap<Integer, String>();
        int suiteCount =0;
        String getFileName ="";
        String getNewFileName ="";
        testCaseReportFolderExist =true;
        testCaseReportFolderEmpty =false;
        
        File folder = new File(tesCaseResultPath);
        if(!folder.exists()){
            JOptionPane.showMessageDialog(null, "Required folder [testResults] not found!", "Alert", JOptionPane.WARNING_MESSAGE);
            testCaseReportFolderExist =false;
            return testSuties;
        }
            
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles ==null || listOfFiles.length ==0){
            testCaseReportFolderEmpty =true;
            return testSuties;
        }
           
        for (File file : listOfFiles) {
            if (file.isFile()) {
                getFileName =file.getName();
                getFileName =getFileName.split("[~]")[0];

                if(!getFileName.contentEquals(getNewFileName) && !getFileName.contentEquals("~$") && !getFileName.isEmpty()) {
                    getNewFileName =file.getName();
                    getNewFileName =getFileName.split("[~]")[0];
                    suiteCount++;
                    testSuties.put(suiteCount, getNewFileName);
                }
            }
        }
        if(testSuties.size() ==0)
            testCaseReportFolderEmpty =true;
        return testSuties;
    }
    
    @SuppressWarnings("null")
    public static LinkedHashMap<Date, String> getTestDateList() {
        LinkedHashMap<Date, String> testCaseResultDate =new LinkedHashMap<Date, String>();
        int dateCount =0;
        testCaseReportFolderExist =true;
        testCaseReportFolderEmpty =false;
        
        File folder = new File(tesCaseResultPath);
        if(!folder.exists()){
            JOptionPane.showMessageDialog(null, "Required folder [testResults] not found!", "Alert", JOptionPane.WARNING_MESSAGE);
            testCaseReportFolderExist =false;
            return testCaseResultDate;
        }
        
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles ==null || listOfFiles.length ==0){
            testCaseReportFolderEmpty =true;
            return testCaseResultDate;
        }
        
        Arrays.sort(listOfFiles, Comparator.comparingLong(File::lastModified).reversed());
        
        String currDate =null;
        String currDate1 =null;
	 	
        for (File file : listOfFiles) {
            if (file.isFile()) {
                Date date1;
                try {
                    date1 = sdf.parse(sdf.format(file.lastModified()));
                    testCaseResultDate.put(date1, "");
                } catch (ParseException exp) {}
            }
        }
        return testCaseResultDate;
    }
    
    public static DefaultMutableTreeNode getHtmlTestReportList(String testSuiteName, DefaultMutableTreeNode htmlSuiteNode) {
        File folder = new File(htmlTestReportPath);
        File[] listOfFiles = folder.listFiles();
        Arrays.sort(listOfFiles, Comparator.comparingLong(File::lastModified).reversed());
        
        for(File file: listOfFiles){
            if (file.isFile()) {
                if(file.getName().split("~")[0].contentEquals(testSuiteName)) {
                    DefaultMutableTreeNode htmlSuiteNodeChild = new DefaultMutableTreeNode(file.getName()+" ("+sdf1.format(file.lastModified())+")");
                    htmlSuiteNode.add(htmlSuiteNodeChild);
                }
            }
        }
        
        return htmlSuiteNode;
    }
    
    public static DefaultMutableTreeNode getTestCaseResultList(String testSuiteName, DefaultMutableTreeNode htmlTestResultNode) {
      File folder = new File(tesCaseResultPath);
      File[] listOfFiles = folder.listFiles();
      Arrays.sort(listOfFiles, Comparator.comparingLong(File::lastModified).reversed());
      DefaultMutableTreeNode testCaseResultDate = null;
      boolean childNodeStatus;
              
      for(File file: listOfFiles){
        if (file.isFile()) {
            if(file.getName().split("~")[0].contentEquals(testSuiteName)) {
                DefaultMutableTreeNode htmlSuiteNodeChild = new DefaultMutableTreeNode(null);
                childNodeStatus =false;
                
                if(htmlTestResultNode.getChildCount() ==0){
                  testCaseResultDate = new DefaultMutableTreeNode(sdf.format(file.lastModified()));
                  htmlSuiteNodeChild.add(testCaseResultDate);
                }

                for(int i=0; i<htmlTestResultNode.getChildCount(); i++){
                    if(!htmlTestResultNode.getChildAt(i).toString().contentEquals(sdf.format(file.lastModified()))){
                      
                        for(int x=0; x<htmlTestResultNode.getChildCount(); x++){
                          if(htmlTestResultNode.getChildAt(x).toString().contentEquals(sdf.format(file.lastModified()))){
                              testCaseResultDate =(DefaultMutableTreeNode) htmlTestResultNode.getChildAt(x);
                              childNodeStatus =true;
                              break;
                            }
                        }
                        
                        if(childNodeStatus ==false){
                            testCaseResultDate = new DefaultMutableTreeNode(sdf.format(file.lastModified()));
                            htmlTestResultNode.add(testCaseResultDate);
                        }else{
                            htmlTestResultNode.add(testCaseResultDate);
                        }
                          
                        break;
                    }
                }
                
                testCaseResultDate.add(new DefaultMutableTreeNode(file.getName()+" ("+sdf1.format(file.lastModified())+")"));
                htmlTestResultNode.add(testCaseResultDate);
            }
        }
      }
      
      return htmlTestResultNode;
    }
    
    
    public static boolean openTheFileOverDesktop(String filePath){
        boolean fileExist =false;
        
        if(Desktop.isDesktopSupported()){
            try {
                Desktop desktop = Desktop.getDesktop();
                File file = new File(filePath);
                if(file.exists()){
                    desktop.open(file);
                    fileExist =true;
                }
            } catch (IOException ex) {
                //Logger.getLogger(htmlReportCommon.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return fileExist;
    }
}