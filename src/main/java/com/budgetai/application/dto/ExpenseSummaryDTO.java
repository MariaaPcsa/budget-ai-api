package com.budgetai.application.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ExpenseSummaryDTO {

    private BigDecimal totalSpent;

    private String topCategory;

    private Integer totalExpenses;
}