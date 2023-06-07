package com.automation.bolt;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
	
public class userDefineTest extends glueCode{
	public static common con;
	public static WebDriver xDriver;
	public static String metaValue;
	public static WebElement getStatus;
	public static htmlReportCommon htmlReport;
	
	@SuppressWarnings("static-access")
	public static void processMetaData(String[] args) {
		xDriver=glueCode.boltDriver;
		metaValue = args[0];
		String increaseBy = args[1];
		String hyperDrive = args[2];
		boolean getProcessStatus = true;
		
		do {
			setMetaValue(metaValue);
			
			for(int i=1;i<=Integer.valueOf(hyperDrive);i++) {
				getProcessStatus =repeatIHProcess(i);
				if (getProcessStatus ==false)
					break;
			}
			int getNewVal = Integer.valueOf(metaValue) + Integer.valueOf(increaseBy);
			metaValue = String.valueOf(getNewVal);
		}while(getProcessStatus ==true);
	}
	
	@SuppressWarnings({ "static-access", "unused" })
	public static boolean repeatIHProcess(int processIndx) {
		
		boolean processStatus =true;
		con.writeStepInWordDocument("starting meta process for " +"[Initiate Hyperdrive "+processIndx +"] with ORT Combination limit: " +metaValue);
		htmlReport.writeStepInHtmlReport("starting meta process for " +"[Initiate Hyperdrive "+processIndx +"] with ORT Combination limit: " +metaValue);
		
		WebElement IH = xDriver.findElement(By.xpath("//*[@class ='cellWrapper' and text()='Initiate Hyperdrive "+processIndx+"']"));
		con.keyAssertClickable(IH);
		IH.click();
		con.runTimmerFromCurrentTime(5);
		
		WebElement Run = xDriver.findElement(By.xpath("//*[@id='anaplan_settings_Actions_Toolbar_0']/span[3]"));
		Run.click();
		con.runTimmerFromCurrentTime(5);
		
		WebElement Run1 = xDriver.findElement(By.xpath("//*[@class='dijitReset dijitInline dijitButtonText' and text()='Run']"));
		con.keyAssertClickable(Run1);
		Run1.click();
		con.runTimmerFromCurrentTime(5);
		
		con.attachScreenShotInWordDocument();
		htmlReport.attachScreenShotInHtmlReport();
		
		WebElement ProgressBar = xDriver.findElement(By.xpath("//span[@id='anaplanDialog_title' and text()='Process']"));
		
		System.err.println("*****start processing");
		do {
			//System.err.println("processing");
		}while(ProgressBar.isDisplayed());
		System.err.println("processing finished*****");
		
		con.attachScreenShotInWordDocument();
		htmlReport.attachScreenShotInHtmlReport();
		
		WebElement CloseProcessResult = xDriver.findElement(By.xpath("//span[@class='dijitReset dijitInline dijitButtonText' and text()='Close']"));
		getStatus = xDriver.findElement(By.xpath("//div[@class='status-text']/strong"));
		String getStatusTxt = getStatus.getText();
			
		con.writeStepInWordDocument(getStatus.getText());
		htmlReport.writeStepInHtmlReport(getStatus.getText());
		//con.writeTestStepWithResult(common.docx, getStatus.getText(), "Pass", "xxxxxx");
		
		if(getStatusTxt.contains("Process failed")) {
			processStatus =false;
			glueCode.stepSuccess =false;
			return processStatus;
		}
		
		CloseProcessResult.click();
		con.runTimmerFromCurrentTime(5);
		
		con.attachScreenShotInWordDocument();
		htmlReport.attachScreenShotInHtmlReport();
		
		WebElement getResponseTime = xDriver.findElement(By.xpath("//*[@class ='cellWrapper' and text()='Initiate Hyperdrive "+processIndx+"']/following::td[3]"));
		String getTheResTime = getResponseTime.getText();
		
		long min = TimeUnit.MILLISECONDS.toMinutes(Long.valueOf(getTheResTime));
	    long sec = TimeUnit.MILLISECONDS.toSeconds(Long.valueOf(getTheResTime)) - 
	       TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(Long.valueOf(getTheResTime)));
	    
		con.writeStepInWordDocument("most recent duration (mins:secs): "+ String.valueOf(min)+":"+String.valueOf(sec));
		htmlReport.writeStepInHtmlReport("most recent duration (mins:secs): "+ String.valueOf(min)+":"+String.valueOf(sec));
		
		if(getStatusTxt.contains("Process failed")) {
			processStatus =false;
			glueCode.stepSuccess =false;
		}
		
		return processStatus;
	}
	
	@SuppressWarnings("static-access")
	public static void setMetaValue(String metaValue) {
		xDriver.switchTo().defaultContent();
		
		xDriver.switchTo().frame(0);
		WebElement MetaParam = xDriver.findElement(By.xpath("//button[@title='Meta Parameters']"));
		con.keyClick(MetaParam);
		
		xDriver.switchTo().frame(0);
		WebElement ORC = xDriver.findElement(By.xpath("//*[@id='anaplan/gridlet/_LinkMixin_1_cell_x_0_y_7']/div"));
		con.keyDoubleClick(ORC);
		
		WebElement ORCTextBox = xDriver.findElement(By.xpath("//input[@id='dijit_form_TextBox_8']"));
		
		con.keyClear(ORCTextBox);
		con.keySet(ORCTextBox, metaValue);
		con.keyPressKey(ORCTextBox, "TAB");
		
		con.writeStepInWordDocument("start run for ORT Combination Limit with :" +metaValue);
		htmlReport.writeStepInHtmlReport("start run for ORT Combination Limit with :" +metaValue);
		con.attachScreenShotInWordDocument();
		htmlReport.attachScreenShotInHtmlReport();
		
		xDriver.switchTo().defaultContent();
		xDriver.switchTo().frame(0);
		
		WebElement actions = xDriver.findElement(By.xpath("//button[@title='Actions']"));
		actions.click();
		con.runTimmerFromCurrentTime(5);
		
		xDriver.switchTo().frame(0);
	}
}
