/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.excelSheetObjectRepositoryOR
 */
package com.automation.bolt.gui;

import com.automation.bolt.common;
import static com.automation.bolt.common.checkValueExistInColumn;
import com.automation.bolt.constants;
import static com.automation.bolt.gui.EditRegressionSuite.AssociateObjORJCheckBox;
import static com.automation.bolt.gui.EditRegressionSuite.GlobalORJRadioButton;
import static com.automation.bolt.gui.EditRegressionSuite.SaveSuite;
import static com.automation.bolt.gui.EditRegressionSuite.importDataFromExcelModel;
import static com.automation.bolt.gui.EditRegressionSuite.testIdTxt;
import static com.automation.bolt.gui.ObjectRepoFrame.RegSuite;
import com.automation.bolt.renderer.*;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JLayeredPane;
//import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.automation.bolt.renderer.*;
import static com.automation.bolt.gui.EditRegressionSuite.RegSuiteTable;
import javax.swing.LayoutStyle;

/**
 *
 * @author zakir
 */
public class TestElementRepository extends javax.swing.JFrame {
    //public static DefaultTableModel importDataFromExcelModel = new DefaultTableModel();
    public static DefaultTableModel importDataFromExcelModelOR = new DefaultTableModel();
    public static DefaultTableModel importDataFromExcelModelORBackup = new DefaultTableModel();
    public ObjectRepoFrame objRepo = new ObjectRepoFrame();
    
    public static JComboBox<String> comboBoxTestFlow = new JComboBox<String>();
    public static JComboBox<String> comboBoxObjectRepository = new JComboBox<String>();
    public static tableAddORCellRenderer renderer = new tableAddORCellRenderer();
    
    private TableColumn testFlowColumn =new TableColumn();
    public TableColumn testObjectRepoColumn =new TableColumn();
    
    //private XSSFSheet excelSheetTestFlow;
    public XSSFSheet excelSheetObjectRepository;
    public XSSFSheet excelSheetObjectRepositorySecodary;
    public XSSFSheet excelSheetObjectRepositoryOR;
    public XSSFSheet excelSheetObjectRepositoryORSecondary;
    private String objectRepositoryList = "";
    public static JFileChooser excelFileImport;
    public static JFileChooser excelFileImportOR;
    public static File excelFile;
    //public static File excelFileOR;
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
    
    public static TableColumn elmNameCol =null;
    public static JTextField elmNameTxt =new JTextField();
    
    public static TableColumn elmIdCol =null;
    public static JTextField elmIdTxt =new JTextField();
    
    public static TableColumn elmXpathCol =null;
    public static JTextField elmXpathTxt =new JTextField();
    
    public static boolean duplicateTestId =false;
    public static int editableRow;
    
    public static int getFlowCellxPoint;
    public static int getFlowCellyPoint;
    public static int getEditingRow;
    public static int getEditingColumn;
    
    public static String getSelRowTestFlow;
    
    public static  HashMap<Integer, String> newTestElmList = new HashMap<>();
    public static  HashMap<Integer, String> testElmList = new HashMap<>();
   
    public static boolean testElmNameVisible;    
    /**
     * Creates new form NewJFrame
     */

    public TestElementRepository() {
        initComponents();        
        
        elmNameCol =ObjectRepoTable.getColumnModel().getColumn(0);
        elmNameCol.setCellEditor(new DefaultCellEditor(elmNameTxt));
        
        elmIdCol =ObjectRepoTable.getColumnModel().getColumn(1);
        elmIdCol.setCellEditor(new DefaultCellEditor(elmIdTxt));
        
        elmXpathCol =ObjectRepoTable.getColumnModel().getColumn(2);
        elmXpathCol.setCellEditor(new DefaultCellEditor(elmXpathTxt));
        
        ObjectRepoTable.setDefaultRenderer(Object.class, renderer);
        
        elmNameTxt.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                common.testElmNameTxtTxtKeyTyped(evt, elmNameTxt);
            }
            
            public void keyReleased(KeyEvent evt) {
                newTestElmList.put(ObjectRepoTable.getSelectedRow(), elmNameTxt.getText());
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

        pnlObjectRepo = new javax.swing.JPanel();
        ObjectRepoScrollPane = new javax.swing.JScrollPane();
        ObjectRepoTable = new javax.swing.JTable();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        pnlDeleteTestElement = new javax.swing.JPanel();
        DeleteTestElement = new javax.swing.JButton();
        pnlSaveTestRepository = new javax.swing.JPanel();
        SaveTestRepository = new javax.swing.JButton();
        pnlAddNewTestElement = new javax.swing.JPanel();
        AddNewTestElement = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit Test Suite");
        setBounds(new java.awt.Rectangle(0, 0, 973, 500));
        setIconImages(null);
        setMinimumSize(new java.awt.Dimension(851, 328));
        setSize(new java.awt.Dimension(0, 0));
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        ObjectRepoScrollPane.setAutoscrolls(true);
        ObjectRepoScrollPane.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        ObjectRepoTable.setBackground(new java.awt.Color(51, 51, 51));
        ObjectRepoTable.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N
        ObjectRepoTable.setForeground(new java.awt.Color(255, 255, 255));
        ObjectRepoTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Test Element Name (user defined)", "id", "xpath"
            }
        ));
        ObjectRepoTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        ObjectRepoTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ObjectRepoTable.setName(""); // NOI18N
        ObjectRepoTable.setRowHeight(30);
        ObjectRepoTable.setRowMargin(2);
        ObjectRepoTable.setSelectionBackground(new java.awt.Color(255, 153, 153));
        ObjectRepoTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ObjectRepoTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ObjectRepoTable.setShowGrid(true);
        ObjectRepoTable.getTableHeader().setReorderingAllowed(false);
        ObjectRepoTable.setUpdateSelectionOnSort(false);
        ObjectRepoTable.setVerifyInputWhenFocusTarget(false);
        ObjectRepoTable.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ObjectRepoTableFocusGained(evt);
            }
        });
        ObjectRepoTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ObjectRepoTableMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ObjectRepoTableMouseReleased(evt);
            }
        });
        ObjectRepoTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ObjectRepoTableKeyReleased(evt);
            }
        });
        ObjectRepoScrollPane.setViewportView(ObjectRepoTable);

        javax.swing.GroupLayout pnlObjectRepoLayout = new javax.swing.GroupLayout(pnlObjectRepo);
        pnlObjectRepo.setLayout(pnlObjectRepoLayout);
        pnlObjectRepoLayout.setHorizontalGroup(
            pnlObjectRepoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ObjectRepoScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE)
        );
        pnlObjectRepoLayout.setVerticalGroup(
            pnlObjectRepoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlObjectRepoLayout.createSequentialGroup()
                .addComponent(ObjectRepoScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pnlDeleteTestElement.setBackground(new java.awt.Color(0, 0, 0));

        DeleteTestElement.setBackground(new java.awt.Color(0, 0, 0));
        DeleteTestElement.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        DeleteTestElement.setForeground(new java.awt.Color(255, 255, 255));
        DeleteTestElement.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir")+"\\icons\\deleteTestStep_Element.png"));
        DeleteTestElement.setToolTipText("delete test element");
        DeleteTestElement.setBorder(null);
        DeleteTestElement.setBorderPainted(false);
        DeleteTestElement.setContentAreaFilled(false);
        DeleteTestElement.setFocusPainted(false);
        DeleteTestElement.setFocusable(false);
        DeleteTestElement.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        DeleteTestElement.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        DeleteTestElement.setRequestFocusEnabled(false);
        DeleteTestElement.setRolloverEnabled(false);
        DeleteTestElement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                DeleteTestElementMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                DeleteTestElementMouseExited(evt);
            }
        });
        DeleteTestElement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteTestElementActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlDeleteTestElementLayout = new javax.swing.GroupLayout(pnlDeleteTestElement);
        pnlDeleteTestElement.setLayout(pnlDeleteTestElementLayout);
        pnlDeleteTestElementLayout.setHorizontalGroup(
            pnlDeleteTestElementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(DeleteTestElement, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );
        pnlDeleteTestElementLayout.setVerticalGroup(
            pnlDeleteTestElementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDeleteTestElementLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(DeleteTestElement, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlSaveTestRepository.setBackground(new java.awt.Color(0, 0, 0));

        SaveTestRepository.setBackground(new java.awt.Color(0, 0, 0));
        SaveTestRepository.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        SaveTestRepository.setForeground(new java.awt.Color(255, 255, 255));
        SaveTestRepository.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir")+"\\icons\\saveTestSuite.png"));
        SaveTestRepository.setToolTipText("save test elements");
        SaveTestRepository.setBorder(null);
        SaveTestRepository.setBorderPainted(false);
        SaveTestRepository.setContentAreaFilled(false);
        SaveTestRepository.setFocusPainted(false);
        SaveTestRepository.setFocusable(false);
        SaveTestRepository.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        SaveTestRepository.setRequestFocusEnabled(false);
        SaveTestRepository.setRolloverEnabled(false);
        SaveTestRepository.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SaveTestRepositoryMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                SaveTestRepositoryMouseExited(evt);
            }
        });
        SaveTestRepository.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveTestRepositoryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSaveTestRepositoryLayout = new javax.swing.GroupLayout(pnlSaveTestRepository);
        pnlSaveTestRepository.setLayout(pnlSaveTestRepositoryLayout);
        pnlSaveTestRepositoryLayout.setHorizontalGroup(
            pnlSaveTestRepositoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SaveTestRepository, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlSaveTestRepositoryLayout.setVerticalGroup(
            pnlSaveTestRepositoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSaveTestRepositoryLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(SaveTestRepository, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlAddNewTestElement.setBackground(new java.awt.Color(0, 0, 0));

        AddNewTestElement.setBackground(new java.awt.Color(0, 0, 0));
        AddNewTestElement.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        AddNewTestElement.setForeground(new java.awt.Color(255, 255, 255));
        AddNewTestElement.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir")+"\\icons\\addTestStep_Element.png"));
        AddNewTestElement.setToolTipText("add test element");
        AddNewTestElement.setBorder(null);
        AddNewTestElement.setBorderPainted(false);
        AddNewTestElement.setContentAreaFilled(false);
        AddNewTestElement.setFocusPainted(false);
        AddNewTestElement.setFocusable(false);
        AddNewTestElement.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        AddNewTestElement.setRequestFocusEnabled(false);
        AddNewTestElement.setRolloverEnabled(false);
        AddNewTestElement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                AddNewTestElementMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                AddNewTestElementMouseExited(evt);
            }
        });
        AddNewTestElement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddNewTestElementActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlAddNewTestElementLayout = new javax.swing.GroupLayout(pnlAddNewTestElement);
        pnlAddNewTestElement.setLayout(pnlAddNewTestElementLayout);
        pnlAddNewTestElementLayout.setHorizontalGroup(
            pnlAddNewTestElementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AddNewTestElement, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlAddNewTestElementLayout.setVerticalGroup(
            pnlAddNewTestElementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAddNewTestElementLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(AddNewTestElement, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jDesktopPane1.setLayer(pnlDeleteTestElement, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(pnlSaveTestRepository, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(pnlAddNewTestElement, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlDeleteTestElement, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlSaveTestRepository, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlAddNewTestElement, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(pnlAddNewTestElement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlDeleteTestElement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlSaveTestRepository, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlObjectRepo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(1, 1, 1)
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlObjectRepo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jDesktopPane1)
                .addGap(1, 1, 1))
        );

        getAccessibleContext().setAccessibleParent(this);

        setSize(new java.awt.Dimension(865, 365));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
            Logger.getLogger(TestElementRepository.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestElementRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return objectRepoExcel;
    }
    
    private void formWindowActivated(WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        tableAddORCellRenderer renderer = new tableAddORCellRenderer();
        ObjectRepoTable.setDefaultRenderer(Object.class, renderer);
    }//GEN-LAST:event_formWindowActivated

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
       
        if(ObjectRepoTable.getRowCount()>0){
            ObjectRepoTable.setRowSelectionInterval(0,0);
            ObjectRepoTable.scrollRectToVisible(ObjectRepoTable.getCellRect(0,0, true));
            ObjectRepoTable.requestFocus();

            for(int i=0; i<ObjectRepoTable.getRowCount(); i++)
                testElmList.put(i, ObjectRepoTable.getValueAt(i, 0).toString());
        }
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosed(WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        importDataFromExcelModelOR.getDataVector().removeAllElements();
        importDataFromExcelModelOR.fireTableDataChanged();
        newTestElmList = new HashMap<>();
        testElmList = new HashMap<>();
    }//GEN-LAST:event_formWindowClosed

    private void SaveTestRepositoryActionPerformed(ActionEvent evt) {//GEN-FIRST:event_SaveTestRepositoryActionPerformed
        boolean ORsuccessfullyUpdated = false;
      
        if(checkEditorIsShowing() ==true)
                tabOutFromEditingColumn(false, ObjectRepoTable, 
                getFlowCellxPoint, 
                getFlowCellyPoint, 
                getEditingRow);
            
        if(common.checkForDuplicateElementName(null, elmNameTxt,
            ObjectRepoTable,
            testElmNameVisible,
            getFlowCellxPoint,
            getFlowCellyPoint,
            getEditingRow) ==true)
        return;
        
        if(common.checkForBlankColumnValue(ObjectRepoTable, 0)){
            ObjectRepoTable.editCellAt(common.blankRowIndex, 0);
            ObjectRepoTable.getEditorComponent().requestFocus();
            ObjectRepoTable.changeSelection(common.blankRowIndex, 0, false, true);
            JOptionPane.showMessageDialog(null, "Element name can not be blank!", "Alert", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if(EditRegressionSuite.checkEditorIsShowing() ==true)
                tabOutFromEditingColumn(false, RegSuiteTable, 
                getFlowCellxPoint, 
                getFlowCellyPoint, 
                RegSuiteTable.getSelectedRow());
        
        if(common.checkForDuplicateTestId(importDataFromExcelModel, RegSuiteTable, RegSuiteTable.getSelectedRow(), testIdTxt) ==true){
            return;
        }
        
        if(EditRegressionSuite.LocalORJRadioButton.isSelected()){
            try {
                if(RegSuite.deleteAllTestSteps(EditRegressionSuite.excelFile,1) ==true){
                    
                    RegSuite.testObjectRepoColumn = new TableColumn();
                    RegSuite.comboBoxObjectRepository = new JComboBox<>();
                    RegSuite.comboBoxObjectRepository.setEditable(true);
                            
                    updateObjectRepository(EditRegressionSuite.excelFile,1);
                    
                    if(!EditRegressionSuite.AssociateObjORJCheckBox.isSelected()){
                        XSSFSheet getCurrSheet = RegSuite.getObjectRepositorySheet(EditRegressionSuite.testSuiteFilePath,1);
                        RegSuite.getObjectListFromObjectRepository(getCurrSheet);
                        RegSuite.ObjectRepositoryList();
                        
                        RegSuite.testObjectRepoColumn = EditRegressionSuite.RegSuiteTable.getColumnModel().getColumn(3);
                        RegSuite.testObjectRepoColumn.setCellEditor(new DefaultCellEditor(RegSuite.comboBoxObjectRepository));
                    }
                    JOptionPane.showMessageDialog(null,"Test suite "+"\""+EditRegressionSuite.excelFileImport.getName(EditRegressionSuite.excelFile)+"\""+" local repository updated and saved!","Alert",JOptionPane.WARNING_MESSAGE);
                    
                    try{
                        for (Map.Entry<Integer,String> entry : newTestElmList.entrySet()){
                            if(!testElmList.get(entry.getKey()).toString().contentEquals(entry.getValue())){
                                for(int i=0; i<RegSuiteTable.getRowCount(); i++){
                                    String getElm =RegSuiteTable.getValueAt(i, 3).toString();
                                    if(testElmList.get(entry.getKey()).toString().contentEquals(getElm)){
                                        RegSuiteTable.setValueAt(entry.getValue(), i, 3);
                                    }
                                }
                            }    
                        }
                    }catch(NullPointerException exp){System.out.println(exp.getMessage());}
                    
                    for(int i=0; i<ObjectRepoTable.getRowCount(); i++)
                        testElmList.put(i, ObjectRepoTable.getValueAt(i, 0).toString());
                    
                    SaveSuite.doClick();
                }
            } catch (IOException ex) {
                Logger.getLogger(ObjectRepoFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(EditRegressionSuite.GlobalORJRadioButton.isSelected()){
            try {
                if(RegSuite.deleteAllTestSteps(RegSuite.excelFileOR,0) ==true){
                    RegSuite.testObjectRepoColumn = new TableColumn();
                    RegSuite.comboBoxObjectRepository = new JComboBox<>();
                    RegSuite.comboBoxObjectRepository.setEditable(true);
                    
                    updateObjectRepository(RegSuite.excelFileOR,0);
                    ORsuccessfullyUpdated = true;
                    JOptionPane.showMessageDialog(EditRegressionSuite.RegressionSuiteScrollPane,"Global repository "+"\""+EditRegressionSuite.excelFileImportOR.getName(RegSuite.excelFileOR)+"\""+" updated and saved!","Alert",JOptionPane.WARNING_MESSAGE);
                }
                    
                if(ORsuccessfullyUpdated ==true){
                    if(EditRegressionSuite.AssociateObjORJCheckBox.isSelected()){
                        XSSFSheet getCurrSheet = RegSuite.getObjectRepositorySheet(EditRegressionSuite.testGlobalORAssociatedFilePath,0);
                        RegSuite.getObjectListFromObjectRepository(getCurrSheet);
                        RegSuite.ObjectRepositoryList();
                        
                        RegSuite.testObjectRepoColumn = EditRegressionSuite.RegSuiteTable.getColumnModel().getColumn(3);
                        RegSuite.testObjectRepoColumn.setCellEditor(new DefaultCellEditor(RegSuite.comboBoxObjectRepository));
                    }
                }
                
                if(AssociateObjORJCheckBox.isEnabled() && AssociateObjORJCheckBox.isSelected()){
                    try{
                        for (Map.Entry<Integer,String> entry : newTestElmList.entrySet()){
                            if(!testElmList.get(entry.getKey()).toString().contentEquals(entry.getValue())){
                                for(int i=0; i<RegSuiteTable.getRowCount(); i++){
                                    String getElm =RegSuiteTable.getValueAt(i, 3).toString();
                                    if(testElmList.get(entry.getKey()).toString().contentEquals(getElm)){
                                        RegSuiteTable.setValueAt(entry.getValue(), i, 3);
                                    }
                                }
                            }    
                        }
                    }catch(NullPointerException exp){System.out.println(exp.getMessage());}

                    for(int i=0; i<ObjectRepoTable.getRowCount(); i++)
                        testElmList.put(i, ObjectRepoTable.getValueAt(i, 0).toString());

                    SaveSuite.doClick();
                }
            } catch (IOException ex) {
                Logger.getLogger(ObjectRepoFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_SaveTestRepositoryActionPerformed

    private void SaveTestRepositoryMouseExited(MouseEvent evt) {//GEN-FIRST:event_SaveTestRepositoryMouseExited
        pnlSaveTestRepository.setBackground(new java.awt.Color(0, 0, 0));
        SaveTestRepository.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_SaveTestRepositoryMouseExited

    private void SaveTestRepositoryMouseEntered(MouseEvent evt) {//GEN-FIRST:event_SaveTestRepositoryMouseEntered
        pnlSaveTestRepository.setBackground(new java.awt.Color(250, 128, 114));
        SaveTestRepository.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_SaveTestRepositoryMouseEntered

    private void AddNewTestElementActionPerformed(ActionEvent evt) {//GEN-FIRST:event_AddNewTestElementActionPerformed
        
        if(checkEditorIsShowing() ==true)
                tabOutFromEditingColumn(false, ObjectRepoTable, 
                getFlowCellxPoint, 
                getFlowCellyPoint, 
                getEditingRow);
            
        if(common.checkForDuplicateElementName(null, elmNameTxt,
            ObjectRepoTable,
            testElmNameVisible,
            getFlowCellxPoint,
            getFlowCellyPoint,
            getEditingRow) ==true)
        return;
            
        importDataFromExcelModelOR.addRow(new Object[]{null, null, null, null, null, null});
        ObjectRepoTable.setColumnSelectionInterval(0, 0);
        ObjectRepoTable.setRowSelectionInterval(ObjectRepoTable.getRowCount() - 1, ObjectRepoTable.getRowCount() - 1);
        ObjectRepoTable.scrollRectToVisible(ObjectRepoTable.getCellRect(ObjectRepoTable.getRowCount() - 1, 0, true));
        ObjectRepoTable.requestFocus();
    }//GEN-LAST:event_AddNewTestElementActionPerformed

    private void AddNewTestElementMouseExited(MouseEvent evt) {//GEN-FIRST:event_AddNewTestElementMouseExited
        pnlAddNewTestElement.setBackground(new java.awt.Color(0, 0, 0));
        AddNewTestElement.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_AddNewTestElementMouseExited

    private void AddNewTestElementMouseEntered(MouseEvent evt) {//GEN-FIRST:event_AddNewTestElementMouseEntered
        pnlAddNewTestElement.setBackground(new java.awt.Color(250, 128, 114));
        AddNewTestElement.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_AddNewTestElementMouseEntered

    private void DeleteTestElementActionPerformed(ActionEvent evt) {//GEN-FIRST:event_DeleteTestElementActionPerformed
        if(ObjectRepoTable.getRowCount()>0){
            if(checkEditorIsShowing() ==true)
                tabOutFromEditingColumn(false, ObjectRepoTable, 
                getFlowCellxPoint, 
                getFlowCellyPoint, 
                ObjectRepoTable.getSelectedRow());
            
           int rowIndex = ObjectRepoTable.getSelectedRow();
           int lastRowIndex;
           String getValAt;
           
            if(ObjectRepoTable.getRowCount()-1 == rowIndex)
                lastRowIndex = ObjectRepoTable.getRowCount()-2;
            else
                lastRowIndex = rowIndex;
            
            try{
                getValAt =ObjectRepoTable.getValueAt(rowIndex, 0).toString();
            }catch(NullPointerException exp){
                getValAt ="";
            }
            
            if(!getValAt.isEmpty() && (EditRegressionSuite.LocalORJRadioButton.isSelected() || 
                EditRegressionSuite.AssociateObjORJCheckBox.isSelected())){
                
                boolean fndElementExist =checkValueExistInColumn(RegSuiteTable,"TestElement",getValAt);

                if(fndElementExist){
                    int response = JOptionPane.showConfirmDialog(null, //
                                "Deleting test element ["+getValAt+"] is used in the current test suite!\n\ndo you want to continue?", //
                                "Confirm", JOptionPane.YES_NO_OPTION, //
                                JOptionPane.QUESTION_MESSAGE);
                    if (response != JOptionPane.YES_OPTION) {
                        return;
                    }
                }
            }
             
            importDataFromExcelModelOR.removeRow(rowIndex);
                if(ObjectRepoTable.getRowCount() >0){
                    ObjectRepoTable.setColumnSelectionInterval(0, 0);
                    ObjectRepoTable.setRowSelectionInterval(lastRowIndex, lastRowIndex);
                }
            ObjectRepoTable.requestFocus();
        }else{
            JOptionPane.showMessageDialog(ObjectRepoTable,"No Object(s) available to delete!","Alert",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_DeleteTestElementActionPerformed

    private void DeleteTestElementMouseExited(MouseEvent evt) {//GEN-FIRST:event_DeleteTestElementMouseExited
        pnlDeleteTestElement.setBackground(new java.awt.Color(0, 0, 0));
        DeleteTestElement.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_DeleteTestElementMouseExited

    private void DeleteTestElementMouseEntered(MouseEvent evt) {//GEN-FIRST:event_DeleteTestElementMouseEntered
        pnlDeleteTestElement.setBackground(new java.awt.Color(250, 128, 114));
        DeleteTestElement.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_DeleteTestElementMouseEntered
    
    private void ObjectRepoTableMousePressed(MouseEvent evt) {//GEN-FIRST:event_ObjectRepoTableMousePressed
        getFlowCellxPoint =ObjectRepoTable.rowAtPoint(evt.getPoint());
        getFlowCellyPoint =ObjectRepoTable.columnAtPoint(evt.getPoint());
        getEditingRow =ObjectRepoTable.getEditingRow();
               
        int getCurRow = ObjectRepoTable.convertRowIndexToModel(ObjectRepoTable.rowAtPoint(evt.getPoint()));
        int gerCurrCol = ObjectRepoTable.convertColumnIndexToModel(ObjectRepoTable.columnAtPoint(evt.getPoint()));
        
        ObjectRepoTable.requestFocus();
        ObjectRepoTable.setRowSelectionInterval(getFlowCellxPoint, getFlowCellxPoint);
        ObjectRepoTable.scrollRectToVisible(ObjectRepoTable.getCellRect(getFlowCellxPoint,getFlowCellxPoint, true));
                            
        if(common.checkForDuplicateElementName(null, elmNameTxt,
            ObjectRepoTable,
            testElmNameVisible,
            getFlowCellxPoint,
            getFlowCellyPoint,
            getEditingRow) ==true)
        return;
        
        if(duplicateTestId ==false){
            switch (gerCurrCol) {
                case 0:
                    ObjectRepoTable.editCellAt(getCurRow, 0);
                    editableRow =ObjectRepoTable.getEditingRow();
                    elmNameTxt.requestFocusInWindow();
                    break;
                case 1:
                    ObjectRepoTable.editCellAt(getCurRow, 1);
                    elmIdTxt.requestFocusInWindow();
                    break;
                case 2:
                    ObjectRepoTable.editCellAt(getCurRow, 2);
                    elmXpathTxt.requestFocusInWindow();
                    break;
                default:
                    break;
            }
        }
    }//GEN-LAST:event_ObjectRepoTableMousePressed

    private void ObjectRepoTableKeyReleased(KeyEvent evt) {//GEN-FIRST:event_ObjectRepoTableKeyReleased
        common.checkForDuplicateElementName(null, elmNameTxt,
            ObjectRepoTable,
            testElmNameVisible,
            getFlowCellxPoint,
            getFlowCellyPoint,
            getEditingRow);
    }//GEN-LAST:event_ObjectRepoTableKeyReleased

    private void ObjectRepoTableFocusGained(FocusEvent evt) {//GEN-FIRST:event_ObjectRepoTableFocusGained
        /*if(ObjectRepoTable.getRowCount()>0){
            for(int i=0; i<ObjectRepoTable.getRowCount(); i++)
                testElmList.put(i, ObjectRepoTable.getValueAt(i, 0).toString());
        }*/
    }//GEN-LAST:event_ObjectRepoTableFocusGained

    private void ObjectRepoTableMouseReleased(MouseEvent evt) {//GEN-FIRST:event_ObjectRepoTableMouseReleased
        getFlowCellxPoint =ObjectRepoTable.rowAtPoint(evt.getPoint());
        getFlowCellyPoint =ObjectRepoTable.columnAtPoint(evt.getPoint());
        getEditingRow =ObjectRepoTable.getEditingRow();
        getEditingColumn =ObjectRepoTable.getEditingColumn();
                
        testElmNameVisible =elmNameTxt.isShowing();
        if(elmNameTxt.isShowing()){
            newTestElmList.put(ObjectRepoTable.getSelectedRow(), elmNameTxt.getText());
        }
    }//GEN-LAST:event_ObjectRepoTableMouseReleased

    private void formWindowClosing(WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if(checkEditorIsShowing() ==true)
               tabOutFromEditingColumn(false, ObjectRepoTable, 
               getFlowCellxPoint, 
               getFlowCellyPoint, 
               getEditingRow);
    }//GEN-LAST:event_formWindowClosing

    private void formWindowGainedFocus(WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
         if(ObjectRepoTable.getRowCount()>0){
            try{
                for(int i=0; i<ObjectRepoTable.getRowCount(); i++)
                    testElmList.put(i, ObjectRepoTable.getValueAt(i, 0).toString());
            }catch(NullPointerException exp){
                
            }    
        }
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

            for (int j = 0; j < importDataFromExcelModelOR.getRowCount(); j++) {
                XSSFRow row = excelSheetTestFlow.createRow(j + 1);
                for (int k = 0; k < importDataFromExcelModelOR.getColumnCount(); k++) {
                    cell = row.createCell(k);
                    cell.setCellType(CellType.STRING);
                    cell.setCellStyle(cellStyle);
                    try {
                        cell.setCellValue(importDataFromExcelModelOR.getValueAt(j, k).toString());
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
            Logger.getLogger(TestElementRepository.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestElementRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void updateObjectRepository(File excelFile, int sheetIndex){
        FileInputStream fis = null;
        
        try {
            fis = new FileInputStream(excelFile);
            XSSFWorkbook wbSuite = new XSSFWorkbook(fis);  
            XSSFSheet excelSheetTestFlow = wbSuite.getSheetAt(sheetIndex);
            XSSFCell cell = null;
            XSSFDataFormat format = wbSuite.createDataFormat();
            
            XSSFFont font = wbSuite.createFont();
            font.setColor(IndexedColors.WHITE.getIndex());
            CellStyle cellStyle = wbSuite.createCellStyle();
            cellStyle.setFont(font);
            
            cellStyle.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
            cellStyle.setFillPattern(FillPatternType.ALT_BARS);

            for(int j=0; j<importDataFromExcelModelOR.getRowCount();j++){
                XSSFRow row = excelSheetTestFlow.createRow(j+1);
                for(int k=0;k<importDataFromExcelModelOR.getColumnCount();k++){
                    cell = row.createCell(k);
                    cell.setCellType(CellType.STRING);
                    cell.setCellStyle(cellStyle);
                    try{
                        cell.setCellValue(importDataFromExcelModelOR.getValueAt(j, k).toString());
                    }catch (NullPointerException exp){
                        cell.setCellValue("");
                    }
                }
            }
        
            fis.close();
            try (FileOutputStream outFile = new FileOutputStream(excelFile)) {
                wbSuite.write(outFile);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EditRegressionSuite.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(TestElementRepository.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestElementRepository.class.getName()).log(Level.SEVERE, null, ex);
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
        ObjectRepoTable.getColumnModel().getColumn(0).setMaxWidth(61);
        ObjectRepoTable.getColumnModel().getColumn(0).setMinWidth(61);
        
        ObjectRepoTable.getColumnModel().getColumn(1).setMaxWidth(72);
        ObjectRepoTable.getColumnModel().getColumn(1).setMinWidth(72);
        
        ObjectRepoTable.getColumnModel().getColumn(2).setMaxWidth(163);
        ObjectRepoTable.getColumnModel().getColumn(2).setMinWidth(163);
    }
    
    public void openObjectRepository(XSSFSheet objRepo){
        importDataFromExcelModelOR = new DefaultTableModel();
        importDataFromExcelModelOR = (DefaultTableModel) ObjectRepoTable.getModel();
        
        if(objRepo !=null){
            for(int i=1;i<=objRepo.getLastRowNum();i++) {
                XSSFRow excelRow = objRepo.getRow(i);

                XSSFCell testElement = excelRow.getCell(0);
                XSSFCell elmId = excelRow.getCell(1);
                XSSFCell elmXpath = excelRow.getCell(2);

                importDataFromExcelModelOR.addRow(new Object[] {testElement,elmId,elmXpath});
           }
            if(ObjectRepoTable.getRowCount() >0){
                ObjectRepoTable.setRowSelectionInterval(0,0);
                ObjectRepoTable.scrollRectToVisible(ObjectRepoTable.getCellRect(0,0, true));
            }
        }
    }
    
    public static boolean checkEditorIsShowing(){
        boolean editorIsShowing =false;
        
        if(elmNameTxt.isShowing() ||
                elmIdTxt.isShowing() ||
                elmXpathTxt.isShowing() ||
                comboBoxTestFlow.isShowing() ||
                comboBoxObjectRepository.isShowing()){
            
            editorIsShowing =true;
        }
            
        return editorIsShowing;
    }
    
    /*public static boolean checkForDuplicateTestId(){
        String getCellVal = null;
        duplicateTestId =false;
        
        if(importDataFromExcelModel.isCellEditable(ObjectRepoTable.getSelectedRow(), 0) ==true){
             try{
                getCellVal =elmNameTxt.getText();
                //getCellVal =createORTabModel.getValueAt(tableAddOR.getSelectedRow(), 0).toString();
            }catch(NullPointerException exp){
                getCellVal ="";
            }
             
            if(!getCellVal.isEmpty()){
               int elmIndex =0;
               for(int i=0; i<importDataFromExcelModel.getRowCount(); i++){
                       String getCellTxt=null;
                       try{
                           getCellTxt =importDataFromExcelModel.getValueAt(i, 0).toString().toLowerCase();
                       }catch (NullPointerException exp){
                           getCellTxt ="";
                       }
                       if(getCellVal.toLowerCase().contentEquals(getCellTxt)){
                           elmIndex++;
                           if(elmIndex ==2){
                               //editableRow =RegressionSuiteTable.getSelectedRow();
                               ObjectRepoTable.editCellAt(editableRow, 0);
                               ObjectRepoTable.setSurrendersFocusOnKeystroke(true);
                               ObjectRepoTable.getEditorComponent().requestFocus();
                               
                               ObjectRepoTable.clearSelection();
                               ObjectRepoTable.changeSelection(editableRow, 0, false, true);
                               elmNameTxt.selectAll();
                               duplicateTestId =true;
                               JOptionPane.showMessageDialog(null, "Test Id ["+getCellTxt+"] already exist!", "Alert", JOptionPane.WARNING_MESSAGE);
                               break;
                           }
                       }
               }
           }
        }
        
        return duplicateTestId;
    }*/

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
            java.util.logging.Logger.getLogger(TestElementRepository.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TestElementRepository.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TestElementRepository.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TestElementRepository.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //System.setProperty("java.awt.headless", "false");
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new TestElementRepository().setVisible(true);
            System.out.println(new TestElementRepository().getWidth());
            System.out.println(new TestElementRepository().getHeight());
            //new EditRegressionSuite().setPreferredSize(new Dimension(959, 456));
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton AddNewTestElement;
    public static javax.swing.JButton DeleteTestElement;
    public static javax.swing.JScrollPane ObjectRepoScrollPane;
    public static javax.swing.JTable ObjectRepoTable;
    public static javax.swing.JButton SaveTestRepository;
    public javax.swing.JDesktopPane jDesktopPane1;
    public javax.swing.JPanel pnlAddNewTestElement;
    public javax.swing.JPanel pnlDeleteTestElement;
    public javax.swing.JPanel pnlObjectRepo;
    public javax.swing.JPanel pnlSaveTestRepository;
    // End of variables declaration//GEN-END:variables

    private void contentEquals(String testId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
