package com.myproject.order_processing.dto;

import java.util.List;
import java.util.Date;

/**
 * OrderDTO - Order data ko ek layer se doosri layer tak bhejne ke liye use hota hai.
 * Yeh class order ki details client aur server ke beech transfer karne ke liye banayi gayi hai.
 */
public class OrderDTO {
    /** Order ki unique ID (हर ऑर्डर की अलग पहचान) */
    private Long id;
    /** Customer ki ID (जिसने ऑर्डर किया) */
    private Long customerId;
    /** Order ki date (Order kab place hua) */
    private Date orderDate;
    /** Order ka total amount (कुल राशि) */
    private Double totalAmount;
    /** Order items ki list (सभी प्रोडक्ट्स/आइटम्स जो ऑर्डर में हैं) */
    private List<String> orderItems;

    // Getter aur Setter methods - Data ko safely access aur modify karne ke liye
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
    public List<String> getOrderItems() { return orderItems; }
    public void setOrderItems(List<String> orderItems) { this.orderItems = orderItems; }
} 