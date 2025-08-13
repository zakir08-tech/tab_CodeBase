/*
 * WebElementInspector.java
 * A Swing-based GUI for inspecting web elements using Selenium WebDriver.
 */
package com.automation.bolt.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import java.awt.geom.RoundRectangle2D;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * A Swing-based GUI application for inspecting web elements using Selenium WebDriver.
 * Allows loading a URL, enabling inspect mode to capture element details, and displaying them in a text pane.
 */
public class WebElementInspector extends javax.swing.JFrame {
    private WebElementDetailsExtractor extractor = null;
    private WebDriver driver;
    private volatile boolean inspectMode = false;
    private volatile boolean capturing = false;
    private final AtomicBoolean polling = new AtomicBoolean(true);
    private final Object lock = new Object(); // For synchronizing inspectMode and capturing

    /**
     * Creates a new WebElementInspector form and initializes components.
     */
    public WebElementInspector() {
        // Initialize extractor (TODO: Ensure WebElementDetailsExtractorImpl is properly defined)
        try {
            extractor = new WebElementDetailsExtractorImpl();
        } catch (Exception e) {
            System.err.println("Failed to initialize WebElementDetailsExtractor: " + e.getMessage());
            e.printStackTrace();
        }
        initComponents();
        startBrowserPolling(); // Start polling in constructor
    }

    /**
     * Initializes the Swing components for the GUI.
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
        setBounds(new Rectangle(0, 0, 973, 500));
        setMinimumSize(new Dimension(851, 328));

        urlField.setBackground(new Color(51, 51, 51));
        urlField.setFont(new Font("Segoe UI", 0, 14));
        urlField.setForeground(new Color(255, 255, 255));
        urlField.setHorizontalAlignment(JTextField.LEFT);
        urlField.setText("https://google.com");
        urlField.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255)));
        urlField.setCaretColor(new Color(255, 255, 255));
        urlField.setOpaque(true);
        urlField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                urlFieldActionPerformed(evt);
            }
        });

        urlLabel.setText("URL");

        scrollPane.setBackground(new Color(0, 0, 0));
        scrollPane.setForeground(new Color(255, 255, 255));
        scrollPane.getViewport().setBackground(new Color(0, 0, 0));

        outputArea.setEditable(false);
        outputArea.setBackground(new Color(0, 0, 0));
        outputArea.setForeground(new Color(255, 255, 255));
        outputArea.setFont(new Font("Segoe UI", 0, 14));
        outputArea.setOpaque(true);
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

        // Horizontal layout
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(urlLabel)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(urlField, GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(loadButton)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(inspectButton)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(clearButton)
                    .addContainerGap())
                .addComponent(scrollPane)
        );

        // Vertical layout
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(urlLabel)
                        .addComponent(urlField, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                        .addComponent(loadButton)
                        .addComponent(inspectButton)
                        .addComponent(clearButton))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
                    .addContainerGap())
        );

        getAccessibleContext().setAccessibleParent(this);
        setSize(new Dimension(865, 505));
        setLocationRelativeTo(null);
    }

    /**
     * Handles the Load URL button click to initialize WebDriver and load the specified URL.
     */
    private void loadButtonActionPerformed(ActionEvent evt) {
        System.out.println("Load URL button clicked: " + urlField.getText());

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
                    System.out.println("WebDriver initialized successfully");
                    loadUrl(urlField.getText());
                } catch (Exception ex) {
                    SwingUtilities.invokeLater(() -> {
                        appendToOutputArea("Error initializing WebDriver: " + ex.getMessage() + "\n");
                        loadButton.setText("Load URL");
                        loadButton.setEnabled(true);
                        inspectButton.setEnabled(true);
                        clearButton.setEnabled(true);
                    });
                    System.out.println("Error initializing WebDriver: " + ex.getMessage());
                    ex.printStackTrace();
                }
            });
        } else {
            loadUrl(urlField.getText());
        }
    }

    /**
     * Toggles inspect mode to capture web element details.
     */
    private void inspectButtonActionPerformed(ActionEvent evt) {
        System.out.println("Toggle Inspect button clicked");
        synchronized (lock) {
            inspectMode = !inspectMode;
            capturing = inspectMode;
        }
        inspectButton.setBackground(inspectMode ? new Color(34, 139, 34) : new Color(0, 0, 0));
        inspectButton.setForeground(new Color(255, 255, 255));
        appendToOutputArea((inspectMode ? "Inspect Mode: Click an element in the browser (ensure browser window is focused)" : "\nInspect Mode: Disabled") + "\n");
        System.out.println("Inspect Mode Toggled: " + inspectMode);
        toggleInspectMode(inspectMode);
    }

    /**
     * Clears the output area and resets its styling, ensuring the entire background is black.
     */
    private void clearButtonActionPerformed(ActionEvent evt) {
        System.out.println("Clear button clicked");
        SwingUtilities.invokeLater(() -> {
            try {
                outputArea.setText("");
                outputArea.setBackground(new Color(0, 0, 0)); // Ensure entire output area background is black
                outputArea.setOpaque(true);
                scrollPane.setBackground(new Color(0, 0, 0));
                scrollPane.getViewport().setBackground(new Color(0, 0, 0));
                StyledDocument doc = outputArea.getStyledDocument();
                SimpleAttributeSet attrs = new SimpleAttributeSet();
                String devanagariFont = getAvailableDevanagariFont();
                StyleConstants.setFontFamily(attrs, devanagariFont);
                StyleConstants.setFontSize(attrs, 14);
                StyleConstants.setForeground(attrs, new Color(255, 255, 255));
                StyleConstants.setBackground(attrs, new Color(0, 0, 0)); // Ensure text background is black
                doc.setCharacterAttributes(0, doc.getLength(), attrs, true);
                System.out.println("Output area cleared with black background");
            } catch (Exception e) {
                System.out.println("Error clearing outputArea: " + e.getMessage());
                appendToOutputArea("Error clearing outputArea: " + e.getMessage() + "\n");
            }
        });
    }

    private void urlFieldActionPerformed(ActionEvent evt) {
        // Placeholder for future implementation
    }

    private void loadButtonMouseEntered(MouseEvent evt) {
        loadButton.setBackground(new Color(250, 128, 114));
        loadButton.setForeground(new Color(0, 0, 0));
    }

    private void loadButtonMouseExited(MouseEvent evt) {
        loadButton.setBackground(new Color(0, 0, 0));
        loadButton.setForeground(new Color(255, 255, 255));
    }

    private void clearButtonMouseEntered(MouseEvent evt) {
        clearButton.setBackground(new Color(250, 128, 114));
        clearButton.setForeground(new Color(0, 0, 0));
    }

    private void clearButtonMouseExited(MouseEvent evt) {
        clearButton.setBackground(new Color(0, 0, 0));
        clearButton.setForeground(new Color(255, 255, 255));
    }

    private void inspectButtonMouseEntered(MouseEvent evt) {
        inspectButton.setBackground(new Color(250, 128, 114));
        inspectButton.setForeground(new Color(0, 0, 0));
    }

    private void inspectButtonMouseExited(MouseEvent evt) {
        inspectButton.setBackground(inspectMode ? new Color(34, 139, 34) : new Color(0, 0, 0));
        inspectButton.setForeground(new Color(255, 255, 255));
    }

    /**
     * Loads the specified URL in the browser and ensures the entire output area background is black.
     * @param url The URL to load.
     */
    private void loadUrl(String url) {
        CompletableFuture.runAsync(() -> {
            try {
                driver.get(url);
                Thread.sleep(2000);
                SwingUtilities.invokeLater(() -> {
                    outputArea.setOpaque(true);
                    outputArea.setBackground(new Color(0, 0, 0)); // Set entire output area background to black
                    outputArea.setForeground(new Color(255, 255, 255));
                    scrollPane.setOpaque(true);
                    scrollPane.setBackground(new Color(0, 0, 0)); // Set scroll pane background to black
                    scrollPane.getViewport().setOpaque(true);
                    scrollPane.getViewport().setBackground(new Color(0, 0, 0)); // Set viewport background to black
                    StyledDocument doc = outputArea.getStyledDocument();
                    SimpleAttributeSet attrs = new SimpleAttributeSet();
                    String devanagariFont = getAvailableDevanagariFont();
                    StyleConstants.setFontFamily(attrs, devanagariFont);
                    StyleConstants.setFontSize(attrs, 14);
                    StyleConstants.setForeground(attrs, new Color(255, 255, 255));
                    StyleConstants.setBackground(attrs, new Color(0, 0, 0)); // Ensure text background is black
                    doc.setCharacterAttributes(0, doc.getLength(), attrs, true);
                    appendToOutputArea("Loaded URL: " + url + "\n");
                    loadButton.setText("Load URL");
                    loadButton.setEnabled(true);
                    inspectButton.setEnabled(true);
                    clearButton.setEnabled(true);
                });
                System.out.println("Loaded URL: " + url);
                synchronized (lock) {
                    if (inspectMode) {
                        inspectMode = false;
                        capturing = false;
                        inspectButton.setBackground(new Color(0, 0, 0));
                        inspectButton.setForeground(new Color(255, 255, 255));
                        SwingUtilities.invokeLater(() -> {
                            appendToOutputArea("\nInspect Mode: Disabled\n");
                        });
                        toggleInspectMode(false);
                    }
                }
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    appendToOutputArea("Error loading URL: " + ex.getMessage() + "\n");
                    loadButton.setText("Load URL");
                    loadButton.setEnabled(true);
                    inspectButton.setEnabled(true);
                    clearButton.setEnabled(true);
                });
                System.out.println("Error loading URL: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }

    /**
     * Appends text to the output area with consistent styling, ensuring black background.
     * @param text The text to append.
     */
    private void appendToOutputArea(String text) {
        SwingUtilities.invokeLater(() -> {
            try {
                StyledDocument doc = outputArea.getStyledDocument();
                SimpleAttributeSet attrs = new SimpleAttributeSet();
                String devanagariFont = getAvailableDevanagariFont();
                StyleConstants.setFontFamily(attrs, devanagariFont);
                StyleConstants.setFontSize(attrs, 14);
                StyleConstants.setForeground(attrs, new Color(255, 255, 255));
                StyleConstants.setBackground(attrs, new Color(0, 0, 0)); // Ensure text background is black
                outputArea.setBackground(new Color(0, 0, 0)); // Reinforce entire output area background
                outputArea.setOpaque(true);
                scrollPane.setBackground(new Color(0, 0, 0));
                scrollPane.getViewport().setBackground(new Color(0, 0, 0));
                System.out.println("Appending text: " + text);
                doc.insertString(doc.getLength(), text, attrs);
                outputArea.setCaretPosition(doc.getLength());
            } catch (BadLocationException e) {
                System.out.println("Error appending to outputArea: " + e.getMessage());
            }
        });
    }

    /**
     * Toggles inspect mode in the browser using JavaScript event listeners.
     * @param enable True to enable inspect mode, false to disable.
     */
    private void toggleInspectMode(boolean enable) {
        System.out.println("Toggling Inspect Mode: " + enable);
        synchronized (lock) {
            capturing = enable;
        }
        if (!enable) {
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
                System.out.println("JavaScript listeners cleared and normal interaction restored");
            } catch (Exception ex) {
                System.out.println("Error clearing JavaScript listeners: " + ex.getMessage());
                ex.printStackTrace();
                appendToOutputArea("Error clearing listeners: " + ex.getMessage() + "\n");
            }
            return;
        }

        CompletableFuture.runAsync(() -> {
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                driver.switchTo().defaultContent();
                Object initResult = js.executeScript(
                    "try {" +
                    "  console.log('Initializing listeners...');" +
                    "  window.elementDetails = null;" +
                    "  window.escapeText = function(text) {" +
                    "    try {" +
                    "      if (!text) return '';" +
                    "      return text.replace(/'/g, \"\\\\'\").replace(/\\\"/g, \"\\\\\\\"\");" +
                    "    } catch (err) {" +
                    "      console.error('escapeText error: ' + err.message);" +
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
                    "      return text.replace(/[\\n\\r]+/g, ' ').replace(/\\s+/g, ' ');" +
                    "    } catch (err) {" +
                    "      console.error('normalizeText error: ' + err.message);" +
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
                    "        var segment = index > 0 ? tag + '[' + index + ']' : tag;" +
                    "        paths.unshift(segment);" +
                    "        current = current.parentNode;" +
                    "      }" +
                    "      return paths.length ? '/html' + (paths.length ? '/' + paths.join('/') : '') : '';" +
                    "    } catch (err) {" +
                    "      console.error('getAbsoluteXPath error: ' + err.message);" +
                    "      return '';" +
                    "    }" +
                    "  };" +
                    "  window.getRelativeXPath = function(element) {" +
                    "    try {" +
                    "      if (!element || !element.tagName) return '';" +
                    "      var tag = element.tagName.toLowerCase();" +
                    "      var predicates = [];" +
                    "      if (element.id && element.id.trim()) {" +
                    "        var idValue = element.id;" +
                    "        var escapedId = window.escapeText(idValue);" +
                    "        predicates.push('@id=\\'' + escapedId + '\\'');" +
                    "      }" +
                    "      if (element.className && element.className.trim()) {" +
                    "        var classValue = element.className;" +
                    "        var escapedClass = window.escapeText(classValue);" +
                    "        predicates.push('@class=\\'' + escapedClass + '\\'');" +
                    "      }" +
                    "      var text = element.textContent ? window.normalizeText(element.textContent) : '';" +
                    "      if (text && text.trim().length > 0) {" +
                    "        var escapedText = window.escapeText(text.trim());" +
                    "        predicates.push('contains(normalize-space(text()),\\'' + escapedText + '\\')');" +
                    "      }" +
                    "      var xpath = '//' + tag;" +
                    "      if (predicates.length > 0) {" +
                    "        xpath += '[' + predicates.join(' and ') + ']';" +
                    "      }" +
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
                    "}"
                );
                System.out.println("initResult: " + (initResult != null ? initResult.toString() : "null"));
                if (initResult == null || initResult.toString().contains("Error:")) {
                    String errorMsg = initResult != null ? initResult.toString() : "Initialization result is null";
                    appendToOutputArea("Failed to initialize listeners: " + errorMsg + "\n");
                    return;
                }

                if (initResult instanceof HashMap) {
                    HashMap<String, Object> resultMap = (HashMap<String, Object>) initResult;
                    String status = (String) resultMap.get("status");
                    System.out.println("Initialization status: " + status);
                    if (!"Listeners setup initiated".equals(status)) {
                        appendToOutputArea("Failed to initialize listeners: " + status + "\n");
                        return;
                    }
                }

                Thread.sleep(1000);
                Object listenerStatus = js.executeScript("return window.checkListeners ? window.checkListeners() : false;");
                System.out.println("Listener status after setup: " + listenerStatus);
                if (listenerStatus instanceof Boolean && !(Boolean) listenerStatus) {
                    appendToOutputArea("Error: Listeners not attached properly\n");
                    return;
                }

                while (true) {
                    synchronized (lock) {
                        if (!capturing || driver == null) break;
                    }
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
                            if (extractor == null) {
                                appendToOutputArea("Error: WebElementDetailsExtractor is not initialized\n");
                                continue;
                            }
                            String parsedDetails = extractor.extractElementDetails(finalDetails);
                            System.out.println("Raw parsed details: " + (parsedDetails != null ? parsedDetails : "null"));
                            if (parsedDetails != null && !parsedDetails.trim().isEmpty() && !parsedDetails.contains("No valid element details parsed")) {
                                String elementId = "Element_" + System.currentTimeMillis();
                                extractor.storeInRepository(elementId, parsedDetails);
                                appendElementDetails(elementId, parsedDetails);
                            } else {
                                System.out.println("No valid details parsed: " + parsedDetails);
                                appendToOutputArea("Element Details: No valid element details parsed: " + parsedDetails + "\n");
                            }
                        } else if (finalDetails != null && finalDetails.startsWith("Error:")) {
                            System.out.println("JavaScript error: " + finalDetails);
                            appendToOutputArea("JavaScript error: " + finalDetails + "\n");
                        }
                        Thread.sleep(500);
                    } catch (Exception ex) {
                        System.out.println("Capture error: " + ex.getMessage());
                        ex.printStackTrace();
                        appendToOutputArea("Capture error: " + ex.getMessage() + "\n");
                    }
                }
            } catch (Exception ex) {
                System.out.println("Error initializing listeners: " + ex.getMessage());
                ex.printStackTrace();
                appendToOutputArea("Error initializing listeners: " + ex.getMessage() + "\n");
            }
        });
    }

    /**
     * Retrieves an available Devanagari font or falls back to a default.
     * @return The font name to use.
     */
    private String getAvailableDevanagariFont() {
        String[] devanagariFonts = {"Mangal", "Noto Sans Devanagari", "Arial Unicode MS"};
        for (String font : devanagariFonts) {
            if (new Font(font, Font.PLAIN, 14).canDisplay('अ')) {
                System.out.println("Using font: " + font);
                return font;
            }
        }
        System.out.println("Warning: No Devanagari font found, falling back to default");
        return "Dialog";
    }

    /**
     * Appends element details to the output area with copy and add buttons.
     * @param elementId The ID for the element.
     * @param parsedDetails The details to display.
     */
    private void appendElementDetails(String elementId, String parsedDetails) {
        SwingUtilities.invokeLater(() -> {
            try {
                StyledDocument doc = outputArea.getStyledDocument();
                SimpleAttributeSet attrs = new SimpleAttributeSet();
                String devanagariFont = getAvailableDevanagariFont();
                StyleConstants.setFontFamily(attrs, devanagariFont);
                StyleConstants.setFontSize(attrs, 14);
                StyleConstants.setForeground(attrs, new Color(255, 255, 255));
                StyleConstants.setBackground(attrs, new Color(0, 0, 0));

                doc.insertString(doc.getLength(), "\n----\nStored in repository as: " + elementId + "\nElement Details:\n", attrs);

                String[] lines = parsedDetails.split("\n");
                String relativeXPath = "";
                String absoluteXPath = "";
                for (String line : lines) {
                    if (line.startsWith("relativeXPath:")) {
                        relativeXPath = line.substring("relativeXPath:".length()).trim();
                        CustomButton copyButton = new CustomButton("Copy", 8, 1, new Color(250, 128, 114), new Color(250, 128, 114));
                        copyButton.setFont(new Font("Arial", Font.PLAIN, 10));
                        copyButton.setMargin(new Insets(2, 4, 2, 4));
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
                        addButton.setMargin(new Insets(2, 4, 2, 4));
                        addButton.setForeground(Color.WHITE);
                        addButton.addActionListener(e -> {
                            appendToOutputArea("Element added to the repository\n");
                            // TODO: Implement CreateTestSuite.addWebElementToRepository
                            // CreateTestSuite.addWebElementToRepository(elementId, finalXPath);
                        });
                        Style buttonStyle = doc.addStyle("button" + System.currentTimeMillis(), null);
                        StyleConstants.setComponent(buttonStyle, copyButton);
                        doc.insertString(doc.getLength(), " ", buttonStyle);
                        doc.insertString(doc.getLength(), " ", attrs);
                        Style addButtonStyle = doc.addStyle("addButton" + System.currentTimeMillis(), null);
                        StyleConstants.setComponent(addButtonStyle, addButton);
                        doc.insertString(doc.getLength(), " ", addButtonStyle);
                        doc.insertString(doc.getLength(), " relativeXPath: " + relativeXPath + "\n", attrs);
                    } else if (line.startsWith("absoluteXPath:")) {
                        absoluteXPath = line.substring("absoluteXPath:".length()).trim();
                        CustomButton copyButton = new CustomButton("Copy", 8, 1, new Color(250, 128, 114), new Color(250, 128, 114));
                        copyButton.setFont(new Font("Arial", Font.PLAIN, 10));
                        copyButton.setMargin(new Insets(2, 4, 2, 4));
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
                        addButton.setMargin(new Insets(2, 4, 2, 4));
                        addButton.setForeground(Color.WHITE);
                        addButton.addActionListener(e -> {
                            appendToOutputArea("Element added to the repository\n");
                            // TODO: Implement CreateTestSuite.addWebElementToRepository
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
            } catch (BadLocationException e) {
                System.out.println("Error appending element details: " + e.getMessage());
            }
        });
    }

    /**
     * Custom button with styled appearance and hover effects.
     */
    public static class CustomButton extends JButton {
        protected Color defaultColor = Color.BLACK;
        protected Color hoverColor = new Color(250, 128, 114);
        protected Color disabledColor = new Color(50, 50, 50);
        protected Color borderColor = null;
        protected Color borderHoverColor = null;
        protected boolean isHovered = false;
        protected boolean isDisabled = false;
        private int arc = 8;
        private int shadowOffset = 1;

        public CustomButton(String text) {
            this(text, 12, 1, null, null);
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
            setFont(new Font("Arial", Font.BOLD, 12));
            setMargin(new Insets(4, 10, 4, 10));
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

    /**
     * Checks if the browser is open and accessible.
     * @return True if the browser is open, false otherwise.
     */
    private boolean isBrowserOpen() {
        if (driver == null) {
            return false;
        }
        try {
            driver.getWindowHandle();
            return true;
        } catch (Exception e) {
            System.out.println("Browser window is closed or unavailable: " + e.getMessage());
            return false;
        }
    }

    /**
     * Starts a polling thread to monitor the browser's state.
     */
    private void startBrowserPolling() {
        CompletableFuture.runAsync(() -> {
            while (polling.get() && driver != null) {
                try {
                    if (!isBrowserOpen()) {
                        System.out.println("Browser window closed, closing JFrame");
                        SwingUtilities.invokeLater(() -> {
                            appendToOutputArea("Browser window closed, shutting down application\n");
                            synchronized (lock) {
                                if (inspectMode) {
                                    toggleInspectMode(false);
                                }
                            }
                            dispose();
                        });
                        break;
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Browser polling interrupted: " + e.getMessage());
                    break;
                }
            }
        });
    }

    /**
     * Cleans up resources and closes the application.
     */
    @Override
    public void dispose() {
        polling.set(false);
        synchronized (lock) {
            if (inspectMode) {
                toggleInspectMode(false);
            }
        }
        if (driver != null) {
            try {
                driver.quit();
                System.out.println("WebDriver closed successfully");
                appendToOutputArea("WebDriver closed\n");
            } catch (Exception e) {
                System.out.println("Error closing WebDriver: " + e.getMessage());
                appendToOutputArea("Error closing WebDriver: " + e.getMessage() + "\n");
            } finally {
                driver = null;
            }
        }
        super.dispose();
    }

    /**
     * Main entry point to launch the application.
     * @param args Command line arguments.
     */
    public static void main(String args[]) {
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
}