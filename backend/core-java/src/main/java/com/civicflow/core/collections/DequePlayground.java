package com.civicflow.core.collections;

import java.util.ArrayDeque;
import java.util.Deque;

public class DequePlayground {

    public static void main(String[] args) {

        Deque<String> deque =
                new ArrayDeque<>();

        deque.addLast("A");
        deque.addLast("B");
        deque.addLast("C");

        System.out.println(deque);

        System.out.println(
                "First: " + deque.peekFirst()
        );

        System.out.println(
                "Last: " + deque.peekLast()
        );

        System.out.println(
                "Remove first: "
                        + deque.pollFirst()
        );

        System.out.println(
                "Remove last: "
                        + deque.pollLast()
        );

        System.out.println(deque);
    }
}