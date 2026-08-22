package com.gustavo.personalassistant.infra.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.gustavo.personalassistant.user.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtService {
    private static final String ISSUER = "personalAssistant";

    private final Algorithm algorithm;
    private final long      expirationMillis;

    public JwtService(@Value("${api.security.jwt.secret}") String secret,
                      @Value("${api.security.jwt.expiration}") long expirationMillis){
        this.algorithm = Algorithm.HMAC256(secret);
        this.expirationMillis = expirationMillis;
    }

    public String generateToken(User user){
        Instant now = Instant.now();

        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(user.getEmail())
                .withClaim("role", user.getRole().name())
                .withIssuedAt(now)
                .withExpiresAt(now.plusMillis(expirationMillis))
                .sign(algorithm);
    }

    public String extractEmail(String token){
        return JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build()
                .verify(token)
                .getSubject();
    }
}
