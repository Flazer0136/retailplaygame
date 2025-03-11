package com.cpro.retailplaygame.service.impl;

import com.cpro.retailplaygame.entity.Coupon;
import com.cpro.retailplaygame.repository.CouponRepository;
import com.cpro.retailplaygame.service.CouponService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service  // This makes the class a Spring service, which is like a helper class for doing stuff with coupons
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;  // This connects to the database where we store our coupons

    // Constructor for dependency injection (basically making sure we can use the CouponRepository in this class)
    public CouponServiceImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    // Create a new coupon in the database
    @Override
    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);  // Save the coupon in the database and return it
    }

    // Get a coupon by its ID
    @Override
    public Coupon getCouponById(Long couponId) {
        Optional<Coupon> optionalCoupon = couponRepository.findById(couponId);  // Try to find the coupon by ID
        return optionalCoupon.orElse(null);  // If we can't find it, return null
    }

    // Get all coupons from the database
    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();  // Return a list of all coupons
    }

    // Update an existing coupon with new data
    @Override
    public Coupon updateCoupon(Coupon coupon) {
        // Check if the coupon already exists in the database
        Optional<Coupon> existingCouponOptional = couponRepository.findById(coupon.getCouponId());
        if (existingCouponOptional.isPresent()) {  // If the coupon is found
            Coupon existingCoupon = existingCouponOptional.get();  // Get the existing coupon
            // Update the details with new data
            existingCoupon.setCouponCode(coupon.getCouponCode());
            existingCoupon.setDiscountType(coupon.getDiscountType());
            existingCoupon.setDiscount(coupon.getDiscount());
            existingCoupon.setExpiryDate(coupon.getExpiryDate());
            existingCoupon.setUsageLimit(coupon.getUsageLimit());
            return couponRepository.save(existingCoupon);  // Save the updated coupon
        }
        return null;  // If the coupon isn't found, return null
    }

    // Delete a coupon by its ID
    @Override
    public void deleteCoupon(Long couponId) {
        couponRepository.deleteById(couponId);  // Delete the coupon from the database
    }
}