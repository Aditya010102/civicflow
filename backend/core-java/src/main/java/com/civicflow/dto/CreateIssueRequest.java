package com.civicflow.dto;

import com.civicflow.core.model.IssuePriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateIssueRequest {

    @NotBlank(message = "Title is required")
    @Size(
            min = 5,
            max = 150,
            message = "Title must be between 5 and 150 characters"
    )
    private String title;

    @NotBlank(message = "Description is required")
    @Size(
            min = 10,
            max = 2000,
            message = "Description must be between 10 and 2000 characters"
    )
    private String description;

    @NotNull(message = "Priority is required")
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