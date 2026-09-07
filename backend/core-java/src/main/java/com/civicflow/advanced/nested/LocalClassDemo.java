package com.civicflow.advanced.nested;

public class LocalClassDemo {

    public void processIssue() {

        class IssueValidator {

            public boolean validate(String title) {
                return title != null
                        && !title.isBlank();
            }
        }

        IssueValidator validator =
                new IssueValidator();

        System.out.println(
                validator.validate("Broken streetlight")
        );
    }

    public static void main(String[] args) {

        LocalClassDemo demo =
                new LocalClassDemo();

        demo.processIssue();
    }
}