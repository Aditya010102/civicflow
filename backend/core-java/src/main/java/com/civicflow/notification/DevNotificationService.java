package com.civicflow.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class DevNotificationService
        implements NotificationService {

    private static final Logger logger =
            LoggerFactory.getLogger(
                    DevNotificationService.class
            );

    @Override
    public void send(
            String recipient,
            String message
    ) {

        logger.info(
                "[DEV NOTIFICATION] recipient={}, message={}",
                recipient,
                message
        );
    }
}