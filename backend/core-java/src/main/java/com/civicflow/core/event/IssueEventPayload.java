package com.civicflow.core.event;

public record IssueEventPayload(
        Long issueId,
        String title,
        String priority
) {
}