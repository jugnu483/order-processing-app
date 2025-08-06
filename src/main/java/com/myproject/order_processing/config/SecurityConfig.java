package com.myproject.order_processing.config; // Declares the package for the SecurityConfig class

import org.springframework.beans.factory.annotation.Autowired; // Imports the custom UserDetailsService
import org.springframework.context.annotation.Bean; // Imports the JWT authentication filter
import org.springframework.context.annotation.Configuration; // Imports Autowired for dependency injection
import org.springframework.security.authentication.AuthenticationManager; // Imports Bean for method-level bean definitions
import org.springframework.security.authentication.AuthenticationProvider; // Imports Configuration for marking a class as a source of bean definitions
import org.springframework.security.authentication.dao.DaoAuthenticationProvider; // Imports AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; // Imports AuthenticationProvider
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; // Imports DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity; // Imports AuthenticationConfiguration
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy; // Imports HttpSecurity for configuring web security
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Imports EnableWebSecurity to enable Spring Security's web security support
import org.springframework.security.crypto.password.PasswordEncoder; // Imports SessionCreationPolicy for stateless sessions
import org.springframework.security.web.SecurityFilterChain; // Imports BCryptPasswordEncoder for password encoding
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // Imports PasswordEncoder interface
import org.springframework.http.HttpMethod;

import com.myproject.order_processing.filter.JwtAuthenticationFilter; // Imports SecurityFilterChain
import com.myproject.order_processing.service.CustomerUserDetailsService; // Imports UsernamePasswordAuthenticationFilter

@Configuration // Marks this class as a source of bean definitions for the Spring application context
@EnableWebSecurity 
@EnableMethodSecurity// Enables Spring Security's web security support and provides the Spring Security integration with Spring MVC
public class SecurityConfig { // Defines the SecurityConfig class for Spring Security configuration

    private final CustomerUserDetailsService customerUserDetailsService; // Declares a final field for CustomerUserDetailsService
    private final JwtAuthenticationFilter jwtAuthFilter; // Declares a final field for JwtAuthenticationFilter

    @Autowired // Injects instances of CustomerUserDetailsService and JwtAuthenticationFilter
    public SecurityConfig(CustomerUserDetailsService customerUserDetailsService, JwtAuthenticationFilter jwtAuthFilter) { // Constructor for dependency injection
        this.customerUserDetailsService = customerUserDetailsService; // Assigns the injected user details service
        this.jwtAuthFilter = jwtAuthFilter; // Assigns the injected JWT authentication filter
    }

    @Bean // Marks the returned object as a Spring bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // Configures the security filter chain
        http
                .csrf(csrf -> csrf.disable()) // Disables CSRF protection (common for stateless REST APIs)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Allows unauthenticated access to authentication endpoints
                        .anyRequest().authenticated() // All other endpoints require authentication, no role restriction
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Configures stateless session management (no sessions created or used)
                )
                .authenticationProvider(authenticationProvider()) // Sets the custom authentication provider
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Adds the JWT filter before the UsernamePasswordAuthenticationFilter

        return http.build(); // Builds and returns the SecurityFilterChain
    }

    @Bean // Marks the returned object as a Spring bean
    public AuthenticationProvider authenticationProvider() { // Provides a custom authentication provider
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(); // Creates a DaoAuthenticationProvider
        authProvider.setUserDetailsService(customerUserDetailsService); // Sets the custom user details service
        authProvider.setPasswordEncoder(passwordEncoder()); // Sets the password encoder
        return authProvider; // Returns the configured authentication provider
    }

    @Bean // Marks the returned object as a Spring bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception { // Provides the AuthenticationManager
        return config.getAuthenticationManager(); // Returns the AuthenticationManager from the AuthenticationConfiguration
    }

    @Bean // Marks the returned object as a Spring bean
    public PasswordEncoder passwordEncoder() { // Provides a BCryptPasswordEncoder bean
        return new BCryptPasswordEncoder(); // Returns an instance of BCryptPasswordEncoder
    }
}
