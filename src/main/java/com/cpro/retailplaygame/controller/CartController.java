package com.cpro.retailplaygame.controller;

import com.cpro.retailplaygame.entity.Cart;
import com.cpro.retailplaygame.service.CartService;
import com.cpro.retailplaygame.service.StripeService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    
    @Autowired
    private StripeService stripeService;

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

    // Add to cart
    @PostMapping("/add")
        public String addToCart(@RequestParam("productId") Long productId,
                                @RequestParam("productName") String productName,
                            @RequestParam("quantity") int quantity,
                            Principal principal,
                            RedirectAttributes redirectAttributes) {
        String username = principal.getName();
        cartService.addToCart(username, productId, quantity);

        // Add success message with product name
        redirectAttributes.addFlashAttribute("successMessage",
                productName + " has been added to your cart!");

        return "redirect:/products";
    }

    @GetMapping
    public String viewCart(Principal principal, Model model) {
        String username = principal.getName();
        Cart cart = cartService.getCartByUsername(username);
        model.addAttribute("username", username);
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", cartService.calculateTotalPrice(cart));
        return "cart";
    }

    // Delete form cart
    @PostMapping("/delete")
    public String deleteFromCart(@RequestParam("cartItemId") Long cartItemId, Principal principal) {
        String username = principal.getName();
        cartService.deleteFromCart(username, cartItemId);
        return "redirect:/cart";
    }

    @PostMapping("/apply-coupon")
    public String applyCoupon(@RequestParam("couponCode") String couponCode,
                              Principal principal, RedirectAttributes redirectAttributes) {
        String username = principal.getName();
        boolean success = cartService.applyCoupon(username, couponCode);

        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Coupon applied successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid or expired coupon.");
        }
        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout(Principal principal, Model model) {
        String username = principal.getName();
        Cart cart = cartService.getCartByUsername(username);

        if (cart == null || cart.getCartItems().isEmpty()) {
            model.addAttribute("errorMessage", "Your cart is empty!");
            return "redirect:/cart/view"; // Redirect back to cart view if cart is empty or null
        }

        try {
            String sessionUrl = stripeService.createCheckoutSession(cart);
            model.addAttribute("sessionUrl", sessionUrl);
            return "redirect:" + sessionUrl; // Redirect to Stripe checkout session URL
        } catch (StripeException e) {
            model.addAttribute("errorMessage", "Error creating checkout session: " + e.getMessage());
            return "cart"; // Return to cart view on failure
        }
    }

    @GetMapping("/success")
    public String success(Model model) {
        // You can add any information you'd like to display on the success page
        model.addAttribute("message", "Thank you for your purchase!");
        return "success"; // This will render success.html
    }

}
