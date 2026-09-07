package com.civicflow.advanced.datetime;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatterDemo {

    public static void main(String[] args) {

        LocalDate date =
                LocalDate.of(2026, 9, 5);

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(
                        "dd-MM-yyyy"
                );

        String formatted =
                date.format(formatter);

        System.out.println(formatted);
    }
}