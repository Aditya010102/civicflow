package com.civicflow.advanced.serialization;

import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;

public class IssueResponse {

    private long id;
    private String title;
    private String description;
    private IssuePriority priority;
    private IssueStatus status;

    public IssueResponse() {
    }

    public IssueResponse(
            long id,
            String title,
            String description,
            IssuePriority priority,
            IssueStatus status
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
    }

    public long getId() {
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
}