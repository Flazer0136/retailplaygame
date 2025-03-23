package com.cpro.retailplaygame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController {

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register"; // This should match the name of your HTML file (registration.html)
    }
}