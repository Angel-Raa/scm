/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.github.angel.scm.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.angel.scm.dto.request.Login;
import com.github.angel.scm.dto.request.Register;
import com.github.angel.scm.exception.ResourceAlreadyExistsException;
import com.github.angel.scm.persistence.entity.Role;
import com.github.angel.scm.persistence.entity.User;
import com.github.angel.scm.persistence.repository.RoleRepository;
import com.github.angel.scm.persistence.repository.UserRepository;
import com.github.angel.scm.service.AuthenticationService;
import com.github.angel.scm.utils.Authorities;

/**
 *
 * @author aguero
 */
@Transactional
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final String PROFILE_IMAGES = "https://img.freepik.com/premium-vector/vector-flat-illustration-grayscale-avatar-user-profile-person-icon-profile-picture-business-profile-woman-suitable-social-media-profiles-icons-screensavers-as-templatex9_719432-1328.jpg?w=826";
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CompromisedPasswordChecker compromisedPasswordChecker;

    public AuthenticationServiceImpl(UserRepository repository, RoleRepository roleRepository,
            BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, CompromisedPasswordChecker compromisedPasswordChecker) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.compromisedPasswordChecker = compromisedPasswordChecker;
    }

    @Override
    public void login(Login login) {
        String email = login.getEmail();
        String passwword = login.getPassword();

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, passwword);
        authentication = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    @Override
    public void register(Register register) {
        String email = register.getEmail();
        String password = register.getPassword();
        String phoneNumber = register.getPhoneNumber();
        String about = register.getAbout();
        User user = new User();
        Role role = new Role();
        if (compromisedPasswordChecker.check(password).isCompromised()) {
            throw new IllegalArgumentException("Password is compromised");
        }

        if(repository.existsByEmail(email)){
            throw new ResourceAlreadyExistsException("Email already exists");
        }
        
        role.setAuthorities(Authorities.USER);
        roleRepository.persist(role);
        user.setProfilePic(PROFILE_IMAGES);
        user.setEmail(email);
        user.setAbout(about);
        user.setPassword(passwordEncoder.encode(password));
        user.setPhoneNumber(phoneNumber);
        user.setRoleId(role.getRoleId());

        repository.persist(user);

    }

}
