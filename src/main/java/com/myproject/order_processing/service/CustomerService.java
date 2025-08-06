package com.myproject.order_processing.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.order_processing.model.Customer;
import com.myproject.order_processing.repository.CustomerRepository;

// Hinglish Comments: CustomerService - Customer se related business logic ka service class hai. Database se data laane, save/update/delete karne ka kaam yahan hota hai.
@Service // Is class ko Spring service banata hai (business logic yahan likhte hain)
public class CustomerService {
    @Autowired // Spring ko batata hai ki dependency inject karo
    private final CustomerRepository customerRepository; // CustomerRepository - database operations ke liye

    // Constructor - dependency injection ke liye
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Sabhi customers ki list laane wala method
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // ID ke basis par ek customer laane wala method
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    // Naya customer create/update karne ka method
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Customer delete karne ka method
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
