package com.civicflow.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class DashboardCacheInvalidationTest {

    @Autowired
    private CacheManager cacheManager;

    @Test
    void dashboardCacheShouldExistWhenConfigured() {

        Cache cache =
                cacheManager.getCache("dashboard-summary");

        assertNotNull(cache);
    }
}