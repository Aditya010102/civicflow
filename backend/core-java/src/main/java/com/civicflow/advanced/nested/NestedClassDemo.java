package com.civicflow.advanced.nested;

public class NestedClassDemo {

    private static String applicationName = "CivicFlow";

    static class Configuration {

        public void printApplicationName() {
            System.out.println(applicationName);
        }
    }

    public static void main(String[] args) {

        Configuration configuration =
                new Configuration();

        configuration.printApplicationName();
    }
}