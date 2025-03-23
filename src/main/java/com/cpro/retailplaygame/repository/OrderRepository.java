package com.cpro.retailplaygame.repository;

import com.cpro.retailplaygame.entity.Order;
import com.cpro.retailplaygame.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
