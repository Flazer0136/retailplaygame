package com.cpro.retailplaygame.service.impl;

import com.cpro.retailplaygame.entity.Authorities;
import com.cpro.retailplaygame.entity.Cart;
import com.cpro.retailplaygame.entity.User;
import com.cpro.retailplaygame.repository.AuthoritiesRepository;
import com.cpro.retailplaygame.repository.CartRepository;
import com.cpro.retailplaygame.repository.UserRepository;
import com.cpro.retailplaygame.service.UserService;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

//Service tells Spring that this class is service
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private AuthoritiesRepository authoritiesRepository;
    private CartRepository cartRepository;

    public UserServiceImpl(UserRepository userRepository,
                           AuthoritiesRepository authoritiesRepository,
                           CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.authoritiesRepository = authoritiesRepository;
        this.cartRepository = cartRepository;
    }

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
        // Save the user first
        User savedUser = userRepository.save(user);

        // Create and save authority
        Authorities authority = new Authorities();
        authority.setUser(savedUser);
        authority.setAuthority("ROLE_CUSTOMER");
        authoritiesRepository.save(authority);

        Cart cart = new Cart(savedUser);
        cartRepository.save(cart);

        return savedUser;
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

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}