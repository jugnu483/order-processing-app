package com.myproject.order_processing.repository; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.order_processing.model.Product; 

@Repository 
public interface ProductRepository extends JpaRepository<Product, Long> { 
   
}
