/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.bolt.gui;

import com.automation.bolt.common;
import static com.automation.bolt.common.tabOutFromEditingColumn;
import com.automation.bolt.constants;
import static com.automation.bolt.gui.EditRegressionSuite.RegressionSuiteScrollPane;
import static com.automation.bolt.gui.EditRegressionSuite.keywordList;
import com.automation.bolt.renderer.*;
import static com.automation.bolt.xlsCommonMethods.createObjectRepoSheetNew;
import static com.automation.bolt.xlsCommonMethods.createTestFlowDataSheet;
import com.google.common.io.Files;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.automation.bolt.renderer.*;
import java.awt.Dimension;

/**
 *
 * @author zakir
 */
public class CreateTestSuite extends javax.swing.JFrame {
	public static WebElementExtractor extractor;
	public static DefaultTableModel createSuiteTabModel =new DefaultTableModel();
    public static DefaultTableModel createORTabModel =new DefaultTableModel();
    
    public static JComboBox<String> cBoxtestFlow =new JComboBox<String>();
    public static JComboBox<String> coBoxObjectRepo =new JComboBox<String>();
    
    public static JTextField elmNameTxt =new JTextField();
    public static JTextField elmIdTxt =new JTextField();
    public static JTextField elmXpathTxt =new JTextField();
    
    public static JTextField testIdTxt =new JTextField();
    public static JTextField testDataTxt =new JTextField();
    public static JTextField testDescTxt =new JTextField();
    
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
    public static TableColumn testDataCol =null;
    public static TableColumn testDescCol =null;
    
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
    
    /**
     * Creates new form CreateTestSuite
     */
    public CreateTestSuite() {
        initComponents();
        createSuiteTabModel =(DefaultTableModel) tableAddTestFlow.getModel();
        createORTabModel =(DefaultTableModel) tableAddOR.getModel();
        
        // test flow cell editors
        testIdCol =tableAddTestFlow.getColumnModel().getColumn(0);
        testIdCol.setCellEditor(new DefaultCellEditor(testIdTxt));
        
        testFlowColumn = tableAddTestFlow.getColumnModel().getColumn(2);
        cBoxtestFlow = new JComboBox<String>();
        keywordList(cBoxtestFlow);
        testFlowColumn.setCellEditor(new DefaultCellEditor(cBoxtestFlow));
        cBoxtestFlow.setEditable(true);
        
        testObjectRepoColumn = tableAddTestFlow.getColumnModel().getColumn(3);
        testObjectRepoColumn.setCellEditor(new DefaultCellEditor(coBoxObjectRepo));
        coBoxObjectRepo.setEditable(true);
        
        testDataCol =tableAddTestFlow.getColumnModel().getColumn(4);
        testDataCol.setCellEditor(new DefaultCellEditor(testDataTxt));
        
        testDescCol =tableAddTestFlow.getColumnModel().getColumn(5);
        testDescCol.setCellEditor(new DefaultCellEditor(testDescTxt));
        
        // test object repository cell editors
        elmNameCol =tableAddOR.getColumnModel().getColumn(0);
        elmNameCol.setCellEditor(new DefaultCellEditor(elmNameTxt));
        
        elmIdCol =tableAddOR.getColumnModel().getColumn(1);
        elmIdCol.setCellEditor(new DefaultCellEditor(elmIdTxt));
        
        elmXpathCol =tableAddOR.getColumnModel().getColumn(2);
        elmXpathCol.setCellEditor(new DefaultCellEditor(elmXpathTxt));
        
        elmNameTxt.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent evt) {
                elmNameTxtFocusLost(evt);
            }
            
            private void elmNameTxtFocusLost(FocusEvent evt) {                                     
                retrieveTestElmList();
            }   
        });
       
        testIdTxt.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                common.testIdTxtKeyTyped(evt, testIdTxt);
            }
        });
        
        elmNameTxt.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                common.testElmNameTxtTxtKeyTyped(evt, elmNameTxt);
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
        scrollPaneobjectRepository = new javax.swing.JScrollPane();
        tableAddOR = new javax.swing.JTable();
        scrollPaneTestFlow = new javax.swing.JScrollPane();
        tableAddTestFlow = new javax.swing.JTable();
        lblTestSuite = new javax.swing.JLabel();
        lblObjectRepository = new javax.swing.JLabel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        bttnAddTestElement = new javax.swing.JButton();
        bttnAddNewTestStep = new javax.swing.JButton();
        bttnDeleteTestElm = new javax.swing.JButton();
        bttnDeleteTestStep = new javax.swing.JButton();
        bttnAddStepUp = new javax.swing.JButton();
        bttnAddStepDown = new javax.swing.JButton();
        bttnSaveSuite = new javax.swing.JButton();
        bttnAddNewTestSuite = new javax.swing.JButton();
        bttnExtractWebElm = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Create Test Suite");
        setMinimumSize(new java.awt.Dimension(955, 492));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        tableAddOR.setBackground(new java.awt.Color(51, 51, 51));
        tableAddOR.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        tableAddOR.setForeground(new java.awt.Color(255, 255, 255));
        tableAddOR.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Element Name (user defined)", "id", "xpath"
            }
        ));
        tableAddOR.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableAddOR.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableAddOR.setName("tableObjectRepository"); // NOI18N
        tableAddOR.setRowHeight(30);
        tableAddOR.setRowMargin(2);
        tableAddOR.setSelectionBackground(new java.awt.Color(255, 204, 204));
        tableAddOR.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableAddOR.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableAddOR.setShowGrid(true);
        tableAddOR.getTableHeader().setReorderingAllowed(false);
        tableAddOR.setUpdateSelectionOnSort(false);
        tableAddOR.setVerifyInputWhenFocusTarget(false);
        tableAddOR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableAddORMousePressed(evt);
            }
        });
        tableAddOR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableAddORKeyReleased(evt);
            }
        });
        scrollPaneobjectRepository.setViewportView(tableAddOR);

        tableAddTestFlow.setBackground(new java.awt.Color(51, 51, 51));
        tableAddTestFlow.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        tableAddTestFlow.setForeground(new java.awt.Color(255, 255, 255));
        tableAddTestFlow.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Test ID", "Test Step", "Test Flow", "Test Element", "Test Data", "Test Description"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableAddTestFlow.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
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
        });
        tableAddTestFlow.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableAddTestFlowKeyReleased(evt);
            }
        });
        scrollPaneTestFlow.setViewportView(tableAddTestFlow);

        lblTestSuite.setBackground(new java.awt.Color(51, 51, 51));
        lblTestSuite.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblTestSuite.setForeground(new java.awt.Color(51, 51, 51));
        lblTestSuite.setText(" Test Suite");

        lblObjectRepository.setBackground(new java.awt.Color(51, 51, 51));
        lblObjectRepository.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        lblObjectRepository.setForeground(new java.awt.Color(51, 51, 51));
        lblObjectRepository.setText(" Object Repository");
        lblObjectRepository.setToolTipText("");
        lblObjectRepository.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        javax.swing.GroupLayout pnlCreateTestSuiteLayout = new javax.swing.GroupLayout(pnlCreateTestSuite);
        pnlCreateTestSuite.setLayout(pnlCreateTestSuiteLayout);
        pnlCreateTestSuiteLayout.setHorizontalGroup(
            pnlCreateTestSuiteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCreateTestSuiteLayout.createSequentialGroup()
                .addGroup(pnlCreateTestSuiteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scrollPaneTestFlow, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 967, Short.MAX_VALUE)
                    .addComponent(scrollPaneobjectRepository, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(1, 1, 1))
            .addComponent(lblTestSuite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblObjectRepository, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlCreateTestSuiteLayout.setVerticalGroup(
            pnlCreateTestSuiteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCreateTestSuiteLayout.createSequentialGroup()
                .addComponent(lblTestSuite, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(scrollPaneTestFlow, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(lblObjectRepository, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(scrollPaneobjectRepository, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );

        bttnAddTestElement.setBackground(new java.awt.Color(0, 0, 0));
        bttnAddTestElement.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        bttnAddTestElement.setForeground(new java.awt.Color(255, 255, 255));
        bttnAddTestElement.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/addTestStep_Element.png"));
            bttnAddTestElement.setToolTipText("add test element");
            bttnAddTestElement.setActionCommand("OpenRegressionSuite");
            bttnAddTestElement.setBorder(null);
            bttnAddTestElement.setBorderPainted(false);
            bttnAddTestElement.setContentAreaFilled(false);
            bttnAddTestElement.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            bttnAddTestElement.setOpaque(true);
            bttnAddTestElement.setRequestFocusEnabled(false);
            bttnAddTestElement.setRolloverEnabled(false);
            bttnAddTestElement.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    bttnAddTestElementMouseEntered(evt);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    bttnAddTestElementMouseExited(evt);
                }
            });
            bttnAddTestElement.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    bttnAddTestElementActionPerformed(evt);
                }
            });

            bttnAddNewTestStep.setBackground(new java.awt.Color(0, 0, 0));
            bttnAddNewTestStep.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
            bttnAddNewTestStep.setForeground(new java.awt.Color(255, 255, 255));
            bttnAddNewTestStep.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/addTestStep_Element.png"));
                bttnAddNewTestStep.setToolTipText("add test step");
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

                bttnDeleteTestElm.setBackground(new java.awt.Color(0, 0, 0));
                bttnDeleteTestElm.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
                bttnDeleteTestElm.setForeground(new java.awt.Color(255, 255, 255));
                bttnDeleteTestElm.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/deleteTestStep_Element.png"));
                    bttnDeleteTestElm.setToolTipText("delete test element");
                    bttnDeleteTestElm.setActionCommand("OpenRegressionSuite");
                    bttnDeleteTestElm.setBorder(null);
                    bttnDeleteTestElm.setBorderPainted(false);
                    bttnDeleteTestElm.setContentAreaFilled(false);
                    bttnDeleteTestElm.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                    bttnDeleteTestElm.setOpaque(true);
                    bttnDeleteTestElm.setRequestFocusEnabled(false);
                    bttnDeleteTestElm.setRolloverEnabled(false);
                    bttnDeleteTestElm.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                            bttnDeleteTestElmMouseEntered(evt);
                        }
                        public void mouseExited(java.awt.event.MouseEvent evt) {
                            bttnDeleteTestElmMouseExited(evt);
                        }
                    });
                    bttnDeleteTestElm.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            bttnDeleteTestElmActionPerformed(evt);
                        }
                    });

                    bttnDeleteTestStep.setBackground(new java.awt.Color(0, 0, 0));
                    bttnDeleteTestStep.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
                    bttnDeleteTestStep.setForeground(new java.awt.Color(255, 255, 255));
                    bttnDeleteTestStep.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/deleteTestStep_Element.png"));
                        bttnDeleteTestStep.setToolTipText("delete test step");
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
                            bttnAddStepUp.setToolTipText("add test step above the selected step");
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
                                bttnAddStepDown.setToolTipText("add test step below the selected step");
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
                                    bttnSaveSuite.setToolTipText("save test suite");
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
                                        bttnAddNewTestSuite.setToolTipText("create new test suite");
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

                                        bttnExtractWebElm.setBackground(new java.awt.Color(0, 0, 0));
                                        bttnExtractWebElm.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
                                        bttnExtractWebElm.setForeground(new java.awt.Color(255, 255, 255));
                                        bttnExtractWebElm.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/inspectWebElm.png"));
                                            bttnExtractWebElm.setToolTipText("delete test element");
                                            bttnExtractWebElm.setActionCommand("OpenRegressionSuite");
                                            bttnExtractWebElm.setBorder(null);
                                            bttnExtractWebElm.setBorderPainted(false);
                                            bttnExtractWebElm.setContentAreaFilled(false);
                                            bttnExtractWebElm.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                                            bttnExtractWebElm.setOpaque(true);
                                            bttnExtractWebElm.setRequestFocusEnabled(false);
                                            bttnExtractWebElm.setRolloverEnabled(false);
                                            bttnExtractWebElm.addMouseListener(new java.awt.event.MouseAdapter() {
                                                public void mouseEntered(java.awt.event.MouseEvent evt) {
                                                    bttnExtractWebElmMouseEntered(evt);
                                                }
                                                public void mouseExited(java.awt.event.MouseEvent evt) {
                                                    bttnExtractWebElmMouseExited(evt);
                                                }
                                            });
                                            bttnExtractWebElm.addActionListener(new java.awt.event.ActionListener() {
                                                public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                    bttnExtractWebElmActionPerformed(evt);
                                                }
                                            });

                                            jDesktopPane1.setLayer(bttnAddTestElement, javax.swing.JLayeredPane.DEFAULT_LAYER);
                                            jDesktopPane1.setLayer(bttnAddNewTestStep, javax.swing.JLayeredPane.DEFAULT_LAYER);
                                            jDesktopPane1.setLayer(bttnDeleteTestElm, javax.swing.JLayeredPane.DEFAULT_LAYER);
                                            jDesktopPane1.setLayer(bttnDeleteTestStep, javax.swing.JLayeredPane.DEFAULT_LAYER);
                                            jDesktopPane1.setLayer(bttnAddStepUp, javax.swing.JLayeredPane.DEFAULT_LAYER);
                                            jDesktopPane1.setLayer(bttnAddStepDown, javax.swing.JLayeredPane.DEFAULT_LAYER);
                                            jDesktopPane1.setLayer(bttnSaveSuite, javax.swing.JLayeredPane.DEFAULT_LAYER);
                                            jDesktopPane1.setLayer(bttnAddNewTestSuite, javax.swing.JLayeredPane.DEFAULT_LAYER);
                                            jDesktopPane1.setLayer(bttnExtractWebElm, javax.swing.JLayeredPane.DEFAULT_LAYER);

                                            javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
                                            jDesktopPane1.setLayout(jDesktopPane1Layout);
                                            jDesktopPane1Layout.setHorizontalGroup(
                                                jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout.createSequentialGroup()
                                                    .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(bttnExtractWebElm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                                            .addGap(1, 1, 1)
                                                            .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(bttnAddTestElement, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(bttnDeleteTestElm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(bttnDeleteTestStep, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(bttnAddStepUp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(bttnAddStepDown, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(bttnSaveSuite, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(bttnAddNewTestSuite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(bttnAddNewTestStep, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))))
                                                    .addGap(1, 1, 1))
                                            );
                                            jDesktopPane1Layout.setVerticalGroup(
                                                jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDesktopPane1Layout.createSequentialGroup()
                                                    .addGap(20, 20, 20)
                                                    .addComponent(bttnAddNewTestStep, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(bttnAddStepUp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(bttnAddStepDown, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(bttnDeleteTestStep, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(bttnSaveSuite, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(bttnAddNewTestSuite, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
                                                    .addComponent(bttnAddTestElement, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(bttnDeleteTestElm, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(bttnExtractWebElm, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(73, 73, 73))
                                            );

                                            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                                            getContentPane().setLayout(layout);
                                            layout.setHorizontalGroup(
                                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                    .addComponent(pnlCreateTestSuite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addGap(0, 0, 0)
                                                    .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            );
                                            layout.setVerticalGroup(
                                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(pnlCreateTestSuite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jDesktopPane1)
                                                    .addGap(1, 1, 1))
                                            );

                                            getAccessibleContext().setAccessibleParent(this);

                                            setSize(new java.awt.Dimension(1019, 554));
                                            setLocationRelativeTo(null);
                                        }// </editor-fold>//GEN-END:initComponents

    private void bttnAddTestElementMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnAddTestElementMouseEntered
        bttnAddTestElement.setBackground(new java.awt.Color(250, 128, 114));
        bttnAddTestElement.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_bttnAddTestElementMouseEntered

    private void bttnAddTestElementMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnAddTestElementMouseExited
        bttnAddTestElement.setBackground(new java.awt.Color(0,0,0));
        bttnAddTestElement.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_bttnAddTestElementMouseExited

    private void bttnAddTestElementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnAddTestElementActionPerformed
        if(getTestFlowSelectedRow !=-1){
            getTestFlowSelectedRow =tableAddTestFlow.getSelectedRow();
            tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableAddTestFlow, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
        
        if(getElmRepoSelectedRow !=-1){
            getElmRepoSelectedRow =tableAddOR.getSelectedRow();
            tabOutFromEditingColumn(getElmRepoCellEditorStatus, tableAddOR, getRepoCellxPoint, getRepoCellyPoint, getElmRepoSelectedRow);
        }
        
        if(checkForDuplicateElementName() ==true)
            return;
                                      
        createORTabModel.addRow(new Object[] {null,null,null,null,null,null});
        tableAddOR.setColumnSelectionInterval(0, 0);
        tableAddOR.setRowSelectionInterval(tableAddOR.getRowCount()-1, tableAddOR.getRowCount()-1);
        tableAddOR.scrollRectToVisible(tableAddOR.getCellRect(tableAddOR.getRowCount()-1,-1, true));
        tableAddOR.requestFocus();
        getElmRepoSelectedRow =tableAddOR.getSelectedRow();
    }//GEN-LAST:event_bttnAddTestElementActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
       tableCellRenderer renderer = new tableCellRenderer();
       tableAddORCellRenderer addORrenderer = new tableAddORCellRenderer();
       
       tableAddTestFlow.setDefaultRenderer(Object.class, renderer);
       tableAddOR.setDefaultRenderer(Object.class, addORrenderer);
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

    private void bttnAddNewTestStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnAddNewTestStepActionPerformed
        if(getElmRepoSelectedRow !=-1){
            getElmRepoSelectedRow =tableAddOR.getSelectedRow();
            tabOutFromEditingColumn(getElmRepoCellEditorStatus, tableAddOR, getRepoCellxPoint, getRepoCellyPoint, getElmRepoSelectedRow);
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
        tableAddTestFlow.setValueAt(Integer.valueOf(getPreviosTestStepNo.toString())+1, tableAddTestFlow.getRowCount()-1, 1);
        tableAddTestFlow.setRowSelectionInterval(tableAddTestFlow.getRowCount()-1, tableAddTestFlow.getRowCount()-1);
        tableAddTestFlow.scrollRectToVisible(tableAddTestFlow.getCellRect(tableAddTestFlow.getRowCount()-1,0, true));
        tableAddTestFlow.requestFocus();
        getTestFlowSelectedRow =tableAddTestFlow.getSelectedRow();
    }//GEN-LAST:event_bttnAddNewTestStepActionPerformed

    private void bttnDeleteTestElmMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnDeleteTestElmMouseEntered
        bttnDeleteTestElm.setBackground(new java.awt.Color(250, 128, 114));
        bttnDeleteTestElm.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_bttnDeleteTestElmMouseEntered

    private void bttnDeleteTestElmMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnDeleteTestElmMouseExited
        bttnDeleteTestElm.setBackground(new java.awt.Color(0,0,0));
        bttnDeleteTestElm.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_bttnDeleteTestElmMouseExited

    private void bttnDeleteTestElmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttnDeleteTestElmActionPerformed
        if(getTestFlowSelectedRow !=-1){
            getTestFlowSelectedRow =tableAddTestFlow.getSelectedRow();
            tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableAddTestFlow, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
        
        if(getTestFlowSelectedRow !=-1){
            getElmRepoSelectedRow =tableAddOR.getSelectedRow();
            tabOutFromEditingColumn(getElmRepoCellEditorStatus, tableAddOR, getRepoCellxPoint, getRepoCellyPoint, getElmRepoSelectedRow);
        }
        
        //boolean lastRowSelected =false;
        String getElmTxt =null;
        
        if (tableAddOR.getRowCount() >0) {
            getElmRepoSelectedRow =tableAddOR.getSelectedRow();
            int xRow =tableAddOR.getEditingRow();
            int rowIndex =tableAddOR.getSelectedRow();
            getElmTxt =(String) tableAddOR.getValueAt(rowIndex, 0);  
            
            if(elmNameTxt.isShowing()){
                elmNameTxt.dispatchEvent(new KeyEvent(elmNameTxt,KeyEvent.KEY_PRESSED, xRow,0,KeyEvent.VK_TAB, 'c'));
            }
            
            if(elmIdTxt.isShowing()){
                elmIdTxt.dispatchEvent(new KeyEvent(elmIdTxt,KeyEvent.KEY_PRESSED, xRow,0,KeyEvent.VK_TAB, 'c'));
            }
            
            if(elmXpathTxt.isShowing()){
                elmXpathTxt.dispatchEvent(new KeyEvent(elmXpathTxt,KeyEvent.KEY_PRESSED, xRow,0,KeyEvent.VK_TAB, 'c'));
                if (tableAddOR.getRowCount() ==rowIndex) {
                    tableAddOR.setColumnSelectionInterval(0, 0);
                    tableAddOR.setRowSelectionInterval(rowIndex, rowIndex);
                    tableAddOR.requestFocus();
                }
            }
            
            if(getElmTxt !=null){
                coBoxObjectRepo.removeItem(tableAddOR.getValueAt(rowIndex, 0));
            }
            createORTabModel.removeRow(rowIndex);
     
            if(tableAddOR.getRowCount()==0){
                coBoxObjectRepo.removeAllItems();
                return;
            }
            
            if (tableAddOR.getRowCount() ==rowIndex) {
                rowIndex =rowIndex-1;
                //lastRowSelected =true;
            }
            
            tableAddOR.setColumnSelectionInterval(0, 0);
            tableAddOR.setRowSelectionInterval(rowIndex, rowIndex);
            tableAddOR.requestFocus();
        }else {
            JOptionPane.showMessageDialog(tableAddOR, "No test test element(s) available to delete!", "Alert", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_bttnDeleteTestElmActionPerformed

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
            getElmRepoSelectedRow =tableAddOR.getSelectedRow();
            tabOutFromEditingColumn(getElmRepoCellEditorStatus, tableAddOR, getRepoCellxPoint, getRepoCellyPoint, getElmRepoSelectedRow);
        }
            
        if(getTestFlowSelectedRow !=-1){
            getTestFlowSelectedRow =tableAddTestFlow.getSelectedRow();
            tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableAddTestFlow, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
        
        if(common.checkForDuplicateTestId(createSuiteTabModel, tableAddTestFlow, editableRow, testIdTxt) ==true)
            return;
        
        String getTestId =null;
        String getTestIdx =null;
        boolean diffTestId =false;
        Object getSelTestStep =null;
        String getNextStepVal ="";
        boolean lastRowSelected =false;
            
        if (tableAddTestFlow.getRowCount() > 0) {
            getTestFlowSelectedRow =tableAddTestFlow.getSelectedRow();
            int rowIndex =tableAddTestFlow.getSelectedRow();
            
            try {
                getTestId =tableAddTestFlow.getValueAt(rowIndex, 0).toString();
            } catch (NullPointerException exp) {
                getTestId ="";
            }
            
            try {
                getTestIdx =tableAddTestFlow.getValueAt(tableAddTestFlow.getSelectedRow() + 1, 0).toString();                
            } catch (ArrayIndexOutOfBoundsException | NullPointerException exp) {
                getTestIdx ="";
            }
            
            if (!getTestIdx.isEmpty() && !getTestIdx.contentEquals("#")) {
                if (!getTestId.contentEquals(getTestIdx)) {
                    diffTestId =true;
                }
            }
            
            getSelTestStep = tableAddTestFlow.getValueAt(tableAddTestFlow.getSelectedRow(), 1);
            int newTestStep = Integer.valueOf(String.valueOf(getSelTestStep));
            createSuiteTabModel.removeRow(rowIndex);
              
            if (tableAddTestFlow.getRowCount() != 0) {
                if (tableAddTestFlow.getRowCount() ==rowIndex) {
                    rowIndex =rowIndex-1;
                    lastRowSelected =true;
                }
                tableAddTestFlow.setRowSelectionInterval(rowIndex, rowIndex);
                tableAddTestFlow.requestFocus();
                
                if(lastRowSelected ==true)
                    return;
               
                if (getTestId ==null || !getTestId.isEmpty() && !getTestId.contentEquals("#")) {
                    try{
                        try{
                            getNextStepVal =createSuiteTabModel.getValueAt(tableAddTestFlow.getSelectedRow() + 1, 0).toString();
                        }catch (NullPointerException exp){
                            getNextStepVal ="";
                        }
                        
                        if (getNextStepVal.isEmpty()
                            || !getTestIdx.contentEquals(getTestId)) {
                        if (diffTestId == true) {
                            getTestId = getTestIdx;
                        }
                        tableAddTestFlow.setValueAt(getTestId, tableAddTestFlow.getSelectedRow(), 0);
                    }
                    }catch (ArrayIndexOutOfBoundsException exp){ 
                        tableAddTestFlow.setValueAt(getTestId, tableAddTestFlow.getSelectedRow(), 0);
                    }
                }
                
                for (int i = tableAddTestFlow.getSelectedRow(); i <tableAddTestFlow.getRowCount(); i++) {
                    tableAddTestFlow.getModel().setValueAt(newTestStep,i , 1);
                    try{
                        try{
                            getNextStepVal =tableAddTestFlow.getModel().getValueAt(i + 1, 0).toString();
                        }catch(NullPointerException exp){
                            getNextStepVal ="";
                        }
                        
                        if (tableAddTestFlow.getModel().getValueAt(i, 0) != null && 
                                !getNextStepVal.isEmpty() &&
                                !getNextStepVal.contentEquals("#")) {
                            break;
                        } else if (tableAddTestFlow.getModel().getValueAt(i, 1) == null || 
                                   tableAddTestFlow.getModel().getValueAt(i + 1, 1).toString().isEmpty()) {
                            break;
                        }
                    }catch (ArrayIndexOutOfBoundsException exp){
                        tableAddTestFlow.getModel().setValueAt(newTestStep, i, 1);
                    }
                    newTestStep++;
                }
            }
            
        }else {
            JOptionPane.showMessageDialog(tableAddTestFlow, "No test step(s) available to delete!", "Alert", JOptionPane.WARNING_MESSAGE);
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
            getElmRepoSelectedRow =tableAddOR.getSelectedRow();
            tabOutFromEditingColumn(getElmRepoCellEditorStatus, tableAddOR, getRepoCellxPoint, getRepoCellyPoint, getElmRepoSelectedRow);
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
            int rowIndex = tableAddTestFlow.getSelectedRow();
            if(rowIndex !=-1){
                rowIndex = tableAddTestFlow.getSelectedRow();
                String getTestId = null;
                    
                try{
                    getTestId = tableAddTestFlow.getValueAt(rowIndex, 0).toString();
                }catch(NullPointerException exp){
                    getTestId ="";
                }
                        
                if(rowIndex ==0)
                    getTestStep = Integer.valueOf(tableAddTestFlow.getValueAt(rowIndex, 1).toString());
                else
                    getTestStep = Integer.valueOf(tableAddTestFlow.getValueAt(rowIndex-1, 1).toString())+1;
             
                createSuiteTabModel.insertRow(rowIndex, new Object[] {null,null,null,null,null,null });
                tableAddTestFlow.setRowSelectionInterval(rowIndex, rowIndex);
                tableAddTestFlow.scrollRectToVisible(tableAddTestFlow.getCellRect(rowIndex,0, true));
                tableAddTestFlow.requestFocus();
                    
                for(int i=rowIndex; i<tableAddTestFlow.getRowCount(); i++ ){
                    try{
                        if(!tableAddTestFlow.getValueAt(i, 0).toString().isEmpty())
                            break;
                    }catch(NullPointerException exp){

                    }
                    tableAddTestFlow.setValueAt(getTestStep++, i, 1);
                }
                    
                if(!getTestId.isEmpty()){
                    tableAddTestFlow.setValueAt(getTestId, tableAddTestFlow.getSelectedRow(), 0);
                    tableAddTestFlow.setValueAt("", tableAddTestFlow.getSelectedRow()+1, 0);
                }
        
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
            getElmRepoSelectedRow =tableAddOR.getSelectedRow();
            tabOutFromEditingColumn(getElmRepoCellEditorStatus, tableAddOR, getRepoCellxPoint, getRepoCellyPoint, getElmRepoSelectedRow);
        }
            
        if(getTestFlowSelectedRow !=-1){
            getTestFlowSelectedRow =tableAddTestFlow.getSelectedRow();
            tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableAddTestFlow, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
        
        if(common.checkForDuplicateTestId(createSuiteTabModel, tableAddTestFlow, editableRow, testIdTxt) ==true)
            return;
        
        if(tableAddTestFlow.getRowCount()>0){
            getTestFlowSelectedRow =tableAddTestFlow.getSelectedRow();
            int rowIndex = tableAddTestFlow.getSelectedRow();
            if (rowIndex != -1) {
                try {
                    try {
                        String getTestID = tableAddTestFlow.getValueAt(tableAddTestFlow.getSelectedRow(), 0).toString();
                        String getTestStep = tableAddTestFlow.getValueAt(tableAddTestFlow.getSelectedRow(), 1).toString();

                        if ((getTestID.isEmpty()) && (getTestStep.isEmpty())) {
                            return;
                        } else {
                            createSuiteTabModel.insertRow(rowIndex + 1, new Object[]{null, null, null, null, null, null});
                            rowIndex = tableAddTestFlow.getSelectedRow();
                            tableAddTestFlow.setRowSelectionInterval(rowIndex + 1, rowIndex + 1);
                            tableAddTestFlow.scrollRectToVisible(tableAddTestFlow.getCellRect(rowIndex + 1, 0, true));
                            tableAddTestFlow.requestFocus();
                        }
                    } catch (NullPointerException exp) {
                        createSuiteTabModel.insertRow(rowIndex + 1, new Object[]{null, null, null, null, null, null});
                        rowIndex = tableAddTestFlow.getSelectedRow();
                        tableAddTestFlow.setRowSelectionInterval(rowIndex + 1, rowIndex + 1);
                        tableAddTestFlow.scrollRectToVisible(tableAddTestFlow.getCellRect(rowIndex + 1, 0, true));
                        tableAddTestFlow.requestFocus();
                    }
                } catch (NullPointerException exp) {
                    createSuiteTabModel.insertRow(rowIndex + 1, new Object[]{null, null, null, null, null, null});
                    rowIndex = tableAddTestFlow.getSelectedRow();
                    tableAddTestFlow.setRowSelectionInterval(rowIndex + 1, rowIndex + 1);
                    tableAddTestFlow.scrollRectToVisible(tableAddTestFlow.getCellRect(rowIndex + 1, 0, true));
                    tableAddTestFlow.requestFocus();
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
            getElmRepoSelectedRow =tableAddOR.getSelectedRow();
            tabOutFromEditingColumn(getElmRepoCellEditorStatus, tableAddOR, getRepoCellxPoint, getRepoCellyPoint, getElmRepoSelectedRow);
        }
            
        if(getTestFlowSelectedRow !=-1){
            getTestFlowSelectedRow =tableAddTestFlow.getSelectedRow();
            tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableAddTestFlow, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);
        }
        
        if(common.checkForDuplicateTestId(createSuiteTabModel, tableAddTestFlow, editableRow, testIdTxt) ==true)
            return;
        
        if(checkForDuplicateElementName() ==true)
                return;
        
        if(common.checkForBlankColumnValue(tableAddOR, 0)){
            tableAddOR.editCellAt(common.blankRowIndex, 0);
            tableAddOR.getEditorComponent().requestFocus();
            tableAddOR.changeSelection(common.blankRowIndex, 0, false, true);
            JOptionPane.showMessageDialog(null, "Element name can not be blank!", "Alert", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
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
                excelJTableExport =createTestFlowDataSheet(excelJTableExport, createSuiteTabModel);   
                // create test element repository sheet
                excelJTableExport =createObjectRepoSheetNew(excelJTableExport, createORTabModel);

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
                Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.SEVERE, null, ex);
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
                                if (ex1.getMessage().contains("The process cannot access the file because it is being used by another process")) {

                                }
                            } catch (IOException ex1) {
                                Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.SEVERE, null, ex1);
                            }
                        }
                    } while (response != JOptionPane.CANCEL_OPTION);
                }
            } catch (IOException ex) {
                Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        else
            JOptionPane.showMessageDialog(tableAddOR,"No test suite is available to save!","Alert",JOptionPane.WARNING_MESSAGE);
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
        if(tableAddTestFlow.getRowCount()<=0 && tableAddOR.getRowCount() <=0){
            return;
        }
        
        if(getTestFlowSelectedRow !=-1){
            getElmRepoSelectedRow =tableAddOR.getSelectedRow();
            tabOutFromEditingColumn(getElmRepoCellEditorStatus, tableAddOR, getRepoCellxPoint, getRepoCellyPoint, getElmRepoSelectedRow);
        }
            
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
        
        DefaultTableModel modelAddOR = (DefaultTableModel)tableAddOR.getModel();
        modelAddOR.getDataVector().removeAllElements();
        modelAddOR.fireTableDataChanged();
        editableRow =0;
        editableAddElmRow =0;
        
        tableAddOR.removeEditor();
        tableAddTestFlow.removeEditor();
        
        createSuiteTabModel =(DefaultTableModel) tableAddTestFlow.getModel();
        createORTabModel =(DefaultTableModel) tableAddOR.getModel();
        
        elmNameTxt =new JTextField();
        elmNameCol =tableAddOR.getColumnModel().getColumn(0);
        elmNameCol.setCellEditor(new DefaultCellEditor(elmNameTxt));
        
        elmIdTxt =new JTextField();
        elmIdCol =tableAddOR.getColumnModel().getColumn(1);
        elmIdCol.setCellEditor(new DefaultCellEditor(elmIdTxt));
        
        elmXpathTxt =new JTextField();
        elmXpathCol =tableAddOR.getColumnModel().getColumn(2);
        elmXpathCol.setCellEditor(new DefaultCellEditor(elmXpathTxt));
        
        getTestFlowCellEditorStatus =false;
        getElmRepoCellEditorStatus =false;
    }//GEN-LAST:event_bttnAddNewTestSuiteActionPerformed

    private void tableAddORKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableAddORKeyReleased
        checkForDuplicateElementName();
    }//GEN-LAST:event_tableAddORKeyReleased
    
    public static void retrieveTestElmList(){
        //coBoxObjectRepo.removeAllItems();
        
        String getCellTxt =null;
        for(int i=0; i<tableAddOR.getRowCount(); i++){
            try{
                getCellTxt =tableAddOR.getValueAt(i, 0).toString();
            }catch(NullPointerException exp){
                getCellTxt ="";
            }
            
            if(!getCellTxt.isEmpty()){
                if(checkElementExistInTheList(getCellTxt) ==false)
                    coBoxObjectRepo.addItem(getCellTxt);
            }
        }
    }
    
    public static boolean checkForDuplicateElementName(){
        String getCellVal = null;
        duplicateElement =false;
        
        if(createORTabModel.isCellEditable(tableAddOR.getSelectedRow(), 0) ==true){
             try{
                getCellVal =elmNameTxt.getText();
            }catch(NullPointerException | ArrayIndexOutOfBoundsException exp){
                getCellVal ="";
            }
             
            if(!getCellVal.isEmpty()){
               int elmIndex =0;
               for(int i=0; i<createORTabModel.getRowCount(); i++){
                       String getCellTxt=null;
                       try{
                           getCellTxt =createORTabModel.getValueAt(i, 0).toString().toLowerCase();
                       }catch (NullPointerException exp){
                           getCellTxt ="";
                       }
                       if(getCellVal.toLowerCase().contentEquals(getCellTxt)){
                           elmIndex++;
                           if(elmIndex ==2){
                               //editableRow =tableAddOR.getSelectedRow();
                               tableAddOR.editCellAt(editableAddElmRow, 0);
                               tableAddOR.setSurrendersFocusOnKeystroke(true);
                               tableAddOR.getEditorComponent().requestFocus();
                               
                               tableAddOR.clearSelection();
                               tableAddOR.changeSelection(editableAddElmRow, 0, false, true);
                               elmNameTxt.selectAll();
                               duplicateElement =true;
                               JOptionPane.showMessageDialog(null, "Element name ["+getCellTxt+"] already exist!", "Alert", JOptionPane.WARNING_MESSAGE);
                               break;
                           }
                       }
               }
           }
        }
        
        return duplicateElement;
    }
        
    private void tableAddTestFlowMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableAddTestFlowMousePressed
        
        getElmRepoSelectedRow =tableAddOR.getSelectedRow();
        tabOutFromEditingColumn(getElmRepoCellEditorStatus, tableAddOR, getRepoCellxPoint, getRepoCellyPoint, getElmRepoSelectedRow);
       
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
                    cBoxtestFlow.requestFocusInWindow();
                    break;
                case 3:
                    tableAddTestFlow.editCellAt(getCurRow, 3);
                    coBoxObjectRepo.requestFocusInWindow();
                    break;    
                case 4:
                    tableAddTestFlow.editCellAt(getCurRow, 4);
                    testDataTxt.requestFocusInWindow();
                    break;
                case 5:
                    tableAddTestFlow.editCellAt(getCurRow, 5);
                    testDescTxt.requestFocusInWindow();
                    break;
                default:
                    break;
            }
        }
    }//GEN-LAST:event_tableAddTestFlowMousePressed

    private void tableAddORMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableAddORMousePressed
        getTestFlowSelectedRow =tableAddTestFlow.getSelectedRow();
        tabOutFromEditingColumn(getTestFlowCellEditorStatus, tableAddTestFlow, getFlowCellxPoint, getFlowCellyPoint, getTestFlowSelectedRow);

        int getCurRow = tableAddOR.convertRowIndexToModel(tableAddOR.rowAtPoint(evt.getPoint()));
        int gerCurrCol = tableAddOR.convertColumnIndexToModel(tableAddOR.columnAtPoint(evt.getPoint()));
        getElmRepoSelectedRow =getCurRow;
        
        getRepoCellxPoint =tableAddOR.rowAtPoint(evt.getPoint());
        getRepoCellyPoint =tableAddOR.columnAtPoint(evt.getPoint());
        
        if(checkForDuplicateElementName() ==true){
            return;
        }
        
        if(duplicateElement ==false){
            switch (gerCurrCol) {
                case 0:
                    tableAddOR.editCellAt(getCurRow, 0);
                    editableAddElmRow =tableAddOR.getEditingRow();
                    elmNameTxt.requestFocusInWindow();
                    break;
                case 1:
                    tableAddOR.editCellAt(getCurRow, 1);
                    elmIdTxt.requestFocusInWindow();
                    break;
                case 2:
                    tableAddOR.editCellAt(getCurRow, 2);
                    elmXpathTxt.requestFocusInWindow();
                    break;
                default:
                    break;
            }
        }
    }//GEN-LAST:event_tableAddORMousePressed

    private void tableAddTestFlowKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableAddTestFlowKeyReleased
        common.checkForDuplicateTestId(createSuiteTabModel, tableAddTestFlow, editableRow, testIdTxt);
    }//GEN-LAST:event_tableAddTestFlowKeyReleased

    private void bttnExtractWebElmMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnExtractWebElmMouseEntered
    	bttnExtractWebElm.setBackground(new java.awt.Color(250, 128, 114));
    	bttnExtractWebElm.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_bttnExtractWebElmMouseEntered

    private void bttnExtractWebElmMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnExtractWebElmMouseExited
    	bttnExtractWebElm.setBackground(new java.awt.Color(0,0,0));
    	bttnExtractWebElm.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_bttnExtractWebElmMouseExited

    private void bttnExtractWebElmActionPerformed(java.awt.event.ActionEvent evt) {
    	//GEN-FIRST:event_bttnExtractWebElmActionPerformed
    	SwingUtilities.invokeLater(() -> {
            //if (extractor == null) {
                try {
                    extractor = new WebElementExtractor();
                    System.out.println("WebElementExtractor launched successfully");
                } catch (Exception ex) {
                    System.err.println("Error launching WebElementExtractor: " + ex.getMessage());
                    //JOptionPane.showMessageDialog(frame, 
                        //"Failed to launch Web Element Extractor: " + ex.getMessage(),
                        //"Error",
                        //JOptionPane.ERROR_MESSAGE);
                }  
        });
    	//objWebElmExtractor.setFocusable(true);
    }//GEN-LAST:event_bttnExtractWebElmActionPerformed
    
    public static boolean checkElementExistInTheList(String listItem){
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
    }
    
    public static void setTableColWidthForCreateRegSuiteTable(){
        tableAddTestFlow.getColumnModel().getColumn(0).setMaxWidth(61);
        tableAddTestFlow.getColumnModel().getColumn(0).setMinWidth(61);
        
        tableAddTestFlow.getColumnModel().getColumn(1).setMaxWidth(72);
        tableAddTestFlow.getColumnModel().getColumn(1).setMinWidth(72);
        
        tableAddTestFlow.getColumnModel().getColumn(2).setMaxWidth(163);
        tableAddTestFlow.getColumnModel().getColumn(2).setMinWidth(163);
        
        //tableAddTestFlow.getColumnModel().getColumn(3).setMaxWidth(173);
        tableAddTestFlow.getColumnModel().getColumn(3).setMinWidth(173);
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
            java.util.logging.Logger.getLogger(CreateTestSuite.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
         //</editor-fold>
         
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new CreateTestSuite().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton bttnAddNewTestStep;
    public static javax.swing.JButton bttnAddNewTestSuite;
    public static javax.swing.JButton bttnAddStepDown;
    public static javax.swing.JButton bttnAddStepUp;
    public javax.swing.JButton bttnAddTestElement;
    public javax.swing.JButton bttnDeleteTestElm;
    public javax.swing.JButton bttnDeleteTestStep;
    public javax.swing.JButton bttnExtractWebElm;
    public static javax.swing.JButton bttnSaveSuite;
    public javax.swing.JDesktopPane jDesktopPane1;
    public javax.swing.JLabel lblObjectRepository;
    public javax.swing.JLabel lblTestSuite;
    public javax.swing.JPanel pnlCreateTestSuite;
    public static javax.swing.JScrollPane scrollPaneTestFlow;
    public javax.swing.JScrollPane scrollPaneobjectRepository;
    public static javax.swing.JTable tableAddOR;
    public static javax.swing.JTable tableAddTestFlow;
    // End of variables declaration//GEN-END:variables
}
