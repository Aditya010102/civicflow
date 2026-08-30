package com.civicflow.core.collections;

import com.civicflow.core.model.Issue;

import java.util.HashSet;
import java.util.Set;

public class UniqueIssueTracker {

    private final Set<Issue> issues = new HashSet<>();

    public boolean add(Issue issue) {
        return issues.add(issue);
    }

    public int size() {
        return issues.size();
    }
}