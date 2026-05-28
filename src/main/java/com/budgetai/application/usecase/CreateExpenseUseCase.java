package com.budgetai.application.usecase;

import com.budgetai.application.dto.ExpenseRequestDTO;
import com.budgetai.application.mapper.ExpenseMapper;
import com.budgetai.domain.entity.Expense;

import com.budgetai.domain.repository.ExpenseRepository;
import com.budgetai.domain.service.ExpenseDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateExpenseUseCase {

    private final ExpenseRepository repository;
    private final ExpenseDomainService domainService;

    public Expense execute(ExpenseRequestDTO dto) {

        Expense expense = ExpenseMapper.toEntity(dto);

        domainService.validateExpense(expense);

        return repository.save(expense);
    }
}