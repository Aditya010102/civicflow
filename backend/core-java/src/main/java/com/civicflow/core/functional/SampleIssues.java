package com.civicflow.core.functional;

import com.civicflow.core.model.Issue;
import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;

import java.util.List;

public class SampleIssues {

    public static List<Issue> create() {

        return List.of(

                new Issue(
                        101,
                        "Water Leakage",
                        "Major water leakage",
                        IssuePriority.HIGH,
                        IssueStatus.REPORTED
                ),

                new Issue(
                        102,
                        "Power Failure",
                        "Entire block has no electricity",
                        IssuePriority.CRITICAL,
                        IssueStatus.IN_PROGRESS
                ),

                new Issue(
                        103,
                        "Garbage Overflow",
                        "Garbage collection required",
                        IssuePriority.LOW,
                        IssueStatus.REPORTED
                ),

                new Issue(
                        104,
                        "Road Damage",
                        "Large pothole",
                        IssuePriority.MEDIUM,
                        IssueStatus.IN_PROGRESS
                ),

                new Issue(
                        105,
                        "Hospital Power Failure",
                        "Emergency generator failed",
                        IssuePriority.CRITICAL,
                        IssueStatus.RESOLVED
                ),

                new Issue(
                        106,
                        "Street Light",
                        "Street light not working",
                        IssuePriority.LOW,
                        IssueStatus.RESOLVED
                )
        );
    }
}