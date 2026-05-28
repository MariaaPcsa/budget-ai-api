package com.budgetai.infrastructure.stt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class FutureSttModule {

    public String transcribeAudio(
            MultipartFile audioFile
    ) {

        log.info("Processando áudio: {}",
                audioFile.getOriginalFilename());

        /*
         Futuramente:
         - OpenAI Whisper
         - Google Speech
         - AWS Transcribe
         */

        return "Transcrição futura";
    }
}