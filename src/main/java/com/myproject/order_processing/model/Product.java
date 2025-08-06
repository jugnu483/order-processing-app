
package com.myproject.order_processing.model;

import jakarta.persistence.*;

/**
 * Product - Product ki entity class, database table ke liye.
 * Yeh class ek product ke saare details ko represent karti hai.
 */
@Entity // Is class ko JPA entity banata hai (database table se map karta hai)
@Table(name = "products") // Table ka naam set karta hai
public class Product {
    /** Product ki unique ID (Primary key, auto-generated) */
    @Id // Primary key ke liye
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

    /** Product ka naam */
    @Column(nullable = false)
    private String name;

    /** Product ka description */
    @Column(nullable = false)
    private String description;

    /** Product ki price */
    @Column(nullable = false)
    private Double price;

    /** Product ka stock (kitne available hain) */
    @Column(nullable = false)
    private Integer stock;

    // Getter aur Setter methods
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}
