package com.cpro.retailplaygame.service.impl;

import com.cpro.retailplaygame.entity.*;
import com.cpro.retailplaygame.repository.OrderItemRepository;
import com.cpro.retailplaygame.repository.OrderRepository;
import com.cpro.retailplaygame.service.CartService;
import com.cpro.retailplaygame.service.OrderService;
import com.cpro.retailplaygame.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartService cartService; // Service to get the cart's contents
    @Autowired
    private UserService userService; // Service to get the current user

    @Override
    public void saveOrder(User user, Cart cart) {
        // Create new order
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date()); // Current date
        double totalPrice = cartService.calculateTotalPrice(cart); // already discounted (finalprice)
        double discount = cart.getCoupon() != null ? cart.getCoupon().getDiscount() : 0;

        order.setTotalPrice(totalPrice);
        order.setCouponCode(cart.getCoupon() != null ? cart.getCoupon().getCouponCode() : "No Coupon");
        order.setDiscount(discount);

        // Save order to the database
        orderRepository.save(order);

        // Save each item in the order
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItem.setTotalPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());

            orderItemRepository.save(orderItem);
        }

        // Now, delete each cart item after the order is created
        for (CartItem cartItem : cart.getCartItems()) {
            cartService.deleteFromCart(user.getUsername(), cartItem.getId());  // Deletes individual cart items
        }
    }

    @Override
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    @Override
    public List<OrderItem> getOrderItemsByOrder(Order order) {
        return orderItemRepository.findByOrder(order);
    }
}
