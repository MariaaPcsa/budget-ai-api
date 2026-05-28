package com.budgetai.usecase;



import com.budgetai.application.usecase.GetExpensesUseCase;
import com.budgetai.domain.entity.Expense;
import com.budgetai.domain.repository.ExpenseRepository;
import com.budgetai.domain.valueobject.ExpenseCategory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GetExpensesUseCaseTest {

    @Test
    void deveRetornarListaDeDespesas() {

        // mock repository
        ExpenseRepository repository =
                mock(ExpenseRepository.class);

        // use case
        GetExpensesUseCase useCase =
                new GetExpensesUseCase(repository);

        // mock despesa
        Expense expense = Expense.builder()
                .id(UUID.randomUUID())
                .description("Netflix")
                .amount(new BigDecimal("39.90"))
                .category(ExpenseCategory.ENTERTAINMENT)
                .location("São Paulo")
                .createdAt(LocalDateTime.now())
                .build();

        // comportamento mockado
        when(repository.findAll())
                .thenReturn(List.of(expense));

        // executa
        List<Expense> result = useCase.execute();

        // validações
        assertEquals(1, result.size());

        assertEquals("Netflix",
                result.get(0).getDescription());

        assertEquals(
                new BigDecimal("39.90"),
                result.get(0).getAmount()
        );

        verify(repository, times(1))
                .findAll();
    }
}