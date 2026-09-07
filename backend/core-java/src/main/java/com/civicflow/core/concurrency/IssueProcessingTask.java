package com.civicflow.core.concurrency;

import com.civicflow.core.model.Issue;

public class IssueProcessingTask implements Runnable {

    private final Issue issue;

    public IssueProcessingTask(Issue issue) {
        this.issue = issue;
    }

    @Override
    public void run() {

        System.out.println(
                "Processing issue "
                        + issue.getId()
                        + " on "
                        + Thread.currentThread().getName()
        );
    }
}