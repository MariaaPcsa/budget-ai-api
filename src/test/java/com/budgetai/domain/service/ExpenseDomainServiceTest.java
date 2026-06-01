package com.budgetai.domain.service;

import com.budgetai.domain.entity.Expense;
import com.budgetai.exception.BusinessException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseDomainServiceTest {

    private final ExpenseDomainService service =
            new ExpenseDomainService();

    @Test
    void shouldValidateExpenseSuccessfully() {

        Expense expense = Expense.builder()
                .amount(BigDecimal.valueOf(100))
                .description("Mercado")
                .build();

        assertDoesNotThrow(() ->
                service.validateExpense(expense)
        );
    }

    @Test
    void shouldThrowExceptionWhenAmountIsNull() {

        Expense expense = Expense.builder()
                .description("Mercado")
                .build();

        BusinessException exception =
                assertThrows(
                        BusinessException.class,
                        () -> service.validateExpense(expense)
                );

        assertEquals(
                "Valor da despesa é obrigatório",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowExceptionWhenAmountIsZero() {

        Expense expense = Expense.builder()
                .amount(BigDecimal.ZERO)
                .description("Mercado")
                .build();

        BusinessException exception =
                assertThrows(
                        BusinessException.class,
                        () -> service.validateExpense(expense)
                );

        assertEquals(
                "Valor deve ser maior que zero",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowExceptionWhenDescriptionIsBlank() {

        Expense expense = Expense.builder()
                .amount(BigDecimal.valueOf(50))
                .description("")
                .build();

        BusinessException exception =
                assertThrows(
                        BusinessException.class,
                        () -> service.validateExpense(expense)
                );

        assertEquals(
                "Descrição é obrigatória",
                exception.getMessage()
        );
    }
}
