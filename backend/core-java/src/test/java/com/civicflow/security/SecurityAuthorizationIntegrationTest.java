package com.civicflow.security;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
class SecurityAuthorizationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void anonymousUserShouldReceive401() throws Exception {

        mockMvc.perform(
                        get("/api/issues")
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void citizenShouldAccessIssues() throws Exception {

        mockMvc.perform(
                        get("/api/issues")
                                .with(
                                        jwt()
                                                .authorities(
                                                        new SimpleGrantedAuthority(
                                                                "ROLE_CITIZEN"
                                                        )
                                                )
                                )
                )
                .andExpect(status().isOk());
    }

    @Test
    void citizenShouldNotDeleteIssue() throws Exception {

        mockMvc.perform(
                        delete("/api/issues/1")
                                .with(
                                        jwt()
                                                .authorities(
                                                        new SimpleGrantedAuthority(
                                                                "ROLE_CITIZEN"
                                                        )
                                                )
                                )
                )
                .andExpect(status().isForbidden());
    }

    @Test
    void staffShouldNotDeleteIssue() throws Exception {

        mockMvc.perform(
                        delete("/api/issues/1")
                                .with(
                                        jwt()
                                                .authorities(
                                                        new SimpleGrantedAuthority(
                                                                "ROLE_STAFF"
                                                        )
                                                )
                                )
                )
                .andExpect(status().isForbidden());
    }

    @Test
    void staffShouldAccessStatusEndpoint() throws Exception {

        mockMvc.perform(
                        put("/api/issues/1/status")
                                .contentType("application/json")
                                .content("""
                                {
                                  "status": "ACKNOWLEDGED"
                                }
                                """)
                                .with(
                                        jwt()
                                                .authorities(
                                                        new SimpleGrantedAuthority(
                                                                "ROLE_STAFF"
                                                        )
                                                )
                                )
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void adminShouldBeAllowedToDelete() throws Exception {

        mockMvc.perform(
                        delete("/api/issues/999999")
                                .with(
                                        jwt()
                                                .authorities(
                                                        new SimpleGrantedAuthority(
                                                                "ROLE_ADMIN"
                                                        )
                                                )
                                )
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void registrationShouldBePublic() throws Exception {

        String email =
                "user-" + System.nanoTime() + "@example.com";

        mockMvc.perform(
                        post("/api/auth/register")
                                .contentType("application/json")
                                .content("""
                                {
                                  "email": "%s",
                                  "password": "Password@123"
                                }
                                """.formatted(email))
                )
                .andExpect(status().isCreated());
    }
}