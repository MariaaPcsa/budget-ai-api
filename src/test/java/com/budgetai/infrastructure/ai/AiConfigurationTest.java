package com.budgetai.infrastructure.ai;



import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AiConfigurationTest {

    @Test
    void shouldCreateChatClient() {

        ChatClient.Builder builder =
                mock(ChatClient.Builder.class);

        ChatClient chatClient =
                mock(ChatClient.class);

        when(builder.build()).thenReturn(chatClient);

        AiConfiguration configuration =
                new AiConfiguration();

        ChatClient result =
                configuration.chatClient(builder);

        assertNotNull(result);
        assertEquals(chatClient, result);

        verify(builder).build();
    }
}
