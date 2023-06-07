/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.bolt.renderer;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author zakir
 */
public class tableCellRenderer implements TableCellRenderer{
    private static final TableCellRenderer RENDERER = new DefaultTableCellRenderer();
    public static boolean duplicateTestId =false;
    public static int getSelectedRow;
            
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c= RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);      
        switch (column) {
            case 2:
                c.setForeground(Color.pink);
                c.setBackground(new java.awt.Color(51, 51, 51));
                break;
            case 3:
                c.setForeground(new java.awt.Color(246, 255, 199));
                c.setBackground(new java.awt.Color(51, 51, 51));
                break;
            case 0:
                try{
                    if(!table.getModel().getValueAt(row, 0).toString().isEmpty()){
                        c.setBackground(Color.darkGray);
                        c.setForeground(Color.yellow);
                        c.setFont(new java.awt.Font("Calibri", 1, 14));
                    }else
                        c.setBackground(new java.awt.Color(51, 51, 51));
                }catch(NullPointerException exp){
                }
                break;
            case 1:
                if(table.getModel().getValueAt(row, 1) ==null || table.getModel().getValueAt(row, 1).toString().isEmpty()){
                    table.getModel().setValueAt(Integer.valueOf(table.getModel().getValueAt(row-1, 1).toString())+1, row, 1);
                }
                c.setForeground(Color.white);
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
                        table.getModel().setValueAt(String.valueOf(testStepSeries), i, 1);
                        
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
                    table.getModel().getValueAt(row, 0).toString().isEmpty() &&
                    table.getModel().getValueAt(row, 1).toString().contentEquals("1")){
                    
                    try{
                        String getPrevVal =table.getModel().getValueAt(row-1, 1).toString();
                        int newSeries =Integer.valueOf(getPrevVal);

                        for(int i=table.getSelectedRow(); i<table.getRowCount(); i++){
                            newSeries++;
                            table.getModel().setValueAt(String.valueOf(newSeries), i, 1);
                            if(table.getModel().getValueAt(i+1, 0) !=null && 
                                    !table.getModel().getValueAt(i+1, 0).toString().isEmpty() &&
                                    !table.getModel().getValueAt(i+1, 0).toString().contentEquals("#")){
                                break;
                            }else if (table.getModel().getValueAt(i+1, 1) ==null || table.getModel().getValueAt(i+1, 1).toString().isEmpty()){
                                break;
                            }
                        }
                    }catch(ArrayIndexOutOfBoundsException exp){
                        
                    }   
                }
            }
             c.setBackground(new java.awt.Color(250, 128, 114));
             c.setForeground(Color.black);
        }else
            c.setBackground(new java.awt.Color(51, 51, 51));
        
        return c;
    }
}
