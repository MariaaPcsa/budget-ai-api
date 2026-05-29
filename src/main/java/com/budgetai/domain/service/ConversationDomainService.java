package com.budgetai.domain.service;

import com.budgetai.domain.entity.Conversation;
import com.budgetai.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class ConversationDomainService {

    public void validate(Conversation conversation) {

        if (conversation.getUserMessage() == null ||
                conversation.getUserMessage().isBlank()) {

            throw new BusinessException(
                    "Mensagem do usuário é obrigatória"
            );
        }

        if (conversation.getAiResponse() == null ||
                conversation.getAiResponse().isBlank()) {

            throw new BusinessException(
                    "Resposta da IA é obrigatória"
            );
        }
    }
}
