package com.cpro.retailplaygame.service;

import com.cpro.retailplaygame.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

// This method gets all users from the database
List<User> getAllUsers();

// This method gets a user by their ID from the database
Optional<User> getUserById(Long id);

// This method creates a new user and saves
// them to the database
User createUser(User user);

// This method updates a user's information in the database
User updateUser(Long id, User userDetails);

// This method deletes a user by their ID
void deleteUser(Long id);

Optional<User> getUserByUsername(String username);

}
