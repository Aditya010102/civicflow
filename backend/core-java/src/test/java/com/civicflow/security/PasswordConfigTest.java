package com.civicflow.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class PasswordConfigTest {

    private final PasswordEncoder passwordEncoder =
            new PasswordConfig().passwordEncoder();

    @Test
    void shouldHashPassword() {

        String rawPassword = "CivicFlow@123";

        String hash =
                passwordEncoder.encode(rawPassword);

        assertNotNull(hash);
        assertNotEquals(rawPassword, hash);

        assertTrue(
                passwordEncoder.matches(
                        rawPassword,
                        hash
                )
        );

        assertFalse(
                passwordEncoder.matches(
                        "WrongPassword",
                        hash
                )
        );
    }

    @Test
    void samePasswordShouldProduceDifferentHashes() {

        String rawPassword = "CivicFlow@123";

        String hash1 =
                passwordEncoder.encode(rawPassword);

        String hash2 =
                passwordEncoder.encode(rawPassword);

        assertNotEquals(hash1, hash2);

        assertTrue(
                passwordEncoder.matches(
                        rawPassword,
                        hash1
                )
        );

        assertTrue(
                passwordEncoder.matches(
                        rawPassword,
                        hash2
                )
        );
    }
}