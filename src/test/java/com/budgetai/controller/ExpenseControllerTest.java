package com.budgetai.controller;

import com.budgetai.application.dto.ExpenseRequestDTO;
import com.budgetai.application.dto.ExpenseSummaryDTO;
import com.budgetai.application.port.CurrentUserProvider;
import com.budgetai.application.usecase.*;
import com.budgetai.domain.entity.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExpenseControllerTest {

    private CreateExpenseUseCase createExpenseUseCase;
    private GetExpensesUseCase getExpensesUseCase;
    private GetExpenseByIdUseCase getExpenseByIdUseCase;
    private UpdateExpenseUseCase updateExpenseUseCase;
    private DeleteExpenseUseCase deleteExpenseUseCase;
    private GetExpenseSummaryUseCase summaryUseCase;
    private CurrentUserProvider currentUserProvider;
    private ExpenseController controller;
    private UUID userId;

    @BeforeEach
    void setup() {
        createExpenseUseCase = mock(CreateExpenseUseCase.class);
        getExpensesUseCase = mock(GetExpensesUseCase.class);
        getExpenseByIdUseCase = mock(GetExpenseByIdUseCase.class);
        updateExpenseUseCase = mock(UpdateExpenseUseCase.class);
        deleteExpenseUseCase = mock(DeleteExpenseUseCase.class);
        summaryUseCase = mock(GetExpenseSummaryUseCase.class);
        currentUserProvider = mock(CurrentUserProvider.class);
        userId = UUID.randomUUID();
        when(currentUserProvider.currentUserId()).thenReturn(userId);

        controller = new ExpenseController(
                createExpenseUseCase,
                getExpensesUseCase,
                getExpenseByIdUseCase,
                updateExpenseUseCase,
                deleteExpenseUseCase,
                summaryUseCase,
                currentUserProvider
        );
    }

    @Test
    void shouldCreateExpense() {
        ExpenseRequestDTO dto = new ExpenseRequestDTO();
        dto.setDescription("Almoço");
        dto.setAmount(BigDecimal.valueOf(35.5));
        dto.setCategory("FOOD");

        Expense created = Expense.builder()
                .id(UUID.randomUUID())
                .description("Almoço")
                .amount(BigDecimal.valueOf(35.5))
                .build();

        when(createExpenseUseCase.execute(userId, dto)).thenReturn(created);

        ResponseEntity<Expense> response = controller.create(dto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(created, response.getBody());
        verify(createExpenseUseCase).execute(userId, dto);
    }

    @Test
    void shouldReturnAllExpenses() {
        Expense expense = Expense.builder()
                .description("Starbucks")
                .amount(BigDecimal.valueOf(50))
                .build();

        when(getExpensesUseCase.execute(userId)).thenReturn(List.of(expense));

        ResponseEntity<List<Expense>> response = controller.getAll();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
        verify(getExpensesUseCase).execute(userId);
    }

    @Test
    void shouldGetExpenseById() {
        UUID id = UUID.randomUUID();
        Expense expense = Expense.builder()
                .id(id)
                .description("Internet")
                .amount(BigDecimal.valueOf(120))
                .build();

        when(getExpenseByIdUseCase.execute(userId, id)).thenReturn(expense);

        ResponseEntity<Expense> response = controller.getById(id);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expense, response.getBody());
        verify(getExpenseByIdUseCase).execute(userId, id);
    }

    @Test
    void shouldUpdateExpense() {
        UUID id = UUID.randomUUID();
        ExpenseRequestDTO dto = new ExpenseRequestDTO();
        dto.setDescription("Internet Editada");
        dto.setAmount(BigDecimal.valueOf(130));

        Expense updated = Expense.builder()
                .id(id)
                .description("Internet Editada")
                .amount(BigDecimal.valueOf(130))
                .build();

        when(updateExpenseUseCase.execute(userId, id, dto)).thenReturn(updated);

        ResponseEntity<Expense> response = controller.update(id, dto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updated, response.getBody());
        verify(updateExpenseUseCase).execute(userId, id, dto);
    }

    @Test
    void shouldDeleteExpense() {
        UUID id = UUID.randomUUID();

        doNothing().when(deleteExpenseUseCase).execute(userId, id);

        ResponseEntity<Void> response = controller.delete(id);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(deleteExpenseUseCase).execute(userId, id);
    }

    @Test
    void shouldReturnExpenseSummary() {
        ExpenseSummaryDTO summary = ExpenseSummaryDTO.builder()
                .totalSpent(BigDecimal.valueOf(150))
                .topCategory("FOOD")
                .totalExpenses(3)
                .build();

        when(summaryUseCase.execute(userId)).thenReturn(summary);

        ResponseEntity<ExpenseSummaryDTO> response = controller.summary();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("FOOD", response.getBody().getTopCategory());
        verify(summaryUseCase).execute(userId);
    }
}
