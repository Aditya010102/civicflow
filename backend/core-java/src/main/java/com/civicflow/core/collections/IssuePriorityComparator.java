package com.civicflow.core.collections;

import com.civicflow.core.model.Issue;

import java.util.Comparator;

public class IssuePriorityComparator
        implements Comparator<Issue> {

    @Override
    public int compare(Issue first, Issue second) {

        return Integer.compare(
                second.getPriority().getSeverity(),
                first.getPriority().getSeverity()
        );
    }
}