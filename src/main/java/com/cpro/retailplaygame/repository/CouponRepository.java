package com.cpro.retailplaygame.repository;

import com.cpro.retailplaygame.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}