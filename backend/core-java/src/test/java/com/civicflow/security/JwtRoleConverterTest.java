package com.civicflow.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtRoleConverterTest {

    private final JwtRoleConverter converter =
            new JwtRoleConverter();

    @Test
    void shouldConvertCitizenRole() {

        Jwt jwt = createJwt("CITIZEN");

        Collection<GrantedAuthority> authorities =
                converter.convert(jwt);

        assertEquals(1, authorities.size());

        assertEquals(
                "ROLE_CITIZEN",
                authorities.iterator()
                        .next()
                        .getAuthority()
        );
    }

    @Test
    void shouldConvertStaffRole() {

        Jwt jwt = createJwt("STAFF");

        Collection<GrantedAuthority> authorities =
                converter.convert(jwt);

        assertEquals(1, authorities.size());

        assertEquals(
                "ROLE_STAFF",
                authorities.iterator()
                        .next()
                        .getAuthority()
        );
    }

    @Test
    void shouldConvertAdminRole() {

        Jwt jwt = createJwt("ADMIN");

        Collection<GrantedAuthority> authorities =
                converter.convert(jwt);

        assertEquals(1, authorities.size());

        assertEquals(
                "ROLE_ADMIN",
                authorities.iterator()
                        .next()
                        .getAuthority()
        );
    }

    @Test
    void shouldReturnEmptyAuthoritiesWhenRoleMissing() {

        Jwt jwt =
                Jwt.withTokenValue("test-token")
                        .headers(headers ->
                                headers.put("alg", "none")
                        )
                        .subject("user@example.com")
                        .issuedAt(Instant.now())
                        .expiresAt(
                                Instant.now().plusSeconds(3600)
                        )
                        .build();

        Collection<GrantedAuthority> authorities =
                converter.convert(jwt);

        assertTrue(authorities.isEmpty());
    }

    private Jwt createJwt(String role) {

        return Jwt.withTokenValue("test-token")
                .headers(headers ->
                        headers.put("alg", "none")
                )
                .subject("user@example.com")
                .claim("role", role)
                .issuedAt(Instant.now())
                .expiresAt(
                        Instant.now().plusSeconds(3600)
                )
                .build();
    }
}