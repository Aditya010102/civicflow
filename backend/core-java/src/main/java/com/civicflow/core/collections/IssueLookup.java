package com.civicflow.core.collections;

import com.civicflow.core.model.Issue;

import java.util.HashMap;
import java.util.Map;

public class IssueLookup {

    private final Map<Long, Issue> issues = new HashMap<>();

    public void add(Issue issue) {
        issues.put(issue.getId(), issue);
    }

    public Issue findById(long id) {
        return issues.get(id);
    }

    public boolean contains(long id) {
        return issues.containsKey(id);
    }

    public int size() {
        return issues.size();
    }
}