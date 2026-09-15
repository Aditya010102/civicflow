package com.civicflow.notification;

import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("dev")
class NotificationProfileTest {

    @Autowired
    private NotificationService notificationService;

    @Test
    void devProfileShouldUseDevNotificationService() {

        Class<?> targetClass =
                AopProxyUtils.ultimateTargetClass(
                        notificationService
                );

        assertEquals(
                DevNotificationService.class,
                targetClass
        );
    }
}