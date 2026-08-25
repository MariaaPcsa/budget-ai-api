package com.budgetai.application.service;

import com.budgetai.infrastructure.ai.SystemPrompts;
import com.budgetai.tools.ExpenseTools;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssistantService {

    private final ChatClient.Builder chatClientBuilder;
    private final ExpenseTools expenseTools;

    public String processMessage(String message) {

        log.info("Mensagem recebida: {}", message);

        /*
         ==========================================
         IA + TOOL CALLING
         ==========================================
         */

        try {

            ChatClient chatClient = chatClientBuilder.build();

            return chatClient.prompt()
                    .system(SystemPrompts.FINANCIAL_ASSISTANT)
                    .user(message)
                    .tools(expenseTools)
                    .call()
                    .content();

        } catch (Exception e) {

            log.error("Erro ao chamar OpenAI", e);

            return """
                    Não consegui processar sua solicitação agora.
                    Tente novamente em alguns segundos.
                    """;
        }
    }
}