package com.civicflow.controller;

import com.civicflow.dto.LoginRequest;
import com.civicflow.dto.LoginResponse;
import com.civicflow.dto.RegisterRequest;
import com.civicflow.dto.UserResponse;
import com.civicflow.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(
        name = "Authentication",
        description = "User registration and authentication operations"
)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Register a new user",
            description =
                    "Registers a new CivicFlow user. "
                            + "New users are assigned the CITIZEN role."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "User registered successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid registration data"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Email address already exists"
            )
    })
    public UserResponse register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return authService.register(request);
    }

    @PostMapping("/login")
    @Operation(
            summary = "Authenticate a user",
            description =
                    "Authenticates a user and returns a JWT access token."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Authentication successful"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid login request"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid credentials"
            )
    })
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request
    ) {
        return authService.login(request);
    }
}