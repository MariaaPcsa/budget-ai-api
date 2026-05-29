package com.budgetai.application.usecase;

import com.budgetai.domain.entity.Conversation;
import com.budgetai.domain.repository.ConversationRepository;
import com.budgetai.domain.service.ConversationDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SaveConversationUseCase {

    private final ConversationRepository repository;
    private final ConversationDomainService domainService;

    public void execute(String userMessage, String aiResponse) {

        Conversation conversation = Conversation.builder()
                .userMessage(userMessage)
                .aiResponse(aiResponse)
                .createdAt(LocalDateTime.now())
                .build();

        domainService.validate(conversation);

        repository.save(conversation);
    }
}
