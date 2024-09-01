/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.github.angel.scm.controller.auth;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.angel.scm.dto.request.Login;
import com.github.angel.scm.dto.request.Register;
import com.github.angel.scm.service.AuthenticationService;

import jakarta.validation.Valid;

/**
 *
 * @author aguero
 */
@Controller
@RequestMapping("/authentication")
public class AuthenticationController {
    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    @GetMapping("/login")
    public String login(final Model model) {
        model.addAttribute("login", new Login());
        return "login";
    }

    @GetMapping("/register")
    public String register(final Model model) {
        model.addAttribute("register", new Register());
        return "register";
    }

    @PostMapping("/login")
    public String login(@Valid final Login login, final BindingResult result, final Model model,
            final RedirectAttributes atribute) {
        System.out.println(login.toString());
        return "redirect:/";
    }

    @PostMapping("/register")
    public String register(@Valid final Register register, final BindingResult result, final Model model,
            final RedirectAttributes atribute) {
        if (result.hasErrors()) {
            // Recopila los errores de validación y los agrega al modelo
            Map<String, Object> errores = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            FieldError::getDefaultMessage));
            model.addAttribute("errors", errores);
            model.addAttribute("register", register);
            return "authentication/register";
        }
        atribute.addAttribute("message", "Registration Successful");
        service.register(register);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(final Model model) {
        return "redirect:/authentication/login?logout=true";
    }

}
