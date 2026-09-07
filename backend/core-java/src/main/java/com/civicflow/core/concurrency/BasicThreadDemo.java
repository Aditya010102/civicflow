package com.civicflow.core.concurrency;

public class BasicThreadDemo {

    public static void main(String[] args) {

        Thread thread = new Thread(() -> {

            System.out.println(
                    "Running in: "
                            + Thread.currentThread().getName()
            );
        });

        thread.start();

        System.out.println(
                "Main thread: "
                        + Thread.currentThread().getName()
        );
    }
}