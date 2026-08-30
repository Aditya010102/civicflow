package com.civicflow.core.model;

public enum IssuePriority {

    LOW(1),
    MEDIUM(2),
    HIGH(3),
    CRITICAL(4);

    private final int severity;

    IssuePriority(int severity) {
        this.severity = severity;
    }

    public int getSeverity() {
        return severity;
    }
}