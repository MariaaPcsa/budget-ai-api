package com.budgetai.application.usecase;

import com.budgetai.application.dto.ConversationResponseDTO;
import com.budgetai.domain.repository.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetConversationHistoryUseCase {

    private final ConversationRepository repository;

    public List<ConversationResponseDTO> execute(UUID userId) {

        return repository.findAllByUserIdOrderByCreatedAtAsc(userId)
                .stream()
                .map(conversation -> new ConversationResponseDTO(
                        conversation.getId(),
                        conversation.getUserMessage(),
                        conversation.getAiResponse(),
                        conversation.getCreatedAt()
                ))
                .toList();
    }
}
