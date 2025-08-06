package com.myproject.order_processing.model;

import jakarta.persistence.*;
import java.util.Date;

/**
 * Order - Order ki entity class, database table ke liye.
 * Yeh class ek order ke saare details ko represent karti hai.
 */
@Entity // Is class ko JPA entity banata hai (database table se map karta hai)
@Table(name = "orders") // Table ka naam set karta hai
public class Order {
    /** Order ki unique ID (Primary key, auto-generated) */
    @Id // Primary key ke liye
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

    /** Customer ki ID (kisne order kiya) */
    @Column(nullable = false)
    private Long customerId;

    /** Order ki date (kab place hua) */
    @Column(nullable = false)
    private Date orderDate;

    /** Order ka total amount */
    @Column(nullable = false)
    private Double totalAmount;

    // TODO: Order items ka relation yahan add kar sakte hain (OneToMany etc.)

    // Getter aur Setter methods
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
}