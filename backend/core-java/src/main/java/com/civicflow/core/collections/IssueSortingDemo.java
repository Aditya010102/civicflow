package com.civicflow.core.collections;

import com.civicflow.core.model.Issue;
import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class IssueSortingDemo {

    public static void main(String[] args) {

        List<Issue> issues = new ArrayList<>();

        issues.add(new Issue(
                101,
                "Water Leakage",
                "Leak near Block B",
                IssuePriority.HIGH,
                IssueStatus.REPORTED
        ));

        issues.add(new Issue(
                102,
                "Broken Light",
                "Street light is broken",
                IssuePriority.CRITICAL,
                IssueStatus.REPORTED
        ));

        issues.add(new Issue(
                103,
                "Garbage Overflow",
                "Garbage bin is overflowing",
                IssuePriority.LOW,
                IssueStatus.REPORTED
        ));

        issues.add(new Issue(
                104,
                "Road Damage",
                "Large pothole",
                IssuePriority.MEDIUM,
                IssueStatus.REPORTED
        ));

        issues.sort(new IssuePriorityComparator());

        for (Issue issue : issues) {
            System.out.println(
                    issue.getId()
                            + " -> "
                            + issue.getPriority()
            );
        }
    }
}