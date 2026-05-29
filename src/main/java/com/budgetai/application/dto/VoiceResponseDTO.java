package com.budgetai.application.dto;

/**
 * DTO responsável por retornar:
 * - transcript: texto transcrito do áudio (STT)
 * - response: resposta gerada pela IA
 */
public record VoiceResponseDTO(
        String transcript,
        String response
) {}