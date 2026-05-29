package com.budgetai.infrastructure.tts;

import com.budgetai.infrastructure.config.OpenAiProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Getter
@Primary
@Component
@RequiredArgsConstructor
public class FutureTtsModule implements TtsService {

    private final OpenAiProperties properties;

    private final RestTemplate restTemplate;

    @Override
    public byte[] generateAudio(String text) {

        try {

            validateText(text);

            log.info("🔊 Gerando áudio com OpenAI TTS");

            HttpHeaders headers = createHeaders();

            Map<String, Object> body = createRequestBody(text);

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(body, headers);

            ResponseEntity<byte[]> response =
                    restTemplate.exchange(
                            properties.getTts().getBaseUrl(),
                            HttpMethod.POST,
                            request,
                            byte[].class
                    );

            if (response.getBody() == null) {

                throw new RuntimeException(
                        "OpenAI retornou áudio vazio"
                );
            }

            log.info("✅ Áudio gerado com sucesso");

            return response.getBody();

        } catch (Exception e) {

            log.error("❌ Erro ao gerar áudio", e);

            throw new RuntimeException(
                    "Falha ao gerar áudio: " + e.getMessage(),
                    e
            );
        }
    }

    private void validateText(String text) {

        if (text == null || text.isBlank()) {

            throw new IllegalArgumentException(
                    "Texto para geração de áudio é obrigatório"
            );
        }
    }

    private HttpHeaders createHeaders() {

        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(properties.getKey());

        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.setAccept(MediaType.parseMediaTypes("audio/mpeg"));

        return headers;
    }

    private Map<String, Object> createRequestBody(String text) {

        return Map.of(
                "model", properties.getTts().getModel(),
                "input", text,
                "voice", properties.getTts().getVoice(),
                "format", "mp3"
        );
    }
}