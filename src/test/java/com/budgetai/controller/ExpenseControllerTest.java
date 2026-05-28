package com.budgetai.controller;

import com.budgetai.application.dto.ExpenseSummaryDTO;
import com.budgetai.application.usecase.GetExpenseSummaryUseCase;
import com.budgetai.application.usecase.GetExpensesUseCase;
import com.budgetai.domain.entity.Expense;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExpenseControllerTest {

    @Test
    void shouldReturnAllExpenses() {

        GetExpensesUseCase getExpensesUseCase =
                mock(GetExpensesUseCase.class);

        GetExpenseSummaryUseCase summaryUseCase =
                mock(GetExpenseSummaryUseCase.class);

        Expense expense = Expense.builder()
                .description("Starbucks")
                .amount(BigDecimal.valueOf(50))
                .build();

        when(getExpensesUseCase.execute())
                .thenReturn(List.of(expense));

        ExpenseController controller =
                new ExpenseController(
                        getExpensesUseCase,
                        summaryUseCase
                );

        ResponseEntity<List<Expense>> response =
                controller.getAll();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());

        verify(getExpensesUseCase).execute();
    }

    @Test
    void shouldReturnExpenseSummary() {

        GetExpensesUseCase getExpensesUseCase =
                mock(GetExpensesUseCase.class);

        GetExpenseSummaryUseCase summaryUseCase =
                mock(GetExpenseSummaryUseCase.class);

        ExpenseSummaryDTO summary =
                ExpenseSummaryDTO.builder()
                        .totalSpent(BigDecimal.valueOf(150))
                        .topCategory("FOOD")
                        .totalExpenses(3)
                        .build();

        when(summaryUseCase.execute())
                .thenReturn(summary);

        ExpenseController controller =
                new ExpenseController(
                        getExpensesUseCase,
                        summaryUseCase
                );

        ResponseEntity<ExpenseSummaryDTO> response =
                controller.summary();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("FOOD",
                response.getBody().getTopCategory());

        verify(summaryUseCase).execute();
    }
}
