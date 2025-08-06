package com.myproject.order_processing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;
import com.myproject.order_processing.service.CustomerService;
import com.myproject.order_processing.model.Customer;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

@SpringBootApplication
public class OrderProcessingApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderProcessingApplication.class, args);
	}

	@Bean
	public CommandLineRunner createDefaultAdmin(CustomerService customerService, PasswordEncoder passwordEncoder) {
		return args -> {
			Optional<Customer> adminOpt = customerService.getAllCustomers().stream()
				.filter(c -> c.getName().equals("admin"))
				.findFirst();
			if (adminOpt.isEmpty()) {
				Customer admin = new Customer();
				admin.setName("admin");
				admin.setEmail("admin@example.com");
				admin.setPassword(passwordEncoder.encode("admin123"));
				admin.setAddress("Admin Address");
				admin.setRole("ADMIN");
				admin.setContactNumber("1234567890");
				customerService.saveCustomer(admin);
				System.out.println("Default admin user created: admin / admin123");
			}
		};
	}
}
