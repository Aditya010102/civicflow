package com.civicflow.advanced.annotations;

public class AnnotationDemo {

    @CriticalOperation(
            value = "Create civic issue",
            severity = 3
    )
    public void createIssue() {
        System.out.println("Creating civic issue...");
    }

    @CriticalOperation(
            value = "Resolve civic issue",
            severity = 2
    )
    public void resolveIssue() {
        System.out.println("Resolving civic issue...");
    }

    public void viewIssue() {
        System.out.println("Viewing civic issue...");
    }
}