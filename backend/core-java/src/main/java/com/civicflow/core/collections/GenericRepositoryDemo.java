package com.civicflow.core.collections;

import com.civicflow.core.model.Issue;
import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;

public class GenericRepositoryDemo {

    public static void main(String[] args) {

        GenericRepository<Issue> repository =
                new GenericRepository<>();

        Issue issue = new Issue(
                101,
                "Water Leakage",
                "Water is leaking",
                IssuePriority.HIGH,
                IssueStatus.REPORTED
        );

        repository.add(issue);

        Issue retrievedIssue = repository.get(0);

        System.out.println(retrievedIssue);
    }
}