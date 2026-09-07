package com.civicflow.core.concurrency;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureDemo {

    public static void main(String[] args)
            throws Exception {

        CompletableFuture<String> future =
                CompletableFuture.supplyAsync(() -> {

                    return "Issue processed";

                });

        System.out.println(
                future.get()
        );
    }
}