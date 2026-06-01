package com.budgetai.domain.service;

import com.budgetai.domain.entity.Conversation;
import com.budgetai.exception.BusinessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConversationDomainServiceTest {

    private final ConversationDomainService service =
            new ConversationDomainService();

    @Test
    void shouldValidateConversationSuccessfully() {

        Conversation conversation = Conversation.builder()
                .userMessage("Quanto gastei hoje?")
                .aiResponse("Você gastou R$ 100")
                .build();

        assertDoesNotThrow(() ->
                service.validate(conversation)
        );
    }

    @Test
    void shouldThrowExceptionWhenUserMessageIsNull() {

        Conversation conversation = Conversation.builder()
                .userMessage(null)
                .aiResponse("Resposta")
                .build();

        BusinessException exception =
                assertThrows(
                        BusinessException.class,
                        () -> service.validate(conversation)
                );

        assertEquals(
                "Mensagem do usuário é obrigatória",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowExceptionWhenAiResponseIsNull() {

        Conversation conversation = Conversation.builder()
                .userMessage("Pergunta")
                .aiResponse(null)
                .build();

        BusinessException exception =
                assertThrows(
                        BusinessException.class,
                        () -> service.validate(conversation)
                );

        assertEquals(
                "Resposta da IA é obrigatória",
                exception.getMessage()
        );
    }
}