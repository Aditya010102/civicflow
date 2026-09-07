package com.civicflow.core.functional;

import com.civicflow.core.model.Issue;

public class IssueAnalyticsDemo {

    public static void main(String[] args) {

        IssueAnalyticsEngine analytics =
                new IssueAnalyticsEngine(
                        SampleIssues.create()
                );

        System.out.println(
                "Critical Issues:"
        );

        analytics.findCriticalIssues()
                .forEach(System.out::println);

        System.out.println(
                "\nOpen Critical Issues:"
        );

        analytics.findOpenCriticalIssues()
                .forEach(System.out::println);

        System.out.println(
                "\nCount By Status:"
        );

        System.out.println(
                analytics.countByStatus()
        );

        System.out.println(
                "\nCount By Priority:"
        );

        System.out.println(
                analytics.countByPriority()
        );

        System.out.println(
                "\nResolved Count: "
                        + analytics.countResolvedIssues()
        );

        System.out.println(
                "\nFind Issue 102:"
        );

        analytics.findById(102)
                .ifPresent(
                        System.out::println
                );
    }
}