package com.civicflow.advanced.datetime;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeZoneDemo {

    public static void main(String[] args) {

        ZonedDateTime india =
                ZonedDateTime.now(
                        ZoneId.of("Asia/Kolkata")
                );

        ZonedDateTime london =
                ZonedDateTime.now(
                        ZoneId.of("Europe/London")
                );

        System.out.println(
                "India: " + india
        );

        System.out.println(
                "London: " + london
        );
    }
}