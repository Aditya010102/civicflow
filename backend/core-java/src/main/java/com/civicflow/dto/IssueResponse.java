package com.civicflow.dto;

import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(
        description = "Response representation of a civic issue"
)
public class IssueResponse {

    @Schema(
            description = "Unique issue identifier",
            example = "15"
    )
    private Long id;

    @Schema(
            description = "Short title of the civic issue",
            example = "Broken streetlight near main road"
    )
    private String title;

    @Schema(
            description = "Detailed description of the civic issue",
            example =
                    "The streetlight has not been working "
                            + "for three days."
    )
    private String description;

    @Schema(
            description = "Priority assigned to the issue",
            example = "HIGH"
    )
    private IssuePriority priority;

    @Schema(
            description = "Current workflow status of the issue",
            example = "REPORTED"
    )
    private IssueStatus status;

    @Schema(
            description = "ID of the department assigned to the issue",
            example = "3"
    )
    private Long departmentId;

    @Schema(
            description = "Timestamp when the issue was created",
            example = "2026-09-22T10:30:00Z"
    )
    private Instant createdAt;

    @Schema(
            description = "Timestamp when the issue was last updated",
            example = "2026-09-22T11:15:00Z"
    )
    private Instant updatedAt;

    public IssueResponse(
            Long id,
            String title,
            String description,
            IssuePriority priority,
            IssueStatus status,
            Long departmentId,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.departmentId = departmentId;
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

    public Long getDepartmentId() {
        return departmentId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}