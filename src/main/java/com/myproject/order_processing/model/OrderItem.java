package com.myproject.order_processing.model; 

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType; 
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name = "order_items") 
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class OrderItem { 

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id; 

    @ManyToOne // Defines a many-to-one relationship with the Order entity (many order items can belong to one order)
    @JoinColumn(name = "order_id", nullable = false) // Specifies the foreign key column in the 'order_items' table that links to the 'orders' table
    private Order order; // Declares the 'order' field as an Order object, representing the order to which this item belongs

    @ManyToOne 
    @JoinColumn(name = "product_id", nullable = false) 
    private Product product; 

    @Column(nullable = false) 
    private Integer quantity; 

    @Column(nullable = false) 
    private Double subtotal; 
}
