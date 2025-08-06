// Hinglish Comments: CustomerUserDetailsService - Custom user details service hai jo Spring Security ke liye user load karta hai.
package com.myproject.order_processing.service; // Declares the package for the CustomerUserDetailsService class

import com.myproject.order_processing.model.Customer; // Imports the Customer entity class
import com.myproject.order_processing.repository.CustomerRepository; // Imports the CustomerRepository interface
import org.springframework.beans.factory.annotation.Autowired; // Imports Autowired for dependency injection
import org.springframework.security.core.userdetails.User; // Imports Spring Security's User class
import org.springframework.security.core.userdetails.UserDetails; // Imports Spring Security's UserDetails interface
import org.springframework.security.core.userdetails.UserDetailsService; // Imports Spring Security's UserDetailsService interface
import org.springframework.security.core.userdetails.UsernameNotFoundException; // Imports UsernameNotFoundException
import org.springframework.stereotype.Service; // Imports the @Service annotation
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;

// Is class ko Spring Service component banata hai
@Service
public class CustomerUserDetailsService implements UserDetailsService { // UserDetailsService implement karta hai custom user loading ke liye

    private final CustomerRepository customerRepository; // CustomerRepository - database se user details nikalne ke liye

    @Autowired // Spring ko batata hai ki dependency inject karo
    public CustomerUserDetailsService(CustomerRepository customerRepository) { // Constructor - dependency injection ke liye
        this.customerRepository = customerRepository;
    }

    @Override // UserDetailsService ka method override karte hain
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // Username ke basis par user load karta hai
        Customer customer = customerRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Customer not found with username: " + username)); // Agar user nahi mila toh exception throw karo

        // Customer entity se Spring Security ka User object banata hai
        return new User(
            customer.getName(),
            customer.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_" + customer.getRole()))
        ); // UserDetails return karta hai username, password, aur authority ke saath
    }
}
