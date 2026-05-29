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

        // 🔹 mock do use case
        CreateExpenseUseCase createExpenseUseCase =
                mock(CreateExpenseUseCase.class);

        // 🔹 cria tool
        ExpenseTools expenseTools =
                new ExpenseTools(createExpenseUseCase);

        // 🔹 executa método
        String response = expenseTools.registrarGasto(
                new BigDecimal("80"),
                "iFood",
                "FOOD",
                "São Paulo"
        );

        // 🔹 captura o DTO enviado
        verify(createExpenseUseCase, times(1))
                .execute(argThat(dto ->
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
}