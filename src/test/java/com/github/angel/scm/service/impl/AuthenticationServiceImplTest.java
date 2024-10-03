package com.github.angel.scm.service.impl;

import com.github.angel.scm.dto.request.Login;
import com.github.angel.scm.dto.request.Register;
import com.github.angel.scm.persistence.entity.Profile;
import com.github.angel.scm.persistence.entity.Role;
import com.github.angel.scm.persistence.repository.RoleRepository;
import com.github.angel.scm.persistence.repository.ProfileRepository;
import com.github.angel.scm.utils.Authorities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private CompromisedPasswordChecker compromisedPasswordChecker;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testLogin() {
        // Given
        Login login = new Login();
        login.setEmail("test@example.com");
        login.setPassword("password");

        Authentication authentication = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());
        Authentication authenticated = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());

        when(authenticationManager.authenticate(authentication)).thenReturn(authenticated);

        // When
        authenticationService.login(login);

        // Then
        assertEquals(authenticated, SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testRegister() {
        // Given
        Register register = new Register();
        register.setEmail("test@example.com");
        register.setPassword("password");
        register.setPhoneNumber("1234567890");
        register.setAbout("Test user");

        Role role = new Role();
        role.setRoleId(UUID.randomUUID());
        role.setAuthorities(Authorities.USER);

        when(roleRepository.persist(any(Role.class))).thenReturn(role);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // When
        authenticationService.register(register);

        // Then
        verify(profileRepository, times(1)).persist(any(Profile.class));
        verify(roleRepository, times(1)).persist(any(Role.class));
    }

    @Test
    void testRegisterWhenEmailAlreadyExists() {
        // Given
        Register register = new Register();
        register.setEmail("test@example.com");
        register.setPassword("password");
        register.setPhoneNumber("1234567890");
        register.setAbout("Test user");


        when(profileRepository.existsByEmail(register.getEmail())).thenReturn(true);

        // When & Then
        assertThrows(RuntimeException.class, () -> authenticationService.register(register));
        verify(profileRepository, never()).persist(any(Profile.class));
    }

    @Test
    void testRegisterWhenPasswordIsCompromised() {
        // Given
        Register register = new Register();
        register.setEmail("test@example.com");
        register.setPassword("password");
        register.setPhoneNumber("1234567890");
        register.setAbout("Test user");


        // When & Then
        assertThrows(IllegalArgumentException.class, () -> authenticationService.register(register));
        verify(profileRepository, never()).persist(any(Profile.class));
    }
}