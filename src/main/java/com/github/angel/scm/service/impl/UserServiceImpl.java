/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.github.angel.scm.service.impl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.angel.scm.dto.request.Profile;
import com.github.angel.scm.dto.response.UserDTO;
import com.github.angel.scm.exception.ResourceNotFoundException;
import com.github.angel.scm.persistence.repository.UserRepository;
import com.github.angel.scm.service.UserService;

/**
 *
 * @author aguero
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDTO getUserById(@NonNull UUID userId) throws ResourceNotFoundException {
        return repository.findByUserIdDto(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

    }

    @Transactional(readOnly = true)
    @Override
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    @Override
    public void deleteUserById(@NonNull UUID userId) throws ResourceNotFoundException {
        if (repository.existsById(userId)) {
            repository.deleteById(userId);
        } else {
            throw new ResourceNotFoundException("User not found");
        }
    }

    @Transactional
    @Override
    public void updateEmail(@NonNull String newEmail, @NonNull String oldEmail) throws ResourceNotFoundException {
        int result = repository.updateEmail(oldEmail, newEmail);
        if (result == 0) {
            throw new ResourceNotFoundException("User not found");
        } else {
            System.out.println("Email updated successfully");
        }

    }

    @Transactional(readOnly = true)
    @Override
    public UserDTO getUserByEmail(@NonNull String email) throws ResourceNotFoundException {
        return repository.findByEmailDto(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
  

}
