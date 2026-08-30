package com.civicflow.core.collections;

import com.civicflow.core.model.Issue;
import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;

public class UniqueIssueTrackerDemo {

    public static void main(String[] args) {

        UniqueIssueTracker tracker = new UniqueIssueTracker();

        Issue issue1 = new Issue(
                101,
                "Water Leakage",
                "Water is leaking near Block B",
                IssuePriority.HIGH,
                IssueStatus.REPORTED
        );

        Issue issue2 = new Issue(
                101,
                "Water Leakage",
                "Water is leaking near Block B",
                IssuePriority.HIGH,
                IssueStatus.REPORTED
        );

        System.out.println(tracker.add(issue1));

        System.out.println(tracker.add(issue2));

        System.out.println("Total: " + tracker.size());
    }
}