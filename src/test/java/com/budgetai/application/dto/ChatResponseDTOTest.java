package com.budgetai.application.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChatResponseDTOTest {

    @Test
    void shouldCreateChatResponseDTO() {

        ChatResponseDTO dto =
                new ChatResponseDTO(
                        "success",
                        "Resposta da IA"
                );

        assertNotNull(dto);

        assertEquals(
                "success",
                dto.status()
        );

        assertEquals(
                "Resposta da IA",
                dto.response()
        );
    }
}
