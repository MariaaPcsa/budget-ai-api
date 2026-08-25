package com.budgetai.application.usecase;

import com.budgetai.domain.entity.Expense;
import com.budgetai.domain.repository.ExpenseRepository;
import com.budgetai.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetExpenseByIdUseCaseTest {

    private ExpenseRepository repository;
    private GetExpenseByIdUseCase useCase;

    @BeforeEach
    void setup() {
        repository = mock(ExpenseRepository.class);
        useCase = new GetExpenseByIdUseCase(repository);
    }

    @Test
    void shouldReturnExpenseWhenFound() {
        UUID id = UUID.randomUUID();
        Expense expense = Expense.builder()
                .id(id)
                .description("Mercado")
                .build();

        when(repository.findById(id)).thenReturn(Optional.of(expense));

        Expense result = useCase.execute(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Mercado", result.getDescription());
        verify(repository).findById(id);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> useCase.execute(id));
        verify(repository).findById(id);
    }
}
