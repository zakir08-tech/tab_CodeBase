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
public class tableAddORCellRenderer implements TableCellRenderer{
    private static final TableCellRenderer RENDERER = new DefaultTableCellRenderer();
    public static boolean duplicateElement =false;
    public static String getDuplicateText;
    public static int getDuplicateCell;
            
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        Component c= RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        switch (column) {
        case 0:
            c.setForeground(new java.awt.Color(246, 255, 199));
            c.setBackground(new java.awt.Color(250, 128, 114));
            break;
        default:
            c.setForeground(Color.white);
            c.setBackground(new java.awt.Color(51, 51, 51));
            break;
        }
        
        if (isSelected) {
            c.setBackground(new java.awt.Color(250, 128, 114));
            c.setForeground(Color.black);
            table.setEditingRow(row);
        }else
            c.setBackground(new java.awt.Color(51, 51, 51));
        
        return c;
    }
}
