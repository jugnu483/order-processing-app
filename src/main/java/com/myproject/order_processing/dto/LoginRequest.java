package com.myproject.order_processing.dto; 

import lombok.AllArgsConstructor;
import lombok.Data; 
import lombok.NoArgsConstructor; 
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class LoginRequest { // Defines the LoginRequest DTO for user login credentials

    private String username; 
    private String password; 
}
