package com.budgetai.tools;

import com.budgetai.application.dto.ExpenseRequestDTO;
import com.budgetai.application.usecase.CreateExpenseUseCase;
import com.budgetai.application.usecase.GetExpenseSummaryUseCase;
import com.budgetai.domain.service.ExpenseSummaryService;
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
    private final GetExpenseSummaryUseCase getExpenseSummaryUseCase;
    private final ExpenseSummaryService expenseSummaryService;

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

        log.info(" Registrando gasto: {} - {} - {}", amount, description, category);

        ExpenseRequestDTO dto = new ExpenseRequestDTO();
        dto.setAmount(amount);
        dto.setDescription(description);
        dto.setLocation(location);

        //  validação segura do enum
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

    @Tool(
            name = "consultar_resumo_gastos",
            description = "Consulta o resumo geral dos gastos acumulados, retornando o total gasto, a maior categoria de despesa e o total de transações"
    )
    public String consultarResumoGastos() {
        log.info(" Executando tool: consultar_resumo_gastos");
        var summary = getExpenseSummaryUseCase.execute();
        return String.format(
                "Resumo geral: total gasto R$ %s, categoria com maior gasto: %s, total de despesas registradas: %d",
                summary.getTotalSpent(),
                summary.getTopCategory(),
                summary.getTotalExpenses()
        );
    }

    @Tool(
            name = "consultar_gastos_hoje",
            description = "Consulta o total gasto no dia de hoje"
    )
    public String consultarGastosHoje() {
        log.info(" Executando tool: consultar_gastos_hoje");
        return expenseSummaryService.getTodayExpenses();
    }
}