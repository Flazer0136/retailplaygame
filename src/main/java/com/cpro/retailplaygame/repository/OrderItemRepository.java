package com.cpro.retailplaygame.repository;

import com.cpro.retailplaygame.entity.Order;
import com.cpro.retailplaygame.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Order order);
}
