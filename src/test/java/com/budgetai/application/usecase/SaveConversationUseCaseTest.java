package com.budgetai.application.usecase;

import com.budgetai.domain.entity.Conversation;
import com.budgetai.domain.repository.ConversationRepository;
import com.budgetai.domain.service.ConversationDomainService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class SaveConversationUseCaseTest {

    @Mock
    private ConversationRepository repository;

    @Mock
    private ConversationDomainService domainService;

    @InjectMocks
    private SaveConversationUseCase useCase;

    @Test
    void shouldSaveConversation() {

        String userMessage = "Quanto gastei hoje?";
        String aiResponse = "Você gastou hoje R$ 200.00";

        UUID userId = UUID.randomUUID();
        useCase.execute(userId, userMessage, aiResponse);

        ArgumentCaptor<Conversation> captor =
                ArgumentCaptor.forClass(Conversation.class);

        verify(domainService).validate(any(Conversation.class));

        verify(repository).save(captor.capture());

        Conversation savedConversation = captor.getValue();

        assertEquals(
                userMessage,
                savedConversation.getUserMessage()
        );

        assertEquals(
                aiResponse,
                savedConversation.getAiResponse()
        );

        assertNotNull(
                savedConversation.getCreatedAt()
        );

        assertEquals(userId, savedConversation.getUser().getId());
    }
}