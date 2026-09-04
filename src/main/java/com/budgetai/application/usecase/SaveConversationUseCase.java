package com.budgetai.application.usecase;

import com.budgetai.domain.entity.Conversation;
import com.budgetai.domain.entity.User;
import com.budgetai.domain.repository.ConversationRepository;
import com.budgetai.domain.service.ConversationDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaveConversationUseCase {

    private final ConversationRepository repository;
    private final ConversationDomainService domainService;

    public void execute(UUID userId, String userMessage, String aiResponse) {
        Conversation conversation = Conversation.builder()
                .userMessage(userMessage)
                .aiResponse(aiResponse)
                .user(User.builder().id(userId).build())
                .createdAt(LocalDateTime.now())
                .build();

        domainService.validate(conversation);

        repository.save(conversation);
    }
}
