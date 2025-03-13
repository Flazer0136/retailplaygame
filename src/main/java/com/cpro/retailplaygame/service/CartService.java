package com.cpro.retailplaygame.service;

import com.cpro.retailplaygame.entity.Cart;

public interface CartService {
    Cart getCartByUsername(String username);

    void addToCart(String username, Long productId, int quantity);

    double calculateTotalPrice(Cart cart);
}