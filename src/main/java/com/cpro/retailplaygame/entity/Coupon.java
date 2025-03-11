package com.cpro.retailplaygame.entity;

import jakarta.persistence.*; // Importing JPA classes so we can work with the database
import java.util.Date; // Importing Date to store expiry dates for the coupon

@Entity // This says that this class is a JPA entity, which means it's going to be a table in the database
@Table(name = "coupons") // The table name in the database will be 'coupons'
public class Coupon {

    @Id  // This tells JPA that couponId is the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // This will auto-increment the couponId in the database
    private Long couponId;  // Unique ID for each coupon

    @Column(nullable = false, unique = true)  // couponCode must be unique and cannot be null
    private String couponCode;  // The code used for the coupon (e.g., 'SUMMER20')

    @Column(nullable = false)  // discountType must be filled in
    private String discountType;  // Type of discount, like 'percentage' or 'fixed'

    @Column(nullable = false)  // discount cannot be null
    private double discount;  // The actual discount amount (e.g., 20 for 20% or 10 for $10)

    @Column(nullable = false)  // expiryDate cannot be null
    private Date expiryDate;  // The date when the coupon expires

    @Column(nullable = false)  // usageLimit cannot be null
    private int usageLimit;  // How many times the coupon can be used

    // No-argument constructor for JPA
    public Coupon() {
    }

    // All-argument constructor so we can create a coupon object with all the data
    public Coupon(Long couponId, String couponCode, String discountType, double discount, Date expiryDate, int usageLimit) {
        this.couponId = couponId;
        this.couponCode = couponCode;
        this.discountType = discountType;
        this.discount = discount;
        this.expiryDate = expiryDate;
        this.usageLimit = usageLimit;
    }

    // Getters and setters so we can get and set values for the fields
    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getUsageLimit() {
        return usageLimit;
    }

    public void setUsageLimit(int usageLimit) {
        this.usageLimit = usageLimit;
    }
}