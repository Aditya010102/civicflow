package com.civicflow.advanced.annotations;

import java.lang.reflect.Method;

public class CriticalOperationProcessor {

    public static void process(Class<?> clazz) {

        System.out.println("Scanning class: " + clazz.getSimpleName());

        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {

            if (method.isAnnotationPresent(CriticalOperation.class)) {

                CriticalOperation annotation =
                        method.getAnnotation(CriticalOperation.class);

                System.out.println("--------------------------------");
                System.out.println("Method: " + method.getName());
                System.out.println("Operation: " + annotation.value());
                System.out.println("Severity: " + annotation.severity());
            }
        }
    }
}