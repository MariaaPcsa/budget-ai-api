package com.budgetai.infrastructure.security;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JwtServiceTest {

    private static final String SECRET = "MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=";

    @Test
    void shouldGenerateAndParseTokenForAuthenticatedUser() {
        JwtService jwtService = new JwtService(SECRET, 3600);
        AuthenticatedUser user = new AuthenticatedUser(UUID.randomUUID(), "user@example.com");

        String token = jwtService.generateToken(user);

        assertEquals(user, jwtService.parseToken(token));
    }
}