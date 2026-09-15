package com.civicflow.notification;

import java.util.concurrent.CompletableFuture;

public interface NotificationService {

    CompletableFuture<Boolean> send(
            String recipient,
            String message
    );
}