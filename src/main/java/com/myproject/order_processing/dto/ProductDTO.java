package com.myproject.order_processing.dto;

/**
 * ProductDTO - Product data ko ek layer se doosri layer tak bhejne ke liye use hota hai.
 * Yeh class product ki details client aur server ke beech transfer karne ke liye banayi gayi hai.
 */
public class ProductDTO {
    /** Product ki unique ID (हर प्रोडक्ट की अलग पहचान) */
    private Long id;
    /** Product ka naam (Name of the product) */
    private String name;
    /** Product ka description (Product ka details/summary) */
    private String description;
    /** Product ki price (Product ki keemat) */
    private Double price;
    /** Product ka stock (Kitne available hain) */
    private Integer stock;

    // Getter aur Setter methods - Data ko safely access aur modify karne ke liye
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