package com.budgetai.usecase;



import com.budgetai.application.dto.ExpenseRequestDTO;
import com.budgetai.application.usecase.CreateExpenseUseCase;
import com.budgetai.domain.entity.Expense;
import com.budgetai.domain.repository.ExpenseRepository;
import com.budgetai.domain.service.ExpenseDomainService;
import com.budgetai.domain.valueobject.ExpenseCategory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateExpenseUseCaseTest {

    @Test
    void deveCriarDespesaComSucesso() {

        // mocks
        ExpenseRepository repository =
                mock(ExpenseRepository.class);

        ExpenseDomainService domainService =
                mock(ExpenseDomainService.class);

        // use case
        CreateExpenseUseCase useCase =
                new CreateExpenseUseCase(
                        repository,
                        domainService
                );

        // dto
        ExpenseRequestDTO dto = new ExpenseRequestDTO();

        dto.setDescription("iFood");
        dto.setAmount(new BigDecimal("80"));
        dto.setCategory("FOOD");
        dto.setLocation("São Paulo");

        // entidade salva
        Expense savedExpense = Expense.builder()
                .description("iFood")
                .amount(new BigDecimal("80"))
                .category(ExpenseCategory.FOOD)
                .location("São Paulo")
                .build();

        // mock save
        when(repository.save(any(Expense.class)))
                .thenReturn(savedExpense);

        // executa
        Expense result = useCase.execute(dto);

        // validações
        assertEquals("iFood",
                result.getDescription());

        assertEquals(
                new BigDecimal("80"),
                result.getAmount()
        );

        assertEquals(
                ExpenseCategory.FOOD,
                result.getCategory()
        );

        // verifica chamadas
        verify(domainService, times(1))
                .validateExpense(any(Expense.class));

        verify(repository, times(1))
                .save(any(Expense.class));
    }
}