package com.civicflow.advanced.datetime;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class DurationPeriodDemo {

    public static void main(String[] args) {

        LocalDateTime created =
                LocalDateTime.of(
                        2026, 9, 5,
                        10, 0
                );

        LocalDateTime resolved =
                LocalDateTime.of(
                        2026, 9, 5,
                        13, 30
                );

        Duration resolutionTime =
                Duration.between(
                        created,
                        resolved
                );

        System.out.println(
                "Hours: "
                        + resolutionTime.toHours()
        );

        System.out.println(
                "Minutes: "
                        + resolutionTime.toMinutes()
        );

        LocalDate startDate =
                LocalDate.of(2026, 9, 5);

        LocalDate endDate =
                LocalDate.of(2026, 10, 10);

        Period period =
                Period.between(
                        startDate,
                        endDate
                );

        System.out.println(
                "Months: "
                        + period.getMonths()
        );

        System.out.println(
                "Days: "
                        + period.getDays()
        );
    }
}