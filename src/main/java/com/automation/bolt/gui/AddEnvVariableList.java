/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.bolt.gui;

import com.automation.bolt.common;
import static com.automation.bolt.common.tabOutFromEditingColumn;
import com.automation.bolt.constants;
import static com.automation.bolt.gui.EditRegressionSuite.*;
import static com.automation.bolt.gui.ObjectRepoFrame.RegSuite;
//import static com.automation.bolt.gui.EditRegressionSuite.importDataFromExcelModel;
import com.automation.bolt.renderer.tableCellRendererAPIEnvVar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author zakir
 */
public class AddEnvVariableList extends javax.swing.JFrame {
        
    public static DefaultTableModel addEnvVariableTabModel = new DefaultTableModel();
    
    public static TableColumn envVarNameCol =null;
    public static TableColumn envVarValueCol =null;
    
    public static JTextField envVarNameTxt =new JTextField();
    public static JTextField envVarValueTxt =new JTextField();
    
    public static boolean fileSaved;
    
    public static DefaultTableModel importDataFromExcelModelORBackup = new DefaultTableModel();
    public ObjectRepoFrame objRepo = new ObjectRepoFrame();
    
    public static JComboBox<String> comboBoxTestFlow = new JComboBox<String>();
    public static JComboBox<String> comboBoxObjectRepository = new JComboBox<String>();
    //public static tableAddORCellRenderer renderer = new tableAddORCellRenderer();
    
    private TableColumn testFlowColumn =new TableColumn();
    public TableColumn testObjectRepoColumn =new TableColumn();
    
    //private XSSFSheet excelSheetTestFlow;
    //public XSSFSheet excelSheetObjectRepository;
    //public XSSFSheet excelSheetObjectRepositorySecodary;
    //public XSSFSheet excelSheetObjectRepositoryOR;
    //public XSSFSheet excelSheetObjectRepositoryORSecondary;
    //private String objectRepositoryList = "";
    //public static JFileChooser excelFileImport;
    //public static JFileChooser excelFileImportOR;
    //public static File excelFile;
    //public static File excelFileOR;
    //public File excelFileORSub;
    //public String testSuiteName;
    //public XSSFWorkbook excelImportWorkBook = new XSSFWorkbook();
    //public XSSFWorkbook excelImportWorkBookOR = new XSSFWorkbook();
    //public boolean noRepoFound;
    //public boolean testSuiteUploaded;
    //public static String testSuiteFilePath;
    //public static String testGlobalORFilePath;
    //public static String testGlobalORAssociatedFilePath;
    //public static FileInputStream excelFIS;
    //public static boolean previousCellClicked =false;
    
    //public static TableColumn elmNameCol =null;
    //public static JTextField elmNameTxt =new JTextField();
    
    //public static TableColumn elmIdCol =null;
    //public static JTextField elmIdTxt =new JTextField();
    
    //public static TableColumn elmXpathCol =null;
    //public static JTextField elmXpathTxt =new JTextField();
    
    //public static boolean duplicateTestId =false;
    public static int editableRow;
    
    public static int getFlowCellxPoint;
    public static int getFlowCellyPoint;
    public static int getEditingRow;
    public static int getEditingColumn;
    
    public static String getSelRowTestFlow;
    
    //public static  HashMap<Integer, String> newTestElmList = new HashMap<>();
    //public static  HashMap<Integer, String> testElmList = new HashMap<>();
   
    public static boolean testElmNameVisible;
    /**
     * Creates new form AddEnvVariable
     */
    public AddEnvVariableList() {
        initComponents();
        addEnvVariableTabModel =(DefaultTableModel) tableEnvVariable.getModel();
        
        envVarNameCol =tableEnvVariable.getColumnModel().getColumn(0);
        envVarNameCol.setCellEditor(new DefaultCellEditor(envVarNameTxt));
        
        envVarValueCol =tableEnvVariable.getColumnModel().getColumn(1);
        envVarValueCol.setCellEditor(new DefaultCellEditor(envVarValueTxt));
        
        envVarNameTxt.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent evt) {
                common.testElmNameTxtTxtKeyTyped(evt, envVarNameTxt);
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

        pnlEnvVariable = new javax.swing.JPanel();
        scrollPEnvVariable = new javax.swing.JScrollPane();
        tableEnvVariable = new javax.swing.JTable();
        dPnlMenu = new javax.swing.JDesktopPane();
        pnlDeleteEnvVariable = new javax.swing.JPanel();
        DeleteEnvVariable = new javax.swing.JButton();
        pnlSaveEnvVariable = new javax.swing.JPanel();
        SaveEnvVariable = new javax.swing.JButton();
        pnlAddEnvVariable = new javax.swing.JPanel();
        AddEnvVariable = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Environment Variable List");
        setName("envVarFrame"); // NOI18N
        setResizable(false);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        scrollPEnvVariable.setAutoscrolls(true);
        scrollPEnvVariable.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N

        tableEnvVariable.setBackground(new java.awt.Color(51, 51, 51));
        tableEnvVariable.setFont(new java.awt.Font("Consolas", 0, 13)); // NOI18N
        tableEnvVariable.setForeground(new java.awt.Color(255, 255, 255));
        tableEnvVariable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "name", "value"
            }
        ));
        tableEnvVariable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableEnvVariable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableEnvVariable.setName(""); // NOI18N
        tableEnvVariable.setRowHeight(30);
        tableEnvVariable.setRowMargin(2);
        tableEnvVariable.setSelectionBackground(new java.awt.Color(255, 153, 153));
        tableEnvVariable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableEnvVariable.setShowGrid(true);
        tableEnvVariable.getTableHeader().setReorderingAllowed(false);
        tableEnvVariable.setUpdateSelectionOnSort(false);
        tableEnvVariable.setVerifyInputWhenFocusTarget(false);
        tableEnvVariable.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tableEnvVariableFocusGained(evt);
            }
        });
        tableEnvVariable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tableEnvVariableMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableEnvVariableMouseReleased(evt);
            }
        });
        tableEnvVariable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tableEnvVariableKeyReleased(evt);
            }
        });
        scrollPEnvVariable.setViewportView(tableEnvVariable);

        javax.swing.GroupLayout pnlEnvVariableLayout = new javax.swing.GroupLayout(pnlEnvVariable);
        pnlEnvVariable.setLayout(pnlEnvVariableLayout);
        pnlEnvVariableLayout.setHorizontalGroup(
            pnlEnvVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPEnvVariable, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pnlEnvVariableLayout.setVerticalGroup(
            pnlEnvVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollPEnvVariable, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
        );

        pnlDeleteEnvVariable.setBackground(new java.awt.Color(0, 0, 0));

        DeleteEnvVariable.setBackground(new java.awt.Color(0, 0, 0));
        DeleteEnvVariable.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        DeleteEnvVariable.setForeground(new java.awt.Color(255, 255, 255));
        DeleteEnvVariable.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir")+"\\icons\\deleteTestStep_Element.png"));
        DeleteEnvVariable.setToolTipText("delete Env variable");
        DeleteEnvVariable.setBorder(null);
        DeleteEnvVariable.setBorderPainted(false);
        DeleteEnvVariable.setContentAreaFilled(false);
        DeleteEnvVariable.setFocusPainted(false);
        DeleteEnvVariable.setFocusable(false);
        DeleteEnvVariable.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        DeleteEnvVariable.setRequestFocusEnabled(false);
        DeleteEnvVariable.setRolloverEnabled(false);
        DeleteEnvVariable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                DeleteEnvVariableMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                DeleteEnvVariableMouseExited(evt);
            }
        });
        DeleteEnvVariable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteEnvVariableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlDeleteEnvVariableLayout = new javax.swing.GroupLayout(pnlDeleteEnvVariable);
        pnlDeleteEnvVariable.setLayout(pnlDeleteEnvVariableLayout);
        pnlDeleteEnvVariableLayout.setHorizontalGroup(
            pnlDeleteEnvVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(DeleteEnvVariable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlDeleteEnvVariableLayout.setVerticalGroup(
            pnlDeleteEnvVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDeleteEnvVariableLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(DeleteEnvVariable, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlSaveEnvVariable.setBackground(new java.awt.Color(0, 0, 0));

        SaveEnvVariable.setBackground(new java.awt.Color(0, 0, 0));
        SaveEnvVariable.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        SaveEnvVariable.setForeground(new java.awt.Color(255, 255, 255));
        SaveEnvVariable.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir")+"\\icons\\saveTestSuite.png"));
        SaveEnvVariable.setToolTipText("save Env variable");
        SaveEnvVariable.setBorder(null);
        SaveEnvVariable.setBorderPainted(false);
        SaveEnvVariable.setContentAreaFilled(false);
        SaveEnvVariable.setFocusPainted(false);
        SaveEnvVariable.setFocusable(false);
        SaveEnvVariable.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        SaveEnvVariable.setRequestFocusEnabled(false);
        SaveEnvVariable.setRolloverEnabled(false);
        SaveEnvVariable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SaveEnvVariableMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                SaveEnvVariableMouseExited(evt);
            }
        });
        SaveEnvVariable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveEnvVariableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSaveEnvVariableLayout = new javax.swing.GroupLayout(pnlSaveEnvVariable);
        pnlSaveEnvVariable.setLayout(pnlSaveEnvVariableLayout);
        pnlSaveEnvVariableLayout.setHorizontalGroup(
            pnlSaveEnvVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SaveEnvVariable, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
        );
        pnlSaveEnvVariableLayout.setVerticalGroup(
            pnlSaveEnvVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSaveEnvVariableLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(SaveEnvVariable, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlAddEnvVariable.setBackground(new java.awt.Color(0, 0, 0));

        AddEnvVariable.setBackground(new java.awt.Color(0, 0, 0));
        AddEnvVariable.setFont(new java.awt.Font("Consolas", 1, 14)); // NOI18N
        AddEnvVariable.setForeground(new java.awt.Color(255, 255, 255));
        AddEnvVariable.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir")+"\\icons\\addTestStep_Element.png"));
        AddEnvVariable.setToolTipText("add Env variable");
        AddEnvVariable.setBorder(null);
        AddEnvVariable.setBorderPainted(false);
        AddEnvVariable.setContentAreaFilled(false);
        AddEnvVariable.setFocusPainted(false);
        AddEnvVariable.setFocusable(false);
        AddEnvVariable.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        AddEnvVariable.setRequestFocusEnabled(false);
        AddEnvVariable.setRolloverEnabled(false);
        AddEnvVariable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                AddEnvVariableMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                AddEnvVariableMouseExited(evt);
            }
        });
        AddEnvVariable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddEnvVariableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlAddEnvVariableLayout = new javax.swing.GroupLayout(pnlAddEnvVariable);
        pnlAddEnvVariable.setLayout(pnlAddEnvVariableLayout);
        pnlAddEnvVariableLayout.setHorizontalGroup(
            pnlAddEnvVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AddEnvVariable, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlAddEnvVariableLayout.setVerticalGroup(
            pnlAddEnvVariableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAddEnvVariableLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(AddEnvVariable, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        dPnlMenu.setLayer(pnlDeleteEnvVariable, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dPnlMenu.setLayer(pnlSaveEnvVariable, javax.swing.JLayeredPane.DEFAULT_LAYER);
        dPnlMenu.setLayer(pnlAddEnvVariable, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout dPnlMenuLayout = new javax.swing.GroupLayout(dPnlMenu);
        dPnlMenu.setLayout(dPnlMenuLayout);
        dPnlMenuLayout.setHorizontalGroup(
            dPnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dPnlMenuLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(dPnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlSaveEnvVariable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlAddEnvVariable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlDeleteEnvVariable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(1, 1, 1))
        );
        dPnlMenuLayout.setVerticalGroup(
            dPnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dPnlMenuLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(pnlAddEnvVariable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlDeleteEnvVariable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlSaveEnvVariable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pnlEnvVariable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(dPnlMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlEnvVariable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dPnlMenu))
                .addGap(0, 0, 0))
        );

        getAccessibleContext().setAccessibleParent(this);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tableEnvVariableFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableEnvVariableFocusGained
        /*if(ObjectRepoTable.getRowCount()>0){
            for(int i=0; i<ObjectRepoTable.getRowCount(); i++)
            testElmList.put(i, ObjectRepoTable.getValueAt(i, 0).toString());
        }*/
    }//GEN-LAST:event_tableEnvVariableFocusGained

    private void tableEnvVariableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableEnvVariableMousePressed
        getFlowCellxPoint =tableEnvVariable.rowAtPoint(evt.getPoint());
        getFlowCellyPoint =tableEnvVariable.columnAtPoint(evt.getPoint());
        getEditingRow =tableEnvVariable.getEditingRow();

        int getCurRow = tableEnvVariable.convertRowIndexToModel(tableEnvVariable.rowAtPoint(evt.getPoint()));
        int gerCurrCol = tableEnvVariable.convertColumnIndexToModel(tableEnvVariable.columnAtPoint(evt.getPoint()));

        tableEnvVariable.requestFocus();
        tableEnvVariable.setRowSelectionInterval(getFlowCellxPoint, getFlowCellxPoint);
        tableEnvVariable.scrollRectToVisible(tableEnvVariable.getCellRect(getFlowCellxPoint,getFlowCellxPoint, true));
          
        if(common.checkForDuplicateEnvVariable(null, envVarNameTxt,
            tableEnvVariable,
            testElmNameVisible,
            getFlowCellxPoint,
            getFlowCellyPoint,
            getEditingRow) ==true)
    return;

    if(duplicateTestId ==false){
        switch (gerCurrCol) {
            case 0:
                tableEnvVariable.editCellAt(getCurRow, 0);
                editableRow =tableEnvVariable.getEditingRow();
                envVarNameTxt.requestFocusInWindow();
                envVarNameTxt.setCaretPosition(0);
            break;
            case 1:
                tableEnvVariable.editCellAt(getCurRow, 1);
                editableRow =tableEnvVariable.getEditingRow();
                envVarValueTxt.requestFocusInWindow();
                envVarValueTxt.setCaretPosition(0);
            break;
            default:
            break;
        }
        }
    }//GEN-LAST:event_tableEnvVariableMousePressed

    private void tableEnvVariableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableEnvVariableMouseReleased
        getFlowCellxPoint =tableEnvVariable.rowAtPoint(evt.getPoint());
        getFlowCellyPoint =tableEnvVariable.columnAtPoint(evt.getPoint());
        getEditingRow =tableEnvVariable.getEditingRow();
        getEditingColumn =tableEnvVariable.getEditingColumn();

        testElmNameVisible =envVarNameTxt.isShowing();
        if(envVarNameTxt.isShowing()){
            //newTestElmList.put(tableEnvVariable.getSelectedRow(), envVarNameTxt.getText());
        }
    }//GEN-LAST:event_tableEnvVariableMouseReleased

    private void tableEnvVariableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tableEnvVariableKeyReleased
        common.checkForDuplicateEnvVariable(null, envVarNameTxt,
            tableEnvVariable,
            testElmNameVisible,
            getFlowCellxPoint,
            getFlowCellyPoint,
            getEditingRow);
    }//GEN-LAST:event_tableEnvVariableKeyReleased

    private void DeleteEnvVariableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DeleteEnvVariableMouseEntered
        pnlDeleteEnvVariable.setBackground(new java.awt.Color(250, 128, 114));
        DeleteEnvVariable.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_DeleteEnvVariableMouseEntered

    private void DeleteEnvVariableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DeleteEnvVariableMouseExited
        pnlDeleteEnvVariable.setBackground(new java.awt.Color(0, 0, 0));
        DeleteEnvVariable.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_DeleteEnvVariableMouseExited

    private void DeleteEnvVariableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteEnvVariableActionPerformed
        if(tableEnvVariable.getRowCount()>0){
            if(checkEditorIsShowing() ==true)
            tabOutFromEditingColumn(false, tableEnvVariable,
                getFlowCellxPoint,
                getFlowCellyPoint,
                tableEnvVariable.getSelectedRow());
            
            int rowIndex = tableEnvVariable.getSelectedRow();
            int lastRowIndex;
            String getValAt;
            
            if(tableEnvVariable.getRowCount()-1 == rowIndex)
                lastRowIndex = tableEnvVariable.getRowCount()-2;
            else
                lastRowIndex = rowIndex;

            addEnvVariableTabModel.removeRow(rowIndex);
            if(tableEnvVariable.getRowCount() >0){
                tableEnvVariable.setColumnSelectionInterval(0, 0);
                tableEnvVariable.setRowSelectionInterval(lastRowIndex, lastRowIndex);
            }
            tableEnvVariable.requestFocus();

        }else{
            JOptionPane.showMessageDialog(tableEnvVariable,"No Object(s) available to delete!","Alert",JOptionPane.WARNING_MESSAGE);
        }
        
        
        /*if(tableEnvVariable.getRowCount()>0){
            if(checkEditorIsShowing() ==true)
            tabOutFromEditingColumn(false, tableEnvVariable,
                getFlowCellxPoint,
                getFlowCellyPoint,
                tableEnvVariable.getSelectedRow());

            int rowIndex = tableEnvVariable.getSelectedRow();
            int lastRowIndex;
            String getValAt;

            if(tableEnvVariable.getRowCount()-1 == rowIndex)
                lastRowIndex = tableEnvVariable.getRowCount()-2;
            else
                lastRowIndex = rowIndex;

            try{
                getValAt =tableEnvVariable.getValueAt(rowIndex, 0).toString();
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

            addEnvVariableTabModel.removeRow(rowIndex);
            if(tableEnvVariable.getRowCount() >0){
                tableEnvVariable.setColumnSelectionInterval(0, 0);
                tableEnvVariable.setRowSelectionInterval(lastRowIndex, lastRowIndex);
            }
            tableEnvVariable.requestFocus();
        }else{
            JOptionPane.showMessageDialog(tableEnvVariable,"No Object(s) available to delete!","Alert",JOptionPane.WARNING_MESSAGE);
        }*/
    }//GEN-LAST:event_DeleteEnvVariableActionPerformed

    private void SaveEnvVariableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SaveEnvVariableMouseEntered
        pnlSaveEnvVariable.setBackground(new java.awt.Color(250, 128, 114));
        SaveEnvVariable.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_SaveEnvVariableMouseEntered

    private void SaveEnvVariableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SaveEnvVariableMouseExited
        pnlSaveEnvVariable.setBackground(new java.awt.Color(0, 0, 0));
        SaveEnvVariable.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_SaveEnvVariableMouseExited

    private void SaveEnvVariableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveEnvVariableActionPerformed
        boolean ORsuccessfullyUpdated = false;

        if(checkEditorIsShowing() ==true)
        tabOutFromEditingColumn(false, tableEnvVariable,
            getFlowCellxPoint,
            getFlowCellyPoint,
            getEditingRow);

        if(common.checkForDuplicateEnvVariable(null, envVarNameTxt,
            tableEnvVariable,
            testElmNameVisible,
            getFlowCellxPoint,
            getFlowCellyPoint,
            getEditingRow) ==true)
            return;

        if(common.checkForBlankColumnValue(tableEnvVariable, 0)){
            tableEnvVariable.editCellAt(common.blankRowIndex, 0);
            tableEnvVariable.getEditorComponent().requestFocus();
            tableEnvVariable.changeSelection(common.blankRowIndex, 0, false, true);
            JOptionPane.showMessageDialog(null, "Environment variable name can not be blank!", "Alert", JOptionPane.WARNING_MESSAGE);
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
        
        saveEnvVarList();
        JOptionPane.showMessageDialog(null, "saved successfully!", "Environment Variable List", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_SaveEnvVariableActionPerformed

    private void AddEnvVariableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddEnvVariableMouseEntered
        pnlAddEnvVariable.setBackground(new java.awt.Color(250, 128, 114));
        AddEnvVariable.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_AddEnvVariableMouseEntered

    private void AddEnvVariableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddEnvVariableMouseExited
        pnlAddEnvVariable.setBackground(new java.awt.Color(0, 0, 0));
        AddEnvVariable.setForeground(new java.awt.Color(255, 255, 255));
    }//GEN-LAST:event_AddEnvVariableMouseExited

    private void AddEnvVariableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddEnvVariableActionPerformed

        if(checkEditorIsShowing() ==true)
        tabOutFromEditingColumn(false, tableEnvVariable,
            getFlowCellxPoint,
            getFlowCellyPoint,
            getEditingRow);

        if(common.checkForDuplicateEnvVariable(null, envVarNameTxt,
            tableEnvVariable,
            testElmNameVisible,
            getFlowCellxPoint,
            getFlowCellyPoint,
            getEditingRow) ==true)
    return;

    addEnvVariableTabModel.addRow(new Object[]{null, null, null, null, null, null});
    tableEnvVariable.setColumnSelectionInterval(0, 0);
    tableEnvVariable.setRowSelectionInterval(tableEnvVariable.getRowCount() - 1, tableEnvVariable.getRowCount() - 1);
    tableEnvVariable.scrollRectToVisible(tableEnvVariable.getCellRect(tableEnvVariable.getRowCount() - 1, 0, true));
    tableEnvVariable.requestFocus();
    }//GEN-LAST:event_AddEnvVariableActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        tableCellRendererAPIEnvVar renderer = new tableCellRendererAPIEnvVar();
        tableEnvVariable.setDefaultRenderer(Object.class, renderer);
    }//GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Image titleIcon = Toolkit.getDefaultToolkit().getImage(constants.userDir+"\\icons\\bolt.jpg");
        this.setIconImage(titleIcon);
        tableEnvVariable.getColumnModel().getColumn(0).setMaxWidth(250);
        tableEnvVariable.getColumnModel().getColumn(0).setMinWidth(130);
        fileSaved =false;
        
        HashMap<Object, Object> jsonMap =common.uploadEnvVariableList();
        jsonMap.entrySet().stream().map(entry -> entry.getValue().toString().split("[,]")).forEachOrdered(getJsonTxt -> {
            addEnvVariableTabModel.addRow(getJsonTxt);
        });
        
         try{
            tableEnvVariable.setColumnSelectionInterval(0, 0);
            tableEnvVariable.setRowSelectionInterval(0, 0);
        }catch (IllegalArgumentException exp){}
    }//GEN-LAST:event_formWindowOpened

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        getEditingRow =tableEnvVariable.getEditingRow();
        if(common.checkForDuplicateEnvVariable(null, envVarNameTxt,
            tableEnvVariable,
            testElmNameVisible,
            getFlowCellxPoint,
            getFlowCellyPoint,
            getEditingRow) ==true)
        return;
    }//GEN-LAST:event_formMousePressed
    
    public static boolean checkEditorIsShowing(){
        boolean editorIsShowing =false;
        
        if(envVarNameTxt.isShowing() ||
                envVarValueTxt.isShowing() ){
            
            editorIsShowing =true;
        }
            
        return editorIsShowing;
    }
    
    public static void saveEnvVarList(){
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        
        if(tableEnvVariable.getRowCount() > 0){
            for(int i =0; i<tableEnvVariable.getRowCount(); i++){
                
                Object getVarName =tableEnvVariable.getValueAt(i, 0);
                if(getVarName ==null)
                    getVarName ="";

                Object getVarValue =tableEnvVariable.getValueAt(i, 1);
                if(getVarValue ==null)
                    getVarValue ="";

                jsonObject.put(getVarName, getVarValue);
            }
            
            array.add(jsonObject);
            
            try {
	    	 
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonElement jsonElement = JsonParser.parseString(array.toJSONString());
                Object prettyJson = gson.toJson(jsonElement);
	    	  
                File directory = new File(String.valueOf("./env-var"));

                if (!directory.exists()) {
                  directory.mkdir();    
                  }
	    	  
                FileWriter file = new FileWriter("./env-var/env-var-list.json");
                file.write(prettyJson.toString());
                file.close();
                  	          	    	  
            } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
        }else{
            File directory = new File(String.valueOf("./env-var"));
            if (!directory.exists()) {
                directory.mkdir();    
            }

            FileWriter file;
            try {
                file = new FileWriter("./env-var/env-var-list.json");
                file.write(array.toJSONString());
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(SSLCertificate.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
            java.util.logging.Logger.getLogger(AddEnvVariableList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddEnvVariableList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddEnvVariableList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddEnvVariableList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddEnvVariableList().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton AddEnvVariable;
    public static javax.swing.JButton DeleteEnvVariable;
    public static javax.swing.JButton SaveEnvVariable;
    private javax.swing.JDesktopPane dPnlMenu;
    private javax.swing.JPanel pnlAddEnvVariable;
    private javax.swing.JPanel pnlDeleteEnvVariable;
    private javax.swing.JPanel pnlEnvVariable;
    private javax.swing.JPanel pnlSaveEnvVariable;
    public static javax.swing.JScrollPane scrollPEnvVariable;
    public static javax.swing.JTable tableEnvVariable;
    // End of variables declaration//GEN-END:variables
}
