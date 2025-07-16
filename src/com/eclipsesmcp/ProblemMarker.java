package com.eclipsesmcp;

public class ProblemMarker {
    private String description;
    private String resource;
    private int lineNumber;
    private String severity;

    public ProblemMarker(String description, String resource, int lineNumber, String severity) {
        this.description = description;
        this.resource = resource;
        this.lineNumber = lineNumber;
        this.severity = severity;
    }

    // Getters and setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}
