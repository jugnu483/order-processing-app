package com.myproject.order_processing.dto; // Declares the package for the LoginResponse class

import lombok.AllArgsConstructor;
import lombok.Data; 
import lombok.NoArgsConstructor; 
@Data 
@NoArgsConstructor 
@AllArgsConstructor
public class LoginResponse { 

    private String token; // Represents the generated JWT token upon successful login
    private String message; // Represents a message indicating the status of the login attempt
}
