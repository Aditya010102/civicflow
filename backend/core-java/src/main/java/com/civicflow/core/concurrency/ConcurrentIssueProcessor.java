package com.civicflow.core.concurrency;

import com.civicflow.core.model.Issue;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentIssueProcessor {

    private final ExecutorService executor =
            Executors.newFixedThreadPool(3);

    public void processAll(List<Issue> issues) {

        for (Issue issue : issues) {

            executor.submit(() -> {

                System.out.println(
                        "Processing issue #"
                                + issue.getId()
                                + " on "
                                + Thread.currentThread()
                                .getName()
                );
            });
        }
    }

    public void shutdown() {

        executor.shutdown();
    }
}