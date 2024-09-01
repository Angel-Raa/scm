/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.github.angel.scm.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author aguero
 */
@Controller
@RequestMapping("/authentication")
public class AuthenticationController {

    @GetMapping("/login")
    public String login(final Model model) {
        return "login";
    }

    @GetMapping("/register")
    public String register(final Model model) {
        return "register";
    }

}
