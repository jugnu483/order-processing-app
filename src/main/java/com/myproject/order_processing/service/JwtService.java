
package com.myproject.order_processing.service; // Declares the package for the JwtService class

import io.jsonwebtoken.Claims; // Imports Claims interface for JWT payload
import io.jsonwebtoken.Jwts; // Imports Jwts for JWT builder and parser
import io.jsonwebtoken.SignatureAlgorithm; // Imports SignatureAlgorithm for signing JWTs
import io.jsonwebtoken.io.Decoders; // Imports Decoders for base64 decoding
import io.jsonwebtoken.security.Keys; // Imports Keys for generating secure keys
import org.springframework.beans.factory.annotation.Value; // Imports Value for injecting properties
import org.springframework.security.core.userdetails.UserDetails; // Imports UserDetails for user information
import org.springframework.stereotype.Service; // Imports the @Service annotation

import java.security.Key; // Imports Key for cryptographic keys
import java.util.Date; // Imports Date for token expiration
import java.util.HashMap; // Imports HashMap for claims
import java.util.Map; // Imports Map for claims
import java.util.function.Function; // Imports Function for functional interfaces

// Hinglish Comments: JwtService - JWT token generate, validate, aur extract karne ka service hai. Saari JWT logic yahan hoti hai.
@Service // Is class ko Spring service banata hai (JWT logic ke liye)
public class JwtService {
    @Value("${jwt.secret}") // application.properties se JWT secret key inject karta hai
    private String secretKey;

    @Value("${jwt.expiration}") // application.properties se JWT expiration time inject karta hai
    private long expirationTime;

    // JWT token se username extract karne ka method
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // JWT token se koi bhi claim extract karne ka generic method
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // UserDetails se JWT token generate karne ka method
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // Extra claims ke saath JWT token generate karne ka method
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // JWT token validate karne ka method
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // JWT token expire hai ya nahi check karne ka method
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // JWT token se expiration date extract karne ka method
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // JWT token se saare claims extract karne ka method
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Secret key se signing key banane ka method
    private Key getSigningKey() {
        byte[] keyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
