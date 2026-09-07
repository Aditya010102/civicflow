package com.civicflow.core.functional;

import com.civicflow.core.model.Issue;
import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;

public class IssueLambdaDemo {

    public static void main(String[] args) {

        Issue criticalIssue = new Issue(
                101,
                "Power Failure",
                "Entire block has no electricity",
                IssuePriority.CRITICAL,
                IssueStatus.REPORTED
        );

        IssuePredicate predicate =
                issue ->
                        issue.getPriority()
                                == IssuePriority.CRITICAL;

        boolean result =
                predicate.test(criticalIssue);

        System.out.println(result);
    }
}