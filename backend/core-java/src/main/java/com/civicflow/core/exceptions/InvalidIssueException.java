package com.civicflow.core.exceptions;

public class InvalidIssueException
        extends RuntimeException {

    public InvalidIssueException(String message) {
        super(message);
    }

    public InvalidIssueException(
            String message,
            Throwable cause
    ) {
        super(message, cause);
    }
}