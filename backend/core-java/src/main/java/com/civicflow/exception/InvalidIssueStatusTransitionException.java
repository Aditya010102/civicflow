package com.civicflow.exception;

import com.civicflow.core.model.IssueStatus;

public class InvalidIssueStatusTransitionException
        extends RuntimeException {

    public InvalidIssueStatusTransitionException(
            IssueStatus currentStatus,
            IssueStatus requestedStatus
    ) {

        super(
                "Invalid issue status transition from "
                        + currentStatus
                        + " to "
                        + requestedStatus
        );
    }
}