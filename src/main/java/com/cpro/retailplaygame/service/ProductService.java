package com.cpro.retailplaygame.service;

import com.cpro.retailplaygame.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    // List all products
    List<Product> getAllProducts();

    // Get a product by ID
    Optional<Product> getProductById(Long id);

    // Create a New Product
    Product createProduct(Product product);

    // Update Product
    Product updateProduct(Long id, Product productDetails);

    // Delete a product
    void deleteProduct(Long id);
}

