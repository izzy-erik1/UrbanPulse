package com.urbanpulse.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    // NOTE: in a later polish step we'll move this to application.properties
    // instead of hardcoding it, same as we did with DB credentials.
    private static final SecretKey SECRET_KEY =
            Keys.hmacShaKeyFor("urbanpulse-super-secret-key-change-me-1234567890".getBytes());

    private static final long EXPIRATION_MS = 24 * 60 * 60 * 1000; // 24 hours

    private JwtUtil() {}

    public static String generateToken(Long userId, String email) {
        return Jwts.builder()
                .subject(email)
                .claim("userId", userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(SECRET_KEY)
                .compact();
    }

    public static Claims validateAndParse(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
