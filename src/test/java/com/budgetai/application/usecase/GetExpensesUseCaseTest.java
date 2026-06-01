package com.budgetai.application.usecase;

import com.budgetai.domain.entity.Expense;
import com.budgetai.domain.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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

        when(repository.findAll())
                .thenReturn(List.of(expense));

        List<Expense> result =
                useCase.execute();

        assertEquals(1, result.size());

        verify(repository)
                .findAll();
    }

    @Test
    void shouldReturnEmptyList() {

        when(repository.findAll())
                .thenReturn(List.of());

        List<Expense> result =
                useCase.execute();

        assertTrue(result.isEmpty());
    }
}