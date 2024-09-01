/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.github.angel.scm.service;

/**
 *
 * @author aguero
 */
public interface EmailService {
    
    /**
     * Send an email with the given parameters
     * @param to
     * @param subject
     * @param body
     */
    void sendEmail(String to, String subject, String body);

    void sendEmail(String to, String subject, String body, String attachment);

}
