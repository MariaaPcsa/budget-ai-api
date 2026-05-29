package com.budgetai.infrastructure.stt;

import com.budgetai.exception.ExternalServiceException;
import com.budgetai.infrastructure.config.OpenAiProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class FutureSttModule {

    private final RestTemplate restTemplate;
    private final OpenAiProperties properties;

    public String transcribeAudio(MultipartFile audioFile) {

        try {

            log.info("🎤 Iniciando STT: {}", audioFile.getOriginalFilename());

            validateAudio(audioFile);

            String apiKey = resolveApiKey();

            HttpHeaders headers = createHeaders(apiKey);

            ByteArrayResource fileResource =
                    createFileResource(audioFile);

            MultiValueMap<String, Object> body =
                    createRequestBody(fileResource);

            HttpEntity<MultiValueMap<String, Object>> request =
                    new HttpEntity<>(body, headers);

            ResponseEntity<Map<String, Object>> response =
                    sendRequest(request);

            log.info("✅ STT concluído com sucesso");

            return extractTranscript(response);

        } catch (IOException e) {

            log.error("Erro ao ler arquivo de áudio", e);

            throw new RuntimeException(
                    "Erro ao processar arquivo de áudio",
                    e
            );

        } catch (ExternalServiceException e) {

            throw e;

        } catch (Exception e) {

            log.error("❌ Erro inesperado no STT", e);

            throw new RuntimeException(
                    "Falha na transcrição de áudio: " + e.getMessage(),
                    e
            );
        }
    }

    private void validateAudio(MultipartFile audioFile) {

        if (audioFile == null || audioFile.isEmpty()) {

            throw new IllegalArgumentException(
                    "Arquivo de áudio é obrigatório"
            );
        }
    }

    private String resolveApiKey() {

        String apiKey = properties.getKey();

        if (apiKey == null || apiKey.isBlank()) {

            apiKey = System.getenv("OPENAI_API_KEY");
        }

        if (apiKey == null || apiKey.isBlank()) {

            throw new IllegalStateException(
                    "OPENAI_API_KEY não configurada"
            );
        }

        apiKey = apiKey.trim();

        if (!apiKey.startsWith("sk-")) {

            throw new IllegalStateException(
                    "Chave OpenAI inválida"
            );
        }

        log.info("🔑 OpenAI Key carregada: {}", maskKey(apiKey));

        return apiKey;
    }

    private HttpHeaders createHeaders(String apiKey) {

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBearerAuth(apiKey);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        return headers;
    }

    private ByteArrayResource createFileResource(
            MultipartFile audioFile
    ) throws IOException {

        return new ByteArrayResource(audioFile.getBytes()) {

            @Override
            public String getFilename() {

                return audioFile.getOriginalFilename();
            }
        };
    }

    private MultiValueMap<String, Object> createRequestBody(
            ByteArrayResource fileResource
    ) {

        MultiValueMap<String, Object> body =
                new LinkedMultiValueMap<>();

        body.add("file", fileResource);

        body.add(
                "model",
                properties.getSttModel() != null
                        ? properties.getSttModel()
                        : "whisper-1"
        );

        return body;
    }

    private ResponseEntity<Map<String, Object>> sendRequest(
            HttpEntity<MultiValueMap<String, Object>> request
    ) {

        String url =
                properties.getBaseUrl() != null
                        ? properties.getBaseUrl()
                        : "https://api.openai.com/v1/audio/transcriptions";

        ParameterizedTypeReference<Map<String, Object>> typeRef =
                new ParameterizedTypeReference<>() {};

        try {

            return restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    typeRef
            );

        } catch (HttpClientErrorException ex) {

            log.error(
                    "Erro 4xx OpenAI STT: {}",
                    ex.getResponseBodyAsString()
            );

            throw new ExternalServiceException(
                    HttpStatus.BAD_REQUEST,
                    "Erro OpenAI STT: " +
                            ex.getResponseBodyAsString(),
                    ex
            );

        } catch (HttpServerErrorException ex) {

            log.error(
                    "Erro 5xx OpenAI STT: {}",
                    ex.getResponseBodyAsString()
            );

            throw new ExternalServiceException(
                    HttpStatus.BAD_GATEWAY,
                    "Erro servidor OpenAI",
                    ex
            );

        } catch (ResourceAccessException ex) {

            log.error("Erro de conexão OpenAI", ex);

            throw new ExternalServiceException(
                    HttpStatus.BAD_GATEWAY,
                    "Falha de conexão com OpenAI",
                    ex
            );
        }
    }

    private String extractTranscript(
            ResponseEntity<Map<String, Object>> response
    ) {

        if (response.getBody() == null) {

            return "";
        }

        Object text = response.getBody().get("text");

        return text != null
                ? text.toString()
                : "";
    }

    private String maskKey(String key) {

        if (key == null || key.length() < 10) {

            return "****";
        }

        return key.substring(0, 4)
                + "****"
                + key.substring(key.length() - 4);
    }
}