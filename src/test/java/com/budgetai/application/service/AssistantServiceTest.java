package com.budgetai.application.service;



import com.budgetai.application.service.AssistantService;
import com.budgetai.tools.ExpenseTools;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AssistantServiceTest {

    @Test
    void shouldReturnAiResponse() {

        // MOCKS
        ChatClient.Builder builder = mock(ChatClient.Builder.class);

        ChatClient chatClient = mock(ChatClient.class);

        ExpenseTools expenseTools = mock(ExpenseTools.class);

        ChatClient.ChatClientRequestSpec requestSpec =
                mock(ChatClient.ChatClientRequestSpec.class);

        ChatClient.CallResponseSpec responseSpec =
                mock(ChatClient.CallResponseSpec.class);

        // FLOW
        when(builder.build()).thenReturn(chatClient);

        when(chatClient.prompt()).thenReturn(requestSpec);

        when(requestSpec.system(anyString()))
                .thenReturn(requestSpec);

        when(requestSpec.user(anyString()))
                .thenReturn(requestSpec);

        when(requestSpec.tools(any()))
                .thenReturn(requestSpec);

        when(requestSpec.call())
                .thenReturn(responseSpec);

        when(responseSpec.content())
                .thenReturn("Despesa registrada com sucesso!");

        // SERVICE
        AssistantService service =
                new AssistantService(
                        builder,
                        expenseTools
                );

        // EXECUTE
        String response =
                service.processMessage("Gastei 80 reais no iFood");

        // ASSERT
        assertEquals(
                "Despesa registrada com sucesso!",
                response
        );

        verify(chatClient, times(1)).prompt();
    }
}