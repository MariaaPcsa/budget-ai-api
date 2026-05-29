package com.budgetai.application.usecase;

import com.budgetai.infrastructure.tts.TtsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenerateSpeechUseCase {

    private final TtsService ttsService;

    public byte[] execute(String text) {

        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException(
                    "Texto para geração de áudio é obrigatório"
            );
        }

        return ttsService.generateAudio(text);
    }
}
