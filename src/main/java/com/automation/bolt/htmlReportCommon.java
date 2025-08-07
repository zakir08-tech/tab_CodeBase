/*
 * Copyright (c) 2025. All rights reserved.
 * Licensed under the Apache License, Version 2.0.
 */
package com.automation.bolt;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import static com.automation.bolt.gui.ExecuteRegressionSuite.arrTestId;
import static com.automation.bolt.gui.ExecuteRegressionSuite.excelFile;
import static com.automation.bolt.gui.ExecuteRegressionSuite.importDataFromExcelModel;

/**
 * Utility class for generating HTML test reports for the Bolt Test Automation Accelerator.
 * Provides templates and methods to create, edit, and manage test report data, including
 * test case steps, screenshots, and summary statistics.
 */
public class htmlReportCommon {

    // region Template Strings
    public static final String trTemplatePassed = """
            <tr class="$tableStatus">
                <td>$testId</td>
                <td>$testStep</td>
                <td style="font-size: 1rem;">$testDuration</td>
                <td>$testDesc</td>
            </tr>""";

    public static final String trTemplateUserDefine = """
            <tr class="$tableStatus">
                <td>$testId</td>
                <td>$testStep</td>
                <td style="font-size: 1rem;">$testDuration</td>
                <td>$testDesc</td>
            </tr>""";

    public static final String trTemplateScreenShot = """
            <tr class="$tableStatus">
                <td>$testId</td>
                <td>$testStep<img src="$screenShotFilePath" width="100%" height="auto" class="screenshot" alt=" "></td>
                <td style="font-size: 1rem;">$testDuration</td>
                <td>$testDesc</td>
            </tr>""";

    public static final String trTemplateScreenShotUserDefine = """
            <tr class="$tableStatus">
                <td>$testId</td>
                <td><img src="$screenShotFilePath" class="screenshot" alt=" " width="100%" height="auto"></td>
                <td style="font-size: 1rem;">$testDuration</td>
                <td>$testDesc</td>
            </tr>""";

    public static final String trTemplateStepFailed = """
            <tr class="$tableStatus">
                <td>$testId</td>
                <td>$testStep<br><font style="color:#87ceeb">$stepError</font>
                <img src="$screenShotFilePath" class="screenshot" alt=" " width="100%" height="auto">
                </td>
                <td style="font-size: 1rem;">$testDuration</td>
                <td>$testDesc</td>
            </tr>""";

    public static final String trTemplateKeywordWarning = """
            <tr class="$tableStatus">
                <td>$testId</td>
                <td>$testStep</td>
                <td style="font-size: 1rem;">$testDuration</td>
                <td>$testDesc</td>
            </tr>""";

    public static final String trTemplateObjectWarning = """
            <tr class="$tableStatus">
                <td>$testId</td>
                <td>$testStep<br><font style="color:#deb887">$stepError</font>
                </td>
                <td style="font-size: 1rem;">$testDuration</td>
                <td>$testDesc</td>
            </tr>""";

    public static final String trTemplateSkipped = """
            <tr class="$tableStatus">
                <td>$testId</td>
                <td>$testStep</td>
                <td></td>
                <td></td>
            </tr>""";

    public static final String trTemplateCard = """
            <div class="card">
                <div class="card-header" style="background-color: $testResultColor; color: #ffffff">
                    <a class="collapsed btn" data-bs-toggle="collapse" href="#TestCase-$collapseIndex" style="width:100%; color: #ffffff">
                        <h6 align="left">$testDescription</h6>
                    </a>
                </div>
                <div id="TestCase-$collapseIndex" class="collapse" data-bs-parent="#accordion">
                    <div class="card-body" style="background-color: #2c2c2c">
                        <div class="container mt-3">
                            <table class="table table-dark">
                                <thead>
                                    <tr class="table-dark">
                                        <th style="width:8%; text-align: left; vertical-align: middle">Step No.</th>
                                        <th style="text-align: left; vertical-align: middle;">Test Step</th>
                                        <th style="width:12%">Duration [min:sec:ms]</th>
                                        <th style="width:15%; vertical-align: middle;">Step Desc.</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    $stepRows
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>""";

    public static final String trTestContainer = """
            <div class="container mt-3">
                <div id="accordion">
                    $testCards
                </div>
            </div>""";
    // endregion

    // region HTML Report Templates
    public static final String htmlTestReport;

    static {
        StringBuilder html = new StringBuilder();
        html.append("""
                <!DOCTYPE html>
                <html lang="en" data-bs-theme="dark">
                    <head>
                        <title>Bolt-TestRunReport</title>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1">
                        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet">
                        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js"></script>
                        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js"></script>
                        <link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.7.0/css/all.css' integrity='sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ' crossorigin='anonymous'>
                        <style>
                            @keyframes example {
                                0% {color: #ff4d4d;}
                                100% {color: #ffffff;}
                            }
                            body {
                                background-color: #1a1a1a;
                                color: #e0e0e0;
                                transform: scale(0.9);
                                transform-origin: top center;
                                min-height: 100vh;
                                display: flex;
                                flex-direction: column;
                                align-items: center;
                            }
                            .container-lg {
                                background-color: #2c2c2c;
                                border: 1px solid #444;
                            }
                            table {
                                table-layout: fixed;
                                width: 100%;
                            }
                            .table-dark tbody tr {
                                color: #000000;
                                border: 1px solid #6c757d;
                            }
                            .table-dark td {
                                border: 1px solid #6c757d;
                            }
                            .table-success {
                                background-color: #92D192 !important;
                            }
                            .table-danger {
                                background-color: #D96F6F !important;
                            }
                            .table-warning {
                                background-color: #ff8f00 !important;
                            }
                            .table-info {
                                background-color: #0288d1 !important;
                            }
                            .table-secondary {
                                background-color: #616161 !important;
                            }
                            .screenshot {
                                cursor: pointer;
                                width: 100%;
                                height: auto;
                                border: 1px solid #444;
                            }
                            .modal {
                                display: none;
                                position: fixed;
                                z-index: 1000;
                                left: 0;
                                top: 0;
                                width: 100%;
                                height: 100%;
                                background-color: rgba(0,0,0,0.9);
                                justify-content: center;
                                align-items: center;
                            }
                            .modal-content {
                                max-width: 90%;
                                max-height: 90%;
                                border: 2px solid #444;
                            }
                            .close-btn {
                                position: absolute;
                                top: 20px;
                                right: 30px;
                                color: #fff;
                                font-size: 30px;
                                cursor: pointer;
                            }
                            .filter-btn {
                                margin: 5px;
                            }
                        </style>
                    </head>
                    <body>
                        <div class="container-lg mt-3" style="width: 100%;">
                            <h4><b>Bo<i class="fa fa-bolt" style="font-size:18px;animation-name:example; position:relative; animation-duration:2s; animation-iteration-count:infinite"></i>t</b></h4>
                            <h6 style="color: #ff4d4d">Test Automation Accelerator</h6>
                        </div>
                        <div class="container-lg mt-3 border" style="width: 100%; height:330px; background-color: #2c2c2c">
                            <div style="float: left">
                                <p><br>Test Run Date: <font style="color: #bb86fc"><b>$runDate </b></font><font style="color: #ff8f00"><b>$runTime</b></font></p>
                                <p>Total Run: <font style="color: #03a9f4"><b>$totalRun</b></font></p>
                                <p>Passed: <font style="color: #92D192"><b>$totalPassed</b></font></p>
                                <p>Failed: <font style="color: #D96F6F"><b>$totalFailed</b></font></p>
                                <p>Warning: <font style="color: #ff8f00"><b>$totalWarnings</b></font></p>
                                <p>Run Time: <font style="color: #d81b60"><b>$totalHrs</b></font> hrs <font style="color: #ff8f00"><b>$totalMins</b></font> mins <font style="color: #03a9f4"><b>$totalSec</b></font> sec</p>
                                <p>Test Suite: <font style="color: #f4d03f"><b>$testSuiteName</b></font></p>
                            </div>
                            <canvas id="myChart" style="max-width:300px; float: right; background-color: #2c2c2c; border-radius: 8px;"></canvas>
                        </div>
                        <div class="container-lg mt-3" style="width: 100%;">
                            <button id="showPass" class="btn btn-outline-success filter-btn">Show Pass</button>
                            <button id="showFail" class="btn btn-outline-danger filter-btn">Show Fail</button>
                            <button id="showAll" class="btn btn-outline-light filter-btn">Show All</button>
                        </div>
                        $testCaseSteps
                        <div id="imageModal" class="modal">
                            <span class="close-btn">×</span>
                            <img class="modal-content" id="modalImage">
                        </div>
                        <script>
                            var xValues = ["Pass", "Fail", "Warning"];
                            var yValues = [$totalPassed, $totalFailed, $totalWarnings];
                            var barColors = [
                                "#92D192",
                                "#D96F6F",
                                "#ff8f00"
                            ];
                            new Chart("myChart", {
                                type: "doughnut",
                                data: {
                                    labels: xValues,
                                    datasets: [{
                                        backgroundColor: barColors,
                                        data: yValues
                                    }]
                                },
                                options: {
                                    title: {
                                        display: true,
                                        text: "Test Run Graph Summary",
                                        color: "#e0e0e0"
                                    },
                                    legend: {
                                        labels: {
                                            fontColor: "#e0e0e0"
                                        }
                                    }
                                }
                            });
                        </script>
                        <script>
                            // Screenshot modal handling
                            const images = document.querySelectorAll('.screenshot');
                            const modal = document.getElementById('imageModal');
                            const modalImg = document.getElementById('modalImage');
                            const closeBtn = document.querySelector('.close-btn');
                            images.forEach(img => {
                                img.addEventListener('click', function() {
                                    modal.style.display = 'flex';
                                    modalImg.src = this.src;
                                });
                            });
                            closeBtn.addEventListener('click', function() {
                                modal.style.display = 'none';
                            });
                            modal.addEventListener('click', function(e) {
                                if (e.target === modal) {
                                    modal.style.display = 'none';
                                }
                            });

                            // Test case filtering and collapsing
                            const showPassBtn = document.getElementById('showPass');
                            const showFailBtn = document.getElementById('showFail');
                            const showAllBtn = document.getElementById('showAll');
                            const testCards = document.querySelectorAll('.card');

                            function collapseAllCards() {
                                testCards.forEach(card => {
                                    const collapseElement = card.querySelector('.collapse');
                                    collapseElement.classList.remove('show');
                                });
                            }

                            showPassBtn.addEventListener('click', function() {
                                collapseAllCards();
                                testCards.forEach(card => {
                                    const header = card.querySelector('.card-header');
                                    card.style.display = header.style.backgroundColor === 'rgb(146, 209, 146)' ? 'block' : 'none';
                                });
                            });

                            showFailBtn.addEventListener('click', function() {
                                collapseAllCards();
                                testCards.forEach(card => {
                                    const header = card.querySelector('.card-header');
                                    card.style.display = header.style.backgroundColor === 'rgb(217, 111, 111)' ? 'block' : 'none';
                                });
                            });

                            showAllBtn.addEventListener('click', function() {
                                collapseAllCards();
                                testCards.forEach(card => {
                                    card.style.display = 'block';
                                });
                            });

                            // Initialize with all test cases visible and collapsed
                            collapseAllCards();
                            testCards.forEach(card => {
                                card.style.display = 'block';
                            });
                        </script>
                    </body>
                </html>""");
        htmlTestReport = html.toString();
    }

    public static String htmlTestReportHardStop = "";
    // endregion

    // region File Paths and Formatters
    public static final String htmlTestReportPath = constants.userDir + "/htmlTestReport";
    public static final String testCaseResultPath = constants.userDir + "/testResults";
    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
    public static final SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
    // endregion

    // region State Variables
    public static boolean testReportFolderExist;
    public static boolean testReportFolderEmpty;
    public static boolean testCaseReportFolderExist;
    public static boolean testCaseReportFolderEmpty;
    public static boltRunner bRunner;
    public static Integer stepIndex = 0;
    // endregion

    /**
     * Adds a custom test step to the user-defined steps map.
     *
     * @param addStep The test step description to add.
     */
    public static void writeStepInHtmlReport(String addStep) {
        boltRunner.userDefineSteps.put(stepIndex++, addStep);
    }

    /**
     * Captures a screenshot and adds it to the user-defined steps map.
     */
    public static void attachScreenShotInHtmlReport() {
        glueCode.keyTakeScreenShot();
        boltRunner.userDefineSteps.put(stepIndex++, "~take~screenshot~" + glueCode.screenshotPath);
    }

    /**
     * Edits the test run summary information in the HTML report template.
     *
     * @param trTemplate      The template string to edit.
     * @param testRunDateTime The test run date and time (format: "dd-MMM-yyyy hh:mm:ss a").
     * @param totalRun        The total number of test cases run.
     * @param runHrs          The total run time in hours.
     * @param runMins         The total run time in minutes.
     * @param runSecs         The total run time in seconds.
     * @return The edited template with replaced placeholders.
     */
    public static String trTemplateEditTestRunInfo(String trTemplate, String testRunDateTime, String totalRun,
            String runHrs, String runMins, String runSecs) {
        var editedTemplate = trTemplate;
        var dateTimeParts = testRunDateTime.split(" ");
        editedTemplate = editedTemplate.replace("$runDate", dateTimeParts[0]);
        editedTemplate = editedTemplate.replace("$runTime", dateTimeParts[1]);
        editedTemplate = editedTemplate.replace("$totalRun", String.valueOf(getTotalRunCount()));
        editedTemplate = editedTemplate.replace("$totalPassed", String.valueOf(getTheStatusCount("PASS")));
        editedTemplate = editedTemplate.replace("$totalFailed", String.valueOf(getTheStatusCount("FAIL")));
        editedTemplate = editedTemplate.replace("$totalWarnings", String.valueOf(getTheStatusCount("WARNING")));
        editedTemplate = editedTemplate.replace("$totalHrs", runHrs);
        editedTemplate = editedTemplate.replace("$totalMins", runMins);
        editedTemplate = editedTemplate.replace("$totalSec", runSecs);
        editedTemplate = editedTemplate.replace("$testSuiteName", excelFile.getName());
        return editedTemplate;
    }

    /**
     * Updates the hard-stop HTML report template with an error message, ensuring XSS safety.
     *
     * @param errorMsg The error message to display in the alert.
     * @return The updated HTML report template with the sanitized error message.
     */
    public static String updateErrorMessageForHardStopAndWebDriverException(String errorMsg) {
        // Sanitize errorMsg to prevent XSS
        var safeErrorMsg = errorMsg.replace("\"", "\\\"").replace("\n", "\\n");
        var html = new StringBuilder();
        html.append("""
                <!DOCTYPE html>
                <html lang="en" data-bs-theme="dark">
                    <head>
                        <title>Bolt-TestRunReport</title>
                        <meta charset="utf-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1">
                        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet">
                        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js"></script>
                        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js"></script>
                        <link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.7.0/css/all.css' integrity='sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ' crossorigin='anonymous'>
                        <script type="text/javascript">
                            window.alert("%s")
                        </script>
                        <style>
                            @keyframes example {
                                0% {color: #ff4d4d;}
                                100% {color: #ffffff;}
                            }
                            body {
                                background-color: #1a1a1a;
                                color: #e0e0e0;
                                transform: scale(0.9);
                                transform-origin: top center;
                                min-height: 100vh;
                                display: flex;
                                flex-direction: column;
                                align-items: center;
                            }
                            .container-lg {
                                background-color: #2c2c2c;
                                border: 1px solid #444;
                            }
                            table {
                                table-layout: fixed;
                                width: 100%;
                            }
                            .table-dark tbody tr {
                                color: #000000;
                                border: 1px solid #6c757d;
                            }
                            .table-dark td {
                                border: 1px solid #6c757d;
                            }
                            .table-success {
                                background-color: #92D192 !important;
                            }
                            .table-danger {
                                background-color: #D96F6F !important;
                            }
                            .table-warning {
                                background-color: #ff8f00 !important;
                            }
                            .table-info {
                                background-color: #0288d1 !important;
                            }
                            .table-secondary {
                                background-color: #616161 !important;
                            }
                            .screenshot {
                                cursor: pointer;
                                width: 100%;
                                height: auto;
                                border: 1px solid #444;
                            }
                            .modal {
                                display: none;
                                position: fixed;
                                z-index: 1000;
                                left: 0;
                                top: 0;
                                width: 100%;
                                height: 100%;
                                background-color: rgba(0,0,0,0.9);
                                justify-content: center;
                                align-items: center;
                            }
                            .modal-content {
                                max-width: 90%;
                                max-height: 90%;
                                border: 2px solid #444;
                            }
                            .close-btn {
                                position: absolute;
                                top: 20px;
                                right: 30px;
                                color: #fff;
                                font-size: 30px;
                                cursor: pointer;
                            }
                            .filter-btn {
                                margin: 5px;
                            }
                        </style>
                    </head>
                    <body>
                        <div class="container-lg mt-3" style="width: 100%;">
                            <h4><b>Bo<i class="fa fa-bolt" style="font-size:18px;animation-name:example; position:relative; animation-duration:2s; animation-iteration-count:infinite"></i>t</b></h4>
                            <h6 style="color: #ff4d4d">Test Automation Accelerator</h6>
                        </div>
                        <div class="container-lg mt-3 border" style="width: 100%; height:330px; background-color: #2c2c2c">
                            <div style="float: left">
                                <p><br>Test Run Date: <font style="color: #bb86fc"><b>$runDate </b></font><font style="color: #ff8f00"><b>$runTime</b></font></p>
                                <p>Total Run: <font style="color: #03a9f4"><b>$totalRun</b></font></p>
                                <p>Passed: <font style="color: #92D192"><b>$totalPassed</b></font></p>
                                <p>Failed: <font style="color: #D96F6F"><b>$totalFailed</b></font></p>
                                <p>Warning: <font style="color: #ff8f00"><b>$totalWarnings</b></font></p>
                                <p>Run Time: <font style="color: #d81b60"><b>$totalHrs</b></font> hrs <font style="color: #ff8f00"><b>$totalMins</b></font> mins <font style="color: #03a9f4"><b>$totalSec</b></font> sec</p>
                                <p>Test Suite: <font style="color: #f4d03f"><b>$testSuiteName</b></font></p>
                            </div>
                            <canvas id="myChart" style="max-width:300px; float: right; background-color: #2c2c2c; border-radius: 8px;"></canvas>
                        </div>
                        <div class="container-lg mt-3" style="width: 100%;">
                            <button id="showPass" class="btn btn-outline-success filter-btn">Show Pass</button>
                            <button id="showFail" class="btn btn-outline-danger filter-btn">Show Fail</button>
                            <button id="showAll" class="btn btn-outline-light filter-btn">Show All</button>
                        </div>
                        $testCaseSteps
                        <div id="imageModal" class="modal">
                            <span class="close-btn">×</span>
                            <img class="modal-content" id="modalImage">
                        </div>
                        <script>
                            var xValues = ["Pass", "Fail", "Warning"];
                            var yValues = [$totalPassed, $totalFailed, $totalWarnings];
                            var barColors = [
                                "#92D192",
                                "#D96F6F",
                                "#ff8f00"
                            ];
                            new Chart("myChart", {
                                type: "doughnut",
                                data: {
                                    labels: xValues,
                                    datasets: [{
                                        backgroundColor: barColors,
                                        data: yValues
                                    }]
                                },
                                options: {
                                    title: {
                                        display: true,
                                        text: "Test Run Graph Summary",
                                        color: "#e0e0e0"
                                    },
                                    legend: {
                                        labels: {
                                            fontColor: "#e0e0e0"
                                        }
                                    }
                                }
                            });
                        </script>
                        <script>
                            // Screenshot modal handling
                            const images = document.querySelectorAll('.screenshot');
                            const modal = document.getElementById('imageModal');
                            const modalImg = document.getElementById('modalImage');
                            const closeBtn = document.querySelector('.close-btn');
                            images.forEach(img => {
                                img.addEventListener('click', function() {
                                    modal.style.display = 'flex';
                                    modalImg.src = this.src;
                                });
                            });
                            closeBtn.addEventListener('click', function() {
                                modal.style.display = 'none';
                            });
                            modal.addEventListener('click', function(e) {
                                if (e.target === modal) {
                                    modal.style.display = 'none';
                                }
                            });

                            // Test case filtering and collapsing
                            const showPassBtn = document.getElementById('showPass');
                            const showFailBtn = document.getElementById('showFail');
                            const showAllBtn = document.getElementById('showAll');
                            const testCards = document.querySelectorAll('.card');

                            function collapseAllCards() {
                                testCards.forEach(card => {
                                    const collapseElement = card.querySelector('.collapse');
                                    collapseElement.classList.remove('show');
                                });
                            }

                            showPassBtn.addEventListener('click', function() {
                                collapseAllCards();
                                testCards.forEach(card => {
                                    const header = card.querySelector('.card-header');
                                    card.style.display = header.style.backgroundColor === 'rgb(146, 209, 146)' ? 'block' : 'none';
                                });
                            });

                            showFailBtn.addEventListener('click', function() {
                                collapseAllCards();
                                testCards.forEach(card => {
                                    const header = card.querySelector('.card-header');
                                    card.style.display = header.style.backgroundColor === 'rgb(217, 111, 111)' ? 'block' : 'none';
                                });
                            });

                            showAllBtn.addEventListener('click', function() {
                                collapseAllCards();
                                testCards.forEach(card => {
                                    card.style.display = 'block';
                                });
                            });

                            // Initialize with all test cases visible and collapsed
                            collapseAllCards();
                            testCards.forEach(card => {
                                card.style.display = 'block';
                            });
                        </script>
                    </body>
                </html>""".formatted(safeErrorMsg));
        htmlTestReportHardStop = html.toString();
        return htmlTestReportHardStop;
    }

    /**
     * Counts the number of test cases with the specified status.
     *
     * @param testRunStatus The status to count (e.g., "PASS", "FAIL", "WARNING").
     * @return The number of test cases with the given status.
     */
    public static int getTheStatusCount(String testRunStatus) {
        var count = 0;
        for (var testId : boltRunner.testResult.keySet()) {
            var value = boltRunner.testResult.get(testId).toString();
            if (value.equals(testRunStatus)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the total number of test cases run.
     *
     * @return The total number of test cases executed.
     */
    public static int getTotalRunCount() {
        var countRun = 0;
        if (constants.externalRun) {
            return arrTestId.size();
        }
        var rowCount = importDataFromExcelModel.getRowCount();
        for (var i = 0; i < rowCount; i++) {
            var run = (boolean) importDataFromExcelModel.getValueAt(i, 0);
            if (run) {
                countRun++;
            }
        }
        return countRun;
    }

    /**
     * Edits the template for a passed test step, including user-defined steps if present.
     *
     * @param trTemplate   The template string to edit.
     * @param trTestStepId The test step ID.
     * @param testDesc     The test step description.
     * @param trTestStep   The test step details.
     * @return The edited template with replaced placeholders.
     */
    public static String trTemplateEditStepPassed(String trTemplate, String trTestStepId, String testDesc,
            String trTestStep) {
        var editedTemplate = trTemplate;
        if (!boltRunner.userDefineSteps.isEmpty()) {
            var userSteps = new StringBuilder();
            for (var entrySteps : boltRunner.userDefineSteps.entrySet()) {
                var stepTemplate = entrySteps.getValue().contains("~take~screenshot~")
                        ? trTemplateScreenShotUserDefine
                        : trTemplateUserDefine;
                stepTemplate = stepTemplate.replace("$tableStatus", "table-info");
                stepTemplate = stepTemplate.replace("$testId", trTestStepId + "." + (entrySteps.getKey() + 1));
                stepTemplate = entrySteps.getValue().contains("~take~screenshot~")
                        ? stepTemplate.replace("$screenShotFilePath", entrySteps.getValue().split("~take~screenshot~")[1])
                        : stepTemplate.replace("$testStep", entrySteps.getValue());
                userSteps.append("\n").append(stepTemplate);
            }
            editedTemplate = editedTemplate.replace("$tableStatus", "table-success");
            editedTemplate = editedTemplate.replace("$testId", trTestStepId);
            editedTemplate = editedTemplate.replace("$testStep", trTestStep);
            editedTemplate = editedTemplate.replace("$testDuration", boltRunner.stepExecTimeInterval);
            editedTemplate = editedTemplate.replace("$testDesc", testDesc);
            editedTemplate += userSteps.toString();
        } else {
            editedTemplate = editedTemplate.replace("$tableStatus", "table-success");
            editedTemplate = editedTemplate.replace("$testId", trTestStepId);
            editedTemplate = editedTemplate.replace("$testStep", trTestStep);
            editedTemplate = editedTemplate.replace("$testDuration", boltRunner.stepExecTimeInterval);
            editedTemplate = editedTemplate.replace("$testDesc", testDesc);
        }
        return editedTemplate;
    }

    /**
     * Edits the template for a skipped test step.
     *
     * @param trTemplate   The template string to edit.
     * @param trTestStepId The test step ID.
     * @param trTestStep   The test step details.
     * @return The edited template with replaced placeholders.
     */
    public static String trTemplateEditStepSkipped(String trTemplate, String trTestStepId, String trTestStep) {
        var editedTemplate = trTemplate;
        editedTemplate = editedTemplate.replace("$tableStatus", "table-secondary");
        editedTemplate = editedTemplate.replace("$testId", trTestStepId);
        editedTemplate = editedTemplate.replace("$testStep", trTestStep);
        return editedTemplate;
    }

    /**
     * Edits the template for a test step with a keyword warning.
     *
     * @param trTemplate   The template string to edit.
     * @param trTestStepId The test step ID.
     * @param trTestStep   The test step details.
     * @return The edited template with replaced placeholders.
     */
    public static String trTemplateEditStepWarningKeyword(String trTemplate, String trTestStepId, String trTestStep) {
        var editedTemplate = trTemplate;
        editedTemplate = editedTemplate.replace("$tableStatus", "table-warning");
        editedTemplate = editedTemplate.replace("$testId", trTestStepId);
        editedTemplate = editedTemplate.replace("$testStep", trTestStep);
        return editedTemplate;
    }

    /**
     * Edits the template for a test step with an object warning.
     *
     * @param trTemplate   The template string to edit.
     * @param trTestStepId The test step ID.
     * @param trTestStep   The test step details.
     * @param stepError    The error message for the warning.
     * @return The edited template with replaced placeholders.
     */
    public static String trTemplateEditStepWarningObject(String trTemplate, String trTestStepId, String trTestStep,
            String stepError) {
        var editedTemplate = trTemplate;
        editedTemplate = editedTemplate.replace("$tableStatus", "table-warning");
        editedTemplate = editedTemplate.replace("$testId", trTestStepId);
        editedTemplate = editedTemplate.replace("$testStep", trTestStep);
        editedTemplate = editedTemplate.replace("$stepError", stepError);
        return editedTemplate;
    }

    /**
     * Edits the template for a test step with a screenshot.
     *
     * @param trTemplate   The template string to edit.
     * @param trTestStepId The test step ID.
     * @param trTestStep   The test step details.
     * @param trStepDesc   The test step description.
     * @param filePath     The screenshot file path.
     * @return The edited template with replaced placeholders.
     */
    public static String trTemplateEditScreenShot(String trTemplate, String trTestStepId, String trTestStep,
            String trStepDesc, String filePath) {
        var editedTemplate = trTemplate;
        editedTemplate = editedTemplate.replace("$tableStatus", "table-success");
        editedTemplate = editedTemplate.replace("$testId", trTestStepId);
        editedTemplate = editedTemplate.replace("$testStep", trTestStep);
        editedTemplate = editedTemplate.replace("$screenShotFilePath", filePath);
        editedTemplate = editedTemplate.replace("$testDuration", boltRunner.stepExecTimeInterval);
        editedTemplate = editedTemplate.replace("$testDesc", trStepDesc);
        return editedTemplate;
    }

    /**
     * Edits the template for a failed test step, including user-defined steps if present.
     *
     * @param trTemplate   The template string to edit.
     * @param trTestStepId The test step ID.
     * @param trTestStep   The test step details.
     * @param stepError    The error message for the failure.
     * @param filePath     The screenshot file path.
     * @return The edited template with replaced placeholders.
     */
    public static String trTemplateEditStepFailed(String trTemplate, String trTestStepId, String trTestStep,
            String stepError, String filePath) {
        var editedTemplate = trTemplate;
        if (!boltRunner.userDefineSteps.isEmpty()) {
            var userSteps = new StringBuilder();
            for (var entrySteps : boltRunner.userDefineSteps.entrySet()) {
                var stepTemplate = entrySteps.getValue().contains("~take~screenshot~")
                        ? trTemplateScreenShotUserDefine
                        : trTemplateUserDefine;
                stepTemplate = stepTemplate.replace("$tableStatus", "table-info");
                stepTemplate = stepTemplate.replace("$testId", trTestStepId + "." + (entrySteps.getKey() + 1));
                stepTemplate = entrySteps.getValue().contains("~take~screenshot~")
                        ? stepTemplate.replace("$screenShotFilePath", entrySteps.getValue().split("~take~screenshot~")[1])
                        : stepTemplate.replace("$testStep", entrySteps.getValue());
                userSteps.append("\n").append(stepTemplate);
            }
            editedTemplate = editedTemplate.replace("$tableStatus", "table-danger");
            editedTemplate = editedTemplate.replace("$testId", trTestStepId);
            editedTemplate = editedTemplate.replace("$testStep", trTestStep);
            editedTemplate = editedTemplate.replace("$stepError", stepError);
            editedTemplate = editedTemplate.replace("$screenShotFilePath", filePath);
            editedTemplate = editedTemplate.replace("$testDuration", boltRunner.stepExecTimeInterval);
            editedTemplate += userSteps.toString();
        } else {
            editedTemplate = editedTemplate.replace("$tableStatus", "table-danger");
            editedTemplate = editedTemplate.replace("$testId", trTestStepId);
            editedTemplate = editedTemplate.replace("$testStep", trTestStep);
            editedTemplate = editedTemplate.replace("$stepError", stepError);
            editedTemplate = editedTemplate.replace("$screenShotFilePath", filePath);
            editedTemplate = editedTemplate.replace("$testDuration", "");
        }
        return editedTemplate;
    }

    /**
     * Edits the card template for a test case, setting the background color based on the test result
     * and formatting the description with italics after the first closing bracket.
     *
     * @param trTemplate      The card template string to edit.
     * @param trTestResult    The test result ("PASS", "FAIL", "WARNING").
     * @param trTestId        The test case ID.
     * @param trTestDesc      The test case description.
     * @return The edited card template with replaced placeholders.
     */
    public static String trTemplateEditCard(String trTemplate, String trTestResult, String trTestId,
            String trTestDesc) {
        var backgroundColor = switch (trTestResult) {
            case "PASS" -> "#92D192"; // Soft green for PASS, uniform black text (#000000), lighter row and td separators (#6c757d)
            case "FAIL" -> "#D96F6F"; // Muted red for FAIL, uniform black text (#000000), lighter row and td separators (#6c757d)
            case "WARNING" -> "#ff8f00"; // Orange for WARNING, uniform black text (#000000), lighter row and td separators (#6c757d)
            default -> "#616161"; // Fallback, uniform black text (#000000), lighter row and td separators (#6c757d)
        };
        var editedTemplate = trTemplate;
        editedTemplate = editedTemplate.replace("$testResultColor", backgroundColor);
        editedTemplate = editedTemplate.replace("$collapseIndex", trTestId);

        // Split description at first ']' and wrap the rest in <i> tags
        var indexCount = trTestDesc.indexOf(']') + 1;
        if (indexCount > 0 && indexCount < trTestDesc.length()) {
            trTestDesc = trTestDesc.substring(0, indexCount) + "<i>" + trTestDesc.substring(indexCount) + "</i>";
        }
        editedTemplate = editedTemplate.replace("$testDescription", trTestDesc);
        return editedTemplate;
    }

    /**
     * Concatenates test steps from a LinkedHashMap with newlines, removing trailing newlines.
     *
     * @param trTestSteps The map of test steps.
     * @return The concatenated string of test steps.
     */
    public static String concatenateHashMapDataWithNewLine(LinkedHashMap<Integer, String> trTestSteps) {
        var trTSteps = new StringBuilder();
        for (var entry : trTestSteps.entrySet()) {
            trTSteps.append(entry.getValue()).append("\n");
        }
        return trTSteps.toString().replaceAll("[\\r\\n]+$", "");
    }

    /**
     * Retrieves a list of test suite names from the HTML report directory.
     *
     * @return A map of suite indices to suite names.
     */
    public static LinkedHashMap<Integer, String> getSuiteList() {
        var testSuties = new LinkedHashMap<Integer, String>();
        var suiteCount = 0;
        var getNewFileName = "";
        testReportFolderExist = true;
        testReportFolderEmpty = false;

        var folder = new File(htmlTestReportPath);
        if (!folder.exists()) {
            JOptionPane.showMessageDialog(null, "Required folder [htmlTestReport] not found!", "Alert",
                    JOptionPane.WARNING_MESSAGE);
            testReportFolderExist = false;
            return testSuties;
        }
        var listOfFiles = folder.listFiles();
        if (listOfFiles == null || listOfFiles.length == 0) {
            testReportFolderEmpty = true;
            return testSuties;
        }
        for (var file : listOfFiles) {
            if (file.isFile()) {
                var getFileName = file.getName().split("[~]")[0];
                if (!getFileName.equals(getNewFileName) && !getFileName.isEmpty()) {
                    getNewFileName = getFileName;
                    testSuties.put(++suiteCount, getNewFileName);
                }
            }
        }
        return testSuties;
    }

    /**
     * Retrieves a list of test case result suite names from the test results directory.
     *
     * @return A map of suite indices to suite names.
     */
    public static LinkedHashMap<Integer, String> getTestCaseResultSuiteList() {
        var testSuties = new LinkedHashMap<Integer, String>();
        var suiteCount = 0;
        var getNewFileName = "";
        testCaseReportFolderExist = true;
        testCaseReportFolderEmpty = false;

        var folder = new File(testCaseResultPath);
        if (!folder.exists()) {
            JOptionPane.showMessageDialog(null, "Required folder [testResults] not found!", "Alert",
                    JOptionPane.WARNING_MESSAGE);
            testCaseReportFolderExist = false;
            return testSuties;
        }
        var listOfFiles = folder.listFiles();
        if (listOfFiles == null || listOfFiles.length == 0) {
            testCaseReportFolderEmpty = true;
            return testSuties;
        }
        for (var file : listOfFiles) {
            if (file.isFile()) {
                var getFileName = file.getName().split("[~]")[0];
                if (!getFileName.equals(getNewFileName) && !getFileName.equals("~$") && !getFileName.isEmpty()) {
                    getNewFileName = getFileName;
                    testSuties.put(++suiteCount, getNewFileName);
                }
            }
        }
        if (testSuties.isEmpty()) {
            testCaseReportFolderEmpty = true;
        }
        return testSuties;
    }

    /**
     * Retrieves a map of test case result dates from the test results directory.
     *
     * @return A map of dates to empty strings, sorted by last modified time (descending).
     */
    @SuppressWarnings("null")
    public static LinkedHashMap<Date, String> getTestDateList() {
        var testCaseResultDate = new LinkedHashMap<Date, String>();
        testCaseReportFolderExist = true;
        testCaseReportFolderEmpty = false;

        var folder = new File(testCaseResultPath);
        if (!folder.exists()) {
            JOptionPane.showMessageDialog(null, "Required folder [testResults] not found!", "Alert",
                    JOptionPane.WARNING_MESSAGE);
            testCaseReportFolderExist = false;
            return testCaseResultDate;
        }
        var listOfFiles = folder.listFiles();
        if (listOfFiles == null || listOfFiles.length == 0) {
            testCaseReportFolderEmpty = true;
            return testCaseResultDate;
        }
        Arrays.sort(listOfFiles, Comparator.comparingLong(File::lastModified).reversed());
        for (var file : listOfFiles) {
            if (file.isFile()) {
                try {
                    var date = sdf.parse(sdf.format(file.lastModified()));
                    testCaseResultDate.put(date, "");
                } catch (ParseException ignored) {
                    // Safe to ignore as date parsing is based on file's last modified time
                }
            }
        }
        return testCaseResultDate;
    }

    /**
     * Builds a tree of HTML test reports for a given test suite.
     *
     * @param testSuiteName The name of the test suite.
     * @param htmlSuiteNode The parent node to add report nodes to.
     * @return The updated tree node with report file names and timestamps.
     */
    public static DefaultMutableTreeNode getHtmlTestReportList(String testSuiteName,
            DefaultMutableTreeNode htmlSuiteNode) {
        var folder = new File(htmlTestReportPath);
        var listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            Arrays.sort(listOfFiles, Comparator.comparingLong(File::lastModified).reversed());
            for (var file : listOfFiles) {
                if (file.isFile() && file.getName().split("[~]")[0].equals(testSuiteName)) {
                    var htmlSuiteNodeChild = new DefaultMutableTreeNode(
                            file.getName() + " (" + sdf1.format(file.lastModified()) + ")");
                    htmlSuiteNode.add(htmlSuiteNodeChild);
                }
            }
        }
        return htmlSuiteNode;
    }

    /**
     * Builds a tree of test case results for a given test suite, grouped by date.
     *
     * @param testSuiteName     The name of the test suite.
     * @param htmlTestResultNode The parent node to add result nodes to.
     * @return The updated tree node with result file names and timestamps, grouped by date.
     */
    public static DefaultMutableTreeNode getTestCaseResultList(String testSuiteName,
            DefaultMutableTreeNode htmlTestResultNode) {
        var folder = new File(testCaseResultPath);
        var listOfFiles = folder.listFiles();
        if (listOfFiles == null) {
            return htmlTestResultNode;
        }
        Arrays.sort(listOfFiles, Comparator.comparingLong(File::lastModified).reversed());
        DefaultMutableTreeNode testCaseResultDate = null;

        for (var file : listOfFiles) {
            if (!file.isFile() || !file.getName().split("[~]")[0].equals(testSuiteName)) {
                continue;
            }
            var dateStr = sdf.format(file.lastModified());
            // Find or create date node
            var dateNodeFound = false;
            for (var i = 0; i < htmlTestResultNode.getChildCount(); i++) {
                if (htmlTestResultNode.getChildAt(i).toString().equals(dateStr)) {
                    testCaseResultDate = (DefaultMutableTreeNode) htmlTestResultNode.getChildAt(i);
                    dateNodeFound = true;
                    break;
                }
            }
            if (!dateNodeFound) {
                testCaseResultDate = new DefaultMutableTreeNode(dateStr);
                htmlTestResultNode.add(testCaseResultDate);
            }
            var fileNode = new DefaultMutableTreeNode(
                    file.getName() + " (" + sdf1.format(file.lastModified()) + ")");
            testCaseResultDate.add(fileNode);
        }
        return htmlTestResultNode;
    }

    /**
     * Opens a file using the desktop's default application.
     *
     * @param filePath The path to the file to open.
     * @return True if the file was opened successfully, false otherwise.
     * @throws IOException If an error occurs while opening the file.
     */
    public static boolean openTheFileOverDesktop(String filePath) throws IOException {
        if (!Desktop.isDesktopSupported()) {
            return false;
        }
        var file = new File(filePath);
        if (!file.exists()) {
            return false;
        }
        try {
            Desktop.getDesktop().open(file);
            return true;
        } catch (IOException ex) {
            throw new IOException("Failed to open file: " + filePath, ex);
        }
    }
}