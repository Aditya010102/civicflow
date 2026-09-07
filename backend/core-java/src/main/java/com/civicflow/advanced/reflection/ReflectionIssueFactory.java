package com.civicflow.advanced.reflection;

import com.civicflow.core.model.Issue;
import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;

import java.lang.reflect.Constructor;

public class ReflectionIssueFactory {

    public Issue createIssue()
            throws Exception {

        Constructor<Issue> constructor =
                Issue.class.getDeclaredConstructor(
                        long.class,
                        String.class,
                        String.class,
                        IssuePriority.class,
                        IssueStatus.class
                );

        return constructor.newInstance(
                101L,
                "Water Leakage",
                "Leak near Block B",
                IssuePriority.HIGH,
                IssueStatus.REPORTED
        );
    }
}