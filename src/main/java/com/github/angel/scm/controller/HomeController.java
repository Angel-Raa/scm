/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.github.angel.scm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author aguero
 */
@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping({"/", "/home"})
    public String home(final Model model){
        model.addAttribute("subtitle", "Home");
        model.addAttribute("content", "This is the home page");
        return "home";
    }

    @GetMapping("/about")
    public String about(final Model model){
        return "about";
    }

    @GetMapping("/service")
    public String service(final Model model){
        return "service";
    }


    @GetMapping("/contact")
    public String contact(final Model model){
        return "contact";
    }


    
  

}
