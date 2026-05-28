package com.budgetai.application.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseSummaryDTOTest {

    @Test
    void shouldCreateExpenseSummaryDTO() {

        ExpenseSummaryDTO dto =
                ExpenseSummaryDTO.builder()
                        .totalSpent(BigDecimal.valueOf(500))
                        .topCategory("FOOD")
                        .totalExpenses(5)
                        .build();

        assertNotNull(dto);

        assertEquals(
                BigDecimal.valueOf(500),
                dto.getTotalSpent()
        );

        assertEquals(
                "FOOD",
                dto.getTopCategory()
        );

        assertEquals(
                5,
                dto.getTotalExpenses()
        );
    }
}