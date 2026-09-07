package com.civicflow.core.concurrency;

import com.civicflow.core.functional.SampleIssues;

public class ConcurrentIssueProcessorDemo {

    public static void main(String[] args)
            throws InterruptedException {

        ConcurrentIssueProcessor processor =
                new ConcurrentIssueProcessor();

        processor.processAll(
                SampleIssues.create()
        );

        processor.shutdown();
    }
}