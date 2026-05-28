package com.budgetai.infrastructure.stt;

import org.springframework.web.multipart.MultipartFile;

public interface SttService {
    String transcribeAudio(MultipartFile audioFile);
}
