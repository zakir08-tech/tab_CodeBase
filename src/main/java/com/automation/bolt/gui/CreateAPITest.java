/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.bolt.gui;

import static com.automation.bolt.common.tabOutFromAnyEditingColumn;
import static com.automation.bolt.common.tabOutFromEditingColumn;
import static com.automation.bolt.gui.EditRegressionSuite.RegressionSuiteScrollPane;
import static com.automation.bolt.xlsCommonMethods.createAPITestFlowDataSheet;

import java.awt.Color;
import java.awt.IllegalComponentStateException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import java.awt.Dimension;

/**
 *
 * @author zakir
 */
public class CreateAPITest extends javax.swing.JFrame {
    //public AddEnvVariableList addEnvVariableList = new AddEnvVariableList();
    public static DefaultTableModel createSuiteTabModel =new DefaultTableModel();
    //public static DefaultTableModel createORTabModel =new DefaultTableModel();
    
    public static JComboBox<String> cBoxApiRequest =new JComboBox<String>();
    public static JComboBox<String> cBoxApiSSL =new JComboBox<String>();
    public static JComboBox<String> coBoxPayloadType =new JComboBox<String>();
    public static JComboBox<String> coBoxAuth =new JComboBox<String>();
    
    //public static JTextField elmNameTxt =new JTextField();
    //public static JTextField elmIdTxt =new JTextField();
    //public static JTextField elmXpathTxt =new JTextField();
    
    public static JTextField testIdTxt =new JTextField();
    public static JTextField testEnvVarTxt =new JTextField();
    public static JTextField testURLTxt =new JTextField();
    public static JTextField testExpectedStatusTxt =new JTextField();
    public static JTextField testPayloadTxt =new JTextField();
    public static JTextField testComTxt =new JTextField();
    
    //public static TableColumn testFlowColumn =null;
    //public static TableColumn testObjectRepoColumn =null;
    
    //public static boolean duplicateElement =false;
    public static boolean duplicateTestId =false;
    
    public static int editableRow;
    //public static int editableAddElmRow;
    
    //public static TableColumn elmNameCol =null;
    //public static TableColumn elmIdCol =null;
    //public static TableColumn elmXpathCol =null;
    
    public static TableColumn testIdCol =null;
    public static TableColumn testURLCol =null;
    public static TableColumn testExpectedStatusCol =null;
    public static TableColumn testApiTypeCol =null;
    public static TableColumn testApiSSLCol =null;
    public static TableColumn testPayloadCol =null;
    public static TableColumn testPayloadTypeCol =null;
    public static TableColumn testAuthCol =null;
    public static TableColumn testComCol =null;
    public static TableColumn testEnvVarCol =null;
    
    public static boolean getTestFlowCellEditorStatus;
    public static int getTestFlowSelectedRow =0;
    public static int getTestFlowSelectedCol =0;
    
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
    //public static AddEnvVariableList envVarListWin = new AddEnvVariableList();
    //public static boolean envVarListWinCreated =false;
    
    /**
     * Creates new form CreateTestSuite
     */
    public CreateAPITest() {
        
        initComponents();
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        createSuiteTabModel =(DefaultTableModel) tableCreateApiTest.getModel();

        testIdCol =tableCreateApiTest.getColumnModel().getColumn(0);
        testIdCol.setCellEditor(new DefaultCellEditor(testIdTxt));
        
        testEnvVarCol =tableCreateApiTest.getColumnModel().getColumn(12);
        testEnvVarCol.setCellEditor(new DefaultCellEditor(testEnvVarTxt));
        
        testURLCol =tableCreateApiTest.getColumnModel().getColumn(2);
        testURLCol.setCellEditor(new DefaultCellEditor(testURLTxt));
        
        testExpectedStatusCol =tableCreateApiTest.getColumnModel().getColumn(17);
        testExpectedStatusCol.setCellEditor(new DefaultCellEditor(testExpectedStatusTxt));
        
        testPayloadCol =tableCreateApiTest.getColumnModel().getColumn(7);
        testPayloadCol.setCellEditor(new DefaultCellEditor(testPayloadTxt));
        
        testApiTypeCol = tableCreateApiTest.getColumnModel().getColumn(1);
        cBoxApiRequest = new JComboBox<String>();
        apiRequestList(cBoxApiRequest);
        testApiTypeCol.setCellEditor(new DefaultCellEditor(cBoxApiRequest));
        //cBoxApiRequest.setEditable(true);
        
        testApiSSLCol = tableCreateApiTest.getColumnModel().getColumn(16);
        cBoxApiSSL = new JComboBox<String>();
        apiSSLCertList();
        testApiSSLCol.setCellEditor(new DefaultCellEditor(cBoxApiSSL));
        //cBoxApiSSL.setEditable(true);
        
        testPayloadTypeCol = tableCreateApiTest.getColumnModel().getColumn(8);
        coBoxPayloadType = new JComboBox<String>();
        apiPayloadTypeList(coBoxPayloadType);
        testPayloadTypeCol.setCellEditor(new DefaultCellEditor(coBoxPayloadType));
        //coBoxPayloadType.setEditable(true);
        
        testAuthCol = tableCreateApiTest.getColumnModel().getColumn(13);
        coBoxAuth = new JComboBox<String>();
        apiAuthList(coBoxAuth);
        testAuthCol.setCellEditor(new DefaultCellEditor(coBoxAuth));
        //coBoxAuth.setEditable(true);
        
        testIdTxt.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                common.testIdTxtKeyTyped(evt, testIdTxt);
            }
        });
        
        testEnvVarTxt.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                common.testEnvVarTxtKeyTyped(evt, testEnvVarTxt);
            }
        });
        
        tableCreateApiTest.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                int getCol =tableCreateApiTest.getSelectedColumn();
                if(getCol ==0 || getCol==17)
                    common.testIdTxtKeyTyped(evt, null);
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
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtRequestType = new javax.swing.JTextField();
        txtExpStatus = new javax.swing.JTextField();
        txtAPIurl = new javax.swing.JTextField();
        lblURL = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        lblRequest = new javax.swing.JLabel();
        lblPayload = new javax.swing.JLabel();
        lblModifyPayload = new javax.swing.JLabel();
        lblVerifyPayload = new javax.swing.JLabel();
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
        scrollModifyPayload = new javax.swing.JScrollPane();
        txtModifyPayload = new javax.swing.JTextArea();
        scrollVerifyPayload = new javax.swing.JScrollPane();
        textVerifyPayload = new javax.swing.JTextArea();
        pnlCreateApiTest = new javax.swing.JPanel();
        scrollCreateApiTest = new javax.swing.JScrollPane();
        tableCreateApiTest = new javax.swing.JTable();
        lblInvalidBody = new javax.swing.JLabel();
        bttnAddNewTestStep = new javax.swing.JButton();
        bttnDeleteTestStep = new javax.swing.JButton();
        bttnAddStepUp = new javax.swing.JButton();
        bttnAddStepDown = new javax.swing.JButton();
        bttnSaveSuite = new javax.swing.JButton();
        bttnAddNewTestSuite = new javax.swing.JButton();
        bttnEnvVarList = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Create API Test");
        setBackground(new java.awt.Color(153, 102, 255));
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

        txtAPIurl.setBackground(new java.awt.Color(51, 51, 51));
        txtAPIurl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtAPIurl.setForeground(new java.awt.Color(255, 255, 204));
        txtAPIurl.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtAPIurl.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 1));
        txtAPIurl.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtAPIurl.setName("APIurl"); // NOI18N
        txtAPIurl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAPIurlFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAPIurlFocusLost(evt);
            }
        });
        txtAPIurl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtAPIurlMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtAPIurlMouseReleased(evt);
            }
        });
        txtAPIurl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAPIurlKeyReleased(evt);
            }
        });

        lblURL.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblURL.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblURL.setText("URL");

        lblStatus.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblStatus.setText("Status");

        lblRequest.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblRequest.setText("Request");

        lblPayload.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblPayload.setText("Payload");

        lblModifyPayload.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblModifyPayload.setText("Modify Payload");

        lblVerifyPayload.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblVerifyPayload.setText("Verify Payload");

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
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAreaPayloadFocusGained(evt);
            }
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

        scrollModifyPayload.setBackground(new java.awt.Color(51, 51, 51));
        scrollModifyPayload.setBorder(null);

        txtModifyPayload.setEditable(false);
        txtModifyPayload.setBackground(new java.awt.Color(51, 51, 51));
        txtModifyPayload.setColumns(20);
        txtModifyPayload.setForeground(new java.awt.Color(255, 255, 204));
        txtModifyPayload.setLineWrap(true);
        txtModifyPayload.setRows(5);
        scrollModifyPayload.setViewportView(txtModifyPayload);

        scrollVerifyPayload.setBorder(null);
        scrollVerifyPayload.setForeground(new java.awt.Color(51, 51, 51));

        textVerifyPayload.setEditable(false);
        textVerifyPayload.setBackground(new java.awt.Color(51, 51, 51));
        textVerifyPayload.setColumns(20);
        textVerifyPayload.setForeground(new java.awt.Color(255, 204, 204));
        textVerifyPayload.setLineWrap(true);
        textVerifyPayload.setRows(5);
        scrollVerifyPayload.setViewportView(textVerifyPayload);

        pnlCreateApiTest.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                pnlCreateApiTestFocusLost(evt);
            }
        });

        tableCreateApiTest.setBackground(new java.awt.Color(51, 51, 51));
        tableCreateApiTest.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        tableCreateApiTest.setForeground(new java.awt.Color(255, 255, 255));
        tableCreateApiTest.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Test ID", "Request", "URL", "Headers (key)", "Headers (value)", "Params (key)", "Params (value)", "Payload", "Payload Type", "Modify Payload (key)", "Modify Payload (value)", "Response Tag Name (value)", "Capture Tag Value (env var)", "Authorization", "", "", "SSL Validation", "Expected Status", "Verify Payload (key)", "Verfiy Payload (value)", "Test Description"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, true, true, false, true, true, true, true, true, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableCreateApiTest.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tableCreateApiTest.setName("tableCreateApiTest"); // NOI18N
        tableCreateApiTest.setRowHeight(30);
        tableCreateApiTest.setRowMargin(2);
        tableCreateApiTest.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableCreateApiTest.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableCreateApiTest.setShowGrid(true);
        tableCreateApiTest.getTableHeader().setReorderingAllowed(false);
        tableCreateApiTest.setUpdateSelectionOnSort(false);
        tableCreateApiTest.setVerifyInputWhenFocusTarget(false);
        tableCreateApiTest.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tableCreateApiTestFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tableCreateApiTestFocusLost(evt);
            }
        });
        tableCreateApiTest.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tableCreateApiTestMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableCreateApiTestMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableCreateApiTestMouseReleased(evt);
            }
        });
        tableCreateApiTest.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableCreateApiTestKeyReleased(evt);
            }
        });
        scrollCreateApiTest.setViewportView(tableCreateApiTest);

        javax.swing.GroupLayout pnlCreateApiTestLayout = new javax.swing.GroupLayout(pnlCreateApiTest);
        pnlCreateApiTest.setLayout(pnlCreateApiTestLayout);
        pnlCreateApiTestLayout.setHorizontalGroup(
            pnlCreateApiTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCreateApiTestLayout.createSequentialGroup()
                .addComponent(scrollCreateApiTest)
                .addGap(1, 1, 1))
        );
        pnlCreateApiTestLayout.setVerticalGroup(
            pnlCreateApiTestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCreateApiTestLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(scrollCreateApiTest, javax.swing.GroupLayout.DEFAULT_SIZE, 445, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        lblInvalidBody.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblInvalidBody.setForeground(new java.awt.Color(255, 51, 51));

        bttnAddNewTestStep.setBackground(new java.awt.Color(0, 0, 0));
        bttnAddNewTestStep.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        bttnAddNewTestStep.setForeground(new java.awt.Color(255, 255, 255));
        bttnAddNewTestStep.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/ApiAddRow.png"));
            bttnAddNewTestStep.setToolTipText("add blank test step");
            bttnAddNewTestStep.setActionCommand("OpenRegressionSuite");
            bttnAddNewTestStep.setBorder(null);
            bttnAddNewTestStep.setBorderPainted(false);
            bttnAddNewTestStep.setContentAreaFilled(false);
            bttnAddNewTestStep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            bttnAddNewTestStep.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
            bttnAddNewTestStep.setOpaque(true);
            bttnAddNewTestStep.setRequestFocusEnabled(false);
            bttnAddNewTestStep.setRolloverEnabled(false);
            bttnAddNewTestStep.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    bttnAddNewTestStepMouseClicked(evt);
                }
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
            bttnDeleteTestStep.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/ApiDeleteRow.png"));
                bttnDeleteTestStep.setToolTipText("delete selected test step");
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
                bttnAddStepUp.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/ApiAddRowUp.png"));
                    bttnAddStepUp.setToolTipText("add new test step above selected step");
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
                    bttnAddStepDown.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/ApiAddRowDown.png"));
                        bttnAddStepDown.setToolTipText("add new test step below selected step");
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
                        bttnSaveSuite.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/ApiSaveTest.png"));
                            bttnSaveSuite.setToolTipText("save api test suite");
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
                            bttnAddNewTestSuite.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/ApiNewTest.png"));
                                bttnAddNewTestSuite.setToolTipText("add blank test suite");
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

                                bttnEnvVarList.setBackground(new java.awt.Color(0, 0, 0));
                                bttnEnvVarList.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
                                bttnEnvVarList.setForeground(new java.awt.Color(255, 255, 255));
                                bttnEnvVarList.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/ApiEnvVar.png"));
                                    bttnEnvVarList.setToolTipText("open env var list");
                                    bttnEnvVarList.setBorder(null);
                                    bttnEnvVarList.setBorderPainted(false);
                                    bttnEnvVarList.setContentAreaFilled(false);
                                    bttnEnvVarList.setFocusPainted(false);
                                    bttnEnvVarList.setFocusable(false);
                                    bttnEnvVarList.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                                    bttnEnvVarList.setOpaque(true);
                                    bttnEnvVarList.setRequestFocusEnabled(false);
                                    bttnEnvVarList.setRolloverEnabled(false);
                                    bttnEnvVarList.addMouseListener(new java.awt.event.MouseAdapter() {
                                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                                            bttnEnvVarListMouseEntered(evt);
                                        }
                                        public void mouseExited(java.awt.event.MouseEvent evt) {
                                            bttnEnvVarListMouseExited(evt);
                                        }
                                        public void mouseReleased(java.awt.event.MouseEvent evt) {
                                            bttnEnvVarListMouseReleased(evt);
                                        }
                                    });
                                    bttnEnvVarList.addActionListener(new java.awt.event.ActionListener() {
                                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                            bttnEnvVarListActionPerformed(evt);
                                        }
                                    });

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
                                                            .addComponent(lblPayload)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addComponent(lblInvalidBody, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 68, Short.MAX_VALUE)))
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(scrollModifyPayload, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblModifyPayload))
                                                    .addGap(4, 4, 4)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblVerifyPayload, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(scrollVerifyPayload, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(txtRequestType, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(lblRequest))
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(txtExpStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(layout.createSequentialGroup()
                                                                    .addComponent(lblURL, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addGap(0, 0, Short.MAX_VALUE))
                                                                .addComponent(txtAPIurl)))
                                                        .addComponent(pnlCreateApiTest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                    .addGap(0, 0, 0)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(bttnEnvVarList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(bttnAddNewTestSuite, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(bttnSaveSuite, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(bttnAddStepDown, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(bttnAddStepUp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(bttnDeleteTestStep, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(bttnAddNewTestStep, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))))
                                            .addGap(0, 0, 0))
                                    );
                                    layout.setVerticalGroup(
                                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(pnlCreateApiTest, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGap(32, 32, 32)
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
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(bttnEnvVarList, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(0, 0, Short.MAX_VALUE)))
                                            .addGap(1, 1, 1)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(lblStatus)
                                                .addComponent(lblRequest)
                                                .addComponent(lblURL))
                                            .addGap(1, 1, 1)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(txtExpStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtRequestType, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtAPIurl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(4, 4, 4)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(lblHeaders, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(0, 0, 0)
                                                    .addComponent(scrlPnlHeaders, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblInvalidBody, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblPayload, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                                        .addComponent(lblModifyPayload, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblVerifyPayload, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(scrollVerifyPayload, javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(scrollModifyPayload, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE))))
                                            .addGap(1, 1, 1))
                                    );

                                    getAccessibleContext().setAccessibleParent(this);

                                    setSize(new java.awt.Dimension(1246, 803));
                                    setLocationRelativeTo(null);
                                }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
       tableCellRendererAPI renderer = new tableCellRendererAPI();
       //tableAddORCellRenderer addORrenderer = new tableAddORCellRenderer();
       
        tableCreateApiTest.setDefaultRenderer(Object.class, renderer);
       //tableAddOR.setDefaultRenderer(Object.class, addORrenderer);
    }//GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Image titleIcon = Toolkit.getDefaultToolkit().getImage(constants.userDir+"\\icons\\Phantom2.png");
        this.setIconImage(titleIcon);
        setTableColWidthForCreateRegSuiteTable();
        fileSaved =false;
    }//GEN-LAST:event_formWindowOpened

    private void bttnAddNewTestStepMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnAddNewTestStepMouseEntered
        bttnAddNewTestStep.setBackground(new java.awt.Color(203, 67, 53));
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
        getCurrRowBeforeKeyPressed =tableCreateApiTest.getSelectedRow();
        String getTestId =(String) tableCreateApiTest.getValueAt(getCurrRowBeforeKeyPressed, 0);

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
    	getCurrRowBeforeKeyPressed =tableCreateApiTest.getSelectedRow();
    	try{
            Object getTestId =tableCreateApiTest.getValueAt(getCurrRowBeforeKeyPressed, 0);
            
            if(getTestId !=null && !getTestId.toString().isEmpty()){
                Object getAuth =tableCreateApiTest.getValueAt(getCurrRowBeforeKeyPressed, 13);
                if(getAuth.toString().contentEquals("Basic Auth")){
                    Object getUsername =tableCreateApiTest.getValueAt(getCurrRowBeforeKeyPressed, 14);
                    if(getUsername ==null)
                    	getUsername ="";
                    Object getPassword =tableCreateApiTest.getValueAt(getCurrRowBeforeKeyPressed, 15);
                    if(getPassword ==null)
                    	getPassword ="";
                    
                    txtAreaAuthorization.setText("Username: "+getUsername +"\n"+ "Password: "+getPassword);
                    lblAuthorization.setText("Authorization: Basic Auth");
                }else if(getAuth.toString().contentEquals("Bearer Token")){
                    Object getToken =tableCreateApiTest.getValueAt(getCurrRowBeforeKeyPressed, 13);
                    if(getToken ==null)
                    	getToken ="";
                    tableCreateApiTest.setValueAt("",getCurrRowBeforeKeyPressed, 15);
                    txtAreaAuthorization.setText("Token: "+getToken);
                    lblAuthorization.setText("Authorization: Bearer Token");
                }else {
                    tableCreateApiTest.setValueAt("", getCurrRowBeforeKeyPressed, 14);
                    tableCreateApiTest.setValueAt("", getCurrRowBeforeKeyPressed, 15);
                    lblAuthorization.setText("Authorization");
                    txtAreaAuthorization.setText("");
                }
                	
            }else {
                lblAuthorization.setText("Authorization");
            	txtAreaAuthorization.setText("");
            }
        }catch(NullPointerException exp){Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.SEVERE, null, exp);}
    }
     
    private void bttnAddNewTestStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnAddNewTestStepActionPerformed
        if(getElmRepoSelectedRow !=-1){
            //getElmRepoSelectedRow =tableAddOR.getSelectedRow();
            //tabOutFromEditingColumn(getElmRepoCellEditorStatus, tableAddOR, getRepoCellxPoint, getRepoCellyPoint, getElmRepoSelectedRow);
        }
            
        if(getTestFlowSelectedRow !=-1){
            getTestFlowSelectedRow =tableCreateApiTest.getSelectedRow();
            tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableCreateApiTest, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
            
        if(common.checkForDuplicateApiTestId(createSuiteTabModel, tableCreateApiTest, editableRow, testIdTxt) ==true)
            return;
        
        Object getPreviosTestStepNo =null;
        if(tableCreateApiTest.getRowCount() ==0)
            getPreviosTestStepNo ="0";
        else
            getPreviosTestStepNo = tableCreateApiTest.getValueAt(tableCreateApiTest.getRowCount()-1, 1);
        
        createSuiteTabModel.addRow(new Object[] {null,null,null,null,null,null});
        tableCreateApiTest.setColumnSelectionInterval(0, 0);
        //tableAddTestFlow.setValueAt(Integer.valueOf(getPreviosTestStepNo.toString())+1, tableAddTestFlow.getRowCount()-1, 1);
        tableCreateApiTest.setRowSelectionInterval(tableCreateApiTest.getRowCount()-1, tableCreateApiTest.getRowCount()-1);
        tableCreateApiTest.scrollRectToVisible(tableCreateApiTest.getCellRect(tableCreateApiTest.getRowCount()-1,0, true));
        tableCreateApiTest.requestFocus();
        getTestFlowSelectedRow =tableCreateApiTest.getSelectedRow();
        
        getCurrRowBeforeKeyPressed =tableCreateApiTest.getSelectedRow();
        updateAPIAttributeData();
    }//GEN-LAST:event_bttnAddNewTestStepActionPerformed

    private void bttnDeleteTestStepMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnDeleteTestStepMouseEntered
        bttnDeleteTestStep.setBackground(new java.awt.Color(203, 67, 53));
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
            getTestFlowSelectedRow =tableCreateApiTest.getSelectedRow();
            tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableCreateApiTest, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
        
        if(common.checkForDuplicateApiTestId(createSuiteTabModel, tableCreateApiTest, editableRow, testIdTxt) ==true)
            return;
        
        String getTestId =null;
            
        if (tableCreateApiTest.getRowCount() > 0) {
            getTestFlowSelectedRow =tableCreateApiTest.getSelectedRow();
            int rowIndex =tableCreateApiTest.getSelectedRow();
            
            try {
                getTestId =tableCreateApiTest.getValueAt(rowIndex, 0).toString();
            } catch (NullPointerException exp) {
                getTestId ="";
            }
            
            if(!getTestId.isEmpty())
            {
            	JOptionPane.showMessageDialog(null, "Can not delete a step with Test Id!", "Alert", JOptionPane.WARNING_MESSAGE);
            	return;
            }
             
            createSuiteTabModel.removeRow(rowIndex);

            txtRequestType.setText("");
            txtExpStatus.setText("");
            txtAPIurl.setText("");
            txtAreaHeaders.setText("");
            txtAreaParams.setText("");
            txtAreaAuthorization.setText("");
            txtAreaPayload.setText("");
            txtModifyPayload.setText("");
            textVerifyPayload.setText("");
            lblAuthorization.setText("Authorization"); 
            
            try {
                tableCreateApiTest.setRowSelectionInterval(rowIndex-1, rowIndex-1);
                tableCreateApiTest.setColumnSelectionInterval(0, 0);
                tableCreateApiTest.requestFocus();
            }catch(IllegalArgumentException exp) {
            	rowIndex =tableCreateApiTest.getSelectedRow();
            	if(rowIndex ==-1 && tableCreateApiTest.getRowCount() ==0)
            		{return;}
            	
            	rowIndex =tableCreateApiTest.getSelectedRow();
            	if(rowIndex ==-1)
            		rowIndex =0;
            	else if(tableCreateApiTest.getRowCount() ==-1)
            		return;
            	
            	tableCreateApiTest.setRowSelectionInterval(rowIndex, rowIndex);
                tableCreateApiTest.setColumnSelectionInterval(0, 0);
                tableCreateApiTest.requestFocus();
            }
        }
    }//GEN-LAST:event_bttnDeleteTestStepActionPerformed

    private void bttnAddStepUpMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnAddStepUpMouseEntered
        bttnAddStepUp.setBackground(new java.awt.Color(203, 67, 53));
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
            getTestFlowSelectedRow =tableCreateApiTest.getSelectedRow();
            tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableCreateApiTest, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
        
        if(common.checkForDuplicateApiTestId(createSuiteTabModel, tableCreateApiTest, editableRow, testIdTxt) ==true)
            return;
        
        int getTestStep =0;
        if(tableCreateApiTest.getRowCount()>0){
            getTestFlowSelectedRow =tableCreateApiTest.getSelectedRow();
            int rowIndex =tableCreateApiTest.getSelectedRow();
            
            if(rowIndex ==0) {
            	try {
                    tableCreateApiTest.setColumnSelectionInterval(rowIndex, 0);
                    return;
            	}catch(IllegalArgumentException exp) {return;}
            }
            	
            if(rowIndex !=-1){
                rowIndex =tableCreateApiTest.getSelectedRow();
                String getTestId = null;
                    
                try{
                    getTestId =tableCreateApiTest.getValueAt(rowIndex, 0).toString();
                }catch(NullPointerException exp){
                    getTestId ="";
                }
                                    
                createSuiteTabModel.insertRow(rowIndex, new Object[] {null,null,null,null,null,null });
                tableCreateApiTest.setRowSelectionInterval(rowIndex, rowIndex);
                tableCreateApiTest.setColumnSelectionInterval(0, 0);
                tableCreateApiTest.scrollRectToVisible(tableCreateApiTest.getCellRect(rowIndex, 0, true));
            }else
                  JOptionPane.showMessageDialog(scrollCreateApiTest,"Select row to add test step!","Alert",JOptionPane.WARNING_MESSAGE);
        }//else
            //JOptionPane.showMessageDialog(scrollCreateApiTest,"No test step(s) available to add a new step up!","Alert",JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_bttnAddStepUpActionPerformed

    private void bttnAddStepDownMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnAddStepDownMouseEntered
        bttnAddStepDown.setBackground(new java.awt.Color(203, 67, 53));
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
        
        if(common.checkForDuplicateApiTestId(createSuiteTabModel, tableCreateApiTest, editableRow, testIdTxt) ==true)
            return;
        
        if(tableCreateApiTest.getRowCount()>0){
            getTestFlowSelectedRow =tableCreateApiTest.getSelectedRow();
            int rowIndex = tableCreateApiTest.getSelectedRow();
            if (rowIndex != -1) {
                try {
                    try {
                        //String getTestID = tableAddTestFlow.getValueAt(tableAddTestFlow.getSelectedRow(), 0).toString();
                        createSuiteTabModel.insertRow(rowIndex + 1, new Object[]{null, null, null, null, null, null});
                        rowIndex = tableCreateApiTest.getSelectedRow();
                        tableCreateApiTest.setRowSelectionInterval(rowIndex + 1, rowIndex + 1);
                        tableCreateApiTest.setColumnSelectionInterval(0, 0);
                        tableCreateApiTest.scrollRectToVisible(tableCreateApiTest.getCellRect(rowIndex+1, 0, true));
                    } catch (NullPointerException exp) {
                        createSuiteTabModel.insertRow(rowIndex + 1, new Object[]{null, null, null, null, null, null});
                        rowIndex = tableCreateApiTest.getSelectedRow();
                        tableCreateApiTest.setRowSelectionInterval(rowIndex + 1, rowIndex + 1);
                        tableCreateApiTest.setColumnSelectionInterval(0, 0);
                        tableCreateApiTest.scrollRectToVisible(tableCreateApiTest.getCellRect(rowIndex+1, 0, true));
                    }
                } catch (NullPointerException exp) {
                    createSuiteTabModel.insertRow(rowIndex + 1, new Object[]{null, null, null, null, null, null});
                    rowIndex = tableCreateApiTest.getSelectedRow();
                    tableCreateApiTest.setRowSelectionInterval(rowIndex + 1, rowIndex + 1);
                    tableCreateApiTest.setColumnSelectionInterval(0, 0);
                    tableCreateApiTest.scrollRectToVisible(tableCreateApiTest.getCellRect(rowIndex+1, 0, true));
                }
            } else {
                JOptionPane.showMessageDialog(tableCreateApiTest, "Select row to add test step!", "Alert", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }//else
            //JOptionPane.showMessageDialog(scrollCreateApiTest,"No test step(s) available to add a new step down!","Alert",JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_bttnAddStepDownActionPerformed

    private void bttnSaveSuiteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnSaveSuiteMouseEntered
        bttnSaveSuite.setBackground(new java.awt.Color(203, 67, 53));
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
            getTestFlowSelectedRow =tableCreateApiTest.getSelectedRow();
            tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableCreateApiTest, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
        
        if(common.checkForDuplicateApiTestId(createSuiteTabModel, tableCreateApiTest, editableRow, testIdTxt) ==true)
            return;
                
        if(tableCreateApiTest.getRowCount() > 0){
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
            excelFileExport.setPreferredSize(new Dimension(450,300));
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
                
                JOptionPane.showMessageDialog(scrollCreateApiTest, "Test suite " + "\"" + excelFileExport.getSelectedFile().getName() + "\"" + " saved successfully!", "Alert", JOptionPane.INFORMATION_MESSAGE);
            } catch (FileNotFoundException ex) {
                //Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.SEVERE, null, ex);
                if (ex.getMessage().contains("The process cannot access the file because it is being used by another process")) {
                    int response;

                    do {
                        response = JOptionPane.showConfirmDialog(scrollCreateApiTest, //
                                "Close test suite " + "\"" + excelFileExport.getSelectedFile().getName() + "\"" + " to save the changes!", //
                                "Alert", JOptionPane.OK_CANCEL_OPTION, //
                                JOptionPane.WARNING_MESSAGE);

                        if (response == JOptionPane.OK_OPTION) {
                            try {
                                excelFos = new FileOutputStream(excelFileExport.getSelectedFile());
                                excelJTableExport.write(excelFos);

                                excelFos.close();
                                excelJTableExport.close();

                                JOptionPane.showMessageDialog(scrollCreateApiTest, "Test suite " + "\"" + excelFileExport.getSelectedFile().getName() + "\"" + " saved successfully!", "Alert", JOptionPane.INFORMATION_MESSAGE);
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
            
        }//else
            //JOptionPane.showMessageDialog(null,"No test suite is available to save!","Alert",JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_bttnSaveSuiteActionPerformed
    
    public static void saveTestFileWhileClosingTheWindow() {
        if(tableCreateApiTest.getRowCount() > 0){
            String tmpdir = System.getProperty("java.io.tmpdir");
            Date dNow = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmMs");
            String datetime = ft.format(dNow);
    
            FileOutputStream excelFos;
            XSSFWorkbook excelJTableExport = new XSSFWorkbook();
            excelJTableExport =createAPITestFlowDataSheet(excelJTableExport, createSuiteTabModel);
            
            try {
                excelFos = new FileOutputStream(tmpdir +"/apiTestSuite"+ datetime +".xlsx");
                excelJTableExport.write(excelFos);

                excelFos.close();
                excelJTableExport.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CreateAPITest.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(CreateAPITest.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    private void bttnAddNewTestSuiteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnAddNewTestSuiteMouseEntered
        bttnAddNewTestSuite.setBackground(new java.awt.Color(203, 67, 53));
        bttnAddNewTestSuite.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_bttnAddNewTestSuiteMouseEntered

    private void bttnAddNewTestSuiteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnAddNewTestSuiteMouseExited
        bttnAddNewTestSuite.setBackground(new java.awt.Color(0,0,0));
        bttnAddNewTestSuite.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_bttnAddNewTestSuiteMouseExited

    private void bttnAddNewTestSuiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnAddNewTestSuiteActionPerformed
        if(tableCreateApiTest.getRowCount() ==0)
            return;
        
        if(getTestFlowSelectedRow !=-1){
            getTestFlowSelectedRow =tableCreateApiTest.getSelectedRow();
            tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableCreateApiTest, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
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
        
        DefaultTableModel modelAddTestFlow = (DefaultTableModel)tableCreateApiTest.getModel();
        modelAddTestFlow.getDataVector().removeAllElements();
        modelAddTestFlow.fireTableDataChanged();
       
        editableRow =0;
        //editableAddElmRow =0;
        
        //tableAddOR.removeEditor();
        tableCreateApiTest.removeEditor();
        
        createSuiteTabModel =(DefaultTableModel) tableCreateApiTest.getModel();
        //createORTabModel =(DefaultTableModel) tableAddOR.getModel();
        
        /*testURLTxt =new JTextField();      
        testURLCol =tableCreateApiTest.getColumnModel().getColumn(2);
        testURLCol.setCellEditor(new DefaultCellEditor(testURLTxt));
        
        testExpectedStatusTxt =new JTextField();
        testExpectedStatusCol =tableCreateApiTest.getColumnModel().getColumn(15);
        testExpectedStatusCol.setCellEditor(new DefaultCellEditor(testExpectedStatusTxt));
        
        testPayloadTxt =new JTextField();
        testPayloadCol =tableCreateApiTest.getColumnModel().getColumn(7);
        testPayloadCol.setCellEditor(new DefaultCellEditor(testPayloadTxt));*/
         
        //getTestFlowCellEditorStatus =false;
        //getElmRepoCellEditorStatus =false;
    }//GEN-LAST:event_bttnAddNewTestSuiteActionPerformed
        
    private void tableCreateApiTestMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCreateApiTestMousePressed
        
        //getElmRepoSelectedRow =tableAddOR.getSelectedRow();
        //tabOutFromEditingColumn(getElmRepoCellEditorStatus, tableAddOR, getRepoCellxPoint, getRepoCellyPoint, getElmRepoSelectedRow);
       
        //writeJsonPayloadToTheTextArea();
        
        if(common.checkForDuplicateApiTestId(createSuiteTabModel, tableCreateApiTest, editableRow, testIdTxt) ==true)
            return;
         
        int getCurRow = tableCreateApiTest.convertRowIndexToModel(tableCreateApiTest.rowAtPoint(evt.getPoint()));
        int gerCurrCol = tableCreateApiTest.convertColumnIndexToModel(tableCreateApiTest.columnAtPoint(evt.getPoint()));
        
        getTestFlowSelectedRow =getCurRow;
        getTestFlowSelectedCol =gerCurrCol;
        
        getFlowCellxPoint =tableCreateApiTest.rowAtPoint(evt.getPoint());
        getFlowCellyPoint =tableCreateApiTest.columnAtPoint(evt.getPoint());
        
        if(duplicateTestId ==false){
            switch (gerCurrCol) {
                case 12:
                    tableCreateApiTest.editCellAt(getCurRow, 12);
                    editableRow =tableCreateApiTest.getEditingRow();
                    testEnvVarTxt.requestFocusInWindow();
                    testEnvVarTxt.setCaretPosition(0);
                    break;
                case 0:
                    tableCreateApiTest.editCellAt(getCurRow, 0);
                    editableRow =tableCreateApiTest.getEditingRow();
                    testIdTxt.requestFocusInWindow();
                    testIdTxt.setCaretPosition(0);
                    break;
                case 1:
                    cBoxApiRequest.setFocusable(true);
                    cBoxApiRequest.showPopup();
                    break;
                case 2:
                    tableCreateApiTest.editCellAt(getCurRow, 2);
                    testURLTxt.requestFocusInWindow();
                    testURLTxt.setCaretPosition(0);
                    break;
                case 7:
                    tableCreateApiTest.editCellAt(getCurRow, 7);
                    testPayloadTxt.requestFocusInWindow();
                    testPayloadTxt.setCaretPosition(0);
                    break;
                case 8:
                    coBoxPayloadType.setFocusable(true);
                    coBoxPayloadType.showPopup();
                    break;
                case 13:
                    coBoxAuth.setFocusable(true);
                    coBoxAuth.showPopup();
                    break;
                case 16:
                	apiSSLCertList();    
                    try{
                    	tableCreateApiTest.editCellAt(getCurRow, 16);
                    	cBoxApiSSL.setFocusable(true);
                        cBoxApiSSL.showPopup();
                    }catch(IllegalComponentStateException exp){
                    	System.err.println(exp.getMessage());}
                    break;
                default:
                    testComCol =tableCreateApiTest.getColumnModel().getColumn(gerCurrCol);
                    testComCol.setCellEditor(new DefaultCellEditor(testComTxt));
                    
                    tableCreateApiTest.editCellAt(getCurRow, gerCurrCol);
                    testComTxt.requestFocusInWindow();
                    testComTxt.setCaretPosition(0);
            }
        }
    }//GEN-LAST:event_tableCreateApiTestMousePressed

    private void tableCreateApiTestKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableCreateApiTestKeyReleased
        updateAPIAttributeData();
        common.checkForDuplicateApiTestId(createSuiteTabModel, tableCreateApiTest, editableRow, testIdTxt);
    }//GEN-LAST:event_tableCreateApiTestKeyReleased
    
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
        try {
        	tableCreateApiTest.setValueAt(getTheAPIurl,getCurrRowBeforeKeyPressed, 2);
        }catch(ArrayIndexOutOfBoundsException exp) {}
    }//GEN-LAST:event_txtAPIurlFocusLost

    private void txtAPIurlKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAPIurlKeyReleased
        try{
            tableCreateApiTest.setValueAt(txtAPIurl.getText(),getCurrRowBeforeKeyPressed, 2);
        }catch(ArrayIndexOutOfBoundsException exp){}
        
    }//GEN-LAST:event_txtAPIurlKeyReleased

    private void tableCreateApiTestMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCreateApiTestMouseReleased
      updateAPIAttributeData();
    }//GEN-LAST:event_tableCreateApiTestMouseReleased

    private void txtExpStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtExpStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtExpStatusActionPerformed

    private void txtRequestTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRequestTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRequestTypeActionPerformed

    private void txtAreaPayloadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAreaPayloadKeyReleased
        try{
            tableCreateApiTest.setValueAt(txtAreaPayload.getText(),getCurrRowBeforeKeyPressed, 7);
        }catch(ArrayIndexOutOfBoundsException exp){}
    }//GEN-LAST:event_txtAreaPayloadKeyReleased

    private void txtAreaPayloadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAreaPayloadFocusLost
        getApiPayload =txtAreaPayload.getText();
        try{
            tableCreateApiTest.setValueAt(getApiPayload,getCurrRowBeforeKeyPressed, 7);
        }catch(ArrayIndexOutOfBoundsException exp){}
    }//GEN-LAST:event_txtAreaPayloadFocusLost

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        saveTestFileWhileClosingTheWindow();
    }//GEN-LAST:event_formWindowClosing

    private void tableCreateApiTestFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableCreateApiTestFocusLost

    }//GEN-LAST:event_tableCreateApiTestFocusLost

    private void pnlCreateApiTestFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pnlCreateApiTestFocusLost

    }//GEN-LAST:event_pnlCreateApiTestFocusLost

    private void txtAPIurlMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAPIurlMouseClicked

    }//GEN-LAST:event_txtAPIurlMouseClicked

    private void txtAPIurlFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAPIurlFocusGained
        if(getTestFlowSelectedRow !=-1){
            getTestFlowSelectedRow =tableCreateApiTest.getSelectedRow();
            tabOutFromAnyEditingColumn(getTestFlowCellEditorStatus, tableCreateApiTest, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
    }//GEN-LAST:event_txtAPIurlFocusGained

    private void txtAreaPayloadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAreaPayloadFocusGained
        if(getTestFlowSelectedRow !=-1){
            getTestFlowSelectedRow =tableCreateApiTest.getSelectedRow();
            tabOutFromAnyEditingColumn(getTestFlowCellEditorStatus, tableCreateApiTest, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
    }//GEN-LAST:event_txtAreaPayloadFocusGained

    private void txtAPIurlMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAPIurlMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAPIurlMouseReleased

    private void tableCreateApiTestFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableCreateApiTestFocusGained

    }//GEN-LAST:event_tableCreateApiTestFocusGained

    private void bttnAddNewTestStepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnAddNewTestStepMouseClicked

    }//GEN-LAST:event_bttnAddNewTestStepMouseClicked

    private void tableCreateApiTestMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCreateApiTestMouseExited
        
    }//GEN-LAST:event_tableCreateApiTestMouseExited

    private void bttnEnvVarListMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnEnvVarListMouseEntered
        bttnEnvVarList.setBackground(new java.awt.Color(203, 67, 53));
        bttnEnvVarList.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_bttnEnvVarListMouseEntered

    private void bttnEnvVarListMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnEnvVarListMouseExited
        bttnEnvVarList.setBackground(new java.awt.Color(0,0,0));
        bttnEnvVarList.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_bttnEnvVarListMouseExited

    private void bttnEnvVarListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnEnvVarListMouseReleased
        EditAPITest envVarListWin = new EditAPITest();
                
        EditAPITest.envVarListWin.setLocationRelativeTo(null);
        EditAPITest.envVarListWin.setVisible(true);
    }//GEN-LAST:event_bttnEnvVarListMouseReleased

    private void bttnEnvVarListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnEnvVarListActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bttnEnvVarListActionPerformed
    
    public static void updateAPIAttributeData(){
        getCurrRowBeforeKeyPressed =tableCreateApiTest.getSelectedRow();
        Object getTestId =null;
        
        try{
            getTestId =tableCreateApiTest.getValueAt(getCurrRowBeforeKeyPressed, 0);
        }catch(ArrayIndexOutOfBoundsException exp){}
        
        if(getTestId ==null || getTestId.toString().isEmpty()){
            txtRequestType.setText("");
            txtExpStatus.setText("");
            txtAPIurl.setText("");
            txtAreaHeaders.setText("");
            txtAreaParams.setText("");
            txtAreaAuthorization.setText("");
            txtAreaPayload.setText("");
            txtModifyPayload.setText("");
            textVerifyPayload.setText("");
            lblAuthorization.setText("Authorization"); 
            lblInvalidBody.setText("");
            return;
        }
        
        // update api test url
        try{    
            getTheAPIurl =tableCreateApiTest.getValueAt(getCurrRowBeforeKeyPressed, 2).toString();
            txtAPIurl.setText(getTheAPIurl);
            txtAPIurl.setCaretPosition(0);
        }catch(NullPointerException | ArrayIndexOutOfBoundsException exp){
            txtAPIurl.setText("");
            txtAPIurl.setCaretPosition(0);
        }
        
        // update api payload body
        try{
            getApiPayload =tableCreateApiTest.getValueAt(getCurrRowBeforeKeyPressed, 7).toString();
            if(getApiPayload !=null){
                Object getPayloadType =tableCreateApiTest.getValueAt(getCurrRowBeforeKeyPressed, 8);
                if(getPayloadType ==null || 
                        getPayloadType.toString().contentEquals("JSON") || 
                        getPayloadType.toString().isEmpty()){
                    
                    String getJsonBody =common.writeJsonPayloadToTheTextArea(getApiPayload);
                    if(!getJsonBody.contentEquals("{invalid json body}")){
                        lblInvalidBody.setText("");
                        txtAreaPayload.setText(common.writeJsonPayloadToTheTextArea(getApiPayload));
                    }
                    else{
                       lblInvalidBody.setText(getJsonBody);
                       txtAreaPayload.setText(getApiPayload); 
                    } 
                }else{
                    lblInvalidBody.setText("");
                    txtAreaPayload.setText(getApiPayload);
                }
                   
                txtAreaPayload.setCaretPosition(0);
            }
        }catch(NullPointerException | ArrayIndexOutOfBoundsException exp){
            lblInvalidBody.setText("");
            txtAreaPayload.setText("");
            txtAreaPayload.setCaretPosition(0);
        }
        
        // update api header list
        try{
            String setHeaders ="";
            int getRowCnt =tableCreateApiTest.getRowCount();
            int rowStart =getCurrRowBeforeKeyPressed;

            for(int rowStart1=rowStart; rowStart1<=getRowCnt;rowStart1++) {
                Object getHeaderName =tableCreateApiTest.getValueAt(rowStart1, 3);
                if(getHeaderName ==null)
                    getHeaderName ="";

                Object getHeaderValue =tableCreateApiTest.getValueAt(rowStart1, 4);
                if(getHeaderValue ==null)
                    getHeaderValue ="";

                if(!getHeaderName.toString().isEmpty() || !getHeaderValue.toString().isEmpty()) {
                    setHeaders = setHeaders + getHeaderName +": "+ getHeaderValue+"\n";
                }

                try {
                    Object getTestId1 =tableCreateApiTest.getValueAt(rowStart1+1, 0);
                    if(getTestId1 !=null && !getTestId1.toString().isEmpty()) {
                            txtAreaHeaders.setText(setHeaders);
                            break;
                    }
                }catch(ArrayIndexOutOfBoundsException exp) {txtAreaHeaders.setText(setHeaders);break;}
            }
        }catch(NullPointerException | ArrayIndexOutOfBoundsException exp){}
        
        // update api param list
        try{
            String setParams ="";
            int getRowCnt =tableCreateApiTest.getRowCount();
            int rowStart =getCurrRowBeforeKeyPressed;

            for(int rowStart1=rowStart; rowStart1<=getRowCnt;rowStart1++) {
                    Object getParamName =tableCreateApiTest.getValueAt(rowStart1, 5);
                if(getParamName ==null)
                    getParamName ="";

                Object getParamValue =tableCreateApiTest.getValueAt(rowStart1, 6);
                if(getParamValue ==null)
                    getParamValue ="";

                if(!getParamName.toString().isEmpty() || !getParamValue.toString().isEmpty()) {
                    setParams = setParams + getParamName +": "+ getParamValue+"\n";
                }

                try {
                    Object getTestId1 =tableCreateApiTest.getValueAt(rowStart1+1, 0);
                    if(getTestId1 !=null && !getTestId1.toString().isEmpty()) {
                            txtAreaParams.setText(setParams);
                            break;
                    }
                }catch(ArrayIndexOutOfBoundsException exp) {txtAreaParams.setText(setParams);break;}
            }
        }catch(NullPointerException | ArrayIndexOutOfBoundsException exp){}
        
        // update authentication
        try{
            Object getAuth =tableCreateApiTest.getValueAt(getCurrRowBeforeKeyPressed, 13);
            if(getAuth.toString().contentEquals("Basic Auth")){
                Object getUsername =tableCreateApiTest.getValueAt(getCurrRowBeforeKeyPressed, 14);
                if(getUsername ==null)
                    getUsername ="";
                Object getPassword =tableCreateApiTest.getValueAt(getCurrRowBeforeKeyPressed, 15);
                if(getPassword ==null)
                    getPassword ="";

                txtAreaAuthorization.setText("Username: "+getUsername +"\n"+ "Password: "+getPassword);
                lblAuthorization.setText("Authorization: Basic Auth");
            }else if(getAuth.toString().contentEquals("Bearer Token")){
                Object getToken =tableCreateApiTest.getValueAt(getCurrRowBeforeKeyPressed, 14);
                if(getToken ==null)
                    getToken ="";

                txtAreaAuthorization.setText("Token: "+getToken);
                lblAuthorization.setText("Authorization: Bearer Token");
            }else {
                    tableCreateApiTest.setValueAt("", getCurrRowBeforeKeyPressed, 14);
                    tableCreateApiTest.setValueAt("", getCurrRowBeforeKeyPressed, 15);
                    lblAuthorization.setText("Authorization");
                    txtAreaAuthorization.setText("");
            }
        }catch(NullPointerException | ArrayIndexOutOfBoundsException exp){/*Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.SEVERE, null, exp);*/}
        
        // update expected status
        try{
            Object getStatus =tableCreateApiTest.getValueAt(getCurrRowBeforeKeyPressed, 17);
            if(getStatus ==null)
                 getStatus ="";

            txtExpStatus.setText(getStatus.toString());
        }catch(NullPointerException | ArrayIndexOutOfBoundsException exp){}
        
        // update request type
        try{
            String getReqType =(String) tableCreateApiTest.getValueAt(getCurrRowBeforeKeyPressed, 1);
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
        }catch(NullPointerException | ArrayIndexOutOfBoundsException exp){}
        
        // update update payload list
        try{
            String setModifyPayload ="";
            int getRowCnt =tableCreateApiTest.getRowCount();
            int rowStart =getCurrRowBeforeKeyPressed;

            for(int rowStart1=rowStart; rowStart1<=getRowCnt;rowStart1++) {
                    Object getPayloadKey =tableCreateApiTest.getValueAt(rowStart1, 9);
                if(getPayloadKey ==null)
                    getPayloadKey ="";

                Object getPayloadVal =tableCreateApiTest.getValueAt(rowStart1, 10);
                if(getPayloadVal ==null)
                    getPayloadVal ="";

                if(!getPayloadKey.toString().isEmpty() || !getPayloadVal.toString().isEmpty()) {
                    setModifyPayload = setModifyPayload + getPayloadKey +": "+ getPayloadVal+"\n";
                }

                try {
                    Object getTestId1 =tableCreateApiTest.getValueAt(rowStart1+1, 0);
                    if(getTestId1 !=null && !getTestId1.toString().isEmpty()) {
                            txtModifyPayload.setText(setModifyPayload);
                            break;
                    }
                }catch(ArrayIndexOutOfBoundsException exp) {txtModifyPayload.setText(setModifyPayload);break;}
            }
        }catch(NullPointerException | ArrayIndexOutOfBoundsException exp){}
        
        // update verify payload list
        try{
            String setVerfiyPayload ="";
            int getRowCnt =tableCreateApiTest.getRowCount();
            int rowStart =getCurrRowBeforeKeyPressed;

            for(int rowStart1=rowStart; rowStart1<=getRowCnt;rowStart1++) {
                    Object getPayloadVerifyKey =tableCreateApiTest.getValueAt(rowStart1, 18);
                if(getPayloadVerifyKey ==null)
                    getPayloadVerifyKey ="";

                Object getPayloadVerifyVal =tableCreateApiTest.getValueAt(rowStart1, 19);
                if(getPayloadVerifyVal ==null)
                    getPayloadVerifyVal ="";

                if(!getPayloadVerifyKey.toString().isEmpty() || !getPayloadVerifyVal.toString().isEmpty()) {
                    setVerfiyPayload = setVerfiyPayload + getPayloadVerifyKey +": "+ getPayloadVerifyVal+"\n";
                }

                try {
                    Object getTestId1 =tableCreateApiTest.getValueAt(rowStart1+1, 0);
                    if(getTestId1 !=null && !getTestId1.toString().isEmpty()) {
                            textVerifyPayload.setText(setVerfiyPayload);
                            break;
                    }
                }catch(ArrayIndexOutOfBoundsException exp) {textVerifyPayload.setText(setVerfiyPayload);break;}
            }
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
    
    public static void apiSSLCertList() {
        HashMap<Integer, Object> jsonMap =common.uploadSSLCertConfiguration();
        cBoxApiSSL = new JComboBox<String>();
        cBoxApiSSL.addItem("");
        
        for (Map.Entry<Integer,Object> entry : jsonMap.entrySet()){
            try{
                String getCertName =entry.getValue().toString().split("[,]")[0];
                if(checkSSLItemExist(cBoxApiSSL, getCertName) ==false) {
                	cBoxApiSSL.addItem(getCertName);
                }
                    
            }catch(ArrayIndexOutOfBoundsException exp){} 
        }
        
        testApiSSLCol.setCellEditor(new DefaultCellEditor(cBoxApiSSL));
    }
    
    public static boolean checkSSLItemExist(JComboBox<String> cBoxTestFlow, String item){        
        boolean itemExist =false;
        
        for(int c = 0; c<cBoxTestFlow.getItemCount(); ++c){
            if(cBoxTestFlow.getItemAt(c).contentEquals(item)){
                itemExist =true;
                break;
            }
        }
        
        return itemExist; 
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
        tableCreateApiTest.getColumnModel().getColumn(0).setMaxWidth(50);
        tableCreateApiTest.getColumnModel().getColumn(0).setMinWidth(50);
        
        tableCreateApiTest.getColumnModel().getColumn(1).setMaxWidth(72);
        tableCreateApiTest.getColumnModel().getColumn(1).setMinWidth(72);
        
        //tableAddTestFlow.getColumnModel().getColumn(2).setMaxWidth(350);
        tableCreateApiTest.getColumnModel().getColumn(2).setMinWidth(350);
        
        //tableAddTestFlow.getColumnModel().getColumn(3).setMaxWidth(120);
        tableCreateApiTest.getColumnModel().getColumn(3).setMinWidth(120);
        
        //tableAddTestFlow.getColumnModel().getColumn(4).setMaxWidth(120);
        tableCreateApiTest.getColumnModel().getColumn(4).setMinWidth(120);
        
        //tableAddTestFlow.getColumnModel().getColumn(5).setMaxWidth(120);
        tableCreateApiTest.getColumnModel().getColumn(5).setMinWidth(120);
        
        //tableAddTestFlow.getColumnModel().getColumn(6).setMaxWidth(120);
        tableCreateApiTest.getColumnModel().getColumn(6).setMinWidth(120);
        
        //tableAddTestFlow.getColumnModel().getColumn(7).setMaxWidth(300);
        tableCreateApiTest.getColumnModel().getColumn(7).setMinWidth(300);
        
        //tableAddTestFlow.getColumnModel().getColumn(8).setMaxWidth(100);
        tableCreateApiTest.getColumnModel().getColumn(8).setMinWidth(100);
        
        //tableAddTestFlow.getColumnModel().getColumn(9).setMaxWidth(150);
        tableCreateApiTest.getColumnModel().getColumn(9).setMinWidth(150);
        
        //tableAddTestFlow.getColumnModel().getColumn(10).setMaxWidth(150);
        tableCreateApiTest.getColumnModel().getColumn(10).setMinWidth(150);
        
        //tableAddTestFlow.getColumnModel().getColumn(11).setMaxWidth(150);
        tableCreateApiTest.getColumnModel().getColumn(11).setMinWidth(165);
        
        //tableAddTestFlow.getColumnModel().getColumn(12).setMaxWidth(150);
        tableCreateApiTest.getColumnModel().getColumn(12).setMinWidth(165);
        
        //tableAddTestFlow.getColumnModel().getColumn(13).setMaxWidth(150);
        tableCreateApiTest.getColumnModel().getColumn(13).setMinWidth(100);
        
        //tableAddTestFlow.getColumnModel().getColumn(14).setMaxWidth(150);
        tableCreateApiTest.getColumnModel().getColumn(14).setMinWidth(150);
        
        
        //tableAddTestFlow.getColumnModel().getColumn(15).setMaxWidth(150);
        tableCreateApiTest.getColumnModel().getColumn(15).setMinWidth(150);
        
        //tableAddTestFlow.getColumnModel().getColumn(16).setMaxWidth(200);
        tableCreateApiTest.getColumnModel().getColumn(16).setMinWidth(120);
        
        //tableAddTestFlow.getColumnModel().getColumn(17).setMaxWidth(100);
        tableCreateApiTest.getColumnModel().getColumn(17).setMinWidth(100);
        
        //tableAddTestFlow.getColumnModel().getColumn(18).setMaxWidth(150);
        tableCreateApiTest.getColumnModel().getColumn(18).setMinWidth(150);
        
        //tableAddTestFlow.getColumnModel().getColumn(19).setMaxWidth(150);
        tableCreateApiTest.getColumnModel().getColumn(19).setMinWidth(150);
        
        //tableAddTestFlow.getColumnModel().getColumn(20).setMaxWidth(200);
        tableCreateApiTest.getColumnModel().getColumn(20).setMinWidth(200);
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
    public static javax.swing.JButton bttnAddNewTestStep;
    public static javax.swing.JButton bttnAddNewTestSuite;
    public static javax.swing.JButton bttnAddStepDown;
    public static javax.swing.JButton bttnAddStepUp;
    public static javax.swing.JButton bttnDeleteTestStep;
    public static javax.swing.JButton bttnEnvVarList;
    public static javax.swing.JButton bttnSaveSuite;
    public static javax.swing.JLabel lblAuthorization;
    public javax.swing.JLabel lblHeaders;
    public static javax.swing.JLabel lblInvalidBody;
    public javax.swing.JLabel lblModifyPayload;
    public javax.swing.JLabel lblParams;
    public javax.swing.JLabel lblPayload;
    public javax.swing.JLabel lblRequest;
    public javax.swing.JLabel lblStatus;
    public javax.swing.JLabel lblURL;
    public javax.swing.JLabel lblVerifyPayload;
    public javax.swing.JPanel pnlCreateApiTest;
    public static javax.swing.JScrollPane scrlPnlAuthorization;
    public javax.swing.JScrollPane scrlPnlHeaders;
    public javax.swing.JScrollPane scrlPnlParams;
    public static javax.swing.JScrollPane scrlPnlPayload;
    public static javax.swing.JScrollPane scrollCreateApiTest;
    public javax.swing.JScrollPane scrollModifyPayload;
    public javax.swing.JScrollPane scrollVerifyPayload;
    public static javax.swing.JTable tableCreateApiTest;
    public static javax.swing.JTextArea textVerifyPayload;
    public static javax.swing.JTextField txtAPIurl;
    public static javax.swing.JTextArea txtAreaAuthorization;
    public static javax.swing.JTextArea txtAreaHeaders;
    public static javax.swing.JTextArea txtAreaParams;
    public static javax.swing.JTextArea txtAreaPayload;
    public static javax.swing.JTextField txtExpStatus;
    public static javax.swing.JTextArea txtModifyPayload;
    public static javax.swing.JTextField txtRequestType;
    // End of variables declaration//GEN-END:variables
}