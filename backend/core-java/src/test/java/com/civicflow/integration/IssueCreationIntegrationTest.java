package com.civicflow.integration;

import com.civicflow.core.model.IssuePriority;
import com.civicflow.dto.CreateIssueRequest;
import com.civicflow.entity.IssueEntity;
import com.civicflow.event.IssueCreatedEvent;
import com.civicflow.repository.IssueRepository;
import com.civicflow.service.IssueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class IssueCreationIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16")
                    .withDatabaseName("civicflow_issue_test")
                    .withUsername("test")
                    .withPassword("test");

    @DynamicPropertySource
    static void configureDatabase(
            DynamicPropertyRegistry registry
    ) {

        registry.add(
                "spring.datasource.url",
                () -> postgres.getJdbcUrl()
                        + "&options=-c%20TimeZone%3DUTC"
        );

        registry.add(
                "spring.datasource.username",
                postgres::getUsername
        );

        registry.add(
                "spring.datasource.password",
                postgres::getPassword
        );

        registry.add(
                "spring.jpa.properties.hibernate.jdbc.time_zone",
                () -> "UTC"
        );
    }

    @Autowired
    private IssueService issueService;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private TestIssueCreatedEventListener testEventListener;

    @BeforeEach
    void cleanDatabase() {
        issueRepository.deleteAll();
    }

    @Test
    @WithMockUser(
            username = "citizen@test.com",
            roles = "CITIZEN"
    )
    void shouldCreateIssueInDatabase() {

        CreateIssueRequest request =
                new CreateIssueRequest(
                        "Broken street light",
                        "Street light is not working near the community park.",
                        IssuePriority.HIGH
                );

        var response =
                issueService.createIssue(request);

        assertNotNull(response);
        assertNotNull(response.getId());

        IssueEntity saved =
                issueRepository
                        .findById(response.getId())
                        .orElseThrow();

        assertEquals(
                "Broken street light",
                saved.getTitle()
        );

        assertEquals(
                IssuePriority.HIGH,
                saved.getPriority()
        );
    }

    @Test
    @WithMockUser(
            username = "citizen@test.com",
            roles = "CITIZEN"
    )
    void shouldPublishIssueCreatedEvent() {

        CreateIssueRequest request =
                new CreateIssueRequest(
                        "Water leakage",
                        "There is water leaking from the public pipeline.",
                        IssuePriority.CRITICAL
                );

        var response =
                issueService.createIssue(request);

        assertNotNull(response.getId());

        IssueCreatedEvent event =
                testEventListener.getLatestEvent();

        assertNotNull(event);

        assertEquals(
                response.getId(),
                event.issueId()
        );
    }
}