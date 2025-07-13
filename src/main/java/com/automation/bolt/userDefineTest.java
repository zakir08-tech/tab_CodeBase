package com.automation.bolt;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
	
public class userDefineTest extends glueCode{
	static WebElement getStatus;
	static htmlReportCommon htmlReport;
	static glueCode gCode = new glueCode();
	static common con = new common();
	static WebDriver xDriver;
	
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
		glueCode gCode = new glueCode();
		xDriver=gCode.boltDriver;
		WebElement registerLogin =null;
		
		String userName = args[0];
		String userPwd = args[1];
		String getAriaLabel;
		
		boolean openRegister = false;
		try {
			List<WebElement> divList = xDriver.findElements(By.tagName("div"));
			
			for(WebElement divElm: divList) {
				getAriaLabel = divElm.getAttribute("aria-label");
				if(getAriaLabel ==null)
					getAriaLabel ="";
				
				if(getAriaLabel.contentEquals("Register closed.")) {
					openRegister =true;
					break;
				}
			}	
		}catch(WebDriverException exp) {}
		
		if(openRegister ==true) {
			// add you login code here
		}
	}
}
