package com.cpro.retailplaygame.service.impl;

import com.cpro.retailplaygame.entity.Cart;
import com.cpro.retailplaygame.entity.CartItem;
import com.cpro.retailplaygame.repository.CartRepository;
import com.cpro.retailplaygame.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Cart getCartByUsername(String username) {
        // Find the cart associated with the user by username
        Optional<Cart> cartOptional = cartRepository.findByUserUsername(username);
        return cartOptional.orElse(null); // Return null if no cart is found
    }

    // Calculate the total price of the cart dynamically
    public double calculateTotalPrice(Cart cart) {
        double total = 0;
        // Loop through all cart items and sum their total price
        for (CartItem item : cart.getCartItems()) {
            // Multiply product price by quantity and add to total
            double itemTotal = item.getProduct().getPrice() * item.getQuantity();
            total += itemTotal;
        }
        return total;  // Return the calculated total price
    }
}
