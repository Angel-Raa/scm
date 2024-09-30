/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.github.angel.scm.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import com.github.angel.scm.dto.response.UserDTO;
import com.github.angel.scm.exception.ResourceNotFoundException;

/**
 *
 * @author aguero
 */
public interface UserService {
    UserDTO getUserById(@NonNull UUID userId) throws ResourceNotFoundException;

    UserDTO getUserByEmail(@NonNull String email) throws ResourceNotFoundException;

    Page<UserDTO> getAllUsers(Pageable pageable);

    void deleteUserById(@NonNull UUID userId) throws ResourceNotFoundException;

    void updateEmail(@NonNull String oldEmail, @NonNull String newEmail) throws ResourceNotFoundException;

    // void createProfile(@NonNull Profile profile);
    boolean existsByEmail(String email);
    

}
