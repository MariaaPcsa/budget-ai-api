package com.budgetai.application.usecase;

import com.budgetai.domain.entity.Expense;
import com.budgetai.domain.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetExpensesUseCase {

    private final ExpenseRepository repository;

    public List<Expense> execute(UUID userId) {
        return repository.findAllByUserId(userId);
    }
}