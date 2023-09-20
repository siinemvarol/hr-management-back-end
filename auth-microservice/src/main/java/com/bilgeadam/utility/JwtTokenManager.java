package com.bilgeadam.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bilgeadam.repository.enums.ERole;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtTokenManager {
    String audience = "hrmanagement";
    String issuer = "bilgeadam";
    String secretKey = "secretKey";

    public Optional<String> createToken(Long id, ERole eRole) {
        String token = null;
        Date date = new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24));
        try {
            token = JWT.create()
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .withIssuedAt(new Date())
                    .withExpiresAt(date)
                    .withClaim("id", id)
                    .withClaim("role", eRole.toString())
                    .sign(Algorithm.HMAC512(secretKey));
            return Optional.of(token);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}