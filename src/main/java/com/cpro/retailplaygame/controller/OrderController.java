package com.cpro.retailplaygame.controller;

import com.cpro.retailplaygame.entity.Order;
import com.cpro.retailplaygame.entity.OrderItem;
import com.cpro.retailplaygame.entity.User;
import com.cpro.retailplaygame.service.OrderService;
import com.cpro.retailplaygame.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    // Fetch the orders of the logged-in user and display them on the order history page
    @GetMapping("/order-history")
    public String viewOrderHistory(Principal principal, Model model) {
        String username = principal.getName();

        // Fetch user details (using the username from the Principal)
        User user = userService.getAllUsers().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        if (user != null) {
            // Fetch all orders for this user
            List<Order> orders = orderService.getOrdersByUser(user);

            model.addAttribute("orders", orders);
        } else {
            model.addAttribute("errorMessage", "User not found.");
        }

        return "order-history";
    }

    // View details of a specific order
    @GetMapping("/order-history/{orderId}")
    public String viewOrderDetails(@PathVariable("orderId") Long orderId, Model model) {
        // Fetch the order by orderId
        Order order = orderService.getOrderById(orderId);

        if (order != null) {
            // Fetch the order items for the given order
            List<OrderItem> orderItems = orderService.getOrderItemsByOrder(order);

            model.addAttribute("order", order);
            model.addAttribute("orderItems", orderItems);
        } else {
            model.addAttribute("errorMessage", "Order not found.");
        }

        return "order-details";
    }
}
