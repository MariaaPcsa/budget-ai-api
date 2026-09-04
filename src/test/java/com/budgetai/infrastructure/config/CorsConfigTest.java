package com.budgetai.infrastructure.config;



import org.junit.jupiter.api.Test;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CorsConfigTest {

    private final CorsConfig corsConfig =
            new CorsConfig(List.of("http://localhost:3000"));

    @Test
    void shouldCreateCorsFilter() {

        CorsFilter filter = corsConfig.corsFilter();

        assertNotNull(filter);
    }

    @Test
    void shouldAllowOnlyConfiguredOrigins() {
        CorsConfiguration configuration = corsConfig.corsConfiguration();

        assertEquals(List.of("http://localhost:3000"), configuration.getAllowedOrigins());
        assertFalse(configuration.getAllowedOrigins().contains("*"));
        assertTrue(configuration.getAllowCredentials());
    }
}
