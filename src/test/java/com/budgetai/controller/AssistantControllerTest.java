package com.budgetai.controller;

import com.budgetai.application.dto.ChatRequestDTO;
import com.budgetai.application.service.AssistantService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AssistantControllerTest {

    @Test
    void shouldReturnChatResponseSuccessfully() {

        AssistantService assistantService =
                mock(AssistantService.class);

        when(assistantService.processMessage("Olá"))
                .thenReturn("Olá, como posso ajudar?");

        AssistantController controller =
                new AssistantController(assistantService);

        ChatRequestDTO request =
                new ChatRequestDTO("Olá");

        ResponseEntity<?> response =
                controller.chat(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());

        verify(assistantService)
                .processMessage("Olá");
    }
}
