package com.cpro.retailplaygame.service;

import com.cpro.retailplaygame.entity.Cart;

public interface CartService {
    Cart getCartByUsername(String username);

    double calculateTotalPrice(Cart cart);
}
