/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.automation.bolt.gui;

import static com.automation.bolt.common.killProcess;
import static com.automation.bolt.gui.CreateAPITest.saveTestFileWhileClosingTheWindow;
import static com.automation.bolt.gui.EditAPITest.saveTestSuitWhenWindowIsGettingClosed;
import static com.automation.bolt.gui.TestReporting.treeTestReporting;

import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;

import com.automation.bolt.constants;
import com.automation.bolt.renderer.JTreeCellRenderer;

/**
 *
 * @author zakir
 */
public class AutomationTestRunner extends javax.swing.JFrame {
    public EditRegressionSuite editTestSuite = new EditRegressionSuite();
    public EditAPITest editApiTest = new EditAPITest();
    public ExecuteApiTest runApiTest = new ExecuteApiTest();
    public ExecuteRegressionSuite runTestSuite = new ExecuteRegressionSuite();
    public CreateTestSuite createTestSuite = new CreateTestSuite();
    public CreateAPITest createApiTestSuite = new CreateAPITest();
    public SSLCertificate2 sslCertConfig = new SSLCertificate2();
    public TestReporting testReporting = new TestReporting();
    public SettingsAndConfiguration settingsAndConfiguration = new SettingsAndConfiguration(); 
    public static String bttnAPIBackColor;
    public static String userDir=System.getProperty("user.dir");
    //public AddEnvVariableList addEnvVariableList = new AddEnvVariableList();
    //public static Image titleIcon = Toolkit.getDefaultToolkit().getImage(constants.userDir+"C:\\Users\\zakir\\Documents\\bolt.jpg");
    /**
     * Creates new form AutomationTestRunner
     */
    public AutomationTestRunner() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        pnlBolt = new javax.swing.JPanel();
        lblBolt = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jDesktopPane2 = new javax.swing.JDesktopPane();
        pnlMenuBarGUI = new javax.swing.JPanel();
        pnlCreateTestSuite = new javax.swing.JPanel();
        lblCreateTestSuite = new javax.swing.JLabel();
        pnlEditTestSuite = new javax.swing.JPanel();
        lblEditTestSuite = new javax.swing.JLabel();
        pnlExecuteTestSuite = new javax.swing.JPanel();
        lblExecuteTestSuite = new javax.swing.JLabel();
        pnlAutomationTestReport = new javax.swing.JPanel();
        lblAutomationTestReport = new javax.swing.JLabel();
        radioBttnGUI = new javax.swing.JRadioButton();
        radioBttnAPI = new javax.swing.JRadioButton();
        lblChooseTestType = new javax.swing.JLabel();
        lblSettingsAndConfiguration = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(955, 258));
        setName("tab"); // NOI18N
        setResizable(false);
        setSize(new java.awt.Dimension(955, 258));
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

        pnlBolt.setBackground(new java.awt.Color(51, 51, 51));
        pnlBolt.setMinimumSize(new java.awt.Dimension(708, 115));
        pnlBolt.setOpaque(false);

        lblBolt.setFont(new java.awt.Font("Segoe UI", 1, 69)); // NOI18N
        lblBolt.setText("Bolt");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 9)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("T e s t  A c c e l e r a t o r . . . ");

        javax.swing.GroupLayout pnlBoltLayout = new javax.swing.GroupLayout(pnlBolt);
        pnlBolt.setLayout(pnlBoltLayout);
        pnlBoltLayout.setHorizontalGroup(
            pnlBoltLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBoltLayout.createSequentialGroup()
                .addGap(277, 277, 277)
                .addGroup(pnlBoltLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBoltLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel2))
                    .addComponent(lblBolt))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlBoltLayout.setVerticalGroup(
            pnlBoltLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBoltLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblBolt, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(99, 99, 99))
        );

        jDesktopPane1.setLayer(pnlBolt, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(pnlBolt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(pnlBolt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );

        pnlMenuBarGUI.setBackground(new java.awt.Color(51, 51, 51));
        pnlMenuBarGUI.setOpaque(false);
        pnlMenuBarGUI.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlCreateTestSuite.setBackground(new java.awt.Color(0, 0, 0));
        pnlCreateTestSuite.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlCreateTestSuiteMouseClicked(evt);
            }
        });

        lblCreateTestSuite.setFont(new java.awt.Font("Franklin Gothic Book", 0, 16)); // NOI18N
        lblCreateTestSuite.setForeground(new java.awt.Color(255, 255, 255));
        lblCreateTestSuite.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCreateTestSuite.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/buildTestSuite.png"));
            lblCreateTestSuite.setText("Create Test Suite");
            lblCreateTestSuite.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    lblCreateTestSuiteMouseEntered(evt);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    lblCreateTestSuiteMouseExited(evt);
                }
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    lblCreateTestSuiteMousePressed(evt);
                }
            });

            javax.swing.GroupLayout pnlCreateTestSuiteLayout = new javax.swing.GroupLayout(pnlCreateTestSuite);
            pnlCreateTestSuite.setLayout(pnlCreateTestSuiteLayout);
            pnlCreateTestSuiteLayout.setHorizontalGroup(
                pnlCreateTestSuiteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblCreateTestSuite, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
            );
            pnlCreateTestSuiteLayout.setVerticalGroup(
                pnlCreateTestSuiteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblCreateTestSuite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );

            pnlMenuBarGUI.add(pnlCreateTestSuite, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 240, 30));

            pnlEditTestSuite.setBackground(new java.awt.Color(0, 0, 0));
            pnlEditTestSuite.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    pnlEditTestSuiteMouseEntered(evt);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    pnlEditTestSuiteMouseExited(evt);
                }
            });

            lblEditTestSuite.setBackground(new java.awt.Color(204, 204, 255));
            lblEditTestSuite.setFont(new java.awt.Font("Franklin Gothic Book", 0, 16)); // NOI18N
            lblEditTestSuite.setForeground(new java.awt.Color(255, 255, 255));
            lblEditTestSuite.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            lblEditTestSuite.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/editTestSuite.png"));
                lblEditTestSuite.setText("Edit Test Suite");
                lblEditTestSuite.setMaximumSize(new java.awt.Dimension(163, 32));
                lblEditTestSuite.setMinimumSize(new java.awt.Dimension(163, 32));
                lblEditTestSuite.setPreferredSize(new java.awt.Dimension(163, 32));
                lblEditTestSuite.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        lblEditTestSuiteMouseEntered(evt);
                    }
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        lblEditTestSuiteMouseExited(evt);
                    }
                    public void mousePressed(java.awt.event.MouseEvent evt) {
                        lblEditTestSuiteMousePressed(evt);
                    }
                });

                javax.swing.GroupLayout pnlEditTestSuiteLayout = new javax.swing.GroupLayout(pnlEditTestSuite);
                pnlEditTestSuite.setLayout(pnlEditTestSuiteLayout);
                pnlEditTestSuiteLayout.setHorizontalGroup(
                    pnlEditTestSuiteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEditTestSuite, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                );
                pnlEditTestSuiteLayout.setVerticalGroup(
                    pnlEditTestSuiteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEditTestSuite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );

                pnlMenuBarGUI.add(pnlEditTestSuite, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 240, 30));

                pnlExecuteTestSuite.setBackground(new java.awt.Color(0, 0, 0));

                lblExecuteTestSuite.setFont(new java.awt.Font("Franklin Gothic Book", 0, 16)); // NOI18N
                lblExecuteTestSuite.setForeground(new java.awt.Color(255, 255, 255));
                lblExecuteTestSuite.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                lblExecuteTestSuite.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/executeTestSuite.png"));
                    lblExecuteTestSuite.setText("Execute Test Suite");
                    lblExecuteTestSuite.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                    lblExecuteTestSuite.setFocusable(false);
                    lblExecuteTestSuite.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                            lblExecuteTestSuiteMouseEntered(evt);
                        }
                        public void mouseExited(java.awt.event.MouseEvent evt) {
                            lblExecuteTestSuiteMouseExited(evt);
                        }
                        public void mousePressed(java.awt.event.MouseEvent evt) {
                            lblExecuteTestSuiteMousePressed(evt);
                        }
                    });

                    javax.swing.GroupLayout pnlExecuteTestSuiteLayout = new javax.swing.GroupLayout(pnlExecuteTestSuite);
                    pnlExecuteTestSuite.setLayout(pnlExecuteTestSuiteLayout);
                    pnlExecuteTestSuiteLayout.setHorizontalGroup(
                        pnlExecuteTestSuiteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblExecuteTestSuite, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    );
                    pnlExecuteTestSuiteLayout.setVerticalGroup(
                        pnlExecuteTestSuiteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlExecuteTestSuiteLayout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(lblExecuteTestSuite, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    );

                    pnlMenuBarGUI.add(pnlExecuteTestSuite, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 240, 30));

                    pnlAutomationTestReport.setBackground(new java.awt.Color(0, 0, 0));

                    lblAutomationTestReport.setFont(new java.awt.Font("Franklin Gothic Book", 0, 16)); // NOI18N
                    lblAutomationTestReport.setForeground(new java.awt.Color(255, 255, 255));
                    lblAutomationTestReport.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                    lblAutomationTestReport.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/testRunReport.png"));
                        lblAutomationTestReport.setText("Automation Test Report");
                        lblAutomationTestReport.addMouseListener(new java.awt.event.MouseAdapter() {
                            public void mouseEntered(java.awt.event.MouseEvent evt) {
                                lblAutomationTestReportMouseEntered(evt);
                            }
                            public void mouseExited(java.awt.event.MouseEvent evt) {
                                lblAutomationTestReportMouseExited(evt);
                            }
                            public void mousePressed(java.awt.event.MouseEvent evt) {
                                lblAutomationTestReportMousePressed(evt);
                            }
                        });

                        javax.swing.GroupLayout pnlAutomationTestReportLayout = new javax.swing.GroupLayout(pnlAutomationTestReport);
                        pnlAutomationTestReport.setLayout(pnlAutomationTestReportLayout);
                        pnlAutomationTestReportLayout.setHorizontalGroup(
                            pnlAutomationTestReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAutomationTestReport, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                        );
                        pnlAutomationTestReportLayout.setVerticalGroup(
                            pnlAutomationTestReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAutomationTestReport, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        );

                        pnlMenuBarGUI.add(pnlAutomationTestReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 240, 30));

                        radioBttnGUI.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                        radioBttnGUI.setForeground(new java.awt.Color(255, 255, 153));
                        radioBttnGUI.setSelected(true);
                        radioBttnGUI.setText("GUI Test");
                        radioBttnGUI.setBorder(null);
                        radioBttnGUI.setContentAreaFilled(false);
                        radioBttnGUI.setFocusPainted(false);
                        radioBttnGUI.setFocusable(false);
                        radioBttnGUI.addMouseListener(new java.awt.event.MouseAdapter() {
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                radioBttnGUIMouseClicked(evt);
                            }
                            public void mouseEntered(java.awt.event.MouseEvent evt) {
                                radioBttnGUIMouseEntered(evt);
                            }
                            public void mouseExited(java.awt.event.MouseEvent evt) {
                                radioBttnGUIMouseExited(evt);
                            }
                            public void mousePressed(java.awt.event.MouseEvent evt) {
                                radioBttnGUIMousePressed(evt);
                            }
                            public void mouseReleased(java.awt.event.MouseEvent evt) {
                                radioBttnGUIMouseReleased(evt);
                            }
                        });
                        radioBttnGUI.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                radioBttnGUIActionPerformed(evt);
                            }
                        });
                        pnlMenuBarGUI.add(radioBttnGUI, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 80, -1));

                        radioBttnAPI.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
                        radioBttnAPI.setForeground(new java.awt.Color(255, 255, 153));
                        radioBttnAPI.setText("API Test");
                        radioBttnAPI.setBorder(null);
                        radioBttnAPI.setContentAreaFilled(false);
                        radioBttnAPI.setFocusPainted(false);
                        radioBttnAPI.setFocusable(false);
                        radioBttnAPI.addMouseListener(new java.awt.event.MouseAdapter() {
                            public void mouseEntered(java.awt.event.MouseEvent evt) {
                                radioBttnAPIMouseEntered(evt);
                            }
                            public void mouseExited(java.awt.event.MouseEvent evt) {
                                radioBttnAPIMouseExited(evt);
                            }
                        });
                        radioBttnAPI.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                radioBttnAPIActionPerformed(evt);
                            }
                        });
                        pnlMenuBarGUI.add(radioBttnAPI, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 80, -1));

                        lblChooseTestType.setForeground(new java.awt.Color(255, 255, 255));
                        lblChooseTestType.setText("choose test type");
                        pnlMenuBarGUI.add(lblChooseTestType, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

                        lblSettingsAndConfiguration.setIcon(new javax.swing.ImageIcon(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/icons/settingAndConfiguration.png"));
                            lblSettingsAndConfiguration.setToolTipText("Setting & Configuration");
                            lblSettingsAndConfiguration.setEnabled(false);
                            lblSettingsAndConfiguration.setFocusable(false);
                            lblSettingsAndConfiguration.addMouseListener(new java.awt.event.MouseAdapter() {
                                public void mousePressed(java.awt.event.MouseEvent evt) {
                                    lblSettingsAndConfigurationMousePressed(evt);
                                }
                            });
                            pnlMenuBarGUI.add(lblSettingsAndConfiguration, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 34, -1));

                            jDesktopPane2.setLayer(pnlMenuBarGUI, javax.swing.JLayeredPane.DEFAULT_LAYER);

                            javax.swing.GroupLayout jDesktopPane2Layout = new javax.swing.GroupLayout(jDesktopPane2);
                            jDesktopPane2.setLayout(jDesktopPane2Layout);
                            jDesktopPane2Layout.setHorizontalGroup(
                                jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jDesktopPane2Layout.createSequentialGroup()
                                    .addGap(1, 1, 1)
                                    .addComponent(pnlMenuBarGUI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(1, 1, 1))
                            );
                            jDesktopPane2Layout.setVerticalGroup(
                                jDesktopPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(pnlMenuBarGUI, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                            );

                            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                            getContentPane().setLayout(layout);
                            layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGap(1, 1, 1)
                                    .addComponent(jDesktopPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(2, 2, 2)
                                    .addComponent(jDesktopPane1))
                            );
                            layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(1, 1, 1)
                                            .addComponent(jDesktopPane1))
                                        .addComponent(jDesktopPane2))
                                    .addGap(1, 1, 1))
                            );

                            setSize(new java.awt.Dimension(969, 300));
                            setLocationRelativeTo(null);
                        }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Image titleIcon = Toolkit.getDefaultToolkit().getImage(constants.userDir+"\\icons\\bolt.jpg");
        this.setIconImage(titleIcon);
        //this.setMinimumSize(new java.awt.Dimension(960, 304));
        
        //getDriverPathFromJSONfile();
        //makeDriverPathJSONfileNotEditable();
    }//GEN-LAST:event_formWindowOpened

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        //System.out.println(tabbedPaneMenuBar.getTabComponentAt(WIDTH).getName());
    }//GEN-LAST:event_formWindowActivated

    private void lblAutomationTestReportMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAutomationTestReportMouseExited
        pnlAutomationTestReport.setBackground(new Color(0,0,0));
        lblAutomationTestReport.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_lblAutomationTestReportMouseExited

    private void lblAutomationTestReportMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAutomationTestReportMouseEntered
        pnlAutomationTestReport.setBackground(new java.awt.Color(250, 128, 114));
        lblAutomationTestReport.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_lblAutomationTestReportMouseEntered

    private void lblExecuteTestSuiteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExecuteTestSuiteMousePressed
        if(lblExecuteTestSuite.getText().contentEquals("Execute Test Suite")){
            runTestSuite.setLocationRelativeTo(null);
            runTestSuite.setVisible(true);
        }else if(lblExecuteTestSuite.getText().contentEquals("Execute API Test")){
            //runApiTest.setLocationRelativeTo(null);
            runApiTest.setVisible(true);
            //if(addEnvVariableList.isVisible())
                //addEnvVariableList.dispose();
        }
    }//GEN-LAST:event_lblExecuteTestSuiteMousePressed

    private void lblExecuteTestSuiteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExecuteTestSuiteMouseExited
        pnlExecuteTestSuite.setBackground(new Color(0,0,0));
        lblExecuteTestSuite.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_lblExecuteTestSuiteMouseExited

    private void lblExecuteTestSuiteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExecuteTestSuiteMouseEntered
        pnlExecuteTestSuite.setBackground(new java.awt.Color(250, 128, 114));
        lblExecuteTestSuite.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_lblExecuteTestSuiteMouseEntered

    private void pnlEditTestSuiteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlEditTestSuiteMouseExited
        //pnlEditTestSuite.setBackground(new Color(204,204,255));
    }//GEN-LAST:event_pnlEditTestSuiteMouseExited

    private void pnlEditTestSuiteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlEditTestSuiteMouseEntered
        //pnlEditTestSuite.setBackground(java.awt.Color.pink);
    }//GEN-LAST:event_pnlEditTestSuiteMouseEntered

    private void lblEditTestSuiteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEditTestSuiteMousePressed
        if(lblEditTestSuite.getText().contentEquals("Edit Test Suite")){
            editTestSuite.setLocationRelativeTo(null);
            editTestSuite.setVisible(true);
        }else if(lblEditTestSuite.getText().contentEquals("Edit API Test")){
            //createApiTestSuite.setLocationRelativeTo(null);
            editApiTest.setVisible(true);
            //if(addEnvVariableList.isVisible())
                //addEnvVariableList.dispose();
        }
    }//GEN-LAST:event_lblEditTestSuiteMousePressed

    private void lblEditTestSuiteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEditTestSuiteMouseExited
        pnlEditTestSuite.setBackground(new Color(0,0,0));
        lblEditTestSuite.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_lblEditTestSuiteMouseExited

    private void lblEditTestSuiteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEditTestSuiteMouseEntered
        pnlEditTestSuite.setBackground(new java.awt.Color(250, 128, 114));
        lblEditTestSuite.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_lblEditTestSuiteMouseEntered

    private void pnlCreateTestSuiteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlCreateTestSuiteMouseClicked

    }//GEN-LAST:event_pnlCreateTestSuiteMouseClicked

    private void lblCreateTestSuiteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCreateTestSuiteMousePressed
        if(lblCreateTestSuite.getText().contentEquals(""
                + "Create Test Suite")){
            createTestSuite.setLocationRelativeTo(null);
            createTestSuite.setVisible(true);
        }else if(lblCreateTestSuite.getText().contentEquals("Create API Test")){
            //createApiTestSuite.setLocationRelativeTo(null);
            createApiTestSuite.setVisible(true);
            //if(addEnvVariableList.isVisible())
                //addEnvVariableList.dispose();
        }
    }//GEN-LAST:event_lblCreateTestSuiteMousePressed

    private void lblCreateTestSuiteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCreateTestSuiteMouseExited
        pnlCreateTestSuite.setBackground(new Color(0,0,0));
        lblCreateTestSuite.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_lblCreateTestSuiteMouseExited

    private void lblCreateTestSuiteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCreateTestSuiteMouseEntered
        pnlCreateTestSuite.setBackground(new java.awt.Color(250, 128, 114));
        lblCreateTestSuite.setForeground(new java.awt.Color(0,0,0));
    }//GEN-LAST:event_lblCreateTestSuiteMouseEntered

    private void radioBttnGUIMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_radioBttnGUIMouseEntered
        radioBttnGUI.setForeground(new java.awt.Color(250, 128, 114));
    }//GEN-LAST:event_radioBttnGUIMouseEntered

    private void radioBttnGUIMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_radioBttnGUIMouseExited
        radioBttnGUI.setForeground(new java.awt.Color(255,255,153));
    }//GEN-LAST:event_radioBttnGUIMouseExited

    private void radioBttnAPIMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_radioBttnAPIMouseEntered
        radioBttnAPI.setForeground(new java.awt.Color(250, 128, 114));
    }//GEN-LAST:event_radioBttnAPIMouseEntered

    private void radioBttnAPIMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_radioBttnAPIMouseExited
        radioBttnAPI.setForeground(new java.awt.Color(255,255,153));
    }//GEN-LAST:event_radioBttnAPIMouseExited

    private void radioBttnGUIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioBttnGUIActionPerformed
        if(radioBttnGUI.isSelected()){
            radioBttnAPI.setSelected(false);
            lblSettingsAndConfiguration.setEnabled(false);
            //lblEnvVar.setEnabled(false);
        }else
            radioBttnGUI.setSelected(true);
    }//GEN-LAST:event_radioBttnGUIActionPerformed

    private void radioBttnAPIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioBttnAPIActionPerformed
        if(radioBttnAPI.isEnabled() && radioBttnAPI.isSelected()){
            radioBttnGUI.setSelected(false);
            //JOptionPane.showMessageDialog(null, "Under Construction!", "Alert", JOptionPane.WARNING_MESSAGE);
            lblSettingsAndConfiguration.setEnabled(true);
            //lblEnvVar.setEnabled(true);
        }else
            radioBttnAPI.setSelected(true);
            lblCreateTestSuite.setText("Create API Test");
            lblEditTestSuite.setText("Edit API Test");
            lblExecuteTestSuite.setText("Execute API Test");
            lblAutomationTestReport.setText("API Automation Report");
    }//GEN-LAST:event_radioBttnAPIActionPerformed

    private void radioBttnGUIMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_radioBttnGUIMouseClicked
        lblCreateTestSuite.setText("Create Test Suite");
        lblEditTestSuite.setText("Edit Test Suite");
        lblExecuteTestSuite.setText("Execute Test Suite");
        lblAutomationTestReport.setText("Automation Test Report");
    }//GEN-LAST:event_radioBttnGUIMouseClicked

    private void radioBttnGUIMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_radioBttnGUIMousePressed
        //lblCreateTestSuite.setText("Build Test Suite");
        //lblEditTestSuite.setText("Edit Test Suite");
        //lblExecuteTestSuite.setText("Execute Test Suite");
        //lblAutomationTestReport.setText("Automation Test Report");
    }//GEN-LAST:event_radioBttnGUIMousePressed

    private void lblSettingsAndConfigurationMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSettingsAndConfigurationMousePressed
        if(lblSettingsAndConfiguration.isEnabled() && radioBttnAPI.isSelected()){
            sslCertConfig.setLocationRelativeTo(null);
            sslCertConfig.setVisible(true);
        }
        //settingsAndConfiguration.setLocationRelativeTo(null);
        //settingsAndConfiguration.setVisible(true);
    }//GEN-LAST:event_lblSettingsAndConfigurationMousePressed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        //if(addEnvVariableList.isVisible())
            //addEnvVariableList.dispose();
        
        if(sslCertConfig.isVisible())
            sslCertConfig.dispose();
        
        if(editTestSuite.isVisible())
            editTestSuite.dispose();
        
        if(runTestSuite.isVisible())
            runTestSuite.dispose();
        
        if(createTestSuite.isVisible())
            createTestSuite.dispose();
        
        if(testReporting.isVisible())
            testReporting.dispose();
        
        if(createApiTestSuite.isVisible())
            createApiTestSuite.dispose();
        
        if(editApiTest.isVisible())
            editApiTest.dispose();
        
        if(runApiTest.isVisible())
            runApiTest.dispose();
        
    }//GEN-LAST:event_formWindowClosed

    private void lblAutomationTestReportMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAutomationTestReportMousePressed
        if(lblAutomationTestReport.getText().contentEquals("Automation Test Report")){
            Image titleIcon = Toolkit.getDefaultToolkit().getImage(constants.userDir+"\\icons\\bolt.jpg");
            testReporting.setIconImage(titleIcon);

            JTreeCellRenderer renderer = new JTreeCellRenderer();
            treeTestReporting.setCellRenderer(renderer);
            
            testReporting.setLocationRelativeTo(null);
            testReporting.setVisible(true);
        }
    }//GEN-LAST:event_lblAutomationTestReportMousePressed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        killProcess("chromedriver.exe");
        killProcess("msedgedriver.exe");
        
        if(editApiTest.isVisible())
            saveTestSuitWhenWindowIsGettingClosed();
        
        if(createApiTestSuite.isVisible())
            saveTestFileWhileClosingTheWindow();
    }//GEN-LAST:event_formWindowClosing

    private void radioBttnGUIMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_radioBttnGUIMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_radioBttnGUIMouseReleased

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
            java.util.logging.Logger.getLogger(AutomationTestRunner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AutomationTestRunner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AutomationTestRunner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AutomationTestRunner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new AutomationTestRunner().setLocationRelativeTo(null);
            new AutomationTestRunner().setVisible(true);
            //MarqueeLabel myLable = new MarqueeLabel("Welcome to www.How2Java.com", MarqueeLabel.RIGHT_TO_LEFT, 20); 
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JDesktopPane jDesktopPane1;
    public static javax.swing.JDesktopPane jDesktopPane2;
    public static javax.swing.JLabel jLabel2;
    public static javax.swing.JLabel lblAutomationTestReport;
    public static javax.swing.JLabel lblBolt;
    public static javax.swing.JLabel lblChooseTestType;
    public static javax.swing.JLabel lblCreateTestSuite;
    public static javax.swing.JLabel lblEditTestSuite;
    public static javax.swing.JLabel lblExecuteTestSuite;
    public static javax.swing.JLabel lblSettingsAndConfiguration;
    public static javax.swing.JPanel pnlAutomationTestReport;
    public static javax.swing.JPanel pnlBolt;
    public static javax.swing.JPanel pnlCreateTestSuite;
    public static javax.swing.JPanel pnlEditTestSuite;
    public static javax.swing.JPanel pnlExecuteTestSuite;
    public static javax.swing.JPanel pnlMenuBarGUI;
    public static javax.swing.JRadioButton radioBttnAPI;
    public static javax.swing.JRadioButton radioBttnGUI;
    // End of variables declaration//GEN-END:variables
}
