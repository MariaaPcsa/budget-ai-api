package com.budgetai.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "openai.api")
public class OpenAiProperties {

    /*
     =========================
     GLOBAL
     =========================
     */

    private String key;

    /*
     =========================
     STT
     =========================
     */

    private String sttModel;
    private String baseUrl;

    /*
     =========================
     TTS
     =========================
     */

    private Tts tts = new Tts();

    @Getter
    @Setter
    public static class Tts {

        private String model;
        private String voice;
        private String baseUrl;
    }
}