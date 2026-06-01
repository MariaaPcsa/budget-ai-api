package com.budgetai.application.usecase;

import com.budgetai.application.dto.VoiceResponseDTO;
import com.budgetai.application.service.AssistantService;
import com.budgetai.infrastructure.stt.FutureSttModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VoiceChatUseCaseTest {

    private FutureSttModule sttModule;
    private AssistantService assistantService;
    private SaveConversationUseCase saveConversationUseCase;

    private VoiceChatUseCase useCase;

    @BeforeEach
    void setup() {

        sttModule = mock(FutureSttModule.class);
        assistantService = mock(AssistantService.class);
        saveConversationUseCase = mock(SaveConversationUseCase.class);

        useCase = new VoiceChatUseCase(
                sttModule,
                assistantService,
                saveConversationUseCase
        );
    }

    @Test
    void shouldProcessVoiceSuccessfully() {

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "audio.mp3",
                        "audio/mpeg",
                        "audio".getBytes()
                );

        when(sttModule.transcribeAudio(file))
                .thenReturn("Quanto gastei hoje?");

        when(assistantService.processMessage(
                "Quanto gastei hoje?"
        )).thenReturn(
                "Você gastou hoje R$ 200.00"
        );

        VoiceResponseDTO response =
                useCase.execute(file);

        assertNotNull(response);

        assertEquals(
                "Quanto gastei hoje?",
                response.transcript()
        );

        assertEquals(
                "Você gastou hoje R$ 200.00",
                response.response()
        );

        verify(saveConversationUseCase)
                .execute(
                        "Quanto gastei hoje?",
                        "Você gastou hoje R$ 200.00"
                );
    }

    @Test
    void shouldThrowExceptionWhenFileIsNull() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> useCase.execute(null)
                );

        assertEquals(
                "Arquivo de áudio não pode ser vazio",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowExceptionWhenFileIsEmpty() {

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "",
                        "audio/mpeg",
                        new byte[0]
                );

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> useCase.execute(file)
                );

        assertEquals(
                "Arquivo de áudio não pode ser vazio",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowExceptionWhenTranscriptIsEmpty() {

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "audio.mp3",
                        "audio/mpeg",
                        "audio".getBytes()
                );

        when(sttModule.transcribeAudio(file))
                .thenReturn("");

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> useCase.execute(file)
                );

        assertTrue(
                exception.getMessage()
                        .contains("Transcrição vazia")
        );
    }

    @Test
    void shouldThrowExceptionWhenAiResponseIsEmpty() {

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "audio.mp3",
                        "audio/mpeg",
                        "audio".getBytes()
                );

        when(sttModule.transcribeAudio(file))
                .thenReturn("Quanto gastei hoje?");

        when(assistantService.processMessage(any()))
                .thenReturn("");

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> useCase.execute(file)
                );

        assertTrue(
                exception.getMessage()
                        .contains("Resposta vazia")
        );
    }

    @Test
    void shouldSaveConversationAfterProcessing() {

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "audio.mp3",
                        "audio/mpeg",
                        "audio".getBytes()
                );

        when(sttModule.transcribeAudio(any()))
                .thenReturn("Olá");

        when(assistantService.processMessage(any()))
                .thenReturn("Resposta");

        useCase.execute(file);

        verify(saveConversationUseCase, times(1))
                .execute(
                        "Olá",
                        "Resposta"
                );
    }
}