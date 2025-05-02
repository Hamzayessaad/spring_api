package com.example.demo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    // Generate a secret key (in real apps, store it securely)
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // ✅ Used to generate JWT token
    public static String generateToken(String email, String fullname, String role) {
        long expirationMillis = 1000 * 60 * 60;

        return Jwts.builder()
                .setSubject(email)
                .claim("fullname", fullname)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(key)
                .compact();
    }

    // ✅ Used to read/verify JWT
    public static Key getKey() {
        return key;
    }
}
