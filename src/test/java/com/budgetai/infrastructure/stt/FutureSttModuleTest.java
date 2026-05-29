package com.budgetai.infrastructure.stt;

import com.budgetai.infrastructure.config.OpenAiProperties;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class FutureSttModuleTest {

    @Test
    void deveCriarServicoSTT() {

        OpenAiProperties properties =
                new OpenAiProperties();

        properties.setKey("sk-teste");

        properties.setBaseUrl(
                "https://api.openai.com/v1/audio/transcriptions"
        );

        properties.setSttModel("whisper-1");

        RestTemplate restTemplate =
                new RestTemplate();

        FutureSttModule service =
                new FutureSttModule(
                        restTemplate,
                        properties
                );

        MockMultipartFile audio =
                new MockMultipartFile(
                        "file",
                        "audio.mp3",
                        "audio/mpeg",
                        "teste".getBytes()
                );

        assertNotNull(service);
    }
}