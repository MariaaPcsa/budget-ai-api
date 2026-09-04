package com.budgetai.application.usecase;

import com.budgetai.domain.repository.ExpenseRepository;
import com.budgetai.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteExpenseUseCase {

    private final ExpenseRepository repository;

    public void execute(UUID userId, UUID id) {
        if (!repository.existsByIdAndUserId(id, userId)) {
            throw new ResourceNotFoundException("Despesa com ID " + id + " não encontrada");
        }
        repository.deleteById(id);
    }
}
