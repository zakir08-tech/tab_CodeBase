/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.bolt.renderer;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author zakir
 */
public class TableCellTestStepIDRenderer implements TableCellRenderer{
    private static final TableCellRenderer RENDERER = new DefaultTableCellRenderer();
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        Component c= RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if(!table.getModel().getValueAt(row, 0).toString().isEmpty()){
            table.getModel().setValueAt(1, row, 1);
        }
       
        return c; 
    }
}
