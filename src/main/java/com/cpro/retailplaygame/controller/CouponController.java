package com.cpro.retailplaygame.controller;

import com.cpro.retailplaygame.entity.Coupon;
import com.cpro.retailplaygame.service.CouponService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // This tells Spring that this class will handle HTTP requests and return responses as JSON
@RequestMapping("api/coupons")  // Base URL for all the routes in this class (e.g., http://localhost:8080/api/coupons)
public class CouponController {

    private final CouponService couponService;  // This connects us to the CouponService where we can interact with coupons

    // Constructor for dependency injection - basically we're making sure we can use CouponService here
    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    // Create a new coupon in the database (this is a POST request)
    @PostMapping
    public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon){
        Coupon savedCoupon = couponService.createCoupon(coupon);  // Save the coupon using the service
        return new ResponseEntity<>(savedCoupon, HttpStatus.CREATED);  // Return the saved coupon with a 201 status
    }

    // Get a coupon by ID (GET request)
    // Example: http://localhost:8080/api/coupons/1
    @GetMapping("{id}")
    public ResponseEntity<Coupon> getCouponById(@PathVariable("id") Long couponId){
        Coupon coupon = couponService.getCouponById(couponId);  // Get coupon by ID using the service
        return new ResponseEntity<>(coupon, HttpStatus.OK);  // Return the coupon with a 200 status
    }

    // Get all the coupons (GET request)
    @GetMapping
    public ResponseEntity<List<Coupon>> getAllCoupons(){
        List<Coupon> coupons = couponService.getAllCoupons();  // Get a list of all coupons
        return new ResponseEntity<>(coupons, HttpStatus.OK);  // Return the list of coupons with a 200 status
    }

    // Update an existing coupon by its ID (PUT request)
    // Example: http://localhost:8080/api/coupons/1
    @PutMapping("{id}")
    public ResponseEntity<Coupon> updateCoupon(@PathVariable("id") Long couponId,
                                               @RequestBody Coupon coupon){
        coupon.setCouponId(couponId);  // Set the coupon ID to make sure we're updating the right coupon
        Coupon updatedCoupon = couponService.updateCoupon(coupon);  // Update the coupon
        return new ResponseEntity<>(updatedCoupon, HttpStatus.OK);  // Return the updated coupon with a 200 status
    }

    // Delete a coupon by its ID (DELETE request)
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCoupon(@PathVariable("id") Long couponId){
        couponService.deleteCoupon(couponId);  // Delete the coupon by ID using the service
        return new ResponseEntity<>("Coupon successfully deleted!", HttpStatus.OK);  // Return a success message
    }
}