package com.cpro.retailplaygame.entity;

import jakarta.persistence.*; // Import JPA annotations

@Entity  // Marks this as an entity class
@Table(name = "products")  // Links this class to the "products" table in the database
public class Product {

    @Id  // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incremented by the database
    private Long productID;

    @Column(nullable = false)  // Ensures product name is not null
    private String productName;  // Name of the product

    @Column(nullable = false)
    private int quantity;  // Number of products available

    @Column(nullable = false)
    private double price;  // Price of the product

    private String info;  // Additional product details

    // No-argument constructor (needed for JPA to work properly)
    public Product() {
    }

    // Constructor to set all fields when creating a new Product
    public Product(Long productID, String productName, int quantity, double price, String info) {
        this.productID = productID;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.info = info;
    }

    // Getters and Setters

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
