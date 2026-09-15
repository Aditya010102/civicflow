package com.civicflow.event;

public record IssueCreatedEvent(
        Long issueId,
        String recipient,
        String message
) {
}