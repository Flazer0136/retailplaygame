package com.cpro.retailplaygame.service.impl;

import com.cpro.retailplaygame.entity.Product;
import com.cpro.retailplaygame.repository.ProductRepository;
import com.cpro.retailplaygame.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// This tells Spring Boot that this class is a Service
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {this.productRepository = productRepository;}

    @Override
    // Method to get ALL products from the database
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    @Override
    // Method to find ONE product by its unique ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    // Method to add a NEW product to the database
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    // Method to UPDATE an existing product
    public Product updateProduct(Long id, Product productDetails) {
        // Tries to find the product, throws an error if it doesn’t exist
        Product product = productRepository.findById(id).orElseThrow();
        // Updates the product’s details with new values
        product.setProductName(productDetails.getProductName());
        product.setQuantity(productDetails.getQuantity());
        product.setPrice(productDetails.getPrice());
        product.setInfo(productDetails.getInfo());
        // Saves the updated product back to the database
        return productRepository.save(product);

    }

    @Override
    // Method to DELETE a product from the database
    public void deleteProduct(Long id) {
        productRepository.deleteById(id); // Removes the product by its ID
    }
}

