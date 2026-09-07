package com.civicflow.advanced.generics;

public class GenericDemo {

    public static void main(String[] args) {

        Box<String> stringBox =
                new Box<>();

        stringBox.set("CivicFlow");

        String value =
                stringBox.get();

        System.out.println(value);


        Box<Integer> integerBox =
                new Box<>();

        integerBox.set(100);

        Integer number =
                integerBox.get();

        System.out.println(number);
    }
}