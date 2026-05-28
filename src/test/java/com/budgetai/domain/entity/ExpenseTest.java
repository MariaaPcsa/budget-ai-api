package com.budgetai.domain.entity;



import com.budgetai.domain.entity.Expense;
import com.budgetai.domain.valueobject.ExpenseCategory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseTest {

    @Test
    void shouldCreateExpenseSuccessfully() {

        Expense expense = Expense.builder()
                .description("iFood")
                .amount(new BigDecimal("80.00"))
                .category(ExpenseCategory.FOOD)
                .location("São Paulo")
                .build();

        assertNotNull(expense);

        assertEquals(
                "iFood",
                expense.getDescription()
        );

        assertEquals(
                new BigDecimal("80.00"),
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
    }


}
