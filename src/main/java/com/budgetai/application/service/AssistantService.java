package com.budgetai.application.service;

import com.budgetai.infrastructure.ai.SystemPrompts;
import com.budgetai.tools.ExpenseTools;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssistantService {

    private final ChatClient.Builder chatClientBuilder;
    private final ExpenseTools expenseTools;

    public String processMessage(String message) {

        ChatClient chatClient = chatClientBuilder.build();

        return chatClient.prompt()
                .system(SystemPrompts.FINANCIAL_ASSISTANT)
                .user(message)
                .tools(expenseTools)
                .call()
                .content();
    }
}