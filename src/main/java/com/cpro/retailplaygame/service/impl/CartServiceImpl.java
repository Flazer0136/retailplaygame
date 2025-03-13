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

    // Adds a product to the cart
    @Override
    public void addToCart(String username, Long productId, int quantity) {
        Optional<Cart> cartOptional = cartRepository.findByUserUsername(username);
        Cart cart = cartOptional.orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);

        cartItemRepository.save(cartItem);
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
