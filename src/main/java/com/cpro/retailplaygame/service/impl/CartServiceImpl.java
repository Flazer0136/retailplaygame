package com.cpro.retailplaygame.service.impl;

import com.cpro.retailplaygame.entity.Cart;
import com.cpro.retailplaygame.entity.CartItem;
import com.cpro.retailplaygame.entity.Product;
import com.cpro.retailplaygame.repository.CartRepository;
import com.cpro.retailplaygame.repository.ProductRepository;
import com.cpro.retailplaygame.repository.CartItemRepository;
import com.cpro.retailplaygame.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public Cart getCartByUsername(String username) {
        // Find the cart associated with the user by username
        Optional<Cart> cartOptional = cartRepository.findByUserUsername(username);
        return cartOptional.orElse(null); // Return null if no cart is found
    }

    @Override
    public void addToCart(String username, Long productId, int quantity) {
        Optional<Cart> cartOptional = cartRepository.findByUserUsername(username);
        Cart cart = cartOptional.orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem existingCartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getProductID().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setProduct(product);
            newCartItem.setQuantity(quantity);
            newCartItem.setCart(cart);
            cart.getCartItems().add(newCartItem);
        }

        cartRepository.save(cart);
    }

    // Delete a product from the cart
    @Override
    public void deleteFromCart(String username, Long cartItemId) {
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
        CartItem cartItem = cartItemOptional.orElseThrow(() -> new RuntimeException("CartItem not found"));
        cartItemRepository.delete(cartItem);
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
