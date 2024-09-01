/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.github.angel.scm.service;

import com.github.angel.scm.dto.request.Login;
import com.github.angel.scm.dto.request.Register;


/**
 *
 * @author aguero
 */
public interface AuthenticationService {

    void login(Login login);
    void register(Register register);
    
    

}
