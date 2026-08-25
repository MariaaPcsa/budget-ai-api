package com.budgetai.controller;

import com.budgetai.application.dto.ExpenseRequestDTO;
import com.budgetai.application.dto.ExpenseSummaryDTO;
import com.budgetai.application.usecase.*;
import com.budgetai.domain.entity.Expense;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final CreateExpenseUseCase createExpenseUseCase;
    private final GetExpensesUseCase getExpensesUseCase;
    private final GetExpenseByIdUseCase getExpenseByIdUseCase;
    private final UpdateExpenseUseCase updateExpenseUseCase;
    private final DeleteExpenseUseCase deleteExpenseUseCase;
    private final GetExpenseSummaryUseCase getExpenseSummaryUseCase;

    @PostMapping
    public ResponseEntity<Expense> create(@Valid @RequestBody ExpenseRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createExpenseUseCase.execute(dto));
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getAll() {
        return ResponseEntity.ok(
                getExpensesUseCase.execute()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(
                getExpenseByIdUseCase.execute(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> update(@PathVariable UUID id, @Valid @RequestBody ExpenseRequestDTO dto) {
        return ResponseEntity.ok(
                updateExpenseUseCase.execute(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteExpenseUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/summary")
    public ResponseEntity<ExpenseSummaryDTO> summary() {
        return ResponseEntity.ok(
                getExpenseSummaryUseCase.execute()
        );
    }
}
