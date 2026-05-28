package com.budgetai.application.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseRequestDTOTest {

    @Test
    void shouldCreateExpenseRequestDTO() {

        ExpenseRequestDTO dto =
                new ExpenseRequestDTO();

        dto.setDescription("Mercado");
        dto.setAmount(BigDecimal.valueOf(200));
        dto.setCategory("FOOD");
        dto.setLocation("São Paulo");

        assertNotNull(dto);

        assertEquals(
                "Mercado",
                dto.getDescription()
        );

        assertEquals(
                BigDecimal.valueOf(200),
                dto.getAmount()
        );

        assertEquals(
                "FOOD",
                dto.getCategory()
        );

        assertEquals(
                "São Paulo",
                dto.getLocation()
        );
    }
}
