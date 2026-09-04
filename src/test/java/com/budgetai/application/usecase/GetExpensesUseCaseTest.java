package com.budgetai.application.usecase;

import com.budgetai.domain.entity.Expense;
import com.budgetai.domain.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetExpensesUseCaseTest {

    private ExpenseRepository repository;
    private GetExpensesUseCase useCase;

    @BeforeEach
    void setup() {

        repository = mock(ExpenseRepository.class);

        useCase = new GetExpensesUseCase(
                repository
        );
    }

    @Test
    void shouldReturnExpenses() {

        Expense expense =
                Expense.builder()
                        .description("iFood")
                        .build();

        UUID userId = UUID.randomUUID();
        when(repository.findAllByUserId(userId))
                .thenReturn(List.of(expense));

        List<Expense> result =
                useCase.execute(userId);

        assertEquals(1, result.size());

        verify(repository)
                .findAllByUserId(userId);
    }

    @Test
    void shouldReturnEmptyList() {

        UUID userId = UUID.randomUUID();
        when(repository.findAllByUserId(userId))
                .thenReturn(List.of());

        List<Expense> result =
                useCase.execute(userId);

        assertTrue(result.isEmpty());
    }
}