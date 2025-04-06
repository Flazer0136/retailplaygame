package com.cpro.retailplaygame.controller;


import com.cpro.retailplaygame.entity.Cart;
import com.cpro.retailplaygame.entity.Product;
import com.cpro.retailplaygame.service.CartService;
import com.cpro.retailplaygame.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductViewController {

    @Autowired
    private final CartService cartService;

    private final ProductService productService;

    public ProductViewController(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }


    @GetMapping("/products")
    public String showProducts(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);

        if (userDetails != null) {
            String username = userDetails.getUsername();
            model.addAttribute("username", username);

            // Only apply coupon if you really need to
            // cartService.applyCoupon(username, "DEFAULT");

            // Instead, get the cart to display info
            Cart cart = cartService.getCartByUsername(username);
            model.addAttribute("cart", cart);
        } else {
            model.addAttribute("username", "Guest");
        }

        return "products";
    }
}
