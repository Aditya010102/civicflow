package com.civicflow.dto;

import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;

import java.time.Instant;

public class IssueResponse {

    private Long id;
    private String title;
    private String description;
    private IssuePriority priority;
    private IssueStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    public IssueResponse(
            Long id,
            String title,
            String description,
            IssuePriority priority,
            IssueStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public IssuePriority getPriority() {
        return priority;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}