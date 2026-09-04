package com.budgetai.controller;

import com.budgetai.application.dto.ConversationResponseDTO;
import com.budgetai.application.port.CurrentUserProvider;
import com.budgetai.application.usecase.GetConversationHistoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/conversations")
@RequiredArgsConstructor
public class ConversationController {

    private final GetConversationHistoryUseCase useCase;
    private final CurrentUserProvider currentUserProvider;

    @GetMapping
    public List<ConversationResponseDTO> findAll() {
        return useCase.execute(currentUserProvider.currentUserId());
    }
}
