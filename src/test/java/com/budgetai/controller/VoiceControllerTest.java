package com.budgetai.controller;

import com.budgetai.application.dto.VoiceResponseDTO;
import com.budgetai.application.usecase.VoiceChatUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VoiceController.class)
class VoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VoiceChatUseCase voiceChatUseCase;

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

        when(voiceChatUseCase.execute(any()))
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