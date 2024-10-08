/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.github.angel.scm.controller.auth;

import java.util.HashMap;
import java.util.Map;

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
import com.github.angel.scm.exception.ResourceAlreadyExistsException;
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
            final RedirectAttributes attribute) {
        if (result.hasErrors()) {
            model.addAttribute("login", login);
            model.addAttribute("errors", result.getFieldErrors());
            return "login";
        }
        service.login(login);
        attribute.addAttribute("message", "Login Successful");
        model.addAttribute("login", login);

        return "redirect:/profile/dashboard";
    }

    @PostMapping("/register")
    public String register(@Valid final Register register, final BindingResult result, final Model model,
            final RedirectAttributes atribute) {
        try {
            if (result.hasErrors()) {
                // Recopila los errores de validación y los agrega al modelo
                Map<String, Object> errores = new HashMap<>();
                for (FieldError fieldError : result.getFieldErrors()) {
                    if (errores.put(fieldError.getField(), fieldError.getDefaultMessage()) != null) {
                        throw new IllegalStateException("Duplicate key");
                    }
                }
                model.addAttribute("errors", errores);
                model.addAttribute("register", register);
                return "register";
            }
            atribute.addAttribute("message", "Registration Successful");
            service.register(register);
            return "redirect:/authentication/login";
        } catch (IllegalArgumentException exception) {
            model.addAttribute("passwordError", exception.getMessage());
            model.addAttribute("register", register);
            return "register";
        } catch (ResourceAlreadyExistsException e) {
            model.addAttribute("emailError", e.getMessage());
            model.addAttribute("register", register);
            return "register";
        }

    }

    @GetMapping("/logout")
    public String logout(final Model model) {
        return "redirect:/authentication/login?logout=true";
    }

}
