package com.civicflow.auth;

import com.civicflow.dto.RegisterRequest;
import com.civicflow.dto.UserResponse;
import com.civicflow.entity.UserEntity;
import com.civicflow.exception.EmailAlreadyExistsException;
import com.civicflow.mapper.UserMapper;
import com.civicflow.repository.UserRepository;
import com.civicflow.security.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponse register(RegisterRequest request) {

        String normalizedEmail =
                request.getEmail().trim().toLowerCase();

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
}