package com.automation.bolt.gui;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;

public class WebElementInspectorGUI {
    private final WebElementDetailsExtractor extractor;
    private final JTextPane outputArea;
    private final JTextField urlField;
    private WebDriver driver;
    private boolean inspectMode = false;
    private volatile boolean capturing = false;
    private CustomButton inspectButton;
    private CustomButton loadButton;

    // Custom button class for enhanced appearance
    private static class CustomButton extends JButton {
        protected Color defaultColor = Color.BLACK; // Black background
        protected Color hoverColor = new Color(250, 128, 114); // Pink on hover
        protected Color disabledColor = new Color(50, 50, 50); // Dark gray for disabled state
        protected Color borderColor = new Color(250, 128, 114); // Pink border
        protected boolean isHovered = false;
        protected boolean isDisabled = false;
        private int arc = 8; // Rounded corners
        private int shadowOffset = 1; // Shadow offset

        public CustomButton(String text) {
            this(text, 12, 1); // Default values for main buttons
        }

        public CustomButton(String text, int arc, int shadowOffset) {
            super(text);
            this.arc = arc;
            this.shadowOffset = shadowOffset;
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setForeground(Color.WHITE);
            setFont(new Font("Arial", Font.BOLD, 12)); // Smaller font
            setMargin(new Insets(4, 8, 4, 8)); // Reduced margins
            setPreferredSize(new Dimension(60, 25)); // Smaller size
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

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(0, 0, 0, 50)); // Shadow
            g2.fill(new RoundRectangle2D.Float(shadowOffset, shadowOffset, getWidth() - shadowOffset, getHeight() - shadowOffset, arc, arc));
            if (isDisabled || !isEnabled()) {
                g2.setColor(isHovered ? disabledColor.brighter() : disabledColor);
            } else {
                g2.setColor(isHovered ? hoverColor : defaultColor);
            }
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - shadowOffset, getHeight() - shadowOffset, arc, arc));
            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(1.5f));
            g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - shadowOffset, getHeight() - shadowOffset, arc, arc));
            super.paintComponent(g);
            g2.dispose();
        }
    }

    public WebElementInspectorGUI() {
        extractor = new WebElementDetailsExtractorImpl();

        JFrame frame = new JFrame("Web Element Inspector");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Create output area as JTextPane
        outputArea = new JTextPane();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        outputArea.setBackground(new Color(40, 40, 40));
        outputArea.setForeground(Color.WHITE);
        StyledDocument doc = outputArea.getStyledDocument();
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setFontFamily(attrs, "Monospaced");
        StyleConstants.setFontSize(attrs, 14);
        StyleConstants.setForeground(attrs, Color.WHITE);
        try {
            doc.insertString(doc.getLength(), "Application Loading......\n", attrs);
        } catch (BadLocationException e) {
            System.out.println("Error initializing outputArea: " + e.getMessage());
        }
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        centerFrameOnScreen(frame);

        try {
            frame.setIconImage(ImageIO.read(getClass().getResource("/icon.png")));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error loading window icon: " + e.getMessage());
        }

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.setBackground(new Color(245, 245, 245));

        urlField = new JTextField("https://example.com", 30);
        urlField.setFont(new Font("Arial", Font.PLAIN, 14));
        urlField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        loadButton = new CustomButton("Load URL");
        inspectButton = new CustomButton("Inspect"); // Changed from "Toggle Inspect" to "Inspect"
        inspectButton.setToolTipText("Toggle Inspect Mode");
        inspectButton.setDisabled(true);
        CustomButton clearButton = new CustomButton("Clear");
        clearButton.setToolTipText("Clear text area and repository");

        inputPanel.add(new JLabel("URL:"));
        inputPanel.add(urlField);
        inputPanel.add(loadButton);
        inputPanel.add(inspectButton);
        inputPanel.add(clearButton);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        try {
            WebDriverManager.chromedriver().setup();
            ChromeOptions co = new ChromeOptions();
            co.addArguments("--remote-allow-origins=*");
            co.addArguments("--start-maximized");
            co.addArguments("--ignore-certificate-errors");
            co.addArguments("--blink-settings=imagesEnabled=true");
            HashMap<String, Object> prefs = new HashMap<>();
            prefs.put("profile.default_content_setting_values.notifications", 2);
            co.setExperimentalOption("prefs", prefs);
            driver = new ChromeDriver(co);
            System.out.println("WebDriver initialized successfully");
        } catch (Exception ex) {
            appendToOutputArea("Error initializing WebDriver: " + ex.getMessage() + "\n");
            System.out.println("Error initializing WebDriver: " + ex.getMessage());
            ex.printStackTrace();
        }

        loadButton.addActionListener(e -> {
            System.out.println("Load URL button clicked: " + urlField.getText());
            loadUrl(urlField.getText());
        });

        inspectButton.addActionListener(e -> {
            System.out.println("Toggle Inspect button clicked");
            inspectMode = !inspectMode;
            inspectButton.setDisabled(!inspectMode);
            appendToOutputArea((inspectMode ? "Inspect Mode: Click an element in the browser (ensure browser window is focused)" : "\nInspect Mode: Disabled") + "\n");
            System.out.println("Inspect Mode Toggled: " + inspectMode);
            toggleInspectMode(inspectMode);
        });

        clearButton.addActionListener(e -> {
            System.out.println("Clear button clicked");
            extractor.clearRepository();
            try {
                StyledDocument docClear = outputArea.getStyledDocument();
                docClear.remove(0, docClear.getLength());
                docClear.insertString(0, "Inspect Mode: " + (inspectMode ? "Click an element in the browser (ensure browser window is focused)" : "Disabled") + "\n", attrs);
            } catch (BadLocationException ex) {
                System.out.println("Error clearing outputArea: " + ex.getMessage());
            }
            System.out.println("Text area and repository cleared");
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                System.out.println("Closing application");
                if (driver != null) {
                    driver.quit();
                }
            }
        });

        frame.setVisible(true);
        System.out.println("GUI frame displayed");

        appendToOutputArea("Application Started\nInspect Mode: Disabled\n");
    }

    private void centerFrameOnScreen(JFrame frame) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle screenBounds = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) ((screenBounds.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((screenBounds.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    private void appendToOutputArea(String text) {
        SwingUtilities.invokeLater(() -> {
            try {
                StyledDocument doc = outputArea.getStyledDocument();
                SimpleAttributeSet attrs = new SimpleAttributeSet();
                StyleConstants.setFontFamily(attrs, "Monospaced");
                StyleConstants.setFontSize(attrs, 14);
                StyleConstants.setForeground(attrs, Color.WHITE);
                doc.insertString(doc.getLength(), text, attrs);
                outputArea.setCaretPosition(doc.getLength());
            } catch (BadLocationException e) {
                System.out.println("Error appending to outputArea: " + e.getMessage());
            }
        });
    }

    private void appendElementDetails(String elementId, String parsedDetails) {
        SwingUtilities.invokeLater(() -> {
            try {
                StyledDocument doc = outputArea.getStyledDocument();
                SimpleAttributeSet attrs = new SimpleAttributeSet();
                StyleConstants.setFontFamily(attrs, "Monospaced");
                StyleConstants.setFontSize(attrs, 14);
                StyleConstants.setForeground(attrs, Color.WHITE);

                doc.insertString(doc.getLength(), "\n----\nStored in repository as: " + elementId + "\nElement Details:\n", attrs);

                String[] lines = parsedDetails.split("\n");
                String relativeXPath = "";
                String absoluteXPath = "";
                for (String line : lines) {
                    if (line.startsWith("relativeXPath:")) {
                        relativeXPath = line.substring("relativeXPath:".length()).trim();
                        CustomButton copyButton = new CustomButton("Copy", 8, 1);
                        copyButton.setFont(new Font("Arial", Font.PLAIN, 10));
                        copyButton.setMargin(new Insets(2, 4, 2, 4));
                        copyButton.setPreferredSize(new Dimension(40, 20));
                        copyButton.setForeground(Color.WHITE);
                        String finalXPath = relativeXPath;
                        copyButton.addActionListener(e -> {
                            StringSelection selection = new StringSelection(finalXPath);
                            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                            clipboard.setContents(selection, null);
                            appendToOutputArea("Copied to clipboard: " + finalXPath + "\n");
                        });
                        Style buttonStyle = doc.addStyle("button" + System.currentTimeMillis(), null);
                        StyleConstants.setComponent(buttonStyle, copyButton);
                        doc.insertString(doc.getLength(), " ", buttonStyle);
                        doc.insertString(doc.getLength(), "relativeXPath: " + relativeXPath + "\n", attrs);
                    } else if (line.startsWith("absoluteXPath:")) {
                        absoluteXPath = line.substring("absoluteXPath:".length()).trim();
                        CustomButton copyButton = new CustomButton("Copy", 8, 1);
                        copyButton.setFont(new Font("Arial", Font.PLAIN, 10));
                        copyButton.setMargin(new Insets(2, 4, 2, 4));
                        copyButton.setPreferredSize(new Dimension(40, 20));
                        copyButton.setForeground(Color.WHITE);
                        String finalXPath = absoluteXPath;
                        copyButton.addActionListener(e -> {
                            StringSelection selection = new StringSelection(finalXPath);
                            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                            clipboard.setContents(selection, null);
                            appendToOutputArea("Copied to clipboard: " + finalXPath + "\n");
                        });
                        Style buttonStyle = doc.addStyle("button" + System.currentTimeMillis(), null);
                        StyleConstants.setComponent(buttonStyle, copyButton);
                        doc.insertString(doc.getLength(), " ", buttonStyle);
                        doc.insertString(doc.getLength(), "absoluteXPath: " + absoluteXPath + "\n", attrs);
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

    private void loadUrl(String url) {
        SwingUtilities.invokeLater(() -> {
            loadButton.setText("Loading URL");
            loadButton.setEnabled(false);
            inspectButton.setEnabled(false);
        });
        CompletableFuture.runAsync(() -> {
            try {
                driver.get(url);
                Thread.sleep(2000);
                SwingUtilities.invokeLater(() -> {
                    appendToOutputArea("Loaded URL: " + url + "\n");
                    loadButton.setText("Load URL");
                    loadButton.setEnabled(true);
                    inspectButton.setEnabled(true);
                });
                System.out.println("Loaded URL: " + url);
                if (inspectMode) {
                    inspectMode = false;
                    inspectButton.setDisabled(true);
                    SwingUtilities.invokeLater(() -> {
                        appendToOutputArea("\nInspect Mode: Disabled\n");
                        loadButton.setText("Load URL");
                        loadButton.setEnabled(true);
                        inspectButton.setEnabled(true);
                    });
                    toggleInspectMode(false);
                }
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    appendToOutputArea("Error loading URL: " + ex.getMessage() + "\n");
                    loadButton.setText("Load URL");
                    loadButton.setEnabled(true);
                    inspectButton.setEnabled(true);
                });
                System.out.println("Error loading URL: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }

    private void toggleInspectMode(boolean enable) {
        System.out.println("Toggling Inspect Mode: " + enable);
        capturing = enable;
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
                    "      return text.replace(/'/g, '\\\\\\'').replace(/\"/g, '\\\\\"');" +
                    "    } catch (err) {" +
                    "      console.error('escapeText error: ' + err.message);" +
                    "      return '';" +
                    "    }" +
                    "  };" +
                    "  window.getCssSelector = function(element) {" +
                    "    try {" +
                    "      console.log('getCssSelector called for element: ' + (element.tagName || 'unknown'));" +
                    "      if (!element || !element.tagName) return '';" +
                    "      var path = [];" +
                    "      var current = element;" +
                    "      while (current && current.nodeType === 1) {" +
                    "        var selector = '';" +
                    "        if (current.id) {" +
                    "          selector = '#' + current.id;" +
                    "          path.unshift(selector);" +
                    "          break;" +
                    "        }" +
                    "        var tag = current.tagName.toLowerCase();" +
                    "        selector = tag;" +
                    "        if (current.className && current.className.trim()) {" +
                    "          var classes = current.className.trim().split(/\\s+/).map(function(cls) {" +
                    "            return '.' + cls.replace(/[^\\w-]/g, '\\\\$&');" +
                    "          }).join('');" +
                    "          var classSelector = tag + classes;" +
                    "          var classPath = path.slice();" +
                    "          classPath.unshift(classSelector);" +
                    "          try {" +
                    "            var testClassSelector = classPath.join(' > ');" +
                    "            if (document.querySelectorAll(testClassSelector).length === 1) {" +
                    "              path = classPath;" +
                    "              break;" +
                    "            }" +
                    "          } catch (err) {" +
                    "            console.log('Class selector test error: ' + err.message);" +
                    "          }" +
                    "        }" +
                    "        var siblings = current.parentNode ? Array.from(current.parentNode.children).filter(function(child) {" +
                    "          return child.tagName === current.tagName;" +
                    "        }) : [];" +
                    "        if (siblings.length > 1) {" +
                    "          var index = siblings.indexOf(current) + 1;" +
                    "          selector = tag + ':nth-of-type(' + index + ')';" +
                    "        }" +
                    "        path.unshift(selector);" +
                    "        try {" +
                    "          var testSelector = path.join(' > ');" +
                    "          if (document.querySelectorAll(testSelector).length === 1) {" +
                    "            break;" +
                    "          }" +
                    "        } catch (err) {" +
                    "          console.log('Selector test error: ' + err.message);" +
                    "        }" +
                    "        current = current.parentNode;" +
                    "        if (path.length > 3) break;" +
                    "      }" +
                    "      return path.join(' > ') || element.tagName.toLowerCase();" +
                    "    } catch (err) {" +
                    "      console.error('getCssSelector error: ' + err.message + ' at line ' + (err.lineNumber || 'unknown'));" +
                    "      return '';" +
                    "    }" +
                    "  };" +
                    "  window.getAbsoluteXPath = function(element) {" +
                    "    try {" +
                    "      console.log('getAbsoluteXPath called');" +
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
                    "      console.error('getAbsoluteXPath error: ' + err.message + ' at line ' + (err.lineNumber || 'unknown'));" +
                    "      return '';" +
                    "    }" +
                    "  };" +
                    "  window.getRelativeXPath = function(element) {" +
                    "    try {" +
                    "      console.log('getRelativeXPath called');" +
                    "      if (!element || !element.tagName) return '';" +
                    "      var tag = element.tagName.toLowerCase();" +
                    "      if (element.id) return '//' + tag + '[@id=\\'' + element.id + '\\']';" +
                    "      if (element.name && element.name.trim()) return '//' + tag + '[@name=\\'' + element.name + '\\']';" +
                    "      if (element.className && element.className.trim()) {" +
                    "        var className = element.className.trim().replace(/\\s+/g, ' ');" +
                    "        var xpath = '//' + tag + '[@class=\\'' + className + '\\']';" +
                    "        try {" +
                    "          var nodes = document.evaluate(xpath, document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);" +
                    "          if (nodes.snapshotLength === 1) return xpath;" +
                    "        } catch (err) {" +
                    "          console.log('XPath class test error: ' + err.message);" +
                    "        }" +
                    "      }" +
                    "      var text = element.textContent ? element.textContent.trim() : '';" +
                    "      if (text && text.length > 3) {" +
                    "        var escapedText = window.escapeText(text);" +
                    "        return '//' + tag + '[contains(text(),\\'' + escapedText + '\\')]';" +
                    "      }" +
                    "      return '//' + tag;" +
                    "    } catch (err) {" +
                    "      console.error('getRelativeXPath error: ' + err.message);" +
                    "      return '';" +
                    "    }" +
                    "  };" +
                    "  window.elementCapture = function(e) {" +
                    "    try {" +
                    "      console.log('elementCapture triggered for element: ' + (e.target.tagName || 'unknown'));" +
                    "      e.preventDefault();" +
                    "      e.stopPropagation();" +
                    "      var el = e.target;" +
                    "      var details = {" +
                    "        tagName: el.tagName ? el.tagName.toLowerCase() : ''," +
                    "        text: el.textContent ? el.textContent.trim().replace(/\\s+/g, ' ').substring(0, 50) : ''," +
                    "        id: el.id || ''," +
                    "        class: el.className || ''," +
                    "        cssSelector: window.getCssSelector(el)," +
                    "        absoluteXPath: window.getAbsoluteXPath(el)," +
                    "        relativeXPath: window.getRelativeXPath(el)" +
                    "      };" +
                    "      console.log('Captured details: ' + JSON.stringify(details));" +
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
                    "      console.log('elementHover error: ' + err.message);" +
                    "    }" +
                    "  };" +
                    "  window.elementHoverOut = function(e) {" +
                    "    try {" +
                    "      e.target.style.outline = '';" +
                    "    } catch (err) {" +
                    "      console.log('elementHoverOut error: ' + err.message);" +
                    "    }" +
                    "  };" +
                    "  window.checkListeners = function() {" +
                    "    try {" +
                    "      var status = !!window.elementCapture;" +
                    "      console.log('checkListeners: ' + status);" +
                    "      return status;" +
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
                    "  console.error('Initialization error: ' + err.message + ' at line ' + (err.lineNumber || 'unknown') + ' stack: ' + (err.stack || 'no stack'));" +
                    "  return { status: 'Error: ' + err.message + ' at line ' + (err.lineNumber || 'unknown') + ' stack: ' + (err.stack || 'no stack') };" +
                    "}"
                );
                System.out.println("Event listeners initialized: " + (initResult != null ? initResult.toString() : "null"));
                if (initResult == null || initResult.toString().contains("Error:")) {
                    String errorMsg = initResult != null ? initResult.toString() : "Initialization result is null";
                    appendToOutputArea("Failed to initialize listeners: " + errorMsg + "\n");
                    return;
                }

                if (initResult instanceof java.util.Map) {
                    java.util.Map<String, Object> resultMap = (java.util.Map<String, Object>) initResult;
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

                while (capturing && driver != null) {
                    try {
                        Object result = js.executeScript(
                            "try {" +
                            "  console.log('Polling elementDetails...');" +
                            "  var result = window.elementDetails;" +
                            "  console.log('elementDetails: ' + (result ? JSON.stringify(result) : 'null'));" +
                            "  if (result) {" +
                            "    window.elementDetails = null;" +
                            "    return { details: JSON.stringify(result) };" +
                            "  }" +
                            "  return { details: null };" +
                            "} catch (err) {" +
                            "  console.log('Poll error: ' + err.message);" +
                            "  return { details: 'Error: ' + err.message };" +
                            "}"
                        );
                        String details = null;
                        if (result instanceof java.util.Map) {
                            java.util.Map<String, Object> resultMap = (java.util.Map<String, Object>) result;
                            details = (String) resultMap.get("details");
                        }
                        final String finalDetails = details;
                        if (finalDetails != null && !finalDetails.isEmpty() && !finalDetails.startsWith("Error:") && !finalDetails.equals("null")) {
                            String parsedDetails = extractor.extractElementDetails(finalDetails);
                            System.out.println("Parsed details: " + (parsedDetails != null ? parsedDetails.replace("\n", "\\n") : "null"));
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

    public static void main(String[] args) {
        System.out.println("Starting application");
        SwingUtilities.invokeLater(WebElementInspectorGUI::new);
    }
}