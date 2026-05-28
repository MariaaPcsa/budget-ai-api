package com.budgetai.application.dto;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChatRequestDTOTest {

    @Test
    void shouldCreateChatRequestDTO() {

        ChatRequestDTO dto =
                new ChatRequestDTO("Olá IA");

        assertNotNull(dto);

        assertEquals(
                "Olá IA",
                dto.message()
        );
    }
}
