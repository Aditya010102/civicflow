package com.civicflow.core.collections;

import com.civicflow.core.model.Issue;
import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;

public class IssueRegistryDemo {

    public static void main(String[] args) {

        IssueRegistry registry = new IssueRegistry();

        Issue issue1 = new Issue(
                101,
                "Water Leakage",
                "Water is leaking near Block B",
                IssuePriority.HIGH,
                IssueStatus.REPORTED
        );

        Issue issue2 = new Issue(
                102,
                "Broken Street Light",
                "Street light is not working",
                IssuePriority.MEDIUM,
                IssueStatus.ASSIGNED
        );

        registry.add(issue1);
        registry.add(issue2);

        System.out.println("Total issues: " + registry.size());

        Issue found = registry.findById(102);

        System.out.println("Found: " + found);
    }
}