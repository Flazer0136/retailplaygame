package com.cpro.retailplaygame.service;

import com.cpro.retailplaygame.entity.Cart;
import com.cpro.retailplaygame.entity.Order;
import com.cpro.retailplaygame.entity.OrderItem;
import com.cpro.retailplaygame.entity.User;

import java.util.List;

public interface OrderService {

    // Save an order
    void saveOrder(User user, Cart cart);

    // Retrieve orders by a user
    List<Order> getOrdersByUser(User user);

    // Other potential methods can be added as needed, for example:
    // Get an order by ID
    Order getOrderById(Long orderId);

    // Get order items by order
    List<OrderItem> getOrderItemsByOrder(Order order);
}
