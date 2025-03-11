package com.cpro.retailplaygame.repository;

import com.cpro.retailplaygame.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
