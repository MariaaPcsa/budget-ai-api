package com.budgetai.application.mapper;

import com.budgetai.application.dto.ExpenseRequestDTO;
import com.budgetai.domain.entity.Expense;
import com.budgetai.domain.valueobject.ExpenseCategory;

import java.time.LocalDateTime;

public class ExpenseMapper {

    public static Expense toEntity(ExpenseRequestDTO dto) {

        return Expense.builder()
                .description(dto.getDescription())
                .amount(dto.getAmount())
                .location(dto.getLocation())
                .category(ExpenseCategory.valueOf(dto.getCategory()))
                .createdAt(LocalDateTime.now())
                .build();
    }
}