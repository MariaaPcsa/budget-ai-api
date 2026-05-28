package com.budgetai.infrastructure.ai;



import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SystemPromptsTest {

    @Test
    void shouldContainFinancialAssistantPrompt() {

        assertNotNull(SystemPrompts.FINANCIAL_ASSISTANT);

        assertTrue(
                SystemPrompts.FINANCIAL_ASSISTANT
                        .contains("assistente financeiro")
        );

        assertTrue(
                SystemPrompts.FINANCIAL_ASSISTANT
                        .contains("tools")
        );
    }
}
