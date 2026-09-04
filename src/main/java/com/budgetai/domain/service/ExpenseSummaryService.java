package com.budgetai.domain.service;

import com.budgetai.domain.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseSummaryService {

    private final ExpenseRepository repository;

        public String getTodayExpenses(UUID userId) {

        LocalDate today = LocalDate.now();

        LocalDateTime start =
                today.atStartOfDay();

        LocalDateTime end =
                today.atTime(23, 59, 59);

        BigDecimal total =
                repository.getTodayTotal(
                        userId,
                        start,
                        end
                );

        return "Você gastou hoje R$ " + total;
    }
}