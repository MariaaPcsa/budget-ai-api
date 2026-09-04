package com.budgetai.controller;

import com.budgetai.application.dto.VoiceResponseDTO;
import com.budgetai.application.port.CurrentUserProvider;
import com.budgetai.application.usecase.VoiceChatUseCase;
import com.budgetai.infrastructure.security.JwtAuthenticationFilter;
import com.budgetai.infrastructure.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = VoiceController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {SecurityConfig.class, JwtAuthenticationFilter.class}
        )
)
@AutoConfigureMockMvc(addFilters = false)
class VoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VoiceChatUseCase voiceChatUseCase;

        @MockBean
        private CurrentUserProvider currentUserProvider;

    @Test
    void shouldProcessVoiceSuccessfully() throws Exception {

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "audio.mp3",
                        "audio/mpeg",
                        "fake audio".getBytes()
                );

        VoiceResponseDTO dto =
                new VoiceResponseDTO(
                        "Quanto gastei hoje?",
                        "Você gastou hoje R$ 200.00"
                );

        when(currentUserProvider.currentUserId()).thenReturn(UUID.randomUUID());
        when(voiceChatUseCase.execute(any(), any()))
                .thenReturn(dto);

        mockMvc.perform(
                        multipart("/api/assistant/voice")
                                .file(file)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transcript")
                        .value("Quanto gastei hoje?"))
                .andExpect(jsonPath("$.response")
                        .value("Você gastou hoje R$ 200.00"));
    }

    @Test
    void shouldReturnBadRequestWhenFileIsEmpty() throws Exception {

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "",
                        "audio/mpeg",
                        new byte[0]
                );

        mockMvc.perform(
                        multipart("/api/assistant/voice")
                                .file(file)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.response")
                        .value("Arquivo de áudio não pode estar vazio"));
    }
}