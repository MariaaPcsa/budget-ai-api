package com.budgetai.application.usecase;

import com.budgetai.domain.entity.Expense;
import com.budgetai.domain.repository.ExpenseRepository;
import com.budgetai.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetExpenseByIdUseCase {

    private final ExpenseRepository repository;

    public Expense execute(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Despesa com ID " + id + " não encontrada"));
    }
}
