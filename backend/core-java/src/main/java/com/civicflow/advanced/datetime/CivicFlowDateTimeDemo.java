package com.civicflow.advanced.datetime;

import com.civicflow.core.model.IssuePriority;

import java.time.Instant;

public class CivicFlowDateTimeDemo {

    public static void main(String[] args) {

        Instant createdAt =
                Instant.now();

        CivicFlowDeadlineCalculator calculator =
                new CivicFlowDeadlineCalculator();

        Instant deadline =
                calculator.calculateDeadline(
                        createdAt,
                        IssuePriority.CRITICAL
                );

        System.out.println(
                "Created at: " + createdAt
        );

        System.out.println(
                "Deadline: " + deadline
        );
    }
}