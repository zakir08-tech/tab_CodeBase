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
public class RunTableColorCellRenderer implements TableCellRenderer{
    private static final TableCellRenderer RENDERER = new DefaultTableCellRenderer();
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
        Component c= RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        switch (column) {
            case 3:
                if(table.getModel().getValueAt(row, 3).toString().contentEquals("PASS"))
                    c.setForeground(Color.green);
                else if(table.getModel().getValueAt(row, 3).toString().contentEquals("FAIL"))
                    c.setForeground(Color.pink);
                else if(table.getModel().getValueAt(row, 3).toString().contentEquals("WARNING"))
                    c.setForeground(Color.orange);
                else if(table.getModel().getValueAt(row, 3).toString().contentEquals("Running..."))
                    c.setForeground(Color.yellow);
                else if(table.getModel().getValueAt(row, 3).toString().contentEquals("Interrupted!"))
                    c.setForeground(Color.pink);
                else if(table.getModel().getValueAt(row, 3).toString().contentEquals("Stopping..."))
                    c.setForeground(Color.pink);
                else if(table.getModel().getValueAt(row, 3).toString().contentEquals("Not Started"))
                    c.setForeground(Color.white);
                break;
            default:
                c.setForeground(Color.white);
                break;
        }
        if(isSelected)
            if(column ==1 || column ==2)
                c.setForeground(Color.yellow);
       return c; 
    }
}
