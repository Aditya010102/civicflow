package com.civicflow.controller;

import com.civicflow.auth.AuthService;
import com.civicflow.dto.LoginRequest;
import com.civicflow.dto.RegisterRequest;
import com.civicflow.dto.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return authService.register(request);
    }
    @PostMapping("/login")
    public void login(
            @Valid @RequestBody LoginRequest request
    ) {
        authService.authenticate(request);
    }
}