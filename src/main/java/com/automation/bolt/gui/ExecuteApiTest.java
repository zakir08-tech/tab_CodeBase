/*
 * To change this license header, choose License Headers in Project Properties.addMouseListener
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.excelSheetObjectRepositoryOR
 */
package com.automation.bolt.gui;

import static com.automation.bolt.boltExecutor.getErrorMessage;
import static com.automation.bolt.boltExecutor.testRunInProgress;
import static com.api.automation.bolt.API_TestRunner.getCurrRunId;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
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

//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
//import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriverException;

import com.api.automation.bolt.boltApiExecutor;
import com.automation.bolt.constants;
import com.automation.bolt.renderer.RunTableColorCellRenderer;
import java.awt.Color;

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
    //private static JComboBox<String> comboBoxTestFlow = new JComboBox<>();
    public JComboBox<String> comboBoxObjectRepository = new JComboBox<>();
    //private TableColumn testFlowColumn;
    public TableColumn testObjectRepoColumn;
    private XSSFSheet excelSheetTestFlow;
    public XSSFSheet excelSheetObjectRepository;
    public XSSFSheet excelSheetObjectRepositorySecodary;
    public XSSFSheet excelSheetObjectRepositoryOR;
    public XSSFSheet excelSheetObjectRepositoryORSecondary;
    //private String objectRepositoryList="";
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
    //public static boltExecutor bExecutor = new boltExecutor();
    public static boltApiExecutor runThread = new boltApiExecutor();
    public static String testRunBrowser =null;
    RunTableColorCellRenderer RunCell_renderer = new RunTableColorCellRenderer();
    public static HashMap<String, String> testResultDocPath =new HashMap<String, String>();
    public static JButton resultDocPath =new JButton();
    public static HashMap<String, Boolean> getRunId = new HashMap<String, Boolean>();
    public static boolean duplicateRunId;
    public static boolean stopExecution;
    public static JTextField textTestType;
    //public static AddEnvVariableList envVarListWin = new AddEnvVariableList();
    
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
        tabExecuteRegSuite = new JTable();
        bttnStartTestRun = new JButton();
        txtTestTypeTag = new JTextField();
        bttnRefreshTestRun = new JButton();
        bttnStopTestRun = new JButton();
        bttnLoadRegSuite = new JButton();
        lblTestTypeTag = new JLabel();
        pnlHeader = new JPanel();
        chkBoxSelectDeselectAllRun = new JCheckBox();
        chkBoxFilterFailTest = new JCheckBox();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Execute API Test");
        setBackground(Color.lightGray);
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

        scrollExecuteRegSuite.setBackground(new Color(51, 51, 51));
        scrollExecuteRegSuite.setAutoscrolls(true);
        scrollExecuteRegSuite.setFont(new Font("Calibri", 0, 12)); // NOI18N
        scrollExecuteRegSuite.setMinimumSize(new Dimension(452, 402));

        tabExecuteRegSuite.setBackground(new Color(51, 51, 51));
        tabExecuteRegSuite.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tabExecuteRegSuite.setFont(new Font("Consolas", 0, 14)); // NOI18N
        tabExecuteRegSuite.setForeground(new Color(255, 255, 255));
        tabExecuteRegSuite.setModel(new DefaultTableModel(
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
        tabExecuteRegSuite.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        tabExecuteRegSuite.setMinimumSize(new Dimension(180, 0));
        tabExecuteRegSuite.setName(""); // NOI18N
        tabExecuteRegSuite.setRowHeight(22);
        tabExecuteRegSuite.setRowMargin(4);
        tabExecuteRegSuite.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabExecuteRegSuite.setShowGrid(true);
        tabExecuteRegSuite.getTableHeader().setReorderingAllowed(false);
        tabExecuteRegSuite.setUpdateSelectionOnSort(false);
        tabExecuteRegSuite.setVerifyInputWhenFocusTarget(false);
        tabExecuteRegSuite.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                tabExecuteRegSuiteFocusGained(evt);
            }
        });
        tabExecuteRegSuite.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tabExecuteRegSuiteMouseClicked(evt);
            }
            public void mouseReleased(MouseEvent evt) {
                tabExecuteRegSuiteMouseReleased(evt);
            }
        });
        tabExecuteRegSuite.addInputMethodListener(new InputMethodListener() {
            public void caretPositionChanged(InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(InputMethodEvent evt) {
                tabExecuteRegSuiteInputMethodTextChanged(evt);
            }
        });
        scrollExecuteRegSuite.setViewportView(tabExecuteRegSuite);
        if (tabExecuteRegSuite.getColumnModel().getColumnCount() > 0) {
            tabExecuteRegSuite.getColumnModel().getColumn(0).setPreferredWidth(5);
            tabExecuteRegSuite.getColumnModel().getColumn(1).setPreferredWidth(10);
        }

        bttnStartTestRun.setBackground(new Color(0, 0, 0));
        bttnStartTestRun.setFont(new Font("Consolas", 1, 14)); // NOI18N
        bttnStartTestRun.setForeground(new Color(255, 255, 255));
        bttnStartTestRun.setIcon(new ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/ApiRunTest.png"));
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

            txtTestTypeTag.setBackground(new Color(0, 0, 0));
            txtTestTypeTag.setFont(new Font("Tahoma", 0, 14)); // NOI18N
            txtTestTypeTag.setForeground(Color.pink);
            txtTestTypeTag.setBorder(BorderFactory.createEmptyBorder(1, 2, 1, 1));
            txtTestTypeTag.setCursor(new Cursor(Cursor.TEXT_CURSOR));
            txtTestTypeTag.setName("SetTestType"); // NOI18N
            txtTestTypeTag.addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent evt) {
                    txtTestTypeTagFocusLost(evt);
                }
            });

            bttnRefreshTestRun.setBackground(new Color(0, 0, 0));
            bttnRefreshTestRun.setFont(new Font("Consolas", 1, 14)); // NOI18N
            bttnRefreshTestRun.setForeground(new Color(255, 255, 255));
            bttnRefreshTestRun.setIcon(new ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/ApiRefreshTest.png"));
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

                bttnStopTestRun.setBackground(new Color(0, 0, 0));
                bttnStopTestRun.setFont(new Font("Consolas", 1, 14)); // NOI18N
                bttnStopTestRun.setForeground(new Color(255, 255, 255));
                bttnStopTestRun.setIcon(new ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/ApiStopRun.png"));
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

                    bttnLoadRegSuite.setBackground(new Color(0, 0, 0));
                    bttnLoadRegSuite.setFont(new Font("Consolas", 1, 14)); // NOI18N
                    bttnLoadRegSuite.setForeground(new Color(255, 255, 255));
                    bttnLoadRegSuite.setIcon(new ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/ApiUploadTest.png"));
                        bttnLoadRegSuite.setToolTipText("upload api test for run");
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

                        lblTestTypeTag.setFont(new Font("Consolas", 0, 12)); // NOI18N
                        lblTestTypeTag.setForeground(new Color(0, 0, 0));
                        lblTestTypeTag.setText("test type: @<tag_name>");

                        pnlHeader.setBackground(new Color(51, 51, 51));
                        pnlHeader.setForeground(new Color(51, 51, 51));
                        pnlHeader.setMinimumSize(new Dimension(206, 40));

                        chkBoxSelectDeselectAllRun.setFont(new Font("Tahoma", 1, 10)); // NOI18N
                        chkBoxSelectDeselectAllRun.setForeground(new Color(255, 255, 255));
                        chkBoxSelectDeselectAllRun.setText("Select ALL Run");
                        chkBoxSelectDeselectAllRun.setToolTipText("will select all test(s) for run");
                        chkBoxSelectDeselectAllRun.setBorder(null);
                        chkBoxSelectDeselectAllRun.setEnabled(false);
                        chkBoxSelectDeselectAllRun.setHorizontalAlignment(SwingConstants.LEFT);
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

                        chkBoxFilterFailTest.setFont(new Font("Tahoma", 1, 10)); // NOI18N
                        chkBoxFilterFailTest.setForeground(new Color(255, 255, 255));
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
                            .addGroup(GroupLayout.Alignment.TRAILING, pnlHeaderLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pnlHeaderLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(chkBoxSelectDeselectAllRun, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(chkBoxFilterFailTest, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
                        );

                        GroupLayout layout = new GroupLayout(getContentPane());
                        getContentPane().setLayout(layout);
                        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(scrollExecuteRegSuite, GroupLayout.DEFAULT_SIZE, 897, Short.MAX_VALUE)
                                .addGap(1, 1, 1))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(pnlHeader, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(bttnLoadRegSuite, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bttnRefreshTestRun, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblTestTypeTag)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTestTypeTag, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bttnStartTestRun, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bttnStopTestRun, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        );
                        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(pnlHeader, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(scrollExecuteRegSuite, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(bttnStopTestRun, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(bttnLoadRegSuite, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(bttnRefreshTestRun, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtTestTypeTag, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblTestTypeTag))
                                    .addComponent(bttnStartTestRun, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(8, 8, 8))
                        );

                        getAccessibleContext().setAccessibleParent(this);

                        setSize(new Dimension(913, 529));
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
       tabExecuteRegSuite.requestFocus();
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
        Image titleIcon = Toolkit.getDefaultToolkit().getImage(constants.userDir+"\\icons\\Phantom.png");
        this.setIconImage(titleIcon);
        tabExecuteRegSuite.setDefaultRenderer(Object.class, RunCell_renderer);
        
        //textTestType = ((JTextField) cBoxTestType.getEditor().getEditorComponent());
        //textTestType.setForeground(new java.awt.Color(255,102,102));
        //textTestType.setFont(new Font("Comic Sans MS",0,12));
        txtTestTypeTag.setEnabled(false);
    }//GEN-LAST:event_formWindowOpened

    private void bttnStartTestRunMouseEntered(MouseEvent evt) {//GEN-FIRST:event_bttnStartTestRunMouseEntered
        bttnStartTestRun.setForeground(new java.awt.Color(0,0,0));
                
        if(bttnStartTestRun.isEnabled() ==true){
            bttnStartTestRun.setBackground(new java.awt.Color(203, 67, 53));
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
        bttnLoadRegSuite.setBackground(new java.awt.Color(203, 67, 53));
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
        excelFileImport.setPreferredSize(new Dimension(450,300));
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
            importDataFromExcelModel = (DefaultTableModel) tabExecuteRegSuite.getModel();
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
                         XSSFCell testSummary = excelRow.getCell(20);

                        String getTestRunId =null;
                        if(testId.getCellType().toString().contentEquals("NUMERIC")){
                            double getTestId = Math.round(Double.parseDouble(testId.toString()));
                            int xgetTestId = (int)getTestId;
                            getTestRunId = String.valueOf(xgetTestId);

                        }else{
                            getTestRunId = testId.toString();
                        }

                            if(testId.toString().matches("[+-]?\\d*(\\.\\d+)?") && !testId.toString().isEmpty()){
                                importDataFromExcelModel.addRow(new Object[] {true, getTestRunId, testSummary, "In Queue" });
                        }
                    }catch(NullPointerException exp){

                    }
                }
                    
                tabExecuteRegSuite.setRowSelectionInterval(0,0);
                tabExecuteRegSuite.scrollRectToVisible(tabExecuteRegSuite.getCellRect(0,0, true));
                tabExecuteRegSuite.requestFocus();

                if(objRepo.isVisible()){
                    ObjectRepoFrame.importObjectRepoData.getDataVector().removeAllElements();
                    ObjectRepoFrame.importObjectRepoData.fireTableDataChanged();
                    objRepo.setTitle("Object Repository: "+excelFileImport.getName(excelFile));
                    objRepo.openObjectRepository(excelSheetObjectRepository);
                }
                this.setTitle("Execute Test Suite: "+excelFileImport.getName(excelFile));
                txtTestTypeTag.setEnabled(true);
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
        bttnStopTestRun.setBackground(new java.awt.Color(203, 67, 53));
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
                tabExecuteRegSuite.setEnabled(true);
                bttnLoadRegSuite.setEnabled(true);
            } 
        }
    }//GEN-LAST:event_formWindowGainedFocus

    private void bttnStartTestRunMouseReleased(MouseEvent evt) {//GEN-FIRST:event_bttnStartTestRunMouseReleased
        if(txtTestTypeTag.getText().trim().contentEquals("@") || !txtTestTypeTag.getText().trim().startsWith("@") && 
                !txtTestTypeTag.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(scrollExecuteRegSuite,"Tag name is not properly defined ["+txtTestTypeTag.getText()+"]");
            return;
        }
        
        if(!txtTestTypeTag.getText().trim().isEmpty()){
            if(selectTestAsPerTestType() ==false){return;}
        }
        
        if(tabExecuteRegSuite.getRowCount() >0){
          if(checkRunIsAvailable() ==false){
              JOptionPane.showMessageDialog(null, "No test is selected to execute!", "Alert", JOptionPane.WARNING_MESSAGE);
              return;
          }

          arrTestId = new ArrayList<>();
          for(int i=0; i<importDataFromExcelModel.getRowCount(); i++){
              boolean run = (boolean) importDataFromExcelModel.getValueAt(i, 0);
              if(run ==true){
                 arrTestId.add(importDataFromExcelModel.getValueAt(i, 1).toString());
                 importDataFromExcelModel.setValueAt("In Queue", i, 3);
              }
          }
        }else{
          JOptionPane.showMessageDialog(null, "No test suite is available to execute!", "Alert", JOptionPane.WARNING_MESSAGE);
          return;
        }
        
        if(tabExecuteRegSuite.getRowCount() >0 && bttnStartTestRun.isEnabled() ==true){
           if(checkRunIsAvailable() ==false)
               return;
           
            stopExecution =false;    
            bttnStartTestRun.setEnabled(false);
            chkBoxFilterFailTest.setEnabled(false);
            chkBoxSelectDeselectAllRun.setEnabled(false);
            tabExecuteRegSuite.setEnabled(false);
            bttnLoadRegSuite.setEnabled(false);
            bttnStopTestRun.setEnabled(true);
            bttnRefreshTestRun.setEnabled(false);
          
            if(runThread.isAlive())
                runThread.interrupt();
            
            runThread = new boltApiExecutor();
            runThread.start();
        }
    }//GEN-LAST:event_bttnStartTestRunMouseReleased

    private void bttnStopTestRunMouseReleased(MouseEvent evt) {//GEN-FIRST:event_bttnStopTestRunMouseReleased
        if(bttnStopTestRun.isEnabled() ==true){ 
            stopExecution =true;
            getErrorMessage ="This test run was stopped/Interrupted by the user!";
            try {
                bttnStopTestRun.setEnabled(false);
                runThread.interrupt();
            }catch (Exception exp) {
                System.out.println("Caught:" + exp.getMessage());
            }
        }
    }//GEN-LAST:event_bttnStopTestRunMouseReleased

    private void chkBoxFilterFailTestActionPerformed(ActionEvent evt) {//GEN-FIRST:event_chkBoxFilterFailTestActionPerformed

    }//GEN-LAST:event_chkBoxFilterFailTestActionPerformed

    private void chkBoxFilterFailTestMouseReleased(MouseEvent evt) {//GEN-FIRST:event_chkBoxFilterFailTestMouseReleased
        int getRowCnt =tabExecuteRegSuite.getRowCount();
        boolean setStatus =false;
        
        if(!chkBoxFilterFailTest.isEnabled())
            return;
                
        if(chkBoxFilterFailTest.isSelected()){
            for(int x=0; x<getRowCnt; x++){
                Boolean run=(Boolean) tabExecuteRegSuite.getValueAt(x, 0);
                String testId =tabExecuteRegSuite.getValueAt(x, 1).toString();
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
                String getTestStatus =tabExecuteRegSuite.getValueAt(x, 3).toString(); 
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
        int getRowCnt =tabExecuteRegSuite.getRowCount();
        boolean tagFound =false;
        
        for(int x=0; x<getRowCnt; x++){
            String getTestStatus =tabExecuteRegSuite.getValueAt(x, 2).toString(); 
            if(getTestStatus.toLowerCase().contains(txtTestTypeTag.getText().toLowerCase())){
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
            JOptionPane.showMessageDialog(scrollExecuteRegSuite,"No test is marked with the given tag ["+txtTestTypeTag.getText()+"]");
        }
        
        return tagFound;
    }
    
    private void bttnRefreshTestRunMouseEntered(MouseEvent evt) {//GEN-FIRST:event_bttnRefreshTestRunMouseEntered
        bttnRefreshTestRun.setBackground(new java.awt.Color(203, 67, 53));
        bttnRefreshTestRun.setForeground(new java.awt.Color(0,0,0));  
    }//GEN-LAST:event_bttnRefreshTestRunMouseEntered

    private void bttnRefreshTestRunMouseExited(MouseEvent evt) {//GEN-FIRST:event_bttnRefreshTestRunMouseExited
        bttnRefreshTestRun.setBackground(new java.awt.Color(0,0,0));
        bttnRefreshTestRun.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_bttnRefreshTestRunMouseExited

    private void bttnRefreshTestRunActionPerformed(ActionEvent evt) {//GEN-FIRST:event_bttnRefreshTestRunActionPerformed
        duplicateRunId =false; 
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
                    XSSFCell testSummary = excelRow.getCell(20);

                    String getTestRunId =null;
                    if(testId.getCellType().toString().contentEquals("NUMERIC")){
                        double getTestId = Math.round(Double.parseDouble(testId.toString()));
                        int xgetTestId = (int)getTestId;
                        getTestRunId = String.valueOf(xgetTestId);

                    }else{
                        getTestRunId = testId.toString();
                    }

                    if(testId.toString().matches("[+-]?\\d*(\\.\\d+)?") && !testId.toString().isEmpty()){
                        importDataFromExcelModel.addRow(new Object[] {true, getTestRunId, testSummary, "In Queue" });
                    }
                }catch(NullPointerException exp){

                }
            }

            tabExecuteRegSuite.setRowSelectionInterval(0,0);
            tabExecuteRegSuite.scrollRectToVisible(tabExecuteRegSuite.getCellRect(0,0, true));
            tabExecuteRegSuite.requestFocus();
            txtTestTypeTag.setText("");
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
        
        if(bttnStartTestRun.isFocusable() ==true){
            if(checkForRunRunning() ==true){
                bttnStartTestRun.setEnabled(false);
                chkBoxFilterFailTest.setEnabled(false);
            }else{
                if(duplicateRunId ==false){
                    bttnStartTestRun.setEnabled(true);
                }
                chkBoxSelectDeselectAllRun.setEnabled(true);
                tabExecuteRegSuite.setEnabled(true);
                bttnLoadRegSuite.setEnabled(true);
            } 
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

    private void tabExecuteRegSuiteInputMethodTextChanged(InputMethodEvent evt) {//GEN-FIRST:event_tabExecuteRegSuiteInputMethodTextChanged

    }//GEN-LAST:event_tabExecuteRegSuiteInputMethodTextChanged

    private void tabExecuteRegSuiteMouseReleased(MouseEvent evt) {//GEN-FIRST:event_tabExecuteRegSuiteMouseReleased
        boolean checkBoxTicked = true;

        for(int i=0; i<importDataFromExcelModel.getRowCount(); i++){
            boolean run = (boolean) importDataFromExcelModel.getValueAt(i, 0);
            if(run ==false){
                checkBoxTicked = false;
                break;
            }
        }

        int gerCurrCol = tabExecuteRegSuite.convertColumnIndexToModel(tabExecuteRegSuite.columnAtPoint(evt.getPoint()));
        int gerCurrRow = tabExecuteRegSuite.convertRowIndexToModel(tabExecuteRegSuite.rowAtPoint(evt.getPoint()));

        if(gerCurrCol ==0){
            if(!tabExecuteRegSuite.getValueAt(gerCurrRow, 0).toString().contentEquals("FAIL"))
            chkBoxFilterFailTest.setSelected(false);
        }

        if(chkBoxSelectDeselectAllRun.isSelected() ==true && checkBoxTicked ==false)
            chkBoxSelectDeselectAllRun.setSelected(false);

        if(chkBoxSelectDeselectAllRun.isSelected() ==false && checkBoxTicked ==true)
            chkBoxSelectDeselectAllRun.setSelected(true);
    }//GEN-LAST:event_tabExecuteRegSuiteMouseReleased

    private void tabExecuteRegSuiteMouseClicked(MouseEvent evt) {//GEN-FIRST:event_tabExecuteRegSuiteMouseClicked
        if (evt.getClickCount() ==2) {
            JTable target =(JTable)evt.getSource();
            int row =target.getSelectedRow();
            int column =target.getSelectedColumn();
            if(column ==3){
                String getCellTxt =tabExecuteRegSuite.getValueAt(row, column).toString();
                if(!getCellTxt.toLowerCase().contentEquals("not started") &&
                    !getCellTxt.toLowerCase().contentEquals("running...") &&
                    !getCellTxt.toLowerCase().contentEquals("interrupted!")){
                    String getRunId =tabExecuteRegSuite.getValueAt(row, 1).toString();
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
    }//GEN-LAST:event_tabExecuteRegSuiteMouseClicked

    private void tabExecuteRegSuiteFocusGained(FocusEvent evt) {//GEN-FIRST:event_tabExecuteRegSuiteFocusGained
        if(tabExecuteRegSuite.getRowCount() >0){
            //if(bttnStartTestRun.isEnabled()){
                //chkBoxRunHeadless.setEnabled(true);
                //chkBoxAssociateObjOR.setEnabled(true);
            //}

            //chkBoxFilterFailTest.setEnabled(true);
            //chkBoxSelectDeselectAllRun.setEnabled(true);
        }else if(tabExecuteRegSuite.getRowCount() ==0){
            //chkBoxRunHeadless.setEnabled(false);
            //chkBoxAssociateObjOR.setEnabled(false);
            chkBoxFilterFailTest.setEnabled(false);
            chkBoxSelectDeselectAllRun.setEnabled(false);
        }
    }//GEN-LAST:event_tabExecuteRegSuiteFocusGained

    private void bttnStopTestRunMousePressed(MouseEvent evt) {//GEN-FIRST:event_bttnStopTestRunMousePressed
        if(bttnStopTestRun.isEnabled())
            ExecuteApiTest.importDataFromExcelModel.setValueAt("Stopping...", getCurrRunId, 3);
    }//GEN-LAST:event_bttnStopTestRunMousePressed

    private void txtTestTypeTagFocusLost(FocusEvent evt) {//GEN-FIRST:event_txtTestTypeTagFocusLost
        //if(!jTextTestType.getText().isEmpty() &&
            //jTextTestType.getText().startsWith("@")){
            //selectTestAsPerTestType();
        //}
    }//GEN-LAST:event_txtTestTypeTagFocusLost
         
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
   
    public static void setTableColWidthForExeRegSuiteTable(){
        tabExecuteRegSuite.getColumnModel().getColumn(0).setMaxWidth(35);
        tabExecuteRegSuite.getColumnModel().getColumn(0).setMinWidth(35);
        
        tabExecuteRegSuite.getColumnModel().getColumn(1).setMaxWidth(55);
        tabExecuteRegSuite.getColumnModel().getColumn(1).setMinWidth(55);
        
        //tableExecuteRegSuite.getColumnModel().getColumn(2).setMaxWidth(570);
        //tableExecuteRegSuite.getColumnModel().getColumn(2).setMinWidth(570);
        
        tabExecuteRegSuite.getColumnModel().getColumn(3).setMaxWidth(137);
        tabExecuteRegSuite.getColumnModel().getColumn(3).setMinWidth(137);
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
    public JLabel lblTestTypeTag;
    public JPanel pnlHeader;
    public static JScrollPane scrollExecuteRegSuite;
    public static JTable tabExecuteRegSuite;
    public static JTextField txtTestTypeTag;
    // End of variables declaration//GEN-END:variables
}
