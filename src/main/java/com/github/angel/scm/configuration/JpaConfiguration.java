/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.github.angel.scm.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.hypersistence.utils.spring.repository.BaseJpaRepositoryImpl;

/**
 *
 * @author aguero
 */
@EnableJpaRepositories(value = {
        "io.hypersistence.utils.spring.repository",
        "com.github.angel.scm.persistence.repository"
}, repositoryBaseClass = BaseJpaRepositoryImpl.class, basePackages = "com.github.angel.scm.persistence.repository")
@Configuration
public class JpaConfiguration {

}
