package com.civicflow.advanced.reflection;

import com.civicflow.core.model.Issue;

import java.lang.reflect.Constructor;

public class ConstructorInspectionDemo {

    public static void main(String[] args) {

        Constructor<?>[] constructors =
                Issue.class.getDeclaredConstructors();

        for (Constructor<?> constructor :
                constructors) {

            System.out.println(
                    "Constructor: "
                            + constructor
            );

            System.out.println(
                    "Parameter count: "
                            + constructor
                            .getParameterCount()
            );
        }
    }
}