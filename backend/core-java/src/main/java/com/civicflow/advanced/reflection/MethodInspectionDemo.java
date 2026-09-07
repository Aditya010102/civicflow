package com.civicflow.advanced.reflection;

import com.civicflow.core.model.Issue;

import java.lang.reflect.Method;

public class MethodInspectionDemo {

    public static void main(String[] args) {

        Class<Issue> clazz = Issue.class;

        Method[] methods =
                clazz.getDeclaredMethods();

        for (Method method : methods) {

            System.out.println(
                    method.getName()
            );
        }
    }
}