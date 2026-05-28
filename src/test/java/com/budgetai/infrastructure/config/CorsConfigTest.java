package com.budgetai.infrastructure.config;



import org.junit.jupiter.api.Test;
import org.springframework.web.filter.CorsFilter;

import static org.junit.jupiter.api.Assertions.*;

class CorsConfigTest {

    private final CorsConfig corsConfig =
            new CorsConfig();

    @Test
    void shouldCreateCorsFilter() {

        CorsFilter filter = corsConfig.corsFilter();

        assertNotNull(filter);
    }
}
