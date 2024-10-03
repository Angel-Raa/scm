package com.github.angel.scm.service.impl;

import com.github.angel.scm.persistence.entity.Profile;
import com.github.angel.scm.persistence.repository.ProfileRepository;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final ProfileRepository repository;

    public CustomUserDetailsService(ProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Profile usersDetails = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found Email"));
        
                return User.builder()
                .username(usersDetails.getEmail())
                .password(usersDetails.getPassword())
                .roles(usersDetails.getRole().getAuthorities().name())
                .authorities(usersDetails.getAuthorities())
                .build();
    }
}
