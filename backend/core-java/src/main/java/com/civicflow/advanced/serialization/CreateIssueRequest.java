package com.civicflow.advanced.serialization;

import com.civicflow.core.model.IssuePriority;

public class CreateIssueRequest {

    private String title;
    private String description;
    private IssuePriority priority;

    public CreateIssueRequest() {
    }

    public CreateIssueRequest(
            String title,
            String description,
            IssuePriority priority
    ) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IssuePriority getPriority() {
        return priority;
    }

    public void setPriority(IssuePriority priority) {
        this.priority = priority;
    }
}