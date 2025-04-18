/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.bolt.gui;

import static com.automation.bolt.htmlReportCommon.getHtmlTestReportList;
import static com.automation.bolt.htmlReportCommon.getSuiteList;
import static com.automation.bolt.htmlReportCommon.getTestCaseResultList;
import static com.automation.bolt.htmlReportCommon.getTestCaseResultSuiteList;
import static com.automation.bolt.htmlReportCommon.htmlTestReportPath;
import static com.automation.bolt.htmlReportCommon.openTheFileOverDesktop;
import static com.automation.bolt.htmlReportCommon.tesCaseResultPath;
import static com.automation.bolt.htmlReportCommon.testCaseReportFolderEmpty;
import static com.automation.bolt.htmlReportCommon.testCaseReportFolderExist;
import static com.automation.bolt.htmlReportCommon.testReportFolderEmpty;
import static com.automation.bolt.htmlReportCommon.testReportFolderExist;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.Enumeration;
import javax.swing.JTree;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author zakir
 */
public class ApiTestReporting extends javax.swing.JFrame {

    /**
     * Creates new form TestReporting
     */
    public ApiTestReporting() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollTestReporting = new javax.swing.JScrollPane();
        treeTestReporting = new javax.swing.JTree();
        pnlCollapseAll = new javax.swing.JPanel();
        testReportTreeCollaspeAll = new javax.swing.JButton();
        pnlRefresh = new javax.swing.JPanel();
        testReportTreeRefresh = new javax.swing.JButton();
        pnlExpandAll = new javax.swing.JPanel();
        testReportTreeExpandAll = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Test Reporting");
        setBackground(new java.awt.Color(51, 51, 51));
        setMinimumSize(new java.awt.Dimension(784, 551));
        setName("testReportFrame"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        scrollTestReporting.setBackground(new java.awt.Color(51, 51, 51));

        treeTestReporting.setBackground(new java.awt.Color(51, 51, 51));
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Test Reporting");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("HTML Reports");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("-");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Test Case Results");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("-");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeTestReporting.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        treeTestReporting.setShowsRootHandles(true);
        treeTestReporting.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                treeTestReportingMouseClicked(evt);
            }
        });
        treeTestReporting.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                treeTestReportingKeyPressed(evt);
            }
        });
        scrollTestReporting.setViewportView(treeTestReporting);

        pnlCollapseAll.setBackground(new java.awt.Color(0, 0, 0));
        pnlCollapseAll.setRequestFocusEnabled(false);

        testReportTreeCollaspeAll.setBackground(new java.awt.Color(0, 0, 0));
        testReportTreeCollaspeAll.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        testReportTreeCollaspeAll.setForeground(new java.awt.Color(255, 255, 255));
        testReportTreeCollaspeAll.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/treeCollapseAll.png"));
            testReportTreeCollaspeAll.setToolTipText("collapse test report tree");
            testReportTreeCollaspeAll.setActionCommand("");
            testReportTreeCollaspeAll.setBorder(null);
            testReportTreeCollaspeAll.setBorderPainted(false);
            testReportTreeCollaspeAll.setContentAreaFilled(false);
            testReportTreeCollaspeAll.setFocusPainted(false);
            testReportTreeCollaspeAll.setFocusable(false);
            testReportTreeCollaspeAll.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            testReportTreeCollaspeAll.setRequestFocusEnabled(false);
            testReportTreeCollaspeAll.setRolloverEnabled(false);
            testReportTreeCollaspeAll.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    testReportTreeCollaspeAllMouseEntered(evt);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    testReportTreeCollaspeAllMouseExited(evt);
                }
            });
            testReportTreeCollaspeAll.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    testReportTreeCollaspeAllActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout pnlCollapseAllLayout = new javax.swing.GroupLayout(pnlCollapseAll);
            pnlCollapseAll.setLayout(pnlCollapseAllLayout);
            pnlCollapseAllLayout.setHorizontalGroup(
                pnlCollapseAllLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(testReportTreeCollaspeAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            pnlCollapseAllLayout.setVerticalGroup(
                pnlCollapseAllLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCollapseAllLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(testReportTreeCollaspeAll, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
            );

            pnlRefresh.setBackground(new java.awt.Color(0, 0, 0));
            pnlRefresh.setRequestFocusEnabled(false);

            testReportTreeRefresh.setBackground(new java.awt.Color(0, 0, 0));
            testReportTreeRefresh.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
            testReportTreeRefresh.setForeground(new java.awt.Color(255, 255, 255));
            testReportTreeRefresh.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/treeRefresh.png"));
                testReportTreeRefresh.setToolTipText("refresh test report tree");
                testReportTreeRefresh.setActionCommand("");
                testReportTreeRefresh.setBorder(null);
                testReportTreeRefresh.setBorderPainted(false);
                testReportTreeRefresh.setContentAreaFilled(false);
                testReportTreeRefresh.setFocusPainted(false);
                testReportTreeRefresh.setFocusable(false);
                testReportTreeRefresh.setRequestFocusEnabled(false);
                testReportTreeRefresh.setRolloverEnabled(false);
                testReportTreeRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        testReportTreeRefreshMouseEntered(evt);
                    }
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        testReportTreeRefreshMouseExited(evt);
                    }
                });
                testReportTreeRefresh.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        testReportTreeRefreshActionPerformed(evt);
                    }
                });

                javax.swing.GroupLayout pnlRefreshLayout = new javax.swing.GroupLayout(pnlRefresh);
                pnlRefresh.setLayout(pnlRefreshLayout);
                pnlRefreshLayout.setHorizontalGroup(
                    pnlRefreshLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(testReportTreeRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                pnlRefreshLayout.setVerticalGroup(
                    pnlRefreshLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRefreshLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(testReportTreeRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                );

                pnlExpandAll.setBackground(new java.awt.Color(0, 0, 0));
                pnlExpandAll.setRequestFocusEnabled(false);

                testReportTreeExpandAll.setBackground(new java.awt.Color(0, 0, 0));
                testReportTreeExpandAll.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
                testReportTreeExpandAll.setForeground(new java.awt.Color(255, 255, 255));
                testReportTreeExpandAll.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/treeExpandAll.png"));
                    testReportTreeExpandAll.setToolTipText("expand test report tree");
                    testReportTreeExpandAll.setActionCommand("");
                    testReportTreeExpandAll.setBorder(null);
                    testReportTreeExpandAll.setBorderPainted(false);
                    testReportTreeExpandAll.setContentAreaFilled(false);
                    testReportTreeExpandAll.setFocusPainted(false);
                    testReportTreeExpandAll.setFocusable(false);
                    testReportTreeExpandAll.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                    testReportTreeExpandAll.setRequestFocusEnabled(false);
                    testReportTreeExpandAll.setRolloverEnabled(false);
                    testReportTreeExpandAll.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                            testReportTreeExpandAllMouseEntered(evt);
                        }
                        public void mouseExited(java.awt.event.MouseEvent evt) {
                            testReportTreeExpandAllMouseExited(evt);
                        }
                    });
                    testReportTreeExpandAll.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            testReportTreeExpandAllActionPerformed(evt);
                        }
                    });

                    javax.swing.GroupLayout pnlExpandAllLayout = new javax.swing.GroupLayout(pnlExpandAll);
                    pnlExpandAll.setLayout(pnlExpandAllLayout);
                    pnlExpandAllLayout.setHorizontalGroup(
                        pnlExpandAllLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(testReportTreeExpandAll, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    );
                    pnlExpandAllLayout.setVerticalGroup(
                        pnlExpandAllLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlExpandAllLayout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(testReportTreeExpandAll, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    );

                    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                    getContentPane().setLayout(layout);
                    layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(1, 1, 1)
                            .addComponent(scrollTestReporting, javax.swing.GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE)
                            .addGap(1, 1, 1)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(pnlExpandAll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pnlCollapseAll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(pnlRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGap(1, 1, 1))
                    );
                    layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(1, 1, 1)
                            .addComponent(scrollTestReporting, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
                            .addGap(1, 1, 1))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(26, 26, 26)
                            .addComponent(pnlRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(pnlExpandAll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(pnlCollapseAll, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    );

                    getAccessibleContext().setAccessibleParent(this);

                    pack();
                    setLocationRelativeTo(null);
                }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        //Image titleIcon = Toolkit.getDefaultToolkit().getImage(constants.userDir+"\\icons\\bolt.jpg");
        //this.setIconImage(titleIcon);

        //JTreeCellRenderer renderer = new JTreeCellRenderer();
        //treeTestReporting.setCellRenderer(renderer);
       
        LinkedHashMap<Integer, String> testSuties =new LinkedHashMap<Integer, String>();
        LinkedHashMap<Integer, String> testCaseSuties =new LinkedHashMap<Integer, String>();
        //LinkedHashMap<Date, String> testCaseResultDate =new LinkedHashMap<Date, String>();
        
        testSuties =getSuiteList();
        testCaseSuties =getTestCaseResultSuiteList();
        //testCaseResultDate =getTestDateList();
        
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode("Test Reporting");
        DefaultMutableTreeNode htmlRepNode = new DefaultMutableTreeNode("HTML Reports");
        DefaultMutableTreeNode testCaseRepNode = new DefaultMutableTreeNode("Test Case Results");
        
        /*add html test reports to the reporting tree*/
        if(testReportFolderExist ==true && testReportFolderEmpty ==false){
            for (Entry<Integer, String> entry : testSuties.entrySet()) {
                String getSuiteName =entry.getValue();
                DefaultMutableTreeNode htmlSuiteNode = new DefaultMutableTreeNode(getSuiteName);

                htmlRepNode.add(getHtmlTestReportList(getSuiteName, htmlSuiteNode));
            }
        }else{
            htmlRepNode.add(new DefaultMutableTreeNode("No test reports available!"));
        }
        
        treeNode.add(htmlRepNode);
        
        /*add test case reports to the reporting tree*/
        if(testCaseReportFolderExist ==true && testCaseReportFolderEmpty ==false){
            for (Entry<Integer, String> entry : testCaseSuties.entrySet()) {
                String getSuiteName =entry.getValue();
                DefaultMutableTreeNode htmlTestResultNode = new DefaultMutableTreeNode(getSuiteName);
                
                testCaseRepNode.add(getTestCaseResultList(getSuiteName, htmlTestResultNode));
            }
        }else{
            testCaseRepNode.add(new DefaultMutableTreeNode("No test case results available!"));
        }
        
        treeNode.add(testCaseRepNode);
        treeTestReporting.setModel(new DefaultTreeModel (treeNode));
        scrollTestReporting.setViewportView(treeTestReporting);
    }//GEN-LAST:event_formWindowOpened
      
    private void testReportTreeRefreshMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_testReportTreeRefreshMouseEntered
        pnlRefresh.setBackground(new java.awt.Color(250, 128, 114));
        testReportTreeRefresh.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_testReportTreeRefreshMouseEntered

    private void testReportTreeRefreshMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_testReportTreeRefreshMouseExited
        pnlRefresh.setBackground(new java.awt.Color(0, 0, 0));
        testReportTreeRefresh.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_testReportTreeRefreshMouseExited

    private void testReportTreeRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testReportTreeRefreshActionPerformed
        //JTreeCellRenderer renderer = new JTreeCellRenderer();
        //treeTestReporting.setCellRenderer(renderer);
       
        //LinkedHashMap<Integer, DefaultMutableTreeNode> resTestStep = new LinkedHashMap<Integer, DefaultMutableTreeNode>();
        LinkedHashMap<Integer, String> testSuties =new LinkedHashMap<Integer, String>();
        LinkedHashMap<Integer, String> testCaseSuties =new LinkedHashMap<Integer, String>();
        //LinkedHashMap<Date, String> testCaseResultDate =new LinkedHashMap<Date, String>();
        
        testSuties =getSuiteList();
        testCaseSuties =getTestCaseResultSuiteList();
        //testCaseResultDate =getTestDateList();
        
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode("Test Reporting");
        DefaultMutableTreeNode htmlRepNode = new DefaultMutableTreeNode("HTML Reports");
        DefaultMutableTreeNode testCaseRepNode = new DefaultMutableTreeNode("Test Case Results");
        
        /*add html test reports to the reporting tree*/
        if(testReportFolderExist ==true && testReportFolderEmpty ==false){
            for (Entry<Integer, String> entry : testSuties.entrySet()) {
                String getSuiteName =entry.getValue();
                DefaultMutableTreeNode htmlSuiteNode = new DefaultMutableTreeNode(getSuiteName);

                htmlRepNode.add(getHtmlTestReportList(getSuiteName, htmlSuiteNode));
            }
        }else{
            htmlRepNode.add(new DefaultMutableTreeNode("No test reports available!"));
        }  
        treeNode.add(htmlRepNode);
        
        /*add test case reports to the reporting tree*/
        if(testCaseReportFolderExist ==true && testCaseReportFolderEmpty ==false){
            for (Entry<Integer, String> entry : testCaseSuties.entrySet()) {
                String getSuiteName =entry.getValue();
                DefaultMutableTreeNode htmlTestResultNode = new DefaultMutableTreeNode(getSuiteName);
                
                testCaseRepNode.add(getTestCaseResultList(getSuiteName, htmlTestResultNode));
            }
        }else{
            testCaseRepNode.add(new DefaultMutableTreeNode("No test case results available!"));
        }
        
        treeNode.add(testCaseRepNode);
        treeTestReporting.setModel(new DefaultTreeModel (treeNode));
        scrollTestReporting.setViewportView(treeTestReporting);
    }//GEN-LAST:event_testReportTreeRefreshActionPerformed

    private void treeTestReportingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_treeTestReportingMouseClicked
        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
            boolean getFileStatus = false;
            String getFilePath ="";
            try{
                DefaultMutableTreeNode selectedNode =(DefaultMutableTreeNode) treeTestReporting.getSelectionPath().getLastPathComponent();
                if(selectedNode.getChildCount() ==0){
                    
                    if(selectedNode.getUserObject().toString().contains("No test reports available!") || selectedNode.getUserObject().toString().contains("No test case results available!"))
                        return;
                    
                    String getFileName =selectedNode.getUserObject().toString().split(" ")[0].trim();
                    
                    if(getFileName.contains(".html")){
                        getFilePath =htmlTestReportPath +"/"+ getFileName;
                    }else if(getFileName.contains(".docx")){
                        getFilePath =tesCaseResultPath +"/"+ getFileName;
                    }
                    getFileStatus =openTheFileOverDesktop(getFilePath);
                    
                    if(getFileStatus ==false)
                        JOptionPane.showMessageDialog(null, "Test report does not exist now!\nrefresh test reporting tree.", "Alert", JOptionPane.WARNING_MESSAGE);
                }
            }catch(NullPointerException exp){}
        }
    }//GEN-LAST:event_treeTestReportingMouseClicked

    private void treeTestReportingKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_treeTestReportingKeyPressed
        if(evt.getKeyCode() ==10){
            boolean getFileStatus = false;
            String getFilePath ="";
            try{
                DefaultMutableTreeNode selectedNode =(DefaultMutableTreeNode) treeTestReporting.getSelectionPath().getLastPathComponent();
                if(selectedNode.getChildCount() ==0){
                    
                    if(selectedNode.getUserObject().toString().contains("No test reports available!") || selectedNode.getUserObject().toString().contains("No test case results available!"))
                        return;
                    
                    String getFileName =selectedNode.getUserObject().toString().split(" ")[0].trim();
                    
                    if(getFileName.contains(".html")){
                        getFilePath =htmlTestReportPath +"/"+ getFileName;
                    }else if(getFileName.contains(".docx")){
                        getFilePath =tesCaseResultPath +"/"+ getFileName;
                    }
                    getFileStatus =openTheFileOverDesktop(getFilePath);
                    
                    if(getFileStatus ==false)
                        JOptionPane.showMessageDialog(null, "Test report does not exist now!\nrefresh test reporting tree.", "Alert", JOptionPane.WARNING_MESSAGE);
                }
            }catch(NullPointerException exp){}
        }
    }//GEN-LAST:event_treeTestReportingKeyPressed

    private void testReportTreeExpandAllMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_testReportTreeExpandAllMouseEntered
        pnlExpandAll.setBackground(new java.awt.Color(250, 128, 114));
        testReportTreeExpandAll.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_testReportTreeExpandAllMouseEntered

    private void testReportTreeExpandAllMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_testReportTreeExpandAllMouseExited
        pnlExpandAll.setBackground(new java.awt.Color(0, 0, 0));
        testReportTreeExpandAll.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_testReportTreeExpandAllMouseExited

    private void testReportTreeExpandAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testReportTreeExpandAllActionPerformed
        TreeNode root = (TreeNode) treeTestReporting.getModel().getRoot();
        expandAll(treeTestReporting, new TreePath(root));
    }//GEN-LAST:event_testReportTreeExpandAllActionPerformed
    
    public void expandAll(JTree tree, TreePath parent) {
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        if (node.getChildCount() >=0) {
          for (Enumeration e = node.children(); e.hasMoreElements();) {
            TreeNode n = (TreeNode) e.nextElement();
            TreePath path = parent.pathByAddingChild(n);
            expandAll(tree, path);
          }
        }
        tree.expandPath(parent);
    }
    
    private void testReportTreeCollaspeAllMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_testReportTreeCollaspeAllMouseEntered
        pnlCollapseAll.setBackground(new java.awt.Color(250, 128, 114));
        testReportTreeCollaspeAll.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_testReportTreeCollaspeAllMouseEntered

    private void testReportTreeCollaspeAllMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_testReportTreeCollaspeAllMouseExited
        pnlCollapseAll.setBackground(new java.awt.Color(0, 0, 0));
        testReportTreeCollaspeAll.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_testReportTreeCollaspeAllMouseExited

    private void testReportTreeCollaspeAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testReportTreeCollaspeAllActionPerformed
        TreeNode root = (TreeNode) treeTestReporting.getModel().getRoot();
        collapseAll(treeTestReporting, new TreePath(root));
        
        DefaultMutableTreeNode rootNode =(DefaultMutableTreeNode)treeTestReporting.getModel().getRoot();
        TreeNode[] path =rootNode.getPath();
                 
        for(TreeNode tNode: path){
            int getCount =tNode.getChildCount();
            for(int i=0; i<getCount; i++){
                TreePath childPath = new TreePath(tNode); 
                treeTestReporting.expandPath(childPath);
            }
        }
    }//GEN-LAST:event_testReportTreeCollaspeAllActionPerformed
    
    public void collapseAll(JTree tree, TreePath parent) {
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        if (node.getChildCount() >=0) {
          for (Enumeration e = node.children(); e.hasMoreElements();) {
            TreeNode n = (TreeNode) e.nextElement();
            TreePath path = parent.pathByAddingChild(n);
            collapseAll(tree, path);
          }
        }
        tree.collapsePath(parent);
    }
    /*
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ApiTestReporting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ApiTestReporting().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JPanel pnlCollapseAll;
    public static javax.swing.JPanel pnlExpandAll;
    public static javax.swing.JPanel pnlRefresh;
    public static javax.swing.JScrollPane scrollTestReporting;
    public static javax.swing.JButton testReportTreeCollaspeAll;
    public static javax.swing.JButton testReportTreeExpandAll;
    public static javax.swing.JButton testReportTreeRefresh;
    public static javax.swing.JTree treeTestReporting;
    // End of variables declaration//GEN-END:variables
}
