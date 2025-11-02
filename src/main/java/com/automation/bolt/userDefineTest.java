package com.automation.bolt;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
	
public class userDefineTest extends glueCode{
	static WebElement getStatus;
	static htmlReportCommon htmlReport;
	static glueCode gCode = new glueCode();
	static boltRunner bRunner = new boltRunner();
	static common con = new common();
	static WebDriver xDriver;
	
	public static void checkAndClickMinimumTenderThreshold(String[] args) {
		System.out.println("checkAndClickMinimumTenderThreshold");
	}
	
	public static void checkClickTime(String[] args) {
	    System.out.println(args[0]);
	    System.out.println(args[1]);
	}

	public static void checkIfRegisterAlreadyOpen(String[] args) {
		xDriver = glueCode.boltDriver;
		try {
			
			List<WebElement> getDivs = xDriver.findElements(By.tagName("div"));
			for(WebElement elm: getDivs) {
				try {
					if(elm.getAttribute("aria-label").contentEquals("Register open.")) {
						System.out.println("Register is already open.");
						throw new RuntimeException("Register is already open.");
					}else {
						System.out.println("Opening register...");
					}	
				}catch(NullPointerException exp) {}	
			}
			
			//List<WebElement> registerDivs = xDriver.findElements(By.xpath("//div[@aria-label='Register open.']"));
			//if (!registerDivs.isEmpty()) {
				//System.out.println("Register is already open.");
				//throw new RuntimeException("Register is already open.");
			//} else {
				//System.out.println("Opening register...");
			//}
		} catch (Exception e) {
			System.out.println("Exception while checking Register status: " + e.getMessage());
			throw new RuntimeException("Step failed due to exception: " + e.getMessage(),e);
		}
	}
	
	@SuppressWarnings("static-access")
	public static void selectSalaryRange(String[] args) {
		
		System.out.println("entering user define ");
		
		glueCode gCode = new glueCode();
		xDriver=gCode.boltDriver;
		
		String xSalary =args[0];
		double barMovement = Double.parseDouble(xSalary)/.5;
		
		WebElement slider =xDriver.findElement(By.cssSelector("input[type='range']"));
		System.out.println("object found");
		slider.click();
		slider.sendKeys(Keys.HOME);
		
		for(int i=1; i<=barMovement;i++) {
			slider.sendKeys(Keys.ARROW_RIGHT);
		}
	}
	
	public static void checkForRegister(String[] args){
		//glueCode gCode = new glueCode();
		//xDriver=gCode.boltDriver;
		WebElement registerLogin =null;
		
		String userName = args[0];
		String userPwd = args[1];
		String getAriaLabel;
		
		boolean openRegister = false;
		try {
			List<WebElement> divList = xDriver.findElements(By.tagName("div1"));
			
			for(WebElement divElm: divList) {
				getAriaLabel = divElm.getAttribute("aria-label");
				if(getAriaLabel ==null)
					getAriaLabel ="";
				
				if(getAriaLabel.contentEquals("Register closed.")) {
					openRegister =true;
					break;
				}
			}	
		}catch(WebDriverException | NullPointerException exp) {}
		
		if(openRegister ==true) {
			// add your login code here
		}else {
			
			
			/*
			 * boolean hardwareError = false;
			 * 
			 * try { List<WebElement> errorDivs = xDriver.findElements(By.tagName("div"));
			 * 
			 * for(WebElement elm: errorDivs) {
			 * if(elm.getText().contentEquals("Hardware Error")) { hardwareError = true;
			 * break; } }
			 * 
			 * if(hardwareError ==true) {
			 * System.out.println("Hardware Error found, clicking OK..."); WebElement
			 * okButton = xDriver.findElement(By.xpath("//div[@title='OK']"));
			 * okButton.click();
			 * 
			 * }else { System.out.println("No Hardware Error found, continuing..."); }
			 * }catch(WebDriverException e) {
			 * System.out.println("Exception while checking Hardware Error: " +
			 * e.getMessage());
			 * 
			 * }
			 */
			
			/*
			 * String trPassed =
			 * htmlReport.trTemplateEditStepPassed(htmlReport.trTemplatePassed,
			 * String.valueOf(bRunner.stepTestNumber), bRunner.testDescription,
			 * common.getTestStepFromString(bRunner.testStepResult));
			 * 
			 * bRunner.trTestSteps.put(bRunner.stepTestNumber, trPassed);
			 */
			
			String trPassed = htmlReport.trTemplateEditStepPassed(htmlReport.trTemplateUserDefine, 
					"", 
					"", 
					"set user name");
			
			bRunner.trTestSteps.put(100, trPassed);
			
			trPassed = htmlReport.trTemplateEditStepPassed(htmlReport.trTemplateUserDefine, 
					"", 
					"", 
					"set user password");
						bRunner.trTestSteps.put(200, trPassed);
			
		}
	}
}
