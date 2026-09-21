package com.civicflow.advanced.serialization;

import com.civicflow.core.model.IssuePriority;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Request payload for creating a civic issue"
)
public class CreateIssueRequest {
    @Schema(
            description = "Short title describing the civic issue",
            example = "Broken streetlight near main road"
    )
    private String title;
    @Schema(
            description = "Detailed description of the reported issue",
            example = "The streetlight has not been working for three days."
    )
    private String description;
    @Schema(
            description = "Priority assigned to the issue",
            example = "HIGH"
    )
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