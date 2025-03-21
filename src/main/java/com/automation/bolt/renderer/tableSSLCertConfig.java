/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.bolt.renderer;

import static com.automation.bolt.gui.CreateAPITest.apiSSLCertList;
import static com.automation.bolt.gui.CreateAPITest.cBoxApiSSL;
import static com.automation.bolt.gui.CreateAPITest.testApiSSLCol;
import java.awt.Color;
import java.awt.Component;
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
public class tableSSLCertConfig implements TableCellRenderer{
    private static final TableCellRenderer RENDERER = new DefaultTableCellRenderer();
    public static boolean duplicateElement =false;
    public static String getDuplicateText;
    public static int getDuplicateCell;
            
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        Component c= RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        c.setForeground(Color.white);
        c.setBackground(new java.awt.Color(51, 51, 51));
        
        if (isSelected) {
            c.setBackground(new java.awt.Color(52, 73, 94));
            c.setForeground(new java.awt.Color(245, 176, 65 ));
            //table.setEditingRow(row);
        }else
            c.setBackground(new java.awt.Color(51, 51, 51));
        
        return c;
    }
}
