package com.budgetai.infrastructure.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OpenAiIntegration {

    public void logIntegration(String message) {

        log.info("OpenAI Integration: {}", message);
    }
}