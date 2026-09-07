package com.civicflow.core.functional;

import com.civicflow.core.model.Issue;

import java.util.ArrayList;
import java.util.List;

public class IssueFilter {

    public List<Issue> filter(
            List<Issue> issues,
            IssuePredicate predicate
    ) {

        List<Issue> result =
                new ArrayList<>();

        for (Issue issue : issues) {

            if (predicate.test(issue)) {
                result.add(issue);
            }
        }

        return result;
    }
}