package com.civicflow.service;

import com.civicflow.dto.LoginRequest;
import com.civicflow.dto.LoginResponse;
import com.civicflow.dto.RegisterRequest;
import com.civicflow.dto.UserResponse;
import com.civicflow.entity.UserEntity;
import com.civicflow.exception.EmailAlreadyExistsException;
import com.civicflow.mapper.UserMapper;
import com.civicflow.repository.UserRepository;
import com.civicflow.security.CivicFlowUserDetails;
import com.civicflow.security.JwtService;
import com.civicflow.security.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional
    public UserResponse register(RegisterRequest request) {

        String normalizedEmail =
                request.getEmail()
                        .trim()
                        .toLowerCase();

        if (userRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new EmailAlreadyExistsException(
                    normalizedEmail
            );
        }

        String passwordHash =
                passwordEncoder.encode(
                        request.getPassword()
                );

        UserEntity user =
                new UserEntity(
                        normalizedEmail,
                        passwordHash,
                        Role.CITIZEN
                );

        UserEntity savedUser =
                userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    public LoginResponse login(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail()
                                        .trim()
                                        .toLowerCase(),
                                request.getPassword()
                        )
                );

        CivicFlowUserDetails userDetails =
                (CivicFlowUserDetails)
                        authentication.getPrincipal();

        UserEntity user =
                userRepository
                        .findByEmailIgnoreCase(
                                userDetails.getUsername()
                        )
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "Authenticated user no longer exists"
                                )
                        );

        String token =
                jwtService.generateToken(user);

        return new LoginResponse(
                token,
                "Bearer"
        );
    }
}