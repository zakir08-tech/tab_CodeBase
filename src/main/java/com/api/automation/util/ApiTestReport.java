package com.api.automation.util;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;

import com.api.automation.bolt.API_TestRunner;
import com.api.automation.bolt.loadAPITestRunner;
import com.automation.bolt.constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class ApiTestReport {
    private static final String HTML_TEST_REPORT_DIR = constants.userDir + "/htmlTestReport/";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy hh:mm a");
    private static final SimpleDateFormat FILE_TIMESTAMP_FORMAT = new SimpleDateFormat("dd-MMMM-yyyy hh-mm-ss a");
    
    private static final String BTN_SUCCESS = "btn btn-success btn-sm";
    private static final String BTN_DANGER = "btn btn-danger btn-sm";
    private static final String TEXT_SUCCESS = "text-success";
    private static final String TEXT_DANGER = "text-danger";

    private static String testAutomationReportPath;
    
    public static void generateApiTestReport() {
        ensureReportDirectoryExists();
        String reportTimestamp = FILE_TIMESTAMP_FORMAT.format(new Date()).toLowerCase().replaceAll(":", "-");
        testAutomationReportPath = HTML_TEST_REPORT_DIR + "BoltApiTestReport_" + reportTimestamp + ".html";

        ReportData reportData = new ReportData();
        processTestRunnerEntries(reportData);
        String htmlContent = buildHtmlReport(reportData);
        
        saveTestReport(htmlContent);
        openReportInBrowser();
    }

    private static void ensureReportDirectoryExists() {
        File reportDir = new File(HTML_TEST_REPORT_DIR);
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }
    }

    private static void processTestRunnerEntries(ReportData reportData) {
        for (Map.Entry<Object, HashMap<Object, Object>> entry : loadAPITestRunner.ApiTestRunnerMap.entrySet()) {
            TestCaseData testCase = processTestCase(entry);
            reportData.addTestCase(testCase);
        }
    }

    private static TestCaseData processTestCase(Map.Entry<Object, HashMap<Object, Object>> entry) {
        TestCaseData testCase = new TestCaseData();
        HashMap<Object, Object> testData = entry.getValue();
        
        testCase.runId = safeToString(testData.get("Run ID")).replaceAll("[.]", "_");
        testCase.request = safeToString(testData.get("Request"));
        testCase.requestUrl = safeToString(testData.get("Request URL"));
        testCase.expectedStatus = safeToString(testData.get("Expected Status"));
        testCase.actualStatus = safeToString(testData.get("Actual Status"));
        testCase.jsonResponse = safeToString(testData.get("JSON Response"));
        testCase.statusCodePhrase = safeToString(testData.get("Status Code Phrase"));
        testCase.testSummary = safeToString(testData.get("Test Summary"), "{no test summary available}");
        testCase.payloadType = safeToString(testData.get("Payload Type"));
        testCase.payload = safeToString(testData.get("Payload"));

        processJsonResponse(testCase, testData.get("Run ID"));
        processHeaders(testCase, testData.get("Run ID"));
        determineTestStatus(testCase);

        return testCase;
    }

    private static void processJsonResponse(TestCaseData testCase, Object runId) {
        HashMap<Object, List<Object>> jsonResponse = API_TestRunner.verifyJsonResponseAttributes.get(runId);
        if (jsonResponse != null) {
            for (Map.Entry<Object, List<Object>> jsonEntry : jsonResponse.entrySet()) {
                String expected = safeToString(jsonEntry.getValue().get(0)).toLowerCase();
                String actual = safeToString(jsonEntry.getValue().get(1)).toLowerCase();
                
                JsonTagData tagData = new JsonTagData();
                tagData.name = safeToString(jsonEntry.getKey());
                
                if (!"#.".equals(expected) && !"#.".equals(actual)) {
                    tagData.expectedValue = formatColoredText(expected, "green");
                    tagData.actualValue = formatColoredText(actual, expected.equals(actual) ? "green" : "red");
                    testCase.jsonTagStatus &= expected.equals(actual);
                } else {
                    tagData.expectedValue = formatColoredText("given tag not found in JSON response!", "red");
                    testCase.jsonTagStatus = false;
                }
                testCase.jsonTags.add(tagData);
            }
        }
    }

    private static void processHeaders(TestCaseData testCase, Object runId) {
        HashMap<Object, Object> headerMap = loadAPITestRunner.saveHeaderMap.get(runId);
        if (headerMap != null && !headerMap.isEmpty()) {
            StringBuilder headers = new StringBuilder();
            headerMap.forEach((key, value) -> headers.append(key).append(": ").append(value).append("\r\n"));
            testCase.headers = headers.toString();
        } else {
            testCase.headers = "No headers defined for the given API request";
        }
    }

    private static void determineTestStatus(TestCaseData testCase) {
        boolean isStatusMatch = testCase.expectedStatus.equals(testCase.actualStatus);
        testCase.buttonColor = isStatusMatch && testCase.jsonTagStatus ? BTN_SUCCESS : BTN_DANGER;
        testCase.textResponseColor = isStatusMatch && testCase.jsonTagStatus ? TEXT_SUCCESS : TEXT_DANGER;
        
        if (isStatusMatch && testCase.jsonTagStatus) {
            testCase.passCount++;
        } else {
            testCase.failCount++;
        }
    }

    private static String buildHtmlReport(ReportData reportData) {
        StringBuilder htmlTable = new StringBuilder();
        StringBuilder htmlDetails = new StringBuilder();

        for (TestCaseData testCase : reportData.testCases) {
            htmlTable.append(buildHtmlTableRow(testCase));
            htmlDetails.append(buildHtmlDetailsSection(testCase));
        }

        return String.join("",
            buildHtmlHeader(),
            buildHtmlBodyStart(reportData),
            buildHtmlBodyMiddle(htmlTable.toString(), htmlDetails.toString()),
            buildHtmlBodyEnd(reportData)
        );
    }

    private static String buildHtmlTableRow(TestCaseData testCase) {
        String status = testCase.passCount > 0 && testCase.failCount == 0 ? "pass" : "fail";
        return String.format(
            "<tr style=\"background-color: #2a2a2a;\" data-status=\"%s\">" +
            "<td><b style=\"color: #e0e0e0; font-size: 12px;\">%s</b></td>" +
            "<td><i style=\"color: #ffab91; font-size: 12px;\">%s</i></td>" +
            "<td><button type=\"button\" class=\"%s\" style=\"font-size: 10px; border-radius: 4px;\" data-bs-toggle=\"modal\" data-bs-target=\"#summaryModal\" onclick=\"scrollWin('%s')\" aria-label=\"View details for %s\"><b>%s</b></button></td>" +
            "<td data-bs-container=\"body\" data-bs-toggle=\"tooltip\" data-bs-placement=\"top\" title=\"Expected Status: %s\" style=\"color: #64b5f6; font-size: 12px;\">%s</td>" +
            "<td data-bs-toggle=\"tooltip\" data-bs-container=\"body\" title=\"%s\" style=\"color: %s; font-size: 12px;\"><b>%s</b></td>" +
            "</tr>",
            status, testCase.runId, testCase.testSummary, testCase.buttonColor, testCase.runId, 
            testCase.runId, testCase.request, testCase.expectedStatus, testCase.requestUrl, 
            testCase.statusCodePhrase, 
            testCase.textResponseColor.equals("text-success") ? "#2ecc71" : "#e74c3c", 
            testCase.actualStatus
        );
    }

    private static String buildHtmlDetailsSection(TestCaseData testCase) {
        String requestColor = testCase.buttonColor.equals(BTN_SUCCESS) ? "#2ecc71" : "#e74c3c";
        StringBuilder details = new StringBuilder();
        details.append(String.format(
            "<div id=\"demo%s\" style=\"display: none;\">",
            testCase.runId
        ));

        details.append(String.format("<p style=\"font-size: 12px;\"><b style=\"color: #bbdefb;\">Request:</b> <span style=\"color: %s;\">", requestColor))
               .append(testCase.request).append("</span></p>");
        details.append("<hr style=\"border-top: 2px solid #bbdefb; margin: 10px 0;\">");

        details.append("<p style=\"font-size: 12px;\"><b style=\"color: #ffab91;\">Request URL:</b> <span style=\"color: #fff44f;\">")
               .append(testCase.requestUrl).append("</span></p>");
        details.append("<hr style=\"border-top: 2px solid #bbdefb; margin: 10px 0;\">");

        details.append("<p style=\"font-size: 12px;\"><b style=\"color: #64b5f6;\">Request headers:</b></p><pre style=\"color: #fff44f; font-size: 12px;\">")
               .append(testCase.headers).append("</pre>");
        details.append("<hr style=\"border-top: 2px solid #bbdefb; margin: 10px 0;\">");

        if (testCase.payload != null && !testCase.payload.isEmpty()) {
            details.append(String.format("<p style=\"font-size: 12px;\"><b style=\"color: #2ecc71;\">JSON payload:</b>%s</p><pre style=\"color: #fff44f; font-size: 12px;\">%s</pre>",
                testCase.payloadType, testCase.payload));
            details.append("<hr style=\"border-top: 2px solid #bbdefb; margin: 10px 0;\">");
        }

        if (testCase.jsonResponse != null && !testCase.jsonResponse.isEmpty()) {
            details.append("<p style=\"font-size: 12px;\"><b style=\"color: #f06292;\">JSON response:</b></p><pre style=\"color: #fff44f; font-size: 12px;\">")
                   .append(testCase.jsonResponse).append("</pre>");
            details.append("<hr style=\"border-top: 2px solid #bbdefb; margin: 10px 0;\">");
        }

        if (!testCase.jsonTags.isEmpty()) {
            details.append("<p style=\"font-size: 12px;\"><b style=\"color: #f1c40f;\">JSON response tag(s) verification:</b></p><pre class=\"text-danger\" style=\"font-size: 12px;\">");
            testCase.jsonTags.forEach(tag -> 
                details.append(tag.name).append(":")
                       .append(tag.expectedValue)
                       .append(tag.actualValue != null ? tag.actualValue : "")
                       .append("\n")
            );
            details.append("</pre>");
        }

        details.append("</div>");
        return details.toString();
    }

    private static String buildHtmlHeader() {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <title>Bolt: API Test Report</title>
                <!-- Bootstrap 5.3.3 CDN -->
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
                <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
                <script src='https://kit.fontawesome.com/a076d05399.js'></script>
                <link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.7.0/css/all.css' integrity='sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ' crossorigin='anonymous'>
                <!-- Bootstrap Icons -->
                <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
                <!-- Chart.js for pie chart -->
                <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                <style>
                    body {
                        background-color: #212121;
                        color: #e0e0e0;
                    }
                    #header-container { 
                        display: flex; 
                        justify-content: flex-start; 
                        align-items: center; 
                        padding: 15px; 
                        margin-bottom: 20px; 
                        background-color: #1a1a1a; 
                        position: relative; 
                        min-height: 150px; 
                    }
                    .chart-container {
                        position: absolute;
                        top: 50%;
                        left: 50%;
                        transform: translate(-50%, -50%);
                    }
                    .status-wrapper {
                        position: absolute;
                        top: 10px;
                        right: 10px;
                    }
                    .pre-container {
                        position: relative;
                        background-color: #424242;
                    }
                    .modal-content {
                        background-color: #303030;
                        border-color: #424242;
                        color: #e0e0e0;
                    }
                    .modal-header, .modal-footer {
                        background-color: #1a1a1a;
                        border-color: #424242;
                    }
                    .modal-title {
                        font-family: Consolas;
                        color: #bbdefb;
                    }
                    .modal-body {
                        max-height: 60vh;
                        overflow-y: auto;
                        background-color: #303030;
                    }
                    .modal-dialog {
                        max-width: 800px;
                    }
                    .tooltip-inner { 
                        color: #212121; 
                        background-color: #fff44f; 
                    }
                    .tooltip-arrow::before { 
                        border-bottom-color: #fff44f !important; 
                    }
                    #scrollable { 
                        max-height: calc(100% - 50px); 
                        width: 100%; 
                        overflow-y: auto; 
                        border: 1px solid #424242; 
                    }
                    #myBtn {
                        display: none; 
                        position: fixed; 
                        bottom: 45px; 
                        right: 35px;
                        font-weight: bold; 
                        z-index: 99; 
                        font-size: 20px; 
                        border: none; 
                        outline: none; 
                        background-color: #d32f2f; 
                        color: #e0e0e0; 
                        cursor: pointer; 
                        padding: 5px; 
                        border-radius: 5px; 
                    }
                    #myBtn:hover { 
                        background-color: #0288d1; 
                    }
                    @keyframes example { 
                        0% {color: #d32f2f;} 
                        100% {color: #e0e0e0;} 
                    }
                    .header-title { 
                        font-family: Consolas; 
                        color: #bbdefb; 
                        font-size: 14px; 
                        margin-top: 10px; 
                    }
                    .header-title h3, .header-title p { 
                        display: block; 
                        margin: 5px 0; 
                        font-size: 18px; 
                    }
                    .header-title p { 
                        font-size: 12px; 
                    }
                    .status-container { 
                        font-family: Consolas; 
                        color: #bbdefb; 
                        text-align: right; 
                        font-size: 14px; 
                        margin-top: 10px; 
                    }
                    .status-container h3, .status-container p { 
                        display: block; 
                        margin: 5px 0; 
                        font-size: 18px; 
                    }
                    .status-container p { 
                        font-size: 12px; 
                    }
                    .bolt-title { 
                        font-size: 16px; 
                        font-family: Consolas; 
                        color: #bbdefb; 
                    }
                    .accelerator-title { 
                        font-size: 10px; 
                        font-family: Consolas; 
                        color: #ef5350; 
                    }
                    .table { 
                        font-family: Consolas; 
                        background-color: #2a2a2a; 
                        color: #e0e0e0; 
                        border-color: #424242;
                    }
                    .table thead th { 
                        background-color: #1a1a1a; 
                        color: #bbdefb; 
                        font-size: 14px; 
                        font-weight: bold; 
                        padding: 8px; 
                        border: 1px solid #424242; 
                    }
                    .table tbody tr { 
                        background-color: #2a2a2a !important; 
                        transition: background-color 0.2s; 
                    }
                    .table tbody tr:hover { 
                        background-color: #3a3a3a !important; 
                    }
                    .table tbody td { 
                        padding: 6px; 
                        border: 1px solid #424242; 
                        vertical-align: middle; 
                        color: #e0e0e0; 
                        background-color: #2a2a2a !important; 
                    }
                    .btn-sm { 
                        padding: 2px 8px; 
                    }
                    .close-btn, .nav-btn { 
                        background-color: #616161; 
                        color: #e0e0e0; 
                        border-radius: 4px; 
                    }
                    .close-btn { 
                        position: absolute; 
                        top: 10px; 
                        right: 10px; 
                        z-index: 10; 
                    }
                    .close-btn:hover, .nav-btn:hover { 
                        background-color: #757575; 
                    }
                    .filter-btn {
                        margin-right: 5px;
                        font-size: 10px;
                    }
                    /* Responsive design */
                    .table-responsive { 
                        overflow-x: auto; 
                    }
                    @media (max-width: 768px) {
                        .header-title h3, .status-container h3 { 
                            font-size: 16px; 
                        }
                        .header-title p, .status-container p { 
                            font-size: 10px; 
                        }
                        .table thead th, .table tbody td { 
                            font-size: 12px; 
                            padding: 4px; 
                            background-color: #2a2a2a !important; 
                        }
                        .table tbody tr { 
                            background-color: #2a2a2a !important; 
                        }
                        .table tbody tr:hover { 
                            background-color: #3a3a3a !important; 
                        }
                        #myBtn { 
                            font-size: 16px; 
                            padding: 3px; 
                        }
                        .modal-dialog {
                            max-width: 90%;
                        }
                        .modal-body {
                            max-height: 50vh;
                        }
                        .filter-btn {
                            font-size: 9px;
                            padding: 2px 6px;
                        }
                    }
                    @media (max-width: 576px) {
                        .header-title h3, .status-container h3 { 
                            font-size: 16px; 
                        }
                        .header-title p, .status-container p { 
                            font-size: 10px; 
                        }
                        .table thead th, .table tbody td { 
                            font-size: 10px; 
                            padding: 3px; 
                            background-color: #2a2a2a !important; 
                        }
                        .table tbody tr { 
                            background-color: #2a2a2a !important; 
                        }
                        .table tbody tr:hover { 
                            background-color: #3a3a3a !important; 
                        }
                        #myBtn { 
                            font-size: 14px; 
                            padding: 2px; 
                        }
                        #passFailChart { 
                            width: 80px; 
                            height: 80px; 
                            max-width: 80px; 
                            max-height: 80px; 
                        }
                        .modal-dialog {
                            max-width: 95%;
                        }
                        .modal-body {
                            max-height: 40vh;
                        }
                        .filter-btn {
                            font-size: 8px;
                            padding: 2px 5px;
                        }
                    }
                    /* Chart styling */
                    #passFailChart { 
                        width: 150px; 
                        height: 150px; 
                        max-width: 150px; 
                        max-height: 150px; 
                    }
                </style>
            </head>
        """;
    }

    private static String buildHtmlBodyStart(ReportData reportData) {
        return String.format("""
            <body>
                <button onclick="topFunction()" id="myBtn" class="btn btn-outline-secondary" aria-label="Scroll to top">
                    <i class="bi bi-arrow-up"></i>
                </button>
                <div class="container-fluid" id="containerFluid">
                    <div id="header-container">
                        <div>
                            <h4 class="bolt-title">
                                <b>Bolt <i class="fa fa-bolt" style="font-size:12px;animation-name:example;animation-duration:2s;animation-iteration-count:infinite"></i></b>
                                <br>
                                <span class="accelerator-title">API Test Accelerator</span>
                            </h4>
                            <div class="header-title">
                                <h3><strong>API Test Report</strong></h3>
                                <p>Dated: <span class="badge bg-warning text-dark">%s</span></p>
                                <p>Execution Run: <span class="badge bg-warning text-dark">%s</span></p>
                            </div>
                        </div>
                        <div class="status-wrapper">
                            <div class="status-container">
                                <h3><strong>Execution Status</strong></h3>
                                <p>Failed APIs: <span class="badge bg-danger">%d</span></p>
                                <p>Passed APIs: <span class="badge bg-success">%d</span></p>
                            </div>
                        </div>
                        <!-- Pass/Fail Chart -->
                        <div class="chart-container">
                            <canvas id="passFailChart"></canvas>
                        </div>
                    </div>
                    <div class="card bg-dark border-secondary" id="panelPanelPrimary">
                        <div style="padding: 10px;">
                            <button type="button" class="btn btn-success btn-sm filter-btn" onclick="filterTests('pass')" aria-label="Show passed test cases">Show Pass</button>
                            <button type="button" class="btn btn-danger btn-sm filter-btn" onclick="filterTests('fail')" aria-label="Show failed test cases">Show Fail</button>
                            <button type="button" class="btn btn-secondary btn-sm filter-btn" onclick="filterTests('all')" aria-label="Show all test cases">Show All</button>
                        </div>
                        <div class="card-body">
            """, DATE_FORMAT.format(new Date()), API_TestRunner.executionTime, reportData.totalFailCount, reportData.totalPassCount);
    }

    private static String buildHtmlBodyMiddle(String htmlTable, String htmlDetails) {
        return String.format("""
                            <div class="table-responsive" id="scrollable">
                                <table class="table table-bordered">
                                    <thead>
                                        <tr>
                                            <th>Run ID</th>
                                            <th>Test Description</th>
                                            <th>Request</th>
                                            <th>Request URL</th>
                                            <th>Response Code</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        %s
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="modal fade" id="summaryModal" tabindex="-1" aria-labelledby="summaryModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="summaryModalLabel">API Test Summary</h5>
                                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    %s
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary btn-sm nav-btn" onclick="prevRecord()" id="prevBtn" aria-label="Previous test case">
                                        <i class="bi bi-chevron-left"></i>
                                    </button>
                                    <button type="button" class="btn btn-secondary btn-sm nav-btn" onclick="nextRecord()" id="nextBtn" aria-label="Next test case">
                                        <i class="bi bi-chevron-right"></i>
                                    </button>
                                    <button type="button" class="btn btn-secondary btn-sm" data-bs-dismiss="modal" aria-label="Close">Close</button>
                                </div>
                            </div>
                        </div>
                    </div>
            """, htmlTable, htmlDetails);
    }

    private static String buildHtmlBodyEnd(ReportData reportData) {
        StringBuilder testCaseIds = new StringBuilder();
        StringBuilder testCaseDescriptions = new StringBuilder();
        StringBuilder testCaseStatuses = new StringBuilder();
        testCaseIds.append("[");
        testCaseDescriptions.append("[");
        testCaseStatuses.append("[");
        for (int i = 0; i < reportData.testCases.size(); i++) {
            TestCaseData testCase = reportData.testCases.get(i);
            String status = testCase.passCount > 0 && testCase.failCount == 0 ? "pass" : "fail";
            testCaseIds.append("\"").append(testCase.runId).append("\"");
            testCaseDescriptions.append("\"").append(testCase.testSummary.replace("\"", "\\\"")).append("\"");
            testCaseStatuses.append("\"").append(status).append("\"");
            if (i < reportData.testCases.size() - 1) {
                testCaseIds.append(",");
                testCaseDescriptions.append(",");
                testCaseStatuses.append(",");
            }
        }
        testCaseIds.append("]");
        testCaseDescriptions.append("]");
        testCaseStatuses.append("]");
        return String.format("""
                </div>
                <script>
                    $(document).ready(function(){
                        var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
                        tooltipTriggerList.forEach(function (tooltipTriggerEl) {
                            new bootstrap.Tooltip(tooltipTriggerEl);
                        });
                        console.log('Test case IDs:', %s);
                        console.log('Test case descriptions:', %s);
                        console.log('Test case statuses:', %s);
                        // Initialize Chart.js
                        var ctx = document.getElementById('passFailChart').getContext('2d');
                        new Chart(ctx, {
                            type: 'pie',
                            data: {
                                labels: ['Passed APIs', 'Failed APIs'],
                                datasets: [{
                                    data: [%d, %d],
                                    backgroundColor: ['#2ecc71', '#e74c3c'],
                                    borderColor: ['#27ae60', '#c0392b'],
                                    borderWidth: 1
                                }]
                            },
                            options: {
                                responsive: true,
                                maintainAspectRatio: true,
                                aspectRatio: 1,
                                plugins: {
                                    legend: {
                                        position: 'top',
                                        labels: {
                                            font: {
                                                size: 10,
                                                family: 'Consolas'
                                            },
                                            color: '#bbdefb'
                                        }
                                    },
                                    title: {
                                        display: true,
                                        text: 'Test Summary',
                                        color: '#bbdefb',
                                        font: {
                                            size: 12,
                                            family: 'Consolas'
                                        }
                                    },
                                    datalabels: {
                                        color: '#ffffff',
                                        formatter: (value, context) => {
                                            let sum = context.dataset.data.reduce((a, b) => a + b, 0);
                                            let percentage = sum ? Math.round((value / sum) * 100) + '%%' : '0%%';
                                            return percentage;
                                        },
                                        font: {
                                            size: 10,
                                            family: 'Consolas'
                                        }
                                    }
                                }
                            },
                            plugins: [{
                                id: 'datalabels'
                            }]
                        });
                        // Initialize Bootstrap modal
                        var summaryModal = new bootstrap.Modal(document.getElementById('summaryModal'), {
                            backdrop: 'static',
                            keyboard: false
                        });
                    });
                    var allTestCaseIds = %s;
                    var allTestCaseDescriptions = %s;
                    var allTestCaseStatuses = %s;
                    var filteredTestCaseIds = allTestCaseIds;
                    var filteredTestCaseDescriptions = allTestCaseDescriptions;
                    var currentRecordIndex = 0;
                    var currentFilter = 'all';
                    function updateFilteredTestCases(status) {
                        if (status === 'all') {
                            filteredTestCaseIds = allTestCaseIds;
                            filteredTestCaseDescriptions = allTestCaseDescriptions;
                        } else {
                            filteredTestCaseIds = [];
                            filteredTestCaseDescriptions = [];
                            for (var i = 0; i < allTestCaseIds.length; i++) {
                                if (allTestCaseStatuses[i] === status) {
                                    filteredTestCaseIds.push(allTestCaseIds[i]);
                                    filteredTestCaseDescriptions.push(allTestCaseDescriptions[i]);
                                }
                            }
                        }
                        currentRecordIndex = 0;
                    }
                    function scrollWin(runId) {
                        console.log('scrollWin called with runId:', runId, 'currentRecordIndex:', currentRecordIndex, 'currentFilter:', currentFilter);
                        currentRecordIndex = filteredTestCaseIds.indexOf(runId) >= 0 ? filteredTestCaseIds.indexOf(runId) : 0;
                        showRecord(currentRecordIndex);
                        var summaryModal = bootstrap.Modal.getOrCreateInstance(document.getElementById('summaryModal'));
                        summaryModal.show();
                    }
                    function showRecord(index) {
                        console.log('showRecord called with index:', index, 'currentFilter:', currentFilter);
                        for (var i = 0; i < allTestCaseIds.length; i++) {
                            var elem = document.getElementById('demo' + allTestCaseIds[i]);
                            if (elem) {
                                elem.style.display = filteredTestCaseIds.indexOf(allTestCaseIds[i]) === index ? 'block' : 'none';
                                elem.setAttribute('aria-expanded', filteredTestCaseIds.indexOf(allTestCaseIds[i]) === index ? 'true' : 'false');
                            }
                        }
                        document.getElementById('prevBtn').style.display = index > 0 ? 'block' : 'none';
                        document.getElementById('nextBtn').style.display = index < filteredTestCaseIds.length - 1 ? 'block' : 'none';
                        // Update modal title with Run ID and Test Description
                        var modalTitle = document.getElementById('summaryModalLabel');
                        modalTitle.innerHTML = 'API Test Summary - Run ID: ' + filteredTestCaseIds[index] + ' (' + filteredTestCaseDescriptions[index] + ')';
                        // Scroll to top of modal body
                        document.querySelector('.modal-body').scrollTop = 0;
                    }
                    function nextRecord() {
                        if (currentRecordIndex < filteredTestCaseIds.length - 1) {
                            currentRecordIndex++;
                            showRecord(currentRecordIndex);
                        }
                    }
                    function prevRecord() {
                        if (currentRecordIndex > 0) {
                            currentRecordIndex--;
                            showRecord(currentRecordIndex);
                        }
                    }
                    function closePanel() {
                        var summaryModal = bootstrap.Modal.getOrCreateInstance(document.getElementById('summaryModal'));
                        summaryModal.hide();
                        for (var i = 0; i < allTestCaseIds.length; i++) {
                            var elem = document.getElementById('demo' + allTestCaseIds[i]);
                            if (elem) {
                                elem.style.display = 'none';
                                elem.setAttribute('aria-expanded', 'false');
                            }
                        }
                        document.getElementById('prevBtn').style.display = 'none';
                        document.getElementById('nextBtn').style.display = 'none';
                    }
                    function filterTests(status) {
                        currentFilter = status;
                        var rows = document.querySelectorAll('table tbody tr');
                        rows.forEach(function(row) {
                            if (status === 'all') {
                                row.style.display = '';
                            } else {
                                row.style.display = row.getAttribute('data-status') === status ? '' : 'none';
                            }
                        });
                        updateFilteredTestCases(status);
                        // Reset modal if open
                        var summaryModal = bootstrap.Modal.getInstance(document.getElementById('summaryModal'));
                        if (summaryModal) {
                            closePanel();
                        }
                    }
                    window.onscroll = function() {scrollFunction()};
                    function scrollFunction() {
                        if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
                            document.getElementById("myBtn").style.display = "block";
                        } else {
                            document.getElementById("myBtn").style.display = "none";
                        }
                    }
                    function topFunction() {
                        document.body.scrollTop = 0;
                        document.documentElement.scrollTop = 0;
                    }
                    // Initialize filtered test cases
                    updateFilteredTestCases('all');
                </script>
            </body>
            </html>
        """, testCaseIds.toString(), testCaseDescriptions.toString(), testCaseStatuses.toString(), reportData.totalPassCount, reportData.totalFailCount, testCaseIds.toString(), testCaseDescriptions.toString(), testCaseStatuses.toString());
    }

    private static void saveTestReport(String htmlContent) {
        try (FileWriter fw = new FileWriter(testAutomationReportPath);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(htmlContent);
        } catch (IOException e) {
            System.err.println("Failed to save test report to " + testAutomationReportPath + ": " + e.getMessage());
            String tempDir = System.getProperty("java.io.tmpdir");
            testAutomationReportPath = tempDir + "Bolt_ApiTestReport_" + UUID.randomUUID() + ".html";
            try (FileWriter fw = new FileWriter(testAutomationReportPath);
                 BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(htmlContent);
            } catch (IOException ex) {
                throw new RuntimeException("Failed to save test report", ex);
            }
        }
    }

    private static void openReportInBrowser() {
        try {
            Desktop.getDesktop().browse(new File(testAutomationReportPath).toURI());
        } catch (IOException e) {
            throw new RuntimeException("Failed to open report in browser", e);
        }
    }

    public static String prettyPrintUsingGson(String uglyJson) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement jsonElement = JsonParser.parseString(uglyJson);
        return gson.toJson(jsonElement);
    }

    public static JSONObject jsonStatusObject(String expectedStatus, String actualStatus) {
        JSONObject jo = new JSONObject();
        jo.put("Expected Status", expectedStatus);
        try {
            jo.put("Actual Status", expectedStatus.equals(actualStatus) ? actualStatus : Integer.valueOf(actualStatus));
        } catch (NumberFormatException e) {
            jo.put("Actual Status", actualStatus);
        }
        return jo;
    }

    private static String safeToString(Object obj, String defaultValue) {
        return obj != null ? obj.toString() : defaultValue;
    }

    private static String safeToString(Object obj) {
        return safeToString(obj, "");
    }

    private static String formatColoredText(String text, String color) {
        return String.format("[<font color=\"%s\"><b>%s</b></font>]", color, text);
    }

    private static class ReportData {
        int totalPassCount = 0;
        int totalFailCount = 0;
        List<TestCaseData> testCases = new java.util.ArrayList<>();

        void addTestCase(TestCaseData testCase) {
            testCases.add(testCase);
            totalPassCount += testCase.passCount;
            totalFailCount += testCase.failCount;
        }
    }

    private static class TestCaseData {
        String runId;
        String request;
        String requestUrl;
        String expectedStatus;
        String actualStatus;
        String jsonResponse;
        String statusCodePhrase;
        String testSummary;
        String payloadType;
        String payload;
        String headers;
        String buttonColor;
        String textResponseColor;
        boolean jsonTagStatus = true;
        int passCount = 0;
        int failCount = 0;
        List<JsonTagData> jsonTags = new java.util.ArrayList<>();
    }

    private static class JsonTagData {
        String name;
        String expectedValue;
        String actualValue;
    }
}