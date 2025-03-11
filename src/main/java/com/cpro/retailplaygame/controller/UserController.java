package com.cpro.retailplaygame.controller;

import com.cpro.retailplaygame.entity.User;
import com.cpro.retailplaygame.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//This marks the class as REST Controller, handling HTTP request
@RestController
@RequestMapping("/api/users") // This sets the base URL for all requests n this controller to be '/api/users'
public class UserController {

    private final UserService userService;
    public UserController(UserService userService)
    {this.userService = userService;}

    // This method handles GET requests to '/api/users'
    // and returns a list of all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // This method handles GET requests to
    // '/api/users/{id}' and fetches a specific user by their ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // This method handles POST requests to
    // '/api/users' and creates a new user
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // This method handles PUT requests to
    // '/api/users/{id}' and updates an existing user's details
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return ResponseEntity.ok(userService.updateUser(id, userDetails));
    }

    // This method handles DELETE requests to
    // '/api/users/{id}' and deletes the specified user
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        // Return a success message with HTTP status 200 (OK)
        return new ResponseEntity<>("User successfully deleted!", HttpStatus.OK);
    }
}
