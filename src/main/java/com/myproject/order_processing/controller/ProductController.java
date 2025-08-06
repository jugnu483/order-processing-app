package com.myproject.order_processing.controller;

import com.myproject.order_processing.model.Product;
import com.myproject.order_processing.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * ProductController - REST controller for managing products.
 * Exposes endpoints for CRUD operations on products.
 */
@RestController // Marks this class as a REST controller
@RequestMapping("/api/products") // Maps requests to /api/products
public class ProductController {
    private final ProductService productService;

    /**
     * Constructor for dependency injection.
     * @param productService the product service
     */
    @Autowired // Injects ProductService
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Get all products.
     * @return list of all products
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    /**
     * Get a product by ID.
     * @param id the product ID
     * @return the product if found, otherwise 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new product.
     * @param product the product to create
     * @return the created product
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productService.saveProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    /**
     * Update a product by ID.
     * @param id the product ID
     * @param product the updated product data
     * @return the updated product if found, otherwise 404
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        if (!productService.getProductById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        product.setId(id);
        Product updatedProduct = productService.saveProduct(product);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Delete a product by ID.
     * @param id the product ID
     * @return 204 No Content if deleted, otherwise 404
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (!productService.getProductById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
} 