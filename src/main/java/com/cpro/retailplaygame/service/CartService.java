package com.cpro.retailplaygame.service;

import com.cpro.retailplaygame.entity.Cart;

public interface CartService {
    Cart getCartByUsername(String username);

    void addToCart(String username, Long productId, int quantity);

    void deleteFromCart(String username, Long cartItemId);  // Add this method

    double calculateTotalPrice(Cart cart);

    boolean applyCoupon(String username, String couponCode);

    void clearCart(String username);
}
