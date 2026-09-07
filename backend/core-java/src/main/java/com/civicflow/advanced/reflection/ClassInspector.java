package com.civicflow.advanced.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ClassInspector {

    public static void inspect(
            Class<?> clazz
    ) {

        System.out.println(
                "===== CLASS ====="
        );

        System.out.println(
                clazz.getName()
        );


        System.out.println(
                "\n===== FIELDS ====="
        );

        for (Field field :
                clazz.getDeclaredFields()) {

            System.out.println(
                    field.getType()
                            .getSimpleName()
                            + " "
                            + field.getName()
            );
        }


        System.out.println(
                "\n===== CONSTRUCTORS ====="
        );

        for (Constructor<?> constructor :
                clazz.getDeclaredConstructors()) {

            System.out.println(
                    constructor
            );
        }


        System.out.println(
                "\n===== METHODS ====="
        );

        for (Method method :
                clazz.getDeclaredMethods()) {

            System.out.println(
                    method.getName()
            );
        }
    }
}