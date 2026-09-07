package com.civicflow.advanced.reflection;

import com.civicflow.core.model.Issue;

public class ClassInspectorDemo {

    public static void main(String[] args) {

        ClassInspector.inspect(
                Issue.class
        );
    }
}