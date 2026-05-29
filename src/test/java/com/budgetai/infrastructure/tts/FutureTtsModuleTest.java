package com.budgetai.infrastructure.tts;

import com.budgetai.infrastructure.config.OpenAiProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

class FutureTtsModuleTest {

    private FutureTtsModule futureTtsModule;

    @BeforeEach
    void setup() {

        OpenAiProperties properties = new OpenAiProperties();

        properties.setKey("fake-key");

        OpenAiProperties.Tts tts = new OpenAiProperties.Tts();
        tts.setModel("gpt-4o-mini-tts");
        tts.setVoice("alloy");
        tts.setBaseUrl("https://api.openai.com/v1/audio/speech");

        properties.setTts(tts);

        RestTemplate restTemplate = new RestTemplate();

        futureTtsModule =
                new FutureTtsModule(
                        properties,
                        restTemplate
                );
    }

    @Test
    void shouldCreateModule() {

        assertNotNull(futureTtsModule);
    }
}