package com.budgetai.application.usecase;

import com.budgetai.domain.repository.ExpenseRepository;
import com.budgetai.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteExpenseUseCaseTest {

    private ExpenseRepository repository;
    private DeleteExpenseUseCase useCase;

    @BeforeEach
    void setup() {
        repository = mock(ExpenseRepository.class);
        useCase = new DeleteExpenseUseCase(repository);
    }

    @Test
    void shouldDeleteExpenseSuccessfully() {
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(true);

        useCase.execute(id);

        verify(repository).existsById(id);
        verify(repository).deleteById(id);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> useCase.execute(id));
        verify(repository).existsById(id);
        verify(repository, never()).deleteById(any(UUID.class));
    }
}
