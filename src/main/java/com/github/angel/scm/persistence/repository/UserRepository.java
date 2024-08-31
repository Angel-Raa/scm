/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.github.angel.scm.persistence.repository;

import org.springframework.stereotype.Repository;

/**
 *
 * @author aguero
 */
@Repository
public interface UserRepository{
    boolean existsByEmail(String email);


}
