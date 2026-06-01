package com.budgetai.application.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ConversationResponseDTOTest {

    @Test
    void shouldCreateConversationResponseDTO() {

        LocalDateTime now = LocalDateTime.now();

        ConversationResponseDTO dto =
                new ConversationResponseDTO(
                        1L,
                        "Quanto gastei hoje?",
                        "Você gastou hoje R$ 200.00",
                        now
                );

        assertEquals(
                1L,
                dto.id()
        );

        assertEquals(
                "Quanto gastei hoje?",
                dto.userMessage()
        );

        assertEquals(
                "Você gastou hoje R$ 200.00",
                dto.aiResponse()
        );

        assertEquals(
                now,
                dto.createdAt()
        );
    }

    @Test
    void shouldCompareEqualRecords() {

        LocalDateTime now = LocalDateTime.now();

        ConversationResponseDTO dto1 =
                new ConversationResponseDTO(
                        1L,
                        "Olá",
                        "Oi",
                        now
                );

        ConversationResponseDTO dto2 =
                new ConversationResponseDTO(
                        1L,
                        "Olá",
                        "Oi",
                        now
                );

        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }
}