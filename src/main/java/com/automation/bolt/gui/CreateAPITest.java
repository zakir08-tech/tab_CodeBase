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
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author zakir
 */
public class CreateAPITest extends javax.swing.JFrame {
    public static DefaultTableModel createSuiteTabModel =new DefaultTableModel();
    public static DefaultTableModel createORTabModel =new DefaultTableModel();
    
    public static JComboBox<String> cBoxApiRequest =new JComboBox<String>();
    public static JComboBox<String> cBoxApiSSL =new JComboBox<String>();
    public static JComboBox<String> coBoxPayloadType =new JComboBox<String>();
    public static JComboBox<String> coBoxAuth =new JComboBox<String>();
    
    public static JTextField elmNameTxt =new JTextField();
    public static JTextField elmIdTxt =new JTextField();
    public static JTextField elmXpathTxt =new JTextField();
    
    public static JTextField testIdTxt =new JTextField();
    public static JTextField testURLTxt =new JTextField();
    public static JTextField testExpectedStatusTxt =new JTextField();
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
    public static TableColumn testExpectedStatusCol =null;
    public static TableColumn testApiTypeCol =null;
    public static TableColumn testApiSSLCol =null;
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
    public CreateAPITest() {
        
        initComponents();
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        createSuiteTabModel =(DefaultTableModel) tableAddTestFlow.getModel();

        testIdCol =tableAddTestFlow.getColumnModel().getColumn(0);
        testIdCol.setCellEditor(new DefaultCellEditor(testIdTxt));
        
        testURLCol =tableAddTestFlow.getColumnModel().getColumn(2);
        testURLCol.setCellEditor(new DefaultCellEditor(testURLTxt));
        
        testExpectedStatusCol =tableAddTestFlow.getColumnModel().getColumn(15);
        testExpectedStatusCol.setCellEditor(new DefaultCellEditor(testExpectedStatusTxt));
        
        testPayloadCol =tableAddTestFlow.getColumnModel().getColumn(7);
        testPayloadCol.setCellEditor(new DefaultCellEditor(testPayloadTxt));
        
        testApiTypeCol = tableAddTestFlow.getColumnModel().getColumn(1);
        cBoxApiRequest = new JComboBox<String>();
        apiRequestList(cBoxApiRequest);
        testApiTypeCol.setCellEditor(new DefaultCellEditor(cBoxApiRequest));
        //cBoxApiRequest.setEditable(true);
        
        testApiSSLCol = tableAddTestFlow.getColumnModel().getColumn(14);
        cBoxApiSSL = new JComboBox<String>();
        apiSSLCertList(cBoxApiSSL);
        testApiSSLCol.setCellEditor(new DefaultCellEditor(cBoxApiSSL));
        //cBoxApiSSL.setEditable(true);
        
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
        
        testExpectedStatusTxt.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                common.testIdTxtKeyTyped(evt, testExpectedStatusTxt);
            }
        });
        
        testExpectedStatusTxt.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                testExpectedStatusTxtKeyReleased(evt, testExpectedStatusTxt);
            }
        });
        
        testPayloadTxt.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                testPayloadTxtKeyReleased(evt, testPayloadTxt);
            }
        });
         
        coBoxAuth.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                authSelected(evt, coBoxAuth);
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

        txtAPIurl = new javax.swing.JTextField();
        pnlCreateTestSuite = new javax.swing.JPanel();
        scrollPaneTestFlow = new javax.swing.JScrollPane();
        tableAddTestFlow = new javax.swing.JTable();
        dPanelMenu = new javax.swing.JDesktopPane();
        pnlCreateSuiteMenu = new javax.swing.JPanel();
        bttnAddNewTestStep = new javax.swing.JButton();
        bttnDeleteTestStep = new javax.swing.JButton();
        bttnAddStepUp = new javax.swing.JButton();
        bttnAddStepDown = new javax.swing.JButton();
        bttnSaveSuite = new javax.swing.JButton();
        bttnAddNewTestSuite = new javax.swing.JButton();
        pnlTestAttributes = new javax.swing.JPanel();
        lblPayload = new javax.swing.JLabel();
        lblAuthorization = new javax.swing.JLabel();
        lblHeaders = new javax.swing.JLabel();
        lblParams = new javax.swing.JLabel();
        scrlPnlParams = new javax.swing.JScrollPane();
        txtAreaParams = new javax.swing.JTextArea();
        scrlPnlHeaders = new javax.swing.JScrollPane();
        txtAreaHeaders = new javax.swing.JTextArea();
        scrlPnlAuthorization = new javax.swing.JScrollPane();
        txtAreaAuthorization = new javax.swing.JTextArea();
        scrlPnlPayload = new javax.swing.JScrollPane();
        txtAreaPayload = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        lblPayload1 = new javax.swing.JLabel();
        lblPayload2 = new javax.swing.JLabel();
        txtRequestType = new javax.swing.JTextField();
        txtExpStatus = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Create API Test");
        setMinimumSize(new java.awt.Dimension(955, 492));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        txtAPIurl.setBackground(new java.awt.Color(51, 51, 51));
        txtAPIurl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtAPIurl.setForeground(new java.awt.Color(255, 255, 204));
        txtAPIurl.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtAPIurl.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 1));
        txtAPIurl.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtAPIurl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAPIurlFocusLost(evt);
            }
        });
        txtAPIurl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAPIurlKeyReleased(evt);
            }
        });

        tableAddTestFlow.setBackground(new java.awt.Color(51, 51, 51));
        tableAddTestFlow.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        tableAddTestFlow.setForeground(new java.awt.Color(255, 255, 255));
        tableAddTestFlow.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Test ID", "Request", "URL", "Headers (key)", "Headers (value)", "Params (key)", "Params (value)", "Payload", "Payload Type", "Modify Payload (key)", "Modify Payload (value)", "Authorization", "", "", "SSL Validation", "Expected Status", "Test Description"
            }
        ));
        tableAddTestFlow.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tableAddTestFlow.setName("tableAddTestFlow"); // NOI18N
        tableAddTestFlow.setRowHeight(30);
        tableAddTestFlow.setRowMargin(2);
        tableAddTestFlow.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableAddTestFlow.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableAddTestFlow.setShowGrid(true);
        tableAddTestFlow.getTableHeader().setReorderingAllowed(false);
        tableAddTestFlow.setUpdateSelectionOnSort(false);
        tableAddTestFlow.setVerifyInputWhenFocusTarget(false);
        tableAddTestFlow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableAddTestFlowMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableAddTestFlowMouseReleased(evt);
            }
        });
        tableAddTestFlow.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableAddTestFlowKeyReleased(evt);
            }
        });
        scrollPaneTestFlow.setViewportView(tableAddTestFlow);

        javax.swing.GroupLayout pnlCreateTestSuiteLayout = new javax.swing.GroupLayout(pnlCreateTestSuite);
        pnlCreateTestSuite.setLayout(pnlCreateTestSuiteLayout);
        pnlCreateTestSuiteLayout.setHorizontalGroup(
            pnlCreateTestSuiteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPaneTestFlow, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        pnlCreateTestSuiteLayout.setVerticalGroup(
            pnlCreateTestSuiteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCreateTestSuiteLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(scrollPaneTestFlow, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
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

                            bttnAddNewTestSuite.setBackground(new java.awt.Color(0, 0, 0));
                            bttnAddNewTestSuite.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
                            bttnAddNewTestSuite.setForeground(new java.awt.Color(255, 255, 255));
                            bttnAddNewTestSuite.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/addUploadTestSuite.png"));
                                bttnAddNewTestSuite.setToolTipText("will add a blank test suite");
                                bttnAddNewTestSuite.setBorder(null);
                                bttnAddNewTestSuite.setBorderPainted(false);
                                bttnAddNewTestSuite.setContentAreaFilled(false);
                                bttnAddNewTestSuite.setFocusPainted(false);
                                bttnAddNewTestSuite.setFocusable(false);
                                bttnAddNewTestSuite.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                                bttnAddNewTestSuite.setOpaque(true);
                                bttnAddNewTestSuite.setRequestFocusEnabled(false);
                                bttnAddNewTestSuite.setRolloverEnabled(false);
                                bttnAddNewTestSuite.addMouseListener(new java.awt.event.MouseAdapter() {
                                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                                        bttnAddNewTestSuiteMouseEntered(evt);
                                    }
                                    public void mouseExited(java.awt.event.MouseEvent evt) {
                                        bttnAddNewTestSuiteMouseExited(evt);
                                    }
                                });
                                bttnAddNewTestSuite.addActionListener(new java.awt.event.ActionListener() {
                                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        bttnAddNewTestSuiteActionPerformed(evt);
                                    }
                                });

                                javax.swing.GroupLayout pnlCreateSuiteMenuLayout = new javax.swing.GroupLayout(pnlCreateSuiteMenu);
                                pnlCreateSuiteMenu.setLayout(pnlCreateSuiteMenuLayout);
                                pnlCreateSuiteMenuLayout.setHorizontalGroup(
                                    pnlCreateSuiteMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCreateSuiteMenuLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(pnlCreateSuiteMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(bttnAddNewTestSuite, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                                            .addComponent(bttnSaveSuite, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(bttnAddNewTestSuite, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                        .addGap(0, 0, 0))
                                );

                                lblPayload.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                                lblPayload.setText("Payload");

                                lblAuthorization.setBackground(new java.awt.Color(51, 51, 51));
                                lblAuthorization.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                                lblAuthorization.setText("Authorization");

                                lblHeaders.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                                lblHeaders.setText("Headers");

                                lblParams.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                                lblParams.setText("Params");

                                scrlPnlParams.setBorder(null);
                                scrlPnlParams.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

                                txtAreaParams.setEditable(false);
                                txtAreaParams.setBackground(new java.awt.Color(51, 51, 51));
                                txtAreaParams.setColumns(20);
                                txtAreaParams.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
                                txtAreaParams.setForeground(new java.awt.Color(153, 255, 204));
                                txtAreaParams.setLineWrap(true);
                                txtAreaParams.setRows(5);
                                scrlPnlParams.setViewportView(txtAreaParams);

                                scrlPnlHeaders.setBackground(new java.awt.Color(51, 51, 51));
                                scrlPnlHeaders.setBorder(null);
                                scrlPnlHeaders.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

                                txtAreaHeaders.setEditable(false);
                                txtAreaHeaders.setBackground(new java.awt.Color(51, 51, 51));
                                txtAreaHeaders.setColumns(20);
                                txtAreaHeaders.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
                                txtAreaHeaders.setForeground(new java.awt.Color(255, 153, 153));
                                txtAreaHeaders.setLineWrap(true);
                                txtAreaHeaders.setRows(5);
                                scrlPnlHeaders.setViewportView(txtAreaHeaders);

                                scrlPnlAuthorization.setBorder(null);

                                txtAreaAuthorization.setEditable(false);
                                txtAreaAuthorization.setBackground(new java.awt.Color(51, 51, 51));
                                txtAreaAuthorization.setColumns(20);
                                txtAreaAuthorization.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
                                txtAreaAuthorization.setForeground(new java.awt.Color(204, 204, 255));
                                txtAreaAuthorization.setLineWrap(true);
                                txtAreaAuthorization.setRows(5);
                                scrlPnlAuthorization.setViewportView(txtAreaAuthorization);

                                scrlPnlPayload.setBorder(null);

                                txtAreaPayload.setBackground(new java.awt.Color(51, 51, 51));
                                txtAreaPayload.setColumns(20);
                                txtAreaPayload.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
                                txtAreaPayload.setForeground(new java.awt.Color(255, 153, 0));
                                txtAreaPayload.setRows(5);
                                txtAreaPayload.setTabSize(1);
                                txtAreaPayload.addFocusListener(new java.awt.event.FocusAdapter() {
                                    public void focusLost(java.awt.event.FocusEvent evt) {
                                        txtAreaPayloadFocusLost(evt);
                                    }
                                });
                                txtAreaPayload.addKeyListener(new java.awt.event.KeyAdapter() {
                                    public void keyReleased(java.awt.event.KeyEvent evt) {
                                        txtAreaPayloadKeyReleased(evt);
                                    }
                                });
                                scrlPnlPayload.setViewportView(txtAreaPayload);

                                jScrollPane1.setBorder(null);

                                jTextArea1.setBackground(new java.awt.Color(51, 51, 51));
                                jTextArea1.setColumns(20);
                                jTextArea1.setRows(5);
                                jScrollPane1.setViewportView(jTextArea1);

                                jScrollPane2.setBackground(new java.awt.Color(51, 51, 51));
                                jScrollPane2.setBorder(null);

                                jTextArea3.setBackground(new java.awt.Color(51, 51, 51));
                                jTextArea3.setColumns(20);
                                jTextArea3.setRows(5);
                                jScrollPane2.setViewportView(jTextArea3);

                                lblPayload1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                                lblPayload1.setText("Modify Payload");

                                lblPayload2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                                lblPayload2.setText("Verify Payload");

                                javax.swing.GroupLayout pnlTestAttributesLayout = new javax.swing.GroupLayout(pnlTestAttributes);
                                pnlTestAttributes.setLayout(pnlTestAttributesLayout);
                                pnlTestAttributesLayout.setHorizontalGroup(
                                    pnlTestAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlTestAttributesLayout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addGroup(pnlTestAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(scrlPnlHeaders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblHeaders))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(pnlTestAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(scrlPnlAuthorization, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(pnlTestAttributesLayout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(lblAuthorization))
                                            .addComponent(scrlPnlParams, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblParams))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(pnlTestAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(scrlPnlPayload, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblPayload, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(pnlTestAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblPayload1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(pnlTestAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblPayload2, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                );
                                pnlTestAttributesLayout.setVerticalGroup(
                                    pnlTestAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(pnlTestAttributesLayout.createSequentialGroup()
                                        .addGroup(pnlTestAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblPayload, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(pnlTestAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(lblPayload1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(lblPayload2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 0, 0)
                                        .addGroup(pnlTestAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(scrlPnlPayload)
                                            .addComponent(jScrollPane1)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)))
                                    .addGroup(pnlTestAttributesLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(pnlTestAttributesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(pnlTestAttributesLayout.createSequentialGroup()
                                                .addComponent(lblParams, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(scrlPnlParams, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(2, 2, 2)
                                                .addComponent(lblAuthorization, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(scrlPnlAuthorization, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(pnlTestAttributesLayout.createSequentialGroup()
                                                .addComponent(lblHeaders, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(scrlPnlHeaders, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                );

                                txtRequestType.setEditable(false);
                                txtRequestType.setBackground(new java.awt.Color(51, 51, 51));
                                txtRequestType.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                                txtRequestType.setForeground(new java.awt.Color(255, 204, 102));
                                txtRequestType.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                                txtRequestType.setBorder(null);
                                txtRequestType.addActionListener(new java.awt.event.ActionListener() {
                                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        txtRequestTypeActionPerformed(evt);
                                    }
                                });

                                txtExpStatus.setEditable(false);
                                txtExpStatus.setBackground(new java.awt.Color(51, 51, 51));
                                txtExpStatus.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                                txtExpStatus.setForeground(new java.awt.Color(255, 204, 102));
                                txtExpStatus.setHorizontalAlignment(javax.swing.JTextField.CENTER);
                                txtExpStatus.setBorder(null);
                                txtExpStatus.addActionListener(new java.awt.event.ActionListener() {
                                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        txtExpStatusActionPerformed(evt);
                                    }
                                });

                                jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                                jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                                jLabel1.setText("URL");

                                jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                                jLabel2.setText("Status");

                                jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                                jLabel3.setText("Request");

                                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                                getContentPane().setLayout(layout);
                                layout.setHorizontalGroup(
                                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(pnlCreateTestSuite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(1, 1, 1))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(pnlTestAttributes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(txtRequestType, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel3))
                                                        .addGap(5, 5, 5)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(txtExpStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(txtAPIurl))))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)))
                                        .addComponent(dPanelMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(1, 1, 1))
                                );
                                layout.setVerticalGroup(
                                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(pnlCreateTestSuite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(1, 1, 1)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addGap(1, 1, 1)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtAPIurl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtExpStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtRequestType, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(1, 1, 1)
                                        .addComponent(pnlTestAttributes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(1, 1, 1))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(dPanelMenu)
                                        .addGap(1, 1, 1))
                                );

                                getAccessibleContext().setAccessibleParent(this);

                                setSize(new java.awt.Dimension(1594, 799));
                                setLocationRelativeTo(null);
                            }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
       tableCellRendererAPI renderer = new tableCellRendererAPI();
       //tableAddORCellRenderer addORrenderer = new tableAddORCellRenderer();
       
        tableAddTestFlow.setDefaultRenderer(Object.class, renderer);
       //tableAddOR.setDefaultRenderer(Object.class, addORrenderer);
    }//GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Image titleIcon = Toolkit.getDefaultToolkit().getImage(constants.userDir+"\\icons\\bolt.jpg");
        this.setIconImage(titleIcon);
        setTableColWidthForCreateRegSuiteTable();
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
        String getURLText =textField.getText();
        txtAPIurl.setText(getURLText);
    }
    
    public static void testExpectedStatusTxtKeyReleased(KeyEvent evt, JTextField textField) {
        getCurrRowBeforeKeyPressed =tableAddTestFlow.getSelectedRow();
        String getTestId =(String) tableAddTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 0);

        if(getTestId !=null && !getTestId.isEmpty()){
            String getStatusText =textField.getText();
            txtExpStatus.setText(getStatusText);
        }
    }
     
    public static void testPayloadTxtKeyReleased(KeyEvent evt, JTextField textField) {
        String getPayloadText =textField.getText();
        txtAreaPayload.setText(common.writeJsonPayloadToTheTextArea(getPayloadText));
    }
    
    public static void authSelected(java.awt.event.FocusEvent evt, JComboBox<String> authField) {
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
    }
     
    private void bttnAddNewTestStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnAddNewTestStepActionPerformed
        if(getElmRepoSelectedRow !=-1){
            //getElmRepoSelectedRow =tableAddOR.getSelectedRow();
            //tabOutFromEditingColumn(getElmRepoCellEditorStatus, tableAddOR, getRepoCellxPoint, getRepoCellyPoint, getElmRepoSelectedRow);
        }
            
        if(getTestFlowSelectedRow !=-1){
            getTestFlowSelectedRow =tableAddTestFlow.getSelectedRow();
            tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableAddTestFlow, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
            
        if(common.checkForDuplicateTestId(createSuiteTabModel, tableAddTestFlow, editableRow, testIdTxt) ==true)
            return;
        
        Object getPreviosTestStepNo =null;
        if(tableAddTestFlow.getRowCount() ==0)
            getPreviosTestStepNo ="0";
        else
            getPreviosTestStepNo = tableAddTestFlow.getValueAt(tableAddTestFlow.getRowCount()-1, 1);
        
        createSuiteTabModel.addRow(new Object[] {null,null,null,null,null,null});
        tableAddTestFlow.setColumnSelectionInterval(0, 0);
        //tableAddTestFlow.setValueAt(Integer.valueOf(getPreviosTestStepNo.toString())+1, tableAddTestFlow.getRowCount()-1, 1);
        tableAddTestFlow.setRowSelectionInterval(tableAddTestFlow.getRowCount()-1, tableAddTestFlow.getRowCount()-1);
        tableAddTestFlow.scrollRectToVisible(tableAddTestFlow.getCellRect(tableAddTestFlow.getRowCount()-1,0, true));
        tableAddTestFlow.requestFocus();
        getTestFlowSelectedRow =tableAddTestFlow.getSelectedRow();
        
        getCurrRowBeforeKeyPressed =tableAddTestFlow.getSelectedRow();
        updateAPIAttributeData();
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
            getTestFlowSelectedRow =tableAddTestFlow.getSelectedRow();
            tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableAddTestFlow, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
        
        if(common.checkForDuplicateTestId(createSuiteTabModel, tableAddTestFlow, editableRow, testIdTxt) ==true)
            return;
        
        String getTestId =null;
            
        if (tableAddTestFlow.getRowCount() > 0) {
            getTestFlowSelectedRow =tableAddTestFlow.getSelectedRow();
            int rowIndex =tableAddTestFlow.getSelectedRow();
            
            try {
                getTestId =tableAddTestFlow.getValueAt(rowIndex, 0).toString();
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
                tableAddTestFlow.setRowSelectionInterval(rowIndex-1, rowIndex-1);
                tableAddTestFlow.setColumnSelectionInterval(0, 0);
                tableAddTestFlow.requestFocus();
            }catch(IllegalArgumentException exp) {
            	rowIndex =tableAddTestFlow.getSelectedRow();
            	if(rowIndex ==-1 && tableAddTestFlow.getRowCount() ==0)
            		{return;}
            	
            	rowIndex =tableAddTestFlow.getSelectedRow();
            	if(rowIndex ==-1)
            		rowIndex =0;
            	else if(tableAddTestFlow.getRowCount() ==-1)
            		return;
            	
            	tableAddTestFlow.setRowSelectionInterval(rowIndex, rowIndex);
                tableAddTestFlow.setColumnSelectionInterval(0, 0);
                tableAddTestFlow.requestFocus();
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
            getTestFlowSelectedRow =tableAddTestFlow.getSelectedRow();
            tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableAddTestFlow, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
        
        if(common.checkForDuplicateTestId(createSuiteTabModel, tableAddTestFlow, editableRow, testIdTxt) ==true)
            return;
        
        int getTestStep =0;
        if(tableAddTestFlow.getRowCount()>0){
            getTestFlowSelectedRow =tableAddTestFlow.getSelectedRow();
            int rowIndex =tableAddTestFlow.getSelectedRow();
            
            if(rowIndex ==0) {
            	try {
                    tableAddTestFlow.setColumnSelectionInterval(rowIndex, 0);
                    return;
            	}catch(IllegalArgumentException exp) {return;}
            }
            	
            if(rowIndex !=-1){
                rowIndex =tableAddTestFlow.getSelectedRow();
                String getTestId = null;
                    
                try{
                    getTestId =tableAddTestFlow.getValueAt(rowIndex, 0).toString();
                }catch(NullPointerException exp){
                    getTestId ="";
                }
                                    
                createSuiteTabModel.insertRow(rowIndex, new Object[] {null,null,null,null,null,null });
                tableAddTestFlow.setRowSelectionInterval(rowIndex, rowIndex);
                tableAddTestFlow.setColumnSelectionInterval(0, 0);
                tableAddTestFlow.scrollRectToVisible(tableAddTestFlow.getCellRect(rowIndex, 0, true));
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
        
        if(common.checkForDuplicateTestId(createSuiteTabModel, tableAddTestFlow, editableRow, testIdTxt) ==true)
            return;
        
        if(tableAddTestFlow.getRowCount()>0){
            getTestFlowSelectedRow =tableAddTestFlow.getSelectedRow();
            int rowIndex = tableAddTestFlow.getSelectedRow();
            if (rowIndex != -1) {
                try {
                    try {
                        //String getTestID = tableAddTestFlow.getValueAt(tableAddTestFlow.getSelectedRow(), 0).toString();
                        createSuiteTabModel.insertRow(rowIndex + 1, new Object[]{null, null, null, null, null, null});
                        rowIndex = tableAddTestFlow.getSelectedRow();
                        tableAddTestFlow.setRowSelectionInterval(rowIndex + 1, rowIndex + 1);
                        tableAddTestFlow.setColumnSelectionInterval(0, 0);
                        tableAddTestFlow.scrollRectToVisible(tableAddTestFlow.getCellRect(rowIndex+1, 0, true));
                    } catch (NullPointerException exp) {
                        createSuiteTabModel.insertRow(rowIndex + 1, new Object[]{null, null, null, null, null, null});
                        rowIndex = tableAddTestFlow.getSelectedRow();
                        tableAddTestFlow.setRowSelectionInterval(rowIndex + 1, rowIndex + 1);
                        tableAddTestFlow.setColumnSelectionInterval(0, 0);
                        tableAddTestFlow.scrollRectToVisible(tableAddTestFlow.getCellRect(rowIndex+1, 0, true));
                    }
                } catch (NullPointerException exp) {
                    createSuiteTabModel.insertRow(rowIndex + 1, new Object[]{null, null, null, null, null, null});
                    rowIndex = tableAddTestFlow.getSelectedRow();
                    tableAddTestFlow.setRowSelectionInterval(rowIndex + 1, rowIndex + 1);
                    tableAddTestFlow.setColumnSelectionInterval(0, 0);
                    tableAddTestFlow.scrollRectToVisible(tableAddTestFlow.getCellRect(rowIndex+1, 0, true));
                }
            } else {
                JOptionPane.showMessageDialog(tableAddTestFlow, "Select row to add test step!", "Alert", JOptionPane.WARNING_MESSAGE);
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
            //getElmRepoSelectedRow =tableAddOR.getSelectedRow();
            //tabOutFromEditingColumn(getElmRepoCellEditorStatus, tableAddOR, getRepoCellxPoint, getRepoCellyPoint, getElmRepoSelectedRow);
        }
            
        if(getTestFlowSelectedRow !=-1){
            getTestFlowSelectedRow =tableAddTestFlow.getSelectedRow();
            tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableAddTestFlow, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
        
        if(common.checkForDuplicateTestId(createSuiteTabModel, tableAddTestFlow, editableRow, testIdTxt) ==true)
            return;
                
        if(tableAddTestFlow.getRowCount() > 0){
            FileOutputStream excelFos;
            XSSFWorkbook excelJTableExport = new XSSFWorkbook();
            boolean fileExist;
            fileExist = false;
            String getCurrDir;
            
            try{
                File getCurrDirectory =excelFileExport.getCurrentDirectory();
                getCurrDir =getCurrDirectory.getAbsolutePath();
            }catch(NullPointerException exp){
                getCurrDir =FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
            }
            
            excelFileExport = new JFileChooser(getCurrDir);
            excelFileExport.setDialogTitle("Save Test Suite");
            excelFileExport.setFileSelectionMode(JFileChooser.FILES_ONLY);

            excelFileExport.addChoosableFileFilter(new FileNameExtensionFilter("EXCEL WORKBOOK", "xlsx"));
            excelFileExport.setAcceptAllFileFilterUsed(false);

            int excelChooser = excelFileExport.showSaveDialog(this);
            
            if (excelChooser == JFileChooser.APPROVE_OPTION) {
                String getFilePath =excelFileExport.getSelectedFile().toString();
                String extension = Files.getFileExtension(getFilePath);
                if(extension.isEmpty())
                    getFilePath =getFilePath+".xlsx";
                
                File exclFile = new File(getFilePath);
                if (exclFile.exists()) {
                    fileExist =true;
                    int response = JOptionPane.showConfirmDialog(RegressionSuiteScrollPane, //
                            "Test suite " + "\"" + excelFileExport.getSelectedFile().getName() + "\"" + " already exist!,\nDo you want to replace the existing test suite?", //
                            "Confirm", JOptionPane.YES_NO_OPTION, //
                            JOptionPane.QUESTION_MESSAGE);
                    if (response != JOptionPane.YES_OPTION) {
                        bttnSaveSuite.doClick();
                        return;
                    }
                }
            }
            
            if (excelChooser == JFileChooser.CANCEL_OPTION) {
                return;
            }
            fileSaved =false;
            try {
                // create test flow sheet
                excelJTableExport =createAPITestFlowDataSheet(excelJTableExport, createSuiteTabModel);   
                // create test element repository sheet
                //excelJTableExport =createObjectRepoSheetNew(excelJTableExport, createORTabModel);

                String getFilePath =null;
                if(fileExist ==true)
                    getFilePath =excelFileExport.getSelectedFile().toString();
                else if(fileExist ==false)
                        getFilePath =excelFileExport.getSelectedFile()+".xlsx";

                excelFos = new FileOutputStream(getFilePath);
                excelJTableExport.write(excelFos);

                excelFos.close();
                excelJTableExport.close();
                fileSaved =true;
                
                JOptionPane.showMessageDialog(scrollPaneTestFlow, "Test suite " + "\"" + excelFileExport.getSelectedFile().getName() + "\"" + " saved successfully!", "Alert", JOptionPane.INFORMATION_MESSAGE);
            } catch (FileNotFoundException ex) {
                //Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.SEVERE, null, ex);
                if (ex.getMessage().contains("The process cannot access the file because it is being used by another process")) {
                    int response;

                    do {
                        response = JOptionPane.showConfirmDialog(scrollPaneTestFlow, //
                                "Close test suite " + "\"" + excelFileExport.getSelectedFile().getName() + "\"" + " to save the changes!", //
                                "Alert", JOptionPane.OK_CANCEL_OPTION, //
                                JOptionPane.WARNING_MESSAGE);

                        if (response == JOptionPane.OK_OPTION) {
                            try {
                                excelFos = new FileOutputStream(excelFileExport.getSelectedFile());
                                excelJTableExport.write(excelFos);

                                excelFos.close();
                                excelJTableExport.close();

                                JOptionPane.showMessageDialog(scrollPaneTestFlow, "Test suite " + "\"" + excelFileExport.getSelectedFile().getName() + "\"" + " saved successfully!", "Alert", JOptionPane.INFORMATION_MESSAGE);
                                fileSaved =true;
                                break;
                            } catch (FileNotFoundException ex1) {
                                /*if (ex1.getMessage().contains("The process cannot access the file because it is being used by another process")) {

                                }*/
                            } catch (IOException ex1) {
                                //Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.SEVERE, null, ex1);
                            }
                        }
                    } while (response != JOptionPane.CANCEL_OPTION);
                }
            } catch (IOException ex) {
                //Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        else
            JOptionPane.showMessageDialog(null,"No test suite is available to save!","Alert",JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_bttnSaveSuiteActionPerformed

    private void bttnAddNewTestSuiteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnAddNewTestSuiteMouseEntered
        bttnAddNewTestSuite.setBackground(new java.awt.Color(250, 128, 114));
        bttnAddNewTestSuite.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_bttnAddNewTestSuiteMouseEntered

    private void bttnAddNewTestSuiteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnAddNewTestSuiteMouseExited
        bttnAddNewTestSuite.setBackground(new java.awt.Color(0,0,0));
        bttnAddNewTestSuite.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_bttnAddNewTestSuiteMouseExited

    private void bttnAddNewTestSuiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnAddNewTestSuiteActionPerformed
            
        if(getTestFlowSelectedRow !=-1){
            getTestFlowSelectedRow =tableAddTestFlow.getSelectedRow();
            tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableAddTestFlow, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
        
        if(fileSaved ==false){
            int response = JOptionPane.showConfirmDialog(RegressionSuiteScrollPane, //
                            "the test suite is not saved, all changes will be lost!\n\ndo you want to continue?", //
                            "Confirm", JOptionPane.YES_NO_OPTION, //
                            JOptionPane.QUESTION_MESSAGE);
            if (response != JOptionPane.YES_OPTION) {
                //bttnSaveSuite.doClick();
                return;
            }
        }
        fileSaved =false;
        
        DefaultTableModel modelAddTestFlow = (DefaultTableModel)tableAddTestFlow.getModel();
        modelAddTestFlow.getDataVector().removeAllElements();
        modelAddTestFlow.fireTableDataChanged();
       
        editableRow =0;
        editableAddElmRow =0;
        
        //tableAddOR.removeEditor();
        tableAddTestFlow.removeEditor();
        
        createSuiteTabModel =(DefaultTableModel) tableAddTestFlow.getModel();
        //createORTabModel =(DefaultTableModel) tableAddOR.getModel();
        
        elmNameTxt =new JTextField();
        //elmNameCol =tableAddOR.getColumnModel().getColumn(0);
        elmNameCol.setCellEditor(new DefaultCellEditor(elmNameTxt));
        
        elmIdTxt =new JTextField();
        //elmIdCol =tableAddOR.getColumnModel().getColumn(1);
        elmIdCol.setCellEditor(new DefaultCellEditor(elmIdTxt));
        
        elmXpathTxt =new JTextField();
        //elmXpathCol =tableAddOR.getColumnModel().getColumn(2);
        elmXpathCol.setCellEditor(new DefaultCellEditor(elmXpathTxt));
        
        getTestFlowCellEditorStatus =false;
        getElmRepoCellEditorStatus =false;
    }//GEN-LAST:event_bttnAddNewTestSuiteActionPerformed
        
    private void tableAddTestFlowMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableAddTestFlowMousePressed
        
        //getElmRepoSelectedRow =tableAddOR.getSelectedRow();
        //tabOutFromEditingColumn(getElmRepoCellEditorStatus, tableAddOR, getRepoCellxPoint, getRepoCellyPoint, getElmRepoSelectedRow);
       
        //writeJsonPayloadToTheTextArea();
        
        if(common.checkForDuplicateTestId(createSuiteTabModel, tableAddTestFlow, editableRow, testIdTxt) ==true)
            return;
         
        int getCurRow = tableAddTestFlow.convertRowIndexToModel(tableAddTestFlow.rowAtPoint(evt.getPoint()));
        int gerCurrCol = tableAddTestFlow.convertColumnIndexToModel(tableAddTestFlow.columnAtPoint(evt.getPoint()));
        
        getTestFlowSelectedRow =getCurRow;
        getFlowCellxPoint =tableAddTestFlow.rowAtPoint(evt.getPoint());
        getFlowCellyPoint =tableAddTestFlow.columnAtPoint(evt.getPoint());
        
        if(duplicateTestId ==false){
            switch (gerCurrCol) {
                case 0:
                    tableAddTestFlow.editCellAt(getCurRow, 0);
                    editableRow =tableAddTestFlow.getEditingRow();
                    testIdTxt.requestFocusInWindow();
                    break;
                case 2:
                    tableAddTestFlow.editCellAt(getCurRow, 2);
                    testURLTxt.requestFocusInWindow();
                    break;
                case 3:
                    tableAddTestFlow.editCellAt(getCurRow, 3);
                    //coBoxObjectRepo.requestFocusInWindow();
                    break;    
                case 4:
                    tableAddTestFlow.editCellAt(getCurRow, 4);
                    testURLTxt.requestFocusInWindow();
                    break;
                case 7:
                    tableAddTestFlow.editCellAt(getCurRow, 7);
                    testPayloadTxt.requestFocusInWindow();
                    break;
                case 15:
                    tableAddTestFlow.editCellAt(getCurRow, 15);
                    testExpectedStatusTxt.requestFocusInWindow();
                default:
                    break;
            }
        }
    }//GEN-LAST:event_tableAddTestFlowMousePressed

    private void tableAddTestFlowKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableAddTestFlowKeyReleased
        updateAPIAttributeData();
        common.checkForDuplicateTestId(createSuiteTabModel, tableAddTestFlow, editableRow, testIdTxt);
    }//GEN-LAST:event_tableAddTestFlowKeyReleased
    
    /*public static String writeJsonPayloadToTheTextArea(String jsonPayload){
    	String prettyJson ="";
        
    	if(jsonPayload ==null || jsonPayload.isEmpty())
            return jsonPayload="";
    	
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonElement jsonElement = JsonParser.parseString(jsonPayload);
            prettyJson = gson.toJson(jsonElement);
            jsonPayload =prettyJson;
        } catch (JsonParseException ex) {
            jsonPayload =jsonPayload;
            JOptionPane.showMessageDialog(null,"Invalid json body!","Alert",JOptionPane.WARNING_MESSAGE);
        }
        
        return jsonPayload;
    }*/
    
    private void txtAPIurlFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAPIurlFocusLost
        getTheAPIurl =txtAPIurl.getText();
        tableAddTestFlow.setValueAt(getTheAPIurl,getCurrRowBeforeKeyPressed, 2);
    }//GEN-LAST:event_txtAPIurlFocusLost

    private void txtAPIurlKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAPIurlKeyReleased
        try{
            tableAddTestFlow.setValueAt(txtAPIurl.getText(),getCurrRowBeforeKeyPressed, 2);
        }catch(ArrayIndexOutOfBoundsException exp){}
        
    }//GEN-LAST:event_txtAPIurlKeyReleased

    private void tableAddTestFlowMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableAddTestFlowMouseReleased
      updateAPIAttributeData();
    }//GEN-LAST:event_tableAddTestFlowMouseReleased

    private void txtAreaPayloadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAreaPayloadFocusLost
        getApiPayload =txtAreaPayload.getText();
        try{
            tableAddTestFlow.setValueAt(getApiPayload,getCurrRowBeforeKeyPressed, 7);
        }catch(ArrayIndexOutOfBoundsException exp){}
    }//GEN-LAST:event_txtAreaPayloadFocusLost

    private void txtAreaPayloadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAreaPayloadKeyReleased
        try{
            tableAddTestFlow.setValueAt(txtAreaPayload.getText(),getCurrRowBeforeKeyPressed, 7);
        }catch(ArrayIndexOutOfBoundsException exp){}
    }//GEN-LAST:event_txtAreaPayloadKeyReleased

    private void txtExpStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtExpStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtExpStatusActionPerformed

    private void txtRequestTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRequestTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRequestTypeActionPerformed
    
    public static void updateAPIAttributeData(){
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
        
        // update expected status
        try{
           String getTestId =(String) tableAddTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 0);
           if(getTestId !=null && !getTestId.isEmpty()){
                String getStatus =(String) tableAddTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 15);
                if(getStatus ==null)
                     getStatus ="";

                txtExpStatus.setText(getStatus);
            }else
                txtExpStatus.setText("");
        }catch(NullPointerException | ArrayIndexOutOfBoundsException exp){}
        
        // update request type
        try{
           String getTestId =(String) tableAddTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 0);
           if(getTestId !=null && !getTestId.isEmpty()){
                String getReqType =(String) tableAddTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 1);
                if(getReqType ==null)
                     getReqType ="";
                
                if(getReqType.contentEquals("GET")){
                    txtRequestType.setForeground(Color.green);
                }else if(getReqType.contentEquals("POST")){
                    txtRequestType.setForeground(new java.awt.Color(255,153,0));
                }else if(getReqType.contentEquals("PUT")){
                    txtRequestType.setForeground(new java.awt.Color(153,153,255));
                }else if(getReqType.contentEquals("PATCH")){
                    txtRequestType.setForeground(new java.awt.Color(255,255,255));
                }else if(getReqType.contentEquals("DELETE")){
                    txtRequestType.setForeground(new java.awt.Color(255,102,102));
                }
                
                txtRequestType.setText(getReqType);
            }else
               txtRequestType.setText("");
        }catch(NullPointerException | ArrayIndexOutOfBoundsException exp){}   
    }
    
    public static boolean checkElementExistInTheList(String listItem){
        boolean itemExist =false;
        /*String getText =null;
        
        for(int x=0; x<coBoxObjectRepo.getItemCount(); x++){
            getText =coBoxObjectRepo.getItemAt(x).trim().toLowerCase();
            if(getText.contentEquals(listItem.toLowerCase().trim())){
                itemExist =true;
                break;
            }
        }*/
        return itemExist;
    }
    
    public static void apiRequestList(JComboBox<String> cBoxTestFlow) {
        String keywordlist = "," +"GET,"
                + "POST,"
                + "PUT,"
                + "PATCH,"
                + "DELETE";
              
        String[] keywordList = keywordlist.split(","); 
        for (String txt : keywordList) {
            cBoxTestFlow.addItem(txt);
        }
    }
    
    public static void apiSSLCertList(JComboBox<String> cBoxTestFlow) {
        HashMap<Integer, Object> jsonMap =common.uploadSSLCertConfiguration();
        
        for (Map.Entry<Integer,Object> entry : jsonMap.entrySet()){
            try{
                String getCertName =entry.getValue().toString().split("[,]")[0];
                cBoxTestFlow.addItem(getCertName);
            }catch(ArrayIndexOutOfBoundsException exp){} 
        }
    }
    
    public static void apiPayloadTypeList(JComboBox<String> cBoxTestFlow) {
        String keywordlist = "," +"form-data,"
                + "x-www-form-urlencoded,"
                + "JSON,"
                + "HTML,"
                + "XML";
              
        String[] keywordList = keywordlist.split(",");  
        for (String txt : keywordList) {
            cBoxTestFlow.addItem(txt);
        }
    }
    
    public static void apiAuthList(JComboBox<String> cBoxTestFlow) {
        String keywordlist = "," +"Basic Auth,"
                + "Bearer Token";
              
        String[] keywordList = keywordlist.split(",");  
        for (String txt : keywordList) {
            cBoxTestFlow.addItem(txt);
        }
    }
        
    public static void setTableColWidthForCreateRegSuiteTable(){
        tableAddTestFlow.getColumnModel().getColumn(0).setMaxWidth(50);
        tableAddTestFlow.getColumnModel().getColumn(0).setMinWidth(50);
        
        tableAddTestFlow.getColumnModel().getColumn(1).setMaxWidth(72);
        tableAddTestFlow.getColumnModel().getColumn(1).setMinWidth(72);
        
        //tableAddTestFlow.getColumnModel().getColumn(2).setMaxWidth(350);
        tableAddTestFlow.getColumnModel().getColumn(2).setMinWidth(350);
        
        //tableAddTestFlow.getColumnModel().getColumn(3).setMaxWidth(120);
        tableAddTestFlow.getColumnModel().getColumn(3).setMinWidth(120);
        
        //tableAddTestFlow.getColumnModel().getColumn(4).setMaxWidth(120);
        tableAddTestFlow.getColumnModel().getColumn(4).setMinWidth(120);
        
        //tableAddTestFlow.getColumnModel().getColumn(5).setMaxWidth(120);
        tableAddTestFlow.getColumnModel().getColumn(5).setMinWidth(120);
        
        //tableAddTestFlow.getColumnModel().getColumn(6).setMaxWidth(120);
        tableAddTestFlow.getColumnModel().getColumn(6).setMinWidth(120);
        
        //tableAddTestFlow.getColumnModel().getColumn(7).setMaxWidth(300);
        tableAddTestFlow.getColumnModel().getColumn(7).setMinWidth(300);
        
        //tableAddTestFlow.getColumnModel().getColumn(8).setMaxWidth(100);
        tableAddTestFlow.getColumnModel().getColumn(8).setMinWidth(100);
        
        //tableAddTestFlow.getColumnModel().getColumn(9).setMaxWidth(150);
        tableAddTestFlow.getColumnModel().getColumn(9).setMinWidth(150);
        
        //tableAddTestFlow.getColumnModel().getColumn(10).setMaxWidth(150);
        tableAddTestFlow.getColumnModel().getColumn(10).setMinWidth(150);
        
        //tableAddTestFlow.getColumnModel().getColumn(11).setMaxWidth(150);
        tableAddTestFlow.getColumnModel().getColumn(11).setMinWidth(100);
        
        //tableAddTestFlow.getColumnModel().getColumn(12).setMaxWidth(150);
        tableAddTestFlow.getColumnModel().getColumn(12).setMinWidth(150);
        
        //tableAddTestFlow.getColumnModel().getColumn(13).setMaxWidth(150);
        tableAddTestFlow.getColumnModel().getColumn(13).setMinWidth(150);
        
        //tableAddTestFlow.getColumnModel().getColumn(14).setMaxWidth(200);
        tableAddTestFlow.getColumnModel().getColumn(14).setMinWidth(120);
        
        //tableAddTestFlow.getColumnModel().getColumn(15).setMaxWidth(100);
        tableAddTestFlow.getColumnModel().getColumn(15).setMinWidth(100);
        
        //tableAddTestFlow.getColumnModel().getColumn(16).setMaxWidth(200);
        tableAddTestFlow.getColumnModel().getColumn(16).setMinWidth(200);
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
            java.util.logging.Logger.getLogger(CreateAPITest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
         //</editor-fold>
         //</editor-fold>
         
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new CreateAPITest().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton bttnAddNewTestStep;
    public static javax.swing.JButton bttnAddNewTestSuite;
    public static javax.swing.JButton bttnAddStepDown;
    public static javax.swing.JButton bttnAddStepUp;
    public javax.swing.JButton bttnDeleteTestStep;
    public static javax.swing.JButton bttnSaveSuite;
    public javax.swing.JDesktopPane dPanelMenu;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JTextArea jTextArea1;
    public javax.swing.JTextArea jTextArea3;
    public static javax.swing.JLabel lblAuthorization;
    public javax.swing.JLabel lblHeaders;
    public javax.swing.JLabel lblParams;
    public javax.swing.JLabel lblPayload;
    public javax.swing.JLabel lblPayload1;
    public javax.swing.JLabel lblPayload2;
    public javax.swing.JPanel pnlCreateSuiteMenu;
    public javax.swing.JPanel pnlCreateTestSuite;
    public javax.swing.JPanel pnlTestAttributes;
    public static javax.swing.JScrollPane scrlPnlAuthorization;
    public javax.swing.JScrollPane scrlPnlHeaders;
    public javax.swing.JScrollPane scrlPnlParams;
    public static javax.swing.JScrollPane scrlPnlPayload;
    public static javax.swing.JScrollPane scrollPaneTestFlow;
    public static javax.swing.JTable tableAddTestFlow;
    public static javax.swing.JTextField txtAPIurl;
    public static javax.swing.JTextArea txtAreaAuthorization;
    public static javax.swing.JTextArea txtAreaHeaders;
    public static javax.swing.JTextArea txtAreaParams;
    public static javax.swing.JTextArea txtAreaPayload;
    public static javax.swing.JTextField txtExpStatus;
    public static javax.swing.JTextField txtRequestType;
    // End of variables declaration//GEN-END:variables
}