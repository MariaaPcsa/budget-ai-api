package com.budgetai.infrastructure.integration;



import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class OpenAiIntegrationTest {

    private final OpenAiIntegration integration =
            new OpenAiIntegration();

    @Test
    void shouldLogIntegrationMessage() {

        assertDoesNotThrow(() -> {
            integration.logIntegration("Teste de integração");
        });
    }
}
