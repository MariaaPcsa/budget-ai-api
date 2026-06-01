package com.budgetai.application.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VoiceResponseDTOTest {

    @Test
    void shouldCreateVoiceResponseDTO() {

        VoiceResponseDTO dto =
                new VoiceResponseDTO(
                        "Quanto gastei hoje?",
                        "Você gastou hoje R$ 200.00"
                );

        assertEquals(
                "Quanto gastei hoje?",
                dto.transcript()
        );

        assertEquals(
                "Você gastou hoje R$ 200.00",
                dto.response()
        );
    }
}