package com.cpro.retailplaygame.service.impl;

import com.cpro.retailplaygame.entity.User;
import com.cpro.retailplaygame.repository.UserRepository;
import com.cpro.retailplaygame.service.UserService;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

//Service tells Spring that this class is service
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public  UserServiceImpl(UserRepository userRepository)
    {this.userRepository = userRepository;}

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    // This method updates a user's information in the database
    public User updateUser(Long id, User userDetails) {
        // Tries to find the user by their ID, or throws an
        // error if not found
        User user = userRepository.findById(id).orElseThrow();

        // Sets the new user details from the passed
        // in userDetails object
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setEnabled(userDetails.getEnabled());

        // Saves the updated user and returns it
        return userRepository.save(user);
    }

    @Override
    // This method deletes a user by their ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id); // Deletes the
        // user from the database using their ID
    }
}