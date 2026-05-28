package com.budgetai.tools;

import com.budgetai.application.dto.ExpenseRequestDTO;
import com.budgetai.application.usecase.CreateExpenseUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ExpenseTools {

    private final CreateExpenseUseCase createExpenseUseCase;

    @Tool(
            name = "registrar_gasto",
            description = "Registra uma despesa financeira"
    )
    public String registrarGasto(
            BigDecimal amount,
            String description,
            String category,
            String location
    ) {

        ExpenseRequestDTO dto = new ExpenseRequestDTO();

        dto.setAmount(amount);
        dto.setDescription(description);
        dto.setCategory(category);
        dto.setLocation(location);

        createExpenseUseCase.execute(dto);

        return "Gasto registrado com sucesso";
    }
}