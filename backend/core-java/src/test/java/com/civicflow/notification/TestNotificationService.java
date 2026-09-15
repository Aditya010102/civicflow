package com.civicflow.notification;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Profile("test")
public class TestNotificationService
        implements NotificationService {

    @Override
    public CompletableFuture<Boolean> send(
            String recipient,
            String message
    ) {
        return CompletableFuture.completedFuture(true);
    }
}