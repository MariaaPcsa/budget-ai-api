package com.budgetai.application.usecase;

import com.budgetai.application.dto.ExpenseSummaryDTO;
import com.budgetai.domain.entity.Expense;
import com.budgetai.domain.repository.ExpenseRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetExpenseSummaryUseCase {

    private final ExpenseRepository repository;

        public ExpenseSummaryDTO execute(UUID userId) {

                List<Expense> expenses = repository.findAllByUserId(userId);

        BigDecimal total = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String topCategory = expenses.stream()
                .max(Comparator.comparing(Expense::getAmount))
                .map(e -> e.getCategory().name())
                .orElse("NONE");

        return ExpenseSummaryDTO.builder()
                .totalSpent(total)
                .topCategory(topCategory)
                .totalExpenses(expenses.size())
                .build();
    }
}