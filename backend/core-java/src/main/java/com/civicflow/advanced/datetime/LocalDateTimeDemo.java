package com.civicflow.advanced.datetime;

import java.time.LocalDateTime;

public class LocalDateTimeDemo {

    public static void main(String[] args) {

        LocalDateTime createdAt =
                LocalDateTime.now();

        System.out.println(
                "Issue created at: " + createdAt
        );
    }
}