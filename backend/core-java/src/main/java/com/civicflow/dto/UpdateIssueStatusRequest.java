package com.civicflow.dto;

import com.civicflow.core.model.IssueStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateIssueStatusRequest {

    @NotNull
    private IssueStatus status;

    public UpdateIssueStatusRequest() {
    }

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
    }
}