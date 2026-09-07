package com.civicflow.core.exceptions;

import com.civicflow.core.model.Issue;

public class IssueValidator {

    public static void validate(Issue issue) {

        if (issue == null) {

            throw new InvalidIssueException(
                    "Issue cannot be null"
            );
        }

        if (issue.getId() <= 0) {

            throw new InvalidIssueException(
                    "Issue ID must be positive"
            );
        }

        if (issue.getTitle() == null
                || issue.getTitle().isBlank()) {

            throw new InvalidIssueException(
                    "Issue title cannot be empty"
            );
        }

        if (issue.getDescription() == null
                || issue.getDescription().isBlank()) {

            throw new InvalidIssueException(
                    "Issue description cannot be empty"
            );
        }
    }
}