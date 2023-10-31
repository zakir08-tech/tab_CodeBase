/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.bolt.gui;

import static com.automation.bolt.common.tabOutFromEditingColumn;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


import com.automation.bolt.common;
import com.automation.bolt.constants;
import com.automation.bolt.renderer.tableSSLCertConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author zakir
 */
public class SSLCertificate extends javax.swing.JFrame {
    public static DefaultTableModel createSuiteTabModel =new DefaultTableModel();
    public static DefaultTableModel createORTabModel =new DefaultTableModel();
    
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
    public SSLCertificate() {
        
        initComponents();
        //setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        createSuiteTabModel =(DefaultTableModel) tableSSLCertConfig.getModel();

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

        pnlCreateTestSuite = new javax.swing.JPanel();
        dPanelMenu = new javax.swing.JDesktopPane();
        bttnAddNewTestStep = new javax.swing.JButton();
        bttnDeleteTestStep = new javax.swing.JButton();
        bttnAddStepUp = new javax.swing.JButton();
        bttnAddStepDown = new javax.swing.JButton();
        bttnSaveSuite = new javax.swing.JButton();
        scrollPaneTestFlow = new javax.swing.JScrollPane();
        tableSSLCertConfig = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SSL Certificate Configuration");
        setBackground(new java.awt.Color(51, 51, 51));
        setMinimumSize(new java.awt.Dimension(1041, 229));
        setPreferredSize(new java.awt.Dimension(1041, 229));
        setResizable(false);
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

        pnlCreateTestSuite.setForeground(new java.awt.Color(51, 51, 51));

        bttnAddNewTestStep.setBackground(new java.awt.Color(0, 0, 0));
        bttnAddNewTestStep.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        bttnAddNewTestStep.setForeground(new java.awt.Color(255, 255, 255));
        bttnAddNewTestStep.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/addTestStep_Element.png"));
            bttnAddNewTestStep.setToolTipText("will add a blank test step");
            bttnAddNewTestStep.setActionCommand("OpenRegressionSuite");
            bttnAddNewTestStep.setBorder(null);
            bttnAddNewTestStep.setBorderPainted(false);
            bttnAddNewTestStep.setContentAreaFilled(false);
            bttnAddNewTestStep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            bttnAddNewTestStep.setOpaque(true);
            bttnAddNewTestStep.setRequestFocusEnabled(false);
            bttnAddNewTestStep.setRolloverEnabled(false);
            bttnAddNewTestStep.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    bttnAddNewTestStepMouseEntered(evt);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    bttnAddNewTestStepMouseExited(evt);
                }
            });
            bttnAddNewTestStep.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    bttnAddNewTestStepActionPerformed(evt);
                }
            });

            bttnDeleteTestStep.setBackground(new java.awt.Color(0, 0, 0));
            bttnDeleteTestStep.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
            bttnDeleteTestStep.setForeground(new java.awt.Color(255, 255, 255));
            bttnDeleteTestStep.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/deleteTestStep_Element.png"));
                bttnDeleteTestStep.setToolTipText("will delete the selected test step");
                bttnDeleteTestStep.setActionCommand("OpenRegressionSuite");
                bttnDeleteTestStep.setBorder(null);
                bttnDeleteTestStep.setBorderPainted(false);
                bttnDeleteTestStep.setContentAreaFilled(false);
                bttnDeleteTestStep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                bttnDeleteTestStep.setOpaque(true);
                bttnDeleteTestStep.setRequestFocusEnabled(false);
                bttnDeleteTestStep.setRolloverEnabled(false);
                bttnDeleteTestStep.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        bttnDeleteTestStepMouseEntered(evt);
                    }
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        bttnDeleteTestStepMouseExited(evt);
                    }
                });
                bttnDeleteTestStep.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        bttnDeleteTestStepActionPerformed(evt);
                    }
                });

                bttnAddStepUp.setBackground(new java.awt.Color(0, 0, 0));
                bttnAddStepUp.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
                bttnAddStepUp.setForeground(new java.awt.Color(255, 255, 255));
                bttnAddStepUp.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/addTestStepUp.png"));
                    bttnAddStepUp.setToolTipText("will add a new test step above the selected step");
                    bttnAddStepUp.setActionCommand("AddNewStep");
                    bttnAddStepUp.setBorder(null);
                    bttnAddStepUp.setBorderPainted(false);
                    bttnAddStepUp.setContentAreaFilled(false);
                    bttnAddStepUp.setDefaultCapable(false);
                    bttnAddStepUp.setFocusPainted(false);
                    bttnAddStepUp.setFocusable(false);
                    bttnAddStepUp.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                    bttnAddStepUp.setOpaque(true);
                    bttnAddStepUp.setRequestFocusEnabled(false);
                    bttnAddStepUp.setRolloverEnabled(false);
                    bttnAddStepUp.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                            bttnAddStepUpMouseEntered(evt);
                        }
                        public void mouseExited(java.awt.event.MouseEvent evt) {
                            bttnAddStepUpMouseExited(evt);
                        }
                    });
                    bttnAddStepUp.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            bttnAddStepUpActionPerformed(evt);
                        }
                    });

                    bttnAddStepDown.setBackground(new java.awt.Color(0, 0, 0));
                    bttnAddStepDown.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
                    bttnAddStepDown.setForeground(new java.awt.Color(255, 255, 255));
                    bttnAddStepDown.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/addStepUpDown.png"));
                        bttnAddStepDown.setToolTipText("will add a new test step below the selected step");
                        bttnAddStepDown.setBorder(null);
                        bttnAddStepDown.setBorderPainted(false);
                        bttnAddStepDown.setContentAreaFilled(false);
                        bttnAddStepDown.setFocusPainted(false);
                        bttnAddStepDown.setFocusable(false);
                        bttnAddStepDown.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                        bttnAddStepDown.setOpaque(true);
                        bttnAddStepDown.setRequestFocusEnabled(false);
                        bttnAddStepDown.setRolloverEnabled(false);
                        bttnAddStepDown.addMouseListener(new java.awt.event.MouseAdapter() {
                            public void mouseEntered(java.awt.event.MouseEvent evt) {
                                bttnAddStepDownMouseEntered(evt);
                            }
                            public void mouseExited(java.awt.event.MouseEvent evt) {
                                bttnAddStepDownMouseExited(evt);
                            }
                        });
                        bttnAddStepDown.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                bttnAddStepDownActionPerformed(evt);
                            }
                        });

                        bttnSaveSuite.setBackground(new java.awt.Color(0, 0, 0));
                        bttnSaveSuite.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
                        bttnSaveSuite.setForeground(new java.awt.Color(255, 255, 255));
                        bttnSaveSuite.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/saveTestSuite.png"));
                            bttnSaveSuite.setToolTipText("will save the test suite");
                            bttnSaveSuite.setBorder(null);
                            bttnSaveSuite.setBorderPainted(false);
                            bttnSaveSuite.setContentAreaFilled(false);
                            bttnSaveSuite.setFocusPainted(false);
                            bttnSaveSuite.setFocusable(false);
                            bttnSaveSuite.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                            bttnSaveSuite.setOpaque(true);
                            bttnSaveSuite.setRequestFocusEnabled(false);
                            bttnSaveSuite.setRolloverEnabled(false);
                            bttnSaveSuite.addMouseListener(new java.awt.event.MouseAdapter() {
                                public void mouseEntered(java.awt.event.MouseEvent evt) {
                                    bttnSaveSuiteMouseEntered(evt);
                                }
                                public void mouseExited(java.awt.event.MouseEvent evt) {
                                    bttnSaveSuiteMouseExited(evt);
                                }
                            });
                            bttnSaveSuite.addActionListener(new java.awt.event.ActionListener() {
                                public void actionPerformed(java.awt.event.ActionEvent evt) {
                                    bttnSaveSuiteActionPerformed(evt);
                                }
                            });

                            dPanelMenu.setLayer(bttnAddNewTestStep, javax.swing.JLayeredPane.DEFAULT_LAYER);
                            dPanelMenu.setLayer(bttnDeleteTestStep, javax.swing.JLayeredPane.DEFAULT_LAYER);
                            dPanelMenu.setLayer(bttnAddStepUp, javax.swing.JLayeredPane.DEFAULT_LAYER);
                            dPanelMenu.setLayer(bttnAddStepDown, javax.swing.JLayeredPane.DEFAULT_LAYER);
                            dPanelMenu.setLayer(bttnSaveSuite, javax.swing.JLayeredPane.DEFAULT_LAYER);

                            javax.swing.GroupLayout dPanelMenuLayout = new javax.swing.GroupLayout(dPanelMenu);
                            dPanelMenu.setLayout(dPanelMenuLayout);
                            dPanelMenuLayout.setHorizontalGroup(
                                dPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(dPanelMenuLayout.createSequentialGroup()
                                    .addGap(1, 1, 1)
                                    .addGroup(dPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(bttnSaveSuite, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(bttnAddStepDown, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(bttnAddStepUp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(bttnDeleteTestStep, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(bttnAddNewTestStep, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(1, 1, 1))
                            );
                            dPanelMenuLayout.setVerticalGroup(
                                dPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(dPanelMenuLayout.createSequentialGroup()
                                    .addGap(27, 27, 27)
                                    .addComponent(bttnAddNewTestStep, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(bttnDeleteTestStep, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(bttnAddStepUp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(bttnAddStepDown, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(bttnSaveSuite, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(47, Short.MAX_VALUE))
                            );

                            scrollPaneTestFlow.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

                            tableSSLCertConfig.setBackground(new java.awt.Color(51, 51, 51));
                            tableSSLCertConfig.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
                            tableSSLCertConfig.setForeground(new java.awt.Color(255, 255, 255));
                            tableSSLCertConfig.setModel(new javax.swing.table.DefaultTableModel(
                                new Object [][] {

                                },
                                new String [] {
                                    "SSL Name", "KeyStore", "KeyStore Password", "TrustStore", "TrustStore Password"
                                }
                            ));
                            tableSSLCertConfig.setName("tableSSLCertConfig"); // NOI18N
                            tableSSLCertConfig.setRowHeight(30);
                            tableSSLCertConfig.setRowMargin(2);
                            tableSSLCertConfig.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
                            tableSSLCertConfig.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
                            tableSSLCertConfig.setShowGrid(true);
                            tableSSLCertConfig.getTableHeader().setReorderingAllowed(false);
                            tableSSLCertConfig.setUpdateSelectionOnSort(false);
                            tableSSLCertConfig.setVerifyInputWhenFocusTarget(false);
                            tableSSLCertConfig.addFocusListener(new java.awt.event.FocusAdapter() {
                                public void focusLost(java.awt.event.FocusEvent evt) {
                                    tableSSLCertConfigFocusLost(evt);
                                }
                            });
                            tableSSLCertConfig.addMouseListener(new java.awt.event.MouseAdapter() {
                                public void mousePressed(java.awt.event.MouseEvent evt) {
                                    tableSSLCertConfigMousePressed(evt);
                                }
                                public void mouseReleased(java.awt.event.MouseEvent evt) {
                                    tableSSLCertConfigMouseReleased(evt);
                                }
                            });
                            tableSSLCertConfig.addKeyListener(new java.awt.event.KeyAdapter() {
                                public void keyReleased(java.awt.event.KeyEvent evt) {
                                    tableSSLCertConfigKeyReleased(evt);
                                }
                            });
                            scrollPaneTestFlow.setViewportView(tableSSLCertConfig);

                            javax.swing.GroupLayout pnlCreateTestSuiteLayout = new javax.swing.GroupLayout(pnlCreateTestSuite);
                            pnlCreateTestSuite.setLayout(pnlCreateTestSuiteLayout);
                            pnlCreateTestSuiteLayout.setHorizontalGroup(
                                pnlCreateTestSuiteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCreateTestSuiteLayout.createSequentialGroup()
                                    .addGap(1, 1, 1)
                                    .addComponent(scrollPaneTestFlow, javax.swing.GroupLayout.DEFAULT_SIZE, 1005, Short.MAX_VALUE)
                                    .addGap(1, 1, 1)
                                    .addComponent(dPanelMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(1, 1, 1))
                            );
                            pnlCreateTestSuiteLayout.setVerticalGroup(
                                pnlCreateTestSuiteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(scrollPaneTestFlow, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(dPanelMenu)
                            );

                            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                            getContentPane().setLayout(layout);
                            layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(pnlCreateTestSuite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(1, 1, 1))
                            );
                            layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(pnlCreateTestSuite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(1, 1, 1))
                            );

                            getAccessibleContext().setAccessibleParent(this);

                            setSize(new java.awt.Dimension(1060, 286));
                            setLocationRelativeTo(null);
                        }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        tableSSLCertConfig renderer = new tableSSLCertConfig();
        tableSSLCertConfig.setDefaultRenderer(Object.class, renderer);
    }//GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Image titleIcon = Toolkit.getDefaultToolkit().getImage(constants.userDir+"\\icons\\bolt.jpg");
        this.setIconImage(titleIcon);
        setTableColWidthForCreateRegSuiteTable();
        
        HashMap<Integer, Object> jsonMap =common.uploadSSLCertConfiguration();    
        jsonMap.entrySet().stream().map(entry -> entry.getValue().toString().split("[,]")).forEachOrdered(getJsonTxt -> {
            createSuiteTabModel.addRow(getJsonTxt);
        });
        
        try{
            tableSSLCertConfig.setColumnSelectionInterval(0, 0);
            tableSSLCertConfig.setRowSelectionInterval(0, 0);
        }catch (IllegalArgumentException exp){}
        
        fileSaved =false;
    }//GEN-LAST:event_formWindowOpened

    private void bttnAddNewTestStepMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnAddNewTestStepMouseEntered
        bttnAddNewTestStep.setBackground(new java.awt.Color(250, 128, 114));
        bttnAddNewTestStep.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_bttnAddNewTestStepMouseEntered

    private void bttnAddNewTestStepMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnAddNewTestStepMouseExited
        bttnAddNewTestStep.setBackground(new java.awt.Color(0,0,0));
        bttnAddNewTestStep.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_bttnAddNewTestStepMouseExited

    private void bttnAddNewTestStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnAddNewTestStepActionPerformed
        if(getTestFlowSelectedRow !=-1){
            getTestFlowSelectedRow =tableSSLCertConfig.getSelectedRow();
            tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableSSLCertConfig, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
                
        Object getPreviosTestStepNo =null;
        if(tableSSLCertConfig.getRowCount() ==0)
            getPreviosTestStepNo ="0";
        else
            getPreviosTestStepNo = tableSSLCertConfig.getValueAt(tableSSLCertConfig.getRowCount()-1, 1);
        
        createSuiteTabModel.addRow(new Object[] {null,null,null,null,null,null});
        tableSSLCertConfig.setColumnSelectionInterval(0, 0);
        //tableAddTestFlow.setValueAt(Integer.valueOf(getPreviosTestStepNo.toString())+1, tableAddTestFlow.getRowCount()-1, 1);
        tableSSLCertConfig.setRowSelectionInterval(tableSSLCertConfig.getRowCount()-1, tableSSLCertConfig.getRowCount()-1);
        tableSSLCertConfig.scrollRectToVisible(tableSSLCertConfig.getCellRect(tableSSLCertConfig.getRowCount()-1,0, true));
        tableSSLCertConfig.requestFocus();
        getTestFlowSelectedRow =tableSSLCertConfig.getSelectedRow();
        
        getCurrRowBeforeKeyPressed =tableSSLCertConfig.getSelectedRow();
        //updateAPIAttributeData();
    }//GEN-LAST:event_bttnAddNewTestStepActionPerformed

    private void bttnDeleteTestStepMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnDeleteTestStepMouseEntered
        bttnDeleteTestStep.setBackground(new java.awt.Color(250, 128, 114));
        bttnDeleteTestStep.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_bttnDeleteTestStepMouseEntered

    private void bttnDeleteTestStepMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnDeleteTestStepMouseExited
        bttnDeleteTestStep.setBackground(new java.awt.Color(0,0,0));
        bttnDeleteTestStep.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_bttnDeleteTestStepMouseExited

    private void bttnDeleteTestStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnDeleteTestStepActionPerformed
        if(getTestFlowSelectedRow !=-1){
            getTestFlowSelectedRow =tableSSLCertConfig.getSelectedRow();
            tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableSSLCertConfig, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
        
        String getTestId ="";
            
        if (tableSSLCertConfig.getRowCount() > 0) {
            getTestFlowSelectedRow =tableSSLCertConfig.getSelectedRow();
            int rowIndex =tableSSLCertConfig.getSelectedRow();
            
            try {
                getTestId =tableSSLCertConfig.getValueAt(rowIndex, 0).toString();
            } catch (NullPointerException exp) {
                getTestId ="";
            }
               
            createSuiteTabModel.removeRow(rowIndex);
            
            try {
                tableSSLCertConfig.setRowSelectionInterval(rowIndex-1, rowIndex-1);
                tableSSLCertConfig.setColumnSelectionInterval(0, 0);
                tableSSLCertConfig.requestFocus();
            }catch(IllegalArgumentException exp) {
            	rowIndex =tableSSLCertConfig.getSelectedRow();
            	if(rowIndex ==-1 && tableSSLCertConfig.getRowCount() ==0)
            		{return;}
            	
            	rowIndex =tableSSLCertConfig.getSelectedRow();
            	if(rowIndex ==-1)
            		rowIndex =0;
            	else if(tableSSLCertConfig.getRowCount() ==-1)
            		return;
            	
            	tableSSLCertConfig.setRowSelectionInterval(rowIndex, rowIndex);
                tableSSLCertConfig.setColumnSelectionInterval(0, 0);
                tableSSLCertConfig.requestFocus();
            }
    
        }else {
            JOptionPane.showMessageDialog(null, "No test step(s) available to delete!", "Alert", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bttnDeleteTestStepActionPerformed

    private void bttnAddStepUpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnAddStepUpMouseEntered
        bttnAddStepUp.setBackground(new java.awt.Color(250, 128, 114));
        bttnAddStepUp.setForeground(new java.awt.Color(0,0,0));   
    }//GEN-LAST:event_bttnAddStepUpMouseEntered

    private void bttnAddStepUpMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnAddStepUpMouseExited
        bttnAddStepUp.setBackground(new java.awt.Color(0,0,0));
        bttnAddStepUp.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_bttnAddStepUpMouseExited

    private void bttnAddStepUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnAddStepUpActionPerformed
        if(getTestFlowSelectedRow !=-1){
            getTestFlowSelectedRow =tableSSLCertConfig.getSelectedRow();
            tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableSSLCertConfig, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
        
        if(tableSSLCertConfig.getRowCount()>0){
            getTestFlowSelectedRow =tableSSLCertConfig.getSelectedRow();
            int rowIndex =tableSSLCertConfig.getSelectedRow();
            
            if(rowIndex ==0) {
            	try {
                    tableSSLCertConfig.setColumnSelectionInterval(rowIndex, 0);
                    return;
            	}catch(IllegalArgumentException exp) {return;}
            }
            	
            if(rowIndex !=-1){
                rowIndex =tableSSLCertConfig.getSelectedRow();
                String getTestId = null;
                    
                try{
                    getTestId =tableSSLCertConfig.getValueAt(rowIndex, 0).toString();
                }catch(NullPointerException exp){
                    getTestId ="";
                }
                                    
                createSuiteTabModel.insertRow(rowIndex, new Object[] {null,null,null,null,null,null });
                tableSSLCertConfig.setRowSelectionInterval(rowIndex, rowIndex);
                tableSSLCertConfig.setColumnSelectionInterval(0, 0);
                tableSSLCertConfig.scrollRectToVisible(tableSSLCertConfig.getCellRect(rowIndex, 0, true));
            }else
                  JOptionPane.showMessageDialog(scrollPaneTestFlow,"Select row to add test step!","Alert",JOptionPane.WARNING_MESSAGE);
        }else
            JOptionPane.showMessageDialog(scrollPaneTestFlow,"No test step(s) available to add a new step up!","Alert",JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_bttnAddStepUpActionPerformed

    private void bttnAddStepDownMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnAddStepDownMouseEntered
        bttnAddStepDown.setBackground(new java.awt.Color(250, 128, 114));
        bttnAddStepDown.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_bttnAddStepDownMouseEntered

    private void bttnAddStepDownMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnAddStepDownMouseExited
        bttnAddStepDown.setBackground(new java.awt.Color(0,0,0));
        bttnAddStepDown.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_bttnAddStepDownMouseExited

    private void bttnAddStepDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnAddStepDownActionPerformed
        if(getTestFlowSelectedRow !=-1){
            getTestFlowSelectedRow =tableSSLCertConfig.getSelectedRow();
            tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableSSLCertConfig, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
        
        if(tableSSLCertConfig.getRowCount()>0){
            getTestFlowSelectedRow =tableSSLCertConfig.getSelectedRow();
            int rowIndex = tableSSLCertConfig.getSelectedRow();
            if (rowIndex != -1) {
                try {
                    try {
                        //String getTestID = tableAddTestFlow.getValueAt(tableAddTestFlow.getSelectedRow(), 0).toString();
                        createSuiteTabModel.insertRow(rowIndex + 1, new Object[]{null, null, null, null, null, null});
                        rowIndex = tableSSLCertConfig.getSelectedRow();
                        tableSSLCertConfig.setRowSelectionInterval(rowIndex + 1, rowIndex + 1);
                        tableSSLCertConfig.setColumnSelectionInterval(0, 0);
                        tableSSLCertConfig.scrollRectToVisible(tableSSLCertConfig.getCellRect(rowIndex+1, 0, true));
                    } catch (NullPointerException exp) {
                        createSuiteTabModel.insertRow(rowIndex + 1, new Object[]{null, null, null, null, null, null});
                        rowIndex = tableSSLCertConfig.getSelectedRow();
                        tableSSLCertConfig.setRowSelectionInterval(rowIndex + 1, rowIndex + 1);
                        tableSSLCertConfig.setColumnSelectionInterval(0, 0);
                        tableSSLCertConfig.scrollRectToVisible(tableSSLCertConfig.getCellRect(rowIndex+1, 0, true));
                    }
                } catch (NullPointerException exp) {
                    createSuiteTabModel.insertRow(rowIndex + 1, new Object[]{null, null, null, null, null, null});
                    rowIndex = tableSSLCertConfig.getSelectedRow();
                    tableSSLCertConfig.setRowSelectionInterval(rowIndex + 1, rowIndex + 1);
                    tableSSLCertConfig.setColumnSelectionInterval(0, 0);
                    tableSSLCertConfig.scrollRectToVisible(tableSSLCertConfig.getCellRect(rowIndex+1, 0, true));
                }
            } else {
                JOptionPane.showMessageDialog(tableSSLCertConfig, "Select row to add test step!", "Alert", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }else
            JOptionPane.showMessageDialog(scrollPaneTestFlow,"No test step(s) available to add a new step down!","Alert",JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_bttnAddStepDownActionPerformed

    private void bttnSaveSuiteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnSaveSuiteMouseEntered
        bttnSaveSuite.setBackground(new java.awt.Color(250, 128, 114));
        bttnSaveSuite.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_bttnSaveSuiteMouseEntered

    private void bttnSaveSuiteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnSaveSuiteMouseExited
        bttnSaveSuite.setBackground(new java.awt.Color(0,0,0));
        bttnSaveSuite.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_bttnSaveSuiteMouseExited

    private void bttnSaveSuiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnSaveSuiteActionPerformed
        if(getTestFlowSelectedRow !=-1){
            getTestFlowSelectedRow =tableSSLCertConfig.getSelectedRow();
            tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableSSLCertConfig, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
        
        saveSSLCertConfigFile();
        JOptionPane.showMessageDialog(null, "saved successfully!", "SSL Certificate Configuration", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_bttnSaveSuiteActionPerformed
    
    public static void saveSSLCertConfigFile(){
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        
        if(tableSSLCertConfig.getRowCount() > 0){
            for(int i =0; i<tableSSLCertConfig.getRowCount(); i++){
                
                Object getName =tableSSLCertConfig.getValueAt(i, 0);
                if(getName ==null)
                    getName ="";

                Object getKeyStore =tableSSLCertConfig.getValueAt(i, 1);
                if(getKeyStore ==null)
                    getKeyStore ="";

                Object getKeyStorePwd =tableSSLCertConfig.getValueAt(i, 2);
                if(getKeyStorePwd ==null)
                    getKeyStorePwd ="";

                Object getTrustStore =tableSSLCertConfig.getValueAt(i, 3);
                if(getTrustStore ==null)
                    getTrustStore ="";

                Object getTrustStorePwd =tableSSLCertConfig.getValueAt(i, 4);
                if(getTrustStorePwd ==null)
                    getTrustStorePwd ="";
                
                jsonObject.put("name", getName);
                jsonObject.put("keystore", getKeyStore);
                jsonObject.put("keystore-pwd", getKeyStorePwd);
                jsonObject.put("truststore", getTrustStore);
                jsonObject.put("truststore-pwd", getTrustStorePwd);
                
                array.add(jsonObject);
                jsonObject = new JSONObject();
            }
            
            try {
	    	 
	    	  Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    	  JsonElement jsonElement = JsonParser.parseString(array.toJSONString());
	    	  Object prettyJson = gson.toJson(jsonElement);
	    	  
	    	  File directory = new File(String.valueOf("./ssl"));

	    	  if (!directory.exists()) {
	    		  directory.mkdir();    
	    	  }
	    	  
	          FileWriter file = new FileWriter("./ssl/sslCert.json");
	          file.write(prettyJson.toString());
	          file.close();
                  	          	    	  
            } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
        }else{
            File directory = new File(String.valueOf("./ssl"));
            if (!directory.exists()) {
                    directory.mkdir();    
            }

            FileWriter file;
            try {
                file = new FileWriter("./ssl/sslCert.json");
                file.write(array.toJSONString());
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(SSLCertificate.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    private void tableSSLCertConfigMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSSLCertConfigMousePressed
        int getCurRow = tableSSLCertConfig.convertRowIndexToModel(tableSSLCertConfig.rowAtPoint(evt.getPoint()));
        int gerCurrCol = tableSSLCertConfig.convertColumnIndexToModel(tableSSLCertConfig.columnAtPoint(evt.getPoint()));
        
        getTestFlowSelectedRow =getCurRow;
        getFlowCellxPoint =tableSSLCertConfig.rowAtPoint(evt.getPoint());
        getFlowCellyPoint =tableSSLCertConfig.columnAtPoint(evt.getPoint());
        
        /*if(duplicateTestId ==false){
            switch (gerCurrCol) {
                case 0:
                    tableSSLCertConfig.editCellAt(getCurRow, 0);
                    editableRow =tableSSLCertConfig.getEditingRow();
                    testIdTxt.requestFocusInWindow();
                    break;
                case 2:
                    tableSSLCertConfig.editCellAt(getCurRow, 2);
                    testURLTxt.requestFocusInWindow();
                    break;
                case 3:
                    tableSSLCertConfig.editCellAt(getCurRow, 3);
                    //coBoxObjectRepo.requestFocusInWindow();
                    break;    
                case 4:
                    tableSSLCertConfig.editCellAt(getCurRow, 4);
                    testURLTxt.requestFocusInWindow();
                    break;
                case 7:
                    tableSSLCertConfig.editCellAt(getCurRow, 7);
                    testPayloadTxt.requestFocusInWindow();
                    break;
                default:
                    break;
            }
        }*/
    }//GEN-LAST:event_tableSSLCertConfigMousePressed

    private void tableSSLCertConfigKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableSSLCertConfigKeyReleased
        //updateAPIAttributeData();
        //common.checkForDuplicateTestId(createSuiteTabModel, tableAddTestFlow, editableRow, testIdTxt);
    }//GEN-LAST:event_tableSSLCertConfigKeyReleased
    
    private void tableSSLCertConfigMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSSLCertConfigMouseReleased
      //updateAPIAttributeData();
    }//GEN-LAST:event_tableSSLCertConfigMouseReleased

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
       saveSSLCertConfigFile();
    }//GEN-LAST:event_formWindowClosed

    private void tableSSLCertConfigFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableSSLCertConfigFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tableSSLCertConfigFocusLost
     
    public static void setTableColWidthForCreateRegSuiteTable(){
        //tableAddTestFlow.getColumnModel().getColumn(0).setMaxWidth(50);
        tableSSLCertConfig.getColumnModel().getColumn(0).setMinWidth(100);
        
        //tableAddTestFlow.getColumnModel().getColumn(1).setMaxWidth(72);
        tableSSLCertConfig.getColumnModel().getColumn(1).setMinWidth(255);
        
        //tableAddTestFlow.getColumnModel().getColumn(2).setMaxWidth(350);
          tableSSLCertConfig.getColumnModel().getColumn(2).setMinWidth(125);
        
        //tableAddTestFlow.getColumnModel().getColumn(3).setMaxWidth(120);
        tableSSLCertConfig.getColumnModel().getColumn(3).setMinWidth(255);
        
        //tableAddTestFlow.getColumnModel().getColumn(4).setMaxWidth(120);
        tableSSLCertConfig.getColumnModel().getColumn(4).setMinWidth(125);
    }
    
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
            java.util.logging.Logger.getLogger(SSLCertificate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
         //</editor-fold>
         //</editor-fold>
         //</editor-fold>
         //</editor-fold>
         
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new SSLCertificate().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton bttnAddNewTestStep;
    public static javax.swing.JButton bttnAddStepDown;
    public static javax.swing.JButton bttnAddStepUp;
    public javax.swing.JButton bttnDeleteTestStep;
    public static javax.swing.JButton bttnSaveSuite;
    public javax.swing.JDesktopPane dPanelMenu;
    public javax.swing.JPanel pnlCreateTestSuite;
    public static javax.swing.JScrollPane scrollPaneTestFlow;
    public static javax.swing.JTable tableSSLCertConfig;
    // End of variables declaration//GEN-END:variables
}