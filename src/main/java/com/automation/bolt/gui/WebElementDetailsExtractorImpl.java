package com.automation.bolt.gui;

import java.util.HashMap;
import java.util.Map;

public class WebElementDetailsExtractorImpl implements WebElementDetailsExtractor {
    private Map<String, String> repository = new HashMap<>();

    @Override
    public String extractElementDetails(String rawDetails) {
        System.out.println("Raw details: " + (rawDetails != null ? rawDetails.replace("\n", "\\n") : "null"));
        if (rawDetails == null || rawDetails.trim().isEmpty() || rawDetails.equals("null")) {
            return "No valid element details parsed";
        }

        try {
            // Remove curly braces and clean up
            String cleaned = rawDetails.replace("{", "").replace("}", "").replace("\"", "").trim();
            // Split on commas only between key-value pairs
            String[] pairs = cleaned.split(",\\s*(?=\\w+:)");
            StringBuilder formatted = new StringBuilder();
            for (String pair : pairs) {
                // Split on first colon to separate key and value
                String[] keyValue = pair.split(":", 2);
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();
                    formatted.append(key).append(":").append(value).append("\n");
                }
            }
            String result = formatted.toString();
            return result.isEmpty() ? "No valid element details parsed" : result;
        } catch (Exception e) {
            System.out.println("Parsing error: " + e.getMessage());
            return "No valid element details parsed: " + e.getMessage();
        }
    }

    @Override
    public void storeInRepository(String elementId, String details) {
        repository.put(elementId, details);
    }

    @Override
    public String getRepositoryDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("Object Repository:\n");
        for (Map.Entry<String, String> entry : repository.entrySet()) {
            sb.append("ID: ").append(entry.getKey()).append("\n");
            sb.append("Element Details:\n").append(entry.getValue()).append("\n\n");
        }
        return sb.toString();
    }

    @Override
    public void clearRepository() {
        repository.clear();
    }
}