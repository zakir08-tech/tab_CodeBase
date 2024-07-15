package com.api.automation.bolt;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.automation.bolt.gui.ExecuteApiTest;
import static com.automation.bolt.gui.ExecuteApiTest.arrTestId;
 
public class loadAPITestRunner {
    public static String APITestRun_FILE_PATH;
    public static FileInputStream testRunnerFile;
    public static ExecuteApiTest runApiTest  = new ExecuteApiTest();
    public static Workbook testRunnerWorkBook;
    public static Sheet apiTestSheet;
    public static Iterator<Row> testRunRowIterator;
    public static Row testRunCurrentRow;
    public static LinkedHashMap<Object, Object> apiTestSteps = new LinkedHashMap<>();
    public static LinkedHashMap<Object, LinkedHashMap<Object, Object>> apiTestSuite = new LinkedHashMap<>();
    public static LinkedHashMap<Object, Object> readHeaderMap = new LinkedHashMap<> ();
    public static LinkedHashMap<Object, LinkedHashMap<Object, Object>> saveHeaderMap = new LinkedHashMap<> ();
    public static LinkedHashMap<Object, Object> getModifyPayloadMap = new LinkedHashMap<> ();
    public static LinkedHashMap<Object, LinkedHashMap<Object, Object>> saveTagElmMap = new LinkedHashMap<> ();
    public static LinkedHashMap<Object, Object> getVerifyResponse = new LinkedHashMap<> ();
    public static LinkedHashMap<Object, LinkedHashMap<Object, Object>> saveVerifyRespTagElmMap = new LinkedHashMap<> ();
    public static LinkedHashMap<Object, Object> savePayloadMap = new LinkedHashMap<> ();
    public static LinkedHashMap<Object, Object> testRunMap = new LinkedHashMap<> ();
    public static LinkedHashMap<Object, HashMap<Object, Object>> saveTestRunMap = new LinkedHashMap<> ();
    public static LinkedHashMap<Object, HashMap<Object, Object>> ApiTestRunnerMap = new LinkedHashMap<> ();
 
    //public static void main(String[] args){
    public void loadApiTest(){
        APITestRun_FILE_PATH =runApiTest.testSuiteFilePath;
        saveVerifyRespTagElmMap = new LinkedHashMap<> ();
        apiTestSteps = new LinkedHashMap<>();
        apiTestSuite = new LinkedHashMap<>();
        readHeaderMap = new LinkedHashMap<> ();
        saveHeaderMap = new LinkedHashMap<> ();
        getModifyPayloadMap = new LinkedHashMap<> ();
        saveTagElmMap = new LinkedHashMap<> ();
        getVerifyResponse = new LinkedHashMap<> ();
        testRunMap = new LinkedHashMap<> ();
        saveTestRunMap = new LinkedHashMap<> ();
        ApiTestRunnerMap = new LinkedHashMap<> ();
        
        try {
            testRunnerFile = new FileInputStream(new File(APITestRun_FILE_PATH));
            testRunnerWorkBook = new XSSFWorkbook(testRunnerFile);
            apiTestSheet = testRunnerWorkBook.getSheetAt(0);
            testRunRowIterator = apiTestSheet.iterator();
            Object getLastTestId =null;
 
            while (testRunRowIterator.hasNext()) {
                testRunCurrentRow = testRunRowIterator.next();
                if (testRunCurrentRow.getRowNum() != 0) {
                    Object getTestId =getCellValue(testRunCurrentRow.getCell(0));
 
                    if(getTestId !=null && !getTestId.toString().isEmpty()){
                        getLastTestId =getTestId;
                        apiTestSteps =new LinkedHashMap<>();
                        readHeaderMap =new LinkedHashMap<> ();
                        getModifyPayloadMap =new LinkedHashMap<>();
                        getVerifyResponse =new LinkedHashMap<>();
                        testRunMap =new LinkedHashMap<>();
 
                        testRunMap.put("Run ID", getTestId);
 
                        Object request =getCellValue(testRunCurrentRow.getCell(1));
                        testRunMap.put("Request", request);
                        apiTestSteps.put("request",request);
 
                        Object url =getCellValue(testRunCurrentRow.getCell(2));
                        
                        testRunMap.put("Request URL", url);
                        apiTestSteps.put("url",url);
 
                        // define headers
                        Object headers_key =getCellValue(testRunCurrentRow.getCell(3));
                        Object headers_value =getCellValue(testRunCurrentRow.getCell(4));
 
                        if(headers_key !=null && !headers_key.toString().isEmpty() &&
                                headers_value !=null && !headers_value.toString().isEmpty()){
                            if(headers_value.toString().contains("|#")){
                                String s = headers_value.toString().split("[|]")[0];
                                String s1 = headers_value.toString().split("[|]")[1];
                                headers_value = s +"_RefFnd_"+ s1;
                            }
                            readHeaderMap.put(headers_key, headers_value);
                            saveHeaderMap.put(getTestId, readHeaderMap);
                        }
 
                        Object params_key =getCellValue(testRunCurrentRow.getCell(5));
                        apiTestSteps.put("params_key",params_key);
 
                        Object params_value =getCellValue(testRunCurrentRow.getCell(6));
                        apiTestSteps.put("params_value",params_value);
 
                        Object payload =getCellValue(testRunCurrentRow.getCell(7));
                        if(payload !=null && !payload.toString().isEmpty()){
                            testRunMap.put("Payload", payload);
                            //savePayloadMap.put(getTestId, payload);
                        }
                        //apiTestSteps.put("payload",payload);
 
                        Object payload_type =getCellValue(testRunCurrentRow.getCell(8));
                        testRunMap.put("Payload Type", payload_type);
                        apiTestSteps.put("payload_type",payload_type);
 
                        // define modify payload
                        Object modify_payload_key =getCellValue(testRunCurrentRow.getCell(9));
                        Object modify_payload_value =getCellValue(testRunCurrentRow.getCell(10));
 
                        if(modify_payload_key !=null && !modify_payload_key.toString().isEmpty() &&
                                modify_payload_value !=null && !modify_payload_value.toString().isEmpty()){
 
                            if(modify_payload_value.toString().contains("|#")){
                                String s = modify_payload_value.toString().split("[|]")[0];
                                String s1 = modify_payload_value.toString().split("[|]")[1];
                                modify_payload_value = s +"_RefFnd_"+ s1;
                            }
                            getModifyPayloadMap.put(modify_payload_key, modify_payload_value);
                            saveTagElmMap.put(getTestId, getModifyPayloadMap);
                        }
 
                        Object authorization_type =getCellValue(testRunCurrentRow.getCell(13));
                        testRunMap.put("Authorization", authorization_type);
                        apiTestSteps.put("authorization_type",authorization_type);
 
                        Object auth_value1 =getCellValue(testRunCurrentRow.getCell(14));
                        if(auth_value1.toString().contains("|#")){
                            String s = auth_value1.toString().split("[|]")[0];
                            String s1 = auth_value1.toString().split("[|]")[1];
                            auth_value1 = s +"_RefFnd_"+ s1;
                        }
                        testRunMap.put("AuthVal1", auth_value1);
                        apiTestSteps.put("auth_value1",auth_value1);
 
                        Object auth_value2 =getCellValue(testRunCurrentRow.getCell(15));
                        testRunMap.put("AuthVal2", auth_value2);
                        apiTestSteps.put("auth_value2",auth_value2);
 
                        Object ssl =getCellValue(testRunCurrentRow.getCell(16));
                        testRunMap.put("SSL Verification", ssl);
                        apiTestSteps.put("ssl",ssl);
 
                        Object status =getCellValue(testRunCurrentRow.getCell(17));
                        testRunMap.put("Expected Status", status);
                        apiTestSteps.put("status",status);
 
                        // define verify payload
                        Object verify_payload_key =getCellValue(testRunCurrentRow.getCell(18));
                        Object verify_payload_value =getCellValue(testRunCurrentRow.getCell(19));
 
                        if(verify_payload_key !=null && !verify_payload_key.toString().isEmpty() &&
                                verify_payload_value !=null && !verify_payload_value.toString().isEmpty()){
 
                            if(verify_payload_value.toString().contains("|#")){
                                String s = verify_payload_value.toString().split("[|]")[0];
                                String s1 = verify_payload_value.toString().split("[|]")[1];
                                verify_payload_value = s +"_RefFnd_"+ s1;
                            }
                            getVerifyResponse.put(verify_payload_key, verify_payload_value);
                            saveVerifyRespTagElmMap.put(getTestId, getVerifyResponse);
                        }
 
                        Object test_desc =getCellValue(testRunCurrentRow.getCell(20));
                        testRunMap.put("Test Summary", test_desc);
                        apiTestSteps.put("test_desc",test_desc);
 
                        saveTestRunMap.put(getTestId, testRunMap);
                        apiTestSuite.put(getTestId, apiTestSteps);
                    }else if (getTestId ==null || getTestId.toString().isEmpty()){
 
                        // define headers
                        Object getHeadersKey =getCellValue(testRunCurrentRow.getCell(3));
                        Object getHeadersVal =getCellValue(testRunCurrentRow.getCell(4));
 
                        if(getHeadersKey !=null && !getHeadersKey.toString().isEmpty() &&
                                getHeadersVal !=null && !getHeadersVal.toString().isEmpty()){
 
                            if(getHeadersVal.toString().contains("|#")){
                                String s = getHeadersVal.toString().split("[|]")[0];
                                String s1 = getHeadersVal.toString().split("[|]")[1];
                                getHeadersVal = s +"_RefFnd_"+ s1;
                            }
 
                            readHeaderMap.put(getHeadersKey, getHeadersVal);
                            if(saveHeaderMap.get(getLastTestId) ==null)
                                saveHeaderMap.put(getLastTestId, readHeaderMap);
                        }
 
                        // define params
                        Object getParamsKey =getCellValue(testRunCurrentRow.getCell(5));
                        if(getParamsKey !=null && !getParamsKey.toString().isEmpty()){
                            getParamsKey =apiTestSteps.get("params_key") +"|"+ getParamsKey;
                            Object getParamsVal =getCellValue(testRunCurrentRow.getCell(6));
                            getParamsVal =apiTestSteps.get("params_value") +"|"+ getParamsVal;
 
                            apiTestSteps.put("params_key",getParamsKey);
                            apiTestSteps.put("params_value",getParamsVal);
                        }
 
                        // define modify payload
                        Object mPayloadKey =getCellValue(testRunCurrentRow.getCell(9));
                        Object mPayloadVal =getCellValue(testRunCurrentRow.getCell(10));
 
                        if(mPayloadKey !=null && !mPayloadKey.toString().isEmpty() &&
                                mPayloadVal !=null && !mPayloadVal.toString().isEmpty()){
 
                            if(mPayloadVal.toString().contains("|#")){
                                String s = mPayloadVal.toString().split("[|]")[0];
                                String s1 = mPayloadVal.toString().split("[|]")[1];
                                mPayloadVal = s +"_RefFnd_"+ s1;
                            }
 
                            getModifyPayloadMap.put(mPayloadKey, mPayloadVal);
                            if(saveTagElmMap.get(getLastTestId) ==null)
                                saveTagElmMap.put(getLastTestId, getModifyPayloadMap);
                        }
 
                        // define verify payload
                        Object vPayloadKey =getCellValue(testRunCurrentRow.getCell(18));
                        Object vPayloadVal =getCellValue(testRunCurrentRow.getCell(19));
 
                        if(vPayloadKey !=null && !vPayloadKey.toString().isEmpty() &&
                                vPayloadVal !=null && !vPayloadVal.toString().isEmpty()){
 
                            if(vPayloadVal.toString().contains("|#")){
                                String s = vPayloadVal.toString().split("[|]")[0];
                                String s1 = vPayloadVal.toString().split("[|]")[1];
                                vPayloadVal = s +"_RefFnd_"+ s1;
                            }
                            getVerifyResponse.put(vPayloadKey, vPayloadVal);
                            if(saveVerifyRespTagElmMap.get(getLastTestId) ==null)
                                saveVerifyRespTagElmMap.put(getLastTestId, getVerifyResponse);
                        }
                    }
                }
            }
            testRunMap =new LinkedHashMap<>();
            for(int i=0; i<arrTestId.size(); i++){
                ApiTestRunnerMap.put(arrTestId.get(i), saveTestRunMap.get(arrTestId.get(i)));
            }
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
 
    public static Object getCellValue(Cell apiCell){
        Object getCellData =null;
 
        try{
            if (apiCell.getCellType() == CellType.STRING) {
                getCellData =apiCell.getStringCellValue();
            }else if (apiCell.getCellType() == CellType.NUMERIC) {
                getCellData =apiCell.getNumericCellValue();
            }
        }catch(NullPointerException exp){}
 
        return getCellData;
    }
    
}

