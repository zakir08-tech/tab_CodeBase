/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.bolt.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author zakir
 */
public class tableCellRendererAPIEnvVar implements TableCellRenderer{
    private static final TableCellRenderer RENDERER = new DefaultTableCellRenderer();
    public static boolean duplicateTestId =false;
    public static int getSelectedRow;
            
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c= RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        Font f;
        switch (column) {
            case 0:
                try{
                    c.setForeground(Color.yellow);
                    c.setBackground(new java.awt.Color(51, 51, 51));
                }catch(NullPointerException exp){
                }
                break;
            default:
                c.setForeground(Color.white);
                c.setBackground(new java.awt.Color(51, 51, 51));
                break;
        }
           
        if (isSelected) {
             c.setBackground(new java.awt.Color(52, 73, 94));
             c.setForeground(new java.awt.Color(245, 176, 65 ));
        }else
            c.setBackground(new java.awt.Color(51, 51, 51));
        
        return c;
    }
}
