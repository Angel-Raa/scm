package com.github.angel.scm.service.impl;

import com.github.angel.scm.persistence.entity.Profile;
import com.github.angel.scm.persistence.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {
    @Mock
    private ProfileRepository repository;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;
    @Test
    void testLoadUserByUsername_Success() {
        // Given

        String email = "test@example.com";
        Profile profile = new Profile();
        profile.setEmail(email);
        profile.setPassword("password");
        when(repository.findByEmail(email)).thenReturn(Optional.of(profile));

        // When
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // Then
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsername_NotFound() {
        // Given
        String email = "test@example.com";
        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(email));
    }

}