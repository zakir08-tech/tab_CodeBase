/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.bolt.gui;


import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


import com.automation.bolt.common;
import static com.automation.bolt.common.tabOutFromEditingColumn;
import com.automation.bolt.constants;
import static com.automation.bolt.gui.CreateAPITest.tableCreateApiTest;
import static com.automation.bolt.gui.EditAPITest.tableEditTestFlow;
import com.automation.bolt.renderer.tableSSLCertConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author zakir
 */
public class SSLCertificate1 extends javax.swing.JFrame {
    public static DefaultTableModel createSuiteTabModel =new DefaultTableModel();
    public static DefaultTableModel createORTabModel =new DefaultTableModel();
    
    public static TableColumn testComCol =null;
    public static JTextField testComTxt =new JTextField();
    
    /*public static JComboBox<String> cBoxApiRequest =new JComboBox<String>();
    public static JComboBox<String> coBoxPayloadType =new JComboBox<String>();
    public static JComboBox<String> coBoxAuth =new JComboBox<String>();*/
    
    /*public static JTextField elmNameTxt =new JTextField();
    public static JTextField elmIdTxt =new JTextField();
    public static JTextField elmXpathTxt =new JTextField();
    
    public static JTextField testIdTxt =new JTextField();
    public static JTextField testURLTxt =new JTextField();
    public static JTextField testPayloadTxt =new JTextField();
    
    public static TableColumn testFlowColumn =null;
    public static TableColumn testObjectRepoColumn =null;*/
    
    public static boolean duplicateElement =false;
    public static boolean duplicateTestId =false;
    
    public static int editableRow;
    public static int editableAddElmRow;
    
    /*public static TableColumn elmNameCol =null;
    public static TableColumn elmIdCol =null;
    public static TableColumn elmXpathCol =null;
    
    public static TableColumn testIdCol =null;
    public static TableColumn testURLCol =null;
    public static TableColumn testApiTypeCol =null;
    public static TableColumn testPayloadCol =null;
    public static TableColumn testPayloadTypeCol =null;
    public static TableColumn testAuthCol =null;*/
    
    public static boolean getTestFlowCellEditorStatus;
    public static int getTestFlowSelectedRow =0;
    
    public static boolean getElmRepoCellEditorStatus;
    public static int getElmRepoSelectedRow =0;
    
    public static int getFlowCellxPoint;
    public static int getFlowCellyPoint;
    
    public static int getRepoCellxPoint;
    public static int getRepoCellyPoint;
    
    public static int testFlowCurrentRow;
    public static int testElemCurrentRow;
    
    public static boolean fileSaved;
    public static JFileChooser excelFileExport;
    public static int getEditingRow;
    
    public static int getCurrRowBeforeKeyPressed;
    public static String getTheAPIurl;
    public static String getApiPayload;
        
    /**
     * Creates new form CreateTestSuite
     */
    public SSLCertificate1() {
        
        initComponents();
        //setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        //createSuiteTabModel =(DefaultTableModel) tabSSLCertConfig.getModel();
        
        //testIdCol =tableSSLCertConfig.getColumnModel().getColumn(0);
        //testIdCol.setCellEditor(new DefaultCellEditor(testIdTxt));
        
        //testURLCol =tableSSLCertConfig.getColumnModel().getColumn(2);
        //testURLCol.setCellEditor(new DefaultCellEditor(testURLTxt));
        
        /*testPayloadCol =tableAddTestFlow.getColumnModel().getColumn(7);
        testPayloadCol.setCellEditor(new DefaultCellEditor(testPayloadTxt));
        
        testApiTypeCol = tableAddTestFlow.getColumnModel().getColumn(1);
        cBoxApiRequest = new JComboBox<String>();
        apiRequestList(cBoxApiRequest);
        testApiTypeCol.setCellEditor(new DefaultCellEditor(cBoxApiRequest));
        //cBoxApiRequest.setEditable(true);
        
        testPayloadTypeCol = tableAddTestFlow.getColumnModel().getColumn(8);
        coBoxPayloadType = new JComboBox<String>();
        apiPayloadTypeList(coBoxPayloadType);
        testPayloadTypeCol.setCellEditor(new DefaultCellEditor(coBoxPayloadType));
        //coBoxPayloadType.setEditable(true);
        
        testAuthCol = tableAddTestFlow.getColumnModel().getColumn(11);
        coBoxAuth = new JComboBox<String>();
        apiAuthList(coBoxAuth);
        testAuthCol.setCellEditor(new DefaultCellEditor(coBoxAuth));
        //coBoxAuth.setEditable(true);
        
        testIdTxt.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                common.testIdTxtKeyTyped(evt, testIdTxt);
            }
        });
        
        testURLTxt.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                testURLTxtKeyReleased(evt, testURLTxt);
            }
        });
        
        testPayloadTxt.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                testPayloadTxtKeyReleased(evt, testPayloadTxt);
            }
        });*/
         
        /*coBoxAuth.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                //authSelected(evt, coBoxAuth);
            }
        });*/
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SSL Certificate Configuration");
        setBackground(new java.awt.Color(51, 51, 51));
        setMinimumSize(new java.awt.Dimension(1041, 229));
        setPreferredSize(new java.awt.Dimension(1041, 229));
        setResizable(false);
        setSize(new java.awt.Dimension(50, 0));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 311, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 249, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleParent(this);

        setSize(new java.awt.Dimension(325, 286));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        tableSSLCertConfig renderer = new tableSSLCertConfig();
        //tabSSLCertConfig.setDefaultRenderer(Object.class, renderer);
    }//GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Image titleIcon = Toolkit.getDefaultToolkit().getImage(constants.userDir+"\\icons\\bolt.jpg");
        this.setIconImage(titleIcon);
      
        
        HashMap<Integer, Object> jsonMap =common.uploadSSLCertConfiguration();    
        jsonMap.entrySet().stream().map(entry -> entry.getValue().toString().split("[,]")).forEachOrdered(getJsonTxt -> {
            createSuiteTabModel.addRow(getJsonTxt);
        });
        
        try{
            //tabSSLCertConfig.setColumnSelectionInterval(0, 0);
            //tabSSLCertConfig.setRowSelectionInterval(0, 0);
        }catch (IllegalArgumentException exp){}
        
        fileSaved =false;
    }//GEN-LAST:event_formWindowOpened
    
   
        
    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
      
    }//GEN-LAST:event_formWindowClosed
     
   
    
    /**
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
            java.util.logging.Logger.getLogger(SSLCertificate1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
         //</editor-fold>
         //</editor-fold>
         //</editor-fold>
         //</editor-fold>
         //</editor-fold>
         //</editor-fold>
         //</editor-fold>
         //</editor-fold>
         
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new SSLCertificate1().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}