/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.bolt.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author zakir
 */
public class JTreeCellRenderer extends DefaultTreeCellRenderer {
     private static final TreeCellRenderer RENDERER = new DefaultTreeCellRenderer();

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object object, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        Component c =RENDERER.getTreeCellRendererComponent(tree, object, selected, expanded, leaf, row, hasFocus);
        
        c.setForeground(new Color(255,255,255));
        tree.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        c.setFont(new Font("Segoe UI", Font.PLAIN, c.getFont().getSize()));
           
        if(object.toString().contains(".html")){
            c.setForeground(Color.orange);
            c.setFont(new Font("Segoe UI", Font.ITALIC, c.getFont().getSize()));
        }else if(object.toString().contains(".docx")){
            c.setForeground(Color.getHSBColor(3,207,252));
            c.setFont(new Font("Segoe UI", Font.ITALIC, c.getFont().getSize()));
        }
        
        if(object.toString().contains("HTML Reports")){
            c.setForeground(Color.orange);
            c.setFont(new Font("Segoe UI", Font.PLAIN, c.getFont().getSize()));
        }else if(object.toString().contains("Test Case Results")){
            c.setForeground(Color.getHSBColor(3,207,252));
            c.setFont(new Font("Segoe UI", Font.PLAIN, c.getFont().getSize()));
        }else if(object.toString().contains("Test Reporting")){
            c.setForeground(Color.GREEN);
            c.setFont(new Font("Segoe UI", Font.PLAIN, c.getFont().getSize()));
        }
        
        if(selected){
            c.setForeground(Color.pink);
            //  c.setFont(new Font("Segoe UI", Font.PLAIN, c.getFont().getSize()));
        }
        
        return c;
    }
}
