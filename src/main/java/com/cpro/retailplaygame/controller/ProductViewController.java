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

    private final ProductService productService;
    private final CartService cartService;

    public ProductViewController(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    @GetMapping("/products")
    public String showProducts(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // Add these TWO CRITICAL model attributes
        model.addAttribute("product", new Product()); // For form binding
        model.addAttribute("newProduct", new Product()); // For modal form

        model.addAttribute("products", productService.getAllProducts());

        if (userDetails != null) {
            String username = userDetails.getUsername();
            model.addAttribute("username", username);
            model.addAttribute("cart", cartService.getCartByUsername(username));
        } else {
            model.addAttribute("username", "Guest");
        }
        return "products";
    }
}
