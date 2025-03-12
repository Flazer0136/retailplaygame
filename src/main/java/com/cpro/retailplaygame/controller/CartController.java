package com.cpro.retailplaygame.controller;

import com.cpro.retailplaygame.entity.Cart;
import com.cpro.retailplaygame.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // View cart
    @GetMapping("/view")
    public String viewCart(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails != null) {
            // Get the username of the logged-in user
            String username = userDetails.getUsername();
            // Get the cart for the logged-in user using CartServiceImpl
            Cart cart = cartService.getCartByUsername(username);

            if (cart != null) {
                model.addAttribute("cart", cart);
                // Calculate the total price dynamically using the service
                double totalPrice = cartService.calculateTotalPrice(cart);
                model.addAttribute("totalPrice", totalPrice);
            } else {
                model.addAttribute("cart", null);
                model.addAttribute("totalPrice", 0.0);  // Default total if cart is empty
            }

            model.addAttribute("username", username);
        } else {
            model.addAttribute("username", "Guest");
            model.addAttribute("cart", null); // No cart for guest user
            model.addAttribute("totalPrice", 0.0); // Default total for guest
        }
        return "cart";
    }
}
