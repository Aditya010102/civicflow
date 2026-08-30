package com.civicflow.core.collections;

import com.civicflow.core.model.Issue;
import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;

public class EmergencyIssueDispatcherDemo {

    public static void main(String[] args) {

        EmergencyIssueDispatcher dispatcher =
                new EmergencyIssueDispatcher();

        dispatcher.addIssue(new Issue(
                101,
                "Water Leakage",
                "Major water leakage",
                IssuePriority.HIGH,
                IssueStatus.REPORTED
        ));

        dispatcher.addIssue(new Issue(
                102,
                "Power Failure",
                "Entire block has no electricity",
                IssuePriority.CRITICAL,
                IssueStatus.REPORTED
        ));

        dispatcher.addIssue(new Issue(
                103,
                "Garbage Overflow",
                "Garbage collection required",
                IssuePriority.LOW,
                IssueStatus.REPORTED
        ));

        dispatcher.addIssue(new Issue(
                104,
                "Road Damage",
                "Large pothole",
                IssuePriority.MEDIUM,
                IssueStatus.REPORTED
        ));

        System.out.println(
                "Next issue: "
                        + dispatcher.peekNext()
        );

        System.out.println(
                "Dispatching: "
                        + dispatcher.dispatchNext()
        );

        System.out.println(
                "Dispatching: "
                        + dispatcher.dispatchNext()
        );

        System.out.println(
                "Dispatching: "
                        + dispatcher.dispatchNext()
        );

        System.out.println(
                "Dispatching: "
                        + dispatcher.dispatchNext()
        );
    }
}