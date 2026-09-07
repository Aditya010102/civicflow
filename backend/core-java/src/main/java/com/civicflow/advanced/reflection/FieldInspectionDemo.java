package com.civicflow.advanced.reflection;

import com.civicflow.core.model.Issue;

import java.lang.reflect.Field;

public class FieldInspectionDemo {

    public static void main(String[] args) {

        Class<Issue> clazz = Issue.class;

        Field[] fields =
                clazz.getDeclaredFields();

        for (Field field : fields) {

            System.out.println(
                    field.getName()
                            + " -> "
                            + field.getType()
            );
        }
    }
}