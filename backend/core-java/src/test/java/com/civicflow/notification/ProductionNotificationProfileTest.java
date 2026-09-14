package com.civicflow.notification;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest
@ActiveProfiles("prod")
class ProductionNotificationProfileTest {

    @Autowired
    private NotificationService notificationService;

    @Test
    void prodProfileShouldUseProductionNotificationService() {

        assertInstanceOf(
                ProductionNotificationService.class,
                notificationService
        );
    }
}