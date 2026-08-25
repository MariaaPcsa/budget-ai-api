package com.budgetai.application.usecase;

import com.budgetai.application.dto.ExpenseRequestDTO;
import com.budgetai.domain.entity.Expense;
import com.budgetai.domain.repository.ExpenseRepository;
import com.budgetai.domain.service.ExpenseDomainService;
import com.budgetai.exception.BusinessException;
import com.budgetai.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateExpenseUseCaseTest {

    private ExpenseRepository repository;
    private ExpenseDomainService domainService;
    private UpdateExpenseUseCase useCase;

    @BeforeEach
    void setup() {
        repository = mock(ExpenseRepository.class);
        domainService = new ExpenseDomainService();
        useCase = new UpdateExpenseUseCase(repository, domainService);
    }

    @Test
    void shouldUpdateExpenseSuccessfully() {
        UUID id = UUID.randomUUID();
        Expense existingExpense = Expense.builder()
                .id(id)
                .description("Antigo")
                .amount(BigDecimal.TEN)
                .category(null)
                .location("Antiga Loja")
                .build();

        ExpenseRequestDTO dto = new ExpenseRequestDTO();
        dto.setDescription("Novo");
        dto.setAmount(BigDecimal.valueOf(20));
        dto.setCategory("FOOD");
        dto.setLocation("Nova Loja");

        when(repository.findById(id)).thenReturn(Optional.of(existingExpense));
        when(repository.save(any(Expense.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Expense result = useCase.execute(id, dto);

        assertNotNull(result);
        assertEquals("Novo", result.getDescription());
        assertEquals(BigDecimal.valueOf(20), result.getAmount());
        assertEquals("Nova Loja", result.getLocation());
        assertEquals("FOOD", result.getCategory().name());
        verify(repository).findById(id);
        verify(repository).save(any(Expense.class));
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenNotFound() {
        UUID id = UUID.randomUUID();
        ExpenseRequestDTO dto = new ExpenseRequestDTO();
        dto.setDescription("Novo");
        dto.setAmount(BigDecimal.valueOf(20));
        dto.setCategory("FOOD");

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> useCase.execute(id, dto));
        verify(repository, never()).save(any(Expense.class));
    }

    @Test
    void shouldFailValidationWhenAmountIsNegative() {
        UUID id = UUID.randomUUID();
        Expense existingExpense = Expense.builder()
                .id(id)
                .description("Antigo")
                .amount(BigDecimal.TEN)
                .build();

        ExpenseRequestDTO dto = new ExpenseRequestDTO();
        dto.setDescription("Novo");
        dto.setAmount(BigDecimal.valueOf(-5));
        dto.setCategory("FOOD");

        when(repository.findById(id)).thenReturn(Optional.of(existingExpense));

        assertThrows(BusinessException.class, () -> useCase.execute(id, dto));
        verify(repository, never()).save(any(Expense.class));
    }
}
