package com.budgetai.tools;

import com.budgetai.application.dto.ExpenseRequestDTO;
import com.budgetai.application.dto.ExpenseSummaryDTO;
import com.budgetai.application.port.CurrentUserProvider;
import com.budgetai.application.usecase.CreateExpenseUseCase;
import com.budgetai.application.usecase.GetExpenseSummaryUseCase;
import com.budgetai.domain.service.ExpenseSummaryService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ExpenseToolsTest {

        private final CurrentUserProvider currentUserProvider = mock(CurrentUserProvider.class);

    @Test
    void deveRegistrarGasto() {

        // 🔹 mocks
        CreateExpenseUseCase createExpenseUseCase =
                mock(CreateExpenseUseCase.class);
        GetExpenseSummaryUseCase getExpenseSummaryUseCase =
                mock(GetExpenseSummaryUseCase.class);
        ExpenseSummaryService expenseSummaryService =
                mock(ExpenseSummaryService.class);

        // 🔹 cria tool
        when(currentUserProvider.currentUserId()).thenReturn(java.util.UUID.randomUUID());
        ExpenseTools expenseTools = new ExpenseTools(
                createExpenseUseCase, getExpenseSummaryUseCase, expenseSummaryService, currentUserProvider);

        // 🔹 executa método
        String response = expenseTools.registrarGasto(
                new BigDecimal("80"),
                "iFood",
                "FOOD",
                "São Paulo"
        );

        // 🔹 captura o DTO enviado
        verify(createExpenseUseCase, times(1))
                .execute(any(), argThat(dto ->
                        dto.getAmount().equals(new BigDecimal("80")) &&
                                dto.getDescription().equals("iFood") &&
                                dto.getCategory().equals("FOOD") &&
                                dto.getLocation().equals("São Paulo")
                ));

        // 🔹 valida retorno
        assertEquals(
                "Gasto registrado com sucesso",
                response
        );
    }

    @Test
    void deveConsultarResumoGastos() {

        // 🔹 mocks
        CreateExpenseUseCase createExpenseUseCase =
                mock(CreateExpenseUseCase.class);
        GetExpenseSummaryUseCase getExpenseSummaryUseCase =
                mock(GetExpenseSummaryUseCase.class);
        ExpenseSummaryService expenseSummaryService =
                mock(ExpenseSummaryService.class);

        ExpenseSummaryDTO summaryDTO = ExpenseSummaryDTO.builder()
                .totalSpent(new BigDecimal("150.00"))
                .topCategory("FOOD")
                .totalExpenses(3)
                .build();

        when(currentUserProvider.currentUserId()).thenReturn(java.util.UUID.randomUUID());
        when(getExpenseSummaryUseCase.execute(any())).thenReturn(summaryDTO);

        // 🔹 cria tool
        ExpenseTools expenseTools = new ExpenseTools(
                createExpenseUseCase, getExpenseSummaryUseCase, expenseSummaryService, currentUserProvider);

        // 🔹 executa
        String response = expenseTools.consultarResumoGastos();

        // 🔹 valida
        assertEquals(
                "Resumo geral: total gasto R$ 150.00, categoria com maior gasto: FOOD, total de despesas registradas: 3",
                response
        );
        verify(getExpenseSummaryUseCase, times(1)).execute(any());
    }

    @Test
    void deveConsultarGastosHoje() {

        // 🔹 mocks
        CreateExpenseUseCase createExpenseUseCase =
                mock(CreateExpenseUseCase.class);
        GetExpenseSummaryUseCase getExpenseSummaryUseCase =
                mock(GetExpenseSummaryUseCase.class);
        ExpenseSummaryService expenseSummaryService =
                mock(ExpenseSummaryService.class);

        when(currentUserProvider.currentUserId()).thenReturn(java.util.UUID.randomUUID());
        when(expenseSummaryService.getTodayExpenses(any())).thenReturn("Você gastou hoje R$ 50.00");

        // 🔹 cria tool
        ExpenseTools expenseTools = new ExpenseTools(
                createExpenseUseCase, getExpenseSummaryUseCase, expenseSummaryService, currentUserProvider);

        // 🔹 executa
        String response = expenseTools.consultarGastosHoje();

        // 🔹 valida
        assertEquals(
                "Você gastou hoje R$ 50.00",
                response
        );
        verify(expenseSummaryService, times(1)).getTodayExpenses(any());
    }
}