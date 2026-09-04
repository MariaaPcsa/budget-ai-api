package com.budgetai.application.usecase;

import com.budgetai.application.dto.ExpenseSummaryDTO;
import com.budgetai.domain.entity.Expense;
import com.budgetai.domain.repository.ExpenseRepository;
import com.budgetai.domain.valueobject.ExpenseCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetExpenseSummaryUseCaseTest {

    private ExpenseRepository repository;
    private GetExpenseSummaryUseCase useCase;

    @BeforeEach
    void setup() {

        repository = mock(ExpenseRepository.class);

        useCase = new GetExpenseSummaryUseCase(
                repository
        );
    }

    @Test
    void shouldCalculateSummary() {

        Expense e1 = Expense.builder()
                .amount(new BigDecimal("50"))
                .category(ExpenseCategory.FOOD)
                .build();

        Expense e2 = Expense.builder()
                .amount(new BigDecimal("100"))
                .category(ExpenseCategory.TRANSPORT)
                .build();

        UUID userId = UUID.randomUUID();
        when(repository.findAllByUserId(userId))
                .thenReturn(List.of(e1, e2));

        ExpenseSummaryDTO result =
                useCase.execute(userId);

        assertEquals(
                new BigDecimal("150"),
                result.getTotalSpent()
        );

        assertEquals(
                "TRANSPORT",
                result.getTopCategory()
        );

        assertEquals(
                2,
                result.getTotalExpenses()
        );
    }

    @Test
    void shouldReturnEmptySummary() {

        UUID userId = UUID.randomUUID();
        when(repository.findAllByUserId(userId))
                .thenReturn(List.of());

        ExpenseSummaryDTO result =
                useCase.execute(userId);

        assertEquals(
                BigDecimal.ZERO,
                result.getTotalSpent()
        );

        assertEquals(
                "NONE",
                result.getTopCategory()
        );

        assertEquals(
                0,
                result.getTotalExpenses()
        );
    }
}