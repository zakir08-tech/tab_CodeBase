/*
 * To change this license header, choose License Headers in Project Properties.addMouseListener
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.excelSheetObjectRepositoryOR
 */
package com.automation.bolt.gui;

//import static com.automation.bolt.gui.ObjectRepoFrame.ObjectRepoTable;
import com.automation.bolt.boltExecutor;
import static com.automation.bolt.boltExecutor.getErrorMessage;
import static com.automation.bolt.boltExecutor.testRunInProgress;
import static com.automation.bolt.boltRunner.getCurrRunId;
import static com.automation.bolt.common.killProcess;
import com.automation.bolt.constants;
import com.automation.bolt.glueCode;
import static com.automation.bolt.gui.ExecuteApiTest.*;
import com.automation.bolt.renderer.*;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
//import javax.swing.JFrame;
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
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

/**
 *
 * @author zakir
 */
public class ExecuteApiTest extends javax.swing.JFrame {
    public static DefaultTableModel importDataFromExcelModel = new DefaultTableModel();
    public static DefaultTableModel importDataFromExcelModelOR = new DefaultTableModel();
    public static DefaultTableModel importDataFromExcelModelORBackup = new DefaultTableModel();
    public static ExecuteApiTest editTest = new ExecuteApiTest();
    public ObjectRepoFrame objRepo = new ObjectRepoFrame();
    private static JComboBox<String> comboBoxTestFlow = new JComboBox<>();
    public JComboBox<String> comboBoxObjectRepository = new JComboBox<>();
    private TableColumn testFlowColumn;
    public TableColumn testObjectRepoColumn;
    private XSSFSheet excelSheetTestFlow;
    public XSSFSheet excelSheetObjectRepository;
    public XSSFSheet excelSheetObjectRepositorySecodary;
    public XSSFSheet excelSheetObjectRepositoryOR;
    public XSSFSheet excelSheetObjectRepositoryORSecondary;
    private String objectRepositoryList="";
    public static JFileChooser excelFileImport;
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
    public static ArrayList<String> arrTestId = new ArrayList<String>();
    public static boltExecutor bExecutor = new boltExecutor();
    public static boltExecutor runThread = new boltExecutor();
    public static String testRunBrowser =null;
    RunTableColorCellRenderer RunCell_renderer = new RunTableColorCellRenderer();
    public static HashMap<String, String> testResultDocPath =new HashMap<String, String>();
    public static JButton resultDocPath =new JButton();
    public static HashMap<String, Boolean> getRunId = new HashMap<String, Boolean>();
    public static boolean duplicateRunId;
    public static boolean stopExecution;
    public static JTextField textTestType;
    
    /**
     * Creates new form NewJFrame
     */
    
    public ExecuteApiTest() {
        initComponents();
        //TableCellRenderer buttonRenderer = new JTableButtonRenderer();
        //tableExecuteRegSuite.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor (resultDocPath));
        //tableExecuteRegSuite.getColumn(3).setCellEditor((TableCellEditor) new JButton("Button3"));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollExecuteRegSuite = new JScrollPane();
        tableExecuteRegSuite = new JTable();
        jDesktopPane1 = new JDesktopPane();
        bttnStartTestRun = new JButton();
        jTextTestType = new JTextField();
        bttnRefreshTestRun = new JButton();
        bttnStopTestRun = new JButton();
        bttnLoadRegSuite = new JButton();
        jLabel1 = new JLabel();
        jDesktopPane2 = new JDesktopPane();
        pnlHeader = new JPanel();
        chkBoxSelectDeselectAllRun = new JCheckBox();
        chkBoxFilterFailTest = new JCheckBox();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Execute API Test");
        setBackground(java.awt.Color.lightGray);
        setBounds(new Rectangle(0, 0, 973, 500));
        setIconImages(null);
        setMaximumSize(new Dimension(900, 467));
        setMinimumSize(new Dimension(900, 467));
        setSize(new Dimension(900, 467));
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
            public void windowOpened(WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        scrollExecuteRegSuite.setBackground(new java.awt.Color(51, 51, 51));
        scrollExecuteRegSuite.setAutoscrolls(true);
        scrollExecuteRegSuite.setFont(new Font("Calibri", 0, 12)); // NOI18N
        scrollExecuteRegSuite.setMinimumSize(new Dimension(452, 402));

        tableExecuteRegSuite.setBackground(new java.awt.Color(51, 51, 51));
        tableExecuteRegSuite.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tableExecuteRegSuite.setFont(new Font("Consolas", 0, 14)); // NOI18N
        tableExecuteRegSuite.setForeground(new java.awt.Color(255, 255, 255));
        tableExecuteRegSuite.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Run", "Test ID", "Test Summary", "Test Status"
            }
        ) {
            Class[] types = new Class [] {
                Boolean.class, Object.class, Object.class, Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableExecuteRegSuite.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        tableExecuteRegSuite.setMinimumSize(new Dimension(180, 0));
        tableExecuteRegSuite.setName(""); // NOI18N
        tableExecuteRegSuite.setRowHeight(22);
        tableExecuteRegSuite.setRowMargin(4);
        tableExecuteRegSuite.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableExecuteRegSuite.setShowGrid(true);
        tableExecuteRegSuite.getTableHeader().setReorderingAllowed(false);
        tableExecuteRegSuite.setUpdateSelectionOnSort(false);
        tableExecuteRegSuite.setVerifyInputWhenFocusTarget(false);
        tableExecuteRegSuite.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                tableExecuteRegSuiteFocusGained(evt);
            }
        });
        tableExecuteRegSuite.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tableExecuteRegSuiteMouseClicked(evt);
            }
            public void mouseReleased(MouseEvent evt) {
                tableExecuteRegSuiteMouseReleased(evt);
            }
        });
        tableExecuteRegSuite.addInputMethodListener(new InputMethodListener() {
            public void caretPositionChanged(InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(InputMethodEvent evt) {
                tableExecuteRegSuiteInputMethodTextChanged(evt);
            }
        });
        scrollExecuteRegSuite.setViewportView(tableExecuteRegSuite);
        if (tableExecuteRegSuite.getColumnModel().getColumnCount() > 0) {
            tableExecuteRegSuite.getColumnModel().getColumn(0).setPreferredWidth(5);
            tableExecuteRegSuite.getColumnModel().getColumn(1).setPreferredWidth(10);
        }

        bttnStartTestRun.setBackground(new java.awt.Color(0, 0, 0));
        bttnStartTestRun.setFont(new Font("Consolas", 1, 14)); // NOI18N
        bttnStartTestRun.setForeground(new java.awt.Color(255, 255, 255));
        bttnStartTestRun.setIcon(new ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/startTestRun.png"));
            bttnStartTestRun.setToolTipText("start test execution");
            bttnStartTestRun.setBorder(null);
            bttnStartTestRun.setBorderPainted(false);
            bttnStartTestRun.setContentAreaFilled(false);
            bttnStartTestRun.setEnabled(false);
            bttnStartTestRun.setHorizontalTextPosition(SwingConstants.CENTER);
            bttnStartTestRun.setMaximumSize(new Dimension(121, 33));
            bttnStartTestRun.setMinimumSize(new Dimension(121, 33));
            bttnStartTestRun.setOpaque(true);
            bttnStartTestRun.setPreferredSize(new Dimension(121, 33));
            bttnStartTestRun.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    bttnStartTestRunMouseEntered(evt);
                }
                public void mouseExited(MouseEvent evt) {
                    bttnStartTestRunMouseExited(evt);
                }
                public void mouseReleased(MouseEvent evt) {
                    bttnStartTestRunMouseReleased(evt);
                }
            });

            jTextTestType.setBackground(new java.awt.Color(0, 0, 0));
            jTextTestType.setFont(new Font("Tahoma", 0, 14)); // NOI18N
            jTextTestType.setForeground(java.awt.Color.pink);
            jTextTestType.setBorder(BorderFactory.createEmptyBorder(1, 2, 1, 1));
            jTextTestType.setCursor(new Cursor(Cursor.TEXT_CURSOR));
            jTextTestType.setName("SetTestType"); // NOI18N
            jTextTestType.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent evt) {
                    jTextTestTypeFocusLost(evt);
                }
            });

            bttnRefreshTestRun.setBackground(new java.awt.Color(0, 0, 0));
            bttnRefreshTestRun.setFont(new Font("Consolas", 1, 14)); // NOI18N
            bttnRefreshTestRun.setForeground(new java.awt.Color(255, 255, 255));
            bttnRefreshTestRun.setIcon(new ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/refreshTestRun.png"));
                bttnRefreshTestRun.setToolTipText("Refresh to reload test run for new changes");
                bttnRefreshTestRun.setActionCommand("OpenRegressionSuite");
                bttnRefreshTestRun.setBorder(null);
                bttnRefreshTestRun.setBorderPainted(false);
                bttnRefreshTestRun.setContentAreaFilled(false);
                bttnRefreshTestRun.setHorizontalTextPosition(SwingConstants.CENTER);
                bttnRefreshTestRun.setMaximumSize(new Dimension(121, 33));
                bttnRefreshTestRun.setMinimumSize(new Dimension(121, 33));
                bttnRefreshTestRun.setOpaque(true);
                bttnRefreshTestRun.setPreferredSize(new Dimension(121, 33));
                bttnRefreshTestRun.setRequestFocusEnabled(false);
                bttnRefreshTestRun.setRolloverEnabled(false);
                bttnRefreshTestRun.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent evt) {
                        bttnRefreshTestRunMouseEntered(evt);
                    }
                    public void mouseExited(MouseEvent evt) {
                        bttnRefreshTestRunMouseExited(evt);
                    }
                });
                bttnRefreshTestRun.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        bttnRefreshTestRunActionPerformed(evt);
                    }
                });

                bttnStopTestRun.setBackground(new java.awt.Color(0, 0, 0));
                bttnStopTestRun.setFont(new Font("Consolas", 1, 14)); // NOI18N
                bttnStopTestRun.setForeground(new java.awt.Color(255, 255, 255));
                bttnStopTestRun.setIcon(new ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/stopTestRun.png"));
                    bttnStopTestRun.setToolTipText("stop test execution");
                    bttnStopTestRun.setBorder(null);
                    bttnStopTestRun.setBorderPainted(false);
                    bttnStopTestRun.setContentAreaFilled(false);
                    bttnStopTestRun.setEnabled(false);
                    bttnStopTestRun.setHorizontalTextPosition(SwingConstants.CENTER);
                    bttnStopTestRun.setMaximumSize(new Dimension(121, 33));
                    bttnStopTestRun.setMinimumSize(new Dimension(121, 33));
                    bttnStopTestRun.setOpaque(true);
                    bttnStopTestRun.setPreferredSize(new Dimension(121, 33));
                    bttnStopTestRun.addMouseListener(new MouseAdapter() {
                        public void mouseEntered(MouseEvent evt) {
                            bttnStopTestRunMouseEntered(evt);
                        }
                        public void mouseExited(MouseEvent evt) {
                            bttnStopTestRunMouseExited(evt);
                        }
                        public void mousePressed(MouseEvent evt) {
                            bttnStopTestRunMousePressed(evt);
                        }
                        public void mouseReleased(MouseEvent evt) {
                            bttnStopTestRunMouseReleased(evt);
                        }
                    });

                    bttnLoadRegSuite.setBackground(new java.awt.Color(0, 0, 0));
                    bttnLoadRegSuite.setFont(new Font("Consolas", 1, 14)); // NOI18N
                    bttnLoadRegSuite.setForeground(new java.awt.Color(255, 255, 255));
                    bttnLoadRegSuite.setIcon(new ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/addUploadTestSuite.png"));
                        bttnLoadRegSuite.setToolTipText("open and upload the test suite for execution");
                        bttnLoadRegSuite.setActionCommand("OpenRegressionSuite");
                        bttnLoadRegSuite.setBorder(null);
                        bttnLoadRegSuite.setBorderPainted(false);
                        bttnLoadRegSuite.setContentAreaFilled(false);
                        bttnLoadRegSuite.setHorizontalTextPosition(SwingConstants.CENTER);
                        bttnLoadRegSuite.setMaximumSize(new Dimension(121, 33));
                        bttnLoadRegSuite.setMinimumSize(new Dimension(121, 33));
                        bttnLoadRegSuite.setOpaque(true);
                        bttnLoadRegSuite.setPreferredSize(new Dimension(121, 33));
                        bttnLoadRegSuite.setRequestFocusEnabled(false);
                        bttnLoadRegSuite.setRolloverEnabled(false);
                        bttnLoadRegSuite.addMouseListener(new MouseAdapter() {
                            public void mouseEntered(MouseEvent evt) {
                                bttnLoadRegSuiteMouseEntered(evt);
                            }
                            public void mouseExited(MouseEvent evt) {
                                bttnLoadRegSuiteMouseExited(evt);
                            }
                        });
                        bttnLoadRegSuite.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent evt) {
                                bttnLoadRegSuiteActionPerformed(evt);
                            }
                        });

                        jLabel1.setFont(new Font("Consolas", 0, 12)); // NOI18N
                        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
                        jLabel1.setText("test type: @<tag_name>");

                        jDesktopPane1.setLayer(bttnStartTestRun, JLayeredPane.DEFAULT_LAYER);
                        jDesktopPane1.setLayer(jTextTestType, JLayeredPane.DEFAULT_LAYER);
                        jDesktopPane1.setLayer(bttnRefreshTestRun, JLayeredPane.DEFAULT_LAYER);
                        jDesktopPane1.setLayer(bttnStopTestRun, JLayeredPane.DEFAULT_LAYER);
                        jDesktopPane1.setLayer(bttnLoadRegSuite, JLayeredPane.DEFAULT_LAYER);
                        jDesktopPane1.setLayer(jLabel1, JLayeredPane.DEFAULT_LAYER);

                        GroupLayout jDesktopPane1Layout = new GroupLayout(jDesktopPane1);
                        jDesktopPane1.setLayout(jDesktopPane1Layout);
                        jDesktopPane1Layout.setHorizontalGroup(jDesktopPane1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(bttnLoadRegSuite, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bttnRefreshTestRun, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextTestType, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bttnStartTestRun, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bttnStopTestRun, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(394, Short.MAX_VALUE))
                        );
                        jDesktopPane1Layout.setVerticalGroup(jDesktopPane1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jDesktopPane1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(bttnStopTestRun, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bttnLoadRegSuite, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(bttnRefreshTestRun, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jDesktopPane1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextTestType, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1))
                                    .addComponent(bttnStartTestRun, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        );

                        pnlHeader.setBackground(new java.awt.Color(0, 153, 153));
                        pnlHeader.setMinimumSize(new Dimension(206, 40));
                        pnlHeader.setOpaque(false);

                        chkBoxSelectDeselectAllRun.setFont(new Font("Consolas", 0, 10)); // NOI18N
                        chkBoxSelectDeselectAllRun.setForeground(new java.awt.Color(255, 255, 255));
                        chkBoxSelectDeselectAllRun.setText("Select ALL Run");
                        chkBoxSelectDeselectAllRun.setToolTipText("will select all test(s) for run");
                        chkBoxSelectDeselectAllRun.setBorder(null);
                        chkBoxSelectDeselectAllRun.setEnabled(false);
                        chkBoxSelectDeselectAllRun.setIconTextGap(2);
                        chkBoxSelectDeselectAllRun.setName(""); // NOI18N
                        chkBoxSelectDeselectAllRun.setVerticalAlignment(SwingConstants.BOTTOM);
                        chkBoxSelectDeselectAllRun.setVerticalTextPosition(SwingConstants.BOTTOM);
                        chkBoxSelectDeselectAllRun.addMouseListener(new MouseAdapter() {
                            public void mouseEntered(MouseEvent evt) {
                                chkBoxSelectDeselectAllRunMouseEntered(evt);
                            }
                            public void mouseExited(MouseEvent evt) {
                                chkBoxSelectDeselectAllRunMouseExited(evt);
                            }
                        });
                        chkBoxSelectDeselectAllRun.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent evt) {
                                chkBoxSelectDeselectAllRunActionPerformed(evt);
                            }
                        });

                        chkBoxFilterFailTest.setFont(new Font("Consolas", 0, 10)); // NOI18N
                        chkBoxFilterFailTest.setForeground(new java.awt.Color(255, 255, 255));
                        chkBoxFilterFailTest.setText("Filter FAIL test(s)");
                        chkBoxFilterFailTest.setToolTipText("will select only failed test(s) for re-run");
                        chkBoxFilterFailTest.setBorder(null);
                        chkBoxFilterFailTest.setEnabled(false);
                        chkBoxFilterFailTest.setHorizontalAlignment(SwingConstants.LEFT);
                        chkBoxFilterFailTest.setIconTextGap(2);
                        chkBoxFilterFailTest.setVerticalAlignment(SwingConstants.BOTTOM);
                        chkBoxFilterFailTest.setVerticalTextPosition(SwingConstants.BOTTOM);
                        chkBoxFilterFailTest.addMouseListener(new MouseAdapter() {
                            public void mouseEntered(MouseEvent evt) {
                                chkBoxFilterFailTestMouseEntered(evt);
                            }
                            public void mouseExited(MouseEvent evt) {
                                chkBoxFilterFailTestMouseExited(evt);
                            }
                            public void mouseReleased(MouseEvent evt) {
                                chkBoxFilterFailTestMouseReleased(evt);
                            }
                        });
                        chkBoxFilterFailTest.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent evt) {
                                chkBoxFilterFailTestActionPerformed(evt);
                            }
                        });

                        GroupLayout pnlHeaderLayout = new GroupLayout(pnlHeader);
                        pnlHeader.setLayout(pnlHeaderLayout);
                        pnlHeaderLayout.setHorizontalGroup(pnlHeaderLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(GroupLayout.Alignment.TRAILING, pnlHeaderLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(chkBoxSelectDeselectAllRun)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(chkBoxFilterFailTest)
                                .addContainerGap())
                        );
                        pnlHeaderLayout.setVerticalGroup(pnlHeaderLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(pnlHeaderLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(pnlHeaderLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(chkBoxSelectDeselectAllRun)
                                    .addComponent(chkBoxFilterFailTest, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
                                .addGap(1, 1, 1))
                        );

                        jDesktopPane2.setLayer(pnlHeader, JLayeredPane.DEFAULT_LAYER);

                        GroupLayout jDesktopPane2Layout = new GroupLayout(jDesktopPane2);
                        jDesktopPane2.setLayout(jDesktopPane2Layout);
                        jDesktopPane2Layout.setHorizontalGroup(jDesktopPane2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(jDesktopPane2Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(pnlHeader, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(1, 1, 1))
                        );
                        jDesktopPane2Layout.setVerticalGroup(jDesktopPane2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(jDesktopPane2Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(pnlHeader, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1))
                        );

                        GroupLayout layout = new GroupLayout(getContentPane());
                        getContentPane().setLayout(layout);
                        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(jDesktopPane1)
                                    .addComponent(scrollExecuteRegSuite, GroupLayout.DEFAULT_SIZE, 897, Short.MAX_VALUE)
                                    .addComponent(jDesktopPane2))
                                .addGap(1, 1, 1))
                        );
                        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jDesktopPane2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(scrollExecuteRegSuite, GroupLayout.PREFERRED_SIZE, 394, Short.MAX_VALUE)
                                .addGap(1, 1, 1)
                                .addComponent(jDesktopPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1))
                        );

                        getAccessibleContext().setAccessibleParent(this);

                        setSize(new Dimension(913, 504));
                        setLocationRelativeTo(null);
                    }// </editor-fold>//GEN-END:initComponents
    
    /*public XSSFSheet getObjectRepositorySheet(String filePath, int orSheetIndex){
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
            Logger.getLogger(ExecuteApiTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExecuteApiTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return objectRepoExcel;
    }*/
                
    private void formWindowActivated(WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
       tableExecuteRegSuite.requestFocus();
       setTableColWidthForExeRegSuiteTable();
    }//GEN-LAST:event_formWindowActivated
    
    /*public CellStyle getHeaderStyle(XSSFWorkbook workBook, short index){
        XSSFFont Headerfont = workBook.createFont();
        CellStyle headerStyle = workBook.createCellStyle();
          
        headerStyle.setFillBackgroundColor(IndexedColors.BLACK1.getIndex());
        headerStyle.setFillPattern(FillPatternType.ALT_BARS);
        
        Headerfont.setColor(index);
        Headerfont.setBold(true);
     
        headerStyle.setFont(Headerfont);
        
        return headerStyle;
    }*/
    
    private void formWindowOpened(WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Image titleIcon = Toolkit.getDefaultToolkit().getImage(constants.userDir+"\\icons\\bolt.jpg");
        this.setIconImage(titleIcon);
        tableExecuteRegSuite.setDefaultRenderer(Object.class, RunCell_renderer);
        
        //textTestType = ((JTextField) cBoxTestType.getEditor().getEditorComponent());
        //textTestType.setForeground(new java.awt.Color(255,102,102));
        //textTestType.setFont(new Font("Comic Sans MS",0,12));
        jTextTestType.setEnabled(false);
    }//GEN-LAST:event_formWindowOpened

    private void bttnStartTestRunMouseEntered(MouseEvent evt) {//GEN-FIRST:event_bttnStartTestRunMouseEntered
        bttnStartTestRun.setForeground(new java.awt.Color(0,0,0));
                
        if(bttnStartTestRun.isEnabled() ==true){
            bttnStartTestRun.setBackground(new java.awt.Color(250, 128, 114));
            bttnStartTestRun.setToolTipText("start test execution");
        }else{
            bttnStartTestRun.setBackground(new java.awt.Color(133, 146, 158));
            bttnStartTestRun.setToolTipText("test run in progress");
        }
    }//GEN-LAST:event_bttnStartTestRunMouseEntered

    private void bttnStartTestRunMouseExited(MouseEvent evt) {//GEN-FIRST:event_bttnStartTestRunMouseExited
        bttnStartTestRun.setBackground(new java.awt.Color(0,0,0));
        bttnStartTestRun.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_bttnStartTestRunMouseExited

    private void bttnLoadRegSuiteMouseEntered(MouseEvent evt) {//GEN-FIRST:event_bttnLoadRegSuiteMouseEntered
        bttnLoadRegSuite.setBackground(new java.awt.Color(250, 128, 114));
        bttnLoadRegSuite.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_bttnLoadRegSuiteMouseEntered

    private void bttnLoadRegSuiteMouseExited(MouseEvent evt) {//GEN-FIRST:event_bttnLoadRegSuiteMouseExited
        bttnLoadRegSuite.setBackground(new java.awt.Color(0,0,0));
        bttnLoadRegSuite.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_bttnLoadRegSuiteMouseExited

    private void bttnLoadRegSuiteActionPerformed(ActionEvent evt) {//GEN-FIRST:event_bttnLoadRegSuiteActionPerformed
        
        BufferedInputStream excelBIS;
        XSSFRow excelRow;
        String getCurrDir;
        
        try{
            File getCurrDirectory =excelFileImport.getCurrentDirectory();
            getCurrDir =getCurrDirectory.getAbsolutePath();
        }catch(NullPointerException exp){
            getCurrDir =FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
        }
        
	excelFileImport = new JFileChooser(getCurrDir);
        excelFileImport.setFileSelectionMode(JFileChooser.FILES_ONLY);
        excelFileImport.setDialogTitle("Load API Test");
        excelFileImport.addChoosableFileFilter(new FileNameExtensionFilter("EXCEL WORKBOOK", "xlsx"));
        excelFileImport.setAcceptAllFileFilterUsed(false);
          
        int excelChooser = excelFileImport.showOpenDialog(this);
        
        if(excelChooser == JFileChooser.CANCEL_OPTION)
            return;
        
        if(excelChooser == JFileChooser.APPROVE_OPTION) {
            importDataFromExcelModel = new DefaultTableModel();
            comboBoxObjectRepository = new JComboBox<>();
            importDataFromExcelModel = (DefaultTableModel) tableExecuteRegSuite.getModel();
            noRepoFound = true;
            testSuiteUploaded = false;
            excelSheetObjectRepository = null;
            testObjectRepoColumn = null;
            duplicateRunId =false;
            testSuiteFilePath =null;
         
            testSuiteUploaded = true;
            excelImportWorkBook = new XSSFWorkbook();
            importDataFromExcelModel.setRowCount(0);
            excelFile = excelFileImport.getSelectedFile();
            testSuiteFilePath = excelFile.getPath();
           
            try {
                try{
                    if(excelFIS.getChannel().isOpen());
                        excelFIS.close();
                }catch(NullPointerException exp){
                    Logger.getLogger(ExecuteApiTest.class.getName()).log(Level.INFO, "channel is closed", "channel is closed");
                }
                    
                excelFIS = new FileInputStream(excelFile); 
                excelBIS = new BufferedInputStream(excelFIS);
              
                excelImportWorkBook = new XSSFWorkbook(excelBIS);   
                excelSheetTestFlow = excelImportWorkBook.getSheetAt(0);
                    
                for(int i=1;i<=excelSheetTestFlow.getLastRowNum();i++) {
                    excelRow = excelSheetTestFlow.getRow(i);

                    try{
                         XSSFCell testId = excelRow.getCell(0);
                         XSSFCell testSummary = excelRow.getCell(18);

                        String getTestRunId =null;
                        if(testId.getCellType().toString().contentEquals("NUMERIC")){
                            double getTestId = Math.round(Double.parseDouble(testId.toString()));
                            int xgetTestId = (int)getTestId;
                            getTestRunId = String.valueOf(xgetTestId);

                        }else{
                            getTestRunId = testId.toString();
                        }

                            if(testId.toString().matches("[+-]?\\d*(\\.\\d+)?") && !testId.toString().isEmpty()){
                                importDataFromExcelModel.addRow(new Object[] {true, getTestRunId, testSummary, "Not Started" });
                        }
                    }catch(NullPointerException exp){

                    }
                }
                    
                tableExecuteRegSuite.setRowSelectionInterval(0,0);
                tableExecuteRegSuite.scrollRectToVisible(tableExecuteRegSuite.getCellRect(0,0, true));
                tableExecuteRegSuite.requestFocus();

                if(objRepo.isVisible()){
                    ObjectRepoFrame.importObjectRepoData.getDataVector().removeAllElements();
                    ObjectRepoFrame.importObjectRepoData.fireTableDataChanged();
                    objRepo.setTitle("Object Repository: "+excelFileImport.getName(excelFile));
                    objRepo.openObjectRepository(excelSheetObjectRepository);
                }
                this.setTitle("Execute Test Suite: "+excelFileImport.getName(excelFile));
                jTextTestType.setEnabled(true);
            } catch (FileNotFoundException exp) {
                    if(exp.getMessage().contains("The system cannot find the file specified")){
                        JOptionPane.showMessageDialog(null,"No test suite "+"\""+excelFileImport.getName(excelFile)+"\""+" found to upload!","Alert",JOptionPane.WARNING_MESSAGE);
                        return;
                    }else{
                        exp.printStackTrace();
                    }
            } catch (IOException exp) {
                    exp.printStackTrace();
            }catch (IllegalArgumentException exp) {
                    if(exp.getMessage().contains("Row index out of range")){
                        JOptionPane.showMessageDialog(null,"No test steps found in "+"\""+excelFileImport.getName(excelFile)+"\""+" test suite to upload!","Alert",JOptionPane.WARNING_MESSAGE);
                        return;
                    }
            }
	}
        
        HashMap<Integer, String> getTestIds =new HashMap<Integer, String>();
        for(int x=0; x<importDataFromExcelModel.getRowCount(); x++){
            String getRunId =importDataFromExcelModel.getValueAt(x, 1).toString().trim();
            for(int y=x+1; y<importDataFromExcelModel.getRowCount(); y++){
                String getRunIdx =importDataFromExcelModel.getValueAt(y, 1).toString().trim();
                if(getRunId.contentEquals(getRunIdx)){
                    getTestIds.put(y, getRunId);
                    break;
                }
            }
        }
        
        if(getTestIds.size() >0){
            String readTestIds ="";
            duplicateRunId =true;
            bttnStartTestRun.setEnabled(false);
            for(String id: getTestIds.values()) {
                readTestIds = readTestIds +"["+id+"] ";
            }
            JOptionPane.showMessageDialog(null,"duplicate run Id's discovered, update the suite and reload again"
                    + "\n\nRun Id list: "+readTestIds,"Alert",JOptionPane.WARNING_MESSAGE);
        }
        
        if(importDataFromExcelModel.getRowCount()>0){
            chkBoxSelectDeselectAllRun.setEnabled(true);
            chkBoxSelectDeselectAllRun.setSelected(true);
        }else{
            chkBoxSelectDeselectAllRun.setEnabled(false);
            chkBoxSelectDeselectAllRun.setSelected(false);
        }
    }//GEN-LAST:event_bttnLoadRegSuiteActionPerformed

    private void bttnStopTestRunMouseEntered(MouseEvent evt) {//GEN-FIRST:event_bttnStopTestRunMouseEntered
        bttnStopTestRun.setForeground(new java.awt.Color(0,0,0));
        bttnStopTestRun.setBackground(new java.awt.Color(250, 128, 114));
        bttnStopTestRun.setToolTipText("stop test execution");
    }//GEN-LAST:event_bttnStopTestRunMouseEntered

    private void bttnStopTestRunMouseExited(MouseEvent evt) {//GEN-FIRST:event_bttnStopTestRunMouseExited
        bttnStopTestRun.setBackground(new java.awt.Color(0,0,0));
        bttnStopTestRun.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_bttnStopTestRunMouseExited

    private void chkBoxSelectDeselectAllRunActionPerformed(ActionEvent evt) {//GEN-FIRST:event_chkBoxSelectDeselectAllRunActionPerformed
        if(!chkBoxSelectDeselectAllRun.isSelected()){
            chkBoxFilterFailTest.setSelected(false);
            for(int i=0;i<importDataFromExcelModel.getRowCount();i++)
                importDataFromExcelModel.setValueAt(false, i, 0);
        }else if(chkBoxSelectDeselectAllRun.isSelected()){
            chkBoxFilterFailTest.setSelected(false);
            for(int i=0;i<importDataFromExcelModel.getRowCount();i++)
                importDataFromExcelModel.setValueAt(true, i, 0);
        }
    }//GEN-LAST:event_chkBoxSelectDeselectAllRunActionPerformed

    private void formWindowGainedFocus(WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        if(bttnStartTestRun.isFocusable() ==true){
            if(checkForRunRunning() ==true){
                bttnStartTestRun.setEnabled(false);
                chkBoxFilterFailTest.setEnabled(false);
            }else{
                if(duplicateRunId ==false){
                    bttnStartTestRun.setEnabled(true);
                }
                chkBoxSelectDeselectAllRun.setEnabled(true);
                tableExecuteRegSuite.setEnabled(true);
                bttnLoadRegSuite.setEnabled(true);
            } 
        }
    }//GEN-LAST:event_formWindowGainedFocus

    private void bttnStartTestRunMouseReleased(MouseEvent evt) {//GEN-FIRST:event_bttnStartTestRunMouseReleased
        if(jTextTestType.getText().trim().contentEquals("@") || !jTextTestType.getText().trim().startsWith("@") && 
                !jTextTestType.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(scrollExecuteRegSuite,"Tag name is not properly defined ["+jTextTestType.getText()+"]");
            return;
        }
        
        if(!jTextTestType.getText().trim().isEmpty()){
            if(selectTestAsPerTestType() ==false){return;}
        }
        
        if(tableExecuteRegSuite.getRowCount() >0){
          if(checkRunIsAvailable() ==false){
              JOptionPane.showMessageDialog(null, "No test is selected to execute!", "Alert", JOptionPane.WARNING_MESSAGE);
              return;
          }

          arrTestId = new ArrayList<>();
          for(int i=0; i<importDataFromExcelModel.getRowCount(); i++){
              boolean run = (boolean) importDataFromExcelModel.getValueAt(i, 0);
              if(run ==true){
                 arrTestId.add(importDataFromExcelModel.getValueAt(i, 1).toString());
                 importDataFromExcelModel.setValueAt("Not Started", i, 3);
              }
          }
        }else{
          JOptionPane.showMessageDialog(null, "No test suite is available to execute!", "Alert", JOptionPane.WARNING_MESSAGE);
          return;
        }
        
        if(tableExecuteRegSuite.getRowCount() >0 && bttnStartTestRun.isEnabled() ==true){
           if(checkRunIsAvailable() ==false)
               return;
           
            stopExecution =false;    
            bttnStartTestRun.setEnabled(false);
            chkBoxFilterFailTest.setEnabled(false);
            chkBoxSelectDeselectAllRun.setEnabled(false);
            tableExecuteRegSuite.setEnabled(false);
            bttnLoadRegSuite.setEnabled(false);
            bttnStopTestRun.setEnabled(true);
            bttnRefreshTestRun.setEnabled(false);
            //chkBoxAssociateObjOR.setEnabled(false);
            //chkBoxRunHeadless.setEnabled(false);

            if(runThread.isAlive())
                runThread.interrupt();
            
            runThread = new boltExecutor();
            runThread.start();
        }
    }//GEN-LAST:event_bttnStartTestRunMouseReleased

    private void bttnStopTestRunMouseReleased(MouseEvent evt) {//GEN-FIRST:event_bttnStopTestRunMouseReleased
        if(bttnStopTestRun.isEnabled() ==true){ 
            stopExecution =true;
            getErrorMessage ="This test run was stopped/Interrupted by the user!";
            try {
                bttnStopTestRun.setEnabled(false);
                try{
                    glueCode.boltDriver.close();
                    glueCode.boltDriver.quit();
                }catch(NullPointerException | WebDriverException exp){}
                runThread.interrupt();
            }catch (Exception exp) {
                System.out.println("Caught:" + exp.getMessage());
            }
        }
    }//GEN-LAST:event_bttnStopTestRunMouseReleased

    private void chkBoxFilterFailTestActionPerformed(ActionEvent evt) {//GEN-FIRST:event_chkBoxFilterFailTestActionPerformed

    }//GEN-LAST:event_chkBoxFilterFailTestActionPerformed

    private void chkBoxFilterFailTestMouseReleased(MouseEvent evt) {//GEN-FIRST:event_chkBoxFilterFailTestMouseReleased
        int getRowCnt =tableExecuteRegSuite.getRowCount();
        boolean setStatus =false;
        
        if(!chkBoxFilterFailTest.isEnabled())
            return;
                
        if(chkBoxFilterFailTest.isSelected()){
            for(int x=0; x<getRowCnt; x++){
                Boolean run=(Boolean) tableExecuteRegSuite.getValueAt(x, 0);
                String testId =tableExecuteRegSuite.getValueAt(x, 1).toString();
                getRunId.put(testId, run);
            }
        }
        
        if(checkIfAnyTestStatusIsFail() ==false || checkIfAllTestStatusIsFail() ==true){
            if(chkBoxFilterFailTest.isSelected())
                chkBoxFilterFailTest.setSelected(false);
            return;
        }
        
        if(chkBoxFilterFailTest.isSelected() ==true){
            setStatus =true;
            for(int x=0; x<getRowCnt; x++){
                String getTestStatus =tableExecuteRegSuite.getValueAt(x, 3).toString(); 
                if(getTestStatus.toLowerCase().contentEquals("fail")){
                    importDataFromExcelModel.setValueAt(setStatus, x, 0);
                }else
                    importDataFromExcelModel.setValueAt(false, x, 0);
            } 
        }else{
            for(int x=0; x<getRowCnt; x++){
                boolean runStatus =getRunId.get(importDataFromExcelModel.getValueAt(x, 1).toString());
                importDataFromExcelModel.setValueAt(runStatus, x, 0);
            }
        }
        
        if(checkAnyRunNotAvailable() ==false)
                chkBoxSelectDeselectAllRun.setSelected(false);
        else
            chkBoxSelectDeselectAllRun.setSelected(true);
    }//GEN-LAST:event_chkBoxFilterFailTestMouseReleased
    
    public static boolean selectTestAsPerTestType(){
        int getRowCnt =tableExecuteRegSuite.getRowCount();
        boolean tagFound =false;
        
        for(int x=0; x<getRowCnt; x++){
            String getTestStatus =tableExecuteRegSuite.getValueAt(x, 2).toString(); 
            if(getTestStatus.toLowerCase().contains(jTextTestType.getText().toLowerCase())){
                importDataFromExcelModel.setValueAt(true, x, 0);
                tagFound =true;
            }else
                importDataFromExcelModel.setValueAt(false, x, 0);
        } 
            
        if(checkAnyRunNotAvailable() ==false)
                chkBoxSelectDeselectAllRun.setSelected(false);
        else
            chkBoxSelectDeselectAllRun.setSelected(true);
        
        if(tagFound ==false){
            JOptionPane.showMessageDialog(scrollExecuteRegSuite,"No test is marked with the given tag ["+jTextTestType.getText()+"]");
        }
        
        return tagFound;
    }
    
    private void bttnRefreshTestRunMouseEntered(MouseEvent evt) {//GEN-FIRST:event_bttnRefreshTestRunMouseEntered
        bttnRefreshTestRun.setBackground(new java.awt.Color(250, 128, 114));
        bttnRefreshTestRun.setForeground(new java.awt.Color(0,0,0));  
    }//GEN-LAST:event_bttnRefreshTestRunMouseEntered

    private void bttnRefreshTestRunMouseExited(MouseEvent evt) {//GEN-FIRST:event_bttnRefreshTestRunMouseExited
        bttnRefreshTestRun.setBackground(new java.awt.Color(0,0,0));
        bttnRefreshTestRun.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_bttnRefreshTestRunMouseExited

    private void bttnRefreshTestRunActionPerformed(ActionEvent evt) {//GEN-FIRST:event_bttnRefreshTestRunActionPerformed
         try {
            excelImportWorkBook = new XSSFWorkbook();
            importDataFromExcelModel.setRowCount(0);
                
            BufferedInputStream excelBIS;

            excelFIS = new FileInputStream(excelFile);
            excelBIS = new BufferedInputStream(excelFIS);
            XSSFRow excelRow;

            excelImportWorkBook = new XSSFWorkbook(excelBIS);   
            excelSheetTestFlow = excelImportWorkBook.getSheetAt(0);

            for(int i=1;i<=excelSheetTestFlow.getLastRowNum();i++) {
                excelRow = excelSheetTestFlow.getRow(i);

                try{
                    XSSFCell testId = excelRow.getCell(0);
                    XSSFCell testSummary = excelRow.getCell(18);

                    String getTestRunId =null;
                    if(testId.getCellType().toString().contentEquals("NUMERIC")){
                        double getTestId = Math.round(Double.parseDouble(testId.toString()));
                        int xgetTestId = (int)getTestId;
                        getTestRunId = String.valueOf(xgetTestId);

                    }else{
                        getTestRunId = testId.toString();
                    }

                    if(testId.toString().matches("[+-]?\\d*(\\.\\d+)?") && !testId.toString().isEmpty()){
                        importDataFromExcelModel.addRow(new Object[] {true, getTestRunId, testSummary, "Not Started" });
                    }
                }catch(NullPointerException exp){

                }
            }

            tableExecuteRegSuite.setRowSelectionInterval(0,0);
            tableExecuteRegSuite.scrollRectToVisible(tableExecuteRegSuite.getCellRect(0,0, true));
            tableExecuteRegSuite.requestFocus();
        } catch (NullPointerException ex) {
            //Logger.getLogger(ExecuteRegressionSuite.class.getName()).log(Level.SEVERE, null, ex);
            return;
        } catch (IOException | IllegalArgumentException ex) {
            //Logger.getLogger(ExecuteRegressionSuite.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        HashMap<Integer, String> getTestIds =new HashMap<Integer, String>();
        for(int x=0; x<importDataFromExcelModel.getRowCount(); x++){
            String getRunId =importDataFromExcelModel.getValueAt(x, 1).toString().trim();
            for(int y=x+1; y<importDataFromExcelModel.getRowCount(); y++){
                String getRunIdx =importDataFromExcelModel.getValueAt(y, 1).toString().trim();
                if(getRunId.contentEquals(getRunIdx)){
                    getTestIds.put(y, getRunId);
                    break;
                }
            }
        }

        if(getTestIds.size() >0){
            String readTestIds ="";
            duplicateRunId =true;
            bttnStartTestRun.setEnabled(false);
            for(String id: getTestIds.values()) {
                readTestIds = readTestIds +"["+id+"] ";
            }
            JOptionPane.showMessageDialog(scrollExecuteRegSuite,"duplicate run Id's discovered, update the suite and reload again"
                    + "\n\nRun Id list: "+readTestIds,"Alert",JOptionPane.WARNING_MESSAGE);
        } 
        
        if(importDataFromExcelModel.getRowCount()>0){
            chkBoxSelectDeselectAllRun.setEnabled(true);
            chkBoxSelectDeselectAllRun.setSelected(true);
        }else{
            chkBoxSelectDeselectAllRun.setEnabled(false);
            chkBoxSelectDeselectAllRun.setSelected(false);
        }
    }//GEN-LAST:event_bttnRefreshTestRunActionPerformed

    private void chkBoxSelectDeselectAllRunMouseEntered(MouseEvent evt) {//GEN-FIRST:event_chkBoxSelectDeselectAllRunMouseEntered
        chkBoxSelectDeselectAllRun.setForeground(java.awt.Color.PINK);
    }//GEN-LAST:event_chkBoxSelectDeselectAllRunMouseEntered

    private void chkBoxSelectDeselectAllRunMouseExited(MouseEvent evt) {//GEN-FIRST:event_chkBoxSelectDeselectAllRunMouseExited
        chkBoxSelectDeselectAllRun.setForeground(java.awt.Color.WHITE);
    }//GEN-LAST:event_chkBoxSelectDeselectAllRunMouseExited

    private void chkBoxFilterFailTestMouseEntered(MouseEvent evt) {//GEN-FIRST:event_chkBoxFilterFailTestMouseEntered
        chkBoxFilterFailTest.setForeground(java.awt.Color.PINK);
    }//GEN-LAST:event_chkBoxFilterFailTestMouseEntered

    private void chkBoxFilterFailTestMouseExited(MouseEvent evt) {//GEN-FIRST:event_chkBoxFilterFailTestMouseExited
        chkBoxFilterFailTest.setForeground(java.awt.Color.WHITE);
    }//GEN-LAST:event_chkBoxFilterFailTestMouseExited

    private void tableExecuteRegSuiteInputMethodTextChanged(InputMethodEvent evt) {//GEN-FIRST:event_tableExecuteRegSuiteInputMethodTextChanged

    }//GEN-LAST:event_tableExecuteRegSuiteInputMethodTextChanged

    private void tableExecuteRegSuiteMouseReleased(MouseEvent evt) {//GEN-FIRST:event_tableExecuteRegSuiteMouseReleased
        boolean checkBoxTicked = true;

        for(int i=0; i<importDataFromExcelModel.getRowCount(); i++){
            boolean run = (boolean) importDataFromExcelModel.getValueAt(i, 0);
            if(run ==false){
                checkBoxTicked = false;
                break;
            }
        }

        int gerCurrCol = tableExecuteRegSuite.convertColumnIndexToModel(tableExecuteRegSuite.columnAtPoint(evt.getPoint()));
        int gerCurrRow = tableExecuteRegSuite.convertRowIndexToModel(tableExecuteRegSuite.rowAtPoint(evt.getPoint()));

        if(gerCurrCol ==0){
            if(!tableExecuteRegSuite.getValueAt(gerCurrRow, 0).toString().contentEquals("FAIL"))
            chkBoxFilterFailTest.setSelected(false);
        }

        if(chkBoxSelectDeselectAllRun.isSelected() ==true && checkBoxTicked ==false)
            chkBoxSelectDeselectAllRun.setSelected(false);

        if(chkBoxSelectDeselectAllRun.isSelected() ==false && checkBoxTicked ==true)
            chkBoxSelectDeselectAllRun.setSelected(true);
    }//GEN-LAST:event_tableExecuteRegSuiteMouseReleased

    private void tableExecuteRegSuiteMouseClicked(MouseEvent evt) {//GEN-FIRST:event_tableExecuteRegSuiteMouseClicked
        if (evt.getClickCount() ==2) {
            JTable target =(JTable)evt.getSource();
            int row =target.getSelectedRow();
            int column =target.getSelectedColumn();
            if(column ==3){
                String getCellTxt =tableExecuteRegSuite.getValueAt(row, column).toString();
                if(!getCellTxt.toLowerCase().contentEquals("not started") &&
                    !getCellTxt.toLowerCase().contentEquals("running...") &&
                    !getCellTxt.toLowerCase().contentEquals("interrupted!")){
                    String getRunId =tableExecuteRegSuite.getValueAt(row, 1).toString();
                    try {
                        File file = new File(testResultDocPath.get(getRunId));
                        if(!file.exists()){
                            JOptionPane.showMessageDialog(scrollExecuteRegSuite,"the required test run ["+getRunId+"] result not found!\n\nDeleted or moved out of test result folder.","Alert",JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        File sameFileName;
                        sameFileName = new File(testResultDocPath.get(getRunId));

                        if(file.renameTo(sameFileName)){
                            Desktop.getDesktop().open(file);
                        }else{
                            JOptionPane.showMessageDialog(scrollExecuteRegSuite,"the required test run ["+getRunId+"] result is already open!","Alert",JOptionPane.WARNING_MESSAGE);
                        }

                    } catch (IOException ex) {
                        Logger.getLogger(ExecuteApiTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }//GEN-LAST:event_tableExecuteRegSuiteMouseClicked

    private void tableExecuteRegSuiteFocusGained(FocusEvent evt) {//GEN-FIRST:event_tableExecuteRegSuiteFocusGained
        if(tableExecuteRegSuite.getRowCount() >0){
            //if(bttnStartTestRun.isEnabled()){
                //chkBoxRunHeadless.setEnabled(true);
                //chkBoxAssociateObjOR.setEnabled(true);
            //}

            //chkBoxFilterFailTest.setEnabled(true);
            //chkBoxSelectDeselectAllRun.setEnabled(true);
        }else if(tableExecuteRegSuite.getRowCount() ==0){
            //chkBoxRunHeadless.setEnabled(false);
            //chkBoxAssociateObjOR.setEnabled(false);
            chkBoxFilterFailTest.setEnabled(false);
            chkBoxSelectDeselectAllRun.setEnabled(false);
        }
    }//GEN-LAST:event_tableExecuteRegSuiteFocusGained

    private void bttnStopTestRunMousePressed(MouseEvent evt) {//GEN-FIRST:event_bttnStopTestRunMousePressed
        if(bttnStopTestRun.isEnabled())
            ExecuteApiTest.importDataFromExcelModel.setValueAt("Stopping...", getCurrRunId, 3);
    }//GEN-LAST:event_bttnStopTestRunMousePressed

    private void jTextTestTypeFocusLost(FocusEvent evt) {//GEN-FIRST:event_jTextTestTypeFocusLost
        //if(!jTextTestType.getText().isEmpty() &&
            //jTextTestType.getText().startsWith("@")){
            //selectTestAsPerTestType();
        //}
    }//GEN-LAST:event_jTextTestTypeFocusLost
         
    public void runTestWithChrome(){
        /*if(rdButtonChrome.isSelected() ==true){
            rdButtonEdge.setSelected(false);
        }else if(rdButtonEdge.isSelected() ==false && rdButtonChrome.isSelected() ==false){
            rdButtonChrome.setSelected(true);
        }*/
    }
    
    public void runTestWithIE(){
        /*if(rdButtonEdge.isSelected() ==true){
            rdButtonChrome.setSelected(false);
        }else if(rdButtonChrome.isSelected() ==false && rdButtonEdge.isSelected() ==false){
            rdButtonEdge.setSelected(true);
        }*/
    }
            
    public static void updateTestSuite(File excelFile){
        FileInputStream fis = null;
        
        try {
            fis = new FileInputStream(excelFile);
            FileOutputStream outFile;
            try (XSSFWorkbook wbSuite = new XSSFWorkbook(fis)) {
                XSSFSheet excelSheetTestFlow = wbSuite.getSheetAt(0);
                XSSFCell cell = null;
                XSSFDataFormat format = wbSuite.createDataFormat();
                XSSFFont font = wbSuite.createFont();
                font.setColor(IndexedColors.WHITE.getIndex());
                XSSFCellStyle cellStyle = wbSuite.createCellStyle();
                cellStyle.setFont(font);
                cellStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
                cellStyle.setFillPattern(FillPatternType.ALT_BARS);
                for(int j=0; j<importDataFromExcelModel.getRowCount();j++){
                    XSSFRow row = excelSheetTestFlow.createRow(j+1);
                    for(int k=0;k<importDataFromExcelModel.getColumnCount();k++){
                        cell = row.createCell(k);
                        cell.setCellType(CellType.STRING);
                        cell.setCellStyle(cellStyle);
                        try{
                            cell.setCellValue(importDataFromExcelModel.getValueAt(j, k).toString());
                        }catch (NullPointerException exp){
                            cell.setCellValue("");
                        }
                    }
                }   fis.close();
                outFile = new FileOutputStream(excelFile);
                wbSuite.write(outFile);
            }
            outFile.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExecuteApiTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExecuteApiTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @SuppressWarnings("LocalVariableHidesMemberVariable")
    public boolean deleteAllTestSteps(File excelFileSuite, int sheetIndex) throws IOException{
        FileInputStream fis;
        boolean deleteSuccessfull = true;
        XSSFWorkbook wbSuite = null;
        FileOutputStream outFile;
                
        try {
            fis = new FileInputStream(excelFileSuite);
            wbSuite = new XSSFWorkbook(fis);
            XSSFSheet excelSheetTestFlow = wbSuite.getSheetAt(sheetIndex);
            
            for(int i=1;i<=excelSheetTestFlow.getLastRowNum();i++) {
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
           
            if(ex.getMessage().contains("The process cannot access the file because it is being used by another process")){
                int response;
                    
                do{
                    response = JOptionPane.showConfirmDialog(scrollExecuteRegSuite, //
                        "Close test suite "+"\""+excelFileImport.getName(excelFile)+"\""+" to save the changes!", //
                        "Alert", JOptionPane.OK_CANCEL_OPTION, //
                        JOptionPane.WARNING_MESSAGE);

                    if (response ==JOptionPane.OK_OPTION) {
                        try {
                            outFile = new FileOutputStream(excelFileSuite);
                            wbSuite.write(outFile);
                            outFile.close();
                            deleteSuccessfull = true;
                            break;
                        } catch (FileNotFoundException ex1) {
                            if(ex.getMessage().contains("The process cannot access the file because it is being used by another process")){

                            }
                        } 
                    } 
                }while (response !=JOptionPane.CANCEL_OPTION);
            }
        } catch (IOException ex) {
            Logger.getLogger(ExecuteApiTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return deleteSuccessfull;
    }
    
    /*public static void keywordList(){
        comboBoxTestFlow = new JComboBox<>();
        
        String keywordlist = "URL,"
                + "SWITCH_WINDOW,"
                + "SWITCH_IFRAME,"
                + "SWITCH_DEFAULT,"
                + "ASSERT_VISIBLE,"
                + "SET,"
                + "CLEAR,"
                + "GET,"
                + "CLICK,"
                + "DOUBLE_CLICK,"
                + "PRESS_KEY,"
                + "PRESS_KEYS,"
                + "MOVE_TO_ELEMENT,"
                + "ASSERT_CLICKABLE,"
                + "OPEN_NEW_WINDOW,"
                + "MOVE_TO_WINDOW,"
                + "ASSERT_TEXT,"
                + "TAKE_SCREENSHOT,"
                + "MOUSE_HOVER,"
                + "USER_DEFINE,"
                + ":START,:GET,:IF,:THEN,:STOP,";
        
        String[] keywordList = keywordlist.split(",");
        for(String txt:keywordList){
            comboBoxTestFlow.addItem(txt);
        }
    }*/
    
    /*public void ObjectRepositoryList(){
        String objectrepositorylist = objectRepositoryList;
        comboBoxObjectRepository = new JComboBox<>();
        
        String[] keywordList = objectrepositorylist.split(",");
        for(String txt:keywordList){
            comboBoxObjectRepository.addItem(txt);
        }
    }*/
    
    public static boolean checkForRunRunning(){
        boolean checkBoxTicked =testRunInProgress;
        return checkBoxTicked;
    }
    
    public static boolean checkRunIsAvailable(){
        boolean run =false;
        for(int i=0; i<importDataFromExcelModel.getRowCount(); i++){
            run = (boolean) importDataFromExcelModel.getValueAt(i, 0);
            if(run ==true){
                run =true;
                break;
            }
        }
        return run;
    }
    
    public static boolean checkAnyRunNotAvailable(){
        boolean run =true;
        for(int i=0; i<importDataFromExcelModel.getRowCount(); i++){
            run = (boolean) importDataFromExcelModel.getValueAt(i, 0);
            if(run ==false){
                run =false;
                break;
            }
        }
        return run;
    }
    
    public static boolean checkIfAllTestStatusIsFail(){
        boolean failStatus =false;
        
        for(int i=0; i<importDataFromExcelModel.getRowCount(); i++){
            String getTestStatus =importDataFromExcelModel.getValueAt(i, 3).toString();
            boolean getRunStatus =(boolean) importDataFromExcelModel.getValueAt(i, 0);
            
            if(getTestStatus.toLowerCase().contentEquals("fail") && getRunStatus ==true){
                failStatus =true;
            }else{
                failStatus =false;
                break;
            }
        }
        return failStatus;
    }
    
    public static boolean checkIfAnyTestStatusIsFail(){
        boolean failStatus =false;
        
        for(int i=0; i<importDataFromExcelModel.getRowCount(); i++){
            String getTestStatus =importDataFromExcelModel.getValueAt(i, 3).toString();
            if(getTestStatus.toLowerCase().contentEquals("fail")){
                failStatus =true;
                break;
            }
        }
        return failStatus;
    }
    
    /*public void getObjectListFromObjectRepository(XSSFSheet suiteObjectRepo){
        objectRepositoryList = "";
        
        for(int i=1;i<=suiteObjectRepo.getLastRowNum();i++) {
            XSSFRow excelRow = suiteObjectRepo.getRow(i);
            try{
                objectRepositoryList = objectRepositoryList + excelRow.getCell(0).toString()+",";
            }catch(NullPointerException exp){
                //Logger.getLogger(EditRegressionSuite2.class.getName()).log(Level.SEVERE, null, exp);
            }
        }
    }*/
    
    /*public void getObjectListFromObjectRepositoryGlobal(){
        objectRepositoryList = "";
        try{
            for(int i=1;i<=excelSheetObjectRepositoryOR.getLastRowNum();i++) {
                XSSFRow excelRow = excelSheetObjectRepositoryOR.getRow(i);
                objectRepositoryList = objectRepositoryList + excelRow.getCell(0).toString()+",";
            }
        }catch (NullPointerException exp){
            //JOptionPane.showMessageDialog(RegressionSuiteScrollPane,"No Object Repository found for the loaded test suite!","Alert",JOptionPane.WARNING_MESSAGE);
        }
    }*/
    
    public static void setTableColWidthForExeRegSuiteTable(){
        tableExecuteRegSuite.getColumnModel().getColumn(0).setMaxWidth(35);
        tableExecuteRegSuite.getColumnModel().getColumn(0).setMinWidth(35);
        
        tableExecuteRegSuite.getColumnModel().getColumn(1).setMaxWidth(55);
        tableExecuteRegSuite.getColumnModel().getColumn(1).setMinWidth(55);
        
        //tableExecuteRegSuite.getColumnModel().getColumn(2).setMaxWidth(570);
        //tableExecuteRegSuite.getColumnModel().getColumn(2).setMinWidth(570);
        
        tableExecuteRegSuite.getColumnModel().getColumn(3).setMaxWidth(137);
        tableExecuteRegSuite.getColumnModel().getColumn(3).setMinWidth(137);
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
            java.util.logging.Logger.getLogger(ExecuteApiTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ExecuteApiTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ExecuteApiTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExecuteApiTest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //System.setProperty("java.awt.headless", "false");
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ExecuteApiTest().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static JButton bttnLoadRegSuite;
    public static JButton bttnRefreshTestRun;
    public static JButton bttnStartTestRun;
    public static JButton bttnStopTestRun;
    public static JCheckBox chkBoxFilterFailTest;
    public static JCheckBox chkBoxSelectDeselectAllRun;
    public JDesktopPane jDesktopPane1;
    public JDesktopPane jDesktopPane2;
    public JLabel jLabel1;
    public static JTextField jTextTestType;
    public JPanel pnlHeader;
    public static JScrollPane scrollExecuteRegSuite;
    public static JTable tableExecuteRegSuite;
    // End of variables declaration//GEN-END:variables
}
