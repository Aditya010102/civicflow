package com.civicflow.core.functional;

import com.civicflow.core.model.Issue;
import com.civicflow.core.model.IssuePriority;

import java.util.List;

public class IssueStreamDemo {

    public static void main(String[] args) {

        List<Issue> issues =
                SampleIssues.create();

        issues.stream()
                .filter(issue ->
                        issue.getPriority()
                                == IssuePriority.CRITICAL)
                .forEach(System.out::println);
    }
}