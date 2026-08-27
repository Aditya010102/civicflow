package com.civicflow.core;

import com.civicflow.core.model.Issue;
import com.civicflow.core.model.IssuePriority;

public class Main {

    public static void main(String[] args) {

        Issue issue = new Issue(
                1,
                "Water Leakage",
                "Water is leaking near Block B",
                IssuePriority.HIGH
        );

        System.out.println(issue);
    }
}