/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.bolt.gui;

import static com.automation.bolt.common.tabOutFromEditingColumn;

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
import static com.automation.bolt.gui.EditRegressionSuite.RegressionSuiteScrollPane;
import com.automation.bolt.renderer.tableCellRendererAPI;
import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFCell;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
/**
 *
 * @author zakir
 */
public class EditAPITest extends javax.swing.JFrame {
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
    
    public static JFileChooser excelFileImport;
    public XSSFWorkbook excelImportWorkBook = new XSSFWorkbook();
    public boolean testSuiteUploaded;
    public static String testSuiteFilePath;
    public static FileInputStream excelFIS;
    public static File excelFile;
    private XSSFSheet excelSheetTestFlow;
    
    /**
     * Creates new form CreateTestSuite
     */
    public EditAPITest() {
        
        initComponents();
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        createSuiteTabModel =(DefaultTableModel) tableEditTestFlow.getModel();

        testIdCol =tableEditTestFlow.getColumnModel().getColumn(0);
        testIdCol.setCellEditor(new DefaultCellEditor(testIdTxt));
        
        testURLCol =tableEditTestFlow.getColumnModel().getColumn(2);
        testURLCol.setCellEditor(new DefaultCellEditor(testURLTxt));
        
        testExpectedStatusCol =tableEditTestFlow.getColumnModel().getColumn(15);
        testExpectedStatusCol.setCellEditor(new DefaultCellEditor(testExpectedStatusTxt));
        
        testPayloadCol =tableEditTestFlow.getColumnModel().getColumn(7);
        testPayloadCol.setCellEditor(new DefaultCellEditor(testPayloadTxt));
        
        testApiTypeCol = tableEditTestFlow.getColumnModel().getColumn(1);
        cBoxApiRequest = new JComboBox<String>();
        apiRequestList(cBoxApiRequest);
        testApiTypeCol.setCellEditor(new DefaultCellEditor(cBoxApiRequest));
        //cBoxApiRequest.setEditable(true);
        
        testApiSSLCol = tableEditTestFlow.getColumnModel().getColumn(14);
        cBoxApiSSL = new JComboBox<String>();
        apiSSLCertList(cBoxApiSSL);
        testApiSSLCol.setCellEditor(new DefaultCellEditor(cBoxApiSSL));
        //cBoxApiSSL.setEditable(true);
        
        testPayloadTypeCol = tableEditTestFlow.getColumnModel().getColumn(8);
        coBoxPayloadType = new JComboBox<String>();
        apiPayloadTypeList(coBoxPayloadType);
        testPayloadTypeCol.setCellEditor(new DefaultCellEditor(coBoxPayloadType));
        //coBoxPayloadType.setEditable(true);
        
        testAuthCol = tableEditTestFlow.getColumnModel().getColumn(11);
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
        lblParams = new javax.swing.JLabel();
        lblAuthorization = new javax.swing.JLabel();
        lblHeaders = new javax.swing.JLabel();
        scrlPnlHeaders = new javax.swing.JScrollPane();
        txtAreaHeaders = new javax.swing.JTextArea();
        scrlPnlAuthorization = new javax.swing.JScrollPane();
        txtAreaAuthorization = new javax.swing.JTextArea();
        scrlPnlParams = new javax.swing.JScrollPane();
        txtAreaParams = new javax.swing.JTextArea();
        scrlPnlPayload = new javax.swing.JScrollPane();
        txtAreaPayload = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        pnlCreateTestSuite = new javax.swing.JPanel();
        scrollPaneTestFlow = new javax.swing.JScrollPane();
        tableEditTestFlow = new javax.swing.JTable();
        dPanelMenu = new javax.swing.JDesktopPane();
        bttnAddNewTestSuite = new javax.swing.JButton();
        bttnAddNewTestStep = new javax.swing.JButton();
        bttnDeleteTestStep = new javax.swing.JButton();
        bttnAddStepUp = new javax.swing.JButton();
        bttnAddStepDown = new javax.swing.JButton();
        bttnSaveSuite = new javax.swing.JButton();
        txtRequestType = new javax.swing.JTextField();
        txtExpStatus = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblParams1 = new javax.swing.JLabel();
        lblParams2 = new javax.swing.JLabel();
        lblParams3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit API Test");
        setMinimumSize(new java.awt.Dimension(1464, 761));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
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

        lblParams.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblParams.setText("Params");

        lblAuthorization.setBackground(new java.awt.Color(51, 51, 51));
        lblAuthorization.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblAuthorization.setText("Authorization");

        lblHeaders.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblHeaders.setText("Headers");

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

        jScrollPane1.setBackground(new java.awt.Color(51, 51, 51));
        jScrollPane1.setBorder(null);

        jTextArea1.setBackground(new java.awt.Color(51, 51, 51));
        jTextArea1.setColumns(20);
        jTextArea1.setForeground(new java.awt.Color(255, 255, 204));
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jScrollPane2.setBorder(null);
        jScrollPane2.setForeground(new java.awt.Color(51, 51, 51));

        jTextArea2.setBackground(new java.awt.Color(51, 51, 51));
        jTextArea2.setColumns(20);
        jTextArea2.setForeground(new java.awt.Color(255, 204, 204));
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        tableEditTestFlow.setBackground(new java.awt.Color(51, 51, 51));
        tableEditTestFlow.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        tableEditTestFlow.setForeground(new java.awt.Color(255, 255, 255));
        tableEditTestFlow.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Test ID", "Request", "URL", "Headers (key)", "Headers (value)", "Params (key)", "Params (value)", "Payload", "Payload Type", "Modify Payload (key)", "Modify Payload (value)", "Authorization", "", "", "SSL Validation", "Expected Status", "Test Description"
            }
        ));
        tableEditTestFlow.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tableEditTestFlow.setName("tableEditTestFlow"); // NOI18N
        tableEditTestFlow.setRowHeight(30);
        tableEditTestFlow.setRowMargin(2);
        tableEditTestFlow.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableEditTestFlow.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableEditTestFlow.setShowGrid(true);
        tableEditTestFlow.getTableHeader().setReorderingAllowed(false);
        tableEditTestFlow.setUpdateSelectionOnSort(false);
        tableEditTestFlow.setVerifyInputWhenFocusTarget(false);
        tableEditTestFlow.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tableEditTestFlowFocusGained(evt);
            }
        });
        tableEditTestFlow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableEditTestFlowMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableEditTestFlowMouseReleased(evt);
            }
        });
        tableEditTestFlow.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableEditTestFlowKeyReleased(evt);
            }
        });
        scrollPaneTestFlow.setViewportView(tableEditTestFlow);

        javax.swing.GroupLayout pnlCreateTestSuiteLayout = new javax.swing.GroupLayout(pnlCreateTestSuite);
        pnlCreateTestSuite.setLayout(pnlCreateTestSuiteLayout);
        pnlCreateTestSuiteLayout.setHorizontalGroup(
            pnlCreateTestSuiteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCreateTestSuiteLayout.createSequentialGroup()
                .addComponent(scrollPaneTestFlow)
                .addGap(1, 1, 1))
        );
        pnlCreateTestSuiteLayout.setVerticalGroup(
            pnlCreateTestSuiteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCreateTestSuiteLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(scrollPaneTestFlow, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

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

                                dPanelMenu.setLayer(bttnAddNewTestSuite, javax.swing.JLayeredPane.DEFAULT_LAYER);
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
                                        .addGroup(dPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(bttnAddNewTestSuite, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(bttnSaveSuite, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(bttnAddStepDown, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(bttnAddStepUp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(bttnDeleteTestStep, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(bttnAddNewTestStep, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, 0))
                                );
                                dPanelMenuLayout.setVerticalGroup(
                                    dPanelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(dPanelMenuLayout.createSequentialGroup()
                                        .addGap(28, 28, 28)
                                        .addComponent(bttnAddNewTestSuite, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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

                                lblParams1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                                lblParams1.setText("Payload");

                                lblParams2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                                lblParams2.setText("Modify Payload");

                                lblParams3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                                lblParams3.setText("Verify Payload");

                                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                                getContentPane().setLayout(layout);
                                layout.setHorizontalGroup(
                                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lblHeaders)
                                                    .addComponent(scrlPnlHeaders, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(4, 4, 4)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblAuthorization))
                                                    .addComponent(lblParams)
                                                    .addComponent(scrlPnlAuthorization)
                                                    .addComponent(scrlPnlParams))
                                                .addGap(4, 4, 4)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(scrlPnlPayload)
                                                        .addGap(4, 4, 4))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(lblParams1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(lblParams2))
                                                .addGap(4, 4, 4)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lblParams3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(txtRequestType, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel3))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(txtExpStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 1003, Short.MAX_VALUE))
                                                            .addComponent(txtAPIurl)))
                                                    .addComponent(pnlCreateTestSuite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(1, 1, 1)
                                                .addComponent(dPanelMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(1, 1, 1))
                                );
                                layout.setVerticalGroup(
                                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(pnlCreateTestSuite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(dPanelMenu))
                                        .addGap(1, 1, 1)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel2)
                                                .addComponent(jLabel3)))
                                        .addGap(1, 1, 1)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(txtAPIurl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtExpStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtRequestType, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(4, 4, 4)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblHeaders, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(scrlPnlHeaders, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblParams1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(scrlPnlPayload, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblParams, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(scrlPnlParams, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(2, 2, 2)
                                                .addComponent(lblAuthorization, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(scrlPnlAuthorization, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lblParams2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(lblParams3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE))))
                                        .addGap(1, 1, 1))
                                );

                                getAccessibleContext().setAccessibleParent(this);

                                setSize(new java.awt.Dimension(1246, 803));
                                setLocationRelativeTo(null);
                            }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
       tableCellRendererAPI renderer = new tableCellRendererAPI();
       //tableAddORCellRenderer addORrenderer = new tableAddORCellRenderer();
       
        tableEditTestFlow.setDefaultRenderer(Object.class, renderer);
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
        getCurrRowBeforeKeyPressed =tableEditTestFlow.getSelectedRow();
        String getTestId =(String) tableEditTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 0);

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
    	getCurrRowBeforeKeyPressed =tableEditTestFlow.getSelectedRow();
    	try{
            String getTestId =(String) tableEditTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 0);
            
            if(getTestId !=null && !getTestId.isEmpty()){
                String getAuth =(String) tableEditTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 11);
                if(getAuth.contentEquals("Basic Auth")){
                    String getUsername =(String) tableEditTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 12);
                    if(getUsername ==null)
                    	getUsername ="";
                    String getPassword =(String) tableEditTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 13);
                    if(getPassword ==null)
                    	getPassword ="";
                    
                    txtAreaAuthorization.setText("Username: "+getUsername +"\n"+ "Password: "+getPassword);
                    lblAuthorization.setText("Authorization: Basic Auth");
                }else if(getAuth.contentEquals("Bearer Token")){
                    String getToken =(String) tableEditTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 12);
                    if(getToken ==null)
                    	getToken ="";
                    tableEditTestFlow.setValueAt("",getCurrRowBeforeKeyPressed, 13);
                    txtAreaAuthorization.setText("Token: "+getToken);
                    lblAuthorization.setText("Authorization: Bearer Token");
                }else {
                    tableEditTestFlow.setValueAt("", getCurrRowBeforeKeyPressed, 12);
                    tableEditTestFlow.setValueAt("", getCurrRowBeforeKeyPressed, 13);
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
            getTestFlowSelectedRow =tableEditTestFlow.getSelectedRow();
            tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableEditTestFlow, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
            
        if(common.checkForDuplicateTestId(createSuiteTabModel, tableEditTestFlow, editableRow, testIdTxt) ==true)
            return;
        
        Object getPreviosTestStepNo =null;
        if(tableEditTestFlow.getRowCount() ==0)
            getPreviosTestStepNo ="0";
        else
            getPreviosTestStepNo = tableEditTestFlow.getValueAt(tableEditTestFlow.getRowCount()-1, 1);
        
        createSuiteTabModel.addRow(new Object[] {null,null,null,null,null,null});
        tableEditTestFlow.setColumnSelectionInterval(0, 0);
        //tableAddTestFlow.setValueAt(Integer.valueOf(getPreviosTestStepNo.toString())+1, tableAddTestFlow.getRowCount()-1, 1);
        tableEditTestFlow.setRowSelectionInterval(tableEditTestFlow.getRowCount()-1, tableEditTestFlow.getRowCount()-1);
        tableEditTestFlow.scrollRectToVisible(tableEditTestFlow.getCellRect(tableEditTestFlow.getRowCount()-1,0, true));
        tableEditTestFlow.requestFocus();
        getTestFlowSelectedRow =tableEditTestFlow.getSelectedRow();
        
        getCurrRowBeforeKeyPressed =tableEditTestFlow.getSelectedRow();
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
            getTestFlowSelectedRow =tableEditTestFlow.getSelectedRow();
            tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableEditTestFlow, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
        
        if(common.checkForDuplicateTestId(createSuiteTabModel, tableEditTestFlow, editableRow, testIdTxt) ==true)
            return;
        
        String getTestId =null;
            
        if (tableEditTestFlow.getRowCount() > 0) {
            getTestFlowSelectedRow =tableEditTestFlow.getSelectedRow();
            int rowIndex =tableEditTestFlow.getSelectedRow();
            
            try {
                getTestId =tableEditTestFlow.getValueAt(rowIndex, 0).toString();
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
                tableEditTestFlow.setRowSelectionInterval(rowIndex-1, rowIndex-1);
                tableEditTestFlow.setColumnSelectionInterval(0, 0);
                tableEditTestFlow.requestFocus();
            }catch(IllegalArgumentException exp) {
            	rowIndex =tableEditTestFlow.getSelectedRow();
            	if(rowIndex ==-1 && tableEditTestFlow.getRowCount() ==0)
            		{return;}
            	
            	rowIndex =tableEditTestFlow.getSelectedRow();
            	if(rowIndex ==-1)
            		rowIndex =0;
            	else if(tableEditTestFlow.getRowCount() ==-1)
            		return;
            	
            	tableEditTestFlow.setRowSelectionInterval(rowIndex, rowIndex);
                tableEditTestFlow.setColumnSelectionInterval(0, 0);
                tableEditTestFlow.requestFocus();
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
            getTestFlowSelectedRow =tableEditTestFlow.getSelectedRow();
            tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableEditTestFlow, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
        
        if(common.checkForDuplicateTestId(createSuiteTabModel, tableEditTestFlow, editableRow, testIdTxt) ==true)
            return;
        
        int getTestStep =0;
        if(tableEditTestFlow.getRowCount()>0){
            getTestFlowSelectedRow =tableEditTestFlow.getSelectedRow();
            int rowIndex =tableEditTestFlow.getSelectedRow();
            
            if(rowIndex ==0) {
            	try {
                    tableEditTestFlow.setColumnSelectionInterval(rowIndex, 0);
                    return;
            	}catch(IllegalArgumentException exp) {return;}
            }
            	
            if(rowIndex !=-1){
                rowIndex =tableEditTestFlow.getSelectedRow();
                String getTestId = null;
                    
                try{
                    getTestId =tableEditTestFlow.getValueAt(rowIndex, 0).toString();
                }catch(NullPointerException exp){
                    getTestId ="";
                }
                                    
                createSuiteTabModel.insertRow(rowIndex, new Object[] {null,null,null,null,null,null });
                tableEditTestFlow.setRowSelectionInterval(rowIndex, rowIndex);
                tableEditTestFlow.setColumnSelectionInterval(0, 0);
                tableEditTestFlow.scrollRectToVisible(tableEditTestFlow.getCellRect(rowIndex, 0, true));
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
        
        if(common.checkForDuplicateTestId(createSuiteTabModel, tableEditTestFlow, editableRow, testIdTxt) ==true)
            return;
        
        if(tableEditTestFlow.getRowCount()>0){
            getTestFlowSelectedRow =tableEditTestFlow.getSelectedRow();
            int rowIndex = tableEditTestFlow.getSelectedRow();
            if (rowIndex != -1) {
                try {
                    try {
                        //String getTestID = tableAddTestFlow.getValueAt(tableAddTestFlow.getSelectedRow(), 0).toString();
                        createSuiteTabModel.insertRow(rowIndex + 1, new Object[]{null, null, null, null, null, null});
                        rowIndex = tableEditTestFlow.getSelectedRow();
                        tableEditTestFlow.setRowSelectionInterval(rowIndex + 1, rowIndex + 1);
                        tableEditTestFlow.setColumnSelectionInterval(0, 0);
                        tableEditTestFlow.scrollRectToVisible(tableEditTestFlow.getCellRect(rowIndex+1, 0, true));
                    } catch (NullPointerException exp) {
                        createSuiteTabModel.insertRow(rowIndex + 1, new Object[]{null, null, null, null, null, null});
                        rowIndex = tableEditTestFlow.getSelectedRow();
                        tableEditTestFlow.setRowSelectionInterval(rowIndex + 1, rowIndex + 1);
                        tableEditTestFlow.setColumnSelectionInterval(0, 0);
                        tableEditTestFlow.scrollRectToVisible(tableEditTestFlow.getCellRect(rowIndex+1, 0, true));
                    }
                } catch (NullPointerException exp) {
                    createSuiteTabModel.insertRow(rowIndex + 1, new Object[]{null, null, null, null, null, null});
                    rowIndex = tableEditTestFlow.getSelectedRow();
                    tableEditTestFlow.setRowSelectionInterval(rowIndex + 1, rowIndex + 1);
                    tableEditTestFlow.setColumnSelectionInterval(0, 0);
                    tableEditTestFlow.scrollRectToVisible(tableEditTestFlow.getCellRect(rowIndex+1, 0, true));
                }
            } else {
                JOptionPane.showMessageDialog(tableEditTestFlow, "Select row to add test step!", "Alert", JOptionPane.WARNING_MESSAGE);
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
            getTestFlowSelectedRow =tableEditTestFlow.getSelectedRow();
            tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableEditTestFlow, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
        
        if(common.checkForDuplicateTestId(createSuiteTabModel, tableEditTestFlow, editableRow, testIdTxt) ==true)
            return;
        
        if (excelFile != null) {
            try {
                bttnSaveSuite.setEnabled(false);
                if (deleteAllTestSteps(excelFile, 0) == true) {
                    updateTestSuite(excelFile);
                    JOptionPane.showMessageDialog(RegressionSuiteScrollPane, "Test suite " + "\"" + excelFileImport.getName(excelFile) + "\"" + " updated and saved!", "Alert", JOptionPane.WARNING_MESSAGE);
                }
                bttnSaveSuite.setEnabled(true);
            } catch (IOException ex) {
                Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else
            JOptionPane.showMessageDialog(RegressionSuiteScrollPane, "No test suite is available to save!", "Alert", JOptionPane.WARNING_MESSAGE);   
    }//GEN-LAST:event_bttnSaveSuiteActionPerformed
    
    public static void saveTestSuitWhenWindowIsGettingClosed(){
        if (excelFile != null) {
            try {
                bttnSaveSuite.setEnabled(false);
                if (deleteAllTestSteps(excelFile, 0) == true) {
                    updateTestSuite(excelFile);
                    //JOptionPane.showMessageDialog(RegressionSuiteScrollPane, "Test suite " + "\"" + excelFileImport.getName(excelFile) + "\"" + " updated and saved!", "Alert", JOptionPane.WARNING_MESSAGE);
                }
                bttnSaveSuite.setEnabled(true);
            } catch (IOException ex) {
                Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void updateTestSuite(File excelFile) {
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(excelFile);
            XSSFWorkbook wbSuite = new XSSFWorkbook(fis);
            XSSFSheet excelSheetTestFlow = wbSuite.getSheetAt(0);
            XSSFCell cell = null;
            //XSSFDataFormat format = wbSuite.createDataFormat();

            XSSFFont font = wbSuite.createFont();
            //font.setColor(IndexedColors.WHITE.getIndex());
            XSSFCellStyle cellStyle = wbSuite.createCellStyle();
            cellStyle.setFont(font);

            //cellStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
            //cellStyle.setFillPattern(FillPatternType.ALT_BARS);

            for (int j = 0; j < createSuiteTabModel.getRowCount(); j++) {
                XSSFRow row = excelSheetTestFlow.createRow(j + 1);
                for (int k = 0; k < createSuiteTabModel.getColumnCount(); k++) {
                    cell = row.createCell(k);
                    cell.setCellType(CellType.STRING);
                    cell.setCellStyle(cellStyle);
                    try {
                        cell.setCellValue(createSuiteTabModel.getValueAt(j, k).toString());
                    } catch (NullPointerException exp) {
                        cell.setCellValue("");
                    }
                }
            }

            fis.close();
            FileOutputStream outFile = new FileOutputStream(excelFile);
            wbSuite.write(outFile);
            wbSuite.close();
            outFile.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    public static boolean deleteAllTestSteps(File excelFileSuite, int sheetIndex) throws IOException {
        FileInputStream fis;
        boolean deleteSuccessfull = true;
        XSSFWorkbook wbSuite = null;
        FileOutputStream outFile;

        try {
            fis = new FileInputStream(excelFileSuite);
            wbSuite = new XSSFWorkbook(fis);
            XSSFSheet excelSheetTestFlow = wbSuite.getSheetAt(sheetIndex);

            for (int i = 1; i <= excelSheetTestFlow.getLastRowNum(); i++) {
                XSSFRow row = excelSheetTestFlow.getRow(i);
                excelSheetTestFlow.removeRow(row);
            }

            fis.close();
            outFile = new FileOutputStream(excelFileSuite);
            wbSuite.write(outFile);
            wbSuite.close();
            outFile.close();
        } catch (FileNotFoundException ex) {
            deleteSuccessfull = false;

            if (ex.getMessage().contains("The process cannot access the file because it is being used by another process")) {
                int response;

                do {
                    response = JOptionPane.showConfirmDialog(RegressionSuiteScrollPane, //
                            "Close test suite " + "\"" + excelFileImport.getName(excelFile) + "\"" + " to save the changes!", //
                            "Alert", JOptionPane.OK_CANCEL_OPTION, //
                            JOptionPane.WARNING_MESSAGE);

                    if (response == JOptionPane.OK_OPTION) {
                        try {
                            outFile = new FileOutputStream(excelFileSuite);
                            wbSuite.write(outFile);
                            outFile.close();
                            deleteSuccessfull = true;
                            break;
                        } catch (FileNotFoundException ex1) {
                            if (ex.getMessage().contains("The process cannot access the file because it is being used by another process")) {

                            }
                        }
                    }
                } while (response != JOptionPane.CANCEL_OPTION);
            }
        } catch (IOException ex) {
            Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return deleteSuccessfull;
    }
    
    private void bttnAddNewTestSuiteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnAddNewTestSuiteMouseEntered
        bttnAddNewTestSuite.setBackground(new java.awt.Color(250, 128, 114));
        bttnAddNewTestSuite.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_bttnAddNewTestSuiteMouseEntered

    private void bttnAddNewTestSuiteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnAddNewTestSuiteMouseExited
        bttnAddNewTestSuite.setBackground(new java.awt.Color(0,0,0));
        bttnAddNewTestSuite.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_bttnAddNewTestSuiteMouseExited

    private void bttnAddNewTestSuiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnAddNewTestSuiteActionPerformed
        
        String getCurrDir;
        BufferedInputStream excelBIS;
        XSSFRow excelRow;
        
        if(getTestFlowSelectedRow !=-1){
            getTestFlowSelectedRow =tableEditTestFlow.getSelectedRow();
            tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableEditTestFlow, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
        
        try{
            File getCurrDirectory =excelFileImport.getCurrentDirectory();
            getCurrDir =getCurrDirectory.getAbsolutePath();
        }catch(NullPointerException exp){
            getCurrDir =FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
        }
        
        excelFileImport = new JFileChooser(getCurrDir);
        excelFileImport.setFileSelectionMode(JFileChooser.FILES_ONLY);
        excelFileImport.setDialogTitle("Open Test Suite");
        excelFileImport.addChoosableFileFilter(new FileNameExtensionFilter("EXCEL WORKBOOK", "xlsx"));
        excelFileImport.setAcceptAllFileFilterUsed(false);
        
        int excelChooser = excelFileImport.showOpenDialog(this);
        if(excelChooser == JFileChooser.CANCEL_OPTION){
            return;
        }
        
        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            createSuiteTabModel = (DefaultTableModel) tableEditTestFlow.getModel();
            
            testSuiteUploaded = true;
            excelImportWorkBook = new XSSFWorkbook();
            createSuiteTabModel.setRowCount(0);
            excelFile = excelFileImport.getSelectedFile();
            testSuiteFilePath = excelFile.getPath();

            try {
                try {
                    if (excelFIS.getChannel().isOpen());
                        excelFIS.close();
                } catch (NullPointerException exp) {
                    //Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.INFO, "channel is closed", "channel is closed");
                }

                excelFIS = new FileInputStream(excelFile);
                excelBIS = new BufferedInputStream(excelFIS);

                excelImportWorkBook = new XSSFWorkbook(excelBIS);
                excelSheetTestFlow = excelImportWorkBook.getSheetAt(0);

                /*if (AssociateObjORJCheckBox.isSelected() == false) {
                    try { // add local object repositroy to the test suite
                        excelSheetObjectRepository = excelImportWorkBook.getSheetAt(1);
                        excelSheetObjectRepositoryOR = excelImportWorkBook.getSheetAt(1);

                        getObjectListFromObjectRepository(excelSheetObjectRepository);
                        ObjectRepositoryList();
                        try{
                            testObjectRepoColumn.setCellEditor(new DefaultCellEditor(comboBoxObjectRepository));
                        }catch(NullPointerException exp){
                            
                        } 
                    } catch (IllegalArgumentException exp) {
                        if (exp.getMessage().contains("Sheet index (1) is out of range")) {
                            JOptionPane.showMessageDialog(RegressionSuiteScrollPane, "No Object Repository found for the loaded test suite!", "Alert", JOptionPane.WARNING_MESSAGE);
                            testObjectRepoColumn.setCellEditor(null);
                            noRepoFound = true;
                        }
                    }
                } else {
                    //excelSheetObjectRepositoryOR = excelImportWorkBook.getSheetAt(1);
                }*/

                for (int i = 1; i <= excelSheetTestFlow.getLastRowNum(); i++) {
                    excelRow = excelSheetTestFlow.getRow(i);
                    try {
                        //XSSFCell run = excelRow.getCell(0);
                        XSSFCell testId = excelRow.getCell(0);
                        XSSFCell testRequest = excelRow.getCell(1);
                        XSSFCell testURL = excelRow.getCell(2);
                        XSSFCell testHeaderKey = excelRow.getCell(3);
                        XSSFCell testHeaderValue = excelRow.getCell(4);
                        XSSFCell testParamKey = excelRow.getCell(5);
                        XSSFCell testParamValue = excelRow.getCell(6);
                        XSSFCell testPayload = excelRow.getCell(7);
                        XSSFCell testPayloadType = excelRow.getCell(8);
                        XSSFCell testModifyPayloadKey = excelRow.getCell(9);
                        XSSFCell testModifyPayloadValue = excelRow.getCell(10);
                        XSSFCell testAuthorizationType = excelRow.getCell(11);
                        XSSFCell testAuthVal1 = excelRow.getCell(12);
                        XSSFCell testAuthVal2 = excelRow.getCell(13);
                        XSSFCell testSSLValidation = excelRow.getCell(14);
                        XSSFCell testExpStatus = excelRow.getCell(15);
                        XSSFCell testTestDesc = excelRow.getCell(16);

                        createSuiteTabModel.addRow(new Object[]{testId, testRequest, testURL, testHeaderKey, testHeaderValue, testParamKey,
                            testParamValue, testPayload, testPayloadType, testModifyPayloadKey, testModifyPayloadValue, testAuthorizationType,
                            testAuthVal1, testAuthVal2, testSSLValidation, testExpStatus, testTestDesc  
                        });
                    } catch (NullPointerException exp) {

                    }
                }

                tableEditTestFlow.setRowSelectionInterval(0, 0);
                tableEditTestFlow.setColumnSelectionInterval(0, 0);
                tableEditTestFlow.scrollRectToVisible(tableEditTestFlow.getCellRect(0, 0, true));
                tableEditTestFlow.requestFocus();
                
                /*if(objRepo.isVisible())
                    objRepo.dispose();*/
                
                /*if (objRepo.isVisible()) {
                    ObjectRepoFrame.importObjectRepoData.getDataVector().removeAllElements();
                    ObjectRepoFrame.importObjectRepoData.fireTableDataChanged();
                    objRepo.setTitle("Object Repository: " + excelFileImport.getName(excelFile));
                    objRepo.openObjectRepository(excelSheetObjectRepository);
                }*/
                this.setTitle("Edit API Test: " + excelFileImport.getName(excelFile));
            } catch (FileNotFoundException exp) {
                if (exp.getMessage().contains("The system cannot find the file specified")) {
                    JOptionPane.showMessageDialog(RegressionSuiteScrollPane, "No test suite " + "\"" + excelFileImport.getName(excelFile) + "\"" + " found to upload!", "Alert", JOptionPane.WARNING_MESSAGE);
                } else {
                    //exp.printStackTrace();
                }
            } catch (IOException exp) {
                //exp.printStackTrace();
            } catch (IllegalArgumentException exp) {
                if (exp.getMessage().contains("Row index out of range")) {
                    JOptionPane.showMessageDialog(RegressionSuiteScrollPane, "No test steps found in " + "\"" + excelFileImport.getName(excelFile) + "\"" + " test suite to upload!", "Alert", JOptionPane.WARNING_MESSAGE);
                    //excelFile = null;
                }
            }
        }
        
        /*if(fileSaved ==false){
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
        elmXpathCol.setCellEditor(new DefaultCellEditor(elmXpathTxt));*/
        
        //getTestFlowCellEditorStatus =false;
        //getElmRepoCellEditorStatus =false;
    }//GEN-LAST:event_bttnAddNewTestSuiteActionPerformed
        
    private void tableEditTestFlowMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableEditTestFlowMousePressed
        
        //getElmRepoSelectedRow =tableAddOR.getSelectedRow();
        //tabOutFromEditingColumn(getElmRepoCellEditorStatus, tableAddOR, getRepoCellxPoint, getRepoCellyPoint, getElmRepoSelectedRow);
       
        //writeJsonPayloadToTheTextArea();
        
        if(common.checkForDuplicateTestId(createSuiteTabModel, tableEditTestFlow, editableRow, testIdTxt) ==true)
            return;
         
        int getCurRow = tableEditTestFlow.convertRowIndexToModel(tableEditTestFlow.rowAtPoint(evt.getPoint()));
        int gerCurrCol = tableEditTestFlow.convertColumnIndexToModel(tableEditTestFlow.columnAtPoint(evt.getPoint()));
        
        getTestFlowSelectedRow =getCurRow;
        getFlowCellxPoint =tableEditTestFlow.rowAtPoint(evt.getPoint());
        getFlowCellyPoint =tableEditTestFlow.columnAtPoint(evt.getPoint());
        
        if(duplicateTestId ==false){
            switch (gerCurrCol) {
                case 0:
                    tableEditTestFlow.editCellAt(getCurRow, 0);
                    editableRow =tableEditTestFlow.getEditingRow();
                    testIdTxt.requestFocusInWindow();
                    break;
                case 2:
                    tableEditTestFlow.editCellAt(getCurRow, 2);
                    testURLTxt.requestFocusInWindow();
                    break;
                case 3:
                    tableEditTestFlow.editCellAt(getCurRow, 3);
                    //coBoxObjectRepo.requestFocusInWindow();
                    break;    
                case 4:
                    tableEditTestFlow.editCellAt(getCurRow, 4);
                    testURLTxt.requestFocusInWindow();
                    break;
                case 7:
                    tableEditTestFlow.editCellAt(getCurRow, 7);
                    testPayloadTxt.requestFocusInWindow();
                    break;
                case 15:
                    tableEditTestFlow.editCellAt(getCurRow, 15);
                    testExpectedStatusTxt.requestFocusInWindow();
                default:
                    break;
            }
        }
    }//GEN-LAST:event_tableEditTestFlowMousePressed

    private void tableEditTestFlowKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableEditTestFlowKeyReleased
        updateAPIAttributeData();
        common.checkForDuplicateTestId(createSuiteTabModel, tableEditTestFlow, editableRow, testIdTxt);
    }//GEN-LAST:event_tableEditTestFlowKeyReleased
    
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
        tableEditTestFlow.setValueAt(getTheAPIurl,getCurrRowBeforeKeyPressed, 2);
    }//GEN-LAST:event_txtAPIurlFocusLost

    private void txtAPIurlKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAPIurlKeyReleased
        try{
            tableEditTestFlow.setValueAt(txtAPIurl.getText(),getCurrRowBeforeKeyPressed, 2);
        }catch(ArrayIndexOutOfBoundsException exp){}
        
    }//GEN-LAST:event_txtAPIurlKeyReleased

    private void tableEditTestFlowMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableEditTestFlowMouseReleased
      updateAPIAttributeData();
    }//GEN-LAST:event_tableEditTestFlowMouseReleased

    private void txtExpStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtExpStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtExpStatusActionPerformed

    private void txtRequestTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRequestTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRequestTypeActionPerformed

    private void txtAreaPayloadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAreaPayloadKeyReleased
        try{
            tableEditTestFlow.setValueAt(txtAreaPayload.getText(),getCurrRowBeforeKeyPressed, 7);
        }catch(ArrayIndexOutOfBoundsException exp){}
    }//GEN-LAST:event_txtAreaPayloadKeyReleased

    private void txtAreaPayloadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAreaPayloadFocusLost
        getApiPayload =txtAreaPayload.getText();
        try{
            tableEditTestFlow.setValueAt(getApiPayload,getCurrRowBeforeKeyPressed, 7);
        }catch(ArrayIndexOutOfBoundsException exp){}
    }//GEN-LAST:event_txtAreaPayloadFocusLost

    private void tableEditTestFlowFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableEditTestFlowFocusGained
        updateAPIAttributeData();
    }//GEN-LAST:event_tableEditTestFlowFocusGained

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
       saveTestSuitWhenWindowIsGettingClosed();
    }//GEN-LAST:event_formWindowClosing
    
    public static void updateAPIAttributeData(){
        getCurrRowBeforeKeyPressed =tableEditTestFlow.getSelectedRow();
        
        // update api test url
        try{    
            getTheAPIurl =tableEditTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 2).toString();
            txtAPIurl.setText(getTheAPIurl);
            txtAPIurl.setCaretPosition(0);
        }catch(NullPointerException | ArrayIndexOutOfBoundsException exp){
            txtAPIurl.setText("");
            txtAPIurl.setCaretPosition(0);
        }
        
        // update api payload body
        try{
            getApiPayload =tableEditTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 7).toString();
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
            Object getTestId =tableEditTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 0);
            String setHeaders ="";
            
            if(getTestId !=null && !getTestId.toString().isEmpty())
            {
            	int getRowCnt =tableEditTestFlow.getRowCount();
            	int rowStart =getCurrRowBeforeKeyPressed;
            	
            	for(int rowStart1=rowStart; rowStart1<=getRowCnt;rowStart1++) {
            		Object getHeaderName =tableEditTestFlow.getValueAt(rowStart1, 3);
                    if(getHeaderName ==null)
                    	getHeaderName ="";
                    
                    Object getHeaderValue =tableEditTestFlow.getValueAt(rowStart1, 4);
                    if(getHeaderValue ==null)
                    	getHeaderValue ="";
                    
                    if(!getHeaderName.toString().isEmpty() || !getHeaderValue.toString().isEmpty()) {
                    	setHeaders = setHeaders + getHeaderName +": "+ getHeaderValue+"\n";
                    }
                    
                    try {
                    	Object getTestId1 =tableEditTestFlow.getValueAt(rowStart1+1, 0);
                        if(getTestId1 !=null && !getTestId1.toString().isEmpty()) {
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
            Object getTestId =tableEditTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 0);
            String setParams ="";
            
            if(getTestId !=null && !getTestId.toString().isEmpty())
            {
            	int getRowCnt =tableEditTestFlow.getRowCount();
            	int rowStart =getCurrRowBeforeKeyPressed;
            	
            	for(int rowStart1=rowStart; rowStart1<=getRowCnt;rowStart1++) {
            		Object getParamName =tableEditTestFlow.getValueAt(rowStart1, 5);
                    if(getParamName ==null)
                    	getParamName ="";
                    
                    Object getParamValue =tableEditTestFlow.getValueAt(rowStart1, 6);
                    if(getParamValue ==null)
                    	getParamValue ="";
                    
                    if(!getParamName.toString().isEmpty() || !getParamValue.toString().isEmpty()) {
                    	setParams = setParams + getParamName +": "+ getParamValue+"\n";
                    }
                    
                    try {
                    	Object getTestId1 =tableEditTestFlow.getValueAt(rowStart1+1, 0);
                        if(getTestId1 !=null && !getTestId1.toString().isEmpty()) {
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
            Object getTestId =tableEditTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 0);
            if(getTestId !=null && !getTestId.toString().isEmpty())
            {
                Object getAuth =tableEditTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 11);
                if(getAuth.toString().contentEquals("Basic Auth")){
                    Object getUsername =tableEditTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 12);
                    if(getUsername ==null)
                    	getUsername ="";
                    Object getPassword =tableEditTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 13);
                    if(getPassword ==null)
                    	getPassword ="";
                    
                    txtAreaAuthorization.setText("Username: "+getUsername +"\n"+ "Password: "+getPassword);
                    lblAuthorization.setText("Authorization: Basic Auth");
                }else if(getAuth.toString().contentEquals("Bearer Token")){
                    String getToken =(String) tableEditTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 12);
                    if(getToken ==null)
                    	getToken ="";
                    
                    txtAreaAuthorization.setText("Token: "+getToken);
                    lblAuthorization.setText("Authorization: Bearer Token");
                }else {
                	tableEditTestFlow.setValueAt("", getCurrRowBeforeKeyPressed, 12);
                	tableEditTestFlow.setValueAt("", getCurrRowBeforeKeyPressed, 13);
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
           Object getTestId =tableEditTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 0);
           if(getTestId !=null && !getTestId.toString().isEmpty()){
                Object getStatus =tableEditTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 15);
                if(getStatus ==null)
                     getStatus ="";

                txtExpStatus.setText(getStatus.toString());
            }else
                txtExpStatus.setText("");
        }catch(NullPointerException | ArrayIndexOutOfBoundsException exp){}
        
        // update request type
        try{
           Object getTestId =tableEditTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 0);
           if(getTestId !=null && !getTestId.toString().isEmpty()){
                Object getReqType =tableEditTestFlow.getValueAt(getCurrRowBeforeKeyPressed, 1);
                if(getReqType ==null)
                     getReqType ="";
                
                if(getReqType.toString().contentEquals("GET")){
                    txtRequestType.setForeground(Color.green);
                }else if(getReqType.toString().contentEquals("POST")){
                    txtRequestType.setForeground(new java.awt.Color(255,153,0));
                }else if(getReqType.toString().contentEquals("PUT")){
                    txtRequestType.setForeground(new java.awt.Color(153,153,255));
                }else if(getReqType.toString().contentEquals("PATCH")){
                    txtRequestType.setForeground(new java.awt.Color(255,255,255));
                }else if(getReqType.toString().contentEquals("DELETE")){
                    txtRequestType.setForeground(new java.awt.Color(255,102,102));
                }
                
                txtRequestType.setText(getReqType.toString());
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
        tableEditTestFlow.getColumnModel().getColumn(0).setMaxWidth(50);
        tableEditTestFlow.getColumnModel().getColumn(0).setMinWidth(50);
        
        tableEditTestFlow.getColumnModel().getColumn(1).setMaxWidth(72);
        tableEditTestFlow.getColumnModel().getColumn(1).setMinWidth(72);
        
        //tableAddTestFlow.getColumnModel().getColumn(2).setMaxWidth(350);
        tableEditTestFlow.getColumnModel().getColumn(2).setMinWidth(350);
        
        //tableAddTestFlow.getColumnModel().getColumn(3).setMaxWidth(120);
        tableEditTestFlow.getColumnModel().getColumn(3).setMinWidth(120);
        
        //tableAddTestFlow.getColumnModel().getColumn(4).setMaxWidth(120);
        tableEditTestFlow.getColumnModel().getColumn(4).setMinWidth(120);
        
        //tableAddTestFlow.getColumnModel().getColumn(5).setMaxWidth(120);
        tableEditTestFlow.getColumnModel().getColumn(5).setMinWidth(120);
        
        //tableAddTestFlow.getColumnModel().getColumn(6).setMaxWidth(120);
        tableEditTestFlow.getColumnModel().getColumn(6).setMinWidth(120);
        
        //tableAddTestFlow.getColumnModel().getColumn(7).setMaxWidth(300);
        tableEditTestFlow.getColumnModel().getColumn(7).setMinWidth(300);
        
        //tableAddTestFlow.getColumnModel().getColumn(8).setMaxWidth(100);
        tableEditTestFlow.getColumnModel().getColumn(8).setMinWidth(100);
        
        //tableAddTestFlow.getColumnModel().getColumn(9).setMaxWidth(150);
        tableEditTestFlow.getColumnModel().getColumn(9).setMinWidth(150);
        
        //tableAddTestFlow.getColumnModel().getColumn(10).setMaxWidth(150);
        tableEditTestFlow.getColumnModel().getColumn(10).setMinWidth(150);
        
        //tableAddTestFlow.getColumnModel().getColumn(11).setMaxWidth(150);
        tableEditTestFlow.getColumnModel().getColumn(11).setMinWidth(100);
        
        //tableAddTestFlow.getColumnModel().getColumn(12).setMaxWidth(150);
        tableEditTestFlow.getColumnModel().getColumn(12).setMinWidth(150);
        
        //tableAddTestFlow.getColumnModel().getColumn(13).setMaxWidth(150);
        tableEditTestFlow.getColumnModel().getColumn(13).setMinWidth(150);
        
        //tableAddTestFlow.getColumnModel().getColumn(14).setMaxWidth(200);
        tableEditTestFlow.getColumnModel().getColumn(14).setMinWidth(120);
        
        //tableAddTestFlow.getColumnModel().getColumn(15).setMaxWidth(100);
        tableEditTestFlow.getColumnModel().getColumn(15).setMinWidth(100);
        
        //tableAddTestFlow.getColumnModel().getColumn(16).setMaxWidth(200);
        tableEditTestFlow.getColumnModel().getColumn(16).setMinWidth(200);
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
            java.util.logging.Logger.getLogger(EditAPITest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
         //</editor-fold>
         //</editor-fold>
         //</editor-fold>
         //</editor-fold>
         
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new EditAPITest().setVisible(true);
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
    public javax.swing.JTextArea jTextArea2;
    public static javax.swing.JLabel lblAuthorization;
    public javax.swing.JLabel lblHeaders;
    public javax.swing.JLabel lblParams;
    public javax.swing.JLabel lblParams1;
    public javax.swing.JLabel lblParams2;
    public javax.swing.JLabel lblParams3;
    public javax.swing.JPanel pnlCreateTestSuite;
    public static javax.swing.JScrollPane scrlPnlAuthorization;
    public javax.swing.JScrollPane scrlPnlHeaders;
    public javax.swing.JScrollPane scrlPnlParams;
    public static javax.swing.JScrollPane scrlPnlPayload;
    public static javax.swing.JScrollPane scrollPaneTestFlow;
    public static javax.swing.JTable tableEditTestFlow;
    public static javax.swing.JTextField txtAPIurl;
    public static javax.swing.JTextArea txtAreaAuthorization;
    public static javax.swing.JTextArea txtAreaHeaders;
    public static javax.swing.JTextArea txtAreaParams;
    public static javax.swing.JTextArea txtAreaPayload;
    public static javax.swing.JTextField txtExpStatus;
    public static javax.swing.JTextField txtRequestType;
    // End of variables declaration//GEN-END:variables
}