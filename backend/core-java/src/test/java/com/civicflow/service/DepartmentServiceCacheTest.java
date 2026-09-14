package com.civicflow.service;

import com.civicflow.entity.DepartmentEntity;
import com.civicflow.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class DepartmentServiceCacheTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:16")
                    .withDatabaseName("civicflow_cache_test")
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
    private DepartmentService departmentService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void clearCache() {

        Cache cache =
                cacheManager.getCache("departments");

        if (cache != null) {
            cache.clear();
        }

        departmentRepository.deleteAll();
    }

    @Test
    void shouldCreateDepartmentsCache() {

        departmentRepository.save(
                new DepartmentEntity("Water")
        );

        List<?> firstResult =
                departmentService.getAllDepartments();

        assertNotNull(firstResult);
        assertEquals(1, firstResult.size());

        departmentRepository.deleteAll();

        List<?> secondResult =
                departmentService.getAllDepartments();

        assertNotNull(secondResult);
        assertEquals(1, secondResult.size());
    }
}