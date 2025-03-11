package com.cpro.retailplaygame.controller;

import com.cpro.retailplaygame.entity.Product;
import com.cpro.retailplaygame.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// This tells Spring Boot that this class is a controller that handles HTTP requests
@RestController
@RequestMapping("api/products") // All requests that start with "api/products" will be handled here
public class ProductController {
    // This connects the controller to the service layer (automatically assigns an instance)
    private final ProductService productService;

    public ProductController(ProductService productService) { this.productService = productService; }

    @GetMapping("/products")
    public String showProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products";
    }

    // This handles GET requests to get all products
    @GetMapping
    public List<Product> getAllProducts(){
        // Calls the service to get all products
        return productService.getAllProducts();
    }

    // This handles GET requests to get a product by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Product>getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id); // Finds product by ID
        // If the product is found, return it; otherwise, return a "Not Found" response
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // This handles POST requests to create a new product
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product); // Calls the service to save the product
    }

    // This handles PUT requests to update an existing product by ID
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        return ResponseEntity.ok(productService.updateProduct(id, productDetails));
        // Updates and returns the updated product
    }

    // This handles DELETE requests to remove a product by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id); // Calls the service to delete the product
        // Return a success message with HTTP status 200 (OK)
        return new ResponseEntity<>("Product successfully deleted!", HttpStatus.OK);
    }
}
