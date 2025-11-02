package com.automation.bolt.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PostmanLikeWindow extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Method selection (GET, POST, etc.)
        ComboBox<String> methodCombo = new ComboBox<>();
        methodCombo.getItems().addAll("GET", "POST", "PUT", "DELETE", "PATCH");
        methodCombo.setValue("GET");

        // URL input
        TextField urlField = new TextField();
        urlField.setPromptText("Enter request URL");

        // Top HBox for method and URL
        HBox topBox = new HBox(10, methodCombo, urlField);
        topBox.setStyle("-fx-background-color: #2E2E2E; -fx-padding: 5px; -fx-spacing: 10px;");

        // Headers section
        GridPane headersGrid = new GridPane();
        headersGrid.add(new Label("Key"), 0, 0);
        headersGrid.add(new Label("Value"), 1, 0);
        TextField headerKey = new TextField();
        TextField headerValue = new TextField();
        headersGrid.add(headerKey, 0, 1);
        headersGrid.add(headerValue, 1, 1);
        headersGrid.setHgap(10);
        headersGrid.setVgap(5);
        headersGrid.setStyle("-fx-background-color: #3C3F41; -fx-padding: 5px;");

        // Params section
        GridPane paramsGrid = new GridPane();
        paramsGrid.add(new Label("Key"), 0, 0);
        paramsGrid.add(new Label("Value"), 1, 0);
        TextField paramKey = new TextField();
        TextField paramValue = new TextField();
        paramsGrid.add(paramKey, 0, 1);
        paramsGrid.add(paramValue, 1, 1);
        paramsGrid.setHgap(10);
        paramsGrid.setVgap(5);
        paramsGrid.setStyle("-fx-background-color: #3C3F41; -fx-padding: 5px;");

        // Body section
        TextArea bodyArea = new TextArea();
        bodyArea.setPromptText("Enter request body (e.g., JSON)");
        bodyArea.setStyle("-fx-background-color: #3C3F41; -fx-text-fill: white; -fx-padding: 5px;");
        bodyArea.setPrefHeight(150);

        // Response section
        TextArea responseArea = new TextArea();
        responseArea.setPromptText("Response will appear here");
        responseArea.setStyle("-fx-background-color: #3C3F41; -fx-text-fill: white; -fx-padding: 5px;");
        responseArea.setPrefHeight(150);

        // Send button
        Button sendButton = new Button("Send");
        sendButton.setStyle("-fx-background-color: #007ACC; -fx-text-fill: white; -fx-padding: 5px;");

        // Main layout
        VBox mainLayout = new VBox(10, topBox, headersGrid, paramsGrid, bodyArea, sendButton, responseArea);
        mainLayout.setStyle("-fx-background-color: #2E2E2E; -fx-padding: 10px;");

        // Scene and Stage
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setTitle("Postman-like API Tester");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}