package com.budgetai.application.usecase;

import com.budgetai.application.dto.ExpenseSummaryDTO;
import com.budgetai.domain.entity.Expense;
import com.budgetai.domain.repository.ExpenseRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetExpenseSummaryUseCase {

    private final ExpenseRepository repository;

    public ExpenseSummaryDTO execute() {

        List<Expense> expenses = repository.findAll();

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