package com.task.orders.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

import com.task.orders.model.Order;
import com.task.orders.model.User;
import com.task.orders.repository.OrderRepository;
import com.task.orders.repository.UserRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    // Method to create an order for a user
    public Order createOrder(Long userId, String product, Integer quantity, BigDecimal price) {
        // Fetch the user by user_id
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        // Create a new order and associate it with the fetched user
        Order order = new Order(user, product, quantity, price);

        // Save the order to the database
        return orderRepository.save(order);
    }
}