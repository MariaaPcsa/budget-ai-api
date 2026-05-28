package com.budgetai.infrastructure.tts;

import org.springframework.stereotype.Service;

@Service
public class OpenAiTtsService implements TtsService {

    @Override
    public byte[] generateAudio(String text) {
        // chamada API TTS
        return new byte[0];
    }
}
