package com.budgetai.usecase;



import com.budgetai.application.dto.ExpenseSummaryDTO;
import com.budgetai.application.usecase.GetExpenseSummaryUseCase;
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

class GetExpenseSummaryUseCaseTest {

    @Test
    void deveGerarResumoDeDespesas() {

        // mock repository
        ExpenseRepository repository = mock(ExpenseRepository.class);

        // cria use case
        GetExpenseSummaryUseCase useCase =
                new GetExpenseSummaryUseCase(repository);

        // mock despesas
        Expense expense1 = Expense.builder()
                .id(UUID.randomUUID())
                .description("iFood")
                .amount(new BigDecimal("80"))
                .category(ExpenseCategory.FOOD)
                .location("São Paulo")
                .createdAt(LocalDateTime.now())
                .build();

        Expense expense2 = Expense.builder()
                .id(UUID.randomUUID())
                .description("Uber")
                .amount(new BigDecimal("50"))
                .category(ExpenseCategory.TRANSPORT)
                .location("São Paulo")
                .createdAt(LocalDateTime.now())
                .build();

        when(repository.findAll())
                .thenReturn(List.of(expense1, expense2));

        // executa
        ExpenseSummaryDTO summary = useCase.execute();

        // valida
        assertEquals(new BigDecimal("130"), summary.getTotalSpent());

        assertEquals("FOOD", summary.getTopCategory());

        assertEquals(2, summary.getTotalExpenses());

        verify(repository, times(1)).findAll();
    }
}