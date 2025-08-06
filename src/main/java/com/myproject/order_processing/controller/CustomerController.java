package com.myproject.order_processing.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.order_processing.dto.CustomerDTO;
import com.myproject.order_processing.model.Customer;
import com.myproject.order_processing.service.CustomerService;

// Hinglish Comments: CustomerController - Customer se related REST endpoints ka controller hai. Yahan CRUD operations milenge.
@RestController // Is class ko REST controller banata hai (Spring Boot ko batata hai ki yeh HTTP requests handle karegi)
@RequestMapping("/api/customers") // Is controller ke sabhi endpoints ka base URL yeh hai
public class CustomerController {
    @Autowired // Spring ko batata hai ki dependency inject karo
    private final CustomerService customerService; // CustomerService - business logic ke liye

    // Constructor - dependency injection ke liye
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Sabhi customers ki list laane wala endpoint
    @GetMapping // GET /api/customers
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        List<CustomerDTO> customerDTOs = customers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(customerDTOs);
    }

    // ID ke basis par ek customer laane wala endpoint
    @GetMapping("/{id}") // GET /api/customers/{id}
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        return customer.map(this::convertToDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Naya customer create karne ka endpoint
    @PostMapping // POST /api/customers
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = convertToEntity(customerDTO);
        customer.setId(null); // Naye customer ke liye ID null honi chahiye
        Customer savedCustomer = customerService.saveCustomer(customer);
        return new ResponseEntity<>(convertToDto(savedCustomer), HttpStatus.CREATED);
    }

    // Customer update karne ka endpoint
    @PutMapping("/{id}") // PUT /api/customers/{id}
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return customerService.getCustomerById(id).map(existingCustomer -> {
            Customer customerToUpdate = convertToEntity(customerDTO);
            customerToUpdate.setId(id);
            Customer updatedCustomer = customerService.saveCustomer(customerToUpdate);
            return ResponseEntity.ok(convertToDto(updatedCustomer));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Customer delete karne ka endpoint
    @DeleteMapping("/{id}") // DELETE /api/customers/{id}
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        if (customerService.getCustomerById(id).isPresent()) {
            customerService.deleteCustomer(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Entity ko DTO me convert karne ka helper method
    private CustomerDTO convertToDto(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setAddress(customer.getAddress());
        customerDTO.setRole(customer.getRole());
        customerDTO.setContactNumber(customer.getContactNumber());
        // Password security ke liye DTO me nahi bhejte
        return customerDTO;
    }

    // DTO ko entity me convert karne ka helper method
    private Customer convertToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setAddress(customerDTO.getAddress());
        customer.setRole(customerDTO.getRole());
        customer.setContactNumber(customerDTO.getContactNumber());
        customer.setPassword(customerDTO.getPassword());
        return customer;
    }
}
