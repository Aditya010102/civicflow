package com.civicflow.security;

import com.civicflow.entity.UserEntity;
import com.civicflow.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CivicFlowUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepository;

    public CivicFlowUserDetailsService(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        UserEntity user =
                userRepository
                        .findByEmailIgnoreCase(username)
                        .orElseThrow(() ->
                                new UsernameNotFoundException(
                                        "User not found"
                                )
                        );

        return new CivicFlowUserDetails(user);
    }
}