package com.budgetai.application.usecase;

import com.budgetai.infrastructure.tts.TtsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GenerateSpeechUseCaseTest {

    private TtsService ttsService;
    private GenerateSpeechUseCase useCase;

    @BeforeEach
    void setup() {

        ttsService = mock(TtsService.class);

        useCase = new GenerateSpeechUseCase(
                ttsService
        );
    }

    @Test
    void shouldGenerateAudio() {

        byte[] expected = "audio".getBytes();

        when(ttsService.generateAudio("Olá"))
                .thenReturn(expected);

        byte[] result = useCase.execute("Olá");

        assertArrayEquals(
                expected,
                result
        );

        verify(ttsService)
                .generateAudio("Olá");
    }

    @Test
    void shouldThrowExceptionWhenTextIsNull() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> useCase.execute(null)
                );

        assertEquals(
                "Texto para geração de áudio é obrigatório",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowExceptionWhenTextIsBlank() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> useCase.execute(" ")
                );

        assertEquals(
                "Texto para geração de áudio é obrigatório",
                exception.getMessage()
        );
    }
}