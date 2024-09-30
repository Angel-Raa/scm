package com.github.angel.scm.service.impl;

import com.github.angel.scm.persistence.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    public CustomUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.github.angel.scm.persistence.entity.User usersDetails = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found Email"));
        return org.springframework.security.core.userdetails.User.builder()

                .username(usersDetails.getEmail())
                .password(usersDetails.getPassword())
                .roles(usersDetails.getRole().getAuthorities().name())
                .authorities(usersDetails.getAuthorities())
                .build();
    }
}
