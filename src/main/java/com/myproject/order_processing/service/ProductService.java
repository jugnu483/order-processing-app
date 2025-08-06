package com.myproject.order_processing.service;

import com.myproject.order_processing.model.Product;
import com.myproject.order_processing.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Hinglish Comments: ProductService - Product se related business logic yahan likhi jaati hai.
@Service // Is class ko Spring service banata hai (Product logic ke liye)
public class ProductService {
    private final ProductRepository productRepository; // ProductRepository - database operations ke liye

    // Constructor - dependency injection ke liye
    @Autowired // Spring ko batata hai ki dependency inject karo
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Sabhi products ki list laane wala method
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // ID ke basis par ek product laane wala method
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Naya product create/update karne ka method
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // Product delete karne ka method
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
} 