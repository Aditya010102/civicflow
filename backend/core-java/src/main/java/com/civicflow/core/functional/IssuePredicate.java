package com.civicflow.core.functional;

import com.civicflow.core.model.Issue;

@FunctionalInterface
public interface IssuePredicate {

    boolean test(Issue issue);
}