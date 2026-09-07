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

    public boolean requiresImmediateAttention() {
        return this == HIGH
                || this == CRITICAL;
    }

    public int escalationHours() {
        return switch (this) {
            case LOW -> 72;
            case MEDIUM -> 48;
            case HIGH -> 24;
            case CRITICAL -> 4;
        };
    }
}