package com.myproject.order_processing.controller;

import com.myproject.order_processing.dto.OrderItemDTO;
import com.myproject.order_processing.model.OrderItem;
import com.myproject.order_processing.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {
    private final OrderItemService orderItemService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping
    public ResponseEntity<List<OrderItemDTO>> getAllOrderItems() {
        List<OrderItemDTO> dtos = orderItemService.getAllOrderItems().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemDTO> getOrderItemById(@PathVariable Long id) {
        Optional<OrderItem> orderItem = orderItemService.getOrderItemById(id);
        return orderItem.map(item -> ResponseEntity.ok(toDTO(item)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrderItemDTO> createOrderItem(@RequestBody OrderItemDTO dto) {
        OrderItem orderItem = toEntity(dto);
        OrderItem saved = orderItemService.saveOrderItem(orderItem);
        return new ResponseEntity<>(toDTO(saved), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItemDTO> updateOrderItem(@PathVariable Long id, @RequestBody OrderItemDTO dto) {
        if (!orderItemService.getOrderItemById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        OrderItem orderItem = toEntity(dto);
        orderItem.setId(id);
        OrderItem updated = orderItemService.saveOrderItem(orderItem);
        return ResponseEntity.ok(toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        if (!orderItemService.getOrderItemById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }

    private OrderItemDTO toDTO(OrderItem item) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(item.getId());
        dto.setOrderId(item.getOrder() != null ? item.getOrder().getId() : null);
        dto.setProductId(item.getProduct() != null ? item.getProduct().getId() : null);
        dto.setQuantity(item.getQuantity());
        dto.setSubtotal(item.getSubtotal());
        return dto;
    }

    private OrderItem toEntity(OrderItemDTO dto) {
        OrderItem item = new OrderItem();
        item.setId(dto.getId());
        // Note: You may need to fetch and set Order and Product entities by their IDs here
        item.setQuantity(dto.getQuantity());
        item.setSubtotal(dto.getSubtotal());
        return item;
    }
} 