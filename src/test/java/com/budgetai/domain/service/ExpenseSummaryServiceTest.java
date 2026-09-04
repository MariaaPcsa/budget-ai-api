package com.budgetai.domain.service;

import com.budgetai.domain.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

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

        UUID userId = UUID.randomUUID();
        when(repository.getTodayTotal(
                any(UUID.class),
                any(),
                any()
        )).thenReturn(BigDecimal.valueOf(250));

        String result =
                service.getTodayExpenses(userId);

        assertEquals(
                "Você gastou hoje R$ 250",
                result
        );
    }
}
