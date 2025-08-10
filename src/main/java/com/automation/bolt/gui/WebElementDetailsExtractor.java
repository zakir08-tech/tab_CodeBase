package com.automation.bolt.gui;

public interface WebElementDetailsExtractor {
    String extractElementDetails(String rawDetails);
    void storeInRepository(String elementId, String details);
    String getRepositoryDetails();
    void clearRepository();
}