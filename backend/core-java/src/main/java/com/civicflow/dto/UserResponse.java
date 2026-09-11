package com.civicflow.dto;

import com.civicflow.security.Role;

import java.time.Instant;

public class UserResponse {

    private Long id;
    private String email;
    private Role role;
    private boolean enabled;
    private Instant createdAt;

    public UserResponse() {
    }

    public UserResponse(
            Long id,
            String email,
            Role role,
            boolean enabled,
            Instant createdAt
    ) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.enabled = enabled;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}