package com.budgetai.tools;

import com.budgetai.application.dto.ExpenseRequestDTO;
import com.budgetai.application.usecase.CreateExpenseUseCase;
import com.budgetai.domain.valueobject.ExpenseCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExpenseTools {

    private final CreateExpenseUseCase createExpenseUseCase;

    @Tool(
            name = "registrar_gasto",
            description = "Registra uma despesa financeira com valor, descrição, categoria e localização"
    )
    public String registrarGasto(
            BigDecimal amount,
            String description,
            String category,
            String location
    ) {

        log.info("💰 Registrando gasto: {} - {} - {}", amount, description, category);

        ExpenseRequestDTO dto = new ExpenseRequestDTO();
        dto.setAmount(amount);
        dto.setDescription(description);
        dto.setLocation(location);

        // 🔥 validação segura do enum
        try {
            dto.setCategory(
                    ExpenseCategory.valueOf(category.toUpperCase())
                            .name()
            );
        } catch (Exception e) {
            log.warn("Categoria inválida: {}. Usando OTHER", category);
            dto.setCategory(ExpenseCategory.OTHER.name());
        }

        createExpenseUseCase.execute(dto);

        return "Gasto registrado com sucesso";
    }
}