package com.budgetai.domain.service;

import com.budgetai.domain.entity.Expense;
import com.budgetai.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ExpenseDomainService {

    public void validateExpense(Expense expense) {

        if (expense.getAmount() == null) {
            throw new BusinessException(
                    "Valor da despesa é obrigatório"
            );
        }

        if (expense.getAmount().compareTo(BigDecimal.ZERO) <= 0) {

            throw new BusinessException(
                    "Valor deve ser maior que zero"
            );
        }

        if (expense.getDescription() == null ||
                expense.getDescription().isBlank()) {

            throw new BusinessException(
                    "Descrição é obrigatória"
            );
        }
    }
}