package com.example.HomeBooks.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    // secret key
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    // Generate token
    public String generateToken(String email) {

        return Jwts.builder()

                // Payload
                .setSubject(email)

                // Issued time
                .setIssuedAt(new Date())

                // Expiration time
                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000 * 60 * 60
                        )
                )

                // Sign token
                .signWith(
                        SignatureAlgorithm.HS256,
                        SECRET_KEY
                )

                .compact();
    }

    // Extract all claims
    public Claims extractAllClaims(String token) {

        return Jwts.parser()

                .setSigningKey(SECRET_KEY)

                .parseClaimsJws(token)

                .getBody();
    }

    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver
    ) {

        final Claims claims =
                extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    public String extractEmail(String token) {

        return extractClaim(
                token,
                Claims::getSubject
        );
    }

    public boolean validateToken(
            String token,
            String email
    ) {

        final String extractedEmail =
                extractEmail(token);

        return extractedEmail.equals(email);
    }
}