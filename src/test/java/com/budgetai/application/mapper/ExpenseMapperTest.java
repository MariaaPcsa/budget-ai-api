package com.budgetai.application.mapper;

import com.budgetai.application.dto.ExpenseRequestDTO;
import com.budgetai.domain.entity.Expense;
import com.budgetai.domain.valueobject.ExpenseCategory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseMapperTest {

    @Test
    void shouldConvertDtoToEntity() {

        ExpenseRequestDTO dto =
                new ExpenseRequestDTO();

        dto.setDescription("Mercado");
        dto.setAmount(BigDecimal.valueOf(150));
        dto.setCategory("FOOD");
        dto.setLocation("São Paulo");

        Expense expense =
                ExpenseMapper.toEntity(dto);

        assertNotNull(expense);

        assertEquals(
                "Mercado",
                expense.getDescription()
        );

        assertEquals(
                BigDecimal.valueOf(150),
                expense.getAmount()
        );

        assertEquals(
                ExpenseCategory.FOOD,
                expense.getCategory()
        );

        assertEquals(
                "São Paulo",
                expense.getLocation()
        );

        assertNotNull(
                expense.getCreatedAt()
        );
    }
}