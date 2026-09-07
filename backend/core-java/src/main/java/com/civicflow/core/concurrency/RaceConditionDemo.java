package com.civicflow.core.concurrency;

public class RaceConditionDemo {

    private static int count = 0;

    public static void main(String[] args)
            throws InterruptedException {

        Thread thread1 =
                new Thread(() -> {

                    for (int i = 0; i < 100_000; i++) {
                        count++;
                    }
                });

        Thread thread2 =
                new Thread(() -> {

                    for (int i = 0; i < 100_000; i++) {
                        count++;
                    }
                });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(
                "Final count = " + count
        );
    }
}