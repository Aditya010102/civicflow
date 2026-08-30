package com.civicflow.core.strings;

public class StringPlayground {

    public static void main(String[] args) {

        String first = "CivicFlow";

        String second = "CivicFlow";

        String third = new String("CivicFlow");

        System.out.println(first == second);

        System.out.println(first == third);

        System.out.println(first.equals(third));
    }
}