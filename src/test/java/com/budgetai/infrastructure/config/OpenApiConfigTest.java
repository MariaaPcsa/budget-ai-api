package com.budgetai.infrastructure.config;



import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenApiConfigTest {

    private final OpenApiConfig config =
            new OpenApiConfig();

    @Test
    void shouldCreateOpenApiConfiguration() {

        OpenAPI openAPI = config.customOpenAPI();

        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());

        assertEquals(
                "Budget AI API",
                openAPI.getInfo().getTitle()
        );

        assertEquals(
                "1.0",
                openAPI.getInfo().getVersion()
        );

        assertEquals(
                "API financeira inteligente",
                openAPI.getInfo().getDescription()
        );
    }
}
