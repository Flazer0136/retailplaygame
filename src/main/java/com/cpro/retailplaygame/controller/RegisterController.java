package com.cpro.retailplaygame.controller;

import com.cpro.retailplaygame.entity.Authorities;
import com.cpro.retailplaygame.entity.User;
import com.cpro.retailplaygame.repository.AuthoritiesRepository;
import com.cpro.retailplaygame.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthoritiesRepository authoritiesRepository;

    // Remove the AuthenticationManager if you're not using it for registration
    public RegisterController(UserRepository userRepository,
                            PasswordEncoder passwordEncoder,
                            AuthoritiesRepository authoritiesRepository) { // Add parameter
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authoritiesRepository = authoritiesRepository; // Initialize
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(User user) {
        // Check if username exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "redirect:/register?error=username_exists";
        }

        // Encode password and enable user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(1);

        // Add default role
        user.addAuthority("ROLE_CUSTOMER");

        // Save user (cascade will save authorities)
        userRepository.save(user);

        return "redirect:/login?registered";
    }
}