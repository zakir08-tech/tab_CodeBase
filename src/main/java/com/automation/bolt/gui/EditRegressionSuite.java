/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.excelSheetObjectRepositoryOR
 */
package com.automation.bolt.gui;

import static com.automation.bolt.gui.ObjectRepoFrame.RegSuite;
import static com.automation.bolt.gui.ObjectRepoFrame.updateObjectRepository;
import static com.automation.bolt.xlsCommonMethods.createObjectRepoDataSheet;
import static com.automation.bolt.xlsCommonMethods.createTestFlowDataSheet;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.automation.bolt.common;
import com.automation.bolt.constants;
import com.automation.bolt.renderer.tableCellRenderer;
import com.google.common.io.Files;

/**
 *
 * @author zakir
 */
public class EditRegressionSuite extends javax.swing.JFrame {
    public static DefaultTableModel importDataFromExcelModel = new DefaultTableModel();
    public static DefaultTableModel importDataFromExcelModelOR = new DefaultTableModel();
    public static DefaultTableModel importDataFromExcelModelORBackup = new DefaultTableModel();
    public TestElementRepository objRepo = new TestElementRepository();
    
    public static JComboBox<String> comboBoxTestFlow = new JComboBox<String>();
    public static JComboBox<String> comboBoxObjectRepository = new JComboBox<String>();
    
    private TableColumn testFlowColumn =new TableColumn();
    public TableColumn testObjectRepoColumn =new TableColumn();
    
    private XSSFSheet excelSheetTestFlow;
    public XSSFSheet excelSheetObjectRepository;
    public XSSFSheet excelSheetObjectRepositorySecodary;
    public XSSFSheet excelSheetObjectRepositoryOR;
    public XSSFSheet excelSheetObjectRepositoryORSecondary;
    private String objectRepositoryList = "";
    public static JFileChooser excelFileImport;
    public static JFileChooser excelFileExport;
    public static JFileChooser excelFileImportOR;
    public static File excelFile;
    public static File excelFileOR;
    public File excelFileORSub;
    public String testSuiteName;
    public XSSFWorkbook excelImportWorkBook = new XSSFWorkbook();
    public XSSFWorkbook excelImportWorkBookOR = new XSSFWorkbook();
    public boolean noRepoFound;
    public boolean testSuiteUploaded;
    public static String testSuiteFilePath;
    public static String testGlobalORFilePath;
    public static String testGlobalORAssociatedFilePath;
    public static FileInputStream excelFIS;
    public static boolean previousCellClicked =false;
    
    public static TableColumn testIdCol =null;
    public static JTextField testIdTxt =new JTextField();
    
    public static TableColumn testDataCol =null;
    public static JTextField testDataTxt =new JTextField();
    
    public static TableColumn testDescCol =null;
    public static JTextField testDescTxt =new JTextField();
    
    public static boolean duplicateTestId =false;
    public static int editableRow;
    
    public static int getFlowCellxPoint;
    public static int getFlowCellyPoint;
    public static int getEditingRow;
    public static int getEditingColumn;
    
    public static String getSelRowTestFlow;
    
    /**
     * Creates new form NewJFrame
     */

    public EditRegressionSuite() {
        initComponents();        
                
        testIdCol =RegressionSuiteTable.getColumnModel().getColumn(0);
        testIdCol.setCellEditor(new DefaultCellEditor(testIdTxt));
        
        testFlowColumn = RegressionSuiteTable.getColumnModel().getColumn(2);
        comboBoxTestFlow = new JComboBox<String>();
        keywordList(comboBoxTestFlow);
        testFlowColumn.setCellEditor(new DefaultCellEditor(comboBoxTestFlow));
        comboBoxTestFlow.setEditable(true);
        
        testObjectRepoColumn = RegressionSuiteTable.getColumnModel().getColumn(3);
        testObjectRepoColumn.setCellEditor(new DefaultCellEditor(comboBoxObjectRepository));
        comboBoxObjectRepository.setEditable(true);
        
        testDataCol =RegressionSuiteTable.getColumnModel().getColumn(4);
        testDataCol.setCellEditor(new DefaultCellEditor(testDataTxt));
        
        testDescCol =RegressionSuiteTable.getColumnModel().getColumn(5);
        testDescCol.setCellEditor(new DefaultCellEditor(testDescTxt));
        
        testIdTxt.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                common.testIdTxtKeyTyped(evt, testIdTxt);
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

        pnlRegSuiteTable = new JPanel();
        RegressionSuiteScrollPane = new JScrollPane();
        RegressionSuiteTable = new JTable();
        jDesktopPane1 = new JDesktopPane();
        pnlMenuBar = new JPanel();
        pnlOpenTestSuite = new JPanel();
        RegressionSuite = new JButton();
        AssociateObjORJCheckBox = new JCheckBox();
        pnlDeleteTestStep = new JPanel();
        DeleteStep = new JButton();
        pnlAddTestStepUp = new JPanel();
        AddStepUp = new JButton();
        pnlSaveAsNewTestSuite = new JPanel();
        SaveSuite1 = new JButton();
        LocalORJRadioButton = new JRadioButton();
        GlobalORJRadioButton = new JRadioButton();
        pnlAddTestStepDown = new JPanel();
        AddStepDown = new JButton();
        pnlSaveTestSuite = new JPanel();
        SaveSuite = new JButton();
        pnlAddNewTestStep = new JPanel();
        AddNewStep = new JButton();
        pnlOpenOR = new JPanel();
        OpenObjectRepository = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit Test Suite");
        setBounds(new Rectangle(0, 0, 973, 500));
        setIconImages(null);
        setMinimumSize(new Dimension(879, 413));
        setPreferredSize(new Dimension(879, 443));
        setSize(new Dimension(0, 0));
        addWindowFocusListener(new WindowFocusListener() {
            public void windowGainedFocus(WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(WindowEvent evt) {
            }
        });
        addWindowListener(new WindowAdapter() {
            public void windowActivated(WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosed(WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pnlRegSuiteTable.setBackground(java.awt.Color.lightGray);

        RegressionSuiteScrollPane.setAutoscrolls(true);
        RegressionSuiteScrollPane.setFont(new Font("Calibri", 0, 12)); // NOI18N

        RegressionSuiteTable.setBackground(new java.awt.Color(51, 51, 51));
        RegressionSuiteTable.setFont(new Font("Consolas", 0, 13)); // NOI18N
        RegressionSuiteTable.setForeground(new java.awt.Color(255, 255, 255));
        RegressionSuiteTable.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Test ID", "Test Step", "Test Flow", "Test Element", "Test Data", "Step Description"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        RegressionSuiteTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        RegressionSuiteTable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        RegressionSuiteTable.setName(""); // NOI18N
        RegressionSuiteTable.setRowHeight(30);
        RegressionSuiteTable.setRowMargin(2);
        RegressionSuiteTable.setSelectionBackground(new java.awt.Color(255, 153, 153));
        RegressionSuiteTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        RegressionSuiteTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        RegressionSuiteTable.setShowGrid(true);
        RegressionSuiteTable.getTableHeader().setReorderingAllowed(false);
        RegressionSuiteTable.setUpdateSelectionOnSort(false);
        RegressionSuiteTable.setVerifyInputWhenFocusTarget(false);
        RegressionSuiteTable.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                RegressionSuiteTableFocusGained(evt);
            }
        });
        RegressionSuiteTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                RegressionSuiteTableMouseClicked(evt);
            }
            public void mousePressed(MouseEvent evt) {
                RegressionSuiteTableMousePressed(evt);
            }
            public void mouseReleased(MouseEvent evt) {
                RegressionSuiteTableMouseReleased(evt);
            }
        });
        RegressionSuiteTable.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                RegressionSuiteTableKeyReleased(evt);
            }
        });
        RegressionSuiteScrollPane.setViewportView(RegressionSuiteTable);

        GroupLayout pnlRegSuiteTableLayout = new GroupLayout(pnlRegSuiteTable);
        pnlRegSuiteTable.setLayout(pnlRegSuiteTableLayout);
        pnlRegSuiteTableLayout.setHorizontalGroup(pnlRegSuiteTableLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(RegressionSuiteScrollPane, GroupLayout.DEFAULT_SIZE, 683, Short.MAX_VALUE)
        );
        pnlRegSuiteTableLayout.setVerticalGroup(pnlRegSuiteTableLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(RegressionSuiteScrollPane, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
        );

        pnlMenuBar.setBackground(new java.awt.Color(0, 153, 153));
        pnlMenuBar.setOpaque(false);

        pnlOpenTestSuite.setBackground(new java.awt.Color(0, 0, 0));

        RegressionSuite.setBackground(new java.awt.Color(0, 0, 0));
        RegressionSuite.setFont(new Font("Consolas", 1, 14)); // NOI18N
        RegressionSuite.setForeground(new java.awt.Color(255, 255, 255));
        RegressionSuite.setIcon(new ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/addUploadTestSuite.png"));
            RegressionSuite.setText("Open Test Suite");
            RegressionSuite.setToolTipText("will upload the test suite for modifications");
            RegressionSuite.setActionCommand("OpenRegressionSuite");
            RegressionSuite.setBorder(null);
            RegressionSuite.setBorderPainted(false);
            RegressionSuite.setContentAreaFilled(false);
            RegressionSuite.setFocusable(false);
            RegressionSuite.setHorizontalAlignment(SwingConstants.LEFT);
            RegressionSuite.setRequestFocusEnabled(false);
            RegressionSuite.setRolloverEnabled(false);
            RegressionSuite.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    RegressionSuiteMouseEntered(evt);
                }
                public void mouseExited(MouseEvent evt) {
                    RegressionSuiteMouseExited(evt);
                }
            });
            RegressionSuite.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    RegressionSuiteActionPerformed(evt);
                }
            });

            GroupLayout pnlOpenTestSuiteLayout = new GroupLayout(pnlOpenTestSuite);
            pnlOpenTestSuite.setLayout(pnlOpenTestSuiteLayout);
            pnlOpenTestSuiteLayout.setHorizontalGroup(pnlOpenTestSuiteLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(RegressionSuite, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            pnlOpenTestSuiteLayout.setVerticalGroup(pnlOpenTestSuiteLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, pnlOpenTestSuiteLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(RegressionSuite, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
            );

            AssociateObjORJCheckBox.setBackground(new java.awt.Color(0, 153, 153));
            AssociateObjORJCheckBox.setFont(new Font("Consolas", 1, 12)); // NOI18N
            AssociateObjORJCheckBox.setForeground(new java.awt.Color(255, 255, 255));
            AssociateObjORJCheckBox.setText("Associate Global OR");
            AssociateObjORJCheckBox.setToolTipText("if checked, will give option to associate a global object repository");
            AssociateObjORJCheckBox.setEnabled(false);
            AssociateObjORJCheckBox.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    AssociateObjORJCheckBoxMouseEntered(evt);
                }
                public void mouseExited(MouseEvent evt) {
                    AssociateObjORJCheckBoxMouseExited(evt);
                }
            });
            AssociateObjORJCheckBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    AssociateObjORJCheckBoxActionPerformed(evt);
                }
            });

            pnlDeleteTestStep.setBackground(new java.awt.Color(0, 0, 0));

            DeleteStep.setBackground(new java.awt.Color(0, 0, 0));
            DeleteStep.setFont(new Font("Consolas", 1, 14)); // NOI18N
            DeleteStep.setForeground(new java.awt.Color(255, 255, 255));
            DeleteStep.setIcon(new ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/deleteTestStep_Element.png"));
                DeleteStep.setText("Delete Test Step");
                DeleteStep.setToolTipText("will delete the selected test step");
                DeleteStep.setBorder(null);
                DeleteStep.setBorderPainted(false);
                DeleteStep.setContentAreaFilled(false);
                DeleteStep.setFocusPainted(false);
                DeleteStep.setFocusable(false);
                DeleteStep.setHorizontalAlignment(SwingConstants.LEFT);
                DeleteStep.setRequestFocusEnabled(false);
                DeleteStep.setRolloverEnabled(false);
                DeleteStep.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent evt) {
                        DeleteStepMouseEntered(evt);
                    }
                    public void mouseExited(MouseEvent evt) {
                        DeleteStepMouseExited(evt);
                    }
                });
                DeleteStep.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        DeleteStepActionPerformed(evt);
                    }
                });

                GroupLayout pnlDeleteTestStepLayout = new GroupLayout(pnlDeleteTestStep);
                pnlDeleteTestStep.setLayout(pnlDeleteTestStepLayout);
                pnlDeleteTestStepLayout.setHorizontalGroup(pnlDeleteTestStepLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(DeleteStep, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                pnlDeleteTestStepLayout.setVerticalGroup(pnlDeleteTestStepLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, pnlDeleteTestStepLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(DeleteStep, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                );

                pnlAddTestStepUp.setBackground(new java.awt.Color(0, 0, 0));

                AddStepUp.setBackground(new java.awt.Color(0, 0, 0));
                AddStepUp.setFont(new Font("Consolas", 1, 14)); // NOI18N
                AddStepUp.setForeground(new java.awt.Color(255, 255, 255));
                AddStepUp.setIcon(new ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/addTestStepUp.png"));
                    AddStepUp.setText("Add Test Step Up");
                    AddStepUp.setToolTipText("will add a new test step above the selected step");
                    AddStepUp.setActionCommand("AddNewStep");
                    AddStepUp.setBorder(null);
                    AddStepUp.setBorderPainted(false);
                    AddStepUp.setContentAreaFilled(false);
                    AddStepUp.setDefaultCapable(false);
                    AddStepUp.setFocusPainted(false);
                    AddStepUp.setFocusable(false);
                    AddStepUp.setHorizontalAlignment(SwingConstants.LEFT);
                    AddStepUp.setRequestFocusEnabled(false);
                    AddStepUp.setRolloverEnabled(false);
                    AddStepUp.addMouseListener(new MouseAdapter() {
                        public void mouseEntered(MouseEvent evt) {
                            AddStepUpMouseEntered(evt);
                        }
                        public void mouseExited(MouseEvent evt) {
                            AddStepUpMouseExited(evt);
                        }
                    });
                    AddStepUp.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            AddStepUpActionPerformed(evt);
                        }
                    });

                    GroupLayout pnlAddTestStepUpLayout = new GroupLayout(pnlAddTestStepUp);
                    pnlAddTestStepUp.setLayout(pnlAddTestStepUpLayout);
                    pnlAddTestStepUpLayout.setHorizontalGroup(pnlAddTestStepUpLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(AddStepUp, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    );
                    pnlAddTestStepUpLayout.setVerticalGroup(pnlAddTestStepUpLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, pnlAddTestStepUpLayout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(AddStepUp, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                    );

                    pnlSaveAsNewTestSuite.setBackground(new java.awt.Color(0, 0, 0));

                    SaveSuite1.setBackground(new java.awt.Color(0, 0, 0));
                    SaveSuite1.setFont(new Font("Consolas", 1, 14)); // NOI18N
                    SaveSuite1.setForeground(new java.awt.Color(255, 255, 255));
                    SaveSuite1.setIcon(new ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/saveAsNewTestSuite.png"));
                        SaveSuite1.setText("Save As New Suite");
                        SaveSuite1.setToolTipText("will save the opened test suite as a new test suite");
                        SaveSuite1.setBorder(null);
                        SaveSuite1.setBorderPainted(false);
                        SaveSuite1.setContentAreaFilled(false);
                        SaveSuite1.setFocusPainted(false);
                        SaveSuite1.setFocusable(false);
                        SaveSuite1.setHorizontalAlignment(SwingConstants.LEFT);
                        SaveSuite1.setRequestFocusEnabled(false);
                        SaveSuite1.setRolloverEnabled(false);
                        SaveSuite1.addMouseListener(new MouseAdapter() {
                            public void mouseEntered(MouseEvent evt) {
                                SaveSuite1MouseEntered(evt);
                            }
                            public void mouseExited(MouseEvent evt) {
                                SaveSuite1MouseExited(evt);
                            }
                        });
                        SaveSuite1.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent evt) {
                                SaveSuite1ActionPerformed(evt);
                            }
                        });

                        GroupLayout pnlSaveAsNewTestSuiteLayout = new GroupLayout(pnlSaveAsNewTestSuite);
                        pnlSaveAsNewTestSuite.setLayout(pnlSaveAsNewTestSuiteLayout);
                        pnlSaveAsNewTestSuiteLayout.setHorizontalGroup(pnlSaveAsNewTestSuiteLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(pnlSaveAsNewTestSuiteLayout.createSequentialGroup()
                                .addComponent(SaveSuite1, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                        );
                        pnlSaveAsNewTestSuiteLayout.setVerticalGroup(pnlSaveAsNewTestSuiteLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(GroupLayout.Alignment.TRAILING, pnlSaveAsNewTestSuiteLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(SaveSuite1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                        );

                        LocalORJRadioButton.setBackground(new java.awt.Color(0, 153, 153));
                        LocalORJRadioButton.setFont(new Font("Consolas", 1, 12)); // NOI18N
                        LocalORJRadioButton.setForeground(new java.awt.Color(255, 255, 255));
                        LocalORJRadioButton.setSelected(true);
                        LocalORJRadioButton.setText("Local");
                        LocalORJRadioButton.setToolTipText("select to open currrent test suite default associated object repository");
                        LocalORJRadioButton.addMouseListener(new MouseAdapter() {
                            public void mouseEntered(MouseEvent evt) {
                                LocalORJRadioButtonMouseEntered(evt);
                            }
                            public void mouseExited(MouseEvent evt) {
                                LocalORJRadioButtonMouseExited(evt);
                            }
                        });
                        LocalORJRadioButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent evt) {
                                LocalORJRadioButtonActionPerformed(evt);
                            }
                        });

                        GlobalORJRadioButton.setBackground(new java.awt.Color(0, 153, 153));
                        GlobalORJRadioButton.setFont(new Font("Consolas", 1, 12)); // NOI18N
                        GlobalORJRadioButton.setForeground(new java.awt.Color(255, 255, 255));
                        GlobalORJRadioButton.setText("Global ");
                        GlobalORJRadioButton.setToolTipText("select to open global object repository");
                        GlobalORJRadioButton.addMouseListener(new MouseAdapter() {
                            public void mouseEntered(MouseEvent evt) {
                                GlobalORJRadioButtonMouseEntered(evt);
                            }
                            public void mouseExited(MouseEvent evt) {
                                GlobalORJRadioButtonMouseExited(evt);
                            }
                        });
                        GlobalORJRadioButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent evt) {
                                GlobalORJRadioButtonActionPerformed(evt);
                            }
                        });

                        pnlAddTestStepDown.setBackground(new java.awt.Color(0, 0, 0));

                        AddStepDown.setBackground(new java.awt.Color(0, 0, 0));
                        AddStepDown.setFont(new Font("Consolas", 1, 14)); // NOI18N
                        AddStepDown.setForeground(new java.awt.Color(255, 255, 255));
                        AddStepDown.setIcon(new ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/addStepUpDown.png"));
                            AddStepDown.setText("Add Test Step Down");
                            AddStepDown.setToolTipText("will add a new test step below the selected step");
                            AddStepDown.setBorder(null);
                            AddStepDown.setBorderPainted(false);
                            AddStepDown.setContentAreaFilled(false);
                            AddStepDown.setFocusPainted(false);
                            AddStepDown.setFocusable(false);
                            AddStepDown.setHorizontalAlignment(SwingConstants.LEFT);
                            AddStepDown.setRequestFocusEnabled(false);
                            AddStepDown.setRolloverEnabled(false);
                            AddStepDown.addMouseListener(new MouseAdapter() {
                                public void mouseEntered(MouseEvent evt) {
                                    AddStepDownMouseEntered(evt);
                                }
                                public void mouseExited(MouseEvent evt) {
                                    AddStepDownMouseExited(evt);
                                }
                            });
                            AddStepDown.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent evt) {
                                    AddStepDownActionPerformed(evt);
                                }
                            });

                            GroupLayout pnlAddTestStepDownLayout = new GroupLayout(pnlAddTestStepDown);
                            pnlAddTestStepDown.setLayout(pnlAddTestStepDownLayout);
                            pnlAddTestStepDownLayout.setHorizontalGroup(pnlAddTestStepDownLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(AddStepDown, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            );
                            pnlAddTestStepDownLayout.setVerticalGroup(pnlAddTestStepDownLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(GroupLayout.Alignment.TRAILING, pnlAddTestStepDownLayout.createSequentialGroup()
                                    .addGap(0, 0, Short.MAX_VALUE)
                                    .addComponent(AddStepDown, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                            );

                            pnlSaveTestSuite.setBackground(new java.awt.Color(0, 0, 0));

                            SaveSuite.setBackground(new java.awt.Color(0, 0, 0));
                            SaveSuite.setFont(new Font("Consolas", 1, 14)); // NOI18N
                            SaveSuite.setForeground(new java.awt.Color(255, 255, 255));
                            SaveSuite.setIcon(new ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/saveTestSuite.png"));
                                SaveSuite.setText("Save Suite");
                                SaveSuite.setToolTipText("will save the updated test suite");
                                SaveSuite.setBorder(null);
                                SaveSuite.setBorderPainted(false);
                                SaveSuite.setContentAreaFilled(false);
                                SaveSuite.setFocusPainted(false);
                                SaveSuite.setFocusable(false);
                                SaveSuite.setHorizontalAlignment(SwingConstants.LEFT);
                                SaveSuite.setRequestFocusEnabled(false);
                                SaveSuite.setRolloverEnabled(false);
                                SaveSuite.addMouseListener(new MouseAdapter() {
                                    public void mouseEntered(MouseEvent evt) {
                                        SaveSuiteMouseEntered(evt);
                                    }
                                    public void mouseExited(MouseEvent evt) {
                                        SaveSuiteMouseExited(evt);
                                    }
                                });
                                SaveSuite.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent evt) {
                                        SaveSuiteActionPerformed(evt);
                                    }
                                });

                                GroupLayout pnlSaveTestSuiteLayout = new GroupLayout(pnlSaveTestSuite);
                                pnlSaveTestSuite.setLayout(pnlSaveTestSuiteLayout);
                                pnlSaveTestSuiteLayout.setHorizontalGroup(pnlSaveTestSuiteLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(SaveSuite, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                );
                                pnlSaveTestSuiteLayout.setVerticalGroup(pnlSaveTestSuiteLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addGroup(GroupLayout.Alignment.TRAILING, pnlSaveTestSuiteLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(SaveSuite, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                );

                                pnlAddNewTestStep.setBackground(new java.awt.Color(0, 0, 0));

                                AddNewStep.setBackground(new java.awt.Color(0, 0, 0));
                                AddNewStep.setFont(new Font("Consolas", 1, 14)); // NOI18N
                                AddNewStep.setForeground(new java.awt.Color(255, 255, 255));
                                AddNewStep.setIcon(new ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/addTestStep_Element.png"));
                                    AddNewStep.setText("Add New Test Step");
                                    AddNewStep.setToolTipText("will add a new test step at the bottom");
                                    AddNewStep.setBorder(null);
                                    AddNewStep.setBorderPainted(false);
                                    AddNewStep.setContentAreaFilled(false);
                                    AddNewStep.setFocusPainted(false);
                                    AddNewStep.setFocusable(false);
                                    AddNewStep.setHorizontalAlignment(SwingConstants.LEFT);
                                    AddNewStep.setRequestFocusEnabled(false);
                                    AddNewStep.setRolloverEnabled(false);
                                    AddNewStep.addMouseListener(new MouseAdapter() {
                                        public void mouseEntered(MouseEvent evt) {
                                            AddNewStepMouseEntered(evt);
                                        }
                                        public void mouseExited(MouseEvent evt) {
                                            AddNewStepMouseExited(evt);
                                        }
                                    });
                                    AddNewStep.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent evt) {
                                            AddNewStepActionPerformed(evt);
                                        }
                                    });

                                    GroupLayout pnlAddNewTestStepLayout = new GroupLayout(pnlAddNewTestStep);
                                    pnlAddNewTestStep.setLayout(pnlAddNewTestStepLayout);
                                    pnlAddNewTestStepLayout.setHorizontalGroup(pnlAddNewTestStepLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(AddNewStep, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    );
                                    pnlAddNewTestStepLayout.setVerticalGroup(pnlAddNewTestStepLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(pnlAddNewTestStepLayout.createSequentialGroup()
                                            .addComponent(AddNewStep, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                            .addGap(0, 0, Short.MAX_VALUE))
                                    );

                                    pnlOpenOR.setBackground(new java.awt.Color(0, 0, 0));

                                    OpenObjectRepository.setBackground(new java.awt.Color(0, 0, 0));
                                    OpenObjectRepository.setFont(new Font("Consolas", 1, 14)); // NOI18N
                                    OpenObjectRepository.setForeground(new java.awt.Color(255, 255, 255));
                                    OpenObjectRepository.setIcon(new ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/editObjectRepository.png"));
                                        OpenObjectRepository.setText("Edit OR");
                                        OpenObjectRepository.setToolTipText("will open the requested local or global object repositroy");
                                        OpenObjectRepository.setBorder(null);
                                        OpenObjectRepository.setBorderPainted(false);
                                        OpenObjectRepository.setContentAreaFilled(false);
                                        OpenObjectRepository.setFocusPainted(false);
                                        OpenObjectRepository.setFocusable(false);
                                        OpenObjectRepository.setHorizontalAlignment(SwingConstants.LEFT);
                                        OpenObjectRepository.setMaximumSize(new Dimension(117, 41));
                                        OpenObjectRepository.setMinimumSize(new Dimension(117, 41));
                                        OpenObjectRepository.setPreferredSize(new Dimension(117, 41));
                                        OpenObjectRepository.setRequestFocusEnabled(false);
                                        OpenObjectRepository.setRolloverEnabled(false);
                                        OpenObjectRepository.addMouseListener(new MouseAdapter() {
                                            public void mouseEntered(MouseEvent evt) {
                                                OpenObjectRepositoryMouseEntered(evt);
                                            }
                                            public void mouseExited(MouseEvent evt) {
                                                OpenObjectRepositoryMouseExited(evt);
                                            }
                                        });
                                        OpenObjectRepository.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent evt) {
                                                OpenObjectRepositoryActionPerformed(evt);
                                            }
                                        });

                                        GroupLayout pnlOpenORLayout = new GroupLayout(pnlOpenOR);
                                        pnlOpenOR.setLayout(pnlOpenORLayout);
                                        pnlOpenORLayout.setHorizontalGroup(pnlOpenORLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addComponent(OpenObjectRepository, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        );
                                        pnlOpenORLayout.setVerticalGroup(pnlOpenORLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addGroup(GroupLayout.Alignment.TRAILING, pnlOpenORLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(OpenObjectRepository, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                                        );

                                        GroupLayout pnlMenuBarLayout = new GroupLayout(pnlMenuBar);
                                        pnlMenuBar.setLayout(pnlMenuBarLayout);
                                        pnlMenuBarLayout.setHorizontalGroup(pnlMenuBarLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addGroup(pnlMenuBarLayout.createSequentialGroup()
                                                .addComponent(LocalORJRadioButton, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(GlobalORJRadioButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGroup(pnlMenuBarLayout.createSequentialGroup()
                                                .addGroup(pnlMenuBarLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(AssociateObjORJCheckBox, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(pnlOpenTestSuite, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(pnlAddTestStepUp, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(pnlAddTestStepDown, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(pnlDeleteTestStep, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(pnlSaveTestSuite, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(pnlAddNewTestStep, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(pnlSaveAsNewTestSuite, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(pnlOpenOR, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        );
                                        pnlMenuBarLayout.setVerticalGroup(pnlMenuBarLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addGroup(pnlMenuBarLayout.createSequentialGroup()
                                                .addGap(27, 27, 27)
                                                .addComponent(AssociateObjORJCheckBox, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(pnlOpenTestSuite, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(pnlAddTestStepUp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(pnlAddTestStepDown, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(13, 13, 13)
                                                .addComponent(pnlDeleteTestStep, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(pnlAddNewTestStep, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(11, 11, 11)
                                                .addComponent(pnlSaveTestSuite, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(pnlSaveAsNewTestSuite, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addGroup(pnlMenuBarLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                    .addComponent(GlobalORJRadioButton)
                                                    .addComponent(LocalORJRadioButton))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(pnlOpenOR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        );

                                        jDesktopPane1.setLayer(pnlMenuBar, JLayeredPane.DEFAULT_LAYER);

                                        GroupLayout jDesktopPane1Layout = new GroupLayout(jDesktopPane1);
                                        jDesktopPane1.setLayout(jDesktopPane1Layout);
                                        jDesktopPane1Layout.setHorizontalGroup(jDesktopPane1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                                .addGap(1, 1, 1)
                                                .addComponent(pnlMenuBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(1, 1, 1))
                                        );
                                        jDesktopPane1Layout.setVerticalGroup(jDesktopPane1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                                .addGap(1, 1, 1)
                                                .addComponent(pnlMenuBar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(1, 1, 1))
                                        );

                                        GroupLayout layout = new GroupLayout(getContentPane());
                                        getContentPane().setLayout(layout);
                                        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(pnlRegSuiteTable, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(2, 2, 2)
                                                .addComponent(jDesktopPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(1, 1, 1))
                                        );
                                        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(pnlRegSuiteTable, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(1, 1, 1))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jDesktopPane1)
                                                .addGap(1, 1, 1))
                                        );

                                        getAccessibleContext().setAccessibleParent(this);

                                        setSize(new Dimension(893, 505));
                                        setLocationRelativeTo(null);
                                    }// </editor-fold>//GEN-END:initComponents

    @SuppressWarnings("LocalVariableHidesMemberVariable")
    private void RegressionSuiteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_RegressionSuiteActionPerformed
        tableCellRenderer renderer = new tableCellRenderer();
        String getCurrDir;
        BufferedInputStream excelBIS;
        XSSFRow excelRow;
        comboBoxObjectRepository = new JComboBox<String>();
        
        tabOutFromEditingColumn(false, RegressionSuiteTable, 
                getFlowCellxPoint, 
                getFlowCellyPoint, 
                getEditingRow);
        
        try{
            File getCurrDirectory =excelFileImport.getCurrentDirectory();
            getCurrDir =getCurrDirectory.getAbsolutePath();
        }catch(NullPointerException exp){
            getCurrDir =FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
        }
        
        excelFileImport = new JFileChooser(getCurrDir);
        excelFileImport.setPreferredSize(new Dimension(450,300));
        excelFileImport.setFileSelectionMode(JFileChooser.FILES_ONLY);
        excelFileImport.setDialogTitle("Open Test Suite");
        excelFileImport.addChoosableFileFilter(new FileNameExtensionFilter("EXCEL WORKBOOK", "xlsx"));
        excelFileImport.setAcceptAllFileFilterUsed(false);
        
        int excelChooser = excelFileImport.showOpenDialog(this);
        if(excelChooser == JFileChooser.CANCEL_OPTION){
            return;
        }
        
        noRepoFound =false;
        
        if (excelChooser == JFileChooser.APPROVE_OPTION) {
            importDataFromExcelModel = (DefaultTableModel) RegressionSuiteTable.getModel();
            if (AssociateObjORJCheckBox.isSelected() == true) {
                testObjectRepoColumn.setCellEditor(new DefaultCellEditor(comboBoxObjectRepository));
                AssociateObjORJCheckBox.setSelected(false);
            }

            testSuiteUploaded = true;
            excelImportWorkBook = new XSSFWorkbook();
            importDataFromExcelModel.setRowCount(0);
            excelFile = excelFileImport.getSelectedFile();
            testSuiteFilePath = excelFile.getPath();

            try {
                try {
                    if (excelFIS.getChannel().isOpen());
                        excelFIS.close();
                } catch (NullPointerException exp) {
                    Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.INFO, "channel is closed", "channel is closed");
                }

                excelFIS = new FileInputStream(excelFile);
                excelBIS = new BufferedInputStream(excelFIS);

                excelImportWorkBook = new XSSFWorkbook(excelBIS);
                excelSheetTestFlow = excelImportWorkBook.getSheetAt(0);

                if (AssociateObjORJCheckBox.isSelected() == false) {
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
                }

                for (int i = 1; i <= excelSheetTestFlow.getLastRowNum(); i++) {
                    excelRow = excelSheetTestFlow.getRow(i);
                    try {
                        //XSSFCell run = excelRow.getCell(0);
                        XSSFCell testId = excelRow.getCell(0);
                        XSSFCell testStep = excelRow.getCell(1);
                        XSSFCell testFlow = excelRow.getCell(2);
                        XSSFCell testElement = excelRow.getCell(3);
                        XSSFCell testData = excelRow.getCell(4);
                        XSSFCell testDesc = excelRow.getCell(5);

                        importDataFromExcelModel.addRow(new Object[]{testId, testStep, testFlow, testElement, testData, testDesc});
                    } catch (NullPointerException exp) {

                    }
                }

                RegressionSuiteTable.setRowSelectionInterval(0, 0);
                RegressionSuiteTable.scrollRectToVisible(RegressionSuiteTable.getCellRect(0, 0, true));
                RegressionSuiteTable.requestFocus();
                
                if(objRepo.isVisible())
                    objRepo.dispose();
                
                /*if (objRepo.isVisible()) {
                    ObjectRepoFrame.importObjectRepoData.getDataVector().removeAllElements();
                    ObjectRepoFrame.importObjectRepoData.fireTableDataChanged();
                    objRepo.setTitle("Object Repository: " + excelFileImport.getName(excelFile));
                    objRepo.openObjectRepository(excelSheetObjectRepository);
                }*/
                this.setTitle("Edit Test Suite: " + excelFileImport.getName(excelFile));
            } catch (FileNotFoundException exp) {
                if (exp.getMessage().contains("The system cannot find the file specified")) {
                    JOptionPane.showMessageDialog(RegressionSuiteScrollPane, "No test suite " + "\"" + excelFileImport.getName(excelFile) + "\"" + " found to upload!", "Alert", JOptionPane.WARNING_MESSAGE);
                } else {
                    exp.printStackTrace();
                }
            } catch (IOException exp) {
                exp.printStackTrace();
            } catch (IllegalArgumentException exp) {
                if (exp.getMessage().contains("Row index out of range")) {
                    JOptionPane.showMessageDialog(RegressionSuiteScrollPane, "No test steps found in " + "\"" + excelFileImport.getName(excelFile) + "\"" + " test suite to upload!", "Alert", JOptionPane.WARNING_MESSAGE);
                    //excelFile = null;
                }
            }
        }

        //RegressionSuiteTable.setDefaultRenderer(Object.class, renderer);
        if (RegressionSuiteTable.getRowCount() > 0) {    
            if (AssociateObjORJCheckBox.isEnabled() == false) {
                AssociateObjORJCheckBox.setEnabled(true);
            }
        } else {
            if (AssociateObjORJCheckBox.isEnabled() == true) {
                AssociateObjORJCheckBox.setEnabled(false);
            }
        }
    }//GEN-LAST:event_RegressionSuiteActionPerformed
     
    public XSSFSheet getObjectRepositorySheet(String filePath, int orSheetIndex) {
        FileInputStream excelFIS;
        BufferedInputStream excelBIS;

        XSSFWorkbook excelImportWorkBook = new XSSFWorkbook();
        XSSFSheet objectRepoExcel = null;

        try {
            excelFIS = new FileInputStream(filePath);
            excelBIS = new BufferedInputStream(excelFIS);

            excelImportWorkBook = new XSSFWorkbook(excelBIS);
            objectRepoExcel = excelImportWorkBook.getSheetAt(orSheetIndex);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
        return objectRepoExcel;
    }
    
    private void OpenObjectRepositoryActionPerformed(ActionEvent evt) {//GEN-FIRST:event_OpenObjectRepositoryActionPerformed
        if(checkEditorIsShowing() ==true)
            tabOutFromEditingColumn(false, RegressionSuiteTable, 
            getFlowCellxPoint, 
            getFlowCellyPoint, 
            getEditingRow);
        
        if (objRepo.isVisible() == true) {
            objRepo.requestFocus();
            return;
        }

        testGlobalORFilePath = null;
        FileInputStream excelFIS;
        BufferedInputStream excelBIS;
        TableColumn testObjectRepoColumn = new TableColumn();
        JComboBox<String> comboBoxObjectRepository = new JComboBox<>();
        String getCurrDir;
        
        if (LocalORJRadioButton.isSelected()) {
            if (RegressionSuiteTable.getRowCount() > 0) {
                try {
                    excelFIS = new FileInputStream(testSuiteFilePath);
                    excelBIS = new BufferedInputStream(excelFIS);

                    excelImportWorkBook = new XSSFWorkbook(excelBIS);
                    excelSheetObjectRepository = excelImportWorkBook.getSheetAt(1);

                    objRepo.openObjectRepository(excelSheetObjectRepository);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    if (ex.getMessage().contains("Sheet index (1) is out of range")) {
                        JOptionPane.showMessageDialog(RegressionSuiteScrollPane, "No Object Repository found for the loaded test suite!", "Alert", JOptionPane.WARNING_MESSAGE);
                    }
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(RegressionSuiteScrollPane, "Upload required test suite to open the associated Object Repository!", "Alert", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } else if (GlobalORJRadioButton.isSelected()) {
            if (AssociateObjORJCheckBox.isSelected()) {
                excelSheetObjectRepositorySecodary = getObjectRepositorySheet(testGlobalORAssociatedFilePath, 0);
                objRepo.openObjectRepository(excelSheetObjectRepositorySecodary);
            } else if (!AssociateObjORJCheckBox.isSelected()) {
                try{
                    File getCurrDirectory =excelFileImportOR.getCurrentDirectory();
                    getCurrDir =getCurrDirectory.getAbsolutePath();
                }catch(NullPointerException exp){
                    getCurrDir =FileSystemView.getFileSystemView().getDefaultDirectory().getPath();

                }
                
                excelFileImportOR = new JFileChooser(getCurrDir);
                excelFileImportOR.setPreferredSize(new Dimension(450,300));
                excelFileImportOR.setFileSelectionMode(JFileChooser.FILES_ONLY);
                excelFileImportOR.setDialogTitle("Open Global Test Repository");
                excelFileImportOR.addChoosableFileFilter(new FileNameExtensionFilter("EXCEL WORKBOOK", "xlsx"));
                excelFileImportOR.setAcceptAllFileFilterUsed(false);

                int excelChooser = excelFileImportOR.showOpenDialog(this);
                //excelFileImport.setPreferredSize(new Dimension(450,300));
                if (excelChooser == JFileChooser.APPROVE_OPTION) {
                    try {

                        excelFileOR = excelFileImportOR.getSelectedFile();
                        testGlobalORFilePath = excelFileOR.getPath();

                        excelFIS = new FileInputStream(excelFileOR);
                        excelBIS = new BufferedInputStream(excelFIS);

                        XSSFWorkbook excelImportWorkBook = new XSSFWorkbook(excelBIS);
                        XSSFSheet excelSheetObjectRepositorySecodary = excelImportWorkBook.getSheetAt(0);

                        objRepo.openObjectRepository(excelSheetObjectRepositorySecodary);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    return;
                }
            }
        }
        //objRepo.setPreferredSize(new Dimension(589,330));
        //objRepo.pack();
        objRepo.setLocationRelativeTo(null);
        objRepo.setVisible(true);
        objRepo.setFocusable(true);
        try {
            if (GlobalORJRadioButton.isSelected()) {
                objRepo.setTitle("Object Repository: " + excelFileImportOR.getName(excelFileOR));
            } else {
                objRepo.setTitle("Object Repository: " + excelFileImport.getName(excelFile));
            }
        } catch (NullPointerException exp) {
            objRepo.setTitle("Object Repository: " + excelFileImportOR.getName(excelFileOR));
        }

        objRepo.setResizable(true);
    }//GEN-LAST:event_OpenObjectRepositoryActionPerformed
    
    private void formWindowActivated(WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        tableCellRenderer renderer = new tableCellRenderer();
        RegressionSuiteTable.setDefaultRenderer(Object.class, renderer);
    }//GEN-LAST:event_formWindowActivated

    private void LocalORJRadioButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_LocalORJRadioButtonActionPerformed
        if(LocalORJRadioButton.isSelected() ==true){
            GlobalORJRadioButton.setSelected(false);
        }
        else if(GlobalORJRadioButton.isSelected() ==false && LocalORJRadioButton.isSelected() ==false){
            LocalORJRadioButton.setSelected(true);
        }
        
        if (objRepo.isVisible()) {
            objRepo.dispose();
        }
    }//GEN-LAST:event_LocalORJRadioButtonActionPerformed

    private void GlobalORJRadioButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_GlobalORJRadioButtonActionPerformed
        if(GlobalORJRadioButton.isSelected() ==true){
            LocalORJRadioButton.setSelected(false);
        }
        else if(GlobalORJRadioButton.isSelected() ==false && LocalORJRadioButton.isSelected() ==false){
            GlobalORJRadioButton.setSelected(true);
        }

        if (objRepo.isVisible()) {
            objRepo.dispose();
        }
    }//GEN-LAST:event_GlobalORJRadioButtonActionPerformed

    private void AssociateObjORJCheckBoxActionPerformed(ActionEvent evt) {//GEN-FIRST:event_AssociateObjORJCheckBoxActionPerformed
        FileInputStream excelFIS;
        BufferedInputStream excelBIS;
        testObjectRepoColumn = new TableColumn();
        comboBoxObjectRepository = new JComboBox<>();
        testSuiteUploaded = false;
        testGlobalORAssociatedFilePath = null;
        String getCurrDir;
        
        tabOutFromEditingColumn(false, RegressionSuiteTable, 
                getFlowCellxPoint, 
                getFlowCellyPoint, 
                getEditingRow);
        
        if (AssociateObjORJCheckBox.isSelected()) {
            try{
                File getCurrDirectory =excelFileImportOR.getCurrentDirectory();
                getCurrDir =getCurrDirectory.getAbsolutePath();
            }catch(NullPointerException exp){
                getCurrDir =FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
            }
            
            excelFileImportOR = new JFileChooser(getCurrDir);
            excelFileImport.setPreferredSize(new Dimension(450,300));
            excelFileImportOR.setFileSelectionMode(JFileChooser.FILES_ONLY);
            excelFileImportOR.addChoosableFileFilter(new FileNameExtensionFilter("EXCEL WORKBOOK", "xlsx"));
            excelFileImportOR.setAcceptAllFileFilterUsed(false);
            excelFileImportOR.setDialogTitle("Associate Global Object Repository");

            int excelChooser = excelFileImportOR.showOpenDialog(this);

            if (excelChooser == JFileChooser.APPROVE_OPTION) {
                importDataFromExcelModel = (DefaultTableModel) RegressionSuiteTable.getModel();
                testSuiteUploaded = true;
                testObjectRepoColumn = RegressionSuiteTable.getColumnModel().getColumn(3);
                comboBoxObjectRepository = new JComboBox<>();
                comboBoxObjectRepository.setEditable(true);
    
                try {
                    excelFileOR = excelFileImportOR.getSelectedFile();
                    testGlobalORAssociatedFilePath = excelFileOR.getPath();
                    excelFIS = new FileInputStream(excelFileOR);
                    excelBIS = new BufferedInputStream(excelFIS);

                    excelImportWorkBook = new XSSFWorkbook(excelBIS);
                    excelSheetObjectRepositorySecodary = excelImportWorkBook.getSheetAt(0);
                    
                    if (excelSheetObjectRepositorySecodary.getLastRowNum() >0){
                        getObjectListFromObjectRepository(excelSheetObjectRepositorySecodary);
                        ObjectRepositoryList();
                        testObjectRepoColumn.setCellEditor(new DefaultCellEditor(comboBoxObjectRepository));
                    }else{
                        JOptionPane.showMessageDialog(RegressionSuiteScrollPane, "Required global object repository " + "\"" + excelFileImportOR.getName(excelFileOR) + "\"" + " is empty!", "Alert", JOptionPane.WARNING_MESSAGE);
                        AssociateObjORJCheckBox.setSelected(false);
                        return;
                    }
                    
                } catch (FileNotFoundException exp) {
                    if (exp.getMessage().contains("The system cannot find the file specified")) {
                        JOptionPane.showMessageDialog(RegressionSuiteScrollPane, "No global object repository " + "\"" + excelFileOR.getName() + "\"" + " found to associate!", "Alert", JOptionPane.WARNING_MESSAGE);
                        AssociateObjORJCheckBox.setSelected(false);
                    } else {
                        Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.SEVERE, null, exp);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (AssociateObjORJCheckBox.isSelected() == false) {
            try {
                if (noRepoFound == true) {
                    testObjectRepoColumn = RegressionSuiteTable.getColumnModel().getColumn(3);
                    testObjectRepoColumn.setCellEditor(new DefaultCellEditor(RegSuite.comboBoxObjectRepository));
                    RegSuite.comboBoxObjectRepository.setEditable(true);
                } else {
                    XSSFSheet getCurrSheet = RegSuite.getObjectRepositorySheet(EditRegressionSuite.testSuiteFilePath, 1);
                    RegSuite.getObjectListFromObjectRepository(getCurrSheet);
                    RegSuite.ObjectRepositoryList();

                    RegSuite.testObjectRepoColumn = EditRegressionSuite.RegressionSuiteTable.getColumnModel().getColumn(3);
                    RegSuite.testObjectRepoColumn.setCellEditor(new DefaultCellEditor(RegSuite.comboBoxObjectRepository));
                    RegSuite.comboBoxObjectRepository.setEditable(true);
                }
            } catch (IllegalArgumentException exp) {
                if (exp.getMessage().contains("Sheet index (1) is out of range")) {
                    JOptionPane.showMessageDialog(RegressionSuiteScrollPane, "No Object Repository found for the loaded test suite!", "Alert", JOptionPane.WARNING_MESSAGE);
                }
            }
        }

        if (testSuiteUploaded == false) {
            if (AssociateObjORJCheckBox.isSelected() == true) {
                AssociateObjORJCheckBox.setSelected(false);
            }
        }
    }//GEN-LAST:event_AssociateObjORJCheckBoxActionPerformed

    public CellStyle getHeaderStyle(XSSFWorkbook workBook, short index) {
        XSSFFont Headerfont = workBook.createFont();
        CellStyle headerStyle = workBook.createCellStyle();

        headerStyle.setFillBackgroundColor(IndexedColors.BLACK1.getIndex());
        headerStyle.setFillPattern(FillPatternType.ALT_BARS);

        Headerfont.setColor(index);
        Headerfont.setBold(true);

        headerStyle.setFont(Headerfont);

        return headerStyle;
    }

    private void formWindowOpened(WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Image titleIcon = Toolkit.getDefaultToolkit().getImage(constants.userDir+"\\icons\\bolt.jpg");
        this.setIconImage(titleIcon);
        setTableColWidthForCreateRegSuiteTable();
    }//GEN-LAST:event_formWindowOpened

    private void RegressionSuiteMouseEntered(MouseEvent evt) {//GEN-FIRST:event_RegressionSuiteMouseEntered
        pnlOpenTestSuite.setBackground(new java.awt.Color(250, 128, 114));
        RegressionSuite.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_RegressionSuiteMouseEntered

    private void RegressionSuiteMouseExited(MouseEvent evt) {//GEN-FIRST:event_RegressionSuiteMouseExited
        pnlOpenTestSuite.setBackground(new java.awt.Color(0, 0, 0));
        RegressionSuite.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_RegressionSuiteMouseExited

    private void OpenObjectRepositoryMouseEntered(MouseEvent evt) {//GEN-FIRST:event_OpenObjectRepositoryMouseEntered
        pnlOpenOR.setBackground(new java.awt.Color(250, 128, 114));
        OpenObjectRepository.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_OpenObjectRepositoryMouseEntered

    private void OpenObjectRepositoryMouseExited(MouseEvent evt) {//GEN-FIRST:event_OpenObjectRepositoryMouseExited
        pnlOpenOR.setBackground(new java.awt.Color(0, 0, 0));
        OpenObjectRepository.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_OpenObjectRepositoryMouseExited

    private void AssociateObjORJCheckBoxMouseEntered(MouseEvent evt) {//GEN-FIRST:event_AssociateObjORJCheckBoxMouseEntered
        AssociateObjORJCheckBox.setForeground(java.awt.Color.PINK);
    }//GEN-LAST:event_AssociateObjORJCheckBoxMouseEntered

    private void AssociateObjORJCheckBoxMouseExited(MouseEvent evt) {//GEN-FIRST:event_AssociateObjORJCheckBoxMouseExited
        AssociateObjORJCheckBox.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_AssociateObjORJCheckBoxMouseExited

    private void LocalORJRadioButtonMouseEntered(MouseEvent evt) {//GEN-FIRST:event_LocalORJRadioButtonMouseEntered
        LocalORJRadioButton.setForeground(java.awt.Color.PINK);
    }//GEN-LAST:event_LocalORJRadioButtonMouseEntered

    private void LocalORJRadioButtonMouseExited(MouseEvent evt) {//GEN-FIRST:event_LocalORJRadioButtonMouseExited
        LocalORJRadioButton.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_LocalORJRadioButtonMouseExited

    private void GlobalORJRadioButtonMouseEntered(MouseEvent evt) {//GEN-FIRST:event_GlobalORJRadioButtonMouseEntered
        GlobalORJRadioButton.setForeground(java.awt.Color.PINK);
    }//GEN-LAST:event_GlobalORJRadioButtonMouseEntered

    private void GlobalORJRadioButtonMouseExited(MouseEvent evt) {//GEN-FIRST:event_GlobalORJRadioButtonMouseExited
        GlobalORJRadioButton.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_GlobalORJRadioButtonMouseExited

    private void formWindowClosed(WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        if (objRepo.isVisible())
            objRepo.dispose();
        
        //AutomationTestRunner xFrame = new AutomationTestRunner();
        //xFrame.toFront();
    }//GEN-LAST:event_formWindowClosed

    private void SaveSuite1ActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SaveSuite1ActionPerformed
        if (RegressionSuiteTable.getRowCount() == 0) {
            JOptionPane.showMessageDialog(RegressionSuiteScrollPane, "No test suite available to Save As New Test Suite!", "Alert", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if(checkEditorIsShowing() ==true)
                tabOutFromEditingColumn(false, RegressionSuiteTable, 
                getFlowCellxPoint, 
                getFlowCellyPoint, 
                getEditingRow);
        
        if(common.checkForDuplicateTestId(importDataFromExcelModel, RegressionSuiteTable, editableRow, testIdTxt) ==true)
            return;
        
        FileOutputStream excelFos;
        XSSFWorkbook excelJTableExport = new XSSFWorkbook();
        boolean fileExist =false;
        String getCurrDir;
        
        try{
            File getCurrDirectory =excelFileExport.getCurrentDirectory();
            getCurrDir =getCurrDirectory.getAbsolutePath();
        }catch(NullPointerException exp){
            getCurrDir =FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
        }
         
        excelFileExport = new JFileChooser(getCurrDir);
        excelFileExport.setPreferredSize(new Dimension(450,300));
        excelFileExport.setDialogTitle("Save As New Test Suite");
        excelFileExport.setFileSelectionMode(JFileChooser.FILES_ONLY);

        excelFileExport.addChoosableFileFilter(new FileNameExtensionFilter("EXCEL WORKBOOK", "xlsx"));
        excelFileExport.setAcceptAllFileFilterUsed(false);

        int excelChooser = excelFileExport.showSaveDialog(this);
        if(excelChooser == JFileChooser.CANCEL_OPTION){
            return;
        }
        
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
                    SaveSuite1.doClick();
                    return;
                }
            }
        }
        
        try {
            // create test flow sheet
            excelJTableExport =createTestFlowDataSheet(excelJTableExport, importDataFromExcelModel);   
            // create test element repository sheet
            excelJTableExport =createObjectRepoDataSheet(excelJTableExport, importDataFromExcelModel, testSuiteFilePath);
            
            String getFilePath =null;
            if(fileExist ==true)
                getFilePath =excelFileExport.getSelectedFile().toString();
            else if(fileExist ==false)
                    getFilePath =excelFileExport.getSelectedFile()+".xlsx";
                            
            excelFos = new FileOutputStream(getFilePath);
            excelJTableExport.write(excelFos);

            excelFos.close();
            excelJTableExport.close();

            JOptionPane.showMessageDialog(RegressionSuiteScrollPane, "Test suite " + "\"" + excelFileExport.getSelectedFile().getName() + "\"" + " saved successfully!", "Alert", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.SEVERE, null, ex);
            if (ex.getMessage().contains("The process cannot access the file because it is being used by another process")) {
                int response;

                do {
                    response = JOptionPane.showConfirmDialog(RegressionSuiteScrollPane, //
                            "Close test suite " + "\"" + excelFileExport.getSelectedFile().getName() + "\"" + " to save the changes!", //
                            "Alert", JOptionPane.OK_CANCEL_OPTION, //
                            JOptionPane.WARNING_MESSAGE);

                    if (response == JOptionPane.OK_OPTION) {
                        try {
                            excelFos = new FileOutputStream(excelFileExport.getSelectedFile());
                            excelJTableExport.write(excelFos);

                            excelFos.close();
                            excelJTableExport.close();

                            JOptionPane.showMessageDialog(RegressionSuiteScrollPane, "Test suite " + "\"" + excelFileExport.getSelectedFile().getName() + "\"" + " saved successfully!", "Alert", JOptionPane.INFORMATION_MESSAGE);
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
    }//GEN-LAST:event_SaveSuite1ActionPerformed

    private void SaveSuite1MouseExited(MouseEvent evt) {//GEN-FIRST:event_SaveSuite1MouseExited
        pnlSaveAsNewTestSuite.setBackground(new java.awt.Color(0, 0, 0));
        SaveSuite1.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_SaveSuite1MouseExited

    private void SaveSuite1MouseEntered(MouseEvent evt) {//GEN-FIRST:event_SaveSuite1MouseEntered
        pnlSaveAsNewTestSuite.setBackground(new java.awt.Color(250, 128, 114));
        SaveSuite1.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_SaveSuite1MouseEntered

    private void SaveSuiteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SaveSuiteActionPerformed
        if (excelFile != null) {
            if(checkEditorIsShowing() ==true)
                tabOutFromEditingColumn(false, RegressionSuiteTable, 
                getFlowCellxPoint, 
                getFlowCellyPoint, 
                getEditingRow);
            
            if(common.checkForDuplicateTestId(importDataFromExcelModel, RegressionSuiteTable, editableRow, testIdTxt) ==true)
                return;
            
            try {
                SaveSuite.setEnabled(false);
                if (deleteAllTestSteps(excelFile, 0) == true) {
                    updateTestSuite(excelFile);
                    JOptionPane.showMessageDialog(RegressionSuiteScrollPane, "Test suite " + "\"" + excelFileImport.getName(excelFile) + "\"" + " updated and saved!", "Alert", JOptionPane.WARNING_MESSAGE);
                }
                SaveSuite.setEnabled(true);
            } catch (IOException ex) {
                Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else
            JOptionPane.showMessageDialog(RegressionSuiteScrollPane, "No test suite is available to save!", "Alert", JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_SaveSuiteActionPerformed

    private void SaveSuiteMouseExited(MouseEvent evt) {//GEN-FIRST:event_SaveSuiteMouseExited
        pnlSaveTestSuite.setBackground(new java.awt.Color(0, 0, 0));
        SaveSuite.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_SaveSuiteMouseExited

    private void SaveSuiteMouseEntered(MouseEvent evt) {//GEN-FIRST:event_SaveSuiteMouseEntered
        pnlSaveTestSuite.setBackground(new java.awt.Color(250, 128, 114));
        SaveSuite.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_SaveSuiteMouseEntered

    private void AddNewStepActionPerformed(ActionEvent evt) {//GEN-FIRST:event_AddNewStepActionPerformed
        boolean rowAdded = false;
        if (RegressionSuiteTable.getRowCount() > 0) {
            if(checkEditorIsShowing() ==true)
                tabOutFromEditingColumn(false, RegressionSuiteTable, 
                getFlowCellxPoint, 
                getFlowCellyPoint, 
                getEditingRow);
            
            if(common.checkForDuplicateTestId(importDataFromExcelModel, RegressionSuiteTable, editableRow, testIdTxt) ==true)
                return;
            
            importDataFromExcelModel.addRow(new Object[]{null, null, null, null, null, null});
            RegressionSuiteTable.setColumnSelectionInterval(0, 0);
            RegressionSuiteTable.setRowSelectionInterval(RegressionSuiteTable.getRowCount() - 1, RegressionSuiteTable.getRowCount() - 1);
            RegressionSuiteTable.scrollRectToVisible(RegressionSuiteTable.getCellRect(RegressionSuiteTable.getRowCount() - 1, 0, true));
            RegressionSuiteTable.requestFocus();
            rowAdded = true;
        } else {
            if (testSuiteFilePath == null) {
                JOptionPane.showMessageDialog(RegressionSuiteScrollPane, "No test suite available to add new step(s)!", "Alert", JOptionPane.WARNING_MESSAGE);
            } else {
                importDataFromExcelModel.addRow(new Object[]{null, null, null, null, null, null});
                RegressionSuiteTable.setColumnSelectionInterval(0, 0);
                RegressionSuiteTable.setRowSelectionInterval(RegressionSuiteTable.getRowCount() - 1, RegressionSuiteTable.getRowCount() - 1);
                RegressionSuiteTable.scrollRectToVisible(RegressionSuiteTable.getCellRect(RegressionSuiteTable.getRowCount() - 1, 0, true));
                RegressionSuiteTable.requestFocus();
                rowAdded = true;
            }
            //JOptionPane.showMessageDialog(RegressionSuiteScrollPane,"No test step(s) available to add new step up!","Alert",JOptionPane.WARNING_MESSAGE);
        }

        if (rowAdded == true) {
            Object getPreviousRowData = 0;
            try {
                getPreviousRowData = RegressionSuiteTable.getValueAt(RegressionSuiteTable.getSelectedRow() - 1, 1);
            } catch (ArrayIndexOutOfBoundsException exp) {
                getPreviousRowData = 0;
            }
            RegressionSuiteTable.setValueAt(Integer.valueOf(getPreviousRowData.toString()) + 1, RegressionSuiteTable.getRowCount() - 1, 1);
        }
    }//GEN-LAST:event_AddNewStepActionPerformed

    private void AddNewStepMouseExited(MouseEvent evt) {//GEN-FIRST:event_AddNewStepMouseExited
        pnlAddNewTestStep.setBackground(new java.awt.Color(0, 0, 0));
        AddNewStep.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_AddNewStepMouseExited

    private void AddNewStepMouseEntered(MouseEvent evt) {//GEN-FIRST:event_AddNewStepMouseEntered
        pnlAddNewTestStep.setBackground(new java.awt.Color(250, 128, 114));
        AddNewStep.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_AddNewStepMouseEntered

    private void DeleteStepActionPerformed(ActionEvent evt) {//GEN-FIRST:event_DeleteStepActionPerformed
        if (RegressionSuiteTable.getRowCount() > 0) {           
            
            if(checkEditorIsShowing() ==true)
                tabOutFromEditingColumn(false, RegressionSuiteTable, 
                getFlowCellxPoint, 
                getFlowCellyPoint, 
                getEditingRow);
                
            if(common.checkForDuplicateTestId(importDataFromExcelModel, RegressionSuiteTable, editableRow, testIdTxt) ==true)
                return;
            
            int rowIndex =RegressionSuiteTable.getSelectedRow();
            int colIndex =getEditingColumn;
                    
            Object getSelTestStep =null;
            String getTestId =null;
            String getTestIdx =null;
            String getNextStepVal ="";
            
            boolean diffTestId =false;
            boolean lastRowSelected =false;
            
            try {
                getTestId =RegressionSuiteTable.getValueAt(rowIndex, 0).toString();
            } catch (NullPointerException exp) {
                getTestId ="";
            }
            
            try {
                getTestIdx =importDataFromExcelModel.getValueAt(RegressionSuiteTable.getSelectedRow() + 1, 0).toString();                
            } catch (ArrayIndexOutOfBoundsException | NullPointerException exp) {
                getTestIdx ="";
            }
            
            if (!getTestIdx.isEmpty() && !getTestIdx.contentEquals("#")) {
                if (!getTestId.contentEquals(getTestIdx)) {
                    diffTestId =true;
                }
            }
            getSelTestStep = importDataFromExcelModel.getValueAt(RegressionSuiteTable.getSelectedRow(), 1);
            int newTestStep = Integer.valueOf(String.valueOf(getSelTestStep));
            importDataFromExcelModel.removeRow(rowIndex);
            
            if (RegressionSuiteTable.getRowCount() != 0) {
                if (RegressionSuiteTable.getRowCount() ==rowIndex) {
                    rowIndex =rowIndex-1;
                    lastRowSelected =true;
                }
                RegressionSuiteTable.requestFocus();
                RegressionSuiteTable.setColumnSelectionInterval(0, 0);
                RegressionSuiteTable.setRowSelectionInterval(rowIndex, rowIndex);
                RegressionSuiteTable.scrollRectToVisible(RegressionSuiteTable.getCellRect(rowIndex,rowIndex, true));
                
                if(lastRowSelected ==true)
                    return;
               
                if (getTestId ==null || !getTestId.isEmpty() && !getTestId.contentEquals("#")) {
                    try{
                        try{
                            getNextStepVal =importDataFromExcelModel.getValueAt(RegressionSuiteTable.getSelectedRow() + 1, 0).toString();
                        }catch (NullPointerException exp){
                            getNextStepVal ="";
                        }
                        
                        if (getNextStepVal.isEmpty()
                            || !getTestIdx.contentEquals(getTestId)) {
                        if (diffTestId == true) {
                            getTestId = getTestIdx;
                        }
                        RegressionSuiteTable.setValueAt(getTestId, RegressionSuiteTable.getSelectedRow(), 0);
                    }
                    }catch (ArrayIndexOutOfBoundsException exp){ 
                        RegressionSuiteTable.setValueAt(getTestId, RegressionSuiteTable.getSelectedRow(), 0);
                    }
                }
                
                for (int i = RegressionSuiteTable.getSelectedRow(); i <RegressionSuiteTable.getRowCount(); i++) {
                    RegressionSuiteTable.getModel().setValueAt(newTestStep,i , 1);
                    try{
                        try{
                            getNextStepVal =RegressionSuiteTable.getModel().getValueAt(i + 1, 0).toString();
                        }catch(NullPointerException exp){
                            getNextStepVal ="";
                        }
                        
                        if (RegressionSuiteTable.getModel().getValueAt(i, 0) != null && 
                                !getNextStepVal.isEmpty() &&
                                !getNextStepVal.contentEquals("#")) {
                            break;
                        } else if (RegressionSuiteTable.getModel().getValueAt(i, 1) == null || 
                                   RegressionSuiteTable.getModel().getValueAt(i + 1, 1).toString().isEmpty()) {
                            break;
                        }
                    }catch (ArrayIndexOutOfBoundsException exp){
                        RegressionSuiteTable.getModel().setValueAt(newTestStep, i, 1);
                    }
                    newTestStep++;
                }
            }
        } else {
            if (testSuiteFilePath == null) {
                JOptionPane.showMessageDialog(RegressionSuiteScrollPane, "No test suite available to delete step(s)!", "Alert", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(RegressionSuiteScrollPane, "No test step(s) available to delete!", "Alert", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_DeleteStepActionPerformed

    private void DeleteStepMouseExited(MouseEvent evt) {//GEN-FIRST:event_DeleteStepMouseExited
        pnlDeleteTestStep.setBackground(new java.awt.Color(0, 0, 0));
        DeleteStep.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_DeleteStepMouseExited

    private void DeleteStepMouseEntered(MouseEvent evt) {//GEN-FIRST:event_DeleteStepMouseEntered
        pnlDeleteTestStep.setBackground(new java.awt.Color(250, 128, 114));
        DeleteStep.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_DeleteStepMouseEntered

    private void AddStepDownActionPerformed(ActionEvent evt) {//GEN-FIRST:event_AddStepDownActionPerformed
        if (RegressionSuiteTable.getRowCount() > 0) {
            if(checkEditorIsShowing() ==true)
                tabOutFromEditingColumn(false, RegressionSuiteTable, 
                getFlowCellxPoint, 
                getFlowCellyPoint, 
                getEditingRow);
            
            if(common.checkForDuplicateTestId(importDataFromExcelModel, RegressionSuiteTable, editableRow, testIdTxt) ==true)
                return;
            
            int rowIndex = RegressionSuiteTable.getSelectedRow();
            if (rowIndex != -1) {
                try {
                    try {
                        String getTestID = RegressionSuiteTable.getValueAt(RegressionSuiteTable.getSelectedRow(), 0).toString();
                        String getTestStep = RegressionSuiteTable.getValueAt(RegressionSuiteTable.getSelectedRow(), 1).toString();

                        if ((getTestID.isEmpty()) && (getTestStep.isEmpty())) {
                            return;
                        } else {
                            importDataFromExcelModel.insertRow(rowIndex + 1, new Object[]{null, null, null, null, null, null});
                            rowIndex = RegressionSuiteTable.getSelectedRow();
                            RegressionSuiteTable.setColumnSelectionInterval(0, 0);
                            RegressionSuiteTable.setRowSelectionInterval(rowIndex + 1, rowIndex + 1);
                            RegressionSuiteTable.scrollRectToVisible(RegressionSuiteTable.getCellRect(rowIndex + 1, 0, true));
                            RegressionSuiteTable.requestFocus();
                        }
                    } catch (NullPointerException exp) {
                        importDataFromExcelModel.insertRow(rowIndex + 1, new Object[]{null, null, null, null, null, null});
                        rowIndex = RegressionSuiteTable.getSelectedRow();
                        RegressionSuiteTable.setColumnSelectionInterval(0, 0);
                        RegressionSuiteTable.setRowSelectionInterval(rowIndex + 1, rowIndex + 1);
                        RegressionSuiteTable.scrollRectToVisible(RegressionSuiteTable.getCellRect(rowIndex + 1, 0, true));
                        RegressionSuiteTable.requestFocus();
                    }
                } catch (NullPointerException exp) {
                    importDataFromExcelModel.insertRow(rowIndex + 1, new Object[]{null, null, null, null, null, null});
                    rowIndex = RegressionSuiteTable.getSelectedRow();
                    RegressionSuiteTable.setColumnSelectionInterval(0, 0);
                    RegressionSuiteTable.setRowSelectionInterval(rowIndex + 1, rowIndex + 1);
                    RegressionSuiteTable.scrollRectToVisible(RegressionSuiteTable.getCellRect(rowIndex + 1, 0, true));
                    RegressionSuiteTable.requestFocus();
                }

            } else {
                JOptionPane.showMessageDialog(RegressionSuiteScrollPane, "Select row to add test step!", "Alert", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } else {
            if (testSuiteFilePath == null) {
                JOptionPane.showMessageDialog(RegressionSuiteScrollPane, "No test suite available to add new step(s)!", "Alert", JOptionPane.WARNING_MESSAGE);
                return;
            } else {
                JOptionPane.showMessageDialog(RegressionSuiteScrollPane, "No test step(s) available to add a new step down!", "Alert", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }
    }//GEN-LAST:event_AddStepDownActionPerformed

    private void AddStepDownMouseExited(MouseEvent evt) {//GEN-FIRST:event_AddStepDownMouseExited
        pnlAddTestStepDown.setBackground(new java.awt.Color(0, 0, 0));
        AddStepDown.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_AddStepDownMouseExited

    private void AddStepDownMouseEntered(MouseEvent evt) {//GEN-FIRST:event_AddStepDownMouseEntered
        pnlAddTestStepDown.setBackground(new java.awt.Color(250, 128, 114));
        AddStepDown.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_AddStepDownMouseEntered

    private void AddStepUpActionPerformed(ActionEvent evt) {//GEN-FIRST:event_AddStepUpActionPerformed
        int getTestStep =0;
        if(RegressionSuiteTable.getRowCount()>0){
            if(checkEditorIsShowing() ==true)
                tabOutFromEditingColumn(false, RegressionSuiteTable, 
                getFlowCellxPoint, 
                getFlowCellyPoint, 
                getEditingRow);
            
            if(common.checkForDuplicateTestId(importDataFromExcelModel, RegressionSuiteTable, editableRow, testIdTxt) ==true)
                return;
            
            int rowIndex = RegressionSuiteTable.getSelectedRow();
            if(rowIndex !=-1){
                rowIndex = RegressionSuiteTable.getSelectedRow();
                String getTestId = null;
                    
                try{
                    getTestId = RegressionSuiteTable.getValueAt(rowIndex, 0).toString();
                }catch(NullPointerException exp){
                    getTestId ="";
                }
                        
                if(rowIndex ==0)
                    getTestStep = Integer.valueOf(RegressionSuiteTable.getValueAt(rowIndex, 1).toString());
                else
                    getTestStep = Integer.valueOf(RegressionSuiteTable.getValueAt(rowIndex-1, 1).toString())+1;
                    
                importDataFromExcelModel.insertRow(rowIndex, new Object[] {null,null,null,null,null,null });
                RegressionSuiteTable.setColumnSelectionInterval(0, 0);
                RegressionSuiteTable.setRowSelectionInterval(rowIndex, rowIndex);
                RegressionSuiteTable.scrollRectToVisible(RegressionSuiteTable.getCellRect(rowIndex,0, true));
                RegressionSuiteTable.requestFocus();
                    
                for(int i=rowIndex; i<RegressionSuiteTable.getRowCount(); i++ ){
                    try{
                        if(!RegressionSuiteTable.getValueAt(i, 0).toString().isEmpty())
                            break;
                    }catch(NullPointerException exp){

                    }
                    RegressionSuiteTable.setValueAt(getTestStep++, i, 1);
                }
                    
                if(!getTestId.isEmpty()){
                    RegressionSuiteTable.setValueAt(getTestId, RegressionSuiteTable.getSelectedRow(), 0);
                    RegressionSuiteTable.setValueAt("", RegressionSuiteTable.getSelectedRow()+1, 0);
                }
        
            }else
                  JOptionPane.showMessageDialog(RegressionSuiteScrollPane,"Select row to add test step!","Alert",JOptionPane.WARNING_MESSAGE);
        }else
            JOptionPane.showMessageDialog(RegressionSuiteScrollPane,"No test step(s) available to add a new step up!","Alert",JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_AddStepUpActionPerformed
    
    //private void comboBoxTestFlowPopupMenuWillBecomeVisible(PopupMenuEvent evt) {                                                      
       
    //}
    
    private void AddStepUpMouseExited(MouseEvent evt) {//GEN-FIRST:event_AddStepUpMouseExited
        pnlAddTestStepUp.setBackground(new java.awt.Color(0, 0, 0));
        AddStepUp.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_AddStepUpMouseExited

    private void AddStepUpMouseEntered(MouseEvent evt) {//GEN-FIRST:event_AddStepUpMouseEntered
        pnlAddTestStepUp.setBackground(new java.awt.Color(250, 128, 114));
        AddStepUp.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_AddStepUpMouseEntered

    private void RegressionSuiteTableMousePressed(MouseEvent evt) {//GEN-FIRST:event_RegressionSuiteTableMousePressed
        getFlowCellxPoint =RegressionSuiteTable.rowAtPoint(evt.getPoint());
        getFlowCellyPoint =RegressionSuiteTable.columnAtPoint(evt.getPoint());
        getEditingRow =RegressionSuiteTable.getEditingRow();
        
        if(getFlowCellyPoint ==2){
           comboBoxTestFlow.dispatchEvent(new KeyEvent(RegressionSuiteTable,KeyEvent.KEY_PRESSED, getEditingRow,2,KeyEvent.VK_DOWN, ' '));
        }     
        
        try{
            getSelRowTestFlow =RegressionSuiteTable.getValueAt(getFlowCellxPoint, 2).toString();
        }catch(NullPointerException | ArrayIndexOutOfBoundsException exp){
            
        }
        
        int getCurRow = RegressionSuiteTable.convertRowIndexToModel(RegressionSuiteTable.rowAtPoint(evt.getPoint()));
        int gerCurrCol = RegressionSuiteTable.convertColumnIndexToModel(RegressionSuiteTable.columnAtPoint(evt.getPoint()));
        
        RegressionSuiteTable.requestFocus();
        RegressionSuiteTable.setRowSelectionInterval(getFlowCellxPoint, getFlowCellxPoint);
        RegressionSuiteTable.scrollRectToVisible(RegressionSuiteTable.getCellRect(getFlowCellxPoint,getFlowCellxPoint, true));
                            
        if(common.checkForDuplicateTestId(importDataFromExcelModel, RegressionSuiteTable, editableRow, testIdTxt) ==true)
            return;
        
        if(duplicateTestId ==false){
            switch (gerCurrCol) {
                case 0:
                    RegressionSuiteTable.editCellAt(getCurRow, 0);
                    editableRow =RegressionSuiteTable.getEditingRow();
                    testIdTxt.requestFocusInWindow();
                    break;
                case 2:
                    RegressionSuiteTable.editCellAt(getCurRow, 2);
                    comboBoxTestFlow.requestFocusInWindow();
                    break;
                case 3:
                    RegressionSuiteTable.editCellAt(getCurRow, 3);
                    comboBoxObjectRepository.requestFocusInWindow();
                    break;
                case 4:
                    RegressionSuiteTable.editCellAt(getCurRow, 4);
                    testDataTxt.requestFocusInWindow();
                    break;
                case 5:
                    RegressionSuiteTable.editCellAt(getCurRow, 5);
                    testDescTxt.requestFocusInWindow();
                    break;
                default:
                    break;
            }
        }
    }//GEN-LAST:event_RegressionSuiteTableMousePressed

    private void RegressionSuiteTableKeyReleased(KeyEvent evt) {//GEN-FIRST:event_RegressionSuiteTableKeyReleased
        common.checkForDuplicateTestId(importDataFromExcelModel, RegressionSuiteTable, editableRow, testIdTxt);
    }//GEN-LAST:event_RegressionSuiteTableKeyReleased

    private void RegressionSuiteTableFocusGained(FocusEvent evt) {//GEN-FIRST:event_RegressionSuiteTableFocusGained
        /*testObjectRepoColumn = RegressionSuiteTable.getColumnModel().getColumn(3);
        testObjectRepoColumn.setCellEditor(new DefaultCellEditor(comboBoxObjectRepository));
        comboBoxObjectRepository.setEditable(true);*/
    }//GEN-LAST:event_RegressionSuiteTableFocusGained

    private void RegressionSuiteTableMouseReleased(MouseEvent evt) {//GEN-FIRST:event_RegressionSuiteTableMouseReleased
        getFlowCellxPoint =RegressionSuiteTable.rowAtPoint(evt.getPoint());
        getFlowCellyPoint =RegressionSuiteTable.columnAtPoint(evt.getPoint());
        getEditingRow =RegressionSuiteTable.getEditingRow();
        getEditingColumn =RegressionSuiteTable.getEditingColumn();
    }//GEN-LAST:event_RegressionSuiteTableMouseReleased

    private void RegressionSuiteTableMouseClicked(MouseEvent evt) {//GEN-FIRST:event_RegressionSuiteTableMouseClicked
        /*testObjectRepoColumn = RegressionSuiteTable.getColumnModel().getColumn(3);
        testObjectRepoColumn.setCellEditor(new DefaultCellEditor(comboBoxObjectRepository));
        comboBoxObjectRepository.setEditable(true);*/
    }//GEN-LAST:event_RegressionSuiteTableMouseClicked

    private void formWindowGainedFocus(WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        testObjectRepoColumn = RegressionSuiteTable.getColumnModel().getColumn(3);
        testObjectRepoColumn.setCellEditor(new DefaultCellEditor(comboBoxObjectRepository));
        comboBoxObjectRepository.setEditable(true);
        
        if(noRepoFound ==true)
            AssociateObjORJCheckBox.setEnabled(true);
        else if(noRepoFound ==false)
            AssociateObjORJCheckBox.setEnabled(false);
    }//GEN-LAST:event_formWindowGainedFocus
    
    public static void tabOutFromEditingColumn(boolean editingColStatus, JTable myTable, int xCellPoint, int yCellPoint, int selectedRow){
        //if(editingColStatus ==true){
            Component getCellComp = myTable.getComponentAt(xCellPoint, yCellPoint);
            if(getCellComp !=null){
                myTable.setFocusable(true);
                getCellComp.dispatchEvent(new KeyEvent(getCellComp,KeyEvent.KEY_PRESSED, selectedRow,0,KeyEvent.VK_TAB, ' '));
                if(selectedRow >=0)
                    myTable.setRowSelectionInterval(selectedRow, selectedRow);
                    myTable.requestFocus();
                    myTable.scrollRectToVisible(myTable.getCellRect(selectedRow,selectedRow, true));
            }
        //}
    }
    
    public static void updateTestSuite(File excelFile) {
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(excelFile);
            XSSFWorkbook wbSuite = new XSSFWorkbook(fis);
            XSSFSheet excelSheetTestFlow = wbSuite.getSheetAt(0);
            XSSFCell cell = null;
            XSSFDataFormat format = wbSuite.createDataFormat();

            XSSFFont font = wbSuite.createFont();
            font.setColor(IndexedColors.WHITE.getIndex());
            XSSFCellStyle cellStyle = wbSuite.createCellStyle();
            cellStyle.setFont(font);

            cellStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
            cellStyle.setFillPattern(FillPatternType.ALT_BARS);

            for (int j = 0; j < importDataFromExcelModel.getRowCount(); j++) {
                XSSFRow row = excelSheetTestFlow.createRow(j + 1);
                for (int k = 0; k < importDataFromExcelModel.getColumnCount(); k++) {
                    cell = row.createCell(k);
                    cell.setCellType(CellType.STRING);
                    cell.setCellStyle(cellStyle);
                    try {
                        cell.setCellValue(importDataFromExcelModel.getValueAt(j, k).toString());
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

    @SuppressWarnings("LocalVariableHidesMemberVariable")
    public boolean deleteAllTestSteps(File excelFileSuite, int sheetIndex) throws IOException {
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

    public static void keywordList(JComboBox<String> cBoxTestFlow) {
        String keywordlist = "URL,"
                + "SWITCH_WINDOW,"
                + "SWITCH_IFRAME,"
                + "SWITCH_DEFAULT,"
                + "ASSERT_VISIBLE,"
                + "ASSERT_SELECTED,"
                + "ASSERT_NOT_SELECTED,"
                + "SELECT_BY_VALUE,"
                + "SELECT_BY_INDEX,"
                + "SELECT_BY_VTEXT,"
                + "ALERT_SUBMIT,"
                + "ALERT_CANCEL,"
                + "HARD_WAIT,"
                + "ALERT_SEND_TEXT,"
                + "SET,"
                + "CLEAR,"
                + "GET,"
                + "CLICK,"
                + "DOUBLE_CLICK,"
                + "PRESS_KEY,"
                + "KEY_EVENTS,"
                + "ROBOT_EVENTS,"
                + "MOVE_TO_ELEMENT,"
                + "ASSERT_CLICKABLE,"
                + "OPEN_NEW_WINDOW,"
                + "MOVE_TO_WINDOW,"
                + "ASSERT_TEXT,"
                + "TAKE_SCREENSHOT,"
                + "ROBOT_SCREENSHOT,"
                + "MOUSE_HOVER,"
                + "UPLOAD_FILE,"
                + "USER_DEFINE,"
                + ":START,:GET,:IF,:THEN,:STOP,";
              
        String[] keywordList = keywordlist.split(",");
        Arrays.sort(keywordList);
         
        for (String txt : keywordList) {
            cBoxTestFlow.addItem(txt);
        }
    }

    public void ObjectRepositoryList() {
        String objectrepositorylist = objectRepositoryList;
        
        String[] keywordList = objectrepositorylist.split(",");
        for (String txt : keywordList) {
            comboBoxObjectRepository.addItem(txt);
        }
    }

    public void updateLocalObjectRepository() throws IOException {
        if (LocalORJRadioButton.isSelected()) {
            if (deleteAllTestSteps(excelFile, 1) == true) {
                //testObjectRepoColumn = null;
                testObjectRepoColumn = new TableColumn();
                comboBoxObjectRepository = new JComboBox<>();

                updateObjectRepository(excelFile, 1);
                testObjectRepoColumn = RegressionSuiteTable.getColumnModel().getColumn(3);
                testObjectRepoColumn.setCellEditor(new DefaultCellEditor(comboBoxObjectRepository));
                JOptionPane.showMessageDialog(RegressionSuiteScrollPane, "Test suite " + "\"" + excelFileImport.getName(excelFile) + "\"" + " updated and saved!", "Alert", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public void getLocalObjectRepTableModel() {

        try {
            FileInputStream excelFIS;
            BufferedInputStream excelBIS;

            importDataFromExcelModelORBackup = new DefaultTableModel();
            objRepo.ObjectRepoTable.removeAll();
            importDataFromExcelModelORBackup = (DefaultTableModel) objRepo.ObjectRepoTable.getModel();

            excelFIS = new FileInputStream(testSuiteFilePath);
            excelBIS = new BufferedInputStream(excelFIS);

            XSSFWorkbook excelImportWorkBook = new XSSFWorkbook();
            XSSFSheet excelSheetObjectRepository;

            excelImportWorkBook = new XSSFWorkbook(excelBIS);
            excelSheetObjectRepository = excelImportWorkBook.getSheetAt(1);

            for (int i = 1; i <= excelSheetObjectRepository.getLastRowNum(); i++) {
                XSSFRow excelRow = excelSheetObjectRepository.getRow(i);

                XSSFCell testElement = excelRow.getCell(0);
                XSSFCell elmId = excelRow.getCell(1);
                XSSFCell elmXpath = excelRow.getCell(2);

                importDataFromExcelModelORBackup.addRow(new Object[]{testElement, elmId, elmXpath});
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getObjectListFromObjectRepository(XSSFSheet suiteObjectRepo) {
        objectRepositoryList = "";

        for (int i = 1; i <= suiteObjectRepo.getLastRowNum(); i++) {
            XSSFRow excelRow = suiteObjectRepo.getRow(i);
            try {
                objectRepositoryList = objectRepositoryList + excelRow.getCell(0).toString() + ",";
            } catch (NullPointerException exp) {
                //Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.SEVERE, null, exp);
            }
        }
    }

    public void getObjectListFromObjectRepositoryGlobal() {
        objectRepositoryList = "";
        try {
            for (int i = 1; i <= excelSheetObjectRepositoryOR.getLastRowNum(); i++) {
                XSSFRow excelRow = excelSheetObjectRepositoryOR.getRow(i);
                objectRepositoryList = objectRepositoryList + excelRow.getCell(0).toString() + ",";
            }
        } catch (NullPointerException exp) {
            //JOptionPane.showMessageDialog(RegressionSuiteScrollPane,"No Object Repository found for the loaded test suite!","Alert",JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public static void setTableColWidthForCreateRegSuiteTable(){
        RegressionSuiteTable.getColumnModel().getColumn(0).setMaxWidth(61);
        RegressionSuiteTable.getColumnModel().getColumn(0).setMinWidth(61);
        
        RegressionSuiteTable.getColumnModel().getColumn(1).setMaxWidth(72);
        RegressionSuiteTable.getColumnModel().getColumn(1).setMinWidth(72);
        
        RegressionSuiteTable.getColumnModel().getColumn(2).setMaxWidth(163);
        RegressionSuiteTable.getColumnModel().getColumn(2).setMinWidth(163);
        
        RegressionSuiteTable.getColumnModel().getColumn(3).setMaxWidth(173);
        RegressionSuiteTable.getColumnModel().getColumn(3).setMinWidth(173);
    }
    
    public static boolean checkEditorIsShowing(){
        boolean editorIsShowing =false;
        
        if(testIdTxt.isShowing() ||
                testDataTxt.isShowing() ||
                testDescTxt.isShowing() ||
                comboBoxTestFlow.isShowing() ||
                comboBoxObjectRepository.isShowing()){
            
            editorIsShowing =true;
        }
            
        return editorIsShowing;
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditRegressionSuite.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditRegressionSuite.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditRegressionSuite.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditRegressionSuite.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //System.setProperty("java.awt.headless", "false");
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new EditRegressionSuite().setVisible(true);
            System.out.println(new EditRegressionSuite().getWidth());
            System.out.println(new EditRegressionSuite().getHeight());
            //new EditRegressionSuite().setPreferredSize(new Dimension(959, 456));
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static JButton AddNewStep;
    public static JButton AddStepDown;
    public static JButton AddStepUp;
    public static JCheckBox AssociateObjORJCheckBox;
    public static JButton DeleteStep;
    public static JRadioButton GlobalORJRadioButton;
    public static JRadioButton LocalORJRadioButton;
    public static JButton OpenObjectRepository;
    public static JButton RegressionSuite;
    public static JScrollPane RegressionSuiteScrollPane;
    public static JTable RegressionSuiteTable;
    public static JButton SaveSuite;
    public static JButton SaveSuite1;
    public JDesktopPane jDesktopPane1;
    public JPanel pnlAddNewTestStep;
    public JPanel pnlAddTestStepDown;
    public JPanel pnlAddTestStepUp;
    public JPanel pnlDeleteTestStep;
    public JPanel pnlMenuBar;
    public JPanel pnlOpenOR;
    public JPanel pnlOpenTestSuite;
    public JPanel pnlRegSuiteTable;
    public JPanel pnlSaveAsNewTestSuite;
    public JPanel pnlSaveTestSuite;
    // End of variables declaration//GEN-END:variables

    private void contentEquals(String testId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
