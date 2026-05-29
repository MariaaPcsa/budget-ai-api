package com.budgetai.application.usecase;

import com.budgetai.application.dto.ConversationResponseDTO;
import com.budgetai.domain.repository.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetConversationHistoryUseCase {

    private final ConversationRepository repository;

    public List<ConversationResponseDTO> execute() {

        return repository.findAll()
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
