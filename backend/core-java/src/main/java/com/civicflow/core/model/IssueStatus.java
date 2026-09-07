package com.civicflow.core.model;

public enum IssueStatus {

    REPORTED,
    ACKNOWLEDGED,
    ASSIGNED,
    IN_PROGRESS,
    RESOLVED,
    CLOSED;

    public boolean canTransitionTo(IssueStatus next) {

        return switch (this) {

            case REPORTED ->
                    next == ACKNOWLEDGED;

            case ACKNOWLEDGED ->
                    next == ASSIGNED;

            case ASSIGNED ->
                    next == IN_PROGRESS;

            case IN_PROGRESS ->
                    next == RESOLVED;

            case RESOLVED ->
                    next == CLOSED;

            case CLOSED ->
                    false;
        };
    }
}