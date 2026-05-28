package com.budgetai.controller;

import com.budgetai.application.dto.ExpenseSummaryDTO;
import com.budgetai.application.usecase.GetExpenseSummaryUseCase;
import com.budgetai.application.usecase.GetExpensesUseCase;
import com.budgetai.domain.entity.Expense;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final GetExpensesUseCase getExpensesUseCase;
    private final GetExpenseSummaryUseCase getExpenseSummaryUseCase;

    @GetMapping
    public ResponseEntity<List<Expense>> getAll() {

        return ResponseEntity.ok(
                getExpensesUseCase.execute()
        );
    }

    @GetMapping("/summary")
    public ResponseEntity<ExpenseSummaryDTO> summary() {

        return ResponseEntity.ok(
                getExpenseSummaryUseCase.execute()
        );
    }
}