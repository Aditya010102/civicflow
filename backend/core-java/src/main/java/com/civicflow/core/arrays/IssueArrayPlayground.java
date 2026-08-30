package com.civicflow.core.arrays;

import com.civicflow.core.model.IssuePriority;

public class IssueArrayPlayground {

    public static void main(String[] args) {

        IssuePriority[] priorities = {
                IssuePriority.LOW,
                IssuePriority.MEDIUM,
                IssuePriority.HIGH,
                IssuePriority.CRITICAL
        };

        for (int i = 0; i < priorities.length; i++) {

            System.out.println(
                    "Index " + i +
                            " = " + priorities[i]
            );
        }
    }
}