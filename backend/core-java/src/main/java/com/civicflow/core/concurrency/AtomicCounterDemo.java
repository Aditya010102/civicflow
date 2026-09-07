package com.civicflow.core.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounterDemo {

    private static final AtomicInteger count =
            new AtomicInteger();

    public static void main(String[] args)
            throws InterruptedException {

        Thread thread1 =
                new Thread(() -> {

                    for (int i = 0; i < 100_000; i++) {
                        count.incrementAndGet();
                    }
                });

        Thread thread2 =
                new Thread(() -> {

                    for (int i = 0; i < 100_000; i++) {
                        count.incrementAndGet();
                    }
                });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(
                count.get()
        );
    }
}