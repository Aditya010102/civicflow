package com.civicflow.security;

import com.civicflow.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    private final JwtProperties jwtProperties;
    private final SecretKey signingKey;

    public JwtService(JwtProperties jwtProperties) {

        this.jwtProperties = jwtProperties;

        this.signingKey =
                Keys.hmacShaKeyFor(
                        jwtProperties
                                .getSecret()
                                .getBytes(StandardCharsets.UTF_8)
                );
    }

    public String generateToken(UserEntity user) {

        Instant now = Instant.now();

        Instant expiration =
                now.plusMillis(
                        jwtProperties.getExpirationMs()
                );

        return Jwts.builder()
                .subject(user.getEmail())
                .claim(
                        "role",
                        user.getRole().name()
                )
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(signingKey)
                .compact();
    }
}