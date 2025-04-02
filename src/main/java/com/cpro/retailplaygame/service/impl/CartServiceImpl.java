package com.cpro.retailplaygame.service.impl;

import com.cpro.retailplaygame.entity.Cart;
import com.cpro.retailplaygame.entity.User;
import com.cpro.retailplaygame.entity.CartItem;
import com.cpro.retailplaygame.entity.Coupon;
import com.cpro.retailplaygame.entity.Product;
import com.cpro.retailplaygame.repository.*;
import com.cpro.retailplaygame.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Override
    @Transactional
    public Cart getCartByUsername(String username) {
        return cartRepository.findByUserUsername(username)
            .orElseGet(() -> {
                User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

                Cart newCart = new Cart();
                newCart.setUser(user); // This sets the proper foreign key
                return cartRepository.save(newCart);
            });
    }

    @Override
    public void addToCart(String username, Long productId, int quantity) {
        Cart cart = getCartByUsername(username); // This now creates cart if needed
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

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

    @Override
    public void clearCart(String username) {
        Optional<Cart> cartOptional = cartRepository.findByUserUsername(username);
        Cart cart = cartOptional.orElseThrow(() -> new RuntimeException("Cart not found"));
        if (cart != null) {
            cartItemRepository.deleteAll(cart.getCartItems()); // Removes all items from the cart
            cart.getCartItems().clear(); // Clear the list to reflect changes
            cartRepository.save(cart); // Save the empty cart
        }
    }

    @Override
    public double calculateTotalPrice(Cart cart) {
        double total = cart.getCartItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
        // Apply discount if a coupon is present
        Coupon coupon = cart.getCoupon();
        if (coupon != null) {
            switch (coupon.getDiscountType().toUpperCase()) {
                case "PERCENTAGE":
                    total *= (1 - (coupon.getDiscount() / 100.0));
                    break;
                case "FIXED":
                    total -= coupon.getDiscount();
                    break;
            }
        }
        return Math.max(total, 0); // Ensure total is never negative
    }

    // Apply Coupon
    @Override
    public boolean applyCoupon(String username, String couponCode) {
        Cart cart = getCartByUsername(username);
        Optional<Coupon> couponOpt = couponRepository.findByCouponCode(couponCode);
        Optional<Coupon> couponOptDefault = couponRepository.findByCouponCode("DEFAULT");

        if (couponOpt.isPresent()) {
            Coupon coupon = couponOpt.get();
            Date today = new Date();

            // Check expiry and usage limit
            if (coupon.getExpiryDate().after(today) && coupon.getUsageLimit() > 0) {
                cart.setCoupon(coupon);
                cartRepository.save(cart);
                return true;
            } else {
                if (couponOptDefault.isPresent()) {
                    cart.setCoupon(couponOptDefault.get());
                    cartRepository.save(cart);
                }
            }
        }
        return false;
    }
}
