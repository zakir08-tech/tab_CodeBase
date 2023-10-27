/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.bolt.gui;

import static com.automation.bolt.common.tabOutFromEditingColumn;
import static com.automation.bolt.gui.EditRegressionSuite.RegressionSuiteScrollPane;
import static com.automation.bolt.xlsCommonMethods.createAPITestFlowDataSheet;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.automation.bolt.common;
import com.automation.bolt.constants;
import com.automation.bolt.renderer.tableCellRendererAPI;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
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
    
    public static JComboBox<String> cBoxApiRequest =new JComboBox<String>();
    public static JComboBox<String> coBoxPayloadType =new JComboBox<String>();
    public static JComboBox<String> coBoxAuth =new JComboBox<String>();
    
    public static JTextField elmNameTxt =new JTextField();
    public static JTextField elmIdTxt =new JTextField();
    public static JTextField elmXpathTxt =new JTextField();
    
    public static JTextField testIdTxt =new JTextField();
    public static JTextField testURLTxt =new JTextField();
    public static JTextField testPayloadTxt =new JTextField();
    
    public static TableColumn testFlowColumn =null;
    public static TableColumn testObjectRepoColumn =null;
    
    public static boolean duplicateElement =false;
    public static boolean duplicateTestId =false;
    
    public static int editableRow;
    public static int editableAddElmRow;
    
    public static TableColumn elmNameCol =null;
    public static TableColumn elmIdCol =null;
    public static TableColumn elmXpathCol =null;
    
    public static TableColumn testIdCol =null;
    public static TableColumn testURLCol =null;
    public static TableColumn testApiTypeCol =null;
    public static TableColumn testPayloadCol =null;
    public static TableColumn testPayloadTypeCol =null;
    public static TableColumn testAuthCol =null;
    
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

        testIdCol =tableSSLCertConfig.getColumnModel().getColumn(0);
        testIdCol.setCellEditor(new DefaultCellEditor(testIdTxt));
        
        testURLCol =tableSSLCertConfig.getColumnModel().getColumn(2);
        testURLCol.setCellEditor(new DefaultCellEditor(testURLTxt));
        
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
         
        coBoxAuth.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                //authSelected(evt, coBoxAuth);
            }
        });
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
        scrollPaneTestFlow = new javax.swing.JScrollPane();
        tableSSLCertConfig = new javax.swing.JTable();
        dPanelMenu = new javax.swing.JDesktopPane();
        pnlCreateSuiteMenu = new javax.swing.JPanel();
        bttnAddNewTestStep = new javax.swing.JButton();
        bttnDeleteTestStep = new javax.swing.JButton();
        bttnAddStepUp = new javax.swing.JButton();
        bttnAddStepDown = new javax.swing.JButton();
        bttnSaveSuite = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SSL Certificate Configuration");
        setMinimumSize(new java.awt.Dimension(955, 492));
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

        tableSSLCertConfig.setBackground(new java.awt.Color(51, 51, 51));
        tableSSLCertConfig.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        tableSSLCertConfig.setForeground(new java.awt.Color(255, 255, 255));
        tableSSLCertConfig.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Certificate Name (user define)", "KeyStore", "KeyStore Password", "TrustStore", "TrustStore Password"
            }
        ));
        tableSSLCertConfig.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tableSSLCertConfig.setName("tableSSLCertConfig"); // NOI18N
        tableSSLCertConfig.setRowHeight(30);
        tableSSLCertConfig.setRowMargin(2);
        tableSSLCertConfig.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableSSLCertConfig.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableSSLCertConfig.setShowGrid(true);
        tableSSLCertConfig.getTableHeader().setReorderingAllowed(false);
        tableSSLCertConfig.setUpdateSelectionOnSort(false);
        tableSSLCertConfig.setVerifyInputWhenFocusTarget(false);
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
            .addComponent(scrollPaneTestFlow, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 995, Short.MAX_VALUE)
        );
        pnlCreateTestSuiteLayout.setVerticalGroup(
            pnlCreateTestSuiteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCreateTestSuiteLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(scrollPaneTestFlow, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pnlCreateSuiteMenu.setBackground(new java.awt.Color(0, 153, 153));
        pnlCreateSuiteMenu.setOpaque(false);

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

                            javax.swing.GroupLayout pnlCreateSuiteMenuLayout = new javax.swing.GroupLayout(pnlCreateSuiteMenu);
                            pnlCreateSuiteMenu.setLayout(pnlCreateSuiteMenuLayout);
                            pnlCreateSuiteMenuLayout.setHorizontalGroup(
                                pnlCreateSuiteMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCreateSuiteMenuLayout.createSequentialGroup()
                                    .addGap(0, 0, Short.MAX_VALUE)
                                    .addGroup(pnlCreateSuiteMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(bttnSaveSuite, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                                        .addComponent(bttnAddStepDown, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(bttnAddStepUp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(bttnDeleteTestStep, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(bttnAddNewTestStep, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            );
                            pnlCreateSuiteMenuLayout.setVerticalGroup(
                                pnlCreateSuiteMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCreateSuiteMenuLayout.createSequentialGroup()
                                    .addGap(19, 19, 19)
                                    .addComponent(bttnAddNewTestStep, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(bttnDeleteTestStep, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(bttnAddStepUp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(bttnAddStepDown, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(bttnSaveSuite, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            );

                            dPanelMenu.setLayer(pnlCreateSuiteMenu, javax.swing.JLayeredPane.DEFAULT_LAYER);

                            javax.swing.GroupLayout dPanelMenuLayout = new javax.swing.GroupLayout(dPanelMenu);
                            dPanelMenu.setLayout(dPanelMenuLayout);
                            dPanelMenuLayout.setHorizontalGroup(
                                dPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(dPanelMenuLayout.createSequentialGroup()
                                    .addGap(4, 4, 4)
                                    .addComponent(pnlCreateSuiteMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            );
                            dPanelMenuLayout.setVerticalGroup(
                                dPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(dPanelMenuLayout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(pnlCreateSuiteMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addContainerGap())
                            );

                            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                            getContentPane().setLayout(layout);
                            layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(pnlCreateTestSuite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(0, 0, 0)
                                    .addComponent(dPanelMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(1, 1, 1))
                            );
                            layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(dPanelMenu)
                                        .addComponent(pnlCreateTestSuite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(1, 1, 1))
                            );

                            getAccessibleContext().setAccessibleParent(this);

                            setSize(new java.awt.Dimension(1055, 534));
                            setLocationRelativeTo(null);
                        }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
       tableCellRendererAPI renderer = new tableCellRendererAPI();
       //tableAddORCellRenderer addORrenderer = new tableAddORCellRenderer();
       
        tableSSLCertConfig.setDefaultRenderer(Object.class, renderer);
       //tableAddOR.setDefaultRenderer(Object.class, addORrenderer);
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
    
     public static void testURLTxtKeyReleased(KeyEvent evt, JTextField textField) {
        //String getURLText =textField.getText();
        //txtAPIurl.setText(getURLText);
    }
     
    public static void testPayloadTxtKeyReleased(KeyEvent evt, JTextField textField) {
        //String getPayloadText =textField.getText();
        //txtAreaPayload.setText(common.writeJsonPayloadToTheTextArea(getPayloadText));
    }
    
    /*public static void authSelected(java.awt.event.FocusEvent evt, JComboBox<String> authField) {
    	getCurrRowBeforeKeyPressed =tableAddTestFlow.getSelectedRow();
    	try{
            String getTestId =(String) tableAddTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 0);
            
            if(getTestId !=null && !getTestId.isEmpty()){
                String getAuth =(String) tableAddTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 11);
                if(getAuth.contentEquals("Basic Auth")){
                    String getUsername =(String) tableAddTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 12);
                    if(getUsername ==null)
                    	getUsername ="";
                    String getPassword =(String) tableAddTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 13);
                    if(getPassword ==null)
                    	getPassword ="";
                    
                    txtAreaAuthorization.setText("Username: "+getUsername +"\n"+ "Password: "+getPassword);
                    lblAuthorization.setText("Authorization: Basic Auth");
                }else if(getAuth.contentEquals("Bearer Token")){
                    String getToken =(String) tableAddTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 12);
                    if(getToken ==null)
                    	getToken ="";
                    tableAddTestFlow.setValueAt("",getCurrRowBeforeKeyPressed, 13);
                    txtAreaAuthorization.setText("Token: "+getToken);
                    lblAuthorization.setText("Authorization: Bearer Token");
                }else {
                    tableAddTestFlow.setValueAt("", getCurrRowBeforeKeyPressed, 12);
                    tableAddTestFlow.setValueAt("", getCurrRowBeforeKeyPressed, 13);
                    lblAuthorization.setText("Authorization");
                    txtAreaAuthorization.setText("");
                }
                	
            }else {
                lblAuthorization.setText("Authorization");
            	txtAreaAuthorization.setText("");
            }
        }catch(NullPointerException exp){}
    }*/
     
    private void bttnAddNewTestStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnAddNewTestStepActionPerformed
        if(getElmRepoSelectedRow !=-1){
            //getElmRepoSelectedRow =tableAddOR.getSelectedRow();
            //tabOutFromEditingColumn(getElmRepoCellEditorStatus, tableAddOR, getRepoCellxPoint, getRepoCellyPoint, getElmRepoSelectedRow);
        }
            
        if(getTestFlowSelectedRow !=-1){
            getTestFlowSelectedRow =tableSSLCertConfig.getSelectedRow();
            tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableSSLCertConfig, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
            
        if(common.checkForDuplicateTestId(createSuiteTabModel, tableSSLCertConfig, editableRow, testIdTxt) ==true)
            return;
        
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
            //getElmRepoSelectedRow =tableAddOR.getSelectedRow();
            //tabOutFromEditingColumn(getElmRepoCellEditorStatus, tableAddOR, getRepoCellxPoint, getRepoCellyPoint, getElmRepoSelectedRow);
        }
            
        if(getTestFlowSelectedRow !=-1){
            getTestFlowSelectedRow =tableSSLCertConfig.getSelectedRow();
            tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableSSLCertConfig, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
        
        if(common.checkForDuplicateTestId(createSuiteTabModel, tableSSLCertConfig, editableRow, testIdTxt) ==true)
            return;
        
        String getTestId =null;
            
        if (tableSSLCertConfig.getRowCount() > 0) {
            getTestFlowSelectedRow =tableSSLCertConfig.getSelectedRow();
            int rowIndex =tableSSLCertConfig.getSelectedRow();
            
            try {
                getTestId =tableSSLCertConfig.getValueAt(rowIndex, 0).toString();
            } catch (NullPointerException exp) {
                getTestId ="";
            }
            
            if(!getTestId.isEmpty())
            {
            	JOptionPane.showMessageDialog(null, "Can not delete a step with Test Id!", "Alert", JOptionPane.WARNING_MESSAGE);
            	return;
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
            //getElmRepoSelectedRow =tableAddOR.getSelectedRow();
            //tabOutFromEditingColumn(getElmRepoCellEditorStatus, tableAddOR, getRepoCellxPoint, getRepoCellyPoint, getElmRepoSelectedRow);
        }
            
        if(getTestFlowSelectedRow !=-1){
            getTestFlowSelectedRow =tableSSLCertConfig.getSelectedRow();
            tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableSSLCertConfig, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
        
        if(common.checkForDuplicateTestId(createSuiteTabModel, tableSSLCertConfig, editableRow, testIdTxt) ==true)
            return;
        
        int getTestStep =0;
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
            //getElmRepoSelectedRow =tableAddOR.getSelectedRow();
            //tabOutFromEditingColumn(getElmRepoCellEditorStatus, tableAddOR, getRepoCellxPoint, getRepoCellyPoint, getElmRepoSelectedRow);
        }
            
        if(getTestFlowSelectedRow !=-1){
            //getTestFlowSelectedRow =tableAddTestFlow.getSelectedRow();
            //tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableAddTestFlow, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
        
        if(common.checkForDuplicateTestId(createSuiteTabModel, tableSSLCertConfig, editableRow, testIdTxt) ==true)
            return;
        
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
        
        //getElmRepoSelectedRow =tableAddOR.getSelectedRow();
        //tabOutFromEditingColumn(getElmRepoCellEditorStatus, tableAddOR, getRepoCellxPoint, getRepoCellyPoint, getElmRepoSelectedRow);
       
        //writeJsonPayloadToTheTextArea();
        
        if(common.checkForDuplicateTestId(createSuiteTabModel, tableSSLCertConfig, editableRow, testIdTxt) ==true)
            return;
         
        int getCurRow = tableSSLCertConfig.convertRowIndexToModel(tableSSLCertConfig.rowAtPoint(evt.getPoint()));
        int gerCurrCol = tableSSLCertConfig.convertColumnIndexToModel(tableSSLCertConfig.columnAtPoint(evt.getPoint()));
        
        getTestFlowSelectedRow =getCurRow;
        getFlowCellxPoint =tableSSLCertConfig.rowAtPoint(evt.getPoint());
        getFlowCellyPoint =tableSSLCertConfig.columnAtPoint(evt.getPoint());
        
        if(duplicateTestId ==false){
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
        }
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
    
    /*public static void updateAPIAttributeData(){
        getCurrRowBeforeKeyPressed =tableAddTestFlow.getSelectedRow();
        
        // update api test url
        try{    
            getTheAPIurl =tableAddTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 2).toString();
            txtAPIurl.setText(getTheAPIurl);
            txtAPIurl.setCaretPosition(0);
        }catch(NullPointerException | ArrayIndexOutOfBoundsException exp){
            txtAPIurl.setText("");
            txtAPIurl.setCaretPosition(0);
        }
        
        // update api payload body
        try{
            getApiPayload =tableAddTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 7).toString();
            if(getApiPayload !=null){
                txtAreaPayload.setText(common.writeJsonPayloadToTheTextArea(getApiPayload));
                txtAreaPayload.setCaretPosition(0);
            }
        }catch(NullPointerException | ArrayIndexOutOfBoundsException exp){
            txtAreaPayload.setText("");
            txtAreaPayload.setCaretPosition(0);
        }
        
        // update api header list
        try{
            String getTestId =(String) tableAddTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 0);
            String setHeaders ="";
            
            if(getTestId !=null && !getTestId.isEmpty())
            {
            	int getRowCnt =tableAddTestFlow.getRowCount();
            	int rowStart =getCurrRowBeforeKeyPressed;
            	
            	for(int rowStart1=rowStart; rowStart1<=getRowCnt;rowStart1++) {
            		String getHeaderName =(String) tableAddTestFlow.getValueAt(rowStart1, 3);
                    if(getHeaderName ==null)
                    	getHeaderName ="";
                    
                    String getHeaderValue = (String) tableAddTestFlow.getValueAt(rowStart1, 4);
                    if(getHeaderValue ==null)
                    	getHeaderValue ="";
                    
                    if(!getHeaderName.isEmpty() || !getHeaderValue.isEmpty()) {
                    	setHeaders = setHeaders + getHeaderName +": "+ getHeaderValue+"\n";
                    }
                    
                    try {
                    	String getTestId1 =(String) tableAddTestFlow.getValueAt(rowStart1+1, 0);
                        if(getTestId1 !=null && !getTestId1.isEmpty()) {
                        	txtAreaHeaders.setText(setHeaders);
                        	break;
                        }
                    }catch(ArrayIndexOutOfBoundsException exp) {txtAreaHeaders.setText(setHeaders);break;}
            	}
            }else
            	txtAreaHeaders.setText("");
            
        }catch(NullPointerException | ArrayIndexOutOfBoundsException exp){}
        
        // update api param list
        try{
            String getTestId =(String) tableAddTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 0);
            String setParams ="";
            
            if(getTestId !=null && !getTestId.isEmpty())
            {
            	int getRowCnt =tableAddTestFlow.getRowCount();
            	int rowStart =getCurrRowBeforeKeyPressed;
            	
            	for(int rowStart1=rowStart; rowStart1<=getRowCnt;rowStart1++) {
            		String getParamName =(String) tableAddTestFlow.getValueAt(rowStart1, 5);
                    if(getParamName ==null)
                    	getParamName ="";
                    
                    String getParamValue = (String) tableAddTestFlow.getValueAt(rowStart1, 6);
                    if(getParamValue ==null)
                    	getParamValue ="";
                    
                    if(!getParamName.isEmpty() || !getParamValue.isEmpty()) {
                    	setParams = setParams + getParamName +": "+ getParamValue+"\n";
                    }
                    
                    try {
                    	String getTestId1 =(String) tableAddTestFlow.getValueAt(rowStart1+1, 0);
                        if(getTestId1 !=null && !getTestId1.isEmpty()) {
                        	txtAreaParams.setText(setParams);
                        	break;
                        }
                    }catch(ArrayIndexOutOfBoundsException exp) {txtAreaParams.setText(setParams);break;}
            	}
            }else
            	txtAreaParams.setText("");
            
        }catch(NullPointerException | ArrayIndexOutOfBoundsException exp){}
        
        // update authentication
        try{
            String getTestId =(String) tableAddTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 0);
            if(getTestId !=null && !getTestId.isEmpty())
            {
                String getAuth =(String) tableAddTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 11);
                if(getAuth.contentEquals("Basic Auth")){
                    String getUsername =(String) tableAddTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 12);
                    if(getUsername ==null)
                    	getUsername ="";
                    String getPassword =(String) tableAddTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 13);
                    if(getPassword ==null)
                    	getPassword ="";
                    
                    txtAreaAuthorization.setText("Username: "+getUsername +"\n"+ "Password: "+getPassword);
                    lblAuthorization.setText("Authorization: Basic Auth");
                }else if(getAuth.contentEquals("Bearer Token")){
                    String getToken =(String) tableAddTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 12);
                    if(getToken ==null)
                    	getToken ="";
                    
                    txtAreaAuthorization.setText("Token: "+getToken);
                    lblAuthorization.setText("Authorization: Bearer Token");
                }else {
                	tableAddTestFlow.setValueAt("", getCurrRowBeforeKeyPressed, 12);
                	tableAddTestFlow.setValueAt("", getCurrRowBeforeKeyPressed, 13);
                	lblAuthorization.setText("Authorization");
                	txtAreaAuthorization.setText("");
                }
            }else {
            	lblAuthorization.setText("Authorization"); 
                txtAreaAuthorization.setText("");
            }
        }catch(NullPointerException | ArrayIndexOutOfBoundsException exp){}
    }*/
    
    /*public static boolean checkElementExistInTheList(String listItem){
        boolean itemExist =false;
        String getText =null;
        
        for(int x=0; x<coBoxObjectRepo.getItemCount(); x++){
            getText =coBoxObjectRepo.getItemAt(x).trim().toLowerCase();
            if(getText.contentEquals(listItem.toLowerCase().trim())){
                itemExist =true;
                break;
            }
        }
        return itemExist;
    }*/
    
    /*public static void apiRequestList(JComboBox<String> cBoxTestFlow) {
        String keywordlist = "," +"GET,"
                + "POST,"
                + "PUT,"
                + "PATCH,"
                + "DELETE";
              
        String[] keywordList = keywordlist.split(",");
        //Arrays.sort(keywordList);
         
        for (String txt : keywordList) {
            cBoxTestFlow.addItem(txt);
        }
    }*/
    
    /*public static void apiPayloadTypeList(JComboBox<String> cBoxTestFlow) {
        String keywordlist = "," +"form-data,"
                + "x-www-form-urlencoded,"
                + "JSON,"
                + "HTML,"
                + "XML";
              
        String[] keywordList = keywordlist.split(",");
        //Arrays.sort(keywordList);
         
        for (String txt : keywordList) {
            cBoxTestFlow.addItem(txt);
        }
    }*/
    
    /*public static void apiAuthList(JComboBox<String> cBoxTestFlow) {
        String keywordlist = "," +"Basic Auth,"
                + "Bearer Token";
              
        String[] keywordList = keywordlist.split(",");
        //Arrays.sort(keywordList);
         
        for (String txt : keywordList) {
            cBoxTestFlow.addItem(txt);
        }
    }*/
    
    public static void setTableColWidthForCreateRegSuiteTable(){
        //tableAddTestFlow.getColumnModel().getColumn(0).setMaxWidth(50);
        tableSSLCertConfig.getColumnModel().getColumn(0).setMinWidth(180);
        
        //tableAddTestFlow.getColumnModel().getColumn(1).setMaxWidth(72);
        tableSSLCertConfig.getColumnModel().getColumn(1).setMinWidth(255);
        
        //tableAddTestFlow.getColumnModel().getColumn(2).setMaxWidth(350);
          tableSSLCertConfig.getColumnModel().getColumn(2).setMinWidth(150);
        
        //tableAddTestFlow.getColumnModel().getColumn(3).setMaxWidth(120);
        tableSSLCertConfig.getColumnModel().getColumn(3).setMinWidth(254);
        
        //tableAddTestFlow.getColumnModel().getColumn(4).setMaxWidth(120);
        tableSSLCertConfig.getColumnModel().getColumn(4).setMinWidth(149);
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
    public javax.swing.JPanel pnlCreateSuiteMenu;
    public javax.swing.JPanel pnlCreateTestSuite;
    public static javax.swing.JScrollPane scrollPaneTestFlow;
    public static javax.swing.JTable tableSSLCertConfig;
    // End of variables declaration//GEN-END:variables
}