package com.civicflow.core.exceptions;

public class IssueImportService {

    public void importLine(String line) {

        if (line == null || line.isBlank()) {

            throw new InvalidIssueException(
                    "Issue record cannot be empty"
            );
        }

        String[] parts = line.split("\\|");

        if (parts.length != 4) {

            throw new InvalidIssueException(
                    "Invalid issue format"
            );
        }

        long id;

        try {

            id = Long.parseLong(parts[0]);

        } catch (NumberFormatException exception) {

            throw new InvalidIssueException(
                    "Issue ID must be numeric"
            );
        }

        System.out.println(
                "Valid issue ID: " + id
        );
    }
}