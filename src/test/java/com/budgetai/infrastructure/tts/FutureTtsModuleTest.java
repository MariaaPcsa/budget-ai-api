package com.budgetai.infrastructure.tts;

import com.budgetai.infrastructure.config.OpenAiProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FutureTtsModuleTest {

    @Mock
    private OpenAiProperties properties;

    @Mock
    private OpenAiProperties.Tts tts;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private FutureTtsModule module;

    @Test
    void shouldGenerateAudioSuccessfully() {

        byte[] expectedAudio =
                "audio".getBytes();

        when(properties.getKey())
                .thenReturn("sk-test");

        when(properties.getTts())
                .thenReturn(tts);

        when(tts.getBaseUrl())
                .thenReturn(
                        "https://api.openai.com/v1/audio/speech"
                );

        when(tts.getModel())
                .thenReturn("gpt-4o-mini-tts");

        when(tts.getVoice())
                .thenReturn("alloy");

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.POST),
                any(),
                eq(byte[].class)
        ))
                .thenReturn(
                        ResponseEntity.ok(expectedAudio)
                );

        byte[] result =
                module.generateAudio("Olá mundo");

        assertNotNull(result);
        assertEquals(
                expectedAudio.length,
                result.length
        );
    }

    @Test
    void shouldThrowRuntimeExceptionWhenTextIsBlank() {

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> module.generateAudio("")
                );

        assertTrue(
                exception.getMessage()
                        .contains("Texto para geração de áudio é obrigatório")
        );
    }
}