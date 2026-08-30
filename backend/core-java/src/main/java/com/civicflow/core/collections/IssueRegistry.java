package com.civicflow.core.collections;

import com.civicflow.core.model.Issue;

import java.util.ArrayList;
import java.util.List;

public class IssueRegistry {

    private final List<Issue> issues = new ArrayList<>();

    public void add(Issue issue) {
        issues.add(issue);
    }
    public Issue findById(long id) {

        for (Issue issue : issues) {

            if (issue.getId() == id) {
                return issue;
            }
        }

        return null;
    }
    public int size() {
        return issues.size();
    }
}