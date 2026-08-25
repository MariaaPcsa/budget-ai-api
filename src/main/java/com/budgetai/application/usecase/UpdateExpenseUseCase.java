package com.budgetai.application.usecase;

import com.budgetai.application.dto.ExpenseRequestDTO;
import com.budgetai.domain.entity.Expense;
import com.budgetai.domain.repository.ExpenseRepository;
import com.budgetai.domain.service.ExpenseDomainService;
import com.budgetai.domain.valueobject.ExpenseCategory;
import com.budgetai.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateExpenseUseCase {

    private final ExpenseRepository repository;
    private final ExpenseDomainService domainService;

    public Expense execute(UUID id, ExpenseRequestDTO dto) {
        Expense expense = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Despesa com ID " + id + " não encontrada"));

        expense.setDescription(dto.getDescription());
        expense.setAmount(dto.getAmount());
        expense.setLocation(dto.getLocation());
        
        if (dto.getCategory() != null) {
            try {
                expense.setCategory(ExpenseCategory.valueOf(dto.getCategory().toUpperCase()));
            } catch (IllegalArgumentException e) {
                expense.setCategory(ExpenseCategory.OTHER);
            }
        }

        domainService.validateExpense(expense);

        return repository.save(expense);
    }
}
