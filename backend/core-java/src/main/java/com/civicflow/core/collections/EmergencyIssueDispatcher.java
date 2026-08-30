package com.civicflow.core.collections;

import com.civicflow.core.model.Issue;

import java.util.PriorityQueue;
import java.util.Queue;

public class EmergencyIssueDispatcher {

    private final Queue<Issue> queue =
            new PriorityQueue<>(
                    new IssuePriorityComparator()
            );

    public void addIssue(Issue issue) {
        queue.offer(issue);
    }

    public Issue dispatchNext() {
        return queue.poll();
    }

    public Issue peekNext() {
        return queue.peek();
    }

    public int size() {
        return queue.size();
    }
}