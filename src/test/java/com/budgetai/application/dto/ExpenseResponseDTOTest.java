package com.budgetai.application.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseResponseDTOTest {

    @Test
    void shouldCreateExpenseResponseDTO() {

        UUID id = UUID.randomUUID();

        LocalDateTime now =
                LocalDateTime.now();

        ExpenseResponseDTO dto =
                ExpenseResponseDTO.builder()
                        .id(id)
                        .description("Netflix")
                        .amount(BigDecimal.valueOf(39.90))
                        .category("ENTERTAINMENT")
                        .location("Online")
                        .createdAt(now)
                        .build();

        assertNotNull(dto);

        assertEquals(id, dto.getId());

        assertEquals(
                "Netflix",
                dto.getDescription()
        );

        assertEquals(
                BigDecimal.valueOf(39.90),
                dto.getAmount()
        );

        assertEquals(
                "ENTERTAINMENT",
                dto.getCategory()
        );

        assertEquals(
                "Online",
                dto.getLocation()
        );

        assertEquals(
                now,
                dto.getCreatedAt()
        );
    }
}
