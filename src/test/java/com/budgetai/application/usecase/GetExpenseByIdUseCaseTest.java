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
        UUID userId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        Expense expense = Expense.builder()
                .id(id)
                .description("Mercado")
                .build();

        when(repository.findByIdAndUserId(id, userId)).thenReturn(Optional.of(expense));

        Expense result = useCase.execute(userId, id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Mercado", result.getDescription());
        verify(repository).findByIdAndUserId(id, userId);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenNotFound() {
        UUID userId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        when(repository.findByIdAndUserId(id, userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> useCase.execute(userId, id));
        verify(repository).findByIdAndUserId(id, userId);
    }

    @Test
    void shouldNotReturnExpenseOwnedByAnotherUser() {
        UUID ownerId = UUID.randomUUID();
        UUID anotherUserId = UUID.randomUUID();
        UUID expenseId = UUID.randomUUID();
        when(repository.findByIdAndUserId(expenseId, anotherUserId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> useCase.execute(anotherUserId, expenseId));

        verify(repository).findByIdAndUserId(expenseId, anotherUserId);
        verify(repository, never()).findById(expenseId);
    }
}
