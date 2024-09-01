/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.github.angel.scm.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.angel.scm.dto.response.UserDTO;
import com.github.angel.scm.persistence.entity.User;

import io.hypersistence.utils.spring.repository.BaseJpaRepository;

/**
 *
 * @author aguero
 */
@Repository
public interface UserRepository extends BaseJpaRepository<User, UUID> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query("""
            SELECT NEW com.github.angel.scm.dto.response.UserDTO(
                u.userId,
                u.email,
                u.about,
                u.profilePic,
                u.phoneNumber
            )
                FROM User u
                WHERE u.email = :email
            """)
    Optional<UserDTO> findByEmailDto(@Param("email") String email);

    @Query("""
            SELECT NEW com.github.angel.scm.dto.response.UserDTO(
                u.userId,
                u.email,
                u.about,
                u.profilePic,
                u.phoneNumber
            )
                FROM User u
                WHERE u.userId = :userId
            """)
    Optional<UserDTO> findByUserIdDto(@Param("userId") UUID userId);

    @Modifying
    @Query("""
            UPDATE User u
            SET u.email = :newEmail
            WHERE u.email = :oldEmail
            """)
    int updateEmail(@Param("oldEmail") String oldEmail, @Param("newEmail") String newEmail);

    @Query(value = """
            SELECT NEW com.github.angel.scm.dto.response.UserDTO(
                u.userId,
                u.email,
                u.about,
                u.profilePic,
                u.phoneNumber
            )
                FROM User u
            """, countQuery = "SELECT COUNT(u) FROM User u")
    Page<UserDTO> findAll(Pageable pageable);

}
