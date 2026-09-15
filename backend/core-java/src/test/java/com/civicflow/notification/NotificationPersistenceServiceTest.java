package com.civicflow.notification;

import com.civicflow.entity.NotificationEntity;
import com.civicflow.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class NotificationPersistenceServiceTest {

    @Test
    void shouldCreatePendingNotification() {

        NotificationRepository repository =
                Mockito.mock(
                        NotificationRepository.class
                );

        NotificationEntity notification =
                new NotificationEntity(
                        100L,
                        "citizen@example.com",
                        "Issue created"
                );

        Mockito.when(
                repository.save(
                        Mockito.any(
                                NotificationEntity.class
                        )
                )
        ).thenReturn(notification);

        NotificationPersistenceService service =
                new NotificationPersistenceService(
                        repository
                );

        NotificationEntity result =
                service.createPending(
                        100L,
                        "citizen@example.com",
                        "Issue created"
                );

        assertNotNull(result);

        Mockito.verify(repository)
                .save(
                        Mockito.any(
                                NotificationEntity.class
                        )
                );
    }
}