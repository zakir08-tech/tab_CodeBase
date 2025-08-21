/*
 * Updated WebElementInspector.java to fix JavaScript syntax error in elementCapture function, 
 * exempt @href and @style attributes for non-SVG element relative XPath generation,
 * align output area equally from left and right sides of the frame,
 * reduce the height of the URL text field with adjusted font size,
 * and adjust the size of Add and Copy buttons to improve appearance without changing their font size.
 */
package com.automation.bolt.gui;

import com.automation.bolt.constants;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.font.TextAttribute;
import java.awt.geom.RoundRectangle2D;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.awt.GraphicsEnvironment;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.plaf.basic.BasicTextPaneUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.NoSuchSessionException;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.JSONObject;

/**
 * WebElementInspector is a Swing-based GUI for inspecting web elements using Selenium WebDriver.
 */
public class WebElementInspector extends javax.swing.JFrame {
    public WebElementDetailsExtractor extractor = null;
    public WebDriver driver;
    public boolean inspectMode = false;
    public volatile boolean capturing = false;
    public final AtomicBoolean polling = new AtomicBoolean(true);
    private static final SimpleDateFormat LOG_TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final int POLLING_INTERVAL_MS = 100; // Optimized polling interval

    /**
     * Creates new form WebElementInspector
     */
    public WebElementInspector() {
        extractor = new WebElementDetailsExtractorImpl();
        initComponents();
        outputArea.setOpaque(true);
        outputArea.setBackground(new Color(0, 0, 0));
        outputArea.setForeground(new Color(255, 255, 255));
        outputArea.setCaretColor(new Color(255, 255, 255));
        outputArea.setUI(new BasicTextPaneUI());
        startBrowserPolling();
    }

    /**
     * Initializes the form components.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {
        urlField = new JTextField();
        urlLabel = new JLabel();
        scrollPane = new JScrollPane();
        outputArea = new JTextPane();
        loadButton = new JButton();
        inspectButton = new JButton();
        clearButton = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Web Element Inspector");
        setBackground(new Color(0, 0, 0));
        setBounds(new Rectangle(0, 0, 973, 500));
        setIconImages(null);
        setMinimumSize(new Dimension(851, 328));
        setSize(new Dimension(0, 0));
        addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowClosing(WindowEvent evt) {
                cleanup();
            }
        });

        urlField.setBackground(new Color(0, 0, 0));
        urlField.setFont(new Font("Segoe UI", 0, 12));
        urlField.setForeground(new Color(255, 255, 255));
        urlField.setHorizontalAlignment(JTextField.LEFT);
        urlField.setText("https://google.com");
        urlField.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255)));
        urlField.setCaretColor(new Color(255, 255, 255));
        urlField.setOpaque(true);
        urlField.setPreferredSize(new Dimension(0, 20));
        urlField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                urlFieldActionPerformed(evt);
            }
        });

        urlLabel.setText("URL");

        scrollPane.setBackground(new Color(0, 0, 0));
        scrollPane.setForeground(new Color(255, 255, 255));

        outputArea.setEditable(false);
        outputArea.setBackground(new Color(0, 0, 0));
        outputArea.setFont(new Font("Segoe UI", 0, 14));
        outputArea.setForeground(new Color(255, 153, 153));
        outputArea.setFocusable(false);
        scrollPane.setViewportView(outputArea);

        loadButton.setBackground(new Color(0, 0, 0));
        loadButton.setForeground(new Color(255, 255, 255));
        loadButton.setText("Load URL");
        loadButton.setBorderPainted(false);
        loadButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                loadButtonMouseEntered(evt);
            }
            public void mouseExited(MouseEvent evt) {
                loadButtonMouseExited(evt);
            }
        });
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                loadButtonActionPerformed(evt);
            }
        });

        inspectButton.setBackground(new Color(0, 0, 0));
        inspectButton.setForeground(new Color(255, 255, 255));
        inspectButton.setText("Inspect");
        inspectButton.setBorderPainted(false);
        inspectButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                inspectButtonMouseEntered(evt);
            }
            public void mouseExited(MouseEvent evt) {
                inspectButtonMouseExited(evt);
            }
        });
        inspectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                inspectButtonActionPerformed(evt);
            }
        });

        clearButton.setBackground(new Color(0, 0, 0));
        clearButton.setForeground(new Color(255, 255, 255));
        clearButton.setText("Clear");
        clearButton.setBorderPainted(false);
        clearButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                clearButtonMouseEntered(evt);
            }
            public void mouseExited(MouseEvent evt) {
                clearButtonMouseExited(evt);
            }
        });
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(urlLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(urlField, GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loadButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inspectButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearButton))
                    .addComponent(scrollPane))
                .addContainerGap())
        );
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(urlLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(urlField, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                            .addComponent(loadButton)
                            .addComponent(inspectButton)
                            .addComponent(clearButton))))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );

        getAccessibleContext().setAccessibleParent(this);

        setSize(new Dimension(646, 485));
        setLocationRelativeTo(null);
    }

    private void loadButtonActionPerformed(ActionEvent evt) {
        log("Load URL button clicked: " + urlField.getText());
        
        SwingUtilities.invokeLater(() -> {
            loadButton.setText("Loading...");
            loadButton.setEnabled(false);
            inspectButton.setEnabled(false);
            clearButton.setEnabled(false);
            appendToOutputArea("Initializing browser...\n");
        });

        if (driver == null || !isBrowserOpen()) {
            CompletableFuture.runAsync(() -> {
                try {
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions co = new ChromeOptions();
                    co.addArguments("--remote-allow-origins=*");
                    co.addArguments("--start-maximized");
                    co.addArguments("--ignore-certificate-errors");
                    co.addArguments("--blink-settings=imagesEnabled=true");
                    co.addArguments("--lang=en-US");
                    HashMap<String, Object> prefs = new HashMap<>();
                    prefs.put("profile.default_content_setting_values.notifications", 2);
                    co.setExperimentalOption("prefs", prefs);
                    driver = new ChromeDriver(co);
                    log("WebDriver initialized successfully");
                    loadUrl(urlField.getText());
                } catch (WebDriverException ex) {
                    SwingUtilities.invokeLater(() -> {
                        appendToOutputArea("Error initializing WebDriver: " + ex.getMessage() + "\n");
                        resetButtons();
                    });
                    log("Error initializing WebDriver: " + ex.getMessage());
                    ex.printStackTrace();
                }
            });
        } else {
            loadUrl(urlField.getText());
        }
    }

    private void inspectButtonActionPerformed(ActionEvent evt) {
        log("Toggle Inspect button clicked");
        inspectMode = !inspectMode;
        inspectButton.setBackground(inspectMode ? new Color(34, 139, 34) : new Color(0, 0, 0));
        inspectButton.setForeground(new Color(255, 255, 255));
        appendToOutputArea((inspectMode ? "Inspect Mode: Click an element in the browser (ensure browser window is focused)" : "Inspect Mode: Disabled") + "\n");
        log("Inspect Mode Toggled: " + inspectMode);
        toggleInspectMode(inspectMode);
    }

    private void clearButtonActionPerformed(ActionEvent evt) {
        log("Clear button clicked");
        SwingUtilities.invokeLater(() -> {
            try {
                outputArea.setText("");
                StyledDocument doc = outputArea.getStyledDocument();
                SimpleAttributeSet attrs = new SimpleAttributeSet();
                String fontFamily = getAvailableFont();
                StyleConstants.setFontFamily(attrs, fontFamily);
                StyleConstants.setFontSize(attrs, 14);
                doc.setCharacterAttributes(0, doc.getLength(), attrs, true);
                outputArea.setBackground(new Color(0, 0, 0));
                outputArea.setForeground(new Color(255, 255, 255));
                log("Output area cleared");
            } catch (Exception e) {
                log("Error clearing outputArea: " + e.getMessage());
                appendToOutputArea("Error clearing outputArea: " + e.getMessage() + "\n");
            }
        });
    }

    private void urlFieldActionPerformed(ActionEvent evt) {
        // Placeholder for potential future functionality
    }

    private void loadButtonMouseEntered(MouseEvent evt) {
        loadButton.setBackground(new Color(250, 128, 114));
        loadButton.setForeground(new Color(0, 0, 0));
    }

    private void loadButtonMouseExited(MouseEvent evt) {
        loadButton.setBackground(new Color(0, 0, 0));
        loadButton.setForeground(new Color(255, 255, 255));
    }

    private void inspectButtonMouseEntered(MouseEvent evt) {
        inspectButton.setBackground(new Color(250, 128, 114));
        inspectButton.setForeground(new Color(0, 0, 0));
    }

    private void inspectButtonMouseExited(MouseEvent evt) {
        inspectButton.setBackground(inspectMode ? new Color(34, 139, 34) : new Color(0, 0, 0));
        inspectButton.setForeground(new Color(255, 255, 255));
    }

    private void clearButtonMouseEntered(MouseEvent evt) {
        clearButton.setBackground(new Color(250, 128, 114));
        clearButton.setForeground(new Color(0, 0, 0));
    }

    private void clearButtonMouseExited(MouseEvent evt) {
        clearButton.setBackground(new Color(0, 0, 0));
        clearButton.setForeground(new Color(255, 255, 255));
    }

    private void formWindowOpened(WindowEvent evt) {
        Image titleIcon = Toolkit.getDefaultToolkit().getImage(constants.userDir + "/icons/bolt.jpg");
        this.setIconImage(titleIcon);
    }

    private void loadUrl(String url) {
        CompletableFuture.runAsync(() -> {
            try {
                driver.get(url);
                Thread.sleep(2000); // Allow page to load
                SwingUtilities.invokeLater(() -> {
                    outputArea.setOpaque(true);
                    outputArea.setBackground(new Color(0, 0, 0));
                    outputArea.setForeground(new Color(255, 255, 255));
                    scrollPane.setBackground(new Color(0, 0, 0));
                    scrollPane.setForeground(new Color(255, 255, 255));
                    scrollPane.getViewport().setBackground(new Color(0, 0, 0));
                    StyledDocument doc = outputArea.getStyledDocument();
                    SimpleAttributeSet attrs = new SimpleAttributeSet();
                    String fontFamily = getAvailableFont();
                    StyleConstants.setFontFamily(attrs, fontFamily);
                    StyleConstants.setFontSize(attrs, 14);
                    doc.setCharacterAttributes(0, doc.getLength(), attrs, true);
                    appendToOutputArea("Loaded URL: " + url + "\n");
                    resetButtons();
                });
                log("Loaded URL: " + url);
                if (inspectMode) {
                    inspectMode = false;
                    inspectButton.setBackground(new Color(0, 0, 0));
                    inspectButton.setForeground(new Color(255, 255, 255));
                    SwingUtilities.invokeLater(() -> {
                        appendToOutputArea("Inspect Mode: Disabled\n");
                        resetButtons();
                    });
                    toggleInspectMode(false);
                }
            } catch (WebDriverException ex) {
                SwingUtilities.invokeLater(() -> {
                    appendToOutputArea("Error loading URL: " + ex.getMessage() + "\n");
                    resetButtons();
                });
                log("Error loading URL: " + ex.getMessage());
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                log("Interrupted while loading URL: " + ex.getMessage());
                Thread.currentThread().interrupt();
            }
        });
    }

    private void resetButtons() {
        SwingUtilities.invokeLater(() -> {
            loadButton.setText("Load URL");
            loadButton.setEnabled(true);
            inspectButton.setEnabled(true);
            clearButton.setEnabled(true);
        });
    }

    private void appendToOutputArea(String text) {
        SwingUtilities.invokeLater(() -> {
            try {
                StyledDocument doc = outputArea.getStyledDocument();
                SimpleAttributeSet attrs = new SimpleAttributeSet();
                String fontFamily = getAvailableFont();
                StyleConstants.setFontFamily(attrs, fontFamily);
                StyleConstants.setFontSize(attrs, 14);
                log("Appending text: " + text);
                doc.insertString(doc.getLength(), text, attrs);
                outputArea.setCaretPosition(doc.getLength());
                outputArea.setBackground(new Color(0, 0, 0));
                outputArea.setForeground(new Color(255, 255, 255));
            } catch (BadLocationException e) {
                log("Error appending to outputArea: " + e.getMessage());
            }
        });
    }

    private void toggleInspectMode(boolean enable) {
        log("Toggling Inspect Mode: " + enable);
        capturing = enable;
        if (!enable) {
            cleanupJavaScriptListeners();
            return;
        }

        CompletableFuture.runAsync(() -> {
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                driver.switchTo().defaultContent();
                Object initResult = js.executeScript(getJavaScriptForInspection());
                log("initResult: " + (initResult != null ? initResult.toString() : "null"));
                if (initResult == null || initResult.toString().contains("Error:")) {
                    String errorMsg = initResult != null ? initResult.toString() : "Initialization result is null";
                    appendToOutputArea("Failed to initialize listeners: " + errorMsg + "\n");
                    return;
                }

                if (initResult instanceof HashMap) {
                    HashMap<String, Object> resultMap = (HashMap<String, Object>) initResult;
                    String status = (String) resultMap.get("status");
                    log("Initialization status: " + status);
                    if (!"Listeners setup initiated".equals(status)) {
                        appendToOutputArea("Failed to initialize listeners: " + status + "\n");
                        return;
                    }
                }

                Thread.sleep(1000);
                Object listenerStatus = js.executeScript("return window.checkListeners ? window.checkListeners() : false;");
                log("Listener status after setup: " + listenerStatus);
                if (listenerStatus instanceof Boolean && !(Boolean) listenerStatus) {
                    appendToOutputArea("Error: Listeners not attached properly\n");
                    return;
                }

                while (capturing && driver != null) {
                    try {
                        Object result = js.executeScript(
                            "try {" +
                            "  console.log('Polling elementDetails...');" +
                            "  var result = window.elementDetails;" +
                            "  if (result) {" +
                            "    window.elementDetails = null;" +
                            "    return { details: JSON.stringify(result) };" +
                            "  }" +
                            "  return { details: null };" +
                            "} catch (err) {" +
                            "  console.error('Poll error: ' + err.message);" +
                            "  return { details: 'Error: ' + err.message };" +
                            "}"
                        );
                        String details = null;
                        if (result instanceof HashMap) {
                            HashMap<String, Object> resultMap = (HashMap<String, Object>) result;
                            details = (String) resultMap.get("details");
                        }
                        final String finalDetails = details;
                        if (finalDetails != null && !finalDetails.isEmpty() && !finalDetails.startsWith("Error:") && !finalDetails.equals("null")) {
                            log("Raw JSON details: " + finalDetails);
                            String parsedDetails = extractor.extractElementDetails(finalDetails);
                            log("Parsed details: " + parsedDetails);
                            if (parsedDetails != null && !parsedDetails.trim().isEmpty() && !parsedDetails.contains("No valid element details parsed")) {
                                String elementId = "Element_" + System.currentTimeMillis();
                                extractor.storeInRepository(elementId, parsedDetails);
                                appendElementDetails(elementId, parsedDetails, finalDetails);
                            } else {
                                log("No valid details parsed: " + parsedDetails);
                                appendToOutputArea("Element Details: No valid element details parsed: " + parsedDetails + "\n");
                            }
                        } else if (finalDetails != null && finalDetails.startsWith("Error:")) {
                            log("JavaScript error: " + finalDetails);
                            appendToOutputArea("JavaScript error: " + finalDetails + "\n");
                        }
                        Thread.sleep(POLLING_INTERVAL_MS);
                    } catch (WebDriverException ex) {
                        log("Capture error: " + ex.getMessage());
                        appendToOutputArea("Capture error: " + ex.getMessage() + "\n");
                        break;
                    } catch (InterruptedException ex) {
                        log("Polling interrupted: " + ex.getMessage());
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            } catch (WebDriverException ex) {
                log("Error initializing listeners: " + ex.getMessage());
                appendToOutputArea("Error initializing listeners: " + ex.getMessage() + "\n");
            } catch (InterruptedException ex) {
                log("Interrupted during listener setup: " + ex.getMessage());
                Thread.currentThread().interrupt();
            }
        });
    }

    private String getJavaScriptForInspection() {
        return "try {" +
               "  console.log('Initializing listeners...');" +
               "  window.elementDetails = null;" +
               "  window.escapeXPathString = function(text) {" +
               "    try {" +
               "      if (!text) return '';" +
               "      return text.replace(/\"/g, '\\\\\"');" +
               "    } catch (err) {" +
               "      console.error('escapeXPathString error: ' + err.message);" +
               "      return '';" +
               "    }" +
               "  };" +
               "  window.hasSpacesOrNewlines = function(value) {" +
               "    try {" +
               "      if (!value) return false;" +
               "      return /^\\s|\\s$|[\\n\\r]/.test(value);" +
               "    } catch (err) {" +
               "      console.error('hasSpacesOrNewlines error: ' + err.message);" +
               "      return false;" +
               "    }" +
               "  };" +
               "  window.normalizeText = function(text) {" +
               "    try {" +
               "      if (!text) return '';" +
               "      return text.replace(/[\\n\\r]+/g, ' ').replace(/\\s+/g, ' ').trim();" +
               "    } catch (err) {" +
               "      console.error('normalizeText error: ' + err.message);" +
               "      return '';" +
               "    }" +
               "  };" +
               "  window.getAttributeLengthExcludingSpaces = function(value) {" +
               "    try {" +
               "      if (!value) return 0;" +
               "      return value.replace(/\\s/g, '').length;" +
               "    } catch (err) {" +
               "      console.error('getAttributeLengthExcludingSpaces error: ' + err.message);" +
               "      return 0;" +
               "    }" +
               "  };" +
               "  window.getValueUpToLastSpace = function(value, maxLength) {" +
               "    try {" +
               "      if (!value) return '';" +
               "      if (value.length <= maxLength) return value.trim();" +
               "      var truncated = value.substring(0, maxLength);" +
               "      var lastSpaceIndex = truncated.lastIndexOf(' ');" +
               "      if (lastSpaceIndex === -1) return truncated.trim();" +
               "      return truncated.substring(0, lastSpaceIndex).trim();" +
               "    } catch (err) {" +
               "      console.error('getValueUpToLastSpace error: ' + err.message);" +
               "      return '';" +
               "    }" +
               "  };" +
               "  window.getValueUpToFirstSpace = function(value) {" +
               "    try {" +
               "      if (!value) return '';" +
               "      var spaceIndex = value.indexOf(' ');" +
               "      if (spaceIndex === -1) return value.trim();" +
               "      return value.substring(0, spaceIndex).trim();" +
               "    } catch (err) {" +
               "      console.error('getValueUpToFirstSpace error: ' + err.message);" +
               "      return '';" +
               "    }" +
               "  };" +
               "  window.getCssSelector = function(element) {" +
               "    try {" +
               "      if (!element || !element.tagName) return '';" +
               "      var path = [];" +
               "      var current = element;" +
               "      while (current && current.nodeType === 1) {" +
               "        var selector = current.tagName.toLowerCase();" +
               "        var siblings = current.parentNode ? Array.from(current.parentNode.children).filter(function(child) {" +
               "          return child.tagName === current.tagName;" +
               "        }) : [];" +
               "        if (siblings.length > 1) {" +
               "          var index = siblings.indexOf(current) + 1;" +
               "          selector += ':nth-of-type(' + index + ')';" +
               "        }" +
               "        path.unshift(selector);" +
               "        current = current.parentNode;" +
               "        if (path.length > 3) break;" +
               "      }" +
               "      return path.join(' > ') || element.tagName.toLowerCase();" +
               "    } catch (err) {" +
               "      console.error('getCssSelector error: ' + err.message);" +
               "      return '';" +
               "    }" +
               "  };" +
               "  window.getAbsoluteXPath = function(element) {" +
               "    try {" +
               "      if (!element || !element.tagName) return '';" +
               "      var paths = [];" +
               "      var current = element;" +
               "      while (current && current.nodeType === 1 && current !== document.documentElement) {" +
               "        var index = 0;" +
               "        var siblings = current.parentNode ? Array.from(current.parentNode.childNodes).filter(function(node) {" +
               "          return node.nodeType === 1 && node.tagName === current.tagName;" +
               "        }) : [];" +
               "        for (var i = 0; i < siblings.length; i++) {" +
               "          if (siblings[i] === current) {" +
               "            index = i + 1;" +
               "            break;" +
               "          }" +
               "        }" +
               "        var tag = current.tagName.toLowerCase();" +
               "        var isSvg = current.namespaceURI === 'http://www.w3.org/2000/svg';" +
               "        var segment = isSvg ? '*[local-name()=\"' + tag + '\"]': tag;" +
               "        segment = index > 0 ? segment + '[' + index + ']' : segment;" +
               "        paths.unshift(segment);" +
               "        current = current.parentNode;" +
               "      }" +
               "      return paths.length ? '/html' + (paths.length ? '/' + paths.join('/') : '') : '';" +
               "    } catch (err) {" +
               "      console.error('getAbsoluteXPath error: ' + err.message);" +
               "      return '';" +
               "    }" +
               "  };" +
               "  window.findLastTextDescendant = function(element) {" +
               "    try {" +
               "      for (var i = element.children.length - 1; i >= 0; i--) {" +
               "        var childLast = window.findLastTextDescendant(element.children[i]);" +
               "        if (childLast) {" +
               "          return childLast;" +
               "        }" +
               "      }" +
               "      var directText = Array.from(element.childNodes).filter(n => n.nodeType === 3).map(n => n.nodeValue.trim()).join(' ').trim();" +
               "      if (directText.length > 0) {" +
               "        return {el: element, text: window.normalizeText(directText)};" +
               "      }" +
               "      return null;" +
               "    } catch (err) {" +
               "      console.error('findLastTextDescendant error: ' + err.message);" +
               "      return null;" +
               "    }" +
               "  };" +
               "  window.getRelativeXPath = function(element) {" +
               "    try {" +
               "      if (!element || !element.tagName) return '';" +
               "      var tag = element.tagName.toLowerCase();" +
               "      var isSvg = element.namespaceURI === 'http://www.w3.org/2000/svg';" +
               "      if (isSvg) {" +
               "        var predicates = [];" +
               "        predicates.push('local-name()=\"' + tag + '\"');" +
               "        var attrsToCheck = ['id', 'name', 'value', 'data-icon', 'aria-label', 'role', 'class', 'd', 'transform', 'x', 'y', 'width', 'height', 'viewBox', 'href', 'style'];" +
               "        var longAttr = null;" +
               "        var longAttrName = null;" +
               "        for (var i = 0; i < attrsToCheck.length; i++) {" +
               "          var attr = attrsToCheck[i];" +
               "          var attrValue = element.getAttribute(attr);" +
               "          if (attrValue && attrValue.trim()) {" +
               "            var trimmedValue = attrValue.trim();" +
               "            var noSpacesLength = window.getAttributeLengthExcludingSpaces(trimmedValue);" +
               "            if (noSpacesLength > 100) {" +
               "              longAttr = window.getValueUpToLastSpace(trimmedValue, 100);" +
               "              longAttrName = attr;" +
               "              break;" +
               "            }" +
               "          }" +
               "        }" +
               "        if (longAttrName && longAttr) {" +
               "          var escapedValue = window.escapeXPathString(longAttr);" +
               "          predicates.push('contains(@*[local-name()=\"' + longAttrName + '\"], \"' + escapedValue + '\")');" +
               "        } else {" +
               "          for (var i = 0; i < attrsToCheck.length; i++) {" +
               "            var attr = attrsToCheck[i];" +
               "            var attrValue = element.getAttribute(attr);" +
               "            if (attrValue && attrValue.trim()) {" +
               "              var trimmedValue = attrValue.trim();" +
               "              var escapedValue = window.escapeXPathString(trimmedValue);" +
               "              predicates.push('contains(@*[local-name()=\"' + attr + '\"], \"' + escapedValue + '\")');" +
               "              break;" +
               "            }" +
               "          }" +
               "        }" +
               "        if (element.getAttributeNS && element.getAttributeNS('http://www.w3.org/1999/xlink', 'href') && element.getAttributeNS('http://www.w3.org/1999/xlink', 'href').trim()) {" +
               "          var hrefValue = element.getAttributeNS('http://www.w3.org/1999/xlink', 'href').trim();" +
               "          var noSpacesLength = window.getAttributeLengthExcludingSpaces(hrefValue);" +
               "          var escapedHref = window.escapeXPathString(noSpacesLength > 100 ? window.getValueUpToLastSpace(hrefValue, 100) : hrefValue);" +
               "          predicates.push('contains(@*[local-name()=\"href\" and namespace-uri()=\"http://www.w3.org/1999/xlink\"], \"' + escapedHref + '\")');" +
               "        }" +
               "        var text = '';" +
               "        var textElement = null;" +
               "        var directText = '';" +
               "        for (var i = 0; i < element.childNodes.length; i++) {" +
               "          var child = element.childNodes[i];" +
               "          if (child.nodeType === 3) {" +
               "            directText += child.nodeValue;" +
               "          } else if (child.nodeType === 1) {" +
               "            var childText = window.normalizeText(child.textContent).trim();" +
               "            if (childText.length > 0) {" +
               "              if (child.tagName.toLowerCase() === 'span') {" +
               "                text = childText;" +
               "                textElement = child;" +
               "                break;" +
               "              } else if (!textElement) {" +
               "                text = childText;" +
               "                textElement = child;" +
               "              }" +
               "            }" +
               "          }" +
               "        }" +
               "        var normalizedDirectText = window.normalizeText(directText).trim();" +
               "        if (normalizedDirectText.length > 0 && !textElement) {" +
               "          text = normalizedDirectText;" +
               "        }" +
               "        var xpath = '//*';" +
               "        if (predicates.length > 0) {" +
               "          xpath += '[' + predicates.join(' and ') + ']';" +
               "        } else {" +
               "          xpath += '[local-name()=\"' + tag + '\"]';" +
               "        }" +
               "        if (text.length > 0 && textElement) {" +
               "          var childTag = textElement.tagName.toLowerCase();" +
               "          var isChildSvg = textElement.namespaceURI === 'http://www.w3.org/2000/svg';" +
               "          var childXPath = (isChildSvg ? '*' : childTag);" +
               "          var childPredicates = [];" +
               "          if (isChildSvg) {" +
               "            childPredicates.push('local-name()=\"' + childTag + '\"');" +
               "          }" +
               "          childPredicates.push('contains(normalize-space(text()), \"' + window.escapeXPathString(text) + '\")');" +
               "          if (childPredicates.length > 0) {" +
               "            xpath += '/' + childXPath + '[' + childPredicates.join(' and ') + ']';" +
               "          } else {" +
               "            xpath += '/' + childXPath;" +
               "          }" +
               "        } else if (text.length > 0) {" +
               "          predicates.push('contains(normalize-space(text()), \"' + window.escapeXPathString(text) + '\")');" +
               "          xpath = '//*' + '[' + predicates.join(' and ') + ']';" +
               "        }" +
               "        var parent = element.parentNode;" +
               "        if (parent && parent.nodeType === 1) {" +
               "          var parentTag = parent.tagName.toLowerCase();" +
               "          var parentIsSvg = parent.namespaceURI === 'http://www.w3.org/2000/svg';" +
               "          var parentPredicates = [];" +
               "          if (parentIsSvg) {" +
               "            parentPredicates.push('local-name()=\"' + parentTag + '\"');" +
               "          }" +
               "          for (var i = 0; i < attrsToCheck.length; i++) {" +
               "            var attr = attrsToCheck[i];" +
               "            var attrValue = parent.getAttribute(attr);" +
               "            if (attrValue && attrValue.trim()) {" +
               "              var trimmedValue = attrValue.trim();" +
               "              var noSpacesLength = window.getAttributeLengthExcludingSpaces(trimmedValue);" +
               "              var escapedValue = window.escapeXPathString(noSpacesLength > 100 ? window.getValueUpToFirstSpace(trimmedValue) : trimmedValue);" +
               "              if (parentIsSvg) {" +
               "                parentPredicates.push('contains(@*[local-name()=\"' + attr + '\"], \"' + escapedValue + '\")');" +
               "              } else {" +
               "                parentPredicates.push('contains(@' + attr + ', \"' + escapedValue + '\")');" +
               "              }" +
               "              break;" +
               "            }" +
               "          }" +
               "          if (parent.getAttributeNS && parent.getAttributeNS('http://www.w3.org/1999/xlink', 'href') && parent.getAttributeNS('http://www.w3.org/1999/xlink', 'href').trim()) {" +
               "            var parentHrefValue = parent.getAttributeNS('http://www.w3.org/1999/xlink', 'href').trim();" +
               "            var noSpacesLength = window.getAttributeLengthExcludingSpaces(parentHrefValue);" +
               "            var escapedHref = window.escapeXPathString(noSpacesLength > 100 ? window.getValueUpToFirstSpace(parentHrefValue) : parentHrefValue);" +
               "            parentPredicates.push('contains(@*[local-name()=\"href\" and namespace-uri()=\"http://www.w3.org/1999/xlink\"], \"' + escapedHref + '\")');" +
               "          }" +
               "          var parentXPath = '//' + (parentIsSvg ? '*' : parentTag);" +
               "          if (parentPredicates.length > 0) {" +
               "            parentXPath += '[' + parentPredicates.join(' and ') + ']';" +
               "          } else if (parentIsSvg) {" +
               "            parentXPath += '[local-name()=\"' + parentTag + '\"]';" +
               "          }" +
               "          xpath = parentXPath + '/' + xpath.replace(/^\\/\\//, '');" +
               "        }" +
               "        console.log('Generated relativeXPath for SVG: ' + xpath);" +
               "        return xpath;" +
               "      } else {" +
               "        var lastTextDescendant = window.findLastTextDescendant(element);" +
               "        var text = '';" +
               "        var textElement = null;" +
               "        if (lastTextDescendant) {" +
               "          text = lastTextDescendant.text;" +
               "          textElement = lastTextDescendant.el;" +
               "        }" +
               "        var usedText = text.length > 0;" +
               "        var xpaath = '';" +
               "        var predicates = [];" +
               "        if (usedText && !window.hasSpacesOrNewlines(text)) {" +
               "          var childTag = textElement.tagName.toLowerCase();" +
               "          xpath = '//' + childTag;" +
               "          predicates.push('normalize-space(text())=\"' + window.escapeXPathString(text) + '\"');" +
               "          xpath += '[' + predicates.join(' and ') + ']';" +
               "        } else {" +
               "          var foundAttr = false;" +
               "          var attrValue = element.getAttribute('id');" +
               "          if (attrValue && attrValue.trim()) {" +
               "            var trimmedValue = attrValue.trim();" +
               "            var noSpacesLength = window.getAttributeLengthExcludingSpaces(trimmedValue);" +
               "            var valueToUse = noSpacesLength > 100 ? window.getValueUpToFirstSpace(trimmedValue) : trimmedValue;" +
               "            predicates.push('contains(@id, \"' + window.escapeXPathString(valueToUse) + '\")');" +
               "            foundAttr = true;" +
               "          }" +
               "          if (!foundAttr) {" +
               "            var priorAttrs = ['aria-label', 'name', 'value'];" +
               "            for (var i = 0; i < priorAttrs.length; i++) {" +
               "              var attr = priorAttrs[i];" +
               "              attrValue = element.getAttribute(attr);" +
               "              if (attrValue && attrValue.trim()) {" +
               "                trimmedValue = attrValue.trim();" +
               "                noSpacesLength = window.getAttributeLengthExcludingSpaces(trimmedValue);" +
               "                valueToUse = noSpacesLength > 100 ? window.getValueUpToFirstSpace(trimmedValue) : trimmedValue;" +
               "                predicates.push('contains(@' + attr + ', \"' + window.escapeXPathString(valueToUse) + '\")');" +
               "                foundAttr = true;" +
               "                break;" +
               "              }" +
               "            }" +
               "          }" +
               "          if (!foundAttr) {" +
               "            attrValue = element.getAttribute('class');" +
               "            if (attrValue && attrValue.trim()) {" +
               "              trimmedValue = attrValue.trim();" +
               "              noSpacesLength = window.getAttributeLengthExcludingSpaces(trimmedValue);" +
               "              valueToUse = noSpacesLength > 100 ? window.getValueUpToFirstSpace(trimmedValue) : trimmedValue;" +
               "              predicates.push('contains(@class, \"' + window.escapeXPathString(valueToUse) + '\")');" +
               "              foundAttr = true;" +
               "            }" +
               "          }" +
               "          if (!foundAttr) {" +
               "            var allAttrs = element.attributes;" +
               "            for (var i = 0; i < allAttrs.length; i++) {" +
               "              var attr = allAttrs[i].name;" +
               "              if (['id', 'aria-label', 'name', 'value', 'class', 'href', 'style'].includes(attr)) continue;" +
               "              console.log('Checking attribute for non-SVG: ' + attr);" +
               "              var value = allAttrs[i].value.trim();" +
               "              if (value) {" +
               "                var query = '//' + tag + '[@' + attr + '=\"' + window.escapeXPathString(value) + '\"]';" +
               "                var result = document.evaluate(query, document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);" +
               "                if (result.snapshotLength === 1) {" +
               "                  predicates.push('@' + attr + '=\"' + window.escapeXPathString(value) + '\"');" +
               "                  foundAttr = true;" +
               "                  console.log('Using attribute for non-SVG: ' + attr);" +
               "                  break;" +
               "                }" +
               "              }" +
               "            }" +
               "          }" +
               "          if (element.getAttributeNS && element.getAttributeNS('http://www.w3.org/1999/xlink', 'href') && element.getAttributeNS('http://www.w3.org/1999/xlink', 'href').trim()) {" +
               "            console.log('Skipping xlink:href for non-SVG element');" +
               "          }" +
               "          xpath = '//' + tag;" +
               "          if (predicates.length > 0) {" +
               "            xpath += '[' + predicates.join(' and ') + ']';" +
               "          }" +
               "        }" +
               "        var result = document.evaluate(xpath, document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);" +
               "        if (result.snapshotLength > 1) {" +
               "          var allAttrs = element.attributes;" +
               "          for (var i = 0; i < allAttrs.length; i++) {" +
               "            var attr = allAttrs[i].name;" +
               "            if (['id', 'aria-label', 'name', 'value', 'class', 'href', 'style'].includes(attr)) continue;" +
               "            console.log('Checking additional attribute for non-SVG: ' + attr);" +
               "            var value = allAttrs[i].value.trim();" +
               "            if (value) {" +
               "              var testXPath = xpath + '[@' + attr + '=\"' + window.escapeXPathString(value) + '\"]';" +
               "              var testResult = document.evaluate(testXPath, document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);" +
               "              if (testResult.snapshotLength === 1) {" +
               "                xpath = testXPath;" +
               "                console.log('Using additional attribute for non-SVG: ' + attr);" +
               "                break;" +
               "              }" +
               "            }" +
               "          }" +
               "        }" +
               "        if (result.snapshotLength > 1) {" +
               "          var parent = element.parentNode;" +
               "          if (parent && parent.nodeType === 1) {" +
               "            var parentTag = parent.tagName.toLowerCase();" +
               "            var parentPredicates = [];" +
               "            var parentAttrs = parent.attributes;" +
               "            for (var i = 0; i < parentAttrs.length; i++) {" +
               "              var attr = parentAttrs[i].name;" +
               "              if (!isSvg && ['href', 'style'].includes(attr)) {" +
               "                console.log('Skipping parent attribute for non-SVG: ' + attr);" +
               "                continue;" +
               "              }" +
               "              var value = parentAttrs[i].value.trim();" +
               "              if (value) {" +
               "                var noSpacesLength = window.getAttributeLengthExcludingSpaces(value);" +
               "                var valueToUse = noSpacesLength > 100 ? window.getValueUpToFirstSpace(value) : value;" +
               "                parentPredicates.push('contains(@' + attr + ', \"' + window.escapeXPathString(valueToUse) + '\")');" +
               "                var parentXPath = '//' + parentTag + '[' + parentPredicates.join(' and ') + ']';" +
               "                var combinedXPath = parentXPath + xpath;" +
               "                var combinedResult = document.evaluate(combinedXPath, document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);" +
               "                if (combinedResult.snapshotLength === 1) {" +
               "                  xpath = combinedXPath;" +
               "                  break;" +
               "                }" +
               "              }" +
               "            }" +
               "            if (parentPredicates.length === 0) {" +
               "              var parentXPath = '//' + parentTag;" +
               "              xpath = parentXPath + xpath;" +
               "            }" +
               "          }" +
               "        }" +
               "      }" +
               "      console.log('Generated relativeXPath: ' + xpath);" +
               "      return xpath;" +
               "    } catch (err) {" +
               "      console.error('getRelativeXPath error: ' + err.message);" +
               "      return '';" +
               "    }" +
               "  };" +
               "  window.elementCapture = function(e) {" +
               "    try {" +
               "      e.preventDefault();" +
               "      e.stopPropagation();" +
               "      var el = e.target;" +
               "      var normalizedText = el.textContent ? window.normalizeText(el.textContent.substring(0, 50)) : '';" +
               "      var details = {" +
               "        tagName: el.tagName ? el.tagName.toLowerCase() : ''," +
               "        text: normalizedText," +
               "        id: el.id || ''," +
               "        class: el.className || ''," +
               "        cssSelector: window.getCssSelector(el)," +
               "        absoluteXPath: window.getAbsoluteXPath(el)," +
               "        relativeXPath: window.getRelativeXPath(el)" +
               "      };" +
               "      console.log('elementCapture details: ' + JSON.stringify(details));" +
               "      window.elementDetails = details;" +
               "      return JSON.stringify(details);" +
               "    } catch (err) {" +
               "      console.error('elementCapture error: ' + err.message);" +
               "      return 'Error: ' + err.message;" +
               "    }" +
               "  };" +
               "  window.elementHover = function(e) {" +
               "    try {" +
               "      e.target.style.outline = '2px solid red';" +
               "    } catch (err) {" +
               "      console.error('elementHover error: ' + err.message);" +
               "    }" +
               "  };" +
               "  window.elementHoverOut = function(e) {" +
               "    try {" +
               "      e.target.style.outline = '';" +
               "    } catch (err) {" +
               "      console.error('elementHoverOut error: ' + err.message);" +
               "    }" +
               "  };" +
               "  window.checkListeners = function() {" +
               "    try {" +
               "      return !!window.elementCapture;" +
               "    } catch (err) {" +
               "      console.error('checkListeners error: ' + err.message);" +
               "      return false;" +
               "    }" +
               "  };" +
               "  document.removeEventListener('click', window.elementCapture);" +
               "  document.removeEventListener('mouseover', window.elementHover);" +
               "  document.removeEventListener('mouseout', window.elementHoverOut);" +
               "  document.addEventListener('click', window.elementCapture, { capture: true, passive: false });" +
               "  document.addEventListener('mouseover', window.elementHover, { capture: true, passive: true });" +
               "  document.addEventListener('mouseout', window.elementHoverOut, { capture: true, passive: true });" +
               "  setTimeout(function() {" +
               "    window.checkListeners();" +
               "  }, 500);" +
               "  console.log('Listeners setup complete');" +
               "  return { status: 'Listeners setup initiated' };" +
               "} catch (err) {" +
               "  console.error('Initialization error: ' + err.message);" +
               "  return { status: 'Error: ' + err.message };" +
               "}";
    }

    private void cleanupJavaScriptListeners() {
        if (driver != null) {
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript(
                    "if (window.elementCapture) {" +
                    "  document.removeEventListener('click', window.elementCapture, { capture: true });" +
                    "  document.removeEventListener('mouseover', window.elementHover, { capture: true });" +
                    "  document.removeEventListener('mouseout', window.elementHoverOut, { capture: true });" +
                    "  window.elementCapture = null;" +
                    "  window.elementHover = null;" +
                    "  window.elementHoverOut = null;" +
                    "  window.elementDetails = null;" +
                    "  document.body.style.removeProperty('pointer-events');" +
                    "  console.log('Inspect mode disabled, all listeners and styles removed');" +
                    "}"
                );
                log("JavaScript listeners cleared and normal interaction restored");
            } catch (WebDriverException ex) {
                log("Error clearing JavaScript listeners: " + ex.getMessage());
                appendToOutputArea("Error clearing listeners: " + ex.getMessage() + "\n");
            }
        }
    }

    private String getAvailableFont() {
        String[] preferredFonts = {"Mangal", "Noto Sans Devanagari", "Arial Unicode MS", "Segoe UI", "Arial"};
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] availableFonts = ge.getAvailableFontFamilyNames();
        for (String font : preferredFonts) {
            for (String availableFont : availableFonts) {
                if (availableFont.equalsIgnoreCase(font)) {
                    log("Using font: " + font);
                    return font;
                }
            }
        }
        log("Warning: No preferred font found, falling back to default");
        return "SansSerif";
    }

    private void appendElementDetails(String elementId, String parsedDetails, String rawJsonDetails) {
        SwingUtilities.invokeLater(() -> {
            try {
                StyledDocument doc = outputArea.getStyledDocument();
                SimpleAttributeSet attrs = new SimpleAttributeSet();
                String fontFamily = getAvailableFont();
                StyleConstants.setFontFamily(attrs, fontFamily);
                StyleConstants.setFontSize(attrs, 14);

                JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
                separator.setForeground(new Color(255, 153, 153));
                separator.setBackground(new Color(0, 0, 0));
                separator.setPreferredSize(new Dimension(outputArea.getWidth() - 20, 2));
                Style separatorStyle = doc.addStyle("separator" + System.currentTimeMillis(), null);
                StyleConstants.setComponent(separatorStyle, separator);
                doc.insertString(doc.getLength(), "\n", attrs);
                doc.insertString(doc.getLength(), " ", separatorStyle);
                doc.insertString(doc.getLength(), "\nStored in repository as: " + elementId + "\nElement Details:\n", attrs);

                String relativeXPath = "";
                String absoluteXPath = "";
                try {
                    JSONObject json = new JSONObject(rawJsonDetails);
                    relativeXPath = json.optString("relativeXPath", "");
                    absoluteXPath = json.optString("absoluteXPath", "");
                    log("Raw relativeXPath from JSON: " + relativeXPath);
                } catch (Exception e) {
                    log("Error parsing raw JSON: " + e.getMessage());
                    doc.insertString(doc.getLength(), "Error parsing JSON: " + e.getMessage() + "\n", attrs);
                }

                String[] lines = parsedDetails.split("\n");
                for (String line : lines) {
                    if (line.startsWith("relativeXPath:") && !relativeXPath.isEmpty()) {
                        CustomButton copyButton = new CustomButton("Copy", 8, 1, new Color(250, 128, 114), new Color(250, 128, 114));
                        copyButton.setFont(new Font("Arial", Font.PLAIN, 10));
                        copyButton.setMargin(new Insets(2, 3, 2, 3));
                        copyButton.setPreferredSize(new Dimension(50, 20));
                        copyButton.setForeground(Color.WHITE);
                        String finalXPath = relativeXPath;
                        copyButton.addActionListener(e -> {
                            StringSelection selection = new StringSelection(finalXPath);
                            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                            clipboard.setContents(selection, null);
                            appendToOutputArea("Copied to clipboard: " + finalXPath + "\n");
                        });
                        CustomButton addButton = new CustomButton("Add", 8, 1, new Color(250, 128, 114), new Color(250, 128, 114));
                        addButton.setFont(new Font("Arial", Font.PLAIN, 10));
                        addButton.setMargin(new Insets(2, 3, 2, 3));
                        addButton.setPreferredSize(new Dimension(50, 20));
                        addButton.setForeground(Color.WHITE);
                        addButton.addActionListener(e -> {
                            appendToOutputArea("Element added to the repository\n");
                            CreateTestSuite.addWebElementToRepository(elementId, finalXPath);
                        });
                        Style buttonStyle = doc.addStyle("button" + System.currentTimeMillis(), null);
                        StyleConstants.setComponent(buttonStyle, copyButton);
                        doc.insertString(doc.getLength(), " ", buttonStyle);
                        doc.insertString(doc.getLength(), " ", attrs);
                        Style addButtonStyle = doc.addStyle("addButton" + System.currentTimeMillis(), null);
                        StyleConstants.setComponent(addButtonStyle, addButton);
                        doc.insertString(doc.getLength(), " ", addButtonStyle);
                        doc.insertString(doc.getLength(), " relativeXPath: " + relativeXPath + "\n", attrs);
                    } else if (line.startsWith("absoluteXPath:") && !absoluteXPath.isEmpty()) {
                        CustomButton copyButton = new CustomButton("Copy", 8, 1, new Color(250, 128, 114), new Color(250, 128, 114));
                        copyButton.setFont(new Font("Arial", Font.PLAIN, 10));
                        copyButton.setMargin(new Insets(2, 3, 2, 3));
                        copyButton.setPreferredSize(new Dimension(50, 20));
                        copyButton.setForeground(Color.WHITE);
                        String finalXPath = absoluteXPath;
                        copyButton.addActionListener(e -> {
                            StringSelection selection = new StringSelection(finalXPath);
                            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                            clipboard.setContents(selection, null);
                            appendToOutputArea("Copied to clipboard: " + finalXPath + "\n");
                        });
                        CustomButton addButton = new CustomButton("Add", 8, 1, new Color(250, 128, 114), new Color(250, 128, 114));
                        addButton.setFont(new Font("Arial", Font.PLAIN, 10));
                        addButton.setMargin(new Insets(2, 3, 2, 3));
                        addButton.setPreferredSize(new Dimension(50, 20));
                        addButton.setForeground(Color.WHITE);
                        addButton.addActionListener(e -> {
                            appendToOutputArea("Element added to the repository\n");
                            CreateTestSuite.addWebElementToRepository(elementId, finalXPath);
                        });
                        Style buttonStyle = doc.addStyle("button" + System.currentTimeMillis(), null);
                        StyleConstants.setComponent(buttonStyle, copyButton);
                        doc.insertString(doc.getLength(), " ", buttonStyle);
                        doc.insertString(doc.getLength(), " ", attrs);
                        Style addButtonStyle = doc.addStyle("addButton" + System.currentTimeMillis(), null);
                        StyleConstants.setComponent(addButtonStyle, addButton);
                        doc.insertString(doc.getLength(), " ", addButtonStyle);
                        doc.insertString(doc.getLength(), " absoluteXPath: " + absoluteXPath + "\n", attrs);
                    } else {
                        doc.insertString(doc.getLength(), line + "\n", attrs);
                    }
                }
                outputArea.setCaretPosition(doc.getLength());
                outputArea.setBackground(new Color(0, 0, 0));
                outputArea.setForeground(new Color(255, 255, 255));
            } catch (BadLocationException e) {
                log("Error appending element details: " + e.getMessage());
            }
        });
    }

    public static class CustomButton extends JButton {
        protected Color defaultColor = Color.BLACK;
        protected Color hoverColor = new Color(250, 128, 114);
        protected Color disabledColor = new Color(50, 50, 50);
        protected Color borderColor = null;
        protected Color borderHoverColor = null;
        protected boolean isHovered = false;
        protected boolean isDisabled = false;
        private int arc = 8; // Restored to 8 for smoother corners
        private int shadowOffset = 1; // Restored to 1 for subtle shadow

        public CustomButton(String text) {
            this(text, 8, 1, null, null);
        }

        public CustomButton(String text, int arc, int shadowOffset) {
            this(text, arc, shadowOffset, null, null);
        }

        public CustomButton(String text, int arc, int shadowOffset, Color borderColor, Color borderHoverColor) {
            super(text);
            this.arc = arc;
            this.shadowOffset = shadowOffset;
            this.borderColor = borderColor;
            this.borderHoverColor = borderHoverColor;
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setForeground(Color.WHITE);
            setFont(new Font("Arial", Font.PLAIN, 10)); // Font size unchanged
            setMargin(new Insets(2, 3, 2, 3)); // Adjusted margins for better spacing
            setPreferredSize(new Dimension(50, 20)); // Slightly larger size for better appearance
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isHovered = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    isHovered = false;
                    repaint();
                }
            });
        }

        public void setDisabled(boolean disabled) {
            this.isDisabled = disabled;
            repaint();
        }

        public void setBackgroundColor(Color color) {
            this.defaultColor = color;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(0, 0, 0, 50));
            g2.fill(new RoundRectangle2D.Float(shadowOffset, shadowOffset, getWidth() - shadowOffset, getHeight() - shadowOffset, arc, arc));
            if (isDisabled || !isEnabled()) {
                g2.setColor(isHovered ? disabledColor.brighter() : disabledColor);
            } else {
                g2.setColor(isHovered ? hoverColor : defaultColor);
            }
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - shadowOffset, getHeight() - shadowOffset, arc, arc));
            if (borderColor != null) {
                g2.setColor(isHovered && borderHoverColor != null ? borderHoverColor : borderColor);
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - shadowOffset, getHeight() - shadowOffset, arc, arc));
            }
            super.paintComponent(g);
            g2.dispose();
        }
    }

    private boolean isBrowserOpen() {
        if (driver == null) {
            return false;
        }
        try {
            driver.getWindowHandle();
            return true;
        } catch (NoSuchSessionException e) {
            log("Browser session is invalid: " + e.getMessage());
            return false;
        } catch (WebDriverException e) {
            log("Browser window is closed or unavailable: " + e.getMessage());
            return false;
        }
    }

    private void startBrowserPolling() {
        CompletableFuture.runAsync(() -> {
            while (polling.get() && driver != null) {
                try {
                    if (!isBrowserOpen()) {
                        log("Browser window closed, closing JFrame");
                        SwingUtilities.invokeLater(() -> {
                            appendToOutputArea("Browser window closed, shutting down application\n");
                            cleanup();
                            dispose();
                        });
                        break;
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log("Browser polling interrupted: " + e.getMessage());
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
    }

    private void cleanup() {
        polling.set(false);
        cleanupJavaScriptListeners();
        if (driver != null) {
            try {
                driver.quit();
                log("WebDriver closed successfully");
                appendToOutputArea("WebDriver closed\n");
            } catch (WebDriverException e) {
                log("Error closing WebDriver: " + e.getMessage());
                appendToOutputArea("Error closing WebDriver: " + e.getMessage() + "\n");
            }
            driver = null;
        }
    }

    @Override
    public void dispose() {
        cleanup();
        super.dispose();
    }

    private void log(String message) {
        System.out.println("[" + LOG_TIMESTAMP_FORMAT.format(new Date()) + "] " + message);
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        /*try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(WebElementInspector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }*/
        java.awt.EventQueue.invokeLater(() -> {
            new WebElementInspector().setVisible(true);
        });
    }

    // Variables declaration
    public JButton clearButton;
    public JButton inspectButton;
    public JButton loadButton;
    public JTextPane outputArea;
    public JScrollPane scrollPane;
    public JTextField urlField;
    public JLabel urlLabel;
    // End of variables declaration
}