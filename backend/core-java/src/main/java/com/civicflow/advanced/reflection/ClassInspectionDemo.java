package com.civicflow.advanced.reflection;

import com.civicflow.core.model.Issue;

public class ClassInspectionDemo {

    public static void main(String[] args) {

        Class<Issue> clazz = Issue.class;

        System.out.println(
                "Simple name: "
                        + clazz.getSimpleName()
        );

        System.out.println(
                "Full name: "
                        + clazz.getName()
        );

        System.out.println(
                "Package: "
                        + clazz.getPackageName()
        );

        System.out.println(
                "Superclass: "
                        + clazz.getSuperclass()
        );
    }
}