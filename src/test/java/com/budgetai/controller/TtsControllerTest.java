package com.budgetai.controller;

import com.budgetai.application.usecase.GenerateSpeechUseCase;
import com.budgetai.infrastructure.security.JwtAuthenticationFilter;
import com.budgetai.infrastructure.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = TtsController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {SecurityConfig.class, JwtAuthenticationFilter.class}
        )
)
@AutoConfigureMockMvc(addFilters = false)
class TtsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenerateSpeechUseCase generateSpeechUseCase;

    @Test
    void shouldGenerateAudio() throws Exception {

        byte[] audioBytes =
                "fake-audio".getBytes();

        when(generateSpeechUseCase.execute("Olá mundo"))
                .thenReturn(audioBytes);

        mockMvc.perform(
                        post("/api/tts")
                                .param("text", "Olá mundo")
                )
                .andExpect(status().isOk())
                .andExpect(header().string(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=audio.mp3"
                ))
                .andExpect(header().string(
                        HttpHeaders.CONTENT_TYPE,
                        "audio/mpeg"
                ))
                .andExpect(content().bytes(audioBytes));
    }
}