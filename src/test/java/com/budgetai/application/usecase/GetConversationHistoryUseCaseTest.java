package com.budgetai.application.usecase;

import com.budgetai.application.dto.ConversationResponseDTO;
import com.budgetai.domain.entity.Conversation;
import com.budgetai.domain.repository.ConversationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetConversationHistoryUseCaseTest {

    private ConversationRepository repository;
    private GetConversationHistoryUseCase useCase;

    @BeforeEach
    void setup() {

        repository = mock(ConversationRepository.class);

        useCase = new GetConversationHistoryUseCase(
                repository
        );
    }

    @Test
    void shouldReturnConversationHistory() {

        LocalDateTime now = LocalDateTime.now();

        Conversation conversation =
                Conversation.builder()
                        .id(1L)
                        .userMessage("Quanto gastei hoje?")
                        .aiResponse("Você gastou hoje R$ 200.00")
                        .createdAt(now)
                        .build();

        UUID userId = UUID.randomUUID();
        when(repository.findAllByUserIdOrderByCreatedAtAsc(userId))
                .thenReturn(List.of(conversation));

        List<ConversationResponseDTO> result =
                useCase.execute(userId);

        assertEquals(1, result.size());

        ConversationResponseDTO dto =
                result.getFirst();

        assertEquals(1L, dto.id());

        assertEquals(
                "Quanto gastei hoje?",
                dto.userMessage()
        );

        assertEquals(
                "Você gastou hoje R$ 200.00",
                dto.aiResponse()
        );

        assertEquals(
                now,
                dto.createdAt()
        );

        verify(repository)
                .findAllByUserIdOrderByCreatedAtAsc(userId);
    }

    @Test
    void shouldReturnEmptyList() {

        UUID userId = UUID.randomUUID();
        when(repository.findAllByUserIdOrderByCreatedAtAsc(userId))
                .thenReturn(List.of());

        List<ConversationResponseDTO> result =
                useCase.execute(userId);

        assertNotNull(result);

        assertTrue(result.isEmpty());

        verify(repository)
                .findAllByUserIdOrderByCreatedAtAsc(userId);
    }
}