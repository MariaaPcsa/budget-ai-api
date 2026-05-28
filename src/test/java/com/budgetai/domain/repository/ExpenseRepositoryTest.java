package com.budgetai.domain.repository;

import com.budgetai.domain.entity.Expense;
import com.budgetai.domain.valueobject.ExpenseCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ExpenseRepositoryTest {

    @Autowired
    private ExpenseRepository repository;

    @Test
    void deveSalvarDespesa() {

        Expense expense = Expense.builder()
                .description("iFood")
                .amount(new BigDecimal("80"))
                .category(ExpenseCategory.FOOD)
                .location("São Paulo")
                .createdAt(LocalDateTime.now())
                .build();

        Expense saved = repository.save(expense);

        assertNotNull(saved.getId());

        assertEquals(
                "iFood",
                saved.getDescription()
        );
    }
}