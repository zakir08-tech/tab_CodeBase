package com.automation.bolt;

import static com.automation.bolt.common.keywordEvent;
import static com.automation.bolt.common.runTimmerFromCurrentTime;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class glueCode {
    public static WebDriver boltDriver;
    public static String waitTimeInSeconds;
    public static WebDriverWait wait;
    public static Boolean stepSuccess =null;
    public static Actions action;
    public static String screenshotPath;
    public static boolean runHeadless;
    public static String implicitWaitTime;
    public static String pageLoadTimeOut;
    public static int parentTagIndex;
    public static Robot robotClassObject;
    public static Alert alert;
    private static ClassLoader classLoader;
    public static String dataUrl;
    
    public static void getWebDriver(String browserType) {
        waitTimeInSeconds = constants.waitTimeInSec;
        List<String> getDriverOptions = readJsonToList();
        
        if(browserType.contentEquals("chrome")){
            WebDriverManager.chromedriver().setup();
             		
            ChromeOptions co = new ChromeOptions();
            co.addArguments("--remote-allow-origins=*");
            co.addArguments("--start-maximized");
            co.addArguments("--ignore-certificate-errors");
            co.addArguments("--blink-settings=imagesEnabled=true");
            co.addArguments(getDriverOptions);
            
            /*Add preference to block notifications*/
            HashMap<String, Object> prefs = new HashMap<>();
            prefs.put("profile.default_content_setting_values.notifications", 2); // 2 = Block
            co.setExperimentalOption("prefs", prefs);
            
            if(runHeadless ==true) {
            	co.addArguments("window-size=1980,960");
            	co.addArguments("--headless");
            }
                
            boltDriver = new ChromeDriver(co);      
        }else if(browserType.contentEquals("edge")){
            WebDriverManager.edgedriver().setup();
          
            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.addArguments("--remote-allow-origins=*");
            edgeOptions.addArguments("--start-maximized");
            edgeOptions.addArguments("--ignore-certificate-errors");
            edgeOptions.addArguments("--blink-settings=imagesEnabled=true");
            edgeOptions.addArguments(getDriverOptions);
            
            /*Add preference to block notifications*/
            HashMap<String, Object> prefs = new HashMap<>();
            prefs.put("profile.default_content_setting_values.notifications", 2); // 2 = Block
            edgeOptions.setExperimentalOption("prefs", prefs);
            
            if(runHeadless ==true) {
            	edgeOptions.addArguments("window-size=1980,960");
            	edgeOptions.addArguments("--headless");
            }
                
            boltDriver =new EdgeDriver(edgeOptions);
        }
     
        //boltDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.valueOf(implicitWaitTime)));
        boltDriver.manage().timeouts().implicitlyWait(Integer.valueOf(implicitWaitTime), TimeUnit.SECONDS);
        //boltDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Integer.valueOf(pageLoadTimeOut)));
        boltDriver.manage().timeouts().pageLoadTimeout(Integer.valueOf(implicitWaitTime), TimeUnit.SECONDS);
        wait = new WebDriverWait(boltDriver,Integer.valueOf(implicitWaitTime));
        //wait = new WebDriverWait(boltDriver, Duration.ofSeconds(Integer.valueOf(implicitWaitTime)));
    }
	
    public static void keyURL(String url) {
        stepSuccess = true;
        try {
            boltDriver.get(url);
        }catch(IllegalArgumentException|
                NullPointerException|
                NoSuchElementException|
                ElementNotInteractableException|
                TimeoutException|StaleElementReferenceException exp) {
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }catch(WebDriverException exp){
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }
    }
    
    public static void keySet(WebElement elm, String setValue) {
        stepSuccess = true;
        String getMethodName =null;
        String[] getMethodArgs =null;
        
        if(elm ==null) {
            stepSuccess = false;
            boltRunner.logError = "No test element defined to SET value "+"\""+setValue+"\"";
            return;
        }
        
        try {
            elm.sendKeys(setValue);
        }catch(IllegalArgumentException|
                NullPointerException|
                NoSuchElementException|
                ElementNotInteractableException|
                TimeoutException|
                StaleElementReferenceException exp) {
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }catch(WebDriverException exp){
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }
    }
    
    public static void keySelectSalaryRange(WebElement elm, String setValue) {
        stepSuccess = true;

        if(elm ==null) {
            stepSuccess = false;
            boltRunner.logError = "No test element defined for "+"\""+"SELECT_SAL_RANGE"+"\"";
            return;
        }

        try {
        	String rangeMax = elm.getAttribute("max");
        	String rangeStep = elm.getAttribute("step");
        	
        	if(setValue.isEmpty()) {
        		setValue ="0";
        	}
        	
        	if(rangeStep ==null) {
        		rangeStep = "0.5";
        	}
        	
        	if(rangeMax !=null) {
        		if(Double.valueOf(setValue)>Double.valueOf(rangeMax)) {
            		setValue = rangeMax;
            	}
        	}
        	
            double barMovement =Double.parseDouble(setValue)/Float.valueOf(rangeStep);
            
            JavascriptExecutor executor = (JavascriptExecutor)glueCode.boltDriver;
            executor.executeScript("arguments[0].click();", elm);
            
            //elm.click();
            elm.sendKeys(Keys.HOME);
            
            for(int i=1; i<=barMovement;i++) {
                elm.sendKeys(Keys.ARROW_RIGHT);
            }
        }catch (IllegalArgumentException|
                NullPointerException|
                NoSuchElementException|
                ElementNotInteractableException|
                TimeoutException|
                StaleElementReferenceException exp) {
            stepSuccess = false;
            boltRunner.logError = exp.toString();
            boltExecutor.log.error(exp);
        }catch(WebDriverException exp){
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }
    }
    
    /* description: upload file */
    public static void keyUploadFile(WebElement elm, String filePath){
        stepSuccess = true;
        
        if(elm ==null) {
            stepSuccess = false;
            boltRunner.logError = "No test element defined to UPLOAD_FILE for value "+"\""+filePath+"\"";
            return;
        }
        
        try {
            elm.sendKeys(filePath);
        }catch(IllegalArgumentException|
                NullPointerException|
                NoSuchElementException|
                ElementNotInteractableException|
                TimeoutException|
                StaleElementReferenceException exp) {
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }catch(WebDriverException exp){
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }
    }
    
    /* description: clear web-element text */
    public static void keyClear(WebElement elm) {
        stepSuccess = true;

        if(elm ==null) {
            stepSuccess = false;
            boltRunner.logError = "No test element defined to CLEAR value";
            return;
        }

        try {
            elm.clear();
        }catch(IllegalArgumentException|
                NullPointerException|
                NoSuchElementException|
                ElementNotInteractableException|
                TimeoutException|
                StaleElementReferenceException exp) {
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }catch(WebDriverException exp){
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }
    }
    
    /* description: get web-element attached text */
    public static String keyGet(WebElement elm) {
        stepSuccess = true;
        String getElmTxt ="";
        
        if(elm ==null) {
            stepSuccess = false;
            boltRunner.logError = "No test element defined for "+"\""+"GET"+"\"";
            return getElmTxt;
        }

        try {
            byte getElmTxtByte[] =elm.getText().getBytes(StandardCharsets.UTF_8);
            getElmTxt = new String(getElmTxtByte, StandardCharsets.UTF_8);
        }catch(IllegalArgumentException|
                NullPointerException|
                NoSuchElementException|
                ElementNotInteractableException|
                TimeoutException|
                StaleElementReferenceException exp) {
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }catch(WebDriverException exp){
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }
        
        return getElmTxt;
    }
	
    public static void keyAssertText(WebElement elm, String assertValue) {
        stepSuccess = true;
        if(elm ==null) {
            stepSuccess = false;
            boltRunner.logError = "No test element defined to ASSERT_TEXT for value "+"\""+assertValue+"\"";
            return;
        }

        String getText = elm.getText().replaceAll("\n", " ");
        try {
            if(getText.toLowerCase().contentEquals(assertValue.toLowerCase()) ==false) {
                stepSuccess = false;
                boltRunner.logError = "Required expected text "+"\""+assertValue+"\""+" not equals to actual text "+"\""+elm.getText()+"\"";
            }
        }catch(IllegalArgumentException|
                NullPointerException|
                NoSuchElementException|
                ElementNotInteractableException|
                TimeoutException|
                StaleElementReferenceException exp) {
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }catch(WebDriverException exp){
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }
    }
	
    public static void keySwitchToWindow(String title) {
        boolean foundWindow = false;
        stepSuccess = true;
        
        try {
            for (String handle : boltDriver.getWindowHandles()) {
                if (boltDriver.switchTo().window(handle).getTitle().contains(title)) {
                    foundWindow = true;
                    break;
                }
            }
        }catch(WebDriverException exp){
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
            return;
        }
        
        if(foundWindow ==false){
            stepSuccess = false;
            boltRunner.logError = "Could not find the window with title "+"\""+title+"\"";
        }
    }
      
    public static void keyPressKey(WebElement elm, String key) {
        boolean keyExist = true;
        stepSuccess = true;

        if(elm ==null) {
            stepSuccess = false;
            boltRunner.logError = "No test element defined for "+"\""+key.trim().toUpperCase()+"\" key";
            return;
        }

        switch(key.trim().toUpperCase()) {
            case "ALT":
              elm.sendKeys(Keys.ALT);
              break;
            case "ARROW_DOWN":
              elm.sendKeys(Keys.ARROW_DOWN);
              break;
            case "ARROW_LEFT":
              elm.sendKeys(Keys.ARROW_LEFT);
              break;
            case "ARROW_RIGHT":
              elm.sendKeys(Keys.ARROW_RIGHT);
              break;
            case "ARROW_UP":
              elm.sendKeys(Keys.ARROW_UP);
              break;
            case "BACK_SPACE":
              elm.sendKeys(Keys.DELETE);
              break;
            case "CLEAR":
              elm.sendKeys(Keys.CLEAR);
              break;
            case "CTRL":
            case "CONTROL":
              elm.sendKeys(Keys.CONTROL);
              break;
            case "DEL":  
            case "DELETE":
              elm.sendKeys(Keys.BACK_SPACE);
              break;
            case "ESC":  
            case "ESCAPE":
              elm.sendKeys(Keys.ESCAPE);
              break;
            case "ENTER":
              elm.sendKeys(Keys.ENTER);
              break;
            case "END":
              elm.sendKeys(Keys.END);
              break;  
            case "F1":
              elm.sendKeys(Keys.F1);
              break;
            case "F2":
              elm.sendKeys(Keys.F2);
              break;
            case "F3":
              elm.sendKeys(Keys.F3);
              break; 
            case "F4":
              elm.sendKeys(Keys.F4);
              break;
            case "F5":
              elm.sendKeys(Keys.F5);
              break;
            case "F6":
              elm.sendKeys(Keys.F6);
              break;
            case "F7":
              elm.sendKeys(Keys.F7);
              break;
            case "F8":
              elm.sendKeys(Keys.F8);
              break;
            case "F9":
              elm.sendKeys(Keys.F9);
              break; 
            case "F10":
              elm.sendKeys(Keys.F10);
              break;
            case "F11":
              elm.sendKeys(Keys.F11);
              break;
            case "F12":
              elm.sendKeys(Keys.F12);
              break;
            case "HELP":
              elm.sendKeys(Keys.HELP);
              break;
            case "HOME":
              elm.sendKeys(Keys.HOME);
              break;
            case "INSERT":
              elm.sendKeys(Keys.INSERT);
              break;
            case "PAGE_DOWN":
              elm.sendKeys(Keys.PAGE_DOWN);
              break;
            case "PAGE_UP":
              elm.sendKeys(Keys.PAGE_UP);
              break;
            case "SHIFT":
              elm.sendKeys(Keys.SHIFT);
              break;
            case "SPACE":
              elm.sendKeys(Keys.SPACE);
              break;
            case "TAB":
              elm.sendKeys(Keys.TAB);
              break;
            default:
              keyExist =false;
          }

        if(keyExist ==false) {
            stepSuccess = false;
            boltRunner.logError = "No such key ["+key+"] exist.";
        }
    }
    
    public static void keyPressKeys(WebElement elm, String keys) {
        stepSuccess = true;
        String getKeyCombination ="";
        
        if(elm ==null) {
            stepSuccess = false;
            boltRunner.logError = "No test element defined for "+"\""+keys.trim().toUpperCase()+"\" key";
            return;
        }
        
        String[] getAllKey =keys.split("[+]");
        for(String keyName: getAllKey){
            getKeyCombination =getKeyCombination + getKeyName(keyName.trim());
        }
        
        elm.sendKeys(Keys.chord(getKeyCombination)); 
    }
    
    public static void keyPageEvent(String keys) {
        stepSuccess =true;
        boolean validKey =true;
        
        try {
            Robot robot = new Robot();
            
            String[] keyAction =keys.split("[+]");
            for(String key:keyAction) {
                if(keywordEvent(key) ==-1){
                    validKey =false;
                    break;
                }   
            }
            
            if(!validKey) {
                stepSuccess = false;
                boltRunner.logError = "Invalid ["+keys.trim().toUpperCase()+"] key(s) defined";
                return;
            }
            
            for(String key:keyAction) {
                robot.keyPress(keywordEvent(key));
            }
            robot.delay(2000);
            
            for(String key:keyAction) {
                robot.keyRelease(keywordEvent(key));
            }
            robot.delay(2000);
        } catch (AWTException exp) {
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }
    }
    
    public static String getKeyName(String getKey){
        String getTheKey ="";
        switch(getKey.trim().toUpperCase()) {
            case "CTRL":
            case "CONTROL":
                getTheKey =Keys.CONTROL.toString();
                break;
            case "ALT":  
                break;
            case "SHIFT":
                getTheKey =Keys.SHIFT.toString();
                break;
            case "DELETE":
            case "DEL":
                getTheKey =Keys.DELETE.toString();
                break;
            case "TAB":
                getTheKey =Keys.TAB.toString();
                break;
            default:
              getTheKey =getKey;
        }
        return getTheKey;
    }
    
    public static void keyMoveToElement(WebElement elm) {
        stepSuccess = true;

        if(elm ==null) {
            stepSuccess = false;
            boltRunner.logError = "No test element defined for "+"\""+"MOVE_TO_ELEMENT"+"\"";
            return;
        }

        try {
            action = new Actions(boltDriver);
            action.moveToElement(elm).perform();
        }catch (IllegalArgumentException|
                NullPointerException|
                NoSuchElementException|
                ElementNotInteractableException|
                TimeoutException|
                StaleElementReferenceException exp) {
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }catch(WebDriverException exp){
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }
    }
    
    public static void keyAssertClickable(WebElement elm) {
        stepSuccess = true;

        if(elm ==null) {
            stepSuccess = false;
            boltRunner.logError = "No test element defined for "+"\""+"ASSERT_CLICKABLE"+"\"";
            return;
        }

        try {
            wait.until(ExpectedConditions.elementToBeClickable(elm));
        }catch(StaleElementReferenceException exp) {
        	if(StaleElementReferenceExceptionRetry() ==true) {
        		stepSuccess = false;
                boltExecutor.log.error(boltRunner.logError);
        	}
        }catch (IllegalArgumentException|
                NullPointerException|
                NoSuchElementException|
                ElementNotInteractableException|
                TimeoutException exp) {
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }catch(WebDriverException exp){
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }
    }
    
    public static boolean StaleElementReferenceExceptionRetry() {
    	boolean staleElmRefExp = true;
    	
    	int retries = 3;
    	for (int i = 1; i <= retries; i++) {
    		boltRunner.getElement = boltRunner.getElement(boltRunner.elmLocator, boltRunner.elmLocatorValue);
    		try {
    			System.out.println("StaleElementReferenceException occurred. Retrying... (" + (i + 1) + "/" + retries + ")");
    			wait.until(ExpectedConditions.elementToBeClickable(boltRunner.getElement));
    			staleElmRefExp = false;
    			break;
    		}catch(StaleElementReferenceException exp) {
    			try {
                    Thread.sleep(3 * 1000); // Wait before retry
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    boltRunner.logError = exp.getMessage();
                }
    		}
    	}
    	return staleElmRefExp;
    }
    
    public static void keyClick(WebElement elm) {
        try{
            wait.until(ExpectedConditions.elementToBeClickable(elm));
            //Actions act = new Actions(boltDriver);
            //act.moveToElement(elm).click().build().perform();
            //elm.click();
            ((JavascriptExecutor) boltDriver).executeScript("arguments[0].click();", elm);
        }catch(NullPointerException|
                NoSuchElementException|
                ElementNotInteractableException|
                TimeoutException|
                StaleElementReferenceException exp) {
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }catch(WebDriverException exp){
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }	
    }
    
    public static void keyUserDefine(String getMethodName, String methodArgs) {
		try {
			//URL[] dependencyUrls = {new URL("file:/"+System.getProperty("user.dir").replaceAll("\\\\", "/")+"/target/tab.jar")};
			//classLoader = new URLClassLoader(dependencyUrls);
			classLoader = ClassLoader.getSystemClassLoader();
			Class<?> loadedClass = classLoader.loadClass("com.automation.bolt.userDefineTest");
			//Class<?> loadedClass = Class.forName("com.automation.bolt.userDefineTest");
			//Class<?> loadedClass = classLoader.loadClass("test.automation.tab.userDefine");
		
			Object obj = loadedClass.getDeclaredConstructor().newInstance();
			Method method = loadedClass.getMethod(getMethodName, String[].class);
			String[] args =methodArgs.split("[|]");
			method.invoke(obj, new Object[] {args});
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException exp) {
			stepSuccess = false;
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);	
            exp.printStackTrace(pw);
			
			boltRunner.logError = sw.toString();
            boltExecutor.log.error(sw.toString());
		}
    }
    
    public static void keyDoubleClick(WebElement elm) {
        try{
            wait.until(ExpectedConditions.elementToBeClickable(elm));
            Actions act = new Actions(boltDriver);
			act.moveToElement(elm).
			doubleClick().
			build().perform();
        }catch(NullPointerException|
                NoSuchElementException|
                ElementNotInteractableException|
                TimeoutException|
                StaleElementReferenceException exp) {
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }catch(WebDriverException exp){
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }	
    }
	
    public static void keyMouseHover(WebElement elm) {
        stepSuccess = true;
        if(elm ==null) {
            stepSuccess = false;
            boltRunner.logError = "No test element defined for "+"\""+"MOUSE_HOVER"+"\"";
            return;
        }

        try {
            Actions builder = new Actions(boltDriver);
            builder.moveToElement(elm).build().perform();
        }catch(InvalidArgumentException|
                NullPointerException|
                NoSuchElementException|
                ElementNotInteractableException|
                TimeoutException|
                StaleElementReferenceException exp) {
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }catch(WebDriverException exp){
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }
    }
	
    public static void keyAssertVisible(WebElement elm) {
        stepSuccess = true;
        try {
            wait.until(ExpectedConditions.visibilityOf(elm));
        }catch(NullPointerException|
                NoSuchElementException|
                ElementNotInteractableException|
                TimeoutException|
                StaleElementReferenceException exp) {
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }catch(WebDriverException exp){
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }
    }
    
    public static void keyAssertSelected(WebElement elm) {
        stepSuccess = true;
        if(elm ==null) {
            stepSuccess = false;
            boltRunner.logError = "No test element defined for "+"\""+"ASSERT_SELECTED"+"\"";
            return;
        }

        try {
            if(!elm.isSelected()){
                stepSuccess =false;
            }
        }catch (IllegalArgumentException|
                NullPointerException|
                NoSuchElementException|
                ElementNotInteractableException|
                TimeoutException|
                StaleElementReferenceException exp) {
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }catch(WebDriverException exp){
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }
    }
    
    public static void keySelectValueByValue(WebElement elm, String textByValue) {
        stepSuccess =true;
        if(elm ==null) {
            stepSuccess = false;
            boltRunner.logError = "No test element defined for "+"\""+"SELECT_BY_VALUE"+"\"";
            return;
        }

        try {
            Select byValue =new Select(elm);
            byValue.selectByValue(textByValue);
        }catch (IllegalArgumentException|
                NullPointerException|
                NoSuchElementException|
                ElementNotInteractableException|
                TimeoutException|
                StaleElementReferenceException exp) {
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }catch(WebDriverException exp){
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }
    }
    
    public static void keySelectValueByIndex(WebElement elm, String textByValue) {
        stepSuccess =true;
        if(elm ==null) {
            stepSuccess = false;
            boltRunner.logError = "No test element defined for "+"\""+"SELECT_BY_INDEX"+"\"";
            return;
        }

        try {
            Select byIndex =new Select(elm);
            byIndex.selectByIndex(Integer.valueOf(textByValue));
        }catch (IllegalArgumentException|
                NullPointerException|
                NoSuchElementException|
                ElementNotInteractableException|
                TimeoutException|
                StaleElementReferenceException exp) {
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }catch(WebDriverException exp){
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }
    }
    
    public static void keySelectValueByVisibleText(WebElement elm, String textByValue) {
        stepSuccess =true;
        if(elm ==null) {
            stepSuccess = false;
            boltRunner.logError = "No test element defined for "+"\""+"SELECT_BY_VTEXT"+"\"";
            return;
        }

        try {
            Select byVText =new Select(elm);
            byVText.selectByVisibleText(textByValue);
        }catch (IllegalArgumentException|
                NullPointerException|
                NoSuchElementException|
                ElementNotInteractableException|
                TimeoutException|
                StaleElementReferenceException exp) {
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }catch(WebDriverException exp){
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }
    }
    
    public static void keySubmitAlertPopup() {
        stepSuccess =true;  
        try {
            alert =boltDriver.switchTo().alert();
            alert.accept();
            boltDriver.switchTo().defaultContent();
        }catch (IllegalArgumentException|
                NullPointerException|
                NoSuchElementException|
                ElementNotInteractableException|
                TimeoutException|
                StaleElementReferenceException exp) {
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }catch(WebDriverException exp){
            if(exp.getMessage().contains("unexpected alert open"))
                return;
            
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }
    }
    
    public static boolean isAlertPopVisible(){
        boolean alertPopup =false;
        try{
            alert =boltDriver.switchTo().alert();
            alertPopup =true;
        }catch(WebDriverException exp){}
        return alertPopup;
    }
            
    public static void keyAssertNotSelected(WebElement elm) {
        stepSuccess = true;
        if(elm ==null) {
            stepSuccess = false;
            boltRunner.logError = "No test element defined for "+"\""+"ASSERT_NOT_SELECTED"+"\"";
            return;
        }

        try {
            if(elm.isSelected()){
                stepSuccess =false;
            }
        }catch (IllegalArgumentException|
                NullPointerException|
                NoSuchElementException|
                ElementNotInteractableException|
                TimeoutException|
                StaleElementReferenceException exp) {
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }catch(WebDriverException exp){
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }
    }
    
    public static List<WebElement> getElmList(WebDriver driver, String tagName, String attributeName, String attributeValue) {
        List<WebElement> getChildElms =new ArrayList<WebElement>();
        List<WebElement> getElm =new ArrayList<WebElement>();

        try {
            getElm = driver.findElements(By.tagName(tagName));
        }catch (InvalidSelectorException | NullPointerException exp) {}
        
        try{
            for(WebElement elm: getElm) {
                if(attributeName.isEmpty() && attributeValue.isEmpty()){
                    getChildElms.add(elm);
                }else{
                    try {
                        if(elm.getAttribute(attributeName).contentEquals(attributeValue))
                            getChildElms.add(elm);
                    }catch(NullPointerException | IndexOutOfBoundsException exp) {}
                }
            }
            
            if(getChildElms.isEmpty()){
                stepSuccess = false;
                boltRunner.logError = "No elements found.";
            }
        }catch(WebDriverException exp) {
            stepSuccess = false;
            boltRunner.logError =exp.getMessage();
        }catch (NullPointerException|
                NoSuchElementException exp){}
      
        return getChildElms;
    }
    
    public static List<WebElement> getElmList(List<WebElement> elm, String tagName, String attributeName, String attributeValue) {
        ArrayList<WebElement> getChildElms = new ArrayList<WebElement>();
        
        if(elm ==null){
            stepSuccess = false;
            boltRunner.logError = "No elements found.";
            return getChildElms;
        }
        
        try{
            for(WebElement getElm: elm) {
                List<WebElement> getElmList = getElm.findElements(By.tagName(tagName));
                for(WebElement getElmChild: getElmList) {
                    if(attributeName.isEmpty() && attributeValue.isEmpty()){
                        getChildElms.add(getElmChild);
                    }else{
                        try{
                        if(getElmChild.getAttribute(attributeName).contentEquals(attributeValue))
                            getChildElms.add(getElmChild);
                        }catch (NullPointerException | IndexOutOfBoundsException exp){}
                    }
                }
            }
            if(getChildElms.isEmpty()){
                stepSuccess = false;
                boltRunner.logError = "No elements found.";
            }
        }catch (WebDriverException exp){
            stepSuccess = false;
            boltRunner.logError =exp.getMessage();
        }catch (NullPointerException|
                NoSuchElementException exp){}
      
        return getChildElms;
    }
    
    public static List<WebElement> getElmListIF(List<WebElement> elm, String tagName, String attributeName, String attributeValue) {
        List<WebElement> getChildElms = new ArrayList<WebElement>();
        boolean elmFnd =false;
        int elmIterator =-1;
        
        if(elm ==null){
            stepSuccess = false;
            boltRunner.logError = "No elements found.";
            return getChildElms;
        }
        
        try{
            for(WebElement getElm: elm) {
            elmIterator++; 
            List<WebElement> getElmList = getElm.findElements(By.tagName(tagName));

            for(WebElement getElmChild: getElmList) {
                if(attributeName.toLowerCase().contentEquals("attachedtext")) {
                    if(getElmChild.getText().toLowerCase().trim().contentEquals(attributeValue.toLowerCase().trim())) {
                        getChildElms.add(getElmChild);
                        elmFnd =true;
                        parentTagIndex =elmIterator;
                        break;
                    }
                }else{
                    try{
                        if(getElmChild.getAttribute(attributeName).contentEquals(attributeValue)){
                            getChildElms.add(getElmChild);
                            elmFnd =true;
                            parentTagIndex =elmIterator;
                            break;
                        }
                    }catch (NullPointerException | IndexOutOfBoundsException exp){}
                }
            }
                if(elmFnd ==true)
                    break;
            }
            
            if(getChildElms.isEmpty()){
                stepSuccess = false;
                boltRunner.logError = "No elements found.";
            }
        }catch(WebDriverException exp){
            stepSuccess = false;
            boltRunner.logError =exp.getMessage();
        }catch(NullPointerException|
                NoSuchElementException exp){}

        return getChildElms;
    }
    
    public static List<WebElement> getElmListIF(WebDriver driver, String tagName, String attributeName, String attributeValue) {
        List<WebElement> getChildElms = new ArrayList<WebElement>();
        List<WebElement> elmList =new ArrayList<WebElement>();
        boolean elmFnd =false;
        int elmIterator =-1;
        
        if(driver ==null){
            stepSuccess = false;
            boltRunner.logError = "No elements found.";
            return getChildElms;
        }
        
        try {
            elmList= driver.findElements(By.tagName(tagName));
        }catch (InvalidSelectorException | NullPointerException exp) {}
        
        try{
            for(WebElement getElm: elmList) {
                elmIterator++; 
                    if(attributeName.toLowerCase().contentEquals("attachedtext")) {
                        if(getElm.getText().toLowerCase().trim().contentEquals(attributeValue.toLowerCase().trim())) {
                            getChildElms.add(getElm);
                            elmFnd =true;
                            parentTagIndex =elmIterator;
                            break;
                        }
                    }else{
                        try{
                            if(getElm.getAttribute(attributeName).contentEquals(attributeValue)){
                                getChildElms.add(getElm);
                                elmFnd =true;
                                parentTagIndex =elmIterator;
                                break;
                            }
                        }catch (NullPointerException | IndexOutOfBoundsException exp){}
                    }
                    
                if(elmFnd ==true)
                        break;
            }
            if(getChildElms.isEmpty()){
                stepSuccess = false;
                boltRunner.logError = "No elements found."; 
            }
        }catch (WebDriverException exp){
            stepSuccess = false;
            boltRunner.logError =exp.getMessage();
        }catch (NullPointerException|
                NoSuchElementException exp){}
        
        return getChildElms;
    }
    
    public static WebElement getElmListTHEN(List<WebElement> elm, String tagName, String attributeName, String attributeValue, String doAction) {
        WebElement getChildElm =null;
        
        try{
            try{
                WebElement getTheFinalElm =elm.get(parentTagIndex);
                List<WebElement> getElmList = getTheFinalElm.findElements(By.tagName(tagName));

                for(WebElement getElmChild: getElmList) {
                	if(attributeName.toLowerCase().contentEquals("attachedtext")) {
                        if(getElmChild.getText().toLowerCase().trim().contentEquals(attributeValue.toLowerCase().trim())) {
                            getChildElm =getElmChild;
                            break;
                        }
                    }else{
                    	getChildElm =getElmChild;
                        if(getElmChild.getAttribute(attributeName).contentEquals(attributeValue)){
                            getChildElm =getElmChild;
                            break;
                        }
                    }   
                }   
            }catch(NullPointerException | IndexOutOfBoundsException exp){}
        
            if(getChildElm ==null){
                stepSuccess = false;
                boltRunner.logError = "No element found.";
            }   
        }catch(WebDriverException exp){
            stepSuccess = false;
            boltRunner.logError =exp.getMessage();
        }catch(NullPointerException|
                NoSuchElementException exp){}
        
        return getChildElm;
    }
    
    public static void keyOpenNewWindow(String testData) {
        try {
            ((JavascriptExecutor)boltDriver).executeScript("window.open()");
            ArrayList<String> tabs = new ArrayList<String>(boltDriver.getWindowHandles());
            boltDriver.switchTo().window(tabs.get(tabs.size()-1));
            stepSuccess = true;

            boltDriver.get(testData);
        }catch(InvalidArgumentException|
                NullPointerException|
                NoSuchElementException|
                ElementNotInteractableException|
                TimeoutException|
                StaleElementReferenceException exp) {
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }catch(WebDriverException exp){
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }	
    }
    
    public static void keySwitchToIframe(String testData) {
        try {
            try{
                int iframeIndex =Integer.valueOf(testData);
                boltDriver.switchTo().frame(iframeIndex);
                stepSuccess = true;
            }catch (NumberFormatException exp){
                boltDriver.switchTo().frame(testData);
                stepSuccess = true;
            }
            
        }catch(InvalidArgumentException|
                NullPointerException|
                NoSuchElementException|
                ElementNotInteractableException|
                TimeoutException|
                StaleElementReferenceException exp) {
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }catch(WebDriverException exp){
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }	
    }
    
    public static void keySwitchToDefaultIframe(String testData) {
        try {
            boltDriver.switchTo().defaultContent();
            stepSuccess = true;
        }catch(InvalidArgumentException|
                NullPointerException|
                NoSuchElementException|
                ElementNotInteractableException|
                TimeoutException|
                StaleElementReferenceException exp) {
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }catch(WebDriverException exp){
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }	
    }
	
    public static void keyMoveToWindow(String testData) {
        stepSuccess = true;
        
        try{
            String Parent_window = boltDriver.getWindowHandle();
            boolean winFound = false;
            ArrayList<String> tabs = new ArrayList<String>(boltDriver.getWindowHandles());

            for(int i=0; i<tabs.size();i++) {
                boltDriver.switchTo().window(tabs.get(i));
                if(boltDriver.getTitle().contentEquals(testData)) {
                    winFound = true;
                    break;
                }
            }

            if(winFound == false) {
                boltDriver.switchTo().window(Parent_window);
                stepSuccess = false;
                boltRunner.logError = "Required window with title "+"\""+testData+"\" not found";
            }
        }catch(InvalidArgumentException|
                NullPointerException|
                NoSuchElementException|
                ElementNotInteractableException|
                TimeoutException|
                StaleElementReferenceException exp) {
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }catch(WebDriverException exp){
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }	
    }
    
    public static Boolean keyTakeScreenShot(WebElement elm) {
        stepSuccess = true;
        screenshotPath = "";
        JavascriptExecutor jse = null;

        try {
            if(elm !=null) {
                keyMoveToElement(elm);
                jse = (JavascriptExecutor) boltDriver;
                jse.executeScript("arguments[0].style.border='3px solid red'", elm);
                Thread.sleep(1000);
            }
            
            //Screenshot s=new AShot().shootingStrategy(ShootingStrategies.viewportPasting(2000)).takeScreenshot(boltDriver);
            //screenshotPath = constants.userDir+"/screenShots/screenShot_"+common.getCurrentDateTimeMS()+".png";
            //ImageIO.write(s.getImage(),"PNG",new File(screenshotPath));
              
            TakesScreenshot ts = (TakesScreenshot)boltDriver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            screenshotPath = constants.userDir+"/screenShots/screenShot_"+common.getCurrentDateTimeMS()+".png";
            FileUtils.copyFile(source, new File(screenshotPath));

            if(elm !=null) {
                jse.executeScript("arguments[0].style.border='0px solid red'", elm);
                Thread.sleep(1000);
            }
            
            // Read image bytes
            byte[] imageBytes = Files.readAllBytes(Paths.get(screenshotPath));
            
            // Convert to Base64 string
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            
            // Determine image MIME type (basic detection based on extension)
            String mimeType = getMimeType(screenshotPath);
            if (mimeType == null) {
                throw new IllegalArgumentException("Unsupported image format: " + screenshotPath);
            }
            // Create data URL
            dataUrl = String.format("data:%s;base64,%s", mimeType, base64Image);
            
        } catch (RuntimeException | IOException | InterruptedException exp) {
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }
        return stepSuccess;
    }
    
    public static Boolean keyTakeScreenShot() {
        stepSuccess = true;
        screenshotPath = "";
        
        try {
            TakesScreenshot ts = (TakesScreenshot)boltDriver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            screenshotPath = constants.userDir+"/screenShots/screenShot_"+common.getCurrentDateTimeMS()+".png";
            FileUtils.copyFile(source, new File(screenshotPath));
            
            // Read image bytes
            byte[] imageBytes = Files.readAllBytes(Paths.get(screenshotPath));
            
            // Convert to Base64 string
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            
            // Determine image MIME type (basic detection based on extension)
            String mimeType = getMimeType(screenshotPath);
            if (mimeType == null) {
                throw new IllegalArgumentException("Unsupported image format: " + screenshotPath);
            }
            // Create data URL
            dataUrl = String.format("data:%s;base64,%s", mimeType, base64Image);
            
        } catch (RuntimeException | IOException exp) {
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }
        return stepSuccess;
    }
    
    // Helper method to determine MIME type based on file extension
    private static String getMimeType(String filePath) {
        String extension = filePath.toLowerCase();
        if (extension.endsWith(".png")) {
            return "image/png";
        } else if (extension.endsWith(".jpg") || extension.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (extension.endsWith(".gif")) {
            return "image/gif";
        } else if (extension.endsWith(".bmp")) {
            return "image/bmp";
        }
        return null; // Unsupported format
    }
    
    public static Boolean keyTakeScreenShotRobot() {
        stepSuccess = true;
        try {
            BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            runTimmerFromCurrentTime(2);
            screenshotPath =constants.userDir+"/screenShots/screenShot_"+common.getCurrentDateTimeMS()+".png";
            ImageIO.write(image, "png",new File(screenshotPath)); 
        } catch (RuntimeException | IOException | AWTException exp) {
            stepSuccess = false;
            boltRunner.logError = exp.getMessage();
            boltExecutor.log.error(exp);
        }
        return stepSuccess;
    }
    
    public static List<String> readJsonToList() {
        List<String> result = new ArrayList<>();
        JSONParser parser = new JSONParser();
        FileReader reader = null;
        String filePath = System.getProperty("user.dir") + "/config/driverOptions.json";
        		
        try { 	
            // Attempt to open and read the file
            reader = new FileReader(filePath);
            
            // Parse JSON file
            Object obj = parser.parse(reader);
            
            // Verify if parsed object is a JSONObject
            if (!(obj instanceof JSONObject)) {
                throw new IllegalArgumentException("Root element must be a JSON object");
            }
            
            JSONObject jsonObject = (JSONObject) obj;
            
            // Iterate through JSON object entries
            for (Object entryObj : jsonObject.entrySet()) {
                @SuppressWarnings("unchecked")
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>) entryObj;
                String key = entry.getKey();
                Object value = entry.getValue();
                
                // Handle null values
                if (key == null) {
                    System.err.println("Warning: Found null key, skipping entry");
                    continue;
                }
                
             // Format the entry: key=value if value exists, otherwise just key
                String formattedPair = (value != null) ? String.format("%s=%s", key, value.toString()) : key;
                
                result.add(formattedPair);
            }
            
        } catch (java.io.FileNotFoundException e) {
            System.err.println("Error: File not found at path: " + filePath);
            return result; // Return empty list
        } catch (IOException e) {
            System.err.println("Error: Unable to read file: " + e.getMessage());
            return result; // Return empty list
        } catch (ParseException e) {
            System.err.println("Error: Invalid JSON structure: " + e.getMessage());
            return result; // Return empty list
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            return result; // Return empty list
        } catch (ClassCastException e) {
            System.err.println("Error: Invalid entry type in JSON object: " + e.getMessage());
            return result; // Return empty list
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            return result; // Return empty list
        } finally {
            // Close the FileReader if it was opened
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("Warning: Error closing file reader: " + e.getMessage());
                }
            }
        }
        
        return result;
    }
}