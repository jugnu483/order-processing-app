package com.myproject.order_processing.service;

import com.myproject.order_processing.model.Order;
import com.myproject.order_processing.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Hinglish Comments: OrderService - Order se related business logic yahan likhi jaati hai.
@Service // Is class ko Spring service banata hai (Order logic ke liye)
public class OrderService {
    private final OrderRepository orderRepository; // OrderRepository - database operations ke liye

    // Constructor - dependency injection ke liye
    @Autowired // Spring ko batata hai ki dependency inject karo
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Sabhi orders ki list laane wala method
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // ID ke basis par ek order laane wala method
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // Naya order create/update karne ka method
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    // Order delete karne ka method
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
} 