package com.cpro.retailplaygame.controller;

import com.cpro.retailplaygame.entity.*;
import com.cpro.retailplaygame.repository.CartRepository;
import com.cpro.retailplaygame.repository.CouponRepository;
import com.cpro.retailplaygame.repository.ProductRepository;
import com.cpro.retailplaygame.service.CartService;
import com.cpro.retailplaygame.service.OrderService;
import com.cpro.retailplaygame.service.StripeService;
import com.cpro.retailplaygame.service.UserService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private StripeService stripeService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

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

                Optional<Coupon> couponOptDefault = couponRepository.findByCouponCode("DEFAULT");
                if (couponOptDefault.isPresent()) {
                    cart.setCoupon(couponOptDefault.get());
                    cartRepository.save(cart);
                }

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
    public String success(Principal principal, Model model) {
        String username = principal.getName();
        Cart cart = cartService.getCartByUsername(username);

        if (cart == null || cart.getCartItems().isEmpty()) {
            model.addAttribute("errorMessage", "Your cart is empty!");
            return "redirect:/cart/view"; // Redirect back to cart if cart is empty
        }

        // Find the user by username from the list of all users
        Optional<User> userOptional = userService.getAllUsers().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();

        if (userOptional.isEmpty()) {
            model.addAttribute("errorMessage", "User not found.");
            return "cart"; // Return to cart view if the user is not found
        }

        User user = userOptional.get();

        // Save the order using the OrderService
        try {
            orderService.saveOrder(user, cart);

            // Update product stock levels after the order is saved
            for (CartItem cartItem : cart.getCartItems()) {
                Product product = cartItem.getProduct();

                if (product.getQuantity() >= cartItem.getQuantity()) {
                    product.setQuantity(product.getQuantity() - cartItem.getQuantity()); // Deduct stock
                    productRepository.save(product); // Save updated stock to the database
                } else {
                    model.addAttribute("errorMessage", "Not enough stock for product: " + product.getProductName());
                    return "cart"; // Redirect back to cart if stock is insufficient
                }
            }

            // Clear the cart after the order has been placed
            cartService.applyCoupon(username, "DEFAULT");
            cartService.clearCart(username);

            model.addAttribute("message", "Your order has been placed successfully!");
            return "success"; // Return to an order confirmation page or success view
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while processing your order: " + e.getMessage());
            return "cart"; // Return to the cart view on failure
        }
    }


}
