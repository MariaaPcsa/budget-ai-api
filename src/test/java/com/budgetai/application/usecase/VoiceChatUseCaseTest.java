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

    private VoiceChatUseCase voiceChatUseCase;

    @BeforeEach
    void setup() {

        sttModule = mock(FutureSttModule.class);

        assistantService = mock(AssistantService.class);

        saveConversationUseCase = mock(
                SaveConversationUseCase.class
        );

        voiceChatUseCase = new VoiceChatUseCase(
                sttModule,
                assistantService,
                saveConversationUseCase
        );
    }

    @Test
    void shouldProcessVoiceSuccessfully() {

        MockMultipartFile audioFile =
                new MockMultipartFile(
                        "file",
                        "audio.mp3",
                        "audio/mpeg",
                        "fake audio".getBytes()
                );

        when(sttModule.transcribeAudio(audioFile))
                .thenReturn("Quanto gastei hoje?");

        when(assistantService.processMessage("Quanto gastei hoje?"))
                .thenReturn("Você gastou R$ 150 hoje.");

        VoiceResponseDTO response =
                voiceChatUseCase.execute(audioFile);

        assertNotNull(response);

        assertEquals(
                "Quanto gastei hoje?",
                response.transcript()
        );

        assertEquals(
                "Você gastou R$ 150 hoje.",
                response.response()
        );

        verify(saveConversationUseCase, times(1))
                .execute(
                        "Quanto gastei hoje?",
                        "Você gastou R$ 150 hoje."
                );
    }
}