package com.civicflow.core.exceptions;

public class IssueNotFoundException
        extends RuntimeException {

    public IssueNotFoundException(String message) {
        super(message);
    }
}