package com.myproject.order_processing.controller;

import com.myproject.order_processing.model.Order;
import com.myproject.order_processing.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * OrderController - REST controller for managing orders.
 * Exposes endpoints for CRUD operations on orders.
 */
@RestController // Marks this class as a REST controller
@RequestMapping("/api/orders") // Maps requests to /api/orders
public class OrderController {
    private final OrderService orderService;

    /**
     * Constructor for dependency injection.
     * @param orderService the order service
     */
    @Autowired // Injects OrderService
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Get all orders.
     * @return list of all orders
     */
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    /**
     * Get an order by ID.
     * @param id the order ID
     * @return the order if found, otherwise 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new order.
     * @param order the order to create
     * @return the created order
     */
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order savedOrder = orderService.saveOrder(order);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    /**
     * Update an order by ID.
     * @param id the order ID
     * @param order the updated order data
     * @return the updated order if found, otherwise 404
     */
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        if (!orderService.getOrderById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        order.setId(id);
        Order updatedOrder = orderService.saveOrder(order);
        return ResponseEntity.ok(updatedOrder);
    }

    /**
     * Delete an order by ID.
     * @param id the order ID
     * @return 204 No Content if deleted, otherwise 404
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (!orderService.getOrderById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
} 