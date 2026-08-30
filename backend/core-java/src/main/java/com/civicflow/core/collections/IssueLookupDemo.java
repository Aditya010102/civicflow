package com.civicflow.core.collections;

import com.civicflow.core.model.Issue;
import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;

public class IssueLookupDemo {

    public static void main(String[] args) {

        IssueLookup lookup = new IssueLookup();

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

        lookup.add(issue1);
        lookup.add(issue2);

        System.out.println("Total issues: " + lookup.size());

        System.out.println("Contains 101: "
                + lookup.contains(101));

        System.out.println("Issue 101: "
                + lookup.findById(101));

        System.out.println("Issue 999: "
                + lookup.findById(999));
    }
}