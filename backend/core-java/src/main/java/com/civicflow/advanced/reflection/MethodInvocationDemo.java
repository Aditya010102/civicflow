package com.civicflow.advanced.reflection;

import com.civicflow.core.model.Issue;
import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;

import java.lang.reflect.Method;

public class MethodInvocationDemo {

    public static void main(String[] args)
            throws Exception {

        Issue issue = new Issue(
                101,
                "Water Leakage",
                "Leak near Block B",
                IssuePriority.HIGH,
                IssueStatus.REPORTED
        );

        Method method =
                Issue.class.getDeclaredMethod(
                        "getTitle"
                );

        Object result =
                method.invoke(issue);

        System.out.println(result);
    }
}