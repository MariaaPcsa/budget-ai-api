package com.budgetai.tools;



import com.budgetai.application.dto.ExpenseRequestDTO;
import com.budgetai.application.usecase.CreateExpenseUseCase;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ExpenseToolsTest {

    @Test
    void deveRegistrarGasto() {

        // mock do use case
        CreateExpenseUseCase createExpenseUseCase =
                mock(CreateExpenseUseCase.class);

        // cria tool
        ExpenseTools expenseTools =
                new ExpenseTools(createExpenseUseCase);

        // executa método
        String response = expenseTools.registrarGasto(
                new BigDecimal("80"),
                "iFood",
                "FOOD",
                "São Paulo"
        );

        // verifica chamada
        verify(createExpenseUseCase, times(1))
                .execute(any(ExpenseRequestDTO.class));

        // valida retorno
        assertEquals(
                "Gasto registrado com sucesso",
                response
        );
    }
}