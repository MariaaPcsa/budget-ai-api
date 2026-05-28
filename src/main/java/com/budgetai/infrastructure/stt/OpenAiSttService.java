package com.budgetai.infrastructure.stt;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class OpenAiSttService implements SttService {

    @Override
    public String transcribeAudio(MultipartFile audioFile) {
        // chamada real Whisper / API
        return "texto transcrito real";
    }
}
