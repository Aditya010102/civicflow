package com.civicflow.core.functional;

import com.civicflow.core.model.Issue;
import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionalInterfacesDemo {

    public static void main(String[] args) {

        Issue issue = new Issue(
                101,
                "Power Failure",
                "Entire block has no electricity",
                IssuePriority.CRITICAL,
                IssueStatus.REPORTED
        );

        Predicate<Issue> isCritical =
                currentIssue ->
                        currentIssue.getPriority()
                                == IssuePriority.CRITICAL;

        Consumer<Issue> printer =
                currentIssue ->
                        System.out.println(
                                currentIssue
                        );

        Function<Issue, String> titleExtractor =
                currentIssue ->
                        currentIssue.getTitle();

        Supplier<String> systemName =
                () -> "CivicFlow";

        System.out.println(
                "Critical? "
                        + isCritical.test(issue)
        );

        printer.accept(issue);

        System.out.println(
                "Title: "
                        + titleExtractor.apply(issue)
        );

        System.out.println(
                "System: "
                        + systemName.get()
        );
    }
}