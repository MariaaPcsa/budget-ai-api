package com.budgetai.application.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ExpenseResponseDTO {

    private UUID id;

    private String description;

    private BigDecimal amount;

    private String category;

    private String location;

    private LocalDateTime createdAt;
}