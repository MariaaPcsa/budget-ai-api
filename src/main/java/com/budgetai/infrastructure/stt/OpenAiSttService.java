package com.budgetai.infrastructure.stt;

import com.budgetai.infrastructure.config.OpenAiProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class OpenAiSttService implements SttService {

    private final OpenAiProperties properties;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String transcribeAudio(MultipartFile audioFile) {

        try {
            log.info("🎤 STT iniciado");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.setBearerAuth(properties.getKey());
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

            ByteArrayResource fileResource = new ByteArrayResource(audioFile.getBytes()) {
                @Override
                public String getFilename() {
                    return audioFile.getOriginalFilename();
                }
            };

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", fileResource);
            body.add("model", properties.getSttModel());

            HttpEntity<MultiValueMap<String, Object>> request =
                    new HttpEntity<>(body, headers);

            ResponseEntity<Map> response =
                    restTemplate.postForEntity(properties.getBaseUrl(), request, Map.class);

            return response.getBody() != null
                    ? String.valueOf(response.getBody().get("text"))
                    : "";

        } catch (Exception e) {
            throw new RuntimeException("Falha na transcrição: " + e.getMessage(), e);
        }
    }
}