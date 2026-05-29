package com.budgetai.application.dto;

import java.time.LocalDateTime;

public record ConversationResponseDTO(
        Long id,
        String userMessage,
        String aiResponse,
        LocalDateTime createdAt
) {
}
