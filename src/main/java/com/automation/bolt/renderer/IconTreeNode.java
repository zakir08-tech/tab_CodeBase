/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.bolt.renderer;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.Icon;

/**
 *
 * @author zakir
 */
public class IconTreeNode extends DefaultMutableTreeNode {
 
   public IconTreeNode(Icon icon, String text) {
      super(text);
      setUserObject(new TreeNodeData(icon, text));
   }
 
   class TreeNodeData {
 
      Icon icon;
      String text;
 
      public TreeNodeData(Icon icon, String text) {
         this.icon = icon;
         this.text = text;
      }
 
      public Icon getIcon() {
         return icon;
      }
 
      public void setIcon(Icon icon) {
         this.icon = icon;
      }
 
      public String getText() {
         return text;
      }
 
      public void setText(String text) {
         this.text = text;
      }
   }
}
