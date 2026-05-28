package com.budgetai.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExpenseRequestDTO {

    @NotBlank
    private String description;

    @NotNull
    private BigDecimal amount;

    private String category;

    private String location;
}