package com.budgetai.infrastructure.tts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FutureTtsModule {

    public byte[] generateAudio(String text) {

        log.info("Gerando áudio do texto: {}", text);

        /*
         Futuramente:
         - OpenAI TTS
         - ElevenLabs
         - Azure Speech
         */

        return new byte[]{};
    }
}