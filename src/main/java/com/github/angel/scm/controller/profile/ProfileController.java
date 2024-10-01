/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.github.angel.scm.controller.profile;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author aguero
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {
    @GetMapping
    @PreAuthorize("hasAuthority('READ')")
    public String profile() {
        return "user/profile";
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasAuthority('READ')")
    public String dashbaord() {
        return "user/dashboard";
    }

}
