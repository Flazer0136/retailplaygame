package com.cpro.retailplaygame.repository;

import com.cpro.retailplaygame.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserUsername(String username);  // Custom query to find the cart by username
}
