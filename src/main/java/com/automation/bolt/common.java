package com.automation.bolt;

import static com.automation.bolt.boltRunner.checkTestDataForRunTime;
import static com.automation.bolt.boltRunner.failTestStep;
import static com.automation.bolt.boltRunner.keywordInvalid;
import static com.automation.bolt.boltRunner.lhm;
import static com.automation.bolt.boltRunner.saveStepResult;
import static com.automation.bolt.boltRunner.testRunStep;
import static com.automation.bolt.boltRunner.testStepResult;
import static com.automation.bolt.gui.AutomationTestRunner.lblSettingsAndConfiguration;
import static com.automation.bolt.gui.ExecuteRegressionSuite.arrTestId;
import static com.automation.bolt.gui.ExecuteRegressionSuite.chkBoxAssociateObjOR;
import static com.automation.bolt.gui.ExecuteRegressionSuite.excelFile;
import static com.automation.bolt.gui.ExecuteRegressionSuite.scrollExecuteRegSuite;
import static com.automation.bolt.gui.ExecuteRegressionSuite.testGlobalORAssociatedFilePath;
import static com.automation.bolt.gui.ExecuteRegressionSuite.testResultDocPath;
import static com.automation.bolt.gui.ExecuteRegressionSuite.testSuiteFilePath;
import static com.automation.bolt.gui.SettingsAndConfiguration.importWebDriver;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebElement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

public class common extends userDefineTest{
    public static Workbook testRunnerWorkbook =null;
    public static File testRunnerFile =null;
    public static FileInputStream testRunnerInputStream =null;
    public static String testRunnerFileName;
    public static Sheet testFlow =null;
    public static Sheet objectRespository =null;
    public static int testFlowRows =0;
    public static int objectRepositoryRows =0;
    public static Properties prop =new Properties();
    public static XWPFDocument docx;
    public static FileOutputStream out;
    public static String testResultPath;

    //public static LinkedHashMap<Integer, String> mapTestStep = new LinkedHashMap<Integer, String>();
    public static LinkedHashMap<Integer, ArrayList<String>> mapTestSteps = new LinkedHashMap<Integer, ArrayList<String>>();
    //public static LinkedHashMap<Integer, LinkedHashMap> mapTestFlow = new LinkedHashMap<Integer, LinkedHashMap>();
    public static LinkedHashMap<Integer, LinkedHashMap> mapTestFlows = new LinkedHashMap<Integer, LinkedHashMap>();
    public static LinkedHashMap<String, String> objectRepositoryElmList = new LinkedHashMap<String, String>();
    public static boolean driverJsonPathFnd =true;
    public static int blankRowIndex;
    
    public static XWPFParagraph paragraph;
    
    public static XWPFRun paraTeststep;
    public static XWPFRun paraTeststep1;
    public static XWPFRun paraTeststep2;
    
    public static XWPFRun paraTestElement;
    public static XWPFRun paraTestData;
    public static int tagListndex;
 
    public static void createTestResult() {
        try {
            if(!new File(constants.userDir+"/testResults").exists()){
                new File(constants.userDir+"/testResults").mkdir();
            }
            
            testResultPath = constants.userDir+"/testResults/"+excelFile.getName().split(".xlsx")[0]+"~TC_"+boltRunner.getTestId+"_"+common.getCurrentDateTimeMS()+".docx";
            docx = new XWPFDocument();
            out = new FileOutputStream(new File(testResultPath));
        } catch (FileNotFoundException exp) {
            glueCode.stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }
    }
    
    public static String  createUniqueID(){
        DateFormat dateFormat = new SimpleDateFormat("yyddmm");
        Date date = new Date();
        
        String dt=String.valueOf(dateFormat.format(date));
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat time = new SimpleDateFormat("HHss");
        String tm= String.valueOf(time.format(new Date()));//time in 24 hour format
        String id= dt+tm;
        
        return id;   
    }
	
    public static void writeTestStepWithResult(XWPFDocument docx, String testStepWithResult, String testResult, String stepKeyword) {
        //XWPFRun paragraphTestStep = paragraph.createRun();
        //XWPFRun paragraphOneRunOne = paragraph.createRun();
        XWPFRun paragraphOneRunTwo = paragraph.createRun();
        
        //paragraphOneRunOne.setFontFamily("Consolas");
        paragraphOneRunTwo.setBold(true);
        
        switch (testResult.toLowerCase()) {
            case "pass":   
                paragraphOneRunTwo.setColor("00b33b");
                paragraphOneRunTwo.setText(testResult);
                break;
            case "fail":
                paragraphOneRunTwo.setColor("ff0000");
                paragraphOneRunTwo.setText(testResult);
                break;
            case "error":
                paraTeststep =paragraph.createRun();
                paraTeststep.addBreak();
                paraTeststep.setColor("ff5400");
                paraTeststep.setText(testStepWithResult);
                break;
            case "warning":
                paraTeststep =paragraph.createRun();
                paraTeststep.setColor("ff8c00");
                paraTeststep.setBold(true);
                paraTeststep.setText(testResult);
                break;
            case "skipped": 
                paraTeststep =paragraph.createRun();
                paraTeststep.setColor("ff8c00");
                paraTeststep.setBold(true);
                paraTeststep.setText(testResult);
                break;    
            case "screenshot":
                //paragraphTestStep.setText(testStepWithResult);
                //paragraphOneRunOne.setText(": "+getStepTxt);
                break;
            default:
                break;
        }
    }
	
    public static void writeStepInWordDocument(String testStep) {
    	boltRunner.stepIndex++;
    	writeTestStep(Integer.valueOf(boltRunner.stepTestNumber)+"."+
    	Integer.valueOf(boltRunner.stepIndex),testStep,"","");
    }
    
    public static void writeTestStep(String getKey, String getTestELm, String getTestData, String getTestDesc) {
        paragraph =docx.createParagraph();
                     
        switch(testRunStep) {
            case "URL":
                testStepResult = "Step "+getKey+": Launch URL " +"\""+getTestData+"\"";
                saveStepResult = "Launch URL " +"\""+getTestData+"\"";

                getFormatter2(getKey, "Launch URL ", getTestData);
                break;
            case "SWITCH_WINDOW":
                testStepResult = "Step "+getKey+": Switch to window " +"\""+getTestData+"\"";
                saveStepResult = "Switch to window " +"\""+getTestData+"\"";

                getFormatter2(getKey, "Switch to window ", getTestData);
                break;
            case "SWITCH_IFRAME":
                testStepResult = "Step "+getKey+": Switch to iframe " +"\""+getTestData+"\"";
                saveStepResult = "Switch to iframe " +"\""+getTestData+"\"";

                getFormatter2(getKey, "Switch to iframe ", getTestData);
                break;
            case "USER_DEFINE":
                testStepResult = "Step "+getKey+": User define test " +"\""+getTestELm+"\"";
                saveStepResult = "User define test " +"\""+getTestELm+"\"";

                getFormatter2(getKey, "User define test ", getTestELm);
                break;    
            case "SWITCH_DEFAULT":
                testStepResult = "Step "+getKey+": Switch to default iframe";
                saveStepResult = "Switch to default iframe";

                getFormatter2(getKey, "Switch to default iframe", "");
                break;     
            case "ASSERT_VISIBLE":
                testStepResult = "Step "+getKey+": Assert that " +"\""+getTestELm+"\" is visible";
                saveStepResult = "Assert that " +"\""+getTestELm+"\" is visible";

                getFormatter3(getKey, "Assert that ", getTestELm, " is visible");
                break;
            case "HARD_WAIT":
                testStepResult = "Step "+getKey+": Pause for " +"\""+getTestData+"\" seconds";
                saveStepResult = "Pause for " +"\""+getTestData+"\" seconds";

                getFormatter3(getKey, "Pause for ", getTestData, " seconds");
                break;    
            case "ASSERT_SELECTED":
                testStepResult = "Step "+getKey+": Assert that " +"\""+getTestELm+"\" is selected";
                saveStepResult = "Assert that " +"\""+getTestELm+"\" is selected";

                getFormatter3(getKey, "Assert that ", getTestELm, " is selected");
                break;
            case "ASSERT_NOT_SELECTED":
                testStepResult = "Step "+getKey+": Assert that " +"\""+getTestELm+"\" is not selected";
                saveStepResult = "Assert that " +"\""+getTestELm+"\" is not selected";

                getFormatter3(getKey, "Assert that ", getTestELm, " is not selected");
                break;
            case "ALERT_SUBMIT":
                testStepResult = "Step "+getKey+": Submit the alert popup request";
                saveStepResult = "Submit the alert popup request";

                getFormatter2(getKey, "Submit the alert popup request", "");
                break;    
            case "SELECT_BY_VALUE":
            case "SELECT_BY_INDEX":
            case "SELECT_BY_VTEXT":    
                testStepResult = "Step "+getKey+": Select list value "+"\""+getTestData+"\" from "+"\""+getTestELm+"\"";
                saveStepResult = "Select list value "+"\""+getTestData+"\" from "+"\""+getTestELm+"\"";

                getFormatter1(getKey, "Select list value ", getTestData, " from ", getTestELm);
                break;    
            case "SET":
                testStepResult = "Step "+getKey+": Set value "+"\""+getTestData+"\" to "+"\""+getTestELm+"\"";
                saveStepResult = "Set value "+"\""+getTestData+"\" to "+"\""+getTestELm+"\"";

                getFormatter1(getKey, "Set value ", getTestData, " to ", getTestELm);
                break;
            case "CLEAR":
                testStepResult = "Step "+getKey+": Clear " +"\""+getTestELm+"\"";
                saveStepResult = "Clear " +"\""+getTestELm+"\"";

                getFormatter4(getKey, "Clear ", getTestELm);
                break;  
            case "GET":
                testStepResult = "Step "+getKey+": Get value ["+getTestData+"] from "+"\""+getTestELm+"\"";
                saveStepResult = "Get value "+getTestData+" from "+"\""+getTestELm+"\"";

                getFormatter1(getKey, "Get value ", getTestData, " from ", getTestELm);
                break;  
            case "CLICK":
                testStepResult = "Step "+getKey+": Click " +"\""+getTestELm+"\"";
                saveStepResult = "Click " +"\""+getTestELm+"\"";

                getFormatter4(getKey, "Click ", getTestELm);
                break;
            case "DOUBLE_CLICK":
                testStepResult = "Step "+getKey+": Double click " +"\""+getTestELm+"\"";
                saveStepResult = "Double click " +"\""+getTestELm+"\"";

                getFormatter4(getKey, "double click ", getTestELm);
                break;    
            case "PRESS_KEY":
                testStepResult = "Step "+getKey+": Press [" + getTestData.toUpperCase()+ "] key over "+"\""+getTestELm+"\"";
                saveStepResult = "Press [" + getTestData.toUpperCase()+ "] key over "+"\""+getTestELm+"\"";

                getFormatter1(getKey, "Press ", getTestData.toUpperCase(), " key over ", getTestELm);
                break;
            case "KEY_EVENTS":
                testStepResult = "Step "+getKey+": Execute key events [" + getTestData.toUpperCase()+ "] over "+"\""+getTestELm+"\"";
                saveStepResult = "Execute key events [" + getTestData.toUpperCase()+ "] over "+"\""+getTestELm+"\"";

                getFormatter1(getKey, "Execute key events ", getTestData.toUpperCase(), " over ", getTestELm);
                break;
            case "ROBOT_EVENTS":
                testStepResult = "Step "+getKey+": Execute robot events [" + getTestData.toUpperCase()+ "]";
                saveStepResult = "Execute robot events [" + getTestData.toUpperCase()+ "]";

                getFormatter4(getKey, "Execute robot events ", getTestData.toUpperCase());
                break;
            case "MOVE_TO_ELEMENT":
                testStepResult = "Step "+getKey+": Move to " +"\""+getTestELm+"\"";
                saveStepResult = "Move to " +"\""+getTestELm+"\"";

                getFormatter4(getKey, "Move to ", getTestELm);
                break;
            case "ASSERT_CLICKABLE":
                testStepResult = "Step "+getKey+": Assert that " +"\""+getTestELm+"\" is clickable";
                saveStepResult = "Assert " +"\""+getTestELm+"\" is clickable";

                getFormatter3(getKey, "Assert ", getTestELm, " is clickable");
                break;  
            case "OPEN_NEW_WINDOW":
                testStepResult = "Step "+getKey+": Open " +"\""+getTestData+"\" new window";
                saveStepResult = "Open " +"\""+getTestData+"\" new window";

                getFormatter5(getKey, "Open ", getTestData, " new window");
                break;
            case "UPLOAD_FILE":
                testStepResult = "Step "+getKey+": Upload " +"\""+getTestData+"\" file";
                saveStepResult = "Upload " +"\""+getTestData+"\" file";

                getFormatter5(getKey, "Upload ", getTestData, " file");
                break;    
            case "MOVE_TO_WINDOW":
                testStepResult = "Step "+getKey+": Move to " +"\""+getTestData+"\" window";
                saveStepResult = "Move to " +"\""+getTestData+"\" window";

                getFormatter5(getKey, "Move to ", getTestData, " window");
                break;
            case "ASSERT_TEXT":
                testStepResult = "Step "+getKey+": Assert that " +"\""+getTestELm+"\" text is equals to "+"\""+getTestData+"\"";
                saveStepResult = "Assert that " +"\""+getTestELm+"\" text is equals to "+"\""+getTestData+"\"";

                getFormatter6(getKey, "Assert that ", getTestELm, " text is equals to ", getTestData);
                break;
            case "TAKE_SCREENSHOT":
            case "ROBOT_SCREENSHOT":    
                testStepResult = "Step "+getKey+": Take screen-shot";
                saveStepResult = "Take screen-shot";

                paraTeststep =paragraph.createRun();
                paraTeststep.setText("Step "+getKey+": ");

                paraTeststep1 =paragraph.createRun();
                paraTeststep1.setColor("ff0000");
                paraTeststep1.setText("Take screen-shot");

                paraTeststep =paragraph.createRun();
                paraTeststep.setText(": ");
                break; 
            case "MOUSE_HOVER":
                testStepResult = "Step "+getKey+": Mouse hover at " +"\""+getTestELm+"\"";
                saveStepResult = "Mouse hover at " +"\""+getTestELm+"\"";

                getFormatter4(getKey, "Mouse hover at ", getTestELm);
                break;
            case ":THEN":
                testStepResult = "Step "+getKey+": Then object(s) with tagName " +"\""+getTestELm+"\" with the matching attribute name and value "+"\""+getTestData+"\""+" do the following action "+"\""+getTestDesc+"\"";
                saveStepResult = "Then object(s) with tagName " +"\""+getTestELm+"\" with the matching attribute name and value "+"\""+getTestData+"\""+" do the following action "+"\""+getTestDesc+"\"";

                getFormatter7(getKey, "Then object(s) with tagName ", getTestELm, " with the matching attribute name and value ", getTestData, " do the following action ", getTestDesc);
                break;
            case ":IF":
                testStepResult = "Step "+getKey+": If object(s) with tagName " +"\""+getTestELm+"\" with the matching attribute name and value "+"\""+getTestData+"\""+" do the following action "+"\""+getTestDesc+"\"";
                saveStepResult = "If object(s) with tagName " +"\""+getTestELm+"\" with the matching attribute name and value "+"\""+getTestData+"\""+" do the following action "+"\""+getTestDesc+"\"";

                getFormatter7(getKey, "If object(s) with tagName ", getTestELm, " with the matching attribute name and value ", getTestData, " do the following action ", getTestDesc);
                break;
            case ":GET":
                testStepResult = "Step "+getKey+": Get object(s) with tagName " +"\""+getTestELm+"\" with the matching attribute name and value "+"\""+getTestData+"\"";
                saveStepResult = "Get object(s) with tagName " +"\""+getTestELm+"\" with the matching attribute name and value "+"\""+getTestData+"\"";

                getFormatter6(getKey, "Get object(s) with tagName ", getTestELm, " with the matching attribute name and value ", getTestData);
                break;
            case ":START":
                testStepResult = "Step "+getKey+": User define steps START";
                saveStepResult = "User define steps START";

                getFormatter3(getKey, "User define steps ", "START", "");
                break;
            case ":STOP":
                testStepResult = "Step "+getKey+": User define steps STOP";
                saveStepResult = "User define steps START";

                getFormatter3(getKey, "User define steps ", "STOP", "");
                break;
            default:
                testStepResult = "Step "+getKey+": ["+testRunStep+"] is not a recognized keyword";
                saveStepResult = "["+testRunStep+"] is not a recognized keyword";

                getFormatter7(getKey, testRunStep, " is not a recognized keyword");
                break;
        }
    }
    
    public static void getFormatter1(String getKey, String paraTxt1, String getTestData, String paraTxt2, String getTestELm){
        paraTeststep =paragraph.createRun();
        paraTeststep.setText("Step "+getKey+": ");
        
        paraTeststep =paragraph.createRun();
        paraTeststep.setText(paraTxt1+"\"");
        
        paraTestData =paragraph.createRun();
        paraTestData.setItalic(true);
        paraTestData.setColor("4000ff");
        paraTestData.setText(getTestData);
        
        paraTeststep =paragraph.createRun();
        paraTeststep.setText("\""+paraTxt2+"\"");
        
        paraTestElement =paragraph.createRun();
        paraTestElement.setColor("b32d00");
        paraTestElement.setText(getTestELm);
        
        paraTeststep =paragraph.createRun();
        paraTeststep.setText("\""+": ");
    }
    
    public static void getFormatter2(String getKey, String paraTxt1, String getTestData){
        paraTeststep =paragraph.createRun();
        paraTeststep.setText("Step "+getKey+": ");
        
        paraTeststep =paragraph.createRun();
        paraTeststep.setText(paraTxt1+"\"");
        
        paraTestData =paragraph.createRun();
        paraTestData.setItalic(true);
        paraTestData.setColor("4000ff");
        paraTestData.setText(getTestData);
         
        paraTeststep =paragraph.createRun();
        paraTeststep.setText("\""+": ");
    }
    
    public static void getFormatter3(String getKey, String paraTxt1, String getTestELm, String paraTxt2){
        paraTeststep =paragraph.createRun();
        paraTeststep.setText("Step "+getKey+": ");
        
        paraTeststep =paragraph.createRun();
        paraTeststep.setText(paraTxt1+"\"");
        
        paraTestElement =paragraph.createRun();
        paraTestElement.setColor("b32d00");
        paraTestElement.setText(getTestELm);
        
        paraTeststep =paragraph.createRun();
        paraTeststep.setText("\""+paraTxt2);
        
        paraTeststep =paragraph.createRun();
        paraTeststep.setText(": ");
    }
    
    public static void getFormatter4(String getKey, String paraTxt1, String getTestELm){
        paraTeststep =paragraph.createRun();
        paraTeststep.setText("Step "+getKey+": ");
        
        paraTeststep =paragraph.createRun();
        paraTeststep.setText(paraTxt1+"\"");
        
        paraTestData =paragraph.createRun();
        paraTestData.setColor("b32d00");
        paraTestData.setText(getTestELm);
         
        paraTeststep =paragraph.createRun();
        paraTeststep.setText("\""+": ");
    }
    
    public static void getFormatter7(String getKey, String getTestELm, String paraTxt1){
        paraTeststep =paragraph.createRun();
        paraTeststep.setText("Step "+getKey+": "+"\"");
        
        paraTestData =paragraph.createRun();
        paraTestData.setColor("b32d00");
        paraTestData.setText(getTestELm);
        
        paraTeststep =paragraph.createRun();
        paraTeststep.setText("\""+paraTxt1);
         
        paraTeststep =paragraph.createRun();
        paraTeststep.setText(": ");
    }
    
    public static void getFormatter5(String getKey, String paraTxt1, String getTestData, String paraTxt2){
        paraTeststep =paragraph.createRun();
        paraTeststep.setText("Step "+getKey+": ");
        
        paraTeststep =paragraph.createRun();
        paraTeststep.setText(paraTxt1+"\"");
        
        paraTestElement =paragraph.createRun();
        paraTestElement.setColor("4000ff");
        paraTestElement.setText(getTestData);
        
        paraTeststep =paragraph.createRun();
        paraTeststep.setText("\""+paraTxt2);
        
        paraTeststep =paragraph.createRun();
        paraTeststep.setText(": ");
    }
    
    public static void getFormatter6(String getKey, String paraTxt1, String getTestELm, String paraTxt2, String getTestData){
        paraTeststep =paragraph.createRun();
        paraTeststep.setText("Step "+getKey+": ");
        
        paraTeststep =paragraph.createRun();
        paraTeststep.setText(paraTxt1+"\"");
        
        paraTestElement =paragraph.createRun();
        paraTestElement.setColor("b32d00");
        paraTestElement.setText(getTestELm);
        
        paraTeststep =paragraph.createRun();
        paraTeststep.setText("\""+paraTxt2+"\"");
        
        paraTestData =paragraph.createRun();
        paraTestData.setItalic(true);
        paraTestData.setColor("4000ff");
        paraTestData.setText(getTestData);
        
        paraTeststep =paragraph.createRun();
        paraTeststep.setText("\""+": ");
    }
    
    public static void getFormatter7(String getKey, String paraTxt1, String getTestELm, String paraTxt2, String getTestData, String paraTxt3, String getTestDesc){
        paraTeststep =paragraph.createRun();
        paraTeststep.setText("Step "+getKey+": ");
        
        paraTeststep =paragraph.createRun();
        paraTeststep.setText(paraTxt1+"\"");
        
        paraTestElement =paragraph.createRun();
        paraTestElement.setColor("b32d00");
        paraTestElement.setText(getTestELm);
        
        paraTeststep =paragraph.createRun();
        paraTeststep.setText("\""+paraTxt2+"\"");
        
        paraTestData =paragraph.createRun();
         paraTestData.setItalic(true);
        paraTestData.setColor("4000ff");
        paraTestData.setText(getTestData);
        
        paraTeststep =paragraph.createRun();
        paraTeststep.setText("\""+paraTxt3+"\"");
        
        paraTestElement =paragraph.createRun();
        paraTestElement.setColor("FF3131");
        paraTestElement.setText(getTestDesc);
        
        paraTeststep =paragraph.createRun();
        paraTeststep.setText("\""+": ");
    }
    
    public static void saveAndCloseDocFile() {
        try {
            docx.write(out);
            out.flush();
            out.close();
            testResultDocPath.put(boltRunner.getTestId, testResultPath);
        } catch (IOException exp) {
            glueCode.stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }
    }
	
    public static void addScreenShotToDoc(XWPFDocument docx, String snapshotFilePath) {
        try {
            XWPFParagraph paragraph = docx.createParagraph();
            XWPFRun run = paragraph.createRun();
            try (InputStream pic = new FileInputStream(snapshotFilePath)) {
                run.addPicture(pic, XWPFDocument.PICTURE_TYPE_PNG, "", Units.toEMU(450), Units.toEMU(250));
            }
        } catch (FileNotFoundException exp) {
            glueCode.stepSuccess = false;
            boltRunner.logError = exp.getLocalizedMessage();
            boltExecutor.log.error(exp);
        } catch (InvalidFormatException | IOException exp) {
            glueCode.stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }
    }
	
    @SuppressWarnings("rawtypes")
	public static boolean readTestRunner_TestSteps() {

    	String fileExtensionName =null;
        testRunnerWorkbook =null;
        testRunnerInputStream =null;
        objectRespository =null;
        
        int testFlowNum =0;
        //mapTestFlow = new LinkedHashMap<Integer, LinkedHashMap>();
        mapTestFlows = new LinkedHashMap<Integer, LinkedHashMap>();

        try {
            testRunnerInputStream = new FileInputStream(testSuiteFilePath);
            fileExtensionName = testSuiteFilePath.substring(testSuiteFilePath.lastIndexOf("."));
        } catch (FileNotFoundException exp) {
            boltExecutor.log.error("required ["+testSuiteFilePath.toString()+"] test runner file not found!\nexiting the run......");
            return false;
        }

        if(fileExtensionName.equals(".xlsx")){
            try {
                testRunnerWorkbook = new XSSFWorkbook(testRunnerInputStream);
            } catch (IOException exp) {
                boltExecutor.log.error(exp);
            }
        }else if(fileExtensionName.equals(".xls")) {
            try {
                testRunnerWorkbook = new XSSFWorkbook(testRunnerInputStream);
            } catch (IOException exp) {
                boltExecutor.log.error(exp);
            }
        }

        try {
            testFlow = testRunnerWorkbook.getSheetAt(0);
        }catch(NullPointerException exp) {
            boltExecutor.log.error(exp);
        }

        if(testFlow ==null) {
            boltExecutor.log.error("required [Test Flow] sheet not found!\nexiting the run......");
        }

        try {
            //objectRespository = testRunnerWorkbook.getSheetAt(1);
            //objectRespository = testRunnerWorkbook.getSheet(constants.objectRepo);
        }catch(NullPointerException | IllegalArgumentException exp) {
            boltExecutor.log.error(exp);
        }

        //if(objectRespository ==null) {
            //boltExecutor.log.error("required [Object Repository] sheet not found!\nexiting the run......");
        //}

        // get test to execute
        testFlowRows = testFlow.getLastRowNum()-testFlow.getFirstRowNum();
        for(int i=0; i<arrTestId.size(); i++){
            for (int j = 1; j <= testFlowRows; j++) {
                Row row = testFlow.getRow(j);
                try {
                    String getTestRunId =null;
                    if(row.getCell(0).getCellType().toString().contentEquals("NUMERIC")){
                        double getTestId = Math.round(Double.parseDouble(row.getCell(0).toString()));
                        int xgetTestId = (int)getTestId;
                        getTestRunId = String.valueOf(xgetTestId);

                    }else{
                        getTestRunId = row.getCell(0).toString();
                    }
                    if(getTestRunId.contentEquals(arrTestId.get(i))) {
                            testFlowNum++;
                            storeTestFlow(testFlow,j);
                            //mapTestFlow.put(testFlowNum, mapTestStep);
                            mapTestFlows.put(testFlowNum, mapTestSteps);
                            break;
                    }
                }catch(NullPointerException exp) {
                    boltExecutor.log.error(exp);
                }
            }
        }

        if(mapTestFlows.isEmpty()) {
            boltExecutor.log.error("no test flow found for execution at run-time!\nexiting the run......");
            return false;
        }
        
        return true;
    }
	
    @SuppressWarnings("unused")
    public static void storeTestFlow(Sheet testFlow, int rowNum) {
        String testRun = "";
        String testId = "";
        String testStep = "";
        String testElement = "";
        String testData = "";
        String testDescription = "";
        String nextRowData = "";
        int mapIndex = 0;
        //mapTestStep = new LinkedHashMap<>();
        mapTestSteps = new LinkedHashMap<Integer, ArrayList<String>>();
        ArrayList<String> testSteps = new ArrayList<String>();
        
        for (int i = rowNum; i <= testFlowRows; i++) {
            testSteps = new ArrayList<String>();
            Row row = testFlow.getRow(i);
            Row nextRow = testFlow.getRow(i+1);
            mapIndex++;

            try {
                nextRowData = nextRow.getCell(0).toString().toLowerCase();
            }catch(NullPointerException exp) {
                boltExecutor.log.error(exp);
            }

            //testRun = readTestStep(row,0);
            testId = readTestStep(row,0);
            testStep = readTestStep(row,2);
            testElement = readTestStep(row,3);
            testData = readTestStep(row,4);
            testDescription = readTestStep(row,5);
            
            testSteps.add(testId);
            testSteps.add(testStep);
            testSteps.add(testElement);
            testSteps.add(testData);
            testSteps.add(testDescription);
                    
            if(!nextRowData.isEmpty()) {
                if(!nextRowData.contentEquals("#")){
                    //mapTestStep.put(mapIndex, testStep+"|"+testElement+"|"+testData+"|"+testDescription+"|"+testId);
                    mapTestSteps.put(mapIndex, testSteps);
                    break;
                }
            }

            //if(!testId.contentEquals("#"))
                //mapTestStep.put(mapIndex, testStep+"|"+testElement+"|"+testData+"|"+testDescription+"|"+testId);
                mapTestSteps.put(mapIndex, testSteps);
        }
    }
	
    public static String readTestStep(Row testStepRow, int colIndex) {
        String getTestStep = "";
        try {
            if (testStepRow.getCell(colIndex).getCellType().toString().contentEquals("NUMERIC")){
                if(testStepRow.getCell(colIndex).toString().contains(".0"))
                    getTestStep = testStepRow.getCell(colIndex).toString().split("[.]")[0];
                else if(testStepRow.getCell(colIndex).toString().contains("E"))
                    getTestStep = testStepRow.getCell(colIndex).toString().replace(".", "").split("E")[0];
            }else
                getTestStep = testStepRow.getCell(colIndex).toString();
         }catch(NullPointerException exp) {
            boltExecutor.log.error(exp);
         }
        return getTestStep;
    }
	
    public static boolean readTestRunner_ObjectRepository() {
        boolean objectRepoExist =true;
        objectRepositoryElmList = new LinkedHashMap<String, String>();
        
        try{
            if(chkBoxAssociateObjOR.isSelected()){
                testRunnerWorkbook = uploadXlsWorkbook(testGlobalORAssociatedFilePath);
                objectRespository = testRunnerWorkbook.getSheetAt(0);
            }else
                objectRespository = testRunnerWorkbook.getSheetAt(1);
        }catch(IllegalArgumentException exp){
            boltExecutor.log.error(exp);
        }
       
        if(objectRespository ==null){
            objectRepoExist =false;
            boltExecutor.log.error("required element repository not found for executing test suite.\n\nstopping the test run NOW!");
            JOptionPane.showMessageDialog(scrollExecuteRegSuite, 
                    "required element repository not found for executing test suite.\n\nstopping the test run NOW!", 
                    "Alert", 
                    JOptionPane.WARNING_MESSAGE);
            return objectRepoExist;
        }
            
        objectRepositoryRows = objectRespository.getLastRowNum()-objectRespository.getFirstRowNum();
        String elmName = "";
        String elmId = "";
        String elmXpath = "";
        String elmProperty = "";

        for (int i = 1; i <= objectRepositoryRows; i++) {
            Row row = objectRespository.getRow(i);
            try {
                elmName = readTestStep(row,0);
                elmId = readTestStep(row,1);
                elmXpath = readTestStep(row,2);

                if(elmId.isEmpty()) {
                    elmProperty = "xpath|"+elmXpath;
                }else if(elmXpath.isEmpty()) {
                    elmProperty = "id|"+elmId;
                }

                objectRepositoryElmList.put(elmName, elmProperty);
             }catch(NullPointerException exp) {
                boltExecutor.log.error(exp.getMessage());
             }
        }

        if(objectRepositoryElmList.isEmpty()) {
            boltExecutor.log.error("no test element(s) declaration found for executing test suite.\n\nstopping the test run NOW!");
            JOptionPane.showMessageDialog(scrollExecuteRegSuite, 
                    "no test element(s) declaration found for executing test suite.\n\nstopping the test run NOW!", 
                    "Alert", 
                    JOptionPane.WARNING_MESSAGE);
            objectRepoExist =false;
        }
        return objectRepoExist;
    }
	
    public static String getElementFromObjectRepository(String elmName) {
        String getElm = null;
        getElm = objectRepositoryElmList.get(elmName);
        
        if(getElm ==null)
            boltRunner.logError = "no reference found in the object repository for element \""+elmName+"\"";

        return getElm;
    }
	
    public static void readConfigProperties() {
        String propFileName = constants.userDir +"/config/"+ constants.propFileName;
        FileReader propReader = null;
        
        try {
            propReader = new FileReader(propFileName);
        } catch (FileNotFoundException exp) {
            boltExecutor.log.error("required ["+propFileName+"] properties file not found!\nexiting the run......");
            return;
        }

        try {
            prop.load(propReader);
        } catch (IOException exp) {
            boltExecutor.log.error("Error found against ["+propFileName+"]: "+exp);
        }
    }
	
    public static String getCurrentDateTimeMS() {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
        String datetime = ft.format(dNow);
        return datetime;
    }
    
    public static void getDriverPathFromJSONfile(){
        JSONParser parser = new JSONParser();
        Object obj = null;
        try {
            try{
                obj = parser.parse(new FileReader(constants.driverPathJSONfile));
                JSONObject jsonObject =(JSONObject) obj;
                String getChromeDriverPath =(String) jsonObject.get("chromeDriver");
                String getEdgeDriverPath =(String) jsonObject.get("edgeDriver");

                //lblChromeDriverPath.setText(getChromeDriverPath);
                //lblEdgeDriverPath.setText(getEdgeDriverPath); 
            }catch (FileNotFoundException exp){
                boltExecutor.log.error(exp);
                JOptionPane.showMessageDialog(lblSettingsAndConfiguration, breakTheExceptionMsg(exp.getMessage()), "Alert", JOptionPane.WARNING_MESSAGE);
                driverJsonPathFnd =false;
            }
        } catch (IOException | ParseException exp) {
            boltExecutor.log.error(exp);
        }
    }
    
    public static void setDriverPathToJSONfile(String whichDriver){
        if(whichDriver.isEmpty())
            return;
        
        JSONParser parser = new JSONParser();
        Object obj =null;
        JSONObject jsonObject =null;
        Path path =null;
        
        try {     
            try{
                obj = parser.parse(new FileReader(constants.driverPathJSONfile));
                jsonObject =  (JSONObject) obj;
                String driverPath = importWebDriver.getSelectedFile().getAbsolutePath();

                jsonObject.put(whichDriver, driverPath);
                File fileToBeHidden = new File(constants.driverPathJSONfile);
                path = FileSystems.getDefault().getPath(constants.driverPathJSONfile);

                if(fileToBeHidden.isHidden())
                    Files.setAttribute(path, "dos:hidden", false);
                
                if(!fileToBeHidden.canWrite()){
                    fileToBeHidden.setReadable(true);
                    fileToBeHidden.setWritable(true);
                }
                    
                try (FileWriter file = new FileWriter(constants.driverPathJSONfile)) 
                    {
                        file.write(jsonObject.toString());
                        file.flush();
                        file.close();
                        //Files.setAttribute(path, "dos:hidden", true);
                        boltExecutor.log.info("Successfully updated json object to file!");
                    }
            }catch(FileNotFoundException exp){
                boltExecutor.log.error(exp);
                JOptionPane.showMessageDialog(lblSettingsAndConfiguration, breakTheExceptionMsg(exp.getMessage()), "Alert", JOptionPane.WARNING_MESSAGE);
            }
        } catch (IOException | ParseException  exp) {
            boltExecutor.log.error(exp);
        }
    }
    
    public static void makeDriverPathJSONfileNotEditable(){
        File file = new File(constants.driverPathJSONfile);
        file.setWritable(false);
    }
    
    public static void makeDriverPathJSONfileEditable(){
        File file = new File(constants.driverPathJSONfile);
        file.setWritable(true);
    }
    
    public static String breakTheExceptionMsg(String exceptionMessage){
        String getTxtMsg =exceptionMessage;
        int startIndex =1;
        String getNewTxtMsg = "";

        String[] splitTxt = getTxtMsg.split(" ");
        for(String getTxt: splitTxt) {
                startIndex++;
                if(startIndex !=10)
                        getNewTxtMsg = getNewTxtMsg + getTxt +" ";
                if(startIndex ==10) {
                        getNewTxtMsg = getNewTxtMsg + getTxt +"\n";
                        startIndex =1;	
                }
        }
        return getNewTxtMsg;
    }
    
    public static void testIdTxtKeyTyped(KeyEvent evt, JTextField textField) {
        boolean result = false;
        boolean result1 = false;
       
        Pattern pattern = Pattern.compile("^(\\d*\\.?\\d*)$");
        Pattern pattern1 = Pattern.compile("[#]");
        
        char keyText = evt.getKeyChar();
        String textId = Character.toString(keyText);
        
        result =pattern.matcher(textId).matches();
        result1 =pattern.matcher(textId).matches();
        
        if(!result){
            result =pattern1.matcher(textId).matches();
            if(!result){
                evt.consume();
            }else
                if(textField.getText().length()>0)
                    evt.consume();
        }
    }
     
    public static void testElmNameTxtTxtKeyTyped(KeyEvent evt, JTextField textField) {
        boolean result = false;
        char keyText = evt.getKeyChar();
        
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]*$");
        String textId = Character.toString(keyText);
        result =pattern.matcher(textId).matches();
        
        if(!result)
            evt.consume();
    }
    
    public static boolean checkForDuplicateTestId(DefaultTableModel tableModel, 
        javax.swing.JTable JTable, 
        int editableRow,
        JTextField testIdTxt){
        
        String getCellVal ="";
        String getTestStepId ="";
        boolean duplicateTestId =false;
        
        if(tableModel.isCellEditable(JTable.getSelectedRow(), 0) ==true){
             try{
                getTestStepId =tableModel.getValueAt(editableRow, 1).toString();
                getCellVal =testIdTxt.getText();
                //getCellVal =createORTabModel.getValueAt(tableAddOR.getSelectedRow(), 0).toString();
            }catch(NullPointerException | ArrayIndexOutOfBoundsException exp){
                getCellVal ="";
            }
             
            if(!getCellVal.isEmpty() && !getCellVal.contentEquals("#")){
               int elmIndex =0;
               for(int i=0; i<tableModel.getRowCount(); i++){
                       String getCellTxt=null;
                       try{
                           getCellTxt =tableModel.getValueAt(i, 0).toString().toLowerCase();
                       }catch (NullPointerException exp){
                           getCellTxt ="";
                       }
                       if(getCellVal.toLowerCase().contentEquals(getCellTxt)){
                           elmIndex++;
                           if(elmIndex ==2){
                               //editableRow =tableAddOR.getSelectedRow();
                               JTable.editCellAt(editableRow, 0);
                               JTable.setSurrendersFocusOnKeystroke(true);
                               JTable.getEditorComponent().requestFocus();
                               
                               JTable.clearSelection();
                               JTable.changeSelection(editableRow, 0, false, true);
                               testIdTxt.selectAll();
                               duplicateTestId =true;
                               JOptionPane.showMessageDialog(null, "Test Id ["+getCellTxt+"] already exist!", "Alert", JOptionPane.WARNING_MESSAGE);
                               break;
                           }
                       }
               }
           }else if (getCellVal.contentEquals("#") && getTestStepId.contentEquals("1")){
                JTable.editCellAt(editableRow, 0);
                JTable.setSurrendersFocusOnKeystroke(true);
                JTable.getEditorComponent().requestFocus();

                JTable.clearSelection();
                JTable.changeSelection(editableRow, 0, false, true);
                testIdTxt.selectAll();
                duplicateTestId =true;
                JOptionPane.showMessageDialog(null, "Can not set SKIP for setp 1, for any given test scenario!", "Alert", JOptionPane.WARNING_MESSAGE);
            }
        }
        return duplicateTestId;
    }
    
    public static boolean checkForDuplicateElementName(DefaultTableModel tableModel, JTextField testElmNameTxt,
            javax.swing.JTable JTable,
            boolean testElmNameVisible,
            int getFlowCellxPoint,
            int getFlowCellyPoint,
            int getEditingRow){
        
        String getElmName ="";
        String getNewElmName ="";
        int elmIndex =0;
        boolean duplicateElmName =false;
        
        if(testElmNameTxt !=null){
            if(testElmNameTxt.isShowing()){
                tabOutFromEditingColumn(true, JTable,getFlowCellxPoint, getFlowCellyPoint, getEditingRow);
                getNewElmName =testElmNameTxt.getText();
            }
        }
        
        if(testElmNameVisible ==true){
            getNewElmName =testElmNameTxt.getText();
            for(int i=0; i<JTable.getRowCount(); i++){
                try{
                    getElmName =JTable.getValueAt(i, 0).toString().toLowerCase();
                }catch (NullPointerException exp){
                    getElmName ="";
                }
                
                if(!getElmName.isEmpty()){
                    if(getElmName.toLowerCase().contentEquals(getNewElmName.toLowerCase())){
                        elmIndex++;
                        if(elmIndex ==2){
                            JTable.editCellAt(getEditingRow, 0);
                            JTable.getEditorComponent().requestFocus();
                            JTable.changeSelection(getEditingRow, 0, false, true);
                            testElmNameTxt.selectAll();
                            duplicateElmName =true;
                            JOptionPane.showMessageDialog(null, "Element name ["+getNewElmName+"] already exist!", "Alert", JOptionPane.WARNING_MESSAGE);
                            break;
                        }
                    }
                }
            }
        }
        return duplicateElmName;
    }
    
    public static void tabOutFromEditingColumn(boolean editingColStatus, JTable myTable, int xCellPoint, int yCellPoint, int selectedRow){
        Component getCellComp = myTable.getComponentAt(xCellPoint, yCellPoint);
        if(getCellComp !=null){
            myTable.setFocusable(true);
            getCellComp.dispatchEvent(new KeyEvent(getCellComp,KeyEvent.KEY_PRESSED, selectedRow,0,KeyEvent.VK_TAB, ' '));
            if(selectedRow >=0)
                myTable.setRowSelectionInterval(selectedRow, selectedRow);
                myTable.requestFocus();
                myTable.scrollRectToVisible(myTable.getCellRect(selectedRow,selectedRow, true));
        }
    }
    
    public static boolean checkForBlankColumnValue(JTable myTable, int colNumber){
        boolean isBlankfnd =false;
        
        try{
            for(int i=0; i<myTable.getRowCount(); i++){
                blankRowIndex =i;
                if(myTable.getValueAt(i, colNumber).toString().isEmpty()){
                    isBlankfnd =true;
                    break;
                }
            }
        }catch (NullPointerException exxp){isBlankfnd =true;}
        return isBlankfnd;
    }
    
    public static boolean checkValueExistInColumn(JTable jTable, String ColName, String searchTxt){
        DefaultTableModel jTabModel = (DefaultTableModel) jTable.getModel();
       
        int getColIndex =getColumnIndex(jTabModel, "Test Element");
        int getRowCount =jTable.getRowCount();
        String getColText;
        boolean textExist =false;
                
        for(int i=0; i<getRowCount; i++){
            getColText =jTable.getValueAt(i, getColIndex).toString();
            if(getColText.contentEquals(searchTxt)){
                textExist =true;
                break;
            }
        }
        return textExist;   
    }
    
    public static int getColumnIndex(DefaultTableModel jTable, String colName){
        int getColCnt =jTable.getColumnCount();
        int colIndex =-1;
        
        for(int i=0; i<getColCnt; i++){
            if(jTable.getColumnName(i).contentEquals(colName)){
                colIndex =i;
                break;   
            }
        }
        return colIndex;
    }
    
    public static XSSFWorkbook uploadXlsWorkbook(String filePath){
        FileInputStream isFile = null;
        XSSFWorkbook xlsWorkbook =null;
        
        try {
            isFile = new FileInputStream(filePath);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(common.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            xlsWorkbook = new XSSFWorkbook(isFile);
        } catch (IOException ex) {
            Logger.getLogger(common.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return xlsWorkbook;
    }
    
    public static Integer getParentIndex(String tagName) {
        Integer tagIndex=null;
        try{
            if(tagName.contains("{") && tagName.contains("}")) {
                try{
                    tagIndex =Integer.valueOf(tagName.split("\\{")[1].split("}")[0]);
                }catch(NumberFormatException exp){}
            }
        }catch(ArrayIndexOutOfBoundsException exp){}
        
        return tagIndex;
    }
    
    public static String getOnlyTag(String tagName) {
        if(tagName.contains("{") && tagName.contains("}")) {
            tagName =tagName.split("\\{")[0];
        }
        return tagName;
    }
    
    public static void executeTestStep(String testRunStep, 
            String testData, 
            WebElement getElement, 
            String testElement, 
            Map.Entry<Integer, ArrayList<String>> testStep,
            String testDescription){
        switch(testRunStep) {
            case "URL":
                glueCode.keyURL(testData);
                break;
            case "SWITCH_WINDOW":
                glueCode.keySwitchToWindow(testData);
                break;
            case "SWITCH_IFRAME":
                glueCode.keySwitchToIframe(testData);
                break;
            case "SWITCH_DEFAULT":
                glueCode.keySwitchToDefaultIframe(testData);
                break;     
            case "ASSERT_VISIBLE":
                glueCode.keyAssertVisible(getElement);
                break;
            case "ASSERT_SELECTED":
                glueCode.keyAssertSelected(getElement);
                break;
            case "ASSERT_NOT_SELECTED":
                glueCode.keyAssertNotSelected(getElement);
                break;
            case "ALERT_SUBMIT":
                glueCode.keySubmitAlertPopup();
                break;
            case "HARD_WAIT":
                try{
                    Integer.valueOf(testData);
                }catch(NumberFormatException exp){testData ="5";}
                common.runTimmerFromCurrentTime(Integer.valueOf(testData));
                break;   
            case "SELECT_BY_VALUE":
                glueCode.keySelectValueByValue(getElement, testData);
                break;
            case "SELECT_BY_INDEX":
                glueCode.keySelectValueByIndex(getElement, testData);
                break;
            case "SELECT_BY_VTEXT":
                glueCode.keySelectValueByVisibleText(getElement, testData);
                break;    
            case "SET":
                glueCode.keySet(getElement, testData);
                break;
            case "UPLOAD_FILE":
                glueCode.keyUploadFile(getElement, testData);
                break;   
            case "CLEAR":
                glueCode.keyClear(getElement);
                break;   
            case "GET":
                String getActualTxt =glueCode.keyGet(getElement);
                testData =getActualTxt;
                testStep.getValue().add(3, testData);
                if((!testRunStep.contentEquals(":IF") || !testRunStep.contentEquals(":THEN")) && !testDescription.toLowerCase().contentEquals("get"))
                    writeTestStep(testStep.getKey().toString(), testElement, testData, testDescription);
                break;
            case "CLICK":
                glueCode.keyClick(getElement);
                break;
            case "DOUBLE_CLICK":
                glueCode.keyDoubleClick(getElement);
                break;    
            case "PRESS_KEY":
                glueCode.keyPressKey(getElement, testData);
                break;
            case "KEY_EVENTS":
                glueCode.keyPressKeys(getElement, testData);
                break;
            case "ROBOT_EVENTS":
                glueCode.keyPageEvent(testData);
                break;    
            case "MOVE_TO_ELEMENT":
                glueCode.keyMoveToElement(getElement);
                break;
            case "ASSERT_CLICKABLE":
                glueCode.keyAssertClickable(getElement);
                break;   
            case "OPEN_NEW_WINDOW":
                glueCode.keyOpenNewWindow(testData);
                break;
            case "MOVE_TO_WINDOW":
                glueCode.keyMoveToWindow(testData);
                break;
            case "ASSERT_TEXT":
                glueCode.keyAssertText(getElement, testData);
                break;
            case "TAKE_SCREENSHOT":
                if(testElement.isEmpty()){
                    if(glueCode.keyTakeScreenShot())
                        common.addScreenShotToDoc(common.docx,glueCode.screenshotPath);
                }    
                else{
                    if(glueCode.keyTakeScreenShot(getElement))
                        common.addScreenShotToDoc(common.docx,glueCode.screenshotPath);
                }
                break;
            case "ROBOT_SCREENSHOT":
                if(glueCode.keyTakeScreenShotRobot())
                       common.addScreenShotToDoc(common.docx,glueCode.screenshotPath);
                break;
            case "MOUSE_HOVER":
                glueCode.keyMouseHover(getElement);
                break;
            case "USER_DEFINE":
            	boltRunner.userDefineSteps = new LinkedHashMap<Integer, String>();
                glueCode.keyUserDefine(testElement, testData);
                break;    
            default:
                failTestStep.put(testStep.getKey(), "keyword ["+testRunStep+"] does not exist");
                keywordInvalid =false;
                stepSuccess =false;
                boltRunner.logError ="keyword ["+testRunStep+"] does not exist";
                boltExecutor.log.error("keyword ["+testRunStep+"] does not exist");
        }
    }
    
    public static void executeUserDefineTestStep(String testRunStep, 
            String testElement, 
            String getAttributeName, 
            String getAttributeValue, 
            String getActionName, 
            Map.Entry<Integer, ArrayList<String>> testStep, 
            String actionName, String actionValue,
            String testDescription){
        
        switch(testRunStep) {
            case ":START":
               lhm = new HashMap<Integer, List<WebElement>>();
               tagListndex =0;
               break;
            case "STOP":
               break;    
            case ":GET":           
                if(getParentIndex(testElement) ==null){
                    List<WebElement> elmList =getElmList(boltDriver, testElement,getAttributeName, getAttributeValue);
                    if(!elmList.isEmpty()){
                        tagListndex =tagListndex+1;
                        lhm.put(tagListndex, elmList);
                    }

                }else{
                   List<WebElement> elmList =getElmList(lhm.get(getParentIndex(testElement)), getOnlyTag(testElement),getAttributeName, getAttributeValue);
                   if(!elmList.isEmpty()){
                        tagListndex =tagListndex+1;
                        lhm.put(tagListndex, elmList);
                    }
               }
               break;
            case ":THEN":
                if(getParentIndex(testElement) ==null){

                }else{
                    WebElement getElm =getElmListTHEN(lhm.get(getParentIndex(testElement)), getOnlyTag(testElement), getAttributeName, getAttributeValue, "");

                    if(getElm !=null){
                        executeTestStep(actionName.trim().toUpperCase(), actionValue.trim(), getElm, testElement,testStep, testDescription);
                    }
                }
                break;
            case ":IF":
                List<WebElement> elmList =new ArrayList<WebElement>();
                if(getParentIndex(testElement) ==null){
                    elmList =getElmListIF(boltDriver, getOnlyTag(testElement),getAttributeName, getAttributeValue); 
                 }else{
                     elmList =getElmListIF(lhm.get(getParentIndex(testElement)), getOnlyTag(testElement),getAttributeName, getAttributeValue);   
                 }

                if(!elmList.isEmpty()){
                    tagListndex =tagListndex+1;
                    lhm.put(tagListndex, elmList);

                    if(!actionName.isEmpty() || !actionValue.isEmpty()){
                        try{
                            if(actionValue.toLowerCase().substring(0,1).contentEquals("{") &&
                                    actionValue.toLowerCase().substring(actionValue.length()-1,actionValue.length()).contentEquals("}")){
                                
                                actionValue =actionValue.substring(1, actionValue.length()-1);
                                if(actionValue.substring(0, 8).contentEquals(":runtime")){
                                    boltRunner.testDescription =actionValue.substring(8, actionValue.length());
                                    if(checkTestDataForRunTime() ==true)
                                        actionValue =boltRunner.testData;
                                    else{
                                        stepSuccess = false;
                                        boltRunner.logError = "Expected reference ["+boltRunner.testDescription+"] not found to retrieve runtime text";
                                        return;
                                    }
                                }
                            }
                        }catch(Exception exp){}
                        
                        executeTestStep(actionName.trim().toUpperCase(), actionValue.trim(), elmList.get(0), getOnlyTag(testElement),testStep, testDescription);
                    } 
                }   
                break;
            default:
              failTestStep.put(testStep.getKey(), "keyword ["+testRunStep+"] does not exist");
              keywordInvalid = false;
              break;    
        }
    }
    
    public static String getTestStepFromString(String testStep){
        int charIndex =0;
        char[] assyTestStep =testStep.toCharArray();
        for(char x: assyTestStep){
            charIndex++;
            if(x ==':'){
                break;
            }
        }
        
        testStep =testStep.substring(charIndex);
        return testStep.trim();
    }
    
        public static String getCurrentDateAndTime(){
        LocalDateTime myDateObj = LocalDateTime.now();  
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");  
        String formattedDate = myDateObj.format(myFormatObj);
        
        return formattedDate;
    }
    
    public static void createHtmlTestReport(String htmlTestReportContents, String testSuiteName) {
        if(!new File(constants.userDir+"/htmlTestReport").exists()){
            new File(constants.userDir+"/htmlTestReport").mkdir();
        }
        
        try {
            String getTestReportFileName =constants.userDir+"/htmlTestReport/"+testSuiteName.split(".xlsx")[0]+"~"+common.getCurrentDateTimeMS()+".html";
            FileWriter writer = new FileWriter(getTestReportFileName);
            writer.close();
            
            File htmlTestReportFile = new File(getTestReportFileName);
            FileUtils.writeStringToFile(htmlTestReportFile, htmlTestReportContents,StandardCharsets.UTF_8);
            
            if(Desktop.isDesktopSupported()){
                Desktop desktop = Desktop.getDesktop();
                File file = new File(constants.userDir+"/htmlTestReport/"+testSuiteName.split(".xlsx")[0]+"~"+common.getCurrentDateTimeMS()+".html");   
                if(file.exists()) 
                    desktop.open(file);
            }
        } catch (IOException exp) {
            glueCode.stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }
    }
    
    public static String getTestrunTime(Date runStartDate, Date runEndDate){
        // Calculating the difference in milliseconds
        long differenceInMilliSeconds =Math.abs(runStartDate.getTime() - runEndDate.getTime());
  
        // Calculating the difference in Hours
        long differenceInHours =(differenceInMilliSeconds / (60 * 60 * 1000))% 24;
  
        // Calculating the difference in Minutes
        long differenceInMinutes =(differenceInMilliSeconds / (60 * 1000))% 60;
  
        // Calculating the difference in Seconds
        long differenceInSeconds =(differenceInMilliSeconds / 1000)% 60;
        
        return differenceInHours +":"+ differenceInMinutes +":"+ differenceInSeconds;
    }
    
    public static void runTimmerFromCurrentTime(int secs){
        Date currDate = new Date();
        Date newDate = DateUtils.addSeconds(currDate, secs);
        
        do {
            currDate = new Date();
        }while(currDate.before(newDate));
    }
    
    /* get keyboard keycodes */
    public static int keywordEvent(String keywordName) {
        int keyCode =-1;
        switch(keywordName) {
            case "CTRL":
            case "CONTROL":
                keyCode =KeyEvent.VK_CONTROL;
                break;
            case "ENTER":
                keyCode =KeyEvent.VK_ENTER;
                break;    
            case "SHIFT":
                keyCode =KeyEvent.VK_SHIFT;
                break;
            case "DEL":    
            case "DELETE":
                keyCode =KeyEvent.VK_DELETE;
                break;
            case "TAB":
                keyCode =KeyEvent.VK_TAB;
                break;
            case "ESCAPE":    
            case "ESC":
                keyCode =KeyEvent.VK_ESCAPE;
                break;
            case "A":
                keyCode =KeyEvent.VK_A;
                break;
            case "F2":
                keyCode =KeyEvent.VK_F2;
                break;    
            default:
                keyCode =-1;
        }
        return keyCode;
    }
    
    /* kill all the given process by name */
    public static void killProcess(String porcessName){
        try {
            Runtime.getRuntime().exec("taskkill /F /IM " +porcessName);
        } catch (IOException ex) {
            Logger.getLogger(common.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void attachScreenShotInWordDocument() {
    	glueCode.keyTakeScreenShot();
    	common.addScreenShotToDoc(common.docx,glueCode.screenshotPath);
    }
    
    public static String getUserDefineClassLocation() {
    	String fileNameToFind = "userDefine.java";
		File rootDirectory = new File(System.getProperty("user.dir"));

		final List<File> foundFiles = new ArrayList<>();
		
		try (Stream<Path> walkStream = Files.walk(rootDirectory.toPath())) {
			  walkStream.filter(p -> p.toFile().isFile())
			      .forEach(f -> {
			    	  System.err.println(f.toString());
			        if (f.toString().endsWith(fileNameToFind)) {
			          foundFiles.add(f.toFile());
			        }
			      });
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return foundFiles.get(0).getParent(); 
    }
    
    public static String writeJsonPayloadToTheTextArea(String jsonPayload){
    	String prettyJson ="";
        
    	if(jsonPayload ==null || jsonPayload.isEmpty())
            return jsonPayload="";
    	
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonElement jsonElement = JsonParser.parseString(jsonPayload);
            prettyJson = gson.toJson(jsonElement);
            jsonPayload =prettyJson;
        } catch (JsonParseException ex) {
            //jsonPayload =jsonPayload;
            JOptionPane.showMessageDialog(null,"Invalid json body!","Alert",JOptionPane.WARNING_MESSAGE);
        }
        
        return jsonPayload;
    }
    
    public static HashMap<Integer, Object> uploadSSLCertConfiguration() {
        JSONParser parser =new JSONParser();
        FileReader reader;
        HashMap<Integer, Object> jsonMap =new HashMap<>();

        try {
                reader =new FileReader("./ssl/sslCert.json");
                Object objJson;
            try {
                objJson =parser.parse(reader);
                JSONArray certList =(JSONArray) objJson;

                for (int i = 0; i < certList.size(); i++) {   
                        JSONObject certObject = (JSONObject) certList.get(i);

                Object getName =certObject.get("name");
                if(getName ==null)
                    getName ="";

                Object getKeyStore =certObject.get("keystore");
                if(getKeyStore ==null)
                    getKeyStore ="";

                Object getKeyStorePwd =certObject.get("keystore-pwd");
                if(getKeyStorePwd ==null)
                    getKeyStorePwd ="";

                Object getTrustStore =certObject.get("truststore");
                if(getTrustStore ==null)
                    getTrustStore ="";

                Object getTrustStorePwd =certObject.get("truststore-pwd");
                if(getTrustStorePwd ==null)
                    getTrustStorePwd ="";

                Object getJsonObj =getName +","+ 
                getKeyStore +","+ 
                getKeyStorePwd +","+ 
                getTrustStore +","+ 
                getTrustStorePwd;

                jsonMap.put(i+1, getJsonObj);
            }

            } catch (IOException | ParseException e) {}

        } catch (FileNotFoundException e) {}

        return jsonMap;
    }
}
