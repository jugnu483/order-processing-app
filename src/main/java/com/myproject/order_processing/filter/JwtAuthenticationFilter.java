package com.myproject.order_processing.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.myproject.order_processing.service.CustomerUserDetailsService;
import com.myproject.order_processing.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Add for logging
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JwtAuthenticationFilter - JWT token ko validate karne aur user ko authenticate karne ka filter.
 * Yeh filter har request par chalta hai aur JWT token check karta hai.
 */
@Component // Is class ko Spring component banata hai (auto-detect hoti hai)
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    // JWT service aur user details service ka instance
    private final JwtService jwtService;
    private final CustomerUserDetailsService customerUserDetailsService;

    /**
     * Constructor dependency injection ke liye
     * @param jwtService - JWT se related logic
     * @param customerUserDetailsService - User details load karne ke liye
     */
    @Autowired
    public JwtAuthenticationFilter(JwtService jwtService, CustomerUserDetailsService customerUserDetailsService) {
        this.jwtService = jwtService;
        this.customerUserDetailsService = customerUserDetailsService;
    }

    /**
     * Har HTTP request par yeh method call hota hai.
     * JWT token ko check karta hai, user ko authenticate karta hai, aur security context set karta hai.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // Agar login ya register endpoint hai, toh filter skip karo
        String path = request.getRequestURI();
        if (path.contains("/api/auth/login") || path.contains("/api/auth/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Authorization header se JWT token nikaalo
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // Agar header nahi hai ya Bearer se start nahi hota, toh aage badho
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.debug("No Authorization header or does not start with Bearer. Skipping JWT filter.");
            filterChain.doFilter(request, response);
            return;
        }

        // JWT token extract karo
        jwt = authHeader.substring(7);
        logger.debug("Extracted JWT: {}", jwt);
        username = jwtService.extractUsername(jwt);
        logger.debug("Extracted username from JWT: {}", username);

        // Agar username mila aur abhi tak authenticate nahi hua
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = this.customerUserDetailsService.loadUserByUsername(username);
                logger.debug("Loaded user details for username: {}", username);
                // Token valid hai ya nahi check karo
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    logger.debug("JWT token is valid for user: {}", username);
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    logger.warn("JWT token is NOT valid for user: {}", username);
                }
            } catch (Exception e) {
                logger.error("Exception while loading user details or validating token for username: {}. Exception: {}", username, e.getMessage());
            }
        } else {
            if (username == null) {
                logger.warn("Username extracted from JWT is null.");
            } else {
                logger.debug("SecurityContext already has authentication for user: {}", username);
            }
        }
        filterChain.doFilter(request, response);
    }
}
