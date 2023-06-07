/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.bolt.renderer;

import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author zakir
 */
public class IconTreeNodeRenderer extends DefaultTreeCellRenderer {
   public Component getTreeCellRendererComponent(JTree tree,
                                                 Object value,
                                                 boolean selected,
                                                 boolean expanded,
                                                 boolean leaf,
                                                 int row,
                                                 boolean hasFocus) {
      IconTreeNode.TreeNodeData data = (IconTreeNode.TreeNodeData) ((IconTreeNode) value).getUserObject();
      setIcon(data.getIcon());
      setText(data.getText());
      return this;
   }
}
