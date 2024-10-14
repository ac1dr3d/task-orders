package com.task.orders.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.task.orders.model.Order;
import com.task.orders.repository.OrderRepository;
import com.task.orders.request.OrderRequest;
import com.task.orders.security.services.OrderService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderRequest order) {

        log.info("Creating order for user with ID: {}", order.getUser_id());

        // Call the service to create the order
        Order createdOrder = orderService.createOrder(order.getUser_id(), order.getProduct(), order.getQuantity(),
                order.getPrice(), order.getStatus());

        return ResponseEntity.ok(createdOrder);
    }

    // GET method to retrieve all orders using findAll()
    @GetMapping("")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Iterable<Order>> getAllOrders() {
        return ResponseEntity.ok(orderRepository.findAll());  // Using findAll() directly
    }

}