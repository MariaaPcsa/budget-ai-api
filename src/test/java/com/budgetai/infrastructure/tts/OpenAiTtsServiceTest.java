package com.budgetai.infrastructure.tts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenAiTtsServiceTest {

    private final OpenAiTtsService service =
            new OpenAiTtsService();

    @Test
    void shouldReturnEmptyAudioArray() {

        byte[] result =
                service.generateAudio("Olá mundo");

        assertNotNull(result);
        assertEquals(0, result.length);
    }
}
