package com.civicflow;

import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;
import com.civicflow.entity.IssueEntity;
import com.civicflow.repository.IssueRepository;
import com.civicflow.specification.IssueSpecifications;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.data.jpa.domain.Specification;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
class IssuePersistenceIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16")
                    .withDatabaseName("civicflow_test")
                    .withUsername("civicflow")
                    .withPassword("civicflow");

    @DynamicPropertySource
    static void configureDatabase(
            DynamicPropertyRegistry registry
    ) {

        registry.add(
                "spring.datasource.url",
                postgres::getJdbcUrl
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
                "spring.jpa.hibernate.ddl-auto",
                () -> "create-drop"
        );

        registry.add(
                "spring.jpa.properties.hibernate.dialect",
                () -> "org.hibernate.dialect.PostgreSQLDialect"
        );
    }

    @Autowired
    private IssueRepository issueRepository;
    @BeforeEach
    void cleanDatabase() {
        issueRepository.deleteAll();
        issueRepository.flush();
    }
    // =========================================================
    // 1. PostgreSQL container
    // =========================================================

    @Test
    void postgresContainerShouldBeRunning() {

        assertTrue(postgres.isRunning());
    }

    // =========================================================
    // 2. Persist an Issue
    // =========================================================

    @Test
    void shouldPersistIssue() {

        IssueEntity issue = new IssueEntity(
                "Broken streetlight",
                "Streetlight near the college is not working.",
                IssuePriority.HIGH,
                IssueStatus.REPORTED
        );

        IssueEntity saved =
                issueRepository.saveAndFlush(issue);

        assertNotNull(saved.getId());

        assertEquals(
                "Broken streetlight",
                saved.getTitle()
        );

        assertEquals(
                IssuePriority.HIGH,
                saved.getPriority()
        );

        assertEquals(
                IssueStatus.REPORTED,
                saved.getStatus()
        );
    }

    // =========================================================
    // 3. Find Issue by ID
    // =========================================================

    @Test
    void shouldFindIssueById() {

        IssueEntity issue = new IssueEntity(
                "Water leakage",
                "Water is leaking from the main pipeline.",
                IssuePriority.CRITICAL,
                IssueStatus.REPORTED
        );

        IssueEntity saved =
                issueRepository.saveAndFlush(issue);

        IssueEntity found =
                issueRepository.findById(saved.getId())
                        .orElseThrow();

        assertEquals(
                saved.getId(),
                found.getId()
        );

        assertEquals(
                "Water leakage",
                found.getTitle()
        );
    }

    // =========================================================
    // 4. Update Issue
    // =========================================================

    @Test
    void shouldUpdateIssueStatus() {

        IssueEntity issue = new IssueEntity(
                "Broken road",
                "Large pothole near the market.",
                IssuePriority.MEDIUM,
                IssueStatus.REPORTED
        );

        IssueEntity saved =
                issueRepository.saveAndFlush(issue);

        saved.updateStatus(
                IssueStatus.ACKNOWLEDGED
        );

        issueRepository.saveAndFlush(saved);

        IssueEntity updated =
                issueRepository.findById(saved.getId())
                        .orElseThrow();

        assertEquals(
                IssueStatus.ACKNOWLEDGED,
                updated.getStatus()
        );
    }

    // =========================================================
    // 5. Pagination
    // =========================================================

    @Test
    void shouldSupportPagination() {

        for (int i = 1; i <= 25; i++) {

            IssueEntity issue = new IssueEntity(
                    "Issue number " + i,
                    "This is test civic issue number " + i,
                    IssuePriority.MEDIUM,
                    IssueStatus.REPORTED
            );

            issueRepository.save(issue);
        }

        issueRepository.flush();

        Pageable pageable =
                PageRequest.of(
                        0,
                        10,
                        Sort.by(
                                Sort.Direction.ASC,
                                "id"
                        )
                );

        Page<IssueEntity> page =
                issueRepository.findAll(pageable);

        assertEquals(
                10,
                page.getNumberOfElements()
        );

        assertEquals(
                25,
                page.getTotalElements()
        );

        assertEquals(
                3,
                page.getTotalPages()
        );
    }

    // =========================================================
    // 6. Filter by Status
    // =========================================================

    @Test
    void shouldFilterIssuesByStatus() {

        issueRepository.save(
                new IssueEntity(
                        "Reported issue",
                        "A newly reported civic issue.",
                        IssuePriority.LOW,
                        IssueStatus.REPORTED
                )
        );

        issueRepository.save(
                new IssueEntity(
                        "Assigned issue",
                        "An issue already assigned to staff.",
                        IssuePriority.MEDIUM,
                        IssueStatus.ASSIGNED
                )
        );

        issueRepository.flush();

        Specification<IssueEntity> specification =
                IssueSpecifications.hasStatus(
                        IssueStatus.REPORTED
                );

        Page<IssueEntity> result =
                issueRepository.findAll(
                        specification,
                        PageRequest.of(0, 20)
                );

        assertEquals(
                1,
                result.getTotalElements()
        );

        assertTrue(
                result.getContent()
                        .stream()
                        .allMatch(
                                issue ->
                                        issue.getStatus()
                                                == IssueStatus.REPORTED
                        )
        );
    }

    // =========================================================
    // 7. Filter by Priority
    // =========================================================

    @Test
    void shouldFilterIssuesByPriority() {

        issueRepository.save(
                new IssueEntity(
                        "Critical water problem",
                        "Major water supply problem.",
                        IssuePriority.CRITICAL,
                        IssueStatus.REPORTED
                )
        );

        issueRepository.save(
                new IssueEntity(
                        "Small road issue",
                        "Small pothole on the road.",
                        IssuePriority.LOW,
                        IssueStatus.REPORTED
                )
        );

        issueRepository.flush();

        Specification<IssueEntity> specification =
                IssueSpecifications.hasPriority(
                        IssuePriority.CRITICAL
                );

        Page<IssueEntity> result =
                issueRepository.findAll(
                        specification,
                        PageRequest.of(0, 20)
                );

        assertEquals(
                1,
                result.getTotalElements()
        );

        assertTrue(
                result.getContent()
                        .stream()
                        .allMatch(
                                issue ->
                                        issue.getPriority()
                                                == IssuePriority.CRITICAL
                        )
        );
    }

    // =========================================================
    // 8. Filter by Department ID
    // =========================================================

    @Test
    void shouldFilterIssuesByDepartmentId() {

        IssueEntity waterIssue =
                new IssueEntity(
                        "Water leakage",
                        "Pipeline leakage near residential area.",
                        IssuePriority.HIGH,
                        IssueStatus.REPORTED
                );

        waterIssue.assignDepartment(10L);

        IssueEntity roadIssue =
                new IssueEntity(
                        "Broken road",
                        "Road requires immediate repair.",
                        IssuePriority.MEDIUM,
                        IssueStatus.ASSIGNED
                );

        roadIssue.assignDepartment(20L);

        issueRepository.save(waterIssue);
        issueRepository.save(roadIssue);

        issueRepository.flush();

        Specification<IssueEntity> specification =
                IssueSpecifications.belongsToDepartment(
                        10L
                );

        Page<IssueEntity> result =
                issueRepository.findAll(
                        specification,
                        PageRequest.of(0, 20)
                );

        assertTrue(
                result.getContent()
                        .stream()
                        .allMatch(
                                issue ->
                                        issue.getDepartmentId()
                                                .equals(10L)
                        )
        );
    }

    // =========================================================
    // 9. Search title / description
    // =========================================================

    @Test
    void shouldSearchTitleAndDescription() {

        issueRepository.save(
                new IssueEntity(
                        "Broken streetlight",
                        "Streetlight is not working.",
                        IssuePriority.HIGH,
                        IssueStatus.REPORTED
                )
        );

        issueRepository.save(
                new IssueEntity(
                        "Water pipeline",
                        "Pipeline has developed a leakage.",
                        IssuePriority.MEDIUM,
                        IssueStatus.REPORTED
                )
        );

        issueRepository.flush();

        Specification<IssueEntity> specification =
                IssueSpecifications
                        .titleOrDescriptionContains(
                                "streetlight"
                        );

        Page<IssueEntity> result =
                issueRepository.findAll(
                        specification,
                        PageRequest.of(0, 20)
                );

        assertEquals(
                1,
                result.getTotalElements()
        );

        assertEquals(
                "Broken streetlight",
                result.getContent()
                        .get(0)
                        .getTitle()
        );
    }

    // =========================================================
    // 10. Optimistic locking version
    // =========================================================

    @Test
    void shouldInitializeOptimisticLockingVersion() {

        IssueEntity issue = new IssueEntity(
                "Version test issue",
                "Testing optimistic locking version.",
                IssuePriority.MEDIUM,
                IssueStatus.REPORTED
        );

        IssueEntity saved =
                issueRepository.saveAndFlush(issue);

        assertNotNull(
                saved.getVersion()
        );

        assertEquals(
                0L,
                saved.getVersion()
        );
    }
}