/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.bolt;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author zakir
 */
public class xlsCommonMethods {   
    public static CellStyle getHeaderStyle(XSSFWorkbook workBook, short index) {
        XSSFFont Headerfont = workBook.createFont();
        CellStyle headerStyle = workBook.createCellStyle();

        headerStyle.setFillBackgroundColor(IndexedColors.BLACK1.getIndex());
        headerStyle.setFillPattern(FillPatternType.ALT_BARS);

        Headerfont.setColor(index);
        Headerfont.setBold(true);

        headerStyle.setFont(Headerfont);

        return headerStyle;
    }
             
    public static XSSFWorkbook createTestFlowDataSheet(XSSFWorkbook testSuite, DefaultTableModel testFlowTableModel){
        XSSFRow excelRow;
        
        XSSFSheet excelSheet = testSuite.createSheet("Test Flow");
        excelRow = excelSheet.createRow(0);

        XSSFFont font = testSuite.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        
        XSSFCellStyle cellStyle = testSuite.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
        cellStyle.setFillPattern(FillPatternType.ALT_BARS);
        
        XSSFFont testRunFont = testSuite.createFont();
        testRunFont.setColor(IndexedColors.LIGHT_BLUE.getIndex());
        XSSFCellStyle testRunStyle = testSuite.createCellStyle();
        testRunStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
        testRunStyle.setFillPattern(FillPatternType.ALT_BARS);
        testRunStyle.setFont(testRunFont);

        XSSFFont testIdFont = testSuite.createFont();
        testIdFont.setColor(IndexedColors.RED.getIndex());
        XSSFCellStyle testIdStyle = testSuite.createCellStyle();
        testIdStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
        testIdStyle.setFillPattern(FillPatternType.ALT_BARS);
        testIdStyle.setFont(testIdFont);

        XSSFFont testFlowFont = testSuite.createFont();
        testFlowFont.setColor(IndexedColors.LIGHT_YELLOW.getIndex());
        XSSFCellStyle testFlowStyle = testSuite.createCellStyle();
        testFlowStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
        testFlowStyle.setFillPattern(FillPatternType.ALT_BARS);
        testFlowStyle.setFont(testFlowFont);

        XSSFFont testElmFont = testSuite.createFont();
        testElmFont.setColor(IndexedColors.AQUA.getIndex());
        XSSFCellStyle testElmStyle = testSuite.createCellStyle();
        testElmStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
        testElmStyle.setFillPattern(FillPatternType.ALT_BARS);
        testElmStyle.setFont(testElmFont);

        XSSFFont testDataFont = testSuite.createFont();
        testDataFont.setColor(IndexedColors.LIGHT_ORANGE.getIndex());
        XSSFCellStyle testDataStyle = testSuite.createCellStyle();
        testDataStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
        testDataStyle.setFillPattern(FillPatternType.ALT_BARS);
        testDataStyle.setFont(testDataFont);

        XSSFFont testDescFont = testSuite.createFont();
        testDescFont.setColor(IndexedColors.LIGHT_GREEN.getIndex());
        XSSFCellStyle testDescStyle = testSuite.createCellStyle();
        testDescStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
        testDescStyle.setFillPattern(FillPatternType.ALT_BARS);
        testDescStyle.setFont(testDescFont);

        XSSFCell cell = excelRow.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(getHeaderStyle(testSuite, IndexedColors.RED.getIndex()));
        cell.setCellValue("Test Id");

        cell = excelRow.createCell(1);
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(getHeaderStyle(testSuite, IndexedColors.WHITE.getIndex()));
        cell.setCellValue("Test Step");

        cell = excelRow.createCell(2);
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(getHeaderStyle(testSuite, IndexedColors.LIGHT_YELLOW.getIndex()));
        cell.setCellValue("Test Flow");

        cell = excelRow.createCell(3);
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(getHeaderStyle(testSuite, IndexedColors.AQUA.getIndex()));
        cell.setCellValue("Test Element");

        cell = excelRow.createCell(4);
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(getHeaderStyle(testSuite, IndexedColors.LIGHT_ORANGE.getIndex()));
        cell.setCellValue("Test Data");

        cell = excelRow.createCell(5);
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(getHeaderStyle(testSuite, IndexedColors.LIGHT_GREEN.getIndex()));
        cell.setCellValue("Test Description");
        
        for (int i = 0; i < testFlowTableModel.getRowCount(); i++) {
            excelRow = excelSheet.createRow(i + 1);
            for (int j = 0; j < testFlowTableModel.getColumnCount(); j++) {
                cell = excelRow.createCell(j);
                cell.setCellType(CellType.STRING);
                cell.setCellStyle(cellStyle);
                try {
                    switch (j) {
                        case 3:
                            cell.setCellStyle(testFlowStyle);
                            break;
                        case 4:
                            cell.setCellStyle(testElmStyle);
                            break;
                        case 5:
                            cell.setCellStyle(testDataStyle);
                            break;
                        case 6:
                            cell.setCellStyle(testDescStyle);
                            break;
                        case 1:
                            cell.setCellStyle(testIdStyle);
                            break;
                        case 0:
                            cell.setCellStyle(testRunStyle);
                            break;
                        default:
                            break;
                    }

                    cell.setCellValue(testFlowTableModel.getValueAt(i, j).toString());
                } catch (NullPointerException exp) {
                    cell.setCellValue("");
                }
            }
        }
        excelSheet.autoSizeColumn(0);
        excelSheet.autoSizeColumn(1);
        excelSheet.autoSizeColumn(2);
        excelSheet.autoSizeColumn(3);
        excelSheet.autoSizeColumn(4);
        excelSheet.autoSizeColumn(5);
        excelSheet.autoSizeColumn(6);

        excelSheet.createFreezePane(0, 1);
        
        return testSuite;
    }
    
    public static XSSFWorkbook createAPITestFlowDataSheet(XSSFWorkbook testSuite, DefaultTableModel testFlowTableModel){
        XSSFRow excelRow;
        
        XSSFSheet excelSheet = testSuite.createSheet("API Test Flow");
        excelRow = excelSheet.createRow(0);

        XSSFFont font = testSuite.createFont();
        //font.setColor(IndexedColors.WHITE.getIndex());
        
        XSSFCellStyle cellStyle = testSuite.createCellStyle();
        cellStyle.setFont(font);
        //cellStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
        //cellStyle.setFillPattern(FillPatternType.ALT_BARS);
        
        XSSFFont testRunFont = testSuite.createFont();
        //testRunFont.setColor(IndexedColors.LIGHT_BLUE.getIndex());
        XSSFCellStyle testRunStyle = testSuite.createCellStyle();
        //testRunStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
        //testRunStyle.setFillPattern(FillPatternType.ALT_BARS);
        testRunStyle.setFont(testRunFont);

        XSSFFont testIdFont = testSuite.createFont();
        //testIdFont.setColor(IndexedColors.RED.getIndex());
        XSSFCellStyle testIdStyle = testSuite.createCellStyle();
        //testIdStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
        //testIdStyle.setFillPattern(FillPatternType.ALT_BARS);
        testIdStyle.setFont(testIdFont);

        XSSFFont testFlowFont = testSuite.createFont();
        //testFlowFont.setColor(IndexedColors.LIGHT_YELLOW.getIndex());
        XSSFCellStyle testFlowStyle = testSuite.createCellStyle();
        //testFlowStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
        //testFlowStyle.setFillPattern(FillPatternType.ALT_BARS);
        testFlowStyle.setFont(testFlowFont);

        XSSFFont testElmFont = testSuite.createFont();
        //testElmFont.setColor(IndexedColors.AQUA.getIndex());
        XSSFCellStyle testElmStyle = testSuite.createCellStyle();
        //testElmStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
        //testElmStyle.setFillPattern(FillPatternType.ALT_BARS);
        testElmStyle.setFont(testElmFont);

        XSSFFont testDataFont = testSuite.createFont();
        //testDataFont.setColor(IndexedColors.LIGHT_ORANGE.getIndex());
        XSSFCellStyle testDataStyle = testSuite.createCellStyle();
        //testDataStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
        //testDataStyle.setFillPattern(FillPatternType.ALT_BARS);
        testDataStyle.setFont(testDataFont);

        XSSFFont testDescFont = testSuite.createFont();
        //testDescFont.setColor(IndexedColors.LIGHT_GREEN.getIndex());
        XSSFCellStyle testDescStyle = testSuite.createCellStyle();
        //testDescStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
        //testDescStyle.setFillPattern(FillPatternType.ALT_BARS);
        testDescStyle.setFont(testDescFont);

        XSSFCell cell = excelRow.createCell(0);
        cell.setCellType(CellType.STRING);
        //cell.setCellStyle(getHeaderStyle(testSuite, IndexedColors.RED.getIndex()));
        cell.setCellValue("Test Id");

        cell = excelRow.createCell(1);
        cell.setCellType(CellType.STRING);
        //cell.setCellStyle(getHeaderStyle(testSuite, IndexedColors.WHITE.getIndex()));
        cell.setCellValue("Request");

        cell = excelRow.createCell(2);
        cell.setCellType(CellType.STRING);
        //cell.setCellStyle(getHeaderStyle(testSuite, IndexedColors.LIGHT_YELLOW.getIndex()));
        cell.setCellValue("URL");

        cell = excelRow.createCell(3);
        cell.setCellType(CellType.STRING);
        //cell.setCellStyle(getHeaderStyle(testSuite, IndexedColors.AQUA.getIndex()));
        cell.setCellValue("Headers (key)");

        cell = excelRow.createCell(4);
        cell.setCellType(CellType.STRING);
        //cell.setCellStyle(getHeaderStyle(testSuite, IndexedColors.LIGHT_ORANGE.getIndex()));
        cell.setCellValue("Headers (value)");

        cell = excelRow.createCell(5);
        cell.setCellType(CellType.STRING);
        //cell.setCellStyle(getHeaderStyle(testSuite, IndexedColors.LIGHT_GREEN.getIndex()));
        cell.setCellValue("Params (key)");
        
        cell = excelRow.createCell(6);
        cell.setCellType(CellType.STRING);
        //cell.setCellStyle(getHeaderStyle(testSuite, IndexedColors.LIGHT_GREEN.getIndex()));
        cell.setCellValue("Params (value)");
        
        cell = excelRow.createCell(7);
        cell.setCellType(CellType.STRING);
        //cell.setCellStyle(getHeaderStyle(testSuite, IndexedColors.LIGHT_GREEN.getIndex()));
        cell.setCellValue("Payload");
        
        cell = excelRow.createCell(8);
        cell.setCellType(CellType.STRING);
        //cell.setCellStyle(getHeaderStyle(testSuite, IndexedColors.LIGHT_GREEN.getIndex()));
        cell.setCellValue("Payload Type");
        
        cell = excelRow.createCell(9);
        cell.setCellType(CellType.STRING);
        //cell.setCellStyle(getHeaderStyle(testSuite, IndexedColors.LIGHT_GREEN.getIndex()));
        cell.setCellValue("Modify Payload (key)");
        
        cell = excelRow.createCell(10);
        cell.setCellType(CellType.STRING);
        //cell.setCellStyle(getHeaderStyle(testSuite, IndexedColors.LIGHT_GREEN.getIndex()));
        cell.setCellValue("Modify Payload (value)");
        
        cell = excelRow.createCell(11);
        cell.setCellType(CellType.STRING);
        //cell.setCellStyle(getHeaderStyle(testSuite, IndexedColors.LIGHT_GREEN.getIndex()));
        cell.setCellValue("Response Tag Name");
        
        cell = excelRow.createCell(12);
        cell.setCellType(CellType.STRING);
        //cell.setCellStyle(getHeaderStyle(testSuite, IndexedColors.LIGHT_GREEN.getIndex()));
        cell.setCellValue("Capture Tag Value (env var)");
        
        cell = excelRow.createCell(13);
        cell.setCellType(CellType.STRING);
        //cell.setCellStyle(getHeaderStyle(testSuite, IndexedColors.LIGHT_GREEN.getIndex()));
        cell.setCellValue("Authorization");
        
        cell = excelRow.createCell(16);
        cell.setCellType(CellType.STRING);
        //cell.setCellStyle(getHeaderStyle(testSuite, IndexedColors.LIGHT_GREEN.getIndex()));
        cell.setCellValue("SSL Validation");
        
        cell = excelRow.createCell(17);
        cell.setCellType(CellType.STRING);
        //cell.setCellStyle(getHeaderStyle(testSuite, IndexedColors.LIGHT_GREEN.getIndex()));
        cell.setCellValue("Expected Status");
        
        cell = excelRow.createCell(18);
        cell.setCellType(CellType.STRING);
        //cell.setCellStyle(getHeaderStyle(testSuite, IndexedColors.LIGHT_GREEN.getIndex()));
        cell.setCellValue("Verify Payload (key)");
        
        cell = excelRow.createCell(19);
        cell.setCellType(CellType.STRING);
        //cell.setCellStyle(getHeaderStyle(testSuite, IndexedColors.LIGHT_GREEN.getIndex()));
        cell.setCellValue("Verify Payload (value)");
        
         cell = excelRow.createCell(20);
        cell.setCellType(CellType.STRING);
        //cell.setCellStyle(getHeaderStyle(testSuite, IndexedColors.LIGHT_GREEN.getIndex()));
        cell.setCellValue("Test Description");
        
        for (int i = 0; i < testFlowTableModel.getRowCount(); i++) {
            excelRow = excelSheet.createRow(i + 1);
            for (int j = 0; j < testFlowTableModel.getColumnCount(); j++) {
                cell = excelRow.createCell(j);
                cell.setCellType(CellType.STRING);
                cell.setCellStyle(cellStyle);
                try {
                    /*switch (j) {
                        case 3:
                            cell.setCellStyle(testFlowStyle);
                            break;
                        case 4:
                            cell.setCellStyle(testElmStyle);
                            break;
                        case 5:
                            cell.setCellStyle(testDataStyle);
                            break;
                        case 6:
                            cell.setCellStyle(testDescStyle);
                            break;
                        case 1:
                            cell.setCellStyle(testIdStyle);
                            break;
                        case 0:
                            cell.setCellStyle(testRunStyle);
                            break;
                        default:
                            break;
                    }*/
                	String getCellText =testFlowTableModel.getValueAt(i, j).toString();
                	
                	if(j ==7) {
                		getCellText =common.writeJsonPayloadToTheTextArea(getCellText);
                	}
                    cell.setCellValue(getCellText);
                } catch (NullPointerException exp) {
                    cell.setCellValue("");
                }
            }
        }
        
        excelSheet.autoSizeColumn(0);
        excelSheet.autoSizeColumn(1);
        excelSheet.autoSizeColumn(2);
        excelSheet.autoSizeColumn(3);
        excelSheet.autoSizeColumn(4);
        excelSheet.autoSizeColumn(5);
        excelSheet.autoSizeColumn(6);
        excelSheet.autoSizeColumn(6);
        excelSheet.autoSizeColumn(7);
        excelSheet.autoSizeColumn(8);
        excelSheet.autoSizeColumn(9);
        excelSheet.autoSizeColumn(10);
        excelSheet.autoSizeColumn(11);
        excelSheet.autoSizeColumn(12);
        excelSheet.autoSizeColumn(13);
        excelSheet.autoSizeColumn(14);
        excelSheet.autoSizeColumn(15);
        excelSheet.autoSizeColumn(16);
        excelSheet.autoSizeColumn(17);
        excelSheet.autoSizeColumn(18);
        excelSheet.autoSizeColumn(19);
   
        excelSheet.createFreezePane(0, 1);
        
        return testSuite;
    }
    
    public static XSSFWorkbook createObjectRepoSheetNew(XSSFWorkbook testElmRepository, DefaultTableModel testRepoTableModel){
        XSSFRow excelRow;
        XSSFCell cell;
          
        XSSFSheet excelSheetOR = testElmRepository.createSheet("Object Repository");
        excelRow = excelSheetOR.createRow(0);

        XSSFFont font = testElmRepository.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        XSSFCellStyle cellStyle = testElmRepository.createCellStyle();
        cellStyle.setFont(font);

        cell = excelRow.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(getHeaderStyle(testElmRepository, IndexedColors.AQUA.getIndex()));
        cell.setCellValue("Test Element");

        cell = excelRow.createCell(1);
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(getHeaderStyle(testElmRepository, IndexedColors.LIGHT_ORANGE.getIndex()));
        cell.setCellValue("id");

        cell = excelRow.createCell(2);
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(getHeaderStyle(testElmRepository, IndexedColors.LIGHT_YELLOW.getIndex()));
        cell.setCellValue("xpath");

        XSSFFont testElmFont = testElmRepository.createFont();
        testElmFont.setColor(IndexedColors.AQUA.getIndex());
        XSSFCellStyle testElmStyle = testElmRepository.createCellStyle();
        testElmStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
        testElmStyle.setFillPattern(FillPatternType.ALT_BARS);
        testElmStyle.setFont(testElmFont);

        XSSFFont testDataFont = testElmRepository.createFont();
        testDataFont.setColor(IndexedColors.LIGHT_ORANGE.getIndex());
        XSSFCellStyle testDataStyle = testElmRepository.createCellStyle();
        testDataStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
        testDataStyle.setFillPattern(FillPatternType.ALT_BARS);
        testDataStyle.setFont(testDataFont);

        XSSFFont testFlowFont = testElmRepository.createFont();
        testFlowFont.setColor(IndexedColors.LIGHT_YELLOW.getIndex());
        XSSFCellStyle testFlowStyle = testElmRepository.createCellStyle();
        testFlowStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
        testFlowStyle.setFillPattern(FillPatternType.ALT_BARS);
        testFlowStyle.setFont(testFlowFont);

        FileInputStream excelFIS = null;
        BufferedInputStream excelBIS;

        //try {
            //excelFIS = new FileInputStream(testSuitePath);
        //} catch (FileNotFoundException ex) {
            //Logger.getLogger(xlsCommonMethods.class.getName()).log(Level.SEVERE, null, ex);
        //}
        
        //excelBIS = new BufferedInputStream(excelFIS);

        //XSSFWorkbook excelImportWorkBook = new XSSFWorkbook();
        XSSFSheet excelSheetObjectRepository;

//        try {
//            excelImportWorkBook = new XSSFWorkbook(excelBIS);
//        } catch (IOException ex) {
//            Logger.getLogger(xlsCommonMethods.class.getName()).log(Level.SEVERE, null, ex);
//        }

        //if (excelImportWorkBook.getNumberOfSheets() >= 2) {
            excelSheetObjectRepository = testElmRepository.getSheetAt(1);

            for (int i =0; i <testRepoTableModel.getRowCount(); i++) {
                //excelRow = excelSheetObjectRepository.getRow(i);
                XSSFRow row = excelSheetOR.createRow(i+1);

                for (int k =0; k < testRepoTableModel.getColumnCount(); k++) {
                    //excelRow = excelSheetObjectRepository.getRow(k);
                    //XSSFRow row = excelSheetOR.createRow(k);
                    //XSSFCell testElement = row.getCell(k);

                    cell = row.createCell(k);
                    cell.setCellType(CellType.STRING);
                    cell.setCellStyle(cellStyle);
                    switch (k) {
                        case 0:
                            cell.setCellStyle(testElmStyle);
                            break;
                        case 1:
                            cell.setCellStyle(testDataStyle);
                            break;
                        case 2:
                            cell.setCellStyle(testFlowStyle);
                            break;
                        default:
                            break;
                    }
                    try {
                        cell.setCellValue(testRepoTableModel.getValueAt(i, k).toString());
                    } catch (NullPointerException exp) {
                        cell.setCellValue("");
                    }
                }
            }
        //}
        excelSheetOR.autoSizeColumn(0);
        excelSheetOR.autoSizeColumn(1);
        excelSheetOR.autoSizeColumn(2);

        excelSheetOR.createFreezePane(0, 1);

        return testElmRepository; 
    }
            
    public static XSSFWorkbook createObjectRepoDataSheet(XSSFWorkbook testElmRepository, DefaultTableModel testRepoTableModel, String testSuitePath){
        XSSFRow excelRow;
        XSSFCell cell;
          
        XSSFSheet excelSheetOR = testElmRepository.createSheet("Object Repository");
        excelRow = excelSheetOR.createRow(0);

        XSSFFont font = testElmRepository.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        XSSFCellStyle cellStyle = testElmRepository.createCellStyle();
        cellStyle.setFont(font);

        cell = excelRow.createCell(0);
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(getHeaderStyle(testElmRepository, IndexedColors.AQUA.getIndex()));
        cell.setCellValue("Test Element");

        cell = excelRow.createCell(1);
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(getHeaderStyle(testElmRepository, IndexedColors.LIGHT_ORANGE.getIndex()));
        cell.setCellValue("id");

        cell = excelRow.createCell(2);
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(getHeaderStyle(testElmRepository, IndexedColors.LIGHT_YELLOW.getIndex()));
        cell.setCellValue("xpath");

        XSSFFont testElmFont = testElmRepository.createFont();
        testElmFont.setColor(IndexedColors.AQUA.getIndex());
        XSSFCellStyle testElmStyle = testElmRepository.createCellStyle();
        testElmStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
        testElmStyle.setFillPattern(FillPatternType.ALT_BARS);
        testElmStyle.setFont(testElmFont);

        XSSFFont testDataFont = testElmRepository.createFont();
        testDataFont.setColor(IndexedColors.LIGHT_ORANGE.getIndex());
        XSSFCellStyle testDataStyle = testElmRepository.createCellStyle();
        testDataStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
        testDataStyle.setFillPattern(FillPatternType.ALT_BARS);
        testDataStyle.setFont(testDataFont);

        XSSFFont testFlowFont = testElmRepository.createFont();
        testFlowFont.setColor(IndexedColors.LIGHT_YELLOW.getIndex());
        XSSFCellStyle testFlowStyle = testElmRepository.createCellStyle();
        testFlowStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
        testFlowStyle.setFillPattern(FillPatternType.ALT_BARS);
        testFlowStyle.setFont(testFlowFont);

        FileInputStream excelFIS = null;
        BufferedInputStream excelBIS;

        try {
            excelFIS = new FileInputStream(testSuitePath);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(xlsCommonMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        excelBIS = new BufferedInputStream(excelFIS);

        XSSFWorkbook excelImportWorkBook = new XSSFWorkbook();
        XSSFSheet excelSheetObjectRepository;

        try {
            excelImportWorkBook = new XSSFWorkbook(excelBIS);
        } catch (IOException ex) {
            Logger.getLogger(xlsCommonMethods.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (excelImportWorkBook.getNumberOfSheets() >= 2) {
            excelSheetObjectRepository = excelImportWorkBook.getSheetAt(1);

            for (int i = 1; i <= excelSheetObjectRepository.getLastRowNum(); i++) {
                excelRow = excelSheetObjectRepository.getRow(i);
                XSSFRow row = excelSheetOR.createRow(i);

                for (int k = 0; k < testRepoTableModel.getColumnCount(); k++) {
                    XSSFCell testElement = excelRow.getCell(k);

                    cell = row.createCell(k);
                    cell.setCellType(CellType.STRING);
                    cell.setCellStyle(cellStyle);
                    switch (k) {
                        case 0:
                            cell.setCellStyle(testElmStyle);
                            break;
                        case 1:
                            cell.setCellStyle(testDataStyle);
                            break;
                        case 2:
                            cell.setCellStyle(testFlowStyle);
                            break;
                        default:
                            break;
                    }
                    try {
                        cell.setCellValue(testElement.toString());
                    } catch (NullPointerException exp) {
                        cell.setCellValue("");
                    }
                }
            }
        }
        excelSheetOR.autoSizeColumn(0);
        excelSheetOR.autoSizeColumn(1);
        excelSheetOR.autoSizeColumn(2);

        excelSheetOR.createFreezePane(0, 1);

        return testElmRepository; 
    }
}