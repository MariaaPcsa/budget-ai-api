package com.budgetai.domain.service;

import com.budgetai.domain.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpenseSummaryServiceTest {

    @Mock
    private ExpenseRepository repository;

    @InjectMocks
    private ExpenseSummaryService service;

    @Test
    void shouldReturnTodayExpensesSummary() {

        when(repository.getTodayTotal(
                any(),
                any()
        )).thenReturn(BigDecimal.valueOf(250));

        String result =
                service.getTodayExpenses();

        assertEquals(
                "Você gastou hoje R$ 250",
                result
        );
    }
}
