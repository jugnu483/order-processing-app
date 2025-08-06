package com.myproject.order_processing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Customer - Customer ki entity class, database table ke liye.
 * Yeh class customer ke saare details ko represent karti hai.
 */
@Entity // Is class ko JPA entity banata hai (database table se map karta hai)
@Table(name = "customers") // Table ka naam set karta hai
@Data // Lombok annotation - getters/setters, equals, hashCode, toString auto-generate karta hai
@NoArgsConstructor // Lombok annotation - no-argument constructor banata hai
@AllArgsConstructor // Lombok annotation - all-argument constructor banata hai
public class Customer {
    /** Customer ki unique ID (Primary key, auto-generated) */
    @Id // Primary key ke liye
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long id;

    /** Customer ka naam (Name) */
    @Column(nullable = false)
    private String name;

    /** Customer ka email (unique hona chahiye) */
    @Column(nullable = false, unique = true)
    private String email;

    /** Customer ka password (Account ke liye) */
    @Column(nullable = false)
    private String password;

    /** Customer ka address */
    @Column(nullable = false)
    private String address;

    /** Customer ka role (USER/ADMIN) */
    @Column(nullable = false)
    private String role;

    /** Customer ka contact number (optional) */
    @Column(nullable = true)
    private String contactNumber;
}