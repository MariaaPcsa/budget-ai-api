package com.budgetai.infrastructure.security;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class JwtAuthenticationFilterTest {

    private static final String SECRET = "MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=";

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldAuthenticateValidBearerToken() throws Exception {
        JwtService jwtService = new JwtService(SECRET, 3600);
        AuthenticatedUser expectedUser = new AuthenticatedUser(UUID.randomUUID(), "user@example.com");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + jwtService.generateToken(expectedUser));

        new JwtAuthenticationFilter(jwtService).doFilter(
                request,
                new MockHttpServletResponse(),
                new MockFilterChain()
        );

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        assertInstanceOf(AuthenticatedUser.class, principal);
        assertEquals(expectedUser, principal);
    }

    @Test
    void shouldRejectInvalidBearerToken() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(new JwtService(SECRET, 3600));
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader("Authorization", "Bearer invalid-token");

        filter.doFilter(request, response, new MockFilterChain());

        assertEquals(401, response.getStatus());
    }
}