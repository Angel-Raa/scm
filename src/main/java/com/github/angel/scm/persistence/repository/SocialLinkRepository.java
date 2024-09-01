/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.github.angel.scm.persistence.repository;

import com.github.angel.scm.persistence.entity.SocialLink;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import io.hypersistence.utils.spring.repository.BaseJpaRepository;

/**
 *
 * @author aguero
 */
@Repository
public interface SocialLinkRepository extends BaseJpaRepository<SocialLink, UUID>{

}
