package com.civicflow.notification;

public interface NotificationService {

    void send(String recipient, String message);
}