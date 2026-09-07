package com.civicflow.advanced.annotations;

import java.lang.reflect.Method;

public class AnnotationInspectionDemo {

    public static void main(String[] args) throws Exception {

        Method method =
                AnnotationDemo.class.getDeclaredMethod("createIssue");

        boolean present =
                method.isAnnotationPresent(CriticalOperation.class);

        System.out.println("Annotation present: " + present);

        if (present) {

            CriticalOperation annotation =
                    method.getAnnotation(CriticalOperation.class);

            System.out.println("Operation: " + annotation.value());
            System.out.println("Severity: " + annotation.severity());
        }
    }
}