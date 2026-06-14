package com.prishare.backend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    private static final SecretKey KEY =
            Keys.hmacShaKeyFor(
                    "mysecretkeymysecretkeymysecretkey123".getBytes()
            );

    public static String generateToken(String email) {

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis() + 86400000
                        )
                )
                .signWith(KEY)
                .compact();
    }
    public static String validateToken(String token) {

    return Jwts.parser()
            .verifyWith(KEY)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
}
} 