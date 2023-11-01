/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.bolt.renderer;

import static com.automation.bolt.gui.CreateAPITest.apiSSLCertList;
import static com.automation.bolt.gui.CreateAPITest.cBoxApiSSL;
import static com.automation.bolt.gui.CreateAPITest.testApiSSLCol;
import static com.automation.bolt.gui.EditAPITest.tableEditTestFlow;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import static com.automation.bolt.gui.CreateAPITest.tableCreateApiTest;

/**
 *
 * @author zakir
 */
public class tableCellRendererAPI implements TableCellRenderer{
    private static final TableCellRenderer RENDERER = new DefaultTableCellRenderer();
    public static boolean duplicateTestId =false;
    public static int getSelectedRow;
            
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c= RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        Font f;
        switch (column) {
            case 2:
                c.setForeground(new java.awt.Color(255,255,204));
                c.setBackground(new java.awt.Color(51, 51, 51));
                break;
            case 3:
                c.setForeground(new java.awt.Color(255,153,153));
                c.setBackground(new java.awt.Color(51, 51, 51));
                break;
            case 5:
                c.setForeground(new java.awt.Color(153,255,204));
                c.setBackground(new java.awt.Color(51, 51, 51));
                break;
            case 0:
                try{
                    //if(!table.getModel().getValueAt(row, 0).toString().isEmpty()){
                        //c.setBackground(Color.darkGray);
                        c.setForeground(Color.yellow);
                        //c.setFont(new java.awt.Font("Calibri", 1, 14));
                    //}else
                        c.setBackground(new java.awt.Color(51, 51, 51));
                }catch(NullPointerException exp){
                }
                break;
            case 1:
                String getRequest ="";
                try{
                    getRequest =table.getModel().getValueAt(row, 1).toString().toUpperCase();
                }catch (NullPointerException exp){}
                
                if(getRequest.contentEquals("GET")){
                    c.setForeground(Color.green);
                }else if(getRequest.contentEquals("POST")){
                    c.setForeground(new java.awt.Color(255,153,0));
                }else if(getRequest.contentEquals("PUT")){
                    c.setForeground(new java.awt.Color(153,153,255));
                }else if(getRequest.contentEquals("PATCH")){
                    c.setForeground(new java.awt.Color(255,255,255));
                }else if(getRequest.contentEquals("DELETE")){
                    c.setForeground(new java.awt.Color(255,102,102));
                }
                
                c.setBackground(new java.awt.Color(51, 51, 51));
                break;
            case 7:
                c.setForeground(new java.awt.Color(255,153,0));
                c.setBackground(new java.awt.Color(51, 51, 51));
                break;
            case 11:
                c.setForeground(new java.awt.Color(204,204,255));
                c.setBackground(new java.awt.Color(51, 51, 51));
                break;
            case 14:
                try{
                    testApiSSLCol = tableCreateApiTest.getColumnModel().getColumn(14);
                    cBoxApiSSL = new JComboBox<String>();
                    apiSSLCertList(cBoxApiSSL);
                    testApiSSLCol.setCellEditor(new DefaultCellEditor(cBoxApiSSL));
                    //cBoxApiSSL.setEditable(true);
                }catch(NullPointerException exp){
                    testApiSSLCol = tableEditTestFlow.getColumnModel().getColumn(14);
                    cBoxApiSSL = new JComboBox<String>();
                    apiSSLCertList(cBoxApiSSL);
                    testApiSSLCol.setCellEditor(new DefaultCellEditor(cBoxApiSSL));
                    //cBoxApiSSL.setEditable(true);
                }
         
                c.setForeground(Color.pink);
                c.setBackground(new java.awt.Color(51, 51, 51));
                break;
            case 15:
                c.setForeground(Color.yellow);
                c.setBackground(new java.awt.Color(51, 51, 51));
                break;
            default:
                c.setForeground(Color.white);
                c.setBackground(new java.awt.Color(51, 51, 51));
                break;
        }
           
        if (isSelected) {
            if(column ==0){
                int testStepSeries =0;
                if(table.getModel().getValueAt(row, 0) !=null && 
                    !table.getModel().getValueAt(row, 0).toString().isEmpty() &&
                    !table.getModel().getValueAt(row, 0).toString().contentEquals("#")){
                    
                    for(int i=table.getSelectedRow(); i<table.getRowCount(); i++){
                        testStepSeries++;

                        //table.getModel().setValueAt(String.valueOf(testStepSeries), i, 1);
                        //table.getModel().setValueAt(String.valueOf(testStepSeries), i, 1);
                        
                        try{
                            if(table.getModel().getValueAt(i+1, 0) !=null && 
                                    !table.getModel().getValueAt(i+1, 0).toString().isEmpty() &&
                                    !table.getModel().getValueAt(i+1, 0).toString().contentEquals("#")){
                                break;
                            }else if (table.getModel().getValueAt(i+1, 1) ==null || table.getModel().getValueAt(i+1, 1).toString().isEmpty()){
                                break;
                            }
                        }catch(ArrayIndexOutOfBoundsException exp){
                            
                        }
                    }
                }else if(table.getModel().getValueAt(row, 0) ==null || 
                    table.getModel().getValueAt(row, 0).toString().isEmpty() /*&&
                    table.getModel().getValueAt(row, 1).toString().contentEquals("1")*/){
                    
                    //try{
                        //String getPrevVal =table.getModel().getValueAt(row-1, 1).toString();
                        //int newSeries =Integer.valueOf(getPrevVal);

                        //for(int i=table.getSelectedRow(); i<table.getRowCount(); i++){
                            //newSeries++;
                            //table.getModel().setValueAt(String.valueOf(newSeries), i, 1);
                            //if(table.getModel().getValueAt(i+1, 0) !=null && 
                                    //!table.getModel().getValueAt(i+1, 0).toString().isEmpty() &&
                                    //!table.getModel().getValueAt(i+1, 0).toString().contentEquals("#")){
                                //break;
                            //}else if (table.getModel().getValueAt(i+1, 1) ==null || table.getModel().getValueAt(i+1, 1).toString().isEmpty()){
                                //break;
                            //}
                        //}
                    //}catch(ArrayIndexOutOfBoundsException exp){
                        
                    //}   
                }
            }
             c.setBackground(new java.awt.Color(250, 128, 114));
             c.setForeground(Color.black);
        }else
            c.setBackground(new java.awt.Color(51, 51, 51));
        
        return c;
    }
}
