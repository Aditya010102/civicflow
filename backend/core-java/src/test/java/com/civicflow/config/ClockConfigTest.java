package com.civicflow.config;

import java.time.Clock;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ClockConfigTest {

    @Autowired
    private Clock clock;

    @Test
    void shouldProvideApplicationClock() {

        assertNotNull(clock);

        assertEquals(
                ZoneOffset.UTC,
                clock.getZone()
        );
    }
}